package com.baiyi.cmall.activities.main.business.entity;

import java.io.Serializable;

/**
 * �̼�����-���̻�������cbm
 * @author tangkun
 *
 */
public class CbmEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//id
	private int id;
	//��˾logoUrl
	private String curl;
	//��ע��
	private int cf;
	//��˾����
	private String cn;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCurl() {
		return curl;
	}
	public void setCurl(String curl) {
		this.curl = curl;
	}
	public int getCf() {
		return cf;
	}
	public void setCf(int cf) {
		this.cf = cf;
	}
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}

}
