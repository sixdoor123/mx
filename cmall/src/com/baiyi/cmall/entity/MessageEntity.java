/**
 * 
 */
package com.baiyi.cmall.entity;

import java.io.Serializable;

public class MessageEntity implements Serializable{
	
	//消息类型
	private String msgType;
	// 消息内容
	private String msgContent;
	// 消息状态
	private String msgState;
	// 消息标题
	private String msgTitle;
	// 消息创建时间
	private String msgCreateDate;
	// 用户id
	private String msgUserId;
	// 
	private String msgSendId;
	// 消息Id
	private String msgId;
	// 消息更新时间
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

