package com.baiyi.cmall.request.manager;

import org.json.JSONObject;

public class NullJsonData {

	/**
	 * 获取POST请求数据
	 * 
	 * @return
	 */
	public static String getPostData() {
		JSONObject json = new JSONObject();
		return json.toString();
	}

}
