package com.baiyi.cmall.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baiyi.cmall.entity.RequestNetResultInfo;

public class JsonParseBase {

	public static boolean getState(JSONObject jsonObject) {
		int result = getIntNodeValue(jsonObject, "state");
		return result == 1 ? true : false;
	}

	public static boolean getstatus(JSONObject jsonObject) {
		int result = getIntNodeValue(jsonObject, "status");
		return result == 1 ? true : false;
	}

	public static int getIntState(JSONObject jsonObject) {
		return getIntNodeValue(jsonObject, "state");
	}

	public static JSONArray getMsgArray(JSONObject jsonObject) {
		JSONArray msg = null;
		try {
			msg = jsonObject.getJSONArray("msg");
		} catch (JSONException e) {
		}
		return msg;
	}

	public static JSONArray getResultArray(JSONObject jsonObject) {
		JSONArray msg = null;
		try {
			msg = jsonObject.getJSONArray("results");
		} catch (JSONException e) {
		}
		return msg;
	}

	public static JSONArray getDataArray(JSONObject jsonObject) {
		JSONArray msg = null;
		try {
			msg = jsonObject.getJSONArray("data");
		} catch (JSONException e) {
		}
		return msg;
	}

	public static JSONObject getResultObj(JSONObject jsonObject) {
		JSONObject msg = null;
		try {
			msg = jsonObject.getJSONObject("data");
		} catch (JSONException e) {
		}
		return msg;
	}

	public static String getResultStr(JSONObject jsonObject) {
		return getStringNodeValue(jsonObject, "data");
	}

	public static String getDataStr(JSONArray array) {
		String data = null;
		try {
			JSONObject jsonObject = array.getJSONObject(0);
			data = jsonObject.getString("data");
		} catch (JSONException e) {
		}
		return data;
	}
	
	public static int getDataInt(JSONArray array) {
		int data = -1;
		try {
			JSONObject jsonObject = array.getJSONObject(0);
			data = jsonObject.getInt("data");
		} catch (JSONException e) {
		}
		return data;
	}

	public static String getMsg(JSONArray array) {
		String msg = null;
		try {
			JSONObject jsonObject = array.getJSONObject(0);
			msg = jsonObject.getString("msg");
		} catch (JSONException e) {
		}
		return msg;
	}
	
	public static boolean getstatus(JSONArray array) {
		int status = 0;
		try {
			JSONObject jsonObject = array.getJSONObject(0);
			status = getIntNodeValue(jsonObject, "status");
		} catch (JSONException e) {
		}
		return status == 1 ? true : false;
	}

	public static int getIntNodeValue(JSONObject o, String name) {
		boolean isHas = o.has(name) && (!o.isNull(name));
		try {
			return isHas ? o.getInt(name) : 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	public static long getLongNodeValue(JSONObject o, String name) {
		boolean isHas = o.has(name) && (!o.isNull(name));
		try {
			return isHas ? o.getLong(name) : 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static String getStringNodeValue(JSONObject o, String name) {
		boolean isHas = o.has(name) && (!o.isNull(name));
		try {
			return isHas ? o.getString(name) : "";
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	public static double getDoubleNodeValue(JSONObject o, String name) {
		boolean isHas = o.has(name) && (!o.isNull(name));
		try {
			return isHas ? o.getDouble(name) : 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static boolean getBooleanNodeValue(JSONObject o, String name) {
		boolean isHas = o.has(name) && (!o.isNull(name));
		try {
			return isHas ? o.getBoolean(name) : false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static JSONArray getArray(JSONObject o, String name) {

		boolean isHas = o.has(name) && (!o.isNull(name));
		try {
			return isHas ? o.getJSONArray(name) : new JSONArray();
		} catch (JSONException e) {
			return new JSONArray();
		}
	}

	public static boolean isHas(JSONObject o, String name) {
		boolean isHas = o.has(name) && (!o.isNull(name));
		return isHas;
	}

	public static RequestNetResultInfo getResultInfo(JSONArray dataArray) {
		RequestNetResultInfo info = new RequestNetResultInfo();

		try {
			for (int i = 0; i < dataArray.length(); i++) {
				JSONObject jsonObject = dataArray.getJSONObject(i);
				info.setMsg(jsonObject.getString("msg"));
				info.setStatus(jsonObject.getInt("status"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return info;
	}
	public static RequestNetResultInfo getResultInfo1(JSONArray dataArray) {
		RequestNetResultInfo info = new RequestNetResultInfo();
		
		try {
			for (int i = 0; i < dataArray.length(); i++) {
				JSONObject jsonObject = dataArray.getJSONObject(i);
				info.setMsg(jsonObject.getString("msg"));
				info.setStatus(jsonObject.getInt("state"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return info;
	}
}
