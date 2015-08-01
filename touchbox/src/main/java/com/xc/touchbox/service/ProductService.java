package com.xc.touchbox.service;

import java.util.List;

import com.xc.gospel.core.vo.PaginationSupport;
import com.xc.touchbox.dao.CDAOTemplate;
import com.xc.touchbox.model.Goods;
import com.xc.touchbox.model.Product;

public interface ProductService extends CDAOTemplate{
	
	/**
	 * 分页查询产品列表数据
	 * 
	 * @param keyword 查询关键字
	 * @param statusInStr 状态字符串
	 * @param page 页码
	 * @param pagesize 一页多少条
	 * @return
	 */
	public PaginationSupport<Product> findProduct(String keyword,String statusInStr,int page, int pagesize);
	
	/**
	 * 保存产品数据
	 * 
	 * @param obj 产品对象
	 */
	public void saveProduct(Product obj);
	
	/**
	 * 获取指定产品的商品列表
	 * 
	 * @param productId
	 * @return
	 */
	public List<Goods> getGoodsList(long productId);
}
