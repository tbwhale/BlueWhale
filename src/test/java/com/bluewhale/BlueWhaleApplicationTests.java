package com.bluewhale;

import java.io.FileOutputStream;

import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlueWhaleApplicationTests {

	@Test
	public void contextLoads() {
		try {
			dropDownList42007("D:\\test.xlsx");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void dropDownList42007(String filePath)
	        throws Exception {
	    XSSFWorkbook workbook = new XSSFWorkbook();
	    XSSFSheet sheet = workbook.createSheet("下拉列表测试");
	    String[] datas = new String[] {"维持","恢复","调整"};
	    XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
	    XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
	            .createExplicitListConstraint(datas);
	    CellRangeAddressList addressList = null;
	    XSSFDataValidation validation = null;
	    
	        addressList = new CellRangeAddressList(1, 100, 0, 0);
	        validation = (XSSFDataValidation) dvHelper.createValidation(
	                dvConstraint, addressList);
	        // 07默认setSuppressDropDownArrow(true);
	        // validation.setSuppressDropDownArrow(true);
	        // validation.setShowErrorBox(true);
	        sheet.addValidationData(validation);
	    
	    FileOutputStream stream = new FileOutputStream(filePath);
	    workbook.write(stream);
	    stream.close();
	    addressList = null;
	    validation = null;
	}
}
