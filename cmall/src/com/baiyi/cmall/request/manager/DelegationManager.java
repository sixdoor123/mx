package com.baiyi.cmall.request.manager;

import org.json.JSONArray;
import org.json.JSONObject;

import android.R.integer;
import android.util.Log;

import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.utils.Utils;

/**
 * 我的委托的管理类
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-1-19 下午2:53:13
 */
public class DelegationManager {

	/**
	 * 委托采购、委托供应-编辑按钮-数据
	 * 
	 * @param goodSName
	 * @param merchaseName
	 * @param weight
	 * @param contact
	 * @param number
	 * @param needContent
	 * @param price
	 * @param brand
	 * @param categoryInfo
	 *            分类
	 * @param personCityInfo
	 *            联系人城市
	 * @param addressInfo
	 *            地址
	 * @param deliveryInfo
	 *            交个地
	 * @param placeInfo
	 *            产地
	 * @param info
	 * @param address
	 * @param personDetailAddress
	 * @return
	 */
	public static String getDeletegationPurProData(String goodSName,
			String merchaseName, String weight, String contact, String number,
			String needContent, String price, String brand,
			SelectedInfo categoryInfo, SelectedInfo personCityInfo,
			SelectedInfo addressInfo, SelectedInfo deliveryInfo,
			SelectedInfo placeInfo, GoodsSourceInfo info,  String address, String personDetailAddress) {

		StringBuilder builder = new StringBuilder();

		builder.append("id").append("=").append(info.getGoodSID()).append("&");
		builder.append("companyname").append("=").append(merchaseName)
				.append("&");
		builder.append("contact").append("=").append(contact).append("&");
		builder.append("mobile").append("=").append(number).append("&");
		builder.append("title").append("=").append(goodSName).append("&");
		builder.append("details").append("=").append(needContent).append("&");

		if (null != personCityInfo) {
			String cityId = personCityInfo.getId();
			if (cityId == null || "null".equals(cityId)) {
				cityId = "0";
			}
			builder.append("cityid").append("=").append(cityId).append("&");
		} else {
			String cityId = info.getCityid();
			if (cityId == null || "null".equals(cityId)) {
				cityId = "0";
			}
			builder.append("cityid").append("=").append(cityId).append("&");
		}
		if (null != categoryInfo) {
			String category = categoryInfo.getId();
			if (category == null || "null".equals(category)) {
				category = "0";
			}
			builder.append("category").append("=").append(category).append("&");
		} else {
			String category = info.getCategoryID();
			if (category == null || "null".equals(category)) {
				category = "0";
			}
			builder.append("category").append("=").append(category).append("&");
		}

		if (null != deliveryInfo) {
			String deliverycityid = deliveryInfo.getId();
			if (deliverycityid == null || "null".equals(deliverycityid)) {
				deliverycityid = "0";
			}
			builder.append("deliverycityid").append("=").append(deliverycityid)
					.append("&");
		} else {
			String deliverycityid = info.getDeliverycityid();
			if (deliverycityid == null || "null".equals(deliverycityid)) {
				deliverycityid = "0";
			}
			builder.append("deliverycityid").append("=").append(deliverycityid)
					.append("&");
		}

		if (null != placeInfo) {
			String origincityid = placeInfo.getId();
			if (origincityid == null || "null".equals(origincityid)) {
				origincityid = "0";
			}
			builder.append("origincityid").append("=").append(origincityid)
					.append("&");
		} else {
			String origincityid = info.getOrigincityid();
			if (origincityid == null || "null".equals(origincityid)) {
				origincityid = "0";
			}
			builder.append("origincityid").append("=").append(origincityid)
					.append("&");
		}

		// 详细地址
		if (null != addressInfo) {
			builder.append("address").append("=").append(address).append("&");
		} else {
			builder.append("address").append("=").append(personDetailAddress)
					.append("&");
		}

		builder.append("brand").append("=").append(brand).append("&");
		builder.append("quantity").append("=")
				.append(weight == null ? 0 : weight).append("&");
		builder.append("price").append("=").append(price == null ? 0 : price);
		return builder.toString();
	}

