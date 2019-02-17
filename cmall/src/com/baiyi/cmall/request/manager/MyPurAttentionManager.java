package com.baiyi.cmall.request.manager;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baiyi.cmall.activities.main.baseList.JsonUtil;
import com.baiyi.cmall.activities.main.total.TotalUtils;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.OrderEntity;
import com.baiyi.cmall.utils.JsonParseBase;
import com.baiyi.cmall.utils.Utils;

/**
 * 我是采购商——采购意向管理
 * 
 * @author lizl
 * 
 */
public class MyPurAttentionManager {

	/**
	 * 我是采购商--采购意向--收到的意向列表
	 */
	public static ArrayList<GoodsSourceInfo> getMyPurchaseAttentionList(JSONArray data) {
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
					GoodsSourceInfo info = new GoodsSourceInfo();

					info.setGoodSPurchaseOrderId((int) (dataObject.getLong("id")));// 意向ID
					info.setType(dataObject.getInt("intentiontype"));// 意向类型
					info.setState(dataObject.getInt("intentionstate"));// 意向状态
					info.setIntentionOrderState(dataObject.getString("intentionstatename"));// 意向状态名称
					info.setGoodSCompanyNmae(dataObject.getString("companyname"));// 公司名称
					info.setGoodSName(dataObject.getString("tarname"));// 名称
					info.setGoodSCategory(dataObject.getString("categoryname"));// 分类
					info.setGoodSBrand(dataObject.getString("brandname"));// 品牌
					info.setGoodSWeight(JsonParseBase.getStringNodeValue(dataObject, "taramount"));// 数量
					info.setGoodSpriceInterval(dataObject.getString("tarprice"));// 价格
					info.setDeletebycompany(dataObject.getInt("deletebycompany"));// 商家删除标志
					datas.add(info);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return datas;
	}

	/**
	 * 我是采购商——采购意向——详情
	 */
	public static GoodsSourceInfo getMyPurchaseAttentionDetail(JSONArray data) {
		GoodsSourceInfo info = new GoodsSourceInfo();
		GoodsSourceInfo purInfo = new GoodsSourceInfo();
		GoodsSourceInfo ProInfo = new GoodsSourceInfo();
		JSONArray array = (JSONArray) data;
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONObject dataObject = jsonObject.getJSONObject("data");

				/*
				 * 解析用于判断意向界面的状态按钮
				 */
				String binaryvalue = JsonParseBase.getStringNodeValue(dataObject, "binaryvalue");
				info.setBinaryvalue(binaryvalue);

				/*
				 * 解析用于判断意向界面的状态按钮
				 */
				String intentionStateName = JsonParseBase.getStringNodeValue(dataObject, "intentionstatename");
				info.setIntentionstatename(intentionStateName);

				/*
				 * 解析采购信息
				 */

				JSONObject purModelData = dataObject.getJSONObject("purModel");
				purInfo.setGoodSPurchaseOrderId(JsonParseBase.getIntNodeValue(purModelData, "id"));// 采购ID
				purInfo.setGoodSName(purModelData.getString("purchasename"));// 名称
				purInfo.setGoodSCategory(purModelData.getString("categoryname"));// 分类
				purInfo.setCategoryID(purModelData.getString("category"));// 分类Id
				purInfo.setGoodSPlace(purModelData.getString("originplacename"));// 产地
				purInfo.setOrigincityid(purModelData.getString("originplace"));// 产地Id
				purInfo.setGoodSBrand(purModelData.getString("brandname"));// 品牌
				purInfo.setBrandID(purModelData.getString("brand"));// 品牌ID

				purInfo.setGoodSWeight(JsonParseBase.getStringNodeValue(purModelData, "amount"));// 数量

				purInfo.setGoodSPrice(JsonParseBase.getStringNodeValue(purModelData, "price") + "");// 货物价格（单价）

				purInfo.setGoodSPrePrice(JsonParseBase.getStringNodeValue(purModelData, "prepayment") + "");// 预付费

				purInfo.setGoodSDelivery(purModelData.getString("deliveryplacename"));// 交割地
				purInfo.setGoodSPurchaseContent(purModelData.getString("purchasedetail"));// 详细内容
				purInfo.setDeliverycityid(purModelData.getString("deliveryplace"));// 交割地ID

				info.setPurSourceInfo(purInfo);

				/*
				 * 解析供应信息
				 */

				JSONObject proModelData = dataObject.getJSONObject("offerModel");
				ProInfo.setGoodSProviderOrderId(JsonParseBase.getIntNodeValue(proModelData, "id"));// 供应ID
				ProInfo.setGoodSName(proModelData.getString("offerName"));// 名称
				ProInfo.setGoodSCategory(proModelData.getString("categoryName"));// 分类
				ProInfo.setGoodSPlace(proModelData.getString("originPlaceName"));// 产地
				ProInfo.setGoodSBrand(proModelData.getString("brandName"));// 品牌
				ProInfo.setGoodSWeight(JsonParseBase.getStringNodeValue(proModelData, "inventory"));// 数量

				ProInfo.setGoodSPrice(JsonParseBase.getStringNodeValue(proModelData, "price") + "");// 货物价格（单价）

				ProInfo.setGoodSPrePrice(JsonParseBase.getStringNodeValue(proModelData, "prepayment") + "");// 预付费
				ProInfo.setGoodSDelivery(proModelData.getString("deliveryPlaceName"));// 交割地
				ProInfo.setGoodSPurchaseContent(proModelData.getString("offerDetail"));// 详细内容

				info.setProSourceInfo(ProInfo);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		// TODO 解析
		return info;
	}

