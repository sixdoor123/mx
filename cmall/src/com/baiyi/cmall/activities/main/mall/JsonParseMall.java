package com.baiyi.cmall.activities.main.mall;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baiyi.cmall.activities.main.mall.entity.CbiEntity;
import com.baiyi.cmall.activities.main.mall.entity.DictionaryEntity;
import com.baiyi.cmall.activities.main.mall.entity.MallContentProEntity;
import com.baiyi.cmall.activities.main.mall.entity.MallDetailProductEntity;
import com.baiyi.cmall.activities.main.mall.entity.MallListAllEntity;
import com.baiyi.cmall.activities.main.mall.entity.MallMainHeadCategory;
import com.baiyi.cmall.activities.main.mall.entity.MallMainHeadPisc;
import com.baiyi.cmall.activities.main.mall.entity.MallMainMenuEntity;
import com.baiyi.cmall.activities.main.mall.entity.MallProEntity;
import com.baiyi.cmall.activities.main.mall.entity.MallProListEntity;
import com.baiyi.cmall.activities.main.mall.entity.PbiEntity;
import com.baiyi.cmall.activities.main.mall.entity.PnmEntity;
import com.baiyi.cmall.activities.main.mall.entity.PnpdmlEntity;
import com.baiyi.cmall.activities.main.mall.entity.PnvmlEntity;
import com.baiyi.cmall.activities.main.mall.entity.PnwmlEntity;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.utils.JsonParseBase;

import android.util.Log;

public class JsonParseMall extends JsonParseBase{
	
