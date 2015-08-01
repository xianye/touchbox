package com.xc.touchbox.quartz;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import org.apache.log4j.Logger;

import com.xc.gospel.core.util.SimpleUtils;
import com.xc.touchbox.service.UserDeliveryService;

public class AllQuartzJob {
	private static final Logger log = Logger.getLogger(AllQuartzJob.class);

	private UserDeliveryService userDeliveryService;
	private boolean buildFlag = false;
	private boolean sendFlag = false;
	private boolean clearFlag = false;
	private ConcurrentSkipListMap<Integer, Boolean> busiSendFlags = new ConcurrentSkipListMap<Integer, Boolean>();
	private Map<String, Date> businessInitTime = new HashMap<String, Date>(); // 业务初始化时间

	public void setUserDeliveryService(UserDeliveryService userDeliveryService) {
		this.userDeliveryService = userDeliveryService;
	}

	public void buildDeliveries() {
		log.info(SimpleUtils
				.appendFlag("AllQuartzJob.buildDeliveries[buildFlag:"
						+ buildFlag + "] is running."));
		if (buildFlag)
			return;

		try {
			buildFlag = true;
			// 构建发货单
			userDeliveryService.doBuildDeliveries(null);

			Thread.sleep(5000);
		} catch (Exception e) {
			SimpleUtils.log(e, log);
		} finally {
			buildFlag = false;
		}

	}

	public void sendDeliveries() {
		log.info(SimpleUtils.appendFlag("AllQuartzJob.sendDeliveries[sendFlag:"
				+ sendFlag + "] is running."));
		if (sendFlag)
			return;

		try {
			sendFlag = true;
			// 构建发货单
			userDeliveryService.doSendDeliveries();

			Thread.sleep(5000);
		} catch (Exception e) {
			SimpleUtils.log(e, log);
		} finally {
			sendFlag = false;
		}

	}

	public void clearPush() {
		log.info(SimpleUtils.appendFlag("PushQuartzJob.clearPush[clearFlag:"
				+ clearFlag + "] is running."));

		if (clearFlag)
			return;
		clearFlag = true;
		try {
			try {// 清除已删除数据
					// userDeliveryService.doClearInvalidMessage(1);
			} catch (Exception e) {
				SimpleUtils.log(e, log);
			}

			Thread.sleep(10000);
		} catch (Exception e) {
			SimpleUtils.log(e, log);
		} finally {
			clearFlag = false;
		}

	}
}
