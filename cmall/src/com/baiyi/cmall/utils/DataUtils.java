package com.baiyi.cmall.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.baiyi.cmall.R;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.IndutryArgumentInfo;
import com.baiyi.cmall.entity.IntentionDetailStandardInfo;
import com.baiyi.cmall.entity.IntentionProviderDetailInfo;
import com.baiyi.cmall.entity.IntentionTypeInfo;
import com.baiyi.cmall.model.Pbi;

/**
 * 
 * ��������Դ�Ĺ�����
 * 
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-15 ����10:26:17
 */
public class DataUtils {

	/**
	 * �����ַ���(��ϸ����)ȥ��
	 * 
	 * @param list
	 */
	public static ArrayList<String> removeDuplicateObj(ArrayList<String> list) {

		ArrayList<String> tempList = new ArrayList<String>();

		for (int k = 0; k < list.size(); k++) {
			if (!tempList.contains(list.get(k))) {
				tempList.add(list.get(k));

			}
		}
		return tempList;
	}

	/**
	 * �õ�ÿһ���͵ļ���
	 * 
	 * @param content
	 * @param datas2
	 * @return
	 */
	public static ArrayList<GoodsSourceInfo> getRelevantSource(String content, ArrayList<GoodsSourceInfo> datas2) {
		ArrayList<GoodsSourceInfo> infos = new ArrayList<GoodsSourceInfo>();
		for (int i = 0; i < datas2.size(); i++) {
			if ((content + "").equals(datas2.get(i).getGoodSPurchaseContent())) {
				infos.add(datas2.get(i));
			}
		}

		return infos;
	}

	/**
	 * ί�й�Ӧ�еĲɹ���Ϣ
	 * 
	 * @param info
	 * @return
	 */
	public static ArrayList<String> getShowList(GoodsSourceInfo info) {

		ArrayList<String> supplyInfos = new ArrayList<String>();

		if (null != info) {

			if (!Utils.isStringEmpty(info.getGoodSMerchant())) {
				supplyInfos.add("��Ӧ������ : " + info.getGoodSMerchant());
			}
			if (!Utils.isStringEmpty(info.getGoodSCategory())) {
				supplyInfos.add("���� : " + info.getGoodSCategory());
			}
			if (!Utils.isStringEmpty(info.getGoodSName())) {
				supplyInfos.add("���� : " + info.getGoodSName());
			}
			if (!Utils.isStringEmpty(info.getGoodSBrand())) {
				supplyInfos.add("Ʒ�� : " + info.getGoodSBrand());
			}
			if (!Utils.isStringEmpty(info.getGoodSPlace())) {
				supplyInfos.add("����: " + info.getGoodSPlace());
			}
			if (!Utils.isStringEmpty(info.getGoodSWeight())) {
				supplyInfos.add("��� :  " + info.getGoodSWeight() + "��");
			}
			if (!Utils.isStringEmpty(info.getGoodSDelivery())) {
				supplyInfos.add("�����  : " + info.getGoodSDelivery());
			}
			if (!Utils.isStringEmpty(info.getGoodSPrePrice())) {
				supplyInfos.add("�۸� : "+ Utils.twoDecimals(info.getGoodSPrePrice()) + "Ԫ/��");
			}
			if (!Utils.isStringEmpty(info.getPrepayment())) {
				supplyInfos.add("Ԥ����� : " + Utils.twoDecimals(info.getPrepayment())+ "Ԫ");
			}
			if (!Utils.isStringEmpty(info.getGoodSPutTime())) {
				supplyInfos.add("����ʱ�� : " + info.getGoodSPutTime());
			}
		}

		return supplyInfos;
	}

