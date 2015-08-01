package com.xc.touchbox.api.controller;

import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.xc.gospel.core.util.SimpleUtils;
import com.xc.touchbox.controller.BaseAction;
import com.xc.touchbox.model.City;
import com.xc.touchbox.model.ISysParam;
import com.xc.touchbox.model.view.CityView;
import com.xc.touchbox.model.view.ListResponse;

@SuppressWarnings("serial")
@ParentPackage("json-default")
@Namespace("/api/city")
@Results({ @Result(name = "error", type = "json", params = { "root", "baseResp" }) })
public class CityController extends BaseAction {

	private static final Logger log = Logger.getLogger(CityController.class);

	private String cityId;// 城市ID

	/**
	 * 查询列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Action(value = "children", results = { @Result(name = "success", type = "json", params = {
			"root", "listResp", "excludeNullProperties", "true" }) })
	public String children() throws Exception {
		int errorCode = 0;
		try {
			boolean authMD5Key = true;

			StringBuilder authkeyStr = new StringBuilder();
			authkeyStr.append("c=").append(c);

			authMD5Key = this.checkAuthMD5Key(authkeyStr.toString());
			if (authMD5Key) {// 秘钥鉴权成功
				City city = ((Map<String, City>) applicationMap.get("cityMap"))
						.get(cityId);

				if (city != null
						&& CollectionUtils.isNotEmpty(city.getChildren())) {
					listResp = new ListResponse(city.getChildren(),
							CityView.class);
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

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

}
