package com.xc.touchbox.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.impl.StdScheduler;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xc.gospel.core.util.SimpleUtils;
import com.xc.touchbox.dao.CDAOTemplate;
import com.xc.touchbox.model.ISysParam;
import com.xc.touchbox.service.ConfigService;

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
			
			// 这里做定时任务是否开启判断，只能允许一个容器项目开启
			boolean quartzEnabled = Boolean.parseBoolean(configService.getSystemParam(CDAOTemplate.SYSPARAM_QUARTZ_ENABLED));
			if(!quartzEnabled){
				try{
					StdScheduler schedulerFactoryBean =  (StdScheduler) wac.getBean("schedulerFactoryBean");
					schedulerFactoryBean.shutdown();
					log.info(SimpleUtils.appendFlag("not allowed run quartz, shutdown."));
				}catch(Exception ex){
				}
			}
			
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
