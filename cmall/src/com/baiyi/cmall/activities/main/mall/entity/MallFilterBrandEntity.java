/**
 * 
 */
package com.baiyi.cmall.activities.main.mall.entity;

import java.io.Serializable;

/**
 * 商城-筛选-品牌
 * @author tangkun
 *
 */
public class MallFilterBrandEntity implements Serializable{
	
	private int bid;
	private String name;
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
