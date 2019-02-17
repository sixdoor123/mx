/**
 * 
 */
package com.baiyi.cmall.utils;

import java.io.Serializable;
import java.util.List;
import com.baiyi.cmall.QrCodeActivity;
import com.baiyi.cmall.activities.main.business.BusinessActivity;
import com.baiyi.cmall.activities.main.business.entity.CbmEntity;
import com.baiyi.cmall.activities.main.buy.OrderBuyActvity;
import com.baiyi.cmall.activities.main.buy.ShoppingCarActivity;
import com.baiyi.cmall.activities.main.buy.SubmitOrderActivity;
import com.baiyi.cmall.activities.main.buy.WebPayActivity;
import com.baiyi.cmall.activities.main.buy.detail.OrderDetailListActivity;
import com.baiyi.cmall.activities.main.buy.req.ReqOrder;
import com.baiyi.cmall.activities.main.header_top.PublishPurchaseActivity;
import com.baiyi.cmall.activities.main.header_top.PublishSupplyActivity;
import com.baiyi.cmall.activities.main.mall.MallCagetoryActivity;
import com.baiyi.cmall.activities.main.mall.MallDefine;
import com.baiyi.cmall.activities.main.mall.MallDetailActivity;
import com.baiyi.cmall.activities.msg.MyMsgListActivity;
import com.baiyi.cmall.activities.user.buyer.form.MyPurOrderActivity;
import com.baiyi.cmall.activities.user.merchant.product.detail.EvaluateMaintainActivity;

import android.content.Context;
import android.content.Intent;

/**
 * @author tangkun
 * 
 */
public class IntentClassChangeUtils {

	public static void startMsg(Context context) {
		Intent intents = new Intent(context, MyMsgListActivity.class);
		context.startActivity(intents);
	}

	public static void startPur(Context context) {
		Intent intent = new Intent(context, PublishPurchaseActivity.class);
		context.startActivity(intent);
	}

	public static void startPri(Context context) {
		Intent intent = new Intent(context, PublishSupplyActivity.class);
		context.startActivity(intent);
	}

	public static void startLogist(Context context) {
		Intent intent = new Intent(context, ShoppingCarActivity.class);
		context.startActivity(intent);
	}

	public static void startMallDetail(Context context, String pi) {

		Intent intent = new Intent(context, MallDetailActivity.class);
		intent.putExtra(MallDefine.PI, pi);
		context.startActivity(intent);
	}

	public static void startCategoryMall(Context context, String cc) {
		Intent intent = new Intent(context, MallCagetoryActivity.class);
		intent.putExtra(MallDefine.CC, cc);
		context.startActivity(intent);
	}

	public static void startShoppingCarActivity(Context context) {
		Intent intent = new Intent(context, ShoppingCarActivity.class);
		context.startActivity(intent);
	}

	// 商家详情
	public static void startShopDetailActivity(Context context, int ci) {
		Intent intent = new Intent(context, BusinessActivity.class);
		intent.putExtra(MallDefine.CI, ci);
		// intent.putExtra(MallDefine.Context, context);
		context.startActivity(intent);
	}

	public static void startOrderBuyActivity(Context context,
			List<ReqOrder> postReqOrderList, String totalPrice, String orderId) {
		Intent intent = new Intent(context, OrderBuyActvity.class);
		intent.putExtra(MallDefine.ReqOrder, (Serializable) postReqOrderList);
		intent.putExtra(MallDefine.OrderTotalPrice, totalPrice);
		intent.putExtra(MallDefine.OrderId, orderId);
		context.startActivity(intent);

	}

	public static void startPayWebActivity(Context context, String payUrl) {
		Intent intent = new Intent(context, WebPayActivity.class);
		intent.putExtra(MallDefine.PayWebUrl, payUrl);
		context.startActivity(intent);

	}

	public static void startOrderBuyActivity(Context context, Object result, String totalPrice) {

		Intent intent = new Intent(context, SubmitOrderActivity.class);
		intent.putExtra(MallDefine.Result, result.toString());
		intent.putExtra(MallDefine.OrderTotalPrice, totalPrice);
		context.startActivity(intent);
	}

	public static void startQrCodeActivity(Context context, int ci, CbmEntity cbmEntity) {
		Intent intent = new Intent(context, QrCodeActivity.class);
		intent.putExtra(MallDefine.QrCode, ci);
		intent.putExtra(MallDefine.CBM, cbmEntity);
		context.startActivity(intent);
	}

	/**
	 * 进入购买的商品列表
	 * 
	 * @param <T>
	 * @param context
	 * @param datas
	 */
	public static void startOrderDetailListActivity(Context context, String datas) {
		Intent intent = new Intent(context, OrderDetailListActivity.class);
		intent.putExtra(MallDefine.Result, datas);
		context.startActivity(intent);
	}

	public static void startMyPurOrderActivity(
			SubmitOrderActivity submitOrderActivity, int state) {
		Intent intent = new Intent(submitOrderActivity,
				MyPurOrderActivity.class);
		intent.putExtra(MallDefine.State, state);
		submitOrderActivity.startActivity(intent);
	}

	/**
	 * 商品分类
	 * 
	 * @param class1
	 * @param info
	 * @param i
	 */
	public static void startMallCagetoryActivity(Context context, String info,
			int i) {
		Intent intent = new Intent(context, MallCagetoryActivity.class);
		intent.putExtra(MallDefine.CC, info);
		intent.putExtra(MallDefine.State, i);
		context.startActivity(intent);
	}

	/**
	 * 进入评论列表
	 * 
	 * @param myProductlInfoPage
	 * @param id
	 */
	public static void startEvaluateMaintain(Context context, String id) {
		Intent intent = new Intent(context, EvaluateMaintainActivity.class);
		intent.putExtra(MallDefine.Id, id);
		context.startActivity(intent);
	}

}
