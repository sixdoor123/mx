package com.baiyi.cmall.activities.user.merchant.order;

import java.util.ArrayList;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.user.merchant.order.inttetion.IntentionOrderListViewPager;
import com.baiyi.cmall.activities.user.merchant.order.product.ProductOrderListViewPager;
import com.baiyi.cmall.pageviews.MyViewPager;
import com.baiyi.cmall.pageviews.PageView;
import com.baiyi.cmall.pageviews.ViewPagerSelected;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.core.util.ContextUtil;

/**
 * 商家订单界面
 * 
 * @author sunxy
 */
public class MerchantOrderActivity extends BaseActivity implements
		OnClickListener {
	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(false);
		initTitle();
		initMainCategory();
		initContent();
	}

	// 标题栏
	private EventTopTitleView topTitleView;

	/**
	 * 标题栏
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("商家订单");
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
				MsgItemUtil.onclickPopItem(state, MerchantOrderActivity.this);
			}
		});
		win_title.addView(topTitleView);
	}

	private ArrayList<View> mainViews;
	// 意向订单
	private LinearLayout mIntentionOrder;
	// 产品订单
	private LinearLayout mProductOrder;

	private ArrayList<TextView> mainTList;

	/**
	 * 上面的总分类
	 */
	private void initMainCategory() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.merchant_order_main_category, null);
		win_content.addView(view);

		mIntentionOrder = (LinearLayout) view.findViewById(R.id.intention_order);
		mProductOrder = (LinearLayout) view.findViewById(R.id.product_order);
		mIntentionOrder.setOnClickListener(this);
		mProductOrder.setOnClickListener(this);

		mainViews = new ArrayList<View>();
		mainViews.add(view.findViewById(R.id.first));
		mainViews.add(view.findViewById(R.id.second));

		mainTList = new ArrayList<TextView>();
		mainTList.add((TextView) view.findViewById(R.id.intention));
		mainTList.add((TextView) view.findViewById(R.id.product));
	}

	private MyViewPager pager = null;
	private ArrayList<PageView> pageViews = null;

	private IntentionOrderListViewPager intentionPager = null;
	private ProductOrderListViewPager productPager = null;

	/**
	 * 初始化内容
	 */
	private void initContent() {
		pager = new MyViewPager(this);
		win_content.addView(pager);

		pageViews = new ArrayList<PageView>();

		intentionPager = new IntentionOrderListViewPager(this);
		productPager = new ProductOrderListViewPager(this);

		pageViews.add(intentionPager);
		pageViews.add(productPager);

		pager.init(pageViews, 0);
		pager.setViewPageSelectedLister(new ViewPagerSelected() {

			@Override
			public void onPageSelected(int currentNum) {
				setButtonPerformClick(currentNum);
				setMainView(currentNum);
			}
		});
	}

	public void setButtonPerformClick(int position) {
		if (position == 0) {
			intentionPager.performClick();
		} else if (position == 1) {
			productPager.performClick();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.intention_order:// 意向订单
			setMainView(0);
			pager.setCurrentItem(0);
			break;
		case R.id.product_order:// 产品订单
			setMainView(1);
			pager.setCurrentItem(1);
			break;

		}
	}

	private void setMainView(int pos) {

		for (int i = 0; i < mainViews.size(); i++) {
			if (pos == i) {
				mainViews.get(i).setBackgroundColor(
						getResources().getColor(R.color.bg_buyer));
				Animation animation = AnimationUtils.loadAnimation(this,
						R.anim.slide_in_from_bottom_select);
				mainViews.get(i).setAnimation(animation);
				mainTList.get(i).setTextColor(
						getResources().getColor(R.color.bg_buyer));
			} else {
				mainViews.get(i).setBackgroundColor(
						getResources().getColor(R.color.bg_white));
				mainTList.get(i).setTextColor(
						getResources().getColor(R.color.bg_black));
			}
		}
	}

}
