/**
 * 
 */
package com.baiyi.cmall.activities.main.mall.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 筛选-全部分类
 * @author tangkun
 *
 */
public class MallCategoryEntity implements Serializable{
	
	private String name;
	private List<String> childList;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getChildList() {
		return childList;
	}
	public void setChildList(List<String> childList) {
		this.childList = childList;
	}

}
