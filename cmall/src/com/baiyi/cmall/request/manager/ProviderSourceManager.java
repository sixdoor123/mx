package com.baiyi.cmall.request.manager;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.baiyi.cmall.activities.main.total.TotalUtils;
import com.baiyi.cmall.entity.GoodSOriginInfo;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.IntentionDetailStandardInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.utils.JsonParseBase;
import com.baiyi.cmall.utils.Utils;

/**
 * 货源解析管理类
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-8 上午10:56:58
 */
public class ProviderSourceManager {

	// 记录参数
	private static String argument;
	// 记住第一个参数的值
	private static String s1 = "";
	// 记住第二个参数的值
	private static String s2 = "";
	// 记住第三个参数的值
	private static String s3 = "";
	// 记住第四个参数的值
	private static String s4 = "";
	private static StringBuilder b;

	/**
	 * 获取POST请求数据
	 * 
	 * @param arg
	 *            选择的参数
	 * @param select
	 *            标志位-记录是哪一个pop被选择 1:分类 2:地区 3:默认排序 0:全部
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 */
	public static String getPostData(String arg, int select, int pageIndex,
			int pageSize) {
		argument = "";
		b = new StringBuilder();
		// 页码
		b.append("&").append("pageIndex").append("=").append(pageIndex);
		// 每页显示数据条数
		b.append("&").append("pageSize").append("=").append(pageSize);

		StringBuilder builder = new StringBuilder();

		switch (select) {
		case 1:
			if (!TextUtils.isEmpty(s1)) {
				s1 = "";
			}
			s1 = builder.append("&").append("category").append("=").append(arg)
					.toString();
			break;
		case 2:
			if (arg.equals("-1")) {
				arg = "";
			}
			if (!TextUtils.isEmpty(s2)) {
				s2 = "";
			}
			s2 = builder.append("&").append("delivery").append("=").append(arg)
					.toString();
			break;
		case 3:
			if (!TextUtils.isEmpty(s3)) {
				s3 = "";
			}
			// s3 =
			// builder.append("&").append("price").append("=").append(arg).toString();
			s3 = builder.append("&").append("sortField").append("=")
					.append(arg).append("&").append("sortType").append("=")
					.append("0").toString();
			break;
		case 4:
			if (!TextUtils.isEmpty(s4)) {
				s4 = "";
			}
			s4 = builder.append("&").append("key").append("=").append(arg)
					.toString();
			break;
		case -1:
			s1 = "";
			s2 = "";
			s3 = "";
			s4 = "";
			break;
		}

		argument = s1 + s2 + s3 + s4;

		if (!argument.contains(b.toString())) {
			argument = b.toString() + argument;
		}
		if (argument.startsWith("&")) {
			argument = argument.substring(1, argument.length());
		}
		return argument;
	}

	/**
	 * 解析供应列表的方法(暂时不用)
	 * 
	 * @param result
	 * @return
	 */
	public static ArrayList<GoodsSourceInfo> getGoodSListInfo(Object result) {
		ArrayList<GoodsSourceInfo> datas = new ArrayList<GoodsSourceInfo>();
		Log.d("TAG", "--getGoodSListInfo--" + result.toString());
		JSONArray array = (JSONArray) result;
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);

