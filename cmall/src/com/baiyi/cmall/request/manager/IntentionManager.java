package com.baiyi.cmall.request.manager;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baiyi.cmall.activities.main.total.TotalUtils;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.model.Odi;

/**
 * 意向
 * 
 * @author sunxy
 */
public class IntentionManager {

	/**
	 * 意向订单
	 * 
	 * @param result
	 */
	public static GoodsSourceInfo getProductInfo(Object result) {

		GoodsSourceInfo goodsSourceInfo = new GoodsSourceInfo();
		ArrayList<GoodsSourceInfo> list = new ArrayList<GoodsSourceInfo>();
		try {
			JSONArray array = (JSONArray) result;
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);

				RequestNetResultInfo resultInfo = new RequestNetResultInfo();
				resultInfo.setMsg(jsonObject.getString("msg"));
				resultInfo.setStatus(jsonObject.getInt("status"));
				goodsSourceInfo.setResultInfo(resultInfo);

				JSONObject data = jsonObject.getJSONObject("data");
				
				//总数
				int total  = data.getInt("tt");
				TotalUtils.getIntence().setTotal(total);
				
				JSONArray jsonArray = data.getJSONArray("li");

				for (int j = 0; j < jsonArray.length(); j++) {
					JSONObject odiObject = jsonArray.getJSONObject(j);
					GoodsSourceInfo info = new GoodsSourceInfo();
					// 订单所属公司名称
					info.setCompanyName(odiObject.getString("bl"));
					info.setId(odiObject.getString("id"));
					info.setStatename(odiObject.getString("os"));
					info.setPrepayment(odiObject.getString("pr"));

					ArrayList<GoodsSourceInfo> datas = new ArrayList<GoodsSourceInfo>();
					JSONArray sArray = odiObject.getJSONArray("opil");
					for (int k = 0; k < sArray.length(); k++) {
						JSONObject object = sArray.getJSONObject(k);
						GoodsSourceInfo sourceInfo = new GoodsSourceInfo();
						sourceInfo.setId(object.getString("id"));
						sourceInfo.setInventory(object.getString("co"));
						sourceInfo.setGoodSName(object.getString("pn"));
						sourceInfo.setPrice(object.getString("pr"));
						sourceInfo.setImageurl(object.getString("purl"));
						datas.add(sourceInfo);
					}
					info.setPurInfos(datas);
					list.add(info);
				}
			}
			goodsSourceInfo.setPurInfos(list);
			return goodsSourceInfo;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return goodsSourceInfo;
	}

}
