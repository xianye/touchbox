package com.xc.touchbox.service;

import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.xc.touchbox.dao.CDAOTemplate;

public interface ConfigService extends CDAOTemplate{
	
	/**
	 * 初始化配置信息
	 */
	public void initialize();
		
	/**
	 * 
	 * 重新初始化系统配置
	 *
	 * @param applicationMap
	 */
	public void reload(Map<String, Object> applicationMap);
	
	/**
	 * 初始化产品分类数据
	 * 
	 * @param applicationMap
	 */
	public void initProductCats(Map<String, Object> applicationMap);

}
