package com.baiyi.cmall.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 订单信息（我是供应商- 我的订单）
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-23 下午1:21:00
 */
public class OrderEntity implements Serializable {
	/**
	 * 序列 UID
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	// 电话号码
	private String phone;
	// 名称
	private String offerName;
	// 名称
	private String purName;
	// 分类名称
	private String categoryName;
	// 品牌
	private String brandname;
	// 价格
	private String price;
	// 库存
	private String inventory;
	// 预付
	private String prepayment;
	// 总价
	private String allMoney;
	// 资源总额
	private String resamount;
	// 收货人名称
	private String receivername;
	// 地址
	private String address;
	// 发票抬头
	private String title;
	// 发票飘内容
	private String context;
	// 发票飘内容id
	private String contextId;
	// 发票类型名称
	private String invoicetypename;
	// 发票类型名称id
	private String invoicetypeId;
	// 订单编号
	private String orderNumber;
	// 订单名称
	private String orderName;
	// 订单状态名称
	private String orderStateName;
	// 订单状态
	private int orderState;
	// 城市
	private String orderCity;
	// 城市ID
	private String orderCityId;
	// 是否默认
	private boolean isDefault;
	// 公司名称
	private String companyname;
	// 公司名称
	private String companyid;
	// 意向ID
	private String intentionid;
	// 最大显示条数
	private int maxNum;
	// 订单编号
	private String intentionNumber;
	// 专门判断状态按鈕
	private String binaryvalue;
	// 地区
	private String eare;
	// 图片
	private String pic;
	// 产品名称
	private String productName;
	// 规格
	private String guige;

	private ArrayList<OrderEntity> opilList;
	private ArrayList<OrderEntity> pdmlList;

	public ArrayList<OrderEntity> getOpilList() {
		return opilList;
	}

	public void setOpilList(ArrayList<OrderEntity> opilList) {
		this.opilList = opilList;
	}

	public ArrayList<OrderEntity> getPdmlList() {
		return pdmlList;
	}

	public void setPdmlList(ArrayList<OrderEntity> pdmlList) {
		this.pdmlList = pdmlList;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	/**
	 * @return the eare
	 */
	public String getEare() {
		return eare;
	}

	/**
	 * @param eare
	 *            the eare to set
	 */
	public void setEare(String eare) {
		this.eare = eare;
	}

	/**
	 * @return the intentionid
	 */
	public String getIntentionid() {
		return intentionid;
	}

	/**
	 * @param intentionid
	 *            the intentionid to set
	 */
	public void setIntentionid(String intentionid) {
		this.intentionid = intentionid;
	}

	/*
	 * 发票列表的基本的信息
	 */
	private ArrayList<OrderEntity> invoiceList;
	/*
	 * 发票内容列表
	 */
	private ArrayList<OrderEntity> contextList;
	/*
	 * 发票类型列表
	 */
	private ArrayList<OrderEntity> typeList;

	public final int getOrderState() {
		return orderState;
	}

	public final void setOrderState(int orderState) {
		this.orderState = orderState;
	}

	/**
	 * @return the orderNumber
	 */
	public String getOrderNumber() {
		return orderNumber;
	}

	/**
	 * @param orderNumber
	 *            the orderNumber to set
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
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
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
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
	 * @return the brandname
	 */
	public String getBrandname() {
		return brandname;
	}

