package com.baiyi.cmall.model;

import java.io.Serializable;

/**
 * .统计tdm
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午2:10:00
 *
 */
public class Tdm implements Serializable {

	// 入驻商家数量
	private int cc = 0;
	// 委托采购次数
	private int pe = 0;
	// 累计成交量
	private int sc = 0;

	/**
	 * @return the cc
	 */
	public int getCc() {
		return cc;
	}

	/**
	 * @param cc
	 *            the cc to set
	 */
	public void setCc(int cc) {
		this.cc = cc;
	}

	/**
	 * @return the pe
	 */
	public int getPe() {
		return pe;
	}

	/**
	 * @param pe
	 *            the pe to set
	 */
	public void setPe(int pe) {
		this.pe = pe;
	}

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

}
