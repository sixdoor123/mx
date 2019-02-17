package com.baiyi.cmall.request.net;

import android.text.TextUtils;
import com.baiyi.cmall.Config;
import com.baiyi.cmall.application.UserApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;

/**
 * ���ʷ����� ��URL
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-7 ����3:35:31
 */
public class AppNetUrl {

	/**
	 * ��ҳ����������г�����Url
	 * 
	 * @return
	 */
	public static String getMainPagerUrl() {
		String url = Config.ROOT_URL + "IndexProductBaseData";
		return url;
	}

	/**
	 * ��Դ�б������Url
	 * 
	 * @return
	 */
	public static String getGoodsListUrl() {
		String url = Config.ROOT_URL + "IndexOffer/ListPage";
		return url;
	}

	/**
	 * ��Դ��Ӧ�����URL
	 * 
	 * @return
	 */
	public static String getGoodSDetailUrl(String id) {
		String url = Config.ROOT_URL + "IndexOffer/Detail/%s";
		url = String.format(url, id);
		return url;
	}

	/**
	 * �ύί�вɹ���URL
	 * 
	 * @param id
	 * 
	 * @return
	 * 
	 *         IndexOfferEntrust/AddPurchase
	 */
	public static String getDetegationDetailUrl() {
		String url = Config.ROOT_URL + "IndexPurchaseEntrust/AddOffer";
		return url;
	}
	
	/**
	 * http://u1q4567516.iask.in/cmallwebservice/Index/OfferEntrust/{id}
	 * @return
	 */
	public static String getHomePurDetailUrl(String id) {
		String url = Config.ROOT_URL + "Index/OfferEntrust/%s";
		url = String.format(url,id);
		return url;
	}

	/**
	 * �����ע��URL
	 * 
	 * @param id
	 * @param userid
	 *            IndexUserFollows/AddCompany
	 * @return
	 */
	public static String getAttentionDetailUrl() {
		String url = Config.ROOT_URL + "IndexUserFollows/AddOffer";
		return url;
	}

	/**
	 * �ɹ��б������------------------------------------------------------
	 * 
	 * @return
	 */
	public static String getPurchaseListUrl() {
		String url = Config.ROOT_URL + "IndexPurchase/ListPage";
		return url;
	}

	/**
	 * �ɹ��б�����URL
	 * 
	 * @param userId
	 * 
	 * @return
	 */
	public static String getPurchaseDetailUrl(String purID) {

		String url = Config.ROOT_URL + "IndexPurchase/Detail/%s";
		url = String.format(url, purID);
		return url;
	}

	/**
	 * �����б����顪����ӦURL http://10.20.22.5/webservice/User/Intention/OfferDetail/319
	 * 
	 * @return
	 */
	public static String getDetailProUrl(String proID) {
		String url = Config.ROOT_URL + "User/Intention/OfferDetail/%s";
		url = String.format(url, proID);
		return url;
	}

	/**
	 * ��ҳ�����ɹ�Դ����ί�й�ӦURL
	 * 
	 * @return
	 */
	public static String getDelSupplyUrl() {
		String url = Config.ROOT_URL + "IndexOfferEntrust/AddPurchase";
		return url;
	}

	/**
	 * ��ע�ɹ�������
	 * 
	 * @return
	 */
	public static String getPurchaseAttentionUrl() {
		String url = Config.ROOT_URL + "IndexCompanyFollows/AddPurchase";
		return url;
	}

	/**
	 * ���ࡪ��ί�вɹ�������
	 * 
	 * @return
	 */
	public static String getDelegatePurchaseUrl() {
		String url = Config.ROOT_URL + "IndexPurchaseEntrust/AddSystem";
		return url;
	}

	/**
	 * ���ࡪ��ί�й�Ӧ������
	 * 
	 * @return
	 */
	public static String getDelegateSupplyUrl() {
		String url = Config.ROOT_URL + "IndexOfferEntrust/AddSystem";
		return url;
	}

	/**
	 * ���ࡪ��ί������������
	 * 
	 * @return
	 */
	public static String getDelegateLogistics() {
		String url = Config.ROOT_URL + "IndexLogisticsEntrust/Add";
		return url;
	}

	/**
	 * �г�����URL
	 * 
	 * @return
	 */
	public static String getMarketUrl() {
		String url = Config.ROOT_URL + "IndexMarket/GetMarketInfo";
		return url;
	}

	/**
	 * ��ҳ��Ѷ�б�URL
	 * 
	 * @return
	 */
	public static String getZiXunUrl() {
		String url = Config.ROOT_URL + "Index/News/1/10";
		return url;
	}

	/**
	 * ���ǲɹ��̡����ҵĲɹ��б�
	 * 
	 * @param userId
	 *            �û�ID
	 * @param state
	 *            ���״̬
	 * @param pageIndex
	 *            ҳ��
	 * @param pageSize
	 *            ÿҳ����
	 * @return
	 */
	public static String getMyPurchaseDetailUrl(String userId, int state,
			int pageIndex, int pageSize) {
		String url = Config.ROOT_URL
				+ "User/Purchase/GetUserPurchase/%s/%s/%s/%s";
		return String.format(url, userId, state, pageIndex, pageSize);
	}

	/**
	 * �����ҵĲɹ� http://10.20.22.5/webservice/User/Purchase/PurInfor/731
	 * 
	 * @param purId
	 * @return
	 */
	public static String getUpdateDetailUrl(String purId) {
		String url = Config.ROOT_URL + "User/Purchase/PurInfor/%s";
		return String.format(url, purId);
	}

	/**
	 * ���ǲɹ��̡����༭�ɹ����桪������URL
	 * 
	 * @param purId
	 *            http://10.20.22.5/webservice/User/Purchase/EditPurchase/345
	 *            �ɹ�ID
	 * @return
	 */
	public static String getEditPurDetailUrl(int purId) {
		String url = Config.ROOT_URL + "User/Intention/PurEdit/%s";
		url = String.format(url, purId);
		return url;
	}

