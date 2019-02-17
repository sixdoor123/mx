package com.baiyi.cmall.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 货源中的选择查询的添加的实体
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-28 下午4:12:41
 */
public class SelectedInfo implements Serializable, Comparable<SelectedInfo> {

	private String id;
	// 分类Code
	private String cm_categorycode;
	// 分类名称
	private String cm_categoryname;
	// 本身所述的分类的父类的名称、
	private String super_categoryName;
	// 存放二级菜单的数据
	private ArrayList<SelectedInfo> sonDatas;
	
	//是否被点击
	private boolean isClick = false;
	
	
	//城市级别
	//1:大区
	//2:省
	//3:市
	//4:县
	private int flag;

	public SelectedInfo(String cm_categorycode, String cm_categoryname) {
		this.cm_categorycode = cm_categorycode;
		this.cm_categoryname = cm_categoryname;
	}

	public SelectedInfo() {

	}

	public boolean isClick() {
		return isClick;
	}

	public void setClick(boolean isClick) {
		this.isClick = isClick;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
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
	 * @return the cm_categorycode
	 */
	public String getCm_categorycode() {
		return cm_categorycode;
	}

	/**
	 * @param cm_categorycode
	 *            the cm_categorycode to set
	 */
	public void setCm_categorycode(String cm_categorycode) {
		this.cm_categorycode = cm_categorycode;
	}

	/**
	 * @return the cm_categoryname
	 */
	public String getCm_categoryname() {
		return cm_categoryname;
	}

	/**
	 * @param cm_categoryname
	 *            the cm_categoryname to set
	 */
	public void setCm_categoryname(String cm_categoryname) {
		this.cm_categoryname = cm_categoryname;
	}

	/**
	 * @return the super_categoryName
	 */
	public String getSuper_categoryName() {
		return super_categoryName;
	}

	/**
	 * @param super_categoryName
	 *            the super_categoryName to set
	 */
	public void setSuper_categoryName(String super_categoryName) {
		this.super_categoryName = super_categoryName;
	}

	/**
	 * @return the sonDatas
	 */
	public ArrayList<SelectedInfo> getSonDatas() {
		return sonDatas;
	}

	/**
	 * @param sonDatas
	 *            the sonDatas to set
	 */
	public void setSonDatas(ArrayList<SelectedInfo> sonDatas) {
		this.sonDatas = sonDatas;
	}

	
	@Override
	public String toString() {
		return "SelectedInfo [id=" + id + ", cm_categorycode=" + cm_categorycode + ", cm_categoryname="
				+ cm_categoryname + ", super_categoryName=" + super_categoryName + ", sonDatas=" + sonDatas
				+ ", flag=" + flag + "]";
	}

	@Override
	public int compareTo(SelectedInfo another) {
		
		return 0;
	}

}
