package com.baiyi.cmall.model;

import java.io.Serializable;

import net.sf.json.JSONArray;

/**
 * .����������ǩftm
 * 
 * @author sunxy
 * @version ����ʱ�䣺2016��4��27�� ����3:20:11
 *
 */
public class Ftm implements Serializable {

	// ���˱�ǩ����
	private String na = null;
	// ���˱�ǩֵ
	private String pro = null;
	// ���˱�ǩ����
	private String cp = null;
	// ���˱�ǩֵ�б�
	private JSONArray ftvml = null;

	/**
	 * @return the na
	 */
	public String getNa() {
		return na;
	}

	/**
	 * @param na
	 *            the na to set
	 */
	public void setNa(String na) {
		this.na = na;
	}

	/**
	 * @return the pro
	 */
	public String getPro() {
		return pro;
	}

	/**
	 * @param pro
	 *            the pro to set
	 */
	public void setPro(String pro) {
		this.pro = pro;
	}

	/**
	 * @return the cp
	 */
	public String getCp() {
		return cp;
	}

	/**
	 * @param cp
	 *            the cp to set
	 */
	public void setCp(String cp) {
		this.cp = cp;
	}

	/**
	 * @return the ftvml
	 */
	public JSONArray getFtvml() {
		return ftvml;
	}

	/**
	 * @param ftvml
	 *            the ftvml to set
	 */
	public void setFtvml(JSONArray ftvml) {
		this.ftvml = ftvml;
	}

}
