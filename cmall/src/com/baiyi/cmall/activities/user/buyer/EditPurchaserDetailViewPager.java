package com.baiyi.cmall.activities.user.buyer;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main._public.AutoSelectView;
import com.baiyi.cmall.activities.main._public.CitySelectionActivity;
import com.baiyi.cmall.activities.main._public.AutoSelectView.AutoOnClickListener;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.itemview.DateSelectorView;
import com.baiyi.cmall.views.itemview.DateSelectorView.OnDateSelectedClickListener;
import com.baiyi.cmall.views.pageview.BaseEditMyPurchaseDetailViewPager;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 
 * 编辑采购详情-采购信息ViewPager
 * 
 * @author lizl
 * 
 */
public class EditPurchaserDetailViewPager extends
		BaseEditMyPurchaseDetailViewPager implements OnClickListener {

	private View view;
	private EditText mEtPurName;// 求購名称
	private TextView mTvClassify;// 分类
	private TextView mTvVariety;// 品种
	private TextView mTvPlace;// 产地
	private EditText mEtWeight;// 数量
	private EditText mEtPriceRange;// 价格
	private EditText mEtPricePre;// 预付费
	private TextView mTvPubTime;// 发布时间
	private TextView mTvEndTime;// 结束时间
	private TextView mTvAutoStart;// 自动开
	private TextView mTvAutoEnd;// 自动关
	private TextView mTvDelivery;// 交割地
	private EditText mEtNeed;// 采购需求

	private TableLayout mTbrIsShow;// 判断是否显示：发布时间/自动开始选择

	/**
	 * 选择的布局
	 */
	private TableRow mTrbPutTime;// 发布时间选择
	private TableRow mTrbEndtime;// 结束时间选择
	private TableRow mTrbAutoStart;// 自动开始选择
	private TableRow mTrbAutoEnd;// 自动结束选择
	private TableRow mTrbDelivery;// 交割地选择

	private String deliveryId;// 交割地ID

	public EditPurchaserDetailViewPager(Context context, int purID,
			boolean isShow) {

		super(context, purID, isShow);

		initView();
	}

	/**
	 * 初始化显示控件
	 */
	private void initView() {
		view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_edit_purchaser_details, null);
		mEtPurName = (EditText) view.findViewById(R.id.et_edit_pur_name);
		mTvClassify = (TextView) view.findViewById(R.id.tv_edit_classify);
		mTvVariety = (TextView) view.findViewById(R.id.tv_edit_variety);
		mTvPlace = (TextView) view.findViewById(R.id.tv_edit_place);
		mEtWeight = (EditText) view.findViewById(R.id.et_edit_weight);
		mEtPriceRange = (EditText) view.findViewById(R.id.tv_edit_price_range);
		mEtPricePre = (EditText) view.findViewById(R.id.tv_edit_price_pre);
		mTvPubTime = (TextView) view.findViewById(R.id.tv_edit_pub_time);
		mTvEndTime = (TextView) view.findViewById(R.id.tv_edit_end_time);
		mTvAutoStart = (TextView) view.findViewById(R.id.tv_edit_auto_start);
		mTvAutoEnd = (TextView) view.findViewById(R.id.tv_edit_auto_end);
		mTvDelivery = (TextView) view.findViewById(R.id.tv_edit_delivery_di);
		mEtNeed = (EditText) view.findViewById(R.id.et_edit_need);

		mTbrIsShow = (TableLayout) view.findViewById(R.id.lin_is_show);

		mTrbPutTime = (TableRow) view.findViewById(R.id.trb_pub_time);
		mTrbEndtime = (TableRow) view.findViewById(R.id.trb_end_time);
		mTrbAutoStart = (TableRow) view.findViewById(R.id.trb_auto_start);
		mTrbAutoEnd = (TableRow) view.findViewById(R.id.trb_auto_end);
		mTrbDelivery = (TableRow) view.findViewById(R.id.trb_delivery_di);

		mTrbPutTime.setOnClickListener(this);
		mTrbEndtime.setOnClickListener(this);
		mTrbAutoStart.setOnClickListener(this);
		mTrbAutoEnd.setOnClickListener(this);
		mTrbDelivery.setOnClickListener(this); 

		TextViewUtil.setText(R.id.purname, "名称", view);
		TextViewUtil.setText(R.id.weight, "数量", view);
		TextViewUtil.setText(R.id.price, "价格", view);
		TextViewUtil.setText(R.id.prepayment, "预付金额", view);
		TextViewUtil.setText(R.id.delivery, "交割地", view);
		TextViewUtil.setText(R.id.put_time, "发布时间", view);
		TextViewUtil.setText(R.id.end_time, "结束时间", view);
		TextViewUtil.setText(R.id.auto_start, "到期自动开始", view);
		TextViewUtil.setText(R.id.auto_end, "到期自动结束", view);
	}

	/**
	 * 获取数据完成后添加——内容
	 */
	@Override
	public void initContent() {

		layout.addView(view);

		setShow();
	}

	/**
	 * 显示数据
	 */
	private void setShow() {

		// 设置控件内容
		mEtPurName.setText(info.getGoodSName());
		mTvClassify.setText(info.getGoodSCategory());
		mTvVariety.setText(info.getGoodSBrand());
		mTvPlace.setText(info.getGoodSPlace());
		mEtWeight.setText(info.getGoodSWeight() + "");

		String price = Utils.twoDecimals(info.getGoodSpriceInterval());
		String prepayMent = Utils.twoDecimals(info.getGoodSPrePrice());
		mEtPriceRange.setText(price);
		mEtPricePre.setText(prepayMent + "");
		mTvPubTime.setText(info.getGoodSPutTime() + "");
		mTvEndTime.setText(info.getGoodSEndTime() + "");
		mTvAutoStart.setText(info.isAutoStart() ? "是" : "否");
		mTvAutoEnd.setText(info.isAutoEnd() ? "是" : "否");
		mTvDelivery.setText(info.getGoodSDelivery());
		mEtNeed.setText(info.getGoodSPurchaseContent());
		// 首次获取交割地的ID
		deliveryId = info.getDeliverycityid();

		/*
		 * 根据不同状态显示与否
		 */
		if (!isShow) {
			mTbrIsShow.setVisibility(View.GONE);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.trb_pub_time:
			selectTime("开始日期", 1);
			break;
		case R.id.trb_end_time:
			selectTime("结束日期", 2);
			break;
		case R.id.trb_auto_start:// 自动开始时间
			String textStart = mTvAutoStart.getText().toString();
			autoSelect(1, textStart);
			break;
		case R.id.trb_auto_end:// 自动结束时间
			String textEnd = mTvAutoEnd.getText().toString();
			autoSelect(2, textEnd);
			break;
		case R.id.trb_delivery_di:// 交割地选择

			Intent intent = new Intent(context, CitySelectionActivity.class);
			intent.putExtra("temp", mTvDelivery.getText().toString());
			((BaseActivity) context).startActivityForResult(intent,
					NumEntity.STATE_DEVALITE);
			break;

		}
	}

	/**
	 * 到期自动开始/結束选择
	 */
	private void autoSelect(final int state, String defaultText) {

		AutoSelectView autoSelectView = null;
		if (1 == state) {
			autoSelectView = new AutoSelectView(context, "到期自动开始", 2);
		} else {
			autoSelectView = new AutoSelectView(context, "到期自动结束", 2);
		}
		// 回调选择的数据
		autoSelectView.setInfoOnClickListener(new AutoOnClickListener() {

			@Override
			public void onClickListener(String content) {

				if (1 == state) {
					mTvAutoStart.setText(content);
				} else {
					mTvAutoEnd.setText(content);
				}
			}
		});
		// 显示
		autoSelectView.show();
		// 设置默认的数据为选择状态
		autoSelectView.setDefault(defaultText);

	}

	/**
	 * 开始/结束日期
	 */
	private void selectTime(String title, final int state) {

		DateSelectorView view = new DateSelectorView(context, title);
		view.show();
		view.setListener(new OnDateSelectedClickListener() {

			@Override
			public void onDateSelected(String date) {

				if (1 == state) {

					mTvPubTime.setText(date);
				} else {

					mTvEndTime.setText(date);
				}

			}
		});
	}

	/**
	 * 添加所有的控件
	 * 
	 * @return
	 */
	public ArrayList<TextView> getAllText() {

		ArrayList<TextView> textViews = new ArrayList<TextView>();
		textViews.add(mEtPurName);
		textViews.add(mEtWeight);
		textViews.add(mEtPriceRange);
		textViews.add(mEtPricePre);

		// 编辑采购信息时有这三个字段，在意向编辑时，没有这三个字段
		if (isShow) {
			textViews.add(mTvPubTime);
			textViews.add(mTvEndTime);
			textViews.add(mTvAutoStart);
			textViews.add(mTvAutoEnd);
		}

		textViews.add(mTvDelivery);
		textViews.add(mEtNeed);
		return textViews;
	}

	/**
	 * 返回交割地ID信息
	 * 
	 * @return
	 */
	public String getDeliveryId() {
		return deliveryId;
	}

	/**
	 * 返回发布时间
	 * 
	 * @return
	 */
	public String getPubTime() {
		return mTvPubTime.getText().toString();
	}

	/**
	 * 返回結束时间
	 * 
	 * @return
	 */
	public String getEndTime() {
		return mTvEndTime.getText().toString();
	}

	/**
	 * 获取编辑的新的交割地地址
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (null == data) {
			return;
		}
		// 交割地
		if (NumEntity.STATE_DEVALITE == requestCode) {

			SelectedInfo info = (SelectedInfo) data.getSerializableExtra("key");

			if (null != info) {

				deliveryId = info.getId();
				mTvDelivery.setText(data.getStringExtra("text"));
			}
		}
	}

	/**
	 * 设置编辑框不可操作
	 */
	public void setNoEdit() {
		mEtPurName.setEnabled(false);
		mEtWeight.setEnabled(false);
		mEtPriceRange.setEnabled(false);
		mEtPricePre.setEnabled(false);
		mTrbDelivery.setEnabled(false);
		mEtNeed.setEnabled(false);
	}

	/**
	 * 设置编辑框不可操作
	 */
	public void setPurNameNoEdit() {
		mEtPurName.setEnabled(false);
	}
}
