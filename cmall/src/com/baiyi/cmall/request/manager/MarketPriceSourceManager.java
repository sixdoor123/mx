package com.baiyi.cmall.request.manager;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;

import com.baiyi.cmall.activities.main.home_pager.entity.MainEntity;
import com.baiyi.cmall.activities.main.home_pager.entity.ZiXunEntity;
import com.baiyi.cmall.entity.GoodsSourceInfo;

/**
 * ��ȡ��������Դ������
 * 
 * @author lizl
 * 
 */
public class MarketPriceSourceManager {

	/**
	 * �õ����������Դ����
	 * 
	 * @param data
	 * 
	 * @return
	 */
	public static MainEntity getMainActivityInfo(JSONArray data) {

		MainEntity mainEntitys = new MainEntity();
		// ����
		ArrayList<MainEntity> newsInfoList = new ArrayList<MainEntity>();
		// ���
		ArrayList<ZiXunEntity> zixunInfoList = new ArrayList<ZiXunEntity>();
		// ��ʱ����
		ArrayList<GoodsSourceInfo> flashCaleList = new ArrayList<GoodsSourceInfo>();
		// ����Ʒ��
		ArrayList<MainEntity> hotBusinessList = new ArrayList<MainEntity>();

		for (int k = 0; k < data.length(); k++) {

			try {
				// ��һ��JSON����
				JSONObject jsonObject = data.getJSONObject(k);
				JSONObject dataOBJ = jsonObject.getJSONObject("data");

				/*
				 * ������Ϣ------------------------------------------
				 */
				JSONArray newsList = dataOBJ.getJSONArray("biml");
				for (int i = 0; i < newsList.length(); i++) {
					JSONObject newsInfo = newsList.getJSONObject(i);

					MainEntity newsEntity = new MainEntity();
					// ����ͼƬ�ĵ������
					newsEntity.setUrl(newsInfo.getString("url"));
					// ���ü���ͼƬ��·��
					newsEntity.setPicPath(newsInfo.getString("fp"));

					newsInfoList.add(newsEntity);
				}
				mainEntitys.setNewsInfoList(newsInfoList);

				/*
				 * ��Ѷ��Ϣ-------------------------------------------
				 */
				JSONObject zixunOBJ = dataOBJ.getJSONObject("nsil");
				JSONArray zixuns = zixunOBJ.getJSONArray("li");
				for (int i = 0; i < zixuns.length(); i++) {
					JSONObject zixunInfo = zixuns.getJSONObject(i);

					ZiXunEntity zixunEntity = new ZiXunEntity();
					// ������Ѷ����
					zixunEntity.setId(zixunInfo.getString("id"));
					zixunEntity.setTitle(zixunInfo.getString("tt"));

					zixunInfoList.add(zixunEntity);
				}
				mainEntitys.setZixunInfoList(zixunInfoList);

				/*
				 * ��ʱ����-------------------------------------------
				 */
				JSONObject flashCaleOBJ = dataOBJ.getJSONObject("blml");
				JSONArray flashCales = flashCaleOBJ.getJSONArray("li");
				for (int i = 0; i < flashCales.length(); i++) {

					JSONObject object = flashCales.getJSONObject(i);

					GoodsSourceInfo flashCaleEntity = new GoodsSourceInfo();

					flashCaleEntity.setId(object.getString("id"));
					flashCaleEntity.setTitle(object.getString("c1"));
					flashCaleEntity.setGoodSContent(object.getString("c2"));
					flashCaleEntity.setCompanyName(object.getString("c3"));
					flashCaleEntity.setCity(object.getString("c4"));
					flashCaleEntity.setPrice("��ͼۣ���" + object.getString("c5") + "Ԫ");

					flashCaleList.add(flashCaleEntity);

				}
				mainEntitys.setFlashCaleList(flashCaleList);

				/*
				 * ����Ʒ��-------------------------------------------
				 */
				JSONObject hotBusinessOBJ = dataOBJ.getJSONObject("bdil");
				JSONArray hotBusiness = hotBusinessOBJ.getJSONArray("li");
				for (int i = 0; i < hotBusiness.length(); i++) {
					JSONObject hotOBJ = hotBusiness.getJSONObject(i);

					MainEntity hotEntity = new MainEntity();
					
					// ����ID
					hotEntity.setId(hotOBJ.getString("id"));
					// ����ͼƬ·��
					hotEntity.setPicPath(hotOBJ.getString("url"));
					// ���ñ���
					hotEntity.setTitle(hotOBJ.getString("nm"));

					hotBusinessList.add(hotEntity);
				}
				mainEntitys.setHotBusinessList(hotBusinessList);

			} catch (JSONException e) {
				// ����������ʱ����ӡ����Ϣ
				Log.d("TAG", "��������");
			}
		}

		return mainEntitys;
	}
}
