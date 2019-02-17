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
 * ί�������Ĺ�����
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-9 ����10:55:32
 */
public class UserLogisticsManager extends JsonParseBase {

	/**
	 * ��ȡί�������б�
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
					// �û�id
					info.setUserId(object.getString("userid"));
					// ��Ʒid
					info.setGoodSID(object.getString("id"));
					// ����
					info.setGoodSTitle(object.getString("title"));
					info.setState(object.getInt("state"));
					info.setStatename(object.getString("statename"));
					// ��˾����
					info.setGoodSMerchant(object.getString("companyname"));
					// ��ϵ��ʽ
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
	 * ��ȡί�вɹ��б�����
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
					// �û�id
					info.setUserId(object.getString("userid"));
					// ��Ʒid
					info.setGoodSID(object.getString("id"));
					// ����
					info.setGoodSTitle(object.getString("title"));
					info.setState(object.getInt("state"));
					info.setStatename(object.getString("statename"));
					// ��˾����
					info.setGoodSMerchant(object.getString("companyname"));
					// ��ϵ��ʽ
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
	 * ί�й�Ӧ�б�
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
					// ��ƷID
					info.setGoodSID(object.getString("id"));
					// ��˾ID
					info.setCompantId(object.getString("companyid"));
					// ����
					info.setGoodSTitle(object.getString("title"));
					info.setState(object.getInt("state"));
					info.setStatename(object.getString("statename"));
					// ��ϵ��
					info.setGoodSContactPerson(object.getString("contact"));
					// ��˾����
					info.setGoodSMerchant(object.getString("companyname"));
					// ��ϵ��ʽ
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
	 * ί�й�Ӧ����
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
					// ��Ʒ�ɣ�
					info.setGoodSID(object.getString("id"));
					// ״̬
					info.setStatename(object.getString("statename"));
					info.setState(object.getInt("state"));
					// ��Ʒ����
					info.setGoodSTitle(object.getString("title"));
					// ����
					info.setGoodSWeight(object.getString("quantity"));
					// ��ϵ��ʽ
					info.setGoodSContactWay(object.getString("mobile"));
					info.setGoodSContactPerson(object.getString("contact"));
					// ״̬
					info.setIntentionOrderState(object.getString("contact"));// ��ϵ��
					info.setGoodSMerchant(object.getString("companyname"));// ��˾����
					// Ʒ��
					info.setGoodSBrand(object.getString("brand"));

					info.setStartAddress(object.getString("origincityname"));// ʼ������
					info.setGoodSArea(object.getString("cityname"));// Ŀ�ĳ���
					
					// ��Ʒ�۸�
					info.setGoodSPrePrice(object.getString("price"));
					// ��˾�ɣ�
					info.setCompantId(object.getString("companyid"));
					
					info.setGoodSPutTime(Utils.getTimeYMD(object.getLong("createtime")));

					// ��ע
					info.setGoodSRemark(object.getString("remark"));
					// ��Ʒ����
					info.setGoodSCategory(object.getString("categoryname"));
					info.setGoodSCategoryNum(object.getString("category"));

					// �ָ��
					info.setGoodSDelivery(object.getString("deliverycityname"));
					// ��Ʒ����
					info.setGoodSContent(object.getString("details"));

					// ��ַ
					info.setAddress(object.getString("address"));

					// ����ID
					info.setOrigincityid(object.getString("origincityid"));
					// ����ID
					info.setCategoryID(object.getString("category"));
					// �����ID��
					info.setDeliverycityid(object.getString("deliverycityid"));
					// ����ID
					info.setCityid(object.getString("cityid"));

					
					// ����ʱ��
					long ceateTome = object.getLong("createtime");
					// ����ʱ��
					info.setGoodSPutTime(Utils.getTimeYMD(ceateTome));
					
					// ����ʱ��
					long time = object.getLong("endtime");
					info.setGoodSEndTime(Utils.getTimeYMD(time));
					// ��ʼʱ��
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
	 * �����༭�ҵ�ί��-�ҵĹ�Ӧ���༭��Ӧ����
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
	 * ����������Ϣʱ����ҪЯ��������
	 * 
	 * @param freightInfo
	 *            ���䷽ʽ
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
		// ��������
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
		// ��װ��ʽ
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
	 * �༭ί������-������ɰ�ť���ص�����
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
	 * ί�вɹ�-�ɹ�����Post����
	 * 
	 * @return
	 */
	public static String getLogisticsPurDetailPostData() {
		JSONObject object = new JSONObject();
		return object.toString();
	}

