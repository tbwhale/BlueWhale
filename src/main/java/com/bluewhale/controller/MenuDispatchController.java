package com.bluewhale.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * 模块分发
 * @author  liuketing
 * @version v0.1.0
 * @Date    创建时间   2019-9-15
 */
@RestController
//@RequestMapping("/menu")
public class MenuDispatchController {

//	private static Logger logger = LoggerFactory.getLogger(MenuDispatchController.class);

	//菜单页面
	@RequestMapping("/menu")
	public ModelAndView moduleHome() {
		ModelAndView mView = new ModelAndView("menu/menu");
		return mView;
	}

	//跳转写日报页面
	@RequestMapping("/writeDailyNewspaper")
	public ModelAndView writeDailyNewspaper() {
		ModelAndView mView = new ModelAndView("dailyNewspaper/writeDailyNewspaper");
		return mView;
	}


	//跳转模块周报页面
	@RequestMapping("/moduleWR")
	public ModelAndView moduleWR() {
		ModelAndView mView = new ModelAndView("module/moduleWR");
		return mView;
	}

	//跳转客户周报页面
	@RequestMapping("/customerWR")
	public ModelAndView customerWR() {
		ModelAndView mView = new ModelAndView("customerWR/customerWR");
		return mView;
	}

	//跳转项目周报页面
	@RequestMapping("/projectWR")
	public ModelAndView weekconfirm() {
		ModelAndView mView = new ModelAndView("projectWR/projectWR");
		return mView;
	}
	
	//跳转项目周报页面
	@RequestMapping("/config")
	public ModelAndView config() {
		ModelAndView mView = new ModelAndView("config/config");
		return mView;
	}
		
	//跳转项目周报页面
	@RequestMapping("/contact")
	public ModelAndView contact() {
		ModelAndView mView = new ModelAndView("contact/contact");
		return mView;
	}

}
