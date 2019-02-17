package com.baiyi.cmall.request.manager;

import org.json.JSONObject;

import android.text.TextUtils;

import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.utils.Utils;

public class MorePublishManager {

	/**
	 * 首页 — 更多 — 委托采购Post数据
	 * 
	 * @param info
	 * 
	 * @return
	 */
	public static String getDelegatePurchasePostData(String userId, GoodsSourceInfo info) {

		StringBuilder builder = new StringBuilder();
		// 如果未登录，那么用户ID不用传
		if (null != userId&&!"".equals(userId)&& !"null".equals(userId)) {

			builder.append("userid").append("=").append(userId);
			builder.append("&");
		}
		builder.append("title").append("=").append(info.getGoodSTitle());
		builder.append("&");
		builder.append("details").append("=")
				.append(info.getGoodSPurchaseNeed());
		builder.append("&");
		builder.append("contact").append("=")
				.append(info.getGoodSContactPerson());
		builder.append("&");
		builder.append("mobile").append("=").append(info.getGoodSContactWay());
		builder.append("&");
		builder.append("companyname").append("=")
				.append(info.getGoodSMerchant());

		builder.append("&");
		builder.append("address").append("=").append(info.getAddress());
		builder.append("&");
		builder.append("quantity")
				.append("=")
				.append(Utils.getNumberOfString(info.getGoodSWeight() == null ? "0"
						: Utils.getNumberOfString(info.getGoodSWeight())));
		builder.append("&");
		builder.append("price")
				.append("=")
				.append(Utils.getNumberOfString(info.getGoodSPrice()) == null ? "0"
						: Utils.getNumberOfString(info.getGoodSPrice()));
		builder.append("&");
		builder.append("cityid").append("=")
				.append(info.getCityid() == null ? "0" : info.getCityid());
		builder.append("&");
		builder.append("deliverycityid")
				.append("=")
				.append(info.getDeliverycityid() == null ? "0" : info
						.getDeliverycityid());
		builder.append("&");
		builder.append("origincityid")
				.append("=")
				.append(info.getOrigincityid() == null ? "0" : info
						.getOrigincityid());
		builder.append("&");
		builder.append("category")
				.append("=")
				.append(info.getCategoryID() == null ? "0" : info
						.getCategoryID());
		builder.append("&");
		builder.append("brand")
				.append("=")
				.append(info.getGoodSBrand() == null ? "0" : info
						.getGoodSBrand());

		return builder.toString();
	}

	/**
	 * 首页 — 更多 — 委托供应Post数据
	 * 
	 * @param companyId
	 * 
	 * @param info
	 * 
	 * @return
	 */
	public static String getDelegateSupplyPostData(String companyId, GoodsSourceInfo info) {

		StringBuilder builder = new StringBuilder();
		if (null != companyId&&!"".equals(companyId)&&!"null".equals(companyId)) {
			builder.append("companyid").append("=").append(companyId);
			builder.append("&");
		}
		builder.append("title").append("=").append(info.getGoodSTitle());
		builder.append("&");
		builder.append("details").append("=").append(info.getGoodSContent());
		builder.append("&");
		builder.append("contact").append("=")
				.append(info.getGoodSContactPerson());
		builder.append("&");
		builder.append("mobile").append("=").append(info.getGoodSContactWay());
		builder.append("&");
		builder.append("companyname").append("=")
				.append(info.getGoodSMerchant());

		builder.append("&");
		builder.append("address").append("=").append(info.getAddress());
		builder.append("&");
		builder.append("quantity")
				.append("=")
				.append(Utils.getNumberOfString(info.getGoodSWeight() == null ? "0"
						: Utils.getNumberOfString(info.getGoodSWeight())));
		builder.append("&");
		builder.append("price")
				.append("=")
				.append(Utils.getNumberOfString(info.getGoodSPrice()) == null ? "0"
						: Utils.getNumberOfString(info.getGoodSPrice()));
		builder.append("&");
		builder.append("cityid").append("=")
				.append(info.getCityid() == null ? "0" : info.getCityid());
		builder.append("&");
		builder.append("deliverycityid")
				.append("=")
				.append(info.getDeliverycityid() == null ? "0" : info
						.getDeliverycityid());
		builder.append("&");
		builder.append("origincityid")
				.append("=")
				.append(info.getOrigincityid() == null ? "0" : info
						.getOrigincityid());
		builder.append("&");
		builder.append("category")
				.append("=")
				.append(info.getCategoryID() == null ? "0" : info
						.getCategoryID());
		builder.append("&");
		builder.append("brand")
				.append("=")
				.append(info.getGoodSBrand() == null ? "0" : info
						.getGoodSBrand());

		return builder.toString();
	}

	/**
	 * 首页 — 更多 — 委托物流Post数据
	 * 
	 * @param info
	 * 
	 * @return
	 */
	public static String getDelegateLogisticsPostData(String userId, GoodsSourceInfo info) {
		JSONObject json = new JSONObject();

		StringBuilder builder = new StringBuilder();
		// 用户ID
		if (!TextViewUtil.isStringEmpty(userId)) {
			builder.append("userid").append("=").append(userId);
			builder.append("&");
		}
		if (null != info) {
			builder.append("title").append("=").append(info.getGoodSTitle());
			builder.append("&");
			builder.append("details").append("=").append(info.getGoodSPurchaseContent());
			builder.append("&");
			builder.append("contact").append("=").append(info.getGoodSContactPerson());
			builder.append("&");
			builder.append("startaddress").append("=").append(info.getGoodSStartCity());
			builder.append("&");
			builder.append("destinationaddress").append("=").append(info.getGoodSEndCity());
			builder.append("&");
			builder.append("mobile").append("=").append(info.getGoodSContactWay());
			builder.append("&");
			builder.append("startcityid").append("=").append(info.getCityid() == null ? "0" : info.getCityid());
			builder.append("&");
			builder.append("destinationcityid").append("=")
					.append(info.getDeliverycityid() == null ? "0" : info.getDeliverycityid());
			builder.append("&");
			builder.append("quantity").append("=").append(info.getGoodSWeight() == null ? "0" : info.getGoodSWeight());
			builder.append("&");
			builder.append("starttime").append("=").append(Utils.getLongTime1(info.getGoodSStartTime()));
			builder.append("&");
			builder.append("destinationtime").append("=").append(Utils.getLongTime1(info.getGoodSEndTime()));
			builder.append("&");
			builder.append("deliverytype").append("=").append(info.getFreightWay() == null ? "0" : info.getFreightWay());
			builder.append("&");
			builder.append("packtype").append("=").append(info.getPackingWay() == null ? "0" : info.getPackingWay());
			builder.append("&");
			builder.append("companyname").append("=").append(info.getGoodSMerchant());

		}
		return builder.toString();
	}

}
