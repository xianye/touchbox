package com.xc.touchbox.thirdparty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.log4j.Logger;

import com.xc.gospel.core.util.SimpleUtils;
import com.xc.touchbox.model.ISysParam;
import com.xc.touchbox.model.UserDelivery;

public class WareHousingServiceImpl implements WareHousingService {

	/**
	 * org.apache.log4j.Logger对象log
	 */
	private static final Logger log = Logger
			.getLogger(WareHousingServiceImpl.class);

	@Override
	public boolean shipmentReuqest(UserDelivery userDelivery) {
		// TODO Auto-generated method stub
		RequestConfig defaultRequestConfig = RequestConfig.custom()
				.setSocketTimeout(60000).setConnectTimeout(60000)
				.setConnectionRequestTimeout(60000)
				.setStaleConnectionCheckEnabled(true).build();
		// 创建默认的httpClient实例.
		CloseableHttpClient httpClient = HttpClients.createDefault();
		httpClient = HttpClients.custom()
				.setDefaultRequestConfig(defaultRequestConfig).build();

		// 创建httppost
		HttpPost httpPost = new HttpPost(
				ISysParam.API_WAREHOUSING_CHINAWAY_HTTPURL);
		httpPost.setHeader("Message-From", "TTX");
		httpPost.setHeader("Subject", "ShipmentRequest");
		httpPost.setHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");

		String reqText = userDelivery.toShipmentRequest("xml");
		StringEntity entity = new StringEntity(reqText, "UTF-8");
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/xml");
		httpPost.setEntity(entity);

		log.info(SimpleUtils.appendFlag("ReqText:" + reqText));
		try {
			HttpResponse resp = httpClient.execute(httpPost);

			String line;
			BufferedReader br = new BufferedReader(new InputStreamReader(resp
					.getEntity().getContent(), "UTF-8"));

			StringBuffer respBuffer = new StringBuffer();
			while ((line = br.readLine()) != null) {
				respBuffer.append(line);
			}
			// 响应文本
			String respText = respBuffer.toString();

			StringBuilder respLog = new StringBuilder("RespText:" + respText);

			log.info(SimpleUtils.appendFlag(respLog.toString()));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public String getShipments(String shipmentId) {
		// TODO Auto-generated method stub

		// 创建默认的httpClient实例.
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httpPost = new HttpPost(
				ISysParam.API_WAREHOUSING_CHINAWAY_HTTPURL);
		httpPost.setHeader("Message-From", "TTX");
		httpPost.setHeader("Subject", "GetShipments");
		httpPost.setHeader("CustomerID",
				ISysParam.API_WAREHOUSING_CHINAWAY_COMPANY);
		httpPost.setHeader("WareHouse",
				ISysParam.API_WAREHOUSING_CHINAWAY_WAREHOUSE);
		httpPost.setHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");

		try {
			HttpResponse resp = httpClient.execute(httpPost);

			String line;
			BufferedReader br = new BufferedReader(new InputStreamReader(resp
					.getEntity().getContent(), "UTF-8"));

			StringBuffer respBuffer = new StringBuffer();
			while ((line = br.readLine()) != null) {
				respBuffer.append(line);
			}
			// 响应文本
			String respText = respBuffer.toString();

			StringBuilder respLog = new StringBuilder("RespText:" + respText);

			log.info(SimpleUtils.appendFlag(respLog.toString()));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean shipmentCancelRequest(String shipmentId, String remark) {
		// TODO Auto-generated method stub

		// 创建默认的httpClient实例.
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httpPost = new HttpPost(
				ISysParam.API_WAREHOUSING_CHINAWAY_HTTPURL);
		httpPost.setHeader("Message-From", "TTX");
		httpPost.setHeader("Subject", "ShipmentCancelRequest");
		httpPost.setHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");

		StringBuilder reqText = new StringBuilder("<ShipmentCancelRequest>");
		reqText.append("<Company>")
				.append(ISysParam.API_WAREHOUSING_CHINAWAY_COMPANY)
				.append("</Company>");
		reqText.append("<WareHouse>")
				.append(ISysParam.API_WAREHOUSING_CHINAWAY_WAREHOUSE)
				.append("</WareHouse>");
		reqText.append("<ShipmentId>").append(shipmentId)
				.append("</ShipmentId>");
		if (StringUtils.isNotEmpty(remark)) {
			reqText.append("<Remark>").append(remark).append("</Remark>");
		}
		reqText.append("</ShipmentCancelRequest>");

		StringEntity entity = new StringEntity(reqText.toString(), "UTF-8");
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/xml");
		httpPost.setEntity(entity);

		log.info(SimpleUtils.appendFlag("ReqText:" + reqText));
		try {
			HttpResponse resp = httpClient.execute(httpPost);

			String line;
			BufferedReader br = new BufferedReader(new InputStreamReader(resp
					.getEntity().getContent(), "UTF-8"));

			StringBuffer respBuffer = new StringBuffer();
			while ((line = br.readLine()) != null) {
				respBuffer.append(line);
			}
			// 响应文本
			String respText = respBuffer.toString();

			StringBuilder respLog = new StringBuilder("RespText:" + respText);

			log.info(SimpleUtils.appendFlag(respLog.toString()));
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public static void main(String[] args) {

		WareHousingService service = new WareHousingServiceImpl();
		UserDelivery a = new UserDelivery();
		a.setCompany(ISysParam.API_WAREHOUSING_CHINAWAY_COMPANY);
		a.setWareHouse(ISysParam.API_WAREHOUSING_CHINAWAY_WAREHOUSE);
		a.setShipmentId("TB" + System.currentTimeMillis());
		a.setShipmentType("销售出库");
		a.setCarrier("ZTO");
		a.setName("Rio");
		a.setMobile("18616205593");
		a.setState("上海市");
		a.setCity("上海市");
		a.setAddress("上海市徐汇区中山西路2025号永升大厦925室");
		a.setGoodsId("6925878410017");
		a.setItemName("3-1产品");

		service.shipmentReuqest(a);//TB1437465233525,TB1437465807022,TB1437468372670,TB1437471536067
		//service.getShipments("TB1437468372670");
		//service.shipmentCancelRequest("TB1437468372670", null);
	}

}
