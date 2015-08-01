package com.xc.touchbox.service;

import java.util.Date;

import com.xc.gospel.core.vo.PaginationSupport;
import com.xc.touchbox.dao.CDAOTemplate;
import com.xc.touchbox.model.UserDelivery;

public interface UserDeliveryService extends CDAOTemplate{
	
	/**
	 * 分页查询订单列表数据
	 * 
	 * @param keyword 查询关键字
	 * @param statusInStr 状态字符串
	 * @param startTime 开始时间
	 * @param endTime 截止时间
	 * @param page 页码
	 * @param pagesize 一页多少条
	 * @return
	 */
	public PaginationSupport<UserDelivery> findDelivery(String keyword,String statusInStr,String startTime, String endTime, int page, int pagesize);
	
	/**
	 * 保存订单数据
	 * 
	 * @param obj 订单对象
	 */
	public void saveDelivery(UserDelivery obj);
	
	/**
	 * 生成截止时间有效的发货单
	 * 
	 * @param endTime 不填默认为当前时间
	 */
	public void doBuildDeliveries(Date endTime);
	
	/**
	 * 发送发货单至第三方仓储
	 */
	public void doSendDeliveries();
}
