package com.xc.touchbox.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import com.xc.touchbox.dao.CDAOTemplate;
import com.xc.touchbox.dao.CDAOTemplateImpl;
import com.xc.touchbox.model.ISysParam;
import com.xc.touchbox.model.ProductCat;
import com.xc.touchbox.model.SystemParam;

public class ConfigServiceImpl extends CDAOTemplateImpl implements
		ConfigService {

	private static ApplicationContext wac;

	// load
	public static void load(ApplicationContext ctx) {
		try {
			wac = ctx;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Object getBean(String name) throws Exception {
		return wac.getBean(name);
	}

	@Override
	public void reload(Map<String, Object> applicationMap) {
		try {
			initialize();

			// 将全局配置参数存入 ServletContext
			applicationMap.put(CDAOTemplate.SYSPARAM_PRODUCTION,
					systemParams.get(CDAOTemplate.SYSPARAM_PRODUCTION));

			applicationMap.put(CDAOTemplate.SYSPARAM_RESOURCE_DIR_URL,
					systemParams.get(CDAOTemplate.SYSPARAM_RESOURCE_DIR_URL));

			initProductCats(applicationMap);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize() {
		List<SystemParam> clist = getSystemParams();
		for (SystemParam config : clist) {
			if (StringUtils.isNotEmpty(config.getValue())) {
				systemParams.put(config.getId(), config.getValue());
			}
		}

		ISysParam.load(systemParams);
	}

	@Override
	public void initProductCats(Map<String, Object> applicationMap) {
		List<ProductCat> list = getHibernateTemplate().find("from ProductCat where status!=2");
		if(CollectionUtils.isNotEmpty(list)){
			applicationMap.put("productCatList", list);
			Map<Integer,ProductCat> map = new HashMap<Integer,ProductCat>();
			for(ProductCat o : list){
				map.put(o.getProductCatId(),o);
			}
			applicationMap.put("productCatMap", map);
		}
	}

}
