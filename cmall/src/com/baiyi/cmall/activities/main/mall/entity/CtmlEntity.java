package com.baiyi.cmall.activities.main.mall.entity;

public class CtmlEntity {
	//id
	private String id;
	
	//�û�ͷ��·��
	private String uh;
	
	//�û�����
	private String un;
	
	//����ʱ��
	private String ct;
	
	//�Ǽ�
	private int sl;
	
	//���
	private String rp;
	
	//��������
	private String pc;
	
	//��������
	private long pt;
	
	//��������
	private String cc;

	public CtmlEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CtmlEntity(String id, String uh, String un, String ct, int sl,
			String rp, String pc, long pt, String cc) {
		super();
		this.id = id;
		this.uh = uh;
		this.un = un;
		this.ct = ct;
		this.sl = sl;
		this.rp = rp;
		this.pc = pc;
		this.pt = pt;
		this.cc = cc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUh() {
		return uh;
	}

	public void setUh(String uh) {
		this.uh = uh;
	}

	public String getUn() {
		return un;
	}

	public void setUn(String un) {
		this.un = un;
	}

	public String getCt() {
		return ct;
	}

	public void setCt(String ct) {
		this.ct = ct;
	}

	public int getSl() {
		return sl;
	}

	public void setSl(int sl) {
		this.sl = sl;
	}

	public String getRp() {
		return rp;
	}

	public void setRp(String rp) {
		this.rp = rp;
	}

	public String getPc() {
		return pc;
	}

	public void setPc(String pc) {
		this.pc = pc;
	}

	public long getPt() {
		return pt;
	}

	public void setPt(long pt) {
		this.pt = pt;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}
	
}
