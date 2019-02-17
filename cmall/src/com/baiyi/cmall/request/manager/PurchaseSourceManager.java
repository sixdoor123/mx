package com.baiyi.cmall.request.manager;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

import com.baiyi.cmall.activities.main.total.TotalUtils;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.IntentionDetailStandardInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.utils.Utils;

/**
 * �ɹ�������
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-8 ����2:16:38
 */
public class PurchaseSourceManager {

	/**
	 * ��ҳ|_�ɹ��б���Ϣ
	 * 
	 * @param result
	 * @return
	 */
	public static ArrayList<GoodsSourceInfo> getPurchaseListInfo(Object result) {

		ArrayList<GoodsSourceInfo> datas = new ArrayList<GoodsSourceInfo>();

		JSONArray array = (JSONArray) result;

		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONObject dataObject = jsonObject.getJSONObject("data");
				
				//����
				int total = dataObject.getInt("total");
				TotalUtils.getIntence().setTotal(total);
				
				JSONArray jsonArray = dataObject.getJSONArray("dataList");
				for (int j = 0; j < jsonArray.length(); j++) {
					JSONObject object = jsonArray.getJSONObject(j);
					GoodsSourceInfo info = new GoodsSourceInfo();
					info.setGoodSID(object.getString("id"));// �ɹ�ID
					info.setGoodSName(object.getString("purchasename"));// ����
					info.setGoodSPurchaseContent(object
							.getString("purchasedetail"));// �ɹ�����
					info.setGoodSCategory(object.getString("categoryName"));// ����
					info.setGoodSWeight(object.getString("amount"));// ������������
					info.setGoodSPrePrice(object.getString("price"));// ����۸񣨵��ۣ�
					long publishTime = object.getLong("purchasebegintime");// ����ʱ��
					info.setGoodSPutTime(Utils.getTimeYMD(publishTime));
					long endTime = object.getLong("purchaseendtime");// ��ֹʱ��
					info.setGoodSPutTime(Utils.getTimeYMD(endTime));
					info.setGoodSBrand(object.getString("brandName"));// Ʒ��

					datas.add(info);
				}
			}

