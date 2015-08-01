package com.xc.touchbox.model.view;

// Generated 2015-5-23 23:22:04 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * ProductSnapshot generated by hbm2java
 */
public class ProductSnapshotView implements java.io.Serializable {

	private Long snapshotId;
	private long productId;
	private int productCatId;
	private String period;
	private String name;
	private String description;
	private float originalPrice;
	private float price;
	private String titleImageUrl;
	private String imageUrl;
	private String deliveryWarehouse;
	private Date startTime;
	private boolean discountExisted = true;
	private int giftClassroomPeriodNum;
	private int status;
	private Date createTime;
	private Date updateTime;

	public ProductSnapshotView() {
	}

	public Long getSnapshotId() {
		return snapshotId;
	}

	public void setSnapshotId(Long snapshotId) {
		this.snapshotId = snapshotId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public int getProductCatId() {
		return productCatId;
	}

	public void setProductCatId(int productCatId) {
		this.productCatId = productCatId;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(float originalPrice) {
		this.originalPrice = originalPrice;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getTitleImageUrl() {
		return titleImageUrl;
	}

	public void setTitleImageUrl(String titleImageUrl) {
		this.titleImageUrl = titleImageUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getDeliveryWarehouse() {
		return deliveryWarehouse;
	}

	public void setDeliveryWarehouse(String deliveryWarehouse) {
		this.deliveryWarehouse = deliveryWarehouse;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public boolean isDiscountExisted() {
		return discountExisted;
	}

	public void setDiscountExisted(boolean discountExisted) {
		this.discountExisted = discountExisted;
	}

	public int getGiftClassroomPeriodNum() {
		return giftClassroomPeriodNum;
	}

	public void setGiftClassroomPeriodNum(int giftClassroomPeriodNum) {
		this.giftClassroomPeriodNum = giftClassroomPeriodNum;
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

}
