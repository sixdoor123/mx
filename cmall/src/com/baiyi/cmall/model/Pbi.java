package com.baiyi.cmall.model;

import java.io.Serializable;

import org.json.JSONArray;

/**
 * 产品基本信息pbi
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午2:16:19
 *
 */
public class Pbi implements Serializable {
	// id
	private long id = 0;
	// 名称
	private String pn = null;
	// 副名称
	private String sn = null;

	// 别名
	private String bn = null;
	// 单价
	private String pp = null;
	// 属性列表
	private JSONArray ggl = null;

	// 产品图片
	private String purl = null;

	/**
	 * 当前用户是否可以购买此产品 null 未登录 true 可以进行购买 false 为自己产品，不可进行购买
	 */
	private String io = null;

	// Int
	// 备注：状态为0的时候仅仅在创建后到发布出现一次
	private int ps = 0;

	// 到期自动开始(是或者否)
	private String dqk = null;
	// 到期自动结束(是或者否)
	private String dqj = null;
	// 创建时间
	private String cd = null;
	// 开始时间
	private String sd = null;
	// 结束时间
	private String jd = null;
	
	// 配送支持
	private JSONArray psz = null;
	// izc 发票支持
	private JSONArray izc = null;
	// 付款方式支持
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