	/**
	 * ί�вɹ��еĹ�Ӧ��Ϣ
	 * 
	 * @param info
	 * @return
	 */
	public static ArrayList<String> getProviderShowList(GoodsSourceInfo info) {

		ArrayList<String> supplyInfos = new ArrayList<String>();
		if (null != info) {
			supplyInfos.add("�ɹ��� : " + TextViewUtil.getEditString(info.getGoodSMerchant()));
			supplyInfos.add("���� : " + TextViewUtil.getEditString(info.getGoodSName()));
			supplyInfos.add("���� : " + TextViewUtil.getEditString(info.getGoodSCategory()));
			supplyInfos.add("Ʒ�� : " + TextViewUtil.getEditString(info.getGoodSBrand()));
			supplyInfos.add("���� : " + TextViewUtil.getEditString(info.getGoodSArea()));
			supplyInfos.add("���� : " + TextViewUtil.getEditString(info.getGoodSWeight()) + "��");
			supplyInfos.add("�� �� ��  : " + TextViewUtil.getEditString(info.getGoodSDelivery()));
			supplyInfos.add("�۸� : " + TextViewUtil.getEditString(info.getGoodSpriceInterval()) + "Ԫ/��");
			supplyInfos.add("Ԥ����� : " + TextViewUtil.getEditString(info.getGoodSPrePrice()) + "Ԫ");
			supplyInfos.add("����ʱ�� : " + TextViewUtil.getEditString(info.getGoodSPutTime()));
		}
		return supplyInfos;
	}

	/**
	 * ��Ӧ�����б� �����б�����Դ
	 * 
	 * @return
	 */
	public static ArrayList<IntentionTypeInfo> getTypeArrayList() {
		ArrayList<IntentionTypeInfo> datas = new ArrayList<IntentionTypeInfo>();

		datas.add(new IntentionTypeInfo("ȫ��", String.valueOf(3)));
		datas.add(new IntentionTypeInfo("���յ��Ĳɹ�����", "1"));
		datas.add(new IntentionTypeInfo("�ҷ����Ĺ�Ӧ����", "2"));

		return datas;
	}

	/**
	 * ��Ӧ�����б� ״̬�б�����Դ
	 * 
	 * @return
	 */
	public static ArrayList<IntentionTypeInfo> getStateArrayList() {
		ArrayList<IntentionTypeInfo> datas = new ArrayList<IntentionTypeInfo>();

		datas.add(new IntentionTypeInfo("ȫ��", "-1"));
		datas.add(new IntentionTypeInfo("����", "1"));
		datas.add(new IntentionTypeInfo("������", "2"));
		datas.add(new IntentionTypeInfo("�Ѿܾ�", "3"));
		datas.add(new IntentionTypeInfo("��ȡ��", "4"));
		datas.add(new IntentionTypeInfo("���µ�", "5"));
		datas.add(new IntentionTypeInfo("�ɹ�����ɾ��", "6"));

		return datas;
	}

	/**
	 * �ɹ������б� ״̬�б�����Դ
	 * 
	 * @return
	 */
	public static ArrayList<IntentionTypeInfo> getPurStateArrayList() {
		ArrayList<IntentionTypeInfo> datas = new ArrayList<IntentionTypeInfo>();

		datas.add(new IntentionTypeInfo("ȫ��", "-1"));
		datas.add(new IntentionTypeInfo("����", "1"));
		datas.add(new IntentionTypeInfo("������", "2"));
		datas.add(new IntentionTypeInfo("�Ѿܾ�", "3"));
		datas.add(new IntentionTypeInfo("��ȡ��", "4"));
		datas.add(new IntentionTypeInfo("���µ�", "5"));
		datas.add(new IntentionTypeInfo("��Ӧ����ɾ��", "6"));

		return datas;
	}

	/**
	 * �ɹ������б� �����б�����Դ
	 * 
	 * @return
	 */
	public static ArrayList<IntentionTypeInfo> getPurTypeArrayList() {
		ArrayList<IntentionTypeInfo> datas = new ArrayList<IntentionTypeInfo>();

		datas.add(new IntentionTypeInfo("ȫ��", "3"));
		datas.add(new IntentionTypeInfo("�ҷ����Ĳɹ�����", "1"));
		datas.add(new IntentionTypeInfo("���յ��Ĺ�Ӧ����", "2"));

		return datas;
	}

	/*
	 * �������򵥵�״̬����ȡ״̬���� 1. �Ѵ��� 2. ������ 3. �Ѿܾ� 5. ���µ�
	 */
	public static String getStateText(int state) {
		String stateString = null;
		switch (state) {
		case 1:
			stateString = "���ظ�";
			break;
		case 2:
			stateString = "������";
			break;
		case 3:
			stateString = "�Ѿܾ�";
			break;
		case 5:
			stateString = "���µ�";
			break;

		}
		return stateString;

	}

