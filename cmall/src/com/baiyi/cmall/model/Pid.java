package com.baiyi.cmall.model;

import java.io.Serializable;

import net.sf.json.JSONArray;

/**
 * 商品详情-详情pid
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午1:50:29
 *
 */
public class Pid implements Serializable {

	// 属性
	private JSONArray rpvl = null;
	// 产品描述
	private Pdi pdi = null;

	/**
	 * @return the rpvl
	 */
	public JSONArray getRpvl() {
		return rpvl;
	}

	/**
	 * @param rpvl
	 *            the rpvl to set
	 */
	public void setRpvl(JSONArray rpvl) {
		this.rpvl = rpvl;
	}

	/**
	 * @return the pdi
	 */
	public Pdi getPdi() {
		return pdi;
	}

	/**
	 * @param pdi
	 *            the pdi to set
	 */
	public void setPdi(Pdi pdi) {
		this.pdi = pdi;
	}

}
