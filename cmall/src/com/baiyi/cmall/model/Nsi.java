package com.baiyi.cmall.model;

import java.io.Serializable;

/**
 * ����nsi
 * 
 * @author sunxy
 * @version ����ʱ�䣺2016��4��27�� ����2:08:40
 *
 */
public class Nsi implements Serializable{

	// id
	private int id = 0;
	// ����
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
