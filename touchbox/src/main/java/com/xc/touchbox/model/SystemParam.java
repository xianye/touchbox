package com.xc.touchbox.model;

import java.util.Date;

/**
 * SystemParam generated by hbm2java
 */
public class SystemParam implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3888484900284306073L;

	private String id;
	private String value;
	private String description;
	private Date createTime;
	private Date updateTime;

	public SystemParam() {
	}

	public SystemParam(String id) {
		this.id = id;
	}

	public SystemParam(String id, String value, String description) {
		this.id = id;
		this.value = value;
		this.description = description;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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

}
