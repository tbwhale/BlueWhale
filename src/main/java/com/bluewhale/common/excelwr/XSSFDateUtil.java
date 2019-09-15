package com.bluewhale.common.excelwr;

import org.apache.poi.ss.usermodel.DateUtil;

import java.util.Calendar;

/**
 * Created by curtin
 * User: curtin
 * Date: 2019/9/15
 * Time: 9:07 PM
 */
public class XSSFDateUtil extends DateUtil {
    protected static int absoluteDay(Calendar cal, boolean use1904windowing) {
        return DateUtil.absoluteDay(cal, use1904windowing);
    }


}
