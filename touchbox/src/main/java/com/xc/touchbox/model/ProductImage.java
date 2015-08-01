package com.xc.touchbox.model;

// Generated 2015-5-23 23:22:04 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * ProductImage generated by hbm2java
 */
public class ProductImage implements java.io.Serializable {

	private Integer imageId;
	private Product product;
	private String name;
	private String thumbnailUrl;
	private String imageUrl;
	private short sequence;
	private byte status;
	private Date createTime;
	private Date updateTime;

	public ProductImage() {
	}

	public ProductImage(short sequence, byte status) {
		this.sequence = sequence;
		this.status = status;
	}

	public ProductImage(Product product, String name, String thumbnailUrl,
			String imageUrl, short sequence, byte status, Date createTime,
			Date updateTime) {
		this.product = product;
		this.name = name;
		this.thumbnailUrl = thumbnailUrl;
		this.imageUrl = imageUrl;
		this.sequence = sequence;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public Integer getImageId() {
		return this.imageId;
	}

	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getThumbnailUrl() {
		return this.thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public short getSequence() {
		return this.sequence;
	}

	public void setSequence(short sequence) {
		this.sequence = sequence;
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

}
