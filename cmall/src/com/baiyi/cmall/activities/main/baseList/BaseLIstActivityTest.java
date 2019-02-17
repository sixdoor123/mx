package com.baiyi.cmall.activities.main.baseList;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.baiyi.cmall.Config;

/**
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-1-21 ÏÂÎç2:19:37
 */
public class BaseLIstActivityTest extends BaseListActivity {

	@Override
	public String getTitleName() {
		// TODO Auto-generated method stub
		return "²âÊÔ BaseLIstActivity";
	}

	@Override
	protected BaseListAdapter getListAdapter() {

		return new MyAdapterText(this);
	}

	@Override
	protected String getRequestUrl() {
		String url = Config.ROOT_URL+"Company/Follow/Purchase/%d/%d/%d";
		url = String.format(url,35,
				pageIndex, Config.LIST_ITEM_COUNT);
		Log.d("TAG", "Â·¾¶--"+url);
		return url;
	}

	@Override
	protected String getRequstPostData() {
		// TODO Auto-generated method stub
		return new JSONObject().toString();
	}

	@Override
	protected String getToken() {
		// TODO Auto-generated method stub
		return "792c7bc5348b423083a5ce74e9b07de2";
	}

	@Override
	protected ArrayList<Serializable> getParseData(Object result) {
		Log.d("TAG","--getParseData--"+result.toString());
		ArrayList<Serializable> datasList = new ArrayList<Serializable>();
		
		JSONArray array = (JSONArray) result;
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonArray = array.getJSONObject(i);
				JSONObject dataJsonObject = jsonArray.getJSONObject("data");
				JSONArray array2 = dataJsonObject.getJSONArray("dataList");
				for (int j = 0; j < array2.length(); j++) {
					JSONObject object = array2.getJSONObject(j);

					MyModelTest test = new MyModelTest();
					test.setId(object.getString("id"));
					test.setName(object.getString("purchasename"));

					datasList.add(test);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return datasList;
	}

}
