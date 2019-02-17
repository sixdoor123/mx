package com.baiyi.cmall.model;

import java.io.Serializable;

import net.sf.json.JSONArray;

/**
 * 商城接口数据sjd
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午1:58:25
 *
 */
public class Sjd implements Serializable{

	// 对应广告列表
	private JSONArray biml = null;
	// 对应二级分类列表
	private JSONArray scil = null;
	// 对应热销产品列表(hpi)
	private Bdm hpil = null;

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
	 * @return the scil
	 */
	public JSONArray getScil() {
		return scil;
	}

	/**
	 * @param scil
	 *            the scil to set
	 */
	public void setScil(JSONArray scil) {
		this.scil = scil;
	}

	/**
	 * @return the hpil
	 */
	public Bdm getHpil() {
		return hpil;
	}

	/**
	 * @param hpil
	 *            the hpil to set
	 */
	public void setHpil(Bdm hpil) {
		this.hpil = hpil;
	}

}
