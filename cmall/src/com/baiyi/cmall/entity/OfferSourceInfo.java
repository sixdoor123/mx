package com.baiyi.cmall.entity;

import java.io.Serializable;
import java.util.ArrayList;

import android.R.integer;
import android.R.string;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 货物的实体类
 * 
 * @author sunxy
 * 
 */
public class OfferSourceInfo implements Serializable {

	// 服务器返回的状态信息
	private RequestNetResultInfo resultInfo;

	// 商品名称
	private String goodSName;
	// 商品分类
	private String goodSCategory;
	// 产地
	private String goodSPlace;
	// 商家
	private String goodSMerchant;
	// 规格
	private String goodSSpecification;
	// 货物重量/数量
	private int goodSWeight;
	// 资源总额
	private String goodSAllMoney;
	// 预付费
	private float goodSPrePrice;
	// 价格(单价)
	private float goodSPrice;
	// FIXME 价格区间与价格重复
	// 价格区间
	private String goodSpriceInterval;
	// 价格方式
	private String goodSPriceWay;
	// 交割地
	private String goodSDelivery;
	// 详细内容
	private String goodSPurchaseContent;

	// 发布时间(审核时间)
	private String goodSPutTime;

	// 地区
	private String goodSArea;

	// 提交时间(创建时间)
	private String goodSCommitTime;

	// 判断意向单的状态
	private String intentionOrderState;

	// 联系人
	private String goodSContactPerson;
	// 联系方式
	private String goodSContactWay;

	// 采购订单ID
	private int goodSPurchaseOrderId;
	// 供应订单ID
	private int goodSProviderOrderId;

	// 采购需求
	private String goodSPurchaseNeed;

	// 属性
	// 发热量
	private String GoodSCalorificValue;
	// 全水分
	private String goodSTotalMoisture;
	// 内水
	private String goodSInlandWaters;
	// 挥发份
	private String goodSVolatileMatter;
	// 灰份
	private String goodSAshContent;
	// 全硫份
	private String goodSTotalSulfur;
	// 固定碳
	private String goodSFixedCarbon;
	// 粒度
	private String goodSParticleSize;
	// 密度
	private String goodSDensity;

	// 灰熔点
	private String goodSAshFusionPoint;
	// 哈氏可磨指数
	private String hardgroverindabilityIndex;
	// 焦渣特征
	private String goodSCinderFeature;
	// 粒结指数
	private String goodSBurlExponent;
	// 角质层厚度
	private String goodSCuticleThickness;
	// 钳锅、自由膨胀系数
	private String goodSExpansionFactor;
	// 基氏流动度
	private String goodSFlowabilitySize;
	// 三氧化二铝
	private String goodSAluminumOxide;
	// 氢
	private String goodSHydrogenSize;
	// 氮
	private String goodSNitrogenSize;

	// 品种
	private String goodSBreed;
	// 品牌
	private String goodSBrand;
	// 物流的起始成是
	private String goodSStartCity;
	// 物流的结束城市
	private String goodSEndCity;

	// 交易员
	private String goodSTradePerson;
	// 交易员联系方式
	private String goodSTradeNumber;

	// 标题
	private String goodSTitle;
	// 内容
	private String goodSContent;

	// 公司类型
	private String goodSCompanyType;

	// 商品ID
	private String goodSID;
	// 供应详情
	private String goodSDetails;
	// 商品公司名称
	private String GoodSCompanyNmae;

	// 用户ID
	private String userId;

	// 公司ID
	private String compantId;
	// 结束时间
	private String goodSEndTime;
	// 开始时间
	private String goodSStartTime;
	// 备注
	private String goodSRemark;
	// 地址
	private String address;
	// 始发地址
	private String startAddress;
	// 目的地址
	private String endAddress;

	// 货运类型
	private String freightWay;
	// 包装方式
	private String packingWay;
	// 意向状态
	private int state;
	// 意向类型
	private int type;
	// 属性名
	private String shuXingName;
	// 属性值
	private String shuXingValue;
	// 属性分类名称
	private String shuXingCategory;
	// 各种类型
	private String moreType;
	// 库存
	private String kuCun;

	/**
	 * @return the goodSTitle
	 */
	public String getGoodSTitle() {
		return goodSTitle;
	}

	/**
	 * @param goodSTitle
	 *            the goodSTitle to set
	 */
	public void setGoodSTitle(String goodSTitle) {
		this.goodSTitle = goodSTitle;
	}

	/**
	 * @return the goodSContent
	 */
	public String getGoodSContent() {
		return goodSContent;
	}

