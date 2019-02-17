package com.baiyi.cmall.entity;

import java.io.Serializable;
import java.util.ArrayList;

import com.baiyi.cmall.entity._public.BrandEntities;
import com.baiyi.core.database.AbstractBaseModel;

import android.R.integer;

/**
 * �����ʵ����
 * 
 * @author sunxy
 * 
 */
@SuppressWarnings("serial")
public class GoodsSourceInfo extends AbstractBaseModel implements Serializable {

	// ���������ص�״̬��Ϣ
	private RequestNetResultInfo resultInfo;
	private String email;
	private String companytypename;
	private String imageurl;
	// ��Ʒ����
	private String goodSName;
	// ��Ʒ����
	private String goodSCategory;
	// ��Ʒ�����
	private String goodSCategoryNum;
	// ����
	private String goodSPlace;
	// �̼�
	private String goodSMerchant;
	// ���
	private String goodSSpecification;
	// ��������/����
	private String goodSWeight;
	// ��Դ�ܶ�
	private String goodSAllMoney;
	// Ԥ����
	private String goodSPrePrice;
	// �۸�(����)
	private String goodSPrice;
	// FIXME �۸�������۸��ظ�
	// �۸�����
	private String goodSpriceInterval;
	// �۸�ʽ
	private String goodSPriceWay;
	// �����
	private String goodSDelivery;
	// ��ϸ����
	private String goodSPurchaseContent;
	// ����ʱ��(���ʱ��)
	private String goodSPutTime;
	// ����
	private String goodSArea;
	// �ύʱ��(����ʱ��)
	private String goodSCommitTime;
	// �ж����򵥵�״̬
	private String intentionOrderState;
	// ��ϵ��
	private String goodSContactPerson;
	// ��ϵ��ʽ
	private String goodSContactWay;
	// �ɹ�����ID
	private int goodSPurchaseOrderId;
	// ��Ӧ����ID
	private int goodSProviderOrderId;
	// �ɹ�����
	private String goodSPurchaseNeed;
	// ����
	// ������
	private String GoodSCalorificValue;
	// ȫˮ��
	private String goodSTotalMoisture;
	// ��ˮ
	private String goodSInlandWaters;
	// �ӷ���
	private String goodSVolatileMatter;
	// �ҷ�
	private String goodSAshContent;
	// ȫ���
	private String goodSTotalSulfur;
	// �̶�̼
	private String goodSFixedCarbon;
	// ����
	private String goodSParticleSize;
	// �ܶ�
	private String goodSDensity;
	// ���۵�
	private String goodSAshFusionPoint;
	// ���Ͽ�ĥָ��
	private String hardgroverindabilityIndex;
	// ��������
	private String goodSCinderFeature;
	// ����ָ��
	private String goodSBurlExponent;
	// ���ʲ���
	private String goodSCuticleThickness;
	// ǯ������������ϵ��
	private String goodSExpansionFactor;
	// ����������
	private String goodSFlowabilitySize;
	// ����������
	private String goodSAluminumOxide;
	// ��
	private String goodSHydrogenSize;
	// ��
	private String goodSNitrogenSize;
	// Ʒ��
	private String goodSBreed;
	// Ʒ��
	private String goodSBrand;
	// Ʒ�ƺ�
	private long goodSBrandNum;
	// ��������ʼ����
	private String goodSStartCity;
	// �����Ľ�������
	private String goodSEndCity;
	// ����Ա
	private String goodSTradePerson;
	// ����Ա��ϵ��ʽ
	private String goodSTradeNumber;
	// ����
	private String goodSTitle;
	// ����
	private String goodSContent;
	// ��˾����
	private String goodSCompanyType;
	// ��ƷID
	private String goodSID;
	// ��Ӧ����
	private String goodSDetails;
	// ��Ʒ��˾����
	private String GoodSCompanyNmae;
	// �û�ID
	private String userId;
	// ��˾ID
	private String compantId;
	// ����ʱ��
	private String goodSEndTime;
	// ��ʼʱ��
	private String goodSStartTime;
	// ��ע
	private String goodSRemark;
	// ��ַ
	private String address;
	// ʼ����ַ
	private String startAddress;
	// Ŀ�ĵ�ַ
	private String endAddress;
	// ��������
	private String freightWay;
	// ��װ��ʽ
	private String packingWay;
	// ����״̬
	private int state;
	// ״̬����
	private String statename;
	// ��������
	private int type;
	// ������
	private String shuXingName;
	// ����ֵ
	private String shuXingValue;
	// ���Է�������
	private String shuXingCategory;
	// ��������
	private String moreType;
	// ��������Id
	private String moreTypeId;
	// ���
	private String kuCun;

