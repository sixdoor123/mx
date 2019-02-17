package com.baiyi.cmall.activities.main.home_pager.entity;

import java.io.Serializable;
import java.util.ArrayList;

import com.baiyi.cmall.entity.GoodsSourceInfo;

/**
 * 主界面的实体类
 * 
 * @author lizl
 * 
 */
public class MainEntity implements Serializable {

	// ID
	private String id;
	// 新闻图片地址
	private String picPath;
	// 链接地址
	private String url;
	// 标题
	private String title;

	// 新闻
	private ArrayList<MainEntity> newsInfoList;
	// 广告
	private ArrayList<ZiXunEntity> zixunInfoList;
	// 限时抢购
	private ArrayList<GoodsSourceInfo> flashCaleList;
	// 热门品牌
	private ArrayList<MainEntity> hotBusinessList;

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<MainEntity> getNewsInfoList() {
		return newsInfoList;
	}

	public void setNewsInfoList(ArrayList<MainEntity> newsInfoList) {
		this.newsInfoList = newsInfoList;
	}

	public ArrayList<ZiXunEntity> getZixunInfoList() {
		return zixunInfoList;
	}

	public void setZixunInfoList(ArrayList<ZiXunEntity> zixunInfoList) {
		this.zixunInfoList = zixunInfoList;
	}

	public ArrayList<GoodsSourceInfo> getFlashCaleList() {
		return flashCaleList;
	}

	public void setFlashCaleList(ArrayList<GoodsSourceInfo> flashCaleList) {
		this.flashCaleList = flashCaleList;
	}

	public ArrayList<MainEntity> getHotBusinessList() {
		return hotBusinessList;
	}

	public void setHotBusinessList(ArrayList<MainEntity> hotBusinessList) {
		this.hotBusinessList = hotBusinessList;
	}


}
