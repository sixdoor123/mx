package com.baiyi.cmall.model;

import java.io.Serializable;
import net.sf.json.JSONArray;

/**
 * 商城-商品详情pif
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午1:53:07
 *
 */
public class Pif implements Serializable{

	// 产品相关图片列表
	private JSONArray biml = null;
	// 产品基本信息
	private Pbi pbi = null;
	// 商家基本信息
	private Cbi cbi = null;

	/**
	 * @return the biml
	 */
	public JSONArray getBiml() {
		return biml;
	}

	/**
	 * @param biml
	 *            the biml to set
	 */
	public void setBiml(JSONArray biml) {
		this.biml = biml;
	}

	/**
	 * @return the cbi
	 */
	public Cbi getCbi() {
		return cbi;
	}

	/**
	 * @param cbi
	 *            the cbi to set
	 */
	public void setCbi(Cbi cbi) {
		this.cbi = cbi;
	}

	/**
	 * @return the pbi
	 */
	public Pbi getPbi() {
		return pbi;
	}

	/**
	 * @param pbi
	 *            the pbi to set
	 */
	public void setPbi(Pbi pbi) {
		this.pbi = pbi;
	}

}
