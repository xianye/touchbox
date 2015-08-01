package com.xc.touchbox.web;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.TimerInterceptor;
import com.opensymphony.xwork2.util.ArrayUtils;
import com.xc.gospel.core.util.SimpleUtils;
import com.xc.touchbox.dao.CDAOTemplate;
import com.xc.touchbox.model.ISysParam;
import com.xc.touchbox.model.Serial;
import com.xc.touchbox.model.User;
import com.xc.touchbox.service.UserService;

public class OperationLogInterceptor extends TimerInterceptor {
	private static final Logger log = Logger
			.getLogger(OperationLogInterceptor.class);

	protected String invokeUnderTiming(ActionInvocation invocation)
			throws Exception {
		long startTime = System.currentTimeMillis();

		StringBuilder message = new StringBuilder();
		message.append("ExecutedAction{url:'").append(
				ServletActionContext.getRequest().getRequestURL());
		// pageUrl是JSP页面用来判断当前所在版块，以实现相应版块样式变化（选中版块颜色变黑）
		HttpServletRequest request = ServletActionContext.getRequest();
		StringBuffer temp = request.getRequestURL();
		int idx = temp.lastIndexOf("/");
		String sectionName = temp.substring(idx + 1, temp.length());
		System.out.println("sectionName=" + sectionName);
		request.setAttribute("sectionName", sectionName);

		String platform = request.getParameter("platform");// 平台

		Map<String, Object> requestParams = invocation.getInvocationContext()
				.getParameters();

		if (MapUtils.isNotEmpty(requestParams)) {

			message.append("?");
			for (String paramKey : requestParams.keySet()) {
				message.append(paramKey).append("=");
				Object paramValue = requestParams.get(paramKey);
				if (paramValue instanceof String[]) {
					String[] paramValueArr = (String[]) paramValue;
					if (paramValueArr.length > 1) {
						message.append("[");
						for (int i = 0; i < paramValueArr.length; i++) {
							message.append(paramValueArr[i]).append(",");
						}
						message.deleteCharAt(message.length() - 1);
						message.append("]");
					} else {
						message.append(paramValueArr[0]);
					}
				} else {
					message.append(paramValue);
				}
				message.append("&");
			}
			message.deleteCharAt(message.length() - 1);
		}
		message.append("'");

		ActionContext ctx = invocation.getInvocationContext();
		Map<String, Object> session = ctx.getSession();
		if (session != null) {
			try {
				// 需要每次初始化
				UserService userService = (UserService) ISysParam
						.getBean("userService");
				Serial serial = new Serial();
				serial.setPlatform(platform);

				if (!serial.isAppPlatform()) {// 非app版本，序列号和session绑定
					Serial serial1 = (Serial) session
							.get(CDAOTemplate.SESSION_SERIAL);
					if (serial1 != null) {
						serial = serial1;
					} else {
						serial.setChannel(request.getParameter("channel"));
						serial.setSoftType(1);
						boolean handleSuccess = userService
								.doGenerateSerial(serial);
						if (handleSuccess) {
							session.put(CDAOTemplate.SESSION_SERIAL, serial);
						}
					}
				}

				User user = (User) session.get(CDAOTemplate.SESSION_USER);
				// TODO 记录操作用户
				String actionName = ctx.getName();// action名称

				message.append(",session:").append(session);

				if (user != null) {
					message.append(",account:").append(user.getUserId());
				}
			} catch (Exception e) {
			}

		}

		String result = invocation.invoke();

		// cookie 记录日志
		Cookie[] cookies = ServletActionContext.getRequest().getCookies();
		if (ArrayUtils.isNotEmpty(cookies)) {
			message.append(",cookies:[");
			for (Cookie cookie : cookies) {
				message.append("{name:").append(cookie.getName())
						.append(",value:").append(cookie.getValue());
				if (cookie.getPath() != null) {
					message.append(",path:").append(cookie.getPath());
				}
				message.append("},");
			}
			message.deleteCharAt(message.length() - 1);
			message.append("]");
		}

		long executionTime = System.currentTimeMillis() - startTime;

		message.append("} took ").append(executionTime).append(" ms.");

		String messageLog = SimpleUtils.appendFlag(message.toString());
		doLog(getLoggerToUse(), messageLog);

		return result;
	}
}
