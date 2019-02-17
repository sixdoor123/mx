package com.baiyi.cmall.model;

import java.io.Serializable;

/**
 * 二级分类sci
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午2:13:50
 *
 */
public class Sci implements Serializable{

	// categrayCode(分类代码)
	private int cc = 0;
	// 分类名称
	private int cn = 0;

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
	 * @return the cn
	 */
	public int getCn() {
		return cn;
	}

	/**
	 * @param cn
	 *            the cn to set
	 */
	public void setCn(int cn) {
		this.cn = cn;
	}

}
