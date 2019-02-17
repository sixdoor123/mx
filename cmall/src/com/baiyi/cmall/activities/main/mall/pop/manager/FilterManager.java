package com.baiyi.cmall.activities.main.mall.pop.manager;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baiyi.cmall.activities.main.mall.pop.entity.Qcm;
import com.baiyi.cmall.model.Ftvm;

/**
 *
 * @author sunxy
 */
public class FilterManager {

	/**
	 * 一级分类数据
	 * 
	 * @param result
	 */
	public static List<Qcm> getFirstFilterData(Object result) {

		List<Qcm> datas = new ArrayList<Qcm>();
		try {
			JSONArray array = (JSONArray) result;
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONArray data = jsonObject.getJSONArray("data");
				for (int j = 0; j < data.length(); j++) {
					JSONObject object = data.getJSONObject(j);

					Qcm qcm = new Qcm();
					qcm.setQn(object.getString("qn"));
					qcm.setQp(object.getString("qp"));
					qcm.setSubName("全部");

					datas.add(qcm);
				}
			}

			return datas;

		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}

	/**
	 * 二级
	 * 
	 * @param result
	 * @param qn
	 */
	public static List<Ftvm> getSeconedFilterData(Object result, String qn) {

		List<Ftvm> ftvms = new ArrayList<Ftvm>();
//		Ftvm f = new Ftvm();
//		f.setDs("全部"/* +qn */);
//		ftvms.add(f);

		try {
			JSONArray array = (JSONArray) result;
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);

				JSONObject data = jsonObject.getJSONObject("data");

				JSONArray jsonArray = data.getJSONArray("ftvml");
				for (int j = 0; j < jsonArray.length(); j++) {

					JSONObject object = jsonArray.getJSONObject(j);
					Ftvm ftvm = new Ftvm();
					ftvm.setBd(object.getString("bd"));
					ftvm.setDs(object.getString("ds"));
					ftvm.setId(object.getString("id"));

					ftvms.add(ftvm);
				}
			}

			return ftvms;

		} catch (Exception e) {
		}

		return null;
	}

}
