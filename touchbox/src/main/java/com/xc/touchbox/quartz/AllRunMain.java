package com.xc.touchbox.quartz;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 
 * @author James
 */
public class AllRunMain {
	private static ApplicationContext context = null;

	static {
		try {

			// 从项目的src目录下加载applicationContext.xml
			context = new ClassPathXmlApplicationContext(new String[] {
					"applicationContext.xml",
					"applicationContext-business.xml",
					"applicationContext-quartz.xml" });

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String log4jConfig = args[0];

			if (log4jConfig == null)
				BasicConfigurator.configure();
			else
				PropertyConfigurator.configure(log4jConfig);

			System.out.println("========= App log4j Starting [" + log4jConfig
					+ "] =========");
		} catch (Exception e) {
		}

		while (true) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
