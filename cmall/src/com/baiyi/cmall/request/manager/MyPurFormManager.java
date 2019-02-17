package com.baiyi.cmall.request.manager;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baiyi.cmall.activities.main.total.TotalUtils;
import com.baiyi.cmall.entity.OrderEntity;

public class MyPurFormManager {

	/**
	 * ���ǲɹ��̡����ɹ������б�(����)
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

					info.setId(dataObject.getString("id"));// ����ID
					info.setOrderName(dataObject.getString("ordername"));// ��������
					info.setPurName(dataObject.getString("purchasename"));// ����
					info.setOrderState(dataObject.getInt("orderstate"));// ����״̬
					info.setOrderStateName(dataObject
							.getString("orderStateName"));// ����״̬����
					info.setPrice(dataObject.getString("price"));// �۸�
					info.setPrepayment(dataObject.getString("prepayment"));// Ԥ����
					info.setBrandname(dataObject.getString("brandname"));// Ʒ��
					info.setCategoryName(dataObject.getString("categoryName"));// ��������
					info.setInventory(dataObject.getString("amount"));// ����
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
	 * ���ǲɹ��̡����ɹ������б�(��Ʒ)
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
					info.setCompanyname(dataObject.getString("bl"));// ����������˾����
					info.setOrderStateName(dataObject.getString("os"));// ����״̬����
					info.setOrderState(dataObject.getInt("oss"));// ����״̬
					info.setPrepayment(dataObject.getString("pr"));// ʵ����

					JSONArray opilArray = dataObject.getJSONArray("opil");
					ArrayList<OrderEntity> opilList = new ArrayList<OrderEntity>();
					for (int k = 0; k < opilArray.length(); k++) {
						JSONObject opilOBJ = opilArray.getJSONObject(k);
						OrderEntity opilInfo = new OrderEntity();
						opilInfo.setId(opilOBJ.getString("id"));// ����ID
						opilInfo.setPic(opilOBJ.getString("purl"));// ͼƬ
						opilInfo.setProductName(opilOBJ.getString("pn"));// ��Ʒ����
						opilInfo.setInventory(opilOBJ.getString("co"));// ����
						opilInfo.setPrice(opilOBJ.getString("pr"));// �۸�
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
	 * ���ǲɹ���--�ɹ�����--��������
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
				entity.setOrderNumber(dataObject.getString("oi"));// �������
				entity.setCompanyid(dataObject.getString("ci"));// �̼�ID
				/*
				 * �ջ�����Ϣ
				 */
				entity.setReceivername(dataObject.getString("rp"));// �ջ�������
				entity.setOrderCity(dataObject.getString("dq"));// ����
				 entity.setOrderCityId(dataObject.getString("ci"));// ����ID
				entity.setAddress(dataObject.getString("ad"));// ��ַ
				entity.setPhone(dataObject.getString("ph"));// �绰
				/*
				 * ��Ʊ��Ϣ
				 */
				entity.setTitle(dataObject.getString("ii"));// ��Ʊ̧ͷ
				entity.setContext(dataObject.getString("ic"));// ��Ʊ����
				// entity.setInvoicetypeId(dataObject.getString("invoicetype"));//
				// ��Ʊ����ID
				entity.setInvoicetypename(dataObject.getString("it"));// ��Ʊ��������

				entity.setCompanyname(dataObject.getString("cn"));// �̼�����
				entity.setBinaryvalue(dataObject.getString("os"));

