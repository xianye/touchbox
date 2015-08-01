package com.xc.touchbox.service;

import com.xc.gospel.core.vo.PaginationSupport;
import com.xc.touchbox.dao.CDAOTemplate;
import com.xc.touchbox.model.Classroom;

public interface ClassroomService extends CDAOTemplate{
	
	/**
	 * 分页查询列表数据
	 * 
	 * @param keyword 查询关键字
	 * @param statusInStr 状态字符串
	 * @param page 页码
	 * @param pagesize 一页多少条
	 * @return
	 */
	public PaginationSupport<Classroom> pageQuery(String keyword,String statusInStr,int page, int pagesize);
	
	/**
	 * 保存数据
	 * 
	 * @param obj 产品对象
	 */
	public void save(Classroom obj);
	
	/**
	 * 是否存在当期视频
	 * 
	 * @param obj
	 * @return
	 */
	public boolean isExist(Classroom obj);
	
	/**
	 * 分页查询订购列表数据
	 * 
	 * @param userId 用户ID
	 * @param page 页码
	 * @param pagesize 一页多少条
	 * @return
	 */
	public PaginationSupport<Classroom> pageQueryOrder(long userId,int page, int pagesize);
	
	/**
	 * 分页查询收藏列表数据
	 * 
	 * @param userId 用户ID
	 * @param page 页码
	 * @param pagesize 一页多少条
	 * @return
	 */
	public PaginationSupport<Classroom> pageQueryCollection(long userId,int page, int pagesize);

}
