package com.baiyi.cmall.activities.main.home_pager;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.baiyi.cmall.activities.base.BaseMsgActivity;
import com.baiyi.cmall.activities.main.SucceeMainActivity;
import com.baiyi.cmall.activities.main._public.CitySelectionActivity;
import com.baiyi.cmall.activities.user.buyer.HintUtils;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.LoginInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.request.manager.ProviderSourceManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.DataUtils;
import com.baiyi.cmall.utils.MobileStateUtils;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.utils.XmlName;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.cmall.views.pulldownview.PullToRefreshListView;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase.Mode;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 供应-委托采购
 * 
 * @author sunxy
 * 
 */
@SuppressLint("ClickableViewAccessibility")
public class HomePagerPurActivity extends BaseMsgActivity implements
		OnClickListener {

	// 供应信息条目
	private LinearLayout mLlSupplyInfo;
	// 向下或者向上的箭头
	private ImageView mImgUpDown;

	// 把供应信息装在集合中
	private ArrayList<String> supplyInfos;

	// 货物实体类
	private GoodsSourceInfo info;

	// 标记是显示或者不显示
	boolean flag = true;

	private View listView;
	// 点击供应信息条目时，显示此ListView
	private PullToRefreshListView mLstDetails;

	// 输入框实例
	private EditText mEdtPurchaseName;// 商家姓名
	private EditText mEdtContactName;// 联系人姓名
	private EditText mEdtContactNumber;// 电话号码
	private EditText mEdtPurchaseNeed;// 采购需求

	// 按钮
	// 提交委托采购
	private TextView mBtnSuuplyBuy;
	// 取消
	private TextView mBtnSuuplyCancel;

	// 用户ID
	private String userID;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);
		ActivityStackControlUtil.add(this);
		initData();
		initTitle();
		initContent();
		initInputContent();
		requestNet(null, 1);
	}

	/**
	 * 初始化一些输入框的信息
	 */
	private void initInputContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_delegate_input, null);
		win_content.addView(view);

		// 实例化
		findEditTextViewById(view);

	}

	/**
	 * 更新界面
	 */
	private void upDataView() {
		if (loginInfo != null) {
			mEdtContactName.setText(loginInfo.getUserName() == null ? ""
					: loginInfo.getUserName());
			mEdtContactNumber.setText(loginInfo.getMobile() == null ? ""
					: loginInfo.getMobile());
			mEdtPurchaseName.setText(loginInfo.getCompanyName() == null ? ""
					: loginInfo.getCompanyName());
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.list_item_pop, R.id.txt_item, supplyInfos);
		mLstDetails.setAdapter(adapter);
		mLlAddLayout
				.addView(listView, getScreenWidth(), MobileStateUtils
						.getTotalHeightofListView(adapter, mLstDetails));
	}

	// 控制下面信息是否显示
	private LinearLayout mLlShow;
	// 显示委托完善或者收起
	private TextView mTxtController;
	// 联系人城市
	private TableRow mTbPersonCity;
	// 显示联系人城市
	private TextView mTxtPersonCity;
	// 联系地址
	private TableRow mTbAddress;
	// 显示联系地址
	private EditText mTxtAddress;
	// 显示分类
	private TextView mTxtCategory;
	// 交割地
	private TableRow mTbDelivery;
	// 显示交割地
	private TextView mTxtDelivery;
	// 显示产地
	private TextView mTxtPlace;
	// 显示品牌
	private TextView mTxtBrand;
	// 数量
	private EditText mEdtWeight;
	// 价格
	private EditText mEdtPrice;

	/**
	 * 找控件
	 * 
	 * @param view
	 */
	private void findEditTextViewById(View view) {
		mEdtContactName = (EditText) view.findViewById(R.id.edt_contact_name);
		mEdtContactNumber = (EditText) view
				.findViewById(R.id.edt_contact_number);
		mEdtPurchaseName = (EditText) view.findViewById(R.id.edt_supply_name);
		mEdtPurchaseNeed = (EditText) view.findViewById(R.id.edt_supply_need);

		mBtnSuuplyBuy = (TextView) view.findViewById(R.id.btn_commit_modify);
		mBtnSuuplyBuy.setText("提交采购委托");
		mBtnSuuplyCancel = (TextView) view.findViewById(R.id.btn_cancel_modify);

		mBtnSuuplyBuy.setOnClickListener(this);
		mBtnSuuplyCancel.setOnClickListener(this);

		mLlShow = (LinearLayout) view.findViewById(R.id.ll_show);
		mTxtController = (TextView) view.findViewById(R.id.txt_control);
		mTxtController.setOnClickListener(this);
		mTbPersonCity = (TableRow) view.findViewById(R.id.tb_person_city);
		mTbPersonCity.setOnClickListener(this);
		mTxtPersonCity = (TextView) view.findViewById(R.id.txt_person_city);
		// mTbAddress = (TableRow) view.findViewById(R.id.tb_address);
		// mTbAddress.setOnClickListener(this);
		mTxtAddress = (EditText) view.findViewById(R.id.txt_address);
		mTxtCategory = (TextView) view.findViewById(R.id.txt_category);
		mTbDelivery = (TableRow) view.findViewById(R.id.tb_delivery);
		mTbDelivery.setOnClickListener(this);
		mTxtDelivery = (TextView) view.findViewById(R.id.txt_delivery);
		mTxtPlace = (TextView) view.findViewById(R.id.txt_place);
		mTxtBrand = (TextView) view.findViewById(R.id.txt_brand);
		mEdtWeight = (EditText) view.findViewById(R.id.edt_weight);
		mEdtPrice = (EditText) view.findViewById(R.id.edt_price);

		// 添加“*”
		TextViewUtil.setText(R.id.contact, "联系人", view);
		TextViewUtil.setText(R.id.mobile, "联系方式", view);
		// TextViewUtil.setText(R.id.txt_merchant_name, "采购商", view);
		TextViewUtil.setText(R.id.txt_supply_price, "需求描述", view);

		mEdtPurchaseNeed.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				// 这句话说的意思告诉父View我自己的事件我自己处理
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}

		});
	}

	private LoginInfo loginInfo;
	private String id;

	/**
	 * 把数据整理放入集合中
	 */
	private void initData() {

		id = getIntent().getStringExtra("arg");

		XmlUtils utils = XmlUtils.getInstence(this);
		userID = utils.getXmlStringValue(XmlName.USER_ID);
		if (!TextUtils.isEmpty(userID)) {
			loginInfo = CmallApplication.getUserInfo();
			if (null == loginInfo) {
				loginInfo.setCompanyName(utils
						.getXmlStringValue(XmlName.Company_Name));
				loginInfo.setUserName(utils
						.getXmlStringValue(XmlName.USER_NAME));
				loginInfo.setMobile(utils.getXmlStringValue(XmlName.Mobile));
			}
		}
	}

	// 添加详情
	private LinearLayout mLlAddLayout;

	/**
	 * 初始化供应信息条目
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_delegate_buy, null);

		win_content.addView(view);
		findProviderViewById(view);
	}

	// 控制是显示还是隐藏
	private LinearLayout mLlControl;

	/**
	 * 找供应信息的控件
	 * 
	 * @param view
	 */
	private void findProviderViewById(View view) {
		mLlAddLayout = (LinearLayout) view.findViewById(R.id.ll_add);

		listView = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.listview_not_divider, null);

		mLstDetails = (PullToRefreshListView) listView
				.findViewById(R.id.lst_in_details);
		mLlControl = (LinearLayout) listView.findViewById(R.id.ll_con);
		mLstDetails.setMode(Mode.DISABLED);
		mLlSupplyInfo = (LinearLayout) view.findViewById(R.id.ll_supply_info);
		mImgUpDown = (ImageView) view.findViewById(R.id.img_up_down);
		mLlSupplyInfo.setOnClickListener(this);
	}

	/**
	 * 初始化标题栏
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
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
				MsgItemUtil.onclickPopItem(state, HomePagerPurActivity.this);
			}
		});
		topTitleView.setEventName("委托采购");
		win_title.addView(topTitleView);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_supply_info:

			if (flag) {
				mLlControl.setVisibility(View.VISIBLE);
				mImgUpDown.setImageResource(R.drawable.up_arrow);
				flag = false;
			} else {
				mLlControl.setVisibility(View.GONE);
				mImgUpDown.setImageResource(R.drawable.down_arrow);
				flag = true;
			}
			break;
		case R.id.btn_commit_modify:// 提交按钮
			supplyBuy();
			break;
		case R.id.btn_cancel_modify:// 取消
			finish();
			break;
		case R.id.txt_control:// 控制下面的内容是否显示
			controlItem();
			break;
		case R.id.tb_delivery:// 交割地
			goActivity(mTxtDelivery.getText().toString().trim(),
					CitySelectionActivity.class, 3);
			break;
		case R.id.tb_person_city:// 选择城市
			goActivity(mTxtPersonCity.getText().toString(),
					CitySelectionActivity.class, 1);
			break;
		}
	}

	private String city;
	private String devalite;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (null == data) {
			return;
		}

		SelectedInfo info = (SelectedInfo) data.getSerializableExtra("key");

		if (null != info) {

			// 联系人城市
			if (resultCode == 1) {
				String text = data.getStringExtra("text");
				city = info.getId();
				mTxtPersonCity.setText(text);
			}

			// 交割地
			if (resultCode == 3) {
				String text = data.getStringExtra("text");
				devalite = info.getId();
				mTxtDelivery.setText(text);
			}
		}
	}

	// 标记位
	private boolean showFlag = true;

	/**
	 * 控制线面的内容是否显示
	 */
	private void controlItem() {
		if (!showFlag) {
			mLlShow.setVisibility(View.GONE);
			mTxtController.setText("委托完善↓");
			showFlag = true;
		} else {
			mLlShow.setVisibility(View.VISIBLE);
			mTxtController.setText("收起↑");
			showFlag = false;
		}
	}

	/**
	 * 供应-委托采购 进入提交成功界面
	 */
	private void supplyBuy() {
		// 采购商姓名
		String purchaseNam = mEdtPurchaseName.getText().toString();

		if (!Utils.isExceed_30(purchaseNam)) {
			displayToast("采购商名称过长");
			return;
		}
		// 联系人姓名
		String contactNam = mEdtContactName.getText().toString();
		if (TextUtils.isEmpty(contactNam)) {
			displayToast("联系人不能为空");
			return;
		}
		if (!Utils.isExceed_20(contactNam)) {
			displayToast("联系人名称过长");
			return;
		}
		// 联系电话号码
		String contactNumber = mEdtContactNumber.getText().toString().trim();
		if (TextUtils.isEmpty(contactNumber)) {
			displayToast("联系方式不能为空");
			return;
		}
		if (!Utils.isExceed_20(contactNumber)) {
			displayToast("联系方式称过长");
			return;
		}
		if (!(Utils.isPhoneNum(contactNumber) && Utils
				.isPhoneNumberValid(contactNumber))) {
			displayToast("联系方式填写不正确");
			return;
		}

		// 采购需求
		String purchaseNeed = mEdtPurchaseNeed.getText().toString();
		if (TextUtils.isEmpty(purchaseNeed)) {
			displayToast("需求描述不能为空");
			return;
		}
		if (!Utils.isExceed_1000(purchaseNeed)) {
			displayToast("需求描述过长");
			return;
		}
		/*
		 * 判断数量-----------------------------------
		 */
		String weight = Utils
				.getNumberOfString(mEdtWeight.getText().toString());
		if (!TextUtils.isEmpty(weight)) {

			if (!"OK".equals(HintUtils.weightHint(weight))) {
				displayToast(HintUtils.weightHint(weight));
				return;
			}
			if (!Utils.isExceed_7(weight)) {
				displayToast("数量填写过长");
				return;
			}
		} else {
			weight = info.getGoodSWeight();
		}

		/*
		 * 判断价格-----------------------------------
		 */
		String price = Utils.getNumberOfString(mEdtPrice.getText().toString());

		if (!TextUtils.isEmpty(price)) {
			if (!"OK".equals(HintUtils.priceHint(price))) {
				displayToast(HintUtils.priceHint(price));
				return;
			}
			if (!Utils.isExceed_8(weight)) {
				displayToast("价格填写过长");
				return;
			}
		} else {
			price = info.getGoodSPrePrice();
		}

		GoodsSourceInfo sourceInfo = new GoodsSourceInfo();
		sourceInfo.setGoodSMerchant(purchaseNam);// 公司名称
		sourceInfo.setGoodSContactPerson(contactNam);// 联系人姓名
		sourceInfo.setGoodSContactWay(contactNumber);// 联系方式
		sourceInfo.setGoodSPurchaseNeed(purchaseNeed);// 采购需求
		sourceInfo.setGoodSPrice(price);//价格
		sourceInfo.setGoodSWeight(weight);//数量
		// 联系人城市ID
		if (TextUtils.isEmpty(city)) {
			city = info.getCityid();
		}
		sourceInfo.setCityid(city);
		// 交割地
		if (TextUtils.isEmpty(devalite)) {
			devalite = info.getDeliverycityid();
		}
		sourceInfo.setDeliverycityid(devalite);
		sourceInfo.setAddress(mTxtAddress.getText().toString());

		requestNet(sourceInfo, 2);
	}

	/**
	 * 请求服务器
	 * 
	 * @param info2
	 */
	private void requestNet(GoodsSourceInfo sourceInfo, final int state) {
		loaderProgress();

		final JsonLoader loader = new JsonLoader(this);
		// 获取数据显示
		if (1 == state) {
			loader.setUrl(AppNetUrl.getHomePurDetailUrl(id));
			loader.setPostData("");
		}
		// 提交数据
		if (2 == state) {
			loader.setUrl(AppNetUrl.getDetegationDetailUrl());
			loader.setPostData(ProviderSourceManager.getDetegationPostData(
					sourceInfo, info, userID));
		}
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				displayToast(arg2);
				stopProgress();
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				RequestNetResultInfo infos = ProviderSourceManager
						.getDetegationResult(arg1);
				stopProgress();

				switch (state) {
				case 1:
					Log.d("TAG", "case 1---" + arg1.toString());
					info = ProviderSourceManager.getHomePurDetailInfo(arg1);
					supplyInfos = DataUtils.getShowList(info);
					upDataView();
					break;
				case 2:
					if (1 == infos.getStatus()) {
						displayToast(infos.getMsg());
						goActivity(SucceeMainActivity.class, 0);
					} else {
						displayToast(infos.getMsg());
						return;
					}
					break;
				}
			}

		});
		CmallApplication.getDataStratey().startLoader(loader);
	}
}
