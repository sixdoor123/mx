package com.baiyi.cmall.activities.main.buy.entity;

import java.io.Serializable;
import java.util.List;

import com.baiyi.cmall.activities.main.mall.entity.PnvmlEntity;

/**
 * ��Ʒ��Ʒģ��
 * 
 * @author sunxy
 */
public class OwmEntity implements Serializable{

	// id
	private String id = null;
	// ��Ʒid
	private String pi = null;
	// ��Ʒ����
	private String pc = null;
	// ��˾����
	private String cn = null;
	// ��˾id
	private String ci = null;
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
	// ���
	private String inv = null;
	// �������
	private String bm = null;

	// �۸�
	private String pr = null;

	// �����ܶ�
	private String ra;

	// ��Ʒ���ֵģ��
	private List<PnvmlEntity> entities = null;

	/**
	 * @return the ra
	 */
	public String getRa() {
		return ra;
	}

	/**
	 * @param ra
	 *            the ra to set
	 */
	public void setRa(String ra) {
		this.ra = ra;
	}

	/**
	 * @return the entities
	 */
	public List<PnvmlEntity> getEntities() {
		return entities;
	}

	/**
	 * @param entities
	 *            the entities to set
	 */
	public void setEntities(List<PnvmlEntity> entities) {
		this.entities = entities;
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
	 * @return the pi
	 */
	public String getPi() {
		return pi;
	}

	/**
	 * @param pi
	 *            the pi to set
	 */
	public void setPi(String pi) {
		this.pi = pi;
	}

	/**
	 * @return the pc
	 */
	public String getPc() {
		return pc;
	}

	/**
	 * @param pc
	 *            the pc to set
	 */
	public void setPc(String pc) {
		this.pc = pc;
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
	public String getCi() {
		return ci;
	}

	/**
	 * @param ci
	 *            the ci to set
	 */
	public void setCi(String ci) {
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
	 * @return the string
	 */
	public String getSn() {
		return sn;
	}

	/**
	 * @param string
	 *            the string to set
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

	/**
	 * @return the inv
	 */
	public String getInv() {
		return inv;
	}

	/**
	 * @param inv
	 *            the inv to set
	 */
	public void setInv(String inv) {
		this.inv = inv;
	}

	/**
	 * @return the bm
	 */
	public String getBm() {
		return bm;
	}

	/**
	 * @param bm
	 *            the bm to set
	 */
	public void setBm(String bm) {
		this.bm = bm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OwmEntity [id=" + id + ", pi=" + pi + ", pc=" + pc + ", cn=" + cn + ", ci=" + ci + ", pn=" + pn
				+ ", sn=" + sn + ", bn=" + bn + ", ip=" + ip + ", in=" + in + ", inv=" + inv + ", bm=" + bm + "]";
	}

}
