package com.bluewhale;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 
 * @ClassName BlueWhaleApplication
 * @author curtin
 * @Date 2019-03-10 12:28:46
 * @version v1.0.0
 */

@MapperScan("com.bluewhale.*.mybatis.mapper")
//@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@SpringBootApplication
public class BlueWhaleApplication {

	private static final Logger log = LoggerFactory.getLogger(BlueWhaleApplication.class);

	/**
	 * 启动入口
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		long startTimestamp = System.currentTimeMillis();
		log.info("BlueWhale项目开始加载...");
		SpringApplication.run(BlueWhaleApplication.class, args);
		log.info("\n" + "                  _oo0oo_  \n" 
				+ "                 o8888888o  \n"
				+ "                 88\" . \"88  \n" 
				+ "                 (| -_- |)  \n"
				+ "                 0\\  =  /0  \n" 
				+ "               ___/`---'\\___  \n"
				+ "             .' \\\\|     |// '.  \n" 
				+ "            / \\\\|||  :  |||// \\  \n"
				+ "           / _||||| -:- |||||- \\  \n" 
				+ "          |   | \\\\\\  -  /// |   |  \n"
				+ "          | \\_|  ''\\---/''  |_/ |  \n" 
				+ "          \\  .-\\__  '-'  ___/-. /  \n"
				+ "        ___'. .'  /--.--\\  `. .'___  \n" 
				+ "     .\"\" '<  `.___\\_<|>_/___.' >' \"\".  \n"
				+ "    | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |  \n" 
				+ "    \\  \\ `_.   \\_ __\\ /__ _/   .-` /  /  \n"
				+ "=====`-.____`.___ \\_____/___.-`___.-'=====  \n" 
				+ "                  `=---='  \n" + "\n"
				+ "~~~~~~~~~~~~~~~~~BlueWhale~~~~~~~~~~~~~~~~~  \n" 
				+ "          佛祖保佑         永无BUG  \n" + "\n"
				+ "     佛曰:  \n" 
				+ "            道路千万条，注释第一条；  \n" 
				+ "            代码不规范，亲人两行泪。  \n"
				+ "~~~~~~~~~~~~~~~~~BlueWhale~~~~~~~~~~~~~~~~~  \n" 
				+ "BlueWhale项目加载完毕！cost {} million seconds \n",System.currentTimeMillis() - startTimestamp);
	}

}