	/**
	 * @param brandname
	 *            the brandname to set
	 */
	public void setBrandname(String brandname) {
		this.brandname = brandname;
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
	 * @return the resamount
	 */
	public String getResamount() {
		return resamount;
	}

	/**
	 * @param resamount
	 *            the resamount to set
	 */
	public void setResamount(String resamount) {
		this.resamount = resamount;
	}

	/**
	 * @return the receivername
	 */
	public String getReceivername() {
		return receivername;
	}

	/**
	 * @param receivername
	 *            the receivername to set
	 */
	public void setReceivername(String receivername) {
		this.receivername = receivername;
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
	 * @return the context
	 */
	public String getContext() {
		return context;
	}

	/**
	 * @param context
	 *            the context to set
	 */
	public void setContext(String context) {
		this.context = context;
	}

	/**
	 * @return the invoicetypename
	 */
	public String getInvoicetypename() {
		return invoicetypename;
	}

	/**
	 * @param invoicetypename
	 *            the invoicetypename to set
	 */
	public void setInvoicetypename(String invoicetypename) {
		this.invoicetypename = invoicetypename;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderStateName() {
		return orderStateName;
	}

	public void setOrderStateName(String orderStateName) {
		this.orderStateName = orderStateName;
	}

	public String getOrderCity() {
		return orderCity;
	}

	public void setOrderCity(String orderCity) {
		this.orderCity = orderCity;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	/**
	 * @return the contextId
	 */
	public String getContextId() {
		return contextId;
	}

	/**
	 * @param contextId
	 *            the contextId to set
	 */
	public void setContextId(String contextId) {
		this.contextId = contextId;
	}

	/**
	 * @return the invoicetypeId
	 */
	public String getInvoicetypeId() {
		return invoicetypeId;
	}

	/**
	 * @param invoicetypeId
	 *            the invoicetypeId to set
	 */
	public void setInvoicetypeId(String invoicetypeId) {
		this.invoicetypeId = invoicetypeId;
	}

	/**
	 * @return the invoiceList
	 */
	public ArrayList<OrderEntity> getInvoiceList() {
		return invoiceList;
	}

	/**
	 * @param invoiceList
	 *            the invoiceList to set
	 */
	public void setInvoiceList(ArrayList<OrderEntity> invoiceList) {
		this.invoiceList = invoiceList;
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

	/**
	 * @return the maxNum
	 */
	public int getMaxNum() {
		return maxNum;
	}

	/**
	 * @param maxNum
	 *            the maxNum to set
	 */
	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	/**
	 * @return the orderCityId
	 */
	public final String getOrderCityId() {
		return orderCityId;
	}

	/**
	 * @param orderCityId
	 *            the orderCityId to set
	 */
	public final void setOrderCityId(String orderCityId) {
		this.orderCityId = orderCityId;
	}

	/**
	 * @return the companyname
	 */
	public final String getCompanyname() {
		return companyname;
	}

	/**
	 * @param companyname
	 *            the companyname to set
	 */
	public final void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	/**
	 * @return the intentionNumber
	 */
	public final String getIntentionNumber() {
		return intentionNumber;
	}

	/**
	 * @param intentionNumber
	 *            the intentionNumber to set
	 */
	public final void setIntentionNumber(String intentionNumber) {
		this.intentionNumber = intentionNumber;
	}

	/**
	 * @return the purName
	 */
	public String getPurName() {
		return purName;
	}

	/**
	 * @param purName
	 *            the purName to set
	 */
	public void setPurName(String purName) {
		this.purName = purName;
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

	public String getAllMoney() {
		return allMoney;
	}

	public void setAllMoney(String allMoney) {
		this.allMoney = allMoney;
	}

	public String getGuige() {
		return guige;
	}

	public void setGuige(String guige) {
		this.guige = guige;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OrderEntity [id=" + id + ", phone=" + phone + ", offerName=" + offerName + ", purName=" + purName
				+ ", categoryName=" + categoryName + ", brandname=" + brandname + ", price=" + price + ", inventory="
				+ inventory + ", prepayment=" + prepayment + ", allMoney=" + allMoney + ", resamount=" + resamount
				+ ", receivername=" + receivername + ", address=" + address + ", title=" + title + ", context="
				+ context + ", contextId=" + contextId + ", invoicetypename=" + invoicetypename + ", invoicetypeId="
				+ invoicetypeId + ", orderNumber=" + orderNumber + ", orderName=" + orderName + ", orderStateName="
				+ orderStateName + ", orderState=" + orderState + ", orderCity=" + orderCity + ", orderCityId="
				+ orderCityId + ", isDefault=" + isDefault + ", companyname=" + companyname + ", companyid=" + companyid
				+ ", intentionid=" + intentionid + ", maxNum=" + maxNum + ", intentionNumber=" + intentionNumber
				+ ", binaryvalue=" + binaryvalue + ", eare=" + eare + ", pic=" + pic + ", productName=" + productName
				+ ", guige=" + guige + ", opilList=" + opilList + ", pdmlList=" + pdmlList + ", invoiceList="
				+ invoiceList + ", contextList=" + contextList + ", typeList=" + typeList + "]";
	}

}
