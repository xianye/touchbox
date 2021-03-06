package com.xc.touchbox.model;

// Generated 2015-5-23 23:22:04 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.xc.touchbox.dao.CDAOTemplate;

/**
 * Role generated by hbm2java
 */
public class Role implements java.io.Serializable {

	private Integer roleId;
	private int type;
	private int groupId;
	private String name;
	private String memo;
	private int status = 1;
	private Date createTime;
	private Date updateTime;
	private Set roleResourceAuthorities = new HashSet(0);
	private Set adminRoleRelations = new HashSet(0);
	private Set admins = new HashSet(0);

	public Role() {
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Set getRoleResourceAuthorities() {
		return roleResourceAuthorities;
	}

	public void setRoleResourceAuthorities(Set roleResourceAuthorities) {
		this.roleResourceAuthorities = roleResourceAuthorities;
	}

	public Set getAdminRoleRelations() {
		return this.adminRoleRelations;
	}

	public void setAdminRoleRelations(Set adminRoleRelations) {
		this.adminRoleRelations = adminRoleRelations;
	}

	public Set getAdmins() {
		return admins;
	}

	public void setAdmins(Set admins) {
		this.admins = admins;
	}

	/**
	 * 是否超级管理员
	 * 
	 * @return
	 */
	public boolean isSuberAdmin(){
		return groupId==CDAOTemplate.CONSTANT_ROLE_GROUP_SUPERADMIN;
	}
}
