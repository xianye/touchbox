package com.xc.touchbox.model;

// Generated 2015-5-23 23:22:04 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * ThirdpartyOauth2User generated by hbm2java
 */
public class ThirdpartyOauth2User implements java.io.Serializable {

	private Long thirdpartyUserId;
	private ThirdpartyPlatform thirdpartyPlatform;
	private User user;
	private String accessToken;
	private Long expiresIn;
	private Boolean status;
	private Date authorizeTime;
	private Date cancelTime;
	private String actualUserId;
	private boolean loginEnabled;

	public ThirdpartyOauth2User() {
	}

	public ThirdpartyOauth2User(Date authorizeTime, String actualUserId,
			boolean loginEnabled) {
		this.authorizeTime = authorizeTime;
		this.actualUserId = actualUserId;
		this.loginEnabled = loginEnabled;
	}

	public ThirdpartyOauth2User(ThirdpartyPlatform thirdpartyPlatform,
			User user, String accessToken, Long expiresIn, Boolean status,
			Date authorizeTime, Date cancelTime, String actualUserId,
			boolean loginEnabled) {
		this.thirdpartyPlatform = thirdpartyPlatform;
		this.user = user;
		this.accessToken = accessToken;
		this.expiresIn = expiresIn;
		this.status = status;
		this.authorizeTime = authorizeTime;
		this.cancelTime = cancelTime;
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

	public String getAccessToken() {
		return this.accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Long getExpiresIn() {
		return this.expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Date getAuthorizeTime() {
		return this.authorizeTime;
	}

	public void setAuthorizeTime(Date authorizeTime) {
		this.authorizeTime = authorizeTime;
	}

	public Date getCancelTime() {
		return this.cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
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