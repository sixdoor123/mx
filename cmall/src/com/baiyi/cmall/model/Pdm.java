package com.baiyi.cmall.model;

import java.io.Serializable;

import org.json.JSONArray;
/**
 * 订单详情-产品信息pdm
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午3:02:41
 *
 */
public class Pdm implements Serializable {

	private String id = null;
	// 订单名称
	private String pn = null;
	// 图片路径
	private String purl = null;
	// 别名
	private String bn = null;
	// 规格列表
	private JSONArray ggl = null;
	// 数量
	private String co = null;
	// 单价
	private String pr = null;
	// 总价
	private String am = null;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
	 * @return the co
	 */
	public String getCo() {
		return co;
	}

	/**
	 * @param co
	 *            the co to set
	 */
	public void setCo(String co) {
		this.co = co;
	}

	/**
	 * @return the pr
	 */
	public String getPr() {
		return pr;
	}

	/**
	 * @param pr
	 *            the pr to set
	 */
	public void setPr(String pr) {
		this.pr = pr;
	}

	/**
	 * @return the am
	 */
	public String getAm() {
		return am;
	}

	/**
	 * @param am
	 *            the am to set
	 */
	public void setAm(String am) {
		this.am = am;
	}

	@Override
	public String toString() {
		return "Pdm [id=" + id + ", pn=" + pn + ", purl=" + purl + ", bn=" + bn + ", ggl=" + ggl + ", co=" + co
				+ ", pr=" + pr + ", am=" + am + "]";
	}

}
