package com.baiyi.cmall.model;

import java.io.Serializable;

import net.sf.json.JSONArray;

/**
 * ��ҳ�������� bdm
 * 
 * @author sunxy
 * @version ����ʱ�䣺2016��4��27�� ����1:32:49
 *
 */
public class Bdm implements Serializable{

	// ����
	private long tt = 0;
	// �б�����
	private  JSONArray li = null;
	/**
	 * @return the tt
	 */
	public long getTt() {
		return tt;
	}
	/**
	 * @param tt the tt to set
	 */
	public void setTt(long tt) {
		this.tt = tt;
	}
	/**
	 * @return the li
	 */
	public JSONArray getli() {
		return li;
	}
	/**
	 * @param li the li to set
	 */
	public void setli(JSONArray li) {
		this.li = li;
	}
	
	
	
}
