package com.xc.touchbox.model;

// Generated 2015-5-23 23:22:04 by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User generated by hbm2java
 */
public class User extends UserCommon implements java.io.Serializable {


	private Date logoutTime;
	private Long longitude;
	private Long latitude;
	private Date placeTime;
	private String portraitUrl;
	private int portraitKey;
	private int status;
	private String signature;
	private Integer integral;
	private boolean freeze;
	private String freezeDetail;
	private int loginFrequency;
	private int source;
	private int vipLevel;
	private Set refunds = new HashSet(0);
	private Set serials = new HashSet(0);
	private Set thirdpartyUsers = new HashSet(0);
	private Set userLessonPlans = new HashSet(0);
	private Set thirdpartyOauth2Users = new HashSet(0);
	private Set salesCodes = new HashSet(0);
	private Set userCollections = new HashSet(0);
	private Set userOrders = new HashSet(0);
	private Set complaints = new HashSet(0);
	private Set userMessages = new HashSet(0);

	private List<UserChildren> childrens = new ArrayList<UserChildren>();// 非持久化
	
	public User() {
	}

	public Date getLogoutTime() {
		return this.logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public Long getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Long longitude) {
		this.longitude = longitude;
	}

	public Long getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Long latitude) {
		this.latitude = latitude;
	}

	public Date getPlaceTime() {
		return this.placeTime;
	}

	public void setPlaceTime(Date placeTime) {
		this.placeTime = placeTime;
	}

	public String getPortraitUrl() {
		return this.portraitUrl;
	}

	public void setPortraitUrl(String portraitUrl) {
		this.portraitUrl = portraitUrl;
	}

	public int getPortraitKey() {
		return this.portraitKey;
	}

	public void setPortraitKey(int portraitKey) {
		this.portraitKey = portraitKey;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSignature() {
		return this.signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Integer getIntegral() {
		return this.integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public boolean isFreeze() {
		return this.freeze;
	}

	public void setFreeze(boolean freeze) {
		this.freeze = freeze;
	}

	public String getFreezeDetail() {
		return this.freezeDetail;
	}

	public void setFreezeDetail(String freezeDetail) {
		this.freezeDetail = freezeDetail;
	}

	public int getLoginFrequency() {
		return this.loginFrequency;
	}

	public void setLoginFrequency(int loginFrequency) {
		this.loginFrequency = loginFrequency;
	}

	public int getSource() {
		return this.source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public int getVipLevel() {
		return this.vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	public Set getRefunds() {
		return this.refunds;
	}

	public void setRefunds(Set refunds) {
		this.refunds = refunds;
	}

	public Set getSerials() {
		return this.serials;
	}

	public void setSerials(Set serials) {
		this.serials = serials;
	}

	public Set getThirdpartyUsers() {
		return this.thirdpartyUsers;
	}

	public void setThirdpartyUsers(Set thirdpartyUsers) {
		this.thirdpartyUsers = thirdpartyUsers;
	}

	public Set getUserLessonPlans() {
		return this.userLessonPlans;
	}

	public void setUserLessonPlans(Set userLessonPlans) {
		this.userLessonPlans = userLessonPlans;
	}

	public Set getThirdpartyOauth2Users() {
		return this.thirdpartyOauth2Users;
	}

	public void setThirdpartyOauth2Users(Set thirdpartyOauth2Users) {
		this.thirdpartyOauth2Users = thirdpartyOauth2Users;
	}

	public Set getSalesCodes() {
		return this.salesCodes;
	}

	public void setSalesCodes(Set salesCodes) {
		this.salesCodes = salesCodes;
	}

	public Set getUserCollections() {
		return this.userCollections;
	}

	public void setUserCollections(Set userCollections) {
		this.userCollections = userCollections;
	}

	public Set getUserOrders() {
		return this.userOrders;
	}

	public void setUserOrders(Set userOrders) {
		this.userOrders = userOrders;
	}

	public Set getComplaints() {
		return this.complaints;
	}

	public void setComplaints(Set complaints) {
		this.complaints = complaints;
	}

	public Set getUserMessages() {
		return this.userMessages;
	}

	public void setUserMessages(Set userMessages) {
		this.userMessages = userMessages;
	}

	public List<UserChildren> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<UserChildren> childrens) {
		this.childrens = childrens;
	}

}