	/**
	 * ���ǲɹ��̡����༭�ɹ����桪������URL
	 * 
	 * @param purId
	 *            http://10.20.22.5/webservice/User/Purchase/EditPurchase/345
	 *            �ɹ�ID
	 * @return
	 */
	public static String getEditIntentionDetailUrl(int purId) {
		String url = Config.ROOT_URL + "User/Purchase/EditPurchase/%s";
		url = String.format(url, purId);
		return url;
	}

	/**
	 * ���ǲɹ��̡����ҵĲɹ���������URL
	 * 
	 * @param purId
	 *            �ɹ�ID
	 * @return
	 */
	public static String getPurDetailUrl(int purId) {
		String url = Config.ROOT_URL + "User/Purchase/GetPurchaseDetail/%s";
		url = String.format(url, purId);
		return url;
	}

	/**
	 * ���ǲɹ��̡����ҵĲɹ��������顪���༭URL
	 * 
	 * @return
	 */
	public static String getEditPurDetailUrl() {
		String url = Config.ROOT_URL + "User/Purchase/EditPurchase";
		return url;
	}

	/**
	 * ���ǲɹ��̡����ɹ����򡪡��б�URL
	 * http://10.20.22.5/webservice/User/Intention/ListPage/1/1/1/1/10
	 * 
	 * @param userId
	 *            �û�ID
	 * @param type
	 *            ��������
	 * @param state
	 *            ����״̬
	 * @param pageIndex
	 *            ҳ��
	 * @param pageSize
	 *            ÿҳ����
	 * @return
	 */
	public static String getPurAttentionListUrl(String userId, int type,
			int state, int pageIndex, int pageSize) {

		String url = Config.ROOT_URL + "User/Intention/ListPage/%s/%s/%s/%s/%s";
		return String.format(url, userId, type, state, pageIndex, pageSize);
	}

	/**
	 * ���ǲɹ��̡����ɹ����򡪡��ɹ�������URL
	 * http://10.20.22.5/webservice/User/Intention/Detail/254
	 * 
	 * @param attentionId
	 *            ����ID
	 * @return
	 */
	public static String getPurAttentionDetailUrl(int attentionId) {

		String url = Config.ROOT_URL + "User/Intention/Detail/%s";
		url = String.format(url, attentionId);
		return url;
	}

	/**
	 * ���ǲɹ��̡����ɹ����򡪡�ȷ�� http://10.20.22.5/webservice/User/Intention/EditPur
	 * 
	 * @param attentionId
	 *            ����ID
	 * @return
	 */
	public static String getEditPurAttentionUrl() {

		String url = Config.ROOT_URL + "User/Intention/PurSave";
		return url;
	}

	/**
	 * ���ǲɹ��̡����ɹ����򡪡�ȡ�� http://10.20.22.5/webservice/User/Intention/Cancel/284
	 * 
	 * @param attentionId
	 *            ����ID
	 * @return
	 */
	public static String getCancelPurAttentionUrl(int attentionId) {

		String url = Config.ROOT_URL + "User/Intention/Cancel/%s";
		url = String.format(url, attentionId);
		return url;
	}

	/**
	 * ���ǲɹ��̡����ɹ����򡪡��h��http://10.20.22.5/webservice/User/Intention/UpdateState/1
	 * 
	 * @param attentionId
	 *            ����ID
	 * @return
	 */
	public static String getDeletePurAttentionUrl(int attentionId) {

		String url = Config.ROOT_URL + "User/Intention/UpdateState/%s";
		url = String.format(url, attentionId);
		return url;
	}

	/**
	 * ���ǲɹ��̡����ɹ����򡪡��ܾ�http://10.20.22.5/webservice/User/Intention/Refuse/1
	 * 
	 * @param attentionId
	 *            ����ID
	 * @return
	 */
	public static String getRefushPurAttentionUrl(int attentionId) {

		String url = Config.ROOT_URL + "User/Intention/Refuse/%s";
		url = String.format(url, attentionId);
		return url;
	}

	/**
	 * ���ǲɹ��̡����ɹ����򡪡��µ�http://10.20.22.5/webservice/User/Intention/PlaceOrder/252
	 * / 5
	 * 
	 * @param attentionId
	 *            ����ID
	 * @param userId
	 * @param
	 * @return
	 */
	public static String getMyPurXiaOrderUrl(int attentionId, String userId) {

		String url = Config.ROOT_URL + "User/Intention/PlaceOrder/%s/%s";
		url = String.format(url, attentionId, userId);
		return url;
	}

	/**
	 * ����ί�������б��url
	 * 
	 * @param userid
	 *            �û�ID
	 * @param pageIndex
	 *            ҳ��
	 * @param pageSize
	 *            ÿҳ����
	 * @return http://10.20.22.5/webservice/User/EntrustLogisticse/ListPage/5/1/
	 *         10
	 */
	public static String getLogisticsDetailUrl(String userid, int pageIndex,
			int pageSize) {
		String url = Config.ROOT_URL
				+ "User/EntrustLogisticse/ListPage/%s/%d/%d";

		url = String.format(url, userid, pageIndex, pageSize);
		return url;
	}

	/**
	 * �ҵ�ί�С���ί�вɹ�URL
	 * 
	 * @param userid
	 *            �û�ID
	 * @param pageIndex
	 *            ҳ��
	 * @param pageSize
	 *            ÿҳ����
	 * @return
	 */
	public static String getLogisticsPurchaseListUrl(String userid,
			int pageIndex, int pageSize) {
		String url = Config.ROOT_URL + "User/EntrustPurchase/ListPage/%s/%d/%d";
		url = String.format(url, userid, pageIndex, pageSize);
		return url;
	}

	/**
	 * �ҵ�ί�С���ί�й�ӦURL
	 * 
	 * @param userid
	 *            �û�ID
	 * @param pageIndex
	 *            ҳ��
	 * @param pageSize
	 *            ÿҳ����
	 * @return
	 */
	public static String getLogisticsProviderListUrl(int pageIndex, int pageSize) {
		String url = Config.ROOT_URL + "Company/EntrustOffer/ListPage/%d/%d";
		url = String.format(url, pageIndex, pageSize);
		return url;
	}

