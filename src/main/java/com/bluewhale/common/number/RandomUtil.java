package com.bluewhale.common.number;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.Validate;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * 随机数工具类
 * <p>
 * 1.获取无锁的ThreadLocalRandom，性能较佳的SecureRandom
 * <p>
 * 2.保证没有负数陷阱，也能更精确设定范围的nextInt/nextLong/nextDouble (copy from Common Lang
 * RandomUtils. 单默认使用性能较优的ThreadLocalRandom，并可配置其它的Random)
 * <p>
 * 3.随机字符串
 * <p>
 * Created by curtin
 * User: curtin
 * Date: 2020/3/24
 * Time: 8:54 PM
 */
public class RandomUtil {

    //////////////////////获取Random实例//////////////////////

    /**
     * 返回无锁的ThreadLocalRandom
     */
    public static Random threadLocalRandom() {
        return java.util.concurrent.ThreadLocalRandom.current();
    }

    /**
     * 使用性能更好的SHA1PRNG，Tomcat的sessionId生成也使用此算法.
     * <p>
     * 但JDK7中，需要在启动参数加入 —Djava.security=file:/dev/./urandom (中间那个点很重要)
     * <p>
     * 详见：《SecureRandom的江湖偏方与真实效果》 http://calvin1978.blogcn.com/articles/securerandom.html
     */
    public static SecureRandom secureRandom() {
        try {
            return SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            return new SecureRandom();
        }
    }

    //////////////////////  nextInt 相关 //////////////////////

    /**
     * 返回0到Integer.MAX_VALUE的随机Int，使用内置全局普通Random
     */
    public static int nextInt() {
        return nextInt(threadLocalRandom());
    }

    /**
     * 返回0到Integer.MAX_VALUE的随机Int，可传入SecureRandom或ThreadLocalRandom
     */
    public static int nextInt(Random random) {
        int n = random.nextInt();
        if (n == Integer.MIN_VALUE) {
            n = 0; //corner case
        } else {
            n = Math.abs(n);
        }
        return n;
    }

    /**
     * 返回0到max的随机Int，使用内置全局普通Random
     */
    public static int nextInt(int max) {
        return nextInt(threadLocalRandom(), max);
    }

    /**
     * 返回0到max的随机Int，可传入SecureRandom或ThreadLocalRandom
     */
    public static int nextInt(Random random, int max) {
        return random.nextInt(max);
    }

    /**
     * 返回min到max的随机Int，使用内置全局普通Random
     * <p>
     * min必须大于0
     */
    public static int nextInt(int min, int max) {
        return nextInt(threadLocalRandom(), min, max);
    }

    /**
     * 返回min到max的随机Int，可传入SecureRandom或ThreadLocalRandom
     * <p>
     * min必须大于0
     * <p>
     * JDK本身不具有控制两端范围的nexInt，因此参考Commons Lang RandomUtils的实现，
     * 不直接复用是因为要传入Random实例
     *
     * @see org.apache.commons.lang3.RandomUtils#nextInt(int, int)
     */
    public static int nextInt(Random random, int min, int max) {
        Validate.isTrue(max >= min, "Start value must be smaller or equal to end value.");

        if (min < 0) {
            throw new IllegalArgumentException("min (" + min + ") must be >= 0");
        }

        if (min == max) {
            return min;
        }

        return min + random.nextInt(max - min);
    }


    //////////////////////  long 相关 //////////////////////

    /**
     * 返回0到Long.MAX_VALUE的随机Long，使用内置全局普通Random
     */
    public static long nextLong() {
        return nextLong(threadLocalRandom());
    }

    /**
     * 返回0到Long.MAX_VALUE的随机Long，可传入SecureRandom或ThreadLocalRandom
     */
    public static Long nextLong(Random random) {
        long n = random.nextLong();
        if (n == Long.MIN_VALUE) {
            n = 0; //corner case
        } else {
            n = Math.abs(n);
        }
        return n;
    }

    /**
     * 返回0到max的随机Long，使用内置全局普通Random
     */
    public static long nextLong(long max) {
        return nextLong(threadLocalRandom(), 0, max);
    }

    /**
     * 返回0到max的随机Long，可传入SecureRandom或ThreadLocalRandom
     */
    public static long nextLong(Random random, long max) {
        return nextLong(random, 0, max);
    }

    /**
     * 返回min到max的随机Long，使用内置全局普通Random
     * <p>
     * min必须大于0
     */
    public static long nextLong(long min, long max) {
        return nextLong(threadLocalRandom(), min, max);
    }

    /**
     * 返回min到max的随机Long，可传入SecureRandom或ThreadLocalRandom
     * <p>
     * min必须大于0
     * <p>
     * JDK本身不具有控制两端范围的nexLong，因此参考Commons Lang RandomUtils的实现，
     * 不直接复用是因为要传入Random实例
     *
     * @see org.apache.commons.lang3.RandomUtils#nextLong(long, long)
     */
    public static long nextLong(Random random, long min, long max) {
        Validate.isTrue(max >= min, "Start value must be smaller or equal to end value.");

        if (min < 0) {
            throw new IllegalArgumentException("min (" + min + ") must be >= 0");
        }


        if (min == max) {
            return min;
        }

        return (long) (min + (max - min) * random.nextDouble());
    }


