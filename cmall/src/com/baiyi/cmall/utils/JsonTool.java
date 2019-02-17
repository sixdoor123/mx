package com.baiyi.cmall.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * JSON自动转化工具
 * 
 * @author sunxy
 * @version 创建时间：2016年4月29日 上午11:29:16
 *
 */
public class JsonTool {
	/**
	 * 将指定的 {@link java.util.HashMap}<String, Object>对象转成json数据
	 */
	public static String fromHashMap(HashMap<String, Object> map) {
		try {
			return getJSONObject(map).toString();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return "";
	}

	/**
	 * 根据指定的map集合得到JSONObject
	 * @param map
	 * @return
	 * @throws JSONException
	 */
	private static JSONObject getJSONObject(HashMap<String, Object> map) throws JSONException {
		JSONObject json = new JSONObject();
		for (Entry<String, Object> entry : map.entrySet()) {
			Object value = entry.getValue();
			if (value instanceof HashMap<?, ?>) {
				value = getJSONObject((HashMap<String, Object>) value);
			} else if (value instanceof ArrayList<?>) {
				value = getJSONArray((ArrayList<Object>) value);
			}
			json.put(entry.getKey(), value);
		}
		return json;
	}
	
	/**
	 * 根据指定的List集合得到JSONArray
	 * @param list
	 * @return
	 * @throws JSONException
	 */
	@SuppressWarnings("unchecked")
	private static JSONArray getJSONArray(ArrayList<Object> list) throws JSONException {
		JSONArray array = new JSONArray();
		for (Object value : list) {
			if (value instanceof HashMap<?, ?>) {
				value = getJSONObject((HashMap<String, Object>) value);
			} else if (value instanceof ArrayList<?>) {
				value = getJSONArray((ArrayList<Object>) value);
			}
			array.put(value);
		}
		return array;
	}

	/**
	 * 从指定的JSONObject得到Map集合
	 * @param object
	 * @return
	 */
	public static HashMap<String, String> getMapFromJsonObject(JSONObject object) {
		HashMap<String, String> map = new HashMap<String, String>();
		@SuppressWarnings("unchecked")
		Iterator<String> iterator = object.keys();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			try {
				String value = object.getString(key);
				if ("null".equals(value)) {
					value = "";
				}
				map.put(key, value);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return map;
	}

	/**
	 * 解析JSON
	 * @param json
	 * @return
	 */
	public static HashMap<String, Object> parseJson(String json) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			if (json != null) {
				JSONObject object = new JSONObject(json);
				Log.i("SQ", object.toString());
				Iterator<String> iterator = object.keys();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					Object obj = object.get(key);
					if (obj instanceof String) {
						String value = obj.toString();
						if ("null".equals(value)) {
							value = "";
						}
						map.put(key, value);
					} else if (obj instanceof JSONObject) {
						HashMap<String, Object> map2 = parseJson(obj.toString());
						if (map2 != null) {
							map.put(key, map2);
						}
					} else if (obj instanceof Integer) {
						int value = Integer.parseInt(obj.toString());
						map.put(key, value);
					} else if (obj instanceof Long) {
						long value = Long.parseLong(obj.toString());
						map.put(key, value);
					} else if (obj instanceof Float) {
						float value = Float.parseFloat(obj.toString());
						map.put(key, value);
					} else if (obj instanceof Boolean) {
						boolean value = (Boolean) obj;
						map.put(key, value);
					} else if (obj instanceof JSONArray) {
						ArrayList<HashMap<String, Object>> mapList = getArrayList((JSONArray) obj);
						map.put(key, mapList);
					} else {
						map.put(key, "");
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	public static ArrayList<HashMap<String, Object>> getArrayList(JSONArray jsonArray) throws JSONException {
		ArrayList<HashMap<String, Object>> mapList = new ArrayList<HashMap<String, Object>>();
		int m = jsonArray.length();
		if (m > 0) {
			for (int j = 0; j < m; j++) {
				Object object2 = jsonArray.get(j);
				if (object2 instanceof JSONObject) {
					JSONObject o = jsonArray.getJSONObject(j);
					mapList.add(parseJson(o.toString()));
				} else if (object2 instanceof String) {
					HashMap<String, Object> map2 = new HashMap<String, Object>();
					map2.put("picURL", object2.toString());
					mapList.add(map2);
				}
			}
		}
		return mapList;
	}

	/**
	 * 解析仅有字符串的json
	 * @param json
	 * @return
	 */
	public static HashMap<String, String> parseJsonOnlyString(String json) {
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			JSONObject object = new JSONObject(json);
			@SuppressWarnings("unchecked")
			Iterator<String> iterator = object.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				String value = object.getString(key);
				map.put(key, value);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

}
