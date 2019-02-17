package com.baiyi.cmall.activities.main.mall.entity;

/**
 * 价格字典列表
 * 
 * @author tangkun
 *
 */
public class PnpdmlEntity {
	//{
//  "id": 476,
//  "pr": "720.00",
//  "in": 170,
//  "nvi": 326,
//  "ni": 140
//}
	private int id;
	private String pr;
	private int in;
	private int nvi;
	private int ni;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPr() {
		return pr;
	}
	public void setPr(String pr) {
		this.pr = pr;
	}
	public int getIn() {
		return in;
	}
	public void setIn(int in) {
		this.in = in;
	}
	public int getNvi() {
		return nvi;
	}
	public void setNvi(int nvi) {
		this.nvi = nvi;
	}
	public int getNi() {
		return ni;
	}
	public void setNi(int ni) {
		this.ni = ni;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PnpdmlEntity [id=" + id + ", pr=" + pr + ", in=" + in + ", nvi=" + nvi + ", ni=" + ni + "]";
	}

	
}
