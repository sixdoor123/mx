package com.baiyi.cmall.activities.main.mall.entity;

/**
 * �̳�-��Ʒ-�̼һ�����Ϣ
 * @author tangkun
 *
 */
public class CbiEntity {
	//�̼�id
	private int id;
	//��˾����
	private String cm;
	//��˾logo��ַ
	private String lg;
	//���
	private String cd;
	//��ע����
	private int fc;
	//ȫ����Ʒ����
	private int ap;
	//ȫ����������
	private int ac;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCm() {
		return cm;
	}
	public void setCm(String cm) {
		this.cm = cm;
	}
	public String getLg() {
		return lg;
	}
	public void setLg(String lg) {
		this.lg = lg;
	}
	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	public int getFc() {
		return fc;
	}
	public void setFc(int fc) {
		this.fc = fc;
	}
	public int getAp() {
		return ap;
	}
	public void setAp(int ap) {
		this.ap = ap;
	}
	public int getAc() {
		return ac;
	}
	public void setAc(int ac) {
		this.ac = ac;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CbiEntity [id=" + id + ", cm=" + cm + ", lg=" + lg + ", cd=" + cd + ", fc=" + fc + ", ap=" + ap
				+ ", ac=" + ac + "]";
	}

}