				JSONArray pdmlArray = dataObject.getJSONArray("pdml");
				ArrayList<OrderEntity> pdmlList = new ArrayList<OrderEntity>();// ��񼯺�
				for (int j = 0; j < pdmlArray.length(); j++) {
					JSONObject pdmlObject = (JSONObject) pdmlArray.get(j);

					OrderEntity pdmlEntity = new OrderEntity();
					pdmlEntity.setId(pdmlObject.getString("id"));// ID
					pdmlEntity.setProductName(pdmlObject.getString("pn"));// ��Ʒ����
					pdmlEntity.setCategoryName(pdmlObject.getString("bn"));// ����
					pdmlEntity.setPic(pdmlObject.getString("purl"));// ��Ʒͼ��
					pdmlEntity.setPrice(pdmlObject.getString("pr"));// �۸�
					pdmlEntity.setInventory(pdmlObject.getString("co"));// ����
					pdmlEntity.setAllMoney(pdmlObject.getString("am"));// �ܼ�

					/*
					 * ���
					 */
					JSONArray guigeList = pdmlObject.getJSONArray("ggl");
					String guige = "";
					for (int k = 0; k < guigeList.length(); k++) {
						guige += guigeList.get(k);
						if (k != guigeList.length() - 1) {
							guige += "/";
						}
					}
					pdmlEntity.setGuige(guige);// ���
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
	 * ���ǲɹ���--�ɹ�����--��������
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
					entity.setOfferName(dataObject.getString("offerName"));// ����
					entity.setCategoryName(dataObject.getString("categoryName"));// ��������
					entity.setBrandname(dataObject.getString("brandname"));// Ʒ��
					entity.setPrice(dataObject.getString("price"));// �۸�
					entity.setInventory(dataObject.getString("inventory"));// ���
					entity.setPrepayment(dataObject.getString("prepayment"));// Ԥ��
					entity.setResamount(dataObject.getString("resamount"));// ��Դ�ܶ�
					entity.setReceivername(dataObject.getString("receivername"));// �ջ�������
					entity.setOrderCity(dataObject.getString("cityname"));// ����
					entity.setOrderCityId(dataObject.getString("cityid"));// ����ID
					entity.setAddress(dataObject.getString("receiveraddress"));// ��ַ
					entity.setPhone(dataObject.getString("phone"));// �绰
					entity.setTitle(dataObject.getString("title"));// ��Ʊ̧ͷ
					entity.setContext(dataObject.getString("context"));// ��Ʊ����
					entity.setInvoicetypename(dataObject
							.getString("invoicetypename"));// ��Ʊ��������
					entity.setInvoicetypeId(dataObject.getString("invoicetype"));// ��Ʊ����ID
					entity.setCompanyname(dataObject.getString("companyname"));// �̼�����
					entity.setOrderNumber(dataObject.getString("id"));// �������
					entity.setIntentionNumber(dataObject
							.getString("intentionid"));// ������
					entity.setBinaryvalue(dataObject.getString("binaryvalue"));// ��ť״ֵ̬

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// TODO ����
		return entity;
	}

	/**
	 * ���ǲɹ���--�ύ�������ص��µ�����--��������
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
					entity.setOfferName(dataObject.getString("offerName"));// ����
					entity.setCategoryName(dataObject.getString("categoryName"));// ��������
					entity.setBrandname(dataObject.getString("brandName"));// Ʒ��
					entity.setPrepayment(dataObject.getString("prepayment"));// Ԥ��
					entity.setResamount(dataObject.getString("resamount"));// ��Դ�ܶ�
					entity.setPrice(dataObject.getString("price"));// ����
					entity.setInventory(dataObject.getString("inventory"));// ���
					entity.setReceivername(dataObject.getString("receivername"));// �ջ�������
					entity.setOrderCity(dataObject.getString("cityname"));// ����
					entity.setOrderCityId(dataObject.getString("cityid"));// ����ID
					entity.setAddress(dataObject.getString("reciveraddress"));// ��ַ
					entity.setPhone(dataObject.getString("phone"));// �绰
					entity.setTitle(dataObject.getString("invoicetitle"));// ��Ʊ̧ͷ
					entity.setContext(dataObject.getString("invoicecontext"));// ��Ʊ����
					entity.setInvoicetypename(dataObject.getString("typename"));// ��Ʊ��������
					entity.setInvoicetypeId(dataObject.getString("invoicetype"));// ��Ʊ����ID
					entity.setCompanyname(dataObject.getString("companyName"));// �̼�����
					entity.setOrderNumber(dataObject.getString("orderid"));// �������
					entity.setIntentionNumber(dataObject
							.getString("intentionid"));// ������
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// TODO ����
		return entity;
	}

	/**
	 * ��ȡ֧����������ݣ�˳��ı䷢Ʊ���ջ�����Ϣ
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
