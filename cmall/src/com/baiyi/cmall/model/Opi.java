package com.baiyi.cmall.model;

import java.io.Serializable;

/**
 * 订单列表-产品基本信息opi
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午3:11:07
 *
 */
public class Opi implements Serializable {

	// 产品图片
	private String purl = null;
	// 产品名称
	private String pn = null;
	// 产品单价
	private String pr = null;
	// 数量
	private String co = null;

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

}
