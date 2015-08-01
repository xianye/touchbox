package com.xc.touchbox.model;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;

import com.xc.gospel.core.util.SimpleUtils;
import com.xc.touchbox.dao.CDAOTemplate;

public class ISysParam {

	private static Logger log = Logger.getLogger(ISysParam.class);

	private static WebApplicationContext wac;

	public static final int API_RESPONSE_ERRORCODE_EXISTEXCEPTION = 1;// 存在异常
	public static final int API_RESPONSE_ERRORCODE_AUTHMD5KEY_FAIL = 2;// 校验MD5加密串失败
	public static final int API_RESPONSE_ERRORCODE_REQUESTPARAM_ERROE = 3;// 请求参数错误
	public static final int API_RESPONSE_ERRORCODE_GETSMSVCODE_FAIL = 101;// 获取短信验证码失败
	public static final int API_RESPONSE_ERRORCODE_LOGIN_NOTEXISTACCOUNT = 106;// 登陆不存在账号
	public static final int API_RESPONSE_ERRORCODE_LOGIN_PASSWORDERROR = 107;// 登陆不存在账号
	public static final int API_RESPONSE_ERRORCODE_FINDDATA_NULL = 108;// 查询数据为null
	public static final int API_RESPONSE_ERRORCODE_SAVEDATA_FAIL = 109;// 保存数据失败
	public static final int API_RESPONSE_ERRORCODE_SAVEDATA_EXISTUSERNAME = 110;// 用户名已存在
	public static final int API_RESPONSE_ERRORCODE_UPLOAD_IMAGE_WIDTHHEIGHT_ERROR = 111;// 上传图片宽高错误
	public static final int API_RESPONSE_ERRORCODE_CLASSROOM_EXIST_SAMEPERIODNUM = 112;// 课堂存在相同的期数

	public static boolean PRODUCTION;// 是否正式环境true/false

	public static String API_AUTH_SECRETKEY;// api密钥
	public static String API_SMS_CONTENT_TEMPLET_VCODE;// api短信内容模板--验证码
	public static String API_SMS_CONTENT_TEMPLET_RESETPASSWORD = "亲爱的客户，系统为您重置新密码为$(password)，请登录后修改！";// api短信内容模板--重置密码
	public static String API_SMS_INTERFACE_HTTPURL;// api短信http接口--URL地址
	public static String API_SMS_INTERFACE_USERNAME;// api短信http接口--用户名
	public static String API_SMS_INTERFACE_PASSWORD;// api短信http接口--密码
	public static String API_WAREHOUSING_CHINAWAY_HTTPURL = "http://180.166.232.98:8888/HttpListener.aspx";// 汉维仓储服务接口http地址
	public static String API_WAREHOUSING_CHINAWAY_COMPANY = "TEST";// 汉维仓储服务接口货主编码
	public static String API_WAREHOUSING_CHINAWAY_WAREHOUSE = "HW01";// 汉维仓储服务接口仓库编码
	
	public static String RESOURCE_DIR_ROOTPATH;// 资源文件存放根目录
	public static String RESOURCE_DIR_URL;// 资源目录http地址

