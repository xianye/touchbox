package com.xc.touchbox.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.xc.gospel.core.util.SimpleUtils;
import com.xc.gospel.core.vo.PaginationSupport;
import com.xc.touchbox.dao.CDAOTemplateImpl;
import com.xc.touchbox.model.Information;

public class InformationServiceImpl extends CDAOTemplateImpl implements
		InformationService {

	/**
	 * org.apache.log4j.Logger对象log
	 */
	private static final Logger log = Logger
			.getLogger(InformationServiceImpl.class);

	@Override
	public PaginationSupport<Information> findInformation(String keyword,int type,int catId, int page,
			int pagesize) {
		StringBuilder hql = new StringBuilder(
				"select a from Information a where a.type=? and a.status!=2 ");
		List params = new ArrayList();
		params.add(type);
		if (StringUtils.isNotEmpty(keyword)) {
			hql.append("and a.title like ? ");
			params.add("%" + keyword + "%");
		}

		int firstResults = (page - 1) * pagesize;
		return getHQLPagination(hql.toString(), params, firstResults, pagesize);
	}

	@Override
	public void saveInformation(Information obj) {
		Date date = new Date();

		try {
			obj.setEditTime(date);
			if (obj.getId() == null) {
				obj.setCreateTime(date);
				getHibernateTemplate().save(obj);
			} else {
				Information dbObj = (Information) this.getHibernateTemplate().get(
						Information.class, obj.getId());
				BeanUtils.copyProperties(obj, dbObj,
						new String[] { "createTime","sequence","type" });
				dbObj.setUpdateTime(date);
				getHibernateTemplate().update(dbObj);
			}
		} catch (Exception e) {
			SimpleUtils.log(e, log);
		}

	}

}
