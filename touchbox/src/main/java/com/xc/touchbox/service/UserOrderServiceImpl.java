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
import com.xc.touchbox.model.Freight;
import com.xc.touchbox.model.UserChildren;
import com.xc.touchbox.model.UserOrder;

public class UserOrderServiceImpl extends CDAOTemplateImpl implements
		UserOrderService {

	/**
	 * org.apache.log4j.Logger对象log
	 */
	private static final Logger log = Logger
			.getLogger(UserOrderServiceImpl.class);

	@Override
	public PaginationSupport<UserOrder> findOrder(String keyword,
			String statusInStr, String startTime, String endTime, int page,
			int pagesize) {
		StringBuilder hql = new StringBuilder(
				"select a from UserOrder a where a.status!=2 ");

		if (StringUtils.isNotEmpty(statusInStr)) {
			hql.append("and a.status in (").append(statusInStr).append(") ");
		}
		List params = new ArrayList();
		if (StringUtils.isNotEmpty(keyword)) {
			hql.append("and a.parentMobile like ? ");
			params.add("%" + keyword + "%");
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
	public void saveOrder(UserOrder obj) {
		Date date = new Date();

		try {
			if (obj.getOrderId() == null) {
				obj.setCreateTime(date);
				getHibernateTemplate().save(obj);
			} else {
				UserOrder dbObj = (UserOrder) this.getHibernateTemplate().get(
						UserOrder.class, obj.getOrderId());
				BeanUtils.copyProperties(obj, dbObj,
						new String[] { "createTime" });
				dbObj.setUpdateTime(date);
				getHibernateTemplate().update(dbObj);
			}
			
			if(StringUtils.isNotEmpty(obj.getChildName())){// 保存小朋友信息
				UserChildren a = new UserChildren();
				a.setUserId(obj.getUser().getUserId());
				a.setChildName(obj.getChildName());
				a.generateChildrenId();
				
				UserChildren b = getHibernateTemplate().get(UserChildren.class, a.getChildrenId());
				if(b!=null){
					if(obj.getChildBirthday()!=null){
						b.setChildBirthday(obj.getChildBirthday());
					}
					if(StringUtils.isNotEmpty(obj.getSchoolName())){
						b.setSchoolName(obj.getSchoolName());
					}
					getHibernateTemplate().update(b);
				}else{
					a.setCreateTime(date);
					getHibernateTemplate().save(a);
				}
			}
		} catch (Exception e) {
			SimpleUtils.log(e, log);
		}

	}

	@Override
	public PaginationSupport<Freight> findFreight(String keyword,
			String statusInStr, int page, int pagesize) {
		StringBuilder hql = new StringBuilder(
				"select a from Freight a where a.status!=2 ");

		if (StringUtils.isNotEmpty(statusInStr)) {
			hql.append("and a.status in (").append(statusInStr).append(") ");
		}
		List params = new ArrayList();
		if (StringUtils.isNotEmpty(keyword)) {
			hql.append("and a.cityName like ? ");
			params.add("%" + keyword + "%");
		}
		
		int firstResults = (page - 1) * pagesize;
		return getHQLPagination(hql.toString(), params, firstResults, pagesize);
	}

}
