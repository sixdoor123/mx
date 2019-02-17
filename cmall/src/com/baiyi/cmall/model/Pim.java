package com.baiyi.cmall.model;

import java.io.Serializable;
import java.util.List;

/**
 * 产品描述条目pim
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午2:23:28
 *
 */
public class Pim implements Serializable {

	// id
	private String id = null;
	// 描述条目名称
	private String in = null;
	// 产品id
	private long pi = 0;
	// 名称是否显示
	// 1 显示
	// 0 不显示
	private int nsf = 0;// Byte

	// 序号
	private int no = 0;

	private List<Pvm> pvms = null;

	public List<Pvm> getPvms() {
		return pvms;
	}

	public void setPvms(List<Pvm> pvms) {
		this.pvms = pvms;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the in
	 */
	public String getIn() {
		return in;
	}

	/**
	 * @param in
	 *            the in to set
	 */
	public void setIn(String in) {
		this.in = in;
	}

	/**
	 * @return the pi
	 */
	public long getPi() {
		return pi;
	}

	/**
	 * @param pi
	 *            the pi to set
	 */
	public void setPi(long pi) {
		this.pi = pi;
	}

	/**
	 * @return the nsf
	 */
	public int getNsf() {
		return nsf;
	}

	/**
	 * @param nsf
	 *            the nsf to set
	 */
	public void setNsf(int nsf) {
		this.nsf = nsf;
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

}
