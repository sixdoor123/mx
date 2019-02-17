package com.baiyi.cmall.entity;

import java.io.Serializable;
import java.util.ArrayList;

import com.baiyi.cmall.entity._public.BrandEntities;
import com.baiyi.core.database.AbstractBaseModel;

import android.R.integer;

/**
 * 货物的实体类
 * 
 * @author sunxy
 * 
 */
@SuppressWarnings("serial")
public class GoodsSourceInfo extends AbstractBaseModel implements Serializable {

	// 服务器返回的状态信息
	private RequestNetResultInfo resultInfo;
	private String email;
	private String companytypename;
	private String imageurl;
	// 商品名称
	private String goodSName;
	// 商品分类
	private String goodSCategory;
	// 商品分类号
	private String goodSCategoryNum;
	// 产地
	private String goodSPlace;
	// 商家
	private String goodSMerchant;
	// 规格
	private String goodSSpecification;
	// 货物重量/数量
	private String goodSWeight;
	// 资源总额
	private String goodSAllMoney;
	// 预付费
	private String goodSPrePrice;
	// 价格(单价)
	private String goodSPrice;
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
	// 品牌号
	private long goodSBrandNum;
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
	// 状态名称
	private String statename;
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
	// 各种类型Id
	private String moreTypeId;
	// 库存
	private String kuCun;

	private boolean autoStart;

	private boolean autoEnd;
	// 库存
	private String goodSInventory;
	// 商家ID
	private String companyId;
	// 发布状态
	private int pubState;
	// 发布状态名称
	private String pubStateName;

	private ArrayList<GoodsSourceInfo> offerInfos;

	// 存放我是供应商-我的供应-采购列表信息
	private ArrayList<GoodsSourceInfo> purInfos;

	// 存放属性的集合
	private ArrayList<IntentionDetailStandardInfo> standardInfos;

	// 存放供应分类查询条件的数据
	private ArrayList<SelectedInfo> categoryDatas;
	// 存放供应交割地查询条件的数据
	private ArrayList<SelectedInfo> deliveryDatas;
	// 存放品牌的数据的集合
	private ArrayList<BrandEntities> brands;
	// 预付款
	private String prepayment;
	// 收货人ID
	private int receiverId;
	// 发票ID
	private int inVoiceId;
	// 审核状态
	private String puchasestatename;
	// 订单状态值
	private int orderstate;

	/*
	 * 属性id
	 */
	private String id;
	private String propertyId;

	// 包装类型
	private String packtypename;
	// 开始城市ID
	private String startcityid;
	// 目的城市ID
	private String destinationcityid;
	// 标题
	private String title;
	// 运输类型
	private String deliverytype;
	// 运输类型名称
	private String deliverytypename;
	// 包装方式
	private String packtype;
	// 意向单发布状态
	private int publicstate;
	// 意向单发布状态名称
	private String publicstatename;
	// 联系人城市
	private String personCity;
	// 产地Id
	private String origincityid;
	// 分类ID
	private String categoryID;
	// 分类ID
	private String brandID;
	// 交割地ID
	private String deliverycityid;
	// 城市ID
	private String cityid;
	// 城市
	private String city;
	// 采购ID
	private String purchaseid;
	// 供应ID
	private String offerid;
	// 用户删除标示
	private int deletebyuser;
	// 商家删除标示
	private int deletebycompany;

	// 运输方式
	private ArrayList<SelectedInfo> yunInfos;
	// 包装方式
	private ArrayList<SelectedInfo> packageInfos;
	// 默认排序
	private ArrayList<SelectedInfo> sortWays;
	// 判读是否被关注
	private boolean isfollow;
	// 用户名
	private String userName;
	// 采购信息
	private GoodsSourceInfo purSourceInfo;
	// 供一个信息
	private GoodsSourceInfo proSourceInfo;
	// 用于判断按钮的显示
	private String binaryvalue;
	/*
	 * 发票内容列表
	 */
	private ArrayList<OrderEntity> contextList;
	/*
	 * 发票类型列表
	 */
	private ArrayList<OrderEntity> typeList;
	
