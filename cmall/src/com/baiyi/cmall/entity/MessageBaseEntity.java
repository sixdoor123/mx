package com.baiyi.cmall.entity;

import java.io.Serializable;
import java.util.List;

public class MessageBaseEntity implements Serializable{
	
	private int recordCount;
	private List<MessageEntity> dataList;
	private String newsName;
	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	public List<MessageEntity> getDataList() {
		return dataList;
	}
	public void setDataList(List<MessageEntity> dataList) {
		this.dataList = dataList;
	}
	public String getNewsName() {
		return newsName;
	}
	public void setNewsName(String newsName) {
		this.newsName = newsName;
	}

}
