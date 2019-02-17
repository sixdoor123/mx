package com.baiyi.cmall.model;

import java.io.Serializable;

/**
 * 过滤条件 fif
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午3:13:02
 *
 */
public class Fif implements Serializable {

	// 检索词
	private String key = null;
	// 分类
	private String cy = null;
	// 品牌
	private String bd = null;
	// 产地
	private String on = null;
	// 交割地
	private String dy = null;
	// 价格
	private String pr = null;
	// 每页显示的条数
	private int ps = 0;
	// 页码
	private int pi = 0;
	// 排序字段
	private int sf = 0;
	// 排序类型
	private int st = 0;
	// 其他属性
	private String pro = null;

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the cy
	 */
	public String getCy() {
		return cy;
	}

	/**
	 * @param cy
	 *            the cy to set
	 */
	public void setCy(String cy) {
		this.cy = cy;
	}

	/**
	 * @return the bd
	 */
	public String getBd() {
		return bd;
	}

	/**
	 * @param bd
	 *            the bd to set
	 */
	public void setBd(String bd) {
		this.bd = bd;
	}

	/**
	 * @return the on
	 */
	public String getOn() {
		return on;
	}

	/**
	 * @param on
	 *            the on to set
	 */
	public void setOn(String on) {
		this.on = on;
	}

	/**
	 * @return the dy
	 */
	public String getDy() {
		return dy;
	}

	/**
	 * @param dy
	 *            the dy to set
	 */
	public void setDy(String dy) {
		this.dy = dy;
	}

	/**
	 * @return the pr
	 */
	public String getPr() {
		return pr;
	}

	/**
	 * @param pr
	 *            the pr to set
	 */
	public void setPr(String pr) {
		this.pr = pr;
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
	 * @return the sf
	 */
	public int getSf() {
		return sf;
	}

	/**
	 * @param sf
	 *            the sf to set
	 */
	public void setSf(int sf) {
		this.sf = sf;
	}

	/**
	 * @return the st
	 */
	public int getSt() {
		return st;
	}

	/**
	 * @param st
	 *            the st to set
	 */
	public void setSt(int st) {
		this.st = st;
	}

	/**
	 * @return the pro
	 */
	public String getPro() {
		return pro;
	}

	/**
	 * @param pro
	 *            the pro to set
	 */
	public void setPro(String pro) {
		this.pro = pro;
	}

}
