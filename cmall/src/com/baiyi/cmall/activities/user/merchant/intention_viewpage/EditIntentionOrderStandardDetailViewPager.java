package com.baiyi.cmall.activities.user.merchant.intention_viewpage;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.IntentionDetailStandardInfo;
import com.baiyi.cmall.entity.IntentionProviderDetailInfo;
import com.baiyi.cmall.pageviews.BasePageView;
import com.baiyi.cmall.pageviews.BaseScrollViewPageView;
import com.baiyi.cmall.views.itemview.MyLinearLayout;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;
import android.view.View.OnClickListener;

/**
 * �༭�ɹ�����-����ViewPager
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-4 ����2:30:27
 */
public class EditIntentionOrderStandardDetailViewPager extends
		BaseScrollViewPageView implements OnClickListener {

	// ������
	private Context context;
	// ����Դ
	private ArrayList<IntentionDetailStandardInfo> datas;

	// �Զ�������Բ���
	private MyLinearLayout layout;

	public EditIntentionOrderStandardDetailViewPager(Context context,
			ArrayList<IntentionDetailStandardInfo> datas) {
		super(context);
		this.context = context;
		this.datas = datas;
		layout = new MyLinearLayout(context);
		initContent();
		addView(layout);
	}

	/**
	 * ����
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_edit_standard_arg, null);
		layout.addView(view);

		// �ҿؼ�
		findViewById(view);
		// ˢ�½���
		updateDataToView();
	}

	// ������Qnet
	private EditText mEdtCalorificValue;
	// ȫˮ��Mt
	private EditText mEdtAllWater;
	// ��ˮMad
	private EditText mEdtInWaters;
	// �ӷ���
	private EditText mEdtVolatileMatter;
	// �ҷ�
	private EditText mEdtAshContent;
	// ȫ���
	private EditText mEdtTotalSulfur;
	// �̶�̼
	private EditText mEdtFixedCarbon;
	// ����
	private EditText mEdtGranularity;
	// �ܶ�
	private EditText mEdtDensity;
	// ��ɰ�ť
	private TextView mBtnComplete;
	// ȡ����ť
	private TextView mBtnCancel;

	/**
	 * �ҿؼ�
	 * 
	 * @param view
	 */
	private void findViewById(View view) {
		mEdtCalorificValue = (EditText) view
				.findViewById(R.id.txt_calorific_value);
		mEdtAllWater = (EditText) view.findViewById(R.id.txt_all_water);
		mEdtInWaters = (EditText) view.findViewById(R.id.txt_in_waters);
		mEdtVolatileMatter = (EditText) view
				.findViewById(R.id.txt_volatile_matter);
		mEdtAshContent = (EditText) view.findViewById(R.id.txt_ash_content);
		mEdtTotalSulfur = (EditText) view.findViewById(R.id.txt_total_sulfur);
		mEdtFixedCarbon = (EditText) view.findViewById(R.id.txt_fixed_carbon);
		mEdtGranularity = (EditText) view.findViewById(R.id.txt_granularity);
		mEdtDensity = (EditText) view.findViewById(R.id.txt_density);
		mBtnComplete = (TextView) view.findViewById(R.id.btn_commit_modify);
		mBtnComplete.setText("���");
		mBtnComplete.setOnClickListener(this);
		mBtnCancel = (TextView) view.findViewById(R.id.btn_cancel_modify);
		mBtnCancel.setText("ȡ��");
		mBtnCancel.setOnClickListener(this);
	}

	/**
	 * ���½�������
	 */
	private void updateDataToView() {
		// mEdtCalorificValue.setText(info.getGoodSCalorificValue());
		// mEdtAllWater.setText(info.getGoodSTotalMoisture());
		// mEdtInWaters.setText(info.getGoodSInlandWaters());
		// mEdtVolatileMatter.setText(info.getGoodSVolatileMatter());
		// mEdtAshContent.setText(info.getGoodSAshContent());
		// mEdtTotalSulfur.setText(info.getGoodSTotalSulfur());
		// mEdtFixedCarbon.setText(info.getGoodSFixedCarbon());
		// mEdtGranularity.setText(info.getGoodSParticleSize());
		// mEdtDensity.setText(info.getGoodSDensity());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_commit_modify:// ��ɰ�ť
			editComplete();
			break;
		case R.id.btn_cancel_modify:// ȡ����ť
			((BaseActivity) context).finish();
			break;

		default:
			break;
		}
	}

	/**
	 * ��ɱ༭
	 */
	private void editComplete() {
		((BaseActivity) context).finish();
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

}
