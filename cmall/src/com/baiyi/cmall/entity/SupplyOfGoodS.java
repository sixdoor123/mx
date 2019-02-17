package com.baiyi.cmall.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *  存放货源列表数据，以及选择查询数据
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-28 
 *       下午4:22:32
 */
public class SupplyOfGoodS implements Serializable{

	
	//存放列表信息的集合
	ArrayList<GoodSOriginInfo> datas;

	// 存放分类选择查询的信息
	private ArrayList<SelectedInfo> categorys;

	// 存放地区的信息
	private ArrayList<SelectedInfo> areas;

	// 存放默认列表的信息
	private ArrayList<SelectedInfo> defaults;

	//分类名称
	private String categoryName;
	//地区名称
	private String areaName;
	//默认的名称
	private String defaulName;
	
	
	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * @param areaName the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * @return the defaulName
	 */
	public String getDefaulName() {
		return defaulName;
	}

	/**
	 * @param defaulName the defaulName to set
	 */
	public void setDefaulName(String defaulName) {
		this.defaulName = defaulName;
	}

	/**
	 * @return the datas
	 */
	public ArrayList<GoodSOriginInfo> getDatas() {
		return datas;
	}

	/**
	 * @param datas the datas to set
	 */
	public void setDatas(ArrayList<GoodSOriginInfo> datas) {
		this.datas = datas;
	}

	/**
	 * @return the categorys
	 */
	public ArrayList<SelectedInfo> getCategorys() {
		return categorys;
	}

	/**
	 * @param categorys the categorys to set
	 */
	public void setCategorys(ArrayList<SelectedInfo> categorys) {
		this.categorys = categorys;
	}

	/**
	 * @return the areas
	 */
	public ArrayList<SelectedInfo> getAreas() {
		return areas;
	}

	/**
	 * @param areas the areas to set
	 */
	public void setAreas(ArrayList<SelectedInfo> areas) {
		this.areas = areas;
	}

	/**
	 * @return the defaults
	 */
	public ArrayList<SelectedInfo> getDefaults() {
		return defaults;
	}

	/**
	 * @param defaults the defaults to set
	 */
	public void setDefaults(ArrayList<SelectedInfo> defaults) {
		this.defaults = defaults;
	}
	
	
}
