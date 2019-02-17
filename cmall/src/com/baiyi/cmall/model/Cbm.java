package com.baiyi.cmall.model;

import java.io.Serializable;

/**
 * 商家详情-店铺基础数据cbm
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午3:24:09
 *
 */
public class Cbm implements Serializable {

	// 公司ID
	private long id = 0;
	// 公司logoUrl
	private String curl = null;
	// 关注量
	private String cf = null;
	// 公司名称
	private String cn = null;
	// 全部商品数量
	private int ap = 0;
	// 新产品数量
	private int np = 0;
	// 促销数量
	private int as = 0;
	// 店铺动态
	private int cd = 0;

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
	 * @return the curl
	 */
	public String getCurl() {
		return curl;
	}

	/**
	 * @param curl
	 *            the curl to set
	 */
	public void setCurl(String curl) {
		this.curl = curl;
	}

	/**
	 * @return the cf
	 */
	public String getCf() {
		return cf;
	}

	/**
	 * @param cf
	 *            the cf to set
	 */
	public void setCf(String cf) {
		this.cf = cf;
	}

	/**
	 * @return the cn
	 */
	public String getCn() {
		return cn;
	}

	/**
	 * @param cn
	 *            the cn to set
	 */
	public void setCn(String cn) {
		this.cn = cn;
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
	 * @return the np
	 */
	public int getNp() {
		return np;
	}

	/**
	 * @param np
	 *            the np to set
	 */
	public void setNp(int np) {
		this.np = np;
	}

	/**
	 * @return the as
	 */
	public int getAs() {
		return as;
	}

	/**
	 * @param as
	 *            the as to set
	 */
	public void setAs(int as) {
		this.as = as;
	}

	/**
	 * @return the cd
	 */
	public int getCd() {
		return cd;
	}

	/**
	 * @param cd
	 *            the cd to set
	 */
	public void setCd(int cd) {
		this.cd = cd;
	}

}
