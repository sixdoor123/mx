package com.baiyi.cmall.activities.main.mall.entity;

/**
 * 商城-产品-商家基本信息
 * @author tangkun
 *
 */
public class CbiEntity {
	//商家id
	private int id;
	//公司名称
	private String cm;
	//公司logo地址
	private String lg;
	//简介
	private String cd;
	//关注人数
	private int fc;
	//全部商品数量
	private int ap;
	//全部评价数量
	private int ac;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCm() {
		return cm;
	}
	public void setCm(String cm) {
		this.cm = cm;
	}
	public String getLg() {
		return lg;
	}
	public void setLg(String lg) {
		this.lg = lg;
	}
	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	public int getFc() {
		return fc;
	}
	public void setFc(int fc) {
		this.fc = fc;
	}
	public int getAp() {
		return ap;
	}
	public void setAp(int ap) {
		this.ap = ap;
	}
	public int getAc() {
		return ac;
	}
	public void setAc(int ac) {
		this.ac = ac;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CbiEntity [id=" + id + ", cm=" + cm + ", lg=" + lg + ", cd=" + cd + ", fc=" + fc + ", ap=" + ap
				+ ", ac=" + ac + "]";
	}

}
