package com.baiyi.cmall.model;

import java.io.Serializable;

import net.sf.json.JSONArray;

/**
 * �̳ǽӿ�����sjd
 * 
 * @author sunxy
 * @version ����ʱ�䣺2016��4��27�� ����1:58:25
 *
 */
public class Sjd implements Serializable{

	// ��Ӧ����б�
	private JSONArray biml = null;
	// ��Ӧ���������б�
	private JSONArray scil = null;
	// ��Ӧ������Ʒ�б�(hpi)
	private Bdm hpil = null;

	/**
	 * @return the biml
	 */
	public JSONArray getBiml() {
		return biml;
	}

	/**
	 * @param biml
	 *            the biml to set
	 */
	public void setBiml(JSONArray biml) {
		this.biml = biml;
	}

	/**
	 * @return the scil
	 */
	public JSONArray getScil() {
		return scil;
	}

	/**
	 * @param scil
	 *            the scil to set
	 */
	public void setScil(JSONArray scil) {
		this.scil = scil;
	}

	/**
	 * @return the hpil
	 */
	public Bdm getHpil() {
		return hpil;
	}

	/**
	 * @param hpil
	 *            the hpil to set
	 */
	public void setHpil(Bdm hpil) {
		this.hpil = hpil;
	}

}
