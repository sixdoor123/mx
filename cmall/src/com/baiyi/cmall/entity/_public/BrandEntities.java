package com.baiyi.cmall.entity._public;

import java.io.Serializable;

/**
 *  品牌的实体
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-31 
 *       上午10:49:09
 */
public class BrandEntities implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = Long.MAX_VALUE;
	//品牌id
	private String id;
	//品牌名称
	private String brandName;
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
	 * @return the brandName
	 */
	public String getBrandName() {
		return brandName;
	}
	/**
	 * @param brandName the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
}
