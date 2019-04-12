package com.bluewhale.daily2weekly.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bluewhale.common.excelwr.ExcelSheetFormat;
import com.bluewhale.common.excelwr.POIWriteReadExcel;
import com.bluewhale.common.excelwr.entity.ExcelSheetPO;
import com.bluewhale.common.excelwr.entity.ExcelStartEndDate;
import com.bluewhale.daily2weekly.mybatis.entity.PersonAndDailyEntity;
import com.bluewhale.daily2weekly.service.DailyToWeeklyService;
import com.bluewhale.daily2weekly.temp.WeekTemp;

/**
 * @author ZhangXr
 * @date 2019年3月24日
 * @time 上午11:37:38
 */
@Service
public class DailyToWeeklyServiceImpl implements DailyToWeeklyService {
	
	@Override
	public void conformWeeklyInfo(String uploadPath, String team, String start, String end) throws FileNotFoundException, IOException {
		List<PersonAndDailyEntity> allDailyEntities = new ArrayList<PersonAndDailyEntity>();
		
		File dirFile = new File(uploadPath);
		File[] listFiles = dirFile.listFiles();
		for (int i = 0; i < listFiles.length; i++) {
			String name = listFiles[i].getName();
			String personname = ExcelSheetFormat.CheckName(name, start, end);
			if (personname == null || "".equals(personname)) {
				continue;
			}
			ExcelStartEndDate checkDate = ExcelSheetFormat.checkDate(name,start,end);
			List<ExcelSheetPO> readExcel = POIWriteReadExcel.readExcel(uploadPath+name, null, null);
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
		
		System.out.println(allDailyEntities);
//		weeklyFormat(allDailyEntities, team, start, end);
	}
	
	@Override
	public List<ExcelSheetPO> weeklyFormat(List<PersonAndDailyEntity> lists,String team,String start,String end) {
		List<ExcelSheetPO> resultList = new ArrayList<ExcelSheetPO>();
		List<List<Object>> dataLists = new ArrayList<List<Object>>();
		
		ExcelSheetPO lastWeekSheet = new ExcelSheetPO();
		lastWeekSheet.setSheetName("上周工作内容");
		lastWeekSheet.setTitle("上周已经完成的任务总结");
		if ("batch".equals(team)) {
			lastWeekSheet.setHeaders(WeekTemp.batch_team_headers);
		}
		for (PersonAndDailyEntity personAndDailyEntity : lists) {
			String personName = personAndDailyEntity.getPersonName();
			List<ExcelSheetPO> dailyDataLists = personAndDailyEntity.getDailyDataLists();
			
			
		}
		
		
		return null;
		
	}
	
	/**
	 * 整理日报中有用的字段
	 * 字段顺序（需求号、任务类型、任务描述、工时）
	 * @param list
	 * @return
	 */
	@Override
	public List<ExcelSheetPO> dailyFormat(List<ExcelSheetPO> list,String start,String end) {
		
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
				weekly.add(dataList.get(i).get(6));
				weekly.add(dataList.get(i).get(2));
				weekly.add(dataList.get(i).get(3));
				weekly.add(dataList.get(i).get(5));
				resultDataList.add(weekly);
			}
			
			excelSheetPO.setDataList(resultDataList);
		}
		
		return list;
	}

	
	
	
}