	/**
	 * ����ί�вɹ�-�ɹ�����
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
					// ״̬
					info.setIntentionOrderState(object.getString("statename"));
					info.setState(object.getInt("state"));

					info.setGoodSID(object.getString("id"));
					info.setGoodSTitle(object.getString("title"));

					info.setGoodSPrePrice(object.getString("price"));
					// ��ϵ��ʽ
					info.setGoodSContactWay(object.getString("mobile"));
					// ��ϵ��
					info.setGoodSContactPerson(object.getString("contact"));
					// ��˾����
					info.setGoodSMerchant(object.getString("companyname"));
					// Ʒ��
					info.setGoodSBrand(object.getString("brand") == null ? "" : object.getString("brand"));

					// ����
					info.setGoodSWeight(object.getString("quantity") == null ? "" : object.getString("quantity"));
					// ʼ����������
					info.setStartAddress(
							object.getString("originCityName") == null ? "" : object.getString("originCityName"));
					// ��Ʒ����
					info.setGoodSCategory(
							object.getString("categoryname") == null ? "" : object.getString("categoryname"));
					// ����
					info.setGoodSContent(object.getString("details") == null ? "" : object.getString("details"));
					// ��ַ
					info.setAddress(object.getString("address") == null ? "" : object.getString("address"));
					// �����
					info.setGoodSDelivery(
							object.getString("deliveryname") == null ? "" : object.getString("deliveryname"));

					// ��ע
					info.setGoodSRemark(object.getString("remark") == null ? "" : object.getString("remark"));
					// ��������
					info.setGoodSArea(object.getString("cityName") == null ? "" : object.getString("cityName"));
					// �û�ID
					info.setUserId(object.getString("userid"));
					// ����ID
					info.setOrigincityid(object.getString("origincityid"));
					// ����ID
					info.setCategoryID(object.getString("category"));
					// �����ID��
					info.setDeliverycityid(object.getString("deliverycityid"));
					// ����ID
					info.setCityid(object.getString("cityid"));
					// ����ʱ��
					info.setGoodSEndTime(Utils.getTimeYMD(object.getLong("endtime")));
					// ��ʼʱ��
					info.setGoodSStartTime(Utils.getTimeYMD(object.getLong("starttime")));
					// ����ʱ��
					info.setGoodSPutTime(Utils.getTimeYMD(object.getLong("createtime")));
				}
			}

			return info;
			
		} catch (Exception e) {
		}
		return info;
	}

	/**
	 * �ҵ�ί��-ί����������
	 * 
	 * @param arg1
	 * @return
	 */
	public static GoodsSourceInfo getLogisticsDetailsInfo(Object arg1) {
		Log.d("TAG", "--����--����--" + arg1.toString());

		JSONArray array = (JSONArray) arg1;
		GoodsSourceInfo info = new GoodsSourceInfo();

		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				for (int j = 0; j < jsonArray.length(); j++) {
					JSONObject object = jsonArray.getJSONObject(j);
					// ����
					info.setGoodSDetails(object.getString("details"));
					// ״̬
					info.setState(object.getInt("state"));
					info.setGoodSContactWay(object.getString("mobile"));
					info.setGoodSContactPerson(object.getString("contact"));
					info.setGoodSMerchant(object.getString("companyname"));
					info.setGoodSID(object.getString("id"));
					info.setTitle(object.getString("title"));

					info.setUserId(object.getString("userid"));
					// Ŀ�ĵ�ַ
					info.setGoodSEndCity(object.getString("destinationaddress"));
					// Ŀ�ĳ���ID
					info.setDestinationcityid(object.getString("destinationcityid"));

					info.setStartAddress(object.getString("startcityname"));
					info.setGoodSStartCity(object.getString("startaddress"));
					info.setStartcityid(object.getString("startcityid"));
					// ����ʱ��
					info.setGoodSPutTime(Utils.getTimeYMD(object.getLong("createtime")));

					// ��������
					info.setDeliverytype(object.getString("deliverytype"));
					// ������������
					info.setDeliverytypename(object.getString("deliverytypename"));
					// ��װ����
					info.setPacktype(object.getString("packtype"));
					// ��װ��������
					info.setPacktypename(object.getString("packtypename"));

					// Ŀ�ĳ��еĺ�������
					String nameString = object.getString("destinationcityname");
					info.setEndAddress(nameString);
					// ״̬����
					info.setStatename(object.getString("statename"));
					info.setGoodSWeight(object.getString("quantity"));

					// ��ʼʱ��
					long startTime = object.getLong("starttime");
					info.setGoodSStartTime(Utils.getTimeYMD(startTime));
					// ����ʱ��
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
