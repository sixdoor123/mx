package com.baiyi.cmall.model;

import java.io.Serializable;

import net.sf.json.JSONArray;

/**
 * ����������Ϣodi
 * 
 * @author sunxy
 * @version ����ʱ�䣺2016��4��27�� ����3:00:09
 *
 */
public class Odi implements Serializable{

	// ����������˾����
	private String bl = null;
	// ʵ����
	private String pr = null;
	// ����״̬
	private String os = null;
	// �����в�Ʒ��Ϣ�б�
	private JSONArray opil = null;

	/**
	 * @return the bl
	 */
	public String getBl() {
		return bl;
	}

	/**
	 * @param bl
	 *            the bl to set
	 */
	public void setBl(String bl) {
		this.bl = bl;
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
	 * @return the os
	 */
	public String getOs() {
		return os;
	}

	/**
	 * @param os
	 *            the os to set
	 */
	public void setOs(String os) {
		this.os = os;
	}

	/**
	 * @return the opil
	 */
	public JSONArray getOpil() {
		return opil;
	}

	/**
	 * @param opil
	 *            the opil to set
	 */
	public void setOpil(JSONArray opil) {
		this.opil = opil;
	}

}
