package com.baiyi.cmall.model;

import java.io.Serializable;

import net.sf.json.JSONArray;

/**
 * 分页基础返回 bdm
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午1:32:49
 *
 */
public class Bdm implements Serializable{

	// 总数
	private long tt = 0;
	// 列表数据
	private  JSONArray li = null;
	/**
	 * @return the tt
	 */
	public long getTt() {
		return tt;
	}
	/**
	 * @param tt the tt to set
	 */
	public void setTt(long tt) {
		this.tt = tt;
	}
	/**
	 * @return the li
	 */
	public JSONArray getli() {
		return li;
	}
	/**
	 * @param li the li to set
	 */
	public void setli(JSONArray li) {
		this.li = li;
	}
	
	
	
}
