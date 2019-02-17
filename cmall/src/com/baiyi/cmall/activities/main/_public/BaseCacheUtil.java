package com.baiyi.cmall.activities.main._public;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;

import com.baiyi.cmall.application.BaseDataApplication;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.request.manager.PublicActivityManager;
import com.baiyi.core.cache.Cache;

/**
 * 基础数据选择
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-1-29 下午4:18:31
 */
public class BaseCacheUtil {

	public static GoodsSourceInfo getPublicDataInfo(Context context) {

		Cache cache = CmallApplication.getBaseDataCache(context);
		if (cache == null) {
			return null;
		}
		if (!cache.isExist(CmallApplication.BaseDataFileName)) {
			return null;
		}
		byte[] data = (byte[]) cache.get(CmallApplication.BaseDataFileName);
		try {
			if (data == null) {
				return null;
			}
			GoodsSourceInfo info = PublicActivityManager.getPublicData(context, new JSONArray(new String(data)));
			BaseDataApplication.setPublicData(info);

			return info;

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}
}
