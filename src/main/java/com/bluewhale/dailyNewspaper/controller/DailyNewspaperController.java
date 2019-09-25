package com.bluewhale.dailyNewspaper.controller;

import com.bluewhale.customerWR.service.CustomerWRService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


/**
 * 写日报操作
 * @author curtin 2019-9-21
 */
@RestController
@RequestMapping("/dailyNewspaper")
public class DailyNewspaperController {

	private static Logger logger = LoggerFactory.getLogger(DailyNewspaperController.class);

	@Value("${bluewhale.customerWR.uploadPath}")
	private String uploadPath;
	@Value("${bluewhale.customerWR.downloadPath}")
	private String downloadPath;
	@Autowired
	private CustomerWRService customerWRService;

	/**
	 * 写日报
	 * @param response HttpServletResponse
	 * @return
	 */
	@PostMapping("/dailyNewspaperSubmit")
	@ResponseBody
	public String conformAndDownload(HttpServletResponse response) {
		try {
			logger.info("日报提交");
		}catch (Exception e){
			e.printStackTrace();
			logger.error("上传Excel文件整合客户周报异常，异常信息："+e.getMessage());
		}
		return "200";
	}



}
