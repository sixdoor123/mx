/**
 * 
 */
package com.baiyi.cmall.activities.main.business.entity;

import java.io.Serializable;

/**
 * @author tangkun
 *
 */
public class SupplyEntity implements Serializable{
	
	private String supplyContent;
	private String companyName;
	private String area;
	private String minPrice;
	public String getSupplyContent() {
		return supplyContent;
	}
	public void setSupplyContent(String supplyContent) {
		this.supplyContent = supplyContent;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}

}
