package com.baiyi.cmall.activities.main.buy.entity;

import java.util.ArrayList;

/**
 * ���ﳵ�ɹ���Ʒucwm
 * 
 * @author tangkun
 *
 */
public class UcwmlEntity {
	private int id;
	// ��Ʒid
	private int ri;
	// ����
	private int rc;
	// �۸�
	private String pr;
	// ��Դ����
	private String pn;
	// ������
	private String sn;
	// ����
	private String bn;
	// ͼƬ·��
	private String ip;
	// ͼƬ����
	private String in;
	// ���ﳵ��Ʒ���ֵ
	private ArrayList<UcrnvmEntity> ucrnvmList;

	// �Ƿ�ѡ��
	private boolean selected = false;

	// �̼�ID;
	private int ci;

	/**
	 * @return the ci
	 */
	public int getCi() {
		return ci;
	}

	/**
	 * @param ci
	 *            the ci to set
	 */
	public void setCi(int ci) {
		this.ci = ci;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRi() {
		return ri;
	}

	public void setRi(int ri) {
		this.ri = ri;
	}

	public int getRc() {
		return rc;
	}

	public void setRc(int rc) {
		this.rc = rc;
	}

	public String getPr() {
		return pr;
	}

	public void setPr(String pr) {
		this.pr = pr;
	}

	public String getPn() {
		return pn;
	}

	public void setPn(String pn) {
		this.pn = pn;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getBn() {
		return bn;
	}

	public void setBn(String bn) {
		this.bn = bn;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getIn() {
		return in;
	}

	public void setIn(String in) {
		this.in = in;
	}

	public ArrayList<UcrnvmEntity> getUcrnvmList() {
		return ucrnvmList;
	}

	public void setUcrnvmList(ArrayList<UcrnvmEntity> ucrnvmList) {
		this.ucrnvmList = ucrnvmList;
	}

	@Override
	public String toString() {
		return "UcwmlEntity [id=" + id + ", ri=" + ri + ", rc=" + rc + ", pr=" + pr + ", pn=" + pn + ", sn=" + sn
				+ ", bn=" + bn + ", ip=" + ip + ", in=" + in + ", ucrnvmList=" + ucrnvmList + ", selected=" + selected
				+ "]";
	}

}