			return datas;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * ��ҳ�����ɹ��б���Ŀ��������
	 */
	public static GoodsSourceInfo getPurchaseItemDetail(JSONArray data) {
		GoodsSourceInfo info = new GoodsSourceInfo();
		ArrayList<IntentionDetailStandardInfo> standDatas = new ArrayList<IntentionDetailStandardInfo>();
		JSONArray array = (JSONArray) data;
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jsonObject = array.getJSONObject(i);

				RequestNetResultInfo resultInfo = new RequestNetResultInfo();
				resultInfo.setMsg(jsonObject.getString("msg"));
				resultInfo.setStatus(jsonObject.getInt("status"));
				info.setResultInfo(resultInfo);

				JSONObject dataObject = jsonObject.getJSONObject("data");

				/**
				 * ���
				 */
				JSONArray standArray = dataObject.getJSONArray("resList");
				for (int j = 0; j < standArray.length(); j++) {
					JSONObject object = standArray.getJSONObject(j);
					IntentionDetailStandardInfo stardInfo = new IntentionDetailStandardInfo();
					stardInfo.setId(object.getString("id"));
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
				JSONObject modelData = dataObject.getJSONObject("purchase");

				info.setGoodSID(modelData.getString("id"));// �ɹ�ID
				info.setGoodSMerchant(modelData
						.getString("purchasecompanyname"));// �ɹ�������
				info.setGoodSName(modelData.getString("purchasename"));// ����
				info.setGoodSPurchaseContent(modelData
						.getString("purchasedetail"));// �ɹ�����
				info.setGoodSCategory(modelData.getString("categoryname"));// ����
				info.setGoodSWeight(modelData.getString("amount"));// ������������
				info.setGoodSpriceInterval(modelData.getString("price"));// ����۸񣨵��ۣ�
				info.setGoodSPrePrice(modelData.getString("prepayment"));// Ԥ����
				info.setGoodSArea(modelData.getString("originplacename"));// ����
				long publishTime = modelData.getLong("purchasebegintime");// ����ʱ��
				info.setGoodSPutTime(Utils.getTimeYMD(publishTime));
				info.setGoodSBrand(modelData.getString("brandname"));// Ʒ��
				info.setGoodSDelivery(modelData.getString("deliveryplacename"));// �����
				info.setIsfollow(modelData.getBoolean("isfollow"));// �Ƿ��ע
				info.setUserId(modelData.getString("userid"));// �û�ID

				info.setCityid(modelData.getString("cityid"));
				info.setCategoryID(modelData.getString("category"));
				info.setDeliverycityid(modelData.getString("deliveryplace"));
				info.setOrigincityid(modelData.getString("originplace"));
				info.setAddress(modelData.getString("address"));
				info.setCity(modelData.getString("cityname"));

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		// TODO ����
		return info;
	}

	/**
	 * �õ���ע�ɹ���ʾ��Post����
	 * 
	 * @return
	 */
	public static String getPurchaseAttentionPostData(String purID,
			String companyid) {

		StringBuilder builder = new StringBuilder();
		builder.append("targetid").append("=").append(purID);
		builder.append("&");
		builder.append("companyid").append("=").append(Utils.isStringEmpty(companyid) ? "0" : companyid);
		return builder.toString();
	}

	/**
	 * ��ҳ�����ɹ�����ί�й�Ӧ�����ɹ���
	 * 
	 * @param info
	 * @param providerName
	 * @param contactName
	 * @param contactNumber
	 * @param priceInfo
	 * @param purId
	 * @return
	 */
	public static String getDelegateSupplyPostData(String companyid,
			GoodsSourceInfo info, GoodsSourceInfo sourceInfo) {

		StringBuilder builder = new StringBuilder();
		if (!TextUtils.isEmpty(companyid) && !"null".equals(companyid)) {

			builder.append("companyid").append("=").append(companyid);
			builder.append("&");
			Log.d("TT", "��¼״̬�ύ����˾ID--" + companyid);
		}

		builder.append("deliverycityid")
				.append("=")
				.append(sourceInfo.getDeliverycityid() == null ? 0 : sourceInfo
						.getDeliverycityid()).append("&");
		builder.append("origincityid")
				.append("=")
				.append(info.getOrigincityid() == null ? 0 : info
						.getOrigincityid()).append("&");
		builder.append("category")
				.append("=")
				.append(info.getCategoryID() == null ? 0 : info.getCategoryID())
				.append("&");

		builder.append("cityid")
				.append("=")
				.append(sourceInfo.getCityid().equals("null") ? 0 : sourceInfo
						.getCityid()).append("&");
		builder.append("address").append("=").append(sourceInfo.getAddress())
				.append("&");

		builder.append("brand").append("=").append(info.getGoodSBrand())
				.append("&");
		builder.append("quantity").append("=")
				.append(sourceInfo.getGoodSWeight()).append("&");
		builder.append("price")
				.append("=")
				.append(sourceInfo.getGoodSpriceInterval() == null ? 0
						: sourceInfo.getGoodSpriceInterval()).append("&");
		builder.append("purchaseid").append("=").append(info.getGoodSID());
		builder.append("&");
		builder.append("title").append("=").append(info.getGoodSName());
		builder.append("&");
		builder.append("details").append("=")
				.append(sourceInfo.getGoodSPurchaseNeed());
		builder.append("&");
		builder.append("contact").append("=")
				.append(sourceInfo.getGoodSContactPerson()).append("&");
		builder.append("mobile").append("=")
				.append(sourceInfo.getGoodSContactWay()).append("&");
		builder.append("companyname").append("=")
				.append(sourceInfo.getGoodSMerchant());

		return builder.toString();
	}

}
