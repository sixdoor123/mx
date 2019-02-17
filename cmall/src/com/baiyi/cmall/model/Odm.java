package com.baiyi.cmall.model;

import java.io.Serializable;
import java.util.List;

import net.sf.json.JSONArray;

/**
 * 订单详情odm
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午3:06:22
 *
 */
public class Odm implements Serializable {

	private String id = null;
	// 订单编号
	private String oi = null;
	// 所属公司名称
	private String cn = null;
	// 收货人
	private String rp = null;
	// 收货地址
	private String ad = null;
	// 联系方式
	private String ph = null;
	// 订单状态
	private int os = 0;
	// 发票类型
	private String it = null;
	// 发票抬头
	private String ic = null;
	// 发票内容
	private String ii = null;
	// 订单详情-产品信息列表
	private JSONArray pdml = null;
	
	private List<Pdm> pdms = null;
	
	//地区
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
