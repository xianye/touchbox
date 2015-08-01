package com.xc.touchbox.service;

import com.xc.gospel.core.vo.PaginationSupport;
import com.xc.touchbox.dao.CDAOTemplate;
import com.xc.touchbox.model.FeedbackCommon;
import com.xc.touchbox.model.FeedbackReplyCommon;

public interface FeedbackService extends CDAOTemplate{
	
	/**
	 * 分页查询【问题提报/投诉建议】列表数据
	 * 
	 * @param clazz 操作实体类
	 * @param keyword 查询关键字
	 * @param type 类型：1-网站公告，2-文章，3-销售人员相关文档与FAQ，4-常见问题
	 * @param status 状态，null表示全部
	 * @param page 页码
	 * @param pagesize 一页多少条
	 * 
	 * @return
	 */
	public PaginationSupport<FeedbackCommon> findFeedback(Class clazz,String keyword,int type,Integer status,int page, int pagesize);
	
	/**
	 * 保存【问题提报/投诉建议】数据
	 * 
	 * @param clazz 操作实体类
	 * @param obj 【问题提报/投诉建议】对象
	 */
	public void saveFeedback(Class clazz,FeedbackCommon obj);
	
	/**
	 * 分页查询【问题提报/投诉建议】回复列表数据
	 * 
	 * @param clazz 操作实体类
	 * @param feedbackId TODO
	 * @param page 页码
	 * @param pagesize 一页多少条
	 * @return
	 */
	public PaginationSupport<FeedbackReplyCommon> findReply(Class clazz,int feedbackId, int page, int pagesize);

	
	/**
	 * 保存【问题提报/投诉建议】回复数据
	 * 
	 * @param entityType 操作实体类分类
	 * @param obj 【问题提报/投诉建议】回复对象
	 */
	public void saveReply(int entityType,FeedbackReplyCommon obj);
	
}
