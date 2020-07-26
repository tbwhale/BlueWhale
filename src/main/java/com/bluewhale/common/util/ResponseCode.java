package com.bluewhale.common.util;

/**
 * 返回值和返回信息枚举类封装
 *
 * @author curtin 2020-04-04 17:01:10
 */
public enum ResponseCode {
    SUCCESS(200, "success"),

    ERROR(400, "error"),

    ILLEGAL_ARGUMENT(2, "illegal argument"),

    NEED_LOGIN(10, "need login");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}