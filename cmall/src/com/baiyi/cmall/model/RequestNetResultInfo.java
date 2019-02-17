package com.baiyi.cmall.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 请求网络状态，结果码
 * 
 * @author sunxy
 * @param <T>
 * @label You can go as far as you want to go.
 * @date 2015-12-8 下午3:00:38
 */
public class RequestNetResultInfo<T> extends ExpandedInfo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 状态、结果码
	private int status;
	// 返回的信息
	private String msg;

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RequestNetResultInfo [status=" + status + ", msg=" + msg + "]";
	}

	
	
	private ArrayList<Serializable>  datas = null;
	
	
	public  ArrayList<Serializable> getDatas() {
		return  datas;
	}

	public void setDatas(ArrayList<Serializable> datas) {
		this.datas = datas;
	}


	private List<Blm> blms = null;
	

	/**
	 * @return the blms
	 */
	public List<Blm> getBlms() {
		return blms;
	}

	/**
	 * @param blms the blms to set
	 */
	public void setBlms(List<Blm> blms) {
		this.blms = blms;
	}

	private List<Bim> bims = null;
	


	/**
	 * @return the bims
	 */
	public List<Bim> getBims() {
		return bims;
	}

	/**
	 * @param bims the bims to set
	 */
	public void setBims(List<Bim> bims) {
		this.bims = bims;
	}



	/************************** Model **************************/

	// 1.首页基础数据 sbd
	private Sbd sbd = null;
	// 2.商品详情-详情pid
	private Pid pid = null;
	// 3.商城-商品详情pif
	private Pif pif = null;
	// 4.商城-商品评价pie
	private Pie pie = null;
	// 5.商城接口数据sjd
	private Sjd sjd = null;
	// 6.商家详情-产品列表 cpim
	private Cpim cpim = null;
	// 7.商家详情-店铺详情 cdim
	private Cdim cdim = null;
	/**** 0：附录-Model ****/
	// 1.基础列表模板blm
	private Blm blm = null;
	// 2.基础轮播图片bim
	private Bim bim = null;
	// 3.基础关键字模板kcm
	private Kcm kcm = null;
	// 4.新闻nsi
	private Nsi nsi = null;
	// 5.统计tdm
	private Tdm tdm = null;
	// 6.推荐商家bdi
	private Bdi bdi = null;
	// 7.二级分类sci
	private Sci sci = null;
	// 8.热销产品（推荐产品）hpi
	private Hpi hpi = null;
	// 9.产品基本信息pbi
	private Pbi pbi = null;
	// 10.商家基本信息cbi
	private Cbi cbi = null;
	// 11.产品描述条目pim
	private Pim pim = null;
	// 12.产品描述条目值pvm
	private Pvm pvm = null;
	// 13.产品描述pdi
	private Pdi pdi = null;
	// 14.属性rpv
	private Rpv rpv = null;
	// 15.评论ctm
	private Ctm ctm = null;
	// 16.订单基础信息odi
	private Odi odi = null;
	// 17.订单详情-产品信息pdm
	private Pdm pdm = null;
	// 18.订单详情odm
	private Odm odm = null;
	// 19.订单列表-产品基本信息opi
	private Opi opi = null;
	// 20.过滤条件 fif
	private Fif fif = null;
	// 21.过滤标签值ftvm
	private Ftvm ftvm = null;
	// 22.过滤条件标签ftm
	private Ftm ftm = null;
	// 24.分页基础返回 bdm
	private Bdm bdm = null;
	// 25.商家详情-店铺基础数据cbm
	private Cbm cbm = null;
	// 26.商家详情-店铺详情dpi
	private Dpi dpi = null;
	// 27.商家详情-使用排序规则 cfif(未定，是否在商家详情中使用新排序)
	private Cfif cfif = null;
	// 28.购物车产品规格值ucrnvm
	private Ucrnvm ucrnvm = null;
	// 29.购物车采购商品ucwm
	private Ucwm ucwm = null;

	/**
	 * @return the sbd
	 */
	public Sbd getSbd() {
		return sbd;
	}

	/**
	 * @param sbd
	 *            the sbd to set
	 */
	public void setSbd(Sbd sbd) {
		this.sbd = sbd;
	}

	/**
	 * @return the pid
	 */
	public Pid getPid() {
		return pid;
	}

	/**
	 * @param pid
	 *            the pid to set
	 */
	public void setPid(Pid pid) {
		this.pid = pid;
	}

	/**
	 * @return the pif
	 */
	public Pif getPif() {
		return pif;
	}

	/**
	 * @param pif
	 *            the pif to set
	 */
	public void setPif(Pif pif) {
		this.pif = pif;
	}

	/**
	 * @return the pie
	 */
	public Pie getPie() {
		return pie;
	}

	/**
	 * @param pie
	 *            the pie to set
	 */
	public void setPie(Pie pie) {
		this.pie = pie;
	}

	/**
	 * @return the sjd
	 */
	public Sjd getSjd() {
		return sjd;
	}

	/**
	 * @param sjd
	 *            the sjd to set
	 */
	public void setSjd(Sjd sjd) {
		this.sjd = sjd;
	}

	/**
	 * @return the cpim
	 */
	public Cpim getCpim() {
		return cpim;
	}

	/**
	 * @param cpim
	 *            the cpim to set
	 */
	public void setCpim(Cpim cpim) {
		this.cpim = cpim;
	}

	/**
	 * @return the cdim
	 */
	public Cdim getCdim() {
		return cdim;
	}

	/**
	 * @param cdim
	 *            the cdim to set
	 */
	public void setCdim(Cdim cdim) {
		this.cdim = cdim;
	}

	/**
	 * @return the blm
	 */
	public Blm getBlm() {
		return blm;
	}

	/**
	 * @param blm
	 *            the blm to set
	 */
	public void setBlm(Blm blm) {
		this.blm = blm;
	}

	/**
	 * @return the bim
	 */
	public Bim getBim() {
		return bim;
	}

	/**
	 * @param bim
	 *            the bim to set
	 */
	public void setBim(Bim bim) {
		this.bim = bim;
	}

	/**
	 * @return the kcm
	 */
	public Kcm getKcm() {
		return kcm;
	}

	/**
	 * @param kcm
	 *            the kcm to set
	 */
	public void setKcm(Kcm kcm) {
		this.kcm = kcm;
	}

	/**
	 * @return the nsi
	 */
	public Nsi getNsi() {
		return nsi;
	}

	/**
	 * @param nsi
	 *            the nsi to set
	 */
	public void setNsi(Nsi nsi) {
		this.nsi = nsi;
	}

	/**
	 * @return the tdm
	 */
	public Tdm getTdm() {
		return tdm;
	}

	/**
	 * @param tdm
	 *            the tdm to set
	 */
	public void setTdm(Tdm tdm) {
		this.tdm = tdm;
	}

	/**
	 * @return the bdi
	 */
	public Bdi getBdi() {
		return bdi;
	}

	/**
	 * @param bdi
	 *            the bdi to set
	 */
	public void setBdi(Bdi bdi) {
		this.bdi = bdi;
	}

	/**
	 * @return the sci
	 */
	public Sci getSci() {
		return sci;
	}

	/**
	 * @param sci
	 *            the sci to set
	 */
	public void setSci(Sci sci) {
		this.sci = sci;
	}

	/**
	 * @return the hpi
	 */
	public Hpi getHpi() {
		return hpi;
	}

	/**
	 * @param hpi
	 *            the hpi to set
	 */
	public void setHpi(Hpi hpi) {
		this.hpi = hpi;
	}

	/**
	 * @return the pbi
	 */
	public Pbi getPbi() {
		return pbi;
	}

	/**
	 * @param pbi
	 *            the pbi to set
	 */
	public void setPbi(Pbi pbi) {
		this.pbi = pbi;
	}

	/**
	 * @return the cbi
	 */
	public Cbi getCbi() {
		return cbi;
	}

	/**
	 * @param cbi
	 *            the cbi to set
	 */
	public void setCbi(Cbi cbi) {
		this.cbi = cbi;
	}

	/**
	 * @return the pim
	 */
	public Pim getPim() {
		return pim;
	}

	/**
	 * @param pim
	 *            the pim to set
	 */
	public void setPim(Pim pim) {
		this.pim = pim;
	}

	/**
	 * @return the pvm
	 */
	public Pvm getPvm() {
		return pvm;
	}

	/**
	 * @param pvm
	 *            the pvm to set
	 */
	public void setPvm(Pvm pvm) {
		this.pvm = pvm;
	}

	/**
	 * @return the pdi
	 */
	public Pdi getPdi() {
		return pdi;
	}

	/**
	 * @param pdi
	 *            the pdi to set
	 */
	public void setPdi(Pdi pdi) {
		this.pdi = pdi;
	}

	/**
	 * @return the rpv
	 */
	public Rpv getRpv() {
		return rpv;
	}

	/**
	 * @param rpv
	 *            the rpv to set
	 */
	public void setRpv(Rpv rpv) {
		this.rpv = rpv;
	}

	/**
	 * @return the ctm
	 */
	public Ctm getCtm() {
		return ctm;
	}

	/**
	 * @param ctm
	 *            the ctm to set
	 */
	public void setCtm(Ctm ctm) {
		this.ctm = ctm;
	}

	/**
	 * @return the odi
	 */
	public Odi getOdi() {
		return odi;
	}

	/**
	 * @param odi
	 *            the odi to set
	 */
	public void setOdi(Odi odi) {
		this.odi = odi;
	}

	/**
	 * @return the pdm
	 */
	public Pdm getPdm() {
		return pdm;
	}

	/**
	 * @param pdm
	 *            the pdm to set
	 */
	public void setPdm(Pdm pdm) {
		this.pdm = pdm;
	}

	/**
	 * @return the odm
	 */
	public Odm getOdm() {
		return odm;
	}

	/**
	 * @param odm
	 *            the odm to set
	 */
	public void setOdm(Odm odm) {
		this.odm = odm;
	}

	/**
	 * @return the opi
	 */
	public Opi getOpi() {
		return opi;
	}

	/**
	 * @param opi
	 *            the opi to set
	 */
	public void setOpi(Opi opi) {
		this.opi = opi;
	}

	/**
	 * @return the fif
	 */
	public Fif getFif() {
		return fif;
	}

	/**
	 * @param fif
	 *            the fif to set
	 */
	public void setFif(Fif fif) {
		this.fif = fif;
	}

	/**
	 * @return the ftvm
	 */
	public Ftvm getFtvm() {
		return ftvm;
	}

	/**
	 * @param ftvm
	 *            the ftvm to set
	 */
	public void setFtvm(Ftvm ftvm) {
		this.ftvm = ftvm;
	}

	/**
	 * @return the ftm
	 */
	public Ftm getFtm() {
		return ftm;
	}

	/**
	 * @param ftm
	 *            the ftm to set
	 */
	public void setFtm(Ftm ftm) {
		this.ftm = ftm;
	}

	/**
	 * @return the bdm
	 */
	public Bdm getBdm() {
		return bdm;
	}

	/**
	 * @param bdm
	 *            the bdm to set
	 */
	public void setBdm(Bdm bdm) {
		this.bdm = bdm;
	}

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

	/**
	 * @return the cfif
	 */
	public Cfif getCfif() {
		return cfif;
	}

	/**
	 * @param cfif
	 *            the cfif to set
	 */
	public void setCfif(Cfif cfif) {
		this.cfif = cfif;
	}

	/**
	 * @return the ucrnvm
	 */
	public Ucrnvm getUcrnvm() {
		return ucrnvm;
	}

	/**
	 * @param ucrnvm
	 *            the ucrnvm to set
	 */
	public void setUcrnvm(Ucrnvm ucrnvm) {
		this.ucrnvm = ucrnvm;
	}

	/**
	 * @return the ucwm
	 */
	public Ucwm getUcwm() {
		return ucwm;
	}

	/**
	 * @param ucwm
	 *            the ucwm to set
	 */
	public void setUcwm(Ucwm ucwm) {
		this.ucwm = ucwm;
	}

}
