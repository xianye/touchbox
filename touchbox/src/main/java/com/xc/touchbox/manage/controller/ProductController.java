package com.xc.touchbox.manage.controller;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.xc.touchbox.controller.BaseAction;

@SuppressWarnings("serial")
@ParentPackage("main-default")
@Namespace("/manage/product")
@Results({ @Result(name = "success", type = "redirect", location = "/index") })
public class ProductController extends BaseAction {

	/**
	 * 功能：列表页 调用：GET /manage/product/list 返回：list.jsp
	 * 
	 * @return
	 */
	@Action(value = "list", results = { @Result(name = "success", location = "list.jsp") })
	public String list() {
		this.fetchSessionAdmin();


		return SUCCESS;
	}

	/**
	 * 功能：编辑页 调用：GET /manage/product/edit 返回：edit.jsp
	 * 
	 * @return
	 */
	@Action(value = "edit", results = { @Result(name = "success", location = "edit.jsp") })
	public String edit() {
		this.fetchSessionAdmin();

		return SUCCESS;
	}

}
