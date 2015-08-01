import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.bcloud.msg.http.HttpSender;
import com.xc.gospel.core.util.SimpleUtils;

public class HttpSenderTest {
	/**
	 * org.apache.log4j.Logger对象log
	 */
	private static final Logger log = Logger
			.getLogger(HttpSenderTest.class);
	public static void main(String[] args) {
		boolean result = false;
		String uri = "http://222.73.117.158/msg/";//应用地址
		String account = "vipyyjy";//账号
		String pswd = "Tch123456";//密码
		String mobiles = "15921119416";//手机号码，多个号码使用","分割
		String content = "亲爱的用户，您的验证码是123456，5分钟内有效。";//短信内容
		boolean needstatus = true;//是否需要状态报告，需要true，不需要false
		String product = null;//产品ID
		String extno = null;//扩展码
		 
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
	}
}
