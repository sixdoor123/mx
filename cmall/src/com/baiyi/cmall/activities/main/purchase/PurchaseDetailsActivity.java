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
 * 采购详情 (属性)界面
 * 
 * @author sunxy
 * 
 */
public class PurchaseDetailsActivity extends BaseMsgActivity implements
		OnCheckedChangeListener, OnClickListener {

	// 数据源
	private String purID;
	// 供应商ID
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
	 * 初始化数据
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
	 * 标题
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("采购详情");
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

	// 采购详情
	private RadioButton mRbProviderDetails;
	// 属性
	private RadioButton mRbProviderStandard;
	// RadioGroup
	private RadioGroup mRgCollecteGroup;
	// 供应向情下的三角
	private ImageView mImgDetailsChose;
	// 属性下的三角
	private ImageView mImgStandardChose;

	// 自定义的ViewPager
	private MyViewPager mVpProvider;

	/**
	 * 初始化RadioGroup的内容
	 */
	private void initRBContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_collection_list, null);
		win_title.addView(view);

		mRgCollecteGroup = (RadioGroup) view.findViewById(R.id.collect_group);
		mRbProviderDetails = (RadioButton) view
				.findViewById(R.id.provider_collection);
		mRbProviderDetails.setText("采购信息");

		mRbProviderStandard = (RadioButton) view
				.findViewById(R.id.purchase_collection);
		mRbProviderStandard.setText("属性");

		mImgDetailsChose = (ImageView) view.findViewById(R.id.provider_choose);
		mImgStandardChose = (ImageView) view.findViewById(R.id.purchase_choose);

		mVpProvider = (MyViewPager) view.findViewById(R.id.vp_collection);

		mRbProviderDetails.setChecked(true);

		mRgCollecteGroup.setOnCheckedChangeListener(this);

		initViewPager();
		initFoot(view);
	}

	// 供应详情ViewPager
	private BasePurchaseDetailViewPager detailsPager;
	// 属性的ViewPager
	private BasePurchaseDetailViewPager standardViewPager;
	// 存放ViewPager的集合
	private ArrayList<PageView> pageViews;

	/**
	 * 初始化ViewPage
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
	 * 设置Button的监听
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
	 * RadioGroup 的监听事件的毁掉方法
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		int id = 0;
		if (checkedId == R.id.provider_collection) {
			id = 0;// 采购信息
			mImgDetailsChose.setVisibility(View.VISIBLE);
			mImgStandardChose.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.purchase_collection) {
			id = 1;// 属性
			mImgDetailsChose.setVisibility(View.INVISIBLE);
			mImgStandardChose.setVisibility(View.VISIBLE);
		}
		mVpProvider.setPageIndex(id);
		mVpProvider.setCurrentItem(id);

	}

	// 委托供应按钮
	private TextView mBtnPurchaseBuy;
	// 加入关注
	private TextView mBtnPurchaseDele;
	// 编辑按钮
	private TextView mEdiTextView;

	/**
	 * 底部操作按钮
	 * 
	 * @param view
	 */
	private void initFoot(View view) {

		final LinearLayout layout = (LinearLayout) view
				.findViewById(R.id.lin_shou_ye);

		mBtnPurchaseBuy = (TextView) view
				.findViewById(R.id.btn_sure_purchase_order);
		mBtnPurchaseBuy.setText("委托供应");

		mEdiTextView = (TextView) view
				.findViewById(R.id.btn_cancel_purchase_order);
		mEdiTextView.setText("编辑");
		mEdiTextView.setVisibility(View.GONE);

		mBtnPurchaseDele = (TextView) view
				.findViewById(R.id.btn_commit_purchase_order);

		mBtnPurchaseDele.setText("加入关注");

		mBtnPurchaseBuy.setOnClickListener(this);
		mBtnPurchaseDele.setOnClickListener(this);

		/**
		 * 当获取到数据后，对button进行选择操作
		 */
		detailsPager.setFinishListener(new OnGetDataFinishListener() {

			@Override
			public void getState(boolean isAttention, String userId,
					RequestNetResultInfo resultInfo) {

				// 自己的信息没有操作按钮
				if (userId.equals(CmallApplication.getUserInfo().getUserID())) {
					Log.d("TT", "该信息为自己的信息");
					return;
				}
				if (isAttention) {// 是否关注
					if (1 == state) {
						mBtnPurchaseDele.setText("取消关注");
						mBtnPurchaseDele.setEnabled(true);
						if (-1 == resultInfo.getStatus()) {
							mBtnPurchaseBuy.setVisibility(View.GONE);
						}
					} else {
						mBtnPurchaseDele.setText("已关注");
						mBtnPurchaseDele.setEnabled(false);
					}

				}
				layout.setVisibility(View.VISIBLE);
				// 未登录状态，不能关注
				if (TextUtils.isEmpty(token)) {
					mBtnPurchaseDele.setText("未登录，无法关注");
					mBtnPurchaseDele.setEnabled(false);
				}
			}

		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_sure_purchase_order:// 委托供应
			purchaseBuy();
			break;
		case R.id.btn_commit_purchase_order:// 添加关注
			if (1 == state) {
				// 取消关注
				detailsPager.cancelAttention();
			} else {
				addAttention();
			}
			break;
		}
	}

	/**
	 * 进入委托供应界面
	 */
	private void purchaseBuy() {

		detailsPager.toActivity();
	}

	/**
	 * 关注
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
				// 显示返回的信息：成功/失败
				RequestNetResultInfo netResultInfo = JsonParse_User
						.getResultInfo(array);

				if (1 != netResultInfo.getStatus()) {
					displayToast(netResultInfo.getMsg());
					if (NumEntity.PLEASE_LOG.equals(netResultInfo.getMsg())) {

						ExitLogin.getInstence(PurchaseDetailsActivity.this)
								.cleer();
						mBtnPurchaseDele.setText("账号过期，请重新登录");
						mBtnPurchaseDele.setEnabled(false);
					}
					return;
				}

				displayToast(netResultInfo.getMsg());
				// 关注成功，将背景置灰
				mBtnPurchaseDele.setText("已关注");
				mBtnPurchaseDele.setEnabled(false);
			}
		});
		CmallApplication.getDataStratey().startLoader(attentionLoader);
	}

}
