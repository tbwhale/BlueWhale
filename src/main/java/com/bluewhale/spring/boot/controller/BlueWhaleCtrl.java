package com.bluewhale.spring.boot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author curtin
 * @Date 2019-03-10 12:09:44
 */
@RestController
//@Controller
public class BlueWhaleCtrl {
	private static final Logger log=LoggerFactory.getLogger(BlueWhaleCtrl.class);
	
	@GetMapping(value = "/hello")
	public String hello() {
		log.info("访问了controller");
		return "Hello BlueWhale";
	}
	

	@GetMapping(value = "/index")
	public ModelAndView index() {
		log.info("访问了index");
		ModelAndView modelAndView = new ModelAndView("index");
		return modelAndView;
	}
	
}
