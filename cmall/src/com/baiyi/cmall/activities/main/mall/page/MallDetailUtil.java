package com.baiyi.cmall.activities.main.mall.page;

import java.util.ArrayList;
import java.util.List;

import com.baiyi.cmall.model.Rpv;
import com.baiyi.cmall.utils.Utils;

/**
* �̳ǡ�����Ʒ���顪�����Թ�����
*@author sunxy
*/
public class MallDetailUtil {

	/**
	 * �������� 1��ҵ���� 2�������� 3�������� 4��ѧ����(pt)
	 * 
	 * @param rpvs
	 * @return
	 */
	public static List<Rpv> getCategoryRpv(List<Rpv> rpvs) {

		List<Rpv> rs = new ArrayList<Rpv>();
		ArrayList<Rpv> pt1 = new ArrayList<Rpv>();
		ArrayList<Rpv> pt2 = new ArrayList<Rpv>();
		ArrayList<Rpv> pt3 = new ArrayList<Rpv>();
		ArrayList<Rpv> pt4 = new ArrayList<Rpv>();

		//�ڴ˽����е����԰����������ͣ�pt�����з��࣬������ֵȫ�����˵�����ʾ
		for (Rpv rpv : rpvs) {

			if (1 == rpv.getPt()) {
				pt1.add(rpv);
			} else if (2 == rpv.getPt()) {
				pt2.add(rpv);
			} else if (3 == rpv.getPt()) {
				pt3.add(rpv);
			} else if (4 == rpv.getPt()) {
				pt4.add(rpv);
			}
		}
		if (!Utils.isStringEmpty(pt1)) {
			Rpv r1 = new Rpv();
			r1.setPtname("��ҵ����");
			r1.setRpvs(pt1);
			rs.add(r1);
		}
		if (!Utils.isStringEmpty(pt2)) {
			Rpv r2 = new Rpv();
			r2.setPtname("��������");
			r2.setRpvs(pt2);
			rs.add(r2);
		}
		if (!Utils.isStringEmpty(pt3)) {
			Rpv r3 = new Rpv();
			r3.setPtname("��������");
			r3.setRpvs(pt3);
			rs.add(r3);
		}
		if (!Utils.isStringEmpty(pt4)) {
			Rpv r4 = new Rpv();
			r4.setPtname("��ѧ����");
			r4.setRpvs(pt4);
			rs.add(r4);
		}

		return rs;
	}
}
