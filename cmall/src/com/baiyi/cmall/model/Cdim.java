package com.baiyi.cmall.model;

import java.io.Serializable;

/**
 * �̼�����-�������� cdim
 * 
 * @author sunxy
 * @version ����ʱ�䣺2016��4��27�� ����2:02:30
 *
 */
public class Cdim implements Serializable{

	// ���̻�����Ϣ
	private Cbm cbm = null;
	// ��������
	private Dpi dpi = null;

	/**
	 * @return the cbm
	 */
	public Cbm getCbm() {
		return cbm;
	}

	/**
	 * @param cbm
	 *            the cbm to set
	 */
	public void setCbm(Cbm cbm) {
		this.cbm = cbm;
	}

	/**
	 * @return the dpi
	 */
	public Dpi getDpi() {
		return dpi;
	}

	/**
	 * @param dpi
	 *            the dpi to set
	 */
	public void setDpi(Dpi dpi) {
		this.dpi = dpi;
	}

}
