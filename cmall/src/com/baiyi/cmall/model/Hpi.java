package com.baiyi.cmall.model;

import java.io.Serializable;

/**
 * ������Ʒ���Ƽ���Ʒ��hpi
 * 
 * @author sunxy
 * @version ����ʱ�䣺2016��4��27�� ����2:15:06
 *
 */
public class Hpi implements Serializable{

	// ��ƷͼƬ
	private String url = null;
	// ��Ʒ����
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
