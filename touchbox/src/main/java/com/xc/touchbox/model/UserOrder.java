package com.xc.touchbox.model;

// Generated 2015-5-23 23:22:04 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * UserOrder generated by hbm2java
 */
public class UserOrder implements java.io.Serializable {

	private Long orderId;
	private User user;
	private long snapshotId;
	private int orderPeriod;
	private int number;
	private float fee;
	private float freightFee;
	private String salesCode;
	private float discountFee;
	private String childName;
	private Date childBirthday;
	private String schoolName;
	private String parentName;
	private String parentMobile;
	private String address;
	private String memo;
	private Date startTime;
	private Date endTime;
	private int giftClassroomPeriodNum;
	private int deliveredPeriodNum;
	private Date deliveredTime;
	private int status;
	private Date createTime;
	private Date updateTime;
	private Date payTime;
	private String deliveryTime;
	private Date refundTime;
	private Date closeTime;
	private Set refunds = new HashSet(0);

	private ProductSnapshot snapshot;// 非持久化

	public UserOrder() {
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getSnapshotId() {
		return snapshotId;
	}

	public void setSnapshotId(long snapshotId) {
		this.snapshotId = snapshotId;
	}

	public int getOrderPeriod() {
		return orderPeriod;
	}

	public void setOrderPeriod(int orderPeriod) {
		this.orderPeriod = orderPeriod;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public float getFee() {
		return fee;
	}

	public void setFee(float fee) {
		this.fee = fee;
	}

	public float getFreightFee() {
		return freightFee;
	}

	public void setFreightFee(float freightFee) {
		this.freightFee = freightFee;
	}

	public String getSalesCode() {
		return salesCode;
	}

	public void setSalesCode(String salesCode) {
		this.salesCode = salesCode;
	}

	public float getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(float discountFee) {
		this.discountFee = discountFee;
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

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParentMobile() {
		return parentMobile;
	}

	public void setParentMobile(String parentMobile) {
		this.parentMobile = parentMobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getGiftClassroomPeriodNum() {
		return giftClassroomPeriodNum;
	}

	public void setGiftClassroomPeriodNum(int giftClassroomPeriodNum) {
		this.giftClassroomPeriodNum = giftClassroomPeriodNum;
	}

	public int getDeliveredPeriodNum() {
		return deliveredPeriodNum;
	}

	public void setDeliveredPeriodNum(int deliveredPeriodNum) {
		this.deliveredPeriodNum = deliveredPeriodNum;
	}

	public Date getDeliveredTime() {
		return deliveredTime;
	}

	public void setDeliveredTime(Date deliveredTime) {
		this.deliveredTime = deliveredTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public Set getRefunds() {
		return refunds;
	}

	public void setRefunds(Set refunds) {
		this.refunds = refunds;
	}

	public ProductSnapshot getSnapshot() {
		return snapshot;
	}

	public void setSnapshot(ProductSnapshot snapshot) {
		this.snapshot = snapshot;
	}

}
