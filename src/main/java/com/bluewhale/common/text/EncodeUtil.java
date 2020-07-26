package com.bluewhale.common.text;

import com.google.common.io.BaseEncoding;

/**
 * hex/base64 编解码工具集.依赖Guava，取消了Common Codec的依赖
 *
 * @author curtin 2020/3/24 11:50 PM
 */
public class EncodeUtil {

    /**
     * Hex编码，将byte[]编码为String，默认为ABCDEF为大写字母.
     */
    public static String encodeHex(byte[] input) {
        return BaseEncoding.base16().encode(input);
    }

    /**
     * Hex解码，将String解码为byte[].
     * <p>
     * 字符串有异常时抛出IllegalArgumentException.
     */
    public static byte[] dncodeHex(CharSequence input) {
        return BaseEncoding.base16().decode(input);
    }

    /**
     * Base64编码，将byte[]编码为String，默认为ABCDEF为大写字母.
     */
    public static String encodeBase64(byte[] input) {
        return BaseEncoding.base64().encode(input);
    }

    /**
     * Base64解码，将String解码为byte[].
     * <p>
     * 字符串有异常时抛出IllegalArgumentException.
     */
    public static byte[] dncodeBase64(CharSequence input) {
        return BaseEncoding.base64().decode(input);
    }

    /**
     * Base64编码，URL安全.(将Base64中的URL非法字符'+'和'/'转为'-'和'_',见RFC3548).
     */
    public static String encodeBase64UrlSafe(byte[] input) {
        return BaseEncoding.base64Url().encode(input);
    }

    /**
     * Base64解码，URL安全.(将Base64中的URL非法字符'+'和'/'转为'-'和'_',见RFC3548).
     * <p>
     * 字符串有异常时抛出IllegalArgumentException.
     */
    public static byte[] dncodeBase64UrlSafe(CharSequence input) {
        return BaseEncoding.base64Url().decode(input);
    }

}
