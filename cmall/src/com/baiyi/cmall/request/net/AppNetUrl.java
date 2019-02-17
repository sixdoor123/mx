package com.baiyi.cmall.request.net;

import android.text.TextUtils;
import com.baiyi.cmall.Config;
import com.baiyi.cmall.application.UserApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;

/**
 * 访问服务器 的URL
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-7 下午3:35:31
 */
public class AppNetUrl {

	/**
	 * 主页分类与广告和市场行情Url
	 * 
	 * @return
	 */
	public static String getMainPagerUrl() {
		String url = Config.ROOT_URL + "IndexProductBaseData";
		return url;
	}

	/**
	 * 货源列表的请求Url
	 * 
	 * @return
	 */
	public static String getGoodsListUrl() {
		String url = Config.ROOT_URL + "IndexOffer/ListPage";
		return url;
	}

	/**
	 * 货源供应详情的URL
	 * 
	 * @return
	 */
	public static String getGoodSDetailUrl(String id) {
		String url = Config.ROOT_URL + "IndexOffer/Detail/%s";
		url = String.format(url, id);
		return url;
	}

	/**
	 * 提交委托采购的URL
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
	 * 加入关注的URL
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
	 * 采购列表的请求------------------------------------------------------
	 * 
	 * @return
	 */
	public static String getPurchaseListUrl() {
		String url = Config.ROOT_URL + "IndexPurchase/ListPage";
		return url;
	}

	/**
	 * 采购列表详情URL
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
	 * 意向列表详情——供应URL http://10.20.22.5/webservice/User/Intention/OfferDetail/319
	 * 
	 * @return
	 */
	public static String getDetailProUrl(String proID) {
		String url = Config.ROOT_URL + "User/Intention/OfferDetail/%s";
		url = String.format(url, proID);
		return url;
	}

	/**
	 * 首页——采购源——委托供应URL
	 * 
	 * @return
	 */
	public static String getDelSupplyUrl() {
		String url = Config.ROOT_URL + "IndexOfferEntrust/AddPurchase";
		return url;
	}

	/**
	 * 关注采购的请求
	 * 
	 * @return
	 */
	public static String getPurchaseAttentionUrl() {
		String url = Config.ROOT_URL + "IndexCompanyFollows/AddPurchase";
		return url;
	}

	/**
	 * 更多——委托采购的请求
	 * 
	 * @return
	 */
	public static String getDelegatePurchaseUrl() {
		String url = Config.ROOT_URL + "IndexPurchaseEntrust/AddSystem";
		return url;
	}

	/**
	 * 更多——委托供应的请求
	 * 
	 * @return
	 */
	public static String getDelegateSupplyUrl() {
		String url = Config.ROOT_URL + "IndexOfferEntrust/AddSystem";
		return url;
	}

	/**
	 * 更多——委托物流的请求
	 * 
	 * @return
	 */
	public static String getDelegateLogistics() {
		String url = Config.ROOT_URL + "IndexLogisticsEntrust/Add";
		return url;
	}

	/**
	 * 市场行情URL
	 * 
	 * @return
	 */
	public static String getMarketUrl() {
		String url = Config.ROOT_URL + "IndexMarket/GetMarketInfo";
		return url;
	}

	/**
	 * 首页资讯列表URL
	 * 
	 * @return
	 */
	public static String getZiXunUrl() {
		String url = Config.ROOT_URL + "Index/News/1/10";
		return url;
	}

	/**
	 * 我是采购商——我的采购列表
	 * 
	 * @param userId
	 *            用户ID
	 * @param state
	 *            审核状态
	 * @param pageIndex
	 *            页码
	 * @param pageSize
	 *            每页条数
	 * @return
	 */
	public static String getMyPurchaseDetailUrl(String userId, int state,
			int pageIndex, int pageSize) {
		String url = Config.ROOT_URL
				+ "User/Purchase/GetUserPurchase/%s/%s/%s/%s";
		return String.format(url, userId, state, pageIndex, pageSize);
	}

