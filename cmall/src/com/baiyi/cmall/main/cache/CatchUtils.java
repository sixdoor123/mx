package com.baiyi.cmall.main.cache;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baiyi.core.cache.Cache;
import com.baiyi.core.loader.cache.SimpleCacheLoader;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.LoginInfo;
import com.baiyi.cmall.request.manager.LoginManager;
import com.baiyi.cmall.utils.XmlName;
import com.baiyi.cmall.utils.XmlUtils;

public class CatchUtils {

	/**
	 * 保存用户ＩＤ 和 供应商ＩＤ
	 * 
	 * @param info
	 */
	public static void saveXml(Context context, LoginInfo info) {

		XmlUtils utils = XmlUtils.getInstence(context);
		utils.savaXmlValue(XmlName.USER_ID, info.getUserID());
		utils.savaXmlValue(XmlName.COMPANY_ID, info.getCompanyID());
		utils.savaXmlValue(XmlName.USER_NAME, info.getUserName());
		utils.savaXmlValue(XmlName.TOKEN, info.getToken());
		utils.savaXmlValue(XmlName.Company_Name, info.getCompanyName());
		utils.savaXmlValue(XmlName.Mobile, info.getMobile());
		utils.savaXmlValue(XmlName.Head_Portrait, info.getHeadPortrait());
		utils.savaXmlValue(XmlName.NAME_user_account, info.getLoginName());
		utils.savaXmlValue(XmlName.NAME_user_mm, info.getPwd());
		utils.savaXmlValue(XmlName.Is_Company, info.isIscompany());
		utils.savaXmlValue(XmlName.Base_Address, info.getBaseAddress());

		Log.d("TAG", "info-----" + info.toString());
	}

	public static void saveXml(Context context, String pic) {
		XmlUtils utils = XmlUtils.getInstence(context);
		utils.deleteXmlValue(XmlName.Head_Portrait);
		utils.savaXmlValue(XmlName.Head_Portrait, pic);
		Log.d("TAG", "info-----" + pic);
	}

	public static void cacheLogin(Context context, byte[] data) {
		SimpleCacheLoader cache = new SimpleCacheLoader(
				CmallApplication.getTestJsonCache(context));
		cache.setUpdate(CmallApplication.UserFileName, data);
		CmallApplication.getDataStratey().startLoader(cache);
	}

	public static void deleteCacheLogin(Context context) {
		SimpleCacheLoader cache = new SimpleCacheLoader(
				CmallApplication.getTestJsonCache(context));
		cache.setDelete(CmallApplication.UserFileName);
		CmallApplication.getDataStratey().startLoader(cache);
	}

	public static void cacheBaseDate(Context context, byte[] data) {
		SimpleCacheLoader cache = new SimpleCacheLoader(
				CmallApplication.getBaseDataCache(context));
		cache.setUpdate(CmallApplication.BaseDataFileName, data);
		CmallApplication.getDataStratey().startLoader(cache);
	}

	public static void updtateCacheLogin(Context context, String pic) {
		Cache cache = CmallApplication.getTestJsonCache(context);
		if (cache == null) {
			return;
		}
		if (!cache.isExist(CmallApplication.UserFileName)) {
			return;
		}
		byte[] data = (byte[]) cache.get(CmallApplication.UserFileName);
		try {
			
			JSONArray array = new JSONArray(new String(
					data == null ? new byte[0] : data));

			CatchUtils.cacheLogin(context, getJSONArray(array, pic));
			LoginInfo info = LoginManager.getLoginResultInfo(new JSONArray(
					new String(data == null ? new byte[0] : data)));
			CmallApplication.setUserInfo(info);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static byte[] getJSONArray(JSONArray array, String pic) {

		JSONArray jsonArray = new JSONArray();
		JSONObject object = new JSONObject();

		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONObject o = jsonObject.getJSONObject("data");
				o.remove("headPortrait");
				o.put("headPortrait", pic);
				object.put("data", o);
			}
			jsonArray.put(object);

			return jsonArray.toString().getBytes();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}

}
