package com.baiyi.cmall.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * ��ҳ��Դ-�б���Ϣ
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-28 ����4:07:05
 */
public class GoodSOriginInfo implements Serializable{
	// ��Դid
	private String id;
	// ����
	private String categoryName;
	// �̼�����
	private String companyName;
	// �۸�
	private String price;
	// ����ʱ��
	private String offerBeginTime;
	// �����Ʒ��
	private String brandName;
	// ��Ӧ����
	private String offerDetails;
	// ��Ʒ����
	private String offerName;

	//����
	private String inventory;
	//����
	private String purchasename;
	//״̬
	private String intentionstatename;
	//�����
	private String deliveryplacename;

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
	 * @return the inventory
	 */
	public String getInventory() {
		return inventory;
	}

	/**
	 * @param inventory the inventory to set
	 */
	public void setInventory(String inventory) {
		this.inventory = inventory;
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
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName
	 *            the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName
	 *            the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @return the offerBeginTime
	 */
	public String getOfferBeginTime() {
		return offerBeginTime;
	}

	/**
	 * @param offerBeginTime
	 *            the offerBeginTime to set
	 */
	public void setOfferBeginTime(String offerBeginTime) {
		this.offerBeginTime = offerBeginTime;
	}

	/**
	 * @return the brandName
	 */
	public String getBrandName() {
		return brandName;
	}

	/**
	 * @param brandName
	 *            the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	/**
	 * @return the offerDetails
	 */
	public String getOfferDetails() {
		return offerDetails;
	}

	/**
	 * @param offerDetails
	 *            the offerDetails to set
	 */
	public void setOfferDetails(String offerDetails) {
		this.offerDetails = offerDetails;
	}

	/**
	 * @return the offerName
	 */
	public String getOfferName() {
		return offerName;
	}

	/**
	 * @param offerName
	 *            the offerName to set
	 */
	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	
}
