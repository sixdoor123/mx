package com.baiyi.cmall.model;

import java.io.Serializable;
import java.util.List;

import net.sf.json.JSONArray;


/**
 * ��ҳ�������� sbd
 * 
 * @author sunxy
 * @version ����ʱ�䣺2016��4��27�� ����1:03:47
 *
 */
public class Sbd implements Serializable {

	// ����б�
	private List<Bim> bims = null;
	// �����б�
	private JSONArray nsil = null;
	// ��ʾ�����б�
	private JSONArray blml = null;
	// �̼��Ƽ��б�
	private JSONArray bdil = null;

	/**
	 * @return the bims
	 */
	public List<Bim> getBims() {
		return bims;
	}

	/**
	 * @param bims
	 *            the bims to set
	 */
	public void setBims(List<Bim> bims) {
		this.bims = bims;
	}

	/**
	 * @return the nsil
	 */
	public JSONArray getNsil() {
		return nsil;
	}

	/**
	 * @param nsil
	 *            the nsil to set
	 */
	public void setNsil(JSONArray nsil) {
		this.nsil = nsil;
	}

	/**
	 * @return the blml
	 */
	public JSONArray getBlml() {
		return blml;
	}

	/**
	 * @param blml
	 *            the blml to set
	 */
	public void setBlml(JSONArray blml) {
		this.blml = blml;
	}

	/**
	 * @return the bdil
	 */
	public JSONArray getBdil() {
		return bdil;
	}

	/**
	 * @param bdil
	 *            the bdil to set
	 */
	public void setBdil(JSONArray bdil) {
		this.bdil = bdil;
	}

}
