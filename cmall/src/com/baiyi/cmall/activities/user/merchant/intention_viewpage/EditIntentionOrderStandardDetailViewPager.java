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
 * 编辑采购详情-属性ViewPager
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-4 下午2:30:27
 */
public class EditIntentionOrderStandardDetailViewPager extends
		BaseScrollViewPageView implements OnClickListener {

	// 上下文
	private Context context;
	// 数据源
	private ArrayList<IntentionDetailStandardInfo> datas;

	// 自定义的线性布局
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
	 * 内容
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_edit_standard_arg, null);
		layout.addView(view);

		// 找控件
		findViewById(view);
		// 刷新界面
		updateDataToView();
	}

	// 发热量Qnet
	private EditText mEdtCalorificValue;
	// 全水份Mt
	private EditText mEdtAllWater;
	// 内水Mad
	private EditText mEdtInWaters;
	// 挥发份
	private EditText mEdtVolatileMatter;
	// 灰份
	private EditText mEdtAshContent;
	// 全硫份
	private EditText mEdtTotalSulfur;
	// 固定碳
	private EditText mEdtFixedCarbon;
	// 粒度
	private EditText mEdtGranularity;
	// 密度
	private EditText mEdtDensity;
	// 完成按钮
	private TextView mBtnComplete;
	// 取消按钮
	private TextView mBtnCancel;

	/**
	 * 找控件
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
		mBtnComplete.setText("完成");
		mBtnComplete.setOnClickListener(this);
		mBtnCancel = (TextView) view.findViewById(R.id.btn_cancel_modify);
		mBtnCancel.setText("取消");
		mBtnCancel.setOnClickListener(this);
	}

	/**
	 * 更新界面数据
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
		case R.id.btn_commit_modify:// 完成按钮
			editComplete();
			break;
		case R.id.btn_cancel_modify:// 取消按钮
			((BaseActivity) context).finish();
			break;

		default:
			break;
		}
	}

	/**
	 * 完成编辑
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
