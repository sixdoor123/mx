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

	// �ջ���
	private EditText mEtPersonName;
	// �ֻ�����
	private EditText mEtPhoneNum;
	// ����
	private TextView mTvCity;
	// ��ַ����
	private EditText mEtAddressDetail;
	// ��ַ����
	private CheckBox mCbIsDefault;
	// ��ַѡ��
	private TableRow mTbCity;
	// ���水ť
	private TextView mBtSave;
	// ����Դ
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
	 * ���ʻ�����
	 */
	private void initData() {
		consigneeEntity = (OrderEntity) getIntent()
				.getSerializableExtra("info");
	}

	/**
	 * ��ʼ��������Ϣ
	 */
	private void initTitle() {
		EventTopTitleView topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("�༭�ջ���ַ");
		win_title.addView(topTitleView);

		// �ڴ˲���ʾ���ఴť�����Խ�������
		FrameLayout frameLayout = (FrameLayout) findViewById(R.id.msg_layout);
		frameLayout.setVisibility(View.INVISIBLE);
	}

	/**
	 * ��������
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

	// �ջ���Id
	private String personId;
	// �ջ���
	private String personName;
	// �ֻ�����
	private String phoneNum;
	// ����
	private String city = "00010001";
	// ��ַ����
	private String addressDetail;
	// �Ƿ�Ĭ��
	private boolean isDefault;

	// �Ñ�ID��ȡ
	private String userId;

	/**
	 * ��ȡ��д���ջ�����Ϣ
	 */
	private void getAddInfo() {

		userId = CmallApplication.getUserInfo().getUserID();

		personId = consigneeEntity.getId();
		personName = mEtPersonName.getText().toString();
		phoneNum = mEtPhoneNum.getText().toString();
		addressDetail = mEtAddressDetail.getText().toString();
		isDefault = mCbIsDefault.isChecked();

		/*
		 * �ж��������Ϣ�Ƿ�ϸ�
		 */
		if (TextUtils.isEmpty(personName)) {
			displayToast("�ջ��˲���Ϊ��");
			return;
		} else if (personName.length() > 50) {
			displayToast("�ջ����������");
			return;
		}
		if (TextUtils.isEmpty(phoneNum)) {
			displayToast("�ֻ����벻��Ϊ��");
			return;
		} else if (!(Utils.isPhoneNum(phoneNum) && Utils
				.isPhoneNumberValid(phoneNum))) {
			displayToast("�ֻ�������д����ȷ");
			return;
		}
		if (TextUtils.isEmpty(city)) {
			displayToast("��ѡ�����");
			return;
		} 
		if (TextUtils.isEmpty(addressDetail)) {
			displayToast("��ϸ��ַ����Ϊ��");
			return;
		} else if (addressDetail.length() > 50) {
			displayToast("��ϸ��ַ�������");
			return;
		}

		saveInfo();
	}

	/**
	 * �������
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

				// ���������ؽ��
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);

				if (1 == info.getStatus()) {

					finish();
				}

			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);
	}

	/**
	 * ѡ����У��ص�
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