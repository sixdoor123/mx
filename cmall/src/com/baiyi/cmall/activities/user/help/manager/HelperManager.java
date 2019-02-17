package com.baiyi.cmall.activities.user.help.manager;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.utils.TextViewUtil;

/**
*
*@author sunxy
*/
public class HelperManager {

	public static String getFeedBackContent(String userID, String content, String number) {
		StringBuilder builder = new StringBuilder();

		if (!TextViewUtil.isStringEmpty(userID)) {
			builder.append("userid").append("=").append(userID).append("&");
		}
		if (!TextViewUtil.isStringEmpty(number)) {
			builder.append("mobile").append("=").append(number).append("&");
		}
		builder.append("suggestiondetail").append("=").append(content);

//		JSONObject object = new JSONObject();
//		try {
//			if (!TextViewUtil.isStringEmpty(userID)) {
//				object.put("userid", userID);
//			}
//			if (!TextViewUtil.isStringEmpty(number)) {
//				object.put("mobile", number);
//			}
//			object.put("suggestiondetail", content);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
		return builder.toString();
	}
	/**
	 * 返回的结果信息
	 * 
	 * @param arg1
	 * @param state
	 * @return
	 */
	public static RequestNetResultInfo getResultInfo(Object arg1) {
		JSONArray array = (JSONArray) arg1;
		RequestNetResultInfo resultInfo = new RequestNetResultInfo();
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);

				resultInfo.setMsg(jsonObject.getString("msg"));
				resultInfo.setStatus(jsonObject.getInt("status"));
			}

			return resultInfo;

		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}
}
