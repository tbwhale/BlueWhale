package com.bluewhale.exception.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * 邮件发送
 *
 * @author curtin 2019-03-30 14:25:21
 * @version v1.0.0
 */
@RestController
@RequestMapping("/exception")
public class ExceptionController {


    /**
     * 抛出异常
     *
     * @return String
     */
    @RequestMapping("/test")
    @ResponseBody
    public void exceptionTest() throws Exception {

        String errorMessage;
//        try {
//            throw new Exception("发生了自定义异常");
            throw new NullPointerException();
//        } catch (Exception e) {
//            Writer writer = new StringWriter();
//            PrintWriter printWriter = new PrintWriter(writer);
//            e.printStackTrace(printWriter);
//            errorMessage = writer.toString();
//            System.out.println("发生了异常，异常信息如下：" + errorMessage);
//        }

    }
}
