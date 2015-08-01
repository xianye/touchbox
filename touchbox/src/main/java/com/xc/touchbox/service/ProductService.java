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
	public PaginationSupport<Product> pageQuery(String keyword,String statusInStr,int page, int pagesize);
	
	/**
	 * 保存产品数据
	 * 
	 * @param obj 产品对象
	 */
	public void save(Product obj);
	
	/**
	 * 获取指定产品的商品列表
	 * 
	 * @param productId
	 * @return
	 */
	public List<Goods> getGoodsList(long productId);
	
	/**
	 * 查询主要的盒子产品，用于商城首页显示
	 * 各主要分类一个数据
	 * 
	 * @return
	 */
	public List<Product> findMajor();
	
	/**
	 * 查询指定分类的盒子产品数据
	 * 
	 * @param catIdInstr 分类Id，多个使用“,”分隔
	 * @return
	 */
	public List<Product> findByCat(String catIdInstr);
}
