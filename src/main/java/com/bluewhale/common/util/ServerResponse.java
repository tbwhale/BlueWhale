package com.bluewhale.common.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import java.util.Map;

/**
 * 通用返回类对象封装
 *
 * @author curtin 2020-04-04 17:01:20
 */
@JsonInclude(Include.NON_NULL)
/* 保证序列化json时候，如果是null对象，key也会消失 */
public class ServerResponse<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    private ServerResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private ServerResponse(int code, String msg) {
        this(code, msg, null);
    }

    private ServerResponse(int code, T data) {
        this(code, null, data);
    }

    private ServerResponse(int code) {
        this(code, null, null);
    }

    @JsonIgnore
    // 保证不在序列化中出现，因为某些序列化产品会将isXXX()方法认为是属性
    public boolean isSuccess() {
        return code == ResponseCode.SUCCESS.getCode();
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public static <T> ServerResponse<T> buildSuccess() {
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServerResponse<T> buildSuccessByMsg(String msg) {
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(), msg);
    }

    public static <T> ServerResponse<T> buildSuccessByData(T data) {
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(), data);
    }

    public static <T> ServerResponse<T> buildSuccess(String msg, T data) {
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> ServerResponse<T> buildError() {
        return new ServerResponse<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    public static <T> ServerResponse<T> buildErrorByMsg(String msg) {
        return new ServerResponse<>(ResponseCode.ERROR.getCode(), msg);
    }

    public static <T> ServerResponse<T> buildErrorByCode(int code, String msg) {
        return new ServerResponse<>(code, msg);
    }

    /**
     * 构建错误返回Map
     *
     * @param returnMap 返回Map实例
     * @param msg       错误描述信息
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author curtin 2020/4/6 1:05 PM
     */
    public static Map<String, Object> buildErrorReturnMap(Map<String, Object> returnMap, String msg) {
        returnMap.put("code", ResponseCode.ERROR.getCode());
        returnMap.put("msg", msg);
        return returnMap;
    }

    /**
     * 构建成功返回Map
     *
     * @param returnMap 返回Map实例
     * @param msg       错误描述信息
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author curtin 2020/4/6 1:05 PM
     */
    public static Map<String, Object> buildSuccessReturnMap(Map<String, Object> returnMap, String msg) {
        returnMap.put("code", ResponseCode.SUCCESS.getCode());
        returnMap.put("msg", msg);
        return returnMap;
    }
}