package com.baiyi.cmall.activities.main.buy.req;

import java.util.ArrayList;

/**
 * �ύ����ģ��
 * @author tangkun
 *
 */
public class ReqEntity {
//	0��������
//	 1���ﳵ����
//	 2�༭
	private int st;
	//������Ʒģ��
	private ReqOpm opmEntity;
	//�ύ��Ʒģ��
	private ArrayList<ReqOswm> oswmList;
	public int getSt() {
		return st;
	}
	public void setSt(int st) {
		this.st = st;
	}
	public ReqOpm getOpmEntity() {
		return opmEntity;
	}
	public void setOpmEntity(ReqOpm opmEntity) {
		this.opmEntity = opmEntity;
	}
	public ArrayList<ReqOswm> getOswmList() {
		return oswmList;
	}
	public void setOswmList(ArrayList<ReqOswm> oswmList) {
		this.oswmList = oswmList;
	}

}
