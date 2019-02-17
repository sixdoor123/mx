package com.baiyi.cmall.activities.main.business;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baiyi.cmall.activities.main.business.entity.BusinessDetailEntity;
import com.baiyi.cmall.activities.main.business.entity.CbmEntity;
import com.baiyi.cmall.activities.main.business.entity.CpimEntity;
import com.baiyi.cmall.activities.main.business.entity.DpiEntity;
import com.baiyi.cmall.activities.main.mall.entity.PbiEntity;
import com.baiyi.cmall.activities.main.total.TotalUtils;
import com.baiyi.cmall.model.Blm;
import com.baiyi.cmall.utils.JsonParseBase;

import android.util.Log;

public class JsonParseBusiness extends JsonParseBase {

	/**
	 * 商家-所有产品列表
	 * 
	 * @param root
	 * @return
	 */
	public static CpimEntity getCpimEntity(JSONArray root) {
		Log.d("TAG", "商家-所有产品列表---"+root.toString());
		CpimEntity entity = new CpimEntity();
		try {
			JSONObject jo = root.getJSONObject(0);
			if (!getstatus(jo)) {
				return null;
			}
			JSONObject dataJson = getResultObj(jo);
			JSONObject cbmJson = dataJson.getJSONObject("cbm");
			CbmEntity cmb = new CbmEntity();
			cmb.setCf(getIntNodeValue(cbmJson, "cf"));
			cmb.setCn(getStringNodeValue(cbmJson, "cn"));
			cmb.setCurl(getStringNodeValue(cbmJson, "curl"));
			cmb.setId(getIntNodeValue(cbmJson, "id"));
			entity.setCbmEntity(cmb);

			JSONObject pbiObj= dataJson.getJSONObject("pbi");
			JSONArray pbiArray = pbiObj.getJSONArray("li");
			ArrayList<PbiEntity> pbiList = new ArrayList<>();
			for (int i = 0; i < pbiArray.length(); i++) {
				JSONObject o = pbiArray.getJSONObject(i);
				PbiEntity pbi = new PbiEntity();
				pbi.setBn(getStringNodeValue(o, "bn"));
				pbi.setId(getIntNodeValue(o, "id"));
				pbi.setPn(getStringNodeValue(o, "pn"));
				pbi.setPp(getStringNodeValue(o, "pp"));
				pbi.setPurl(getStringNodeValue(o, "purl"));
				pbi.setSn(getStringNodeValue(o, "sn"));
				JSONArray gglArray = o.getJSONArray("ggl");
				StringBuilder builder = new StringBuilder();
				builder.append("  ");
				for (int j = 0; j < gglArray.length(); j++) {
					builder.append(gglArray.getString(j) + "  ");
				}
				pbi.setGgs(builder.toString());
				pbiList.add(pbi);
			}
			entity.setPbiList(pbiList);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}

	/**
	 * 商家-店铺详情
	 * 
	 * @param root
	 * @return
	 */
	public static BusinessDetailEntity getBusinessDetail(JSONArray root) {
		BusinessDetailEntity entity = new BusinessDetailEntity();
		try {
			JSONObject jo = root.getJSONObject(0);
			if (!getstatus(jo)) {
				return null;
			}
			JSONObject dataJson = getResultObj(jo);
			JSONObject cbmJson = dataJson.getJSONObject("cbm");
			CbmEntity cmb = new CbmEntity();
			cmb.setCf(getIntNodeValue(cbmJson, "cf"));
			cmb.setCn(getStringNodeValue(cbmJson, "cn"));
			cmb.setCurl(getStringNodeValue(cbmJson, "curl"));
			cmb.setId(getIntNodeValue(cbmJson, "id"));
			entity.setCbmEntity(cmb);

			JSONObject dpiJson = dataJson.getJSONObject("dpi");
			DpiEntity dpiEntity = new DpiEntity();
			dpiEntity.setCa(getStringNodeValue(dpiJson, "ca"));
			dpiEntity.setCn(getStringNodeValue(dpiJson, "cn"));
			dpiEntity.setCt(getStringNodeValue(dpiJson, "ct"));
			dpiEntity.setPh(getStringNodeValue(dpiJson, "ph"));
			dpiEntity.setTi(getStringNodeValue(dpiJson, "ti"));
			entity.setDpiEntity(dpiEntity);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}

	/**
	 * 供应
	 * 
	 * @param result
	 * @return
	 */
	public static ArrayList<Blm> getAllProduct(JSONArray result) {
		ArrayList<Blm> blms = new ArrayList<Blm>();
		try {
			for (int i = 0; i < result.length(); i++) {
				JSONObject jsonObject = result.getJSONObject(i);
				JSONObject data = jsonObject.getJSONObject("data");
				// 添加总数
				int total = getIntNodeValue(data, "tt");
				TotalUtils.getIntence().setTotal(total);

				JSONArray li = data.getJSONArray("li");
				for (int j = 0; j < li.length(); j++) {
					JSONObject object = li.getJSONObject(j);
					Blm blm = new Blm();
					blm.setId(object.getString("id"));
					blm.setC1(object.getString("c1"));
					blm.setC2(object.getString("c2"));
					blm.setC3(object.getString("c3"));
					blm.setC4(object.getString("c4"));
					blm.setC5(object.getString("c5"));
					blm.setC6(object.getString("c6"));

					blms.add(blm);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return blms;
	}

}
