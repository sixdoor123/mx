package com.baiyi.cmall.activities.main.buy.req;

import java.io.Serializable;

/**
 * 订单产品模型
 * @author tangkun
 *
 */
@SuppressWarnings("serial")
public class ReqOpm implements Serializable{
	//订单id
	private int id;
	//订单名称
	private String on;
	//买家id
	private int ui;
	//订单总额
	private String ra;
	//商家id
	private int ci;
	private String cn;
	//订单类型
	private int ot;
	//快递类型
	private int dt;
	//快递单号
	private String dc;
	//快递价格
	private int dp;
	//快递名称
	private String dn;
	//发票抬头
	private String itit;
	//发票内容
	private String ic;
	//发票类型
	private int it;
	//收货人姓名
	private String rn;
	//收货城市id
	private int rci;
	//收货详细地址
	private String radd;
	//收货人电话
	private String rp;
	//订单状态名称
	private String osn;
	//买家留言
	private String bm;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOn() {
		return on;
	}
	public void setOn(String on) {
		this.on = on;
	}
	public int getUi() {
		return ui;
	}
	public void setUi(int ui) {
		this.ui = ui;
	}
	public String getRa() {
		return ra;
	}
	public void setRa(String ra) {
		this.ra = ra;
	}
	public int getCi() {
		return ci;
	}
	public void setCi(int ci) {
		this.ci = ci;
	}
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public int getOt() {
		return ot;
	}
	public void setOt(int ot) {
		this.ot = ot;
	}
	public int getDt() {
		return dt;
	}
	public void setDt(int dt) {
		this.dt = dt;
	}
	public String getDc() {
		return dc;
	}
	public void setDc(String dc) {
		this.dc = dc;
	}
	public int getDp() {
		return dp;
	}
	public void setDp(int dp) {
		this.dp = dp;
	}
	public String getDn() {
		return dn;
	}
	public void setDn(String dn) {
		this.dn = dn;
	}
	public String getItit() {
		return itit;
	}
	public void setItit(String itit) {
		this.itit = itit;
	}
	public String getIc() {
		return ic;
	}
	public void setIc(String ic) {
		this.ic = ic;
	}
	public int getIt() {
		return it;
	}
	public void setIt(int it) {
		this.it = it;
	}
	public String getRn() {
		return rn;
	}
	public void setRn(String rn) {
		this.rn = rn;
	}
	public int getRci() {
		return rci;
	}
	public void setRci(int rci) {
		this.rci = rci;
	}
	public String getRadd() {
		return radd;
	}
	public void setRadd(String radd) {
		this.radd = radd;
	}
	public String getRp() {
		return rp;
	}
	public void setRp(String rp) {
		this.rp = rp;
	}
	public String getOsn() {
		return osn;
	}
	public void setOsn(String osn) {
		this.osn = osn;
	}
	public String getBm() {
		return bm;
	}
	public void setBm(String bm) {
		this.bm = bm;
	}

}
