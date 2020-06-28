package com.bluewhale.common.excelwr;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.*;

import com.bluewhale.common.excelwr.entity.ExcelSheetPO;
import com.bluewhale.common.excelwr.entity.ExcelVersion;
import com.bluewhale.common.util.FileUtil;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTDataValidation;


/**
 * POI读写Excel工具类
 * @author 张晓睿
 * @version 创建时间   2019年3月11日 下午6:11:08
 */
public class POIWriteReadExcel {
	
	//标题样式
    private final static String STYLE_HEADER = "header";
    //表头样式
    private final static String STYLE_TITLE = "title";
    //数据样式
    private final static String STYLE_DATA = "data";
    //长文本样式
    private final static String STYLE_LONGDATA = "longData";
    
    //存储样式
    private static final HashMap<String, CellStyle> cellStyleMap = new HashMap<String, CellStyle>();

    /**
     * 读取excel文件
     * @param file 文件名（含后缀名）
     * @param rowCount Excel要读取行数 可为空
     * @param columnCount Excel要读取列数 可为空
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
	public static List<ExcelSheetPO> readExcel(String file,Integer rowCount,Integer columnCount) {
		// 根据后缀名称判断excel的版本
        String extName = FileUtil.getFileExtName(file);
        Workbook wb = null;
        if (ExcelVersion.V2003.getSuffix().equals(extName)) {
            try {
                wb = new HSSFWorkbook(new FileInputStream(file));
            }catch (Exception e){
                e.printStackTrace();
                try {
                    wb = new XSSFWorkbook(new FileInputStream(file));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (ExcelVersion.V2007.getSuffix().equals(extName)) {
            try {
                wb = new XSSFWorkbook(new FileInputStream(file));
            }catch (Exception e){
                e.printStackTrace();
                try {
                    wb = new HSSFWorkbook(new FileInputStream(file));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            // 无效后缀名称，这里之能保证excel的后缀名称，不能保证文件类型正确，不过没关系，在创建Workbook的时候会校验文件格式
            throw new IllegalArgumentException("Invalid excel version");
        }
        // 开始读取数据
        List<ExcelSheetPO> sheetPOs = new ArrayList<ExcelSheetPO>();
        // 解析sheet
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            Sheet sheet = wb.getSheetAt(i);
            List<List<Object>> dataList = new ArrayList<List<Object>>();
            ExcelSheetPO sheetPO = new ExcelSheetPO();
            sheetPO.setSheetName(sheet.getSheetName());
            sheetPO.setDataList(dataList);
            int readRowCount = 0;
            if (rowCount == null || rowCount > sheet.getPhysicalNumberOfRows()) {
                readRowCount = sheet.getPhysicalNumberOfRows();
            } else {
                readRowCount = rowCount;
            }
            // 解析sheet 的行
            for (int j = sheet.getFirstRowNum(); j < readRowCount; j++) {
                Row row = sheet.getRow(j);
                if (row == null) {
                    continue;
                }
                if (row.getFirstCellNum() < 0) {
                    continue;
                }
                int readColumnCount = 0;
                if (columnCount == null || columnCount > row.getLastCellNum()) {
                    readColumnCount = (int) row.getLastCellNum();
                } else {
                    readColumnCount = columnCount;
                }
                List<Object> rowValue = new LinkedList<Object>();
                // 解析sheet 的列
                for (int k = 0; k < readColumnCount; k++) {
                    Cell cell = row.getCell(k);
                    rowValue.add(getCellValue(wb, cell));
                }
                dataList.add(rowValue);
            }
            sheetPOs.add(sheetPO);
        }
        return sheetPOs;
    
	}

    /**
     * 读取excel文件
     * @param inputStream InputStream
     * @param fileName 文件名
     * @param rowCount Excel要读取行数 可为空
     * @param columnCount Excel要读取列数 可为空
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static List<ExcelSheetPO> readExcelByInputStream(InputStream inputStream,String fileName, Integer rowCount, Integer columnCount) {
        // 根据后缀名称判断excel的版本
        String extName = FileUtil.getFileExtName(fileName);

        Workbook wb = null;
        FileInputStream fileInputStream = (FileInputStream)inputStream ;
        if (ExcelVersion.V2003.getSuffix().equals(extName)) {
            try {
                wb = new HSSFWorkbook(fileInputStream);
            }catch (Exception e){
                e.printStackTrace();
                try {
                    wb = new XSSFWorkbook(fileInputStream);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (ExcelVersion.V2007.getSuffix().equals(extName)) {
            try {
                wb = new XSSFWorkbook(fileInputStream);
            }catch (Exception e){
                e.printStackTrace();
                try {
                    wb = new HSSFWorkbook(fileInputStream);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            // 无效后缀名称，这里之能保证excel的后缀名称，不能保证文件类型正确，不过没关系，在创建Workbook的时候会校验文件格式
            throw new IllegalArgumentException("Invalid excel version");
        }
        // 开始读取数据
        List<ExcelSheetPO> sheetPOs = new ArrayList<ExcelSheetPO>();
        // 解析sheet
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            Sheet sheet = wb.getSheetAt(i);
            List<List<Object>> dataList = new ArrayList<List<Object>>();
            ExcelSheetPO sheetPO = new ExcelSheetPO();
            sheetPO.setSheetName(sheet.getSheetName());
            sheetPO.setDataList(dataList);
            int readRowCount = 0;
            if (rowCount == null || rowCount > sheet.getPhysicalNumberOfRows()) {
                readRowCount = sheet.getPhysicalNumberOfRows();
            } else {
                readRowCount = rowCount;
            }
            // 解析sheet 的行
            for (int j = sheet.getFirstRowNum(); j < readRowCount; j++) {
                Row row = sheet.getRow(j);
                if (row == null) {
                    continue;
                }
                if (row.getFirstCellNum() < 0) {
                    continue;
                }
                int readColumnCount = 0;
                if (columnCount == null || columnCount > row.getLastCellNum()) {
                    readColumnCount = (int) row.getLastCellNum();
                } else {
                    readColumnCount = columnCount;
                }
                List<Object> rowValue = new LinkedList<Object>();
                // 解析sheet 的列
                for (int k = 0; k < readColumnCount; k++) {
                    Cell cell = row.getCell(k);
                    if (wb instanceof HSSFWorkbook){
                        rowValue.add(getCellValue(wb, cell));
                    }else if (wb instanceof XSSFWorkbook){
                        rowValue.add(getStringCellValue(cell));
                    }
                }
                dataList.add(rowValue);
            }
            sheetPOs.add(sheetPO);
        }
        return sheetPOs;
    }

	/**
	 * 单元格数据样式格式化(2003版)
	 * @param wb
	 * @param cell
	 * @return
	 */
	private static Object getCellValue(Workbook wb, Cell cell) {
		Object columnValue = null;
        if (cell != null) {
            DecimalFormat df = new DecimalFormat("0");// 格式化 number
            // String
            // 字符
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");// 格式化日期字符串
//            DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
            switch (cell.getCellTypeEnum()) {
            case STRING:
                columnValue = cell.getStringCellValue();
                break;
            case NUMERIC:
                if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                    columnValue = df.format(cell.getNumericCellValue());
                } else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
//                    columnValue = nf.format(cell.getNumericCellValue());
                    columnValue = BigDecimal.valueOf(cell.getNumericCellValue()).stripTrailingZeros().toPlainString();
                } else {
                    columnValue = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
                }
                break;
            case BOOLEAN:
                columnValue = cell.getBooleanCellValue();
                break;
            case BLANK:
                columnValue = "";
                break;
            case FORMULA:
                // 格式单元格
                FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
                evaluator.evaluateFormulaCellEnum(cell);
                CellValue cellValue = evaluator.evaluate(cell);
                columnValue = cellValue.getNumberValue();
                break;
            default:
                columnValue = cell.toString();
            }
        }
        return columnValue;
	}

    /**
     * 获取单元格数据内容为字符串类型的数据(2007版)
     *
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private static Object getStringCellValue(Cell cell) {
        String strCell = "";
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case XSSFCell.CELL_TYPE_STRING:
                strCell = cell.getStringCellValue();
                break;
            case XSSFCell.CELL_TYPE_NUMERIC:
                if (XSSFDateUtil.isCellDateFormatted(cell)) {
                    //  如果是date类型则 ，获取该cell的date值
                    strCell = new SimpleDateFormat("yyyy/MM/dd").format(XSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
                } else { // 纯数字
//                    strCell = String.valueOf(cell.getNumericCellValue());
                    strCell = BigDecimal.valueOf(cell.getNumericCellValue()).stripTrailingZeros().toPlainString();
                }
                break;
            case XSSFCell.CELL_TYPE_BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case XSSFCell.CELL_TYPE_BLANK:
                strCell = "";
                break;
            default:
                strCell = "";
                break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }

        return strCell;
    }


    /**
     * 生成Workbook
     * @param version excel版本
     * @param excelSheets List<ExcelSheetPO>
     * @return Workbook
     * @throws IOException
     */
    public static Workbook createWorkbook(ExcelVersion version, List<ExcelSheetPO> excelSheets){
        Workbook wb = null;
        if (CollectionUtils.isNotEmpty(excelSheets)) {
            wb = createWorkBook(version, excelSheets);

        }
        return wb;
    }

	/**
     * 在硬盘上写入excel文件
     * @param version
     * @param excelSheets
     * @param filePath
     * @throws IOException
     */
    public static void createWorkbookAtDisk(ExcelVersion version, List<ExcelSheetPO> excelSheets, String filePath)
            throws IOException {
        FileOutputStream fileOut = new FileOutputStream(filePath);
        createWorkbookAtOutStream(version, excelSheets, fileOut, true);
    }

    /**
     * 把excel表格写入输出流中，输出流会被关闭
     * @param version
     * @param excelSheets
     * @param outStream
     * @param closeStream
     *            是否关闭输出流
     * @throws IOException
     */
    private static void createWorkbookAtOutStream(ExcelVersion version, List<ExcelSheetPO> excelSheets,
            OutputStream outStream, boolean closeStream) throws IOException {
        if (CollectionUtils.isNotEmpty(excelSheets)) {
            Workbook wb = createWorkBook(version, excelSheets);
            String[] datas = new String[]{"1-需求","2-设计","3-开发","4-运维","5-测试","6-配置","7-编写手册","8-其它"};
            wb = dropDownList(wb,datas,3);
            wb.write(outStream);
            if (closeStream) {
                outStream.close();
            }
        }
    }

    /**
     * 构建工作簿（Excel）
     * @param version
     * @param excelSheets
     * @return
     */
    private static Workbook createWorkBook(ExcelVersion version, List<ExcelSheetPO> excelSheets) {
        Workbook wb = createWorkbook(version);
        for (int i = 0; i < excelSheets.size(); i++) {
            ExcelSheetPO excelSheetPO = excelSheets.get(i);
            if (excelSheetPO.getSheetName() == null) {
                excelSheetPO.setSheetName("sheet" + i);
            }
            // 过滤特殊字符
            Sheet tempSheet = wb.createSheet(WorkbookUtil.createSafeSheetName(excelSheetPO.getSheetName()));
            buildSheetData(wb, tempSheet, excelSheetPO, version);
        }
        return wb;
    }

    /**
     * 构建工作表（sheet）
     * @param wb
     * @param sheet
     * @param excelSheetPO
     * @param version
     */
    private static void buildSheetData(Workbook wb, Sheet sheet, ExcelSheetPO excelSheetPO, ExcelVersion version) {
        sheet.setColumnWidth(0, 4608);
        sheet.setColumnWidth(1, 2560);
        sheet.setColumnWidth(2, 17920);
        sheet.setColumnWidth(3, 3328);
        sheet.setColumnWidth(4, 3328);
        sheet.setColumnWidth(5, 5120);
        sheet.setColumnWidth(6, 1920);
        sheet.setColumnWidth(7, 2560);
        sheet.setColumnWidth(8, 5888);
//    	sheet.setDefaultRowHeight((short) 400);
//      sheet.setDefaultColumnWidth((short) 20);
        
        boolean titleFlag = false;
        boolean headersFlag = false;
        
        if (excelSheetPO.getTitle() != null && !"".equals(excelSheetPO.getTitle())) {
        	titleFlag = true;
        	createTitle(sheet, excelSheetPO, wb, version);
		}
        if (excelSheetPO.getHeaders() != null && excelSheetPO.getHeaders().length > 0) {
        	headersFlag = true;
        	createHeader(sheet, excelSheetPO, wb, version, titleFlag);
		}
        if (excelSheetPO.getDataList() != null && excelSheetPO.getDataList().size() > 0) {
        	createBody(sheet, excelSheetPO, wb, version, titleFlag, headersFlag);
		}
    }

    /**
     * 构建数据体
     * @param sheet
     * @param excelSheetPO
     * @param wb
     * @param version
     * @param titleFlag
     * @param headersFlag
     */
    private static void createBody(Sheet sheet, ExcelSheetPO excelSheetPO, Workbook wb, 
    		ExcelVersion version, boolean titleFlag, boolean headersFlag) {
        List<List<Object>> dataList = excelSheetPO.getDataList();
        if (dataList == null || dataList.size() <= 0) {
			return;
		}
        
        int rowStart = 0;
        if (titleFlag) {
			if (headersFlag) {
				rowStart = 2;
			} else {
				rowStart = 1;
			}
		} else {
			if (headersFlag) {
				rowStart = 1;
			}
		}
        
        for (int i = 0; i < dataList.size() && i < version.getMaxRow(); i++) {
            List<Object> values = dataList.get(i);
            Row row = sheet.createRow(rowStart + i);
            for (int j = 0; j < values.size() && j < version.getMaxColumn(); j++) {
                Cell cell = row.createCell(j);
//                String string = values.get(j).toString();
//                if (string != null && string.length() > 10) {
//                	cell.setCellStyle(getStyle(STYLE_LONGDATA, wb));
//				} else {
//					cell.setCellStyle(getStyle(STYLE_DATA, wb));
//				}

                if (j == 2) {
                    cell.setCellStyle(getStyle(STYLE_LONGDATA, wb));
                } else {
                    cell.setCellStyle(getStyle(STYLE_DATA, wb));
                }
                
                cell.setCellValue(values.get(j).toString());
            }
        }

    }

    /**
     * 构建表头
     * @param sheet
     * @param excelSheetPO
     * @param wb
     * @param version
     * @param titleFlag
     */
    private static void createHeader(Sheet sheet, ExcelSheetPO excelSheetPO, Workbook wb, 
    		ExcelVersion version, boolean titleFlag) {
        String[] headers = excelSheetPO.getHeaders();
        if (headers == null || headers.length <= 0) {
			return;
		}
        Row row;
        if (titleFlag) {
        	row = sheet.createRow(1);
		} else {
			row = sheet.createRow(0);
		}
      
        for (int i = 0; i < headers.length && i < version.getMaxColumn(); i++) {
            Cell cellHeader = row.createCell(i);
            cellHeader.setCellStyle(getStyle(STYLE_HEADER, wb));
//            cellHeader.getCellStyle().cloneStyleFrom(getStyle(STYLE_HEADER, wb));
            cellHeader.setCellValue(headers[i]);
        }
    }

    /**
     * 构建表标题
     * @param sheet
     * @param excelSheetPO
     * @param wb
     * @param version
     */
    private static void createTitle(Sheet sheet, ExcelSheetPO excelSheetPO, Workbook wb, ExcelVersion version) {
    	if (excelSheetPO.getTitle() == null) {
			return;
		}
    	// 限制最大列数
    	int column = excelSheetPO.getHeaders().length > version.getMaxColumn() ? version.getMaxColumn()
                : excelSheetPO.getHeaders().length;
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, column-1));
        Row titleRow = sheet.createRow(0);
        Cell createCell = titleRow.createCell(0);
        createCell.setCellStyle(getStyle(STYLE_TITLE, wb));
