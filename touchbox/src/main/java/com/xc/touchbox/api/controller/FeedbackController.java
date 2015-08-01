package com.xc.touchbox.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.xc.gospel.core.util.SimpleUtils;
import com.xc.gospel.core.vo.PaginationSupport;
import com.xc.touchbox.controller.BaseAction;
import com.xc.touchbox.model.Admin;
import com.xc.touchbox.model.Complaint;
import com.xc.touchbox.model.ComplaintReply;
import com.xc.touchbox.model.Feedback;
import com.xc.touchbox.model.FeedbackCommon;
import com.xc.touchbox.model.FeedbackReply;
import com.xc.touchbox.model.FeedbackReplyCommon;
import com.xc.touchbox.model.ISysParam;
import com.xc.touchbox.model.User;
import com.xc.touchbox.model.view.FeedbackReplyView;
import com.xc.touchbox.model.view.FeedbackView;
import com.xc.touchbox.model.view.GetDataResponse;
import com.xc.touchbox.model.view.PaginationResponse;
import com.xc.touchbox.service.FeedbackService;

@SuppressWarnings("serial")
@ParentPackage("json-default")
@Namespace("/api/feedback")
@Results({ @Result(name = "error", type = "json", params = { "root", "baseResp" }) })
public class FeedbackController extends BaseAction {

	private static final Logger log = Logger
			.getLogger(FeedbackController.class);

	@Autowired
	@Qualifier("feedbackService")
	private FeedbackService feedbackService;

	private FeedbackCommon instance;// 输入FeedbackCommon对象数据
	private FeedbackReplyCommon reply;// 输入FeedbackReplyCommon对象数据
	private int entityType = 1;// 1-问题提报，2-投诉建议，默认为问题提报
	private Class clazz = Feedback.class;// 操作实体类
	private int type;// 类型：1-网站公告，2-相关新闻，3-销售人员相关文档与FAQ，4-常见问题
	private Integer status;// 状态：-1表示全部，0-待回复，1-已回复

