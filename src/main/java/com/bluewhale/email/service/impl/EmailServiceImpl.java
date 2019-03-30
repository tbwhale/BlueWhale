package com.bluewhale.email.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.bluewhale.common.util.MailUtil;
import com.bluewhale.common.util.Messages;
import com.bluewhale.email.service.EmailService;

/**
 * 发送邮件
 * @author curtin 2019-03-30 14:31:10
 * @version v1.0.0
 */
@Service("emailService")
public class EmailServiceImpl implements EmailService {

	@Autowired
	private MailUtil mailUtil;
	
	/**
	 * 发件人邮箱
	 */
	@Value("${bluewhale.mail.fromMail}")
    private String fromMail;
	
	/**
     * 发送简单邮件
     * @param receiver 收件人
     * @param carbonCopy 抄送(没有为null)
     * @param subject 主题
     * @param content 内容
     */
	@Override
	public Messages sendSimpleEmail(String[] receiver, String[] carbonCopy, String subject, String content) {
        Messages msg = new Messages();
        try {
        	msg = mailUtil.sendSimpleEmail(fromMail, receiver, carbonCopy, subject, content);
        } catch (Exception e) {
        	msg.setMsgDesc("出现异常："+e.getMessage());
            e.printStackTrace();
        }
		return msg;
	}
	
	/**
     * 发送HTML邮件
     * @param receiver 收件人
     * @param carbonCopy 抄送(没有为null)
     * @param subject 主题
     * @param content 内容
     * @param isHtml 是否是HTML邮件 true
     */
	@Override
	public Messages sendHtmlEmail(String[] receiver, String[] carbonCopy, String subject, String content, boolean isHtml) {
        Messages msg = new Messages();
        try {
        	msg = mailUtil.sendHtmlEmail(fromMail, receiver, carbonCopy, subject, content, isHtml);
        } catch (Exception e) {
        	msg.setMsgDesc("出现异常："+e.getMessage());
            e.printStackTrace();
        }
		return msg;
	}

	/**
	 * 发送含附件邮件
	 * @param receiver 接收者邮件地址
	 * @param carbonCopy 抄送者邮件地址(没有为null)
	 * @param subject 主题
	 * @param content 邮件内容
	 * @param isHtml 是否是HTML邮件 true
	 * @param fileName 附件名字
	 * @param file 附件
     * @return Message
     */
	@Override
	public Messages sendAttachmentFileEmail(String[] receiver, String[] carbonCopy, String subject, String content,
			boolean isHtml, String fileName, File file) {
		Messages msg = new Messages();
        try {
        	msg = mailUtil.sendAttachmentFileEmail(fromMail, receiver, carbonCopy, subject, content, isHtml, fileName, file);
        } catch (Exception e) {
        	msg.setMsgDesc("出现异常："+e.getMessage());
            e.printStackTrace();
        }
		return msg;
	}

    /**
	 * 发送模板邮件
	 * @param receiver 接收者邮件地址
	 * @param carbonCopy 抄送者邮件地址(没有为null)
	 * @param subject 主题
	 * @param template 模板
	 * @param context 
     * @return Message
     */
	@Override
	public Messages sendTemplateEmail(String[] receiver, String[] carbonCopy, String subject,
			String template, Context context) {
		Messages msg = new Messages();
        try {
        	msg = mailUtil.sendTemplateEmail(fromMail, receiver, carbonCopy, subject, template, context);
        } catch (Exception e) {
        	msg.setMsgDesc("出现异常："+e.getMessage());
            e.printStackTrace();
        }
		return msg;
	}
}
