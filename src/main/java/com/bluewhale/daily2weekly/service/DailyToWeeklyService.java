package com.bluewhale.daily2weekly.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.bluewhale.common.excelwr.entity.ExcelSheetPO;
import com.bluewhale.daily2weekly.mybatis.entity.PersonAndDailyEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ZhangXr
 * @date 2019年3月24日
 * @time 上午11:36:32
 */
public interface DailyToWeeklyService {

	String conformWeeklyInfoByMultipartFiles(MultipartFile[] multipartFiles, String team, String startDate, String endDate) throws FileNotFoundException, IOException;

	List<ExcelSheetPO> weeklyFormat(List<PersonAndDailyEntity> lists,String team,String start,String end);

	String conformWeeklyInfo(String uploadPath, String team, String start, String end) throws FileNotFoundException, IOException;

	List<ExcelSheetPO> dailyFormat(List<ExcelSheetPO> list, String start,
			String end);
}
