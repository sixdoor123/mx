package com.baiyi.cmall.activities.main.buy.entity;

import java.util.ArrayList;

/**
 * 用户购物车列表
 * @author tangkun
 *
 */
public class UclwmEntity {
	//商家名称
	private String cn;
	//商家id
	private int ci;
	//购物车采购商品
	private ArrayList<UcwmlEntity> ucwmlList;
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public int getCi() {
		return ci;
	}
	public void setCi(int ci) {
		this.ci = ci;
	}
	public ArrayList<UcwmlEntity> getUcwmlList() {
		return ucwmlList;
	}
	public void setUcwmlList(ArrayList<UcwmlEntity> ucwmlList) {
		this.ucwmlList = ucwmlList;
	}
	@Override
	public String toString() {
		return "UclwmEntity [cn=" + cn + ", ci=" + ci + ", ucwmlList=" + ucwmlList + "]";
	}
	
	
}
