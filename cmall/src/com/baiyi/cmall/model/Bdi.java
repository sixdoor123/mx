package com.baiyi.cmall.model;

import java.io.Serializable;

/**
 * �Ƽ��̼�bdi
 * 
 * @author sunxy
 * @version ����ʱ�䣺2016��4��27�� ����2:12:00
 *
 */
public class Bdi implements Serializable{

	// id
	private int id = 0;
	// �̼�ͼƬ��ַ
	private String url = null;
	// �̼�����
	private String nm = null;

	/**Bdm
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

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
	 * @return the nm
	 */
	public String getNm() {
		return nm;
	}

	/**
	 * @param nm
	 *            the nm to set
	 */
	public void setNm(String nm) {
		this.nm = nm;
	}

}
