package com.bluewhale.spring.boot;

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
	
	/**
	 * 启动入口
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(BlueWhaleApplication.class, args);
	}

	
}
