package com.baiyi.cmall.model;

import java.io.Serializable;
import java.security.KeyStore.PrivateKeyEntry;

/**
 * �����ֲ�ͼƬbim
 * 
 * @author sunxy
 * @version ����ʱ�䣺2016��4��27�� ����1:07:01
 *
 */
public class Bim implements Serializable {

	// ���ӵ�ַ
	private String url = null;
	// ͼƬ��ַ
	private String fp = null;
	// ���
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
