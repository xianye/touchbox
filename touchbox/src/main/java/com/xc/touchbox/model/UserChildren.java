package com.xc.touchbox.model;

// Generated 2015-5-23 23:22:04 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import net.sf.json.JSONObject;

import com.xc.gospel.core.util.SimpleUtils;

/**
 * UserChildren generated by hbm2java
 */
public class UserChildren implements java.io.Serializable {

	private String childrenId;
	private long userId;
	private String childName;
	private Date childBirthday;
	private String schoolName;
	private Date createTime;

	public UserChildren() {
	}

	public String getChildrenId() {
		return childrenId;
	}

	public void setChildrenId(String childrenId) {
		this.childrenId = childrenId;
	}

	/**
	 * 生成小朋友编号MD5
	 */
	public void generateChildrenId(){
		JSONObject origin= new JSONObject();
		origin.put("userId", userId);
		origin.put("childName", childName);
		/*
		if(childBirthday!=null){
			origin.put("childBirthday", DateFormatUtils.ISO_DATETIME_FORMAT.format(childBirthday));
		}
		origin.put("schoolName", schoolName);*/
		SimpleUtils.MD5Encode(origin.toString());
	}
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	public Date getChildBirthday() {
		return childBirthday;
	}

	public void setChildBirthday(Date childBirthday) {
		this.childBirthday = childBirthday;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
