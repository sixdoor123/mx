package com.baiyi.cmall.entity;

import java.io.Serializable;
import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/**
 * 订单详情采购信息实体
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-25 下午5:20:18
 */
public class IntentionPurchaseDetailInfo implements Serializable{

	/**
	 * 
	 */
	// 采购信息
	// 采购信息ID
	private String id;
	// 名称
	private String purchasename;
	// 采购分类
	private String categoryname;
	// 产地
	private String originplacename;
	// 品牌
	private String brandname;
	// 采购数量
	private String amount;
	// 价格
	private String price;
	// 预付金额
	private String prepayment;
	// 交割地
	private String deliveryplacename;
	// 详细内容
	private String purchasedetail;
	//状态
		private String proState;

		//供应商名称
		private String merchantName;
	//采购信息的属性的集合
	private ArrayList<IntentionDetailStandardInfo> purStandardInfos;
	private String intentionstatename;

	/**
	 * @return the intentionstatename
	 */
	public String getIntentionstatename() {
		return intentionstatename;
	}

	/**
	 * @param intentionstatename the intentionstatename to set
	 */
	public void setIntentionstatename(String intentionstatename) {
		this.intentionstatename = intentionstatename;
	}
	/**
	 * @return the proState
	 */
	public String getProState() {
		return proState;
	}
	/**
	 * @param proState the proState to set
	 */
	public void setProState(String proState) {
		this.proState = proState;
	}
	/**
	 * @return the merchantName
	 */
	public String getMerchantName() {
		return merchantName;
	}
	/**
	 * @param merchantName the merchantName to set
	 */
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	/**
	 * @return the purStandardInfos
	 */
	public ArrayList<IntentionDetailStandardInfo> getPurStandardInfos() {
		return purStandardInfos;
	}
	/**
	 * @param purStandardInfos the purStandardInfos to set
	 */
	public void setPurStandardInfos(
			ArrayList<IntentionDetailStandardInfo> purStandardInfos) {
		this.purStandardInfos = purStandardInfos;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the purchasename
	 */
	public String getPurchasename() {
		return purchasename;
	}
	/**
	 * @param purchasename the purchasename to set
	 */
	public void setPurchasename(String purchasename) {
		this.purchasename = purchasename;
	}
	/**
	 * @return the categoryname
	 */
	public String getCategoryname() {
		return categoryname;
	}
	/**
	 * @param categoryname the categoryname to set
	 */
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	/**
	 * @return the originplacename
	 */
	public String getOriginplacename() {
		return originplacename;
	}
	/**
	 * @param originplacename the originplacename to set
	 */
	public void setOriginplacename(String originplacename) {
		this.originplacename = originplacename;
	}
	/**
	 * @return the brandname
	 */
	public String getBrandname() {
		return brandname;
	}
	/**
	 * @param brandname the brandname to set
	 */
	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}
	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	/**
	 * @return the prepayment
	 */
	public String getPrepayment() {
		return prepayment;
	}
	/**
	 * @param prepayment the prepayment to set
	 */
	public void setPrepayment(String prepayment) {
		this.prepayment = prepayment;
	}
	/**
	 * @return the deliveryplacename
	 */
	public String getDeliveryplacename() {
		return deliveryplacename;
	}
	/**
	 * @param deliveryplacename the deliveryplacename to set
	 */
	public void setDeliveryplacename(String deliveryplacename) {
		this.deliveryplacename = deliveryplacename;
	}
	/**
	 * @return the purchasedetail
	 */
	public String getPurchasedetail() {
		return purchasedetail;
	}
	/**
	 * @param purchasedetail the purchasedetail to set
	 */
	public void setPurchasedetail(String purchasedetail) {
		this.purchasedetail = purchasedetail;
	}
	

	public IntentionPurchaseDetailInfo() {
		// TODO Auto-generated constructor stub
	}
//	
	
	
	@Override
	public String toString() {
		return "IntentionPurchaseDetailInfo [id=" + id + ", purchasename="
				+ purchasename + ", categoryname=" + categoryname
				+ ", originplacename=" + originplacename + ", brandname="
				+ brandname + ", amount=" + amount + ", price=" + price
				+ ", prepayment=" + prepayment + ", deliveryplacename="
				+ deliveryplacename + ", purchasedetail=" + purchasedetail
				+ ", purStandardInfos=" + purStandardInfos + "]";
	}
	
	
}
