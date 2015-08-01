package com.xc.touchbox.thirdparty;

import com.xc.touchbox.model.UserDelivery;


public interface WareHousingService {
	
	/**
	 * 出库单同步接口(ShipmentRequest)
	 * 
	 * @param userDelivery 用户发货信息
	 * @return
	 */
	public boolean shipmentReuqest(UserDelivery userDelivery);
	
	/**
	 * 获取发货信息回传接口(GetShipments)
	 * 
	 * @param shipmentId 出库单号
	 * @return
	 */
	public String getShipments(String shipmentId);
	
	/**
	 * 出库单取消接口(ShipmentCancelRequest)
	 * 
	 * @param shipmentId 出库单号
	 * @param remark 备注
	 * @return
	 */
	public boolean shipmentCancelRequest(String shipmentId,String remark);


}
