package com.baiyi.cmall.request.manager;

import org.json.JSONObject;

public class NullJsonData {

	/**
	 * ��ȡPOST��������
	 * 
	 * @return
	 */
	public static String getPostData() {
		JSONObject json = new JSONObject();
		return json.toString();
	}

}
