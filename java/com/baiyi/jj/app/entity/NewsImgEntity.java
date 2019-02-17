/**
 * 
 */
package com.baiyi.jj.app.entity;

import com.baiyi.core.database.AbstractBaseModel;

/**
 * @author tangkun
 *
 */
public class NewsImgEntity extends AbstractBaseModel{
	//ͼƬ��ַ
	private String media_path;
	//��ʾ����
	private String picture_type;
	//ͼƬ��
	private int wideth;
	//ͼƬ��
	private int high;
	// 
	private String media_remark;
	private int placeholderid;

	public int getPlaceholderid() {
		return placeholderid;
	}

	public void setPlaceholderid(int placeholderid) {
		this.placeholderid = placeholderid;
	}

	public String getMedia_remark() {
		return media_remark;
	}
	public void setMedia_remark(String media_remark) {
		this.media_remark = media_remark;
	}
	public String getMedia_path() {
		return media_path;
	}
	public void setMedia_path(String media_path) {
		this.media_path = media_path;
	}
	public String getPicture_type() {
		return picture_type;
	}
	public void setPicture_type(String picture_type) {
		this.picture_type = picture_type;
	}
	public int getWideth() {
		return wideth;
	}
	public void setWideth(int wideth) {
		this.wideth = wideth;
	}
	public int getHigh() {
		return high;
	}
	public void setHigh(int high) {
		this.high = high;
	}

}
