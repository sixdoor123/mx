package com.baiyi.cmall.activities.main.mall.pop.entity;

/**
 * ɸѡ�ּ� qcm
 * 
 * @author sunxy
 */
public class Qcm {

	// ��ʾ�ּ�����
	private String qn;
	// �ּ���ʶ
	private String qp;

	// �ӷ�������
	private String subName;
	//�������ֵ
	private String bd = null;

	/**
	 * @return the bd
	 */
	public String getBd() {
		return bd;
	}

	/**
	 * @param bd the bd to set
	 */
	public void setBd(String bd) {
		this.bd = bd;
	}

	/**
	 * @return the subName
	 */
	public String getSubName() {
		return subName;
	}

	/**
	 * @param subName
	 *            the subName to set
	 */
	public void setSubName(String subName) {
		this.subName = subName;
	}

	/**
	 * @return the qn
	 */
	public String getQn() {
		return qn;
	}

	/**
	 * @param qn
	 *            the qn to set
	 */
	public void setQn(String qn) {
		this.qn = qn;
	}

	/**
	 * @return the qp
	 */
	public String getQp() {
		return qp;
	}

	/**
	 * @param qp
	 *            the qp to set
	 */
	public void setQp(String qp) {
		this.qp = qp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Qcm [qn=" + qn + ", qp=" + qp + ", subName=" + subName + "]";
	}

}
