package com.baiyi.cmall.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * ��Դ�е�ѡ���ѯ����ӵ�ʵ��
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-28 ����4:12:41
 */
public class SelectedInfo implements Serializable, Comparable<SelectedInfo> {

	private String id;
	// ����Code
	private String cm_categorycode;
	// ��������
	private String cm_categoryname;
	// ���������ķ���ĸ�������ơ�
	private String super_categoryName;
	// ��Ŷ����˵�������
	private ArrayList<SelectedInfo> sonDatas;
	
	//�Ƿ񱻵��
	private boolean isClick = false;
	
	
	//���м���
	//1:����
	//2:ʡ
	//3:��
	//4:��
	private int flag;

	public SelectedInfo(String cm_categorycode, String cm_categoryname) {
		this.cm_categorycode = cm_categorycode;
		this.cm_categoryname = cm_categoryname;
	}

	public SelectedInfo() {

	}

	public boolean isClick() {
		return isClick;
	}

	public void setClick(boolean isClick) {
		this.isClick = isClick;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

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
	 * @return the cm_categorycode
	 */
	public String getCm_categorycode() {
		return cm_categorycode;
	}

	/**
	 * @param cm_categorycode
	 *            the cm_categorycode to set
	 */
	public void setCm_categorycode(String cm_categorycode) {
		this.cm_categorycode = cm_categorycode;
	}

	/**
	 * @return the cm_categoryname
	 */
	public String getCm_categoryname() {
		return cm_categoryname;
	}

	/**
	 * @param cm_categoryname
	 *            the cm_categoryname to set
	 */
	public void setCm_categoryname(String cm_categoryname) {
		this.cm_categoryname = cm_categoryname;
	}

	/**
	 * @return the super_categoryName
	 */
	public String getSuper_categoryName() {
		return super_categoryName;
	}

	/**
	 * @param super_categoryName
	 *            the super_categoryName to set
	 */
	public void setSuper_categoryName(String super_categoryName) {
		this.super_categoryName = super_categoryName;
	}

	/**
	 * @return the sonDatas
	 */
	public ArrayList<SelectedInfo> getSonDatas() {
		return sonDatas;
	}

	/**
	 * @param sonDatas
	 *            the sonDatas to set
	 */
	public void setSonDatas(ArrayList<SelectedInfo> sonDatas) {
		this.sonDatas = sonDatas;
	}

	
	@Override
	public String toString() {
		return "SelectedInfo [id=" + id + ", cm_categorycode=" + cm_categorycode + ", cm_categoryname="
				+ cm_categoryname + ", super_categoryName=" + super_categoryName + ", sonDatas=" + sonDatas
				+ ", flag=" + flag + "]";
	}

	@Override
	public int compareTo(SelectedInfo another) {
		
		return 0;
	}

}
