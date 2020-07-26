package com.bluewhale.common.util;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.junit.Test;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by curtin
 * User: curtin
 * Date: 2020/3/14
 * Time: 1:25 AM
 */
public class QRCode {

    /**
     * 生成二维码
     * @throws Exception
     */
    @Test
    public void generateQRCode() throws Exception {
        //生成一个二维码

        //定义一个json格式的字符串，使用fastJson
        JSONObject jsonObject = new JSONObject();

        //给jsonObject对象存放数据
        jsonObject.put("companyName", "sinosoft");
        jsonObject.put("company", "www.sinosoft.com");
        jsonObject.put("authorName", "curtin");
        jsonObject.put("adress", "北京海淀");

        //将json对象转换为json格式的字符串
        String content = jsonObject.toString();
        System.out.println(content);

        //二维码的宽高
        int width = 200;
        int hight = 200;

        //创建map集合
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        //创建一个矩阵
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, hight, hints);

        //生成的路径
        String filePath = "/Users/curtin/eclipse-workspace/BlueWhale";
        String fileName = "QRCone.jpg";

        //创建一个路径对象
        Path path = FileSystems.getDefault().getPath(filePath, fileName);

        //将矩阵对象生成一个图片
        MatrixToImageWriter.writeToPath(bitMatrix, "jpg", path);

        System.out.println("生成二维码成功");


    }
}
