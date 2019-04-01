package com.bluewhale.daily2weekly.mybatis.entity;

import java.util.List;

import com.bluewhale.common.excelwr.entity.ExcelSheetPO;

/**
 * 用户及其日报信息
 * @author  张晓睿
 * @version v0.1.0
 * @Date    创建时间   2019年3月26日 下午8:26:06
 */
public class PersonAndDailyEntity {

	private String personName;
	
	private List<List<ExcelSheetPO>> dailyDataLists;

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public List<List<ExcelSheetPO>> getDailyDataLists() {
		return dailyDataLists;
	}

	public void setDailyDataLists(List<List<ExcelSheetPO>> dailyDataLists) {
		this.dailyDataLists = dailyDataLists;
	}

	@Override
	public String toString() {
		return "PersonAndDailyEntity [personName=" + personName
				+ ", dailyDataLists=" + dailyDataLists + "]";
	}
	
}
