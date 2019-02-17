package com.baiyi.cmall.model;

import java.io.Serializable;
import java.security.KeyStore.PrivateKeyEntry;

/**
 * 基础轮播图片bim
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午1:07:01
 *
 */
public class Bim implements Serializable {

	// 连接地址
	private String url = null;
	// 图片地址
	private String fp = null;
	// 序号
	private String no = null;

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
	 * @return the fp
	 */
	public String getFp() {
		return fp;
	}

	/**
	 * @param fp
	 *            the fp to set
	 */
	public void setFp(String fp) {
		this.fp = fp;
	}

	/**
	 * @return the no
	 */
	public String getNo() {
		return no;
	}

	/**
	 * @param no
	 *            the no to set
	 */
	public void setNo(String no) {
		this.no = no;
	}

}
