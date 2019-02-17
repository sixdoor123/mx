package com.baiyi.jj.app.entity;

import com.baiyi.core.database.AbstractBaseModel;

public class VideoMedieInfo extends AbstractBaseModel{
	
	private boolean wifi;
	private String media_backup_path;
	private String cover_img_url;
	private String media_remark;
	private String media_path;
	private String media_type;
	private String media_codec;
	private int media_width;
	private int media_height;
	private double media_duration;
	
	

	public String getMedia_codec() {
		return media_codec;
	}
	public void setMedia_codec(String media_codec) {
		this.media_codec = media_codec;
	}
	public int getMedia_width() {
		return media_width;
	}
	public void setMedia_width(int media_width) {
		this.media_width = media_width;
	}
	public int getMedia_height() {
		return media_height;
	}
	public void setMedia_height(int media_height) {
		this.media_height = media_height;
	}
	public double getMedia_duration() {
		return media_duration;
	}
	public void setMedia_duration(double media_duration) {
		this.media_duration = media_duration;
	}
	public boolean isWifi() {
		return wifi;
	}
	public void setWifi(boolean wifi) {
		this.wifi = wifi;
	}
	public String getMedia_backup_path() {
		return media_backup_path;
	}
	public void setMedia_backup_path(String media_backup_path) {
		this.media_backup_path = media_backup_path;
	}
	public String getCover_img_url() {
		return cover_img_url;
	}
	public void setCover_img_url(String cover_img_url) {
		this.cover_img_url = cover_img_url;
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
	public String getMedia_type() {
		return media_type;
	}
	public void setMedia_type(String media_type) {
		this.media_type = media_type;
	}
	
	

}