				JSONArray jsonArray = jsonObject.getJSONArray("data");
				for (int j = 0; j < jsonArray.length(); j++) {

					JSONObject object = jsonArray.getJSONObject(j);
					GoodsSourceInfo info = new GoodsSourceInfo();
					// 发布时间
					long time = object.getLong("offerBeginTime");
					info.setGoodSPutTime(Utils.getTimeYMD(time));
					// 商品名称
					info.setGoodSName(object.getString("offerName"));
					// 商品id
					info.setGoodSID(object.getString("id"));
					// 商品分类
					info.setGoodSCategory(object.getString("categoryName"));
					// 供应详细请
					info.setGoodSDetails(object.getString("offerDetail"));
					// 货物价格
					info.setGoodSPrePrice(object.getString("price"));
					// 公司名称
					info.setGoodSMerchant(object.getString("companyName"));
					// 货物的品种
					info.setGoodSBreed(object.getString("brandName"));

					datas.add(info);
				}
			}

			return datas;

		} catch (JSONException e) {
			Log.e("TAG", "货源解析管理类JSONException----------error--------------");
			e.printStackTrace();
		}

		return null;
	}

	public static GoodsSourceInfo getGoodSdetailsInfo(Object result) {
		JSONArray array = (JSONArray) result;
		Log.d("TAG", "--getGoodSdetailsInfo--" + result.toString());
		GoodsSourceInfo info = new GoodsSourceInfo();
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONObject object1 = jsonObject.getJSONObject("data");

				JSONObject object = object1.getJSONObject("indexOfferModel");
				// 货源id
				info.setGoodSID(object.getString("id"));
				// 商品名称
				info.setGoodSName(object.getString("offerName"));
				// 商品分类
				info.setGoodSCategory(object.getString("categoryName"));
				// 商品产地
				info.setGoodSPlace(object.getString("originPlaceName"));
				// 公司名称
				info.setGoodSMerchant(object.getString("companyName"));
				// 货物重量
				info.setGoodSWeight(object.getString("inventory"));
				// 货物价格
				info.setGoodSPrePrice(object.getString("price"));
				// 交割地
				info.setGoodSDelivery(object.getString("deliveryPlaceName"));

				// 价格方式
				info.setGoodSPriceWay(object.getString("priceWayName"));
				// 三种规格
				// String s1 = object.getString("codevalue");
				// String s2 = object.getString("propertyname");
				// String s3 = object.getString("propertyvalue");
				// info.setGoodSSpecification("("+s1+"/"+s2+"/"+s3+")");

				// 发布时间
				long time = object.getLong("offerBeginTime");
				info.setGoodSPutTime(Utils.getTimeYMD(time));

				// info.setGoodSArea(object.getString("areaName"));
				// 提交时间
				long commTime = object.getLong("createTime");
				info.setGoodSCommitTime(Utils.getTimeYMD(commTime));
				// 货物的品种
				info.setGoodSBreed(object.getString("brandName"));
				// 联系人
				info.setGoodSContactPerson(object.getString("contact"));
				// 联系方式
				info.setGoodSContactWay(object.getString("phone"));
			}

			return info;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取供应详情参数的Post数据
	 * 
	 * @return
	 */
	public static String getGoodSDetailPostData() {
		JSONObject json = new JSONObject();

		return json.toString();
	}

	/**
	 * 解析提交委托采购返回的结果
	 * 
	 * @param arg1
	 * @return
	 */
	public static RequestNetResultInfo getDetegationResult(Object arg1) {

		RequestNetResultInfo info = new RequestNetResultInfo();

		Log.d("TAG", "--getDetegationResult--" + arg1.toString());

		JSONArray array = (JSONArray) arg1;
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				// JSONArray jsonArray = jsonObject.getJSONArray("data");
				info.setMsg(jsonObject.getString("msg"));
				info.setStatus(jsonObject.getInt("status"));
			}

			return info;

		} catch (Exception e) {
		}

		return null;
	}

	/**
	 * 提交委托采购需要携带的参数
	 * 
	 * @param info2
	 * @param info2
	 * @return
	 */
	public static String getDetegationPostData(GoodsSourceInfo sourceInfo,
			GoodsSourceInfo info, String userID) {

		StringBuilder builder = new StringBuilder();

		// builder.append("userid").append("=").append(1).append("&");
		if (null != info) {

			if (!TextUtils.isEmpty(userID)) {
				builder.append("userid").append("=").append(userID).append("&");
			}
			if (!TextUtils.isEmpty(sourceInfo.getDeliverycityid())) {
				builder.append("deliverycityid").append("=")
						.append(sourceInfo.getDeliverycityid()).append("&");
			} 
			if (!TextUtils.isEmpty(sourceInfo.getOrigincityid())) {
				builder.append("origincityid").append("=")
						.append(info.getOrigincityid()).append("&");
			}
			if (!TextUtils.isEmpty(info.getCategoryID())) {
				builder.append("category").append("=").append(info.getCategoryID())
						.append("&");
			}
			if (!TextUtils.isEmpty(info.getCityid())) {
				builder.append("cityid").append("=").append(sourceInfo.getCityid())
						.append("&");
			}
			builder.append("address").append("=")
					.append(sourceInfo.getAddress()).append("&");

			builder.append("companyname").append("=")
					.append(sourceInfo.getGoodSMerchant()).append("&");

			builder.append("brand").append("=").append(info.getGoodSBrand())
					.append("&");
			builder.append("quantity").append("=")
					.append(sourceInfo.getGoodSWeight()).append("&");
			builder.append("price").append("=")
					.append(sourceInfo.getGoodSPrice()).append("&");

			builder.append("offerid").append("=").append(info.getGoodSID())
					.append("&");
			builder.append("title").append("=").append(info.getGoodSName())
					.append("&");
			builder.append("details").append("=")
					.append(sourceInfo.getGoodSPurchaseNeed()).append("&");
			builder.append("contact").append("=")
					.append(sourceInfo.getGoodSContactPerson()).append("&");
			builder.append("mobile").append("=")
					.append(sourceInfo.getGoodSContactWay());
		}

		return builder.toString();
	}

	/**
	 * 供应-加入关注携带的数据
	 * 
	 * @param info
	 * @return
	 */
	public static String getAttiontionPostData(String userID,
			GoodsSourceInfo info) {

		//
		StringBuilder builder = new StringBuilder();
		builder.append("userid").append("=").append(userID).append("&");
		builder.append("targetid").append("=").append(info.getGoodSID());

		return builder.toString();
	}

	/**
	 * 解析加入关注返回结果
	 * 
	 * @param arg1
	 */
	public static RequestNetResultInfo getAttiontionResultInfo(Object arg1) {
		Log.d("TAG", "---------getAttiontionResultInfo------------" + arg1);
		RequestNetResultInfo info = new RequestNetResultInfo();

		JSONArray array = (JSONArray) arg1;
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				// JSONArray jsonArray = jsonObject.getJSONArray("data");
				// 返回的信息
				info.setMsg(jsonObject.getString("msg"));
				// 返回的状态码
				info.setStatus(jsonObject.getInt("status"));
			}
		} catch (Exception e) {
			Log.d("TAG", e.toString());
		}
		return info;
	}

	/**
	 * 获取供应详情里面的属性
	 * 
	 * @param arg1
	 * @return
	 */
	public static GoodsSourceInfo getStandardArgmentResultInfo(Object arg1) {

		GoodsSourceInfo sourceInfo = new GoodsSourceInfo();
		ArrayList<IntentionDetailStandardInfo> datas = new ArrayList<IntentionDetailStandardInfo>();
		Log.d("TAG", "--getStandardArgmentResultInfo--" + arg1.toString());
		try {
			JSONArray array = (JSONArray) arg1;
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);

				RequestNetResultInfo resultInfo = new RequestNetResultInfo();
				resultInfo.setMsg(jsonObject.getString("msg"));
				resultInfo.setStatus(jsonObject.getInt("status"));
				sourceInfo.setResultInfo(resultInfo);

				JSONObject dataJsonObject = jsonObject.getJSONObject("data");
				JSONArray jsonArray = dataJsonObject.getJSONArray("resList");
				for (int j = 0; j < jsonArray.length(); j++) {
					JSONObject object = jsonArray.getJSONObject(j);
					IntentionDetailStandardInfo info = new IntentionDetailStandardInfo();
					info.setId(object.getString("id"));
					info.setCodevalue(object.getString("codevalue"));
					info.setPropertyname(object.getString("propertyname"));
					info.setPropertyvalue(object.getString("propertyvalue"));
					info.setPropertyunit(object.getString("propertyunit"));

					datas.add(info);
				}
				sourceInfo.setStandardInfos(datas);

				JSONObject detils = dataJsonObject.getJSONObject("offer");

				sourceInfo.setGoodSID(detils.getString("id"));
				// 提交时间
				// long time = detils.getLong("createTime");
				// sourceInfo.setGoodSCommitTime(Utils.getTimeYMD(time));
				// sourceInfo.setGoodSContactWay(detils.getString("phone"));
				sourceInfo.setGoodSName(detils.getString("offername"));
				// 结束时间
				// long date = detils.getLong("offerEndTime");
				// sourceInfo.setGoodSEndTime(Utils.getTimeYMD(date));
				// 发布时间
				long t = detils.getLong("offerbegintime");
				sourceInfo.setGoodSPutTime(Utils.getTimeYMD(t));

				// sourceInfo.setGoodSPriceWay(detils.getString("priceWayName"));
				sourceInfo.setGoodSMerchant(detils
						.getString("offercompanyname"));
				// sourceInfo.setGoodSContactPerson(detils.getString("contact"));
				sourceInfo.setGoodSBrand(detils.getString("brandname"));
				sourceInfo.setGoodSDelivery(detils
						.getString("deliveryplacename"));

				sourceInfo.setGoodSCategory(detils.getString("categoryname"));
				sourceInfo.setGoodSDetails(detils.getString("offerdetail"));
				sourceInfo.setGoodSPrePrice(detils.getString("price"));
				sourceInfo.setPrepayment(detils.getString("prepayment"));
				sourceInfo.setGoodSWeight(detils.getString("inventory"));
				// sourceInfo.setOrigincityid(detils.getString("origincityid"));
				sourceInfo.setGoodSPlace(detils.getString("originplacename"));
				// sourceInfo.setCompantId(detils.getString("companyId"));
				sourceInfo.setIsfollow(detils.getBoolean("isfollow"));
				sourceInfo.setCompanyId(detils.getString("companyid"));

				sourceInfo.setCityid(detils.getString("cityid"));
				sourceInfo.setCategoryID(detils.getString("category"));
				sourceInfo.setDeliverycityid(detils.getString("deliveryplace"));
				sourceInfo.setOrigincityid(detils.getString("originplace"));
				sourceInfo.setAddress(detils.getString("address"));
				sourceInfo.setGoodSArea(detils.getString("cityname"));

				sourceInfo.setIsowner(detils.getBoolean("isowner"));
			}

			return sourceInfo;

		} catch (Exception e) {
		}

		return sourceInfo;
	}

	/**
	 * 我是供应商-我的供应-关注商家
	 * 
	 * @param info
	 * @return
	 */
	public static String getAttiontionMerchantPostData(String userID,
			GoodsSourceInfo info) {
		//
		StringBuilder builder = new StringBuilder();
		if (null != info) {
			builder.append("userid").append("=").append(userID).append("&");
			builder.append("targetid").append("=").append(info.getGoodSID());
		}
		return builder.toString();
	}

	/**
	 * 首页货源列表 包括查询条件信息
	 * 
	 * @param result
	 * @return
	 */
	public static ArrayList<GoodSOriginInfo> getGoodSList(Context context,
			Object result) {

		ArrayList<GoodSOriginInfo> datas = new ArrayList<GoodSOriginInfo>();

		Log.d("TAG", "--getProviderIntentationOrderList--" + result.toString());
		JSONArray array = (JSONArray) result;

		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONObject data = jsonObject.getJSONObject("data");

				// 保存总数
				int total = data.getInt("total");
				TotalUtils.getIntence().setTotal(total);

				JSONArray tagList = data.getJSONArray("dataList");

				for (int j = 0; j < tagList.length(); j++) {
					JSONObject list = tagList.getJSONObject(j);

					GoodSOriginInfo info = new GoodSOriginInfo();
					// 开始时间
					long time = list.getLong("offerBeginTime");
					info.setOfferBeginTime(Utils.getTimeYMD(time));
					info.setId(list.getString("id"));
					info.setCategoryName(list.getString("categoryName"));
					info.setOfferDetails(list.getString("offerDetail"));
					info.setInventory(list.getString("inventory"));
					info.setCompanyName(list.getString("companyName"));
					info.setOfferName(list.getString("offerName"));
					info.setBrandName(list.getString("brandName"));
					info.setPrice(list.getString("price"));

					datas.add(info);
				}
			}

			return datas;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 获取供应详情里面的属性
	 * 
	 * @param arg1
	 * @return
	 */
	public static GoodsSourceInfo getHomePurDetailInfo(Object arg1) {

		GoodsSourceInfo sourceInfo = new GoodsSourceInfo();
		try {
			JSONArray array = (JSONArray) arg1;
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);

				RequestNetResultInfo resultInfo = new RequestNetResultInfo();
				resultInfo.setMsg(jsonObject.getString("msg"));
				resultInfo.setStatus(jsonObject.getInt("status"));
				sourceInfo.setResultInfo(resultInfo);

				JSONObject detils = jsonObject.getJSONObject("data");

				sourceInfo.setGoodSID(JsonParseBase.getStringNodeValue(detils,
						"id"));
				sourceInfo.setGoodSMerchant(JsonParseBase.getStringNodeValue(
						detils, "cn"));
				sourceInfo.setGoodSCategory(JsonParseBase.getStringNodeValue(
						detils, "ca"));
				sourceInfo.setGoodSName(JsonParseBase.getStringNodeValue(
						detils, "tt"));
				sourceInfo.setGoodSBrand(JsonParseBase.getStringNodeValue(
						detils, "br"));
				sourceInfo.setGoodSPlace(JsonParseBase.getStringNodeValue(
						detils, "cd"));
				sourceInfo.setGoodSWeight(JsonParseBase.getStringNodeValue(
						detils, "ac"));
				sourceInfo.setGoodSDelivery(JsonParseBase.getStringNodeValue(
						detils, "jg"));
				sourceInfo.setGoodSPrePrice(JsonParseBase.getStringNodeValue(
						detils, "am"));
				sourceInfo.setPrepayment(JsonParseBase.getStringNodeValue(
						detils, "pr"));
				long t = JsonParseBase.getLongNodeValue(detils, "pt");
				if (0 != t) {
					sourceInfo.setGoodSPutTime(Utils.getTimeYMD(t));
				}
			}

			return sourceInfo;

		} catch (Exception e) {
		}

		return sourceInfo;
	}

}