	/**
	 * ί�й�Ӧ����URL
	 * 
	 * @param id
	 * @return
	 */
	public static String getDelegationProDetailsDetailUrl(String id) {
		String url = Config.ROOT_URL
				+ "Company/EntrustOffer/GetEntrustOfferDetail/%s";
		url = String.format(url, id);
		return url;
	}

	/**
	 * ��Ӧ�����ģ����ǹ�Ӧ�̣� �ҵĹ�Ӧ
	 * 
	 * @param offerState
	 *            ״̬
	 * @param companyid
	 *            �̼�id
	 * @param pageSize
	 *            ÿҳ����
	 * @param pageIndex
	 *            ҳ��
	 * @return
	 * 
	 *         http://10.20.22.5/webservice/Company/Offer/ListPage/0/35/1/10
	 *         http://10.20.22.5/webservice/Company/Offer/GetCompanyOffer/0/35
	 */
	public static String getMyProviderGoodSUrl(String offerState,
			int pageIndex, int pageSize) {
		String url = Config.ROOT_URL + "Company/Offer/ListPage/%s/%d/%d";
		url = String.format(url, offerState, pageIndex, pageSize);
		return url;
	}

	/**
	 * ���ǹ�Ӧ��-�ҵĹ�Ӧ����
	 * 
	 * @param pageSize
	 * @param pageIndex
	 * @param orderState
	 * @param companyid
	 * 
	 * @return
	 */
	public static String getProviderOrderListUrl(String companyid,
			String orderState, int pageIndex, int pageSize) {
		String url = Config.ROOT_URL + "Company/Order/%s/%s/%d/%d";
		url = String.format(url, companyid, orderState, pageIndex, pageSize);
		return url;
	}

	/**
	 * ���ǹ�Ӧ��-�ҵĹ�Ӧ����
	 * 
	 * @param orderState
	 * @param companyid
	 * @return User/Order/GetUserOrder/
	 * 
	 *         Company/Intention/ListPage/1/1/35/1/10
	 */
	public static String getMyProviderOrderListUrl(String type,
			String orderstate, int pageIndex, int pageSize) {
		String url = Config.ROOT_URL + "Company/Intention/ListPage/%s/%s/%d/%d";
		url = String.format(url, type, orderstate, pageIndex, pageSize);
		return url;
	}

	/**
	 * ��Դ-����-����
	 * 
	 * @param goodSID
	 * @return
	 */
	public static String getStandardAgrmentDetailUrl(String goodSID,
			String userID) {
		String url = Config.ROOT_URL + "IndexOffer/Detail/%s/%s";

		if (userID == null) {
			userID = String.valueOf(0);
		}

		url = String.format(url, goodSID, userID);
		return url;
	}

	/**
	 * �༭�������ʱ��URL
	 * 
	 * @return
	 */
	public static String getEditLogisticsDetailUrl() {
		String url = Config.ROOT_URL + "User/EntrustLogisticse/Edit";
		return url;
	}

	/**
	 * ί�вɹ�-����URL
	 * 
	 * @param id
	 *            ί�вɹ�ID
	 * @return
	 */
	public static String getLogisticsPurchaseDetailsUrl(String id) {
		String url = Config.ROOT_URL + "User/EntrustPurchase/EntrustDetail/%s";
		url = String.format(url, id);

		// String url = Config.ROOT_URL + "IndexLogisticsEntrust/Add";
		return url;
	}

	/**
	 * ���ǹ�Ӧ��-�ҵĵĹ�Ӧ-�༭��Ӧ���� Company/Intention/EditOffer ��������
	 * 
	 * @return Company/Offer/EditOffer
	 */
	public static String getMyProviderDetailEditUrl(String id) {
		String url = Config.ROOT_URL + "Company/Offer/EditOffer/%s";
		url = String.format(url, id);
		return url;
	}

	/**
	 * ���ǹ�Ӧ��-��Ӧ����-�༭��Ӧ���� Company/Intention/EditOffer ��������
	 * 
	 * @return Company/Offer/EditOffer
	 */
	public static String getIntentionProviderDetailEditUrl(String id) {
		String url = Config.ROOT_URL + "Company/Intention/EditOffer/%s";
		url = String.format(url, id);
		return url;
	}

	/**
	 * ���ǹ�Ӧ��-�ҵĵĹ�Ӧ-�༭��Ӧ���� -��� ��������
	 * 
	 * @return
	 */
	public static String getMyProviderDetailEditCompleteUrl(int state) {
		String url = null;
		if (1 == state) {
			url = Config.ROOT_URL + "Company/Intention/OffSave";
		} else {
			url = Config.ROOT_URL + "Company/Offer/Update";
		}
		return url;
	}

	/**
	 * ���ǹ�Ӧ��-��Ӧ����-�༭��Ӧ���� -��� ��������
	 * 
	 * @return
	 */
	public static String getIntentionProviderDetailEditCompleteUrl(int state) {
		String url = null;
		if (1 == state) {
			url = Config.ROOT_URL + "Company/Intention/OffSave";
		} else {
			url = Config.ROOT_URL + "Company/Offer/Update";
		}
		return url;
	}

	/**
	 * ���ǹ�Ӧ��-�ҵĹ�Ӧ-�ɹ��̼�ȫ���б�-��Ӧ����
	 * 
	 * @param goodSID
	 * @return
	 */
	public static String getMyProviderMerchantListUrl(String goodSID) {
		String url = Config.ROOT_URL + "/Company/Offer/Detail/%s";
		url = String.format(url, goodSID);
		return url;
	}

	/**
	 * ���ǹ�Ӧ��-�ҵ�����-ȷ������ Config.ROOT_URL + "Company/Intention/EditOffer";
	 * http://10.20.22.154:8090/webservice/Company/Intention/OffSave
	 * 
	 * @return
	 */
	public static String getMyProviderIntentionSureUrl() {

		String url = Config.ROOT_URL + "Company/Intention/OffSave";
		// String url =
		// "http://10.20.22.154:8090/webservice/Company/Intention/OffSave";
		return url;
	}

