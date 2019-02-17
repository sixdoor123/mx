package com.baiyi.cmall.activities.user.merchant.provider.intention.viewpager;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main._public.AutoSelectView;
import com.baiyi.cmall.activities.main._public.AutoSelectView.AutoOnClickListener;
import com.baiyi.cmall.activities.main._public.CitySelectionActivity;
import com.baiyi.cmall.activities.user.buyer.HintUtils;
import com.baiyi.cmall.activities.user.merchant.provider.viewpager.ProDataUtils;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.DateSelectorView;
import com.baiyi.cmall.views.itemview.DateSelectorView.OnDateSelectedClickListener;
import com.baiyi.core.util.ContextUtil;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * 我是供应商-我的供应-编辑供应详情
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-4 下午2:24:25
 */
public class EditProviderIntentionDetailViewPager extends
		BeseEditIntentionProviderViewPager implements OnClickListener {

	// 状态 不为1 是：我是供应商-我的供应进入
	// 状态 为1 是： 我是供应商-供应意向进入
	// 影藏
	private boolean isProNameEdit;
	
	public EditProviderIntentionDetailViewPager(Context context, String id,
			int state, boolean isEditDetailEnable, boolean isProNameEdit) {
		super(context, id);
		this.state = state;
		this.isProNameEdit = isProNameEdit;
		this.isEditDetailEnable = isEditDetailEnable;
	}

	/**
	 * 内容
	 */
	@Override
	public void initContent() {
		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_edit_provider_details, null);
		layout.addView(view);

		findViewById(view);
		updateEdtView();
	}

	// 名称
	private EditText mEdtGoodSNam;
	// 控制分类的条目
	// private TableRow mLlGoodSCategory;
	// 显示分类的
	private TextView mTxtCategory;
	// 控制品牌种的条目
	// private TableRow mLlGoodSBrand;
	// 显示品牌
	private TextView mTxtBrand;
	// 控制产地的条目
	// private TableRow mLlConductPlace;
	// 显示产地
	private TextView mTxtPlace;
	// 数量 库存
	private EditText mEdtGoodSInventory;
	// 价格
	private EditText mEdtGoodSPric;
	// 预付金额
	private EditText mEdtGoodSPrepayment;
	// 发布时间
	private TableRow mLlGoodSPutTime;
	// 显示发布时间
	private TextView mTxtPutTime;
	// 结束时间
	private TableRow mLlGoodSEndTime;
	// 显示结束时间
	private TextView mTxtEndTime;
	// 控制到期自动开
	private TableRow mLlAutoStart;
	// 显示是否制动开
	private TextView mTxtStart;
	// 控制到期自动结束
	private TableRow mLlAutoEnd;
	// 显示是否自动关
	private TextView mTxtOver;
	// 控制交割地的条目
	private TableRow mLlGoodSDelivery;
	// 显示交割地
	private TextView mTxtDelivery;
	// 详细内容
	private EditText mTxtDetails;

	/**
	 * 找控件
	 * 
	 * @param view
	 */
	private void findViewById(View view) {

		// 名称
		mEdtGoodSNam = (EditText) view.findViewById(R.id.edt_goods_name_01);
		// 显示分类的
		mTxtCategory = (TextView) view.findViewById(R.id.txt_catagory);
		// 显示品牌
		mTxtBrand = (TextView) view.findViewById(R.id.txt_brand);
		// 显示产地
		mTxtPlace = (TextView) view.findViewById(R.id.txt_place);
		// 数量 库存
		mEdtGoodSInventory = (EditText) view
				.findViewById(R.id.edt_goods_inventory);
		// 价格
		mEdtGoodSPric = (EditText) view.findViewById(R.id.edt_goods_price);
		// 预付金额
		mEdtGoodSPrepayment = (EditText) view
				.findViewById(R.id.edt_goods_prepayment);
		// 发布时间
		mLlGoodSPutTime = (TableRow) view
				.findViewById(R.id.ll_conduct_put_time);
		// 显示发布时间
		mTxtPutTime = (TextView) view.findViewById(R.id.txt_put_time);
		// 结束时间
		mLlGoodSEndTime = (TableRow) view
				.findViewById(R.id.ll_conduct_end_time);
		// 显示结束时间
		mTxtEndTime = (TextView) view.findViewById(R.id.txt_end_time);
		// 控制到期自动开
		mLlAutoStart = (TableRow) view.findViewById(R.id.ll_conduct_auto_start);
		// 显示是否制动开
		mTxtStart = (TextView) view.findViewById(R.id.txt_auto_start);
		// 控制到期自动结束
		mLlAutoEnd = (TableRow) view.findViewById(R.id.ll_conduct_auto_over);
		// 显示是否自动关
		mTxtOver = (TextView) view.findViewById(R.id.txt_auto_over);
		// 控制交割地的条目
		if (1 == state) {
			mLlGoodSPutTime.setVisibility(View.GONE);
			mLlGoodSEndTime.setVisibility(View.GONE);
			mLlAutoStart.setVisibility(View.GONE);
			mLlAutoEnd.setVisibility(View.GONE);
			view.findViewById(R.id.line_01).setVisibility(View.GONE);
			view.findViewById(R.id.line_02).setVisibility(View.GONE);
			view.findViewById(R.id.line_03).setVisibility(View.GONE);
			view.findViewById(R.id.line_04).setVisibility(View.GONE);
		}

		mLlGoodSDelivery = (TableRow) view
				.findViewById(R.id.ll_conduct_delivery);
		// 显示交割地
		mTxtDelivery = (TextView) view.findViewById(R.id.txt_delivery);
		// 详细内容
		mTxtDetails = (EditText) view.findViewById(R.id.txt_details_01);

		loadingBar = (MyLoadingBar) view.findViewById(R.id.load);

		mLlGoodSPutTime.setOnClickListener(this);
		mLlGoodSEndTime.setOnClickListener(this);
		mLlAutoStart.setOnClickListener(this);
		mLlAutoEnd.setOnClickListener(this);
		mLlGoodSDelivery.setOnClickListener(this);

		TextViewUtil.setText(R.id.pro_name, "名称", view);
		TextViewUtil.setText(R.id.invo, "库存", view);
		TextViewUtil.setText(R.id.price, "价格", view);
		TextViewUtil.setText(R.id.prepayment, "预付金额", view);
		TextViewUtil.setText(R.id.delivery, "交割地", view);
		TextViewUtil.setText(R.id.put_time, "发布时间", view);
		TextViewUtil.setText(R.id.end_time, "结束时间", view);
		TextViewUtil.setText(R.id.atuo_start, "到期自动开", view);
		TextViewUtil.setText(R.id.auto_end, "到期自动关", view);

		// 盘点是否可操作
		if (!isEditDetailEnable) {
			orperationInput();
		}

		if (state != 9) {
			if (!isProNameEdit) {
				mEdtGoodSNam.setEnabled(false);
			}
		}

		mTxtDetails.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				// 这句话说的意思告诉父View我自己的事件我自己处理
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}

		});
	}

	/**
	 * 判断是否可操作 不可操作变灰
	 */
	private void orperationInput() {
		mEdtGoodSInventory.setEnabled(false);
		mEdtGoodSNam.setEnabled(false);
		mEdtGoodSPrepayment.setEnabled(false);
		mEdtGoodSPric.setEnabled(false);
		mTxtDetails.setEnabled(false);

		mLlGoodSDelivery.setEnabled(false);
		mLlAutoEnd.setEnabled(false);
		mLlAutoStart.setEnabled(false);

	}

	// 记录开始时间
	private String startTime;
	// 记录结束时间
	private String endTime;

	/**
	 * 更新输入框中的内容
	 */
	private void updateEdtView() {
		if (null != info) {
			endTime = info.getGoodSEndTime();
			startTime = info.getGoodSPutTime();
			// 名称
			mEdtGoodSNam.setText(info.getGoodSName());
			// 显示分类的
			mTxtCategory.setText(info.getGoodSCategory());
			// 显示品牌
			mTxtBrand.setText(info.getGoodSBrand());
			// 显示产地
			mTxtPlace.setText(info.getGoodSPlace());
			// 数量 库存
			mEdtGoodSInventory.setText(info.getGoodSInventory());
			// 价格
			String prioce = Utils.twoDecimals(info.getGoodSPrePrice());
			if (prioce.startsWith(".")) {
				prioce = "0" + prioce;
			}
			mEdtGoodSPric.setText(prioce);
			// 预付金额
			String prepayMent = Utils.twoDecimals(info.getPrepayment().equals(
					"null") ? String.valueOf(0) : String.valueOf(info
					.getPrepayment()));
			if (prepayMent.startsWith(".")) {
				prepayMent = "0" + prepayMent;
			}
			mEdtGoodSPrepayment.setText(prepayMent);
			// 显示发布时间
			mTxtPutTime.setText(info.getGoodSPutTime());
			// 显示结束时间
			mTxtEndTime.setText(info.getGoodSEndTime());
			// 显示是否制动开
			mTxtStart.setText(info.isAutoStart() ? "是" : "否");
			// 显示是否自动关
			mTxtOver.setText(info.isAutoEnd() ? "是" : "否");
			// 显示交割地
			mTxtDelivery.setText(info.getGoodSDelivery());
			// 详细内容
			mTxtDetails.setText(info.getGoodSDetails());
		}
	}

	// 修改的信息
	// private GoodsSourceInfo sourceInfo;

	/**
	 * 获取输入的商品的信息 封装成实体类
	 * 
	 * @return
	 */
	@Override
	public boolean getInputGoodSInfo() {
		String proName = mEdtGoodSNam.getText().toString().trim();
		if (TextUtils.isEmpty(proName)) {
			((BaseActivity)(context)).displayToast("名称不能为空");
			return false;
		}
		if (!Utils.isExceed_30(proName)) {
			((BaseActivity)(context)).displayToast("名称填写过长");
			return false;
		}
		
		String inventory = mEdtGoodSInventory.getText().toString().trim();
		if (!"OK".equals(HintUtils.weightHint(inventory))) {
			((BaseActivity) context).displayToast(HintUtils
					.weightHint(inventory));
			return false;
		}
		if (!Utils.isExceed_7(inventory)) {
			((BaseActivity) context).displayToast("库存填写过长");
			return false;
		}
		String price = mEdtGoodSPric.getText().toString().trim();

		if (!"OK".equals(HintUtils.priceHint(price))) {
			((BaseActivity) context).displayToast(HintUtils.priceHint(price));
			return false;
		}
		if (!Utils.isExceed_8(price)) {
			((BaseActivity) context).displayToast("价格填写过长");
			return false;
		}

		String prepayment = mEdtGoodSPrepayment.getText().toString().trim();
		if (!"OK".equals(HintUtils.prePriceHint(prepayment))) {
			((BaseActivity) context).displayToast(HintUtils
					.prePriceHint(prepayment));
			return false;
		}
		if (!Utils.isExceed_10(prepayment)) {
			((BaseActivity) context).displayToast("价格填写过长");
			return false;
		}

		if (!Utils.isExceed_1000(mTxtDetails.getText().toString().trim())) {
			((BaseActivity) context).displayToast("详细内容填写过长");
			return false;
		}

		ProDataUtils.setIndex(0);
		ProDataUtils.setDetails(mEdtGoodSNam);
		ProDataUtils.setDetails(mTxtCategory);
		ProDataUtils.setDetails(mTxtBrand);
		ProDataUtils.setDetails(mTxtPlace);
		ProDataUtils.setDetails(mEdtGoodSInventory);
		ProDataUtils.setDetails(mEdtGoodSPric);
		ProDataUtils.setDetails(mEdtGoodSPrepayment);
		ProDataUtils.setDetails(mTxtPutTime);
		ProDataUtils.setDetails(mTxtEndTime);
		ProDataUtils.setDetails(mTxtStart);
		ProDataUtils.setDetails(mTxtOver);
		ProDataUtils.setDetails(mTxtDelivery);
		ProDataUtils.setDetails(mTxtDetails);
		// 商家名称
		String merchaseName = mEdtGoodSNam.getText().toString().trim();
		if (TextUtils.isEmpty(merchaseName)) {
			((BaseActivity) context).displayToast("名称不能为空");
			return false;
		}

		/*
		 * 判断库存
		 */
		// String intvetory =
		// Utils.getNumberOfString(mEdtGoodSInventory.getText()
		// .toString());
		// if (!"OK".equals(HintUtils.inventoryHint(intvetory))) {
		// ((BaseActivity) context).displayToast(HintUtils
		// .inventoryHint(intvetory));
		// return false;
		// }
		/*
		 * 判断价格-----------------------------------
		 */
		// String price = Utils.getNumberOfString(mEdtGoodSPric.getText()
		// .toString());
		//
		// if (!"OK".equals(HintUtils.priceHint(price))) {
		// ((BaseActivity) context).displayToast(HintUtils.priceHint(price));
		// return false;
		// }

		/*
		 * 判断预付金额-----------------------------------
		 */
		// String prepayment = Utils.getNumberOfString(mEdtGoodSPrepayment
		// .getText().toString());
		//
		// if (!"OK".equals(HintUtils.prePriceHint(prepayment))) {
		// ((BaseActivity) context).displayToast(HintUtils
		// .prePriceHint(prepayment));
		// return false;
		// }
		if (1 != state) {
			startTime = mTxtPutTime.getText().toString().trim();
			endTime = mTxtEndTime.getText().toString().trim();
			String result = HintUtils.timeHint(startTime, endTime);
			if (!"OK".equals(result)) {
				((BaseActivity)context).displayToast(result);
				return false;
			}
		}
		// 详细信息
		String detail = mTxtDetails.getText().toString().trim();

		// sourceInfo.setId(id);
		// sourceInfo = new GoodsSourceInfo();
		// sourceInfo.setId(info.getGoodSID());
		// sourceInfo.setGoodSID("123");
		// sourceInfo.setGoodSName(merchaseName);
		// sourceInfo.setGoodSWeight(intvetory);
		// sourceInfo.setGoodSPrePrice(Float.parseFloat(price));
		// sourceInfo.setGoodSPutTime(mTxtPutTime.getText().toString().trim());
		// sourceInfo.setGoodSDelivery(content);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_conduct_delivery:// 交割地
			((BaseActivity) context).goActivity(mTxtDelivery.getText()
					.toString().trim(), CitySelectionActivity.class);
			break;
		case R.id.ll_conduct_end_time:// 结束时间
			endTime();
			break;
		case R.id.ll_conduct_put_time:// 发布时间
			startTime();
			break;
		case R.id.ll_conduct_auto_start:// 自动开始
			String textStart = mTxtStart.getText().toString();
			autoStartOrEnd(1, textStart);
			break;
		case R.id.ll_conduct_auto_over:// 自动结束
			String textEnd = mTxtOver.getText().toString();
			autoStartOrEnd(2, textEnd);
			break;
		}
	}

	private void autoStartOrEnd(final int state, String defaultText) {

		AutoSelectView view = null;

		if (1 == state) {
			view = new AutoSelectView(context, "到期自动开始", 2);
		} else if (2 == state) {
			view = new AutoSelectView(context, "到期自动结束", 2);
		}

		view.setInfoOnClickListener(new AutoOnClickListener() {

			@Override
			public void onClickListener(String content) {
				if (1 == state) {
					autoStart = content;
					mTxtStart.setText(content);
				} else if (2 == state) {
					autoEnd = content;
					mTxtOver.setText(content);
				}
			}
		});
		view.show();
		view.setDefault(defaultText);
	}

	/**
	 * 结束日期
	 */
	private void endTime() {
		DateSelectorView view = new DateSelectorView(context, "结束日期");
		view.show();
		view.setListener(new OnDateSelectedClickListener() {

			@Override
			public void onDateSelected(String date) {
				// ((BaseActivity) context).displayToast("结束时间" + date);
				endTime = date;
				// long start = com.baiyi.dmall.utils.Utils
				// .getLongTime1(startTime);
				// long end = com.baiyi.dmall.utils.Utils.getLongTime1(date);
				// if (start > end) {
				// ((BaseActivity) context).displayToast("结束时间不能小于开始时间");
				// return;
				// }

				mTxtEndTime.setText(date);
			}
		});
	}

	/**
	 * 开始日期
	 */
	private void startTime() {
		DateSelectorView view = new DateSelectorView(context, "开始日期");
		view.show();
		view.setListener(new OnDateSelectedClickListener() {

			@Override
			public void onDateSelected(String date) {
				// ((BaseActivity) context).displayToast("开始时间" + date);
				startTime = date;

				mTxtPutTime.setText(date);
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 1) {
			countryInfo = (SelectedInfo) data.getSerializableExtra("key");
			content = data.getStringExtra("text");
			mTxtDelivery.setText(content);
		}
	}
}
