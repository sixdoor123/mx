package com.baiyi.cmall.model;

import java.io.Serializable;
import java.util.List;

/**
 * ��Ʒ������Ŀpim
 * 
 * @author sunxy
 * @version ����ʱ�䣺2016��4��27�� ����2:23:28
 *
 */
public class Pim implements Serializable {

	// id
	private String id = null;
	// ������Ŀ����
	private String in = null;
	// ��Ʒid
	private long pi = 0;
	// �����Ƿ���ʾ
	// 1 ��ʾ
	// 0 ����ʾ
	private int nsf = 0;// Byte

	// ���
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
