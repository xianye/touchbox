package com.xc.touchbox.model;

// Generated 2015-5-23 23:22:04 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * SalaryAccount generated by hbm2java
 */
public class SalaryAccount implements java.io.Serializable {

	private Integer salaryAccountId;
	private Admin admin;
	private Byte type;
	private String content;
	private byte status;
	private Date createTime;
	private Date updateTime;
	private Date startTime;
	private Date endTime;
	private Date chargeTime;
	private Date arrivalTime;

	public SalaryAccount() {
	}

	public SalaryAccount(byte status) {
		this.status = status;
	}

	public SalaryAccount(Admin admin, Byte type, String content, byte status,
			Date createTime, Date updateTime, Date startTime, Date endTime,
			Date chargeTime, Date arrivalTime) {
		this.admin = admin;
		this.type = type;
		this.content = content;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.startTime = startTime;
		this.endTime = endTime;
		this.chargeTime = chargeTime;
		this.arrivalTime = arrivalTime;
	}

	public Integer getSalaryAccountId() {
		return this.salaryAccountId;
	}

	public void setSalaryAccountId(Integer salaryAccountId) {
		this.salaryAccountId = salaryAccountId;
	}

	public Admin getAdmin() {
		return this.admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Byte getType() {
		return this.type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
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

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getChargeTime() {
		return this.chargeTime;
	}

	public void setChargeTime(Date chargeTime) {
		this.chargeTime = chargeTime;
	}

	public Date getArrivalTime() {
		return this.arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

}
