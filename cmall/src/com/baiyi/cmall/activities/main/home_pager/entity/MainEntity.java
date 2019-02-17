package com.baiyi.cmall.activities.main.home_pager.entity;

import java.io.Serializable;
import java.util.ArrayList;

import com.baiyi.cmall.entity.GoodsSourceInfo;

/**
 * �������ʵ����
 * 
 * @author lizl
 * 
 */
public class MainEntity implements Serializable {

	// ID
	private String id;
	// ����ͼƬ��ַ
	private String picPath;
	// ���ӵ�ַ
	private String url;
	// ����
	private String title;

	// ����
	private ArrayList<MainEntity> newsInfoList;
	// ���
	private ArrayList<ZiXunEntity> zixunInfoList;
	// ��ʱ����
	private ArrayList<GoodsSourceInfo> flashCaleList;
	// ����Ʒ��
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
