package com.xc.touchbox.thirdparty;

/**
 * 短信接口第三方服务
 * 
 * @author James
 *
 */
public interface SmsService {
	/**
	 * 下发短信内容
	 * 
	 * @param mobiles 手机号码，多个号码使用","分割
	 * @param content 短信内容
	 * @return
	 */
	public boolean sendSms(String mobiles, String content);

}