	public static void load(Properties systemParams) {
		try {
			PRODUCTION = Boolean.parseBoolean(systemParams.getProperty(
					CDAOTemplate.SYSPARAM_PRODUCTION, "false"));
			API_AUTH_SECRETKEY = systemParams
					.getProperty(CDAOTemplate.SYSPARAM_API_AUTH_SECRETKEY);
			API_SMS_CONTENT_TEMPLET_VCODE = systemParams
					.getProperty(CDAOTemplate.SYSPARAM_API_SMS_CONTENT_TEMPLET_VCODE);
			API_SMS_INTERFACE_HTTPURL = systemParams
					.getProperty(CDAOTemplate.SYSPARAM_API_SMS_INTERFACE_HTTPURL);
			API_SMS_INTERFACE_USERNAME = systemParams
					.getProperty(CDAOTemplate.SYSPARAM_API_SMS_INTERFACE_USERNAME);
			API_SMS_INTERFACE_PASSWORD = systemParams
					.getProperty(CDAOTemplate.SYSPARAM_API_SMS_INTERFACE_PASSWORD);
			API_WAREHOUSING_CHINAWAY_HTTPURL = systemParams
					.getProperty(CDAOTemplate.SYSPARAM_API_WAREHOUSING_CHINAWAY_HTTPURL);
			API_WAREHOUSING_CHINAWAY_COMPANY = systemParams
					.getProperty(CDAOTemplate.SYSPARAM_API_WAREHOUSING_CHINAWAY_COMPANY);
			API_WAREHOUSING_CHINAWAY_WAREHOUSE = systemParams
					.getProperty(CDAOTemplate.SYSPARAM_API_WAREHOUSING_CHINAWAY_WAREHOUSE);
			
			RESOURCE_DIR_ROOTPATH = systemParams
					.getProperty(CDAOTemplate.SYSPARAM_RESOURCE_DIR_ROOTPATH);
			RESOURCE_DIR_URL = systemParams
					.getProperty(CDAOTemplate.SYSPARAM_RESOURCE_DIR_URL);
		} catch (Exception e) {
			SimpleUtils.log(e, log);
		}
	}

	// load
	public static void initCtx(WebApplicationContext ctx) {
		try {
			wac = ctx;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Object getBean(String name) throws Exception {
		return wac.getBean(name);
	}
	
	/**
	 * 获取当前环境的ApplicationContext
	 * @return
	 */
	public static WebApplicationContext getCtx() {
		return wac;
	}
	
	/**
	 * 原始密码MD5加密
	 * 
	 * @param origin
	 *            原始密码
	 * @return
	 */
	public static String encodePassword(String origin) {
		if (StringUtils.isEmpty(origin))
			return origin;
		return SimpleUtils.MD5Encode("t" + origin);
	}

	/**
	 * 获取指定资讯分类名称
	 * 
	 * @param type
	 *            资讯分类（1-网站公告，2-相关新闻，3-销售人员相关文档与FAQ，4-常见问题）
	 * @return
	 */
	public static String getInformationTypeName(int type) {
		switch (type) {
		case CDAOTemplate.CONSTANT_INFORMATION_TYPE_PROCLAMATION:
			return "网站公告";
		case CDAOTemplate.CONSTANT_INFORMATION_TYPE_ABOUTNEWS:
			return "相关新闻";
		case CDAOTemplate.CONSTANT_INFORMATION_TYPE_SALESTOOL:
			return "销售工具";
		case CDAOTemplate.CONSTANT_INFORMATION_TYPE_FAQ:
			return "常见问题";
		}
		return "资讯";
	}

	/**
	 * 获取指定反馈实体分类名称
	 * 
	 * @param entityType
	 *            反馈实体分类（1-问题提报，2-投诉建议，默认为问题提报）
	 * @return
	 */
	public static String getFeedbackName(int entityType) {
		switch (entityType) {
		case CDAOTemplate.CONSTANT_FEEDBACK_ENTITYTYPE_FEEDBACK:
			return "问题提报";
		case CDAOTemplate.CONSTANT_FEEDBACK_ENTITYTYPE_COMPLAINT:
			return "投诉建议";
		}
		return "问题提报";
	}
	
	/**
	 * 获取指定发货仓编码对应的名称
	 * 
	 * @param deliveryWarehouse
	 *           发货仓编码（SH-上海，BJ-北京）
	 * @return
	 */
	public static String getDeliveryWarehouseName(String deliveryWarehouse) {
		String name = "";
		if(StringUtils.isNotEmpty(deliveryWarehouse)){
			if("SH".equalsIgnoreCase(deliveryWarehouse)){
				name = "上海";
			}else if("BJ".equalsIgnoreCase(deliveryWarehouse)){
				name = "北京";
			}else{
				name = deliveryWarehouse;
			}
		}
		return name;
	}
}
