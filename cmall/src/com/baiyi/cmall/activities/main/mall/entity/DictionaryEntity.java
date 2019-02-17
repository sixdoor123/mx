package com.baiyi.cmall.activities.main.mall.entity;

import java.util.ArrayList;

//产品规格 字典
public class DictionaryEntity {
	private ArrayList<PnwmlEntity> pnwmlList;
	private ArrayList<PnpdmlEntity> pnpdmlList;
	
	
	public ArrayList<PnwmlEntity> getPnwmlList() {
		return pnwmlList;
	}
	public void setPnwmlList(ArrayList<PnwmlEntity> pnwmlList) {
		this.pnwmlList = pnwmlList;
	}
	public ArrayList<PnpdmlEntity> getPnpdmlList() {
		return pnpdmlList;
	}
	public void setPnpdmlList(ArrayList<PnpdmlEntity> pnpdmlList) {
		this.pnpdmlList = pnpdmlList;
	}

}
