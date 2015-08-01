package com.xc.touchbox.manage.controller;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.xc.touchbox.controller.BaseAction;

@SuppressWarnings("serial")
@ParentPackage("main-default")
@Namespace("/manage/user")
@Results({ @Result(name = "success", type = "redirect", location = "/index") })
public class UserController extends BaseAction {

	/**
	 * 功能：角色列表页 调用：GET /manage/user/list 返回：list.jsp
	 * 
	 * @return
	 */
	@Action(value="list", results = { @Result(name = "success",location = "list.jsp") })
	public String list() {
		this.fetchSessionAdmin();

		return SUCCESS;
	}
	
	/**
	 * 功能：角色编辑页 调用：GET /manage/user/edit 返回：edit.jsp
	 * 
	 * @return
	 */
	@Action(value="edit", results = { @Result(name = "success",location = "edit.jsp") })
	public String edit() {
		this.fetchSessionAdmin();
			
		return SUCCESS;
	}
	
	/**
	 * 功能：浏览页 调用：GET /manage/user/view 返回：view.jsp
	 * 
	 * @return
	 */
	@Action(value="view", results = { @Result(name = "success",location = "view.jsp") })
	public String view() {
		this.fetchSessionAdmin();
			
		return SUCCESS;
	}

}