	/**
	 * 获取商城-菜单
	 * @param root
	 * @return
	 */
	public static ArrayList<MallMainMenuEntity> getMainMenu(JSONArray root)
	{
		ArrayList<MallMainMenuEntity> data = new ArrayList<MallMainMenuEntity>();
		try {
			JSONObject jo = root.getJSONObject(0);
			if(!getstatus(jo))
			{
				return data;
			}
			JSONArray array = getDataArray(jo);
			for(int i = 0; i < array.length(); i++)
			{
				JSONObject o = array.getJSONObject(i);
				MallMainMenuEntity entity = new MallMainMenuEntity();
				entity.setId(getStringNodeValue(o, "id"));
				entity.setCc(getStringNodeValue(o, "cc"));
				entity.setCn(getStringNodeValue(o, "cn"));
				data.add(entity);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * 商城主页-列表所有数据
	 * @param root
	 * @return
	 */
	public static MallListAllEntity getMallListAllEntity(JSONArray root)
	{
		MallListAllEntity mallListAllEntity = new MallListAllEntity();
		try {
			JSONObject jo = root.getJSONObject(0);
			if(!getstatus(jo))
			{
				return mallListAllEntity;
			}
			JSONObject data = getResultObj(jo);
			JSONArray biml = data.getJSONArray("biml");
			ArrayList<MallMainHeadPisc> picsList = new ArrayList<MallMainHeadPisc>();
			for(int i = 0; i < biml.length(); i++)
			{
				JSONObject o = biml.getJSONObject(i);
				MallMainHeadPisc pic = new MallMainHeadPisc();
				pic.setFp(getStringNodeValue(o, "fp"));
				pic.setNo(getStringNodeValue(o, "no"));
				pic.setUrl(getStringNodeValue(o, "url"));
				picsList.add(pic);
			}
			mallListAllEntity.setPicsList(picsList);
			JSONArray scil = data.getJSONArray("scil");
			ArrayList<MallMainHeadCategory> categoryList = new ArrayList<>();
			for(int i = 0; i < scil.length(); i++)
			{
				JSONObject o = scil.getJSONObject(i);
				MallMainHeadCategory category = new MallMainHeadCategory();
				category.setCc(getStringNodeValue(o, "cc"));
				category.setCn(getStringNodeValue(o, "cn"));
				category.setId(getStringNodeValue(o, "id"));
				categoryList.add(category);
			}
			mallListAllEntity.setCategoryList(categoryList);
			
			JSONObject hpil = data.getJSONObject("hpil");
			MallProListEntity mallProListEntity = new MallProListEntity();
			mallProListEntity.setId(getIntNodeValue(hpil, "id"));
			mallProListEntity.setTt(getIntNodeValue(hpil, "tt"));
			
			JSONArray li = hpil.getJSONArray("li");
			ArrayList<MallContentProEntity> proList = new ArrayList<>();
			for(int i = 0; i < li.length(); i++)
			{
				JSONObject o = li.getJSONObject(i);
				MallContentProEntity pro = new MallContentProEntity();
				pro.setId(getIntNodeValue(o, "id"));
				pro.setPn(getStringNodeValue(o, "pn"));
				pro.setUrl(getStringNodeValue(o, "url"));
				proList.add(pro);
			}
			mallProListEntity.setProList(proList);
			mallListAllEntity.setProListEntity(mallProListEntity);
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mallListAllEntity;
	}
	
	/**
	 * 获取商城-二级列表
	 * @param root
	 * @return
	 */
	public static ArrayList<MallProEntity> getCategoryProList(JSONArray root)
	{
		ArrayList<MallProEntity> data = new ArrayList<MallProEntity>();
		try {
			JSONObject jo = root.getJSONObject(0);
			if(!getstatus(jo))
			{
				return data;
			}
			JSONObject jbData = getResultObj(jo);
			
			JSONArray array = jbData.getJSONArray("li");
			for(int i = 0; i < array.length(); i++)
			{
				JSONObject o = array.getJSONObject(i);
				MallProEntity entity = new MallProEntity();
				entity.setId(getIntNodeValue(o, "id"));
				entity.setBn(getStringNodeValue(o, "bn"));
				entity.setPn(getStringNodeValue(o, "pn"));
				entity.setPp(getStringNodeValue(o, "pp"));
				entity.setSn(getStringNodeValue(o, "sn"));
				entity.setPurl(getStringNodeValue(o, "purl"));
				
				JSONArray tip = o.getJSONArray("ggl");
				ArrayList<String> tips = new ArrayList<>();
				for(int j = 0; j < tip.length(); j++)
				{
					tips.add(tip.getString(j));
				}
				entity.setSpecificList(tips);
				data.add(entity);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * 产品信息-产品
	 * @return
	 */
	public static MallDetailProductEntity getMallDetailProductEntity(JSONArray root)
	{
		
		Log.d("TAG", "产品信息-产品---"+root.toString());
		MallDetailProductEntity entity = new MallDetailProductEntity();
		try {
			JSONObject jo = root.getJSONObject(0);
			if(!getstatus(jo))
			{
				return null;
			}
			JSONObject jbData = getResultObj(jo);
			entity.setId(getIntNodeValue(jbData, "id"));
			
			ArrayList<MallMainHeadPisc> pics = getBiml(jbData);
			entity.setPicsList(pics);
			
			entity.setCbiEntity(getCbiEntity(jbData));
			
			entity.setPbiEntity(getPbiEntity(jbData));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}
	
	/**
	 * 产品规格 字典
	 * @param root
	 * @return
	 */
	public static DictionaryEntity getPnwmlEntity(JSONArray root)
	{
		DictionaryEntity entity = new DictionaryEntity();
		try {
			JSONObject jo = root.getJSONObject(0);
			if(!getstatus(jo))
			{
				return null;
			}
			JSONObject dataJson = getResultObj(jo);
			
			JSONArray pnwmlArray = dataJson.getJSONArray("pnwml");
			entity.setPnwmlList(getPnwmlist(pnwmlArray));
			JSONArray pnpdmlArray = dataJson.getJSONArray("pnpdml");
			entity.setPnpdmlList(getPnpdmlList(pnpdmlArray));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}
	/**
	 * 价格字典列表
	 * @param pnpdmlArray
	 * @return
	 */
	public static ArrayList<PnpdmlEntity> getPnpdmlList(JSONArray pnpdmlArray)
	{
		ArrayList<PnpdmlEntity> data = new ArrayList<>();
		try {
			for(int i = 0; i < pnpdmlArray.length(); i++)
			{
				JSONObject o = pnpdmlArray.getJSONObject(i);
				PnpdmlEntity entity = new PnpdmlEntity();
				entity.setId(getIntNodeValue(o, "id"));
				entity.setIn(getIntNodeValue(o, "in"));
				entity.setNi(getIntNodeValue(o, "ni"));
				entity.setNvi(getIntNodeValue(o, "nvi"));
				entity.setPr(getStringNodeValue(o, "pr"));
				data.add(entity);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * 产品规格列表
	 * @param pnwmlArray
	 * @return
	 */
	public static ArrayList<PnwmlEntity> getPnwmlist(JSONArray pnwmlArray)
	{
		ArrayList<PnwmlEntity> data = new ArrayList<>();
		try {
			for(int i = 0; i < pnwmlArray.length(); i++)
			{
				JSONObject o = pnwmlArray.getJSONObject(i);
				PnwmlEntity entity = new PnwmlEntity();
				JSONObject pnmJson = o.getJSONObject("pnm");
				entity.setPnmEntity(getPnmEntity(pnmJson));
				JSONArray pnvmlJson = o.getJSONArray("pnvml");
				entity.setPnvmlList(getPnvmlList(pnvmlJson));
				data.add(entity);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * 产品价格规格模型
	 * @param pnmJson
	 * @return
	 */
	public static PnmEntity getPnmEntity(JSONObject pnmJson)
	{
		PnmEntity entity = new PnmEntity();
		entity.setId(getIntNodeValue(pnmJson, "id"));
		entity.setNn(getStringNodeValue(pnmJson, "nn"));
		entity.setNu(getStringNodeValue(pnmJson, "nu"));
		entity.setOn(getIntNodeValue(pnmJson, "on"));
		entity.setRi(getIntNodeValue(pnmJson, "ri"));
		entity.setRt(getIntNodeValue(pnmJson, "rt"));
		return entity;
	}
	
	/**
	 * 价格规格值列表
	 * @param pnvmlJson
	 * @return
	 */
	public static ArrayList<PnvmlEntity> getPnvmlList(JSONArray pnvmlArray)
	{
		ArrayList<PnvmlEntity> data = new ArrayList<>();
		try {
			for(int i = 0; i < pnvmlArray.length(); i++)
			{
				JSONObject o = pnvmlArray.getJSONObject(i);
				PnvmlEntity entity = new PnvmlEntity();
				entity.setId(getIntNodeValue(o, "id"));
				entity.setNi(getIntNodeValue(o, "ni"));
				entity.setNv(getStringNodeValue(o, "nv"));
				entity.setOn(getIntNodeValue(o, "on"));
				data.add(entity);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	/**
	 * 图片列表 biml
	 * @param root
	 * @return
	 */
	public static ArrayList<MallMainHeadPisc> getBiml(JSONObject root)
	{
		ArrayList<MallMainHeadPisc> pics = new ArrayList<>();
		JSONArray bimlArray;
		try {
			bimlArray = root.getJSONArray("biml");
			for(int i = 0; i < bimlArray.length(); i++)
			{
				JSONObject o = bimlArray.getJSONObject(i);
				MallMainHeadPisc entity = new MallMainHeadPisc();
				entity.setFp(getStringNodeValue(o, "fp"));
				entity.setNo(getStringNodeValue(o, "no"));
				entity.setUrl(getStringNodeValue(o, "url"));
				pics.add(entity);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pics;
	}
	
	/**
	 * 产品详情-产品信息
	 * @param root
	 * @return
	 */
	public static PbiEntity getPbiEntity(JSONObject root)
	{
		PbiEntity pbi = new PbiEntity();
		try {
			JSONObject o = root.getJSONObject("pbi");
			pbi.setBn(getStringNodeValue(o, "bn"));
			pbi.setId(getIntNodeValue(o, "id"));
			pbi.setPn(getStringNodeValue(o, "pn"));
			pbi.setPp(getStringNodeValue(o, "pp"));
			pbi.setSn(getStringNodeValue(o, "sn"));
			pbi.setIo(getStringNodeValue(o, "io"));
			
			JSONArray ggl = o.getJSONArray("ggl");
			ArrayList<String> gglList = new ArrayList<>();
			for(int i = 0; i < ggl.length(); i++)
			{
				gglList.add(ggl.getString(i));
			}
			pbi.setGgl(gglList);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pbi;
	}
	
	/**
	 * 产品详情-商家信息
	 * @param root
	 * @return
	 */
	public static CbiEntity getCbiEntity(JSONObject root)
	{
		CbiEntity entity = new CbiEntity();
		try {
			JSONObject o = root.getJSONObject("cbi");
			entity.setAc(getIntNodeValue(o, "ac"));
			entity.setAp(getIntNodeValue(o, "ap"));
			entity.setCd(getStringNodeValue(o, "cd"));
			entity.setCm(getStringNodeValue(o, "cm"));
			entity.setFc(getIntNodeValue(o, "fc"));
			entity.setId(getIntNodeValue(o, "id"));
			entity.setLg(getStringNodeValue(o, "lg"));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}

	/**
	 * 进入购物车后返回的信息
	 * @param result
	 */
	public static RequestNetResultInfo getJoinShopCarResultInfo(Object result) {
		Log.d("TAG", "result - "+result);
		JSONArray array = (JSONArray) result;
		
		RequestNetResultInfo resultInfo = new RequestNetResultInfo();
		try {
			
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				
				resultInfo.setMsg(object.getString("msg"));
				resultInfo.setStatus(object.getInt("status"));
			}
			
			return resultInfo;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