	/**
	 * �õ�Ʒ�����͵���������Ӧ����������
	 * 
	 * @param string
	 * @param datas
	 * @return
	 */
	public static ArrayList<IntentionDetailStandardInfo> getStandardRelevantSource(String string,
			ArrayList<IntentionDetailStandardInfo> datas) {
		ArrayList<IntentionDetailStandardInfo> infos = new ArrayList<IntentionDetailStandardInfo>();
		for (int i = 0; i < datas.size(); i++) {
			if ((string + "").equals(datas.get(i).getCodevalue())) {
				infos.add(datas.get(i));
			}
		}

		return infos;
	}

	/**
	 * ���ǲɹ��̡�����ù�Ӧ�̾�����Ϣ
	 * 
	 * @param info
	 * @return
	 */
	public static ArrayList<IndutryArgumentInfo> getOfferDetail(GoodsSourceInfo info) {
		ArrayList<IndutryArgumentInfo> datas = new ArrayList<IndutryArgumentInfo>();

		/**
		 * info.setGoodSID(modelData.getString("id"));// ����ID
		 * info.setGoodSDetails(modelData.getString("offerDetail"));// ��Ӧ����
		 * info.setGoodSpriceInterval(modelData.getString("price"));// ����۸񣨵��ۣ�
		 * info.setGoodSMerchant(modelData .getString("purchasecompanyname"));//
		 * �ɹ������� info.setGoodSName(modelData.getString("purchasename"));// ����
		 * info.setGoodSWeight(modelData.getInt("inventory"));// ������������
		 * info.setGoodSPrePrice(Float.parseFloat(modelData
		 * .getString("prepayment")));// Ԥ����
		 * info.setGoodSDelivery(modelData.getString("deliveryPlaceName"));//
		 * �����
		 */
		IndutryArgumentInfo argumentInfo0 = new IndutryArgumentInfo();
		argumentInfo0.setArgNmae("����: ");
		argumentInfo0.setArgValue(info.getGoodSContent());
		datas.add(argumentInfo0);

		IndutryArgumentInfo argumentInfo1 = new IndutryArgumentInfo();
		argumentInfo1.setArgNmae("����: ");
		argumentInfo1.setArgValue(info.getGoodSCategory());
		datas.add(argumentInfo1);

		IndutryArgumentInfo argumentInfo2 = new IndutryArgumentInfo();
		argumentInfo2.setArgNmae("Ʒ��:");
		argumentInfo2.setArgValue(info.getGoodSBrand());
		datas.add(argumentInfo2);

		IndutryArgumentInfo argumentInfo3 = new IndutryArgumentInfo();
		argumentInfo3.setArgNmae("����:");
		argumentInfo3.setArgValue(info.getGoodSArea());
		datas.add(argumentInfo3);

		IndutryArgumentInfo argumentInfo4 = new IndutryArgumentInfo();
		argumentInfo4.setArgNmae("���:");
		argumentInfo4.setArgValue(info.getGoodSWeight() + "");
		datas.add(argumentInfo4);

		IndutryArgumentInfo argumentInfo5 = new IndutryArgumentInfo();
		argumentInfo5.setArgNmae("�۸�:");
		argumentInfo5.setArgValue(info.getGoodSpriceInterval() + "Ԫ/��");
		datas.add(argumentInfo5);

		IndutryArgumentInfo argumentInfo6 = new IndutryArgumentInfo();
		argumentInfo6.setArgNmae("Ԥ�����:");
		argumentInfo6.setArgValue(info.getGoodSPrePrice() + "Ԫ");
		datas.add(argumentInfo6);

		IndutryArgumentInfo argumentInfo7 = new IndutryArgumentInfo();
		argumentInfo7.setArgNmae("�����:");
		argumentInfo7.setArgValue(info.getGoodSDelivery());
		datas.add(argumentInfo7);

		IndutryArgumentInfo argumentInfo8 = new IndutryArgumentInfo();
		argumentInfo8.setArgNmae("��ϸ����:");
		argumentInfo8.setArgValue(info.getGoodSDetails());
		datas.add(argumentInfo8);

		return datas;
	}

