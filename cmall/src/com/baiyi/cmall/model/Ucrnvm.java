package com.baiyi.cmall.model;

import java.io.Serializable;

/**
 * 购物车产品规格值ucrnvm
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午3:35:49
 *
 */
public class Ucrnvm implements Serializable {

	// id
	private long id = 0;
	// 购物车id
	private long ri = 0;
	// 产品规格名称
	private String nn = null;
	// 产品规格单位
	private String nu = null;
	// 购物车编号
	private int on = 0;
	// 产品规格值
	private String nv = null;
	// 规格值id
	private String nvi = null;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the ri
	 */
	public long getRi() {
		return ri;
	}

	/**
	 * @param ri
	 *            the ri to set
	 */
	public void setRi(long ri) {
		this.ri = ri;
	}

	/**
	 * @return the nn
	 */
	public String getNn() {
		return nn;
	}

	/**
	 * @param nn
	 *            the nn to set
	 */
	public void setNn(String nn) {
		this.nn = nn;
	}

	/**
	 * @return the nu
	 */
	public String getNu() {
		return nu;
	}

	/**
	 * @param nu
	 *            the nu to set
	 */
	public void setNu(String nu) {
		this.nu = nu;
	}

	/**
	 * @return the on
	 */
	public int getOn() {
		return on;
	}

	/**
	 * @param on
	 *            the on to set
	 */
	public void setOn(int on) {
		this.on = on;
	}

	/**
	 * @return the nv
	 */
	public String getNv() {
		return nv;
	}

	/**
	 * @param nv
	 *            the nv to set
	 */
	public void setNv(String nv) {
		this.nv = nv;
	}

	/**
	 * @return the nvi
	 */
	public String getNvi() {
		return nvi;
	}

	/**
	 * @param nvi
	 *            the nvi to set
	 */
	public void setNvi(String nvi) {
		this.nvi = nvi;
	}

}
