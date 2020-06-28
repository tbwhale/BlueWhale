package com.bluewhale.email.controller;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

import com.bluewhale.email.service.EmailService;
import com.bluewhale.common.util.Messages;

/**
 * 邮件发送
 * @author curtin 2019-03-30 14:25:21
 * @version v1.0.0
 */
@RestController
@RequestMapping("/email")
public class EmailController {
	
	@Autowired
	private EmailService emailService;
	
	/**
	 * 发送普通邮件
	 * @return String
	 */
	@RequestMapping("/sendSimpleEmail")
	@ResponseBody
	public String sendSimpleEmail() {
		Messages message = new Messages();
        String[] receiver = {"curtin@aliyun.com"};
        String[] carbonCopy = null;
        String subject = "This is a simple email";
        String content = "This is a simple content";
        
        try {
        	message = emailService.sendSimpleEmail(receiver, carbonCopy, subject, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return message.toString();
	}
	
	/**
	 * 发送HTML邮件
	 * @return String
	 */
	@RequestMapping("/sendHtmlEmail")
	@ResponseBody
	public String sendHtmlEmail() {
		Messages message = new Messages();
        String[] receiver = {"curtin@aliyun.com"};
        String[] carbonCopy = null;
        String subject = "This is a HTML content email";
        String content = "<h1>This is HTML content email </h1>";
        boolean isHtml = true;
        
        try {
        	message = emailService.sendHtmlEmail(receiver, carbonCopy, subject, content, isHtml);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return message.toString();
	}
	
	/**
	 * 发送含附件邮件
	 * @return String
	 */
	@RequestMapping("/sendAttachmentFileEmail")
	@ResponseBody
	public String sendAttachmentFileEmail() {
		Messages message = new Messages();
        String[] receiver = {"curtin@aliyun.com","huangjian@sinosoft.com.cn","zhangxiaorui@sinosoft.com.cn"};
        String[] carbonCopy = {"biwuheng@sinosoft.com.cn"};
        String FILE_PATH = "/Users/curtin/Library/Log/BlueWhaleDev.all.log";
        String subject = "This is an attachment file email";
        String content = "<h2>This is an attachment file email</h2>";
        boolean isHtml = true;

        File file = new File(FILE_PATH);
        String fileName = FILE_PATH.substring(FILE_PATH.lastIndexOf(File.separator));
        
        try {
        	message = emailService.sendAttachmentFileEmail(receiver, carbonCopy, subject, content, isHtml, fileName, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return message.toString();
	}
    /**
     * 发送含附件邮件（服务器用）
     * @return String
     */
    @RequestMapping("/sendFileEmail")
    @ResponseBody
    public String sendFileEmail() {
        Messages message = new Messages();
        String[] receiver = {"curtin@aliyun.com"};
        String[] carbonCopy = null;
        String FILE_PATH = "/home/weblogic/installdir/liuketing/bluewhale/api-gateway.sh";
        String subject = "This is an send file email";
        String content = "<h2>This is an send file email</h2>";
        boolean isHtml = true;

        File file = new File(FILE_PATH);
        String fileName = FILE_PATH.substring(FILE_PATH.lastIndexOf(File.separator));

        try {
            message = emailService.sendAttachmentFileEmail(receiver, carbonCopy, subject, content, isHtml, fileName, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message.toString();
    }

	/**
	 * 发送模板邮件
	 * @return String
	 */
	@RequestMapping("/sendTemplateEmail")
	@ResponseBody
	public String sendTemplateEmail() {
		Messages message = new Messages();
        String[] receiver = {"curtin@aliyun.com","huangjian@sinosoft.com.cn","zhangxiaorui@sinosoft.com.cn"};
        String[] carbonCopy = {"biwuheng@sinosoft.com.cn"};
        String subject = "This is a template email";
        String template = "mail/InternalServerErrorTemplate";
        Context context = new Context();
        String errorMessage;

        try {
            throw new NullPointerException();
        } catch (NullPointerException e) {
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            e.printStackTrace(printWriter);
            errorMessage = writer.toString();
        }

        context.setVariable("username", "BlueWhale");
        context.setVariable("methodName", "com.bluewhale.email.controller.EmailController()");
        context.setVariable("errorMessage", errorMessage);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        context.setVariable("occurredTime", sdf.format(new Date()));
        
        try {
        	message = emailService.sendTemplateEmail(receiver, carbonCopy, subject, template, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return message.toString();
	}
}