	/**
	 * ���ǲɹ��̡�����òɹ�������Ϣ
	 * 
	 * @param info
	 * @return
	 */
	public static ArrayList<IndutryArgumentInfo> getPurDetail(GoodsSourceInfo info) {
		ArrayList<IndutryArgumentInfo> datas = new ArrayList<IndutryArgumentInfo>();

		IndutryArgumentInfo argumentInfo0 = new IndutryArgumentInfo();
		argumentInfo0.setArgNmae("����: ");
		argumentInfo0.setArgValue(info.getGoodSName());
		datas.add(argumentInfo0);

		IndutryArgumentInfo argumentInfo1 = new IndutryArgumentInfo();
		argumentInfo1.setArgNmae("����: ");
		argumentInfo1.setArgValue(info.getGoodSCategory());
		datas.add(argumentInfo1);

		IndutryArgumentInfo argumentInfo2 = new IndutryArgumentInfo();
		argumentInfo2.setArgNmae("Ʒ��:");
		argumentInfo2.setArgValue(info.getGoodSBrand());
		datas.add(argumentInfo2);

		IndutryArgumentInfo argumentInfo3 = new IndutryArgumentInfo();
		argumentInfo3.setArgNmae("����:");
		argumentInfo3.setArgValue(info.getGoodSPlace());
		datas.add(argumentInfo3);

		IndutryArgumentInfo argumentInfo4 = new IndutryArgumentInfo();
		argumentInfo4.setArgNmae("����:");
		argumentInfo4.setArgValue(info.getGoodSWeight() + "");
		datas.add(argumentInfo4);

		IndutryArgumentInfo argumentInfo5 = new IndutryArgumentInfo();
		argumentInfo5.setArgNmae("�۸�:");
		argumentInfo5.setArgValue(info.getGoodSpriceInterval() + "Ԫ/��");
		datas.add(argumentInfo5);

		IndutryArgumentInfo argumentInfo6 = new IndutryArgumentInfo();
		argumentInfo6.setArgNmae("Ԥ�����:");
		argumentInfo6.setArgValue(info.getGoodSPrePrice() + "Ԫ");
		datas.add(argumentInfo6);

		IndutryArgumentInfo argumentInfo7 = new IndutryArgumentInfo();
		argumentInfo7.setArgNmae("�����:");
		argumentInfo7.setArgValue(info.getGoodSDelivery());
		datas.add(argumentInfo7);

		IndutryArgumentInfo argumentInfo8 = new IndutryArgumentInfo();
		argumentInfo8.setArgNmae("��ϸ����:");
		argumentInfo8.setArgValue(info.getGoodSPurchaseContent());
		datas.add(argumentInfo8);

		return datas;
	}

	/**
	 * ��ҳ��ϵͳ��������òɹ�������Ϣ
	 * 
	 * @param info
	 * @return
	 */
	public static ArrayList<IndutryArgumentInfo> getSystemPurDetail(GoodsSourceInfo info) {
		ArrayList<IndutryArgumentInfo> datas = new ArrayList<IndutryArgumentInfo>();

		IndutryArgumentInfo argumentInfo0 = new IndutryArgumentInfo();
		argumentInfo0.setArgNmae("�ɹ���: ");
		argumentInfo0.setArgValue(info.getGoodSMerchant());
		datas.add(argumentInfo0);

		IndutryArgumentInfo argumentInfo1 = new IndutryArgumentInfo();
		argumentInfo1.setArgNmae("����: ");
		argumentInfo1.setArgValue(info.getGoodSName());
		datas.add(argumentInfo1);

		IndutryArgumentInfo argumentInfo2 = new IndutryArgumentInfo();
		argumentInfo2.setArgNmae("����: ");
		argumentInfo2.setArgValue(info.getGoodSCategory());
		datas.add(argumentInfo2);

		IndutryArgumentInfo argumentInfo3 = new IndutryArgumentInfo();
		argumentInfo3.setArgNmae("Ʒ��:");
		argumentInfo3.setArgValue(info.getGoodSBrand());
		datas.add(argumentInfo3);

		IndutryArgumentInfo argumentInfo4 = new IndutryArgumentInfo();
		argumentInfo4.setArgNmae("����:");
		argumentInfo4.setArgValue(info.getGoodSArea());
		datas.add(argumentInfo4);

		IndutryArgumentInfo argumentInfo5 = new IndutryArgumentInfo();
		argumentInfo5.setArgNmae("����:");
		argumentInfo5.setArgValue(info.getGoodSWeight() + "��");
		datas.add(argumentInfo5);

		IndutryArgumentInfo argumentInfo6 = new IndutryArgumentInfo();
		argumentInfo6.setArgNmae("�����:");
		argumentInfo6.setArgValue(info.getGoodSDelivery());
		datas.add(argumentInfo6);

		IndutryArgumentInfo argumentInfo7 = new IndutryArgumentInfo();
		argumentInfo7.setArgNmae("�۸�:");
		argumentInfo7.setArgValue(info.getGoodSpriceInterval() + "Ԫ/��");
		datas.add(argumentInfo7);

		IndutryArgumentInfo argumentInfo8 = new IndutryArgumentInfo();
		argumentInfo8.setArgNmae("Ԥ�����:");
		argumentInfo8.setArgValue(info.getGoodSPrePrice() + "Ԫ");
		datas.add(argumentInfo8);

		IndutryArgumentInfo argumentInfo9 = new IndutryArgumentInfo();
		argumentInfo9.setArgNmae("����ʱ��:");
		argumentInfo9.setArgValue(info.getGoodSPutTime() + "");
		datas.add(argumentInfo9);

		IndutryArgumentInfo argumentInfo10 = new IndutryArgumentInfo();
		argumentInfo10.setArgNmae("��ϸ����:");
		argumentInfo10.setArgValue(info.getGoodSPurchaseContent());
		datas.add(argumentInfo10);

		return datas;
	}

