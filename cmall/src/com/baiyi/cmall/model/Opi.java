package com.baiyi.cmall.model;

import java.io.Serializable;

/**
 * �����б�-��Ʒ������Ϣopi
 * 
 * @author sunxy
 * @version ����ʱ�䣺2016��4��27�� ����3:11:07
 *
 */
public class Opi implements Serializable {

	// ��ƷͼƬ
	private String purl = null;
	// ��Ʒ����
	private String pn = null;
	// ��Ʒ����
	private String pr = null;
	// ����
	private String co = null;

	/**
	 * @return the purl
	 */
	public String getPurl() {
		return purl;
	}

	/**
	 * @param purl
	 *            the purl to set
	 */
	public void setPurl(String purl) {
		this.purl = purl;
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

	/**
	 * @return the pr
	 */
	public String getPr() {
		return pr;
	}

	/**
	 * @param pr
	 *            the pr to set
	 */
	public void setPr(String pr) {
		this.pr = pr;
	}

	/**
	 * @return the co
	 */
	public String getCo() {
		return co;
	}

	/**
	 * @param co
	 *            the co to set
	 */
	public void setCo(String co) {
		this.co = co;
	}

}
