package com.baiyi.cmall.model;

import java.io.Serializable;

import net.sf.json.JSONArray;

/**
 * �̳�-��Ʒ����pie
 * 
 * @author sunxy
 * @version ����ʱ�䣺2016��4��27�� ����1:55:36
 *
 */
public class Pie implements Serializable{

	// ȫ����������
	private int ac = 0;
	// ������
	private int gc = 0;
	// ������
	private int bc = 0;
	// ���ϵ�ǰ�����б�
	private JSONArray ctml = null;

	/**
	 * @return the ac
	 */
	public int getAc() {
		return ac;
	}

	/**
	 * @param ac
	 *            the ac to set
	 */
	public void setAc(int ac) {
		this.ac = ac;
	}

	/**
	 * @return the gc
	 */
	public int getGc() {
		return gc;
	}

	/**
	 * @param gc
	 *            the gc to set
	 */
	public void setGc(int gc) {
		this.gc = gc;
	}

	/**
	 * @return the bc
	 */
	public int getBc() {
		return bc;
	}

	/**
	 * @param bc
	 *            the bc to set
	 */
	public void setBc(int bc) {
		this.bc = bc;
	}

	/**
	 * @return the ctml
	 */
	public JSONArray getCtml() {
		return ctml;
	}

	/**
	 * @param ctml
	 *            the ctml to set
	 */
	public void setCtml(JSONArray ctml) {
		this.ctml = ctml;
	}

}
