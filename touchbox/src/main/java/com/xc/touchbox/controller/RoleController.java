package com.xc.touchbox.controller;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ModelDriven;
import com.xc.touchbox.model.User;

@SuppressWarnings("serial")
@ParentPackage("main-default")
@Namespace("/manage/role")
@Results({ @Result(name = "success", type = "redirect", location = "/index") })
public class RoleController extends BaseAction implements ModelDriven<Object> {

	// 用户实体
	private User model = new User();

	/**
	 * 使用ModelDriven，重写getModel()方法
	 */
	@Override
	public Object getModel() {
		if (list != null) {
			return list;
		} else {
			return model;
		}
	}

	/**
	 * 功能：角色列表页 调用：GET /manage/role/list 返回：list.jsp
	 * 
	 * @return
	 */
	@Action(value="list", results = { @Result(name = "success",location = "list.jsp") })
	public String list() {
		this.fetchSessionAdmin();

		return SUCCESS;
	}
	
	/**
	 * 功能：角色编辑页 调用：GET /manage/role/edit 返回：edit.jsp
	 * 
	 * @return
	 */
	@Action(value="edit", results = { @Result(name = "success",location = "edit.jsp") })
	public String edit() {
		this.fetchSessionAdmin();
			
		System.out.println(dataId);
		
		return SUCCESS;
	}

	
}
