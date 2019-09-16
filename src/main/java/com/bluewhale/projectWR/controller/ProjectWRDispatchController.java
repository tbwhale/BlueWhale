package com.bluewhale.projectWR.controller;

import com.bluewhale.common.util.Messages;
import com.bluewhale.daily2weekly.service.DailyToWeeklyService;
import com.bluewhale.projectWR.service.ProjectWRService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 项目周报操作
 * @author  liuketing
 * @version v0.1.0
 * @Date    创建时间   2019-9-15
 */
@RestController
@RequestMapping("/projectWR")
public class ProjectWRDispatchController {

	private static Logger logger = LoggerFactory.getLogger(ProjectWRDispatchController.class);

	@Value("${bluewhale.projectWR.uploadPath}")
	private String uploadPath;
	@Value("${bluewhale.projectWR.downloadPath}")
	private String downloadPath;
	@Autowired
	private ProjectWRService projectWRService;

	/**
	 * 上传excel文件并整合
	 * @param multipartFiles excel文件数组
	 * @return
	 */
	@PostMapping("/uploadAndConform")
	@ResponseBody
	public String conformAndDownload(@RequestParam("file")MultipartFile[] multipartFiles) {
		try {
			projectWRService.conformWeeklyInfoByMultipartFiles(multipartFiles);
		}catch (Exception e){
			e.printStackTrace();
			logger.error("上传Excel文件整合项目周报异常，异常信息："+e.getMessage());
		}
		return "200";
	}

	/**
	 * 下载excel文件
	 * @param response HttpServletResponse
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/excelDownload")
	@ResponseBody
	public void excelDownload(HttpServletResponse response) throws Exception {

		String fileNameDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String filename = "LI-华夏个险-PMC-周报-"+fileNameDate+".xlsx";
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
}
