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
 * ��ȡ��ҵ��̬��Ϣ
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
	 * �ڴ˽�������ȡ����
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
					// ����
					trendsEntity.setmTvTrendsTitle(dataInfo.getString("title"));
					// ����
					trendsEntity.setmTvTrendsContent(dataInfo
							.getString("content"));

					//��ȡʱ���ַ���
					String timeString = dataInfo.getString("publishtime");
					//�ڴ��ж������Ƿ�ΪNULL����Ϊ�գ���null��ת����long�;ͻᱨ��
					if ("null".equals(timeString)) {
						trendsEntity.setmTvTrendsTime(timeString);
					} else {
						long time = Long.parseLong(timeString);
						// ����ʱ��YY-MM-DD
						trendsEntity.setmTvTrendsTime(Utils.getTimeYMD(time));
					}
					trendsEntities.add(trendsEntity);
				}
			} catch (JSONException e) {
				// ����������ʱ����ӡ����Ϣ
				Log.d("TAG", "��������");
			}
		}
		return trendsEntities;

	}
}
