package com.baiyi.cmall.activities.main.header_top;

import org.json.JSONArray;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.base.BaseMsgActivity;
import com.baiyi.cmall.activities.main.SucceeMainActivity;
import com.baiyi.cmall.activities.main._public.CitySelectionActivity;
import com.baiyi.cmall.activities.main._public.GoodSCategoryActivity;
import com.baiyi.cmall.activities.user.buyer.HintUtils;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.LoginInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.request.manager.MorePublishManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.JsonParse_User;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.utils.XmlName;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;

/**
 * 供应单界面-我要供应（委托供应）
 * 
 * @author lizl
 * 
 */
public class PublishSupplyActivity extends BaseMsgActivity implements OnClickListener {

	// 公司名称
	private EditText mEtMerchant;
	// 联系人姓名
	private EditText mEtContactPerson;
	// 联系方式
	private EditText mEtContactPhone;
	// 标题
	private EditText mEtTitle;
	// 内容
	private EditText mEtContent;

	// 联系人城市
	private TextView mTvContactCity;
	// 详细地址
	private EditText mEtAdress;
	// 分类
	private TextView mTViClassify;
	// 交割地
	private TextView mTvDelivery;
	// 产地
	private TextView mTvPlace;
	// 品牌/煤种
	private EditText mEtGoodSCategory;
	// 数量
	private EditText mEtWeight;
	// 价格
	private EditText mEdgoodSPrice;
	// 始发时间
	// private TextView mTvStartTime;
	// // 到达时间
	// private TextView mTvendTime;

	/**
	 * 几种选择内容的按钮
	 */
	// 联系人城市选择
	private TableRow mTrbContactCity;
	// 分类选择
	private TableRow mTrbClassify;
	// 交割地选择
	private TableRow mTrbDeliveryDi;
	// 产地选择
	private TableRow mTrbPlace;
	// 始发时间选择
	@SuppressWarnings("unused")
	private TableRow mTrbStartTime;
	// 到达时间选择
	@SuppressWarnings("unused")
	private TableRow mTrbEndTime;

	// 完成按钮
	private TextView mTvSubmit;
	// 是否显示全部按钮
	private TextView mTvUpDown;
	// 判断是否显示标志
	private boolean isVisible;

	private TableLayout mLlControlDetailLogistics;
	// 需要传送的实体类
	private GoodsSourceInfo sourceInfo;
	// 公司ID
	private String companyId;

	@Override
	protected void initWin(boolean hasScrollView) {
		super.initWin(true);

		// 添加Activity
		ActivityStackControlUtil.add(this);
		initCompanyId();
		initTitle();
		initContent();
	}

	private LoginInfo loginInfo;
	private String userID;

	/**
	 * 初始化供应商ID
	 */
	private void initCompanyId() {
		XmlUtils utils = XmlUtils.getInstence(this);
		companyId = utils.getXmlStringValue(XmlName.COMPANY_ID);
		userID = utils.getXmlStringValue(XmlName.USER_ID);
		if (!TextUtils.isEmpty(userID)) {
			loginInfo = CmallApplication.getUserInfo();
			if (null == loginInfo) {
				loginInfo.setCompanyName(utils.getXmlStringValue(XmlName.Company_Name));
				loginInfo.setUserName(utils.getXmlStringValue(XmlName.USER_NAME));
				loginInfo.setMobile(utils.getXmlStringValue(XmlName.Mobile));
			}
		}
	}

	/**
	 * 添加顶部标题栏
	 */
	private void initTitle() {

		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("发布供应");
		topTitleView.setTitleNewsOnclick(new TitleNewsOnclick() {

			@Override
			public void setTitleNewsOnclickLister(boolean isPop) {

				topTitleView.displayPoP(MsgItemUtil.Pop_Default_txt, MsgItemUtil.Pop_Default_img);

			}
		});
		topTitleView.setNewsPopItemOnclick(new NewsPopItemOnclick() {

			@Override
			public void setNewsPopItemOnclickLister(int state) {
				MsgItemUtil.onclickPopItem(state, PublishSupplyActivity.this);
			}
		});
		win_title.addView(topTitleView);
	}

	/**
	 * 初始化內容
	 */
	@SuppressLint("InflateParams")
	private void initContent() {

		View view = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_supply_goods_orders, null);
		win_content.addView(view);

