package com.baiyi.cmall.model;

import java.io.Serializable;
import java.security.KeyStore.PrivateKeyEntry;

/**
 * �����ؼ���ģ��kcm
 * 
 * @author sunxy
 * @version ����ʱ�䣺2016��4��27�� ����2:06:29
 *
 */
public class Kcm implements Serializable{

	// id
	private int id = 0;
	// �ؼ�������
	private String ky = null;

	// �ؼ���ֵ
	private String cv = null;
	// ���
	private int no = 0;

	/**
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
	 * @return the ky
	 */
	public String getKy() {
		return ky;
	}

	/**
	 * @param ky
	 *            the ky to set
	 */
	public void setKy(String ky) {
		this.ky = ky;
	}

	/**
	 * @return the cv
	 */
	public String getCv() {
		return cv;
	}

	/**
	 * @param cv
	 *            the cv to set
	 */
	public void setCv(String cv) {
		this.cv = cv;
	}

	/**
	 * @return the no
	 */
	public int getNo() {
		return no;
	}

	/**
	 * @param no
	 *            the no to set
	 */
	public void setNo(int no) {
		this.no = no;
	}

}
