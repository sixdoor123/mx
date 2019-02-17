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
 * 获取分类数据源工具类
 * 
 * @author lizl
 * 
 */
public class MarketPriceSourceManager {

	/**
	 * 得到具体的数据源集合
	 * 
	 * @param data
	 * 
	 * @return
	 */
	public static MainEntity getMainActivityInfo(JSONArray data) {

		MainEntity mainEntitys = new MainEntity();
		// 新闻
		ArrayList<MainEntity> newsInfoList = new ArrayList<MainEntity>();
		// 广告
		ArrayList<ZiXunEntity> zixunInfoList = new ArrayList<ZiXunEntity>();
		// 限时抢购
		ArrayList<GoodsSourceInfo> flashCaleList = new ArrayList<GoodsSourceInfo>();
		// 热门品牌
		ArrayList<MainEntity> hotBusinessList = new ArrayList<MainEntity>();

		for (int k = 0; k < data.length(); k++) {

			try {
				// 第一个JSON对象
				JSONObject jsonObject = data.getJSONObject(k);
				JSONObject dataOBJ = jsonObject.getJSONObject("data");

				/*
				 * 新闻信息------------------------------------------
				 */
				JSONArray newsList = dataOBJ.getJSONArray("biml");
				for (int i = 0; i < newsList.length(); i++) {
					JSONObject newsInfo = newsList.getJSONObject(i);

					MainEntity newsEntity = new MainEntity();
					// 设置图片的点击链接
					newsEntity.setUrl(newsInfo.getString("url"));
					// 设置加载图片的路径
					newsEntity.setPicPath(newsInfo.getString("fp"));

					newsInfoList.add(newsEntity);
				}
				mainEntitys.setNewsInfoList(newsInfoList);

				/*
				 * 资讯信息-------------------------------------------
				 */
				JSONObject zixunOBJ = dataOBJ.getJSONObject("nsil");
				JSONArray zixuns = zixunOBJ.getJSONArray("li");
				for (int i = 0; i < zixuns.length(); i++) {
					JSONObject zixunInfo = zixuns.getJSONObject(i);

					ZiXunEntity zixunEntity = new ZiXunEntity();
					// 设置资讯标题
					zixunEntity.setId(zixunInfo.getString("id"));
					zixunEntity.setTitle(zixunInfo.getString("tt"));

					zixunInfoList.add(zixunEntity);
				}
				mainEntitys.setZixunInfoList(zixunInfoList);

				/*
				 * 限时抢购-------------------------------------------
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
					flashCaleEntity.setPrice("最低价：￥" + object.getString("c5") + "元");

					flashCaleList.add(flashCaleEntity);

				}
				mainEntitys.setFlashCaleList(flashCaleList);

				/*
				 * 热门品牌-------------------------------------------
				 */
				JSONObject hotBusinessOBJ = dataOBJ.getJSONObject("bdil");
				JSONArray hotBusiness = hotBusinessOBJ.getJSONArray("li");
				for (int i = 0; i < hotBusiness.length(); i++) {
					JSONObject hotOBJ = hotBusiness.getJSONObject(i);

					MainEntity hotEntity = new MainEntity();
					
					// 设置ID
					hotEntity.setId(hotOBJ.getString("id"));
					// 设置图片路徑
					hotEntity.setPicPath(hotOBJ.getString("url"));
					// 设置标题
					hotEntity.setTitle(hotOBJ.getString("nm"));

					hotBusinessList.add(hotEntity);
				}
				mainEntitys.setHotBusinessList(hotBusinessList);

			} catch (JSONException e) {
				// 当解析错误时，打印此消息
				Log.d("TAG", "解析错误");
			}
		}

		return mainEntitys;
	}
}
