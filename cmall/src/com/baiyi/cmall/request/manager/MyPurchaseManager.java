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
 * ���ǲɹ��� ���ݽ���
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
	 * ��ȡ�ҵĲɹ��������б�
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
					 * �ɹ�������
					 */
					JSONArray purArray = dataObject
							.getJSONArray("userPurModel");
					JSONObject purObject = purArray.getJSONObject(0);

					GoodsSourceInfo puriInfo = new GoodsSourceInfo();

					puriInfo.setGoodSPurchaseOrderId((int) (purObject
							.getLong("id")));// �ɹ�ID
					puriInfo.setGoodSName(purObject.getString("purchasename"));// ����
					puriInfo.setGoodSCategory(purObject
							.getString("categoryName"));// ��������
					puriInfo.setGoodSCategoryNum(purObject
							.getString("category"));// �������ƺ���
					puriInfo.setGoodSPlace(purObject
							.getString("originPlaceName"));// ��������
					puriInfo.setOrigincityid(purObject.getString("originplace"));// �������ƺ���
					puriInfo.setGoodSWeight(purObject.getString("amount"));// ����
					puriInfo.setGoodSPrice(purObject.getString("price"));// ����۸񣨵��ۣ�
					puriInfo.setGoodSPrePrice(purObject.getString("prepayment"));// Ԥ����
					long time = purObject.getLong("purchasebegintime");// ����ʱ��
					puriInfo.setGoodSPutTime(Utils.getTimeYMD(time));
					puriInfo.setGoodSBrand(purObject.getString("brandName"));// Ʒ��
					puriInfo.setGoodSBrandNum(purObject.getLong("brand"));// Ʒ�ֺ�
					puriInfo.setGoodSDelivery(purObject
							.getString("deliveryPlaceName"));// �����
					puriInfo.setDeliverycityid(purObject
							.getString("deliveryplace"));// ����غ�
					puriInfo.setIntentionOrderState(purObject
							.getString("purchaseStateName"));// ״̬����
					puriInfo.setPubState(purObject.getInt("publicstate"));// ����״̬
					puriInfo.setPubStateName(purObject
							.getString("publicstatename"));// ����״̬����
					puriInfo.setGoodSRemark(purObject
							.getString("purchaseAudit"));// ��ע
					puriInfo.setIntentionstatename(purObject
							.getString("statename"));// �ɹ��б���ʾ��״̬����

					/*
					 * �ɹ��̶�Ӧ�Ĺ�Ӧ����Ϣ
					 */
					JSONArray offerArray = dataObject
							.getJSONArray("userOfferIntentionModelList");
					// ��Ӧ�Ĺ�Ӧ��
					ArrayList<GoodsSourceInfo> offerDatas = new ArrayList<GoodsSourceInfo>();
					for (int k = 0; k < offerArray.length(); k++) {

						JSONObject offerObject = offerArray.getJSONObject(k);

						GoodsSourceInfo offerInfo = new GoodsSourceInfo();
						offerInfo.setGoodSPurchaseOrderId(offerObject
								.getInt("id"));// ��Ӧ����ID
						offerInfo.setGoodSName(offerObject
								.getString("offername"));// ����
						offerInfo.setGoodSCompanyNmae(offerObject
								.getString("companyname"));// ��Ӧ������
						offerInfo.setKuCun(offerObject.getString("inventory"));// ���
						offerInfo.setIntentionOrderState(offerObject
								.getString("intentionstatename"));// ״̬
						offerInfo.setGoodSDelivery(offerObject
								.getString("deliveryplacename"));// ���������
						offerInfo.setGoodSPrice(offerObject.getString("price"));// ����۸񣨵��ۣ�
						offerInfo.setType(offerObject.getInt("intentiontype"));// ��������
						offerInfo
								.setState(offerObject.getInt("intentionstate"));// ����״̬
						offerInfo.setDeletebycompany(offerObject
								.getInt("deletebycompany"));// �̼�ɾ����־
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
	 * ��ȡ�ҵĲɹ��������б�����
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

					puriInfo.setGoodSPurchaseOrderId((int) (purObject.getLong("id")));// �ɹ�ID
					puriInfo.setGoodSName(purObject.getString("purchasename"));// ����
					puriInfo.setGoodSCategory(purObject.getString("categoryName"));// ��������
					puriInfo.setGoodSCategoryNum(purObject.getString("category"));// �������ƺ���
					puriInfo.setGoodSPlace(purObject.getString("originPlaceName"));// ��������
					puriInfo.setOrigincityid(purObject.getString("originplace"));// �������ƺ���
					puriInfo.setGoodSWeight(purObject.getString("amount"));// ����
					puriInfo.setGoodSPrice(purObject.getString("price"));// ����۸񣨵��ۣ�
					puriInfo.setGoodSPrePrice(purObject.getString("prepayment"));// Ԥ����
					long startTime = purObject.getLong("purchasebegintime");// ����ʱ��
					puriInfo.setGoodSPutTime(Utils.getTimeYMD(startTime));
					long endTime = purObject.getLong("purchaseendtime");// ����ʱ��
					puriInfo.setGoodSEndTime(Utils.getTimeYMD(endTime));
					puriInfo.setGoodSBrand(purObject.getString("brandName"));// Ʒ��
					puriInfo.setGoodSBrandNum(purObject.getLong("brand"));// Ʒ�ֺ�
					puriInfo.setGoodSDelivery(purObject.getString("deliveryPlaceName"));// �����
					puriInfo.setDeliverycityid(purObject.getString("deliveryplace"));// ����غ�
					puriInfo.setIntentionOrderState(purObject.getString("purchaseStateName"));// ״̬����
					puriInfo.setPubState(purObject.getInt("publicstate"));// ����״̬
					puriInfo.setPubStateName(purObject.getString("publicstatename"));// ����״̬����
					puriInfo.setGoodSRemark(purObject.getString("purchaseAudit"));// ��ע

				}

				/*
				 * �ɹ��̶�Ӧ�Ĺ�Ӧ����Ϣ
				 */
				JSONArray offerArray = dataObject.getJSONArray("userOfferIntentionModelList");
				// ��Ӧ�Ĺ�Ӧ��
				ArrayList<GoodsSourceInfo> offerDatas = new ArrayList<GoodsSourceInfo>();
				for (int k = 0; k < offerArray.length(); k++) {

					JSONObject offerObject = offerArray.getJSONObject(k);

					GoodsSourceInfo offerInfo = new GoodsSourceInfo();
					offerInfo.setGoodSPurchaseOrderId(offerObject.getInt("id"));// ��Ӧ����ID
					offerInfo.setGoodSName(offerObject.getString("offername"));// ����
					offerInfo.setGoodSCompanyNmae(offerObject.getString("companyname"));// ��Ӧ������
					offerInfo.setKuCun(offerObject.getString("inventory"));// ���
					offerInfo.setIntentionOrderState(offerObject.getString("intentionstatename"));// ״̬
					offerInfo.setGoodSDelivery(offerObject.getString("deliveryplacename"));// ���������
					offerInfo.setGoodSPrice(offerObject.getString("price"));// ����۸񣨵��ۣ�
					offerInfo.setType(offerObject.getInt("intentiontype"));// ��������
					offerInfo.setState(offerObject.getInt("intentionstate"));// ����״̬
					offerInfo.setDeletebycompany(offerObject.getInt("deletebycompany"));// ��Ӧ��ɾ����־
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
	 * ��ȡ�ҵĲɹ��������б�����
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
				 * ���
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
				 * ����
				 */
				JSONObject modelData = dataObject.getJSONObject("purModel");

				info.setGoodSID(modelData.getString("id"));// �ɹ�ID
				info.setGoodSName(modelData.getString("purchasename"));// ����
				info.setGoodSCategory(modelData.getString("categoryName"));// ����
				info.setCategoryID(modelData.getString("category"));// ����ID
				info.setGoodSPlace(modelData.getString("originPlaceName"));// ����
				info.setOrigincityid(modelData.getString("originplace"));// ����ID
				info.setGoodSBrand(modelData.getString("brandName"));// Ʒ��
				info.setBrandID(modelData.getString("brand"));// Ʒ��Id
				info.setGoodSWeight(modelData.getString("amount"));// ������������
				info.setGoodSpriceInterval(modelData.getString("price"));// ����۸񣨵��ۣ�
				info.setGoodSPrePrice(modelData.getString("prepayment"));// Ԥ����
				long publishTime = JsonParseBase.getLongNodeValue(modelData,
						"purchasebegintime");// ����ʱ��
				info.setGoodSPutTime(Utils.getTimeYMD(publishTime));
				long endTime = JsonParseBase.getLongNodeValue(modelData,
						"purchaseendtime");// ����ʱ��
				info.setGoodSEndTime(Utils.getTimeYMD(endTime));
				info.setAutoStart(JsonParseBase.getBooleanNodeValue(modelData,
						"autobeginflag"));// �����Զ���ʼ
				info.setAutoEnd(JsonParseBase.getBooleanNodeValue(modelData,
						"autoendflag"));// �����Զ�����
				info.setGoodSDelivery(modelData.getString("deliveryPlaceName"));// �����
				info.setDeliverycityid(modelData.getString("deliveryplace"));// �����ID
				info.setGoodSPurchaseContent(modelData
						.getString("purchasedetail"));// �ɹ�����

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return info;
	}

	/**
	 * �༭�ɹ�����
	 * 
	 * @param info
	 * @return
	 */
	public static String getEditPurDetailPostData(GoodsSourceInfo info) {

		StringBuilder builder = new StringBuilder();
		builder.append("purchasename").append("=").append("8u89yu");// ����
		builder.append("&");
		builder.append("category").append("=").append(info.getGoodSCategory());// ����
		builder.append("&");
		builder.append("originplace").append("=").append(info.getGoodSPlace());// ����
		builder.append("&");
		builder.append("brand").append("=").append(info.getGoodSBrand());// Ʒ��
		builder.append("&");
		builder.append("amount").append("=").append(info.getGoodSWeight());// ����
		builder.append("&");
		builder.append("price").append("=").append(info.getGoodSPrePrice());// �۸�
		builder.append("&");
		builder.append("prepayment").append("=")
				.append(info.getGoodSPrePrice());// Ԥ����
		builder.append("&");
		builder.append("purchasebegintime").append("=")
				.append(info.getGoodSPutTime());// ����ʱ��
		builder.append("&");
		builder.append("purchaseendtime").append("=").append(new Date());// ����ʱ��
		builder.append("&");
		builder.append("autobeginflag").append("=").append(true);// �����Զ���ʼ
		builder.append("&");
		builder.append("autoendflag").append("=").append(false);// �����Զ�����
		builder.append("&");
		builder.append("deliveryplace").append("=")
				.append(info.getGoodSDelivery());// �����
		builder.append("&");
		builder.append("purchasedetail").append("=")
				.append(info.getGoodSContent());// ��ϸ����

		return builder.toString();
	}

	/**
	 * ��Ӧ����
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
				 * ���
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
				 * ����
				 */
				JSONObject modelData = dataObject.getJSONObject("offer");

				info.setGoodSID(modelData.getString("id"));// ��ӦID
				info.setGoodSCategory(modelData.getString("categoryname"));// ����
				info.setGoodSDetails(modelData.getString("offerdetail"));// ��Ӧ����
				info.setGoodSpriceInterval(modelData.getString("price"));// ����۸񣨵��ۣ�
				info.setGoodSWeight(modelData.getString("inventory"));// ������������
				info.setGoodSArea(modelData.getString("originplacename"));// ����
				info.setGoodSPrePrice(modelData.getString("prepayment"));// Ԥ����
				info.setGoodSBrand(modelData.getString("brandname"));// Ʒ��
				info.setGoodSContent(modelData.getString("offername"));// ��ϸ����
				info.setGoodSDelivery(modelData.getString("deliveryplacename"));// �����

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		// TODO ����
		return info;
	}

	/**
	 * �ɹ�����
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
				 * ���
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
				 * ����
				 */
				JSONObject modelData = dataObject.getJSONObject("offerModel");

				info.setGoodSID(modelData.getString("id"));// ��ӦID
				info.setGoodSCategory(modelData.getString("categoryname"));// ����
				info.setGoodSDetails(modelData.getString("purchasedetail"));// �ɹ�����
				info.setGoodSpriceInterval(modelData.getString("price"));// ����۸񣨵��ۣ�
				info.setGoodSWeight(modelData.getString("amount"));// ������������
				info.setGoodSArea(modelData.getString("originplacename"));// ����
				info.setGoodSPrePrice(modelData.getString("prepayment"));// Ԥ����
				info.setGoodSBrand(modelData.getString("brandname"));// Ʒ��
				info.setGoodSContent(modelData.getString("purchasename"));// ��ϸ����
				info.setGoodSDelivery(modelData.getString("deliveryplacename"));// �����

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		// TODO ����
		return info;
	}

	/**
	 * ���ǲɹ���--�ҵĲɹ�---�༭�ɹ���Ϣ������
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
	 * �ɹ�Model�༭----�з��ࡢƷ�ơ�����
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
							.trim()));// ����
			object.put(
					"price",
					Utils.getNumberOfString(details.get(2).getText().toString()
							.trim()));// ��Ǯ
			object.put(
					"prepayment",
					Utils.getNumberOfString(details.get(3).getText().toString()
							.trim()));// Ԥ����
			object.put(
					"purchasebegintime",
					Utils.getLongTime1(details.get(4).getText().toString()
							.trim()));// ��ʼʱ��
			object.put(
					"purchaseendtime",
					Utils.getLongTime1(details.get(5).getText().toString()
							.trim()));// ����ʱ��
			object.put("autobeginflag", "��".equals(details.get(6).getText()
					.toString().trim()) ? true : false);
			object.put("autoendflag", "��".equals(details.get(7).getText()
					.toString().trim()) ? true : false);
			object.put("deliveryplace", deliveryId);
			object.put("purchasedetail", details.get(9).getText().toString()
					.trim());// ����
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return object;
	}

	/**
	 * ���ǲɹ���--�ҵ�����---�༭�ɹ���Ϣ������
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
	 * �ɹ�Model�༭��������----�з��ࡢƷ�ơ�����
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
							.trim()));// ����
			object.put(
					"price",
					Utils.getNumberOfString(details.get(2).getText().toString()
							.trim()));// ��Ǯ
			object.put(
					"prepayment",
					Utils.getNumberOfString(details.get(3).getText().toString()
							.trim()));// Ԥ����
			object.put("deliveryplace", deliveryId);
			object.put("purchasedetail", details.get(5).getText().toString()
					.trim());// ����
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return object;
	}

	/**
	 * �������
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