	/**
	 * 关注商家、关注采购-详情解析
	 * 
	 * @param arg1
	 * @param state
	 *            1:关注供应，0:关注采购
	 */
	public static GoodsSourceInfo getAttentionProPurDetailInfo(Object arg1,
			int state) {
		Log.d("TAG", "--getAttentionProPurDetailInfo--" + arg1.toString());

		JSONArray array = (JSONArray) arg1;
		GoodsSourceInfo info = new GoodsSourceInfo();

		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);

				RequestNetResultInfo resultInfo = new RequestNetResultInfo();
				resultInfo.setMsg(jsonObject.getString("msg"));
				resultInfo.setStatus(jsonObject.getInt("status"));
				info.setResultInfo(resultInfo);

				JSONObject object = jsonObject.getJSONObject("data");
				// 解析不同之处
				if (2 == state) {
					info.setGoodSID(object.getString("id"));
					info.setCompantId(object.getString("companyid"));
					info.setGoodSContactWay(object.getString("phone"));
					info.setAddress(object.getString("address"));
					info.setEmail(object.getString("email"));
					info.setGoodSDetails(object.getString("descript"));
					info.setCompanytypename(object.getString("companytypename"));
					info.setCity(object.getString("cityname"));
					info.setImageurl(object.getString("imageurl"));
					info.setCompanyName(object.getString("companyName"));
					info.setGoodSContactPerson(object.getString("contact"));

				} else {
					if (1 == state) {
						// 解析关注供应详情
						info.setGoodSName(object.getString("offerName"));
						info.setOfferid(object.getString("offerid"));
						// 发布时间
						long time = object.getLong("offerBeginTime");
						info.setGoodSPutTime(Utils.getTimeYMD(time));
						info.setGoodSDetails(object.getString("offerDetail"));
					} else {
						info.setPurchaseid(object.getString("purchaseid"));
						// 解析关注采购详情
						info.setGoodSName(object.getString("purchaseName"));
						info.setPurchaseid(object.getString("purchaseid"));
						// 发布时间
						long time = object.getLong("purchaseBeginTime");
						info.setGoodSPutTime(Utils.getTimeYMD(time));
						info.setGoodSDetails(object.getString("purchaseDetail"));
					}

					info.setGoodSID(object.getString("id"));

					info.setGoodSCategory(object.getString("categoryName"));
					info.setGoodSPlace(object.getString("originPlaceName"));
					info.setGoodSBrand(object.getString("brandName"));

					String weght = object.getString("inventory");
					if ("null".equals(weght)) {
						weght = String.valueOf(0);
					}
					info.setGoodSWeight(weght);

					String price = object.getString("price");
					if ("null".equals(price)) {
						price = String.valueOf(0);
					}
					info.setGoodSPrePrice(price);

					info.setPrepayment(object.getString("prepayment"));
					info.setGoodSDelivery(object.getString("deliveryPlaceName"));
				}
			}

			return info;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return info;
	}

	/**
	 * 取消关注返回的结果信息
	 * 
	 * @param arg1
	 * @param state
	 * @return
	 */
	public static RequestNetResultInfo getCancelAttentionResultInfo(
			Object arg1, int state) {
		JSONArray array = (JSONArray) arg1;
		RequestNetResultInfo resultInfo = new RequestNetResultInfo();
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);

				resultInfo.setMsg(jsonObject.getString("msg"));
				resultInfo.setStatus(jsonObject.getInt("status"));
			}

			return resultInfo;

		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}

	
	/**
	 * 关注的详情
	 * 
	 * @param arg1
	 * @return
	 */
	public static GoodsSourceInfo getAttentionBuyerDetailInfo(Object arg1) {
		JSONArray array = (JSONArray) arg1;
		GoodsSourceInfo info = new GoodsSourceInfo();
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);

				RequestNetResultInfo resultInfo = new RequestNetResultInfo();
				resultInfo.setMsg(jsonObject.getString("msg"));
				resultInfo.setStatus(jsonObject.getInt("status"));
				info.setResultInfo(resultInfo);

				JSONObject object = jsonObject.getJSONObject("data");

				info.setGoodSID(object.getString("id"));
				info.setUserName(object.getString("username"));
				info.setCompanytypename(object.getString("usertypename"));
				info.setAddress(object.getString("address"));
				info.setEmail(object.getString("email"));
				info.setUserId(object.getString("userid"));
				info.setImageurl(object.getString("imageurl"));
				info.setGoodSContactWay(object.getString("mobile"));

			}

			return info;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
