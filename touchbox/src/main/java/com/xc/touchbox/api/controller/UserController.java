package com.xc.touchbox.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.BeanUtils;

import com.xc.gospel.core.util.SimpleUtils;
import com.xc.gospel.core.vo.PaginationSupport;
import com.xc.touchbox.controller.BaseAction;
import com.xc.touchbox.model.Account;
import com.xc.touchbox.model.Admin;
import com.xc.touchbox.model.ISysParam;
import com.xc.touchbox.model.Role;
import com.xc.touchbox.model.User;
import com.xc.touchbox.model.Verification;
import com.xc.touchbox.model.view.AccountView;
import com.xc.touchbox.model.view.AdminView;
import com.xc.touchbox.model.view.GetDataResponse;
import com.xc.touchbox.model.view.LoginResponse;
import com.xc.touchbox.model.view.PaginationResponse;
import com.xc.touchbox.model.view.UserView;

@SuppressWarnings("serial")
@ParentPackage("json-default")
@Namespace("/api/user")
@Results({ @Result(name = "error", type = "json", params = { "root", "baseResp" }) })
public class UserController extends BaseAction {

	/**
	 * 
	 */
	private static final Logger log = Logger.getLogger(UserController.class);

	private Map<String, Object> msg = new HashMap<String, Object>();

	public Map<String, Object> getMsg() {
		return msg;
	}

	private LoginResponse loginResp = new LoginResponse();

	public LoginResponse getLoginResp() {
		return loginResp;
	}

	private int roleId;// 角色ID
	private String roleGroupId;// 角色分组ID
	private Admin admin;// 输入admin对象数据
	private User user;// 输入user对象数据
	private String newPassword;// 新密码

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleGroupId() {
		return roleGroupId;
	}

	public void setRoleGroupId(String roleGroupId) {
		this.roleGroupId = roleGroupId;
	}

