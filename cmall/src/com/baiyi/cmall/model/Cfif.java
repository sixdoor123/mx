package com.baiyi.cmall.model;

import java.io.Serializable;

/**
 * 商家详情-使用排序规则 cfif(未定，是否在商家详情中使用新排序)
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午3:31:25
 *
 */
public class Cfif implements Serializable {

	// 销量
	private int sc = 0;
	// 新品
	private int in = 0;
	// 分类
	private int ct = 0;
	// 每页显示的条数
	private int ps = 0;
	// 页码
	private int pi = 0;
	// 价格
	private int jp = 0;
	/**
	 * 1.全部商品, 2.上新（未定）, 3.促销 , 4.店铺动态,
	 */
	private int pt = 0;

	/**
	 * @return the sc
	 */
	public int getSc() {
		return sc;
	}

	/**
	 * @param sc
	 *            the sc to set
	 */
	public void setSc(int sc) {
		this.sc = sc;
	}

	/**
	 * @return the in
	 */
	public int getIn() {
		return in;
	}

	/**
	 * @param in
	 *            the in to set
	 */
	public void setIn(int in) {
		this.in = in;
	}

	/**
	 * @return the ct
	 */
	public int getCt() {
		return ct;
	}

	/**
	 * @param ct
	 *            the ct to set
	 */
	public void setCt(int ct) {
		this.ct = ct;
	}

	/**
	 * @return the ps
	 */
	public int getPs() {
		return ps;
	}

	/**
	 * @param ps
	 *            the ps to set
	 */
	public void setPs(int ps) {
		this.ps = ps;
	}

	/**
	 * @return the pi
	 */
	public int getPi() {
		return pi;
	}

	/**
	 * @param pi
	 *            the pi to set
	 */
	public void setPi(int pi) {
		this.pi = pi;
	}

	/**
	 * @return the jp
	 */
	public int getJp() {
		return jp;
	}

	/**
	 * @param jp
	 *            the jp to set
	 */
	public void setJp(int jp) {
		this.jp = jp;
	}

	/**
	 * @return the pt
	 */
	public int getPt() {
		return pt;
	}

	/**
	 * @param pt
	 *            the pt to set
	 */
	public void setPt(int pt) {
		this.pt = pt;
	}

}
