package com.baiyi.cmall.model;

import java.io.Serializable;

/**
 * 商家基本信息cbi
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午2:20:11
 *
 */
public class Cbi implements Serializable{

	// id
	private long id = 0;
	// 公司名称
	private String cm = null;
	// 公司logo地址
	private String lg = null;
	// 简介
	private String cd = null;
	// 关注人数
	private int fc = 0;
	// 全部商品数量
	private int ap = 0;
	// 全部贫家数量
	private int ac = 0;

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
	 * @return the cm
	 */
	public String getCm() {
		return cm;
	}

	/**
	 * @param cm
	 *            the cm to set
	 */
	public void setCm(String cm) {
		this.cm = cm;
	}

	/**
	 * @return the lg
	 */
	public String getLg() {
		return lg;
	}

	/**
	 * @param lg
	 *            the lg to set
	 */
	public void setLg(String lg) {
		this.lg = lg;
	}

	/**
	 * @return the cd
	 */
	public String getCd() {
		return cd;
	}

	/**
	 * @param cd
	 *            the cd to set
	 */
	public void setCd(String cd) {
		this.cd = cd;
	}

	/**
	 * @return the fc
	 */
	public int getFc() {
		return fc;
	}

	/**
	 * @param fc
	 *            the fc to set
	 */
	public void setFc(int fc) {
		this.fc = fc;
	}

	/**
	 * @return the ap
	 */
	public int getAp() {
		return ap;
	}

	/**
	 * @param ap
	 *            the ap to set
	 */
	public void setAp(int ap) {
		this.ap = ap;
	}

	/**
	 * @return the ac
	 */
	public int getAc() {
		return ac;
	}

	/**
	 * @param ac
	 *            the ac to set
	 */
	public void setAc(int ac) {
		this.ac = ac;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Cbi [id=" + id + ", cm=" + cm + ", lg=" + lg + ", cd=" + cd + ", fc=" + fc + ", ap=" + ap + ", ac=" + ac
				+ "]";
	}

	
}
