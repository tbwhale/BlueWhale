package com.bluewhale.customerWR.entity;

import com.bluewhale.common.excelwr.entity.ExcelSheetPO;

import java.util.List;

/**
 * 项目组及其周报信息
 * @author  liuketing
 * @version v0.1.0
 * @Date    创建时间   2019年9月15日
 */
public class ItemAndWREntity {

	private String item;
	
	private List<ExcelSheetPO> wRDataLists;

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public List<ExcelSheetPO> getWRDataLists() {
		return wRDataLists;
	}

	public void setWRDataLists(List<ExcelSheetPO> wRDataLists) {
		this.wRDataLists = wRDataLists;
	}

	@Override
	public String toString() {
		return "PersonAndDailyEntity [item=" + item
				+ ", wRDataLists=" + wRDataLists + "]";
	}
	
}
