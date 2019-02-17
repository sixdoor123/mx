package com.baiyi.cmall.entity;

import java.io.Serializable;

/**
 * 消息实体
 * 
 * @author lizl
 * 
 */
public class NewsEntity implements Serializable {

	// 消息来源
	private String newsSource;
	// 消息内容
	private String newsContent;
	// 消息时间
	private String newstime;
	// 消息状态
	private int newsState;

	public String getNewsSource() {
		return newsSource;
	}

	public void setNewsSource(String newsSource) {
		this.newsSource = newsSource;
	}

	public String getNewsContent() {
		return newsContent;
	}

	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

	public String getNewstime() {
		return newstime;
	}

	public void setNewstime(String newstime) {
		this.newstime = newstime;
	}

	public int getNewsState() {
		return newsState;
	}

	public void setNewsState(int newsState) {
		this.newsState = newsState;
	}

}
