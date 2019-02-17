package com.baiyi.cmall.activities.main.provider.viewpager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.MyLinearLayout;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;
import android.view.View.OnClickListener;

/**
 * ��������
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-23 ����9:38:49
 */
public class ProviderDetailsViewPager extends BaseProviderDetailsViewPager
		implements OnClickListener {

	public ProviderDetailsViewPager(Context context, String id, String userID,
			GoodsSourceInfo sourceInfo) {
		super(context);
		this.id = id;
		this.userID = userID;
		this.sourceInfo = sourceInfo;
	}

	// ��Ӧ������
	private TextView mTxtDetailMerchant;
	// ����
	private TextView mTxtProviderName;
	// ����
	private TextView mTxtDetailCategory;
	// Ʒ��
	private TextView mTxtDetailBreed;
	// ����
	private TextView mTxtDetailArea;
	// ���
	private TextView mTxtDetailInventory;
	// �����
	private TextView mTxtDetailDelivery;
	// �۸�
	private TextView mTxtDetailPrice;
	// Ԥ�����
	private TextView mTxtDetailPayment;
	// ����ʱ��
	private TextView mTxtDetailPutTime;
	// �ɹ�����
	private TextView mTxtDetailPurNeed;

	/**
	 * ��ʼ�������б�
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_provider_information_content, null);
		layout.addView(view);

		findViewById(view);

		updateView();
	}

	// ������
	private MyLoadingBar loadingBar;

	/**
	 * �ҿؼ�
	 * 
	 * @param view
	 */
	private void findViewById(View view) {

		mTxtDetailMerchant = (TextView) view
				.findViewById(R.id.txt_detail_merchant);
		mTxtProviderName = (TextView) view.findViewById(R.id.txt_provider_name);
		mTxtDetailCategory = (TextView) view
				.findViewById(R.id.txt_detail_category);
		mTxtDetailBreed = (TextView) view.findViewById(R.id.txt_detail_breed);
		mTxtDetailArea = (TextView) view.findViewById(R.id.txt_detail_area);
		mTxtDetailInventory = (TextView) view
				.findViewById(R.id.txt_detail_weight_01);
		mTxtDetailPrice = (TextView) view.findViewById(R.id.txt_detail_price);

		mTxtDetailDelivery = (TextView) view
				.findViewById(R.id.txt_detail_delivery);
		mTxtDetailPayment = (TextView) view
				.findViewById(R.id.txt_detail_put_time);
		mTxtDetailPutTime = (TextView) view
				.findViewById(R.id.txt_detail_price_range);
		mTxtDetailPurNeed = (TextView) view
				.findViewById(R.id.txt_detail_price_way);

		loadingBar = (MyLoadingBar) view.findViewById(R.id.load);
	}

	/**
	 * ���½�������
	 */
	private void updateView() {
		if (null != info) {
			mTxtDetailMerchant.setText(info.getGoodSMerchant());
			mTxtProviderName.setText(info.getGoodSName());
			mTxtDetailCategory.setText(info.getGoodSCategory());
			mTxtDetailBreed.setText(info.getGoodSBrand());
			mTxtDetailArea.setText(info.getGoodSPlace());
			mTxtDetailInventory.setText(info.getGoodSWeight() + "��");
			mTxtDetailPrice.setText(info.getGoodSPrePrice() + "Ԫ/��");
			mTxtDetailDelivery.setText(info.getGoodSDelivery());
			mTxtDetailPayment.setText(info.getPrepayment() + "Ԫ");
			mTxtDetailPutTime.setText(info.getGoodSPutTime());
			mTxtDetailPurNeed.setText(info.getGoodSDetails());
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initContentView(MyLinearLayout layout) {
		if (-1 == resultInfo.getStatus()) {
			initNotContent();
		} else {
			initContent();
		}
	}

	
}
