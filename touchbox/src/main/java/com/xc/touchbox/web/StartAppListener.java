package com.xc.touchbox.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xc.touchbox.model.ISysParam;
import com.xc.touchbox.service.ConfigService;
import com.xc.touchbox.service.ConfigServiceImpl;

public class StartAppListener extends ContextLoaderListener implements
		ServletContextListener {
	private static final Log log = LogFactory.getLog(StartAppListener.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {

		try {
			super.contextInitialized(event);
			ServletContext scontext = event.getServletContext();
			WebApplicationContext wac = WebApplicationContextUtils
					.getRequiredWebApplicationContext(scontext);
			// 加载系统配置
			ISysParam.initCtx(wac);
			
			ConfigService configService = (ConfigService) ISysParam
					.getBean("configService");
			
			configService.initialize();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("StartAppListener.contextInitialized() error: " + e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
	}

}
