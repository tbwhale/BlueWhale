package com.bluewhale.common.misc;

import com.google.common.annotations.Beta;
import com.bluewhale.common.date.DateUtil;
import com.bluewhale.common.number.RandomUtil;
import com.bluewhale.common.text.EncodeUtil;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 封装各种生成唯一性ID算法的工具类
 * Created by curtin
 * User: curtin
 * Date: 2020/3/24
 * Time: 8:52 AM
 */
@Beta
public class IdGenerator {

    private static SecureRandom random = new SecureRandom();

    /**
     * 封装JDK自带的UUID，通过Random数字生成，中间有-分割。
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 封装JDK自带的UUID，通过Random数字生成，中间无-分割。
     */
    public static String uuid2() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 使用SecureRandom随机生成Long
     */
    public static long randomLong() {
        return RandomUtil.nextLong();
    }


    /**
     * 基于URLSafeBase64编码的SecureRandom随机生成bytes.
     */
    public static String randomBase64(int length) {
        byte[] randomBytes = new byte[length];
        random.nextBytes(randomBytes);
        return EncodeUtil.encodeBase64UrlSafe(randomBytes);
    }

    /**
     * 生成日期加时间加六位随机数共20位流水号
     *
     * @return String
     */
    public static String getSerialNo() {
        //生成年月日时分秒14位数字字符串
        String tSerialNo;
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmsss");
        String tCurrentDate = DateUtil.getNow(DateUtil.DATETIME_FORMAT_2);
        //生成6位随机数字
        int RandomNo = (int) ((Math.random() * 9 + 1) * 100000);
        tSerialNo = tCurrentDate + String.valueOf(RandomNo);
        return tSerialNo;

    }

    /**
     * 生成流水号
     *
     * @return String
     */
    public static String getSnowflakeId() {
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        return String.valueOf(idWorker.nextId());
    }

    public static void main(String[] args) {
        System.out.println(randomBase64(30));
        System.out.println(uuid());
        System.out.println(uuid2());
        System.out.println(randomLong());
        System.out.println(getSerialNo());
        System.out.println(getSnowflakeId());
    }


}