		// 供应商
		mEtMerchant = (EditText) view.findViewById(R.id.edt_company_name);
		// 联系人
		mEtContactPerson = (EditText) view.findViewById(R.id.edt_contact_person_01);
		// 联系人电话
		mEtContactPhone = (EditText) view.findViewById(R.id.edt_contact_ways);
		// 标题
		mEtTitle = (EditText) view.findViewById(R.id.edt_goods_title);
		// 内容
		mEtContent = (EditText) view.findViewById(R.id.edt_goods_content);

		// 联系人城市
		mTvContactCity = (TextView) view.findViewById(R.id.tv_contact_city);
		// 详细地址
		mEtAdress = (EditText) view.findViewById(R.id.edt_end_detail_address);
		// 分类
		mTViClassify = (TextView) view.findViewById(R.id.tv_classify);
		// 交割地
		mTvDelivery = (TextView) view.findViewById(R.id.tv_devilate_di);
		// 产地
		mTvPlace = (TextView) view.findViewById(R.id.tv_place);
		// 品牌/煤种
		mEtGoodSCategory = (EditText) view.findViewById(R.id.tv_variety);
		// 数量
		mEtWeight = (EditText) view.findViewById(R.id.et_weight);
		// 价格
		mEdgoodSPrice = (EditText) view.findViewById(R.id.et_price);

		mTrbContactCity = (TableRow) findViewById(R.id.trb_contact_city);
		mTrbClassify = (TableRow) findViewById(R.id.trb_classify);
		mTrbDeliveryDi = (TableRow) findViewById(R.id.trb_delivery_di);
		mTrbPlace = (TableRow) findViewById(R.id.trb_Place);

		mTrbContactCity.setOnClickListener(this);
		mTrbClassify.setOnClickListener(this);
		mTrbDeliveryDi.setOnClickListener(this);
		mTrbPlace.setOnClickListener(this);

		mTvUpDown = (TextView) findViewById(R.id.tv_up_down);
		mTvUpDown.setOnClickListener(this);
		mTvSubmit = (TextView) findViewById(R.id.btn_commit_complete);
		mTvSubmit.setOnClickListener(this);
		mLlControlDetailLogistics = (TableLayout) findViewById(R.id.add_purchase_layout);

		// 添加“*”
		TextViewUtil.setText(R.id.contact, "联系人", view);
		TextViewUtil.setText(R.id.moble, "联系方式", view);
		TextViewUtil.setText(R.id.title, "标题", view);
		TextViewUtil.setText(R.id.content, "内容", view);

