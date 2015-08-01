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
import com.xc.touchbox.model.Complaint;
import com.xc.touchbox.model.ComplaintReply;
import com.xc.touchbox.model.Feedback;
import com.xc.touchbox.model.FeedbackCommon;
import com.xc.touchbox.model.FeedbackReply;
import com.xc.touchbox.model.FeedbackReplyCommon;

public class FeedbackServiceImpl extends CDAOTemplateImpl implements
		FeedbackService {

	/**
	 * org.apache.log4j.Logger对象log
	 */
	private static final Logger log = Logger
			.getLogger(FeedbackServiceImpl.class);

	@Override
	public PaginationSupport<FeedbackCommon> findFeedback(Class clazz,
			String keyword, int type, Integer status, int page, int pagesize) {
		StringBuilder hql = new StringBuilder("select a from "
				+ clazz.getName() + " a where a.status!=2 ");
		List params = new ArrayList();
		if (StringUtils.isNotEmpty(keyword)) {
			hql.append("and a.content like ? ");
			params.add("%" + keyword + "%");
		}
		if (type > 0) {
			hql.append("and a.type=? ");
			params.add(type);
		}
		if (status != null && status >= 0) {
			hql.append("and a.status=? ");
			params.add(status);
		}

		int firstResults = (page - 1) * pagesize;
		return getHQLPagination(hql.toString(), params, firstResults, pagesize);
	}

	@Override
	public void saveFeedback(Class clazz, FeedbackCommon obj) {
		Date date = new Date();

		try {
			if (obj.getId() == null) {
				FeedbackCommon target = (FeedbackCommon) clazz.newInstance();
				BeanUtils.copyProperties(obj, target);

				target.setCreateTime(date);
				getHibernateTemplate().save(target);
			} else {
				FeedbackCommon dbObj = (FeedbackCommon) this
						.getHibernateTemplate().get(clazz, obj.getId());
				BeanUtils.copyProperties(obj, dbObj, new String[] {
						"createTime", "type" });
				dbObj.setUpdateTime(date);
				getHibernateTemplate().update(dbObj);
			}
		} catch (Exception e) {
			SimpleUtils.log(e, log);
		}

	}

	@Override
	public PaginationSupport<FeedbackReplyCommon> findReply(Class clazz,
			int feedbackId, int page, int pagesize) {
		StringBuilder hql = new StringBuilder(
				"select a from "
						+ clazz.getName()
						+ " a where a.status!=2 and a.feedbackId=? order by replayId desc");
		int firstResults = (page - 1) * pagesize;
		return getHQLPagination(hql.toString(), feedbackId, firstResults,
				pagesize);
	}

	@Override
	public void saveReply(int entityType, FeedbackReplyCommon obj) {
		Date date = new Date();

		try {
			Class clazz = Feedback.class, replyClazz = FeedbackReply.class;
			if (entityType == 2) {// 投诉建议回复
				clazz = Complaint.class;
				replyClazz = ComplaintReply.class;
			}
			if (obj.getReplayId() == null) {

				FeedbackReplyCommon target = (FeedbackReplyCommon) replyClazz
						.newInstance();
				BeanUtils.copyProperties(obj, target);

				target.setCreateTime(date);
				getHibernateTemplate().save(target);

				getHibernateTemplate()
						.bulkUpdate(
								"update "
										+ clazz.getName()
										+ " set admin.userId=?,handlerName=?,handleTime=now(),status=1 where id=?",
								new Object[] { obj.getReplierId(),
										obj.getReplierName(),
										obj.getFeedbackId() });
			} else {
				FeedbackReplyCommon dbObj = (FeedbackReplyCommon) this
						.getHibernateTemplate().get(replyClazz,
								obj.getReplayId());
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
