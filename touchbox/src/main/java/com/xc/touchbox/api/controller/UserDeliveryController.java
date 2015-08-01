package com.xc.touchbox.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
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
import com.xc.touchbox.model.ISysParam;
import com.xc.touchbox.model.UserDelivery;
import com.xc.touchbox.model.view.GetDataResponse;
import com.xc.touchbox.model.view.PaginationResponse;
import com.xc.touchbox.model.view.UserDeliveryView;
import com.xc.touchbox.service.UserDeliveryService;

@SuppressWarnings("serial")
@ParentPackage("json-default")
@Namespace("/api/delivery")
@Results({ @Result(name = "error", type = "json", params = { "root", "baseResp" }) })
public class UserDeliveryController extends BaseAction {

	private static final Logger log = Logger
			.getLogger(UserDeliveryController.class);

	@Autowired
	@Qualifier("userDeliveryService")
	private UserDeliveryService userDeliveryService;

	private UserDelivery instance;// 输入UserDelivery对象数据

	/**
	 * 查询列表
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
				PaginationSupport<UserDelivery> ps = userDeliveryService
						.findDelivery(keyword, null, startTime, endTime, page,
								pagesize);

				if (ps != null) {
					paginationResp = new PaginationResponse(ps,
							UserDeliveryView.class);
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
		int errorCode = 0;
		try {
			boolean authMD5Key = true;

			StringBuilder authkeyStr = new StringBuilder();
			authkeyStr.append("c=").append(c).append("&dataId=").append(dataId);

			authMD5Key = this.checkAuthMD5Key(authkeyStr.toString());

			if (authMD5Key) {// 秘钥鉴权成功
				UserDelivery a = (UserDelivery) userDeliveryService.get(
						UserDelivery.class, NumberUtils.toLong(dataId));

				if (a != null) {

					getDataResp = new GetDataResponse(a, UserDeliveryView.class);

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
				Admin admin = sessionAdmin;
				if (admin == null) {
					admin = (Admin) userDeliveryService
							.get(Admin.class, userId);
				}
				userDeliveryService.saveDelivery(instance);

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
					whereParamVals.add(instance.getOrderId());
					Map<String, Object> setParams = new HashMap<String, Object>();
					setParams.put("status", instance.getStatus());
					setParams.put("updateTime", date);
					int updateCount = userDeliveryService.executeUpdate(
							UserDelivery.class, setParams, " where id=?",
							whereParamVals);
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

	public UserDelivery getInstance() {
		return instance;
	}

	public void setInstance(UserDelivery instance) {
		this.instance = instance;
	}

}