	/**
	 * ���ǲɹ��̡����ɹ����������б�URL(����)
	 * http://10.20.22.5/webservice/User/Order/GetUserOrder/5/2/1/10
	 * 
	 * @param userId
	 *            �û�ID
	 * @param state
	 *            ����״̬
	 * @param pageIndex
	 *            ҳ��
	 * @param pageSize
	 *            ÿҳ����
	 * @return
	 */
	public static String getPurIntentionFormListUrl(String userId, int state,
			int pageIndex, int pageSize) {

		String url = Config.ROOT_URL + "User/Order/GetUserOrder/%s/%s/%s/%s";
		return String.format(url, userId, state, pageIndex, pageSize);
	}

	/**
	 * ���ǲɹ��̡����ɹ����������б�URL(��Ʒ)
	 * http://10.20.22.5/webservice/User/Order/Order/5/2/1/10
	 * 
	 * @param userId
	 *            �û�ID
	 * @param state
	 *            ����״̬
	 * @param pageIndex
	 *            ҳ��
	 * @param pageSize
	 *            ÿҳ����
	 * @return
	 */
	public static String getPurProductFormListUrl(int state, int pageIndex,
			int pageSize) {

		String url = Config.ROOT_URL + "User/ProductOrder/%s/%s/%s";
		return String.format(url, state, pageIndex, pageSize);
	}

	/**
	 * ���ǲɹ���-�����б�-�������飨����
	 * http://10.20.22.5/webservice/User/Order/GetOrderDetail/222
	 * 
	 * @param goodSID
	 * @return
	 */
	public static String getMyPurIntentionFormDetailUrl(String formID) {
		String url = Config.ROOT_URL + "User/Order/GetOrderDetail/%s";
		url = String.format(url, formID);
		return url;
	}

	/**
	 * ���ǲɹ���-�����б�-�������� ����Ʒ�� http://10.20.22.5/webservice/User/Order/Detail/222
	 * http://u1q4567516.iask.in/cmallwebservice/Index/Product/{pi}
	 * 
	 * @param goodSID
	 * @return
	 */
	public static String getMyPurProductFormDetailUrl(String formID) {
		String url = Config.ROOT_URL + "User/ProductOrder/Detail/%s";
		url = String.format(url, formID);
		return url;
	}

	/**
	 * ���ǹ�Ӧ��-��Ӧ����-ȡ������
	 * 
	 * @param goodSID
	 * @return
	 */
	public static String getMyProviderIntentionCancelUrl(String goodSID) {
		String url = Config.ROOT_URL + "Company/Intention/Delete/%s";
		url = String.format(url, goodSID);
		return url;
	}

	/**
	 * <<<<<<< HEAD �ҵĹ�ע-��ע��Ӧ-����
	 * 
	 * @param id
	 * @return
	 */
	public static String getAttentionProviderDetailUrl(String id) {
		String url = Config.ROOT_URL
				+ "Company/EntrustOffer/GetEntrustOfferDetail/%s";
		url = String.format(url, id);
		return url;
	}

	/**
	 * �ҵĹ�ע-��ע�ɹ�-����
	 * 
	 * @param id
	 * @return
	 */
	public static String getAttentionPurchaseDetailUrl(String id) {
		String url = Config.ROOT_URL + "User/EntrustPurchase/EntrustDetail/%s";
		url = String.format(url, id);
		return url;
	}

	/**
	 * �ҵĹ�Ӧ-��Ӧ����-��ע�̼�
	 * 
	 * @return
	 */
	public static String getAttentionMerchantUrl() {
		String url = Config.ROOT_URL + "IndexUserFollows/AddCompany";
		return url;
	}

	/**
	 * ======= >>>>>>> 50abcfe8859f7b7cc44773fc2cb37a6963c0aacd �ҵĹ�ע-��ע��Ӧ
	 * 
	 * @param userid
	 * @param pageIndex
	 * @param pageSize
	 * @return Company/Follow/User User/Follows/FollowOffer/5/1/10
	 */
	public static String getAttentionProviderUrl(String companyId,
			int pageIndex, int pageSize) {
		String url = Config.ROOT_URL + "User/Follows/FollowOffer/%s/%d/%d";
		url = String.format(url, companyId, pageIndex, pageSize);
		return url;
	}

	/**
	 * ��ע�ɹ�
	 * 
	 * @param companyId
	 * @param pageIndex
	 * @param pageSize
	 *            http://10.20.22.5/webservice/Company/Follow/Purchase/35/1/10
	 * @return
	 */
	public static String getAttentionPurchaseListUrl(String companyId,
			int pageIndex, int pageSize) {
		String url = Config.ROOT_URL + "Company/Follow/Purchase/%s/%d/%d";
		url = String.format(url, companyId, pageIndex, pageSize);
		return url;
	}

	/**
	 * �ҵĹ�ע-��ע����
	 * 
	 * @param userId
	 * @param pageIndex
	 * @param pageSize
	 * @return User/Follows/ListPage
	 */
	public static String getAttentionLogisticsListUrl(String userId,
			int pageIndex, int pageSize) {
		String url = Config.ROOT_URL + "User/Follows/ListPage/%s/%d/%d";
		url = String.format(url, userId, pageIndex, pageSize);
		return url;
	}

	/**
	 * ���ǹ�Ӧ��-�ҵĶ���-��������
	 * 
	 * @param orderId
	 * @return
	 */
	public static String getProviderOrderDetailUrl(int orderId) {
		String url = Config.ROOT_URL + "Company/Order/GetOrderDetail/%d";
		url = String.format(url, orderId);
		return url;
	}

	/**
	 * ���ǹ�Ӧ��-�ҵĶ���-����
	 * 
	 * @param orderId
	 * @return
	 */
	public static String getProviderOrderSendGoodSUrl(int orderId) {
		String url = Config.ROOT_URL + "Company/Order/Deliver/%d";
		url = String.format(url, orderId);
		return url;
	}