	//判断供应是否是自己的
	private boolean isowner;

	private String deliveryplacecode;

	/**
	 * @return the isowner
	 */
	public boolean isIsowner() {
		return isowner;
	}

	/**
	 * @param isowner the isowner to set
	 */
	public void setIsowner(boolean isowner) {
		this.isowner = isowner;
	}

	/**
	 * @return the deliveryplacecode
	 */
	public String getDeliveryplacecode() {
		return deliveryplacecode;
	}

	/**
	 * @param deliveryplacecode
	 *            the deliveryplacecode to set
	 */
	public void setDeliveryplacecode(String deliveryplacecode) {
		this.deliveryplacecode = deliveryplacecode;
	}

	/**
	 * @return the companytypename
	 */
	public String getCompanytypename() {
		return companytypename;
	}

	/**
	 * @param companytypename
	 *            the companytypename to set
	 */
	public void setCompanytypename(String companytypename) {
		this.companytypename = companytypename;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the imageurl
	 */
	public String getImageurl() {
		return imageurl;
	}

	/**
	 * @param imageurl
	 *            the imageurl to set
	 */
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the sortWays
	 */
	public ArrayList<SelectedInfo> getSortWays() {
		return sortWays;
	}

	/**
	 * @param sortWays
	 *            the sortWays to set
	 */
	public void setSortWays(ArrayList<SelectedInfo> sortWays) {
		this.sortWays = sortWays;
	}

	/**
	 * @return the isfollow
	 */
	public boolean isIsfollow() {
		return isfollow;
	}

	/**
	 * @param isfollow
	 *            the isfollow to set
	 */
	public void setIsfollow(boolean isfollow) {
		this.isfollow = isfollow;
	}

	/**
	 * @return the packtype
	 */
	public String getPacktype() {
		return packtype;
	}

	/**
	 * @param packtype
	 *            the packtype to set
	 */
	public void setPacktype(String packtype) {
		this.packtype = packtype;
	}

	/**
	 * @return the yunInfos
	 */
	public ArrayList<SelectedInfo> getYunInfos() {
		return yunInfos;
	}

	/**
	 * @param yunInfos
	 *            the yunInfos to set
	 */
	public void setYunInfos(ArrayList<SelectedInfo> yunInfos) {
		this.yunInfos = yunInfos;
	}

	/**
	 * @return the packageInfos
	 */
	public ArrayList<SelectedInfo> getPackageInfos() {
		return packageInfos;
	}

	/**
	 * @param packageInfos
	 *            the packageInfos to set
	 */
	public void setPackageInfos(ArrayList<SelectedInfo> packageInfos) {
		this.packageInfos = packageInfos;
	}

	/**
	 * @return the brands
	 */
	public ArrayList<BrandEntities> getBrands() {
		return brands;
	}

	/**
	 * @param brands
	 *            the brands to set
	 */
	public void setBrands(ArrayList<BrandEntities> brands) {
		this.brands = brands;
	}

	/**
	 * @return the deletebyuser
	 */
	public int getDeletebyuser() {
		return deletebyuser;
	}

	/**
	 * @param deletebyuser
	 *            the deletebyuser to set
	 */
	public void setDeletebyuser(int deletebyuser) {
		this.deletebyuser = deletebyuser;
	}

	/**
	 * @return the offerid
	 */
	public String getOfferid() {
		return offerid;
	}

	/**
	 * @param offerid
	 *            the offerid to set
	 */
	public void setOfferid(String offerid) {
		this.offerid = offerid;
	}

	/**
	 * @return the purchaseid
	 */
	public String getPurchaseid() {
		return purchaseid;
	}

	/**
	 * @param purchaseid
	 *            the purchaseid to set
	 */
	public void setPurchaseid(String purchaseid) {
		this.purchaseid = purchaseid;
	}

	/**
	 * @return the publicstatename
	 */
	public String getPublicstatename() {
		return publicstatename;
	}

	/**
	 * @param publicstatename
	 *            the publicstatename to set
	 */
	public void setPublicstatename(String publicstatename) {
		this.publicstatename = publicstatename;
	}

	/**
	 * @return the cityid
	 */
	public String getCityid() {
		return cityid;
	}

	/**
	 * @param cityid
	 *            the cityid to set
	 */
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	/**
	 * @return the deliverycityid
	 */
	public String getDeliverycityid() {
		return deliverycityid;
	}

	/**
	 * @param deliverycityid
	 *            the deliverycityid to set
	 */
	public void setDeliverycityid(String deliverycityid) {
		this.deliverycityid = deliverycityid;
	}

	/**
	 * @return the categoryID
	 */
	public String getCategoryID() {
		return categoryID;
	}

	/**
	 * @param categoryID
	 *            the categoryID to set
	 */
	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}

