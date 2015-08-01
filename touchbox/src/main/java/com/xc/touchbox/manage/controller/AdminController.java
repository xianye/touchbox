package com.xc.touchbox.manage.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.xc.touchbox.controller.BaseAction;
import com.xc.touchbox.service.UserService;

@SuppressWarnings("serial")
@ParentPackage("main-default")
@Namespace("/manage/admin")
@Results({ @Result(name = "success", type = "redirect", location = "/index") })
public class AdminController extends BaseAction {

	// 用户Service
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	private String roleGroupId = "0,3";// 不限，客服

	public String getRoleGroupId() {
		return roleGroupId;
	}

	public void setRoleGroupId(String roleGroupId) {
		this.roleGroupId = roleGroupId;
	}

	public String getModelName() {
		if (StringUtils.isNotEmpty(roleGroupId)) {
			if ("2".equals(roleGroupId)) {
				return "销售员";
			}
		}
		return "用户";
	}

	/**
	 * 功能：用户列表页 调用：GET /manage/admin/list 返回：list.jsp
	 * 
	 * @return
	 */
	@Action(value = "list", results = { @Result(name = "success", location = "list.jsp") })
	public String list() {
		this.fetchSessionAdmin();

		if (roleGroupId == null) {
			roleGroupId = "2";// 销售员
		}
		
		request.setAttribute("isSeller", roleGroupId.equals("2"));

		return SUCCESS;
	}

	/**
	 * 功能：销售员列表页 调用：GET /manage/admin/sellers 返回：list.jsp
	 * 
	 * @return
	 */
	@Action(value = "sellers", results = { @Result(name = "success", location = "list.jsp") })
	public String sellers() {
		roleGroupId = "2";// 销售员

		return list();
	}

	/**
	 * 功能：用户编辑页 调用：GET /manage/admin/edit 返回：edit.jsp
	 * 
	 * @return
	 */
	@Action(value = "edit", results = { @Result(name = "success", location = "edit.jsp") })
	public String edit() {
		this.fetchSessionAdmin();

		return SUCCESS;
	}

	
	/**
	 * 功能：个人资料页 调用：GET /manage/admin/personalInfo 返回：edit.jsp
	 * 
	 * @return
	 */
	@Action(value = "personalInfo", results = { @Result(name = "success", location = "personalInfo.jsp") })
	public String personalInfo() {
		this.fetchSessionAdmin();

		return SUCCESS;
	}
	
	/**
	 * 功能：修改密码页 调用：GET /manage/admin/modifyPass 返回：edit.jsp
	 * 
	 * @return
	 */
	@Action(value = "modifyPass", results = { @Result(name = "success", location = "modifyPass.jsp") })
	public String modifyPass() {
		this.fetchSessionAdmin();

		return SUCCESS;
	}
}
