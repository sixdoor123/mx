package com.baiyi.cmall.request.manager;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baiyi.cmall.activities.main.total.TotalUtils;
import com.baiyi.cmall.entity.OrderEntity;

public class MyPurFormManager {

	/**
	 * 我是采购商——采购订单列表(意向)
	 * 
	 * @param data
	 * @return
	 */
	public static ArrayList<OrderEntity> getMyPurchaseIntentionFormList(
			JSONArray data) {
		ArrayList<OrderEntity> datas = new ArrayList<OrderEntity>();
		JSONArray array = (JSONArray) data;
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONObject jsonOBJ = jsonObject.getJSONObject("data");
				
				int total = jsonOBJ.getInt("total");
				TotalUtils.getIntence().setTotal(total);

				JSONArray dataArray = jsonOBJ.getJSONArray("dataList");

				for (int j = 0; j < dataArray.length(); j++) {
					JSONObject dataObject = dataArray.getJSONObject(j);
					OrderEntity info = new OrderEntity();

					info.setId(dataObject.getString("id"));// 订单ID
					info.setOrderName(dataObject.getString("ordername"));// 订单名称
					info.setPurName(dataObject.getString("purchasename"));// 名称
					info.setOrderState(dataObject.getInt("orderstate"));// 订单状态
					info.setOrderStateName(dataObject
							.getString("orderStateName"));// 订单状态名称
					info.setPrice(dataObject.getString("price"));// 价格
					info.setPrepayment(dataObject.getString("prepayment"));// 预付款
					info.setBrandname(dataObject.getString("brandname"));// 品牌
					info.setCategoryName(dataObject.getString("categoryName"));// 分类名称
					info.setInventory(dataObject.getString("amount"));// 数量
					info.setCompanyname(dataObject.getString("companyname"));

					datas.add(info);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return datas;
	}

	/**
	 * 我是采购商——采购订单列表(产品)
	 * 
	 * @param data
	 * @return
	 */
	public static ArrayList<OrderEntity> getMyPurchaseProductFormList(
			JSONArray data) {
		ArrayList<OrderEntity> datas = new ArrayList<OrderEntity>();
		JSONArray array = (JSONArray) data;
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONObject jsonOBJ = jsonObject.getJSONObject("data");
				
				int total = jsonOBJ.getInt("tt");
				TotalUtils.getIntence().setTotal(total);
				
				JSONArray dataArray = jsonOBJ.getJSONArray("li");

				for (int j = 0; j < dataArray.length(); j++) {
					JSONObject dataObject = dataArray.getJSONObject(j);
					OrderEntity info = new OrderEntity();

					info.setId(dataObject.getString("id"));// ID
					info.setCompanyname(dataObject.getString("bl"));// 订单所属公司名称
					info.setOrderStateName(dataObject.getString("os"));// 订单状态名称
					info.setOrderState(dataObject.getInt("oss"));// 订单状态
					info.setPrepayment(dataObject.getString("pr"));// 实付款

					JSONArray opilArray = dataObject.getJSONArray("opil");
					ArrayList<OrderEntity> opilList = new ArrayList<OrderEntity>();
					for (int k = 0; k < opilArray.length(); k++) {
						JSONObject opilOBJ = opilArray.getJSONObject(k);
						OrderEntity opilInfo = new OrderEntity();
						opilInfo.setId(opilOBJ.getString("id"));// 订单ID
						opilInfo.setPic(opilOBJ.getString("purl"));// 图片
						opilInfo.setProductName(opilOBJ.getString("pn"));// 产品名称
						opilInfo.setInventory(opilOBJ.getString("co"));// 数量
						opilInfo.setPrice(opilOBJ.getString("pr"));// 价格
						opilList.add(opilInfo);
					}

					info.setOpilList(opilList);
					datas.add(info);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return datas;
	}

	/**
	 * 我是采购商--采购订单--详情数据
	 * 
	 * @param data
	 * @return
	 */
	public static OrderEntity getMyPurProductFormDetail(JSONArray data) {
		// TODO Auto-generated method stub
		OrderEntity entity = new OrderEntity();
		JSONArray array = (JSONArray) data;
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONObject dataObject = jsonObject.getJSONObject("data");

				entity.setId(dataObject.getString("id"));// ID
				entity.setOrderNumber(dataObject.getString("oi"));// 订单编号
				entity.setCompanyid(dataObject.getString("ci"));// 商家ID
				/*
				 * 收货人信息
				 */
				entity.setReceivername(dataObject.getString("rp"));// 收货人名称
				entity.setOrderCity(dataObject.getString("dq"));// 地区
				 entity.setOrderCityId(dataObject.getString("ci"));// 地区ID
				entity.setAddress(dataObject.getString("ad"));// 地址
				entity.setPhone(dataObject.getString("ph"));// 电话
				/*
				 * 发票信息
				 */
				entity.setTitle(dataObject.getString("ii"));// 发票抬头
				entity.setContext(dataObject.getString("ic"));// 发票内容
				// entity.setInvoicetypeId(dataObject.getString("invoicetype"));//
				// 发票类型ID
				entity.setInvoicetypename(dataObject.getString("it"));// 发票类型名称

				entity.setCompanyname(dataObject.getString("cn"));// 商家名称
				entity.setBinaryvalue(dataObject.getString("os"));

				JSONArray pdmlArray = dataObject.getJSONArray("pdml");
				ArrayList<OrderEntity> pdmlList = new ArrayList<OrderEntity>();// 规格集合
				for (int j = 0; j < pdmlArray.length(); j++) {
					JSONObject pdmlObject = (JSONObject) pdmlArray.get(j);

					OrderEntity pdmlEntity = new OrderEntity();
					pdmlEntity.setId(pdmlObject.getString("id"));// ID
					pdmlEntity.setProductName(pdmlObject.getString("pn"));// 产品名称
					pdmlEntity.setCategoryName(pdmlObject.getString("bn"));// 别名
					pdmlEntity.setPic(pdmlObject.getString("purl"));// 产品图标
					pdmlEntity.setPrice(pdmlObject.getString("pr"));// 价格
					pdmlEntity.setInventory(pdmlObject.getString("co"));// 数量
					pdmlEntity.setAllMoney(pdmlObject.getString("am"));// 总价

					/*
					 * 规格
					 */
					JSONArray guigeList = pdmlObject.getJSONArray("ggl");
					String guige = "";
					for (int k = 0; k < guigeList.length(); k++) {
						guige += guigeList.get(k);
						if (k != guigeList.length() - 1) {
							guige += "/";
						}
					}
					pdmlEntity.setGuige(guige);// 规格
					pdmlList.add(pdmlEntity);
				}
				entity.setPdmlList(pdmlList);

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return entity;
	}

	/**
	 * 我是采购商--采购订单--详情数据
	 * 
	 * @param data
	 * @return
	 */
	public static OrderEntity getMyPurIntentionFormDetail(JSONArray data) {
		// TODO Auto-generated method stub
		OrderEntity entity = new OrderEntity();
		JSONArray array = (JSONArray) data;
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONArray dataArray = jsonObject.getJSONArray("data");

				for (int j = 0; j < dataArray.length(); j++) {
					JSONObject dataObject = dataArray.getJSONObject(j);

					entity.setId(dataObject.getString("id"));// ID
					entity.setOfferName(dataObject.getString("offerName"));// 名称
					entity.setCategoryName(dataObject.getString("categoryName"));// 分类名称
					entity.setBrandname(dataObject.getString("brandname"));// 品牌
					entity.setPrice(dataObject.getString("price"));// 价格
					entity.setInventory(dataObject.getString("inventory"));// 库存
					entity.setPrepayment(dataObject.getString("prepayment"));// 预付
					entity.setResamount(dataObject.getString("resamount"));// 资源总额
					entity.setReceivername(dataObject.getString("receivername"));// 收货人名称
					entity.setOrderCity(dataObject.getString("cityname"));// 地区
					entity.setOrderCityId(dataObject.getString("cityid"));// 地区ID
					entity.setAddress(dataObject.getString("receiveraddress"));// 地址
					entity.setPhone(dataObject.getString("phone"));// 电话
					entity.setTitle(dataObject.getString("title"));// 发票抬头
					entity.setContext(dataObject.getString("context"));// 发票内容
					entity.setInvoicetypename(dataObject
							.getString("invoicetypename"));// 发票类型名称
					entity.setInvoicetypeId(dataObject.getString("invoicetype"));// 发票类型ID
					entity.setCompanyname(dataObject.getString("companyname"));// 商家名称
					entity.setOrderNumber(dataObject.getString("id"));// 订单编号
					entity.setIntentionNumber(dataObject
							.getString("intentionid"));// 意向编号
					entity.setBinaryvalue(dataObject.getString("binaryvalue"));// 按钮状态值

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// TODO 解析
		return entity;
	}

	/**
	 * 我是采购商--提交订单返回的下单数据--详情数据
	 * 
	 * @param data
	 * @return
	 */
	public static OrderEntity getMyTiJiaoDetailData(JSONArray data) {
		// TODO Auto-generated method stub
		OrderEntity entity = new OrderEntity();
		JSONArray array = (JSONArray) data;
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONArray dataArray = jsonObject.getJSONArray("data");

				for (int j = 0; j < dataArray.length(); j++) {
					JSONObject dataObject = dataArray.getJSONObject(j);

					entity.setId(dataObject.getString("orderid"));// ID
					entity.setOfferName(dataObject.getString("offerName"));// 名称
					entity.setCategoryName(dataObject.getString("categoryName"));// 分类名称
					entity.setBrandname(dataObject.getString("brandName"));// 品牌
					entity.setPrepayment(dataObject.getString("prepayment"));// 预付
					entity.setResamount(dataObject.getString("resamount"));// 资源总额
					entity.setPrice(dataObject.getString("price"));// 单价
					entity.setInventory(dataObject.getString("inventory"));// 库存
					entity.setReceivername(dataObject.getString("receivername"));// 收货人名称
					entity.setOrderCity(dataObject.getString("cityname"));// 地区
					entity.setOrderCityId(dataObject.getString("cityid"));// 地区ID
					entity.setAddress(dataObject.getString("reciveraddress"));// 地址
					entity.setPhone(dataObject.getString("phone"));// 电话
					entity.setTitle(dataObject.getString("invoicetitle"));// 发票抬头
					entity.setContext(dataObject.getString("invoicecontext"));// 发票内容
					entity.setInvoicetypename(dataObject.getString("typename"));// 发票类型名称
					entity.setInvoicetypeId(dataObject.getString("invoicetype"));// 发票类型ID
					entity.setCompanyname(dataObject.getString("companyName"));// 商家名称
					entity.setOrderNumber(dataObject.getString("orderid"));// 订单编号
					entity.setIntentionNumber(dataObject
							.getString("intentionid"));// 意向编号
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// TODO 解析
		return entity;
	}

	/**
	 * 获取支付界面的数据，顺便改变发票、收货人信息
	 * 
	 * @param receiverName
	 * @param receiverCityId
	 * @param receiverCityName
	 * @param reciverAddress
	 * @param receiverPhone
	 * @param invoiceTypeId
	 * @param invoiceType
	 * @param invoiceTitle
	 * @param invoiceContext
	 * @return
	 */

	public static String getZhiFuData(String orderId, String receiverName,
			String receiverCityId, String receiverCityName,
			String reciverAddress, String receiverPhone, String invoiceTypeId,
			String invoiceType, String invoiceTitle, String invoiceContext) {
		StringBuilder builder = new StringBuilder();
		builder.append("id").append("=").append(orderId);
		builder.append("&");
		builder.append("receivername").append("=").append(receiverName);
		builder.append("&");
		builder.append("cityid").append("=").append(receiverCityId);
		builder.append("&");
		builder.append("cityname").append("=").append(receiverCityName);
		builder.append("&");
		builder.append("reciveraddress").append("=").append(reciverAddress);
		builder.append("&");
		builder.append("phone").append("=").append(receiverPhone);
		builder.append("&");
		builder.append("invoicetype").append("=").append(invoiceTypeId);
		builder.append("&");
		builder.append("typename").append("=").append(invoiceType);
		builder.append("&");
		builder.append("invoicetitle").append("=").append(invoiceTitle);
		builder.append("&");
		builder.append("invoicecontext").append("=").append(invoiceContext);

		return builder.toString();
	}
}
