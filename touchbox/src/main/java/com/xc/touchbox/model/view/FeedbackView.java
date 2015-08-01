package com.xc.touchbox.model.view;

// Generated 2015-5-23 23:22:04 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.BeanUtils;

import com.xc.touchbox.model.Admin;
import com.xc.touchbox.model.User;

public class FeedbackView implements java.io.Serializable {

	private Integer id;
	private AdminView admin;
	private String handlerName;
	private UserView user;
	private int type;
	private String mobile;
	private String name;
	private String content;
	private int status;
	private Date createTime;
	private Date updateTime;
	private Date handleTime;

	public FeedbackView() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public AdminView getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		if (admin != null) {
			this.admin = new AdminView();
			BeanUtils.copyProperties(admin, this.admin);
		}
	}

	public String getHandlerName() {
		return handlerName;
	}

	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	public UserView getUser() {
		return user;
	}

	public void setUser(User user) {
		if (user != null) {
			this.user = new UserView();
			BeanUtils.copyProperties(user, this.user);
		}		
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTypeName() {
		String typeName = "";
		//1：产品及物流\r\n            2：销售员\r\n            3：网站与 app使用\r\n            4：其他--退款、其他问题、合作
		switch(type){
		case 1:
			typeName = "产品及物流";
			break;
		case 2:
			typeName = "销售员";
			break;
		case 3:
			typeName = "网站与 app使用";
			break;
		case 4:
			typeName = "其他--退款、其他问题、合作";
			break;
		}
		return typeName;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@JSON (format="yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}
}
