package com.baiyi.cmall.model;

import java.io.Serializable;

/**
 * 过滤标签值ftvm
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午3:18:18
 *
 */
public class Ftvm implements Serializable {

	private String id = null;
	// 过滤显示文本
	private String ds = null;
	// 过滤值
	private String bd = null;
	
	//是否被选中
	private boolean isSelected;
	
	
	/**
	 * @return the isSelected
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * @param isSelected the isSelected to set
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the ds
	 */
	public String getDs() {
		return ds;
	}

	/**
	 * @param ds
	 *            the ds to set
	 */
	public void setDs(String ds) {
		this.ds = ds;
	}

	/**
	 * @return the bd
	 */
	public String getBd() {
		return bd;
	}

	/**
	 * @param bd
	 *            the bd to set
	 */
	public void setBd(String bd) {
		this.bd = bd;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Ftvm [id=" + id + ", ds=" + ds + ", bd=" + bd + "]";
	}

}