	private boolean autoStart;

	private boolean autoEnd;
	// ���
	private String goodSInventory;
	// �̼�ID
	private String companyId;
	// ����״̬
	private int pubState;
	// ����״̬����
	private String pubStateName;

	private ArrayList<GoodsSourceInfo> offerInfos;

	// ������ǹ�Ӧ��-�ҵĹ�Ӧ-�ɹ��б���Ϣ
	private ArrayList<GoodsSourceInfo> purInfos;

	// ������Եļ���
	private ArrayList<IntentionDetailStandardInfo> standardInfos;

	// ��Ź�Ӧ�����ѯ����������
	private ArrayList<SelectedInfo> categoryDatas;
	// ��Ź�Ӧ����ز�ѯ����������
	private ArrayList<SelectedInfo> deliveryDatas;
	// ���Ʒ�Ƶ����ݵļ���
	private ArrayList<BrandEntities> brands;
	// Ԥ����
	private String prepayment;
	// �ջ���ID
	private int receiverId;
	// ��ƱID
	private int inVoiceId;
	// ���״̬
	private String puchasestatename;
	// ����״ֵ̬
	private int orderstate;

	/*
	 * ����id
	 */
	private String id;
	private String propertyId;

	// ��װ����
	private String packtypename;
	// ��ʼ����ID
	private String startcityid;
	// Ŀ�ĳ���ID
	private String destinationcityid;
	// ����
	private String title;
	// ��������
	private String deliverytype;
	// ������������
	private String deliverytypename;
	// ��װ��ʽ
	private String packtype;
	// ���򵥷���״̬
	private int publicstate;
	// ���򵥷���״̬����
	private String publicstatename;
	// ��ϵ�˳���
	private String personCity;
	// ����Id
	private String origincityid;
	// ����ID
	private String categoryID;
	// ����ID
	private String brandID;
	// �����ID
	private String deliverycityid;
	// ����ID
	private String cityid;
	// ����
	private String city;
	// �ɹ�ID
	private String purchaseid;
	// ��ӦID
	private String offerid;
	// �û�ɾ����ʾ
	private int deletebyuser;
	// �̼�ɾ����ʾ
	private int deletebycompany;

	// ���䷽ʽ
	private ArrayList<SelectedInfo> yunInfos;
	// ��װ��ʽ
	private ArrayList<SelectedInfo> packageInfos;
	// Ĭ������
	private ArrayList<SelectedInfo> sortWays;
	// �ж��Ƿ񱻹�ע
	private boolean isfollow;
	// �û���
	private String userName;
	// �ɹ���Ϣ
	private GoodsSourceInfo purSourceInfo;
	// ��һ����Ϣ
	private GoodsSourceInfo proSourceInfo;
	// �����жϰ�ť����ʾ
	private String binaryvalue;
	/*
	 * ��Ʊ�����б�
	 */
	private ArrayList<OrderEntity> contextList;
	/*
	 * ��Ʊ�����б�
	 */
	private ArrayList<OrderEntity> typeList;
	
	//�жϹ�Ӧ�Ƿ����Լ���
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

	// ����
	private String inventory;
	// ����
	private String purchasename;
	// ״̬
	private String intentionstatename;
	// �����
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
