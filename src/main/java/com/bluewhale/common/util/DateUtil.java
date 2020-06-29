package com.bluewhale.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * @author curtin 2020-6-29 08:27:12
 */
public class DateUtil {
    /**
     * 获得入参日期下周一的日期
     *
     * @param date 入参日期
     * @return 入参日期的下周一
     */
    public static Date getNextMonday(Date date) {
        //获得入参的日期
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);

        // 获得入参日期是一周的第几天
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        // 获得入参日期相对于下周一的偏移量（在国外，星期一是一周的第二天，所以下周一是这周的第九天）
        // 若入参日期是周日，它的下周一偏移量是1
        int nextMondayOffset = dayOfWeek == 1 ? 1 : 9 - dayOfWeek;

        // 增加到入参日期的下周一
        cd.add(Calendar.DAY_OF_MONTH, nextMondayOffset);
        return cd.getTime();
    }

    /**
     * 获得入参日期下周五的日期
     *
     * @param date 入参日期
     * @return 入参日期的下周五
     */
    public static Date getNextFriday(Date date) {
        //获得入参的日期
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);

        // 获得入参日期是一周的第几天
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        // 获得入参日期相对于下周五的偏移量（在国外，星期一是一周的第二天，所以下周五是这周的第十三天）
        // 若入参日期是周日，它的下周五偏移量是5
        int nextFridayOffset = dayOfWeek == 5 ? 5 : 13 - dayOfWeek;

        // 增加到入参日期的下周一
        cd.add(Calendar.DAY_OF_MONTH, nextFridayOffset);
        return cd.getTime();
    }



    public static void main(String[] args) {
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2020-6-26 00:00:00");
            Date date2 = getNextMonday(date1);
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date2));
            Date date3 = getNextFriday(date1);
            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date3));


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
