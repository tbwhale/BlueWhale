package com.bluewhale.common.excelwr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.bluewhale.common.excelwr.entity.ExcelStartEndDate;
import com.bluewhale.common.util.StringHelper;

/**
 * Excel-sheet格式化
 * @author  张晓睿
 * @version v0.1.0
 * @Date    创建时间   2019年4月12日 下午2:09:10
 */
public class ExcelSheetFormat {
	
	public static String sheetNameFormat(String sheetName) {
		
		if (sheetName.trim().length() > 2) {
			try {
				sheetName = new SimpleDateFormat("dd").format(new SimpleDateFormat("MM-dd").parse(sheetName));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (sheetName.trim().length() == 1) {
			sheetName = "0"+sheetName;
		}
		return sheetName;
		
	}
	
	public static String CheckName(String name,String start,String end) {
		if (name == null || "".equals(name)) {
			return null;
		}
		
		String[] split = StringHelper.qualifier(name).split("-");
		if (split.length != 3) {
			return null;
		}
		
		String startMonth = start.replace("-", "").substring(0, 6);
		String endMonth = end.replace("-", "").substring(0, 6);
		
		if (startMonth.equals(split[1]) || endMonth.equals(split[1])) {
			return split[2];
		}
		
		return null;
		
	}

	/**
	 * 获取当月最后一天
	 * @param date
	 * @return
	 */
	public static Date getLastdayofMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		Date lastDate = calendar.getTime();
		return lastDate;
	}
	
	/**
	 * 获取当月第一天
	 * @param date
	 * @return
	 */
	public static Date getFirstdayofMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date firstDate = calendar.getTime();
		return firstDate;
	}

	public static ExcelStartEndDate checkDate(String name,String start, String end) {
		if (name == null || "".equals(name)) {
			return null;
		}
		
		String[] split = StringHelper.qualifier(name).split("-");
		if (split.length != 3) {
			return null;
		}
		
		SimpleDateFormat monthFormat = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		ExcelStartEndDate startEndDate = new ExcelStartEndDate();
		try {
			Date startDate = dateFormat.parse(start);
			Date endDate = dateFormat.parse(end);
			Date lastDate = getLastdayofMonth(monthFormat.parse(split[1]));
			Date firstDate = getFirstdayofMonth(monthFormat.parse(split[1]));
			if (endDate.compareTo(lastDate) > 0) {
				endDate = lastDate;
			}
			if (startDate.compareTo(firstDate) < 0) {
				startDate = firstDate;
			}
			startEndDate.setStartDate(startDate);
			startEndDate.setEndDate(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return startEndDate;
	}
}
