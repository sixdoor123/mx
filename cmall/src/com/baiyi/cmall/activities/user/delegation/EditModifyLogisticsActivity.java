package com.baiyi.cmall.activities.user.delegation;

import android.content.Intent;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main._public.CitySelectionActivity;
import com.baiyi.cmall.activities.main._public.FreightWayActivity;
import com.baiyi.cmall.activities.main._public.PackagingWayActivity;
import com.baiyi.cmall.activities.user.buyer.HintUtils;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.request.manager.UserLogisticsManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.DateSelectorView;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.DateSelectorView.OnDateSelectedClickListener;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 
 * 修改物流信息
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-23 下午4:49:57
 */
public class EditModifyLogisticsActivity extends BaseActivity implements
		OnClickListener {

	// 用户ID
	private String userID;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);
		initData();
		initTitle();
		initContent();
	}

	// 数据源
	private GoodsSourceInfo info;

	/**
	 * 数据
	 */
	private void initData() {
		userID = CmallApplication.getUserInfo().getUserID();

		info = (GoodsSourceInfo) getIntent().getSerializableExtra("key");
	}

	// 自定义标题栏
	protected EventTopTitleView topTitleView = null;

	/**
	 * 标题栏
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("编辑委托物流");
		topTitleView.setTitleNewsOnclick(new TitleNewsOnclick() {

			@Override
			public void setTitleNewsOnclickLister(boolean isPop) {

				topTitleView.displayPoP(MsgItemUtil.Pop_Default_txt,
						MsgItemUtil.Pop_Default_img);

			}
		});
		topTitleView.setNewsPopItemOnclick(new NewsPopItemOnclick() {

			@Override
			public void setNewsPopItemOnclickLister(int state) {
				MsgItemUtil.onclickPopItem(state,
						EditModifyLogisticsActivity.this);
			}
		});
		win_title.addView(topTitleView);
	}

	/**
	 * 修改物流的具体内容
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_edit_logistics_info, null);
		win_content.addView(view);

		findViewById(view);
		updateViewData();
	}

	// 公司名称
	private EditText mEdtCompanyName;
	// 联系人
	private EditText mEdtContactPerson;
	// 联系方式
	private EditText mEdtContactNumber;
	// 标题
	private EditText medtGoodSTitle;
	// 内容
	private EditText mEdtGoodSContent;

	// 控制始发城市的条目
	private LinearLayout mLlStartContury;
	// 始发城市的详细地址
	private EditText mEdtStartDetailAddress;
	// 控制目的城市的条目
	private LinearLayout mLlEndContury;
	// 目的城市的详细地址
	private EditText mEdtEndDetailAddress;
	// 数量
	private EditText mEdtGoodSWeight;
	// 控制始发时间的条目
	private LinearLayout mLlStartTime;
	// 控制到达时间的条目
	private LinearLayout mLlEndTime;
	// 控制货运类型货运类型
	private LinearLayout mLlCargoType;
	// 控制包装方式的条目
	private LinearLayout mLlPackageWay;

	// 决定详细物流信息是否显示
	private TableLayout mLlControlDetailLogistics;
	// 控制详细物流是否显示
	private TextView mTxtDetailUpDown;

	// 完成按钮
	private TextView mBtnCommitComplete;
	// 进度
	private MyLoadingBar loadingBar;

	// 显示始发城市
	private TextView mTxtStartCity;
	// 显示目的城市
	private TextView mTxtEndCity;
	// 显示始发时间
	private TextView mTxtStartTime;
	// 显示到达时间
	private TextView mTxtEndTime;
	// 显示货运类型
	private TextView mTxtFreightType;
	// 显示包装方式
	private TextView mTxtPackType;

	/**
	 * 找控件
	 * 
	 * @param view
	 */
	private void findViewById(View view) {
		mEdtCompanyName = (EditText) view.findViewById(R.id.edt_company_name);
		mEdtContactPerson = (EditText) view
				.findViewById(R.id.edt_contact_person);
		mEdtContactNumber = (EditText) view.findViewById(R.id.edt_contact_num);
		medtGoodSTitle = (EditText) view.findViewById(R.id.edt_goods_title);
		mEdtGoodSContent = (EditText) view.findViewById(R.id.edt_goods_cintent);

		mEdtStartDetailAddress = (EditText) view
				.findViewById(R.id.edt_start_detail_address);
		mEdtEndDetailAddress = (EditText) view
				.findViewById(R.id.edt_end_detail_address);
		mEdtGoodSWeight = (EditText) view.findViewById(R.id.edt_goods_weight);

		mLlStartContury = (LinearLayout) view
				.findViewById(R.id.ll_start_contury);
		mLlEndContury = (LinearLayout) view.findViewById(R.id.ll_end_contury);
		mLlStartTime = (LinearLayout) view.findViewById(R.id.ll_start_time);
		mLlEndTime = (LinearLayout) view.findViewById(R.id.ll_end_time);
		mLlCargoType = (LinearLayout) view.findViewById(R.id.ll_cargo_type);
		mLlPackageWay = (LinearLayout) view.findViewById(R.id.ll_package_way);

		mLlControlDetailLogistics = (TableLayout) view
				.findViewById(R.id.control_detail_logistcs);
		mTxtDetailUpDown = (TextView) view
				.findViewById(R.id.txt_detail_up_down);
		mBtnCommitComplete = (TextView) view
				.findViewById(R.id.btn_commit_complete);

		if (null != info) {
			if (2 != info.getState()) {
				mBtnCommitComplete.setVisibility(View.VISIBLE);
			} else {
				mBtnCommitComplete.setVisibility(View.GONE);
			}
		}

		mTxtStartCity = (TextView) view.findViewById(R.id.txt_start_city);
		mTxtEndCity = (TextView) view.findViewById(R.id.txt_end_city);
		mTxtStartTime = (TextView) view.findViewById(R.id.txt_start_time);
		mTxtEndTime = (TextView) view.findViewById(R.id.txt_end_time);
		mTxtFreightType = (TextView) view.findViewById(R.id.txt_freight_type);
		mTxtPackType = (TextView) view.findViewById(R.id.txt_pack_type);

		loadingBar = (MyLoadingBar) view.findViewById(R.id.load);

		mLlStartContury.setOnClickListener(this);
		mLlEndContury.setOnClickListener(this);
		mLlStartTime.setOnClickListener(this);
		mLlEndTime.setOnClickListener(this);
		mLlCargoType.setOnClickListener(this);
		mLlPackageWay.setOnClickListener(this);
		mTxtDetailUpDown.setOnClickListener(this);
		mBtnCommitComplete.setOnClickListener(this);

		// 添加“*”
		TextViewUtil.setText(R.id.contact, "联系人", view);
		TextViewUtil.setText(R.id.moble, "联系方式", view);
		TextViewUtil.setText(R.id.title, "标题", view);
		TextViewUtil.setText(R.id.content, "内容", view);
		// TextViewUtil.setText(R.id.company, "公司名称", view);

		mEdtGoodSContent.setOnTouchListener(new OnTouchListener() {

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
	 * 更新界面上的数据
	 */
	private void updateViewData() {
		if (null != info) {
			mEdtCompanyName.setText(info.getGoodSMerchant());
			mEdtContactPerson.setText(info.getGoodSContactPerson());
			mEdtContactNumber.setText(info.getGoodSContactWay());
			medtGoodSTitle.setText(info.getTitle());
			mEdtGoodSContent.setText(info.getGoodSDetails());
			mEdtStartDetailAddress.setText(getEditString(info
					.getGoodSStartCity()));
			mEdtEndDetailAddress.setText(getEditString(info.getGoodSEndCity()));
			// 设置数量
			mEdtGoodSWeight.setText("0".equals(getEditString(info
					.getGoodSWeight())) ? "" : getEditString(info
					.getGoodSWeight()));
			mTxtStartCity.setText(getSelectString(info.getStartAddress()));
			mTxtEndCity.setText(getSelectString(info.getEndAddress()));

			// 设置开始时间
			mTxtStartTime.setText(getSelectString(info.getGoodSStartTime()));
			// 设置结束时间
			mTxtEndTime.setText(getSelectString(info.getGoodSEndTime()));

			mTxtFreightType
					.setText(getSelectString(info.getDeliverytypename()));
			mTxtPackType.setText(getSelectString(info.getPacktypename()));
		}
	}

	private String getSelectString(String s) {

		if ("null".equals(s) || null == s || "".equals(s)) {
			return "请选择";
		}

		return s;
	}

	private String getEditString(String s) {

		if ("null".equals(s) || null == s || "".equals(s)) {
			return "";
		}

		return s;
	}

	// 标志位
	private boolean flag = true;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_start_contury:// 起始城市
			startCity();
			break;
		case R.id.ll_end_contury:// 目的城市
			goalCity();
			break;
		case R.id.ll_start_time:// 起始时间
			startTime();
			break;
		case R.id.ll_end_time:// 到达时间
			endTime();
			break;
		case R.id.ll_cargo_type:// 货运类型
			goActivity(FreightWayActivity.class);
			break;
		case R.id.ll_package_way:// 包装方式
			goActivity(PackagingWayActivity.class);
			break;
		case R.id.btn_commit_complete:// 完成按钮
			completed();
			break;
		case R.id.txt_detail_up_down:// 控制详细信息是否要显示
			if (flag) {
				mLlControlDetailLogistics.setVisibility(View.VISIBLE);
				mTxtDetailUpDown.setText("收起↑");
				flag = false;
			} else {
				mLlControlDetailLogistics.setVisibility(View.GONE);
				mTxtDetailUpDown.setText("委托完善↓");
				flag = true;
			}
			break;

		}
	}

	// 用户输入的封装实体类
	private GoodsSourceInfo sourceInfo = null;

	/**
	 * 完成按钮
	 */
	private void completed() {
		// 得到输入的实体类
		String result = getSourceInfo();
		if (null != result) {
			displayToast(result);
			return;
		}

		if (TextUtils.isEmpty(userID)) {
			displayToast("用户未登录,请登录后重试!");
			return;
		}

		loadingBar.setVisibility(View.VISIBLE);
		loadingBar.setProgressInfo("正在提交中...");
		loadingBar.start();

		mLlControlDetailLogistics.setVisibility(View.GONE);
		mTxtDetailUpDown.setText("委托完善↓");
		flag = true;

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getEditLogisticsDetailUrl());
		loader.setPostData(UserLogisticsManager.getEditLogisticsPostData(info,
				freightInfo, packInfo, sourceInfo, startCityInfo, endCityInfo));
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.addRequestHeader("token", CmallApplication.getUserInfo()
				.getToken());
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {

			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				displayToast(arg2);
				loadingBar.setVisibility(View.GONE);
				loadingBar.stop();
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				RequestNetResultInfo info = UserLogisticsManager
						.getEditLogisticsResultInfo(arg1);
				displayToast(info.getMsg());
				loadingBar.setVisibility(View.GONE);
				loadingBar.stop();
				if (1 == info.getStatus()) {
					finish();
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	// 状态标志位，判断信息是否输入完全
	private int state;

	/**
	 * 得到用户修改以后的信息
	 * 
	 * @return
	 */
	private String getSourceInfo() {
		sourceInfo = new GoodsSourceInfo();

		// 公司名称
		String companyName = mEdtCompanyName.getText().toString().trim();
		if (!Utils.isExceed_30(companyName)) {
			return "公司名称输入过长";
		}
		sourceInfo.setGoodSMerchant(companyName);

		// 联系人
		String contact = mEdtContactPerson.getText().toString().trim();

		if (TextUtils.isEmpty(contact)) {
			return "联系人不能为空";
		}
		if (!Utils.isExceed_20(companyName)) {
			return "联系人输入过长";
		}
		sourceInfo.setGoodSContactPerson(contact);

		// 联系方式
		String number = mEdtContactNumber.getText().toString().trim();
		if (TextUtils.isEmpty(number)) {
			// displayToast();
			return "联系方式不能为空";
		}
		if (!Utils.isExceed_20(number)) {
			return "联系方式输入过长";
		}
		if (!(Utils.isPhoneNum(number) && Utils.isPhoneNumberValid(number))) {
			return "联系方式填写不正确";
		}
		sourceInfo.setGoodSContactWay(number);

		// 标题
		String title = medtGoodSTitle.getText().toString().trim();
		if (TextUtils.isEmpty(title)) {
			// displayToast();
			return "标题不能为空";
		}
		if (!Utils.isExceed_30(title)) {
			return "标题输入过长";
		}
		sourceInfo.setGoodSTitle(title);

		// 内容
		String content = mEdtGoodSContent.getText().toString().trim();

		if (TextUtils.isEmpty(content)) {
			// displayToast();
			return "内容不能为空";
		}
		if (!Utils.isExceed_1000(content)) {
			return "内容输入过长";
		}
		sourceInfo.setGoodSContent(content);

		// 始发城市详细地址
		String startAddress = mEdtStartDetailAddress.getText().toString()
				.trim();
		if (!Utils.isExceed_30(startAddress)) {
			return "始发城市详细地址输入过长";
		}
		sourceInfo.setStartAddress(startAddress);

		// 目的城市
		String endAddress = mEdtEndDetailAddress.getText().toString().trim();
		if (!Utils.isExceed_30(endAddress)) {
			return "目的城市详细地址输入过长";
		}
		sourceInfo.setEndAddress(endAddress);

		/*
		 * 判断数量-----------------------------------
		 */
		String weight = Utils.getNumberOfString(mEdtGoodSWeight.getText()
				.toString());
		if (!"OK".equals(HintUtils.getWeight(weight))) {
			return HintUtils.getWeight(weight);
		}
		if (!Utils.isExceed_7(weight)) {
			return "数量输入过长";
		}
		sourceInfo.setGoodSWeight(weight);

		String startTime = mTxtStartTime.getText().toString().trim();
		String endTime = mTxtEndTime.getText().toString().trim();
		String result = HintUtils.timeHint(startTime, endTime);
		if (!"OK".equals(result)) {
			return result;
		}

		sourceInfo.setGoodSStartTime(startTime);
		sourceInfo.setGoodSEndTime(endTime);

		return null;
	}

	/**
	 * 目的城市
	 */
	private void goalCity() {
		goActivity(mTxtEndCity.getText().toString(),
				CitySelectionActivity.class, 1);
	}

	/**
	 * 物流起始城市选择
	 */
	private void startCity() {
		goActivity(mTxtStartCity.getText().toString(),
				CitySelectionActivity.class, 0);
	}

	// 起始城市
	private SelectedInfo startCityInfo;
	// 目的城市
	private SelectedInfo endCityInfo;
	// 货运方式
	private SelectedInfo freightInfo;
	// 包装方式
	private SelectedInfo packInfo;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (0 == resultCode) {
			if (null != data) {
				startCityInfo = (SelectedInfo) data.getSerializableExtra("key");
				String text = data.getStringExtra("text");
				mTxtStartCity.setText(text);
			}
		} else if (1 == resultCode) {
			if (null != data) {
				endCityInfo = (SelectedInfo) data.getSerializableExtra("key");
				String text = data.getStringExtra("text");
				mTxtEndCity.setText(text);
			}
		} else if (8 == resultCode) {
			if (null != data) {
				freightInfo = (SelectedInfo) data.getSerializableExtra("key");
				mTxtFreightType.setText(freightInfo.getCm_categoryname());
			}
		} else if (9 == resultCode) {
			if (null != data) {
				packInfo = (SelectedInfo) data.getSerializableExtra("key");
				mTxtPackType.setText(packInfo.getCm_categoryname());
			}
		}

	}

	/**
	 * 到达时间
	 */
	private void endTime() {
		DateSelectorView view = new DateSelectorView(this, "选择到达日期");
		view.show();
		view.setListener(new OnDateSelectedClickListener() {

			@Override
			public void onDateSelected(String date) {
				// displayToast("到达日期：" + date);
				mTxtEndTime.setText(date);
			}
		});
	}

	/**
	 * 选择始发时间
	 */
	private void startTime() {
		DateSelectorView view = new DateSelectorView(this, "选择始发日期");
		view.show();
		view.setListener(new OnDateSelectedClickListener() {

			@Override
			public void onDateSelected(String date) {
				// displayToast("始发日期：" + date);
				mTxtStartTime.setText(date);
			}
		});
	}

}
