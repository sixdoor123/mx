package com.baiyi.cmall.activities.main.purchase;

import java.util.ArrayList;

import org.json.JSONArray;

import android.net.NetworkInfo.State;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.base.BaseMsgActivity;
import com.baiyi.cmall.activities.user.login.ExitLogin;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.pageviews.MyViewPager;
import com.baiyi.cmall.pageviews.PageView;
import com.baiyi.cmall.pageviews.ViewPagerSelected;
import com.baiyi.cmall.request.manager.PurchaseSourceManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.JsonParse_User;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.utils.XmlName;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.cmall.views.pageview.BasePurchaseDetailViewPager;
import com.baiyi.cmall.views.pageview.BasePurchaseDetailViewPager.OnGetDataFinishListener;

/**
 * �ɹ����� (����)����
 * 
 * @author sunxy
 * 
 */
public class PurchaseDetailsActivity extends BaseMsgActivity implements
		OnCheckedChangeListener, OnClickListener {

	// ����Դ
	private String purID;
	// ��Ӧ��ID
	private String companyId;
	// TOKEN
	private String token;

	@Override
	protected void initWin(boolean hasScrollView) {
		super.initWin(true);
		ActivityStackControlUtil.add(this);
		initData();
		initTitle();
		initRBContent();

	}

	private int state;
	private GoodsSourceInfo sourceInfo;

	/**
	 * ��ʼ������
	 */
	private void initData() {
		sourceInfo = (GoodsSourceInfo) getIntent().getSerializableExtra("key");
		purID = getIntent().getStringExtra("temp");
		if (purID == null) {
			purID = sourceInfo.getPurchaseid();
		}
		state = getIntent().getIntExtra("state", 0);
		companyId = CmallApplication.getUserInfo().getCompanyID();
		token = XmlUtils.getInstence(this).getXmlStringValue(XmlName.TOKEN);
	}

	/**
	 * ����
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("�ɹ�����");
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
				MsgItemUtil.onclickPopItem(state, PurchaseDetailsActivity.this);
			}
		});
		win_title.addView(topTitleView);
	}

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

	/**
	 * ��ʼ��RadioGroup������
	 */
	private void initRBContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
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

		initViewPager();
		initFoot(view);
	}

	// ��Ӧ����ViewPager
	private BasePurchaseDetailViewPager detailsPager;
	// ���Ե�ViewPager
	private BasePurchaseDetailViewPager standardViewPager;
	// ���ViewPager�ļ���
	private ArrayList<PageView> pageViews;

	/**
	 * ��ʼ��ViewPage
	 */
	private void initViewPager() {
		pageViews = new ArrayList<PageView>();
		detailsPager = new PurchaseDetailViewPager(this, purID, sourceInfo);
		standardViewPager = new PurchaseStandardArgumentViewPager(this, purID,
				sourceInfo);

		pageViews.add(detailsPager);
		pageViews.add(standardViewPager);

		mVpProvider.init(pageViews, 0);
		mVpProvider.setViewPageSelectedLister(new ViewPagerSelected() {

			@Override
			public void onPageSelected(int currentNum) {
				setButtonPerformClick(currentNum);
			}
		});
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
			id = 0;// �ɹ���Ϣ
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

	// ί�й�Ӧ��ť
	private TextView mBtnPurchaseBuy;
	// �����ע
	private TextView mBtnPurchaseDele;
	// �༭��ť
	private TextView mEdiTextView;

	/**
	 * �ײ�������ť
	 * 
	 * @param view
	 */
	private void initFoot(View view) {

		final LinearLayout layout = (LinearLayout) view
				.findViewById(R.id.lin_shou_ye);

		mBtnPurchaseBuy = (TextView) view
				.findViewById(R.id.btn_sure_purchase_order);
		mBtnPurchaseBuy.setText("ί�й�Ӧ");

		mEdiTextView = (TextView) view
				.findViewById(R.id.btn_cancel_purchase_order);
		mEdiTextView.setText("�༭");
		mEdiTextView.setVisibility(View.GONE);

		mBtnPurchaseDele = (TextView) view
				.findViewById(R.id.btn_commit_purchase_order);

		mBtnPurchaseDele.setText("�����ע");

		mBtnPurchaseBuy.setOnClickListener(this);
		mBtnPurchaseDele.setOnClickListener(this);

		/**
		 * ����ȡ�����ݺ󣬶�button����ѡ�����
		 */
		detailsPager.setFinishListener(new OnGetDataFinishListener() {

			@Override
			public void getState(boolean isAttention, String userId,
					RequestNetResultInfo resultInfo) {

				// �Լ�����Ϣû�в�����ť
				if (userId.equals(CmallApplication.getUserInfo().getUserID())) {
					Log.d("TT", "����ϢΪ�Լ�����Ϣ");
					return;
				}
				if (isAttention) {// �Ƿ��ע
					if (1 == state) {
						mBtnPurchaseDele.setText("ȡ����ע");
						mBtnPurchaseDele.setEnabled(true);
						if (-1 == resultInfo.getStatus()) {
							mBtnPurchaseBuy.setVisibility(View.GONE);
						}
					} else {
						mBtnPurchaseDele.setText("�ѹ�ע");
						mBtnPurchaseDele.setEnabled(false);
					}

				}
				layout.setVisibility(View.VISIBLE);
				// δ��¼״̬�����ܹ�ע
				if (TextUtils.isEmpty(token)) {
					mBtnPurchaseDele.setText("δ��¼���޷���ע");
					mBtnPurchaseDele.setEnabled(false);
				}
			}

		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_sure_purchase_order:// ί�й�Ӧ
			purchaseBuy();
			break;
		case R.id.btn_commit_purchase_order:// ��ӹ�ע
			if (1 == state) {
				// ȡ����ע
				detailsPager.cancelAttention();
			} else {
				addAttention();
			}
			break;
		}
	}

	/**
	 * ����ί�й�Ӧ����
	 */
	private void purchaseBuy() {

		detailsPager.toActivity();
	}

	/**
	 * ��ע
	 */
	private void addAttention() {

		JsonLoader attentionLoader = new JsonLoader(this);
		attentionLoader.setUrl(AppNetUrl.getPurchaseAttentionUrl());
		attentionLoader.setPostData(PurchaseSourceManager
				.getPurchaseAttentionPostData(purID, companyId));
		attentionLoader.addRequestHeader("token", token);
		attentionLoader.addRequestHeader("iscompany", iscompany);
		attentionLoader.setMethod(BaseNetLoder.Method_Post);
		attentionLoader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		attentionLoader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {
				displayToast(errorMessage);
			}

			@Override
			public void onCompelete(Object tag, Object result) {
				JSONArray array = (JSONArray) result;
				// ��ʾ���ص���Ϣ���ɹ�/ʧ��
				RequestNetResultInfo netResultInfo = JsonParse_User
						.getResultInfo(array);

				if (1 != netResultInfo.getStatus()) {
					displayToast(netResultInfo.getMsg());
					if (NumEntity.PLEASE_LOG.equals(netResultInfo.getMsg())) {

						ExitLogin.getInstence(PurchaseDetailsActivity.this)
								.cleer();
						mBtnPurchaseDele.setText("�˺Ź��ڣ������µ�¼");
						mBtnPurchaseDele.setEnabled(false);
					}
					return;
				}

				displayToast(netResultInfo.getMsg());
				// ��ע�ɹ����������û�
				mBtnPurchaseDele.setText("�ѹ�ע");
				mBtnPurchaseDele.setEnabled(false);
			}
		});
		CmallApplication.getDataStratey().startLoader(attentionLoader);
	}

}
