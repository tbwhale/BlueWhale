package com.bluewhale.spring.boot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author curtin
 * @Date 2019-03-10 12:09:44
 */
@RestController
public class BlueWhaleCtrl {
	private static final Logger log=LoggerFactory.getLogger(BlueWhaleCtrl.class);
	
	@RequestMapping(value = "/hello")
	public String index() {
		log.info("访问了controller");
		return "Hello BlueWhale";
	}
	
}