	/**
	 * ���ǹ�Ӧ��-�ҵĶ���-�ܾ�����
	 * 
	 * @param orderId
	 * @return Company/Order/Refuse/1
	 */
	public static String getProviderOrderRefuseOrderUrl(int orderId) {
		String url = Config.ROOT_URL + "Company/Order/Refuse/%d";
		url = String.format(url, orderId);
		return url;
	}

	/**
	 * ���ǹ�Ӧ��-�ҵĶ���-ͬ���˿�
	 * 
	 * @param orderId
	 * @return Company/Order/AgreeRefund/1
	 */
	public static String getProviderOrderAgreenRefundUrl(int orderId) {
		String url = Config.ROOT_URL + "Company/Order/AgreeRefund/%d";
		url = String.format(url, orderId);
		return url;
	}

	/**
	 * ���ǹ�Ӧ��-�ҵĹ�Ӧ����-���飨���򵥣�
	 * 
	 * @param id
	 * @return Company/Intention/Detail Company/Intention/Detail
	 */
	public static String getProviderIntentionDetailsUrl(String id) {
		String url = Config.ROOT_URL + "Company/Intention/Detail/%s";
		url = String.format(url, id);
		return url;
	}

	/**
	 * ���ǲɹ��̡����ջ���ַURL http://10.20.22.5/webservice/User/Receiver/ListPage/5/1/10
	 * 
	 * @param userId
	 *            �û�ID
	 * @param pageIndex
	 *            ҳ��
	 * @param pageSize
	 *            ÿҳ����
	 * @return
	 */
	public static String getConsigneeUrl(String userId, int pageIndex,
			int pageSize) {

		String url = Config.ROOT_URL + "User/Receiver/ListPage/%s/%s/%s";
		return String.format(url, userId, pageIndex, pageSize);
	}

	/**
	 * ���ǲɹ��̡�����ӡ����ջ���ַURL http://10.20.22.5/webservice/User/Receiver/Add
	 * 
	 */
	public static String getAddConsigneeUrl() {

		String url = Config.ROOT_URL + "User/Receiver/Add";
		return url;
	}

	/**
	 * ���ǲɹ��̡�����ӡ��� ��ƱURL http://10.20.22.5/webservice/User/Invoice/Add
	 * 
	 */
	public static String getAddInvoiceUrl() {

		String url = Config.ROOT_URL + "User/Invoice/Add";
		return url;
	}

	/**
	 * ���ǲɹ��̡����༭�����ջ���ַURL http://10.20.22.5/webservice/User/Receiver/Edit
	 * 
	 */
	public static String getEditConsigneeUrl() {

		String url = Config.ROOT_URL + "User/Receiver/Edit";
		return url;
	}

	/**
	 * ���ǲɹ��̡���ɾ�������ջ���ַURL http://10.20.22.5/webservice/User/Receiver/Delete/320
	 * 
	 * @param consigneeId
	 * 
	 */
	public static String getDeleteConsigneeUrl(String consigneeId) {

		String url = Config.ROOT_URL + "User/Receiver/Delete/%s";
		return String.format(url, consigneeId);
	}

	/**
	 * ���ǲɹ��̡�����Ʊ��ϢURL http://10.20.22.5/webservice/User/Invoice/ListPage/5/1/10
	 * 
	 * @param userId
	 *            �û�ID
	 * @param pageIndex
	 *            ҳ��
	 * @param pageSize
	 *            ÿҳ����
	 * @return
	 */
	public static String getInVoiceUrl(String userId, int pageIndex,
			int pageSize) {

		String url = Config.ROOT_URL + "User/Invoice/ListPage/%s/%s/%s";
		return String.format(url, userId, pageIndex, pageSize);
	}

	/**
	 * ���ǲɹ��̡����༭���� ��ƱURL http://10.20.22.5/webservice/User/Invoice/Edit
	 * 
	 */
	public static String getEditInvoiceUrl() {

		String url = Config.ROOT_URL + "User/Invoice/Edit";
		return url;
	}

	/**
	 * ���ǲɹ��̡���ɾ��������ƱURLhttp://10.20.22.5/webservice/User/Invoice/Delete/323
	 * 
	 * @param consigneeId
	 * 
	 */
	public static String getDeleteInvoiceUrl(String invoiceId) {

		String url = Config.ROOT_URL + "User/Invoice/Delete/%s";
		return String.format(url, invoiceId);
	}

	/**
	 * ���ǹ�Ӧ��-��Ӧ����-�޸Ĺ�Ӧ��Ϣ Company/Intention/EditOffer
	 * 
	 * @return
	 */
	public static String getMyIntentionProviderDetailEditUrl() {
		String url = Config.ROOT_URL + "Company/Intention/EditOffer";
		return url;
	}

	/**
	 * ������Ļ������ݷ���
	 * 
	 * @return
	 */
	public static String getPublicUrl() {
		String url = Config.ROOT_URL + "IndexBaseData/getBaseInfo";
		return url;
	}

	/**
	 * ��¼
	 * 
	 * @return
	 */
	public static String getLoginURL() {
		// ��Ա���ĵ�¼
		// http://www.gsoft.cc/webservice/
		String url = Config.ROOT_URL + "IndexPlatLogin/Login";
		// String url = "http://10.20.22.5:8063/webservice/" +
		// "IndexPlatLogin/Login";
		// web�˵�¼
		// String url = Config.ROOT_URL + "IndexLogin/Login";
		return url;
	}

	/**
	 * ��Ӧ������-�ɹ���Ϣ���ĵ������Լ�����
	 * 
	 * @param id
	 * @return User/Intention/PurDetail
	 */
	public static String getUserIntentionDetailsUrl(String id) {
		String url = Config.ROOT_URL + "Company/Intention/PurDetail/%s";
		url = String.format(url, id);
		return url;
	}

	/**
	 * �ύ���� http://10.20.22.5/webservice/User/Order/Submit
	 * 
	 * @param userId
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public static String getCommitOrderUrl() {

		String url = Config.ROOT_URL + "User/Order/Submit";
		return url;
	}

	/**
	 * ���ǲɹ��̡����ҵĶ������������˿�URL http://10.20.22.5/webservice/User/Order/AppRefund/1
	 * 
	 * @param id
	 * @return
	 */
	public static String getIntentionTuiKuanUrl(String id) {
		String url = Config.ROOT_URL + "User/Order/AppRefund/%s";
		url = String.format(url, id);
		return url;
	}

