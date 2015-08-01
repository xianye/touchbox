package com.xc.touchbox.web;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.xc.gospel.core.util.SimpleUtils;
import com.xc.touchbox.dao.CDAOTemplate;
import com.xc.touchbox.model.Admin;

public class IdentificationInterceptor extends AbstractInterceptor {

	/**
	 * org.apache.log4j.Logger对象log
	 */
	private static final Logger log = Logger
			.getLogger(IdentificationInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// 获取请求的action名称
		ActionContext ctx = invocation.getInvocationContext();
		Map session = ctx.getSession();
		HttpSession httpSession = ServletActionContext.getRequest()
				.getSession();

		Admin admin = (Admin) session.get(CDAOTemplate.SESSION_ADMIN);
		String requestUrl = getRequestURL(ServletActionContext.getRequest(),
				"UTF-8");
		ServletActionContext.getRequest()
				.setAttribute("RequestURL", requestUrl);
		if (admin != null) {// 确定为白名单账号对象
			return invocation.invoke();
		} else {
			log.info(SimpleUtils.appendFlag("lost session, forward to login"));
			return Action.LOGIN;
		}
	}

	/**
	 * 
	 * 取得请求地址，不管是何种方式的请求
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param encoding
	 *            中文参数编码方案
	 * @return 请求地址
	 */
	private String getRequestURL(HttpServletRequest request, String encoding) {
		String url = null;
		try {
			HttpSession session = request.getSession();
			url = request.getRequestURL().toString();
			// url += ";jsessionid=" + session.getId();
			String name;
			String value = "";
			String querystr = request.getQueryString();
			String paramVal = null;
			if (request.getMethod().equalsIgnoreCase("post")) {
				for (Enumeration e = request.getParameterNames(); e
						.hasMoreElements();) {
					name = (String) e.nextElement();
					paramVal = request.getParameter(name);
					log.info("Parameter[" + name + ":" + paramVal + "]");
					if (paramVal != null) {
						value += (name + "=" + paramVal + "&");
					}
				}
				if (value.length() != 0) {
					value = value.substring(0, value.length() - 1);
				}
			} else {
				if (querystr != null) {
					value += querystr;
				}
			}
			if (value.length() != 0) {
				url += ("?" + value);
			}
			if (url != null) {
				url = url.replaceAll("&amp;", "&");
			}
			// url = URLEncoder.encode(url, encoding);
			log.info("RequestURL：" + url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}

}
