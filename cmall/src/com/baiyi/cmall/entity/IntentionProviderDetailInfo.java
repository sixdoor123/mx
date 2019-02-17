package com.baiyi.cmall.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * �������鹩Ӧ��Ϣʵ��
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-25 ����4:34:21
 */
public class IntentionProviderDetailInfo implements Serializable {

	/**
	 * 
	 */
	// ��Ӧ��Ϣ
	// ����
	private String offerName;
	// ��Ӧ����
	private String categoryName;
	// ����
	private String originPlaceName;
	// Ʒ��
	private String brandName;
	// ��棨������
	private String inventory;
	// �۸�
	private String price;
	// Ԥ�����
	private String prepayment;
	// �����
	private String deliveryPlaceName;
	// ��ϸ����
	private String offerDetail;
	// ��ӦID
	private String id;
	// ����
	private String purchasename;
	// ״̬
	private String proState;

	// ��Ӧ������
	private String merchantName;

	// ��ϵ��
	private String contact;
	// ��ϵ��ʽ
	private String mobile;

	// �ɹ���Ϣ��ʵ��
	private IntentionPurchaseDetailInfo purchaseDetailInfo;
	// ��Ź�Ӧ��Ϣ���Եļ���
	private ArrayList<IntentionDetailStandardInfo> proDetailStandardInfos;

	// ������������Ϣ
	private RequestNetResultInfo resultInfo;

	// ����ʱ��
	private String putTime;
	// ����ʱ��
	private String endTime;

	// �Ƿ��Զ���
	private boolean autoSatrt;
	// �Ƿ��Զ���
	private boolean autoEnd;

	// ����ID
	private String category;
	// Ʒ��Id
	private String brand;
	// ����id
	private String originplace;
	// �����ID
	private String deliveryplace;

	private String targetId;
	// �жϰ�ť
	private String binaryvalue;
	
	private String intentionstatename;
	
	private String deliveryplacecode;
	

	/**
	 * @return the deliveryplacecode
	 */
	public String getDeliveryplacecode() {
		return deliveryplacecode;
	}

