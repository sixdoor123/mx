package com.baiyi.cmall.activities.main.mall.entity;

import java.util.ArrayList;

//商城-商品-基本信息
public class PbiEntity {

	private int id;
	// 名称
	private String pn;
	// 副名称
	private String sn;
	// 别名
	private String bn;
	// 单价
	private String pp;
	// 属性
	private ArrayList<String> ggl;
	// 属性字符窜
	private String ggs;
	// 产品图片
	private String purl;

	/*
	 * 当前用户是否可以购买此产品
	 *
	 * 1.null 未登录
	 * 
	 * 2.true 可以进行购买
	 * 
	 * 3.false 为自己产品，不可进行购买
	 */
	private String io;

	/**
	 * @return the io
	 */
	public String getIo() {
		return io;
	}

	/**
	 * @param io
	 *            the io to set
	 */
	public void setIo(String io) {
		this.io = io;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getBn() {
		return bn;
	}

	public void setBn(String bn) {
		this.bn = bn;
	}

	public String getPp() {
		return pp;
	}

	public void setPp(String pp) {
		this.pp = pp;
	}

	public ArrayList<String> getGgl() {
		return ggl;
	}

	public void setGgl(ArrayList<String> ggl) {
		this.ggl = ggl;
	}

	public String getPurl() {
		return purl;
	}

	public void setPurl(String purl) {
		this.purl = purl;
	}

	public String getGgs() {
		return ggs;
	}

	public void setGgs(String ggs) {
		this.ggs = ggs;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PbiEntity [id=" + id + ", pn=" + pn + ", sn=" + sn + ", bn=" + bn + ", pp=" + pp + ", ggl=" + ggl
				+ ", ggs=" + ggs + ", purl=" + purl + ", io=" + io + "]";
	}

}
