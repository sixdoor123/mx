package com.baiyi.cmall.model;

import java.io.Serializable;

import net.sf.json.JSONArray;

/**
 * .过滤条件标签ftm
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午3:20:11
 *
 */
public class Ftm implements Serializable {

	// 过滤标签名称
	private String na = null;
	// 过滤标签值
	private String pro = null;
	// 过滤标签属性
	private String cp = null;
	// 过滤标签值列表
	private JSONArray ftvml = null;

	/**
	 * @return the na
	 */
	public String getNa() {
		return na;
	}

	/**
	 * @param na
	 *            the na to set
	 */
	public void setNa(String na) {
		this.na = na;
	}

	/**
	 * @return the pro
	 */
	public String getPro() {
		return pro;
	}

	/**
	 * @param pro
	 *            the pro to set
	 */
	public void setPro(String pro) {
		this.pro = pro;
	}

	/**
	 * @return the cp
	 */
	public String getCp() {
		return cp;
	}

	/**
	 * @param cp
	 *            the cp to set
	 */
	public void setCp(String cp) {
		this.cp = cp;
	}

	/**
	 * @return the ftvml
	 */
	public JSONArray getFtvml() {
		return ftvml;
	}

	/**
	 * @param ftvml
	 *            the ftvml to set
	 */
	public void setFtvml(JSONArray ftvml) {
		this.ftvml = ftvml;
	}

}
