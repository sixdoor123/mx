package com.baiyi.cmall.model;

import java.io.Serializable;
import java.util.List;

import net.sf.json.JSONArray;


/**
 * 首页基础数据 sbd
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午1:03:47
 *
 */
public class Sbd implements Serializable {

	// 广告列表
	private List<Bim> bims = null;
	// 新闻列表
	private JSONArray nsil = null;
	// 显示抢购列表
	private JSONArray blml = null;
	// 商家推荐列表
	private JSONArray bdil = null;

	/**
	 * @return the bims
	 */
	public List<Bim> getBims() {
		return bims;
	}

	/**
	 * @param bims
	 *            the bims to set
	 */
	public void setBims(List<Bim> bims) {
		this.bims = bims;
	}

	/**
	 * @return the nsil
	 */
	public JSONArray getNsil() {
		return nsil;
	}

	/**
	 * @param nsil
	 *            the nsil to set
	 */
	public void setNsil(JSONArray nsil) {
		this.nsil = nsil;
	}

	/**
	 * @return the blml
	 */
	public JSONArray getBlml() {
		return blml;
	}

	/**
	 * @param blml
	 *            the blml to set
	 */
	public void setBlml(JSONArray blml) {
		this.blml = blml;
	}

	/**
	 * @return the bdil
	 */
	public JSONArray getBdil() {
		return bdil;
	}

	/**
	 * @param bdil
	 *            the bdil to set
	 */
	public void setBdil(JSONArray bdil) {
		this.bdil = bdil;
	}

}
