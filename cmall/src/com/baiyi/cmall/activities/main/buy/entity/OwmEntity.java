package com.baiyi.cmall.activities.main.buy.entity;

import java.io.Serializable;
import java.util.List;

import com.baiyi.cmall.activities.main.mall.entity.PnvmlEntity;

/**
 * 产品商品模型
 * 
 * @author sunxy
 */
public class OwmEntity implements Serializable{

	// id
	private String id = null;
	// 产品id
	private String pi = null;
	// 产品数量
	private String pc = null;
	// 公司名称
	private String cn = null;
	// 公司id
	private String ci = null;
	// 资源名称
	private String pn = null;
	// 副名称
	private String sn = null;
	// 别名
	private String bn = null;
	// 图片路径
	private String ip = null;
	// 图片名称
	private String in = null;
	// 库存
	private String inv = null;
	// 买家留言
	private String bm = null;

	// 价格
	private String pr = null;

	// 订单总额
	private String ra;

	// 产品规格值模型
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
