package com.baiyi.cmall.request.manager;

import java.util.ArrayList;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.TextView;

import com.baiyi.cmall.activities.main.total.TotalUtils;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.IntentionDetailStandardInfo;
import com.baiyi.cmall.utils.JsonParseBase;
import com.baiyi.cmall.utils.Utils;

/**
 * 我是采购商 数据解析
 * 
 * @author lizl
 * 
 */
public class MyPurchaseManager {

	private static MyPurchaseManager purchaseManager;

	private MyPurchaseManager() {
	}

	public static MyPurchaseManager getInstance() {
		if (null == purchaseManager) {
			purchaseManager = new MyPurchaseManager();
		}
		return purchaseManager;
	}

	/**
	 * 获取我的采购单数据列表
	 */
	public static ArrayList<GoodsSourceInfo> getMyPurchaseList(JSONArray data) {
		ArrayList<GoodsSourceInfo> datas = new ArrayList<GoodsSourceInfo>();
		JSONArray array = (JSONArray) data;
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONObject dataOBJ = jsonObject.getJSONObject("data");
				
				int total = dataOBJ.getInt("total");
				TotalUtils.getIntence().setTotal(total);
				
				JSONArray dataArray = dataOBJ.getJSONArray("dataList");

				for (int j = 0; j < dataArray.length(); j++) {
					JSONObject dataObject = dataArray.getJSONObject(j);
					/*
					 * 采购商数据
					 */
					JSONArray purArray = dataObject
							.getJSONArray("userPurModel");
					JSONObject purObject = purArray.getJSONObject(0);

					GoodsSourceInfo puriInfo = new GoodsSourceInfo();

					puriInfo.setGoodSPurchaseOrderId((int) (purObject
							.getLong("id")));// 采购ID
					puriInfo.setGoodSName(purObject.getString("purchasename"));// 名称
					puriInfo.setGoodSCategory(purObject
							.getString("categoryName"));// 分类名称
					puriInfo.setGoodSCategoryNum(purObject
							.getString("category"));// 分类名称号码
					puriInfo.setGoodSPlace(purObject
							.getString("originPlaceName"));// 产地名称
					puriInfo.setOrigincityid(purObject.getString("originplace"));// 产地名称号码
					puriInfo.setGoodSWeight(purObject.getString("amount"));// 数量
					puriInfo.setGoodSPrice(purObject.getString("price"));// 货物价格（单价）
					puriInfo.setGoodSPrePrice(purObject.getString("prepayment"));// 预付费
					long time = purObject.getLong("purchasebegintime");// 发布时间
					puriInfo.setGoodSPutTime(Utils.getTimeYMD(time));
					puriInfo.setGoodSBrand(purObject.getString("brandName"));// 品种
					puriInfo.setGoodSBrandNum(purObject.getLong("brand"));// 品种号
					puriInfo.setGoodSDelivery(purObject
							.getString("deliveryPlaceName"));// 交割地
					puriInfo.setDeliverycityid(purObject
							.getString("deliveryplace"));// 交割地号
					puriInfo.setIntentionOrderState(purObject
							.getString("purchaseStateName"));// 状态名称
					puriInfo.setPubState(purObject.getInt("publicstate"));// 发布状态
					puriInfo.setPubStateName(purObject
							.getString("publicstatename"));// 发布状态名称
					puriInfo.setGoodSRemark(purObject
							.getString("purchaseAudit"));// 备注
					puriInfo.setIntentionstatename(purObject
							.getString("statename"));// 采购列表显示的状态名称

					/*
					 * 采购商对应的供应商信息
					 */
					JSONArray offerArray = dataObject
							.getJSONArray("userOfferIntentionModelList");
					// 对应的供应商
					ArrayList<GoodsSourceInfo> offerDatas = new ArrayList<GoodsSourceInfo>();
					for (int k = 0; k < offerArray.length(); k++) {

						JSONObject offerObject = offerArray.getJSONObject(k);

						GoodsSourceInfo offerInfo = new GoodsSourceInfo();
						offerInfo.setGoodSPurchaseOrderId(offerObject
								.getInt("id"));// 供应意向ID
						offerInfo.setGoodSName(offerObject
								.getString("offername"));// 名称
						offerInfo.setGoodSCompanyNmae(offerObject
								.getString("companyname"));// 供应商名称
						offerInfo.setKuCun(offerObject.getString("inventory"));// 库存
						offerInfo.setIntentionOrderState(offerObject
								.getString("intentionstatename"));// 状态
						offerInfo.setGoodSDelivery(offerObject
								.getString("deliveryplacename"));// 交割地名称
						offerInfo.setGoodSPrice(offerObject.getString("price"));// 货物价格（单价）
						offerInfo.setType(offerObject.getInt("intentiontype"));// 意向类型
						offerInfo
								.setState(offerObject.getInt("intentionstate"));// 意向状态
						offerInfo.setDeletebycompany(offerObject
								.getInt("deletebycompany"));// 商家删除标志
						offerDatas.add(offerInfo);
					}

					puriInfo.setOfferInfos(offerDatas);
					datas.add(puriInfo);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return datas;
	}

	/**
	 * 获取我的采购单数据列表详情
	 */
	public static GoodsSourceInfo getUpdatePurchaseDetail(JSONArray data) {
		GoodsSourceInfo puriInfo = new GoodsSourceInfo();
		JSONArray array = (JSONArray) data;
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONObject dataObject = jsonObject.getJSONObject("data");
				JSONArray purArray = dataObject.getJSONArray("userPurModel");
				for (int j = 0; j < purArray.length(); j++) {
					JSONObject purObject = purArray.getJSONObject(j);

					puriInfo.setGoodSPurchaseOrderId((int) (purObject.getLong("id")));// 采购ID
					puriInfo.setGoodSName(purObject.getString("purchasename"));// 名称
					puriInfo.setGoodSCategory(purObject.getString("categoryName"));// 分类名称
					puriInfo.setGoodSCategoryNum(purObject.getString("category"));// 分类名称号码
					puriInfo.setGoodSPlace(purObject.getString("originPlaceName"));// 产地名称
					puriInfo.setOrigincityid(purObject.getString("originplace"));// 产地名称号码
					puriInfo.setGoodSWeight(purObject.getString("amount"));// 数量
					puriInfo.setGoodSPrice(purObject.getString("price"));// 货物价格（单价）
					puriInfo.setGoodSPrePrice(purObject.getString("prepayment"));// 预付费
					long startTime = purObject.getLong("purchasebegintime");// 发布时间
					puriInfo.setGoodSPutTime(Utils.getTimeYMD(startTime));
					long endTime = purObject.getLong("purchaseendtime");// 结束时间
					puriInfo.setGoodSEndTime(Utils.getTimeYMD(endTime));
					puriInfo.setGoodSBrand(purObject.getString("brandName"));// 品种
					puriInfo.setGoodSBrandNum(purObject.getLong("brand"));// 品种号
					puriInfo.setGoodSDelivery(purObject.getString("deliveryPlaceName"));// 交割地
					puriInfo.setDeliverycityid(purObject.getString("deliveryplace"));// 交割地号
					puriInfo.setIntentionOrderState(purObject.getString("purchaseStateName"));// 状态名称
					puriInfo.setPubState(purObject.getInt("publicstate"));// 发布状态
					puriInfo.setPubStateName(purObject.getString("publicstatename"));// 发布状态名称
					puriInfo.setGoodSRemark(purObject.getString("purchaseAudit"));// 备注

				}

				/*
				 * 采购商对应的供应商信息
				 */
				JSONArray offerArray = dataObject.getJSONArray("userOfferIntentionModelList");
				// 对应的供应商
				ArrayList<GoodsSourceInfo> offerDatas = new ArrayList<GoodsSourceInfo>();
				for (int k = 0; k < offerArray.length(); k++) {

					JSONObject offerObject = offerArray.getJSONObject(k);

					GoodsSourceInfo offerInfo = new GoodsSourceInfo();
					offerInfo.setGoodSPurchaseOrderId(offerObject.getInt("id"));// 供应意向ID
					offerInfo.setGoodSName(offerObject.getString("offername"));// 名称
					offerInfo.setGoodSCompanyNmae(offerObject.getString("companyname"));// 供应商名称
					offerInfo.setKuCun(offerObject.getString("inventory"));// 库存
					offerInfo.setIntentionOrderState(offerObject.getString("intentionstatename"));// 状态
					offerInfo.setGoodSDelivery(offerObject.getString("deliveryplacename"));// 交割地名称
					offerInfo.setGoodSPrice(offerObject.getString("price"));// 货物价格（单价）
					offerInfo.setType(offerObject.getInt("intentiontype"));// 意向类型
					offerInfo.setState(offerObject.getInt("intentionstate"));// 意向状态
					offerInfo.setDeletebycompany(offerObject.getInt("deletebycompany"));// 供应商删除标志
					offerDatas.add(offerInfo);
				}

				puriInfo.setOfferInfos(offerDatas);

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return puriInfo;
	}
	/**
	 * 获取我的采购单数据列表详情
	 */
	public static GoodsSourceInfo getMyPurchaseDetail(JSONArray data) {
		GoodsSourceInfo info = new GoodsSourceInfo();
		ArrayList<IntentionDetailStandardInfo> standDatas = new ArrayList<IntentionDetailStandardInfo>();
		JSONArray array = (JSONArray) data;
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONObject dataObject = jsonObject.getJSONObject("data");

				/**
				 * 规格
				 */
				JSONArray standArray = dataObject.getJSONArray("list");
				for (int j = 0; j < standArray.length(); j++) {
					JSONObject object = standArray.getJSONObject(j);
					IntentionDetailStandardInfo stardInfo = new IntentionDetailStandardInfo();
					stardInfo.setId(object.getString("id"));
					stardInfo.setPropertyid(object.getString("propertyid"));
					stardInfo.setCodevalue(object.getString("codevalue"));
					stardInfo.setPropertyname(object.getString("propertyname"));
					stardInfo.setPropertyvalue(object
							.getString("propertyvalue"));
					stardInfo.setPropertyunit(object.getString("propertyunit"));
					standDatas.add(stardInfo);
				}
				info.setStandardInfos(standDatas);

				/**
				 * 详情
				 */
				JSONObject modelData = dataObject.getJSONObject("purModel");

				info.setGoodSID(modelData.getString("id"));// 采购ID
				info.setGoodSName(modelData.getString("purchasename"));// 名称
				info.setGoodSCategory(modelData.getString("categoryName"));// 分类
				info.setCategoryID(modelData.getString("category"));// 分类ID
				info.setGoodSPlace(modelData.getString("originPlaceName"));// 产地
				info.setOrigincityid(modelData.getString("originplace"));// 产地ID
				info.setGoodSBrand(modelData.getString("brandName"));// 品种
				info.setBrandID(modelData.getString("brand"));// 品种Id
				info.setGoodSWeight(modelData.getString("amount"));// 数量（重量）
				info.setGoodSpriceInterval(modelData.getString("price"));// 货物价格（单价）
				info.setGoodSPrePrice(modelData.getString("prepayment"));// 预付费
				long publishTime = JsonParseBase.getLongNodeValue(modelData,
						"purchasebegintime");// 发布时间
				info.setGoodSPutTime(Utils.getTimeYMD(publishTime));
				long endTime = JsonParseBase.getLongNodeValue(modelData,
						"purchaseendtime");// 结束时间
				info.setGoodSEndTime(Utils.getTimeYMD(endTime));
				info.setAutoStart(JsonParseBase.getBooleanNodeValue(modelData,
						"autobeginflag"));// 到期自动开始
				info.setAutoEnd(JsonParseBase.getBooleanNodeValue(modelData,
						"autoendflag"));// 到期自动结束
				info.setGoodSDelivery(modelData.getString("deliveryPlaceName"));// 交割地
				info.setDeliverycityid(modelData.getString("deliveryplace"));// 交割地ID
				info.setGoodSPurchaseContent(modelData
						.getString("purchasedetail"));// 采购详情

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return info;
	}

	/**
	 * 编辑采购详情
	 * 
	 * @param info
	 * @return
	 */
	public static String getEditPurDetailPostData(GoodsSourceInfo info) {

		StringBuilder builder = new StringBuilder();
		builder.append("purchasename").append("=").append("8u89yu");// 名称
		builder.append("&");
		builder.append("category").append("=").append(info.getGoodSCategory());// 分类
		builder.append("&");
		builder.append("originplace").append("=").append(info.getGoodSPlace());// 产地
		builder.append("&");
		builder.append("brand").append("=").append(info.getGoodSBrand());// 品牌
		builder.append("&");
		builder.append("amount").append("=").append(info.getGoodSWeight());// 数量
		builder.append("&");
		builder.append("price").append("=").append(info.getGoodSPrePrice());// 价格
		builder.append("&");
		builder.append("prepayment").append("=")
				.append(info.getGoodSPrePrice());// 预付费
		builder.append("&");
		builder.append("purchasebegintime").append("=")
				.append(info.getGoodSPutTime());// 发布时间
		builder.append("&");
		builder.append("purchaseendtime").append("=").append(new Date());// 结束时间
		builder.append("&");
		builder.append("autobeginflag").append("=").append(true);// 到期自动开始
		builder.append("&");
		builder.append("autoendflag").append("=").append(false);// 到期自动结束
		builder.append("&");
		builder.append("deliveryplace").append("=")
				.append(info.getGoodSDelivery());// 交割地
		builder.append("&");
		builder.append("purchasedetail").append("=")
				.append(info.getGoodSContent());// 详细内容

		return builder.toString();
	}

	/**
	 * 供应详情
	 */
	public static GoodsSourceInfo getProItemDetail(JSONArray data) {
		GoodsSourceInfo info = new GoodsSourceInfo();
		ArrayList<IntentionDetailStandardInfo> standDatas = new ArrayList<IntentionDetailStandardInfo>();
		JSONArray array = (JSONArray) data;
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONObject dataObject = jsonObject.getJSONObject("data");

				/**
				 * 规格
				 */
				JSONArray standArray = dataObject.getJSONArray("resList");
				for (int j = 0; j < standArray.length(); j++) {
					JSONObject object = standArray.getJSONObject(j);
					IntentionDetailStandardInfo stardInfo = new IntentionDetailStandardInfo();
					stardInfo.setCodevalue(object.getString("codevalue"));
					stardInfo.setPropertyname(object.getString("propertyname"));
					stardInfo.setPropertyvalue(object
							.getString("propertyvalue"));
					stardInfo.setPropertyunit(object.getString("propertyunit"));

					standDatas.add(stardInfo);
				}
				info.setStandardInfos(standDatas);

				/**
				 * 详情
				 */
				JSONObject modelData = dataObject.getJSONObject("offer");

				info.setGoodSID(modelData.getString("id"));// 供应ID
				info.setGoodSCategory(modelData.getString("categoryname"));// 分类
				info.setGoodSDetails(modelData.getString("offerdetail"));// 供应详情
				info.setGoodSpriceInterval(modelData.getString("price"));// 货物价格（单价）
				info.setGoodSWeight(modelData.getString("inventory"));// 数量（重量）
				info.setGoodSArea(modelData.getString("originplacename"));// 产地
				info.setGoodSPrePrice(modelData.getString("prepayment"));// 预付费
				info.setGoodSBrand(modelData.getString("brandname"));// 品种
				info.setGoodSContent(modelData.getString("offername"));// 详细内容
				info.setGoodSDelivery(modelData.getString("deliveryplacename"));// 交割地

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		// TODO 解析
		return info;
	}

	/**
	 * 采购详情
	 */
	public static GoodsSourceInfo getPurItemDetail(JSONArray data) {
		GoodsSourceInfo info = new GoodsSourceInfo();
		ArrayList<IntentionDetailStandardInfo> standDatas = new ArrayList<IntentionDetailStandardInfo>();
		JSONArray array = (JSONArray) data;
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONObject dataObject = jsonObject.getJSONObject("data");

				/**
				 * 规格
				 */
				JSONArray standArray = dataObject.getJSONArray("resModelList");
				for (int j = 0; j < standArray.length(); j++) {
					JSONObject object = standArray.getJSONObject(j);
					IntentionDetailStandardInfo stardInfo = new IntentionDetailStandardInfo();
					stardInfo.setCodevalue(object.getString("codevalue"));
					stardInfo.setPropertyname(object.getString("propertyname"));
					stardInfo.setPropertyvalue(object
							.getString("propertyvalue"));

					standDatas.add(stardInfo);
				}
				info.setStandardInfos(standDatas);

				/**
				 * 详情
				 */
				JSONObject modelData = dataObject.getJSONObject("offerModel");

				info.setGoodSID(modelData.getString("id"));// 供应ID
				info.setGoodSCategory(modelData.getString("categoryname"));// 分类
				info.setGoodSDetails(modelData.getString("purchasedetail"));// 采购详情
				info.setGoodSpriceInterval(modelData.getString("price"));// 货物价格（单价）
				info.setGoodSWeight(modelData.getString("amount"));// 数量（重量）
				info.setGoodSArea(modelData.getString("originplacename"));// 产地
				info.setGoodSPrePrice(modelData.getString("prepayment"));// 预付费
				info.setGoodSBrand(modelData.getString("brandname"));// 品种
				info.setGoodSContent(modelData.getString("purchasename"));// 详细内容
				info.setGoodSDelivery(modelData.getString("deliveryplacename"));// 交割地

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		// TODO 解析
		return info;
	}

	/**
	 * 我是采购商--我的采购---编辑采购信息的数据
	 * 
	 * @param id
	 * @param details
	 * @param standards
	 * @param datas
	 * @param deliveryId
	 * @param brandId
	 * @param originplaceId
	 * @param categoryId
	 * @return
	 */
	public static String getMyPurEditPostData(String purId,
			ArrayList<TextView> details, ArrayList<TextView> standards,
			ArrayList<IntentionDetailStandardInfo> datas, String deliveryId) {

		JSONObject object = new JSONObject();

		try {
			object.put("id", JSONObject.NULL);
			object.put("purModel",
					getPurModelDetail(purId, details, deliveryId));
			object.put("list", getStantardArray(standards, datas));

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return object.toString();
	}

	/**
	 * 采购Model编辑----有分类、品牌、产地
	 * 
	 * @param id
	 * @param details
	 * @param deliveryId
	 * @param brandId
	 * @param originplaceId
	 * @param categoryId
	 * @return
	 */
	private static JSONObject getPurModelDetail(String purId,
			ArrayList<TextView> details, String deliveryId) {
		JSONObject object = new JSONObject();
		try {
			object.put("id", purId);
			object.put("purchasename", details.get(0).getText().toString()
					.trim());
			object.put(
					"amount",
					Utils.getNumberOfString(details.get(1).getText().toString()
							.trim()));// 数量
			object.put(
					"price",
					Utils.getNumberOfString(details.get(2).getText().toString()
							.trim()));// 价钱
			object.put(
					"prepayment",
					Utils.getNumberOfString(details.get(3).getText().toString()
							.trim()));// 预付费
			object.put(
					"purchasebegintime",
					Utils.getLongTime1(details.get(4).getText().toString()
							.trim()));// 开始时间
			object.put(
					"purchaseendtime",
					Utils.getLongTime1(details.get(5).getText().toString()
							.trim()));// 结束时间
			object.put("autobeginflag", "是".equals(details.get(6).getText()
					.toString().trim()) ? true : false);
			object.put("autoendflag", "是".equals(details.get(7).getText()
					.toString().trim()) ? true : false);
			object.put("deliveryplace", deliveryId);
			object.put("purchasedetail", details.get(9).getText().toString()
					.trim());// 详情
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return object;
	}

	/**
	 * 我是采购商--我的意向---编辑采购信息的数据
	 * 
	 * @param id
	 * @param details
	 * @param standards
	 * @param datas
	 * @param deliveryId
	 * @param brandId
	 * @param originplaceId
	 * @param categoryId
	 * @return
	 */
	public static String getMyIntentEditPostData(String intentId,
			ArrayList<TextView> details, ArrayList<TextView> standards,
			ArrayList<IntentionDetailStandardInfo> datas, String deliveryId) {

		JSONObject object = new JSONObject();

		try {
			object.put("id", intentId);
			object.put("purModel", getIntentionModelDetail(details, deliveryId));
			object.put("list", getStantardArray(standards, datas));

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return object.toString();
	}

	/**
	 * 采购Model编辑（新增）----有分类、品牌、产地
	 * 
	 * @param id
	 * @param details
	 * @param deliveryId
	 * @param brandId
	 * @param originplaceId
	 * @param categoryId
	 * @return
	 */
	private static JSONObject getIntentionModelDetail(
			ArrayList<TextView> details, String deliveryId) {
		JSONObject object = new JSONObject();
		try {
			object.put("purchasename", details.get(0).getText().toString()
					.trim());

			object.put(
					"amount",
					Utils.getNumberOfString(details.get(1).getText().toString()
							.trim()));// 数量
			object.put(
					"price",
					Utils.getNumberOfString(details.get(2).getText().toString()
							.trim()));// 价钱
			object.put(
					"prepayment",
					Utils.getNumberOfString(details.get(3).getText().toString()
							.trim()));// 预付费
			object.put("deliveryplace", deliveryId);
			object.put("purchasedetail", details.get(5).getText().toString()
					.trim());// 详情
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return object;
	}

	/**
	 * 规格数据
	 * 
	 * @param standards
	 * @param datas
	 * @return
	 */
	private static JSONArray getStantardArray(ArrayList<TextView> standards,
			ArrayList<IntentionDetailStandardInfo> datas) {
		JSONArray array = new JSONArray();
		try {
			for (int i = 0; i < standards.size(); i++) {
				JSONObject object = new JSONObject();
				IntentionDetailStandardInfo info = datas.get(i);
				object.put("id", info.getId());
				object.put("propertyid", info.getPropertyid());
				object.put(
						"propertyvalue",
						String.valueOf(standards.get(i).getText().toString()
								.trim()));

				array.put(object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}
}
