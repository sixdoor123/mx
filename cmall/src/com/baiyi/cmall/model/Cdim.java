package com.baiyi.cmall.model;

import java.io.Serializable;

/**
 * 商家详情-店铺详情 cdim
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午2:02:30
 *
 */
public class Cdim implements Serializable{

	// 店铺基本信息
	private Cbm cbm = null;
	// 店铺详情
	private Dpi dpi = null;

	/**
	 * @return the cbm
	 */
	public Cbm getCbm() {
		return cbm;
	}

	/**
	 * @param cbm
	 *            the cbm to set
	 */
	public void setCbm(Cbm cbm) {
		this.cbm = cbm;
	}

	/**
	 * @return the dpi
	 */
	public Dpi getDpi() {
		return dpi;
	}

	/**
	 * @param dpi
	 *            the dpi to set
	 */
	public void setDpi(Dpi dpi) {
		this.dpi = dpi;
	}

}
