package com.bluewhale.spring.boot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author curtin
 * @Date 2019-03-10 12:09:44
 */
@RestController
public class BlueWhaleCtrl {
	
	@RequestMapping(value = "/hello")
	public String index() {
		return "Hello BlueWhale";
	}
	
}
