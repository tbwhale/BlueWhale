package com.bluewhale.common.util;

import java.io.File;
import java.util.Arrays;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * 邮件发送
 * @author curtin
 * @date 2019-03-30 12:21:58
 * @version v1.0.0
 */
@Component
public class MailUtil {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
    TemplateEngine templateEngine;
	
	/**
	 * 发送普通邮件
	 * @param deliver 发件人地址
	 * @param receiver 接收者邮件地址
	 * @param carbonCopy 抄送者邮件地址(没有为null)
	 * @param subject 主题
	 * @param content 邮件内容
	 * @throws Exception
	 */
	public Messages sendSimpleEmail(String deliver, String[] receiver, String[] carbonCopy, String subject, String content) throws Exception {
		Messages msg = new Messages();
        long startTimestamp = System.currentTimeMillis();
        logger.info("Start send mail ... ");
        logger.info("发件人:["+deliver+"] 收件人:"+Arrays.toString(receiver)+" 抄送:"+Arrays.toString(carbonCopy));
        
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            
            message.setFrom(deliver);
            message.setTo(receiver);
            if(carbonCopy != null) {
            	message.setCc(carbonCopy);
            }
            message.setSubject(subject);
            message.setText(content);
            
            mailSender.send(message);
            logger.info("Send mail success, cost {} million seconds", System.currentTimeMillis() - startTimestamp);
            msg.setMsgCode("Y");
            msg.setMsgDesc("Send mail success, cost "+ (System.currentTimeMillis() - startTimestamp) +" million seconds");
        } catch (MailException e) {
            logger.error("Send mail failed, error message is : {} \n", e.getMessage());
            msg.setMsgCode("N");
            msg.setMsgDesc("Send mail failed, error message is : "+ e.getMessage());
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
		return msg;
    }
	
	/**
	 * 发送HTML邮件
	 * @param deliver 发件人地址
	 * @param receiver 接收者邮件地址
	 * @param carbonCopy 抄送者邮件地址(没有为null)
	 * @param subject 主题
	 * @param content 邮件内容
	 * @param isHtml 是否是HTML邮件 true
	 * @throws Exception
	 */
	public Messages sendHtmlEmail(String deliver, String[] receiver, String[] carbonCopy, String subject, String content, boolean isHtml) throws Exception {
		Messages msg = new Messages();
		long startTimestamp = System.currentTimeMillis();
        logger.info("Start send email ...");
        logger.info("发件人:["+deliver+"] 收件人:"+Arrays.toString(receiver)+" 抄送:"+Arrays.toString(carbonCopy));
        
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);

            messageHelper.setFrom(deliver);
            messageHelper.setTo(receiver);
            if(carbonCopy != null) {
            	messageHelper.setCc(carbonCopy);
            }
            messageHelper.setSubject(subject);
            messageHelper.setText(content, isHtml);

            mailSender.send(message);
            logger.info("Send email success, cost {} million seconds", System.currentTimeMillis() - startTimestamp);
            msg.setMsgCode("Y");
            msg.setMsgDesc("Send mail success, cost "+ (System.currentTimeMillis() - startTimestamp) +" million seconds");
        } catch (MessagingException e) {
            logger.error("Send email failed, error message is {} \n", e.getMessage());
            msg.setMsgCode("N");
            msg.setMsgDesc("Send mail failed, error message is : "+ e.getMessage());
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return msg;
    }
	
	/**
	 * 发送含附件邮件
	 * @param deliver 发件人地址
	 * @param receiver 接收者邮件地址
	 * @param carbonCopy 抄送者邮件地址(没有为null)
	 * @param subject 主题
	 * @param content 邮件内容
	 * @param isHtml 是否是HTML邮件 true
	 * @param fileName 附件名字
	 * @param file 附件
	 * @throws Exception
	 */
	public Messages sendAttachmentFileEmail(String deliver, String[] receiver, String[] carbonCopy, String subject, String content, boolean isHtml, String fileName, File file) throws Exception {
		Messages msg = new Messages();
		long startTimestamp = System.currentTimeMillis();
        logger.info("Start send mail ...");
        logger.info("发件人:["+deliver+"] 收件人:"+Arrays.toString(receiver)+" 抄送:"+Arrays.toString(carbonCopy));

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            messageHelper.setFrom(deliver);
            messageHelper.setTo(receiver);
            if(carbonCopy != null) {
            	messageHelper.setCc(carbonCopy);
            }
            messageHelper.setSubject(subject);
            messageHelper.setText(content, isHtml);
            messageHelper.addAttachment(fileName, file);

            mailSender.send(message);
            logger.info("Send mail success, cost {} million seconds", System.currentTimeMillis() - startTimestamp);
            msg.setMsgCode("Y");
            msg.setMsgDesc("Send mail success, cost "+ (System.currentTimeMillis() - startTimestamp) +" million seconds");
        } catch (MessagingException e) {
            logger.error("Send mail failed, error message is {}\n", e.getMessage());
            msg.setMsgCode("N");
            msg.setMsgDesc("Send mail failed, error message is : "+ e.getMessage());
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
		return msg;
    }

	/**
	 * 发送模板邮件
	 * @param deliver 发件人地址
	 * @param receiver 接收者邮件地址
	 * @param carbonCopy 抄送者邮件地址(没有为null)
	 * @param subject 主题
	 * @param template 模板
	 * @param context 
	 * @throws Exception
	 */
	public Messages sendTemplateEmail(String deliver, String[] receiver, String[] carbonCopy, String subject, String template, Context context) throws Exception {
		Messages msg = new Messages();
		long startTimestamp = System.currentTimeMillis();
        logger.info("Start send mail ...");
        logger.info("发件人:["+deliver+"] 收件人:"+Arrays.toString(receiver)+" 抄送:"+Arrays.toString(carbonCopy));

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);

            String content = templateEngine.process(template, context);
            messageHelper.setFrom(deliver);
            messageHelper.setTo(receiver);
            if(carbonCopy != null) {
            	messageHelper.setCc(carbonCopy);
            }
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);

            mailSender.send(message);
            logger.info("Send mail success, cost {} million seconds", System.currentTimeMillis() - startTimestamp);
            msg.setMsgCode("Y");
            msg.setMsgDesc("Send mail success, cost "+ (System.currentTimeMillis() - startTimestamp) +" million seconds");
        } catch (MessagingException e) {
            logger.error("Send mail failed, error message is {} \n", e.getMessage());
            msg.setMsgCode("N");
            msg.setMsgDesc("Send mail failed, error message is : "+ e.getMessage());
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
		return msg;
    }
	
}
