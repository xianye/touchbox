package com.xc.touchbox.controller;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.xc.touchbox.model.ISysParam;

@SuppressWarnings("serial")
@ParentPackage("main-default")
@Namespace("/manage/feedback")
@Results({ @Result(name = "success", type = "redirect", location = "/index") })
public class FeedbackController extends BaseAction {

	private int entityType = 1;// 1-问题提报，2-投诉建议，默认为问题提报

	public int getEntityType() {
		return entityType;
	}

	public void setEntityType(int entityType) {
		this.entityType = entityType;
	}

	public String getModelName() {
		return ISysParam.getFeedbackName(entityType);
	}

	/**
	 * 功能：反馈类列表页 调用：GET /manage/feedback/list 返回：list.jsp
	 * 
	 * @return
	 */
	@Action(value = "list", results = { @Result(name = "success", location = "list.jsp") })
	public String list() {
		this.fetchSessionAdmin();

		return SUCCESS;
	}

	/**
	 * 功能：问题提报列表页 调用：GET /manage/feedback/feedback 返回：list.jsp
	 * 
	 * @return
	 */
	@Action(value = "feedback", results = { @Result(name = "success", location = "list.jsp") })
	public String feedback() {
		entityType = 1;// 1-问题提报

		return list();
	}

	/**
	 * 功能：投诉建议列表页 调用：GET /manage/feedback/complaint 返回：list.jsp
	 * 
	 * @return
	 */
	@Action(value = "complaint", results = { @Result(name = "success", location = "list.jsp") })
	public String complaint() {
		entityType = 2;// 2-投诉建议

		return list();
	}

	/**
	 * 功能：反馈类编辑页 调用：GET /manage/feedback/edit 返回：edit.jsp
	 * 
	 * @return
	 */
	@Action(value = "edit", results = { @Result(name = "success", location = "edit.jsp") })
	public String edit() {
		this.fetchSessionAdmin();

		return SUCCESS;
	}
	
	/**
	 * 功能：反馈类回复页 调用：GET /manage/feedback/reply 返回：edit.jsp
	 * 
	 * @return
	 */
	@Action(value = "reply", results = { @Result(name = "success", location = "reply.jsp") })
	public String reply() {
		this.fetchSessionAdmin();

		return SUCCESS;
	}
}
