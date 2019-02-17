package com.baiyi.cmall.activities.main.business.entity;

import java.util.ArrayList;
import java.util.List;

import com.baiyi.cmall.activities.main.mall.entity.PbiEntity;
import com.baiyi.cmall.model.Blm;

/**
 * 商家详情-全部商品
 * @author tangkun
 *
 */
public class CpimEntity {
	private CbmEntity cbmEntity;
	private ArrayList<PbiEntity> pbiList;
	
	private ArrayList<Blm> blms;
	
	public ArrayList<Blm> getBlms() {
		return blms;
	}
	public void setBlms(ArrayList<Blm> blms) {
		this.blms = blms;
	}
	public CbmEntity getCbmEntity() {
		return cbmEntity;
	}
	public void setCbmEntity(CbmEntity cbmEntity) {
		this.cbmEntity = cbmEntity;
	}
	public ArrayList<PbiEntity> getPbiList() {
		return pbiList;
	}
	public void setPbiList(ArrayList<PbiEntity> pbiList) {
		this.pbiList = pbiList;
	}
}
