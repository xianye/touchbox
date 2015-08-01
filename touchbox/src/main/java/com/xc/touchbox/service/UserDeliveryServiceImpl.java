package com.xc.touchbox.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.xc.gospel.core.util.SimpleUtils;
import com.xc.gospel.core.vo.PaginationSupport;
import com.xc.touchbox.dao.CDAOTemplateImpl;
import com.xc.touchbox.model.Goods;
import com.xc.touchbox.model.ProductSnapshot;
import com.xc.touchbox.model.User;
import com.xc.touchbox.model.UserDelivery;
import com.xc.touchbox.model.UserOrder;
import com.xc.touchbox.thirdparty.WareHousingService;

public class UserDeliveryServiceImpl extends CDAOTemplateImpl implements
		UserDeliveryService {

	/**
	 * org.apache.log4j.Logger对象log
	 */
	private static final Logger log = Logger
			.getLogger(UserDeliveryServiceImpl.class);

	private WareHousingService wareHousingService;

	public void setWareHousingService(WareHousingService wareHousingService) {
		this.wareHousingService = wareHousingService;
	}

	@Override
	public PaginationSupport<UserDelivery> findDelivery(String keyword,
			String statusInStr, String startTime, String endTime, int page,
			int pagesize) {
		StringBuilder hql = new StringBuilder(
				"select a from UserDelivery a where a.status!=2 ");

		if (StringUtils.isNotEmpty(statusInStr)) {
			hql.append("and a.status in (").append(statusInStr).append(") ");
		}
		List params = new ArrayList();
		if (StringUtils.isNotEmpty(keyword)) {
			hql.append("and (a.itemName like ? or a.goodsId=?) ");
			params.add("%" + keyword + "%");
			params.add(keyword);
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
	public void saveDelivery(UserDelivery obj) {
		Date date = new Date();

		try {
			if (obj.getId() == null) {
				obj.setCreateTime(date);
				getHibernateTemplate().save(obj);
			} else {
				UserDelivery dbObj = (UserDelivery) this.getHibernateTemplate()
						.get(UserDelivery.class, obj.getId());
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
	public void doBuildDeliveries(Date endTime) {
		Date date = new Date();

		String monthFirstDayStr = DateFormatUtils.format(date, "yyyy-MM")
				+ "-01";
		Date monthFirstDay = java.sql.Date.valueOf(monthFirstDayStr);
		Date monthLastDay = DateUtils.addDays(
				DateUtils.addMonths(monthFirstDay, 1), -1);

		Map<Long, ProductSnapshot> snapshotMap = new HashMap<Long, ProductSnapshot>();
		Map<Long, Map<Integer, Goods>> productGoods = new HashMap<Long, Map<Integer, Goods>>();
		// 已支付及已发货订单处理
		StringBuilder hql = new StringBuilder(
				"select a from UserOrder a where a.status in (1,3) and a.orderPeriod>a.deliveredPeriodNum and (a.deliveredTime is null or a.deliveredTime<'"
						+ monthFirstDayStr + "') order by orderId asc");
		List<UserOrder> list = findHQL(hql.toString(), null, 500);

		Map<Integer, List<Long>> orderIdsMap = new HashMap<Integer, List<Long>>();

		for (UserOrder o : list) {
			User user = o.getUser();

			UserDelivery a = new UserDelivery();
			a.setOrderId(o.getOrderId());
			a.setUserId(user.getUserId());
			a.setMobile(o.getParentMobile());
			a.setAddress(user.getAddress());
			a.parseAddress();
			a.setStatus(0);
			a.setCreateTime(date);
			ProductSnapshot b = getProductSnapshot(snapshotMap,
					o.getSnapshotId());
			a.setWareHouse(b.getDeliveryWarehouse());
			Date lastTime = b.getStartTime();
			if (o.getDeliveredPeriodNum() > 0) {
				lastTime = DateUtils.addMonths(lastTime,
						o.getDeliveredPeriodNum());
			}
			if (monthFirstDay.compareTo(lastTime) >= 0) {// 需要生成发货单
				int nextPeriodNum = o.getDeliveredPeriodNum();
				Map<Integer, Goods> goodsMap = getGoodsMap(productGoods,
						b.getProductId());

				while (monthFirstDay.compareTo(lastTime) >= 0
						&& o.getEndTime().before(lastTime)) {
					nextPeriodNum++;

					Goods goods = goodsMap.get(nextPeriodNum);
					a.setGoodsId(goods.getGoodsId());
					a.setItemName(goods.getName());
					a.setItemCount(o.getNumber());

					UserDelivery target = new UserDelivery();
					BeanUtils.copyProperties(a, target);

					getHibernateTemplate().save(target);

					lastTime = DateUtils.addMonths(lastTime, 1);
				}
				List<Long> orderIds = orderIdsMap.get(nextPeriodNum);
				if (orderIds == null) {
					orderIds = new ArrayList<Long>();
				}
				orderIds.add(o.getOrderId());
			}
		}

		for (Integer key : orderIdsMap.keySet()) {
			getHibernateTemplate()
					.bulkUpdate(
							"update UserOrder set deliveredPeriodNum=?,deliveredTime=?,updateTime=? where orderId in ("
									+ StringUtils.join(orderIdsMap.get(key),
											",") + ")",
							new Object[] { key, date, date });
		}
	}

	@Override
	public void doSendDeliveries() {
		StringBuilder hql = new StringBuilder(
				"select a from UserDelivery a where a.status=0 and a.wareHouse='SH' order by a.id asc");
		List<UserDelivery> list = findHQL(hql.toString(), null, 1000);
		StringBuilder deliveryIds = new StringBuilder();
		for (UserDelivery a : list) {
			boolean deliverySuccess = wareHousingService.shipmentReuqest(a);
			if (deliverySuccess) {
				deliveryIds.append(a.getId()).append(",");
			}
		}
		if (deliveryIds.length() > 0) {
			deliveryIds.deleteCharAt(deliveryIds.length() - 1);
			getHibernateTemplate().bulkUpdate(
					"update UserDelivery set status=1 where id in ("
							+ deliveryIds + ")");
		}
	}

}
