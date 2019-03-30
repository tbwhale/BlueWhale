package com.bluewhale.common.util;

import java.io.Serializable;

/**
 * 消息定义
 * @author curtin
 * @date 2019-03-30 14:46:40
 */
public class Messages implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5660655433656317676L;

	/**
     * 返回码
     */
    private String msgCode;
    
    /**
     * 返回消息
     */
    private String msgDesc;

    
    public Messages() {}
	public Messages(String msgCode, String msgDesc) {
		super();
		this.msgCode = msgCode;
		this.msgDesc = msgDesc;
	}

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	public String getMsgDesc() {
		return msgDesc;
	}

	public void setMsgDesc(String msgDesc) {
		this.msgDesc = msgDesc;
	}
    
	@Override
	public String toString() {
		return "Messages [msgCode=" + msgCode + ", msgDesc=" + msgDesc + "]";
	}
}
