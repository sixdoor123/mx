package com.baiyi.cmall.model;

import java.io.Serializable;

/**
 * ���ﳵ�ɹ���Ʒucwm
 * 
 * @author sunxy
 * @version ����ʱ�䣺2016��4��27�� ����3:39:22
 *
 */
public class Ucwm implements Serializable {

	// id
	private long id = 0;
	// ��Ʒid
	private long ri = 0;
	// ����
	private int rc = 0;
	// �۸�
	private String pr = null;
	// ��˾����
	private String cn = null;
	// ��˾id
	private long ci = 0;
	// ��Դ����
	private String pn = null;
	// ������
	private String sn = null;
	// ����
	private String bn = null;
	// ͼƬ·��
	private String ip = null;
	// ͼƬ����
	private String in = null;

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
	 * @return the ri
	 */
	public long getRi() {
		return ri;
	}

	/**
	 * @param ri
	 *            the ri to set
	 */
	public void setRi(long ri) {
		this.ri = ri;
	}

	/**
	 * @return the rc
	 */
	public int getRc() {
		return rc;
	}

	/**
	 * @param rc
	 *            the rc to set
	 */
	public void setRc(int rc) {
		this.rc = rc;
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
	 * @return the ci
	 */
	public long getCi() {
		return ci;
	}

	/**
	 * @param ci
	 *            the ci to set
	 */
	public void setCi(long ci) {
		this.ci = ci;
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
	 * @return the sn
	 */
	public String getSn() {
		return sn;
	}

	/**
	 * @param sn
	 *            the sn to set
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * @return the bn
	 */
	public String getBn() {
		return bn;
	}

	/**
	 * @param bn
	 *            the bn to set
	 */
	public void setBn(String bn) {
		this.bn = bn;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip
	 *            the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
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

}
