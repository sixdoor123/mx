package com.baiyi.cmall.model;

import java.io.Serializable;

import net.sf.json.JSONArray;

/**
 * 订单基础信息odi
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午3:00:09
 *
 */
public class Odi implements Serializable{

	// 顶大所属公司名称
	private String bl = null;
	// 实付款
	private String pr = null;
	// 订单状态
	private String os = null;
	// 订单中产品信息列表
	private JSONArray opil = null;

	/**
	 * @return the bl
	 */
	public String getBl() {
		return bl;
	}

	/**
	 * @param bl
	 *            the bl to set
	 */
	public void setBl(String bl) {
		this.bl = bl;
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
	 * @return the os
	 */
	public String getOs() {
		return os;
	}

	/**
	 * @param os
	 *            the os to set
	 */
	public void setOs(String os) {
		this.os = os;
	}

	/**
	 * @return the opil
	 */
	public JSONArray getOpil() {
		return opil;
	}

	/**
	 * @param opil
	 *            the opil to set
	 */
	public void setOpil(JSONArray opil) {
		this.opil = opil;
	}

}
