package com.baiyi.cmall.model;

import java.io.Serializable;

/**
 * ��Ʒ������Ŀֵpvm
 * 
 * @author sunxy
 * @version ����ʱ�䣺2016��4��27�� ����2:29:21
 *
 */
public class Pvm implements Serializable {

	// ������Ŀid������������Ŀ��
	private String id = null;

	/**
	 * ������Ŀֵ���ͣ� 1:���� 2:ͼƬ
	 */
	private int it = 0;
	// ������Ŀֵ
	private String il = null;
	// ���
	private int no = 0;
	// ͼƬ·��
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
