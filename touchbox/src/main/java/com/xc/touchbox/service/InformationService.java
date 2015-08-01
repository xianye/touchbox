package com.xc.touchbox.service;

import com.xc.gospel.core.vo.PaginationSupport;
import com.xc.touchbox.dao.CDAOTemplate;
import com.xc.touchbox.model.Information;
import com.xc.touchbox.model.Product;

public interface InformationService extends CDAOTemplate{
	
	/**
	 * 分页查询资讯列表数据
	 * 
	 * @param keyword 查询关键字
	 * @param type 类型：1-网站公告，2-文章，3-销售人员相关文档与FAQ，4-常见问题
	 * @param catId 资讯分类
	 * @param page 页码
	 * @param pagesize 一页多少条
	 * @return
	 */
	public PaginationSupport<Information> findInformation(String keyword,int type,int catId,int page,int pagesize);
	
	/**
	 * 保存资讯数据
	 * 
	 * @param obj 资讯对象
	 */
	public void saveInformation(Information obj);
	
}
