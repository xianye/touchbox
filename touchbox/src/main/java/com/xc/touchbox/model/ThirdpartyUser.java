package com.xc.touchbox.model;

// Generated 2015-5-23 23:22:04 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * ThirdpartyUser generated by hbm2java
 */
public class ThirdpartyUser implements java.io.Serializable {

	private Long thirdpartyUserId;
	private ThirdpartyPlatform thirdpartyPlatform;
	private User user;
	private String account;
	private String password;
	private Byte whetherAuthorised;
	private Date createTime;
	private String appkey;
	private String secrect;
	private String actualUserId;
	private boolean loginEnabled;

	public ThirdpartyUser() {
	}

	public ThirdpartyUser(Date createTime, String actualUserId,
			boolean loginEnabled) {
		this.createTime = createTime;
		this.actualUserId = actualUserId;
		this.loginEnabled = loginEnabled;
	}

	public ThirdpartyUser(ThirdpartyPlatform thirdpartyPlatform, User user,
			String account, String password, Byte whetherAuthorised,
			Date createTime, String appkey, String secrect,
			String actualUserId, boolean loginEnabled) {
		this.thirdpartyPlatform = thirdpartyPlatform;
		this.user = user;
		this.account = account;
		this.password = password;
		this.whetherAuthorised = whetherAuthorised;
		this.createTime = createTime;
		this.appkey = appkey;
		this.secrect = secrect;
		this.actualUserId = actualUserId;
		this.loginEnabled = loginEnabled;
	}

	public Long getThirdpartyUserId() {
		return this.thirdpartyUserId;
	}

	public void setThirdpartyUserId(Long thirdpartyUserId) {
		this.thirdpartyUserId = thirdpartyUserId;
	}

	public ThirdpartyPlatform getThirdpartyPlatform() {
		return this.thirdpartyPlatform;
	}

	public void setThirdpartyPlatform(ThirdpartyPlatform thirdpartyPlatform) {
		this.thirdpartyPlatform = thirdpartyPlatform;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Byte getWhetherAuthorised() {
		return this.whetherAuthorised;
	}

	public void setWhetherAuthorised(Byte whetherAuthorised) {
		this.whetherAuthorised = whetherAuthorised;
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

	public String getActualUserId() {
		return this.actualUserId;
	}

	public void setActualUserId(String actualUserId) {
		this.actualUserId = actualUserId;
	}

	public boolean isLoginEnabled() {
		return this.loginEnabled;
	}

	public void setLoginEnabled(boolean loginEnabled) {
		this.loginEnabled = loginEnabled;
	}

}