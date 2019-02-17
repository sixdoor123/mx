package com.baiyi.cmall.activities.user.merchant.product;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baiyi.cmall.activities.main.total.TotalUtils;
import com.baiyi.cmall.model.Blm;
import com.baiyi.cmall.model.RequestNetResultInfo;

/**
 *
 * @author sunxy
 */
public class ProductManager {

	/**
	 * 我的产品列表
	 * 
	 * @param result
	 */
	public static RequestNetResultInfo getProductList(Object result) {
		JSONArray array = (JSONArray) result;
		RequestNetResultInfo resultInfo = new RequestNetResultInfo();

		ArrayList<Blm> blms = new ArrayList<Blm>();

		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject data = array.getJSONObject(i);

				resultInfo.setMsg(data.getString("msg"));
				resultInfo.setStatus(data.getInt("status"));				
				
				JSONObject jsonObject = data.getJSONObject("data");
				
				//总数
				int total  = jsonObject.getInt("tt");
				TotalUtils.getIntence().setTotal(total);
				
				JSONArray li = jsonObject.getJSONArray("li");
				
				for (int j = 0; j < li.length(); j++) {
					JSONObject object = li.getJSONObject(j);

					Blm blm = new Blm();
					blm.setId(object.getString("id"));
					blm.setC1(object.getString("c1"));
					blm.setC2(object.getString("c2"));
					blm.setC3(object.getString("c3"));
					blm.setC4(object.getString("c4"));
					blm.setC5(object.getString("c5"));
					blm.setC6(object.getString("c6"));
					
					blms.add(blm);
				}
				resultInfo.setDatas(blms);
			}
			
			return resultInfo;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}

}
