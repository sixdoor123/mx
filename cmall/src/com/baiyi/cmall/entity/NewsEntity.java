package com.baiyi.cmall.entity;

import java.io.Serializable;

/**
 * ��Ϣʵ��
 * 
 * @author lizl
 * 
 */
public class NewsEntity implements Serializable {

	// ��Ϣ��Դ
	private String newsSource;
	// ��Ϣ����
	private String newsContent;
	// ��Ϣʱ��
	private String newstime;
	// ��Ϣ״̬
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
