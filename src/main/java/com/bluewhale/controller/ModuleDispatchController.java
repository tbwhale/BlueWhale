package com.bluewhale.controller;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bluewhale.daily2weekly.service.impl.DailyToWeeklyServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bluewhale.common.util.Messages;
import com.bluewhale.daily2weekly.service.DailyToWeeklyService;

import javax.servlet.http.HttpServletResponse;

/**
 * 模块分发
 * @author  张晓睿
 * @version v0.1.0
 * @Date    创建时间   2019年3月25日 下午3:55:25
 */
@RestController
@RequestMapping("/module")
public class ModuleDispatchController {

	private static Logger logger = LoggerFactory.getLogger(ModuleDispatchController.class);

	@Value("${bluewhale.module.uploadPath}")
	private String uploadPath;
	@Value("${bluewhale.module.downloadPath}")
	private String downloadPath;
	@Autowired
	private DailyToWeeklyService dailyToWeeklyService;
	
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

	/**
	 * 上传excel文件并整合下载
	 * @param multipartFiles excel文件数组
	 * @param team 项目组
	 * @param startDate 整合开始日期
	 * @param endDate 整合结束日期
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/conformAndDownload")
	@ResponseBody
	public String conformAndDownload(@RequestParam("file")MultipartFile[] multipartFiles, String team, String startDate, String endDate) throws Exception {

		dailyToWeeklyService.conformWeeklyInfoByMultipartFiles(multipartFiles,team,startDate,endDate);

		return "200";
	}

	/**
	 * 下载excel文件
	 * @param response HttpServletResponse
	 * @param team 项目组
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/excelDownload")
	@ResponseBody
	public void excelDownload(HttpServletResponse response, String team) throws Exception {

		if ("批处理".equals(team)){
			downloadPath = downloadPath + "/BATCH";
		} else if ("新契约".equals(team)){
			downloadPath = downloadPath + "/UW";
		} else if ("续期".equals(team)){
			downloadPath = downloadPath + "/RN";
		} else if ("保全".equals(team)){
			downloadPath = downloadPath + "/POS";
		} else if ("理赔".equals(team)){
			downloadPath = downloadPath + "/CLAIM";
		}

		String fileNameDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String filename = "LI-HuaXia-PMC-项目周报-"+fileNameDate+"-"+team+".xlsx";
		String filePathAndName = downloadPath+"/"+filename;
		File file = new File(filePathAndName);
		if(file.exists()){
			logger.info("开始下载excel文件：{}",filePathAndName);
			response.addHeader("content-disposition", "attachment;filename="
					+ java.net.URLEncoder.encode(filename, "UTF-8"));

			OutputStream out = null;
			// 2.下载
			out = response.getOutputStream();

			// inputStream：读文件，前提是这个文件必须存在，要不就会报错
			InputStream is = new FileInputStream(file);

			byte[] b = new byte[4096];
			int size = is.read(b);
			while (size > 0) {
				out.write(b, 0, size);
				size = is.read(b);
			}
			out.close();
			is.close();
		}
	}

	/**
	 * 周报整合
	 * @param team 所属项目组
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/conform")
	@ResponseBody
	public Messages conformWR(@RequestParam("team")String team, @RequestParam("start")String start, @RequestParam("end")String end) throws Exception {
		Messages messages = new Messages();
		String errorMessage = null;
		messages.setMsgCode("200");
		messages.setMsgDesc("整合成功");
		try {
			String downloadPath = dailyToWeeklyService.conformWeeklyInfo(uploadPath,team,start,end);
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
