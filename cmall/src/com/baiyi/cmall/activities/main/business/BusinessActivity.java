package com.baiyi.cmall.activities.main.business;

import org.json.JSONArray;
import me.relex.seamlessviewpagerheader.tools.ScrollableFragmentListener;
import me.relex.seamlessviewpagerheader.tools.ScrollableListener;
import me.relex.seamlessviewpagerheader.tools.ViewPagerHeaderHelper;
import me.relex.seamlessviewpagerheader.widget.SlidingTabLayout;
import me.relex.seamlessviewpagerheader.widget.TouchCallbackLayout;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.business.entity.CbmEntity;
import com.baiyi.cmall.activities.main.business.page.DynamicFragment;
import com.baiyi.cmall.activities.main.business.page.ProFragment;
import com.baiyi.cmall.activities.main.business.page.ProFragment.OnGetBusinessDataListener;
import com.baiyi.cmall.activities.main.business.page.ShopDetailFragment;
import com.baiyi.cmall.activities.main.business.page.SupplyFragment;
import com.baiyi.cmall.activities.main.mall.JsonParseMall;
import com.baiyi.cmall.activities.main.mall.MallDefine;
import com.baiyi.cmall.activities.main.mall.UrlNet;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.request.manager.MallManager;
import com.baiyi.cmall.utils.ImageTools;
import com.baiyi.cmall.utils.JsonParseBase;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.utils.XmlName;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.cmall.views.itemview.CommonSearchView;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;

/**
 * 商家-店铺
 * 
 * @author tangkun
 * 
 */
