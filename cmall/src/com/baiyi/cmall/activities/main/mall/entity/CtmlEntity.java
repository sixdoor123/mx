package com.baiyi.cmall.activities.main.mall.entity;

public class CtmlEntity {
	//id
	private String id;
	
	//用户头像路径
	private String uh;
	
	//用户名称
	private String un;
	
	//评价时间
	private String ct;
	
	//星级
	private int sl;
	
	//规格
	private String rp;
	
	//购买数量
	private String pc;
	
	//购买日期
	private long pt;
	
	//评价内容
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
