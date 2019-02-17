package com.baiyi.cmall.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.core.util.ContextUtil;

import android.content.Context;

/**
 * 解析JSON的工具类
 * 
 * @author sunxy
 *
 */
public class JSONParseUtils {

	/**
	 *得到JSONObject
	 * 
	 * @return
	 */
	private static JSONObject getJsonObject(Context context) {

		StringBuilder builder = new StringBuilder();
		JSONObject object = null;
		BufferedReader reader = null;
		try {
			InputStream inputStream = context.getAssets().open(
					"data_source.txt");
			reader = new BufferedReader(new InputStreamReader(inputStream,"GBK"));

			String string = null;
			
			while ((string = reader.readLine()) != null) {
				builder.append(string);
			}
			
			object = new JSONObject(builder.toString());
			return object;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return null;
	}
	
	/**
	 * 解析JSON
	 * @param context
	 * @return
	 */
	public static ArrayList<GoodsSourceInfo> jsonParse(Context context){
		JSONObject jsonObject = getJsonObject(context);
		
		ArrayList<GoodsSourceInfo> datas = new ArrayList<GoodsSourceInfo>();
		
		try {
			JSONArray array = jsonObject.getJSONArray("list");
			
			for (int i = 0; i < array.length(); i++) {
				GoodsSourceInfo info = new GoodsSourceInfo();
				JSONObject object = array.getJSONObject(i);
				
				info.setGoodSName(object.getString("goodSName"));
				info.setGoodSCategory(object.getString("goodSCategory"));
				info.setGoodSPlace(object.getString("goodSPlace"));
				info.setGoodSMerchant(object.getString("goodSMerchant"));
				info.setGoodSSpecification(object.getString("goodSSpecification"));
				info.setGoodSWeight(object.getString("goodSWeight"));
				info.setGoodSPrePrice(object.getString("goodSPrePrice"));
				info.setGoodSPriceWay(object.getString("goodSPriceWay"));
				info.setGoodSDelivery(object.getString("goodSDelivery"));
				info.setGoodSPurchaseContent(object.getString("goodSPurchaseContent"));
				info.setGoodSpriceInterval(object.getString("goodSpriceInterval"));
				info.setGoodSPutTime(object.getString("goodSPutTime"));
				info.setGoodSArea(object.getString("goodSArea"));
				info.setGoodSCommitTime(object.getString("goodSCommitTime"));
				info.setIntentionOrderState(object.getString("intentionOrderState"));
				info.setGoodSBreed(object.getString("goodSBreed"));
				info.setGoodSContactPerson(object.getString("goodSContactPerson"));
				info.setGoodSContactWay(object.getString("goodSContactWay"));
				info.setGoodSPurchaseOrderId(object.getInt("goodSPurchaseOrderId"));
				info.setGoodSProviderOrderId(object.getInt("goodSProviderOrderId"));
				info.setGoodSPurchaseNeed(object.getString("goodSPurchaseNeed"));
				
				//属性
				info.setGoodSCalorificValue(object.getString("GoodSCalorificValue")+"");
				info.setGoodSTotalMoisture(object.getString("goodSTotalMoisture")+"%");
				info.setGoodSInlandWaters(object.getString("goodSInlandWaters")+"%");
				info.setGoodSVolatileMatter(object.getString("goodSVolatileMatter")+"%");
				info.setGoodSAshContent(object.getString("goodSAshContent")+"%");
				info.setGoodSTotalSulfur(object.getString("goodSTotalSulfur")+"%");
				info.setGoodSFixedCarbon(object.getString("goodSFixedCarbon")+"%");
				info.setGoodSParticleSize(object.getString("goodSParticleSize")+"%");
				info.setGoodSDensity(object.getString("goodSDensity")+"%");
				
				info.setGoodSAshFusionPoint(object.getString("goodSAshFusionPoint")+"%");
				info.setHardgroverindabilityIndex(object.getString("hardgroverindabilityIndex")+"%");
				info.setGoodSCinderFeature(object.getString("goodSCinderFeature")+"%");
				info.setGoodSBurlExponent(object.getString("goodSBurlExponent")+"%");
				info.setGoodSCuticleThickness(object.getString("goodSCuticleThickness")+"%");
				info.setGoodSExpansionFactor(object.getString("goodSExpansionFactor")+"%");
				info.setGoodSFlowabilitySize(object.getString("goodSFlowabilitySize")+"%");
				info.setGoodSAluminumOxide(object.getString("goodSAluminumOxide")+"%");
				info.setGoodSHydrogenSize(object.getString("goodSHydrogenSize")+"%");
				info.setGoodSNitrogenSize(object.getString("goodSNitrogenSize")+"%");
				
				info.setGoodSBrand(object.getString("goodSBrand"));
				info.setGoodSStartCity(object.getString("goodSStartCity"));
				info.setGoodSEndCity(object.getString("goodSEndCity"));
				datas.add(info);
			}
			return datas;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
}
