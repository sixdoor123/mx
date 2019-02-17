package com.baiyi.cmall.model;

import java.io.Serializable;

/**
 * 热销产品（推荐产品）hpi
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午2:15:06
 *
 */
public class Hpi implements Serializable{

	// 产品图片
	private String url = null;
	// 产品名称
	private String pn = null;

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the pn
	 */
	public String getPn() {
		return pn;
	}

	/**
	 * @param pn
	 *            the pn to set
	 */
	public void setPn(String pn) {
		this.pn = pn;
	}

}