	/**
	 * 更新我的采购 http://10.20.22.5/webservice/User/Purchase/PurInfor/731
	 * 
	 * @param purId
	 * @return
	 */
	public static String getUpdateDetailUrl(String purId) {
		String url = Config.ROOT_URL + "User/Purchase/PurInfor/%s";
		return String.format(url, purId);
	}

	/**
	 * 我是采购商——编辑采购界面——详情URL
	 * 
	 * @param purId
	 *            http://10.20.22.5/webservice/User/Purchase/EditPurchase/345
	 *            采购ID
	 * @return
	 */
	public static String getEditPurDetailUrl(int purId) {
		String url = Config.ROOT_URL + "User/Intention/PurEdit/%s";
		url = String.format(url, purId);
		return url;
	}

	/**
	 * 我是采购商——编辑采购界面——详情URL
	 * 
	 * @param purId
	 *            http://10.20.22.5/webservice/User/Purchase/EditPurchase/345
	 *            采购ID
	 * @return
	 */
	public static String getEditIntentionDetailUrl(int purId) {
		String url = Config.ROOT_URL + "User/Purchase/EditPurchase/%s";
		url = String.format(url, purId);
		return url;
	}

	/**
	 * 我是采购商——我的采购——详情URL
	 * 
	 * @param purId
	 *            采购ID
	 * @return
	 */
	public static String getPurDetailUrl(int purId) {
		String url = Config.ROOT_URL + "User/Purchase/GetPurchaseDetail/%s";
		url = String.format(url, purId);
		return url;
	}

	/**
	 * 我是采购商——我的采购——详情——编辑URL
	 * 
	 * @return
	 */
	public static String getEditPurDetailUrl() {
		String url = Config.ROOT_URL + "User/Purchase/EditPurchase";
		return url;
	}

	/**
	 * 我是采购商——采购意向——列表URL
	 * http://10.20.22.5/webservice/User/Intention/ListPage/1/1/1/1/10
	 * 
	 * @param userId
	 *            用户ID
	 * @param type
	 *            意向类型
	 * @param state
	 *            意向状态
	 * @param pageIndex
	 *            页码
	 * @param pageSize
	 *            每页条数
	 * @return
	 */
	public static String getPurAttentionListUrl(String userId, int type,
			int state, int pageIndex, int pageSize) {

		String url = Config.ROOT_URL + "User/Intention/ListPage/%s/%s/%s/%s/%s";
		return String.format(url, userId, type, state, pageIndex, pageSize);
	}

	/**
	 * 我是采购商——采购意向——采购方详情URL
	 * http://10.20.22.5/webservice/User/Intention/Detail/254
	 * 
	 * @param attentionId
	 *            意向ID
	 * @return
	 */
	public static String getPurAttentionDetailUrl(int attentionId) {

		String url = Config.ROOT_URL + "User/Intention/Detail/%s";
		url = String.format(url, attentionId);
		return url;
	}

	/**
	 * 我是采购商——采购意向——确认 http://10.20.22.5/webservice/User/Intention/EditPur
	 * 
	 * @param attentionId
	 *            意向ID
	 * @return
	 */
	public static String getEditPurAttentionUrl() {

		String url = Config.ROOT_URL + "User/Intention/PurSave";
		return url;
	}

	/**
	 * 我是采购商——采购意向——取消 http://10.20.22.5/webservice/User/Intention/Cancel/284
	 * 
	 * @param attentionId
	 *            意向ID
	 * @return
	 */
	public static String getCancelPurAttentionUrl(int attentionId) {

		String url = Config.ROOT_URL + "User/Intention/Cancel/%s";
		url = String.format(url, attentionId);
		return url;
	}

	/**
	 * 我是采购商——采购意向——刪除http://10.20.22.5/webservice/User/Intention/UpdateState/1
	 * 
	 * @param attentionId
	 *            意向ID
	 * @return
	 */
	public static String getDeletePurAttentionUrl(int attentionId) {

		String url = Config.ROOT_URL + "User/Intention/UpdateState/%s";
		url = String.format(url, attentionId);
		return url;
	}

