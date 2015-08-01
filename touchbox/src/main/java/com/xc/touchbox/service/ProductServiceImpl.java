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
import com.xc.touchbox.model.Goods;
import com.xc.touchbox.model.Product;

public class ProductServiceImpl extends CDAOTemplateImpl implements
		ProductService {

	/**
	 * org.apache.log4j.Logger对象log
	 */
	private static final Logger log = Logger
			.getLogger(ProductServiceImpl.class);

	@Override
	public PaginationSupport<Product> pageQuery(String keyword,
			String statusInStr, int page, int pagesize) {
		StringBuilder hql = new StringBuilder(
				"select a from Product a where a.status!=2 ");

		if (StringUtils.isNotEmpty(statusInStr)) {
			hql.append("and a.status in (").append(statusInStr).append(") ");
		}
		List params = new ArrayList();
		if (StringUtils.isNotEmpty(keyword)) {
			hql.append("and a.name like ? ");
			params.add("%" + keyword + "%");
		}

		int firstResults = (page - 1) * pagesize;
		return getHQLPagination(hql.toString(), params, firstResults, pagesize);
	}

	@Override
	public void save(Product obj) {
		Date date = new Date();

		try {
			if (obj.getProductId() == null) {
				obj.setCreateTime(date);
				getHibernateTemplate().save(obj);
			} else {
				Product dbObj = (Product) this.getHibernateTemplate().get(
						Product.class, obj.getProductId());
				BeanUtils.copyProperties(obj, dbObj,
						new String[] { "createTime" });
				dbObj.setUpdateTime(date);
				getHibernateTemplate().update(dbObj);
			}

			if (CollectionUtils.isNotEmpty(obj.getGoodsList())) {// 保存商品信息
				StringBuilder incGoodsIds = new StringBuilder();
				for (Goods o : obj.getGoodsList()) {
					o.setProductId(obj.getProductId());
					if (o.getStatus() > 0) {
						o.setUpdateTime(date);
						getHibernateTemplate().update(o);
					} else {
						o.setStatus(1);
						o.setCreateTime(date);
						getHibernateTemplate().save(o);
					}
					incGoodsIds.append("'").append(o.getGoodsId()).append("',");
				}
				incGoodsIds.deleteCharAt(incGoodsIds.length() - 1);

				getHibernateTemplate().bulkUpdate(
						"delete from Goods where status!=10 and productId=? and goodsId not in ("
								+ incGoodsIds + ")", obj.getProductId());
			}
		} catch (Exception e) {
			SimpleUtils.log(e, log);
		}

	}

	@Override
	public List<Goods> getGoodsList(long productId) {
		return getHibernateTemplate()
				.find("from Goods where status!=2 and productId=? order by periodNum asc",
						productId);
	}

	@Override
	public List<Product> findMajor() {
		return getHibernateTemplate().find("from Product a where a.status=1 and a.productCatId in (1,2,3) order by a.productCatId asc");
	}

	@Override
	public List<Product> findByCat(String catIdInstr) {
		return getHibernateTemplate().find("from Product a where a.status=1 and a.productCatId in ("+catIdInstr+") order by a.productId desc");
	}

}
