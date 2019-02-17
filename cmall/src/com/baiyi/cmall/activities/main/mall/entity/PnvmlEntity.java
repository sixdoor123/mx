package com.baiyi.cmall.activities.main.mall.entity;

/**
 * 价格规格值列表
 * 
 * @author tangkun
 *
 */
public class PnvmlEntity {
	private int id;
	// 规格id
	private String nvi;
	// 序号
	private int on;
	// 规格值
	private String nv;
	// 规格名称
	private String nn;
	private int ni;

	/**
	 * @return the ni
	 */
	public int getNi() {
		return ni;
	}

	/**
	 * @param ni
	 *            the ni to set
	 */
	public void setNi(int ni) {
		this.ni = ni;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
		setNvi(id+"");
	}

	public String getNvi() {
		return nvi;
	}

	public void setNvi(String nvi) {
		this.nvi = nvi;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PnvmlEntity [id=" + id + ", nvi=" + nvi + ", on=" + on + ", nv=" + nv + ", nn=" + nn + ", ni=" + ni
				+ "]";
	}

}
