package com.baiyi.cmall.model;

import java.io.Serializable;

import net.sf.json.JSONArray;

/**
 * ��Ʒ����-����pid
 * 
 * @author sunxy
 * @version ����ʱ�䣺2016��4��27�� ����1:50:29
 *
 */
public class Pid implements Serializable {

	// ����
	private JSONArray rpvl = null;
	// ��Ʒ����
	private Pdi pdi = null;

	/**
	 * @return the rpvl
	 */
	public JSONArray getRpvl() {
		return rpvl;
	}

	/**
	 * @param rpvl
	 *            the rpvl to set
	 */
	public void setRpvl(JSONArray rpvl) {
		this.rpvl = rpvl;
	}

	/**
	 * @return the pdi
	 */
	public Pdi getPdi() {
		return pdi;
	}

	/**
	 * @param pdi
	 *            the pdi to set
	 */
	public void setPdi(Pdi pdi) {
		this.pdi = pdi;
	}

}