//        createCell.getCellStyle().cloneStyleFrom(getStyle(STYLE_TITLE, wb));
        createCell.setCellValue(excelSheetPO.getTitle());
        for (int i = 1; i < column; i++) {
        	Cell nullCell = titleRow.createCell(i);
        	nullCell.setCellValue("");
		}
    }

    /**
     * 工作表样式设置
     * @param type
     * @param wb
     * @return
     */
    private static CellStyle getStyle(String type, Workbook wb) {

        // modify by zhisj 去掉这行代码，否则项目会在第二次导出周报时报错
//        if (cellStyleMap.containsKey(type)) {
//            return cellStyleMap.get(type);
//        }
        // 生成一个样式
        CellStyle style = null;
        if (wb instanceof HSSFWorkbook){
            style = (HSSFCellStyle) wb.createCellStyle();
        }else if (wb instanceof XSSFWorkbook){
            style = (XSSFCellStyle) wb.createCellStyle();
        }
        //设置边框
        style.setBorderBottom(BorderStyle.THIN);//下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderRight(BorderStyle.THIN);//右边框
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setWrapText(true);

        if (STYLE_HEADER == type) {
            //设置居中
        	style.setAlignment(HorizontalAlignment.CENTER);
        	style.setVerticalAlignment(VerticalAlignment.CENTER);
            //设置字体
        	Font font = wb.createFont();
            font.setFontHeightInPoints((short) 10);//设置字体大小
            font.setBold(true);
            font.setFontName("宋体");
            style.setFont(font);
            //设置背景色
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_YELLOW.getIndex());
        } else if (STYLE_TITLE == type) {
        	style.setAlignment(HorizontalAlignment.CENTER);
        	style.setVerticalAlignment(VerticalAlignment.CENTER);
            Font font = wb.createFont();
            font.setFontHeightInPoints((short) 10);
            font.setBold(true);
            font.setFontName("宋体");
            style.setFont(font);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
        } else if (STYLE_DATA == type) {
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            Font font = wb.createFont();
            font.setFontHeightInPoints((short) 10);
            font.setFontName("宋体");
            style.setFont(font);
        } else if (STYLE_LONGDATA == type) {
        	style.setAlignment(HorizontalAlignment.LEFT);
        	style.setVerticalAlignment(VerticalAlignment.CENTER);
            Font font = wb.createFont();
            font.setFontHeightInPoints((short) 10);
            font.setFontName("宋体");
            style.setFont(font);
		}
        cellStyleMap.put(type, style);
        return style;
    }

    /**
     * 依据excel版本号，创建对应格式的excel文件
     * @param version
     * @return
     */
    private static Workbook createWorkbook(ExcelVersion version) {
        switch (version) {
        case V2003:
              return new HSSFWorkbook();
        case V2007:
            return new XSSFWorkbook();
        }
        return null;
    }

    private static Workbook dropDownList(Workbook wb, String[] datas, int hiddenSheetIndex) {

        CellStyle style = wb.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            Sheet sheet = wb.getSheetAt(i);
            int startRow = 2;
            int endRow = sheet.getLastRowNum();
            if (endRow < startRow) {
				endRow = 100;
			}
            Row sheetRow = sheet.getRow(1);
            int column = 0;
            for (int k = 0; k < sheetRow.getLastCellNum() - 1; k++) {
                if ("任务类型".equals(sheetRow.getCell(k).getStringCellValue()) || "类型".equals(sheetRow.getCell(k).getStringCellValue())) {
                    column = k;
                    break;
                }
            }
            DataValidationHelper helper = sheet.getDataValidationHelper();
            DataValidationConstraint constraint = helper.createExplicitListConstraint(datas);
            CellRangeAddressList addressList = null;
            DataValidation validation = null;
//            for (int j = startRow; j <= endRow; j++) {

                addressList = new CellRangeAddressList(startRow, endRow, column, column);
                validation = helper.createValidation(constraint, addressList);
//                validation.setSuppressDropDownArrow(true);
//                if (validation instanceof XSSFDataValidation) {
//                    validation.setShowErrorBox(true);
//                }
                sheet.addValidationData(validation);
//            }

        }

        return wb;
    }
}