	public static String getProductTuiKuanUrl(String id) {
		String url = Config.ROOT_URL + "User/ProductOrder/ApplyRefund/%s";
		url = String.format(url, id);
		return url;
	}

	/**
	 * ���ǲɹ��̡����ҵĶ�������ȷ���ջ�URL http://10.20.22.5/webservice/User/Order/SureAccept/1
	 * 
	 * @param id
	 * @return
	 */
	public static String getIntentionSureAcceptUrl(String id) {
		String url = Config.ROOT_URL + "User/Order/SureAccept/%s";
		url = String.format(url, id);
		return url;
	}

	public static String getProductSureAcceptUrl(String id) {
		String url = Config.ROOT_URL + "User/ProductOrder/Received/%s";
		url = String.format(url, id);
		return url;
	}

	/**
	 * ���ǲɹ��̡����ҵĶ�������ȡ������URL
	 * http://10.20.22.5/webservice/User/Order/CancelOrder/1
	 * 
	 * @param id
	 * @return
	 */
	public static String getIntentionCancelOrderUrl(String id) {
		String url = Config.ROOT_URL + "User/Order/CancelOrder/%s";
		url = String.format(url, id);
		return url;
	}

	public static String getProductCancelOrderUrl(String id) {
		String url = Config.ROOT_URL + "User/ProductOrder/Cancel/%s";
		url = String.format(url, id);
		return url;
	}

	/**
	 * ���ǲɹ��̡����ҵĶ�������ɾ������URL http://10.20.22.5/webservice/User/Order/Delete/1
	 * 
	 * @param id
	 * @return
	 */
	public static String getDeleteOrderUrl(String id) {
		String url = Config.ROOT_URL + "User/ProductOrder/Delete/%s";
		url = String.format(url, id);
		return url;
	}

	/**
	 * ���ǲɹ��̡����ҵĶ�����������URL http://10.20.22.5/webservice/User/Order/Complaint/493
	 * 
	 * @param id
	 * @return
	 */
	public static String getShenSuOrderUrl(String id) {
		String url = Config.ROOT_URL + "User/Order/Complaint/%s";
		url = String.format(url, id);
		return url;
	}

	/**
	 * --------------------------------------------------------------------���Խӿ�
	 * ���ǲɹ��̡����ҵĶ�����������ɹ�URL
	 * http://10.20.22.5/webservice/User/Order/PaymentSuccess/1
	 * 
	 * @param id
	 * @return
	 */
	public static String getPaymentSuccessUrl(String id) {
		String url = Config.ROOT_URL + "User/Order/PaymentSuccess/%s";
		url = String.format(url, id);
		return url;
	}

	/**
	 * --------------------------------------------------------------------���Խӿ�
	 * ���ǲɹ��̡����ҵĶ�����������ʧ��URL
	 * http://10.20.22.5/webservice/User/Order/PaymentFail/1
	 * 
	 * @param id
	 * @return
	 */
	public static String getPaymentFailUrl(String id) {
		String url = Config.ROOT_URL + "User/Order/PaymentFail/%s";
		url = String.format(url, id);
		return url;
	}

	/**
	 * ���ǲɹ��̡��������ɹ� http://10.20.22.5/webservice/User/Purchase/PubPurchase/318
	 * 
	 * @param id
	 * @return
	 */
	public static String getPubPurchaseUrl(int id) {
		String url = Config.ROOT_URL + "User/Purchase/PubPurchase/%s";
		url = String.format(url, id);
		return url;
	}

	/**
	 * ���ǲɹ��̡������������ɹ� http://10.20.22.5/webservice/User/Purchase/RevPurchase/318
	 * 
	 * @param id
	 * @return
	 */
	public static String getRevPurchaseUrl(int id) {
		String url = Config.ROOT_URL + "User/Purchase/RevPurchase/%s";
		url = String.format(url, id);
		return url;
	}

	/**
	 * ���ǹ�Ӧ��-�ҵĶ���-ȷ���տ�
	 * 
	 * @param orderId
	 * @return
	 * 
	 *         Company/Order/ConfirmReceivables
	 */
	public static String getProviderOrderSureMoneyUrl(int orderId) {
		String url = Config.ROOT_URL + "Company/Order/ConfirmReceivables/%s";
		url = String.format(url, orderId);
		return url;
	}

	/**
	 * ���ǹ�Ӧ��-�ҵĶ���-ɾ��
	 * 
	 * @param orderId
	 * @return
	 */
	public static String getProviderOrderdeleteUrl(int orderId) {
		String url = Config.ROOT_URL + "Company/Order/Delete/%s";
		url = String.format(url, orderId);
		return url;
	}

	/**
	 * ���ǹ�Ӧ��-�ҵĶ���-�ܾ��˿�
	 * 
	 * @param orderId
	 * @return
	 */
	public static String getProviderOrderRefuseMoneyUrl(int orderId) {
		String url = Config.ROOT_URL + "Company/Order/RefusedRefund/%s";
		url = String.format(url, orderId);
		return url;
	}

	/**
	 * �ҵ�ί��-ί������-����
	 * 
	 * @param goodSID
	 * @return
	 */
	public static String getLogisticsDetailsUrl(String goodSID) {
		String url = Config.ROOT_URL
				+ "User/EntrustLogisticse/EntrustLogisticseDetail/%s";
		url = String.format(url, goodSID);
		return url;
	}

	/**
	 * ���ǹ�Ӧ��-��Ӧ����-�ܾ�
	 * 
	 * @param id
	 * @return
	 */
	public static String getIntentionOrderRefuse(String id) {
		String url = Config.ROOT_URL + "Company/Intention/Refuse/%s";
		url = String.format(url, id);
		return url;
	}

