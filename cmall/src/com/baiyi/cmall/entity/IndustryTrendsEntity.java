package com.baiyi.cmall.entity;

import java.io.Serializable;

/**
 * 行业动态实体类
 * @author lizl
 *
 */
public class IndustryTrendsEntity implements Serializable {

	// 动态标题
	private String mTvTrendsTitle;
	// 动态内容
	private String mTvTrendsContent;
	// 动态发布时间
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