	/**
	 * �ҵ�ί��-ί�вɹ�-����
	 * 
	 * @param info
	 * @return
	 */
	public static ArrayList<IndutryArgumentInfo> getDelegationPurDetail(GoodsSourceInfo info) {
		ArrayList<IndutryArgumentInfo> datas = new ArrayList<IndutryArgumentInfo>();

		if (null != info) {
			datas.add(new IndutryArgumentInfo("����:", info.getGoodSTitle()));
			datas.add(new IndutryArgumentInfo("��˾����:", info.getGoodSMerchant()));
			datas.add(new IndutryArgumentInfo("��ϵ��:", info.getGoodSContactPerson()));
			datas.add(new IndutryArgumentInfo("��ϵ��ʽ:", info.getGoodSContactWay()));
			datas.add(new IndutryArgumentInfo("��������:", info.getGoodSContent()));
			datas.add(new IndutryArgumentInfo("��ϵ�˳���:", info.getGoodSArea()));
			String address = info.getAddress();
			if (address == null || "null".equals(address)) {
				address = "";
			}
			datas.add(new IndutryArgumentInfo("��ϵ�˵�ַ:", address));
			String category = info.getGoodSCategory();
			if (category == null || "null".equals(category)) {
				category = "";
			}

			datas.add(new IndutryArgumentInfo("����:", category));
			datas.add(new IndutryArgumentInfo("�����:", info.getGoodSDelivery()));
			datas.add(new IndutryArgumentInfo("����:", info.getStartAddress()));

			datas.add(new IndutryArgumentInfo("Ʒ��:", info.getGoodSBrand()));
			// ������ʾ�����ݷ���
			datas.add(new IndutryArgumentInfo("����:",
					("0".equals(info.getGoodSWeight()) || "null".equals(info.getGoodSWeight())) ? ""
							: info.getGoodSWeight() + "��"));
			// �۸���ʾ�����ݷ���
			String price = Utils.twoDecimals(info.getGoodSPrePrice());
			datas.add(new IndutryArgumentInfo("�۸�:", "0".equals(price) || "0.00".equals(price) ? "" : price + "Ԫ/��"));

		}
		return datas;
	}

	/**
	 * �ҵ�ί������-��������
	 * 
	 * @param info
	 * @return
	 */
	public static ArrayList<IndutryArgumentInfo> getDelegationLogisticsDetail(GoodsSourceInfo info) {
		ArrayList<IndutryArgumentInfo> datas = new ArrayList<IndutryArgumentInfo>();
		if (null != info) {

			datas.add(new IndutryArgumentInfo("����:", info.getTitle()));
			datas.add(new IndutryArgumentInfo("��˾����:", info.getGoodSMerchant()));
			datas.add(new IndutryArgumentInfo("��ϵ��:", info.getGoodSContactPerson()));
			datas.add(new IndutryArgumentInfo("��ϵ��ʽ:", info.getGoodSContactWay()));
			datas.add(new IndutryArgumentInfo("��������:", info.getGoodSDetails()));
			datas.add(new IndutryArgumentInfo("ʼ������:", info.getStartAddress()));
			datas.add(new IndutryArgumentInfo("ʼ����ַ:", info.getGoodSStartCity()));
			datas.add(new IndutryArgumentInfo("Ŀ�ĳ���:", info.getEndAddress()));
			datas.add(new IndutryArgumentInfo("Ŀ�ĵ�ַ:", info.getGoodSEndCity()));
			datas.add(new IndutryArgumentInfo("����:",
					("0".equals(info.getGoodSWeight()) || "null".equals(info.getGoodSWeight())) ? ""
							: info.getGoodSWeight() + "��"));
			String type = info.getDeliverytypename();
			if ("null".equals(type) || null == type) {
				type = "";
			}
			datas.add(new IndutryArgumentInfo("��������:", type));
			String pack = info.getPacktypename();
			if ("null".equals(pack) || null == pack) {
				pack = "";
			}
			datas.add(new IndutryArgumentInfo("��װ��ʽ:", pack));
			// ��ʼʱ��
			long startTime = Utils.getLongTime1(info.getGoodSStartTime());
			// ����ʱ��
			long endTime = Utils.getLongTime1(info.getGoodSEndTime());
			datas.add(new IndutryArgumentInfo("ʼ��ʱ��:", startTime <= 0 ? "" : info.getGoodSStartTime()));
			datas.add(new IndutryArgumentInfo("����ʱ��:", endTime <= 0 ? "" : info.getGoodSEndTime()));

		}

		return datas;
	}

