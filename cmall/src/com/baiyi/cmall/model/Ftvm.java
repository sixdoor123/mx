package com.baiyi.cmall.model;

import java.io.Serializable;

/**
 * ���˱�ǩֵftvm
 * 
 * @author sunxy
 * @version ����ʱ�䣺2016��4��27�� ����3:18:18
 *
 */
public class Ftvm implements Serializable {

	private String id = null;
	// ������ʾ�ı�
	private String ds = null;
	// ����ֵ
	private String bd = null;
	
	//�Ƿ�ѡ��
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
