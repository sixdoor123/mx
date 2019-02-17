package com.baiyi.cmall.entity;

import java.util.List;
public class CompanyOfferEditTotalModel {
	private static final long serialVersionUID = 2409900014005896383L;
	private Long ID;

    private CompanyOfferEditModel offerModel;

	private List<CommonResPropertyModel> list;
	
	public CompanyOfferEditModel getOfferModel() {
		return offerModel;
	}
	public void setOfferModel(CompanyOfferEditModel offerModel) {
		this.offerModel = offerModel;
	}
	public List<CommonResPropertyModel> getList() {
		return list;
	}
	public void setList(List<CommonResPropertyModel> list) {
		this.list = list;
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