	/**
	 * @return the brandID
	 */
	public String getBrandID() {
		return brandID;
	}

	/**
	 * @param brandID
	 *            the brandID to set
	 */
	public void setBrandID(String brandID) {
		this.brandID = brandID;
	}

	/**
	 * @return the origincityid
	 */
	public String getOrigincityid() {
		return origincityid;
	}

	/**
	 * @param origincityid
	 *            the origincityid to set
	 */
	public void setOrigincityid(String origincityid) {
		this.origincityid = origincityid;
	}

	/**
	 * @return the personCity
	 */
	public String getPersonCity() {
		return personCity;
	}

	/**
	 * @param personCity
	 *            the personCity to set
	 */
	public void setPersonCity(String personCity) {
		this.personCity = personCity;
	}

	/**
	 * @return the publicstate
	 */
	public int getPublicstate() {
		return publicstate;
	}

	/**
	 * @param publicstate
	 *            the publicstate to set
	 */
	public void setPublicstate(int publicstate) {
		this.publicstate = publicstate;
	}

	/**
	 * @return the deliverytypename
	 */
	public String getDeliverytypename() {
		return deliverytypename;
	}

	/**
	 * @param deliverytypename
	 *            the deliverytypename to set
	 */
	public void setDeliverytypename(String deliverytypename) {
		this.deliverytypename = deliverytypename;
	}

	/**
	 * @return the destinationcityid
	 */
	public String getDestinationcityid() {
		return destinationcityid;
	}

	/**
	 * @param destinationcityid
	 *            the destinationcityid to set
	 */
	public void setDestinationcityid(String destinationcityid) {
		this.destinationcityid = destinationcityid;
	}

	/**
	 * @return the statename
	 */
	public String getStatename() {
		return statename;
	}

	/**
	 * @param statename
	 *            the statename to set
	 */
	public void setStatename(String statename) {
		this.statename = statename;
	}

	/**
	 * @return the deliverytype
	 */
	public String getDeliverytype() {
		return deliverytype;
	}

