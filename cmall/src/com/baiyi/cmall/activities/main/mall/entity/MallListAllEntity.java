package com.baiyi.cmall.activities.main.mall.entity;

import java.util.ArrayList;

/**
 * 商城列表all
 * @author tangkun
 *
 */
public class MallListAllEntity {
	private ArrayList<MallMainHeadPisc> picsList;
	private ArrayList<MallMainHeadCategory> categoryList;
	private MallProListEntity proListEntity;
	public ArrayList<MallMainHeadPisc> getPicsList() {
		return picsList;
	}
	public void setPicsList(ArrayList<MallMainHeadPisc> picsList) {
		this.picsList = picsList;
	}
	public ArrayList<MallMainHeadCategory> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(ArrayList<MallMainHeadCategory> categoryList) {
		this.categoryList = categoryList;
	}
	public MallProListEntity getProListEntity() {
		return proListEntity;
	}
	public void setProListEntity(MallProListEntity proListEntity) {
		this.proListEntity = proListEntity;
	}

}
