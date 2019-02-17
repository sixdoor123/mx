package com.baiyi.cmall.activities.user.merchant.intention_viewpage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main._public.CitySelectionActivity;
import com.baiyi.cmall.activities.main._public.GoodSBrandActivity;
import com.baiyi.cmall.activities.main._public.GoodSCategoryActivity;
import com.baiyi.cmall.activities.main._public.PriceRangeActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.IntentionProviderDetailInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.pageviews.BaseScrollViewPageView;
import com.baiyi.cmall.request.manager.MerchantCenterManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.MyLinearLayout;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * ���ǹ�Ӧ��-�ҵĹ�Ӧ����-�༭��Ӧ����
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-4 ����2:24:25
 */
public class EditIntentionOrderProviderDetailViewPager extends
		BaseScrollViewPageView implements OnClickListener {

	// ������
	private Context context;
	// ����Դ
	private IntentionProviderDetailInfo info;

	// �Զ�������Բ���
	private MyLinearLayout layout;

	public EditIntentionOrderProviderDetailViewPager(Context context,
			IntentionProviderDetailInfo info) {
		super(context);
		this.context = context;
		this.info = info;

		layout = new MyLinearLayout(context);
		initContent();
		initButton();
		addView(layout);
	}

	/**
	 * ��ʼ����ť
	 */
	private void initButton() {
		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_button, null);
		layout.addView(view);
		mBtnEditCommitComplete = (TextView) view
				.findViewById(R.id.btn_commit_modify);
		mBtnEditCommitComplete.setText("���");
		mBtnEditCommitComplete.setOnClickListener(this);
		mBtnEditCancelComplete = (TextView) view
				.findViewById(R.id.btn_cancel_modify);
		mBtnEditCancelComplete.setOnClickListener(this);
	}

	/**
	 * ����
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_edit_provider_details, null);
		layout.addView(view);

		findViewById(view);
		updateEdtView();
	}

	// ��ɰ�ť(�ύ�޸�)
	private TextView mBtnEditCommitComplete;
	// ȡ��
	private TextView mBtnEditCancelComplete;

	// ����
	private EditText mEdtGoodSNam;
	// ���Ʒ������Ŀ
//	private TableRow mLlGoodSCategory;
	// ��ʾ�����
	private TextView mTxtCategory;
	// ����Ʒ���ֵ���Ŀ
//	private TableRow mLlGoodSBrand;
	// ��ʾƷ��
	private TextView mTxtBrand;
	// ���Ʋ��ص���Ŀ
//	private TableRow mLlConductPlace;
	// ��ʾ����
	private TextView mTxtPlace;
	// ���� ���
	private EditText mEdtGoodSInventory;
	// �۸�
	private EditText mEdtGoodSPric;
	// Ԥ�����
	private EditText mEdtGoodSPrepayment;
	// ����ʱ��
	private TableRow mLlGoodSPutTime;
	// ��ʾ����ʱ��
	private TextView mTxtPutTime;
	// ����ʱ��
	private TableRow mLlGoodSEndTime;
	// ��ʾ����ʱ��
	private TextView mTxtEndTime;
	// ���Ƶ����Զ���
	private TableRow mLlAutoStart;
	// ��ʾ�Ƿ��ƶ���
	private TextView mTxtStart;
	// ���Ƶ����Զ�����
	private TableRow mLlAutoEnd;
	// ��ʾ�Ƿ��Զ���
	private TextView mTxtOver;
	// ���ƽ���ص���Ŀ
	private TableRow mLlGoodSDelivery;
	// ��ʾ�����
	private TextView mTxtDelivery;
	// ��ϸ����
	private EditText mTxtDetails;

	// ����
	private MyLoadingBar loadingBar;

	/**
	 * �ҿؼ�
	 * 
	 * @param view
	 */
	private void findViewById(View view) {
		// ����
		mEdtGoodSNam = (EditText) view.findViewById(R.id.edt_goods_name);
		// ��ʾ�����
		mTxtCategory = (TextView) view.findViewById(R.id.txt_catagory);
		// ��ʾƷ��
		mTxtBrand = (TextView) view.findViewById(R.id.txt_brand);
		// ��ʾ����
		mTxtPlace = (TextView) view.findViewById(R.id.txt_place);
		// ���� ���
		mEdtGoodSInventory = (EditText) view
				.findViewById(R.id.edt_goods_inventory);
		// �۸�
		mEdtGoodSPric = (EditText) view.findViewById(R.id.edt_goods_price);
		// Ԥ�����
		mEdtGoodSPrepayment = (EditText) view
				.findViewById(R.id.edt_goods_prepayment);
		// ����ʱ��
		mLlGoodSPutTime = (TableRow) view
				.findViewById(R.id.ll_conduct_put_time);
		// ��ʾ����ʱ��
		mTxtPutTime = (TextView) view.findViewById(R.id.txt_put_time);
		// ����ʱ��
		mLlGoodSEndTime = (TableRow) view
				.findViewById(R.id.ll_conduct_end_time);
		// ��ʾ����ʱ��
		mTxtEndTime = (TextView) view.findViewById(R.id.txt_end_time);
		// ���Ƶ����Զ���
		mLlAutoStart = (TableRow) view.findViewById(R.id.ll_conduct_auto_start);
		// ��ʾ�Ƿ��ƶ���
		mTxtStart = (TextView) view.findViewById(R.id.txt_auto_start);
		// ���Ƶ����Զ�����
		mLlAutoEnd = (TableRow) view.findViewById(R.id.ll_conduct_auto_over);
		// ��ʾ�Ƿ��Զ���
		mTxtOver = (TextView) view.findViewById(R.id.txt_auto_over);
		// ���ƽ���ص���Ŀ
		mLlGoodSDelivery = (TableRow) view
				.findViewById(R.id.ll_conduct_delivery);
		// ��ʾ�����
		mTxtDelivery = (TextView) view.findViewById(R.id.txt_delivery);
		// ��ϸ����
		mTxtDetails = (EditText) view.findViewById(R.id.txt_details_01);

		loadingBar = (MyLoadingBar) view.findViewById(R.id.load);

		mLlGoodSPutTime.setOnClickListener(this);
		mLlGoodSEndTime.setOnClickListener(this);
		mLlAutoStart.setOnClickListener(this);
		mLlAutoEnd.setOnClickListener(this);
		mLlGoodSDelivery.setOnClickListener(this);
	}

	/**
	 * ����������е�����
	 */
	private void updateEdtView() {
		if (null != info) {
			// ����
			mEdtGoodSNam.setText(info.getPurchasename());
			// ��ʾ�����
			mTxtCategory.setText(info.getCategoryName());
			// ��ʾƷ��
			mTxtBrand.setText(info.getBrandName());
			// ��ʾ����
			mTxtPlace.setText(info.getOriginPlaceName());
			// ���� ���
			mEdtGoodSInventory.setText(info.getInventory());
			// �۸�
			mEdtGoodSPric.setText(info.getPrice() + "Ԫ/��");
			// Ԥ�����
			mEdtGoodSPrepayment.setText(info.getPrepayment() + "Ԫ");
			// ��ʾ����ʱ��
			mTxtPutTime.setText(info.getPutTime());
			// ��ʾ����ʱ��
			mTxtEndTime.setText(info.getEndTime());
			// ��ʾ�Ƿ��ƶ���
			mTxtStart.setText(info.isAutoSatrt() ? "��" : "��");
			// ��ʾ�Ƿ��Զ���
			mTxtOver.setText(info.isAutoEnd() ? "��" : "��");
			// ��ʾ�����
			mTxtDelivery.setText(info.getDeliveryPlaceName());
			// ��ϸ����
			mTxtDetails.setText(info.getOfferDetail());
		}
	}

	// �޸ĵ���Ϣ
	private GoodsSourceInfo sourceInfo;

	/**
	 * ��ȡ�������Ʒ����Ϣ ��װ��ʵ����
	 * 
	 * @return
	 */
	private void getInputGoodSInfo() {

		sourceInfo = new GoodsSourceInfo();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_commit_modify:// ���
			editComplete();
			break;
		case R.id.btn_cancel_modify:// ȡ��
			((BaseActivity) context).finish();
			break;
		case R.id.ll_conduct_delivery:// �����
			((BaseActivity) context).goActivity(GoodSCategoryActivity.class);
			break;
		case R.id.ll_conduct_end_time:// ����ʱ��
			// ((BaseActivity) context).goActivity(PriceRangeActivity.class);
			break;
		case R.id.ll_conduct_put_time:// ����ʱ��
			break;
		case R.id.ll_conduct_auto_start:// �Զ���ʼ
			break;
		case R.id.ll_conduct_auto_over:// �Զ�����
			break;
		}
	}

	/**
	 * �ҵĹ�Ӧ �༭����ύ�޸İ�ť
	 */
	private void editComplete() {
		// �õ������������Ϣ
		// getInputGoodSInfo();

		loadingBar.setVisibility(View.VISIBLE);
		loadingBar.setProgressInfo("���ڼ�����...");
		loadingBar.start();

		JsonLoader loader = new JsonLoader(context);
		loader.setUrl(AppNetUrl.getMyIntentionProviderDetailEditUrl());
		loader.setPostData(MerchantCenterManager
				.getMyIntentionProviderDetailEditArg(info));
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
				loadingBar.setProgressInfo("���ڽ���...");
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				loadingBar.setVisibility(View.GONE);
				loadingBar.stop();
				((BaseActivity) context).displayToast(arg2.toString());
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				RequestNetResultInfo info = MerchantCenterManager
						.getgetMyProviderDetailsEditComplete(arg1);
				if (info.getStatus() == 1) {
					((BaseActivity) context).displayToast(info.getMsg());
					// ((BaseActivity) context).finish();
				} else {
					((BaseActivity) context).displayToast(info.getMsg());
				}
				loadingBar.setVisibility(View.GONE);
				loadingBar.stop();
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
		// goActivity(SucceeMainActivity.class);

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 0) {
			//Ʒ��
			
		}
	}
}
