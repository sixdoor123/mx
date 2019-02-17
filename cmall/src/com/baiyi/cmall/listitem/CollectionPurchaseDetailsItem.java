package com.baiyi.cmall.listitem;

import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * �ղ���ϸ��Ϣ����Ŀ
 * 
 * @author sunxy
 * 
 */
public class CollectionPurchaseDetailsItem extends LinearLayout {

	private Context context;

	// ����
	private TextView mTxtSubCategory;
	// ����
	private TextView mTxtSubWeight;
	// �۸�����
	private TextView mTxtSubPriceRange;
	// ����ʱ��
	private TextView mTxtSubPutTime;
	// ����
	private TextView mTxtSubArea;

	public CollectionPurchaseDetailsItem(Context context) {
		super(context);
		this.context = context;
		initView(context);
	}

	/**
	 * ��ʼ������
	 * 
	 * @param context
	 */
	private void initView(Context context) {
		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.collection_purchase_details_item, null);
		addView(view);
		view.setBackgroundResource(R.drawable.listview_bg_selector);
		mTxtSubCategory = (TextView) view.findViewById(R.id.txt_sub_category);
		mTxtSubWeight = (TextView) view.findViewById(R.id.txt_sub_weight);
		mTxtSubPriceRange = (TextView) view
				.findViewById(R.id.txt_sub_price_range);
		mTxtSubArea = (TextView) view.findViewById(R.id.txt_sub_area);
		mTxtSubPutTime = (TextView) view.findViewById(R.id.txt_sub_put_time);
	}

	/**
	 * ������ʾ����
	 * @param info
	 */
	public void displayData(GoodsSourceInfo info) {
		mTxtSubCategory.setText(info.getGoodSCategory());
		mTxtSubWeight.setText(info.getGoodSWeight() + "��");
		mTxtSubPriceRange.setText(info.getGoodSpriceInterval());
		mTxtSubArea.setText(info.getGoodSArea());
		mTxtSubPutTime.setText(info.getGoodSPutTime());
	}

}
