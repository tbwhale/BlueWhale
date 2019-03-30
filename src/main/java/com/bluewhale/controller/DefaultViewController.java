package com.bluewhale.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 锁定首页
 * @author  张晓睿
 * @version v0.1.0
 * @Date    创建时间   2019年3月19日 下午8:32:28
 */
@Configuration
public class DefaultViewController implements WebMvcConfigurer{

	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("index");
		WebMvcConfigurer.super.addViewControllers(registry);
	}

}
