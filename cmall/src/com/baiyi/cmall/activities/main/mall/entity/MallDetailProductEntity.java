package com.baiyi.cmall.activities.main.mall.entity;

import java.util.ArrayList;

/**
 * 商城-商品
 * @author tangkun
 *
 */
public class MallDetailProductEntity {
	private int id;
	private ArrayList<MallMainHeadPisc> picsList;
	private PbiEntity pbiEntity;
	private CbiEntity cbiEntity;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ArrayList<MallMainHeadPisc> getPicsList() {
		return picsList;
	}
	public void setPicsList(ArrayList<MallMainHeadPisc> picsList) {
		this.picsList = picsList;
	}
	public PbiEntity getPbiEntity() {
		return pbiEntity;
	}
	public void setPbiEntity(PbiEntity pbiEntity) {
		this.pbiEntity = pbiEntity;
	}
	public CbiEntity getCbiEntity() {
		return cbiEntity;
	}
	public void setCbiEntity(CbiEntity cbiEntity) {
		this.cbiEntity = cbiEntity;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MallDetailProductEntity [id=" + id + ", picsList=" + picsList + ", pbiEntity=" + pbiEntity
				+ ", cbiEntity=" + cbiEntity + "]";
	}


}
