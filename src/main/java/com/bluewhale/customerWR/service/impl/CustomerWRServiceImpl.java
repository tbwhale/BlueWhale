package com.bluewhale.customerWR.service.impl;

import com.bluewhale.common.excelwr.ExcelSheetFormat;
import com.bluewhale.common.excelwr.POIWriteReadExcel;
import com.bluewhale.common.excelwr.entity.ExcelHeaders;
import com.bluewhale.common.excelwr.entity.ExcelSheetPO;
import com.bluewhale.common.excelwr.entity.ExcelVersion;
import com.bluewhale.customerWR.entity.ItemAndWREntity;
import com.bluewhale.customerWR.service.CustomerWRService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 客户周报处理
 * @author liuketing
 * @date 2019年9月15日
 */
@Service
public class CustomerWRServiceImpl implements CustomerWRService {
	
	private static Logger logger = LoggerFactory.getLogger(CustomerWRServiceImpl.class);
	
	@Value("${bluewhale.customerWR.downloadPath}")
	private String downloadPath;

	/**
	 *  整合客户周报
	 * @param multipartFiles 上传excel文件数组
	 * @return Workbook
	 */
	@Override
	public String conformWeeklyInfoByMultipartFiles(MultipartFile[] multipartFiles) throws FileNotFoundException, IOException {
		Long startTimestamp = System.currentTimeMillis();
		logger.info("整合周报开始...");

		List<ItemAndWREntity> allWREntities = new ArrayList<ItemAndWREntity>();
		//循环获取multipartFiles数组中的文件
		for(int i = 0;i < multipartFiles.length;i++){
			MultipartFile multipartFile = multipartFiles[i];
			//读取文件数据
			if (!multipartFile.isEmpty()){
				try {
					//获取excel文件名
					String fileName = multipartFile.getOriginalFilename().toString();
					String item = ExcelSheetFormat.getItemByWRName(fileName);
					if (item == null || "".equals(item)) {
						continue;
					}
					InputStream inputStream = multipartFile.getInputStream();
					logger.info("读取周报信息，文件：{}",fileName);
					List<ExcelSheetPO> list = POIWriteReadExcel.readExcelByInputStream(inputStream, fileName,null, null);
					inputStream.close();
					boolean existFlag = false;
					if (!allWREntities.isEmpty()) {
						for (ItemAndWREntity entity : allWREntities) {
							if (item.equals(entity.getItem())) {
								existFlag = true;
								List<ExcelSheetPO> wRDataLists = entity.getWRDataLists();
								wRDataLists.addAll(list);
								entity.setWRDataLists(wRDataLists);
								break;
							}
						}
					}
					if (!existFlag) {
						ItemAndWREntity entity = new ItemAndWREntity();
						entity.setItem(item);
						entity.setWRDataLists(list);
						allWREntities.add(entity);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		//周报整合
		List<ExcelSheetPO> weeklyInfo = weeklyFormat(allWREntities);

		File downloads = new File(downloadPath);

		if (!downloads.exists()) {
			downloads.mkdirs();
		}
		String fileNameDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String filePathAndName = downloadPath+"/LI-HuaXia-PMC-项目周报-"+fileNameDate+".xlsx";
		logger.info("客户周报开始写入服务器磁盘...");
		POIWriteReadExcel.createWorkbookAtDisk(ExcelVersion.V2007, weeklyInfo, filePathAndName);
		logger.info("模块周报写入服务器磁盘成功！磁盘路径：{}",filePathAndName);
		logger.info("整合周报结束, cost {} million seconds",System.currentTimeMillis() - startTimestamp);
		return filePathAndName;
	}

	/**
	 * 根据所有模块周报组合成客户周报
	 * @param lists
	 * @return
	 */
	@Override
	public List<ExcelSheetPO> weeklyFormat(List<ItemAndWREntity> lists) {
		List<ExcelSheetPO> resultList = new ArrayList<ExcelSheetPO>();
		List<List<Object>> lastWeekdataLists = new ArrayList<List<Object>>();
		List<List<Object>> nextWeekdataLists = new ArrayList<List<Object>>();

		ExcelSheetPO lastWeekSheet = new ExcelSheetPO();
		lastWeekSheet.setSheetName("上周完成任务");
		lastWeekSheet.setTitle("上周已经完成的任务总结");
		lastWeekSheet.setHeaders(ExcelHeaders.customer_wr_this_week_mission);
		for (ItemAndWREntity itemAndWREntity : lists) {
			String item = itemAndWREntity.getItem();
			logger.equals(item);
			List<ExcelSheetPO> wRDataLists = itemAndWREntity.getWRDataLists();
			for (ExcelSheetPO excelSheetPO : wRDataLists) {
				String sheetname = excelSheetPO.getSheetName();
				if("上周工作内容".equals(sheetname)||"上周完成任务".equals(sheetname)){

					for (int i = 0; i < excelSheetPO.getDataList().size(); i++) {
						List<List<Object>> dataList = excelSheetPO.getDataList();
						List<Object> rowDataList = new ArrayList<Object>();
						//排除表头内容
						if(i==0 || i==1){
							continue;
						}
						//判断内容非空
						if (dataList.get(i).get(2) == null || "".equals(dataList.get(i).get(2))
								|| dataList.get(i).get(3) == null || "".equals(dataList.get(i).get(3))) {
							continue;
						}
						rowDataList.add(dataList.get(i).get(0));
						rowDataList.add(dataList.get(i).get(1));
						rowDataList.add(dataList.get(i).get(2));
						rowDataList.add(dataList.get(i).get(3).toString().replace("-","/"));
						rowDataList.add(dataList.get(i).get(4).toString().replace("-","/"));
						rowDataList.add(dataList.get(i).get(5));
						rowDataList.add(dataList.get(i).get(6));
						rowDataList.add("");
						rowDataList.add("");
						lastWeekdataLists.add(rowDataList);
					}
				}
			}
		}

		lastWeekSheet.setDataList(lastWeekdataLists);
		resultList.add(lastWeekSheet);

		ExcelSheetPO nextWeekPlanSheet = new ExcelSheetPO();
		nextWeekPlanSheet.setSheetName("本周计划任务");
		nextWeekPlanSheet.setTitle("本周一至五的工作计划安排");
		nextWeekPlanSheet.setHeaders(ExcelHeaders.customer_wr_next_week_mission);
		for (ItemAndWREntity itemAndWREntity : lists) {
			String item = itemAndWREntity.getItem();
			logger.equals(item);
			List<ExcelSheetPO> wRDataLists = itemAndWREntity.getWRDataLists();
			for (ExcelSheetPO excelSheetPO : wRDataLists) {
				String sheetname = excelSheetPO.getSheetName();
				if("下周工作计划".equals(sheetname)||"本周计划任务".equals(sheetname)){

					for (int i = 0; i < excelSheetPO.getDataList().size(); i++) {
						List<List<Object>> dataList = excelSheetPO.getDataList();
						List<Object> rowDataList = new ArrayList<Object>();
						//排除表头内容
						if(i==0 || i==1){
							continue;
						}
						//判断内容非空
						if (dataList.get(i).get(2) == null || "".equals(dataList.get(i).get(2))
								|| dataList.get(i).get(3) == null || "".equals(dataList.get(i).get(3))) {
							continue;
						}
						rowDataList.add(dataList.get(i).get(0));
						rowDataList.add(dataList.get(i).get(1));
						rowDataList.add(dataList.get(i).get(2));
						rowDataList.add(dataList.get(i).get(3).toString().replace("-","/"));
						rowDataList.add(dataList.get(i).get(4).toString().replace("-","/"));
						rowDataList.add(dataList.get(i).get(5));
						rowDataList.add(dataList.get(i).get(6));
						rowDataList.add("");
						nextWeekdataLists.add(rowDataList);
					}
				}
			}
		}
		nextWeekPlanSheet.setDataList(nextWeekdataLists);

		resultList.add(nextWeekPlanSheet);
		
		return resultList;
	}


}
