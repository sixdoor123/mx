package com.baiyi.cmall.activities.main.mall.req;

import java.util.ArrayList;

/**
 * ��ӹ��ﳵ�ķ�װ����uscawm
 * @author tangkun
 *
 */
public class ReqUscawm {
	//��Ʒid
	private String pi;
	//��Ʒ����
	private int pc;
	//��Ʒ�۸�
	private String pp;
	//���ﳵNorm����
	private ArrayList<ReqUcrnvm> ucrnvmList;
	public String getPi() {
		return pi;
	}
	public void setPi(String pi) {
		this.pi = pi;
	}
	public int getPc() {
		return pc;
	}
	public void setPc(int pc) {
		this.pc = pc;
	}
	public String getPp() {
		return pp;
	}
	public void setPp(String pp) {
		this.pp = pp;
	}
	public ArrayList<ReqUcrnvm> getUcrnvmList() {
		return ucrnvmList;
	}
	public void setUcrnvmList(ArrayList<ReqUcrnvm> ucrnvmList) {
		this.ucrnvmList = ucrnvmList;
	}
	
	public String toPostString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("pi="+pi);
		sb.append("&");
		sb.append("pc="+pc);
		sb.append("&");
		sb.append("pp="+pp);
		for(int i = 0; i < ucrnvmList.size(); i++)
		{
			ReqUcrnvm ucrnvm = ucrnvmList.get(i);
			sb.append("&");
			sb.append("ucrnvm["+i+"].id=" + ucrnvm.getId());
			sb.append("&");
			sb.append("ucrnvm["+i+"].nn=" + ucrnvm.getNn());
			sb.append("&");
			sb.append("ucrnvm["+i+"].nu=" + ucrnvm.getNu());
			sb.append("&");
			sb.append("ucrnvm["+i+"].nv=" + ucrnvm.getNv());
			sb.append("&");
			sb.append("ucrnvm["+i+"].nvi=" + ucrnvm.getNvi());
			sb.append("&");
			sb.append("ucrnvm["+i+"].on=" + ucrnvm.getOn());
			sb.append("&");
			sb.append("ucrnvm["+i+"].ri=" + ucrnvm.getRi());
		}
		System.out.println(sb.toString());
		return sb.toString();
	}

}