	/**
	 * ���ǹ�Ӧ��-��Ӧ����-ɾ��
	 * 
	 * @param id
	 * @return
	 */
	public static String getIntentionOrderdelete(String id) {
		String url = Config.ROOT_URL + "Company/Intention/Delete/%s";
		url = String.format(url, id);
		return url;
	}

	/**
	 * ���ǹ�Ӧ��-�ҵĹ�Ӧ-������Ӧ����
	 * 
	 * @param goodSID
	 * @return
	 */
	public static String getPubOfferUrl(String goodSID) {
		String url = Config.ROOT_URL + "Company/Offer/PubOffer/%s";
		url = String.format(url, goodSID);
		return url;
	}

	/**
	 * ���ǹ�Ӧ��-�ҵĹ�Ӧ-������Ӧ����
	 * 
	 * @param goodSID
	 * @return
	 */
	public static String getRevOfferUrl(String goodSID) {
		String url = Config.ROOT_URL + "Company/Offer/RevOffer/%s";
		url = String.format(url, goodSID);
		return url;
	}

	/**
	 * �ҵ�ί��-�༭ί�вɹ���ί�й�Ӧ
	 * 
	 * @param state
	 * @return
	 */
	public static String getEditDelegationPurProUrl(int state) {
		String url = null;
		if (1 == state) {
			// ί�й�Ӧ
			url = Config.ROOT_URL + "Company/EntrustOffer/Edit";
		} else if (0 == state) {
			// ί�вɹ�
			url = Config.ROOT_URL + "User/EntrustPurchase/Edit";
		}
		return url;
	}

	/**
	 * �˳���¼
	 * 
	 * @return
	 */
	public static String getExitLoginUrl() {
		String url =  Config.ROOT_URL + "IndexLogin/Exit";
		return url;
	}

	/**
	 * ȡ����ע�ɹ���ȡ����ע��Ӧ
	 * 
	 * @param id
	 * @param state
	 * @return
	 */
	public static String getCancelAttentionPurchaseUrl(String id, int state) {
		String url = null;
		if (1 == state) {
			// ��ע��Ӧ
			url = Config.ROOT_URL + "User/Follows/Cancel/%s";
		} else if (2 == state) {
			// ��ע�̼�
			url = Config.ROOT_URL + "User/Follows/Cancel/%s";
		} else {
			// ��ע�ɹ�
			url = Config.ROOT_URL + "Company/Follow/CancelPur/%s";
		}

		url = String.format(url, id);

		return url;
	}

	/**
	 * ȡ����ע�ɹ���ȡ����ע��Ӧ-��ȡ����
	 * 
	 * @param id
	 * @param state
	 * @return
	 */
	public static String getAttentionPurchaseProDetailsUrl(
			GoodsSourceInfo info, int state) {
		String url = null;
		if (1 == state) {
			// ��ע��Ӧ
			url = Config.ROOT_URL + "User/Follows/FollowDetails/%s";
			url = String.format(url, info.getOfferid());
		} else if (0 == state) {
			// ��ע�ɹ�
			url = Config.ROOT_URL + "Company/Follow/FollowDetail/%s";
			url = String.format(url, info.getPurchaseid());
		} else if (2 == state) {
			// ��ע�̼�
			url = Config.ROOT_URL + "User/Follows/FollowComDetails/%s";
			url = String.format(url, info.getCompantId());
		}
		return url;
	}

	/**
	 * ȡ����ע�̼�
	 * 
	 * @param goodSID
	 * @return
	 */
	public static String getCancelAttentionCompanyUrl(String goodSID) {
		String url = Config.ROOT_URL + "User/Follows/Cancel/%s";
		url = String.format(url, goodSID);
		return url;
	}

	/**
	 * ���ǹ�Ӧ��-�ҵĹ�Ӧ����-ȡ������ ======= �����̼�-�ҵĹ�������-ȡ������ >>>>>>>
	 * 50abcfe8859f7b7cc44773fc2cb37a6963c0aacd
	 * 
	 * @param id
	 * @return
	 */
	public static String getIntentionOrderCancel(String id) {
		String url = Config.ROOT_URL + "Company/Intention/Cancel/%s";
		url = String.format(url, id);
		return url;
	}

	/**
	 * �ҵ�ί��-ί�вɹ�-ɾ��
	 * 
	 * @param id
	 * @return User/EntrustPurchase/Delete
	 */
	public static String getDeleteDelegationPurUril(String id) {
		String url = Config.ROOT_URL + "User/EntrustPurchase/Delete/%s";
		url = String.format(url, id);
		return url;
	}

	/**
	 * �ҵ�ί��-ɾ��ί������
	 * 
	 * @param id
	 * @return
	 */
	public static String getDeleteDelegationLogUril(String id) {
		String url = Config.ROOT_URL + "User/EntrustLogisticse/Delete/%s";
		url = String.format(url, id);
		return url;
	}

	/**
	 * �ҵ�ί��-ɾ��ί�й�Ӧ
	 * 
	 * @param id
	 * @return
	 */
	public static String getDeleteDelegationProUril(String id) {
		String url = Config.ROOT_URL + "Company/EntrustOffer/Delete/%s";
		url = String.format(url, id);
		return url;
	}

	/**
	 * ����֧���ӿ� http://10.20.22.5/webservice/User/Order/LinePay/PayWayCMBC/1
	 * 
	 * @param orderId
	 * @return
	 */
	public static String getZhiFuUrl(String orderId) {
		String url = Config.ROOT_URL + "User/Order/LinePay/PayWayCMBC/%s";
		url = String.format(url, orderId);
		return url;
	}

	/**
	 * �ҵĹ�Ӧ-��ϸ��Ϣ
	 * 
	 * @return
	 */
	public static String getProInfosUrl(String id) {
		String url = Config.ROOT_URL + "Company/Offer/OfferInfor/%s";
		url = String.format(url, id);
		return url;
	}

	/**
	 * ���ǹ�Ӧ��-ɾ���ҵĹ�Ӧ
	 * 
	 * @param intentionID
	 * @return
	 */
	public static String getMyProviderDetailDeleteUrl(String intentionID) {
		String url = Config.ROOT_URL + "Company/Offer/Delete/%s";
		url = String.format(url, intentionID);
		return url;
	}

