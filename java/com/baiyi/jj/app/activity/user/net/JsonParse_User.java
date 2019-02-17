package com.baiyi.jj.app.activity.user.net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.baiyi.jj.app.activity.user.entity.IntegalEntity;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.utils.JsonParseBase;
import com.baiyi.jj.app.utils.TLog;

public class JsonParse_User extends JsonParseBase {

	/**
	 * ??????
	 *
	 * @param array
	 * @return
	 */
	public static UserInfoEntity getUserInfoEntity(JSONArray array,int usertype) {
		UserInfoEntity info = new UserInfoEntity();
		try {
			JSONObject root = array.getJSONObject(0);
			if (!getState200(root)) {
				return null;
			}
			JSONArray obj = getDataArray(root);
			JSONObject o = obj.getJSONObject(0);

			info.setAvatar(getStringNodeValue(o, "avatar"));
			info.setCountry(getStringNodeValue(o, "country"));
			info.setLanguage(getStringNodeValue(o, "language"));
			info.setDisplay(getStringNodeValue(o, "display"));
			info.setJob(getStringNodeValue(o, "job"));
			info.setEmail(getStringNodeValue(o, "email"));
			info.setFirst_name(getStringNodeValue(o, "first_name"));
			info.setLast_name(getStringNodeValue(o, "last_name"));
			info.setName(getStringNodeValue(o,"name"));
			info.setLocation(getStringNodeValue(o, "location"));
			info.setMobile(getStringNodeValue(o, "mobile"));
			info.setPaypal(getStringNodeValue(o, "paypal"));
			info.setIntegral(getIntNodeValue(o, "integral"));
			info.setUserType(usertype);
			info.setMID(String.valueOf(getIntNodeValue(o, "userid")));
		} catch (JSONException e) {
		}
		return info;
	}

	public static UserInfoEntity getToken(JSONArray array) {
		UserInfoEntity info = new UserInfoEntity();
		try {
			JSONObject root = array.getJSONObject(0);
			if (root.isNull("access_token")) {
				return null;
			}
			info.setToken(root.getString("access_token"));
			info.setRefreshToken(root.getString("refresh_token"));
			info.setTokenType(root.getString("token_type"));
		} catch (JSONException e) {
		}
		return info;
	}

	public static UserInfoEntity getGustToken(Object result) {

		UserInfoEntity info = new UserInfoEntity();
		try {
			JSONArray array = (JSONArray) result;
			TLog.e("loadInitGust","onCompelete-----"+array.toString());
			JSONObject root = array.getJSONObject(0);
			if (root.isNull("token")) {
				return null;
			}
			info.setToken(root.getString("token"));
			info.setAccount(root.getString("username"));
			info.setMID(root.getString("id"));
		} catch (JSONException e) {
			return null;
		}
		return info;
	}

	public static IntegalEntity getIntegalEntity(String obj) {

		IntegalEntity entity = new IntegalEntity();
		try {
			JSONObject object1 = new JSONObject(obj);
			if (!getState200(object1)){
				return  null;
			}
			JSONObject object = getResultObj(object1);
			entity.setCurrency(getStringNodeValue(object,"currency"));
			entity.setExchange(getDoubleNodeValue(object,"exchange"));
			entity.setMaxDayPoint(getIntNodeValue(object,"max_day_point"));
			entity.setMaxMoney(getIntNodeValue(object,"max_money"));
			entity.setMinMoney(getIntNodeValue(object,"min_money"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}
}