    //////////////////////  Double 相关 //////////////////////

    /**
     * 返回0到Double.MAX_VALUE的随机double，使用内置全局普通Random
     */
    public static double nextDouble() {
        return nextDouble(threadLocalRandom(), 0, Double.MAX_VALUE);
    }

    /**
     * 返回0到Double.MAX_VALUE的随机double，可传入SecureRandom或ThreadLocalRandom
     */
    public static double nextDouble(Random random) {
        return nextDouble(random, 0, Double.MAX_VALUE);
    }

    /**
     * 返回0到max的随机double，使用内置全局普通Random
     * <p>
     * 注意：与JDK默认返回0-1的行为不一致
     */
    public static double nextDouble(double max) {
        return nextDouble(threadLocalRandom(), 0, max);
    }

    /**
     * 返回0到max的随机double，可传入SecureRandom或ThreadLocalRandom
     */
    public static double nextDouble(Random random, double max) {
        return nextDouble(random, 0, max);
    }

    /**
     * 返回min到max的随机double，使用内置全局普通Random
     * <p>
     * min必须大于0
     */
    public static double nextDouble(final double min, final double max) {
        return nextDouble(threadLocalRandom(), min, max);
    }

    /**
     * 返回min到max的随机DOuble，可传入SecureRandom或ThreadLocalRandom
     * <p>
     * min必须大于0
     * <p>
     * JDK本身不具有控制两端范围的nexLong，因此参考Commons Lang RandomUtils的实现，
     * 不直接复用是因为要传入Random实例
     *
     * @see org.apache.commons.lang3.RandomUtils#nextDouble(double, double)
     */
    public static double nextDouble(Random random, double min, double max) {
        Validate.isTrue(max >= min, "Start value must be smaller or equal to end value.");

        if (min < 0) {
            throw new IllegalArgumentException("min (" + min + ") must be >= 0");
        }


        if (min == max) {
            return min;
        }

        return min + ((max - min) * random.nextDouble());
    }


    //////////////////////  String 相关 //////////////////////

    /**
     * 随机字母或数字，固定长度
     */
    public static String randomStringFixLength(int length) {
        return RandomStringUtils.random(length, 0, 0, true, true, null, threadLocalRandom());
    }

    /**
     * 随机字母或数字，固定长度
     */
    public static String randomStringFixLength(Random random, int length) {
        return RandomStringUtils.random(length, 0, 0, true, true, null, random);
    }

    /**
     * 随机字母或数字，随机长度
     */
    public static String randomStringRandomLength(int minLength, int maxLength) {
        return RandomStringUtils.random(nextInt(minLength, maxLength), 0, 0, true, true, null, threadLocalRandom());
    }

    /**
     * 随机字母或数字，随机长度
     */
    public static String randomStringRandomLength(Random random, int minLength, int maxLength) {
        return RandomStringUtils.random(nextInt(random, minLength, maxLength), 0, 0, true, true, null, random);
    }

    /**
     * 随机字母，固定长度
     */
    public static String randomLetterFixLength(int length) {
        return RandomStringUtils.random(length, 0, 0, true, false, null, threadLocalRandom());
    }

    /**
     * 随机字母，固定长度
     */
    public static String randomLetterFixLength(Random random, int length) {
        return RandomStringUtils.random(length, 0, 0, true, false, null, random);
    }

    /**
     * 随机字母，随机长度
     */
    public static String randomLetterRandomLength(int minLength, int maxLength) {
        return RandomStringUtils.random(nextInt(minLength, maxLength), 0, 0, true, false, null, threadLocalRandom());
    }

    /**
     * 随机字母，随机长度
     */
    public static String randomLetterRandomLength(Random random, int minLength, int maxLength) {
        return RandomStringUtils.random(nextInt(random, minLength, maxLength), 0, 0, true, false, null, random);
    }

    /**
     * 随机ASCII字符（含字母，数字及其它符号），固定长度
     */
    public static String randomAsciiFixLength(int length) {
        return RandomStringUtils.random(length, 32, 127, false, false, null, threadLocalRandom());
    }

    /**
     * 随机ASCII字符（含字母，数字及其它符号），固定长度
     */
    public static String randomAsciiFixLength(Random random, int length) {
        return RandomStringUtils.random(length, 32, 127, false, false, null, random);
    }

    /**
     * 随机ASCII字符（含字母，数字及其它符号），随机长度
     */
    public static String randomAsciiRandomLength(int minLength, int maxLength) {
        return RandomStringUtils.random(nextInt(minLength, maxLength), 32, 127, false, false, null, threadLocalRandom());
    }

    /**
     * 随机ASCII字符（含字母，数字及其它符号），随机长度
     */
    public static String randomAsciiRandomLength(Random random, int minLength, int maxLength) {
        return RandomStringUtils.random(nextInt(random, minLength, maxLength), 32, 127, false, false, null, random);
    }



}
