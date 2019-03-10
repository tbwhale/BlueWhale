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
		log.info("BlurWhale项目加载完毕！");
	}

	
}
