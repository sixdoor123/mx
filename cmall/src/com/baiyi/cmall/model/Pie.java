package com.baiyi.cmall.model;

import java.io.Serializable;

import net.sf.json.JSONArray;

/**
 * 商城-商品评价pie
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午1:55:36
 *
 */
public class Pie implements Serializable{

	// 全部评价数量
	private int ac = 0;
	// 好评量
	private int gc = 0;
	// 差评量
	private int bc = 0;
	// 符合当前评论列表
	private JSONArray ctml = null;

	/**
	 * @return the ac
	 */
	public int getAc() {
		return ac;
	}

	/**
	 * @param ac
	 *            the ac to set
	 */
	public void setAc(int ac) {
		this.ac = ac;
	}

	/**
	 * @return the gc
	 */
	public int getGc() {
		return gc;
	}

	/**
	 * @param gc
	 *            the gc to set
	 */
	public void setGc(int gc) {
		this.gc = gc;
	}

	/**
	 * @return the bc
	 */
	public int getBc() {
		return bc;
	}

	/**
	 * @param bc
	 *            the bc to set
	 */
	public void setBc(int bc) {
		this.bc = bc;
	}

	/**
	 * @return the ctml
	 */
	public JSONArray getCtml() {
		return ctml;
	}

	/**
	 * @param ctml
	 *            the ctml to set
	 */
	public void setCtml(JSONArray ctml) {
		this.ctml = ctml;
	}

}
