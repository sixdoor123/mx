package com.baiyi.cmall.entity;

import java.io.Serializable;

/**
 * 请求网络状态，结果码
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-8 下午3:00:38
 */
public class RequestNetResultInfo implements Serializable{

	// 状态、结果码
	private int status;
	// 返回的信息
	private String msg;

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RequestNetResultInfo [status=" + status + ", msg=" + msg + "]";
	}
	
	
}
