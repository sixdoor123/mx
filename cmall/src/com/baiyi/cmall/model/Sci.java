package com.baiyi.cmall.model;

import java.io.Serializable;

/**
 * ��������sci
 * 
 * @author sunxy
 * @version ����ʱ�䣺2016��4��27�� ����2:13:50
 *
 */
public class Sci implements Serializable{

	// categrayCode(�������)
	private int cc = 0;
	// ��������
	private int cn = 0;

	/**
	 * @return the cc
	 */
	public int getCc() {
		return cc;
	}

	/**
	 * @param cc
	 *            the cc to set
	 */
	public void setCc(int cc) {
		this.cc = cc;
	}

	/**
	 * @return the cn
	 */
	public int getCn() {
		return cn;
	}

	/**
	 * @param cn
	 *            the cn to set
	 */
	public void setCn(int cn) {
		this.cn = cn;
	}

}
