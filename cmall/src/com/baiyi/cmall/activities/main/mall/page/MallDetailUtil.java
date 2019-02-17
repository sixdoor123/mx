package com.baiyi.cmall.activities.main.mall.page;

import java.util.ArrayList;
import java.util.List;

import com.baiyi.cmall.model.Rpv;
import com.baiyi.cmall.utils.Utils;

/**
* 商城——产品详情——属性工具类
*@author sunxy
*/
public class MallDetailUtil {

	/**
	 * 属性类型 1工业属性 2工艺属性 3物理属性 4化学属性(pt)
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

		//在此将所有的属性按照属性类型（pt）进行分类，并将空值全部过滤掉不显示
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
			r1.setPtname("工业属性");
			r1.setRpvs(pt1);
			rs.add(r1);
		}
		if (!Utils.isStringEmpty(pt2)) {
			Rpv r2 = new Rpv();
			r2.setPtname("工艺属性");
			r2.setRpvs(pt2);
			rs.add(r2);
		}
		if (!Utils.isStringEmpty(pt3)) {
			Rpv r3 = new Rpv();
			r3.setPtname("物理属性");
			r3.setRpvs(pt3);
			rs.add(r3);
		}
		if (!Utils.isStringEmpty(pt4)) {
			Rpv r4 = new Rpv();
			r4.setPtname("化学属性");
			r4.setRpvs(pt4);
			rs.add(r4);
		}

		return rs;
	}
}
