package com.baiyi.cmall.model;

import java.io.Serializable;
import java.util.List;

import com.baiyi.cmall.R;

/**
 * ����rpv
 * 
 * @author sunxy
 * @version ����ʱ�䣺2016��4��27�� ����2:34:14
 *
 */
public class Rpv implements Serializable {

	private String id;
	// ����ֵ
	private String pv = null;
	// ���
	private int no = 0;
	// ��������
	private String pn = null;
	
	private List<Rpv> rpvs = null;

	/**
	 * �������� 1:��ҵ���� 2:�������� 3:�������� 4:��ѧ����
	 */
	private int pt = 0;
	private String ptname;
	// ���Ե�λ
	private String pu = null;

	public List<Rpv> getRpvs() {
		return rpvs;
	}

	public void setRpvs(List<Rpv> rpvs) {
		this.rpvs = rpvs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the pv
	 */
	public String getPv() {
		return pv;
	}

	/**
	 * @param pv
	 *            the pv to set
	 */
	public void setPv(String pv) {
		this.pv = pv;
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
	 * @return the pn
	 */
	public String getPn() {
		return pn;
	}

	/**
	 * @param pn
	 *            the pn to set
	 */
	public void setPn(String pn) {
		this.pn = pn;
	}

	/**
	 * @return the pt
	 */
	public int getPt() {
		return pt;
	}

	/**
	 * @param pt
	 *            the pt to set
	 */
	public void setPt(int pt) {
		this.pt = pt;
	}

	public String getPtname() {
		return ptname;
	}

	public void setPtname(String ptname) {
		this.ptname = ptname;
	}

	/**
	 * @return the pu
	 */
	public String getPu() {
		return pu;
	}

	/**
	 * @param pu
	 *            the pu to set
	 */
	public void setPu(String pu) {
		this.pu = pu;
	}

}
