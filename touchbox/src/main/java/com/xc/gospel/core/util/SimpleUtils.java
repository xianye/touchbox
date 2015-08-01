package com.xc.gospel.core.util;

import java.io.UnsupportedEncodingException;
import java.lang.Character.UnicodeBlock;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Random;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class SimpleUtils {

	/**
	 * org.apache.log4j.Logger对象log
	 */
	private static final Logger log = Logger.getLogger(SimpleUtils.class);

	// ////////常量start

	/**
	 * 日志输出普通格式
	 */
	public static final int LOG_FORMAT_NORMAL = 0;

	/**
	 * 日志输出漂亮的格式
	 */
	public static final int LOG_FORMAT_PRETTY = 1;

	public static final String SYS_ENCODING = System
			.getProperty("sun.jnu.encoding");
	public static final String SYS_TMPDIR = System
			.getProperty("java.io.tmpdir");

	private static char[] HEXCHAR = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 在进制表示中的字符集合。
	 */
	private final static char[] digits = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
			'x', 'y', 'z' };

	public static final BASE64Encoder BASE64Encoder = new sun.misc.BASE64Encoder();
	public static final BASE64Decoder BASE64Decoder = new sun.misc.BASE64Decoder();

	private static ThreadLocal tflags = new ThreadLocal() {
		public Object initialValue() {
			Random rand = new Random();
			return String.valueOf(System.currentTimeMillis())
					+ rand.nextInt(10) + rand.nextInt(10) + rand.nextInt(10);
		}
	};

	public static String currentFlag() {
		return (String) tflags.get();
	}

	public static String appendFlag(String str) {
		return "[" + currentFlag() + "] " + str;
	}

	public static void log(Exception e, Logger log) {
		log(e, log, LOG_FORMAT_NORMAL);
	}

	/**
	 * 输出异常日志
	 * 
	 * @param e
	 * @param log
	 */
	public static void log(Exception e, Logger log, int format) {
		e.printStackTrace();
		StringBuffer sb = new StringBuffer(e.toString());
		StackTraceElement[] ste = e.getStackTrace();
		if (ste != null) {
			for (int i = 0; i < ste.length; i++) {
				switch (format) {
				case LOG_FORMAT_PRETTY:
					sb.append("\n\tat ");
					break;
				default:
					sb.append("\\n\\tat ");
				}
				sb.append(ste[i].toString());
			}
		}
		log.error(appendFlag(sb.toString()));
	}

	public static void log(Exception e, Log log) {
		log(e, log, LOG_FORMAT_NORMAL);
	}

	public static void log(Exception e, Log log, int format) {
		e.printStackTrace();
		System.out.println();
		StringBuffer sb = new StringBuffer(e.toString());
		StackTraceElement[] ste = e.getStackTrace();
		if (ste != null) {
			for (int i = 0; i < ste.length; i++) {
				switch (format) {
				case LOG_FORMAT_PRETTY:
					sb.append("\n\tat ");
					break;
				default:
					sb.append("\\n\\tat ");
				}
				sb.append(ste[i].toString());
			}
		}
		log.error(appendFlag(sb.toString()));
	}

	/**
	 * 字符串编码转换
	 * 
	 * @param source
	 *            源字符串
	 * @param srcCode
	 *            源编码
	 * @param extCode
	 *            目的编码
	 * @return
	 */
	public static String stringConvert(String source, String srcCode,
			String extCode) {
		try {
			return new String(source.getBytes(srcCode), extCode);
		} catch (UnsupportedEncodingException e) {
			log(e, log);
		}
		return "";
	}

	/**
	 * 
	 * 将多行内容转换为一行
	 * 
	 * @param content
	 *            内容
	 * @param ignoreLinebreak
	 *            是否忽略换行符
	 * @return
	 */
	public static String toOneLine(String content, boolean ignoreLinebreak) {
		content = StringUtils.trimToEmpty(content);
		if (ignoreLinebreak) {
			content = content.replaceAll("\\r\\n", "");
			content = content.replaceAll("\\n", "");
		} else {
			content = content.replaceAll("\\r\\n", "\\\\r\\\\n");
			content = content.replaceAll("\\n", "\\\\n");
		}
		return content;
	}

	/**
	 * 
	 * 将内容显示在一行
	 * 
	 * @param content
	 *            内容
	 * @param ignoreLinebreak
	 *            是否忽略换行符
	 * @return
	 * @deprecated 废弃不再使用,请使用toOneLine方法
	 */
	public static String showAtOneLine(String content, boolean ignoreLinebreak) {
		return toOneLine(content, ignoreLinebreak);
	}

	public static String printCharsets(String key, String content) {
		try {
			if (content != null) {
				System.out.println(key + "[normal] : " + content);
				System.out.println(key + "[iso8859_1] : "
						+ URLEncoder.encode(content, "iso8859_1"));
				System.out.println(key + "[gbk] : "
						+ URLEncoder.encode(content, "gbk"));
				System.out.println(key + "[utf-8] : "
						+ URLEncoder.encode(content, "utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * 从Unicode内码转变为中英文及符号
	 * 
	 * @param param
	 *            Unicode内码字符串参数
	 * @return 转码后的中文
	 */
	public static String escapeUnicodeToGB(String param) {
		return escapeUnicodeToGB(param, true);
	}

	public static String escapeUnicodeToGB(String param, boolean isCheck) {
		boolean isChange = true;
		if (isCheck) {
			isChange = param.matches(".*&#\\d+;.*");
		}
		if (isChange) {
			log.debug("Before uniToGB.param[" + param + "]");
			String startString = "&#", endString = ";";
			int startIndex = 0, endIndex = 0;
			StringBuilder sb = new StringBuilder();
			while (startIndex != -1) {
				startIndex = param.indexOf(startString, endIndex);
				if (startIndex != -1) {
					if (startIndex > endIndex) {
						if (endIndex == 0) {
							sb.append(param.substring(0, startIndex));
						} else {
							sb.append(param.substring(
									endIndex + endString.length(), startIndex));
						}
					}
				} else {
					if (endIndex + endString.length() < param.length()) {
						sb.append(param.substring(endIndex + endString.length()));
						break;
					}
				}
				endIndex = param.indexOf(endString, startIndex);
				String tempStr = param.substring(
						startIndex + startString.length(), endIndex);
				if (tempStr.matches("\\d+")) {
					int tempInt = Integer.parseInt(tempStr);
					sb.append((char) tempInt);
				} else {
					sb.append(startString + tempStr + endString);
				}
				if (endIndex + endString.length() == param.length()) {
					break;
				}
			}
			log.debug("After uniToGB.result[" + sb + "]");
			return sb.toString();
		} else {
			return param;
		}
	}

	/**
	 * 返回中英文及符号的Unicode内码
	 * 
	 * @param param
	 *            中英文及符号字符串参数
	 * @return Unicode内码
	 */
	public static String gbToEscapeUnicode(String param) {
		if (param != null) {
			log.debug("Before gbToUni.param[" + param + "]");
			StringBuilder sb = new StringBuilder();
			char[] c = param.toCharArray();
			String tempUni;
			for (int i = 0; i < c.length; i++) {
				if (c[i] >= 32 && c[i] <= 126) {// 大小写字母、数字、空格以及符号不转换
					tempUni = String.valueOf(c[i]);
				} else {
					tempUni = "&#" + (int) c[i] + ";";
				}
				sb.append(tempUni);
			}
			log.debug("After gbToUni.result[" + sb + "]");
			return sb.toString();
		}
		return param;
	}

	/**
	 * MD5 编码
	 * 
	 * @param origin
	 * @return
	 */
	public static String MD5Encode(String origin) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byte2hex(md.digest(resultString.getBytes()));
		} catch (Exception ex) {
			log(ex, log);
		}
		return resultString;
	}

	/**
	 * 
	 * 二行制转字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEXCHAR[(b[i] & 0xf0) >>> 4]);
			sb.append(HEXCHAR[b[i] & 0x0f]);
		}
		return sb.toString();
	}

	/**
	 * 十六进制字符串 转二进制
	 * 
	 * @param s
	 * @return
	 */
	public static byte[] hex2byte(String s) {
		byte[] bytes;
		bytes = new byte[s.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2),
					16);
		}
		return bytes;
	}

	/**
	 * 将十进制的数字转换为指定进制的字符串。
	 * 
	 * @param i
	 *            十进制的数字。
	 * @param system
	 *            指定的进制，常见的2/8/16。
	 * @return 转换后的字符串。
	 */
	public static String toCustomNumericString(long i, int system) {
		long num = 0;
		if (i < 0) {
			num = ((long) 2 * 0x7fffffff) + i + 2;
		} else {
			num = i;
		}
		char[] buf = new char[32];
		int charPos = 32;
		while ((num / system) > 0) {
			buf[--charPos] = digits[(int) (num % system)];
			num /= system;
		}
		buf[--charPos] = digits[(int) (num % system)];
		return new String(buf, charPos, (32 - charPos));
	}

	/**
	 * 将其它进制的数字（字符串形式）转换为十进制的数字。
	 * 
	 * @param s
	 *            其它进制的数字（字符串形式）
	 * @param system
	 *            指定的进制，常见的2/8/16。
	 * @return 转换后的数字。
	 */
	public static long toCustomNumeric(String s, int system) {
		char[] buf = new char[s.length()];
		s.getChars(0, s.length(), buf, 0);
		long num = 0;
		for (int i = 0; i < buf.length; i++) {
			for (int j = 0; j < digits.length; j++) {
				if (digits[j] == buf[i]) {
					num += j * Math.pow(system, buf.length - i - 1);
					break;
				}
			}
		}
		return num;
	}

	/**
	 * 
	 * 　　从上面的代码中可以看出，由于受到digits字符数量的限制，现在能够实现的最大自定义进制只有36进制，如果想要构造更大的进制表示，
	 * 我们可以扩充digits中的字符。当然不能自己构造一个一进制、零进制的数字。<br>
	 * 　　下面的一个方法是JDK中的标准类库提供的方法，它在性能上有很大的提高，毕竟用到了位运算，而不是简单的比较。 <br>
	 * 
	 * 由于用到了位移操作，所以它的进制表示只是局限于2/4/8/16/32，其它的就不能实现了。当shift为1时，表示二进制；当shift为2时，
	 * 表示四进制；依次类推。
	 * 
	 * @param i
	 * @param shift
	 * @return
	 */
	public static String toUnsignedString(long i, int shift) {
		char[] buf = new char[32];
		int charPos = 32;
		int radix = 1 << shift;
		int mask = radix - 1;
		do {
			buf[--charPos] = digits[(int) (i & mask)];
			i >>>= shift;
		} while (i != 0);
		return new String(buf, charPos, (32 - charPos));
	}

	/**
	 * 汉字转换位汉语拼音首字母，英文字符不变
	 * 
	 * @param chStr
	 *            汉字
	 * @return 拼音
	 */
	public static String getFirstSpell(String chStr) {
		String pinyinName = "";
		char[] nameChar = chStr.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {
				try {
					pinyinName += PinyinHelper.toHanyuPinyinStringArray(
							nameChar[i], defaultFormat)[0].charAt(0);
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				} catch (Exception e) {
					log.error("getFirstSpell(" + chStr + ") error.", e);
				}
			} else {
				pinyinName += nameChar[i];
			}
		}
		return pinyinName;
	}

	/**
	 * 汉字转换位汉语拼音，英文字符不变
	 * 
	 * @param chStr
	 *            汉字
	 * @return 拼音
	 */
	public static String getFullSpell(String chStr) {
		String pinyinName = "";
		char[] nameChar = chStr.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {
				try {
					pinyinName += PinyinHelper.toHanyuPinyinStringArray(
							nameChar[i], defaultFormat)[0];
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				} catch (Exception e) {
					log.error("getFullSpell(" + chStr + ") error.", e);
				}
			} else {
				pinyinName += nameChar[i];
			}
		}
		return pinyinName;
	}

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param str
	 * 
	 * 
	 * @return String
	 */

	public static String gbk2Unicode(String str) {

		StringBuffer result = new StringBuffer();

		for (int i = 0; i < str.length(); i++) {

			char chr1 = (char) str.charAt(i);

			if (!isNeedConvert(chr1)) {

				result.append(chr1);

				continue;

			}

			result.append("\\u" + Integer.toHexString((int) chr1));

		}

		return result.toString();

	}

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param dataStr
	 * 
	 * 
	 * @return String
	 */

	public static String unicode2gbk(String dataStr) {

		int index = 0;

		StringBuffer buffer = new StringBuffer();

		int li_len = dataStr.length();

		while (index < li_len) {

			if (index >= li_len - 1

			|| !"\\u".equals(dataStr.substring(index, index + 2))) {

				buffer.append(dataStr.charAt(index));

				index++;

				continue;

			}

			String charStr = "";

			charStr = dataStr.substring(index + 2, index + 6);

			char letter = (char) Integer.parseInt(charStr, 16);

			buffer.append(letter);

			index += 6;

		}

		return buffer.toString();

	}

	private static boolean isNeedConvert(char para) {
		return ((para & (0x00FF)) != para);
	}

	/**
	 * 
	 * 
	 * utf-8 转unicode
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param inStr
	 * 
	 * 
	 * @return String
	 */

	public static String utf8ToUnicode(String inStr) {
		char[] myBuffer = inStr.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < inStr.length(); i++) {

			UnicodeBlock ub = UnicodeBlock.of(myBuffer[i]);

			if (ub == UnicodeBlock.BASIC_LATIN) {

				sb.append(myBuffer[i]);

			} else if (ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {

				int j = (int) myBuffer[i] - 65248;

				sb.append((char) j);

			} else {

				short s = (short) myBuffer[i];

				String hexS = Integer.toHexString(s);

				String unicode = "\\u" + hexS;

				sb.append(unicode.toLowerCase());

			}

		}

		return sb.toString();

	}

	/**
	 * 
	 * 
	 * 
	 * 
	 * 
	 * @param theString
	 * 
	 * 
	 * @return String
	 */

	public static String unicodeToUtf8(String theString) {

		char aChar;

		int len = theString.length();

		StringBuffer outBuffer = new StringBuffer(len);

		for (int x = 0; x < len;) {

			aChar = theString.charAt(x++);

			if (aChar == '\\') {

				aChar = theString.charAt(x++);

				if (aChar == 'u') {

					// Read the xxxx

					int value = 0;

					for (int i = 0; i < 4; i++) {

						aChar = theString.charAt(x++);

						switch (aChar) {

						case '0':

						case '1':

						case '2':

						case '3':

						case '4':

						case '5':

						case '6':

						case '7':

						case '8':

						case '9':

							value = (value << 4) + aChar - '0';

							break;

						case 'a':

						case 'b':

						case 'c':

						case 'd':

						case 'e':

						case 'f':

							value = (value << 4) + 10 + aChar - 'a';

							break;

						case 'A':

						case 'B':

						case 'C':

						case 'D':

						case 'E':

						case 'F':

							value = (value << 4) + 10 + aChar - 'A';

							break;

						default:

							throw new IllegalArgumentException(

							"Malformed   \\uxxxx   encoding.");

						}

					}

					outBuffer.append((char) value);

				} else {

					if (aChar == 't')

						aChar = '\t';

					else if (aChar == 'r')

						aChar = '\r';

					else if (aChar == 'n')

						aChar = '\n';

					else if (aChar == 'f')

						aChar = '\f';

					outBuffer.append(aChar);

				}

			} else

				outBuffer.append(aChar);

		}

		return outBuffer.toString();

	}

	/**
	 * gbk字符转utf8
	 * 
	 * @param gbk
	 * @return
	 */
	public static String gbk2utf8(String gbk) {
		String l_temp = gbk2Unicode(gbk);
		l_temp = unicodeToUtf8(l_temp);
		return l_temp;
	}

	public static String utf82gbk(String utf) {
		String l_temp = utf8ToUnicode(utf);
		l_temp = unicode2gbk(l_temp);
		return l_temp;
	}

	/**
	 * 获取源字符串中随机digit位字符
	 * 
	 * @param source 源字符串
	 * @param digit 位数
	 * @return
	 */
	public static String getRandomString(String source,int digit){
		char[] charArr = digits;
		if(StringUtils.isNotEmpty(source)){
			charArr = source.toCharArray();
		}
		StringBuilder target = new StringBuilder();
		for(int i=0;i<digit;i++){
			target.append(charArr[RandomUtils.nextInt(charArr.length)]);
		}

		return target.toString();
	}
}
