package com.baiyi.cmall.model;

import java.io.Serializable;
import net.sf.json.JSONArray;

/**
 * �̳�-��Ʒ����pif
 * 
 * @author sunxy
 * @version ����ʱ�䣺2016��4��27�� ����1:53:07
 *
 */
public class Pif implements Serializable{

	// ��Ʒ���ͼƬ�б�
	private JSONArray biml = null;
	// ��Ʒ������Ϣ
	private Pbi pbi = null;
	// �̼һ�����Ϣ
	private Cbi cbi = null;

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
	 * @return the cbi
	 */
	public Cbi getCbi() {
		return cbi;
	}

	/**
	 * @param cbi
	 *            the cbi to set
	 */
	public void setCbi(Cbi cbi) {
		this.cbi = cbi;
	}

	/**
	 * @return the pbi
	 */
	public Pbi getPbi() {
		return pbi;
	}

	/**
	 * @param pbi
	 *            the pbi to set
	 */
	public void setPbi(Pbi pbi) {
		this.pbi = pbi;
	}

}
