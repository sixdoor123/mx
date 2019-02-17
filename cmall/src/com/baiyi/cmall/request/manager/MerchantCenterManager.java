package com.baiyi.cmall.request.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.security.auth.PrivateCredentialPermission;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baiyi.cmall.activities.main.total.TotalUtils;
import com.baiyi.cmall.activities.user.merchant.provider.viewpager.ProDataUtils;
import com.baiyi.cmall.database.dao.GoodSSourceLoader;
import com.baiyi.cmall.entity.GoodSOriginInfo;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.IntentionDetailStandardInfo;
import com.baiyi.cmall.entity.IntentionProviderDetailInfo;
import com.baiyi.cmall.entity.IntentionPurchaseDetailInfo;
import com.baiyi.cmall.entity.NewsEntity;
import com.baiyi.cmall.entity.OrderEntity;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.entity.TransforData;
import com.baiyi.cmall.entity._public.BrandEntities;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.core.util.ContextUtil;

/**
 * 我是供应商
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-10 下午2:57:35
 */
public class MerchantCenterManager {

	/**
	 * 供应意向单列表
	 * 
	 * @param arg1
	 * @return
	 */
	public static GoodsSourceInfo getProviderIntentationOrderList(Context context, Object arg1) {
		ArrayList<GoodsSourceInfo> datas = new ArrayList<GoodsSourceInfo>();
		Log.d("TAG", "--getProviderIntentationOrderList--" + arg1.toString());
		JSONArray array = (JSONArray) arg1;

		GoodsSourceInfo sourceInfo = new GoodsSourceInfo();
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				
				RequestNetResultInfo resultInfo = new RequestNetResultInfo();
				resultInfo.setMsg(jsonObject.getString("msg"));
				resultInfo.setStatus(jsonObject.getInt("status"));
				sourceInfo.setResultInfo(resultInfo);

				JSONObject jsonArray = jsonObject.getJSONObject("data");
				
				//总数
				int total = jsonArray.getInt("total");
				TotalUtils.getIntence().setTotal(total);

				JSONArray dataList = jsonArray.getJSONArray("dataList");
				for (int j = 0; j < dataList.length(); j++) {

					JSONObject object = dataList.getJSONObject(j);
					GoodsSourceInfo info = new GoodsSourceInfo();
					info.setGoodSID(object.getString("id"));
					info.setIntentionOrderState(object.getString("intentionstatename"));
					info.setGoodSBrand(object.getString("brandname"));
					// 名称/名称
					info.setGoodSName(object.getString("tarname"));
					info.setGoodSCategory(object.getString("categoryname"));
					// 意向类型
					String initentionType = object.getString("intentiontype");
					info.setType(Integer.parseInt(initentionType));
					info.setState(object.getInt("intentionstate"));
					info.setDeletebyuser(object.getInt("deletebyuser"));

					info.setGoodSMerchant(object.getString("companyname"));
					info.setGoodSProviderOrderId(object.getInt("tarid"));

					info.setUserName(object.getString("userName"));

					String price = object.getString("tarprice");
					if ("null".equals(price)) {
						price = String.valueOf(0);
					}
					info.setGoodSPrePrice(price);

					String count = object.getString("taramount");
					if ("null".equals(count)) {
						count = String.valueOf(0);
					}
					info.setGoodSWeight(count);

					datas.add(info);
				}
				sourceInfo.setOfferInfos(datas);
			}

			return sourceInfo;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 获取每种状态意向单的列表
	 * 
	 * @param arg1
	 * @return
	 */
	public static GoodsSourceInfo getPreIntentationOrderList(Context context, Object arg1) {
		GoodsSourceInfo sourceInfo = new GoodsSourceInfo();
		ArrayList<GoodsSourceInfo> datas = new ArrayList<GoodsSourceInfo>();
		Log.d("TAG", "--商家-供应-" + arg1.toString());
		JSONArray array = (JSONArray) arg1;

		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);

				RequestNetResultInfo resultInfo = new RequestNetResultInfo();
				resultInfo.setMsg(jsonObject.getString("msg"));
				resultInfo.setStatus(jsonObject.getInt("status"));
				sourceInfo.setResultInfo(resultInfo);

