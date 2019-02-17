/**
 * 
 */
package com.baiyi.cmall.activities.main.mall.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 商城-列表
 * @author tangkun
 *
 */
public class MallProListEntity implements Serializable{
	private int id;
	private int tt;
	private ArrayList<MallContentProEntity> proList;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTt() {
		return tt;
	}
	public void setTt(int tt) {
		this.tt = tt;
	}
	public ArrayList<MallContentProEntity> getProList() {
		return proList;
	}
	public void setProList(ArrayList<MallContentProEntity> proList) {
		this.proList = proList;
	}

}