	/**
	 * 我是采购商——采购意向——拒绝http://10.20.22.5/webservice/User/Intention/Refuse/1
	 * 
	 * @param attentionId
	 *            意向ID
	 * @return
	 */
	public static String getRefushPurAttentionUrl(int attentionId) {

		String url = Config.ROOT_URL + "User/Intention/Refuse/%s";
		url = String.format(url, attentionId);
		return url;
	}

	/**
	 * 我是采购商——采购意向——下单http://10.20.22.5/webservice/User/Intention/PlaceOrder/252
	 * / 5
	 * 
	 * @param attentionId
	 *            意向ID
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
	 * 请求委托物流列表的url
	 * 
	 * @param userid
	 *            用户ID
	 * @param pageIndex
	 *            页码
	 * @param pageSize
	 *            每页条数
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
	 * 我的委托——委托采购URL
	 * 
	 * @param userid
	 *            用户ID
	 * @param pageIndex
	 *            页码
	 * @param pageSize
	 *            每页条数
	 * @return
	 */
	public static String getLogisticsPurchaseListUrl(String userid,
			int pageIndex, int pageSize) {
		String url = Config.ROOT_URL + "User/EntrustPurchase/ListPage/%s/%d/%d";
		url = String.format(url, userid, pageIndex, pageSize);
		return url;
	}

	/**
	 * 我的委托——委托供应URL
	 * 
	 * @param userid
	 *            用户ID
	 * @param pageIndex
	 *            页码
	 * @param pageSize
	 *            每页条数
	 * @return
	 */
	public static String getLogisticsProviderListUrl(int pageIndex, int pageSize) {
		String url = Config.ROOT_URL + "Company/EntrustOffer/ListPage/%d/%d";
		url = String.format(url, pageIndex, pageSize);
		return url;
	}

	/**
	 * 委托供应详情URL
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
	 * 供应商中心（我是供应商） 我的供应
	 * 
	 * @param offerState
	 *            状态
	 * @param companyid
	 *            商家id
	 * @param pageSize
	 *            每页条数
	 * @param pageIndex
	 *            页码
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
	 * 我是供应商-我的供应订单
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
	 * 我是供应商-我的供应意向单
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
	 * 货源-详情-属性
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
	 * 编辑物流完成时的URL
	 * 
	 * @return
	 */
	public static String getEditLogisticsDetailUrl() {
		String url = Config.ROOT_URL + "User/EntrustLogisticse/Edit";
		return url;
	}

	/**
	 * 委托采购-详情URL
	 * 
	 * @param id
	 *            委托采购ID
	 * @return
	 */
	public static String getLogisticsPurchaseDetailsUrl(String id) {
		String url = Config.ROOT_URL + "User/EntrustPurchase/EntrustDetail/%s";
		url = String.format(url, id);

		// String url = Config.ROOT_URL + "IndexLogisticsEntrust/Add";
		return url;
	}

	/**
	 * 我是供应商-我的的供应-编辑供应详情 Company/Intention/EditOffer 包括属性
	 * 
	 * @return Company/Offer/EditOffer
	 */
	public static String getMyProviderDetailEditUrl(String id) {
		String url = Config.ROOT_URL + "Company/Offer/EditOffer/%s";
		url = String.format(url, id);
		return url;
	}

	/**
	 * 我是供应商-供应意向单-编辑供应详情 Company/Intention/EditOffer 包括属性
	 * 
	 * @return Company/Offer/EditOffer
	 */
	public static String getIntentionProviderDetailEditUrl(String id) {
		String url = Config.ROOT_URL + "Company/Intention/EditOffer/%s";
		url = String.format(url, id);
		return url;
	}

	/**
	 * 我是供应商-我的的供应-编辑供应详情 -完成 包括属性
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
	 * 我是供应商-供应意向单-编辑供应详情 -完成 包括属性
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
	 * 我是供应商-我的供应-采供商家全部列表-供应详情
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
	 * 我是供应商-我的意向单-确认意向单 Config.ROOT_URL + "Company/Intention/EditOffer";
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
	 * 我是采购商——采购订单——列表URL(意向)
	 * http://10.20.22.5/webservice/User/Order/GetUserOrder/5/2/1/10
	 * 
	 * @param userId
	 *            用户ID
	 * @param state
	 *            意向状态
	 * @param pageIndex
	 *            页码
	 * @param pageSize
	 *            每页条数
	 * @return
	 */
	public static String getPurIntentionFormListUrl(String userId, int state,
			int pageIndex, int pageSize) {

		String url = Config.ROOT_URL + "User/Order/GetUserOrder/%s/%s/%s/%s";
		return String.format(url, userId, state, pageIndex, pageSize);
	}