	/**
	 * �ҵĲɹ�����ɾ�� http://10.20.22.5/webservice/User/Receiver/UpdatePurchaseState/1
	 * 
	 * @param purId
	 * @return
	 */
	public static String getDeletePurUrl(String intentId) {
		String url = Config.ROOT_URL + "User/Purchase/UpdatePurchaseState/%s";
		url = String.format(url, intentId);
		return url;
	}

	/**
	 * ��Ӧ����פHtml��Url
	 * http://10.20.22.5:8061/Member/Index?token=BZ6VELR7E9&sysId=100
	 * 
	 * @return
	 */
	public static String getCompanyEntryHtmlUrl(String token, String sysId) {
		// String url = Config.ROOT_URL + "";
		String url = "http://10.20.22.5:8061/Member/Index/%s/%s";
		url = String.format(url, token, sysId);
		return url;
	}

	/**
	 * �ֻ���̬��¼-������֤��
	 * 
	 * @return
	 */
	public static String getMobileLoginURL() {
		String url = Config.ROOT_URL + "IndexPlatLogin/LoginMobile";
		return url;
	}

	/**
	 * ��ȡ�ֻ�������֤��
	 * 
	 * @param phoneName
	 * @return
	 */
	public static String getMobileCodeURL(String phoneName) {
		String url = Config.ROOT_URL + "IndexPlatLogin/SendVcode/%s";
		url = String.format(url, phoneName);
		return url;
	}

	/**
	 * ��ҵ��Ա��¼ http://10.20.22.5/webservice/IndexPlatLogin/loginCompany
	 * 
	 * @return
	 */
	public static String getCompanyLoginURL() {
		String url = Config.ROOT_URL + "IndexPlatLogin/loginCompany";
		return url;
	}

	/**
	 * ֧��ʱ���ȡ������URL�������޸ķ�Ʊ�Լ��ջ�����Ϣ
	 * 
	 * @return
	 */
	public static String getIntentionZhiFuURL() {
		String url = Config.ROOT_URL + "User/Order/ToPay";
		return url;
	}

	public static String getProductZhiFuURL() {
		String url = Config.ROOT_URL + "User/Order/Pay/ToPay";
		return url;
	}

	/**
	 * ���򶩵�
	 * 
	 * @param companyId
	 * @param orderState
	 * @param pageIndex
	 * @param listItemCount
	 * @return
	 */
	public static String getIntentionOrderCategoryUrl(String userID,
			String orderState, int pageIndex, int listItemCount) {
		String url = Config.ROOT_URL + "Company/ProductOrder/%s/%d/%d";
		url = String.format(url, orderState, pageIndex, listItemCount);
		return url;
	}

	/**
	 * ��ע���̼�
	 * 
	 * @param userId
	 * @param pageIndex
	 * @param listItemCount
	 * @return
	 */
	public static String getAttentionMerUrl(String userId, int pageIndex,
			int listItemCount) {
		String url = Config.ROOT_URL + "User/Follows/ListPage/%s/%d/%d";
		url = String.format(url, userId, pageIndex, listItemCount);
		return url;
	}

	public static String getAttentionPurchaseUrl(int pageIndex,
			int listItemCount) {
		String url = Config.ROOT_URL + "Company/Follow/Purchase/%d/%d";
		url = String.format(url, pageIndex, listItemCount);
		return url;
	}

	/**
	 * ��ע����� Company/Follow/User
	 * 
	 * @param pageIndex
	 * @param listItemCount
	 * @return
	 */
	public static String getAttentionBuyerUrl(int pageIndex, int listItemCount) {
		String url = Config.ROOT_URL + "Company/Follow/User/%d/%d";
		url = String.format(url, pageIndex, listItemCount);
		return url;
	}

	/**
	 * ��ע������
	 * 
	 * @param pageIndex
	 * @param listItemCount
	 * @return
	 */
	public static String getAttentionLogisticsUrl(int pageIndex,
			int listItemCount) {
		String url = Config.ROOT_URL + "Company/Follow/Logistics/%d/%d";
		url = String.format(url, pageIndex, listItemCount);
		return url;
	}
	/**
	 * ��ע�Ĳ�Ʒ
	 * 
	 * @param pageIndex
	 * @param listItemCount
	 * @return
	 */
	public static String getAttentionProductUrl(int pageIndex,
			int listItemCount) {
		String url = Config.ROOT_URL + "User/Follow/Product/%d/%d";
		url = String.format(url, pageIndex, listItemCount);
		return url;
	}
	/**
	 * �ҵĲ�Ʒ
	 * 
	 * @param companyId
	 * @param pageIndex
	 * @param listItemCount
	 * @return
	 */
	public static String getProductUrl(String userId, int pageIndex,
			int listItemCount) {
		String url = Config.ROOT_URL + "Company/Product/%d/%d";
		url = String.format(url, pageIndex, listItemCount);
		return url;
	}
	/**
	 * ͷ��
	 * 
	 * @return
	 */
	public static String getPhotoStyleUrl() {
		String url = Config.ROOT_URL
				+ "User/HeadPortrait/GetImageSetAddress";
		return url;
	}
	/**
	 * �������
	 * 
	 * @return
	 */
	public static String getFeedBackUrl() {
		String url = Config.ROOT_URL + "Other/Suggestion/Add";
		return url;
	}
	/**
	 * ��ע�Ĳɹ�������
	 * 
	 * @param id
	 * @return
	 */
	public static String getAttentionBuyerDetailsUrl(String id) {
		String url = Config.ROOT_URL + "Company/Follow/UserDetail/%s";
		url = String.format(url, id);
		return url;
	}
	/**
	 * ȡ����ע�ɹ���
	 * 
	 * @param id
	 * @return
	 */
	public static String getCancelAttentionBuyerUrl(String id) {
		String url = Config.ROOT_URL + "Company/Follow/CancelPur/%s";
		url = String.format(url, id);
		return url;
	}
}
