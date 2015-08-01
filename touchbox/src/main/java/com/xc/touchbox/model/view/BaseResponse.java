package com.xc.touchbox.model.view;

import java.io.Serializable;

import com.xc.touchbox.model.ISysParam;

public class BaseResponse implements Serializable {

	private int errorCode = 0;
	private String errorMsg = "";

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
		String errMsg = "";
		switch (errorCode) {
		case 0:
			break;
		case ISysParam.API_RESPONSE_ERRORCODE_AUTHMD5KEY_FAIL:
			errMsg = "校验MD5加密串失败";
			break;
		case ISysParam.API_RESPONSE_ERRORCODE_REQUESTPARAM_ERROE:
			errMsg = "请求参数不正确，请检查";
			break;
		case ISysParam.API_RESPONSE_ERRORCODE_GETSMSVCODE_FAIL:
			errMsg = "获取短信验证码失败";
			break;
		case ISysParam.API_RESPONSE_ERRORCODE_LOGIN_NOTEXISTACCOUNT:
			errMsg = "登陆操作，不存在账号";
			break;
		case ISysParam.API_RESPONSE_ERRORCODE_LOGIN_PASSWORDERROR:
			errMsg = "登陆操作，密码错误";
			break;
		case ISysParam.API_RESPONSE_ERRORCODE_FINDDATA_NULL:
			errMsg = "查询数据为null";
			break;
		case ISysParam.API_RESPONSE_ERRORCODE_SAVEDATA_FAIL:// 保存数据失败
			errMsg = "保存数据失败";
			break;
		case ISysParam.API_RESPONSE_ERRORCODE_SAVEDATA_EXISTUSERNAME:// 用户名已存在
			errMsg = "用户名已存在";
			break;
		case ISysParam.API_RESPONSE_ERRORCODE_UPLOAD_IMAGE_WIDTHHEIGHT_ERROR:
			errMsg = "上传图片的宽高不符合要求";
			break;
		case ISysParam.API_RESPONSE_ERRORCODE_CLASSROOM_EXIST_SAMEPERIODNUM:
			errMsg = "存在相同的期数课堂";
			break;
		}
		this.setErrorMsg(errMsg);
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
