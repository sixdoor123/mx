package com.baiyi.cmall.activities.main.buy.req;

import java.io.Serializable;
import java.util.List;

/**
 * �µ��ύ
 * @author tangkun
 *
 */
@SuppressWarnings("serial")
public class ReqOrder implements Serializable{
	//0	�������� 1	���ﳵ����
	private int st;
	//�̼�����
	private String cn;
	
	//�µ�����ģ��
	private ReqOpm opmEntity;
	
	//�µ�-�ύ��Ʒģ��
	private List<ReqOswm> oswmList;
	public int getSt() {
		return st;
	}
	public void setSt(int st) {
		this.st = st;
	}
	
	
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public ReqOpm getOpmEntity() {
		return opmEntity;
	}
	public void setOpmEntity(ReqOpm opmEntity) {
		this.opmEntity = opmEntity;
	}
	public List<ReqOswm> getOswmList() {
		return oswmList;
	}
	public void setOswmList(List<ReqOswm> oswmList) {
		this.oswmList = oswmList;
	}

}
