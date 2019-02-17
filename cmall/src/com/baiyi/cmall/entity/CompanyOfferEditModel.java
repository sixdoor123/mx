package com.baiyi.cmall.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 渚涘簲缂栬緫Model
 * @author 琚佸皬寤�?
 */
public class CompanyOfferEditModel {
	private Long ID;
	/**
	 * 搴忓垪鍙�?
	 */
	private static final long serialVersionUID = 6151196989028556259L;
	/**
     * 渚涘簲鍚嶇�?
     */
    private String offername;
    /**
     * 渚涘簲鍐呭
     */
    private String offerdetail;
    /**
     * 渚涘簲寮�鏃堕�?(鍙戝竷鏃堕棿)
     */
    private Date offerbegintime;
    /**
     * 渚涘簲缁撴潫鏃堕�?
     */
    private Date offerendtime;
    /**
     * 鍒版湡鑷姩寮��?
     */
    private Boolean autobeginflag;
    /**
     * 鍒版湡鑷姩缁撴�?
     */
    private Boolean autoendflag;
    /**
     * 搴撳�?
     */
    private Long inventory;
    /**
     * 浠锋�?
     */
    private BigDecimal price;
    /**
     * 棰勪�?
     */
    private BigDecimal prepayment;  
    /**
     * 鍝佺墝锛堢叅绉嶏�?
     */
    private String brandName;
	/**
     * 鍒嗙�?
     */
    private String categoryName;
    /**
     * 浜у湴鍚嶇О
     */
    private String originPlaceName;
    /**
     * 浜ゅ壊鍦板悕绉�
     */
    private String deliveryPlaceName;
    /**
     * 浜у湴
     */
    private Long originplace;
    /**
     * 浜ゅ壊鍦�?
     */
    private Long deliveryplace;
    /**
     * 鍝佺�?
     */
    private Long brand;
    /**
     * 绫诲�?
     */
    private Long category;
    
	public String getOffername() {
		return offername;
	}
	public void setOffername(String offername) {
		this.offername = offername;
	}
	public String getOfferdetail() {
		return offerdetail;
	}
	public void setOfferdetail(String offerdetail) {
		this.offerdetail = offerdetail;
	}
	public Date getOfferbegintime() {
		return offerbegintime;
	}
	public void setOfferbegintime(Date offerbegintime) {
		this.offerbegintime = offerbegintime;
	}
	public Date getOfferendtime() {
		return offerendtime;
	}
	public void setOfferendtime(Date offerendtime) {
		this.offerendtime = offerendtime;
	}
	public Boolean getAutobeginflag() {
		return autobeginflag;
	}
	public void setAutobeginflag(Boolean autobeginflag) {
		this.autobeginflag = autobeginflag;
	}
	public Boolean getAutoendflag() {
		return autoendflag;
	}
	public void setAutoendflag(Boolean autoendflag) {
		this.autoendflag = autoendflag;
	}
	public Long getInventory() {
		return inventory;
	}
	public void setInventory(Long inventory) {
		this.inventory = inventory;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getPrepayment() {
		return prepayment;
	}
	public void setPrepayment(BigDecimal prepayment) {
		this.prepayment = prepayment;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getOriginPlaceName() {
		return originPlaceName;
	}
	public void setOriginPlaceName(String originPlaceName) {
		this.originPlaceName = originPlaceName;
	}
	public String getDeliveryPlaceName() {
		return deliveryPlaceName;
	}
	public void setDeliveryPlaceName(String deliveryPlaceName) {
		this.deliveryPlaceName = deliveryPlaceName;
	}
	public Long getOriginplace() {
		return originplace;
	}
	public void setOriginplace(Long originplace) {
		this.originplace = originplace;
	}
	public Long getDeliveryplace() {
		return deliveryplace;
	}
	public void setDeliveryplace(Long deliveryplace) {
		this.deliveryplace = deliveryplace;
	}
	public Long getBrand() {
		return brand;
	}
	public void setBrand(Long brand) {
		this.brand = brand;
	}
	public Long getCategory() {
		return category;
	}
	public void setCategory(Long category) {
		this.category = category;
	}
	/**
	 * @return the iD
	 */
	public Long getID() {
		return ID;
	}
	/**
	 * @param iD the iD to set
	 */
	public void setID(Long iD) {
		ID = iD;
	}
	
}
