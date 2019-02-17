package com.baiyi.cmall.request.manager;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baiyi.cmall.activities.main.total.TotalUtils;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.model.Blm;

import android.util.Log;

/**
 * 关注的管理类
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-19 上午10:38:42
 */
public class AttentionManager {

	/**
	 * 关注供应 post数据
	 * 
	 * @return
	 */
	public static String getProviderPostData() {
		JSONObject object = new JSONObject();
		return object.toString();
	}

	/**
	 * 关注采购
	 * 
	 * @param arg1
	 * @return
	 */
	public static GoodsSourceInfo getAttentionPurchaseResultInfo(Object arg1) {
		ArrayList<GoodsSourceInfo> datas = new ArrayList<GoodsSourceInfo>();
		JSONArray array = (JSONArray) arg1;
		GoodsSourceInfo sourceInfo = new GoodsSourceInfo();
		Log.d("TAG", "--getAttentionPurchaseResultInfo--" + arg1.toString());
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);

				RequestNetResultInfo resultInfo = new RequestNetResultInfo();
				resultInfo.setMsg(jsonObject.getString("msg"));
				resultInfo.setStatus(jsonObject.getInt("status"));
				sourceInfo.setResultInfo(resultInfo);

				JSONObject jsonArray = jsonObject.getJSONObject("data");

				// 总数
				int total = jsonArray.getInt("total");
				TotalUtils.getIntence().setTotal(total);

