package com.baiyi.cmall.model;

import java.io.Serializable;

/**
 * .ͳ��tdm
 * 
 * @author sunxy
 * @version ����ʱ�䣺2016��4��27�� ����2:10:00
 *
 */
public class Tdm implements Serializable {

	// ��פ�̼�����
	private int cc = 0;
	// ί�вɹ�����
	private int pe = 0;
	// �ۼƳɽ���
	private int sc = 0;

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
	 * @return the pe
	 */
	public int getPe() {
		return pe;
	}

	/**
	 * @param pe
	 *            the pe to set
	 */
	public void setPe(int pe) {
		this.pe = pe;
	}

	/**
	 * @return the sc
	 */
	public int getSc() {
		return sc;
	}

	/**
	 * @param sc
	 *            the sc to set
	 */
	public void setSc(int sc) {
		this.sc = sc;
	}

}
