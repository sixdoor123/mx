package com.baiyi.cmall.activities.user.merchant.provider;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.user.merchant.MyProviderDetailsActivity;
import com.baiyi.cmall.activities.user.merchant.intention.ProviderIntentionOrderActivity;
import com.baiyi.cmall.entity.GoodSOriginInfo;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * ���ǹ�Ӧ��-�ҵĹ�Ӧ-ViewPager����������Ŀ
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-18 ����10:50:29
 */
public class MerchantProviderItem extends LinearLayout implements
		OnClickListener {

	// ������
	private Context context;

	public MerchantProviderItem(Context context) {
		this(context, null);
	}

	public MerchantProviderItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

		initView();
	}

	// �̼�����
	private TextView mTxtMerchantName;
	// �ط�
	private TextView mTxtMerchantPlace;
	// ����
	private TextView mTxtMerchantWeight;
	// ״̬
	private TextView mTxtMerchantState;
	// ����
	private TextView mTxtMerchantPrice;

	/**
	 * �������ֽ���
	 */
	private void initView() {
		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.item_5_view, null);
		addView(view);

		mTxtMerchantName = (TextView) view.findViewById(R.id.tv_content1);
		mTxtMerchantPlace = (TextView) view.findViewById(R.id.tv_content2);
		mTxtMerchantWeight = (TextView) view.findViewById(R.id.tv_content3);
		mTxtMerchantState = (TextView) view.findViewById(R.id.tv_content4);
		mTxtMerchantPrice = (TextView) view.findViewById(R.id.tv_content5);

		this.setOnClickListener(this);
	}

	// ����Դ
	private GoodsSourceInfo info;

	/**
	 * ��ʾ
	 * 
	 * @param info
	 */
	public void setDisplay(GoodsSourceInfo info) {
		this.info = info;
		mTxtMerchantName.setText(info.getPurchasename());
		mTxtMerchantPlace.setText(info.getCompanyName());
		mTxtMerchantWeight.setText(Utils.dealWeight(info.getInventory()) + "��");
		mTxtMerchantState.setText(info.getIntentionstatename());
		mTxtMerchantPrice.setText(Utils.dealPrice(info.getPrice()) + "Ԫ/��");
	}

	@Override
	public void onClick(View v) {
		((BaseActivity) context).goActivity(info,
				ProviderIntentionOrderActivity.class);
	}

}
