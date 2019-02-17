package com.baiyi.cmall.model;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

/**
 * 产品描述pdi
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午2:32:37
 *
 */
public class Pdi implements Serializable {

	private String id;
	// 产品描述条目
	private Pim pim = null;
	// 产品描述条目值
	private ArrayList<Pvm> pvm = null;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the pim
	 */
	public Pim getPim() {
		return pim;
	}

	/**
	 * @param pim
	 *            the pim to set
	 */
	public void setPim(Pim pim) {
		this.pim = pim;
	}

	public ArrayList<Pvm> getPvm() {
		return pvm;
	}

	public void setPvm(ArrayList<Pvm> pvm) {
		this.pvm = pvm;
	}

	
}
