package com.xc.touchbox.model.view;

// Generated 2015-5-23 23:22:04 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.BeanUtils;

import com.xc.touchbox.model.Account;

public class FeedbackReplyView implements java.io.Serializable {

	private Integer replayId;
	private int feedbackId;
	private long replierId;
	private String replierName;
	private String content;
	private int status;
	private Date createTime;
	private Date updateTime;

	public FeedbackReplyView() {
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

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
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
