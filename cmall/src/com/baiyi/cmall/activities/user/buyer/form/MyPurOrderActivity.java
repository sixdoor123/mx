package com.baiyi.cmall.activities.user.buyer.form;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.mall.MallDefine;
import com.baiyi.cmall.activities.user.buyer.form.intention_order.IntentionOrderView;
import com.baiyi.cmall.activities.user.buyer.form.intention_order.MyIntentionOrderViewPager;
import com.baiyi.cmall.activities.user.buyer.form.product_order.MyProductOrderViewPager;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.pageviews.MyViewPager;
import com.baiyi.cmall.pageviews.PageView;
import com.baiyi.cmall.pageviews.ViewPagerSelected;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 用户里面-我的采购公共类
 * 
 * 是個抽象类，继承它的类必须实现里面未实现的方法
 * 
 * @author lizl
 * 
 */
public class MyPurOrderActivity extends BaseActivity implements OnClickListener, OnCheckedChangeListener {

	// 数据源
	public ArrayList<GoodsSourceInfo> datas;

	private MyViewPager myViewPager = null;

	private List<PageView> pageViews = null;

	/*
	 * ViewPager添加的四个界面
	 */
	private MyIntentionOrderViewPager orderView1 = null;
	private MyProductOrderViewPager orderView2 = null;

	// 买家订单
	private RadioGroup mRgMyOrderGroup;
	// 意向订单按钮
	private RadioButton mRbIntentOrderButton;
	// 产品订单按钮
	private RadioButton mRbProductOrderButton;

	// 状态
	private int state = 0;

	@Override
	protected void initWin(boolean hasScrollView) {
		super.initWin(false);
		initData();
		initTitle();
		initRg();
		initViewPager();
	}

	private void initData() {
		state = getIntent().getIntExtra(MallDefine.State, 0);
	}

	/**
	 * 标题栏
	 */
	public void initTitle() {
		EventTopTitleView topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("买家订单");
		win_title.addView(topTitleView);
	}

	/**
	 * 初始化RadioButton
	 */
	public void initRg() {

		View view = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_my_order, null);
		win_title.addView(view);

		myViewPager = (MyViewPager) findViewById(R.id.vp_pager_order);
		mRgMyOrderGroup = (RadioGroup) view.findViewById(R.id.rg_my_order);
		mRbIntentOrderButton = (RadioButton) view.findViewById(R.id.rb_my_intent_order);
		mRbProductOrderButton = (RadioButton) view.findViewById(R.id.rb_my_product_order);
		mRbIntentOrderButton.setChecked(true);
		mRgMyOrderGroup.setOnCheckedChangeListener(this);

	}

	/**
	 * 初始化ViewPager
	 */
	private void initViewPager() {
		pageViews = new ArrayList<PageView>();

		orderView1 = new MyIntentionOrderViewPager(this);
		orderView2 = new MyProductOrderViewPager(this);

		pageViews.add(orderView1);
		pageViews.add(orderView2);

		myViewPager.init(pageViews, state);

		if (1 == state) {
			setButtonPerformClick(state);
		}

		/**
		 * 当viewPager页面变化的时候RadioButton也一起发生变化
		 * 
		 */
		myViewPager.setViewPageSelectedLister(new ViewPagerSelected() {

			@Override
			public void onPageSelected(int currentNum) {
				// TODO Auto-generated method stub
				setButtonPerformClick(currentNum);
			}
		});
	}

	public void setButtonPerformClick(int position) {
		if (position == 0) {
			mRbIntentOrderButton.performClick();

		} else if (position == 1) {
			mRbProductOrderButton.performClick();
		}

	}

	/**
	 * RadioGroup 的监听事件的回调方法
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		int id = 0;
		if (checkedId == R.id.rb_my_intent_order) {
			id = 0;
		} else if (checkedId == R.id.rb_my_product_order) {
			id = 1;
		}
		// 跟随radioButton的点击设置当前的页面位置
		myViewPager.setPageIndex(id);
		myViewPager.setCurrentItem(id);

	}

	/**
	 * 当订单中的按钮操作成功时，调用一下方法
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == NumEntity.RESULT_FORM_OK) {
			orderView1.onActivityResult(requestCode, resultCode, data);
			orderView2.onActivityResult(requestCode, resultCode, data);
		}

	}

	@Override
	public void onClick(View v) {

	}
}
