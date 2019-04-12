package com.bluewhale.daily2weekly.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.bluewhale.common.excelwr.entity.ExcelSheetPO;
import com.bluewhale.daily2weekly.mybatis.entity.PersonAndDailyEntity;

/**
 * @author ZhangXr
 * @date 2019年3月24日
 * @time 上午11:36:32
 */
public interface DailyToWeeklyService {

	List<ExcelSheetPO> weeklyFormat(List<PersonAndDailyEntity> lists,String team,String start,String end);

	void conformWeeklyInfo(String uploadPath, String team, String start, String end) throws FileNotFoundException, IOException;

	List<ExcelSheetPO> dailyFormat(List<ExcelSheetPO> list, String start,
			String end);
}
