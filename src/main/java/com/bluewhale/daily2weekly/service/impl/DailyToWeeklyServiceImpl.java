package com.bluewhale.daily2weekly.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.bluewhale.common.excelwr.entity.ExcelSheetPO;
import com.bluewhale.daily2weekly.mybatis.entity.PersonAndDailyEntity;
import com.bluewhale.daily2weekly.service.DailyToWeeklyService;
import com.bluewhale.daily2weekly.temp.WeekTemp;

/**
 * @author ZhangXr
 * @date 2019年3月24日
 * @time 上午11:37:38
 */
public class DailyToWeeklyServiceImpl implements DailyToWeeklyService {
	
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
			List<List<ExcelSheetPO>> dailyDataLists = personAndDailyEntity.getDailyDataLists();
			for (List<ExcelSheetPO> dailyData : dailyDataLists) {
				
			}
			
		}
		
		
		return null;
		
	}
	
	/**
	 * 整理日报中有用的字段
	 * 字段顺序（需求号、任务类型、任务描述、工时）
	 * @param list
	 * @return
	 */
	public List<ExcelSheetPO> dailyFormat(List<ExcelSheetPO> list) {
		
		for (ExcelSheetPO excelSheetPO : list) {
			List<List<Object>> dataList = excelSheetPO.getDataList();
			List<List<Object>> resultDataList = new ArrayList<List<Object>>();
			for (List<Object> daily : dataList) {
				List<Object> weekly = new LinkedList<Object>();
				weekly.add(daily.get(6));
				weekly.add(daily.get(2));
				weekly.add(daily.get(3));
				weekly.add(daily.get(5));
				resultDataList.add(weekly);
			}
			excelSheetPO.setDataList(resultDataList);
		}
		
		return list;
	}
	
	
}
