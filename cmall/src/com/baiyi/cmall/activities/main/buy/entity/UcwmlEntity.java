package com.baiyi.cmall.activities.main.buy.entity;

import java.util.ArrayList;

/**
 * 购物车采购商品ucwm
 * 
 * @author tangkun
 *
 */
public class UcwmlEntity {
	private int id;
	// 产品id
	private int ri;
	// 数量
	private int rc;
	// 价格
	private String pr;
	// 资源名称
	private String pn;
	// 副名称
	private String sn;
	// 别名
	private String bn;
	// 图片路径
	private String ip;
	// 图片名称
	private String in;
	// 购物车产品规格值
	private ArrayList<UcrnvmEntity> ucrnvmList;

	// 是否被选中
	private boolean selected = false;

	// 商家ID;
	private int ci;

	/**
	 * @return the ci
	 */
	public int getCi() {
		return ci;
	}

	/**
	 * @param ci
	 *            the ci to set
	 */
	public void setCi(int ci) {
		this.ci = ci;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

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

	public int getRc() {
		return rc;
	}

	public void setRc(int rc) {
		this.rc = rc;
	}

	public String getPr() {
		return pr;
	}

	public void setPr(String pr) {
		this.pr = pr;
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIn() {
		return in;
	}

	public void setIn(String in) {
		this.in = in;
	}

	public ArrayList<UcrnvmEntity> getUcrnvmList() {
		return ucrnvmList;
	}

	public void setUcrnvmList(ArrayList<UcrnvmEntity> ucrnvmList) {
		this.ucrnvmList = ucrnvmList;
	}

	@Override
	public String toString() {
		return "UcwmlEntity [id=" + id + ", ri=" + ri + ", rc=" + rc + ", pr=" + pr + ", pn=" + pn + ", sn=" + sn
				+ ", bn=" + bn + ", ip=" + ip + ", in=" + in + ", ucrnvmList=" + ucrnvmList + ", selected=" + selected
				+ "]";
	}

}
