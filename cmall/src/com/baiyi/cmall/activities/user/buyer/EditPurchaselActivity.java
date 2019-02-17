package com.baiyi.cmall.activities.user.buyer;

import java.util.ArrayList;

import org.json.JSONArray;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.base.BaseMsgActivity;
import com.baiyi.cmall.activities.user.buyer.form.FormStateUtils;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.IntentionDetailStandardInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.pageviews.MyViewPager;
import com.baiyi.cmall.pageviews.PageView;
import com.baiyi.cmall.pageviews.ViewPagerSelected;
import com.baiyi.cmall.request.manager.MyPurchaseManager;
import com.baiyi.cmall.request.manager.NullJsonData;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.JsonParse_User;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

/**
 * 
 * �ҵĲɹ����� ���򡪡��༭�ɹ�����Activity
 * 
 * @author lizl
 * 
 */
public class EditPurchaselActivity extends BaseMsgActivity implements
		OnCheckedChangeListener, OnClickListener {

	// �ɹ�����
	private RadioButton mRbProviderDetails;
	// ����
	private RadioButton mRbProviderStandard;
	// RadioGroup
	private RadioGroup mRgCollecteGroup;
	// ��Ӧ�����µ�����
	private ImageView mImgDetailsChose;
	// �����µ�����
	private ImageView mImgStandardChose;

	// �Զ����ViewPager
	private MyViewPager mVpProvider;
	// ��������
	private View view;
	// �Ƿ���ʾ ---����/���� ����Ϣ
	private boolean isShow;
	// ����ID/�ɹ�ID
	private int intentId;
	// �Ƿ���ʾ�༭��ť
	private String binaryValue;
	// �ܷ��������
	private boolean isEditPurName;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);
		intData();
		initTitle();
		initRBContent();
		initViewPager();

	}

	/**
	 * ����Դ
	 */
	private void intData() {

		isShow = getIntent().getBooleanExtra("show", true);

		intentId = getIntent().getIntExtra("intent", 0);

		binaryValue = getIntent().getStringExtra("state");

		isEditPurName = getIntent().getBooleanExtra("is_edit", true);

	}

	/**
	 * ����
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
				MsgItemUtil.onclickPopItem(state, EditPurchaselActivity.this);
			}
		});
		topTitleView.setEventName("�༭�ɹ�����");
		win_title.addView(topTitleView);
	}

	/**
	 * ��ʼ��RadioGroup������
	 */
	private void initRBContent() {
		view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_collection_list, null);
		win_title.addView(view);

		mRgCollecteGroup = (RadioGroup) view.findViewById(R.id.collect_group);
		mRbProviderDetails = (RadioButton) view
				.findViewById(R.id.provider_collection);
		mRbProviderDetails.setText("�ɹ���Ϣ");

		mRbProviderStandard = (RadioButton) view
				.findViewById(R.id.purchase_collection);
		mRbProviderStandard.setText("����");

		mImgDetailsChose = (ImageView) view.findViewById(R.id.provider_choose);
		mImgStandardChose = (ImageView) view.findViewById(R.id.purchase_choose);

		mVpProvider = (MyViewPager) view.findViewById(R.id.vp_collection);

		mRbProviderDetails.setChecked(true);
		mRgCollecteGroup.setOnCheckedChangeListener(this);

	}

	// ��Ӧ����ViewPager
	private EditPurchaserDetailViewPager detailsPager;
	// ���Ե�ViewPager
	private EditPurchaseStandardArgViewPager standardViewPager;
	// ���ViewPager�ļ���
	private ArrayList<PageView> pageViews;

	/**
	 * ��ʼ��ViewPage
	 */
	private void initViewPager() {
		pageViews = new ArrayList<PageView>();
		// purId:�ɹ�ID
		// state�������Ǳ༭��ť�����Ǳ��水ť
		// showStat:������ʾ״̬
		detailsPager = new EditPurchaserDetailViewPager(this, intentId, isShow);
		standardViewPager = new EditPurchaseStandardArgViewPager(this,
				intentId, isShow);

		pageViews.add(detailsPager);
		pageViews.add(standardViewPager);

		mVpProvider.init(pageViews, 0);

		mVpProvider.setViewPageSelectedLister(new ViewPagerSelected() {

			@Override
			public void onPageSelected(int currentNum) {
				setButtonPerformClick(currentNum);
			}
		});
		// ��ʼ���ײ��ؼ�
		initFoot();
	}

	/**
	 * ����Button�ļ���
	 * 
	 * @param position
	 */
	private void setButtonPerformClick(int position) {
		if (position == 0) {
			mRbProviderDetails.performClick();
		} else if (position == 1) {
			mRbProviderStandard.performClick();
		}
	}

	/**
	 * RadioGroup �ļ����¼��Ļٵ�����
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		int id = 0;
		if (checkedId == R.id.provider_collection) {
			id = 0;// ��Ӧ��Ϣ
			mImgDetailsChose.setVisibility(View.VISIBLE);
			mImgStandardChose.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.purchase_collection) {
			id = 1;// ����
			mImgDetailsChose.setVisibility(View.INVISIBLE);
			mImgStandardChose.setVisibility(View.VISIBLE);
		}

		mVpProvider.setPageIndex(id);
		mVpProvider.setCurrentItem(id);

	}

	public TextView mEdiTextView;
	public TextView mBtnCancel;

	/**
	 * ��ʼ���ײ����o
	 */
	private void initFoot() {

		LinearLayout footView = (LinearLayout) view
				.findViewById(R.id.lin_foot_button);
		footView.setVisibility(View.VISIBLE);
		mEdiTextView = (TextView) view.findViewById(R.id.btn_commit_modify);
		mBtnCancel = (TextView) view.findViewById(R.id.btn_cancel_modify);
		mBtnCancel.setText("ɾ��");
		mBtnCancel.setOnClickListener(this);

		/**
		 * ���ݲ�ͬ��״̬��ʾ��ͬ�İ�ť 0������ 1���༭
		 */
		mEdiTextView.setText("�༭���");

		// �ɹ������еı༭(�������״̬�ж��Ƿ���ʾ)
		if (isShow) {

			mBtnCancel.setVisibility(View.VISIBLE);
		} else {
			mBtnCancel.setVisibility(View.GONE);

			if (View.VISIBLE == FormStateUtils.isShow(binaryValue,// �༭��ť
					FormStateUtils.STATE_EDIT)
					|| View.VISIBLE == FormStateUtils.isShow(binaryValue,// �ɹ���ť
							FormStateUtils.STATE_BUY)
					|| View.VISIBLE == FormStateUtils.isShow(binaryValue,// ��������ť
							FormStateUtils.STATE_FASONG)) {
				mEdiTextView.setVisibility(View.VISIBLE);
				// �ж������Ƿ�ɱ༭
				if (!isEditPurName) {
					detailsPager.setPurNameNoEdit();
				}
			} else {
				footView.setVisibility(View.GONE);
				// ��������༭�򲻿ɲ���
				detailsPager.setNoEdit();
				// �������Կ򲻿ɲ���---��ӱ�־λ��trueΪ���ɲ�����
				standardViewPager.setStandIsEnable(true);

			}

		}

		mEdiTextView.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_commit_modify:// �༭

			// ��ȡ�༭������
			getEditData();
			break;

		case R.id.btn_cancel_modify:// ɾ��

			deletePur(AppNetUrl.getDeletePurUrl(intentId + ""));
			break;
		}
	}

	private String deliveryId;// �����
	ArrayList<TextView> detailViews;
	ArrayList<TextView> standardViews;
	ArrayList<IntentionDetailStandardInfo> standardId;

	/**
	 * ��ȡ�༭�������
	 */
	public void getEditData() {

		/*
		 * ����ʾʱ���Լ��Զ���ʼѡ���ʱ���ж�����
		 */
		if (isShow) {


			// ����ʱ��
			String startTime = detailsPager.getPubTime();
			// ����ʱ��
			String endTime = detailsPager.getEndTime();

			// ʱ����ʾ��
			String timeHintInfo = HintUtils.timeHint(startTime,endTime);
			if (!"OK".equals(timeHintInfo)) {
					displayToast(timeHintInfo);
					return;
			}
		}

		detailViews = detailsPager.getAllText(); 

		/*
		 * �ж�����
		 */
		String purName = detailViews.get(0).getText().toString().trim();
		if (TextUtils.isEmpty(purName)) {
			displayToast("���Ʋ���Ϊ��");
			return;
		}
		/*
		 * �ж�����
		 */
		String weight = Utils.getNumberOfString(detailViews.get(1).getText()
				.toString());
		if (!"OK".equals(HintUtils.weightHint(weight))) {
			displayToast(HintUtils.weightHint(weight));
			return;
		}
		/*
		 * �жϼ۸�-----------------------------------
		 */
		String price = Utils.getNumberOfString(detailViews.get(2).getText()
				.toString());

		if (!"OK".equals(HintUtils.priceHint(price))) {
			displayToast(HintUtils.priceHint(price));
			return;
		}
		/*
		 * �ж�Ԥ�����-----------------------------------
		 */
		String prepayment = Utils.getNumberOfString(detailViews.get(3)
				.getText().toString());

		if (!"OK".equals(HintUtils.prePriceHint(prepayment))) {
			displayToast(HintUtils.prePriceHint(prepayment));
			return;
		}
		deliveryId = detailsPager.getDeliveryId();
		standardViews = standardViewPager.getAllstandardView();
		standardId = standardViewPager.getAllstandardId();
		Log.d("TT", "����ؼ�����---------" + detailViews.size());
		Log.d("TT", "���ؼ�����---------" + standardViews.size());
		Log.d("TT", "���Id����---------" + standardId.size());
		// �ύ����------------------------------------
		getCommitData();
	}

	/**
	 * ��ȡ�ύ������
	 */
	private void getCommitData() {

		// �ɹ��еı༭URL
		String show_editUrl = Config.ROOT_URL + "User/Purchase/Update";
		// �����еı༭URL
		String noShow_editUrl = Config.ROOT_URL + "User/Intention/PurSave";

		if (isShow) {

			// ��ʾʱ�䣬�Զ�ѡ��------��post����
			// �༭�ɹ��У���û�����򣬹�---û������ID
			String show_editPostData = MyPurchaseManager.getMyPurEditPostData(
					intentId + "", detailViews, standardViews, standardId,
					deliveryId);
			commitData(show_editUrl, show_editPostData);
		} else {

			// ����ʾʱ�䣬�Զ�ѡ��------��post����
			// intentId---------��Ҫ����ID�Ͳɹ���ID
			String noShow_PostData_Edit = MyPurchaseManager
					.getMyIntentEditPostData(intentId + "", detailViews,
							standardViews, standardId, deliveryId);
			commitData(noShow_editUrl, noShow_PostData_Edit);
		}

	}

	/**
	 * �༭�����ύ����
	 */
	public void commitData(String url, String postData) {

		JsonLoader jsonLoader = new JsonLoader(this);
		jsonLoader.setUrl(url);
		jsonLoader.setPostData(postData);
		jsonLoader.setMethod(BaseNetLoder.Method_Post);
		jsonLoader.setType("application/json");
		jsonLoader.addRequestHeader("token", CmallApplication.getUserInfo()
				.getToken());
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
					// �ɹ�ʱ���˳��༭ҳ�棬��ȥˢ��
					Intent intent = new Intent();
					setResult(100, intent);
					finish();
				}
				displayToast(info.getMsg());
			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);

	}

	/**
	 * ɾ���ɹ���Ϣ
	 * 
	 * @param url
	 */
	private void deletePur(String url) {
		JsonLoader jsonLoader = new JsonLoader(this);

		jsonLoader.setUrl(url);
		jsonLoader.setPostData(NullJsonData.getPostData());
		jsonLoader.addRequestHeader("token", CmallApplication.getUserInfo()
				.getToken());
		jsonLoader.setMethod(BaseNetLoder.Method_Post);
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
				displayToast(info.getMsg());
				if (1 == info.getStatus()) {
					Intent intent = new Intent();
					setResult(NumEntity.RESULT_CANCEL, intent);
					finish();
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		detailsPager.onActivityResult(requestCode, resultCode, data);
	}

}