public class BusinessActivity extends BaseActivity implements
		TouchCallbackLayout.TouchEventListener, ScrollableFragmentListener,
		ViewPagerHeaderHelper.OnViewPagerTouchListener {

	private static final long DEFAULT_DURATION = 300L;
	private static final float DEFAULT_DAMPING = 1.5f;
	private static final String TAG = "MainActivity";

	private SparseArrayCompat<ScrollableListener> mScrollableListenerArrays = new SparseArrayCompat<ScrollableListener>();
	private ViewPager mViewPager;
	private View mHeaderLayoutView;
	private ViewPagerHeaderHelper mViewPagerHeaderHelper;
	private TextView mTvFollow;
	private ImageView mImgBusinessPic;// 商家头像
	private TextView mTvBusinessName;// 商家名称
	private TextView mTvBusinessFollowNum;// 关注人数

	private int mTouchSlop;
	private int mTabHeight;
	private int mHeaderHeight;

	private String token = null;
	private Interpolator mInterpolator = new DecelerateInterpolator();

	private static final String[] titles = { "全部商品", "供应", "店铺详情", "店铺动态" };
	// 商家id
	private int ci;

	protected void initWin(boolean hasScrollView) {
		super.initWin(false);
		token = XmlUtils.getInstence(this).getXmlStringValue(XmlName.TOKEN);
		ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_business,
				win_content);
		initTitleView();
		initContent();
		ci = getIntent().getIntExtra(MallDefine.CI, -1);
		loadData(UrlNet.getFollowFocusCompany(ci + ""), "", FOLLOW_FOCUS);
	}

	private void initTitleView() {

		CommonSearchView commonSearchView = new CommonSearchView(
				BusinessActivity.this);
		commonSearchView.showBack();
		win_title.addView(commonSearchView);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	private void initContent() {

		View view = ContextUtil.getLayoutInflater(this)//
				.inflate(R.layout.activity_business, null);
		win_content.addView(view);
		mTvFollow = (TextView) findViewById(R.id.tv_follow);

		mImgBusinessPic = (ImageView) findViewById(R.id.img_business_pic);
		mTvBusinessName = (TextView) findViewById(R.id.tv_business_name);
		mTvBusinessFollowNum = (TextView) findViewById(R.id.tv_business_follow_num);
		mTvFollow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (TextViewUtil.isStringEmpty(token)) {
					displayToast("请先登录");
					return;
				}
				loadData(UrlNet.addFollowCompany(),
						MallManager.getAddCompanyData(ci), ADD_FOLLOW);
			}
		});

		mTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
		mTabHeight = getResources().getDimensionPixelSize(R.dimen.tabs_height);
		mHeaderHeight = getResources().getDimensionPixelSize(
				R.dimen.viewpager_header_height);

		mViewPagerHeaderHelper = new ViewPagerHeaderHelper(this, this);

		TouchCallbackLayout touchCallbackLayout = (TouchCallbackLayout) findViewById(R.id.layout);
		touchCallbackLayout.setTouchEventListener(this);

		mHeaderLayoutView = findViewById(R.id.header);

		SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tabs);
		// slidingTabLayout.setDistributeEvenly(true);

		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		// 不预加载
		mViewPager.setOffscreenPageLimit(0);
		mViewPager
				.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

		slidingTabLayout.setViewPager(mViewPager);

		ViewCompat.setTranslationY(mViewPager, mHeaderHeight);
	}

	@Override
	public boolean onLayoutInterceptTouchEvent(MotionEvent event) {

		return mViewPagerHeaderHelper.onLayoutInterceptTouchEvent(event,
				mTabHeight + mHeaderHeight);
	}

	@Override
	public boolean onLayoutTouchEvent(MotionEvent event) {
		return mViewPagerHeaderHelper.onLayoutTouchEvent(event);
	}

	@Override
	public boolean isViewBeingDragged(MotionEvent event) {
		return mScrollableListenerArrays.valueAt(mViewPager.getCurrentItem())
				.isViewBeingDragged(event);
	}

	@Override
	public void onMoveStarted(float y) {

	}

	@Override
	public void onMove(float y, float yDx) {
		float headerTranslationY = ViewCompat
				.getTranslationY(mHeaderLayoutView) + yDx;
		if (headerTranslationY >= 0) {
			headerExpand(0L);

			if (countPullEnd >= 1) {
				if (countPullEnd == 1) {
					downtime = SystemClock.uptimeMillis();
					simulateTouchEvent(mViewPager, downtime,
							SystemClock.uptimeMillis(),
							MotionEvent.ACTION_DOWN, 250, y + mHeaderHeight);
				}
				simulateTouchEvent(mViewPager, downtime,
						SystemClock.uptimeMillis(), MotionEvent.ACTION_MOVE,
						250, y + mHeaderHeight);
			}
			countPullEnd++;

		} else if (headerTranslationY <= -mHeaderHeight) { // push end
			headerFold(0L);

			if (countPushEnd >= 1) {
				if (countPushEnd == 1) {
					downtime = SystemClock.uptimeMillis();
					simulateTouchEvent(mViewPager, downtime,
							SystemClock.uptimeMillis(),
							MotionEvent.ACTION_DOWN, 250, y + mHeaderHeight);
				}
				simulateTouchEvent(mViewPager, downtime,
						SystemClock.uptimeMillis(), MotionEvent.ACTION_MOVE,
						250, y + mHeaderHeight);
			}
			countPushEnd++;

		} else {

			ViewCompat.animate(mHeaderLayoutView)
					.translationY(headerTranslationY).setDuration(0).start();
			ViewCompat.animate(mViewPager)
					.translationY(headerTranslationY + mHeaderHeight)
					.setDuration(0).start();
		}
	}

	long downtime = -1;
	int countPushEnd = 0, countPullEnd = 0;

	private void simulateTouchEvent(View dispatcher, long downTime,
			long eventTime, int action, float x, float y) {
		MotionEvent motionEvent = MotionEvent.obtain(
				SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), action,
				x, y, 0);
		try {
			dispatcher.dispatchTouchEvent(motionEvent);
		} catch (Throwable e) {
			Log.e(TAG, "simulateTouchEvent error: " + e.toString());
		} finally {
			motionEvent.recycle();
		}
	}

	@Override
	public void onMoveEnded(boolean isFling, float flingVelocityY) {

		countPushEnd = countPullEnd = 0;

		float headerY = ViewCompat.getTranslationY(mHeaderLayoutView);
		if (headerY == 0 || headerY == -mHeaderHeight) {
			return;
		}

		if (mViewPagerHeaderHelper.getInitialMotionY()
				- mViewPagerHeaderHelper.getLastMotionY() < -mTouchSlop) {
			headerExpand(headerMoveDuration(true, headerY, isFling,
					flingVelocityY));
		} else if (mViewPagerHeaderHelper.getInitialMotionY()
				- mViewPagerHeaderHelper.getLastMotionY() > mTouchSlop) {
			headerFold(headerMoveDuration(false, headerY, isFling,
					flingVelocityY));
		} else {
			if (headerY > -mHeaderHeight / 2f) {
				headerExpand(headerMoveDuration(true, headerY, isFling,
						flingVelocityY));
			} else {
				headerFold(headerMoveDuration(false, headerY, isFling,
						flingVelocityY));
			}
		}
	}

	private long headerMoveDuration(boolean isExpand, float currentHeaderY,
			boolean isFling, float velocityY) {

		long defaultDuration = DEFAULT_DURATION;

		if (isFling) {

			float distance = isExpand ? Math.abs(mHeaderHeight)
					- Math.abs(currentHeaderY) : Math.abs(currentHeaderY);
			velocityY = Math.abs(velocityY) / 1000;

			defaultDuration = (long) (distance / velocityY * DEFAULT_DAMPING);

			defaultDuration = defaultDuration > DEFAULT_DURATION ? DEFAULT_DURATION
					: defaultDuration;
		}

		return defaultDuration;
	}

	private void headerFold(long duration) {
		ViewCompat.animate(mHeaderLayoutView).translationY(-mHeaderHeight)
				.setDuration(duration).setInterpolator(mInterpolator).start();

		ViewCompat.animate(mViewPager).translationY(0).setDuration(duration)
				.setInterpolator(mInterpolator).start();

		mViewPagerHeaderHelper.setHeaderExpand(false);
	}

	private void headerExpand(long duration) {
		ViewCompat.animate(mHeaderLayoutView).translationY(0)
				.setDuration(duration).setInterpolator(mInterpolator).start();

		ViewCompat.animate(mViewPager).translationY(mHeaderHeight)
				.setDuration(duration).setInterpolator(mInterpolator).start();
		mViewPagerHeaderHelper.setHeaderExpand(true);
	}

	@Override
	public void onFragmentAttached(ScrollableListener listener, int position) {
		mScrollableListenerArrays.put(position, listener);
	}

	@Override
	public void onFragmentDetached(ScrollableListener listener, int position) {
		mScrollableListenerArrays.remove(position);
	}

	private class ViewPagerAdapter extends FragmentStatePagerAdapter {

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {

			if (position == 0) {// 商家信息必须在商家对应的商品列表中回调获取
				return ProFragment.newInstance(position, ci)
						.setOnGetBusinessData(new OnGetBusinessDataListener() {

							@Override
							public void getBusinessData(CbmEntity cbmEntity) {

								setBusinessView(cbmEntity);

							}
						});
			} else if (position == 1) {
				return SupplyFragment.newInstance(position, ci);
			} else if (position == 2) {
				return ShopDetailFragment.newInstance(position, ci);
			} else {
				return DynamicFragment.newInstance(position);
			}
		}

		/**
		 * 设置商家信息
		 * 
		 * @param cbmEntity
		 */
		private void setBusinessView(CbmEntity cbmEntity) {

			ImageTools.getNormalImage(BusinessActivity.this, mImgBusinessPic,
					cbmEntity.getCurl());
			mTvBusinessName.setText(cbmEntity.getCn());
			mTvBusinessFollowNum.setText(cbmEntity.getCf() + "人关注");
		}

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
		}
	}

	// 判断是否有没有关注
	private static final int FOLLOW_FOCUS = 2;
	// 添加关注
	private static final int ADD_FOLLOW = 3;

	private void loadData(String url, String postData, final int state) {
		loaderProgress();
		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(url);
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setPostData(postData);
		loader.addRequestHeader("token", CmallApplication.getUserInfo()
				.getToken());
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum,
					long totalByteNum) {
			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {
				stopProgress();
			}

			@Override
			public void onCompelete(Object tag, Object result) {
				stopProgress();
				switch (state) {

				case FOLLOW_FOCUS:
					RequestNetResultInfo requestNetResultInfo = JsonParseMall
							.getResultInfo((JSONArray) result);
					/*
					 * followState=0时：当前用户没有关注当前商家 followState=1时：当前用户已经关注当前商家
					 * followState=2时：因某些原因不能对当前商家进行关注，原因会在msg中
					 */
					int followState = JsonParseMall
							.getDataInt((JSONArray) result);
					if (followState == 0) {
						mTvFollow.setEnabled(true);
					} else if (followState == 1) {
						mTvFollow.setText("已关注");
						mTvFollow.setEnabled(false);
					} else if (followState == 2) {
						displayToast(requestNetResultInfo.getMsg());
					}
					break;
				case ADD_FOLLOW:
					if (JsonParseBase.getstatus((JSONArray) result)) {
						mTvFollow.setText("已关注");
						mTvFollow.setEnabled(false);
					}
					displayToast(JsonParseBase.getMsg((JSONArray) result));
					break;
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}
}
