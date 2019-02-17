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
 * �޸�������Ϣ
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-23 ����4:49:57
 */
public class EditModifyLogisticsActivity extends BaseActivity implements
		OnClickListener {

	// �û�ID
	private String userID;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);
		initData();
		initTitle();
		initContent();
	}

	// ����Դ
	private GoodsSourceInfo info;

	/**
	 * ����
	 */
	private void initData() {
		userID = CmallApplication.getUserInfo().getUserID();

		info = (GoodsSourceInfo) getIntent().getSerializableExtra("key");
	}

	// �Զ��������
	protected EventTopTitleView topTitleView = null;

	/**
	 * ������
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("�༭ί������");
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
	 * �޸������ľ�������
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_edit_logistics_info, null);
		win_content.addView(view);

		findViewById(view);
		updateViewData();
	}

	// ��˾����
	private EditText mEdtCompanyName;
	// ��ϵ��
	private EditText mEdtContactPerson;
	// ��ϵ��ʽ
	private EditText mEdtContactNumber;
	// ����
	private EditText medtGoodSTitle;
	// ����
	private EditText mEdtGoodSContent;

	// ����ʼ�����е���Ŀ
	private LinearLayout mLlStartContury;
	// ʼ�����е���ϸ��ַ
	private EditText mEdtStartDetailAddress;
	// ����Ŀ�ĳ��е���Ŀ
	private LinearLayout mLlEndContury;
	// Ŀ�ĳ��е���ϸ��ַ
	private EditText mEdtEndDetailAddress;
	// ����
	private EditText mEdtGoodSWeight;
	// ����ʼ��ʱ�����Ŀ
	private LinearLayout mLlStartTime;
	// ���Ƶ���ʱ�����Ŀ
	private LinearLayout mLlEndTime;
	// ���ƻ������ͻ�������
	private LinearLayout mLlCargoType;
	// ���ư�װ��ʽ����Ŀ
	private LinearLayout mLlPackageWay;

	// ������ϸ������Ϣ�Ƿ���ʾ
	private TableLayout mLlControlDetailLogistics;
	// ������ϸ�����Ƿ���ʾ
	private TextView mTxtDetailUpDown;

	// ��ɰ�ť
	private TextView mBtnCommitComplete;
	// ����
	private MyLoadingBar loadingBar;

	// ��ʾʼ������
	private TextView mTxtStartCity;
	// ��ʾĿ�ĳ���
	private TextView mTxtEndCity;
	// ��ʾʼ��ʱ��
	private TextView mTxtStartTime;
	// ��ʾ����ʱ��
	private TextView mTxtEndTime;
	// ��ʾ��������
	private TextView mTxtFreightType;
	// ��ʾ��װ��ʽ
	private TextView mTxtPackType;

	/**
	 * �ҿؼ�
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

		// ��ӡ�*��
		TextViewUtil.setText(R.id.contact, "��ϵ��", view);
		TextViewUtil.setText(R.id.moble, "��ϵ��ʽ", view);
		TextViewUtil.setText(R.id.title, "����", view);
		TextViewUtil.setText(R.id.content, "����", view);
		// TextViewUtil.setText(R.id.company, "��˾����", view);

		mEdtGoodSContent.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				// ��仰˵����˼���߸�View���Լ����¼����Լ�����
				v.getParent().requestDisallowInterceptTouchEvent(true);
				return false;
			}

		});
	}

	/**
	 * ���½����ϵ�����
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
			// ��������
			mEdtGoodSWeight.setText("0".equals(getEditString(info
					.getGoodSWeight())) ? "" : getEditString(info
					.getGoodSWeight()));
			mTxtStartCity.setText(getSelectString(info.getStartAddress()));
			mTxtEndCity.setText(getSelectString(info.getEndAddress()));

			// ���ÿ�ʼʱ��
			mTxtStartTime.setText(getSelectString(info.getGoodSStartTime()));
			// ���ý���ʱ��
			mTxtEndTime.setText(getSelectString(info.getGoodSEndTime()));

			mTxtFreightType
					.setText(getSelectString(info.getDeliverytypename()));
			mTxtPackType.setText(getSelectString(info.getPacktypename()));
		}
	}

	private String getSelectString(String s) {

		if ("null".equals(s) || null == s || "".equals(s)) {
			return "��ѡ��";
		}

		return s;
	}

	private String getEditString(String s) {

		if ("null".equals(s) || null == s || "".equals(s)) {
			return "";
		}

		return s;
	}

	// ��־λ
	private boolean flag = true;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_start_contury:// ��ʼ����
			startCity();
			break;
		case R.id.ll_end_contury:// Ŀ�ĳ���
			goalCity();
			break;
		case R.id.ll_start_time:// ��ʼʱ��
			startTime();
			break;
		case R.id.ll_end_time:// ����ʱ��
			endTime();
			break;
		case R.id.ll_cargo_type:// ��������
			goActivity(FreightWayActivity.class);
			break;
		case R.id.ll_package_way:// ��װ��ʽ
			goActivity(PackagingWayActivity.class);
			break;
		case R.id.btn_commit_complete:// ��ɰ�ť
			completed();
			break;
		case R.id.txt_detail_up_down:// ������ϸ��Ϣ�Ƿ�Ҫ��ʾ
			if (flag) {
				mLlControlDetailLogistics.setVisibility(View.VISIBLE);
				mTxtDetailUpDown.setText("�����");
				flag = false;
			} else {
				mLlControlDetailLogistics.setVisibility(View.GONE);
				mTxtDetailUpDown.setText("ί�����ơ�");
				flag = true;
			}
			break;

		}
	}

	// �û�����ķ�װʵ����
	private GoodsSourceInfo sourceInfo = null;

	/**
	 * ��ɰ�ť
	 */
	private void completed() {
		// �õ������ʵ����
		String result = getSourceInfo();
		if (null != result) {
			displayToast(result);
			return;
		}

		if (TextUtils.isEmpty(userID)) {
			displayToast("�û�δ��¼,���¼������!");
			return;
		}

		loadingBar.setVisibility(View.VISIBLE);
		loadingBar.setProgressInfo("�����ύ��...");
		loadingBar.start();

		mLlControlDetailLogistics.setVisibility(View.GONE);
		mTxtDetailUpDown.setText("ί�����ơ�");
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

	// ״̬��־λ���ж���Ϣ�Ƿ�������ȫ
	private int state;

	/**
	 * �õ��û��޸��Ժ����Ϣ
	 * 
	 * @return
	 */
	private String getSourceInfo() {
		sourceInfo = new GoodsSourceInfo();

		// ��˾����
		String companyName = mEdtCompanyName.getText().toString().trim();
		if (!Utils.isExceed_30(companyName)) {
			return "��˾�����������";
		}
		sourceInfo.setGoodSMerchant(companyName);

		// ��ϵ��
		String contact = mEdtContactPerson.getText().toString().trim();

		if (TextUtils.isEmpty(contact)) {
			return "��ϵ�˲���Ϊ��";
		}
		if (!Utils.isExceed_20(companyName)) {
			return "��ϵ���������";
		}
		sourceInfo.setGoodSContactPerson(contact);

		// ��ϵ��ʽ
		String number = mEdtContactNumber.getText().toString().trim();
		if (TextUtils.isEmpty(number)) {
			// displayToast();
			return "��ϵ��ʽ����Ϊ��";
		}
		if (!Utils.isExceed_20(number)) {
			return "��ϵ��ʽ�������";
		}
		if (!(Utils.isPhoneNum(number) && Utils.isPhoneNumberValid(number))) {
			return "��ϵ��ʽ��д����ȷ";
		}
		sourceInfo.setGoodSContactWay(number);

		// ����
		String title = medtGoodSTitle.getText().toString().trim();
		if (TextUtils.isEmpty(title)) {
			// displayToast();
			return "���ⲻ��Ϊ��";
		}
		if (!Utils.isExceed_30(title)) {
			return "�����������";
		}
		sourceInfo.setGoodSTitle(title);

		// ����
		String content = mEdtGoodSContent.getText().toString().trim();

		if (TextUtils.isEmpty(content)) {
			// displayToast();
			return "���ݲ���Ϊ��";
		}
		if (!Utils.isExceed_1000(content)) {
			return "�����������";
		}
		sourceInfo.setGoodSContent(content);

		// ʼ��������ϸ��ַ
		String startAddress = mEdtStartDetailAddress.getText().toString()
				.trim();
		if (!Utils.isExceed_30(startAddress)) {
			return "ʼ��������ϸ��ַ�������";
		}
		sourceInfo.setStartAddress(startAddress);

		// Ŀ�ĳ���
		String endAddress = mEdtEndDetailAddress.getText().toString().trim();
		if (!Utils.isExceed_30(endAddress)) {
			return "Ŀ�ĳ�����ϸ��ַ�������";
		}
		sourceInfo.setEndAddress(endAddress);

		/*
		 * �ж�����-----------------------------------
		 */
		String weight = Utils.getNumberOfString(mEdtGoodSWeight.getText()
				.toString());
		if (!"OK".equals(HintUtils.getWeight(weight))) {
			return HintUtils.getWeight(weight);
		}
		if (!Utils.isExceed_7(weight)) {
			return "�����������";
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
	 * Ŀ�ĳ���
	 */
	private void goalCity() {
		goActivity(mTxtEndCity.getText().toString(),
				CitySelectionActivity.class, 1);
	}

	/**
	 * ������ʼ����ѡ��
	 */
	private void startCity() {
		goActivity(mTxtStartCity.getText().toString(),
				CitySelectionActivity.class, 0);
	}

	// ��ʼ����
	private SelectedInfo startCityInfo;
	// Ŀ�ĳ���
	private SelectedInfo endCityInfo;
	// ���˷�ʽ
	private SelectedInfo freightInfo;
	// ��װ��ʽ
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
	 * ����ʱ��
	 */
	private void endTime() {
		DateSelectorView view = new DateSelectorView(this, "ѡ�񵽴�����");
		view.show();
		view.setListener(new OnDateSelectedClickListener() {

			@Override
			public void onDateSelected(String date) {
				// displayToast("�������ڣ�" + date);
				mTxtEndTime.setText(date);
			}
		});
	}

	/**
	 * ѡ��ʼ��ʱ��
	 */
	private void startTime() {
		DateSelectorView view = new DateSelectorView(this, "ѡ��ʼ������");
		view.show();
		view.setListener(new OnDateSelectedClickListener() {

			@Override
			public void onDateSelected(String date) {
				// displayToast("ʼ�����ڣ�" + date);
				mTxtStartTime.setText(date);
			}
		});
	}

}