	/**
	 * ��ע��Ӧ����ע�ɹ�����
	 * 
	 * @param info
	 * @param state
	 *            1:��ע��Ӧ 0����ע�ɹ�
	 * @return
	 */
	public static ArrayList<IndutryArgumentInfo> getAttentionPurProDetail(GoodsSourceInfo info, int state) {
		ArrayList<IndutryArgumentInfo> datas = new ArrayList<IndutryArgumentInfo>();

		if (null != info) {
			if (2 == state) {
				datas.add(new IndutryArgumentInfo("��Ӧ��:", TextViewUtil.getEditString(info.getCompanyName())));
				datas.add(new IndutryArgumentInfo("��Ӧ������:", TextViewUtil.getEditString(info.getCompanytypename())));
				datas.add(new IndutryArgumentInfo("����:", TextViewUtil.getEditString(info.getCity())));
				datas.add(new IndutryArgumentInfo("��ϵ��:", TextViewUtil.getEditString(info.getGoodSContactPerson())));
				datas.add(new IndutryArgumentInfo("��ϵ�绰:", TextViewUtil.getEditString(info.getGoodSContactWay())));
				datas.add(new IndutryArgumentInfo("����:", TextViewUtil.getEditString(info.getEmail())));
				datas.add(new IndutryArgumentInfo("��ϸ��ַ:", TextViewUtil.getEditString(info.getAddress())));
				datas.add(new IndutryArgumentInfo("��Ӧ�̼��:", TextViewUtil.getEditString(info.getGoodSDetails())));
			} else {
				if (1 == state) {
					datas.add(new IndutryArgumentInfo("����:", TextViewUtil.getEditString(info.getGoodSName())));
				} else {
					datas.add(new IndutryArgumentInfo("����:", TextViewUtil.getEditString(info.getGoodSName())));
				}
				datas.add(new IndutryArgumentInfo("����:", info.getGoodSCategory()));
				datas.add(new IndutryArgumentInfo("����:", TextViewUtil.getEditString(info.getGoodSPlace())));
				datas.add(new IndutryArgumentInfo("Ʒ��:", TextViewUtil.getEditString(info.getGoodSBrand())));
				datas.add(new IndutryArgumentInfo("����:", info.getGoodSWeight() + "��"));
				String price = Utils.twoDecimals(info.getGoodSPrePrice());
				if (price.startsWith(".")) {
					price = "0" + price;
				}
				datas.add(new IndutryArgumentInfo("�۸�:", price + "Ԫ/��"));
				datas.add(new IndutryArgumentInfo("Ԥ�����:", Utils.twoDecimals(info.getPrepayment()) + "Ԫ"));
				datas.add(new IndutryArgumentInfo("�����:", TextViewUtil.getEditString(info.getGoodSDelivery())));
				datas.add(new IndutryArgumentInfo("����ʱ��:", TextViewUtil.getEditString(info.getGoodSPutTime())));
				datas.add(new IndutryArgumentInfo("��ע����:", TextViewUtil.getEditString(info.getGoodSDetails())));

			}
			return datas;
		}

		return null;
	}

