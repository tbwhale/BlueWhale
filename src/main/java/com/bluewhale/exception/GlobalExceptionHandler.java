package com.bluewhale.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by curtin
 * User: curtin
 * Date: 2019/12/7
 * Time: 4:21 PM
 */
public @ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();
        String errorMessage;
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        errorMessage = writer.toString();

        StackTraceElement stackTraceElement = e.getStackTrace()[0];
//        String errorMessage = "文件名："+stackTraceElement.getFileName()+
//                "\r\n类名："+stackTraceElement.getClassName()+
//                "\r\n方法名："+stackTraceElement.getMethodName()+
//                "\r\n抛出异常行号："+stackTraceElement.getLineNumber()+
//                "\r\n机构ID异常，无法获取本机构信息："+e;
        String methodName = stackTraceElement.getFileName() + stackTraceElement.getClassName() + stackTraceElement.getMethodName() +
                +stackTraceElement.getLineNumber();

        mav.addObject("username", "BlueWhale");
        mav.addObject("methodName", methodName);
        mav.addObject("errorMessage", errorMessage);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        mav.addObject("occurredTime", sdf.format(new Date()));
        mav.addObject("errorMsg", errorMessage);
        mav.setViewName("error");
        return mav;
    }
}
