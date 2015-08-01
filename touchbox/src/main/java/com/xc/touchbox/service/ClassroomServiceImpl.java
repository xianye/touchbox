package com.xc.touchbox.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.xc.gospel.core.util.SimpleUtils;
import com.xc.gospel.core.vo.PaginationSupport;
import com.xc.touchbox.dao.CDAOTemplateImpl;
import com.xc.touchbox.model.Classroom;
import com.xc.touchbox.model.Product;

public class ClassroomServiceImpl extends CDAOTemplateImpl implements
		ClassroomService {

	/**
	 * org.apache.log4j.Logger对象log
	 */
	private static final Logger log = Logger
			.getLogger(ClassroomServiceImpl.class);

	@Override
	public PaginationSupport<Classroom> pageQuery(String keyword,
			String statusInStr, int page, int pagesize) {
		StringBuilder hql = new StringBuilder(
				"from Classroom a,Product b where a.status!=2 and b.status!=2 and a.productId=b.productId ");

		if (StringUtils.isNotEmpty(statusInStr)) {
			hql.append("and a.status in (").append(statusInStr).append(") ");
		}
		List params = new ArrayList();
		if (StringUtils.isNotEmpty(keyword)) {
			hql.append("and a.name like ? ");
			params.add("%" + keyword + "%");
		}

		int firstResults = (page - 1) * pagesize;
		PaginationSupport ps = getHQLPagination(hql.toString(), params,
				firstResults, pagesize);

		if (ps != null && CollectionUtils.isNotEmpty(ps.getItems())) {// 获取产品信息并赋值
			List<Classroom> items = new ArrayList<Classroom>();
			for (Object[] o : (List<Object[]>) ps.getItems()) {
				Classroom a = (Classroom) o[0];
				a.setProduct((Product) o[1]);
				items.add(a);
			}
			ps.setItems(items);
		}
		return ps;
	}

	@Override
	public void save(Classroom obj) {
		Date date = new Date();

		try {
			if (obj.getClassroomId() == null) {
				obj.setCreateTime(date);
				getHibernateTemplate().save(obj);
			} else {
				Classroom dbObj = (Classroom) this.getHibernateTemplate().get(
						Classroom.class, obj.getClassroomId());
				BeanUtils.copyProperties(obj, dbObj,
						new String[] { "createTime" });
				dbObj.setUpdateTime(date);
				getHibernateTemplate().update(dbObj);
			}
		} catch (Exception e) {
			SimpleUtils.log(e, log);
		}

	}

	@Override
	public boolean isExist(Classroom obj) {
		Long classroomId = (Long) getHQLUnique(
				"select classroomId from Classroom where status!=2 and productId=? and periodNum=? ",
				new Object[] { obj.getProductId(), obj.getPeriodNum() });

		return classroomId != null && classroomId > 0;
	}

	@Override
	public PaginationSupport<Classroom> pageQueryOrder(long userId, int page,
			int pagesize) {
		List<Long> classroomIds = null;
		if (userId > 0) {
			classroomIds = getHibernateTemplate()
					.find("select classroomId from UserClassroomOrder where userId=?",
							userId);
		}

		StringBuilder hql = new StringBuilder(
				"select a,b from Classroom a,Product b where a.status=1 and b.status!=2 and a.productId=b.productId ");

		List params = new ArrayList();
		if (CollectionUtils.isNotEmpty(classroomIds)) {
			hql.append("and (a.classroomId in ("
					+ StringUtils.join(classroomIds, ",")
					+ ") or a.free=true) ");
		} else {
			hql.append("and a.free=true ");
		}

		hql.append("order by a.classroomId desc");
		
		int firstResults = (page - 1) * pagesize;
		PaginationSupport ps = getHQLPagination(hql.toString(), params,
				firstResults, pagesize);

		if (ps != null && CollectionUtils.isNotEmpty(ps.getItems())) {// 获取产品信息并赋值
			List<Classroom> items = new ArrayList<Classroom>();
			for (Object[] o : (List<Object[]>) ps.getItems()) {
				Classroom a = (Classroom) o[0];
				a.setProduct((Product) o[1]);
				items.add(a);
			}
			ps.setItems(items);
		}
		return ps;
	}

	@Override
	public PaginationSupport<Classroom> pageQueryCollection(long userId,
			int page, int pagesize) {
		List<Long> classroomIds = getHibernateTemplate()
				.find("select classroomId from UserClassroomCollection where userId=?",
						userId);
		if (CollectionUtils.isNotEmpty(classroomIds)) {

			StringBuilder hql = new StringBuilder(
					"select a,b from Classroom a,Product b where a.status=1 and b.status!=2 and a.productId=b.productId ");

			List params = new ArrayList();
			hql.append("and a.classroomId in ("
					+ StringUtils.join(classroomIds, ",") + ") ");
			
			hql.append("order by a.classroomId desc");

			int firstResults = (page - 1) * pagesize;
			PaginationSupport ps = getHQLPagination(hql.toString(), params,
					firstResults, pagesize);

			if (ps != null && CollectionUtils.isNotEmpty(ps.getItems())) {// 获取产品信息并赋值
				List<Classroom> items = new ArrayList<Classroom>();
				for (Object[] o : (List<Object[]>) ps.getItems()) {
					Classroom a = (Classroom) o[0];
					a.setProduct((Product) o[1]);
					items.add(a);
				}
				ps.setItems(items);
			}
			return ps;
		}
		return null;
	}

}
