package com.baiyi.cmall.model;

import java.io.Serializable;

/**
 * �̼�����-ʹ��������� cfif(δ�����Ƿ����̼�������ʹ��������)
 * 
 * @author sunxy
 * @version ����ʱ�䣺2016��4��27�� ����3:31:25
 *
 */
public class Cfif implements Serializable {

	// ����
	private int sc = 0;
	// ��Ʒ
	private int in = 0;
	// ����
	private int ct = 0;
	// ÿҳ��ʾ������
	private int ps = 0;
	// ҳ��
	private int pi = 0;
	// �۸�
	private int jp = 0;
	/**
	 * 1.ȫ����Ʒ, 2.���£�δ����, 3.���� , 4.���̶�̬,
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
