package com.baiyi.cmall.entity;

import java.io.Serializable;

/**
 * ��ҵ����ʵ����
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-2 ����2:36:56
 */
public class IndutryArgumentInfo implements Serializable{

	// ��������
	private String argNmae;
	// ����ֵ
	private String argValue;

	/**
	 * @return the argNmae
	 */
	public String getArgNmae() {
		return argNmae;
	}

	/**
	 * @param argNmae
	 *            the argNmae to set
	 */
	public void setArgNmae(String argNmae) {
		this.argNmae = argNmae;
	}

	/**
	 * @return the argValue
	 */
	public String getArgValue() {
		return argValue;
	}

	/**
	 * @param argValue
	 *            the argValue to set
	 */
	public void setArgValue(String argValue) {
		this.argValue = argValue;
	}

	public IndutryArgumentInfo() {
		// TODO Auto-generated constructor stub
	}
	public IndutryArgumentInfo(String argNmae, String argValue) {
		this.argNmae = argNmae;
		this.argValue = argValue;
	}

	
}
