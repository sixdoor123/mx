package com.baiyi.cmall.activities.main.mall.entity;

import java.util.ArrayList;

/**
 * 产品规格列表
 * @author tangkun
 *
 */
public class PnwmlEntity {
	private PnmEntity pnmEntity;
	private ArrayList<PnvmlEntity> pnvmlList;
	public PnmEntity getPnmEntity() {
		return pnmEntity;
	}
	public void setPnmEntity(PnmEntity pnmEntity) {
		this.pnmEntity = pnmEntity;
	}
	public ArrayList<PnvmlEntity> getPnvmlList() {
		return pnvmlList;
	}
	public void setPnvmlList(ArrayList<PnvmlEntity> pnvmlList) {
		this.pnvmlList = pnvmlList;
	}
	
}
