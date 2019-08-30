package com.bluewhale.daily2weekly.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bluewhale.common.excelwr.ExcelSheetFormat;
import com.bluewhale.common.excelwr.POIWriteReadExcel;
import com.bluewhale.common.excelwr.entity.ExcelSheetPO;
import com.bluewhale.common.excelwr.entity.ExcelStartEndDate;
import com.bluewhale.common.excelwr.entity.ExcelVersion;
import com.bluewhale.daily2weekly.mybatis.entity.PersonAndDailyEntity;
import com.bluewhale.daily2weekly.service.DailyToWeeklyService;
import com.bluewhale.daily2weekly.temp.WeekTemp;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ZhangXr
 * @date 2019年3月24日
 * @time 上午11:37:38
 */
@Service
public class DailyToWeeklyServiceImpl implements DailyToWeeklyService {
	
	private static Logger logger = LoggerFactory.getLogger(DailyToWeeklyServiceImpl.class);
	
	@Value("${bluewhale.module.downloadPath}")
	private String downloadPath;

	/**
	 *  整合周报
	 * @param multipartFiles 上传excel文件数组
	 * @param team 项目组
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return Workbook
	 */
	@Override
	public String conformWeeklyInfoByMultipartFiles(MultipartFile[] multipartFiles, String team, String startDate, String endDate) throws FileNotFoundException, IOException {
		Long startTimestamp = System.currentTimeMillis();
		logger.info("整合周报开始，参数：[项目组：{}，整合日期范围：{}至{}]",team,startDate,endDate);

		List<PersonAndDailyEntity> allDailyEntities = new ArrayList<PersonAndDailyEntity>();
		//循环获取multipartFiles数组中的文件
		for(int i = 0;i < multipartFiles.length;i++){
			MultipartFile multipartFile = multipartFiles[i];
			//读取文件数据
			if (!multipartFile.isEmpty()){
				try {
					//获取excel文件名
					String fileName = multipartFile.getOriginalFilename().toString();
					String personname = ExcelSheetFormat.CheckName(fileName, startDate, endDate);
					if (personname == null || "".equals(personname)) {
						continue;
					}
					ExcelStartEndDate checkDate = ExcelSheetFormat.checkDate(fileName,startDate,endDate);
					InputStream inputStream = multipartFile.getInputStream();
					logger.info("读取日报信息，文件：{}",fileName);
					List<ExcelSheetPO> readExcel = POIWriteReadExcel.readExcelByInputStream(inputStream, fileName,null, null);
					inputStream.close();
					List<ExcelSheetPO> list = dailyFormat(readExcel,checkDate.getStartDate(),checkDate.getEndDate());
					boolean existFlag = false;
					if (!allDailyEntities.isEmpty()) {
						for (PersonAndDailyEntity entity : allDailyEntities) {
							if (personname.equals(entity.getPersonName())) {
								existFlag = true;
								List<ExcelSheetPO> dailyDataLists = entity.getDailyDataLists();
								dailyDataLists.addAll(list);
								entity.setDailyDataLists(dailyDataLists);
								break;
							}
						}
					}
					if (!existFlag) {
						PersonAndDailyEntity entity = new PersonAndDailyEntity();
						entity.setPersonName(personname);
						entity.setDailyDataLists(list);
						allDailyEntities.add(entity);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		//周报整合
		List<ExcelSheetPO> weeklyInfo = weeklyFormat(allDailyEntities, team, startDate, endDate);
		Workbook workbook = null;
//		Workbook workbook = POIWriteReadExcel.createWorkbook(ExcelVersion.V2007,weeklyInfo);
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

		File downloads = new File(downloadPath);

		if (!downloads.exists()) {
			downloads.mkdirs();
		}

		String fileNameDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String filePathAndName = downloadPath+"/LI-HuaXia-PMC-项目周报-"+fileNameDate+"-"+team+".xlsx";

		POIWriteReadExcel.createWorkbookAtDisk(ExcelVersion.V2007, weeklyInfo, filePathAndName);
		logger.info("整合周报结束!cost {} million seconds",System.currentTimeMillis() - startTimestamp);
		return filePathAndName;
	}

	/**
	 *  整合周报
	 * @param uploadPath 上传路径
	 * @param team 项目组
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return String 整合生成文件路径及文件名
	 */
	@Override
	public String conformWeeklyInfo(String uploadPath, String team, String start, String end) throws FileNotFoundException, IOException {
		
		logger.info("整合周报开始");
		
		List<PersonAndDailyEntity> allDailyEntities = new ArrayList<PersonAndDailyEntity>();
		String readpath = uploadPath;
		File dirFile = new File(readpath);
		File[] listFiles = dirFile.listFiles();
		for (int i = 0; i < listFiles.length; i++) {
			String name = listFiles[i].getName();
			String personname = ExcelSheetFormat.CheckName(name, start, end);
			if (personname == null || "".equals(personname)) {
				continue;
			}
			ExcelStartEndDate checkDate = ExcelSheetFormat.checkDate(name,start,end);
			List<ExcelSheetPO> readExcel = POIWriteReadExcel.readExcel(readpath+name, null, null);
			List<ExcelSheetPO> list = dailyFormat(readExcel,checkDate.getStartDate(),checkDate.getEndDate());
			boolean existFlag = false;
			if (!allDailyEntities.isEmpty()) {
				for (PersonAndDailyEntity entity : allDailyEntities) {
					if (personname.equals(entity.getPersonName())) {
						existFlag = true;
						List<ExcelSheetPO> dailyDataLists = entity.getDailyDataLists();
						dailyDataLists.addAll(list);
						entity.setDailyDataLists(dailyDataLists);
						break;
					}
				}
			} 
			if (!existFlag) {
				PersonAndDailyEntity entity = new PersonAndDailyEntity();
				entity.setPersonName(personname);
				entity.setDailyDataLists(list);
				allDailyEntities.add(entity);
			}
		}
		//周报整合
		List<ExcelSheetPO> weeklyInfo = weeklyFormat(allDailyEntities, team, start, end);
		
		File downloads = new File(downloadPath);
		
		if (!downloads.exists()) {
			downloads.mkdirs();
		}
		
		String fileNameDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String filePathAndName = downloads+"\\LI-HuaXia-PMC-项目周报-"+fileNameDate+"-"+team+".xlsx";
		
		POIWriteReadExcel.createWorkbookAtDisk(ExcelVersion.V2003, weeklyInfo, filePathAndName);
		
		logger.info("整合周报结束");
		return filePathAndName;
	}

	/**
	 * 根据开始日期和结束日期将所有日报组合成模块周报
	 * @param lists
	 * @param team
	 * @param start
	 * @param end
	 * @return
	 */
	@Override
	public List<ExcelSheetPO> weeklyFormat(List<PersonAndDailyEntity> lists,String team,String start,String end) {
		List<ExcelSheetPO> resultList = new ArrayList<ExcelSheetPO>();
		List<List<Object>> dataLists = new ArrayList<List<Object>>();
		ExcelSheetPO lastWeekSheet = new ExcelSheetPO();
		lastWeekSheet.setSheetName("上周工作内容");
		lastWeekSheet.setTitle("上周已经完成的任务总结");
		lastWeekSheet.setHeaders(WeekTemp.team_headers);
		for (PersonAndDailyEntity personAndDailyEntity : lists) {
			String personName = personAndDailyEntity.getPersonName();
			logger.equals(personName);
			List<ExcelSheetPO> dailyDataLists = personAndDailyEntity.getDailyDataLists();
			for (ExcelSheetPO excelSheetPO : dailyDataLists) {
				for (int i = 0; i < excelSheetPO.getDataList().size(); i++) {
					List<List<Object>> dataList = excelSheetPO.getDataList();
					List<Object> rowDataList = new ArrayList<Object>();
					if (dataList.get(i).get(2) == null || "".equals(dataList.get(i).get(2))
							|| dataList.get(i).get(3) == null || "".equals(dataList.get(i).get(3))) {
						continue;
					}
					rowDataList.add(dataList.get(i).get(0));
					rowDataList.add(dataList.get(i).get(1));
					rowDataList.add(dataList.get(i).get(2));
					rowDataList.add(start);
					rowDataList.add(end);
					double worktime = Double.parseDouble(String.valueOf(dataList.get(i).get(3)));
					rowDataList.add(worktime/8);
					rowDataList.add(personName);
					rowDataList.add("");
					rowDataList.add("");
					dataLists.add(rowDataList);
				}
			}
		}
		lastWeekSheet.setDataList(dataLists);
		ExcelSheetPO nextWeekPlanSheet = new ExcelSheetPO();
		nextWeekPlanSheet.setSheetName("下周工作计划");
		nextWeekPlanSheet.setTitle("本周一至五的工作计划安排");
		nextWeekPlanSheet.setHeaders(WeekTemp.next_week_plan);
		
		resultList.add(lastWeekSheet);
		resultList.add(nextWeekPlanSheet);
		
		return resultList;
		
	}
	
	/**
	 * 整理日报中有用的字段
	 * 字段顺序（需求号、任务类型、任务描述、工时）
	 * @param list
	 * @return
	 */
	@Override
	public List<ExcelSheetPO> dailyFormat(List<ExcelSheetPO> list,String start,String end) {
		
		List<ExcelSheetPO> resultDaily = new ArrayList<ExcelSheetPO>();
		
		for (ExcelSheetPO excelSheetPO : list) {
			String sheetname = ExcelSheetFormat.sheetNameFormat(excelSheetPO.getSheetName());
			if (Integer.parseInt(sheetname) < Integer.parseInt(start) || 
					Integer.parseInt(sheetname) > Integer.parseInt(end)) {
				continue;
			}
			List<List<Object>> dataList = excelSheetPO.getDataList();
			List<List<Object>> resultDataList = new ArrayList<List<Object>>();
			for (int i = 1; i < dataList.size(); i++) {
				List<Object> weekly = new LinkedList<Object>();
				if (dataList.get(i).get(3) == null || "".equals(dataList.get(i).get(3))) {
					continue;
				}
				weekly.add(dataList.get(i).get(6));
				weekly.add(dataList.get(i).get(2));
				weekly.add(dataList.get(i).get(3));
				weekly.add(dataList.get(i).get(5));
				resultDataList.add(weekly);
			}
			
			excelSheetPO.setDataList(resultDataList);
			resultDaily.add(excelSheetPO);
		}
		
		return resultDaily;
	}

}
