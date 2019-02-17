/**
 * 
 */
package com.baiyi.cmall.activities.main.mall.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 店铺商品 item
 * 
 * @author tangkun
 * 
 */
public class MallProEntity implements Serializable {

	private int id;
	// "pn": "氯化石蜡",
	private String pn;
	// "sn": "Chlorinated paraffin",
	private String sn;
	// "bn": "氯化链烷烃"
	private String bn;
	// "pp": "400.00"
	private String pp;
	// "起订量:90","纯度:99", "凝固点:-50"
	private ArrayList<String> specificList;

	private String purl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getBn() {
		return bn;
	}

	public void setBn(String bn) {
		this.bn = bn;
	}

	public String getPp() {
		return pp;
	}

	public void setPp(String pp) {
		this.pp = pp;
	}

	public String getPurl() {
		return purl;
	}

	public void setPurl(String purl) {
		this.purl = purl;
	}

	public ArrayList<String> getSpecificList() {
		return specificList;
	}

	public void setSpecificList(ArrayList<String> specificList) {
		this.specificList = specificList;
	}
}
