package com.xc.touchbox.model;

// Generated 2015-5-23 23:22:04 by Hibernate Tools 3.4.0.CR1

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Resource generated by hbm2java
 */
public class Resource implements java.io.Serializable {

	private String resourceId;
	private int type;
	private String name;
	private int level;
	private String parentId;
	private String link;
	private String feature;
	private Short sequence;
	private int status;
	private Date createTime;
	private Date updateTime;
	private Set roleResourceAuthorities = new HashSet(0);

	private List<Resource> children = new ArrayList<Resource>();

	public Resource() {
	}

	public String getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
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

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getFeature() {
		return this.feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public Short getSequence() {
		return this.sequence;
	}

	public void setSequence(Short sequence) {
		this.sequence = sequence;
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

	public List<Resource> getChildren() {
		return children;
	}

	public void setChildren(List<Resource> children) {
		this.children = children;
	}

}
