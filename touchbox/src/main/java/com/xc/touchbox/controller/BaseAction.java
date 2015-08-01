package com.xc.touchbox.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.xc.gospel.core.util.SimpleUtils;
import com.xc.touchbox.dao.CDAOTemplate;
import com.xc.touchbox.model.Admin;
import com.xc.touchbox.model.ISysParam;
import com.xc.touchbox.model.User;
import com.xc.touchbox.model.view.BaseResponse;
import com.xc.touchbox.model.view.GetDataResponse;
import com.xc.touchbox.model.view.ListResponse;
import com.xc.touchbox.model.view.PaginationResponse;
import com.xc.touchbox.service.ConfigService;
import com.xc.touchbox.service.UserService;
import com.xc.touchbox.thirdparty.SmsService;

/**
 * 
 * @author James
 * 
 */
public abstract class BaseAction extends ActionSupport implements SessionAware,
		ServletRequestAware, ServletResponseAware, ApplicationAware {

	/**
	 * org.apache.log4j.Logger对象log
	 */
	private static final Logger log = Logger.getLogger(BaseAction.class);

	@Autowired
	@Qualifier("configService")
	protected ConfigService configService;

	@Autowired
	@Qualifier("userService")
	protected UserService userService;
	
	@Autowired
	@Qualifier("smsService")
	protected SmsService smsService;
	
	protected String dataId;// 数据ID
	protected String[] dataIds;// 数据ID
	protected long userId;// 用户ID
	protected String username;// 用户名
	protected String password;// 密码
	protected String authkey;// MD5加密串
	protected String version;// 接口版本
	protected String serialId;// 序列号
	protected String platform;// 软件平台类型（web，wap，android，iphone等）
	protected int softType;// 软件分类（同一平台按数字编号，从1开始自增）
	protected String channel;// 渠道号(空为普通版本)
	protected String keyword;// 关键字
	protected String mobile;// 手机号码
	protected String startTime;// 开始时间
	protected String endTime;// 截止时间
	protected int page = 1;// 页码
	protected int pagesize = 10;// 一页多少条
	protected Integer c;// 功能指令
	protected String format;// 格式

	protected String ip;// 远程访问ip地址
	protected String contextDir;// web上下文所在目录
	protected String rootPath;// http根路径

	protected User sessionUser;// session用户
	protected Admin sessionAdmin;// session管理员

	// 列表对象
	protected Collection list;
	protected Map map;

	protected BaseResponse baseResp = new BaseResponse();
	protected GetDataResponse getDataResp = new GetDataResponse();
	protected ListResponse listResp = new ListResponse();
	protected PaginationResponse paginationResp = new PaginationResponse();

	public Map getMap() {
		return map;
	}

	public Collection getList() {
		return list;
	}

	public PaginationResponse getPaginationResp() {
		return paginationResp;
	}

	public BaseResponse getBaseResp() {
		return baseResp;
	}

	public ListResponse getListResp() {
		return listResp;
	}

	public GetDataResponse getGetDataResp() {
		return getDataResp;
	}

	public String getRootPath() {
		return rootPath;
	}

	public String getContextDir() {
		return contextDir;
	}

	public String getIp() {
		return ip;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {

		if (StringUtils.isNotEmpty(keyword)&&request.getMethod().equalsIgnoreCase("GET")) {
			try {
				log.info(SimpleUtils.appendFlag("keyword:" + keyword + ",utf8:"
						+ URLEncoder.encode(keyword, "utf-8") + ",gbk:"
						+ URLEncoder.encode(keyword, "gbk") + ",iso8859_1:"
						+ URLEncoder.encode(keyword, "iso8859_1")));
			} catch (UnsupportedEncodingException e) {
			}
			this.keyword = SimpleUtils.stringConvert(keyword, "iso8859_1",
					"utf-8");
		}else{
			this.keyword = keyword;
		}
	}
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		if (page <= 0)
			this.page = 1;
		else
			this.page = page;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		if (pagesize <= 0)
			this.pagesize = 10;
		else
			this.pagesize = pagesize;
	}

	public Integer getC() {
		return c;
	}

	public void setC(Integer c) {
		this.c = c;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String[] getDataIds() {
		return dataIds;
	}

	public void setDataIds(String[] dataIds) {
		this.dataIds = dataIds;
	}

	Map<String, Object> sessionMap;// session集合
	protected Map<String, Object> applicationMap;// 应用全局集合
	protected transient HttpServletRequest request;
	protected transient HttpServletResponse response;

	@Override
	public void setApplication(Map<String, Object> arg0) {
		applicationMap = arg0;
	}

	@SuppressWarnings("unchecked")
	public void setSession(Map<String, Object> session) {
		this.sessionMap = session;
	}

	public Map<String, Object> getSession() {
		return sessionMap;
	}

	public void putInSession(String key, String value) {
		sessionMap.put(key, value);
	}

	public void putInSession(String key, Object value) {
		sessionMap.put(key, value);
	}

	public Object getFromSession(String key) {
		return sessionMap.get(key);
	}

	public void removeSession(String key) {
		sessionMap.remove(key);
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	public void setServletResponse(HttpServletResponse arg0) {
		this.response = arg0;
		response.setCharacterEncoding("utf-8");
	}

	public String getAuthkey() {
		return authkey;
	}

	public void setAuthkey(String authkey) {
		this.authkey = authkey;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public User getSessionUser() {
		return sessionUser;
	}

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * 数据初始化
	 * 
	 * @param applicationMap
	 */
	public static void initData(Map<String, Object> applicationMap) {
		try {
			log.info("init start ................... "
					+ DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			Boolean initSuccess = (Boolean) applicationMap.get("InitSuccess");
			if (initSuccess == null || !initSuccess) {
				ConfigService configService = (ConfigService) ISysParam
						.getBean("configService");
				configService.reload(applicationMap);

				applicationMap.put("InitSuccess", true);
				applicationMap.put("InitTime", System.currentTimeMillis());
				log.info("InitSuccess, InitTime:" + System.currentTimeMillis());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.info("init end ................... "
				+ DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 获取session用户对象
	 */
	protected void fetchSessionUser() {
		sessionUser = (User) ActionContext.getContext().getSession()
				.get(CDAOTemplate.SESSION_USER);
	}

	/**
	 * 获取session管理员对象
	 */
	protected void fetchSessionAdmin() {
		sessionAdmin = (Admin) sessionMap.get(CDAOTemplate.SESSION_ADMIN);
	}

	/**
	 * 初始化web上下文内容
	 */
	protected void initWebContext() {
		HttpServletRequest request = ServletActionContext.getRequest();
		ip = request.getRemoteAddr();
		if (request.getServerPort() == 80) {
			rootPath = request.getScheme() + "://" + request.getServerName();
		} else {
			rootPath = request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort();
		}
		rootPath += request.getContextPath() + "/";

		// fetchSessionAdmin();

		ActionContext ctx = ActionContext.getContext();
		ServletContext sc = (ServletContext) ctx
				.get(ServletActionContext.SERVLET_CONTEXT);
		contextDir = sc.getRealPath("/");
	}

	/**
	 * 获取MD5加密串
	 * 
	 * @param authkeyStr
	 * @return
	 */
	protected String getMD5Authkey(String authkeyStr) {
		String currAuthkey = SimpleUtils.MD5Encode(SimpleUtils.MD5Encode(
				authkeyStr).toUpperCase()
				+ ISysParam.API_AUTH_SECRETKEY);
		return currAuthkey;
	}

	/**
	 * DAO对象初始化web_context数据（如：ip,operator,...）
	 */
	public void bindWebContextToBean(CDAOTemplate obj) {
		initWebContext();
		obj.setRootDir(contextDir);
		obj.setRootPath(rootPath);
	}

	/**
	 * 检查MD5校验串是否正确
	 * 
	 * @param authkeyStr
	 *            原始字符串
	 * @return
	 */
	protected boolean checkAuthMD5Key(String authkeyStr) {
		String currAuthkey = getMD5Authkey(authkeyStr.toString());
		if (currAuthkey.equalsIgnoreCase(authkey)) {
			return true;
		} else {
			log.info(SimpleUtils.appendFlag("AuthMD5Key '" + authkey
					+ "' fail, myAuthkeyPre is '" + authkeyStr
					+ "', myAuthkey is '" + currAuthkey
					+ "' , please check-code."));
			// TODO 测试临时放开
			return true;
		}

	}

}
