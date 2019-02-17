package com.baiyi.cmall.activities.main.buy.req;

import java.util.ArrayList;

/**
 * 提交订单模型
 * @author tangkun
 *
 */
public class ReqEntity {
//	0立即购买
//	 1购物车结算
//	 2编辑
	private int st;
	//订单产品模型
	private ReqOpm opmEntity;
	//提交产品模型
	private ArrayList<ReqOswm> oswmList;
	public int getSt() {
		return st;
	}
	public void setSt(int st) {
		this.st = st;
	}
	public ReqOpm getOpmEntity() {
		return opmEntity;
	}
	public void setOpmEntity(ReqOpm opmEntity) {
		this.opmEntity = opmEntity;
	}
	public ArrayList<ReqOswm> getOswmList() {
		return oswmList;
	}
	public void setOswmList(ArrayList<ReqOswm> oswmList) {
		this.oswmList = oswmList;
	}

}