	/**
	 * 查询分页列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "find", results = { @Result(name = "success", type = "json", params = {
			"root", "paginationResp", "excludeNullProperties", "true" }) })
	public String find() throws Exception {
		int errorCode = 0;
		try {
			boolean authMD5Key = true;

			StringBuilder authkeyStr = new StringBuilder();
			authkeyStr.append("c=").append(c);

			authMD5Key = this.checkAuthMD5Key(authkeyStr.toString());
			if (authMD5Key) {// 秘钥鉴权成功

				PaginationSupport<FeedbackCommon> ps = feedbackService
						.findFeedback(clazz, keyword, type, status, page,
								pagesize);

				if (ps != null) {
					paginationResp = new PaginationResponse(ps,
							FeedbackView.class);
					return SUCCESS;
				} else {
					errorCode = ISysParam.API_RESPONSE_ERRORCODE_FINDDATA_NULL;
				}
			} else {
				errorCode = ISysParam.API_RESPONSE_ERRORCODE_AUTHMD5KEY_FAIL;
			}
		} catch (Exception e) {
			SimpleUtils.log(e, log);
			errorCode = ISysParam.API_RESPONSE_ERRORCODE_EXISTEXCEPTION;
			baseResp.setErrorMsg(e.toString());
		}
		baseResp.setErrorCode(errorCode);
		return ERROR;
	}

	/**
	 * 获取指定ID的数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "fetchItem", results = { @Result(name = "success", type = "json", params = {
			"root", "getDataResp", "excludeNullProperties", "true" }) })
	public String fetchItem() throws Exception {
		try {
			boolean authMD5Key = true;

			StringBuilder authkeyStr = new StringBuilder();
			authkeyStr.append("c=").append(c).append("&dataId=").append(dataId);

			authMD5Key = this.checkAuthMD5Key(authkeyStr.toString());

			if (authMD5Key) {// 秘钥鉴权成功
				FeedbackCommon a = (FeedbackCommon) feedbackService.get(clazz,
						NumberUtils.toInt(dataId));

				if (a != null) {
					getDataResp = new GetDataResponse(a, FeedbackView.class);

					return SUCCESS;
				} else {
					baseResp.setErrorCode(ISysParam.API_RESPONSE_ERRORCODE_FINDDATA_NULL);
				}
			} else {
				baseResp.setErrorCode(ISysParam.API_RESPONSE_ERRORCODE_AUTHMD5KEY_FAIL);
			}
		} catch (Exception e) {
			SimpleUtils.log(e, log);
		}
		return ERROR;
	}

	/**
	 * 保存数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "save", results = { @Result(name = "success", type = "json", params = {
			"root", "baseResp" }) })
	public String save() throws Exception {
		int errorCode = 0;
		try {
			fetchSessionAdmin();
			boolean authMD5Key = true;

			StringBuilder authkeyStr = new StringBuilder();
			authkeyStr.append("c=").append(c).append("&serialId=")
					.append(serialId);

			authMD5Key = this.checkAuthMD5Key(authkeyStr.toString());

			if (authMD5Key) {// 秘钥鉴权成功
				Date date = new Date();
				User user = sessionUser;
				if (user == null) {
					if (userId > 0) {
						user = (User) userService.get(User.class, userId);
					} else {
						if (StringUtils.isNotEmpty(instance.getMobile())) {
							user = userService.getUserByMobile(instance
									.getMobile());
						}
					}
				}

				instance.setUser(user);
				feedbackService.saveFeedback(clazz, instance);

				return SUCCESS;

			} else {
				errorCode = ISysParam.API_RESPONSE_ERRORCODE_AUTHMD5KEY_FAIL;
			}
		} catch (Exception e) {
			SimpleUtils.log(e, log);
			errorCode = ISysParam.API_RESPONSE_ERRORCODE_EXISTEXCEPTION;
			baseResp.setErrorMsg(e.toString());
		}
		baseResp.setErrorCode(errorCode);

		return ERROR;
	}

	/**
	 * 更新状态
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "updateStatus", results = { @Result(name = "success", type = "json", params = {
			"root", "baseResp" }) })
	public String updateStatus() throws Exception {
		int errorCode = 0;
		try {
			boolean authMD5Key = true;

			StringBuilder authkeyStr = new StringBuilder();
			authkeyStr.append("c=").append(c).append("&serialId=")
					.append(serialId);

			authMD5Key = this.checkAuthMD5Key(authkeyStr.toString());

			if (authMD5Key) {// 秘钥鉴权成功
				Date date = new Date();

				if (instance.getId() != null) {

					List whereParamVals = new ArrayList();
					whereParamVals.add(instance.getId());
					Map<String, Object> setParams = new HashMap<String, Object>();
					setParams.put("status", instance.getStatus());
					setParams.put("updateTime", date);
					int updateCount = feedbackService.executeUpdate(clazz,
							setParams, " where id=?", whereParamVals);
					if (updateCount > 0) {// 更新成功
						return SUCCESS;
					}

				} else {
					errorCode = ISysParam.API_RESPONSE_ERRORCODE_REQUESTPARAM_ERROE;
				}
			} else {
				errorCode = ISysParam.API_RESPONSE_ERRORCODE_AUTHMD5KEY_FAIL;
			}
		} catch (Exception e) {
			SimpleUtils.log(e, log);
			errorCode = ISysParam.API_RESPONSE_ERRORCODE_EXISTEXCEPTION;
			baseResp.setErrorMsg(e.toString());
		}
		baseResp.setErrorCode(errorCode);

		return ERROR;

	}

	/**
	 * 查询分页列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "replyFind", results = { @Result(name = "success", type = "json", params = {
			"root", "paginationResp", "excludeNullProperties", "true" }) })
	public String replyFind() throws Exception {
		int errorCode = 0;
		try {
			boolean authMD5Key = true;

			StringBuilder authkeyStr = new StringBuilder();
			authkeyStr.append("c=").append(c);

			authMD5Key = this.checkAuthMD5Key(authkeyStr.toString());
			if (authMD5Key) {// 秘钥鉴权成功
				if (entityType == 2) {// 投诉建议回复
					clazz = ComplaintReply.class;
				}else{
					clazz = FeedbackReply.class;
				}
				PaginationSupport<FeedbackReplyCommon> ps = feedbackService
						.findReply(clazz, NumberUtils.toInt(dataId), page, pagesize);

				if (ps != null) {
					paginationResp = new PaginationResponse(ps,
							FeedbackReplyView.class);
					return SUCCESS;
				} else {
					errorCode = ISysParam.API_RESPONSE_ERRORCODE_FINDDATA_NULL;
				}
			} else {
				errorCode = ISysParam.API_RESPONSE_ERRORCODE_AUTHMD5KEY_FAIL;
			}
		} catch (Exception e) {
			SimpleUtils.log(e, log);
			errorCode = ISysParam.API_RESPONSE_ERRORCODE_EXISTEXCEPTION;
			baseResp.setErrorMsg(e.toString());
		}
		baseResp.setErrorCode(errorCode);
		return ERROR;
	}

	/**
	 * 保存回复数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value = "replySave", results = { @Result(name = "success", type = "json", params = {
			"root", "baseResp" }) })
	public String replySave() throws Exception {
		int errorCode = 0;
		try {
			fetchSessionAdmin();
			boolean authMD5Key = true;

			StringBuilder authkeyStr = new StringBuilder();
			authkeyStr.append("c=").append(c).append("&serialId=")
					.append(serialId);

			authMD5Key = this.checkAuthMD5Key(authkeyStr.toString());

			if (authMD5Key) {// 秘钥鉴权成功
				Date date = new Date();
				Admin admin = sessionAdmin;
				if (admin == null) {
					admin = (Admin) userService.get(Admin.class, userId);
				}

				reply.setReplierId(admin.getUserId());
				reply.setReplierName(admin.getName());
				feedbackService.saveReply(entityType, reply);

				return SUCCESS;

			} else {
				errorCode = ISysParam.API_RESPONSE_ERRORCODE_AUTHMD5KEY_FAIL;
			}
		} catch (Exception e) {
			SimpleUtils.log(e, log);
			errorCode = ISysParam.API_RESPONSE_ERRORCODE_EXISTEXCEPTION;
			baseResp.setErrorMsg(e.toString());
		}
		baseResp.setErrorCode(errorCode);

		return ERROR;
	}

	public FeedbackCommon getInstance() {
		return instance;
	}

	public void setInstance(FeedbackCommon instance) {
		this.instance = instance;
	}
	
	public FeedbackReplyCommon getReply() {
		return reply;
	}

	public void setReply(FeedbackReplyCommon reply) {
		this.reply = reply;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getEntityType() {
		return entityType;
	}

	public void setEntityType(int entityType) {
		this.entityType = entityType;
		if (entityType == 2) {// 投诉
			this.clazz = Complaint.class;
		}
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
