package com.baiyi.cmall.database;

import com.baiyi.core.database.AbstractBaseModel;

/**
 *
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-18 
 *       ÉÏÎç11:15:48
 */
public class Guanzhu extends AbstractBaseModel{
	
	private long time;
	private int gId;
	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}
	/**
	 * @return the gId
	 */
	public int getgId() {
		return gId;
	}
	/**
	 * @param gId the gId to set
	 */
	public void setgId(int gId) {
		this.gId = gId;
	}

}
