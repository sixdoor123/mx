package com.baiyi.cmall.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *  ��Ż�Դ�б����ݣ��Լ�ѡ���ѯ����
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-28 
 *       ����4:22:32
 */
public class SupplyOfGoodS implements Serializable{

	
	//����б���Ϣ�ļ���
	ArrayList<GoodSOriginInfo> datas;

	// ��ŷ���ѡ���ѯ����Ϣ
	private ArrayList<SelectedInfo> categorys;

	// ��ŵ�������Ϣ
	private ArrayList<SelectedInfo> areas;

	// ���Ĭ���б����Ϣ
	private ArrayList<SelectedInfo> defaults;

	//��������
	private String categoryName;
	//��������
	private String areaName;
	//Ĭ�ϵ�����
	private String defaulName;
	
	
	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	 * @return the areaName
	 */
	public String getAreaName() {
		return areaName;
	}

	/**
	 * @param areaName the areaName to set
	 */
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	/**
	 * @return the defaulName
	 */
	public String getDefaulName() {
		return defaulName;
	}

	/**
	 * @param defaulName the defaulName to set
	 */
	public void setDefaulName(String defaulName) {
		this.defaulName = defaulName;
	}

	/**
	 * @return the datas
	 */
	public ArrayList<GoodSOriginInfo> getDatas() {
		return datas;
	}

	/**
	 * @param datas the datas to set
	 */
	public void setDatas(ArrayList<GoodSOriginInfo> datas) {
		this.datas = datas;
	}

	/**
	 * @return the categorys
	 */
	public ArrayList<SelectedInfo> getCategorys() {
		return categorys;
	}

	/**
	 * @param categorys the categorys to set
	 */
	public void setCategorys(ArrayList<SelectedInfo> categorys) {
		this.categorys = categorys;
	}

	/**
	 * @return the areas
	 */
	public ArrayList<SelectedInfo> getAreas() {
		return areas;
	}

	/**
	 * @param areas the areas to set
	 */
	public void setAreas(ArrayList<SelectedInfo> areas) {
		this.areas = areas;
	}

	/**
	 * @return the defaults
	 */
	public ArrayList<SelectedInfo> getDefaults() {
		return defaults;
	}

	/**
	 * @param defaults the defaults to set
	 */
	public void setDefaults(ArrayList<SelectedInfo> defaults) {
		this.defaults = defaults;
	}
	
	
}
