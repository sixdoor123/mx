package com.baiyi.cmall.entity;

import java.io.Serializable;

import android.R.string;

/**
 * �ϴ�ͷ��
 * 
 * @author lizl
 * 
 */
public class AddressEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	// �ϴ�ͷ��
	private String imageupload;
	// �޸�ͷ��
	private String imageupdate;
	// ͷ���ַ
	private String imageaddress;
	// DATA
	private String data;

	public String getImageupload() {
		return imageupload;
	}

	public void setImageupload(String imageupload) {
		this.imageupload = imageupload;
	}

	public String getImageupdate() {
		return imageupdate;
	}

	public void setImageupdate(String imageupdate) {
		this.imageupdate = imageupdate;
	}

	public String getImageaddress() {
		return imageaddress;
	}

	public void setImageaddress(String imageaddress) {
		this.imageaddress = imageaddress;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
