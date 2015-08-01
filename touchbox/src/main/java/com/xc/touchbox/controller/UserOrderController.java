package com.xc.touchbox.controller;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@SuppressWarnings("serial")
@ParentPackage("main-default")
@Namespace("/manage/order")
@Results({ @Result(name = "success", type = "redirect", location = "/index") })
public class UserOrderController extends BaseAction {

	/**
	 * 功能：列表页 调用：GET /manage/order/list 返回：list.jsp
	 * 
	 * @return
	 */
	@Action(value = "list", results = { @Result(name = "success", location = "list.jsp") })
	public String list() {
		this.fetchSessionAdmin();


		return SUCCESS;
	}

	/**
	 * 功能：编辑页 调用：GET /manage/order/view 返回：view.jsp
	 * 
	 * @return
	 */
	@Action(value = "view", results = { @Result(name = "success", location = "view.jsp") })
	public String edit() {
		this.fetchSessionAdmin();

		return SUCCESS;
	}

	/**
	 * 功能：列表页 调用：GET /manage/order/freights 返回：list.jsp
	 * 
	 * @return
	 */
	@Action(value = "freights", results = { @Result(name = "success", location = "freights.jsp") })
	public String freights() {
		this.fetchSessionAdmin();

		return SUCCESS;
	}
}