		UpdataViewDate();
	}

	private void UpdataViewDate() {
		if (null != loginInfo) {
			// 采购商名称
			mEtMerchant.setText(TextViewUtil.getEditString(loginInfo.getCompanyName()));
			// 联系人
			mEtContactPerson.setText(TextViewUtil.getEditString(loginInfo.getUserName()));
			// 联系人电话
			mEtContactPhone.setText(TextViewUtil.getEditString(loginInfo.getMobile()));
		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.trb_contact_city:// 选择城市
			goActivity(mTvContactCity.getText().toString(), CitySelectionActivity.class, 1);
			break;
		case R.id.trb_classify:// 选择分类
			goActivity(GoodSCategoryActivity.class);
			break;
		case R.id.trb_delivery_di:// 选择交割地
			goActivity(mTvDelivery.getText().toString(), CitySelectionActivity.class, 2);
			break;
		case R.id.trb_Place:// 选择产地
			goActivity(mTvPlace.getText().toString(), CitySelectionActivity.class, 3);
			break;
		case R.id.btn_commit_complete:// 提交数据
			// TODO 如果提交成功，將跳轉至成功界面
			commitData();
			break;

		// 委托完善、收起切换
		case R.id.tv_up_down:
			Log.d("TAG", isVisible + "");
			if (isVisible) {
				mLlControlDetailLogistics.setVisibility(View.GONE);
				mTvUpDown.setText("委托完善↓");
				isVisible = false;
			} else {
				mLlControlDetailLogistics.setVisibility(View.VISIBLE);
				mTvUpDown.setText("收起↑");
				isVisible = true;
			}
			break;

		}
	}

	/**
	 * 获取字符串数据
	 */
	private void commitData() {

		// 供应商
		String merchant = mEtMerchant.getText().toString();

		// 联系人
		String contantPerson = mEtContactPerson.getText().toString();
		if (TextUtils.isEmpty(contantPerson)) {
			displayToast("联系人不能为空");
			return;
		}

		// 联系人电话
		String contantPhone = mEtContactPhone.getText().toString();
		if (TextUtils.isEmpty(contantPhone)) {
			displayToast("联系方式不能为空");
			return;
		}
		if (!(Utils.isPhoneNum(contantPhone) && Utils.isPhoneNumberValid(contantPhone))) {
			displayToast("联系方式格式不正确");
			return;
		}

		// 标题
		String title = mEtTitle.getText().toString();
		if (TextUtils.isEmpty(title)) {
			displayToast("标题不能为空");
			return;
		}

		// 内容
		String content = mEtContent.getText().toString();
		if (TextUtils.isEmpty(content)) {
			displayToast("内容不能为空");
			return;
		}

		// 详细地址不为空时，联系人城市必须选择三级
		String adress = mEtAdress.getText().toString();

		/**
		 * 构造数据实体类
		 */
		sourceInfo = new GoodsSourceInfo();

		sourceInfo.setGoodSMerchant(merchant);
		sourceInfo.setGoodSContactPerson(contantPerson);
		sourceInfo.setGoodSContactWay(contantPhone);
		sourceInfo.setGoodSTitle(title);
		sourceInfo.setGoodSContent(content);
		sourceInfo.setAddress(adress);
		sourceInfo.setGoodSBrand(mEtGoodSCategory.getText().toString());

		/*
		 * 判断数量-----------------------------------
		 */
		String weight = Utils.getNumberOfString(mEtWeight.getText().toString());
		if (!TextUtils.isEmpty(weight)) {

			if (!"OK".equals(HintUtils.weightHint(weight))) {
				displayToast(HintUtils.weightHint(weight));
				return;
			}
			sourceInfo.setGoodSWeight(weight);
		}

		/*
		 * 判断价格-----------------------------------
		 */
		String price = Utils.getNumberOfString(mEdgoodSPrice.getText().toString());

		if (!TextUtils.isEmpty(price)) {

			if (!"OK".equals(HintUtils.priceHint(price))) {
				displayToast(HintUtils.priceHint(price));
				return;
			}
			sourceInfo.setGoodSPrice(price);
		}
		sourceInfo.setCityid(city);
		sourceInfo.setDeliverycityid(devalite);
		sourceInfo.setOrigincityid(place);
		sourceInfo.setCategoryID(category);
		supplyProvider();

	}

	/**
	 * 向服务器提交数据
	 */
	private void supplyProvider() {

		startLoading();
		/**
		 * 提交数据
		 */
		JsonLoader jsonLoader = new JsonLoader(this);
		jsonLoader.setUrl(AppNetUrl.getDelegateSupplyUrl());
		jsonLoader.setMethod(BaseNetLoder.Method_Post);
		jsonLoader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		jsonLoader.setPostData(MorePublishManager.getDelegateSupplyPostData(companyId, sourceInfo));
		jsonLoader.addRequestHeader("token", token);
		jsonLoader.addRequestHeader("iscompany", iscompany);
		jsonLoader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object tag, int responseCode, String errorMessage) {
				displayToast(errorMessage);
				stopLoading();
			}

			@Override
			public void onCompelete(Object arg0, Object result) {

				stopLoading();

				JSONArray array = (JSONArray) result;
				// 服务器返回信息
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);
				displayToast(info.getMsg());
				// 当成功时跳转至成功界面
				if (1 == info.getStatus()) {
					goActivity(SucceeMainActivity.class, 2);
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);

	}

	private String city;
	private String devalite;
	private String place;
	private String category;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
				mTvContactCity.setText(text);
			}

			// 交割地
			if (resultCode == 2) {

				String text = data.getStringExtra("text");
				devalite = info.getId();
				mTvDelivery.setText(text);
			}

			// 产地
			if (resultCode == 3) {

				String text = data.getStringExtra("text");
				place = info.getId();
				mTvPlace.setText(text);
			}

			// 分类
			if (resultCode == 4) {

				String text = data.getStringExtra("text");
				category = info.getId();
				mTViClassify.setText(text);
			}
		}
	}

}
