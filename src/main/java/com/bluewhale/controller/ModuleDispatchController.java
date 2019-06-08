package com.bluewhale.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bluewhale.common.excelwr.POIWriteReadExcel;
import com.bluewhale.common.excelwr.entity.ExcelSheetPO;
import com.bluewhale.common.excelwr.entity.ExcelVersion;
import com.bluewhale.common.util.Messages;
import com.bluewhale.daily2weekly.service.DailyToWeeklyService;

/**
 * 模块分发
 * @author  张晓睿
 * @version v0.1.0
 * @Date    创建时间   2019年3月25日 下午3:55:25
 */
@RestController
@RequestMapping("/module")
public class ModuleDispatchController {

	@Value("${bluewhale.module.uploadPath}")
	private String uploadPath;
	@Value("${bluewhale.module.downloadPath}")
	private String downloadPath;
	@Autowired
	private DailyToWeeklyService service;
	
	@RequestMapping("/excelWR")
	public ModelAndView moduleHome() {
		ModelAndView mView = new ModelAndView("module/test");
		return mView;
	}
	@RequestMapping("/weekconfirm")
	public ModelAndView weekconfirm() {
		ModelAndView mView = new ModelAndView("module/module");
		return mView;
	}
	@PostMapping("/upload")
	@ResponseBody
	public String upload(MultipartFile file) {
		if (file == null) {
			return "400";
		}
		
		File dirFile = new File(uploadPath);
		
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		
		String filename = file.getOriginalFilename();
		File serverFile = new File(uploadPath + filename);
		
		try {
			file.transferTo(serverFile);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			return "400";
		}
		
		return "200";
	}
	
	@RequestMapping("/conform")
	@ResponseBody
	public Messages batchWR(@RequestParam("team")String team, @RequestParam("start")String start, @RequestParam("end")String end) throws Exception {
		Messages messages = new Messages();
		String errorMessage = null;
		messages.setMsgCode("200");
		messages.setMsgDesc("整合成功");
		try {
			String downloadPath = service.conformWeeklyInfo(uploadPath,team,start,end);
			messages.setMsgDesc("整合成功，文件路径："+downloadPath);
		} catch (Exception e) {
			e.printStackTrace();
			Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            e.printStackTrace(printWriter);
            errorMessage = e.getMessage();
		}
		
		if (errorMessage != null) {
			messages.setMsgCode("500");
			messages.setMsgDesc("整合异常，异常原因如下："+errorMessage);
		}
		
		return messages;
	}
}
