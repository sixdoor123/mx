package com.baiyi.cmall.activities.user.merchant.product.detail;

import me.relex.seamlessviewpagerheader.widget.SlidingTabLayout;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseViewpagerActivity;
import com.baiyi.cmall.activities.main.mall.page.MalllCommentPage;
import com.baiyi.cmall.activities.main.mall.page.MalllDetailPage;
import com.baiyi.cmall.activities.user.merchant.product.pager.MyProductlInfoPage;
import com.baiyi.cmall.pageviews.MyViewPager;

/**
 * 我的产品-详情
 * 
 * @author sunxy
 */
public class MyProductDetailActivity extends BaseViewpagerActivity {

	private final static String[] titles = { "商品", "详情", "评论" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public SlidingTabLayout getSlidingTabLayout(View view) {
		SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tabs);
		slidingTabLayout.setDistributeEvenly(false);
		slidingTabLayout.setSelectStripColor(0xffffffff);
		slidingTabLayout.setTitleColor(0xffffffff);
		return slidingTabLayout;
	}

	private ImageView mImgBack;
	@Override
	public MyViewPager getMyViewPager(View view) {

		mImgBack = (ImageView) view.findViewById(R.id.img_back_product_detail);
		mImgBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		return (MyViewPager) findViewById(R.id.product_pager);
	}

	@Override
	public String[] getTitles() {
		return titles;
	}

	@Override
	public Fragment getFrament(int position) {

		int state = 0;

		if ("发布".equals(stateName)) {
			state = 1;
		} else {
			state = -1;
		}

		if (position == 0) {
			return MyProductlInfoPage.newInstance(position).setId(id).setState(state);
		} else if (position == 1) {
			return MalllDetailPage.newInstance(position).setId(id).setState(state);
		} else {
			return MalllCommentPage.newInstance(position).setId(id).setState(state);
		}
	}

	@Override
	public int getContentLayout() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			return R.layout.activity_product_detail_h;
		}
		return R.layout.activity_product_detail_l;
	}

}
