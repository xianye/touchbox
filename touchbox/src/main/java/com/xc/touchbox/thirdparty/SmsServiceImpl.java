package com.xc.touchbox.thirdparty;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.bcloud.msg.http.HttpSender;
import com.xc.gospel.core.util.SimpleUtils;
import com.xc.touchbox.model.ISysParam;

public class SmsServiceImpl implements SmsService {
	/**
	 * org.apache.log4j.Logger对象log
	 */
	private static final Logger log = Logger
			.getLogger(SmsServiceImpl.class);
	
	@Override
	public boolean sendSms(String mobiles, String content) {
		boolean result = false;
		String uri = ISysParam.API_SMS_INTERFACE_HTTPURL;// 应用地址
		String account = ISysParam.API_SMS_INTERFACE_USERNAME;// 账号
		String pswd = ISysParam.API_SMS_INTERFACE_PASSWORD;// 密码
		boolean needstatus = true;// 是否需要状态报告，需要true，不需要false
		String product = null;// 产品ID
		String extno = null;// 扩展码
		StringBuilder logBuilder = new StringBuilder(
				"SmsService.sendSms({mobiles:'");
		logBuilder.append(mobiles).append("',content:'").append(content)
				.append("'});returnString:");
		try {
			String returnString = HttpSender.batchSend(uri, account, pswd,
					mobiles, content, needstatus, product, extno);
			if (StringUtils.isNotEmpty(returnString)) {
				logBuilder.append("'").append(returnString.replace("\n", "#")).append("'");
				String[] a = returnString.split("\n");
				if (a.length > 1) {
					String[] b = a[0].split(",");
					result = b.length == 2 && "0".equals(b[1]);
					if(result){
						logBuilder.append(";result:").append(result);
					}
				}
			}
		} catch (Exception e) {
			// TODO 处理异常
			SimpleUtils.log(e, log);
			logBuilder.append(";exception:").append(e.getMessage());
		}finally{
			log.info(SimpleUtils.appendFlag(logBuilder.toString()));
		}

		return result;
	}

}