	/**
	 * @param goodSContent
	 *            the goodSContent to set
	 */
	public void setGoodSContent(String goodSContent) {
		this.goodSContent = goodSContent;
	}

	/**
	 * @return the freightWay
	 */
	public String getFreightWay() {
		return freightWay;
	}

	/**
	 * @param freightWay
	 *            the freightWay to set
	 */
	public void setFreightWay(String freightWay) {
		this.freightWay = freightWay;
	}

	/**
	 * @return the packingWay
	 */
	public String getPackingWay() {
		return packingWay;
	}

	/**
	 * @param packingWay
	 *            the packingWay to set
	 */
	public void setPackingWay(String packingWay) {
		this.packingWay = packingWay;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the startAddress
	 */
	public String getStartAddress() {
		return startAddress;
	}

	/**
	 * @param startAddress
	 *            the startAddress to set
	 */
	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}

	/**
	 * @return the endAddress
	 */
	public String getEndAddress() {
		return endAddress;
	}

	/**
	 * @param endAddress
	 *            the endAddress to set
	 */
	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}

	/**
	 * @return the goodSRemark
	 */
	public String getGoodSRemark() {
		return goodSRemark;
	}

	/**
	 * @param goodSRemark
	 *            the goodSRemark to set
	 */
	public void setGoodSRemark(String goodSRemark) {
		this.goodSRemark = goodSRemark;
	}

	/**
	 * @return the goodSStartTime
	 */
	public String getGoodSStartTime() {
		return goodSStartTime;
	}

	/**
	 * @param goodSStartTime
	 *            the goodSStartTime to set
	 */
	public void setGoodSStartTime(String goodSStartTime) {
		this.goodSStartTime = goodSStartTime;
	}

	/**
	 * @return the goodSEndTime
	 */
	public String getGoodSEndTime() {
		return goodSEndTime;
	}

	/**
	 * @param goodSEndTime
	 *            the goodSEndTime to set
	 */
	public void setGoodSEndTime(String goodSEndTime) {
		this.goodSEndTime = goodSEndTime;
	}

	/**
	 * @return the compantId
	 */
	public String getCompantId() {
		return compantId;
	}

	/**
	 * @param compantId
	 *            the compantId to set
	 */
	public void setCompantId(String compantId) {
		this.compantId = compantId;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the goodSCompanyNmae
	 */
	public String getGoodSCompanyNmae() {
		return GoodSCompanyNmae;
	}

	/**
	 * @param goodSCompanyNmae
	 *            the goodSCompanyNmae to set
	 */
	public void setGoodSCompanyNmae(String goodSCompanyNmae) {
		GoodSCompanyNmae = goodSCompanyNmae;
	}

	/**
	 * @return the goodSDetails
	 */
	public String getGoodSDetails() {
		return goodSDetails;
	}

	/**
	 * @param goodSDetails
	 *            the goodSDetails to set
	 */
	public void setGoodSDetails(String goodSDetails) {
		this.goodSDetails = goodSDetails;
	}

	/**
	 * @return the goodSID
	 */
	public String getGoodSID() {
		return goodSID;
	}

	/**
	 * @param goodSID
	 *            the goodSID to set
	 */
	public void setGoodSID(String goodSID) {
		this.goodSID = goodSID;
	}

	/**
	 * @return the goodSCompanyType
	 */
	public String getGoodSCompanyType() {
		return goodSCompanyType;
	}

	/**
	 * @param goodSCompanyType
	 *            the goodSCompanyType to set
	 */
	public void setGoodSCompanyType(String goodSCompanyType) {
		this.goodSCompanyType = goodSCompanyType;
	}

	/**
	 * @return the goodSTradePerson
	 */
	public String getGoodSTradePerson() {
		return goodSTradePerson;
	}

	/**
	 * @param goodSTradePerson
	 *            the goodSTradePerson to set
	 */
	public void setGoodSTradePerson(String goodSTradePerson) {
		this.goodSTradePerson = goodSTradePerson;
	}

	/**
	 * @return the goodSTradeNumber
	 */
	public String getGoodSTradeNumber() {
		return goodSTradeNumber;
	}

	/**
	 * @param goodSTradeNumber
	 *            the goodSTradeNumber to set
	 */
	public void setGoodSTradeNumber(String goodSTradeNumber) {
		this.goodSTradeNumber = goodSTradeNumber;
	}

	/**
	 * @return the goodSStartCityString
	 */
	public String getGoodSStartCity() {
		return goodSStartCity;
	}

	/**
	 * @param goodSStartCityString
	 *            the goodSStartCityString to set
	 */
	public void setGoodSStartCity(String goodSStartCity) {
		this.goodSStartCity = goodSStartCity;
	}

	/**
	 * @return the goodSStartEndString
	 */
	public String getGoodSEndCity() {
		return goodSEndCity;
	}

	/**
	 * @param goodSStartEndString
	 *            the goodSStartEndString to set
	 */
	public void setGoodSEndCity(String goodSEndCity) {
		this.goodSEndCity = goodSEndCity;
	}

	/**
	 * @return the goodSBrand
	 */
	public String getGoodSBrand() {
		return goodSBrand;
	}

	/**
	 * @param goodSBrand
	 *            the goodSBrand to set
	 */
	public void setGoodSBrand(String goodSBrand) {
		this.goodSBrand = goodSBrand;
	}

	/**
	 * @return the goodSCalorificValue
	 */
	public String getGoodSCalorificValue() {
		return GoodSCalorificValue;
	}

	/**
	 * @param goodSCalorificValue
	 *            the goodSCalorificValue to set
	 */
	public void setGoodSCalorificValue(String goodSCalorificValue) {
		GoodSCalorificValue = goodSCalorificValue;
	}

	/**
	 * @return the goodSTotalMoisture
	 */
	public String getGoodSTotalMoisture() {
		return goodSTotalMoisture;
	}

	/**
	 * @param goodSTotalMoisture
	 *            the goodSTotalMoisture to set
	 */
	public void setGoodSTotalMoisture(String goodSTotalMoisture) {
		this.goodSTotalMoisture = goodSTotalMoisture;
	}

	/**
	 * @return the goodSInlandWaters
	 */
	public String getGoodSInlandWaters() {
		return goodSInlandWaters;
	}

	/**
	 * @param goodSInlandWaters
	 *            the goodSInlandWaters to set
	 */
	public void setGoodSInlandWaters(String goodSInlandWaters) {
		this.goodSInlandWaters = goodSInlandWaters;
	}

	/**
	 * @return the goodSVolatileMatter
	 */
	public String getGoodSVolatileMatter() {
		return goodSVolatileMatter;
	}

	/**
	 * @param goodSVolatileMatter
	 *            the goodSVolatileMatter to set
	 */
	public void setGoodSVolatileMatter(String goodSVolatileMatter) {
		this.goodSVolatileMatter = goodSVolatileMatter;
	}

	/**
	 * @return the goodSAshContent
	 */
	public String getGoodSAshContent() {
		return goodSAshContent;
	}

	/**
	 * @param goodSAshContent
	 *            the goodSAshContent to set
	 */
	public void setGoodSAshContent(String goodSAshContent) {
		this.goodSAshContent = goodSAshContent;
	}

	/**
	 * @return the goodSTotalSulfur
	 */
	public String getGoodSTotalSulfur() {
		return goodSTotalSulfur;
	}

	/**
	 * @param goodSTotalSulfur
	 *            the goodSTotalSulfur to set
	 */
	public void setGoodSTotalSulfur(String goodSTotalSulfur) {
		this.goodSTotalSulfur = goodSTotalSulfur;
	}

	/**
	 * @return the goodSFixedCarbon
	 */
	public String getGoodSFixedCarbon() {
		return goodSFixedCarbon;
	}

	/**
	 * @param goodSFixedCarbon
	 *            the goodSFixedCarbon to set
	 */
	public void setGoodSFixedCarbon(String goodSFixedCarbon) {
		this.goodSFixedCarbon = goodSFixedCarbon;
	}

	/**
	 * @return the goodSParticleSize
	 */
	public String getGoodSParticleSize() {
		return goodSParticleSize;
	}

	/**
	 * @param goodSParticleSize
	 *            the goodSParticleSize to set
	 */
	public void setGoodSParticleSize(String goodSParticleSize) {
		this.goodSParticleSize = goodSParticleSize;
	}

	/**
	 * @return the goodSDensity
	 */
	public String getGoodSDensity() {
		return goodSDensity;
	}

	/**
	 * @param goodSDensity
	 *            the goodSDensity to set
	 */
	public void setGoodSDensity(String goodSDensity) {
		this.goodSDensity = goodSDensity;
	}

	/**
	 * @return the goodSAshFusionPoint
	 */
	public String getGoodSAshFusionPoint() {
		return goodSAshFusionPoint;
	}

	/**
	 * @param goodSAshFusionPoint
	 *            the goodSAshFusionPoint to set
	 */
	public void setGoodSAshFusionPoint(String goodSAshFusionPoint) {
		this.goodSAshFusionPoint = goodSAshFusionPoint;
	}

	/**
	 * @return the hardgroverindabilityIndex
	 */
	public String getHardgroverindabilityIndex() {
		return hardgroverindabilityIndex;
	}

	/**
	 * @param hardgroverindabilityIndex
	 *            the hardgroverindabilityIndex to set
	 */
	public void setHardgroverindabilityIndex(String hardgroverindabilityIndex) {
		this.hardgroverindabilityIndex = hardgroverindabilityIndex;
	}

	/**
	 * @return the goodSCinderFeature
	 */
	public String getGoodSCinderFeature() {
		return goodSCinderFeature;
	}

	/**
	 * @param goodSCinderFeature
	 *            the goodSCinderFeature to set
	 */
	public void setGoodSCinderFeature(String goodSCinderFeature) {
		this.goodSCinderFeature = goodSCinderFeature;
	}

	/**
	 * @return the goodSBurlExponent
	 */
	public String getGoodSBurlExponent() {
		return goodSBurlExponent;
	}

	/**
	 * @param goodSBurlExponent
	 *            the goodSBurlExponent to set
	 */
	public void setGoodSBurlExponent(String goodSBurlExponent) {
		this.goodSBurlExponent = goodSBurlExponent;
	}

	/**
	 * @return the goodSCuticleThickness
	 */
	public String getGoodSCuticleThickness() {
		return goodSCuticleThickness;
	}

	/**
	 * @param goodSCuticleThickness
	 *            the goodSCuticleThickness to set
	 */
	public void setGoodSCuticleThickness(String goodSCuticleThickness) {
		this.goodSCuticleThickness = goodSCuticleThickness;
	}

	/**
	 * @return the goodSExpansionFactor
	 */
	public String getGoodSExpansionFactor() {
		return goodSExpansionFactor;
	}

	/**
	 * @param goodSExpansionFactor
	 *            the goodSExpansionFactor to set
	 */
	public void setGoodSExpansionFactor(String goodSExpansionFactor) {
		this.goodSExpansionFactor = goodSExpansionFactor;
	}

	/**
	 * @return the goodSFlowabilitySize
	 */
	public String getGoodSFlowabilitySize() {
		return goodSFlowabilitySize;
	}

	/**
	 * @param goodSFlowabilitySize
	 *            the goodSFlowabilitySize to set
	 */
	public void setGoodSFlowabilitySize(String goodSFlowabilitySize) {
		this.goodSFlowabilitySize = goodSFlowabilitySize;
	}

	/**
	 * @return the goodSAluminumOxide
	 */
	public String getGoodSAluminumOxide() {
		return goodSAluminumOxide;
	}

	/**
	 * @param goodSAluminumOxide
	 *            the goodSAluminumOxide to set
	 */
	public void setGoodSAluminumOxide(String goodSAluminumOxide) {
		this.goodSAluminumOxide = goodSAluminumOxide;
	}

	/**
	 * @return the goodSHydrogenSize
	 */
	public String getGoodSHydrogenSize() {
		return goodSHydrogenSize;
	}

	/**
	 * @param goodSHydrogenSize
	 *            the goodSHydrogenSize to set
	 */
	public void setGoodSHydrogenSize(String goodSHydrogenSize) {
		this.goodSHydrogenSize = goodSHydrogenSize;
	}

	/**
	 * @return the goodSNitrogenSize
	 */
	public String getGoodSNitrogenSize() {
		return goodSNitrogenSize;
	}

	/**
	 * @param goodSNitrogenSize
	 *            the goodSNitrogenSize to set
	 */
	public void setGoodSNitrogenSize(String goodSNitrogenSize) {
		this.goodSNitrogenSize = goodSNitrogenSize;
	}

	public String getGoodSPurchaseNeed() {
		return goodSPurchaseNeed;
	}

	public void setGoodSPurchaseNeed(String goodSPurchaseNeed) {
		this.goodSPurchaseNeed = goodSPurchaseNeed;
	}

	public int getGoodSPurchaseOrderId() {
		return goodSPurchaseOrderId;
	}

	public void setGoodSPurchaseOrderId(int goodSPurchaseOrderId) {
		this.goodSPurchaseOrderId = goodSPurchaseOrderId;
	}

	public int getGoodSProviderOrderId() {
		return goodSProviderOrderId;
	}

	public void setGoodSProviderOrderId(int goodSProviderOrderId) {
		this.goodSProviderOrderId = goodSProviderOrderId;
	}

	public String getGoodSContactPerson() {
		return goodSContactPerson;
	}

	public void setGoodSContactPerson(String goodSContactPerson) {
		this.goodSContactPerson = goodSContactPerson;
	}

	public String getGoodSContactWay() {
		return goodSContactWay;
	}

	public void setGoodSContactWay(String goodSContactWay) {
		this.goodSContactWay = goodSContactWay;
	}

	public String getGoodSBreed() {
		return goodSBreed;
	}

	public void setGoodSBreed(String goodSBreed) {
		this.goodSBreed = goodSBreed;
	}

	public String getGoodSCommitTime() {
		return goodSCommitTime;
	}

	public void setGoodSCommitTime(String goodSCommitTime) {
		this.goodSCommitTime = goodSCommitTime;
	}

	public String getIntentionOrderState() {
		return intentionOrderState;
	}

	public void setIntentionOrderState(String intentionOrderState) {
		this.intentionOrderState = intentionOrderState;
	}

	public String getGoodSSpecification() {
		return goodSSpecification;
	}

	public void setGoodSSpecification(String goodSSpecification) {
		this.goodSSpecification = goodSSpecification;
	}

	public float getGoodSPrePrice() {
		return goodSPrePrice;
	}

	public void setGoodSPrePrice(float goodSPrePrice) {
		this.goodSPrePrice = goodSPrePrice;
	}

	public float getGoodSPrice() {
		return goodSPrice;
	}

	public void setGoodSPrice(float goodSPrice) {
		this.goodSPrice = goodSPrice;
	}

	public String getGoodSPriceWay() {
		return goodSPriceWay;
	}

	public void setGoodSPriceWay(String goodSPriceWay) {
		this.goodSPriceWay = goodSPriceWay;
	}

	public String getGoodSDelivery() {
		return goodSDelivery;
	}

	public void setGoodSDelivery(String goodSDelivery) {
		this.goodSDelivery = goodSDelivery;
	}

	public String getGoodSPurchaseContent() {
		return goodSPurchaseContent;
	}

	public void setGoodSPurchaseContent(String goodSPurchaseContent) {
		this.goodSPurchaseContent = goodSPurchaseContent;
	}

	public String getGoodSpriceInterval() {
		return goodSpriceInterval;
	}

	public void setGoodSpriceInterval(String goodSpriceInterval) {
		this.goodSpriceInterval = goodSpriceInterval;
	}

	public String getGoodSPutTime() {
		return goodSPutTime;
	}

	public void setGoodSPutTime(String goodSPutTime) {
		this.goodSPutTime = goodSPutTime;
	}

	public String getGoodSArea() {
		return goodSArea;
	}

	public void setGoodSArea(String goodSArea) {
		this.goodSArea = goodSArea;
	}

	public String getGoodSName() {
		return goodSName;
	}

	public void setGoodSName(String goodSName) {
		this.goodSName = goodSName;
	}

	public String getGoodSCategory() {
		return goodSCategory;
	}

	public void setGoodSCategory(String goodSCategory) {
		this.goodSCategory = goodSCategory;
	}

	public String getGoodSPlace() {
		return goodSPlace;
	}

	public void setGoodSPlace(String goodSPlace) {
		this.goodSPlace = goodSPlace;
	}

	public String getGoodSMerchant() {
		return goodSMerchant;
	}

	public void setGoodSMerchant(String goodSMerchant) {
		this.goodSMerchant = goodSMerchant;
	}

	public int getGoodSWeight() {
		return goodSWeight;
	}

	public void setGoodSWeight(int goodSWeight) {
		this.goodSWeight = goodSWeight;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getShuXingName() {
		return shuXingName;
	}

	public void setShuXingName(String shuXingName) {
		this.shuXingName = shuXingName;
	}

	public String getShuXingValue() {
		return shuXingValue;
	}

	public void setShuXingValue(String shuXingValue) {
		this.shuXingValue = shuXingValue;
	}

	public String getShuXingCategory() {
		return shuXingCategory;
	}

	public void setShuXingCategory(String shuXingCategory) {
		this.shuXingCategory = shuXingCategory;
	}

	public String getGoodSAllMoney() {
		return goodSAllMoney;
	}

	public void setGoodSAllMoney(String goodSAllMoney) {
		this.goodSAllMoney = goodSAllMoney;
	}

	public String getMoreType() {
		return moreType;
	}

	public void setMoreType(String moreType) {
		this.moreType = moreType;
	}

	public String getKuCun() {
		return kuCun;
	}

	public void setKuCun(String kuCun) {
		this.kuCun = kuCun;
	}

}
