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
 * ��Ʒ��������
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
		Log.d("TAG", "��Ʒ���� ��\n" + arg1);
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
			// ������
			mTxtOrderNumber.setText(String.format(getResources().getString(R.string.form_num), odm.getId()));
			// ����״̬
			// mTxtTradeState.setText(odm.getOs());
			// �ջ���
			mTxtConsignee.setText(odm.getRp());

			// �ջ���ַ
			mTxtAcceptAddress.setText(odm.getAd());
			// ��Ʊ����
			mTxtBillType.setText(odm.getIt());
			// ��Ʊ̧ͷ
			mTxtBillRise.setText(odm.getIc());
			// ��Ʊ����
			mTxtBillContent.setText(odm.getIi());
			// ������
			mTxtIntentionNum.setText(odm.getOi());
			// ����
			mTxtOrderEare.setText(odm.getDq());

			// ��ϵ��
			mTxtTelPhone.setText(odm.getPh());
		}
	}

	private XRecyclerView mRecyclerView = null;
	// ������ȥ
	private ProductOrderDetailAdapter adapter = null;

	private View view = null;
	private SyLinearLayoutManager layout = null;

	@SuppressWarnings("rawtypes")
	@Override
	public void initUnlikeView() {
		view = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_shop_bus, null);

		win_content.addView(view);
		
		mRecyclerView = (XRecyclerView) view.findViewById(R.id.recycleview);
		// ���ò��ֹ�����
		layout = new SyLinearLayoutManager(this);
		mRecyclerView.setLayoutManager(layout);

		// ����Item���ӡ��Ƴ�����
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		// ��ӷָ���
		mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
		// ����adapter
		adapter = new ProductOrderDetailAdapter(this);
		mRecyclerView.setAdapter(adapter);
		
		mRecyclerView.setPullRefreshEnabled(false);
		mRecyclerView.setLoadingMoreEnabled(false);

		// ��Ŀ����¼�
		adapter.setItemClickListener(this);
	}


	@SuppressWarnings("unchecked")
	@Override
	public void updateUnlikeView() {

		adapter.setDatas(odm.getPdms());
//		displayToast("����: "+odm.getPdms().size());
		
	}

	/**
	 * ��Ŀ�ĵ���¼�
	 * 
	 * @param view
	 * @param position
	 * @param t
	 *            ����
	 */
	@Override
	public <T> void onItemClick(View view, int position, T t) {
	}

	/**
	 * �ܾ��˿�
	 */
	@Override
	public String providerOrderRefuseMoneyUrl(int orderId) {
		String url = Config.ROOT_URL + "Company/ProductOrder/RefusedRefund/%d";
		url = String.format(url, orderId);
		return url;
	}

	/**
	 * ɾ������
	 */
	@Override
	public String providerOrderdeleteUrl(int orderId) {
		String url = Config.ROOT_URL + "Company/ProductOrder/Delete/%d";
		url = String.format(url, orderId);
		return url;
	}

	/**
	 * ȷ���ջ�
	 */
	@Override
	public String providerOrderSureMoneyUrl(int orderId) {
		String url = Config.ROOT_URL + "/%d";
		url = String.format(url, orderId);
		return url;
	}

	/**
	 * ͬ���˿�
	 */
	@Override
	public String providerOrderAgreenRefundUrl(int orderId) {
		String url = Config.ROOT_URL + "Company/ProductOrder/UnRefund/%d";
		url = String.format(url, orderId);
		return url;
	}

	/**
	 * �ܾ�����
	 */
	@Override
	public String providerOrderRefuseOrderUrl(int orderId) {
		String url = Config.ROOT_URL + "/%d";
		url = String.format(url, orderId);
		return url;
	}

	/**
	 * ȷ�Ϸ���
	 */
	@Override
	public String providerOrderSendGoodSUrl(int orderId) {
		String url = Config.ROOT_URL + "Company/ProductOrder/UnConfirm/%d";
		url = String.format(url, orderId);
		return url;
	}

	/**
	 * ������������
	 */
	@Override
	public void goIntentionDetail() {

	}

}
