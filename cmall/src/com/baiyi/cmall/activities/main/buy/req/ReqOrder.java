package com.baiyi.cmall.activities.main.buy.req;

import java.io.Serializable;
import java.util.List;

/**
 * 下单提交
 * @author tangkun
 *
 */
@SuppressWarnings("serial")
public class ReqOrder implements Serializable{
	//0	立即购买 1	购物车结算
	private int st;
	//商家名称
	private String cn;
	
	//下单订单模型
	private ReqOpm opmEntity;
	
	//下单-提交产品模型
	private List<ReqOswm> oswmList;
	public int getSt() {
		return st;
	}
	public void setSt(int st) {
		this.st = st;
	}
	
	
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public ReqOpm getOpmEntity() {
		return opmEntity;
	}
	public void setOpmEntity(ReqOpm opmEntity) {
		this.opmEntity = opmEntity;
	}
	public List<ReqOswm> getOswmList() {
		return oswmList;
	}
	public void setOswmList(List<ReqOswm> oswmList) {
		this.oswmList = oswmList;
	}

}