				JSONArray dataList = jsonArray.getJSONArray("dataList");
				for (int j = 0; j < dataList.length(); j++) {
					JSONObject object = dataList.getJSONObject(j);

					GoodsSourceInfo info = new GoodsSourceInfo();
					info.setGoodSID(object.getString("id"));
					info.setIntentionOrderState(object.getString("puchasestatename"));
					info.setPublicstate(object.getInt("publicstate"));
					info.setPublicstatename(object.getString("publicstatename"));
					info.setGoodSWeight(object.getString("amount"));
					info.setGoodSName(object.getString("purchasename"));
					info.setGoodSCategory(object.getString("categoryname"));
					info.setPuchasestatename(object.getString("puchasestatename"));
					info.setGoodSPrePrice(object.getString("price"));
					info.setGoodSBrand(object.getString("brandname"));
					info.setPurchaseid(object.getString("purid"));

					datas.add(info);
				}
			}
			sourceInfo.setPurInfos(datas);
			return sourceInfo;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 关注供应
	 * 
	 * @param arg1
	 * @return
	 */
	public static GoodsSourceInfo getAttentionProviderResultInfo(Object arg1) {
		JSONArray array = (JSONArray) arg1;
		GoodsSourceInfo sourceInfo = new GoodsSourceInfo();
		ArrayList<GoodsSourceInfo> datas = new ArrayList<GoodsSourceInfo>();
		Log.d("TAG", "--getAttentionProviderResultInfo--" + arg1.toString());
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONObject jsonArray = jsonObject.getJSONObject("data");

				RequestNetResultInfo resultInfo = new RequestNetResultInfo();
				resultInfo.setMsg(jsonObject.getString("msg"));
				resultInfo.setStatus(jsonObject.getInt("status"));
				sourceInfo.setResultInfo(resultInfo);

				// 总数
				int total = jsonArray.getInt("total");
				TotalUtils.getIntence().setTotal(total);

				JSONArray dataList = jsonArray.getJSONArray("dataList");
				for (int j = 0; j < dataList.length(); j++) {
					JSONObject object = dataList.getJSONObject(j);
					GoodsSourceInfo info = new GoodsSourceInfo();

					info.setGoodSID(object.getString("id"));
					info.setGoodSName(object.getString("offerName"));
					info.setGoodSBrand(object.getString("brandname"));
					info.setGoodSCategory(object.getString("categoryname"));
					info.setGoodSWeight(object.getString("inventory"));
					info.setPublicstatename(object.getString("publicstatename"));
					info.setPublicstate(object.getInt("publicstate"));
					info.setIntentionOrderState(object.getString("offerStatename"));
					info.setGoodSPrePrice(object.getString("price"));
					info.setOfferid(object.getString("offerid"));

					datas.add(info);
				}
			}
			sourceInfo.setPurInfos(datas);
			return sourceInfo;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 我的关注-关注供应-详情
	 * 
	 * @param arg1
	 * @return
	 */
	public static GoodsSourceInfo getAttentionProviderDetailInfo(Object arg1) {
		Log.d("TAG", "--getAttentionProviderDetailInfo--" + arg1.toString());
		GoodsSourceInfo info = new GoodsSourceInfo();
		JSONArray array = (JSONArray) arg1;
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				for (int j = 0; j < jsonArray.length(); j++) {
					JSONObject object = jsonArray.getJSONObject(j);

				}
			}

			return info;

		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 我的关注-关注采购-详情
	 * 
	 * @param arg1
	 * @return
	 */
	public static GoodsSourceInfo getAttentionPurchaseDetailInfo(Object arg1) {
		Log.d("TAG", "--getAttentionPurchaseDetailInfo--" + arg1.toString());
		GoodsSourceInfo info = new GoodsSourceInfo();
		JSONArray array = (JSONArray) arg1;
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				for (int j = 0; j < jsonArray.length(); j++) {
					JSONObject object = jsonArray.getJSONObject(j);

				}
			}
			return info;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 我的关注-关注商家列表
	 * 
	 * @param arg1
	 * @return
	 */
	public static GoodsSourceInfo getAttentionMerchantListInfo(Object arg1) {
		JSONArray array = (JSONArray) arg1;
		Log.d("TAG", "--getAttentionLogisticsListInfo--" + arg1.toString());
		ArrayList<GoodsSourceInfo> datas = new ArrayList<GoodsSourceInfo>();
		GoodsSourceInfo sourceInfo = new GoodsSourceInfo();
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);

				RequestNetResultInfo resultInfo = new RequestNetResultInfo();
				resultInfo.setMsg(jsonObject.getString("msg"));
				resultInfo.setStatus(jsonObject.getInt("status"));
				sourceInfo.setResultInfo(resultInfo);

				JSONObject jsonArray = jsonObject.getJSONObject("data");

				// 总数
				int total = jsonArray.getInt("total");
				TotalUtils.getIntence().setTotal(total);

				JSONArray dataList = jsonArray.getJSONArray("dataList");
				for (int j = 0; j < dataList.length(); j++) {
					JSONObject object = dataList.getJSONObject(j);
					GoodsSourceInfo info = new GoodsSourceInfo();
					info.setGoodSID(object.getString("id"));
					info.setCompantId(object.getString("companyid"));
					info.setGoodSMerchant(object.getString("companyName"));
					info.setAddress(object.getString("cityname"));
					info.setGoodSContactWay(object.getString("phone"));
					info.setGoodSContactPerson(object.getString("contact"));
					datas.add(info);
				}
			}

			sourceInfo.setPurInfos(datas);
			return sourceInfo;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 商家关注-关注的买家
	 * 
	 * @param arg1
	 * @return
	 */
	public static GoodsSourceInfo getAttentionBuyerListInfo(Object arg1) {
		JSONArray array = (JSONArray) arg1;
		GoodsSourceInfo sourceInfo = new GoodsSourceInfo();
		ArrayList<GoodsSourceInfo> datas = new ArrayList<GoodsSourceInfo>();

		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);

				RequestNetResultInfo resultInfo = new RequestNetResultInfo();
				resultInfo.setMsg(jsonObject.getString("msg"));
				resultInfo.setStatus(jsonObject.getInt("status"));
				sourceInfo.setResultInfo(resultInfo);

				JSONObject jsonArray = jsonObject.getJSONObject("data");

				// 总数
				int total = jsonArray.getInt("total");
				TotalUtils.getIntence().setTotal(total);

				JSONArray dataList = jsonArray.getJSONArray("dataList");
				for (int j = 0; j < dataList.length(); j++) {
					JSONObject object = dataList.getJSONObject(j);
					
					GoodsSourceInfo info = new GoodsSourceInfo();
					info.setGoodSID(object.getString("id"));
					info.setUserName(object.getString("username"));
					info.setUserId(object.getString("userid"));
					info.setGoodSContactWay(object.getString("mobile"));
					info.setEmail(object.getString("email"));
					info.setAddress(object.getString("address"));
					datas.add(info);
				}
			}
			sourceInfo.setPurInfos(datas);
			return sourceInfo;
		} catch (Exception e) {
		}
		return sourceInfo;
	}

	public static GoodsSourceInfo getAttentionLogisticsResultInfo(Object arg1) {
		ArrayList<GoodsSourceInfo> datas = new ArrayList<GoodsSourceInfo>();
		GoodsSourceInfo sourceInfo = new GoodsSourceInfo();
		JSONArray array = (JSONArray) arg1;
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);

				RequestNetResultInfo resultInfo = new RequestNetResultInfo();
				resultInfo.setMsg(jsonObject.getString("msg"));
				resultInfo.setStatus(jsonObject.getInt("status"));
				sourceInfo.setResultInfo(resultInfo);

				JSONObject jObject = jsonObject.getJSONObject("data");

				// 总数
				int total = jObject.getInt("total");
				TotalUtils.getIntence().setTotal(total);

				JSONArray arr = jObject.getJSONArray("dataList");
				for (int j = 0; j < arr.length(); j++) {
					JSONObject object = arr.getJSONObject(j);
					GoodsSourceInfo info = new GoodsSourceInfo();

					info.setGoodSContactPerson(object.getString("contactname"));
					info.setGoodSContactWay(object.getString("contactphone"));
					info.setCompanyName(object.getString("companyname"));
					info.setCompanytypename(object.getString("companytypename"));
					info.setId(object.getString("id"));

					datas.add(info);

				}
			}
			sourceInfo.setPurInfos(datas);
			return sourceInfo;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public static com.baiyi.cmall.model.RequestNetResultInfo getAttentionProductResultInfo(Object arg1) {

		Log.d("TAG", "关注的产品 = " + arg1);
		JSONArray array = (JSONArray) arg1;
		List<Blm> blms = new ArrayList<Blm>();
		com.baiyi.cmall.model.RequestNetResultInfo info = new com.baiyi.cmall.model.RequestNetResultInfo<>();
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);

				info.setMsg(jsonObject.getString("msg"));
				info.setStatus(jsonObject.getInt("status"));

				JSONObject data = jsonObject.getJSONObject("data");

				JSONArray li = data.getJSONArray("li");

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
			}
			info.setBlms(blms);

			return info;
		} catch (Exception e) {
		}
		return null;

	}

}
