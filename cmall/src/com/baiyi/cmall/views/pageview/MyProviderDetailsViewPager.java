package com.baiyi.cmall.views.pageview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.pageviews.BaseScrollViewPageView;
import com.baiyi.cmall.views.itemview.MyLinearLayout;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

import android.view.View.OnClickListener;

/**
 * ���ǹ�Ӧ��-�ҵĹ�Ӧ-�ɹ�����
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-23 ����9:38:49
 */
@SuppressLint("CutPasteId")
public class MyProviderDetailsViewPager extends BaseScrollViewPageView
		implements OnClickListener {

	// ����Դ
	private GoodsSourceInfo info;
	// ������
	private Context context;
	// ��Ʒ ID
	private String id;
	// �û�ID
	private String userID;

	// �Ƿ���ʾ�༭
	// private int state;
	private MyLinearLayout layout;

	public MyProviderDetailsViewPager(Context context, GoodsSourceInfo info) {
		super(context);
		this.context = context;
		this.info = info;
		// this.state = state;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		layout = new MyLinearLayout(context);
		addView(layout);
//		userID = XmlUtils.getInstence(context).getXmlStringValue("userID");
		// initData();
		initContent();
	}

	// ��Ӧ������
	private TextView mTxtDetailMerchant;
	// ����
	private TextView mTxtDetailName;
	// ����
	private TextView mTxtDetailCategory;
	// ����
	private TextView mTxtDetailArea;
	// Ʒ��
	private TextView mTxtDetailBreed;
	// ����
	private TextView mTxtDetailWeight;
	//�۸�
	private TextView mTxtDetailPrice;
	//Ԥ�����
	private TextView mTxtDetailPrepayment;
	// �����
	private TextView mTxtDetailDelivery;
	//��ϸ����
	private TextView mTxtDetailContent;
	// ����ʱ��
	private TextView mTxtDetailPutTime;

	/**
	 * ��ʼ�������б�
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_provider_details_content, null);
		layout.addView(view);

		// ����ʵ����
		findViewById(view);

		updateView();
	}

	/**
	 * �ҿؼ�
	 * 
	 * @param view
	 */
	private void findViewById(View view) {

		mTxtDetailMerchant = (TextView) view
				.findViewById(R.id.txt_detail_merchant);
		mTxtDetailName = (TextView) view.findViewById(R.id.txt_detail_name);
		mTxtDetailCategory = (TextView) view
				.findViewById(R.id.txt_detail_category);
		mTxtDetailArea = (TextView) view.findViewById(R.id.txt_detail_area);
		mTxtDetailBreed = (TextView) view.findViewById(R.id.txt_detail_breed);
		mTxtDetailWeight = (TextView) view.findViewById(R.id.txt_detail_weight);
		mTxtDetailPrice = (TextView) view.findViewById(R.id.txt_detail_weight);
		mTxtDetailPrepayment = (TextView) view.findViewById(R.id.txt_detail_prepayment);
		mTxtDetailDelivery = (TextView) view
				.findViewById(R.id.txt_detail_delivery);
		mTxtDetailContent = (TextView) view
				.findViewById(R.id.txt_detail_content);
		mTxtDetailPutTime = (TextView) view
				.findViewById(R.id.txt_detail_put_time);

		mTxtDetailWeight = (TextView) view.findViewById(R.id.txt_detail_price);
	}

	/**
	 * ���½�������
	 */

	private void updateView() {
		if (null != info) {
			
			mTxtDetailMerchant.setText(info.getGoodSMerchant());
			mTxtDetailName.setText(info.getGoodSName());
			mTxtDetailCategory.setText(info.getGoodSCategory());
			mTxtDetailArea.setText(info.getGoodSPlace());
			mTxtDetailBreed.setText(info.getGoodSBrand());
			mTxtDetailWeight.setText(info.getGoodSPrePrice()+"Ԫ/��");
			mTxtDetailPrice.setText(info.getGoodSWeight()+"��");
			mTxtDetailPrepayment.setText(info.getPrepayment()+"Ԫ");
			mTxtDetailDelivery.setText(info.getGoodSDelivery());
			mTxtDetailContent.setText(info.getGoodSDetails());
			mTxtDetailPutTime.setText(info.getGoodSPutTime());
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

	/**
	 * ����¼�
	 */
	@Override
	public void onClick(View v) {
	}
}
