package com.baiyi.cmall.model;

import java.io.Serializable;
import java.util.List;

import com.baiyi.cmall.R;

/**
 * 属性rpv
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午2:34:14
 *
 */
public class Rpv implements Serializable {

	private String id;
	// 属性值
	private String pv = null;
	// 序号
	private int no = 0;
	// 属性名称
	private String pn = null;
	
	private List<Rpv> rpvs = null;

	/**
	 * 属性类型 1:工业属性 2:工艺属性 3:物理属性 4:化学属性
	 */
	private int pt = 0;
	private String ptname;
	// 属性单位
	private String pu = null;

	public List<Rpv> getRpvs() {
		return rpvs;
	}

	public void setRpvs(List<Rpv> rpvs) {
		this.rpvs = rpvs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the pv
	 */
	public String getPv() {
		return pv;
	}

	/**
	 * @param pv
	 *            the pv to set
	 */
	public void setPv(String pv) {
		this.pv = pv;
	}

	/**
	 * @return the no
	 */
	public int getNo() {
		return no;
	}

	/**
	 * @param no
	 *            the no to set
	 */
	public void setNo(int no) {
		this.no = no;
	}

	/**
	 * @return the pn
	 */
	public String getPn() {
		return pn;
	}

	/**
	 * @param pn
	 *            the pn to set
	 */
	public void setPn(String pn) {
		this.pn = pn;
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

	public String getPtname() {
		return ptname;
	}

	public void setPtname(String ptname) {
		this.ptname = ptname;
	}

	/**
	 * @return the pu
	 */
	public String getPu() {
		return pu;
	}

	/**
	 * @param pu
	 *            the pu to set
	 */
	public void setPu(String pu) {
		this.pu = pu;
	}

}
