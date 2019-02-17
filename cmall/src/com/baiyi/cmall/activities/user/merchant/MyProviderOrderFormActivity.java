package com.baiyi.cmall.activities.user.merchant;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.pageviews.MyViewPager;
import com.baiyi.cmall.pageviews.PageView;
import com.baiyi.cmall.pageviews.ViewPagerSelected;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.pageview.MyProviderOrderFormView;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 
 * 我的供应单界面
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-15 上午10:18:10
 */
public class MyProviderOrderFormActivity extends BaseActivity implements
		OnCheckedChangeListener {

	// 数据源
	public ArrayList<GoodsSourceInfo> datas;
	// 存放ViewPage 的集合
	private List<PageView> pageViews = null;

	/**
	 * 界面跳转选择位 0 跳转到供应意向单界面 1 跳转到供应详情界面
	 */
	// private int select;

	// 加载进度
	private MyLoadingBar loadingBar;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);

		initTitle();
		initContent();
		init();
		myViewPager.setVisibility(View.VISIBLE);
		initViewPager();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}

	// 商家ID
	private String companyID;

	private void init() {
		companyID = XmlUtils.getInstence(this).getXmlStringValue("companyID");

	}

	/**
	 * 标题栏
	 */
	private void initTitle() {
		EventTopTitleView topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("我的供应单");
		win_title.addView(topTitleView);
	}

	// 我的供应单分组
	private RadioGroup mRgMyOrderGroup;
	// 全部订单按钮
	private RadioButton mRbAllOrderButton;
	// 未确认的意向单 （未审核）
	private RadioButton mRbNotSureOrderButton;
	// 确认的意向单 （已审核）
	private RadioButton mRbSureOrderButton;
	// 完成的意向单 （审核未通过）
	private RadioButton mRbCompleteOrderButton;
	// 取消的意向单
	// private RadioButton mRbCancelOrderButton;

	// 全部下面的图标
	private ImageView mImgAllProviderChosed;
	// 未审核下面的图标
	private ImageView mImgNotAudirProvider;
	// 已审核下面的图标
	private ImageView mImgAuditProviderChosed;
	// 审核未通过下面的图标
	private ImageView mImgNotPassProviderChosed;

	// 当前选中的RadioButton距离左侧的距离
	private float mCurrentCheckedRadioLeft;

	// 水平滚动条
	private HorizontalScrollView scrollView;

	/**
	 * 详细列表
	 */
	private void initContent() {

		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_my_provider_order, null);
		win_title.addView(view);

		findViewById(view);

	}

	/**
	 * 找控件
	 * 
	 * @param view
	 */
	@SuppressLint("WrongViewCast")
	private void findViewById(View view) {

		scrollView = (HorizontalScrollView) view.findViewById(R.id.scrollview);
		// 找下面三角小图标
		mImgAllProviderChosed = (ImageView) view
				.findViewById(R.id.all_provider_choose);
		mImgNotAudirProvider = (ImageView) view
				.findViewById(R.id.not_audit_provider_choose);
		mImgAuditProviderChosed = (ImageView) view
				.findViewById(R.id.audit_provider_choose);
		mImgNotPassProviderChosed = (ImageView) view
				.findViewById(R.id.not_pass_provider_choose);
		mRgMyOrderGroup = (RadioGroup) view.findViewById(R.id.rg_my_order);
		mRgMyOrderGroup.setOnCheckedChangeListener(this);
		mRbAllOrderButton = (RadioButton) view
				.findViewById(R.id.rb_all_intent_order);
		mRbNotSureOrderButton = (RadioButton) view
				.findViewById(R.id.rb_not_sure_intent_order);
		mRbSureOrderButton = (RadioButton) view
				.findViewById(R.id.rb_sure_intent_order);
		mRbCompleteOrderButton = (RadioButton) view
				.findViewById(R.id.rb_complete_intent_order);
		// mRbCancelOrderButton = (RadioButton) view
		// .findViewById(R.id.rb_cancel_intent_order);

		myViewPager = (MyViewPager) findViewById(R.id.vp_pager);
		myViewPager.setVisibility(View.GONE);
		// 默认全部选中
		mRbAllOrderButton.setChecked(true);
		// mCurrentCheckedRadioLeft = getCurrentCheckedRadioLeft();

	}

	// /**
	// * 获取当前获得焦点的RadioButton距左边的距离
	// * @return
	// */
	// private float getCurrentCheckedRadioLeft() {
	// if (mRbAllOrderButton.isChecked()) {
	// return getResources().getDimension(R.dimen.margin_left);
	// }else if (mRbNotSureOrderButton.isChecked()){
	// return getResources().getDimension(R.dimen.margin_left_20);
	// }else if (mRbSureOrderButton.isChecked()){
	// return getResources().getDimension(R.dimen.margin_left_50);
	// }else if (mRbCompleteOrderButton.isChecked()){
	// return getResources().getDimension(R.dimen.margin_left_150);
	// }
	// return 0f;
	// }

	/**
	 * RadioGroup 的监听事件的毁掉方法
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		int id = 0;
		if (checkedId == R.id.rb_all_intent_order) {
			id = 0;// 全部
			mImgAllProviderChosed.setVisibility(View.VISIBLE);
			mImgNotAudirProvider.setVisibility(View.INVISIBLE);
			mImgAuditProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotPassProviderChosed.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.rb_not_sure_intent_order) {
			id = 1;// 未确认 (未审核)
			mImgAllProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotAudirProvider.setVisibility(View.VISIBLE);
			mImgAuditProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotPassProviderChosed.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.rb_sure_intent_order) {
			id = 2;// 确认 （已审核）
			mImgAllProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotAudirProvider.setVisibility(View.INVISIBLE);
			mImgAuditProviderChosed.setVisibility(View.VISIBLE);
			mImgNotPassProviderChosed.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.rb_complete_intent_order) {
			id = 3;// 完成 （审核未通过）
			mImgAllProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotAudirProvider.setVisibility(View.INVISIBLE);
			mImgAuditProviderChosed.setVisibility(View.INVISIBLE);
			mImgNotPassProviderChosed.setVisibility(View.VISIBLE);
		}
		myViewPager.setPageIndex(id);
		myViewPager.setCurrentItem(id);

		// mCurrentCheckedRadioLeft = getCurrentCheckedRadioLeft();
		// scrollView.smoothScrollTo((int)mCurrentCheckedRadioLeft-(int)getResources().getDimension(R.dimen.margin_bottom_20),
		// 0);
	}

	// 所有的意向单
	private MyProviderOrderFormView allPager;
	// 已审核的意向单
	private MyProviderOrderFormView surePager;
	// 未审核的意向单
	private MyProviderOrderFormView notSurePager;
	// 取消的意向单
	// private IntentionOrderPager cancelPager;
	// 完成的意向单
	private MyProviderOrderFormView completePager;
	// 自定义的ViewPager
	private MyViewPager myViewPager;

	private int state = 0;

	/**
	 * 初始化ViewPage
	 */
	private void initViewPager() {
		allPager = new MyProviderOrderFormView(this, companyID, "-1");// 全部
		surePager = new MyProviderOrderFormView(this, companyID, "1");// 待付款
		notSurePager = new MyProviderOrderFormView(this, companyID, "2");// 待发货
		completePager = new MyProviderOrderFormView(this, companyID, "3");// 已发货

		pageViews = new ArrayList<PageView>();

		pageViews.add(allPager);
		pageViews.add(surePager);
		pageViews.add(notSurePager);
		pageViews.add(completePager);

		myViewPager.init(pageViews, state);

		myViewPager.setViewPageSelectedLister(new ViewPagerSelected() {

			@Override
			public void onPageSelected(int currentNum) {
				// TODO Auto-generated method stub
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
			mRbAllOrderButton.performClick();
		} else if (position == 1) {
			mRbNotSureOrderButton.performClick();
		} else if (position == 2) {
			mRbSureOrderButton.performClick();
		} else if (position == 3) {
			mRbCompleteOrderButton.performClick();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == 7) {
			state = data.getIntExtra("state", 0);
		}
		surePager.onActivityResult(requestCode, resultCode, data);
		notSurePager.onActivityResult(requestCode, resultCode, data);
		completePager.onActivityResult(requestCode, resultCode, data);
		allPager.onActivityResult(requestCode, resultCode, data);
	}
}
