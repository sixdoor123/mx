package com.baiyi.cmall.model;

import java.io.Serializable;

import net.sf.json.JSONArray;

/**
 * �̼�����-��Ʒ�б� cpim
 * 
 * @author sunxy
 * @version ����ʱ�䣺2016��4��27�� ����2:00:34
 *
 */
public class Cpim implements Serializable{

	// ���̻�������
	private Cbm cbm = null;
	// ��Ʒ�б�
	private JSONArray pbi = null;

	/**
	 * @return the cbm
	 */
	public Cbm getCbm() {
		return cbm;
	}

	/**
	 * @param cbm
	 *            the cbm to set
	 */
	public void setCbm(Cbm cbm) {
		this.cbm = cbm;
	}

	/**
	 * @return the pbi
	 */
	public JSONArray getPbi() {
		return pbi;
	}

	/**
	 * @param pbi
	 *            the pbi to set
	 */
	public void setPbi(JSONArray pbi) {
		this.pbi = pbi;
	}

}
