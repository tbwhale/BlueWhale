package com.bluewhale.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bluewhale.common.excelwr.POIWriteReadExcel;
import com.bluewhale.common.excelwr.entity.ExcelSheetPO;
import com.bluewhale.common.excelwr.entity.ExcelVersion;

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
	
	@RequestMapping("/moduleHome")
	public ModelAndView moduleHome() {
		System.out.println("----");
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
		File dirFile2 = new File(downloadPath);
		if (!dirFile.exists()) {
			dirFile.mkdir();
		}
		if (!dirFile2.exists()) {
			dirFile2.mkdir();
		}
		String filename = file.getOriginalFilename();
		File serverFile = new File(uploadPath + filename);
		String outFilename = "批处理.xlsx";
		try {
			file.transferTo(serverFile);
			List<ExcelSheetPO> readExcel = POIWriteReadExcel.readExcel(uploadPath+serverFile.getName(), 10, 10);
			System.out.println(readExcel.toString());
			POIWriteReadExcel.createWorkbookAtDisk(ExcelVersion.V2003, readExcel, downloadPath+outFilename);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			return "400";
		}
		
		
		
		return "200";
	}
}
