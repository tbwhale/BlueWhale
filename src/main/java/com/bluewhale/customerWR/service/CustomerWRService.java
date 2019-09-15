package com.bluewhale.customerWR.service;

import com.bluewhale.common.excelwr.entity.ExcelSheetPO;
import com.bluewhale.customerWR.entity.ItemAndWREntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * 客户周报处理
 * @author liuketing
 * @date 2019年9月15日
 */
public interface CustomerWRService {

	String conformWeeklyInfoByMultipartFiles(MultipartFile[] multipartFiles) throws FileNotFoundException, IOException;

	List<ExcelSheetPO> weeklyFormat(List<ItemAndWREntity> lists);

}
