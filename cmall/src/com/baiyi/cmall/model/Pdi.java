package com.baiyi.cmall.model;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

/**
 * ��Ʒ����pdi
 * 
 * @author sunxy
 * @version ����ʱ�䣺2016��4��27�� ����2:32:37
 *
 */
public class Pdi implements Serializable {

	private String id;
	// ��Ʒ������Ŀ
	private Pim pim = null;
	// ��Ʒ������Ŀֵ
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