	/**
	 * 我是采购商--采购意向--下单
	 * 
	 * @param data
	 * @return
	 */
	public static GoodsSourceInfo getMyPurXiaOrderData(JSONArray data) {

		GoodsSourceInfo info = new GoodsSourceInfo();
		// 发票内容数据集合
		ArrayList<OrderEntity> contextDatas = new ArrayList<OrderEntity>();
		// 发票类型数据集合
		ArrayList<OrderEntity> typeDatas = new ArrayList<OrderEntity>();

		JSONArray array = (JSONArray) data;
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONArray dataArray = jsonObject.getJSONArray("data");

				for (int j = 0; j < dataArray.length(); j++) {
					JSONObject dataObject = dataArray.getJSONObject(j);

					info.setCompanyId(dataObject.getString("companyid"));// 商家ID
					info.setGoodSMerchant(dataObject.getString("companyname"));// 公司名称
					info.setGoodSName(dataObject.getString("offerName"));// 商品名称
					info.setGoodSCategory(dataObject.getString("categoryName"));// 分类名称
					info.setGoodSBrand(dataObject.getString("brandname"));// 品牌
					info.setGoodSPrice(JsonParseBase.getDoubleNodeValue(dataObject, "price") + "");// 货物价格（单价）
					info.setKuCun(JsonParseBase.getIntNodeValue(dataObject, "inventory") + "");// 库存
					info.setGoodSPrePrice(JsonParseBase.getDoubleNodeValue(dataObject, "prepayment") + "");// 预付费
					// info.setGoodSAllMoney(dataObject.getString(""));//
					// TODO 资源总额

					/*
					 * 只显示默认的收货人信息
					 */
					JSONArray receiver = JsonParseBase.getArray(dataObject, "receiver");
					for (int k = 0; k < receiver.length(); k++) {

						JSONObject receiverObject = (JSONObject) receiver.get(k);
						if (receiverObject.getBoolean("isdefault")) {

							info.setGoodSContactPerson(receiverObject.getString("receivername"));// 联系人
							info.setCity(receiverObject.getString("cityname"));// 联系人城市
							info.setCityid(receiverObject.getString("cityid"));// 联系人城市Id
							info.setGoodSArea(receiverObject.getString("address"));// 联系地址
							info.setGoodSContactWay(receiverObject.getString("phone"));// 电话
							info.setReceiverId(receiverObject.getInt("id"));// 收货人ID
						}
					}
					/*
					 * 只显示默认的发票信息
					 */
					JSONArray invoice = JsonParseBase.getArray(dataObject, "invoice");
					for (int k = 0; k < invoice.length(); k++) {

						JSONObject invoiceObject = (JSONObject) invoice.get(k);
						if (invoiceObject.getBoolean("isdefault")) {

							info.setGoodSTitle(invoiceObject.getString("title"));// 发票抬头
							info.setGoodSContent(invoiceObject.getString("context"));// 发票内容
							info.setMoreType(invoiceObject.getString("typename"));// 类型名称
							info.setMoreTypeId(invoiceObject.getString("type"));// 类型名称Id
							info.setInVoiceId(JsonParseBase.getIntNodeValue(invoiceObject, "id"));// 发票ID
						}
					}

					/*
					 * 获取内容信息
					 */
					JSONArray dataContextList = JsonParseBase.getArray(dataObject, "contexttype");

					for (int k = 0; k < dataContextList.length(); k++) {

						JSONObject dataContextObject = dataContextList.getJSONObject(k);

						OrderEntity contextInfo = new OrderEntity();

						contextInfo.setId(dataContextObject.getString("id"));// 发票内容ID
						contextInfo.setContext(dataContextObject.getString("codeValue"));// 发票内容
						contextDatas.add(contextInfo);
					}
					info.setContextList(contextDatas);

					/*
					 * 获取类型信息
					 */
					JSONArray dataTypeList = JsonParseBase.getArray(dataObject, "type");

					for (int k = 0; k < dataTypeList.length(); k++) {

						JSONObject dataTypeObject = dataTypeList.getJSONObject(k);

						OrderEntity typeInfo = new OrderEntity();

						typeInfo.setId(dataTypeObject.getString("orderNo"));// 发票类型ID
						typeInfo.setInvoicetypename(dataTypeObject.getString("codeValue"));// 发票类型
						typeDatas.add(typeInfo);
					}
					info.setTypeList(typeDatas);

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		// TODO 解析
		return info;
	}

	/**
	 * 编辑采购意向信息
	 * 
	 * @param attentionID
	 * 
	 * @param info
	 * @return
	 */
	public static String getEditPurAttentionPostData(int attentionID, GoodsSourceInfo info) {

		JSONObject dataObject = new JSONObject();
		/*
		 * 拼接model
		 */
		JSONObject purModelObject = new JSONObject();
		try {
			purModelObject.put("purchasename", info.getGoodSName());
			purModelObject.put("amount", info.getGoodSWeight());
			purModelObject.put("price", info.getGoodSPrice());
			purModelObject.put("prepayment", info.getGoodSPrePrice());
			purModelObject.put("deliveryplace", info.getDeliverycityid());
			purModelObject.put("purchasedetail", info.getGoodSPurchaseContent());

			dataObject.put("id", attentionID);
			dataObject.put("purModel", purModelObject);
			dataObject.put("list", new JSONArray());// 拼接空的LIST

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return dataObject.toString();
	}

	/**
	 * 我是采购商——收货人信息列表信息
	 * 
	 * @param data
	 * @return
	 */
	public static ArrayList<OrderEntity> getConsigneeList(JSONArray data) {
		ArrayList<OrderEntity> datas = new ArrayList<OrderEntity>();
		JSONArray array = (JSONArray) data;
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONObject dataOBJ = jsonObject.getJSONObject("data");
				JSONArray dataArray = dataOBJ.getJSONArray("dataList");

				for (int j = 0; j < dataArray.length(); j++) {
					JSONObject dataObject = dataArray.getJSONObject(j);
					OrderEntity info = new OrderEntity();

					info.setId(dataObject.getString("id"));// 收货人ID
					info.setReceivername(dataObject.getString("receivername"));// 姓名
					info.setOrderCity(dataObject.getString("cityname"));// 城市
					info.setOrderCityId(dataObject.getString("cityid"));// 城市ID
					info.setAddress(dataObject.getString("address"));// 地址
					info.setPhone(dataObject.getString("phone"));// 电话
					info.setDefault(dataObject.getBoolean("isdefault"));// 是否默认

					datas.add(info);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return datas;
	}

	/**
	 * 我是采购商—— 发票信息列表信息
	 * 
	 * @param data
	 * @return
	 */
	public static OrderEntity getInvoiceList(JSONArray data) {
		ArrayList<OrderEntity> invoiceDatas = new ArrayList<OrderEntity>();
		ArrayList<OrderEntity> contextDatas = new ArrayList<OrderEntity>();
		ArrayList<OrderEntity> typeDatas = new ArrayList<OrderEntity>();
		OrderEntity allInfo = new OrderEntity();
		JSONArray array = (JSONArray) data;
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONObject jsonOBJ = jsonObject.getJSONObject("data");
				JSONArray dataArray = jsonOBJ.getJSONArray("dataList");

				for (int j = 0; j < dataArray.length(); j++) {
					JSONObject listObject = dataArray.getJSONObject(j);

					/*
					 * 获取发票列表的信息
					 */
					JSONArray dataInvoiceList = listObject.getJSONArray("invoiceList");

					for (int k = 0; k < dataInvoiceList.length(); k++) {

						JSONObject dataObject = dataInvoiceList.getJSONObject(k);

						OrderEntity info = new OrderEntity();

						info.setId(dataObject.getString("id"));// 发票ID
						info.setTitle(dataObject.getString("title"));// 发票抬头
						info.setContext(dataObject.getString("context"));// 发票内容
						info.setInvoicetypename(dataObject.getString("typename"));// 发票类型名称
						info.setInvoicetypeId(dataObject.getString("type"));// 发票类型ID
						info.setDefault(JsonParseBase.getBooleanNodeValue(dataObject, "isdefault"));// 是否默认
						invoiceDatas.add(info);
					}
					allInfo.setInvoiceList(invoiceDatas);
					/*
					 * 获取内容信息
					 */
					JSONArray dataContextList = listObject.getJSONArray("contexttype");

					for (int k = 0; k < dataContextList.length(); k++) {

						JSONObject dataObject = dataContextList.getJSONObject(k);

						OrderEntity info = new OrderEntity();

						info.setId(dataObject.getString("id"));// 发票内容ID
						info.setContext(dataObject.getString("codeValue"));// 发票内容
						contextDatas.add(info);
					}
					allInfo.setContextList(contextDatas);
					/*
					 * 获取类型信息
					 */
					JSONArray dataTypeList = listObject.getJSONArray("type");

					for (int k = 0; k < dataTypeList.length(); k++) {

						JSONObject dataObject = dataTypeList.getJSONObject(k);

						OrderEntity info = new OrderEntity();

						info.setId(dataObject.getString("orderNo"));// 发票类型ID
						info.setInvoicetypename(dataObject.getString("codeValue"));// 发票类型
						typeDatas.add(info);
					}
					allInfo.setMaxNum(listObject.getInt("max"));
					allInfo.setTypeList(typeDatas);

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return allInfo;
	}

	/**
	 * 提交订单信息
	 * 
	 * @param invoiceid
	 * @param receiverid
	 * @param intentionid
	 * @param string
	 * @param companyId
	 * @param userid
	 * @param string2
	 * @return
	 */
	public static String getCommitOrderPostData(String receiverName, String receiverCityId, String receiverCityName,
			String reciverAddress, String receiverPhone, String invoiceTypeId, String invoiceType, String invoiceTitle,
			String invoiceContext, int intentionid, String userid, String companyid, String prepayment,
			String resamount) {

		StringBuilder builder = new StringBuilder();
		builder.append("receivername").append("=").append(receiverName);// 收货人姓名
		builder.append("&");
		builder.append("cityid").append("=").append(receiverCityId);// 收货人城市ID
		builder.append("&");
		builder.append("cityname").append("=").append(receiverCityName);// 收货人城市
		builder.append("&");
		builder.append("reciveraddress").append("=").append(reciverAddress);// 收货人地址
		builder.append("&");
		builder.append("phone").append("=").append(receiverPhone);// 收货人手机号码
		builder.append("&");
		builder.append("invoicetype").append("=").append(invoiceTypeId);// 发票类型ID
		builder.append("&");
		builder.append("typename").append("=").append(invoiceType);// 发票类型
		builder.append("&");
		builder.append("invoicetitle").append("=").append(invoiceTitle);// 发票抬头
		builder.append("&");
		builder.append("invoicecontext").append("=").append(invoiceContext);// 发票内容
		builder.append("&");
		builder.append("intentionid").append("=").append(intentionid);// 采购ID
		builder.append("&");
		builder.append("userid").append("=").append(userid);// 用户ID
		builder.append("&");
		builder.append("companyid").append("=").append(companyid);// 商家ID
		builder.append("&");
		// TODO
		builder.append("resamount").append("=").append(resamount);// 总额
		builder.append("&");
		builder.append("prepayment").append("=").append(prepayment);// 预付

		return builder.toString();
	}

	/**
	 * 添加----收货人信息
	 * 
	 * @param userid
	 * @param receivername
	 * @param cityid
	 * @param address
	 * @param phone
	 * @param isdefault
	 * @return
	 */
	public static String getReceiverPostData(String userid, String receivername, String cityid, String address,
			String phone, boolean isdefault) {

		StringBuilder builder = new StringBuilder();
		builder.append("userid").append("=").append(userid);// 用户ID
		builder.append("&");
		builder.append("receivername").append("=").append(receivername);// 姓名
		builder.append("&");
		builder.append("cityid").append("=").append(cityid);// 城市
		builder.append("&");
		builder.append("address").append("=").append(address);// 地址
		builder.append("&");
		builder.append("phone").append("=").append(phone);// 电话
		builder.append("&");
		builder.append("isdefault").append("=").append(isdefault);// 是否默认

		return builder.toString();
	}

	/**
	 * 编辑----收货人信息
	 * 
	 * @param userid
	 * @param receivername
	 * @param cityid
	 * @param address
	 * @param phone
	 * @param isdefault
	 * @return
	 */
	public static String getEditReceiverPostData(String userid, String personId, String receivername, String cityid,
			String address, String phone, boolean isdefault) {

		StringBuilder builder = new StringBuilder();
		builder.append("userid").append("=").append(userid);// 用户ID
		builder.append("&");
		builder.append("id").append("=").append(personId);// 收货人ID
		builder.append("&");
		builder.append("receivername").append("=").append(receivername);// 姓名
		builder.append("&");
		builder.append("cityid").append("=").append(cityid);// 城市
		builder.append("&");
		builder.append("address").append("=").append(address);// 地址
		builder.append("&");
		builder.append("phone").append("=").append(phone);// 电话
		builder.append("&");
		builder.append("isdefault").append("=").append(isdefault);// 是否默认

		return builder.toString();
	}

	/**
	 * 添加----发票信息
	 * 
	 * @param contextString
	 * @param title
	 * @param typeId
	 * @param userId
	 * @param isDefault
	 * 
	 */
	public static String getAddInvoicePostData(String userId, String typeId, String title, String contextString,
			boolean isDefault) {

		StringBuilder builder = new StringBuilder();
		builder.append("userid").append("=").append(userId);// 用户ID
		builder.append("&");
		builder.append("type").append("=").append(typeId);// 类型
		builder.append("&");
		builder.append("title").append("=").append(title);// 抬头
		builder.append("&");
		builder.append("context").append("=").append(contextString);// 内容
		builder.append("&");
		builder.append("isdefault").append("=").append(isDefault);// 是否默认

		return builder.toString();
	}

	/**
	 * 编辑----发票信息
	 * 
	 * @param userId
	 * @param typeId
	 * @param title
	 * @param contextString
	 * @param invoiceId
	 * @param isDefault
	 * @return
	 */
	public static String getEditInvoicePostData(String userId, String typeId, String title, String contextString,
			String invoiceId, boolean isDefault) {

		StringBuilder builder = new StringBuilder();
		builder.append("id").append("=").append(invoiceId);// 发票ID
		builder.append("&");
		builder.append("userid").append("=").append(userId);// 用户ID
		builder.append("&");
		builder.append("type").append("=").append(typeId);// 类型
		builder.append("&");
		builder.append("title").append("=").append(title);// 抬头
		builder.append("&");
		builder.append("context").append("=").append(contextString);// 内容
		builder.append("&");
		builder.append("isdefault").append("=").append(isDefault);// 是否默认

		return builder.toString();
	}

}
