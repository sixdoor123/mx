/**
 * 
 */
package com.baiyi.cmall.activities.main;

import me.relex.seamlessviewpagerheader.tools.ScrollableFragmentListener;
import me.relex.seamlessviewpagerheader.tools.ScrollableListener;
import me.relex.seamlessviewpagerheader.widget.SlidingTabLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.mall.MallDefine;
import com.baiyi.cmall.pageviews.MyViewPager;
import com.baiyi.core.util.ContextUtil;

/**
 * viewpager+slide title
 * 
 * @author tangkun
 *
 */
public abstract class BaseViewpagerActivity extends BaseActivity implements ScrollableFragmentListener {

	private MyViewPager mViewPager = null;

	public String id;

	// ²úÆ·×´Ì¬
	public String stateName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		View view = ContextUtil.getLayoutInflater(this)//
				.inflate(getContentLayout(), null);
		setContentView(view);

		initData();
		initViewPager(view);
	}

	protected void initData() {
		stateName = getIntent().getStringExtra("arg");
		id = getIntent().getStringExtra("temp");
		if (id == null) {
			id = getIntent().getStringExtra(MallDefine.PI);
		}
	
		// displayToast("id = " + id);
	}

	public ViewPagerAdapter adapter;

	private void initViewPager(View view) {

		SlidingTabLayout slidingTabLayout = getSlidingTabLayout(view);
		mViewPager = getMyViewPager(view);
		mViewPager.setOffscreenPageLimit(0);
		adapter = new ViewPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(adapter);
		slidingTabLayout.setViewPager(mViewPager);

	}

	public class ViewPagerAdapter extends FragmentStatePagerAdapter {

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {

			return getFrament(position);
		}

		@Override
		public int getCount() {
			return getTitles().length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return getTitles()[position];
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
		}
	}

	@Override
	public void onFragmentAttached(ScrollableListener fragment, int position) {

	}

	@Override
	public void onFragmentDetached(ScrollableListener fragment, int position) {

	}

	public abstract int getContentLayout();

	public abstract SlidingTabLayout getSlidingTabLayout(View view);

	public abstract MyViewPager getMyViewPager(View view);

	public abstract String[] getTitles();

	public abstract Fragment getFrament(int position);

}
