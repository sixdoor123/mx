package com.baiyi.cmall.activities.main.business.entity;

import java.io.Serializable;

/**
 * 商家详情-店铺基础数据cbm
 * @author tangkun
 *
 */
public class CbmEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//id
	private int id;
	//公司logoUrl
	private String curl;
	//关注量
	private int cf;
	//公司名称
	private String cn;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCurl() {
		return curl;
	}
	public void setCurl(String curl) {
		this.curl = curl;
	}
	public int getCf() {
		return cf;
	}
	public void setCf(int cf) {
		this.cf = cf;
	}
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}

}
