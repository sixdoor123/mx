package com.baiyi.cmall.model;

import java.io.Serializable;

import org.json.JSONArray;

/**
 * ��Ʒ������Ϣpbi
 * 
 * @author sunxy
 * @version ����ʱ�䣺2016��4��27�� ����2:16:19
 *
 */
public class Pbi implements Serializable {
	// id
	private long id = 0;
	// ����
	private String pn = null;
	// ������
	private String sn = null;

	// ����
	private String bn = null;
	// ����
	private String pp = null;
	// �����б�
	private JSONArray ggl = null;

	// ��ƷͼƬ
	private String purl = null;

	/**
	 * ��ǰ�û��Ƿ���Թ���˲�Ʒ null δ��¼ true ���Խ��й��� false Ϊ�Լ���Ʒ�����ɽ��й���
	 */
	private String io = null;

	// Int
	// ��ע��״̬Ϊ0��ʱ������ڴ����󵽷�������һ��
	private int ps = 0;

	// �����Զ���ʼ(�ǻ��߷�)
	private String dqk = null;
	// �����Զ�����(�ǻ��߷�)
	private String dqj = null;
	// ����ʱ��
	private String cd = null;
	// ��ʼʱ��
	private String sd = null;
	// ����ʱ��
	private String jd = null;
	
	// ����֧��
	private JSONArray psz = null;
	// izc ��Ʊ֧��
	private JSONArray izc = null;
	// ���ʽ֧��
	private JSONArray pmz = null;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the pn
	 */
	public String getPn() {
		return pn;
	}

	/**
	 * @param pn
	 *            the pn to set
	 */
	public void setPn(String pn) {
		this.pn = pn;
	}

	/**
	 * @return the sn
	 */
	public String getSn() {
		return sn;
	}

	/**
	 * @param sn
	 *            the sn to set
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * @return the bn
	 */
	public String getBn() {
		return bn;
	}

	/**
	 * @param bn
	 *            the bn to set
	 */
	public void setBn(String bn) {
		this.bn = bn;
	}

	/**
	 * @return the pp
	 */
	public String getPp() {
		return pp;
	}

	/**
	 * @param pp
	 *            the pp to set
	 */
	public void setPp(String pp) {
		this.pp = pp;
	}

	/**
	 * @return the ggl
	 */
	public JSONArray getGgl() {
		return ggl;
	}

	/**
	 * @param ggl
	 *            the ggl to set
	 */
	public void setGgl(JSONArray ggl) {
		this.ggl = ggl;
	}

	/**
	 * @return the purl
	 */
	public String getPurl() {
		return purl;
	}

	/**
	 * @param purl
	 *            the purl to set
	 */
	public void setPurl(String purl) {
		this.purl = purl;
	}

	/**
	 * @return the io
	 */
	public String getIo() {
		return io;
	}

	/**
	 * @param io
	 *            the io to set
	 */
	public void setIo(String io) {
		this.io = io;
	}

	/**
	 * @return the ps
	 */
	public int getPs() {
		return ps;
	}

	/**
	 * @param ps
	 *            the ps to set
	 */
	public void setPs(int ps) {
		this.ps = ps;
	}

	/**
	 * @return the dqk
	 */
	public String getDqk() {
		return dqk;
	}

	/**
	 * @param dqk
	 *            the dqk to set
	 */
	public void setDqk(String dqk) {
		this.dqk = dqk;
	}

	/**
	 * @return the dqj
	 */
	public String getDqj() {
		return dqj;
	}

	/**
	 * @param dqj
	 *            the dqj to set
	 */
	public void setDqj(String dqj) {
		this.dqj = dqj;
	}

	/**
	 * @return the cd
	 */
	public String getCd() {
		return cd;
	}

	/**
	 * @param cd
	 *            the cd to set
	 */
	public void setCd(String cd) {
		this.cd = cd;
	}

	/**
	 * @return the sd
	 */
	public String getSd() {
		return sd;
	}

	/**
	 * @param sd
	 *            the sd to set
	 */
	public void setSd(String sd) {
		this.sd = sd;
	}

	/**
	 * @return the jd
	 */
	public String getJd() {
		return jd;
	}

	/**
	 * @param jd
	 *            the jd to set
	 */
	public void setJd(String jd) {
		this.jd = jd;
	}

	/**
	 * @return the psz
	 */
	public JSONArray getPsz() {
		return psz;
	}

	/**
	 * @param psz the psz to set
	 */
	public void setPsz(JSONArray psz) {
		this.psz = psz;
	}

	/**
	 * @return the izc
	 */
	public JSONArray getIzc() {
		return izc;
	}

	/**
	 * @param izc the izc to set
	 */
	public void setIzc(JSONArray izc) {
		this.izc = izc;
	}

	/**
	 * @return the pmz
	 */
	public JSONArray getPmz() {
		return pmz;
	}

	/**
	 * @param pmz the pmz to set
	 */
	public void setPmz(JSONArray pmz) {
		this.pmz = pmz;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Pbi [id=" + id + ", pn=" + pn + ", sn=" + sn + ", bn=" + bn + ", pp=" + pp + ", ggl=" + ggl + ", purl="
				+ purl + ", io=" + io + ", ps=" + ps + ", dqk=" + dqk + ", dqj=" + dqj + ", cd=" + cd + ", sd=" + sd
				+ ", jd=" + jd + ", psz=" + psz + ", izc=" + izc + ", pmz=" + pmz + "]";
	}

	

}
