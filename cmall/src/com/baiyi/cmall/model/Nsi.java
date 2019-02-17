package com.baiyi.cmall.model;

import java.io.Serializable;

/**
 * 新闻nsi
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午2:08:40
 *
 */
public class Nsi implements Serializable{

	// id
	private int id = 0;
	// 标题
	private String tt = null;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the tt
	 */
	public String getTt() {
		return tt;
	}

	/**
	 * @param tt
	 *            the tt to set
	 */
	public void setTt(String tt) {
		this.tt = tt;
	}

}
