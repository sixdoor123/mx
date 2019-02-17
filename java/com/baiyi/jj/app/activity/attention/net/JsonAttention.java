package com.baiyi.jj.app.activity.attention.net;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baiyi.jj.app.activity.attention.AttentionWordsEntity;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.utils.JsonParseBase;
import java.util.ArrayList;
import java.util.List;

public class JsonAttention extends JsonParseBase {

	public static List<AttentionWordsEntity> getNoFollowWords(String obj) {


		List<AttentionWordsEntity> arrayList = new ArrayList();
		try {
			JSONObject object = new JSONObject(obj);
			JSONArray array = getDataArray(object);
			if (!getState200(object)){
				return  null;
			}
			for (int i = 0;i<array.length();i++){
				AttentionWordsEntity entity = new AttentionWordsEntity();
				JSONObject wordObject = array.getJSONObject(i);

				entity.setWords(getStringNodeValue(wordObject,"words"));
				entity.setChannel_id(getStringNodeValue(wordObject,"channel_id"));
				entity.setChannel_name(getStringNodeValue(wordObject,"channel_name"));
				entity.setAlias(getStringNodeValue(wordObject,"alias"));
				entity.setIcon(getStringNodeValue(wordObject,"icon"));
				entity.setCreated_at(getStringNodeValue(wordObject,"created_at"));
				entity.setHotword_id(getIntNodeValue(wordObject,"id"));
				if (CmsApplication.getUserInfoEntity() != null){
					entity.setMid(CmsApplication.getUserInfoEntity().getMID());
				}
				arrayList.add(entity);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return arrayList;
	}

	public static ArrayList<AttentionWordsEntity> getFollowWords(String obj) {


		ArrayList<AttentionWordsEntity> arrayList = new ArrayList();
		try {
			JSONObject object = new JSONObject(obj);
			JSONArray array = getDataArray(object);
			if (!getState200(object)){
				return  null;
			}
			for (int i = 0;i<array.length();i++){
				AttentionWordsEntity entity = new AttentionWordsEntity();
				JSONObject wordObject = array.getJSONObject(i);

				entity.setWords(getStringNodeValue(wordObject,"words"));
				entity.setChannel_id(getStringNodeValue(wordObject,"channel_id"));
				entity.setChannel_name(getStringNodeValue(wordObject,"channel_name"));
				entity.setAlias(getStringNodeValue(wordObject,"hotword_alias"));
				entity.setIcon(getStringNodeValue(wordObject,"icon"));
				entity.setCreated_at(getStringNodeValue(wordObject,"created_at"));
				entity.setFollow_id(getIntNodeValue(wordObject,"follow_id"));
				entity.setHotword_id(getIntNodeValue(wordObject,"hotword_id"));
				if (CmsApplication.getUserInfoEntity() != null){
					entity.setMid(CmsApplication.getUserInfoEntity().getMID());
				}
				entity.setAttet(true);
				arrayList.add(entity);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return arrayList;
	}

	public static ArrayList<ChannelItem> getTagChannels(String obj) {


		ArrayList<ChannelItem> arrayList = new ArrayList();
		try {
			JSONObject object = new JSONObject(obj);
			JSONArray array = getDataArray(object);
			if (!getState200(object)){
				return  null;
			}
			for (int i = 0;i<array.length();i++){
				ChannelItem entity = new ChannelItem();
				JSONObject wordObject = array.getJSONObject(i);
				entity.setCid(getStringNodeValue(wordObject,"channel_id"));
				entity.setChannel_name(getStringNodeValue(wordObject,"channel_name"));
				entity.setMid("alltag");
				arrayList.add(entity);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return arrayList;
	}
}
