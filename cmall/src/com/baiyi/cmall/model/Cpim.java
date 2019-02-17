package com.baiyi.cmall.model;

import java.io.Serializable;

import net.sf.json.JSONArray;

/**
 * 商家详情-产品列表 cpim
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午2:00:34
 *
 */
public class Cpim implements Serializable{

	// 店铺基础数据
	private Cbm cbm = null;
	// 产品列表
	private JSONArray pbi = null;

	/**
	 * @return the cbm
	 */
	public Cbm getCbm() {
		return cbm;
	}

	/**
	 * @param cbm
	 *            the cbm to set
	 */
	public void setCbm(Cbm cbm) {
		this.cbm = cbm;
	}

	/**
	 * @return the pbi
	 */
	public JSONArray getPbi() {
		return pbi;
	}

	/**
	 * @param pbi
	 *            the pbi to set
	 */
	public void setPbi(JSONArray pbi) {
		this.pbi = pbi;
	}

}
