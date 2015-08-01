package com.xc.touchbox.controller;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.xc.touchbox.model.ISysParam;

@SuppressWarnings("serial")
@ParentPackage("main-default")
@Namespace("/manage/information")
@Results({ @Result(name = "success", type = "redirect", location = "/index") })
public class InformationController extends BaseAction {

	private int type;// 资讯类型:1-网站公告，2-相关新闻，3-销售人员相关文档与FAQ，4-常见问题

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public String getModelName() {
		return ISysParam.getInformationTypeName(type);
	}

	/**
	 * 功能：用户列表页 调用：GET /manage/information/list 返回：list.jsp
	 * 
	 * @return
	 */
	@Action(value = "list", results = { @Result(name = "success", location = "list.jsp") })
	public String list() {
		this.fetchSessionAdmin();


		return SUCCESS;
	}

	/**
	 * 功能：相关新闻列表页 调用：GET /manage/information/aboutNewsList 返回：list.jsp
	 * 
	 * @return
	 */
	@Action(value = "aboutNews", results = { @Result(name = "success", location = "list.jsp") })
	public String aboutNews() {
		type = 2;//2-相关新闻，3-销售人员相关文档与FAQ，4-常见问题

		return list();
	}
	
	/**
	 * 功能：销售工具列表页 调用：GET /manage/information/salesTool 返回：list.jsp
	 * 
	 * @return
	 */
	@Action(value = "salesTool", results = { @Result(name = "success", location = "list.jsp") })
	public String salesTool() {
		type = 3;//2-相关新闻，3-销售人员相关文档与FAQ，4-常见问题

		return list();
	}
	
	/**
	 * 功能：常见问题列表页 调用：GET /manage/information/faq 返回：list.jsp
	 * 
	 * @return
	 */
	@Action(value = "faq", results = { @Result(name = "success", location = "list.jsp") })
	public String faq() {
		type = 4;//2-相关新闻，3-销售人员相关文档与FAQ，4-常见问题

		return list();
	}

	/**
	 * 功能：用户编辑页 调用：GET /manage/information/edit 返回：edit.jsp
	 * 
	 * @return
	 */
	@Action(value = "edit", results = { @Result(name = "success", location = "edit.jsp") })
	public String edit() {
		this.fetchSessionAdmin();

		return SUCCESS;
	}
}
