package com.bluewhale.email.service;

import java.io.File;

import org.thymeleaf.context.Context;

import com.bluewhale.common.util.Messages;

/**
 * 发送邮件
 * @author curtin 2019-03-30 14:30:34
 * @version v1.0.0
 */
public interface EmailService {

	
	/**
     * 发送简单邮件
     * @param receiver 收件人
     * @param carbonCopy 抄送(没有为null)
     * @param subject 主题
     * @param content 内容
     * @return Message
     */
    Messages sendSimpleEmail(String[] receiver, String[] carbonCopy, String subject, String content);
    
    /**
     * 发送HTML邮件
     * @param receiver 收件人
     * @param carbonCopy 抄送(没有为null)
     * @param subject 主题
     * @param content 内容
     * @param isHtml 是否是HTML邮件 true
     * @return Message
     */
    Messages sendHtmlEmail(String[] receiver, String[] carbonCopy, String subject, String content, boolean isHtml);

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
    Messages sendAttachmentFileEmail(String[] receiver, String[] carbonCopy, String subject, String content, boolean isHtml, String fileName, File file);
    
    /**
	 * 发送模板邮件
	 * @param receiver 接收者邮件地址
	 * @param carbonCopy 抄送者邮件地址(没有为null)
	 * @param subject 主题
	 * @param template 模板
	 * @param context 
     * @return Message
     */
    Messages sendTemplateEmail(String[] receiver, String[] carbonCopy, String subject, String template, Context context);
    
}
