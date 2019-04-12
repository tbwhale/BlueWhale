package com.bluewhale.common.excelwr.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日报提取的起始日期与终止日期
 * @author  张晓睿
 * @version v0.1.0
 * @Date    创建时间   2019年4月12日 下午5:40:54
 */
public class ExcelStartEndDate {

	private String startDate;
	
	private String endDate;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = new SimpleDateFormat("dd").format(startDate);
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = new SimpleDateFormat("dd").format(endDate);
	}
	
	
}