	/**
	 * 我是采购商——采购订单——列表URL(产品)
	 * http://10.20.22.5/webservice/User/Order/Order/5/2/1/10
	 * 
	 * @param userId
	 *            用户ID
	 * @param state
	 *            意向状态
	 * @param pageIndex
	 *            页码
	 * @param pageSize
	 *            每页条数
	 * @return
	 */
	public static String getPurProductFormListUrl(int state, int pageIndex,
			int pageSize) {

		String url = Config.ROOT_URL + "User/ProductOrder/%s/%s/%s";
		return String.format(url, state, pageIndex, pageSize);
	}

	/**
	 * 我是采购商-订单列表-订单详情（意向）
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
	 * 我是采购商-订单列表-订单详情 （产品） http://10.20.22.5/webservice/User/Order/Detail/222
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
	 * 我是供应商-供应意向-取消意向单
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
	 * <<<<<<< HEAD 我的关注-关注供应-详情
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
	 * 我的关注-关注采购-详情
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
	 * 我的供应-供应详情-关注商家
	 * 
	 * @return
	 */
	public static String getAttentionMerchantUrl() {
		String url = Config.ROOT_URL + "IndexUserFollows/AddCompany";
		return url;
	}

	/**
	 * ======= >>>>>>> 50abcfe8859f7b7cc44773fc2cb37a6963c0aacd 我的关注-关注供应
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
	 * 关注采购
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
	 * 我的关注-关注物流
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
	 * 我是供应商-我的订单-订单详情
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
	 * 我是供应商-我的订单-发货
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
	 * 我是供应商-我的订单-拒绝订单
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
	 * 我是供应商-我的订单-同意退款
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
	 * 我是供应商-我的供应意向-详情（意向单）
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
	 * 我是采购商——收货地址URL http://10.20.22.5/webservice/User/Receiver/ListPage/5/1/10
	 * 
	 * @param userId
	 *            用户ID
	 * @param pageIndex
	 *            页码
	 * @param pageSize
	 *            每页条数
	 * @return
	 */
	public static String getConsigneeUrl(String userId, int pageIndex,
			int pageSize) {

		String url = Config.ROOT_URL + "User/Receiver/ListPage/%s/%s/%s";
		return String.format(url, userId, pageIndex, pageSize);
	}

	/**
	 * 我是采购商——添加——收货地址URL http://10.20.22.5/webservice/User/Receiver/Add
	 * 
	 */
	public static String getAddConsigneeUrl() {

		String url = Config.ROOT_URL + "User/Receiver/Add";
		return url;
	}

	/**
	 * 我是采购商——添加—— 发票URL http://10.20.22.5/webservice/User/Invoice/Add
	 * 
	 */
	public static String getAddInvoiceUrl() {

		String url = Config.ROOT_URL + "User/Invoice/Add";
		return url;
	}

	/**
	 * 我是采购商——编辑——收货地址URL http://10.20.22.5/webservice/User/Receiver/Edit
	 * 
	 */
	public static String getEditConsigneeUrl() {

		String url = Config.ROOT_URL + "User/Receiver/Edit";
		return url;
	}

	/**
	 * 我是采购商——删除——收货地址URL http://10.20.22.5/webservice/User/Receiver/Delete/320
	 * 
	 * @param consigneeId
	 * 
	 */
	public static String getDeleteConsigneeUrl(String consigneeId) {

		String url = Config.ROOT_URL + "User/Receiver/Delete/%s";
		return String.format(url, consigneeId);
	}

