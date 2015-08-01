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
import com.xc.touchbox.model.UserDelivery;

public class UserDeliveryServiceImpl extends CDAOTemplateImpl implements UserDeliveryService {

	/**
	 * org.apache.log4j.Logger对象log
	 */
	private static final Logger log = Logger
			.getLogger(UserDeliveryServiceImpl.class);

	@Override
	public PaginationSupport<UserDelivery> findDelivery(String keyword,
			String statusInStr, String startTime, String endTime, int page, int pagesize) {
		StringBuilder hql = new StringBuilder(
				"select a from UserDelivery a where a.status!=2 ");

		if (StringUtils.isNotEmpty(statusInStr)) {
			hql.append("and a.status in (").append(statusInStr).append(") ");
		}
		List params = new ArrayList();
		if (StringUtils.isNotEmpty(keyword)) {
			hql.append("and (a.itemName like ? or a.goodsId=?) ");
			params.add("%" + keyword + "%");
			params.add(keyword);
		}
		if (StringUtils.isNotEmpty(startTime)) {
			hql.append("and a.createTime>= ? ");
			params.add(startTime);
		}
		if (StringUtils.isNotEmpty(endTime)) {
			hql.append("and a.createTime<= ? ");
			params.add(endTime + " 23:59:59");
		}

		int firstResults = (page - 1) * pagesize;
		return getHQLPagination(hql.toString(), params, firstResults, pagesize);
	}

	@Override
	public void saveDelivery(UserDelivery obj) {
		Date date = new Date();

		try {
			if (obj.getId() == null) {
				obj.setCreateTime(date);
				getHibernateTemplate().save(obj);
			} else {
				UserDelivery dbObj = (UserDelivery) this.getHibernateTemplate().get(
						UserDelivery.class, obj.getId());
				BeanUtils.copyProperties(obj, dbObj,
						new String[] { "createTime" });
				dbObj.setUpdateTime(date);
				getHibernateTemplate().update(dbObj);
			}

		} catch (Exception e) {
			SimpleUtils.log(e, log);
		}

	}

}
