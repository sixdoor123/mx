package com.baiyi.cmall.model;

import java.io.Serializable;

/**
 * �̼�����-���̻�������cbm
 * 
 * @author sunxy
 * @version ����ʱ�䣺2016��4��27�� ����3:24:09
 *
 */
public class Cbm implements Serializable {

	// ��˾ID
	private long id = 0;
	// ��˾logoUrl
	private String curl = null;
	// ��ע��
	private String cf = null;
	// ��˾����
	private String cn = null;
	// ȫ����Ʒ����
	private int ap = 0;
	// �²�Ʒ����
	private int np = 0;
	// ��������
	private int as = 0;
	// ���̶�̬
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
