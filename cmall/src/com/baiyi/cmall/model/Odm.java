package com.baiyi.cmall.model;

import java.io.Serializable;
import java.util.List;

import net.sf.json.JSONArray;

/**
 * ��������odm
 * 
 * @author sunxy
 * @version ����ʱ�䣺2016��4��27�� ����3:06:22
 *
 */
public class Odm implements Serializable {

	private String id = null;
	// �������
	private String oi = null;
	// ������˾����
	private String cn = null;
	// �ջ���
	private String rp = null;
	// �ջ���ַ
	private String ad = null;
	// ��ϵ��ʽ
	private String ph = null;
	// ����״̬
	private int os = 0;
	// ��Ʊ����
	private String it = null;
	// ��Ʊ̧ͷ
	private String ic = null;
	// ��Ʊ����
	private String ii = null;
	// ��������-��Ʒ��Ϣ�б�
	private JSONArray pdml = null;
	
	private List<Pdm> pdms = null;
	
	//����
	private String dq = null;

	/**
	 * @return the dq
	 */
	public String getDq() {
		return dq;
	}

	/**
	 * @param dq the dq to set
	 */
	public void setDq(String dq) {
		this.dq = dq;
	}

	public List<Pdm> getPdms() {
		return pdms;
	}

	public void setPdms(List<Pdm> pdms) {
		this.pdms = pdms;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the oi
	 */
	public String getOi() {
		return oi;
	}

	/**
	 * @param oi
	 *            the oi to set
	 */
	public void setOi(String oi) {
		this.oi = oi;
	}

	/**
	 * @return the cn
	 */
	public String getCn() {
		return cn;
	}

	/**
	 * @param cn
	 *            the cn to set
	 */
	public void setCn(String cn) {
		this.cn = cn;
	}

	/**
	 * @return the rp
	 */
	public String getRp() {
		return rp;
	}

	/**
	 * @param rp
	 *            the rp to set
	 */
	public void setRp(String rp) {
		this.rp = rp;
	}

	/**
	 * @return the ad
	 */
	public String getAd() {
		return ad;
	}

	/**
	 * @param ad
	 *            the ad to set
	 */
	public void setAd(String ad) {
		this.ad = ad;
	}

	/**
	 * @return the ph
	 */
	public String getPh() {
		return ph;
	}

	/**
	 * @param ph
	 *            the ph to set
	 */
	public void setPh(String ph) {
		this.ph = ph;
	}

	/**
	 * @return the os
	 */
	public int getOs() {
		return os;
	}

	/**
	 * @param os
	 *            the os to set
	 */
	public void setOs(int os) {
		this.os = os;
	}

	/**
	 * @return the it
	 */
	public String getIt() {
		return it;
	}

	/**
	 * @param it
	 *            the it to set
	 */
	public void setIt(String it) {
		this.it = it;
	}

	/**
	 * @return the ic
	 */
	public String getIc() {
		return ic;
	}

	/**
	 * @param ic
	 *            the ic to set
	 */
	public void setIc(String ic) {
		this.ic = ic;
	}

	/**
	 * @return the ii
	 */
	public String getIi() {
		return ii;
	}

	/**
	 * @param ii
	 *            the ii to set
	 */
	public void setIi(String ii) {
		this.ii = ii;
	}

	/**
	 * @return the pdml
	 */
	public JSONArray getPdml() {
		return pdml;
	}

	/**
	 * @param pdml
	 *            the pdml to set
	 */
	public void setPdml(JSONArray pdml) {
		this.pdml = pdml;
	}

	@Override
	public String toString() {
		return "Odm [id=" + id + ", oi=" + oi + ", cn=" + cn + ", rp=" + rp + ", ad=" + ad + ", ph=" + ph + ", os=" + os
				+ ", it=" + it + ", ic=" + ic + ", ii=" + ii + ", pdml=" + pdml + ", pdms=" + pdms + "]";
	}

}
