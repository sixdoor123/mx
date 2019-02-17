package com.baiyi.cmall.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.baiyi.cmall.entity.NewsEntity;

/**
 * 消息工具
 * 
 * @author lizl
 * 
 */
public class NewsUtil {

	private Context context;
	
	private static NewsUtil newsUtil;

	private NewsUtil(Context context) {
		this.context = context;
	}

	public static NewsUtil getInstance(Context context) {

		if (null == newsUtil) {
			newsUtil = new NewsUtil(context);
		}
		return newsUtil;
	}

	/**
	 * 获取消息数据
	 * @param data 传过来的JSON数据
	 * 
	 * @return
	 */
	public static ArrayList<NewsEntity> getNewsInfo(String data) {

		ArrayList<NewsEntity> newsEntities = new ArrayList<NewsEntity>();
		
		JSONObject object = null;
		
		try {
			object = new JSONObject(data);
			JSONArray dataList = object.getJSONArray("my_news");
			for (int i = 0; i < dataList.length(); i++) {
				
				JSONObject dataInfo = dataList.getJSONObject(i);
				NewsEntity newsEntity = new NewsEntity();
				newsEntity.setNewsState(dataInfo.getInt("state"));
				newsEntity.setNewsSource(dataInfo.getString("name"));
				newsEntity.setNewsContent(dataInfo.getString("content"));
				newsEntity.setNewstime(dataInfo.getString("time"));
				newsEntities.add(newsEntity);
				
			}
			
		} catch (JSONException e) {
			//当解析错误时，打印此消息
			Log.d("TAG", "解析错误");
		}

		return newsEntities;

	}
}