	/**
	 * @param deliveryplacecode the deliveryplacecode to set
	 */
	public void setDeliveryplacecode(String deliveryplacecode) {
		this.deliveryplacecode = deliveryplacecode;
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
	 * @return the binaryvalue
	 */
	public String getBinaryvalue() {
		return binaryvalue;
	}

	/**
	 * @param binaryvalue
	 *            the binaryvalue to set
	 */
	public void setBinaryvalue(String binaryvalue) {
		this.binaryvalue = binaryvalue;
	}

	/**
	 * @return the targetId
	 */
	public String getTargetId() {
		return targetId;
	}

	/**
	 * @param targetId
	 *            the targetId to set
	 */
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	/**
	 * @return the originplace
	 */
	public String getOriginplace() {
		return originplace;
	}

	/**
	 * @param originplace
	 *            the originplace to set
	 */
	public void setOriginplace(String originplace) {
		this.originplace = originplace;
	}

	/**
	 * @return the deliveryplace
	 */
	public String getDeliveryplace() {
		return deliveryplace;
	}

	/**
	 * @param deliveryplace
	 *            the deliveryplace to set
	 */
	public void setDeliveryplace(String deliveryplace) {
		this.deliveryplace = deliveryplace;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}

	/**
	 * @param brand
	 *            the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * @return the autoSatrt
	 */
	public boolean isAutoSatrt() {
		return autoSatrt;
	}

	/**
	 * @param autoSatrt
	 *            the autoSatrt to set
	 */
	public void setAutoSatrt(boolean autoSatrt) {
		this.autoSatrt = autoSatrt;
	}

	/**
	 * @return the autoEnd
	 */
	public boolean isAutoEnd() {
		return autoEnd;
	}

	/**
	 * @param autoEnd
	 *            the autoEnd to set
	 */
	public void setAutoEnd(boolean autoEnd) {
		this.autoEnd = autoEnd;
	}

	/**
	 * @return the putTime
	 */
	public String getPutTime() {
		return putTime;
	}

	/**
	 * @param putTime
	 *            the putTime to set
	 */
	public void setPutTime(String putTime) {
		this.putTime = putTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the resultInfo
	 */
	public RequestNetResultInfo getResultInfo() {
		return resultInfo;
	}

	/**
	 * @param resultInfo
	 *            the resultInfo to set
	 */
	public void setResultInfo(RequestNetResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}

	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * @param contact
	 *            the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the merchantName
	 */
	public String getMerchantName() {
		return merchantName;
	}

	/**
	 * @param merchantName
	 *            the merchantName to set
	 */
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	/**
	 * @return the proState
	 */
	public String getProState() {
		return proState;
	}

	/**
	 * @param proState
	 *            the proState to set
	 */
	public void setProState(String proState) {
		this.proState = proState;
	}

	public String getPurchasename() {
		return purchasename;
	}

	public void setPurchasename(String purchasename) {
		this.purchasename = purchasename;
	}

	/**
	 * @return the purchaseDetailInfo
	 */
	public IntentionPurchaseDetailInfo getPurchaseDetailInfo() {
		return purchaseDetailInfo;
	}

	/**
	 * @param purchaseDetailInfo
	 *            the purchaseDetailInfo to set
	 */
	public void setPurchaseDetailInfo(
			IntentionPurchaseDetailInfo purchaseDetailInfo) {
		this.purchaseDetailInfo = purchaseDetailInfo;
	}

	/**
	 * @return the proDetailStandardInfos
	 */
	public ArrayList<IntentionDetailStandardInfo> getProDetailStandardInfos() {
		return proDetailStandardInfos;
	}

	/**
	 * @param proDetailStandardInfos
	 *            the proDetailStandardInfos to set
	 */
	public void setProDetailStandardInfos(
			ArrayList<IntentionDetailStandardInfo> proDetailStandardInfos) {
		this.proDetailStandardInfos = proDetailStandardInfos;
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
	 * @return the originPlaceName
	 */
	public String getOriginPlaceName() {
		return originPlaceName;
	}

	/**
	 * @param originPlaceName
	 *            the originPlaceName to set
	 */
	public void setOriginPlaceName(String originPlaceName) {
		this.originPlaceName = originPlaceName;
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
	 * @return the inventory
	 */
	public String getInventory() {
		return inventory;
	}

	/**
	 * @param inventory
	 *            the inventory to set
	 */
	public void setInventory(String inventory) {
		this.inventory = inventory;
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
	 * @return the prepayment
	 */
	public String getPrepayment() {
		return prepayment;
	}

	/**
	 * @param prepayment
	 *            the prepayment to set
	 */
	public void setPrepayment(String prepayment) {
		this.prepayment = prepayment;
	}

	/**
	 * @return the deliveryPlaceName
	 */
	public String getDeliveryPlaceName() {
		return deliveryPlaceName;
	}

	/**
	 * @param deliveryPlaceName
	 *            the deliveryPlaceName to set
	 */
	public void setDeliveryPlaceName(String deliveryPlaceName) {
		this.deliveryPlaceName = deliveryPlaceName;
	}

	/**
	 * @return the offerDetail
	 */
	public String getOfferDetail() {
		return offerDetail;
	}

	/**
	 * @param offerDetail
	 *            the offerDetail to set
	 */
	public void setOfferDetail(String offerDetail) {
		this.offerDetail = offerDetail;
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

	@Override
	public String toString() {
		return "IntentionProviderDetailInfo [offerName=" + offerName
				+ ", categoryName=" + categoryName + ", originPlaceName="
				+ originPlaceName + ", brandName=" + brandName + ", inventory="
				+ inventory + ", price=" + price + ", prepayment=" + prepayment
				+ ", deliveryPlaceName=" + deliveryPlaceName + ", offerDetail="
				+ offerDetail + ", id=" + id + ", purchasename=" + purchasename
				+ ", purchaseDetailInfo=" + purchaseDetailInfo
				+ ", proDetailStandardInfos=" + proDetailStandardInfos + "]";
	}

	public IntentionProviderDetailInfo() {
		// TODO Auto-generated constructor stub
	}

}
