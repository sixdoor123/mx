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
 * 我的采购—— 意向——编辑采购详情Activity
 * 
 * @author lizl
 * 
 */
public class EditPurchaselActivity extends BaseMsgActivity implements
		OnCheckedChangeListener, OnClickListener {

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
	// 整个布局
	private View view;
	// 是否显示 ---分类/产地 等信息
	private boolean isShow;
	// 意向ID/采购ID
	private int intentId;
	// 是否显示编辑按钮
	private String binaryValue;
	// 能否操作名称
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
	 * 数据源
	 */
	private void intData() {

		isShow = getIntent().getBooleanExtra("show", true);

		intentId = getIntent().getIntExtra("intent", 0);

		binaryValue = getIntent().getStringExtra("state");

		isEditPurName = getIntent().getBooleanExtra("is_edit", true);

	}

	/**
	 * 标题
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
		topTitleView.setEventName("编辑采购详情");
		win_title.addView(topTitleView);
	}

	/**
	 * 初始化RadioGroup的内容
	 */
	private void initRBContent() {
		view = ContextUtil.getLayoutInflater(this).inflate(
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

	}

	// 供应详情ViewPager
	private EditPurchaserDetailViewPager detailsPager;
	// 属性的ViewPager
	private EditPurchaseStandardArgViewPager standardViewPager;
	// 存放ViewPager的集合
	private ArrayList<PageView> pageViews;

	/**
	 * 初始化ViewPage
	 */
	private void initViewPager() {
		pageViews = new ArrayList<PageView>();
		// purId:采购ID
		// state：区分是编辑按钮，还是保存按钮
		// showStat:区分显示状态
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
		// 初始化底部控件
		initFoot();
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
			id = 0;// 供应信息
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

	public TextView mEdiTextView;
	public TextView mBtnCancel;

	/**
	 * 初始化底部按鈕
	 */
	private void initFoot() {

		LinearLayout footView = (LinearLayout) view
				.findViewById(R.id.lin_foot_button);
		footView.setVisibility(View.VISIBLE);
		mEdiTextView = (TextView) view.findViewById(R.id.btn_commit_modify);
		mBtnCancel = (TextView) view.findViewById(R.id.btn_cancel_modify);
		mBtnCancel.setText("删除");
		mBtnCancel.setOnClickListener(this);

		/**
		 * 根据不同的状态显示不同的按钮 0：新增 1：编辑
		 */
		mEdiTextView.setText("编辑完成");

		// 采购意向中的编辑(必须根据状态判断是否显示)
		if (isShow) {

			mBtnCancel.setVisibility(View.VISIBLE);
		} else {
			mBtnCancel.setVisibility(View.GONE);

			if (View.VISIBLE == FormStateUtils.isShow(binaryValue,// 编辑按钮
					FormStateUtils.STATE_EDIT)
					|| View.VISIBLE == FormStateUtils.isShow(binaryValue,// 采购按钮
							FormStateUtils.STATE_BUY)
					|| View.VISIBLE == FormStateUtils.isShow(binaryValue,// 发送意向按钮
							FormStateUtils.STATE_FASONG)) {
				mEdiTextView.setVisibility(View.VISIBLE);
				// 判断名称是否可编辑
				if (!isEditPurName) {
					detailsPager.setPurNameNoEdit();
				}
			} else {
				footView.setVisibility(View.GONE);
				// 设置详情编辑框不可操作
				detailsPager.setNoEdit();
				// 设置属性框不可操作---添加标志位（true为不可操作）
				standardViewPager.setStandIsEnable(true);

			}

		}

		mEdiTextView.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_commit_modify:// 编辑

			// 获取编辑的数据
			getEditData();
			break;

		case R.id.btn_cancel_modify:// 删除

			deletePur(AppNetUrl.getDeletePurUrl(intentId + ""));
			break;
		}
	}

	private String deliveryId;// 交割地
	ArrayList<TextView> detailViews;
	ArrayList<TextView> standardViews;
	ArrayList<IntentionDetailStandardInfo> standardId;

	/**
	 * 获取编辑后的数据
	 */
	public void getEditData() {

		/*
		 * 当显示时间以及自动开始选择的时候，判断数据
		 */
		if (isShow) {


			// 发布时间
			String startTime = detailsPager.getPubTime();
			// 结束时间
			String endTime = detailsPager.getEndTime();

			// 时间提示语
			String timeHintInfo = HintUtils.timeHint(startTime,endTime);
			if (!"OK".equals(timeHintInfo)) {
					displayToast(timeHintInfo);
					return;
			}
		}

		detailViews = detailsPager.getAllText(); 

		/*
		 * 判断名称
		 */
		String purName = detailViews.get(0).getText().toString().trim();
		if (TextUtils.isEmpty(purName)) {
			displayToast("名称不能为空");
			return;
		}
		/*
		 * 判断数量
		 */
		String weight = Utils.getNumberOfString(detailViews.get(1).getText()
				.toString());
		if (!"OK".equals(HintUtils.weightHint(weight))) {
			displayToast(HintUtils.weightHint(weight));
			return;
		}
		/*
		 * 判断价格-----------------------------------
		 */
		String price = Utils.getNumberOfString(detailViews.get(2).getText()
				.toString());

		if (!"OK".equals(HintUtils.priceHint(price))) {
			displayToast(HintUtils.priceHint(price));
			return;
		}
		/*
		 * 判断预付金额-----------------------------------
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
		Log.d("TT", "详情控件数量---------" + detailViews.size());
		Log.d("TT", "规格控件数量---------" + standardViews.size());
		Log.d("TT", "规格Id数量---------" + standardId.size());
		// 提交数据------------------------------------
		getCommitData();
	}

	/**
	 * 获取提交的数据
	 */
	private void getCommitData() {

		// 采购中的编辑URL
		String show_editUrl = Config.ROOT_URL + "User/Purchase/Update";
		// 意向中的编辑URL
		String noShow_editUrl = Config.ROOT_URL + "User/Intention/PurSave";

		if (isShow) {

			// 显示时间，自动选择------的post数据
			// 编辑采购中，还没有意向，故---没有意向ID
			String show_editPostData = MyPurchaseManager.getMyPurEditPostData(
					intentId + "", detailViews, standardViews, standardId,
					deliveryId);
			commitData(show_editUrl, show_editPostData);
		} else {

			// 不显示时间，自动选择------的post数据
			// intentId---------需要意向ID和采购的ID
			String noShow_PostData_Edit = MyPurchaseManager
					.getMyIntentEditPostData(intentId + "", detailViews,
							standardViews, standardId, deliveryId);
			commitData(noShow_editUrl, noShow_PostData_Edit);
		}

	}

	/**
	 * 编辑——提交数据
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
				// 服务器返回结果
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);
				if (1 == info.getStatus()) {
					// 成功时，退出编辑页面，回去刷新
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
	 * 删除采购信息
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

				// 服务器返回结果
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
