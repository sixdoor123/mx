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
 * ��Դ����������
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-8 ����10:56:58
 */
public class ProviderSourceManager {

	// ��¼����
	private static String argument;
	// ��ס��һ��������ֵ
	private static String s1 = "";
	// ��ס�ڶ���������ֵ
	private static String s2 = "";
	// ��ס������������ֵ
	private static String s3 = "";
	// ��ס���ĸ�������ֵ
	private static String s4 = "";
	private static StringBuilder b;

	/**
	 * ��ȡPOST��������
	 * 
	 * @param arg
	 *            ѡ��Ĳ���
	 * @param select
	 *            ��־λ-��¼����һ��pop��ѡ�� 1:���� 2:���� 3:Ĭ������ 0:ȫ��
	 * @param pageSize
	 * @param pageIndex
	 * @return
	 */
	public static String getPostData(String arg, int select, int pageIndex,
			int pageSize) {
		argument = "";
		b = new StringBuilder();
		// ҳ��
		b.append("&").append("pageIndex").append("=").append(pageIndex);
		// ÿҳ��ʾ��������
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
	 * ������Ӧ�б�ķ���(��ʱ����)
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
					// ����ʱ��
					long time = object.getLong("offerBeginTime");
					info.setGoodSPutTime(Utils.getTimeYMD(time));
					// ��Ʒ����
					info.setGoodSName(object.getString("offerName"));
					// ��Ʒid
					info.setGoodSID(object.getString("id"));
					// ��Ʒ����
					info.setGoodSCategory(object.getString("categoryName"));
					// ��Ӧ��ϸ��
					info.setGoodSDetails(object.getString("offerDetail"));
					// ����۸�
					info.setGoodSPrePrice(object.getString("price"));
					// ��˾����
					info.setGoodSMerchant(object.getString("companyName"));
					// �����Ʒ��
					info.setGoodSBreed(object.getString("brandName"));

					datas.add(info);
				}
			}

			return datas;

		} catch (JSONException e) {
			Log.e("TAG", "��Դ����������JSONException----------error--------------");
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
				// ��Դid
				info.setGoodSID(object.getString("id"));
				// ��Ʒ����
				info.setGoodSName(object.getString("offerName"));
				// ��Ʒ����
				info.setGoodSCategory(object.getString("categoryName"));
				// ��Ʒ����
				info.setGoodSPlace(object.getString("originPlaceName"));
				// ��˾����
				info.setGoodSMerchant(object.getString("companyName"));
				// ��������
				info.setGoodSWeight(object.getString("inventory"));
				// ����۸�
				info.setGoodSPrePrice(object.getString("price"));
				// �����
				info.setGoodSDelivery(object.getString("deliveryPlaceName"));

				// �۸�ʽ
				info.setGoodSPriceWay(object.getString("priceWayName"));
				// ���ֹ��
				// String s1 = object.getString("codevalue");
				// String s2 = object.getString("propertyname");
				// String s3 = object.getString("propertyvalue");
				// info.setGoodSSpecification("("+s1+"/"+s2+"/"+s3+")");

				// ����ʱ��
				long time = object.getLong("offerBeginTime");
				info.setGoodSPutTime(Utils.getTimeYMD(time));

				// info.setGoodSArea(object.getString("areaName"));
				// �ύʱ��
				long commTime = object.getLong("createTime");
				info.setGoodSCommitTime(Utils.getTimeYMD(commTime));
				// �����Ʒ��
				info.setGoodSBreed(object.getString("brandName"));
				// ��ϵ��
				info.setGoodSContactPerson(object.getString("contact"));
				// ��ϵ��ʽ
				info.setGoodSContactWay(object.getString("phone"));
			}

			return info;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��ȡ��Ӧ���������Post����
	 * 
	 * @return
	 */
	public static String getGoodSDetailPostData() {
		JSONObject json = new JSONObject();

		return json.toString();
	}

	/**
	 * �����ύί�вɹ����صĽ��
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
	 * �ύί�вɹ���ҪЯ���Ĳ���
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
	 * ��Ӧ-�����עЯ��������
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
	 * ���������ע���ؽ��
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
				// ���ص���Ϣ
				info.setMsg(jsonObject.getString("msg"));
				// ���ص�״̬��
				info.setStatus(jsonObject.getInt("status"));
			}
		} catch (Exception e) {
			Log.d("TAG", e.toString());
		}
		return info;
	}

	/**
	 * ��ȡ��Ӧ�������������
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
				// �ύʱ��
				// long time = detils.getLong("createTime");
				// sourceInfo.setGoodSCommitTime(Utils.getTimeYMD(time));
				// sourceInfo.setGoodSContactWay(detils.getString("phone"));
				sourceInfo.setGoodSName(detils.getString("offername"));
				// ����ʱ��
				// long date = detils.getLong("offerEndTime");
				// sourceInfo.setGoodSEndTime(Utils.getTimeYMD(date));
				// ����ʱ��
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
	 * ���ǹ�Ӧ��-�ҵĹ�Ӧ-��ע�̼�
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
	 * ��ҳ��Դ�б� ������ѯ������Ϣ
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

				// ��������
				int total = data.getInt("total");
				TotalUtils.getIntence().setTotal(total);

				JSONArray tagList = data.getJSONArray("dataList");

				for (int j = 0; j < tagList.length(); j++) {
					JSONObject list = tagList.getJSONObject(j);

					GoodSOriginInfo info = new GoodSOriginInfo();
					// ��ʼʱ��
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
	 * ��ȡ��Ӧ�������������
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
