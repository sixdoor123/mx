package com.baiyi.jj.app.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParseBase {


    public static boolean getState(JSONObject jsonObject) {
        int result = getIntNodeValue(jsonObject, "state");
        return result == 1 ? true : false;
    }

    public static boolean getState200(JSONObject jsonObject) {
        int result = getIntNodeValue(jsonObject, "state");
        return result == 200 ? true : false;
    }

    public static boolean getState200(String jsonObject) {
        int state = -1;
        try {
            JSONObject object = new JSONObject(jsonObject);
            state = getIntNodeValue(object, "state");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return state == 200 ? true : false;
    }

    public static boolean getState200(JSONArray jsonArray) {
        int state = -1;
        try {
            JSONObject object = jsonArray.getJSONObject(0);
            state = getIntNodeValue(object, "state");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return state == 200 ? true : false;
    }

    public static int getIntState(JSONObject jsonObject) {
        return getIntNodeValue(jsonObject, "state");
    }

    public static boolean getState521(JSONArray jsonArray) {
        int state = -1;
        try {
            JSONObject object = jsonArray.getJSONObject(0);
            state = getIntNodeValue(object, "state");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return state == 521 ? true : false;
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

    public static JSONArray getResult(JSONObject jsonObject) {
        JSONArray msg = null;
        try {
            msg = jsonObject.getJSONArray("result");
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

    public static String getDataStr(JSONObject jsonObject) {
        return getStringNodeValue(jsonObject, "data");
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

    public static String getDataMsg(JSONArray array) {
        String msg = null;
        try {
            JSONObject jsonObject = array.getJSONObject(0);
            JSONObject dataObject = jsonObject.getJSONObject("data");
            msg = dataObject.getString("msg");
        } catch (JSONException e) {
        }
        return msg;
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

    public static boolean isHas(JSONObject o, String name) {
        boolean isHas = o.has(name) && (!o.isNull(name));
        return isHas;
    }
}
