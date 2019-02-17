package com.baiyi.cmall.activities.user.merchant.order.product.detail;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.home.itemview.DividerItemDecoration;
import com.baiyi.cmall.activities.main.home.itemview.SyLinearLayoutManager;
import com.baiyi.cmall.activities.user.merchant.order.BaseMerchantOrderActivity;
import com.baiyi.cmall.activities.user.merchant.order.manager.MerchantOrderManager;
import com.baiyi.cmall.activities.user.merchant.order.product.adapter.ProductOrderDetailAdapter;
import com.baiyi.cmall.model.RequestNetResultInfo;
import com.baiyi.core.util.ContextUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.port.OnRecycleViewItemClickListener;

import android.support.v7.widget.DefaultItemAnimator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 产品订单详情
 * 
 * @author sunxy
 */
public class ProductOrderDetailActivity extends BaseMerchantOrderActivity
		implements OnClickListener, OnRecycleViewItemClickListener {

	private RequestNetResultInfo resultInfo;

	// http://u1q4567516.iask.in/cmallwebservice/Company/ProductOrder/Detail/{oi}
	@Override
	public String getOrderDetailUrl(int orderId) {
		// TODO Auto-generated method stub
		return Config.ROOT_URL + "Company/ProductOrder/Detail/" + orderId;
	}

	@Override
	public void parseDetailDatas(Object arg1) {
		Log.d("TAG", "产品详情 ：\n" + arg1);
		resultInfo = MerchantOrderManager.parseProductDetail(arg1);

		if (null != resultInfo) {
			if (1 != resultInfo.getStatus()) {
				displayToast(resultInfo.getMsg());
				return;
			}
			odm = resultInfo.getOdm();
		}

	}

	@Override
	public void updateView() {
		if (null != odm) {
			// 订单号
			mTxtOrderNumber.setText(String.format(getResources().getString(R.string.form_num), odm.getId()));
			// 交易状态
			// mTxtTradeState.setText(odm.getOs());
			// 收货人
			mTxtConsignee.setText(odm.getRp());

			// 收货地址
			mTxtAcceptAddress.setText(odm.getAd());
			// 发票类型
			mTxtBillType.setText(odm.getIt());
			// 发票抬头
			mTxtBillRise.setText(odm.getIc());
			// 发票内容
			mTxtBillContent.setText(odm.getIi());
			// 意向编号
			mTxtIntentionNum.setText(odm.getOi());
			// 地区
			mTxtOrderEare.setText(odm.getDq());

			// 联系人
			mTxtTelPhone.setText(odm.getPh());
		}
	}

	private XRecyclerView mRecyclerView = null;
	// 适配器去
	private ProductOrderDetailAdapter adapter = null;

	private View view = null;
	private SyLinearLayoutManager layout = null;

	@SuppressWarnings("rawtypes")
	@Override
	public void initUnlikeView() {
		view = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_shop_bus, null);

		win_content.addView(view);
		
		mRecyclerView = (XRecyclerView) view.findViewById(R.id.recycleview);
		// 设置布局管理器
		layout = new SyLinearLayoutManager(this);
		mRecyclerView.setLayoutManager(layout);

		// 设置Item增加、移除动画
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		// 添加分割线
		mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
		// 设置adapter
		adapter = new ProductOrderDetailAdapter(this);
		mRecyclerView.setAdapter(adapter);
		
		mRecyclerView.setPullRefreshEnabled(false);
		mRecyclerView.setLoadingMoreEnabled(false);

		// 条目点击事件
		adapter.setItemClickListener(this);
	}


	@SuppressWarnings("unchecked")
	@Override
	public void updateUnlikeView() {

		adapter.setDatas(odm.getPdms());
//		displayToast("长度: "+odm.getPdms().size());
		
	}

	/**
	 * 条目的点击事件
	 * 
	 * @param view
	 * @param position
	 * @param t
	 *            数据
	 */
	@Override
	public <T> void onItemClick(View view, int position, T t) {
	}

	/**
	 * 拒绝退款
	 */
	@Override
	public String providerOrderRefuseMoneyUrl(int orderId) {
		String url = Config.ROOT_URL + "Company/ProductOrder/RefusedRefund/%d";
		url = String.format(url, orderId);
		return url;
	}

	/**
	 * 删除订单
	 */
	@Override
	public String providerOrderdeleteUrl(int orderId) {
		String url = Config.ROOT_URL + "Company/ProductOrder/Delete/%d";
		url = String.format(url, orderId);
		return url;
	}

	/**
	 * 确认收货
	 */
	@Override
	public String providerOrderSureMoneyUrl(int orderId) {
		String url = Config.ROOT_URL + "/%d";
		url = String.format(url, orderId);
		return url;
	}

	/**
	 * 同意退款
	 */
	@Override
	public String providerOrderAgreenRefundUrl(int orderId) {
		String url = Config.ROOT_URL + "Company/ProductOrder/UnRefund/%d";
		url = String.format(url, orderId);
		return url;
	}

	/**
	 * 拒绝订单
	 */
	@Override
	public String providerOrderRefuseOrderUrl(int orderId) {
		String url = Config.ROOT_URL + "/%d";
		url = String.format(url, orderId);
		return url;
	}

	/**
	 * 确认发货
	 */
	@Override
	public String providerOrderSendGoodSUrl(int orderId) {
		String url = Config.ROOT_URL + "Company/ProductOrder/UnConfirm/%d";
		url = String.format(url, orderId);
		return url;
	}

	/**
	 * 进入意向详情
	 */
	@Override
	public void goIntentionDetail() {

	}

}
