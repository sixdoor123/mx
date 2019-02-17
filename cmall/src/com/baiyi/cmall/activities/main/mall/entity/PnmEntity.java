package com.baiyi.cmall.activities.main.mall.entity;

/**
 * 产品价格规格模型
 * @author tangkun
 *
 */
public class PnmEntity {
	private int id;
	//规格名称
	private String nn;
	//规格单位
	private String nu;
	//序号
	private int on;
	//资源id
	private int ri;
	//资源类型
	private int rt;
	//规格ID
	private String nvi;
	
	
	/**
	 * @return the nvi
	 */
	public String getNvi() {
		return nvi;
	}
	/**
	 * @param nvi the nvi to set
	 */
	public void setNvi(String nvi) {
		this.nvi = nvi;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getRi() {
		return ri;
	}
	public void setRi(int ri) {
		this.ri = ri;
	}
	public int getRt() {
		return rt;
	}
	public void setRt(int rt) {
		this.rt = rt;
	}

}
