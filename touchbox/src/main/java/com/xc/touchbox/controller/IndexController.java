package com.xc.touchbox.controller;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.xc.gospel.core.util.SimpleUtils;
import com.xc.touchbox.dao.CDAOTemplate;
import com.xc.touchbox.model.Account;
import com.xc.touchbox.model.ISysParam;
import com.xc.touchbox.service.UserService;

@SuppressWarnings("serial")
@ParentPackage("root-default")
@Results({ @Result(name = "success", type = "redirect", location = "/index") })
public class IndexController extends BaseAction {
	private static final Logger log = Logger.getLogger(IndexController.class);

	// 用户Service
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 功能：管理后台首页 调用：GET /manage/index 返回：index.jsp
	 * 
	 * @return
	 */
	@Action(value = "/manage/index", results = { @Result(name = "success", location = "index.jsp") })
	public String manageIndex() {
		this.fetchSessionAdmin();
		if (sessionAdmin != null) {
			return SUCCESS;
		}
		return LOGIN;
	}

	/**
	 * 功能：登陆页面 调用：GET /login 返回：login.jsp
	 * 
	 * @return
	 */
	@Action(value = "login", results = { @Result(name = "success", location = "manage/login.jsp") })
	public String login() {
		return SUCCESS;
	}

	/**
	 * 功能：登出 调用：GET /logout 返回：/login
	 * 
	 * @return
	 */
	@Action(value = "logout", results = { @Result(name = "success", type = "redirect", location = "/login") })
	public String logout() {
		sessionMap.clear();

		return SUCCESS;
	}

	/**
	 * 用户登陆（管理员&用户） 调用：GET /doLogin 返回：index
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "doLogin", results = { @Result(name = "adminSuccess", type = "redirect", location = "/manage/index") })
	public String doLogin() throws Exception {
		int errorCode = 0;
		try {
			boolean authMD5Key = true;

			StringBuilder authkeyStr = new StringBuilder();
			authkeyStr.append("c=").append(c).append("&username=")
					.append(username).append("&password=").append(password);

			authMD5Key = this.checkAuthMD5Key(authkeyStr.toString());
			if (authMD5Key) {// 秘钥鉴权成功

				Account a = new Account();
				a.setUsername(username);
				a.setPassword(password);
				errorCode = userService.doLogin(a);

				if (errorCode == 0) {
					if (a.isAdminEnabled()) {
						sessionAdmin = a.getAdmin();
						if (sessionAdmin != null) {
							sessionAdmin.getRoleList().addAll(
									userService.getAdminRoles(sessionAdmin
											.getUserId()));

							userService.fetchAdminResources(sessionAdmin);
							sessionMap.put(CDAOTemplate.SESSION_ADMIN,
									sessionAdmin);
						}
					}
					
					if (a.isUserEnabled()) {
						sessionUser = a.getUser();
						if (sessionUser != null) {
							sessionMap.put(CDAOTemplate.SESSION_USER,
									sessionUser);
						}
						return SUCCESS;
					}else if (a.isAdminEnabled()) {
						return "adminSuccess";
					}
				}
			} else {
				errorCode = ISysParam.API_RESPONSE_ERRORCODE_AUTHMD5KEY_FAIL;
			}
		} catch (Exception e) {
			SimpleUtils.log(e, log);
			errorCode = ISysParam.API_RESPONSE_ERRORCODE_EXISTEXCEPTION;
			baseResp.setErrorMsg(e.toString());
		}
		baseResp.setErrorCode(errorCode);
		return LOGIN;
	}

}