				// 总数
				// int total = jsonArray.getInt("total");
				// XmlUtils.getInstence(context).savaXmlValue("mer_pro", total);
				JSONObject jsonArray = jsonObject.getJSONObject("data");
				JSONArray dataList = jsonArray.getJSONArray("dataList");
				for (int j = 0; j < dataList.length(); j++) {
					JSONObject object = dataList.getJSONObject(j);
					GoodsSourceInfo info = new GoodsSourceInfo();
					ArrayList<GoodsSourceInfo> purInfos = new ArrayList<GoodsSourceInfo>();

					// 采购列表
					JSONArray purList = object.getJSONArray("companyPurIntentionModelList");
					for (int k = 0; k < purList.length(); k++) {
						JSONObject purs = purList.getJSONObject(k);
						GoodsSourceInfo info2 = new GoodsSourceInfo();
						info2.setGoodSID(purs.getString("id"));
						// 名称
						info2.setPurchasename(purs.getString("purchasename"));
						// 采购商名称
						info2.setCompanyName(purs.getString("purcompanyname"));
						info2.setInventory(purs.getString("amount"));
						info2.setIntentionOrderState(purs.getString("intentionstatename"));
						info2.setIntentionstatename(purs.getString("intentionstatename"));
						info2.setDeliveryplacename(purs.getString("deliveryplacename"));
						info2.setPrice(purs.getString("price"));

						info2.setState(purs.getInt("intentionstate"));
						info2.setType(purs.getInt("intentiontype"));
						info2.setDeletebyuser(purs.getInt("deletebyuser"));

						purInfos.add(info2);
					}
					info.setPurInfos(purInfos);
					// 供应信息
					JSONObject pros = object.getJSONObject("companyOfferModel");
					// 发布时间
					long time = pros.getLong("offerBeginTime");
					info.setGoodSPutTime(Utils.getTimeYMD(time));

					info.setGoodSID(pros.getString("id"));
					info.setGoodSCategory(pros.getString("categoryName"));
					// 供应详情
					String offerDetails = pros.getString("offerDetail");
					info.setGoodSPurchaseContent(offerDetails);

					info.setGoodSPrePrice(pros.getString("price"));
					info.setGoodSWeight(pros.getString("inventory"));
					// 原产地
					info.setGoodSPlace(pros.getString("originPlaceName"));
					// 预付款
					info.setPrepayment(pros.getString("prepayment"));
					info.setGoodSMerchant(pros.getString("companyName"));
					info.setGoodSBrand(pros.getString("brandName"));
					// 商品名称
					info.setGoodSName(pros.getString("offerName"));
					info.setGoodSDelivery(pros.getString("deliveryPlaceName"));
					// 状态
					info.setState(pros.getInt("offerState"));
					info.setStatename(pros.getString("offerStateName"));
					// 发布状态
					info.setPublicstate(pros.getInt("publicstate"));
					info.setPublicstatename(pros.getString("publicstatename"));
					info.setGoodSRemark(pros.getString("offerAudit"));
					info.setStatename(pros.getString("statename"));

					datas.add(info);
				}
			}

			sourceInfo.setPurInfos(datas);
			return sourceInfo;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return sourceInfo;
	}

	/**
	 * 解析我的供应单列表
	 * 
	 * @param arg1
	 * @return
	 */
	public static ArrayList<GoodsSourceInfo> getgetMyProviderOrderListInfo(Context context, Object arg1) {
		ArrayList<GoodsSourceInfo> datas = new ArrayList<GoodsSourceInfo>();
		Log.d("TAG", "--getgetMyProviderOrderListInfo--" + arg1.toString());
		JSONArray array = (JSONArray) arg1;

		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONObject jsonArray = jsonObject.getJSONObject("data");

				JSONArray dataList = jsonArray.getJSONArray("dataList");
				for (int j = 0; j < dataList.length(); j++) {

					JSONObject object = dataList.getJSONObject(j);
					GoodsSourceInfo info = new GoodsSourceInfo();
					info.setGoodSProviderOrderId(object.getInt("id"));
					info.setGoodSName(object.getString("offername"));
					info.setGoodSBrand(object.getString("brandname"));
					info.setGoodSCategory(object.getString("categoryName"));
					info.setIntentionOrderState(object.getString("orderStateName"));
					info.setGoodSPrePrice(object.getString("price"));
					info.setOrderstate(object.getInt("orderstate"));
					// 预付 prepayment
					info.setPrepayment(object.getString("prepayment"));
					String email = object.getString("email");

					info.setGoodSWeight(object.getString("inventory"));

					info.setGoodSContactPerson(object.getString("username"));
					info.setGoodSContactWay(object.getString("mobile"));
					info.setAddress(object.getString("address"));

					datas.add(info);
				}
			}
			return datas;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public static String getProviderPostData() {
		JSONObject json = new JSONObject();
		return json.toString();
	}

	public static GoodsSourceInfo getMyProviderOrderSdetailsInfo(Object result) {

		JSONArray array = (JSONArray) result;
		GoodsSourceInfo info = new GoodsSourceInfo();
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				for (int j = 0; j < jsonArray.length(); j++) {
					JSONObject object = jsonArray.getJSONObject(j);
					info.setGoodSID(object.getString("id"));

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 我是供应商-我的供应-完成编辑-解析结果
	 * 
	 * @param arg1
	 * @return
	 */
	public static RequestNetResultInfo getgetMyProviderDetailsEditComplete(Object arg1) {
		JSONArray array = (JSONArray) arg1;
		RequestNetResultInfo info = new RequestNetResultInfo();
		Log.d("TAG", "--getgetMyProviderDetailsEditComplete--" + arg1.toString());

		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				info.setMsg(jsonObject.getString("msg"));
				info.setStatus(jsonObject.getInt("status"));
			}

			return info;

		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}

	/**
	 * 我是供应商-我的供应-编辑供应详情要携带的数据
	 * 
	 * @param details
	 *            修改后的详情实体集合
	 * @param hashMap
	 *            修改后的属性实体集合
	 * @param autoEnd
	 * @param autoStart
	 * @param arrayList
	 * @return
	 * 
	 * 		.append("").append("=") .append("{").append("}")
	 */
	public static String getMyProviderDetailEditArg(GoodsSourceInfo sourceInfo, ArrayList<TextView> details,
			HashMap<String, EditText> hashMap, HashMap<String, IntentionDetailStandardInfo> infos, SelectedInfo info,
			int state, String intentionID, int idState, String autoStart, String autoEnd) {
		JSONObject object = new JSONObject();

		try {
			if (1 == state) {
				object.put("id", intentionID);
			} else {
				object.put("id", JSONObject.NULL);
			}
			object.put("list", getStantardArray(hashMap, infos));
			object.put("offerModel",
					getOfferModelDetail(sourceInfo, details, info, state, idState, autoStart, autoEnd));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object.toString();
	}

	/**
	 * 完成的
	 * 
	 * @param id
	 * @param details
	 * @param info
	 * @param state
	 * @param idState
	 * @param autoEnd
	 * @param autoStart
	 * @return
	 */
	@SuppressWarnings("unused")
	private static JSONObject getOfferModelDetail(GoodsSourceInfo sourceInfo, ArrayList<TextView> details,
			SelectedInfo info, int state, int idState, String autoStart, String autoEnd) {
		JSONObject object = new JSONObject();
		try {

			if (3 == idState) {
				object.put("id", JSONObject.NULL);
			} else {
				object.put("id", sourceInfo.getGoodSID());
			}

			object.put("offername", ((EditText) details.get(0)).getText().toString().trim());
			object.put("inventory", Utils.getNumberOfString(details.get(4).getText().toString().trim()));
			object.put("price", Utils.getNumberOfString(details.get(5).getText().toString().trim()));
			object.put("prepayment", Utils.getNumberOfString(((EditText) details.get(6)).getText().toString().trim()));
			if (1 == state) {
				// object.put("category", sourceInfo.getGoodSCategoryNum());
				// object.put("brand", sourceInfo.getBrandID());
				// object.put("originplace", sourceInfo.getOrigincityid());
			}
			if (1 != state) {
				object.put("offerbegintime", Utils.getLongTime(details.get(7).getText().toString().trim()));
				object.put("offerendtime", Utils.getLongTime(details.get(8).getText().toString().trim()));
				// if (TextUtils.isEmpty(autoStart)) {
				object.put("autobeginflag", "是".equals(details.get(9).getText().toString().trim()) ? true : false);
				// }
				object.put("autoendflag", "是".equals(details.get(10).getText().toString().trim()) ? true : false);
			}
			if (null != info) {
				object.put("deliveryplace", info.getId());
				object.put("deliveryplacecode", info.getCm_categorycode());
			} else {
				object.put("deliveryplace", sourceInfo.getDeliverycityid());
				object.put("deliveryplacecode", sourceInfo.getDeliveryplacecode());
			}

			object.put("offerdetail", ((EditText) details.get(12)).getText().toString().trim());

			details.clear();

			return object;

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	@SuppressWarnings("unused")
	private static JSONArray getStantardArray(HashMap<String, EditText> map,
			HashMap<String, IntentionDetailStandardInfo> infos) {
		JSONArray array = new JSONArray();
		// HashMap<String, EditText> map = ProDataUtils.getMap();

		try {
			Set<String> keySet = map.keySet();
			Iterator<String> iterator = keySet.iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				JSONObject object = new JSONObject();
				IntentionDetailStandardInfo info = infos.get(key);

				object.put("id", info.getId());
				object.put("propertyid", info.getPropertyid());
				object.put("propertyvalue", map.get(key).getText().toString().trim());

				array.put(object);
			}

		} catch (Exception e) {
			return null;
		}
		return array;
	}

	/**
	 * 我是供应商-我的供应-采购商全部列表-供应详情
	 * 
	 * @param arg1
	 * @return
	 */
	public static GoodsSourceInfo getMyProviderMerchantList(Object arg1) {
		JSONArray array = (JSONArray) arg1;
		Log.d("TAG", "--getMyProviderMerchantList--" + arg1.toString());
		GoodsSourceInfo sourceInfo = new GoodsSourceInfo();

		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONObject dataJsonObject = jsonObject.getJSONObject("data");
				JSONObject detils = dataJsonObject.getJSONObject("companyOfferModel");

				// 提交时间
				long time = detils.getLong("createTime");
				sourceInfo.setGoodSCommitTime(Utils.getTimeYMD(time));
				sourceInfo.setGoodSContactWay(detils.getString("phone"));

				// 结束时间
				long date = detils.getLong("offerEndTime");
				sourceInfo.setGoodSEndTime(Utils.getTimeYMD(date));

				sourceInfo.setGoodSPriceWay(detils.getString("priceWayName"));
				sourceInfo.setGoodSMerchant(detils.getString("companyName"));
				sourceInfo.setGoodSContactPerson(detils.getString("contact"));
				sourceInfo.setGoodSBrand(detils.getString("brandName"));
				sourceInfo.setGoodSDelivery(detils.getString("deliveryPlaceName"));
				sourceInfo.setGoodSID(detils.getString("offerBeginTime"));
				sourceInfo.setGoodSCategory(detils.getString("categoryName"));
				sourceInfo.setGoodSDetails(detils.getString("offerDetail"));
				sourceInfo.setGoodSPrePrice(detils.getString("price"));
				sourceInfo.setGoodSWeight(detils.getString("inventory"));
				sourceInfo.setGoodSPlace(detils.getString("originPlaceName"));
				sourceInfo.setCompantId(detils.getString("companyId"));
				sourceInfo.setGoodSName(detils.getString("offerName"));
			}

			return sourceInfo;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 我是供应商-供应意向单-确认意向单
	 * 
	 * @param arg1
	 * @return
	 */
	public static GoodsSourceInfo getProviderIntentationSureInfo(Object arg1, int state) {
		JSONArray array = (JSONArray) arg1;
		GoodsSourceInfo sourceInfo = new GoodsSourceInfo();
		RequestNetResultInfo info = new RequestNetResultInfo();
		Log.d("TAG", "--getProviderIntentationSureInfo--" + arg1.toString());

		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				info.setMsg(object.getString("msg"));
				info.setStatus(object.getInt("status"));
				sourceInfo.setResultInfo(info);
				if (1 != state) {
					String data = object.getString("data");
					sourceInfo.setBinaryvalue(data);
				}
			}
			return sourceInfo;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 我是供应商-确认意向单
	 * 
	 * @param editInfo
	 *            详情信息
	 * @param info
	 *            列表传送
	 * @param weight
	 * @param sumMoney
	 * @param contact
	 * @param number
	 * @return
	 */
	public static String getSureIntentionOrderPostDates(SelectedInfo deliveryInfo, SelectedInfo areaInfo,
			BrandEntities brandInfo, SelectedInfo categoryInfo, IntentionProviderDetailInfo proinfo, String id,
			String weight, String proName, String inventory, String price, String prepayment, String detail) {

		JSONObject object = new JSONObject();
		try {
			object.put("id", id);
			object.put("list", new JSONArray());
			object.put("offerModel", getOfferModel(deliveryInfo, areaInfo, brandInfo, categoryInfo, proinfo, weight,
					proName, inventory, price, prepayment, detail));

		} catch (Exception e) {
		}
		return object.toString();
	}

	/**
	 * 确定按钮的
	 * 
	 * @param deliveryInfo
	 * @param areaInfo
	 * @param brandInfo
	 * @param categoryInfo
	 * @param proinfo
	 * @param weight
	 * @param proName
	 * @param inventory
	 * @param price
	 * @param prepayment
	 * @param detail
	 * @return
	 */
	private static JSONObject getOfferModel(SelectedInfo deliveryInfo, SelectedInfo areaInfo, BrandEntities brandInfo,
			SelectedInfo categoryInfo, IntentionProviderDetailInfo proinfo, String weight, String proName,
			String inventory, String price, String prepayment, String detail) {

		JSONObject object = new JSONObject();

		try {
			// object.put("id", proinfo.getId());

			object.put("offername", proName);
			if (null != categoryInfo) {
				object.put("category", categoryInfo.getId());
			} else {
				object.put("category", proinfo.getCategory());
			}
			if (null != areaInfo) {
				object.put("originplace", areaInfo.getId());
			} else {
				object.put("originplace", proinfo.getOriginplace());
			}
			if (null != brandInfo) {
				object.put("brand", brandInfo.getId());
			} else {
				object.put("brand", proinfo.getBrand());
			}
			if (null != deliveryInfo) {
				object.put("deliveryplace", deliveryInfo.getId());
				object.put("deliveryplacecode", deliveryInfo.getCm_categorycode());
			} else {
				object.put("deliveryplace", proinfo.getDeliveryplace());
				object.put("deliveryplacecode", proinfo.getDeliveryplacecode());
			}

			object.put("inventory", inventory);
			object.put("price", price);
			object.put("prepayment", prepayment);
			object.put("offerdetail", detail);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return object;
	}

	/**
	 * 供应商中心-我的的订单-订单详情
	 * 
	 * @param arg1
	 */
	public static OrderEntity getProviderOrderDetailsInfo(Object arg1) {
		Log.d("TAG", "--getProviderOrderDetailsInfo--" + arg1.toString());
		JSONArray array = (JSONArray) arg1;
		OrderEntity entity = new OrderEntity();

		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				for (int j = 0; j < jsonArray.length(); j++) {
					JSONObject object = jsonArray.getJSONObject(j);

					entity.setPhone(object.getString("phone"));
					// 收货人名称
					entity.setReceivername(object.getString("receivername"));
					// 品牌
					entity.setBrandname(object.getString("brandname"));
					// 订单ID
					entity.setId(object.getString("id"));
					// 分类
					entity.setCategoryName(object.getString("categoryName"));
					// 标题
					entity.setTitle(object.getString("title"));
					// 价格
					entity.setPrice(object.getString("price"));
					// 发票类型名称
					entity.setInvoicetypename(object.getString("invoicetypename"));
					// 地址
					entity.setAddress(object.getString("receiveraddress"));
					// 库存
					entity.setInventory(object.getString("inventory"));
					// 发票内容
					entity.setContext(object.getString("context"));
					entity.setEare(object.getString("cityname"));
					// 预付
					entity.setPrepayment(object.getString("prepayment"));
					// 名称
					entity.setOfferName(object.getString("offerName"));
					// 公司名称
					entity.setCompanyname(object.getString("purcompanyname"));
					entity.setOrderState(object.getInt("orderstate"));
					entity.setIntentionid(object.getString("intentionid"));
					// 资源总额
					entity.setResamount(object.getString("resamount"));
					entity.setBinaryvalue(object.getString("binaryvalue"));
				}
			}
			return entity;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}

	/**
	 * 我是供应商-我的订单-发货结果
	 * 
	 * @param arg1
	 * @return
	 */
	public static GoodsSourceInfo getProviderOrderSendGoodSInfo(Object arg1) {
		Log.d("TAG", "--getProviderOrderSendGoodSInfo--" + arg1.toString());
		JSONArray array = (JSONArray) arg1;
		GoodsSourceInfo sourceInfo = new GoodsSourceInfo();
		RequestNetResultInfo info = new RequestNetResultInfo();
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				info.setMsg(object.getString("msg"));
				info.setStatus(object.getInt("status"));
				sourceInfo.setResultInfo(info);

				String dataObject = object.getString("data");
				sourceInfo.setBinaryvalue(dataObject);
				// String orderState = dataObject.getString("orderstate");
				// if ("null".equals(orderState)) {
				// sourceInfo.setOrderstate(-9);
				// } else {
				// sourceInfo.setOrderstate(Integer.parseInt(orderState));
				// }
			}

			return sourceInfo;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 我是供应商-供应意向-详情（意向单） 包括属性
	 * 
	 * @param arg1
	 * @return
	 */
	public static IntentionProviderDetailInfo getProviderIntentationDetails(Object arg1) {

		Log.d("TAG", "--getProviderIntentationDetails--" + arg1.toString());
		JSONArray array = (JSONArray) arg1;
		// 存放供应信息属性的集合
		ArrayList<IntentionDetailStandardInfo> proStandardInfos = new ArrayList<IntentionDetailStandardInfo>();
		// 供应信息实体
		IntentionProviderDetailInfo proInfo = new IntentionProviderDetailInfo();
		// 采购信息实体
		IntentionPurchaseDetailInfo purInfo = new IntentionPurchaseDetailInfo();
		try {
			for (int i = 0; i < array.length(); i++) {

				JSONObject jsonObject = array.getJSONObject(i);

				RequestNetResultInfo resultInfo = new RequestNetResultInfo();
				resultInfo.setMsg(jsonObject.getString("msg"));
				resultInfo.setStatus(jsonObject.getInt("status"));
				proInfo.setResultInfo(resultInfo);

				JSONObject data = jsonObject.getJSONObject("data");

				// 得到供应信息
				JSONObject proObject = data.getJSONObject("offerModel");

				proInfo.setId(proObject.getString("id"));
				proInfo.setCategoryName(proObject.getString("categoryName"));
				proInfo.setOfferDetail(proObject.getString("offerDetail"));
				proInfo.setPrice(proObject.getString("price"));
				proInfo.setInventory(proObject.getString("inventory"));
				proInfo.setOriginPlaceName(proObject.getString("originPlaceName"));

				proInfo.setPrepayment(proObject.getString("prepayment"));
				proInfo.setBrandName(proObject.getString("brandName"));
				proInfo.setOfferName(proObject.getString("offerName"));
				proInfo.setDeliveryPlaceName(proObject.getString("deliveryPlaceName"));
				proInfo.setDeliveryplacecode(proObject.getString("deliveryplacecode"));

				// 获取ID
				proInfo.setCategory(proObject.getString("category"));
				proInfo.setBrand(proObject.getString("brand"));

				proInfo.setDeliveryplace(proObject.getString("deliveryplace"));
				proInfo.setOriginplace(proObject.getString("originplace"));

				// 得到targetId
				// String targetId = data.getString("targetId");
				// proInfo.setTargetId(targetId);
				proInfo.setBinaryvalue(data.getString("binaryvalue"));
				proInfo.setIntentionstatename(data.getString("intentionstatename"));

				// 得到采购信息purModel
				JSONObject purObject = data.getJSONObject("purModel");
				if (purObject != null || !"null".equals(purObject)) {
					purInfo.setAmount(purObject.getString("amount"));
					purInfo.setId(purObject.getString("id"));
					purInfo.setPrice(purObject.getString("price"));
					purInfo.setBrandname(purObject.getString("brandname"));
					purInfo.setPurchasename(purObject.getString("purchasename"));
					purInfo.setOriginplacename(purObject.getString("originplacename"));
					purInfo.setCategoryname(purObject.getString("categoryname"));
					purInfo.setPurchasedetail(purObject.getString("purchasedetail"));
					purInfo.setDeliveryplacename(purObject.getString("deliveryplacename"));
					purInfo.setPrepayment(purObject.getString("prepayment"));
					purInfo.setIntentionstatename(data.getString("intentionstatename"));
					// 加入
				} else {
					purInfo = null;
				}
				proInfo.setPurchaseDetailInfo(purInfo);

			}

			return proInfo;

		} catch (Exception e) {
			Log.d("TAG", "解析错误: " + e.getMessage());
			proInfo.setPurchaseDetailInfo(null);
		}
		return proInfo;
	}

	/**
	 * 我是供应商-供应意向-详情（意向单） 不包括属性
	 * 
	 * @param arg1
	 * @return
	 */
	public static IntentionProviderDetailInfo getProviderIntentationDetail(Object arg1) {

		Log.d("TAG", "--getProviderIntentationDetails--" + arg1.toString());
		JSONArray array = (JSONArray) arg1;
		// 供应信息实体
		IntentionProviderDetailInfo proInfo = new IntentionProviderDetailInfo();
		try {
			for (int i = 0; i < array.length(); i++) {

				JSONObject jsonObject = array.getJSONObject(i);

				RequestNetResultInfo resultInfo = new RequestNetResultInfo();
				resultInfo.setMsg(jsonObject.getString("msg"));
				resultInfo.setStatus(jsonObject.getInt("status"));
				proInfo.setResultInfo(resultInfo);

				JSONObject data = jsonObject.getJSONObject("data");
				// JSONObject resPurModel = data.getJSONObject("offerModel");
				// 采购你信息实体
				IntentionPurchaseDetailInfo purInfo = new IntentionPurchaseDetailInfo();

				// 得到采购信息
				JSONObject purObject = data.getJSONObject("purModel");
				purInfo.setAmount(purObject.getString("amount"));
				purInfo.setId(purObject.getString("id"));
				purInfo.setPrice(purObject.getString("price"));
				purInfo.setBrandname(purObject.getString("brandname"));
				purInfo.setPurchasename(purObject.getString("purchasename"));
				purInfo.setOriginplacename(purObject.getString("originplacename"));
				purInfo.setCategoryname(purObject.getString("categoryname"));
				purInfo.setPurchasedetail(purObject.getString("purchasedetail"));
				purInfo.setDeliveryplacename(purObject.getString("deliveryplacename"));
				purInfo.setPrepayment(purObject.getString("prepayment"));

				// 加入
				proInfo.setPurchaseDetailInfo(purInfo);

				// 得到供应信息
				JSONObject proObject = data.getJSONObject("offerModel");

				proInfo.setId(proObject.getString("id"));
				proInfo.setCategoryName(proObject.getString("categoryName"));
				proInfo.setOfferDetail(proObject.getString("offerDetail"));
				proInfo.setPrice(proObject.getString("price"));
				proInfo.setInventory(proObject.getString("inventory"));
				proInfo.setOriginPlaceName(proObject.getString("originPlaceName"));

				proInfo.setPrepayment(proObject.getString("prepayment"));
				proInfo.setBrandName(proObject.getString("brandName"));
				proInfo.setOfferName(proObject.getString("offerName"));
				proInfo.setDeliveryPlaceName(proObject.getString("deliveryPlaceName"));

			}

			return proInfo;

		} catch (Exception e) {
			Log.d("TAG", "解析错误: " + e.getMessage());
		}
		return null;
	}

	/**
	 * 我是供应商-供应意向单-详情-编辑供应详情 参数
	 * 
	 * @param info
	 * @return
	 */
	public static String getMyIntentionProviderDetailEditArg(IntentionProviderDetailInfo info) {

		StringBuilder builder = new StringBuilder();

		// builder.append("offerName").append("=").append(info.getOfferName())
		// .append("&");
		// builder.append("category").append("=").append(info.getCategoryName())
		// .append("&");
		// builder.append("originPlace").append("=")
		// .append(info.getOriginPlaceName()).append("&");
		// builder.append("brand").append("=").append(info.getBrandName())
		// .append("&");
		//
		// builder.append("prepayment").append("=").append(info.getPrepayment())
		// .append("&");

		// 数量（库存）
		builder.append("inventory").append("=").append(info.getInventory()).append("&");
		// 价格
		builder.append("price").append("=").append(info.getPrice()).append("&");

		// 交割地
		builder.append("deliveryPlace").append("=").append(12).append("&");

		builder.append("offerDetail").append("=").append(info.getOfferDetail()).append("&");
		builder.append("id").append("=").append(info.getId());

		Log.d("TAG", builder.toString());

		return builder.toString();
	}

	/**
	 * 我是供应商-我的供应-详情-关注商家
	 * 
	 * @param userid
	 * @param targeId
	 * @return
	 */
	public static String getIntentionMerchantPostData(String userid, String targeId) {
		StringBuilder builder = new StringBuilder();
		builder.append("userid").append("=").append(userid).append("&");
		builder.append("targetid").append("=").append(targeId);
		return builder.toString();
	}

	public static GoodsSourceInfo getUserPurIntentionDetails(Object arg1) {
		Log.d("TAG", "--getUserPurIntentionDetails--" + arg1.toString());
		GoodsSourceInfo info = new GoodsSourceInfo();

		JSONArray array = (JSONArray) arg1;
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONObject dataJsonObject = jsonObject.getJSONObject("data");
				ArrayList<IntentionDetailStandardInfo> standards = new ArrayList<IntentionDetailStandardInfo>();
				JSONArray resList = dataJsonObject.getJSONArray("resList");

				for (int j = 0; j < resList.length(); j++) {
					JSONObject object = resList.getJSONObject(j);

					IntentionDetailStandardInfo standardInfo = new IntentionDetailStandardInfo();

					standardInfo.setId(object.getString("id"));
					standardInfo.setPropertyvalue(object.getString("propertyvalue"));
					standardInfo.setPropertyname(object.getString("propertyname"));
					standardInfo.setPropertyunit(object.getString("propertyunit"));
					standardInfo.setPropertyid(object.getString("propertyid"));
					standardInfo.setCodevalue(object.getString("codevalue"));

					standards.add(standardInfo);
				}

				info.setStandardInfos(standards);

				JSONObject object = dataJsonObject.getJSONObject("purchase");

				info.setGoodSWeight(object.getString("amount"));
				info.setGoodSID(object.getString("id"));
				// 发布时间
				String purchasebegintime = object.getString("purchasebegintime");
				if (purchasebegintime == null || "null".equals(purchasebegintime)) {
					purchasebegintime = "0";
				}
				// long time = object.getLong(purchasebegintime);
				info.setGoodSPutTime(Utils.getTimeYMD(Long.parseLong(purchasebegintime)));
				info.setGoodSPrePrice(object.getString("price"));
				info.setGoodSBrand(object.getString("brandname"));
				// 名称
				info.setGoodSName(object.getString("purchasename"));
				info.setGoodSPlace(object.getString("originplacename"));
				info.setGoodSCategory(object.getString("categoryname"));
				info.setGoodSMerchant(object.getString("purchasecompanyname"));
				info.setGoodSDetails(object.getString("purchasedetail"));
				info.setGoodSDelivery(object.getString("deliveryplacename"));
				info.setPrepayment(object.getString("prepayment"));
			}

			return info;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	private static JSONObject jObject;

	public static void setjObject(JSONObject jObject) {
		MerchantCenterManager.jObject = jObject;
	}

	public static JSONObject getjObject() {
		return jObject;
	}

	/**
	 * 我是供应商-编辑-详情
	 * 
	 * @param arg1
	 * @return
	 */
	public static GoodsSourceInfo getEditMyProviderDetails(Object arg1) {
		Log.d("TAG", "--getEditMyProviderDetails--" + arg1.toString());

		GoodsSourceInfo info = new GoodsSourceInfo();
		JSONArray array = (JSONArray) arg1;
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);

				JSONObject dataJsonObject = jsonObject.getJSONObject("data");
				// setjObject(dataJsonObject);
				ArrayList<IntentionDetailStandardInfo> standards = new ArrayList<IntentionDetailStandardInfo>();

				JSONArray standArray = dataJsonObject.getJSONArray("list");
				for (int j = 0; j < standArray.length(); j++) {
					// 解析属性
					JSONObject object = standArray.getJSONObject(j);

					IntentionDetailStandardInfo standardInfo = new IntentionDetailStandardInfo();

					standardInfo.setId(object.getString("id"));
					standardInfo.setPropertyvalue(object.getString("propertyvalue"));
					standardInfo.setPropertyname(object.getString("propertyname"));
					standardInfo.setPropertyunit(object.getString("propertyunit"));
					standardInfo.setPropertyid(object.getString("propertyid"));
					standardInfo.setCodevalue(object.getString("codevalue"));

					standards.add(standardInfo);
				}

				info.setStandardInfos(standards);

				// 解析详情信息
				JSONObject object = dataJsonObject.getJSONObject("offerModel");
				// 发布时间
				String offerbegintime = object.getString("offerbegintime");
				if ("null".equals(offerbegintime) || offerbegintime == null) {
					offerbegintime = "0";
				}
				// long time = object.getLong("offerbegintime");
				info.setGoodSPutTime(Utils.getTimeYMD(Long.parseLong(offerbegintime)));
				String autoStart = object.getString("autobeginflag");
				if ("null".equals(autoStart) || autoStart == null) {
					autoStart = "true";
				}
				info.setAutoStart(Boolean.parseBoolean(autoStart));
				info.setGoodSBrand(object.getString("brandName"));
				info.setBrandID(object.getString("brand"));
				info.setDeliveryplacecode(object.getString("deliveryplacecode"));
				info.setGoodSDelivery(object.getString("deliveryPlaceName"));
				info.setDeliveryplacecode(object.getString("deliveryplacecode"));
				info.setDeliverycityid(object.getString("deliveryplace"));
				info.setGoodSID(object.getString("id"));
				info.setGoodSCategory(object.getString("categoryName"));
				info.setGoodSCategoryNum(object.getString("category"));
				info.setGoodSPrePrice(object.getString("price"));
				info.setGoodSInventory(object.getString("inventory"));
				info.setGoodSPlace(object.getString("originPlaceName"));
				info.setOrigincityid(object.getString("originplace"));
				info.setGoodSDetails(object.getString("offerdetail"));
				info.setPrepayment(object.getString("prepayment"));
				info.setGoodSName(object.getString("offername"));

				// 结束时间
				String offerendtime = object.getString("offerendtime");
				if ("null".equals(offerendtime) || offerendtime == null) {
					offerendtime = "0";
				}
				// long data = object.getLong("offerendtime");
				info.setGoodSEndTime(Utils.getTimeYMD(Long.parseLong(offerendtime)));

				String autoendflag = object.getString("autoendflag");
				if ("null".equals(autoendflag) || autoendflag == null) {
					autoendflag = "false";
				}
				info.setAutoEnd(Boolean.parseBoolean(autoendflag));
			}

			return info;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	public static ArrayList<String[]> getStandards(ArrayList<EditText> standards,
			ArrayList<IntentionDetailStandardInfo> datas) {
		ArrayList<String[]> strings = new ArrayList<String[]>();

		for (int i = 0; i < standards.size(); i++) {
			strings.add(new String[] { "propertyId", datas.get(i).getPropertyid() });
			strings.add(new String[] { "id", datas.get(i).getId() });
			strings.add(new String[] { "propertyValue",
					Utils.getNumberOfString(standards.get(i).getText().toString().trim()) });
		}

		return null;
	}

	/**
	 * 我是供应商-供应意向单-编辑供应详情要携带的数据
	 * 
	 * @param details
	 *            修改后的详情实体集合
	 * @param map
	 *            修改后的属性实体集合
	 * @param autoEnd
	 * @param autoStart
	 * @param arrayList
	 * @return
	 * 
	 * 		.append("").append("=") .append("{").append("}")
	 */
	public static String getIntentionProviderDetailEditArg(GoodsSourceInfo sourceInfo, ArrayList<TextView> details,
			HashMap<String, EditText> map, HashMap<String, IntentionDetailStandardInfo> infos, SelectedInfo info,
			int state, String intentionID, int idState, String autoStart, String autoEnd) {
		JSONObject object = new JSONObject();

		try {
			if (1 == state) {
				object.put("id", intentionID);
			} else {
				object.put("id", JSONObject.NULL);
			}
			object.put("list", getIntentionStantardArray(map, infos));
			object.put("offerModel",
					getIntentionOfferModelDetail(sourceInfo, details, info, state, idState, autoStart, autoEnd));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object.toString();
	}

	/**
	 * 我是供应商-供应意向单-编辑供供应的详情
	 * 
	 * @param id
	 * @param details
	 * @param info
	 * @param state
	 * @param idState
	 * @param autoEnd
	 * @param autoStart
	 * @return
	 */
	@SuppressWarnings("unused")
	private static JSONObject getIntentionOfferModelDetail(GoodsSourceInfo sourceInfo, ArrayList<TextView> details,
			SelectedInfo info, int state, int idState, String autoStart, String autoEnd) {
		JSONObject object = new JSONObject();
		try {

			if (3 == idState) {
				object.put("id", JSONObject.NULL);
			} else {
				object.put("id", sourceInfo.getGoodSID());
			}

			object.put("offername", ((EditText) details.get(0)).getText().toString().trim());
			object.put("inventory", Utils.getNumberOfString(details.get(4).getText().toString().trim()));
			object.put("price", Utils.getNumberOfString(details.get(5).getText().toString().trim()));
			object.put("prepayment", Utils.getNumberOfString(((EditText) details.get(6)).getText().toString().trim()));
			if (1 == state) {
				// object.put("category", sourceInfo.getGoodSCategoryNum());
				// object.put("brand", sourceInfo.getBrandID());
				// object.put("originplace", sourceInfo.getOrigincityid());
			}
			if (1 != state) {
				object.put("offerbegintime", Utils.getLongTime(details.get(7).getText().toString().trim()));
				object.put("offerendtime", Utils.getLongTime(details.get(8).getText().toString().trim()));
				// if (TextUtils.isEmpty(autoStart)) {
				object.put("autobeginflag", "是".equals(details.get(9).getText().toString().trim()) ? true : false);
				// }
				object.put("autoendflag", "是".equals(details.get(10).getText().toString().trim()) ? true : false);
			}
			if (null != info) {
				object.put("deliveryplace", info.getId());
				object.put("deliveryplacecode", info.getCm_categorycode());
			} else {
				object.put("deliveryplace", sourceInfo.getDeliverycityid());
				object.put("deliveryplacecode", sourceInfo.getDeliveryplacecode());
			}

			object.put("offerdetail", ((EditText) details.get(12)).getText().toString().trim());

			details.clear();

			return object;

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 我是供应商-供应意向单-编辑供供应的属性
	 * 
	 * @param map
	 * @param infos
	 * @return
	 */
	@SuppressWarnings("unused")
	private static JSONArray getIntentionStantardArray(HashMap<String, EditText> map,
			HashMap<String, IntentionDetailStandardInfo> infos) {
		JSONArray array = new JSONArray();
		// HashMap<String, EditText> map = ProDataUtils.getMap();
		try {
			Set<String> keySet = map.keySet();
			Iterator<String> iterator = keySet.iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				JSONObject object = new JSONObject();
				IntentionDetailStandardInfo info = infos.get(key);

				object.put("id", info.getId());
				object.put("propertyid", info.getPropertyid());
				object.put("propertyvalue", map.get(key).getText().toString().trim());

				array.put(object);
			}

		} catch (Exception e) {
			return null;
		}
		return array;
	}

}
