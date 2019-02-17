package com.baiyi.cmall.request.manager;

import java.util.ArrayList;

import javax.security.auth.PrivateCredentialPermission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.content.Context;
import android.util.Log;

import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.utils.JsonParseBase;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.utils.XmlUtils;

/**
 * 委托物流的管理类
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-9 上午10:55:32
 */
public class UserLogisticsManager extends JsonParseBase {

	/**
	 * 获取委托物流列表
	 * 
	 * @param arg1
	 * @return
	 */
	
	public static GoodsSourceInfo getLogisticsResultInfo(Object arg1) {
		ArrayList<GoodsSourceInfo> datas = new ArrayList<GoodsSourceInfo>();
		GoodsSourceInfo sourceInfo = new GoodsSourceInfo();
		Log.d("TAG", "---------getLogisticsResultInfo------------" + arg1.toString());

		JSONArray array = (JSONArray) arg1;
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);

				RequestNetResultInfo resultInfo = new RequestNetResultInfo();
				resultInfo.setMsg(jsonObject.getString("msg"));
				resultInfo.setStatus(jsonObject.getInt("status"));
				sourceInfo.setResultInfo(resultInfo);

				JSONObject jsonArray = jsonObject.getJSONObject("data");
				JSONArray dataList = jsonArray.getJSONArray("dataList");
				for (int j = 0; j < dataList.length(); j++) {
					JSONObject object = dataList.getJSONObject(j);
					GoodsSourceInfo info = new GoodsSourceInfo();
					// 用户id
					info.setUserId(object.getString("userid"));
					// 商品id
					info.setGoodSID(object.getString("id"));
					// 标题
					info.setGoodSTitle(object.getString("title"));
					info.setState(object.getInt("state"));
					info.setStatename(object.getString("statename"));
					// 公司名称
					info.setGoodSMerchant(object.getString("companyname"));
					// 联系方式
					info.setGoodSContactWay(object.getString("mobile"));
					info.setGoodSContactPerson(object.getString("contact"));

					datas.add(info);
				}
			}
			sourceInfo.setPurInfos(datas);
			return sourceInfo;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * 获取委托采购列表数据
	 * 
	 * @param arg1
	 * @return
	 */
	public static GoodsSourceInfo getLogisticsPurchaseResultInfo(Context context, Object arg1) {
		ArrayList<GoodsSourceInfo> datas = new ArrayList<GoodsSourceInfo>();
		GoodsSourceInfo sourceInfo = new GoodsSourceInfo();
		JSONArray array = (JSONArray) arg1;
		Log.d("TAG", "--getLogisticsPurchaseResultInfo--" + arg1.toString());

		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONObject jsonArray = jsonObject.getJSONObject("data");

				RequestNetResultInfo resultInfo = new RequestNetResultInfo();
				resultInfo.setMsg(jsonObject.getString("msg"));
				resultInfo.setStatus(jsonObject.getInt("status"));
				sourceInfo.setResultInfo(resultInfo);

				JSONArray dataList = jsonArray.getJSONArray("dataList");
				for (int j = 0; j < dataList.length(); j++) {
					JSONObject object = dataList.getJSONObject(j);
					GoodsSourceInfo info = new GoodsSourceInfo();
					// 用户id
					info.setUserId(object.getString("userid"));
					// 商品id
					info.setGoodSID(object.getString("id"));
					// 标题
					info.setGoodSTitle(object.getString("title"));
					info.setState(object.getInt("state"));
					info.setStatename(object.getString("statename"));
					// 公司名称
					info.setGoodSMerchant(object.getString("companyname"));
					// 联系方式
					info.setGoodSContactWay(object.getString("mobile"));
					info.setGoodSContactPerson(object.getString("contact"));

					datas.add(info);
				}
			}
			sourceInfo.setPurInfos(datas);
			return sourceInfo;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 委托供应列表
	 * 
	 * @param arg1
	 * @return
	 */
	public static GoodsSourceInfo getLogisticsProviderResultInfo(Object arg1) {
		ArrayList<GoodsSourceInfo> datas = new ArrayList<GoodsSourceInfo>();
		JSONArray array = (JSONArray) arg1;
		GoodsSourceInfo sourceInfo = new GoodsSourceInfo();
		Log.d("TAG", "--getLogisticsProviderResultInfo--" + arg1.toString());
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);

				RequestNetResultInfo resultInfo = new RequestNetResultInfo();
				resultInfo.setMsg(jsonObject.getString("msg"));
				resultInfo.setStatus(jsonObject.getInt("status"));
				sourceInfo.setResultInfo(resultInfo);

				JSONObject jsonArray = jsonObject.getJSONObject("data");
				JSONArray dataList = jsonArray.getJSONArray("dataList");
				for (int j = 0; j < dataList.length(); j++) {
					JSONObject object = dataList.getJSONObject(j);

					GoodsSourceInfo info = new GoodsSourceInfo();
					// 商品ID
					info.setGoodSID(object.getString("id"));
					// 公司ID
					info.setCompantId(object.getString("companyid"));
					// 标题
					info.setGoodSTitle(object.getString("title"));
					info.setState(object.getInt("state"));
					info.setStatename(object.getString("statename"));
					// 联系人
					info.setGoodSContactPerson(object.getString("contact"));
					// 公司名称
					info.setGoodSMerchant(object.getString("companyname"));
					// 联系方式
					info.setGoodSContactWay(object.getString("mobile"));

					datas.add(info);
				}
			}
			sourceInfo.setPurInfos(datas);
			return sourceInfo;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 委托供应详情
	 * 
	 * @param arg1
	 */
	public static GoodsSourceInfo getDelegationProDetailsResultInfo(Object arg1) {

		JSONArray array = (JSONArray) arg1;
		Log.d("TAG", "--getDelegationProDetailsResultInfo--" + arg1.toString());
		GoodsSourceInfo info = new GoodsSourceInfo();
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				for (int j = 0; j < jsonArray.length(); j++) {
					JSONObject object = jsonArray.getJSONObject(j);
					// 商品ＩＤ
					info.setGoodSID(object.getString("id"));
					// 状态
					info.setStatename(object.getString("statename"));
					info.setState(object.getInt("state"));
					// 商品标题
					info.setGoodSTitle(object.getString("title"));
					// 数量
					info.setGoodSWeight(object.getString("quantity"));
					// 联系方式
					info.setGoodSContactWay(object.getString("mobile"));
					info.setGoodSContactPerson(object.getString("contact"));
					// 状态
					info.setIntentionOrderState(object.getString("contact"));// 联系人
					info.setGoodSMerchant(object.getString("companyname"));// 公司名称
					// 品牌
					info.setGoodSBrand(object.getString("brand"));

					info.setStartAddress(object.getString("origincityname"));// 始发城市
					info.setGoodSArea(object.getString("cityname"));// 目的城市
					
					// 商品价格
					info.setGoodSPrePrice(object.getString("price"));
					// 公司ＩＤ
					info.setCompantId(object.getString("companyid"));
					
					info.setGoodSPutTime(Utils.getTimeYMD(object.getLong("createtime")));

					// 备注
					info.setGoodSRemark(object.getString("remark"));
					// 商品分类
					info.setGoodSCategory(object.getString("categoryname"));
					info.setGoodSCategoryNum(object.getString("category"));

					// 分割地
					info.setGoodSDelivery(object.getString("deliverycityname"));
					// 商品内容
					info.setGoodSContent(object.getString("details"));

					// 地址
					info.setAddress(object.getString("address"));

					// 产地ID
					info.setOrigincityid(object.getString("origincityid"));
					// 分类ID
					info.setCategoryID(object.getString("category"));
					// 交割地ID、
					info.setDeliverycityid(object.getString("deliverycityid"));
					// 城市ID
					info.setCityid(object.getString("cityid"));

					
					// 创建时间
					long ceateTome = object.getLong("createtime");
					// 发布时间
					info.setGoodSPutTime(Utils.getTimeYMD(ceateTome));
					
					// 结束时间
					long time = object.getLong("endtime");
					info.setGoodSEndTime(Utils.getTimeYMD(time));
					// 开始时间
					long date = object.getLong("starttime");
					info.setGoodSStartTime(Utils.getTimeYMD(date));

					return info;
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return info;
	}

	/**
	 * 解析编辑我的委托-我的供应—编辑供应详情
	 * 
	 * @param arg1
	 * @return
	 */
	public static RequestNetResultInfo getEditDelegationProDetailsResultInfo(Object arg1) {
		JSONArray array = (JSONArray) arg1;
		Log.d("TAG", "--getEditDelegationProDetailsResultInfo--" + arg1.toString());
		RequestNetResultInfo info = new RequestNetResultInfo();

		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				info.setMsg(object.getString("msg"));
				info.setStatus(object.getInt("status"));
			}
			return info;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return null;
	}

	/**
	 * 保存物流信息时，需要携带的数据
	 * 
	 * @param freightInfo
	 *            运输方式
	 * @param packInfo
	 * @param endCityInfo
	 * @param startCityInfo
	 * 
	 * @return
	 */
	public static String getEditLogisticsPostData(GoodsSourceInfo info, SelectedInfo freightInfo, SelectedInfo packInfo,
			GoodsSourceInfo sourceInfo, SelectedInfo startCityInfo, SelectedInfo endCityInfo) {

		StringBuilder builder = new StringBuilder();
		builder.append("id").append("=").append(info.getGoodSID()).append("&");
		builder.append("title").append("=").append(sourceInfo.getGoodSTitle()).append("&");
		builder.append("companyname").append("=").append(sourceInfo.getGoodSMerchant()).append("&");
		builder.append("contact").append("=").append(sourceInfo.getGoodSContactPerson()).append("&");
		builder.append("mobile").append("=").append(sourceInfo.getGoodSContactWay()).append("&");
		builder.append("details").append("=").append(sourceInfo.getGoodSContent()).append("&");

		if (null != startCityInfo) {
			String cityId = startCityInfo.getId();
			if (null == cityId || "null".equals(cityId)) {
				cityId = "0";
			}
			builder.append("startcityid").append("=").append(cityId).append("&");
		} else {
			String cityId = info.getStartcityid();
			if (null == cityId || "null".equals(cityId)) {
				cityId = "0";
			}
			builder.append("startcityid").append("=").append(cityId).append("&");
		}

		if (null != endCityInfo) {
			String cityId = endCityInfo.getId();
			if (null == cityId || "null".equals(cityId)) {
				cityId = "0";
			}
			builder.append("destinationcityid").append("=").append(cityId).append("&");
		} else {
			String cityId = info.getDestinationcityid();
			if (null == cityId || "null".equals(cityId)) {
				cityId = "0";
			}
			builder.append("destinationcityid").append("=").append(cityId).append("&");
		}
		builder.append("startaddress").append("=").append(sourceInfo.getStartAddress()).append("&");
		builder.append("destinationaddress").append("=").append(sourceInfo.getEndAddress()).append("&");

		builder.append("starttime").append("=").append(Utils.getLongTime(sourceInfo.getGoodSStartTime())).append("&");
		builder.append("destinationtime").append("=").append(Utils.getLongTime(sourceInfo.getGoodSEndTime()))
				.append("&");

		builder.append("quantity").append("=")
				.append(sourceInfo.getGoodSWeight() == null ? 0 : sourceInfo.getGoodSWeight()).append("&");
		// 货运类型
		if (null != freightInfo) {
			String result = freightInfo.getCm_categorycode();
			if (null == result || "null".equals(result)) {
				result = "0";
			}
			builder.append("deliverytype").append("=").append(result).append("&");
		} else {
			String result = info.getDeliverytype();
			if (null == result || "null".equals(result)) {
				result = "0";
			}
			builder.append("deliverytype").append("=").append(result).append("&");
		}
		// 包装方式
		if (null != packInfo) {
			String result = packInfo.getCm_categorycode();
			if (null == result || "null".equals(result)) {
				result = "0";
			}
			builder.append("packtype").append("=").append(result);
		} else {
			String result = info.getPacktype();
			if (null == result || "null".equals(result)) {
				result = "0";
			}
			builder.append("packtype").append("=").append(result);
		}

		return builder.toString();
	}

	/**
	 * 编辑委托物流-解析完成按钮返回的数据
	 * 
	 * @param arg1
	 * @return
	 */
	public static RequestNetResultInfo getEditLogisticsResultInfo(Object arg1) {
		JSONArray array = (JSONArray) arg1;
		Log.d("TAG", "--getEditLogisticsResultInfo--" + arg1.toString());
		RequestNetResultInfo info = new RequestNetResultInfo();
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				info.setMsg(object.getString("msg"));
				info.setStatus(object.getInt("status"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return info;
	}

	/**
	 * 委托采购-采购详情Post数据
	 * 
	 * @return
	 */
	public static String getLogisticsPurDetailPostData() {
		JSONObject object = new JSONObject();
		return object.toString();
	}

	/**
	 * 解析委托采购-采购详情
	 * 
	 * @param arg1
	 * @return
	 */
	public static GoodsSourceInfo getLogisticsPurchaseDetailInfo(Object arg1) {
		JSONArray array = (JSONArray) arg1;
		Log.d("TAG", "--getLogisticsPurchaseDetailInfo--" + arg1.toString());
		GoodsSourceInfo info = new GoodsSourceInfo();
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				for (int j = 0; j < jsonArray.length(); j++) {
					JSONObject object = jsonArray.getJSONObject(j);
					// 状态
					info.setIntentionOrderState(object.getString("statename"));
					info.setState(object.getInt("state"));

					info.setGoodSID(object.getString("id"));
					info.setGoodSTitle(object.getString("title"));

					info.setGoodSPrePrice(object.getString("price"));
					// 联系方式
					info.setGoodSContactWay(object.getString("mobile"));
					// 联系人
					info.setGoodSContactPerson(object.getString("contact"));
					// 公司名称
					info.setGoodSMerchant(object.getString("companyname"));
					// 品牌
					info.setGoodSBrand(object.getString("brand") == null ? "" : object.getString("brand"));

					// 数量
					info.setGoodSWeight(object.getString("quantity") == null ? "" : object.getString("quantity"));
					// 始发城市名字
					info.setStartAddress(
							object.getString("originCityName") == null ? "" : object.getString("originCityName"));
					// 商品分类
					info.setGoodSCategory(
							object.getString("categoryname") == null ? "" : object.getString("categoryname"));
					// 内容
					info.setGoodSContent(object.getString("details") == null ? "" : object.getString("details"));
					// 地址
					info.setAddress(object.getString("address") == null ? "" : object.getString("address"));
					// 交割地
					info.setGoodSDelivery(
							object.getString("deliveryname") == null ? "" : object.getString("deliveryname"));

					// 备注
					info.setGoodSRemark(object.getString("remark") == null ? "" : object.getString("remark"));
					// 城市名称
					info.setGoodSArea(object.getString("cityName") == null ? "" : object.getString("cityName"));
					// 用户ID
					info.setUserId(object.getString("userid"));
					// 产地ID
					info.setOrigincityid(object.getString("origincityid"));
					// 分类ID
					info.setCategoryID(object.getString("category"));
					// 交割地ID、
					info.setDeliverycityid(object.getString("deliverycityid"));
					// 城市ID
					info.setCityid(object.getString("cityid"));
					// 结束时间
					info.setGoodSEndTime(Utils.getTimeYMD(object.getLong("endtime")));
					// 开始时间
					info.setGoodSStartTime(Utils.getTimeYMD(object.getLong("starttime")));
					// 发布时间
					info.setGoodSPutTime(Utils.getTimeYMD(object.getLong("createtime")));
				}
			}

			return info;
			
		} catch (Exception e) {
		}
		return info;
	}

	/**
	 * 我的委托-委托物流详情
	 * 
	 * @param arg1
	 * @return
	 */
	public static GoodsSourceInfo getLogisticsDetailsInfo(Object arg1) {
		Log.d("TAG", "--物流--详情--" + arg1.toString());

		JSONArray array = (JSONArray) arg1;
		GoodsSourceInfo info = new GoodsSourceInfo();

		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				for (int j = 0; j < jsonArray.length(); j++) {
					JSONObject object = jsonArray.getJSONObject(j);
					// 详情
					info.setGoodSDetails(object.getString("details"));
					// 状态
					info.setState(object.getInt("state"));
					info.setGoodSContactWay(object.getString("mobile"));
					info.setGoodSContactPerson(object.getString("contact"));
					info.setGoodSMerchant(object.getString("companyname"));
					info.setGoodSID(object.getString("id"));
					info.setTitle(object.getString("title"));

					info.setUserId(object.getString("userid"));
					// 目的地址
					info.setGoodSEndCity(object.getString("destinationaddress"));
					// 目的城市ID
					info.setDestinationcityid(object.getString("destinationcityid"));

					info.setStartAddress(object.getString("startcityname"));
					info.setGoodSStartCity(object.getString("startaddress"));
					info.setStartcityid(object.getString("startcityid"));
					// 发布时间
					info.setGoodSPutTime(Utils.getTimeYMD(object.getLong("createtime")));

					// 运输类型
					info.setDeliverytype(object.getString("deliverytype"));
					// 运输类型名称
					info.setDeliverytypename(object.getString("deliverytypename"));
					// 包装类型
					info.setPacktype(object.getString("packtype"));
					// 包装类型名称
					info.setPacktypename(object.getString("packtypename"));

					// 目的城市的汉子名称
					String nameString = object.getString("destinationcityname");
					info.setEndAddress(nameString);
					// 状态名称
					info.setStatename(object.getString("statename"));
					info.setGoodSWeight(object.getString("quantity"));

					// 开始时间
					long startTime = object.getLong("starttime");
					info.setGoodSStartTime(Utils.getTimeYMD(startTime));
					// 结束时间
					long endTime = object.getLong("destinationtime");
					info.setGoodSEndTime(Utils.getTimeYMD(endTime));
				}
			}

			return info;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return info;
	}
}
