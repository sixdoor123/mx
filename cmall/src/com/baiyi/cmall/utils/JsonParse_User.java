package com.baiyi.cmall.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.MessageBaseEntity;
import com.baiyi.cmall.entity.MessageEntity;
import com.baiyi.cmall.entity.UserInfoEntity;
import com.baiyi.core.loader.ImageLoader;
import com.baiyi.core.loader.Loader.LoaderListener;

public class JsonParse_User extends JsonParseBase {

	/**
	 * 我的消息
	 * 
	 * @param context
	 * @param array
	 * @param newsName
	 * @return
	 */
	public static MessageBaseEntity getNewsList(Context context,
			JSONArray array, String newsName) {
		MessageBaseEntity data = new MessageBaseEntity();
		JSONObject root;
		try {
			root = array.getJSONObject(0);
			if (!getState(root)) {
				return null;
			}

			JSONObject jo = getResultObj(root);
			// CmsApplication.setNews(context,
			// JsonParse.getIntNodeValue(jo, "MultiMaxID"),
			// JsonParse.getIntNodeValue(jo, "UserUnReadCount"));
			data.setRecordCount(getIntNodeValue(jo, "count"));
			data.setNewsName(newsName);
			JSONArray ja = getResultArray(jo);
			List<MessageEntity> dataList = new ArrayList<MessageEntity>();
			for (int i = 0; i < ja.length(); i++) {
				JSONObject o = ja.getJSONObject(i);
				MessageEntity entity = new MessageEntity();
				entity.setMsgContent(getStringNodeValue(o, "msg_content"));
				entity.setMsgId(getStringNodeValue(o, "id"));
				entity.setMsgCreateDate(getStringNodeValue(o, "msg_create_date"));
				entity.setMsgState(getStringNodeValue(o, "msg_state"));
				entity.setMsgSendId(getStringNodeValue(o, "msg_send_id"));
				entity.setMsgTitle(getStringNodeValue(o, "msg_title"));
				entity.setMsgType(getStringNodeValue(o, "msg_type"));
				entity.setMsgUpdateDate(getStringNodeValue(o, "msg_update_date"));
				entity.setMsgUserId(getStringNodeValue(o, "msg_user"));
				dataList.add(entity);
			}
			data.setDataList(dataList);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	public static UserInfoEntity getToken(Object result) {
		JSONArray array = (JSONArray) result;
		UserInfoEntity entity = new UserInfoEntity();
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONObject object = jsonObject.getJSONObject("data");

				entity.setAccount(object.getString("loginName"));
				entity.setCompanyID(object.getString("companyID"));
				entity.setEM(object.getString("email"));
				entity.setMO(object.getString("mobile"));
				entity.setUserID(object.getString("userID"));
				entity.setMN(object.getString("userName"));
				entity.setId(object.getString("id"));
				entity.setToken(object.getString("token"));
			}

			return entity;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	
}
