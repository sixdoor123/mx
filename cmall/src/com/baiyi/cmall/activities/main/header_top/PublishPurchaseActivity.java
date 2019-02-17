package com.baiyi.cmall.activities.main.header_top;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
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
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * �ɹ�������-��Ҫ�ɹ�
 * 
 * @author sunxy
 * 
 */
@SuppressLint("ResourceAsColor")
public class PublishPurchaseActivity extends BaseMsgActivity implements
		OnClickListener {
	// �ɹ�������
	private EditText mEtMerchant;
	// ��ϵ������
	private EditText mEtContactPerson;
	// ��ϵ��ʽ
	private EditText mEtContactPhone;
	// ����
	private EditText mEtTitle;
	// �ɹ�����
	private EditText mEtNeed;

	// ��ϵ�˳���
	private TextView mTvContactCity;
	// ��ϸ��ַ
	private EditText mEtAdress;
	// ����
	private TextView mTViClassify;
	// �����
	private TextView mTvDelivery;
	// ����
	private TextView mTvPlace;
	// Ʒ��
	private TextView mEtGoodSCategory;
	// ����
	private EditText mEtWeight;
	// �۸�
	private EditText mEdgoodSPrice;
	// // ʼ��ʱ��
	// private TextView mTvStartTime;
	// ����ʱ��
	// private TextView mTvendTime;
	// ��Ҫ���͵�ʵ����
	private GoodsSourceInfo sourceInfo;
	/**
	 * ����ѡ�����ݵİ�ť
	 */
	// ��ϵ�˳���ѡ��
	private TableRow mTrbContactCity;
	// ����ѡ��
	private TableRow mTrbClassify;
	// �����ѡ��
	private TableRow mTrbDeliveryDi;
	// ����ѡ��
	private TableRow mTrbPlace;
	// ʼ��ʱ��ѡ��
	@SuppressWarnings("unused")
	private TableRow mTrbStartTime;
	// ����ʱ��ѡ��
	@SuppressWarnings("unused")
	private TableRow mTrbEndTime;

	// ��ɰ�ť
	private TextView mTvSubmit;
	// �Ƿ���ʾȫ����ť
	private TextView mTvUpDown;
	// �ж��Ƿ���ʾ����Ա�����
	private boolean isVisible;

	private TableLayout mLlControlDetailLogistics;

	// �Ñ�ID��ȡ
	private String userId;

	@Override
	protected void initWin(boolean hasScrollView) {
		super.initWin(true);

		ActivityStackControlUtil.add(this);

		intUserId();
		initTitle();
		initContent();
	}

	private LoginInfo loginInfo;

	/**
	 * ��ʼ���û�ID
	 */
	private void intUserId() {

		XmlUtils utils = XmlUtils.getInstence(this);
		userId = utils.getXmlStringValue(XmlName.USER_ID);
		if (!TextUtils.isEmpty(userId)) {
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

	/**
	 * ��Ӷ���������
	 */
	private void initTitle() {

		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("ί�вɹ�");
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
				MsgItemUtil.onclickPopItem(state, PublishPurchaseActivity.this);
			}
		});
		win_title.addView(topTitleView);

	}

	/**
	 * ��ʼ������
	 */
	private void initContent() {

		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_purchase_order, win_content);
		// �ɹ�������
		mEtMerchant = (EditText) findViewById(R.id.edt_company_name);
		// ��ϵ��
		mEtContactPerson = (EditText) findViewById(R.id.edt_contact_person);
		// ��ϵ�˵绰
		mEtContactPhone = (EditText) findViewById(R.id.edt_contact_ways);
		// ����
		mEtTitle = (EditText) findViewById(R.id.edt_goods_title);
		// �ɹ�����
		mEtNeed = (EditText) findViewById(R.id.edt_goods_need);

		// ��ϵ�˳���
		mTvContactCity = (TextView) findViewById(R.id.tv_contact_city);
		// ��ϸ��ַ
		mEtAdress = (EditText) findViewById(R.id.edt_end_detail_address);
		// ����
		mTViClassify = (TextView) findViewById(R.id.tv_classify);
		// �����
		mTvDelivery = (TextView) findViewById(R.id.tv_devilate_di);
		// ����
		mTvPlace = (TextView) findViewById(R.id.tv_place);
		// Ʒ��
		mEtGoodSCategory = (EditText) findViewById(R.id.tv_variety);
		// ����
		mEtWeight = (EditText) findViewById(R.id.et_weight);
		// �۸�
		mEdgoodSPrice = (EditText) findViewById(R.id.et_price);

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

		// ���
		TextViewUtil.setText(R.id.contact, "��ϵ��", view);
		TextView phone = (TextView) findViewById(R.id.phone);
		phone.setText(Html.fromHtml("<font color=\"red\">*</font>" + "��ϵ��ʽ"));
		TextView title = (TextView) findViewById(R.id.pur_title);
		title.setText(Html.fromHtml("<font color=\"red\">*</font>" + "����"));
		TextView pur_need = (TextView) findViewById(R.id.pur_need);
		pur_need.setText(Html.fromHtml("<font color=\"red\">*</font>" + "�ɹ�����"));

		UpdataViewDate();
	}

	private void UpdataViewDate() {
		if (null != loginInfo) {
			// �ɹ�������
			mEtMerchant.setText(getEditString(loginInfo.getCompanyName()));
			// ��ϵ��
			mEtContactPerson.setText(getEditString(loginInfo.getUserName()));
			// ��ϵ�˵绰
			mEtContactPhone.setText(getEditString(loginInfo.getMobile()));
		}
	}

	private String getEditString(String s) {

		if ("null".equals(s) || null == s || "".equals(s)) {
			return "";
		}

		return s;
	}

	/**
	 * ����¼�
	 */
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.trb_contact_city:// ѡ�����
			goActivity(mTvContactCity.getText().toString(),
					CitySelectionActivity.class, 1);
			break;
		case R.id.trb_classify:// ѡ�����
			goActivity(GoodSCategoryActivity.class);
			break;
		case R.id.trb_delivery_di:// ѡ�񽻸��
			goActivity(mTvDelivery.getText().toString(),
					CitySelectionActivity.class, 2);
			break;
		case R.id.trb_Place:// ѡ�����
			goActivity(mTvPlace.getText().toString(),
					CitySelectionActivity.class, 3);
			break;
		case R.id.btn_commit_complete:
			// TODO ����ύ�ɹ��������D���ɹ�����
			commitData();
			break;

		// ί�����ơ������л�
		case R.id.tv_up_down:
			Log.d("TAG", isVisible + "");
			if (isVisible) {
				mLlControlDetailLogistics.setVisibility(View.GONE);
				mTvUpDown.setText("ί�����ơ�");
				isVisible = false;
			} else {
				mLlControlDetailLogistics.setVisibility(View.VISIBLE);
				mTvUpDown.setText("�����");
				isVisible = true;
			}
			break;

		}
	}

	/**
	 * ��ȡ�ַ�������
	 */
	private void commitData() {
		// ��Ӧ��
		String merchant = mEtMerchant.getText().toString();
		
		// ��ϵ��
		String contantPerson = mEtContactPerson.getText().toString();
		if (TextUtils.isEmpty(contantPerson)) {
			displayToast("��ϵ�˲���Ϊ��");
			return;
		}
		// ��ϵ�˵绰
		String contantPhone = mEtContactPhone.getText().toString();
		if (TextUtils.isEmpty(contantPhone)) {
			displayToast("��ϵ��ʽ����Ϊ��");
			return;
		}
		if (!(Utils.isPhoneNum(contantPhone) && Utils
				.isPhoneNumberValid(contantPhone))) {
			displayToast("��ϵ��ʽ��д����ȷ");
			return;
		}
		// ����
		String title = mEtTitle.getText().toString();
		if (TextUtils.isEmpty(title)) {
			displayToast("���ⲻ��Ϊ��");
			return;
		}
		// ����
		String need = mEtNeed.getText().toString();
		if (TextUtils.isEmpty(need)) {
			displayToast("������Ϊ��");
			return;
		}
		// ��ϸ��ַ��Ϊ��ʱ����ϵ�˳��б���ѡ������
		String adress = mEtAdress.getText().toString();
		
		/**
		 * ��������ʵ����
		 */
		sourceInfo = new GoodsSourceInfo();
		sourceInfo.setGoodSMerchant(merchant);
		sourceInfo.setGoodSContactPerson(contantPerson);
		sourceInfo.setGoodSContactWay(contantPhone);
		sourceInfo.setGoodSTitle(title);
		sourceInfo.setGoodSPurchaseNeed(need);
		sourceInfo.setAddress(mEtAdress.getText().toString());

		/*
		 * �ж�����-----------------------------------
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
		 * �жϼ۸�-----------------------------------
		 */
		String price = Utils.getNumberOfString(mEdgoodSPrice.getText()
				.toString());

		if (!TextUtils.isEmpty(price)) {

			if (!"OK".equals(HintUtils.priceHint(price))) {
				displayToast(HintUtils.priceHint(price));
				return;
			}
			sourceInfo.setGoodSPrice(price);
		}
		sourceInfo.setGoodSBrand(mEtGoodSCategory.getText().toString());

		sourceInfo.setCityid(city);
		sourceInfo.setDeliverycityid(devalite);
		sourceInfo.setOrigincityid(place);
		sourceInfo.setCategoryID(category);
		supplyProvider();

	}

	/**
	 * ��������ύ����
	 */
	private void supplyProvider() {

		startLoading();
		
		JsonLoader jsonLoader = new JsonLoader(this);
		jsonLoader.setUrl(AppNetUrl.getDelegatePurchaseUrl());
		jsonLoader.setMethod(BaseNetLoder.Method_Post);
		jsonLoader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		jsonLoader.setPostData(MorePublishManager.getDelegatePurchasePostData(
				userId, sourceInfo));
		jsonLoader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {
				displayToast(errorMessage);
				stopLoading();

			}

			@Override
			public void onCompelete(Object arg0, Object result) {

				stopLoading();
				
				JSONArray array = (JSONArray) result;
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);
				displayToast(info.getMsg());
				// ���ɹ�ʱ��ת���ɹ�����
				if (1 == info.getStatus()) {
					goActivity(SucceeMainActivity.class, 3);
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

			// ��ϵ�˳���
			if (resultCode == 1) {

				String text = data.getStringExtra("text");
				city = info.getId();
				mTvContactCity.setText(text);
			}

			// �����
			if (resultCode == 2) {

				String text = data.getStringExtra("text");
				devalite = info.getId();
				mTvDelivery.setText(text);
			}

			// ����
			if (resultCode == 3) {

				String text = data.getStringExtra("text");
				place = info.getId();
				mTvPlace.setText(text);
			}

			// ����
			if (resultCode == 4) {

				String text = data.getStringExtra("text");
				category = info.getId();
				mTViClassify.setText(text);
			}
		}
	}

}
