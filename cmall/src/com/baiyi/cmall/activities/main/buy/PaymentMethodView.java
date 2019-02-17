package com.baiyi.cmall.activities.main.buy;

import java.util.ArrayList;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.buy.pageview.PageOfflinePay;
import com.baiyi.cmall.activities.main.buy.pageview.PageOnlinePay;
import com.baiyi.cmall.pageviews.MyViewPager;
import com.baiyi.cmall.pageviews.PageView;
import com.baiyi.cmall.pageviews.ViewPagerSelected;
import com.baiyi.core.util.ContextUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class PaymentMethodView extends LinearLayout implements OnCheckedChangeListener{
	
	private RadioGroup paymentRg;
	// 线上支付
	private RadioButton rbOnline;
	// 线下支付
	private RadioButton rbOffline;

	private ImageView mImgOnline;
	private ImageView mImgOffline;
	
	private ArrayList<PageView> pageViews = null;
	private PageOfflinePay pageOfflinePay = null;
	private PageOnlinePay pageOnlinePay = null;
	private MyViewPager myViewPager = null;
	
	private String oi;
	private String unPrice;

	public PaymentMethodView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initViews();
	}

	public PaymentMethodView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initViews();
	}
	
	private void initViews()
	{
		setOrientation(LinearLayout.VERTICAL);
		ContextUtil.getLayoutInflater(getContext()).inflate(R.layout.view_payment_method, this);
		initViewPager();
		paymentRg = (RadioGroup)findViewById(R.id.rg_my_order);
		rbOnline = (RadioButton)findViewById(R.id.rb_online_pay);
		rbOffline = (RadioButton)findViewById(R.id.rb_offline_pay);
		
		mImgOnline = (ImageView)findViewById(R.id.online_choose);
		mImgOffline = (ImageView)findViewById(R.id.offline_choose);
		paymentRg.setOnCheckedChangeListener(this);
		
	}
	
	private void initViewPager() {
		myViewPager = (MyViewPager)findViewById(R.id.vp_pager);
		pageViews = new ArrayList<PageView>();

		pageOnlinePay = new PageOnlinePay(getContext());
		pageOfflinePay = new PageOfflinePay(getContext());

		pageViews.add(pageOnlinePay);
		pageViews.add(pageOfflinePay);

		myViewPager.init(pageViews, 0);

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

	public String getOi() {
		return oi;
	}

	public void setOi(String oi) {
		this.oi = oi;
		pageOnlinePay.setOi(oi);
	}

	public String getUnPrice() {
		return unPrice;
	}

	public void setUnPrice(String unPrice) {
		this.unPrice = unPrice;
		pageOnlinePay.setUnPrice(unPrice);
	}

	public void setButtonPerformClick(int position) {
		if (position == 0) {
			rbOnline.performClick();

		} else if (position == 1) {
			rbOffline.performClick();
		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		int id = 0;
		if (checkedId == R.id.rb_online_pay) {
			id = 0;// 线上
			mImgOnline.setVisibility(View.VISIBLE);
			mImgOffline.setVisibility(View.INVISIBLE);
		} else if (checkedId == R.id.rb_offline_pay) {
			id = 1;// 线下
			mImgOnline.setVisibility(View.INVISIBLE);
			mImgOffline.setVisibility(View.VISIBLE);
		}
		// 跟随radioButton的点击设置当前的页面位置
		myViewPager.setPageIndex(id);
		myViewPager.setCurrentItem(id);
	}

}
