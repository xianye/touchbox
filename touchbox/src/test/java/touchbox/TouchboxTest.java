package touchbox;

import com.xc.gospel.core.util.SimpleUtils;

public class TouchboxTest {

	public static void main(String[] args){
		
		String url = "http://localhost:8080/yuqing-api/port.action";
		url = "http://180.153.135.186:2180/hljxhs_api/port.action";
		//url = "http://192.168.1.53:8380/yuqing-api/query.action";
		String authkeyStr = "c=2003&username=13900000000&password=123456";
		String myUrl = url + "?" + authkeyStr;
		String authkey = SimpleUtils.MD5Encode(SimpleUtils
				.MD5Encode(authkeyStr).toUpperCase() + "ya9me8");
		myUrl += "&page=1&authkey=" + authkey;

		System.out.println(myUrl);
		
		//SimpleHttpUtils.getText(myUrl, "UTF-8");

		System.out.println(SimpleUtils.MD5Encode("t" + "123456"));

	}
}