	/**
	 * 我是采购商——发票信息URL http://10.20.22.5/webservice/User/Invoice/ListPage/5/1/10
	 * 
	 * @param userId
	 *            用户ID
	 * @param pageIndex
	 *            页码
	 * @param pageSize
	 *            每页条数
	 * @return
	 */
	public static String getInVoiceUrl(String userId, int pageIndex,
			int pageSize) {

		String url = Config.ROOT_URL + "User/Invoice/ListPage/%s/%s/%s";
		return String.format(url, userId, pageIndex, pageSize);
	}

	/**
	 * 我是采购商——编辑—— 发票URL http://10.20.22.5/webservice/User/Invoice/Edit
	 * 
	 */
	public static String getEditInvoiceUrl() {

		String url = Config.ROOT_URL + "User/Invoice/Edit";
		return url;
	}

	/**
	 * 我是采购商——删除——发票URLhttp://10.20.22.5/webservice/User/Invoice/Delete/323
	 * 
	 * @param consigneeId
	 * 
	 */
	public static String getDeleteInvoiceUrl(String invoiceId) {

		String url = Config.ROOT_URL + "User/Invoice/Delete/%s";
		return String.format(url, invoiceId);
	}

	/**
	 * 我是供应商-供应意向单-修改供应信息 Company/Intention/EditOffer
	 * 
	 * @return
	 */
	public static String getMyIntentionProviderDetailEditUrl() {
		String url = Config.ROOT_URL + "Company/Intention/EditOffer";
		return url;
	}

	/**
	 * 公共类的基础数据访问
	 * 
	 * @return
	 */
	public static String getPublicUrl() {
		String url = Config.ROOT_URL + "IndexBaseData/getBaseInfo";
		return url;
	}

	/**
	 * 登录
	 * 
	 * @return
	 */
	public static String getLoginURL() {
		// 会员中心登录
		// http://www.gsoft.cc/webservice/
		String url = Config.ROOT_URL + "IndexPlatLogin/Login";
		// String url = "http://10.20.22.5:8063/webservice/" +
		// "IndexPlatLogin/Login";
		// web端登录
		// String url = Config.ROOT_URL + "IndexLogin/Login";
		return url;
	}

	/**
	 * 供应商中心-采购信息对心的详情以及属性
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
	 * 提交订单 http://10.20.22.5/webservice/User/Order/Submit
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
	 * 我是采购商——我的订单——申请退款URL http://10.20.22.5/webservice/User/Order/AppRefund/1
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
	 * 我是采购商——我的订单——确认收货URL http://10.20.22.5/webservice/User/Order/SureAccept/1
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
	 * 我是采购商——我的订单——取消订单URL
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
	 * 我是采购商——我的订单——删除订单URL http://10.20.22.5/webservice/User/Order/Delete/1
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
	 * 我是采购商——我的订单——申诉URL http://10.20.22.5/webservice/User/Order/Complaint/493
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
	 * --------------------------------------------------------------------测试接口
	 * 我是采购商——我的订单——付款成功URL
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
	 * --------------------------------------------------------------------测试接口
	 * 我是采购商——我的订单——付款失败URL
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
	 * 我是采购商——发布采购 http://10.20.22.5/webservice/User/Purchase/PubPurchase/318
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
	 * 我是采购商——撤销发布采购 http://10.20.22.5/webservice/User/Purchase/RevPurchase/318
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
	 * 我是供应商-我的订单-确认收款
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
	 * 我是供应商-我的订单-删除
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
	 * 我是供应商-我的订单-拒绝退款
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
	 * 我的委托-委托物流-详情
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
	 * 我是供应商-供应意向单-拒绝
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
	 * 我是供应商-供应意向单-删除
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
	 * 我是供应商-我的供应-发布供应意向单
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
	 * 我是供应商-我的供应-撤销供应意向单
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
	 * 我的委托-编辑委托采购、委托供应
	 * 
	 * @param state
	 * @return
	 */
	public static String getEditDelegationPurProUrl(int state) {
		String url = null;
		if (1 == state) {
			// 委托供应
			url = Config.ROOT_URL + "Company/EntrustOffer/Edit";
		} else if (0 == state) {
			// 委托采购
			url = Config.ROOT_URL + "User/EntrustPurchase/Edit";
		}
		return url;
	}

