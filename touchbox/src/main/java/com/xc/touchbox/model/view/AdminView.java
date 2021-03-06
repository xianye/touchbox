package com.xc.touchbox.model.view;

// Generated 2015-5-23 23:22:04 by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.BeanUtils;

import com.xc.touchbox.model.Account;
import com.xc.touchbox.model.Role;

/**
 * Admin generated by hbm2java
 */
public class AdminView implements java.io.Serializable {

	private long userId;
	private String username;
	private String email;
	private String mobile;
	private int type;
	private String name;
	private Byte sex;
	private Date birthday;
	private String identityCard;
	private String area;
	private String phone;
	private String address;
	private Float salesCut;
	private boolean greenway;
	private Long creator;
	private Long parentUserId;
	private Date loginTime;
	private String loginIp;
	private Date logoutTime;
	private boolean authorityAvailable;
	private int status;
	private Date createTime;

	private AccountView account;
	private List<RoleView> roles;

	public AdminView() {
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Byte getSex() {
		return this.sex;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	@JSON(format = "yyyy-MM-dd")
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getIdentityCard() {
		return this.identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Float getSalesCut() {
		return this.salesCut;
	}

	public void setSalesCut(Float salesCut) {
		this.salesCut = salesCut;
	}

	public boolean isGreenway() {
		return this.greenway;
	}

	public void setGreenway(boolean greenway) {
		this.greenway = greenway;
	}

	public Long getCreator() {
		return this.creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Long getParentUserId() {
		return this.parentUserId;
	}

	public void setParentUserId(Long parentUserId) {
		this.parentUserId = parentUserId;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginIp() {
		return this.loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getLogoutTime() {
		return this.logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public boolean isAuthorityAvailable() {
		return this.authorityAvailable;
	}

	public void setAuthorityAvailable(boolean authorityAvailable) {
		this.authorityAvailable = authorityAvailable;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public AccountView getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		try {

			if (account != null && account.getUserId() != null) {
				AccountView target = new AccountView();
				BeanUtils.copyProperties(account, target, new String[] {
						"admin", "createTime" });
				this.account = target;
			}
		} catch (Exception e) {
		}

	}

	public List<RoleView> getRoles() {
		return roles;
	}

	public void setRoleList(List<Role> roleList) {
		if (CollectionUtils.isNotEmpty(roleList)) {
			this.roles = new ArrayList<RoleView>();
			for (Role o : roleList) {
				RoleView target = new RoleView();
				BeanUtils.copyProperties(o, target);
				this.roles.add(target);
			}
		}
	}

}
