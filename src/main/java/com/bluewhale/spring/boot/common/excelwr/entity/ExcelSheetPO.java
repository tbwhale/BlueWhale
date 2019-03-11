package com.bluewhale.spring.boot.common.excelwr.entity;

import java.util.Arrays;
import java.util.List;

/**
 * @author ZhangXr
 * @date   2019年3月1日
 */
public class ExcelSheetPO {
	/**
     * sheet的名称
     */
    private String sheetName;

 
    /**
     * 表格标题
     */
    private String title;

    /**
     * 头部标题集合
     */
    private String[] headers;

    /**
     * 数据集合
     */
    private List<List<Object>> dataList;

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getHeaders() {
		return headers;
	}

	public void setHeaders(String[] headers) {
		this.headers = headers;
	}

	public List<List<Object>> getDataList() {
		return dataList;
	}

	public void setDataList(List<List<Object>> dataList) {
		this.dataList = dataList;
	}

	@Override
	public String toString() {
		return "ExcelSheetPO [sheetName=" + sheetName + ", title=" + title
				+ ", headers=" + Arrays.toString(headers) + ", dataList="
				+ dataList + "]";
	}
    
	
}
