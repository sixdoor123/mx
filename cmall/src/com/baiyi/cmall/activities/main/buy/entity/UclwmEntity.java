package com.baiyi.cmall.activities.main.buy.entity;

import java.util.ArrayList;

/**
 * �û����ﳵ�б�
 * @author tangkun
 *
 */
public class UclwmEntity {
	//�̼�����
	private String cn;
	//�̼�id
	private int ci;
	//���ﳵ�ɹ���Ʒ
	private ArrayList<UcwmlEntity> ucwmlList;
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public int getCi() {
		return ci;
	}
	public void setCi(int ci) {
		this.ci = ci;
	}
	public ArrayList<UcwmlEntity> getUcwmlList() {
		return ucwmlList;
	}
	public void setUcwmlList(ArrayList<UcwmlEntity> ucwmlList) {
		this.ucwmlList = ucwmlList;
	}
	@Override
	public String toString() {
		return "UclwmEntity [cn=" + cn + ", ci=" + ci + ", ucwmlList=" + ucwmlList + "]";
	}
	
	
}