	/**
	 * 获取验证码
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "getVcode", params = { "encode", "true" }, results = { @Result(name = "success", type = "json", params = {
			"root", "baseResp" }) })
	public String getVcode() throws Exception {
		try {
			boolean authMD5Key = true;

			StringBuilder authkeyStr = new StringBuilder();
			authkeyStr.append("c=").append(c).append("&serialId=")
					.append(serialId).append("&mobile=").append(mobile);

			authMD5Key = this.checkAuthMD5Key(authkeyStr.toString());

			if (authMD5Key) {// 秘钥鉴权成功
				Verification a = userService.doSendVcode(serialId, mobile);

				if (a != null) {
				} else {
					baseResp.setErrorCode(ISysParam.API_RESPONSE_ERRORCODE_GETSMSVCODE_FAIL);
				}
			} else {
				baseResp.setErrorCode(ISysParam.API_RESPONSE_ERRORCODE_AUTHMD5KEY_FAIL);
			}
		} catch (Exception e) {
			SimpleUtils.log(e, log);
		}

		return SUCCESS;
	}

	/**
	 * 用户登陆（管理员&用户）
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "login", results = { @Result(name = "success", type = "json", params = {
			"root", "loginResp" }) })
	public String login() throws Exception {
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
					AccountView b = new AccountView();
					BeanUtils.copyProperties(a, b);
					loginResp.setAccount(b);
					return SUCCESS;
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
		return ERROR;
	}

	/**
	 * 查询用户列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "findUser", results = { @Result(name = "success", type = "json", params = {
			"root", "paginationResp", "excludeNullProperties", "true" }) })
	public String findUser() throws Exception {
		int errorCode = 0;
		try {
			boolean authMD5Key = true;

			StringBuilder authkeyStr = new StringBuilder();
			authkeyStr.append("c=").append(c);

			authMD5Key = this.checkAuthMD5Key(authkeyStr.toString());
			if (authMD5Key) {// 秘钥鉴权成功
				PaginationSupport<User> ps = userService.findUser(mobile, page,
						pagesize);

				if (ps != null) {
					paginationResp = new PaginationResponse(ps, UserView.class);
					return SUCCESS;
				} else {
					errorCode = ISysParam.API_RESPONSE_ERRORCODE_FINDDATA_NULL;
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
		return ERROR;
	}

	/**
	 * 获取管理员账号数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "fetchUser", results = { @Result(name = "success", type = "json", params = {
			"root", "getDataResp", "excludeNullProperties", "true" }) })
	public String fetchUser() throws Exception {
		int errorCode = 0;
		try {
			boolean authMD5Key = true;

			StringBuilder authkeyStr = new StringBuilder();
			authkeyStr.append("c=").append(c).append("&userId=").append(userId);

			authMD5Key = this.checkAuthMD5Key(authkeyStr.toString());

			if (authMD5Key) {// 秘钥鉴权成功
				User a = (User) userService.get(User.class, userId);

				if (a != null) {
					getDataResp = new GetDataResponse(a, UserView.class);

					return SUCCESS;

				} else {
					errorCode = ISysParam.API_RESPONSE_ERRORCODE_FINDDATA_NULL;
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

		return ERROR;
	}

	/**
	 * 查询管理员列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "findAdmin", results = { @Result(name = "success", type = "json", params = {
			"root", "paginationResp", "excludeNullProperties", "true" }) })
	public String findAdmin() throws Exception {
		int errorCode = 0;
		try {
			boolean authMD5Key = true;

			StringBuilder authkeyStr = new StringBuilder();
			authkeyStr.append("c=").append(c);

			authMD5Key = this.checkAuthMD5Key(authkeyStr.toString());
			if (authMD5Key) {// 秘钥鉴权成功
				PaginationSupport<Admin> ps = userService.findAdmin(keyword,
						roleGroupId, roleId, page, pagesize);
				if (ps != null) {
					paginationResp = new PaginationResponse(ps, AdminView.class);
					return SUCCESS;
				} else {
					errorCode = ISysParam.API_RESPONSE_ERRORCODE_FINDDATA_NULL;
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
		return ERROR;
	}

	/**
	 * 获取管理员账号数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "fetchAdmin", results = { @Result(name = "success", type = "json", params = {
			"root", "getDataResp", "excludeNullProperties", "true" }) })
	public String fetchAdmin() throws Exception {
		int errorCode = 0;
		try {
			boolean authMD5Key = true;

			StringBuilder authkeyStr = new StringBuilder();
			authkeyStr.append("c=").append(c).append("&userId=").append(userId);

			authMD5Key = this.checkAuthMD5Key(authkeyStr.toString());

			if (authMD5Key) {// 秘钥鉴权成功
				Admin a = (Admin) userService.get(Admin.class, userId);

				if (a != null) {
					List<Integer> roleIds = userService.getAdminRoleIds(userId);
					if (CollectionUtils.isNotEmpty(roleIds)) {
						Role role = new Role();
						role.setRoleId(roleIds.get(0));
						a.getRoleList().add(role);
					}

					getDataResp = new GetDataResponse(a, AdminView.class);

					return SUCCESS;

				} else {
					errorCode = ISysParam.API_RESPONSE_ERRORCODE_FINDDATA_NULL;
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

		return ERROR;
	}

	/**
	 * 保存管理员数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "saveAdmin", results = { @Result(name = "success", type = "json", params = {
			"root", "baseResp" }) })
	public String saveAdmin() throws Exception {
		int errorCode = 0;
		try {
			boolean authMD5Key = true;

			StringBuilder authkeyStr = new StringBuilder();
			authkeyStr.append("c=").append(c).append("&serialId=")
					.append(serialId);

			authMD5Key = this.checkAuthMD5Key(authkeyStr.toString());

			if (authMD5Key) {// 秘钥鉴权成功
				admin.setUserId(userId);
				errorCode = userService.saveAdmin(admin, roleId);

				return SUCCESS;

			} else {
				errorCode = ISysParam.API_RESPONSE_ERRORCODE_AUTHMD5KEY_FAIL;
			}
		} catch (Exception e) {
			SimpleUtils.log(e, log);
			errorCode = ISysParam.API_RESPONSE_ERRORCODE_EXISTEXCEPTION;
			baseResp.setErrorMsg(e.toString());
		}
		baseResp.setErrorCode(errorCode);

		return ERROR;

	}

	/**
	 * 更新用户状态（admin为管理员，user为客户）
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "updateStatus", results = { @Result(name = "success", type = "json", params = {
			"root", "baseResp" }) })
	public String updateStatus() throws Exception {
		int errorCode = 0;
		try {
			boolean authMD5Key = true;

			StringBuilder authkeyStr = new StringBuilder();
			authkeyStr.append("c=").append(c).append("&serialId=")
					.append(serialId);

			authMD5Key = this.checkAuthMD5Key(authkeyStr.toString());

			if (authMD5Key) {// 秘钥鉴权成功
				Date date = new Date();
				Map<String, Object> setParams = new HashMap<String, Object>();
				setParams.put("updateTime", date);

				Class clazz = null;
				if (admin != null) {
					setParams.put("status", admin.getStatus());
					clazz = Admin.class;
				} else if (user != null) {
					setParams.put("freeze", user.isFreeze());
					clazz = User.class;
				}
				if (userId > 0 && clazz != null) {
					List whereParamVals = new ArrayList();
					whereParamVals.add(userId);
					int updateCount = userService.executeUpdate(clazz,
							setParams, " where userId=?", whereParamVals);
					if (updateCount > 0) {// 更新成功
						return SUCCESS;
					}

				} else {
					errorCode = ISysParam.API_RESPONSE_ERRORCODE_REQUESTPARAM_ERROE;
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

		return ERROR;

	}

	/**
	 * 更新用户密码
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "updatePassword", results = { @Result(name = "success", type = "json", params = {
			"root", "baseResp" }) })
	public String updatePassword() throws Exception {
		int errorCode = 0;
		try {
			boolean authMD5Key = true;

			StringBuilder authkeyStr = new StringBuilder();
			authkeyStr.append("c=").append(c).append("&serialId=")
					.append(serialId);

			authMD5Key = this.checkAuthMD5Key(authkeyStr.toString());

			if (authMD5Key) {// 秘钥鉴权成功
				Date date = new Date();
				Map<String, Object> setParams = new HashMap<String, Object>();
				setParams.put("updateTime", date);

				if (userId > 0 && StringUtils.isNotEmpty(password)
						&& StringUtils.isNotEmpty(newPassword)) {
					Account account = (Account) userService.get(Account.class,
							userId);

					if (account != null
							&& password.equals(account.getPassword())) {
						List whereParamVals = new ArrayList();
						whereParamVals.add(userId);

						setParams.put("password",
								ISysParam.encodePassword(newPassword));
						int updateCount = userService.executeUpdate(
								Account.class, setParams, " where userId=?",
								whereParamVals);
						if (updateCount > 0) {// 更新成功
							return SUCCESS;
						}
					}
				} else {
					errorCode = ISysParam.API_RESPONSE_ERRORCODE_REQUESTPARAM_ERROE;
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

		return ERROR;

	}

	/**
	 * 重置用户密码（短信下发给用户）
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "resetPassword", results = { @Result(name = "success", type = "json", params = {
			"root", "baseResp" }) })
	public String resetPassword() throws Exception {
		int errorCode = 0;
		try {
			boolean authMD5Key = true;

			StringBuilder authkeyStr = new StringBuilder();
			authkeyStr.append("c=").append(c).append("&serialId=")
					.append(serialId);

			authMD5Key = this.checkAuthMD5Key(authkeyStr.toString());

			if (authMD5Key) {// 秘钥鉴权成功
				Date date = new Date();
				Map<String, Object> setParams = new HashMap<String, Object>();
				setParams.put("updateTime", date);

				if (userId > 0) {
					newPassword = SimpleUtils.getRandomString(null, 6);
					Account account = (Account) userService.get(Account.class,
							userId);
					if (account != null) {
						List whereParamVals = new ArrayList();
						whereParamVals.add(userId);

						setParams.put("password",
								ISysParam.encodePassword(newPassword));
						int updateCount = userService.executeUpdate(
								Account.class, setParams, " where userId=?",
								whereParamVals);
						if (updateCount > 0) {// 更新成功
							smsService
									.sendSms(
											account.getMobile(),
											ISysParam.API_SMS_CONTENT_TEMPLET_RESETPASSWORD
													.replace("$(password)",
															newPassword));
							return SUCCESS;
						}
					} else {
						errorCode = ISysParam.API_RESPONSE_ERRORCODE_FINDDATA_NULL;
					}
				} else {
					errorCode = ISysParam.API_RESPONSE_ERRORCODE_REQUESTPARAM_ERROE;
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

		return ERROR;

	}
}
