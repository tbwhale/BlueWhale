package com.bluewhale.spring.boot;

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
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BlueWhaleApplication {
	
	private static final Logger log = LoggerFactory.getLogger(BlueWhaleApplication.class);
	
	/**
	 * 启动入口
	 * @param args
	 */
	public static void main(String[] args) {
		log.info("BlurWhale项目开始加载...");
		SpringApplication.run(BlueWhaleApplication.class, args);
		log.info("BlurWhale项目加载完毕！"+"\n");
		log.info("\n"+
				 "                  _oo0oo_"+"\n"+
				 "                 o8888888o"+"\n"+
				 "                 88\" . \"88"+"\n"+
				 "                 (| -_- |)"+"\n"+
				 "                 0\\  =  /0"+"\n"+
				 "               ___/`---'\\___"+"\n"+
				 "             .' \\\\|     |// '."+"\n"+
				 "            / \\\\|||  :  |||// \\"+"\n"+
				 "           / _||||| -:- |||||- \\"+"\n"+
				 "          |   | \\\\\\  -  /// |   |"+"\n"+
				 "          | \\_|  ''\\---/''  |_/ |"+"\n"+
				 "          \\  .-\\__  '-'  ___/-. /"+"\n"+
				 "        ___'. .'  /--.--\\  `. .'___"+"\n"+
				 "     .\"\" '<  `.___\\_<|>_/___.' >' \"\"."+"\n"+
				 "    | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |"+"\n"+
				 "    \\  \\ `_.   \\_ __\\ /__ _/   .-` /  /"+"\n"+
				 "=====`-.____`.___ \\_____/___.-`___.-'====="+"\n"+
				 "                  `=---='"+"\n"+
				 ""+"\n"+
				 "~~~~~~~~~~~~~~~~~BlueWhale~~~~~~~~~~~~~~~~~"+"\n"+
				 "          佛祖保佑         永无BUG"+"\n"+
				 ""+"\n"+
				 "     佛曰:"+"\n"+
				 "         写字楼里写字间，写字间里程序员；"+"\n"+
				 "         程序人员写程序，又拿程序换酒钱。"+"\n"+
				 "         酒醒只在网上坐，酒醉还来网下眠；"+"\n"+
				 "         酒醉酒醒日复日，网上网下年复年。"+"\n"+
				 "         但愿老死电脑间，不愿鞠躬老板前；"+"\n"+
				 "         奔驰宝马贵者趣，公交自行程序员。"+"\n"+
				 "         别人笑我忒疯癫，我笑自己命太贱；"+"\n"+
				 "         不见满街漂亮妹，哪个归得程序员？"+"\n"+
				 "~~~~~~~~~~~~~~~~~BlueWhale~~~~~~~~~~~~~~~~~"
		);
	}

	
}