	/**
	 * @param deliverytype
	 *            the deliverytype to set
	 */
	public void setDeliverytype(String deliverytype) {
		this.deliverytype = deliverytype;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the startcityid
	 */
	public String getStartcityid() {
		return startcityid;
	}

	/**
	 * @param startcityid
	 *            the startcityid to set
	 */
	public void setStartcityid(String startcityid) {
		this.startcityid = startcityid;
	}

	/**
	 * @return the packtypename
	 */
	public String getPacktypename() {
		return packtypename;
	}

	/**
	 * @param packtypename
	 *            the packtypename to set
	 */
	public void setPacktypename(String packtypename) {
		this.packtypename = packtypename;
	}

	public final String getId() {
		return id;
	}

	public final void setId(String id) {
		this.id = id;
	}

	public final String getPropertyId() {
		return propertyId;
	}

	public final void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	/**
	 * @return the orderstate
	 */
	public int getOrderstate() {
		return orderstate;
	}

	/**
	 * @param orderstate
	 *            the orderstate to set
	 */
	public void setOrderstate(int orderstate) {
		this.orderstate = orderstate;
	}

	/**
	 * @return the puchasestatename
	 */
	public String getPuchasestatename() {
		return puchasestatename;
	}

	/**
	 * @param puchasestatename
	 *            the puchasestatename to set
	 */
	public void setPuchasestatename(String puchasestatename) {
		this.puchasestatename = puchasestatename;
	}

	/**
	 * @return the goodSInventory
	 */
	public String getGoodSInventory() {
		return goodSInventory;
	}

	/**
	 * @param goodSInventory
	 *            the goodSInventory to set
	 */
	public void setGoodSInventory(String goodSInventory) {
		this.goodSInventory = goodSInventory;
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
	 * @return the purInfos
	 */
	public ArrayList<GoodsSourceInfo> getPurInfos() {
		return purInfos;
	}

	/**
	 * @param purInfos
	 *            the purInfos to set
	 */
	public void setPurInfos(ArrayList<GoodsSourceInfo> purInfos) {
		this.purInfos = purInfos;
	}

	/**
	 * @return the categoryDatas
	 */
	public ArrayList<SelectedInfo> getCategoryDatas() {
		return categoryDatas;
	}

	/**
	 * @param categoryDatas
	 *            the categoryDatas to set
	 */
	public void setCategoryDatas(ArrayList<SelectedInfo> categoryDatas) {
		this.categoryDatas = categoryDatas;
	}

	/**
	 * @return the deliveryDatas
	 */
	public ArrayList<SelectedInfo> getDeliveryDatas() {
		return deliveryDatas;
	}

	/**
	 * @param deliveryDatas
	 *            the deliveryDatas to set
	 */
	public void setDeliveryDatas(ArrayList<SelectedInfo> deliveryDatas) {
		this.deliveryDatas = deliveryDatas;
	}

	/**
	 * @return the standardInfos
	 */
	public ArrayList<IntentionDetailStandardInfo> getStandardInfos() {
		return standardInfos;
	}

	/**
	 * @param standardInfos
	 *            the standardInfos to set
	 */
	public void setStandardInfos(
			ArrayList<IntentionDetailStandardInfo> standardInfos) {
		this.standardInfos = standardInfos;
	}

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

	public String getGoodSPrePrice() {
		return goodSPrePrice;
	}

	public void setGoodSPrePrice(String goodSPrePrice) {
		this.goodSPrePrice = goodSPrePrice;
	}

	public String getGoodSPrice() {
		return goodSPrice;
	}

	public void setGoodSPrice(String goodSPrice) {
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

	public String getGoodSWeight() {
		return goodSWeight;
	}

	public void setGoodSWeight(String goodSWeight) {
		this.goodSWeight = goodSWeight;
	}

	// @Override
	// public int describeContents() {
	// return 0;
	// }

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

	public ArrayList<GoodsSourceInfo> getOfferInfos() {
		return offerInfos;
	}

	public void setOfferInfos(ArrayList<GoodsSourceInfo> offerInfos) {
		this.offerInfos = offerInfos;
	}

	public String getGoodSCategoryNum() {
		return goodSCategoryNum;
	}

	public void setGoodSCategoryNum(String goodSCategoryNum) {
		this.goodSCategoryNum = goodSCategoryNum;
	}

	public long getGoodSBrandNum() {
		return goodSBrandNum;
	}

	public void setGoodSBrandNum(long goodSBrandNum) {
		this.goodSBrandNum = goodSBrandNum;
	}

	public boolean isAutoStart() {
		return autoStart;
	}

	public void setAutoStart(boolean autoStart) {
		this.autoStart = autoStart;
	}

	public boolean isAutoEnd() {
		return autoEnd;
	}

	public void setAutoEnd(boolean autoEnd) {
		this.autoEnd = autoEnd;
	}

	public GoodsSourceInfo() {
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

	public int getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}

	public int getInVoiceId() {
		return inVoiceId;
	}

	public void setInVoiceId(int inVoiceId) {
		this.inVoiceId = inVoiceId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public int getPubState() {
		return pubState;
	}

	public void setPubState(int pubState) {
		this.pubState = pubState;
	}

	/**
	 * @return the pubStateName
	 */
	public String getPubStateName() {
		return pubStateName;
	}

	/**
	 * @param pubStateName
	 *            the pubStateName to set
	 */
	public void setPubStateName(String pubStateName) {
		this.pubStateName = pubStateName;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the deletebycompany
	 */
	public int getDeletebycompany() {
		return deletebycompany;
	}

	/**
	 * @param deletebycompany
	 *            the deletebycompany to set
	 */
	public void setDeletebycompany(int deletebycompany) {
		this.deletebycompany = deletebycompany;
	}

	// 分类
	private String categoryName;
	// 商家名称
	private String companyName;
	// 价格
	private String price;
	// 发布时间
	private String offerBeginTime;
	// 货物的品种
	private String brandName;
	// 供应详情
	private String offerDetails;
	// 商品名称
	private String offerName;

	// 数量
	private String inventory;
	// 名称
	private String purchasename;
	// 状态
	private String intentionstatename;
	// 交割地
	private String deliveryplacename;

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
	 * @return the purchasename
	 */
	public String getPurchasename() {
		return purchasename;
	}

	/**
	 * @param purchasename
	 *            the purchasename to set
	 */
	public void setPurchasename(String purchasename) {
		this.purchasename = purchasename;
	}

	/**
	 * @return the intentionstatename
	 */
	public String getIntentionstatename() {
		return intentionstatename;
	}

	/**
	 * @param intentionstatename
	 *            the intentionstatename to set
	 */
	public void setIntentionstatename(String intentionstatename) {
		this.intentionstatename = intentionstatename;
	}

	/**
	 * @return the deliveryplacename
	 */
	public String getDeliveryplacename() {
		return deliveryplacename;
	}

	/**
	 * @param deliveryplacename
	 *            the deliveryplacename to set
	 */
	public void setDeliveryplacename(String deliveryplacename) {
		this.deliveryplacename = deliveryplacename;
	}

	/**
	 * @return the purSourceInfo
	 */
	public GoodsSourceInfo getPurSourceInfo() {
		return purSourceInfo;
	}

	/**
	 * @param purSourceInfo
	 *            the purSourceInfo to set
	 */
	public void setPurSourceInfo(GoodsSourceInfo purSourceInfo) {
		this.purSourceInfo = purSourceInfo;
	}

	/**
	 * @return the proSourceInfo
	 */
	public GoodsSourceInfo getProSourceInfo() {
		return proSourceInfo;
	}

	/**
	 * @param proSourceInfo
	 *            the proSourceInfo to set
	 */
	public void setProSourceInfo(GoodsSourceInfo proSourceInfo) {
		this.proSourceInfo = proSourceInfo;
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
	 * @return the moreTypeId
	 */
	public String getMoreTypeId() {
		return moreTypeId;
	}

	/**
	 * @param moreTypeId
	 *            the moreTypeId to set
	 */
	public void setMoreTypeId(String moreTypeId) {
		this.moreTypeId = moreTypeId;
	}

	/**
	 * @return the contextList
	 */
	public ArrayList<OrderEntity> getContextList() {
		return contextList;
	}

	/**
	 * @param contextList
	 *            the contextList to set
	 */
	public void setContextList(ArrayList<OrderEntity> contextList) {
		this.contextList = contextList;
	}

	/**
	 * @return the typeList
	 */
	public ArrayList<OrderEntity> getTypeList() {
		return typeList;
	}

	/**
	 * @param typeList
	 *            the typeList to set
	 */
	public void setTypeList(ArrayList<OrderEntity> typeList) {
		this.typeList = typeList;
	}

}
