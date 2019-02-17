package com.baiyi.cmall.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.baiyi.cmall.entity.IndustryTrendsEntity;
import com.baiyi.cmall.test.AssetsUtil;

/**
 * 获取行业动态信息
 * 
 * @author lizl
 * 
 */
public class IndustryTrendsUtil {

	private static IndustryTrendsUtil industryTrendsUtil;

	private IndustryTrendsUtil() {
	}

	public static IndustryTrendsUtil getInstance() {
		if (null == industryTrendsUtil) {
			industryTrendsUtil = new IndustryTrendsUtil();
		}
		return industryTrendsUtil;
	}

	/**
	 * 在此解析，获取数据
	 * 
	 * @return
	 */
	public static ArrayList<IndustryTrendsEntity> getTrendsInfo(JSONArray data) {

		ArrayList<IndustryTrendsEntity> trendsEntities = new ArrayList<IndustryTrendsEntity>();
		for (int k = 0; k < data.length(); k++) {

			try {
				JSONObject objectData = data.getJSONObject(k);
				JSONArray dataList = objectData.getJSONArray("data");
				for (int i = 0; i < dataList.length(); i++) {

					JSONObject dataInfo = dataList.getJSONObject(i);
					IndustryTrendsEntity trendsEntity = new IndustryTrendsEntity();
					// 标题
					trendsEntity.setmTvTrendsTitle(dataInfo.getString("title"));
					// 内容
					trendsEntity.setmTvTrendsContent(dataInfo
							.getString("content"));

					//获取时间字符串
					String timeString = dataInfo.getString("publishtime");
					//在此判断数据是否为NULL，若为空，“null”转化成long型就会报错
					if ("null".equals(timeString)) {
						trendsEntity.setmTvTrendsTime(timeString);
					} else {
						long time = Long.parseLong(timeString);
						// 发布时间YY-MM-DD
						trendsEntity.setmTvTrendsTime(Utils.getTimeYMD(time));
					}
					trendsEntities.add(trendsEntity);
				}
			} catch (JSONException e) {
				// 当解析错误时，打印此消息
				Log.d("TAG", "解析错误");
			}
		}
		return trendsEntities;

	}
}