	/**
	 * 退出登录
	 * 
	 * @return
	 */
	public static String getExitLoginUrl() {
		String url =  Config.ROOT_URL + "IndexLogin/Exit";
		return url;
	}

	/**
	 * 取消关注采购、取消关注供应
	 * 
	 * @param id
	 * @param state
	 * @return
	 */
	public static String getCancelAttentionPurchaseUrl(String id, int state) {
		String url = null;
		if (1 == state) {
			// 关注供应
			url = Config.ROOT_URL + "User/Follows/Cancel/%s";
		} else if (2 == state) {
			// 关注商家
			url = Config.ROOT_URL + "User/Follows/Cancel/%s";
		} else {
			// 关注采购
			url = Config.ROOT_URL + "Company/Follow/CancelPur/%s";
		}

		url = String.format(url, id);

		return url;
	}

	/**
	 * 取消关注采购、取消关注供应-获取详情
	 * 
	 * @param id
	 * @param state
	 * @return
	 */
	public static String getAttentionPurchaseProDetailsUrl(
			GoodsSourceInfo info, int state) {
		String url = null;
		if (1 == state) {
			// 关注供应
			url = Config.ROOT_URL + "User/Follows/FollowDetails/%s";
			url = String.format(url, info.getOfferid());
		} else if (0 == state) {
			// 关注采购
			url = Config.ROOT_URL + "Company/Follow/FollowDetail/%s";
			url = String.format(url, info.getPurchaseid());
		} else if (2 == state) {
			// 关注商家
			url = Config.ROOT_URL + "User/Follows/FollowComDetails/%s";
			url = String.format(url, info.getCompantId());
		}
		return url;
	}

	/**
	 * 取消关注商家
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
	 * 我是供应商-我的供应意向-取消意向单 ======= 我是商家-我的供货意向-取消意向单 >>>>>>>
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
	 * 我的委托-委托采购-删除
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
	 * 我的委托-删除委托物流
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
	 * 我的委托-删除委托供应
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
	 * 招行支付接口 http://10.20.22.5/webservice/User/Order/LinePay/PayWayCMBC/1
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
	 * 我的供应-详细信息
	 * 
	 * @return
	 */
	public static String getProInfosUrl(String id) {
		String url = Config.ROOT_URL + "Company/Offer/OfferInfor/%s";
		url = String.format(url, id);
		return url;
	}

	/**
	 * 我是供应商-删除我的供应
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
	 * 我的采购——删除 http://10.20.22.5/webservice/User/Receiver/UpdatePurchaseState/1
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
	 * 供应商入驻Html的Url
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
	 * 手机动态登录-发送验证码
	 * 
	 * @return
	 */
	public static String getMobileLoginURL() {
		String url = Config.ROOT_URL + "IndexPlatLogin/LoginMobile";
		return url;
	}

	/**
	 * 获取手机短信验证吗
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
	 * 企业会员登录 http://10.20.22.5/webservice/IndexPlatLogin/loginCompany
	 * 
	 * @return
	 */
	public static String getCompanyLoginURL() {
		String url = Config.ROOT_URL + "IndexPlatLogin/loginCompany";
		return url;
	}

	/**
	 * 支付时候获取的数据URL。可以修改发票以及收货人信息
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
	 * 意向订单
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
	 * 关注的商家
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
	 * 关注的买家 Company/Follow/User
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
	 * 关注的物流
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
	 * 关注的产品
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
	 * 我的产品
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
	 * 头像
	 * 
	 * @return
	 */
	public static String getPhotoStyleUrl() {
		String url = Config.ROOT_URL
				+ "User/HeadPortrait/GetImageSetAddress";
		return url;
	}
	/**
	 * 意见反馈
	 * 
	 * @return
	 */
	public static String getFeedBackUrl() {
		String url = Config.ROOT_URL + "Other/Suggestion/Add";
		return url;
	}
	/**
	 * 关注的采购商详情
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
	 * 取消关注采购商
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
