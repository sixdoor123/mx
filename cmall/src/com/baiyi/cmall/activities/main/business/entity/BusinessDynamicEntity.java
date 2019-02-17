/**
 * 
 */
package com.baiyi.cmall.activities.main.business.entity;

import java.io.Serializable;

/**
 * @author tangkun
 *
 */
public class BusinessDynamicEntity implements Serializable{
	
	private String imgPath;
	private String price;
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}

}
