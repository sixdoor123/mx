package com.baiyi.cmall.entity;

import java.io.Serializable;

/**
 * 工业参数实体类
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-2 下午2:36:56
 */
public class IndutryArgumentInfo implements Serializable{

	// 参数分类
	private String argNmae;
	// 参数值
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
