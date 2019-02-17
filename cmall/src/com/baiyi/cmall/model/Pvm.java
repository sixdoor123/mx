package com.baiyi.cmall.model;

import java.io.Serializable;

/**
 * 产品描述条目值pvm
 * 
 * @author sunxy
 * @version 创建时间：2016年4月27日 下午2:29:21
 *
 */
public class Pvm implements Serializable {

	// 描述条目id（关联描述条目）
	private String id = null;

	/**
	 * 描述条目值类型： 1:文字 2:图片
	 */
	private int it = 0;
	// 描述条目值
	private String il = null;
	// 序号
	private int no = 0;
	// 图片路径
	private String iurl = null;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the it
	 */
	public int getIt() {
		return it;
	}

	/**
	 * @param it
	 *            the it to set
	 */
	public void setIt(int it) {
		this.it = it;
	}

	/**
	 * @return the il
	 */
	public String getIl() {
		return il;
	}

	/**
	 * @param il
	 *            the il to set
	 */
	public void setIl(String il) {
		this.il = il;
	}

	/**
	 * @return the no
	 */
	public int getNo() {
		return no;
	}

	/**
	 * @param no
	 *            the no to set
	 */
	public void setNo(int no) {
		this.no = no;
	}

	/**
	 * @return the iurl
	 */
	public String getIurl() {
		return iurl;
	}

	/**
	 * @param iurl
	 *            the iurl to set
	 */
	public void setIurl(String iurl) {
		this.iurl = iurl;
	}

}
