package com.bluewhale.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author curtin 2020/3/21 8:48 PM
 */
public class CommonsUtil {

    public static final char enter = 10;

    /**
     * 将捕获的异常，其中所有的堆栈信息组成一个字符串
     * 以应对 e.getMessage() 返回null的情况
     *
     * @param e 异常对象
     * @return 堆栈信息字符串
     */
    public static String getExceptionStackMsg(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String rst = sw.toString();
        rst = rst.replaceAll(" at ", "\\r\\n at ");
        rst = rst.replaceAll("Caused by\\:", "\\r\\n\\r\\nCaused by\\:");
        try {
            if (sw != null) {
                sw.close();
            }
            if (pw != null) {
                pw.close();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return rst;
    }

//    public static String getExceptionStackMsg1(Throwable e) {
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        e.printStackTrace(pw);
//        String rst = sw.toString();
//        rst = rst.replaceAll(" at ", enter +"at ");
//        rst = rst.replaceAll("Caused by\\:", enter + enter+"Caused by\\:");
//        try {
//            if (sw != null) {
//                sw.close();
//            }
//            if (pw != null) {
//                pw.close();
//            }
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//        return rst;
//    }

    /**
     * 判断字符串是否包含一个值
     *
     * @param targetValue 目标值
     * @param strArray    字符串 中间用,分割
     * @return boolean 包含：true;不包含：false
     * @author curtin 2020/4/12 10:58 AM
     */
    public static boolean in(String targetValue, String strArray) {
        String[] strArr = strArray.split(",");
        for (String s : strArr) {
            if (s.equals(targetValue)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否包含一个值
     *
     * @param targetValue 目标值
     * @param strArray    字符串 中间用,分割
     * @return boolean 包含：true;不包含：false
     * @author curtin 2020/4/12 10:58 AM
     */
    public static boolean notIn(String targetValue, String strArray) {
        return !in(targetValue, strArray);
    }
}