	/**
	 * ��������
	 * 
	 * @return
	 */
	public static ArrayList<String> getShareName() {
		ArrayList<String> datas = new ArrayList<String>();
		datas.add("΢��");
		datas.add("΢������Ȧ");
		datas.add("�ֻ�QQ");
		datas.add("QQ�ռ�");
		datas.add("�ʼ�");
		datas.add("��������");
		return datas;
	}

	/**
	 * ����ͼ��
	 * 
	 * @return
	 */
	public static ArrayList<Integer> getShareImgs() {
		ArrayList<Integer> datas = new ArrayList<Integer>();

		datas.add(R.drawable.share_weixin2x);
		datas.add(R.drawable.share_weixin_circle_friends2x);
		datas.add(R.drawable.share_tel_qq2x);
		datas.add(R.drawable.share_qqspace2x);
		datas.add(R.drawable.share_e_mail2x);
		datas.add(R.drawable.share_link2x);

		return datas;
	}

	/**
	 * ��ע�Ĳɹ�������
	 * 
	 * @param info
	 * @return
	 */
	public static ArrayList<IndutryArgumentInfo> getAttentionBuyerDetail(GoodsSourceInfo info) {
		ArrayList<IndutryArgumentInfo> infos = new ArrayList<IndutryArgumentInfo>();

		infos.add(new IndutryArgumentInfo("�ɹ���: ", TextViewUtil.getEditString(info.getUserName())));
		infos.add(new IndutryArgumentInfo("�ɹ�������: ", TextViewUtil.getEditString(info.getCompanytypename())));
		infos.add(new IndutryArgumentInfo("��ϵ��ʽ: ", TextViewUtil.getEditString(info.getGoodSContactWay())));
		infos.add(new IndutryArgumentInfo("����: ", TextViewUtil.getEditString(info.getEmail())));
		infos.add(new IndutryArgumentInfo("��ַ: ", TextViewUtil.getEditString(info.getAddress())));
		return infos;
	}

	public static List<IndutryArgumentInfo> getPageInfo(Pbi pbi) {
		ArrayList<IndutryArgumentInfo> infos = new ArrayList<IndutryArgumentInfo>();

		infos.add(new IndutryArgumentInfo("��Ʒ����: ", TextViewUtil.getEditString(pbi.getPn())));
		infos.add(new IndutryArgumentInfo("������: ", TextViewUtil.getEditString(pbi.getSn())));
		infos.add(new IndutryArgumentInfo("����: ", TextViewUtil.getEditString(pbi.getBn())));
		infos.add(new IndutryArgumentInfo("�۸�: ", TextViewUtil.getEditString(pbi.getPp() + "Ԫ/��")));
		infos.add(new IndutryArgumentInfo("״̬: ", TextViewUtil.getEditString(getStateName(pbi.getPs()))));
		infos.add(new IndutryArgumentInfo("�����Զ���ʼ: ", TextViewUtil.getEditString(pbi.getDqk())));
		infos.add(new IndutryArgumentInfo("�����Զ�����: ", TextViewUtil.getEditString(pbi.getDqj())));
		infos.add(new IndutryArgumentInfo("����ʱ��: ", TextViewUtil.getEditString(pbi.getCd())));
		infos.add(new IndutryArgumentInfo("��ʼʱ��: ", TextViewUtil.getEditString(pbi.getSd())));
		infos.add(new IndutryArgumentInfo("����ʱ��: ", TextViewUtil.getEditString(pbi.getJd())));

		infos.add(new IndutryArgumentInfo("������: ", TextViewUtil.getEditString(fromJSONArray(pbi.getGgl()))));
		
		infos.add(new IndutryArgumentInfo("����֧��: ", TextViewUtil.getEditString(fromJSONArray(pbi.getPsz()))));
		infos.add(new IndutryArgumentInfo("��Ʊ֧��: ", TextViewUtil.getEditString(fromJSONArray(pbi.getIzc()))));
		infos.add(new IndutryArgumentInfo("���ʽ: ", TextViewUtil.getEditString(fromJSONArray(pbi.getPmz()))));

		return infos;
	}

	private static String fromJSONArray(JSONArray array) {

		StringBuilder builder = new StringBuilder();
		try {
			for (int i = 0; i < array.length(); i++) {

				builder.append(array.get(i)).append(" ");
				
			}
			
			return builder.toString();
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return "";
	}

	private static String getStateName(int ps) {

		switch (ps) {
		case 0:
			return "δ����";
		case 1:
			return "����";
		case -1:
			return "����";

		}
		return "";
	}
}
