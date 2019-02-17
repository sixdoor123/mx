package com.baiyi.cmall.activities.main.buy.req;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * �µ�-�ύ��Ʒģ��
 * @author tangkun
 *
 */
@SuppressWarnings("serial")
public class ReqOswm implements Serializable{
	//����id
	private int id;
	//��Ʒid
	private int pid;
	//��Ʒ����
	private String pn;
	//�̼�����
	private String cn;
	//��Ʒ����
	private int pc;
	//�Ƿ�ѡ�в�Ʒ
	private boolean isSelect;
	private String pr;
	//�۸���ֵid
	private ArrayList<Long> nvids;
	private ArrayList<String> spList;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getPn() {
		return pn;
	}
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public void setPn(String pn) {
		this.pn = pn;
	}
	public int getPc() {
		return pc;
	}
	public void setPc(int pc) {
		this.pc = pc;
	}
	public String getPr() {
		return pr;
	}
	public void setPr(String pr) {
		this.pr = pr;
	}
	public ArrayList<Long> getNvids() {
		return nvids;
	}
	public void setNvids(ArrayList<Long> nvids) {
		this.nvids = nvids;
	}
	public boolean isSelect() {
		return isSelect;
	}
	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}
	public ArrayList<String> getSpList() {
		return spList;
	}
	public void setSpList(ArrayList<String> spList) {
		this.spList = spList;
	}
	public String getPostData(int i)
	{
		if(!isSelect)
		{
			return "";
		}
		StringBuilder sb = new StringBuilder();
//	    sb.append("&");
//		sb.append("oswm["+i+"].id=" + id);
		sb.append("&");
		sb.append("oswm["+i+"].pid=" + pid);
		sb.append("&");
		sb.append("oswm["+i+"].pn=" + pn);
		sb.append("&");
		sb.append("oswm["+i+"].pc=" + pc);
//		sb.append("&");
//		sb.append("isSelect=" + isSelect);
		for(int j = 0; j < nvids.size(); j++)
		{
			sb.append("&");
			sb.append("oswm[" + i + "].nvids["+j+"]="+nvids.get(j));
		}
		return sb.toString();
	}
}
