package com.xc.touchbox.model;

// Generated 2015-5-23 23:22:04 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

public class FeedbackReplyCommon implements java.io.Serializable {

	private Integer replayId;
	private int feedbackId;
	private long replierId;
	private String replierName;
	private String content;
	private int status = 1;
	private Date createTime;
	private Date updateTime;

	public FeedbackReplyCommon() {
	}

	public Integer getReplayId() {
		return this.replayId;
	}

	public void setReplayId(Integer replayId) {
		this.replayId = replayId;
	}

	public int getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(int feedbackId) {
		this.feedbackId = feedbackId;
	}

	public long getReplierId() {
		return replierId;
	}

	public void setReplierId(long replierId) {
		this.replierId = replierId;
	}

	public String getReplierName() {
		return this.replierName;
	}

	public void setReplierName(String replierName) {
		this.replierName = replierName;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
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

}
