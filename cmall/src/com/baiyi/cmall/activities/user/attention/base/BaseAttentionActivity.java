package com.baiyi.cmall.activities.user.attention.base;

import me.relex.seamlessviewpagerheader.widget.SlidingTabLayout;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseViewpagerActivity;
import com.baiyi.cmall.pageviews.MyViewPager;
import com.baiyi.cmall.views.itemview.EventTopTitleView;

/**
 * 商家关注和买家关注的基类
 * 
 * @author sunxy
 */
public abstract class BaseAttentionActivity extends BaseViewpagerActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public int getContentLayout() {
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			return R.layout.attention_title_h;
		}
		return R.layout.attention_title_l;
	}

	private ImageView mImgBack;

	@Override
	public SlidingTabLayout getSlidingTabLayout(View view) {
		mImgBack = (ImageView) view.findViewById(R.id.back);
		mImgBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		SlidingTabLayout slidingTabLayout = (SlidingTabLayout) 
				findViewById(R.id.tabs);
		slidingTabLayout.setDistributeEvenly(false);
		slidingTabLayout.setSelectStripColor(0xffffffff);
		slidingTabLayout.setTitleColor(0xffffffff);
		return slidingTabLayout;
	}

	@Override
	public MyViewPager getMyViewPager(View view) {
		// TODO Auto-generated method stub
		return (MyViewPager) findViewById(R.id.product_pager);
	}

	@Override
	public String[] getTitles() {
		return getTopTitles();
	}

	@Override
	public Fragment getFrament(int position) {
		return getFragments(position);
	}

	/**
	 * 得到标题
	 * 
	 * @return
	 */
	public abstract String[] getTopTitles();

	public abstract Fragment getFragments(int position);
}
