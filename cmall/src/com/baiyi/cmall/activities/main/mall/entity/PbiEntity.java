package com.baiyi.cmall.activities.main.mall.entity;

import java.util.ArrayList;

//�̳�-��Ʒ-������Ϣ
public class PbiEntity {

	private int id;
	// ����
	private String pn;
	// ������
	private String sn;
	// ����
	private String bn;
	// ����
	private String pp;
	// ����
	private ArrayList<String> ggl;
	// �����ַ���
	private String ggs;
	// ��ƷͼƬ
	private String purl;

	/*
	 * ��ǰ�û��Ƿ���Թ���˲�Ʒ
	 *
	 * 1.null δ��¼
	 * 
	 * 2.true ���Խ��й���
	 * 
	 * 3.false Ϊ�Լ���Ʒ�����ɽ��й���
	 */
	private String io;

	/**
	 * @return the io
	 */
	public String getIo() {
		return io;
	}

	/**
	 * @param io
	 *            the io to set
	 */
	public void setIo(String io) {
		this.io = io;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getPp() {
		return pp;
	}

	public void setPp(String pp) {
		this.pp = pp;
	}

	public ArrayList<String> getGgl() {
		return ggl;
	}

	public void setGgl(ArrayList<String> ggl) {
		this.ggl = ggl;
	}

	public String getPurl() {
		return purl;
	}

	public void setPurl(String purl) {
		this.purl = purl;
	}

	public String getGgs() {
		return ggs;
	}

	public void setGgs(String ggs) {
		this.ggs = ggs;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PbiEntity [id=" + id + ", pn=" + pn + ", sn=" + sn + ", bn=" + bn + ", pp=" + pp + ", ggl=" + ggl
				+ ", ggs=" + ggs + ", purl=" + purl + ", io=" + io + "]";
	}

}
