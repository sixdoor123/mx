package com.baiyi.cmall.entity;

import java.io.Serializable;

/**
 *
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-3-8 
 *       ÏÂÎç5:22:49
 */
public class AppVersionInfo implements Serializable{
	
	private String pkName; 
	private String versionName ;
	private int versionCode;
	/**
	 * @return the pkName
	 */
	public String getPkName() {
		return pkName;
	}
	/**
	 * @param pkName the pkName to set
	 */
	public void setPkName(String pkName) {
		this.pkName = pkName;
	}
	/**
	 * @return the versionName
	 */
	public String getVersionName() {
		return versionName;
	}
	/**
	 * @param versionName the versionName to set
	 */
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	/**
	 * @return the versionCode
	 */
	public int getVersionCode() {
		return versionCode;
	}
	/**
	 * @param versionCode the versionCode to set
	 */
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}
	
	
}
