package com.bluewhale.spring.boot.common.util;
/**
 * 类说明
 * @author 张晓睿
 * @version 创建时间   2019年3月11日 下午6:23:07
 */
public class FileUtil {

	/**
     * 获取文件拓展名
     * @param file
     * @return
     */
	public static String getFileExtName(String file) {
		if (file != null && file.length() > 0) {
			int dot = file.lastIndexOf(".");
			if (dot > -1 && dot < file.length()-1) {
				return file.substring(dot);
			}
		}
		return file;
	}
}
