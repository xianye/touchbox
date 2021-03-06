package com.xc.touchbox.model;

// Generated 2015-5-23 23:22:04 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * ThirdpartyPlatform generated by hbm2java
 */
public class ThirdpartyPlatform implements java.io.Serializable {

	private Integer thirdpartyId;
	private String name;
	private String iconUrl;
	private String description;
	private Date createTime;
	private String appkey;
	private String secrect;
	private Set thirdpartyUsers = new HashSet(0);
	private Set thirdpartyOauth2Users = new HashSet(0);

	public ThirdpartyPlatform() {
	}

	public ThirdpartyPlatform(Date createTime) {
		this.createTime = createTime;
	}

	public ThirdpartyPlatform(String name, String iconUrl, String description,
			Date createTime, String appkey, String secrect,
			Set thirdpartyUsers, Set thirdpartyOauth2Users) {
		this.name = name;
		this.iconUrl = iconUrl;
		this.description = description;
		this.createTime = createTime;
		this.appkey = appkey;
		this.secrect = secrect;
		this.thirdpartyUsers = thirdpartyUsers;
		this.thirdpartyOauth2Users = thirdpartyOauth2Users;
	}

	public Integer getThirdpartyId() {
		return this.thirdpartyId;
	}

	public void setThirdpartyId(Integer thirdpartyId) {
		this.thirdpartyId = thirdpartyId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIconUrl() {
		return this.iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAppkey() {
		return this.appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getSecrect() {
		return this.secrect;
	}

	public void setSecrect(String secrect) {
		this.secrect = secrect;
	}

	public Set getThirdpartyUsers() {
		return this.thirdpartyUsers;
	}

	public void setThirdpartyUsers(Set thirdpartyUsers) {
		this.thirdpartyUsers = thirdpartyUsers;
	}

	public Set getThirdpartyOauth2Users() {
		return this.thirdpartyOauth2Users;
	}

	public void setThirdpartyOauth2Users(Set thirdpartyOauth2Users) {
		this.thirdpartyOauth2Users = thirdpartyOauth2Users;
	}

}
