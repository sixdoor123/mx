package com.baiyi.cmall.activities.main.buy.entity;

import java.io.Serializable;

/**
 * 购物车采购商品ucwm
 * @author tangkun
 *
 */
public class UcrnvmEntity implements Serializable{
	private int id;
	//产品id
	private int ri;
	//规格
	private String nn;
	private String nu;
	private int on;
	private String nv;
	private int nvi;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRi() {
		return ri;
	}
	public void setRi(int ri) {
		this.ri = ri;
	}
	public String getNn() {
		return nn;
	}
	public void setNn(String nn) {
		this.nn = nn;
	}
	public String getNu() {
		return nu;
	}
	public void setNu(String nu) {
		this.nu = nu;
	}
	public int getOn() {
		return on;
	}
	public void setOn(int on) {
		this.on = on;
	}
	public String getNv() {
		return nv;
	}
	public void setNv(String nv) {
		this.nv = nv;
	}
	public int getNvi() {
		return nvi;
	}
	public void setNvi(int nvi) {
		this.nvi = nvi;
	}

}
