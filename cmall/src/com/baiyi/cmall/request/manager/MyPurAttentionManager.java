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
 * ���ǲɹ��̡����ɹ��������
 * 
 * @author lizl
 * 
 */
public class MyPurAttentionManager {

	/**
	 * ���ǲɹ���--�ɹ�����--�յ��������б�
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

					info.setGoodSPurchaseOrderId((int) (dataObject.getLong("id")));// ����ID
					info.setType(dataObject.getInt("intentiontype"));// ��������
					info.setState(dataObject.getInt("intentionstate"));// ����״̬
					info.setIntentionOrderState(dataObject.getString("intentionstatename"));// ����״̬����
					info.setGoodSCompanyNmae(dataObject.getString("companyname"));// ��˾����
					info.setGoodSName(dataObject.getString("tarname"));// ����
					info.setGoodSCategory(dataObject.getString("categoryname"));// ����
					info.setGoodSBrand(dataObject.getString("brandname"));// Ʒ��
					info.setGoodSWeight(JsonParseBase.getStringNodeValue(dataObject, "taramount"));// ����
					info.setGoodSpriceInterval(dataObject.getString("tarprice"));// �۸�
					info.setDeletebycompany(dataObject.getInt("deletebycompany"));// �̼�ɾ����־
					datas.add(info);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return datas;
	}

	/**
	 * ���ǲɹ��̡����ɹ����򡪡�����
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
				 * ���������ж���������״̬��ť
				 */
				String binaryvalue = JsonParseBase.getStringNodeValue(dataObject, "binaryvalue");
				info.setBinaryvalue(binaryvalue);

				/*
				 * ���������ж���������״̬��ť
				 */
				String intentionStateName = JsonParseBase.getStringNodeValue(dataObject, "intentionstatename");
				info.setIntentionstatename(intentionStateName);

				/*
				 * �����ɹ���Ϣ
				 */

				JSONObject purModelData = dataObject.getJSONObject("purModel");
				purInfo.setGoodSPurchaseOrderId(JsonParseBase.getIntNodeValue(purModelData, "id"));// �ɹ�ID
				purInfo.setGoodSName(purModelData.getString("purchasename"));// ����
				purInfo.setGoodSCategory(purModelData.getString("categoryname"));// ����
				purInfo.setCategoryID(purModelData.getString("category"));// ����Id
				purInfo.setGoodSPlace(purModelData.getString("originplacename"));// ����
				purInfo.setOrigincityid(purModelData.getString("originplace"));// ����Id
				purInfo.setGoodSBrand(purModelData.getString("brandname"));// Ʒ��
				purInfo.setBrandID(purModelData.getString("brand"));// Ʒ��ID

				purInfo.setGoodSWeight(JsonParseBase.getStringNodeValue(purModelData, "amount"));// ����

				purInfo.setGoodSPrice(JsonParseBase.getStringNodeValue(purModelData, "price") + "");// ����۸񣨵��ۣ�

				purInfo.setGoodSPrePrice(JsonParseBase.getStringNodeValue(purModelData, "prepayment") + "");// Ԥ����

				purInfo.setGoodSDelivery(purModelData.getString("deliveryplacename"));// �����
				purInfo.setGoodSPurchaseContent(purModelData.getString("purchasedetail"));// ��ϸ����
				purInfo.setDeliverycityid(purModelData.getString("deliveryplace"));// �����ID

				info.setPurSourceInfo(purInfo);

				/*
				 * ������Ӧ��Ϣ
				 */

				JSONObject proModelData = dataObject.getJSONObject("offerModel");
				ProInfo.setGoodSProviderOrderId(JsonParseBase.getIntNodeValue(proModelData, "id"));// ��ӦID
				ProInfo.setGoodSName(proModelData.getString("offerName"));// ����
				ProInfo.setGoodSCategory(proModelData.getString("categoryName"));// ����
				ProInfo.setGoodSPlace(proModelData.getString("originPlaceName"));// ����
				ProInfo.setGoodSBrand(proModelData.getString("brandName"));// Ʒ��
				ProInfo.setGoodSWeight(JsonParseBase.getStringNodeValue(proModelData, "inventory"));// ����

				ProInfo.setGoodSPrice(JsonParseBase.getStringNodeValue(proModelData, "price") + "");// ����۸񣨵��ۣ�

				ProInfo.setGoodSPrePrice(JsonParseBase.getStringNodeValue(proModelData, "prepayment") + "");// Ԥ����
				ProInfo.setGoodSDelivery(proModelData.getString("deliveryPlaceName"));// �����
				ProInfo.setGoodSPurchaseContent(proModelData.getString("offerDetail"));// ��ϸ����

				info.setProSourceInfo(ProInfo);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		// TODO ����
		return info;
	}

	/**
	 * ���ǲɹ���--�ɹ�����--�µ�
	 * 
	 * @param data
	 * @return
	 */
	public static GoodsSourceInfo getMyPurXiaOrderData(JSONArray data) {

		GoodsSourceInfo info = new GoodsSourceInfo();
		// ��Ʊ�������ݼ���
		ArrayList<OrderEntity> contextDatas = new ArrayList<OrderEntity>();
		// ��Ʊ�������ݼ���
		ArrayList<OrderEntity> typeDatas = new ArrayList<OrderEntity>();

		JSONArray array = (JSONArray) data;
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject jsonObject = array.getJSONObject(i);
				JSONArray dataArray = jsonObject.getJSONArray("data");

				for (int j = 0; j < dataArray.length(); j++) {
					JSONObject dataObject = dataArray.getJSONObject(j);

					info.setCompanyId(dataObject.getString("companyid"));// �̼�ID
					info.setGoodSMerchant(dataObject.getString("companyname"));// ��˾����
					info.setGoodSName(dataObject.getString("offerName"));// ��Ʒ����
					info.setGoodSCategory(dataObject.getString("categoryName"));// ��������
					info.setGoodSBrand(dataObject.getString("brandname"));// Ʒ��
					info.setGoodSPrice(JsonParseBase.getDoubleNodeValue(dataObject, "price") + "");// ����۸񣨵��ۣ�
					info.setKuCun(JsonParseBase.getIntNodeValue(dataObject, "inventory") + "");// ���
					info.setGoodSPrePrice(JsonParseBase.getDoubleNodeValue(dataObject, "prepayment") + "");// Ԥ����
					// info.setGoodSAllMoney(dataObject.getString(""));//
					// TODO ��Դ�ܶ�

					/*
					 * ֻ��ʾĬ�ϵ��ջ�����Ϣ
					 */
					JSONArray receiver = JsonParseBase.getArray(dataObject, "receiver");
					for (int k = 0; k < receiver.length(); k++) {

						JSONObject receiverObject = (JSONObject) receiver.get(k);
						if (receiverObject.getBoolean("isdefault")) {

							info.setGoodSContactPerson(receiverObject.getString("receivername"));// ��ϵ��
							info.setCity(receiverObject.getString("cityname"));// ��ϵ�˳���
							info.setCityid(receiverObject.getString("cityid"));// ��ϵ�˳���Id
							info.setGoodSArea(receiverObject.getString("address"));// ��ϵ��ַ
							info.setGoodSContactWay(receiverObject.getString("phone"));// �绰
							info.setReceiverId(receiverObject.getInt("id"));// �ջ���ID
						}
					}
					/*
					 * ֻ��ʾĬ�ϵķ�Ʊ��Ϣ
					 */
					JSONArray invoice = JsonParseBase.getArray(dataObject, "invoice");
					for (int k = 0; k < invoice.length(); k++) {

						JSONObject invoiceObject = (JSONObject) invoice.get(k);
						if (invoiceObject.getBoolean("isdefault")) {

							info.setGoodSTitle(invoiceObject.getString("title"));// ��Ʊ̧ͷ
							info.setGoodSContent(invoiceObject.getString("context"));// ��Ʊ����
							info.setMoreType(invoiceObject.getString("typename"));// ��������
							info.setMoreTypeId(invoiceObject.getString("type"));// ��������Id
							info.setInVoiceId(JsonParseBase.getIntNodeValue(invoiceObject, "id"));// ��ƱID
						}
					}

					/*
					 * ��ȡ������Ϣ
					 */
					JSONArray dataContextList = JsonParseBase.getArray(dataObject, "contexttype");

					for (int k = 0; k < dataContextList.length(); k++) {

						JSONObject dataContextObject = dataContextList.getJSONObject(k);

						OrderEntity contextInfo = new OrderEntity();

						contextInfo.setId(dataContextObject.getString("id"));// ��Ʊ����ID
						contextInfo.setContext(dataContextObject.getString("codeValue"));// ��Ʊ����
						contextDatas.add(contextInfo);
					}
					info.setContextList(contextDatas);

					/*
					 * ��ȡ������Ϣ
					 */
					JSONArray dataTypeList = JsonParseBase.getArray(dataObject, "type");

					for (int k = 0; k < dataTypeList.length(); k++) {

						JSONObject dataTypeObject = dataTypeList.getJSONObject(k);

						OrderEntity typeInfo = new OrderEntity();

						typeInfo.setId(dataTypeObject.getString("orderNo"));// ��Ʊ����ID
						typeInfo.setInvoicetypename(dataTypeObject.getString("codeValue"));// ��Ʊ����
						typeDatas.add(typeInfo);
					}
					info.setTypeList(typeDatas);

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		// TODO ����
		return info;
	}

	/**
	 * �༭�ɹ�������Ϣ
	 * 
	 * @param attentionID
	 * 
	 * @param info
	 * @return
	 */
	public static String getEditPurAttentionPostData(int attentionID, GoodsSourceInfo info) {

		JSONObject dataObject = new JSONObject();
		/*
		 * ƴ��model
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
			dataObject.put("list", new JSONArray());// ƴ�ӿյ�LIST

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return dataObject.toString();
	}

	/**
	 * ���ǲɹ��̡����ջ�����Ϣ�б���Ϣ
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

					info.setId(dataObject.getString("id"));// �ջ���ID
					info.setReceivername(dataObject.getString("receivername"));// ����
					info.setOrderCity(dataObject.getString("cityname"));// ����
					info.setOrderCityId(dataObject.getString("cityid"));// ����ID
					info.setAddress(dataObject.getString("address"));// ��ַ
					info.setPhone(dataObject.getString("phone"));// �绰
					info.setDefault(dataObject.getBoolean("isdefault"));// �Ƿ�Ĭ��

					datas.add(info);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return datas;
	}

	/**
	 * ���ǲɹ��̡��� ��Ʊ��Ϣ�б���Ϣ
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
					 * ��ȡ��Ʊ�б����Ϣ
					 */
					JSONArray dataInvoiceList = listObject.getJSONArray("invoiceList");

					for (int k = 0; k < dataInvoiceList.length(); k++) {

						JSONObject dataObject = dataInvoiceList.getJSONObject(k);

						OrderEntity info = new OrderEntity();

						info.setId(dataObject.getString("id"));// ��ƱID
						info.setTitle(dataObject.getString("title"));// ��Ʊ̧ͷ
						info.setContext(dataObject.getString("context"));// ��Ʊ����
						info.setInvoicetypename(dataObject.getString("typename"));// ��Ʊ��������
						info.setInvoicetypeId(dataObject.getString("type"));// ��Ʊ����ID
						info.setDefault(JsonParseBase.getBooleanNodeValue(dataObject, "isdefault"));// �Ƿ�Ĭ��
						invoiceDatas.add(info);
					}
					allInfo.setInvoiceList(invoiceDatas);
					/*
					 * ��ȡ������Ϣ
					 */
					JSONArray dataContextList = listObject.getJSONArray("contexttype");

					for (int k = 0; k < dataContextList.length(); k++) {

						JSONObject dataObject = dataContextList.getJSONObject(k);

						OrderEntity info = new OrderEntity();

						info.setId(dataObject.getString("id"));// ��Ʊ����ID
						info.setContext(dataObject.getString("codeValue"));// ��Ʊ����
						contextDatas.add(info);
					}
					allInfo.setContextList(contextDatas);
					/*
					 * ��ȡ������Ϣ
					 */
					JSONArray dataTypeList = listObject.getJSONArray("type");

					for (int k = 0; k < dataTypeList.length(); k++) {

						JSONObject dataObject = dataTypeList.getJSONObject(k);

						OrderEntity info = new OrderEntity();

						info.setId(dataObject.getString("orderNo"));// ��Ʊ����ID
						info.setInvoicetypename(dataObject.getString("codeValue"));// ��Ʊ����
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
	 * �ύ������Ϣ
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
		builder.append("receivername").append("=").append(receiverName);// �ջ�������
		builder.append("&");
		builder.append("cityid").append("=").append(receiverCityId);// �ջ��˳���ID
		builder.append("&");
		builder.append("cityname").append("=").append(receiverCityName);// �ջ��˳���
		builder.append("&");
		builder.append("reciveraddress").append("=").append(reciverAddress);// �ջ��˵�ַ
		builder.append("&");
		builder.append("phone").append("=").append(receiverPhone);// �ջ����ֻ�����
		builder.append("&");
		builder.append("invoicetype").append("=").append(invoiceTypeId);// ��Ʊ����ID
		builder.append("&");
		builder.append("typename").append("=").append(invoiceType);// ��Ʊ����
		builder.append("&");
		builder.append("invoicetitle").append("=").append(invoiceTitle);// ��Ʊ̧ͷ
		builder.append("&");
		builder.append("invoicecontext").append("=").append(invoiceContext);// ��Ʊ����
		builder.append("&");
		builder.append("intentionid").append("=").append(intentionid);// �ɹ�ID
		builder.append("&");
		builder.append("userid").append("=").append(userid);// �û�ID
		builder.append("&");
		builder.append("companyid").append("=").append(companyid);// �̼�ID
		builder.append("&");
		// TODO
		builder.append("resamount").append("=").append(resamount);// �ܶ�
		builder.append("&");
		builder.append("prepayment").append("=").append(prepayment);// Ԥ��

		return builder.toString();
	}

	/**
	 * ���----�ջ�����Ϣ
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
		builder.append("userid").append("=").append(userid);// �û�ID
		builder.append("&");
		builder.append("receivername").append("=").append(receivername);// ����
		builder.append("&");
		builder.append("cityid").append("=").append(cityid);// ����
		builder.append("&");
		builder.append("address").append("=").append(address);// ��ַ
		builder.append("&");
		builder.append("phone").append("=").append(phone);// �绰
		builder.append("&");
		builder.append("isdefault").append("=").append(isdefault);// �Ƿ�Ĭ��

		return builder.toString();
	}

	/**
	 * �༭----�ջ�����Ϣ
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
		builder.append("userid").append("=").append(userid);// �û�ID
		builder.append("&");
		builder.append("id").append("=").append(personId);// �ջ���ID
		builder.append("&");
		builder.append("receivername").append("=").append(receivername);// ����
		builder.append("&");
		builder.append("cityid").append("=").append(cityid);// ����
		builder.append("&");
		builder.append("address").append("=").append(address);// ��ַ
		builder.append("&");
		builder.append("phone").append("=").append(phone);// �绰
		builder.append("&");
		builder.append("isdefault").append("=").append(isdefault);// �Ƿ�Ĭ��

		return builder.toString();
	}

	/**
	 * ���----��Ʊ��Ϣ
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
		builder.append("userid").append("=").append(userId);// �û�ID
		builder.append("&");
		builder.append("type").append("=").append(typeId);// ����
		builder.append("&");
		builder.append("title").append("=").append(title);// ̧ͷ
		builder.append("&");
		builder.append("context").append("=").append(contextString);// ����
		builder.append("&");
		builder.append("isdefault").append("=").append(isDefault);// �Ƿ�Ĭ��

		return builder.toString();
	}

	/**
	 * �༭----��Ʊ��Ϣ
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
		builder.append("id").append("=").append(invoiceId);// ��ƱID
		builder.append("&");
		builder.append("userid").append("=").append(userId);// �û�ID
		builder.append("&");
		builder.append("type").append("=").append(typeId);// ����
		builder.append("&");
		builder.append("title").append("=").append(title);// ̧ͷ
		builder.append("&");
		builder.append("context").append("=").append(contextString);// ����
		builder.append("&");
		builder.append("isdefault").append("=").append(isDefault);// �Ƿ�Ĭ��

		return builder.toString();
	}

}
