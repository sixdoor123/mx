package com.baiyi.cmall.entity;

import java.io.Serializable;

/**
 * ��ҵ��̬ʵ����
 * @author lizl
 *
 */
public class IndustryTrendsEntity implements Serializable {

	// ��̬����
	private String mTvTrendsTitle;
	// ��̬����
	private String mTvTrendsContent;
	// ��̬����ʱ��
	private String mTvTrendsTime;
	
	public String getmTvTrendsTitle() {
		return mTvTrendsTitle;
	}
	public void setmTvTrendsTitle(String mTvTrendsTitle) {
		this.mTvTrendsTitle = mTvTrendsTitle;
	}
	public String getmTvTrendsContent() {
		return mTvTrendsContent;
	}
	public void setmTvTrendsContent(String mTvTrendsContent) {
		this.mTvTrendsContent = mTvTrendsContent;
	}
	public String getmTvTrendsTime() {
		return mTvTrendsTime;
	}
	public void setmTvTrendsTime(String mTvTrendsTime) {
		this.mTvTrendsTime = mTvTrendsTime;
	}

	

}
