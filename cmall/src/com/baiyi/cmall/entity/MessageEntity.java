/**
 * 
 */
package com.baiyi.cmall.entity;

import java.io.Serializable;

public class MessageEntity implements Serializable{
	
	//��Ϣ����
	private String msgType;
	// ��Ϣ����
	private String msgContent;
	// ��Ϣ״̬
	private String msgState;
	// ��Ϣ����
	private String msgTitle;
	// ��Ϣ����ʱ��
	private String msgCreateDate;
	// �û�id
	private String msgUserId;
	// 
	private String msgSendId;
	// ��ϢId
	private String msgId;
	// ��Ϣ����ʱ��
	private String msgUpdateDate;
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getMsgState() {
		return msgState;
	}
	public void setMsgState(String msgState) {
		this.msgState = msgState;
	}
	public String getMsgTitle() {
		return msgTitle;
	}
	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
	public String getMsgCreateDate() {
		return msgCreateDate;
	}
	public void setMsgCreateDate(String msgCreateDate) {
		this.msgCreateDate = msgCreateDate;
	}
	public String getMsgUserId() {
		return msgUserId;
	}
	public void setMsgUserId(String msgUserId) {
		this.msgUserId = msgUserId;
	}
	public String getMsgSendId() {
		return msgSendId;
	}
	public void setMsgSendId(String msgSendId) {
		this.msgSendId = msgSendId;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getMsgUpdateDate() {
		return msgUpdateDate;
	}
	public void setMsgUpdateDate(String msgUpdateDate) {
		this.msgUpdateDate = msgUpdateDate;
	}
	
	
	 

}

