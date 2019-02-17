package com.baiyi.cmall.model;

import java.io.Serializable;
import java.security.KeyStore.PrivateKeyEntry;

/**
 * 基础关键字模板kcm
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午2:06:29
 *
 */
public class Kcm implements Serializable{

	// id
	private int id = 0;
	// 关键字名称
	private String ky = null;

	// 关键字值
	private String cv = null;
	// 序号
	private int no = 0;

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
	 * @return the ky
	 */
	public String getKy() {
		return ky;
	}

	/**
	 * @param ky
	 *            the ky to set
	 */
	public void setKy(String ky) {
		this.ky = ky;
	}

	/**
	 * @return the cv
	 */
	public String getCv() {
		return cv;
	}

	/**
	 * @param cv
	 *            the cv to set
	 */
	public void setCv(String cv) {
		this.cv = cv;
	}

	/**
	 * @return the no
	 */
	public int getNo() {
		return no;
	}

	/**
	 * @param no
	 *            the no to set
	 */
	public void setNo(int no) {
		this.no = no;
	}

}
