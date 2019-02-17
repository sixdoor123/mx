package com.baiyi.cmall.activities.user.buyer.intention;

import org.json.JSONArray;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.baiyi.cmall.activities.base.BaseMsgActivity;
import com.baiyi.cmall.activities.main._public.CitySelectionActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.OrderEntity;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.request.manager.MyPurAttentionManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.JsonParse_User;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

public class EditConsigneeActivity extends BaseMsgActivity implements
		OnClickListener {

	// 收货人
	private EditText mEtPersonName;
	// 手机号码
	private EditText mEtPhoneNum;
	// 地区
	private TextView mTvCity;
	// 地址详情
	private EditText mEtAddressDetail;
	// 地址详情
	private CheckBox mCbIsDefault;
	// 地址选择
	private TableRow mTbCity;
	// 保存按钮
	private TextView mBtSave;
	// 数据源
	private OrderEntity consigneeEntity;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);
		initTitle();
		initData();
		setContent();
	}

	/**
	 * 舒适化数据
	 */
	private void initData() {
		consigneeEntity = (OrderEntity) getIntent()
				.getSerializableExtra("info");
	}

	/**
	 * 初始化标题信息
	 */
	private void initTitle() {
		EventTopTitleView topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("编辑收货地址");
		win_title.addView(topTitleView);

		// 在此不显示更多按钮，所以将其隐藏
		FrameLayout frameLayout = (FrameLayout) findViewById(R.id.msg_layout);
		frameLayout.setVisibility(View.INVISIBLE);
	}

	/**
	 * 设置内容
	 */
	private void setContent() {

		ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_edit_consignee, win_content);

		mEtPersonName = (EditText) findViewById(R.id.edt_person_name);
		mEtPhoneNum = (EditText) findViewById(R.id.edt_phone_num);
		mTvCity = (TextView) findViewById(R.id.tv_edit_city);
		mEtAddressDetail = (EditText) findViewById(R.id.edt_edit_address);
		mCbIsDefault = (CheckBox) findViewById(R.id.rb_is_default);
		mTbCity = (TableRow) findViewById(R.id.trb_city);
		mTbCity.setOnClickListener(this);
		mBtSave = (TextView) findViewById(R.id.btn_save);
		mBtSave.setOnClickListener(this);

		mEtPersonName.setText(consigneeEntity.getReceivername());
		mEtPhoneNum.setText(consigneeEntity.getPhone());
		mTvCity.setText(consigneeEntity.getOrderCity());
		mEtAddressDetail.setText(consigneeEntity.getAddress());
		mCbIsDefault.setChecked(consigneeEntity.isDefault());
		if (consigneeEntity.isDefault()) {
			mCbIsDefault.setVisibility(View.GONE);
		}

		city = consigneeEntity.getOrderCityId();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.trb_city:
			Intent intent = new Intent(this, CitySelectionActivity.class);
			intent.putExtra("temp", mTvCity.getText().toString());
			startActivityForResult(intent, 1);
			break;
		case R.id.btn_save:
			getAddInfo();
			break;

		default:
			break;
		}
	}

	// 收货人Id
	private String personId;
	// 收货人
	private String personName;
	// 手机号码
	private String phoneNum;
	// 地区
	private String city = "00010001";
	// 地址详情
	private String addressDetail;
	// 是否默认
	private boolean isDefault;

	// 用戶ID获取
	private String userId;

	/**
	 * 获取填写的收货人信息
	 */
	private void getAddInfo() {

		userId = CmallApplication.getUserInfo().getUserID();

		personId = consigneeEntity.getId();
		personName = mEtPersonName.getText().toString();
		phoneNum = mEtPhoneNum.getText().toString();
		addressDetail = mEtAddressDetail.getText().toString();
		isDefault = mCbIsDefault.isChecked();

		/*
		 * 判断输入的信息是否合格
		 */
		if (TextUtils.isEmpty(personName)) {
			displayToast("收货人不能为空");
			return;
		} else if (personName.length() > 50) {
			displayToast("收获人输入过长");
			return;
		}
		if (TextUtils.isEmpty(phoneNum)) {
			displayToast("手机号码不能为空");
			return;
		} else if (!(Utils.isPhoneNum(phoneNum) && Utils
				.isPhoneNumberValid(phoneNum))) {
			displayToast("手机号码填写不正确");
			return;
		}
		if (TextUtils.isEmpty(city)) {
			displayToast("请选择地区");
			return;
		} 
		if (TextUtils.isEmpty(addressDetail)) {
			displayToast("详细地址不能为空");
			return;
		} else if (addressDetail.length() > 50) {
			displayToast("详细地址输入过长");
			return;
		}

		saveInfo();
	}

	/**
	 * 保存操作
	 */
	private void saveInfo() {

		JsonLoader jsonLoader = new JsonLoader(this);
		jsonLoader.setUrl(AppNetUrl.getEditConsigneeUrl());
		jsonLoader.setPostData(MyPurAttentionManager.getEditReceiverPostData(
				userId, personId, personName, city, addressDetail, phoneNum,
				isDefault));
		jsonLoader.setMethod(BaseNetLoder.Method_Post);
		jsonLoader.addRequestHeader("token", CmallApplication.getUserInfo()
				.getToken());
		jsonLoader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		jsonLoader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {
				displayToast(errorMessage);
			}

			@Override
			public void onCompelete(Object arg0, Object result) {
				JSONArray array = (JSONArray) result;

				// 服务器返回结果
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);

				if (1 == info.getStatus()) {

					finish();
				}

			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);
	}

	/**
	 * 选择城市，回调
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (null == data) {
			return;
		}
		SelectedInfo info = (SelectedInfo) data.getSerializableExtra("key");

		if (null != info) {
			city = info.getId();
			mTvCity.setText(data.getStringExtra("text"));
		}

	}

}
