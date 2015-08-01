package com.xc.touchbox.web;

import java.util.Map;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.xc.touchbox.controller.BaseAction;
import com.xc.touchbox.model.ISysParam;
import com.xc.touchbox.service.ConfigService;

/**
 * 数据初始化拦截器
 * 
 * @author James
 * 
 */
public class DataInitInterceptor implements Interceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void destroy() {

	}

	public void init() {

	}

	@SuppressWarnings("unchecked")
	public String intercept(ActionInvocation invocation) throws Exception {
		Map session = invocation.getInvocationContext().getSession();
		Map<String, Object> applicationMap = invocation.getInvocationContext()
				.getApplication();
		try {

			// 需要每次初始化
			ConfigService configService = (ConfigService) ISysParam
					.getBean("configService");

			Boolean initSuccess = (Boolean) applicationMap.get("InitSuccess");
			if (initSuccess == null || !initSuccess) {
				// 设置session超时时间，单位“秒”
				ServletActionContext.getRequest().getSession()
						.setMaxInactiveInterval(600 * 60);
				BaseAction.initData(applicationMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return invocation.invoke();
	}
}
