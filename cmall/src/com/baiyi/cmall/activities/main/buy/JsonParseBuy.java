package com.baiyi.cmall.activities.main.buy;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baiyi.cmall.activities.main.buy.entity.OwmEntity;
import com.baiyi.cmall.activities.main.buy.entity.UclwmEntity;
import com.baiyi.cmall.activities.main.buy.entity.UcrnvmEntity;
import com.baiyi.cmall.activities.main.buy.entity.UcwmlEntity;
import com.baiyi.cmall.activities.main.mall.entity.PnmEntity;
import com.baiyi.cmall.activities.main.mall.entity.PnvmlEntity;
import com.baiyi.cmall.utils.JsonParseBase;

import android.util.Log;

public class JsonParseBuy extends JsonParseBase {

	/**
	 * 商家-所有产品列表
	 * 
	 * @param root
	 * @return
	 */
	public static ArrayList<UclwmEntity> getUclwmList(JSONArray root) {
		ArrayList<UclwmEntity> dataList = new ArrayList<UclwmEntity>();
		try {
			JSONObject jo = root.getJSONObject(0);
			if (!getstatus(jo)) {
				return null;
			}
			JSONArray array = getDataArray(jo);
			for (int i = 0; i < array.length(); i++) {
				UclwmEntity entity = new UclwmEntity();
				JSONObject uclwmJson = array.getJSONObject(i);
				entity.setCi(getIntNodeValue(uclwmJson, "ci"));
				entity.setCn(getStringNodeValue(uclwmJson, "cn"));

				JSONArray ucwmlArray = uclwmJson.getJSONArray("ucwml");
				entity.setUcwmlList(getUcwmlList(ucwmlArray));

				dataList.add(entity);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataList;
	}

	/**
	 * 购物车产品
	 * 
	 * @param ucwmlArray
	 * @return
	 */
	public static ArrayList<UcwmlEntity> getUcwmlList(JSONArray ucwmlArray) {
		ArrayList<UcwmlEntity> ucwmlList = new ArrayList<>();
		try {
			for (int j = 0; j < ucwmlArray.length(); j++) {
				JSONObject ucwmJson = ucwmlArray.getJSONObject(j);
				UcwmlEntity ucwm = new UcwmlEntity();
				ucwm.setBn(getStringNodeValue(ucwmJson, "bn"));
				ucwm.setId(getIntNodeValue(ucwmJson, "id"));
				ucwm.setIn(getStringNodeValue(ucwmJson, "in"));
				ucwm.setIp(getStringNodeValue(ucwmJson, "ip"));
				ucwm.setPn(getStringNodeValue(ucwmJson, "pn"));
				ucwm.setPr(getStringNodeValue(ucwmJson, "pr"));
				ucwm.setRc(getIntNodeValue(ucwmJson, "rc"));
				ucwm.setRi(getIntNodeValue(ucwmJson, "ri"));
				ucwm.setSn(getStringNodeValue(ucwmJson, "sn"));
				JSONArray ucrnvm = ucwmJson.getJSONArray("ucrnvml");
				ucwm.setUcrnvmList(getUcrnvmList(ucrnvm));
				ucwmlList.add(ucwm);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ucwmlList;
	}

	/**
	 * 购物车-产品规格
	 * 
	 * @param array
	 * @return
	 */
	public static ArrayList<UcrnvmEntity> getUcrnvmList(JSONArray array) {
		ArrayList<UcrnvmEntity> ucrnvmList = new ArrayList<>();
		try {
			for (int j = 0; j < array.length(); j++) {
				JSONObject ucrnvmJson = array.getJSONObject(j);
				UcrnvmEntity ucrnvm = new UcrnvmEntity();
				ucrnvm.setId(getIntNodeValue(ucrnvmJson, "id"));
				ucrnvm.setNn(getStringNodeValue(ucrnvmJson, "nn"));
				ucrnvm.setNu(getStringNodeValue(ucrnvmJson, "nu"));
				ucrnvm.setNv(getStringNodeValue(ucrnvmJson, "nv"));
				ucrnvm.setNvi(getIntNodeValue(ucrnvmJson, "nvi"));
				ucrnvm.setOn(getIntNodeValue(ucrnvmJson, "on"));
				ucrnvm.setRi(getIntNodeValue(ucrnvmJson, "ri"));
				ucrnvmList.add(ucrnvm);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return ucrnvmList;
	}

	/**
	 * 结算生成订单列表
	 * 
	 * @param jsonArray
	 */
	public static List<OwmEntity> getOrderList(String result) {
		Log.d("TAG", "getOrderList--" + result);
		JSONArray jsonArray = null;
		List<OwmEntity> owmEntities = new ArrayList<OwmEntity>();
		try {
			jsonArray = new JSONArray(result);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				JSONArray data = jsonObject.getJSONArray("data");
				for (int j = 0; j < data.length(); j++) {
					JSONObject object = data.getJSONObject(j);
					// 产品详情
					JSONObject om = object.getJSONObject("om");
					OwmEntity owmEntity = new OwmEntity();
					owmEntity.setId(getStringNodeValue(om,"id"));
					owmEntity.setBn(om.getString("bn"));
					owmEntity.setSn(om.getString("sn"));
					owmEntity.setCn(om.getString("cn"));
					owmEntity.setBm(om.getString("bm"));
					owmEntity.setPc(om.getString("pc"));
					owmEntity.setCi(om.getString("ci"));
					owmEntity.setInv(om.getString("inv"));
					owmEntity.setPn(om.getString("pn"));
					owmEntity.setIn(om.getString("in"));
					owmEntity.setPi(om.getString("pi"));
					owmEntity.setPr(om.getString("pr"));
					owmEntity.setIp(om.getString("ip"));

					// 规格参数
					JSONArray array = object.getJSONArray("onvwml");
					List<PnvmlEntity> entities = new ArrayList<PnvmlEntity>();
					for (int k = 0; k < array.length(); k++) {
						JSONObject onvwml = array.getJSONObject(k);

						PnvmlEntity entity = new PnvmlEntity();
						entity.setId(getIntNodeValue(onvwml,"id"));
						entity.setNn(onvwml.getString("nu"));
						entity.setOn(getIntNodeValue(onvwml,"on"));
						entity.setNvi(onvwml.getString("nvi"));
						entity.setNv(onvwml.getString("nv"));
						entity.setNn(onvwml.getString("nn"));

						entities.add(entity);
					}
					owmEntity.setEntities(entities);
					owmEntities.add(owmEntity);
				}
			}

			return owmEntities;
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

}
