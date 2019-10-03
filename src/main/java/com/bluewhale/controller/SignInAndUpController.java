package com.bluewhale.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignInAndUpController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SignInAndUpController.class);
	
	@RequestMapping("/signIn")
	public String signIn() {
		
		return "";
	}
	
	@RequestMapping("/signUp")
	public String signUp() {
		
		return "";
	}
	
	@RequestMapping("/forgot")
	public String forgot() {
		
		return "";
	}
}
