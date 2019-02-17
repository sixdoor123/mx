package com.baiyi.cmall.activities.main.business.page;

import com.baiyi.cmall.views.LoadingBar;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import me.relex.seamlessviewpagerheader.tools.ScrollableFragmentListener;
import me.relex.seamlessviewpagerheader.tools.ScrollableListener;

public abstract class BaseViewPagerFragment extends Fragment implements ScrollableListener {

	private static final String TAG = "BaseViewPagerFragment";
	protected ScrollableFragmentListener mListener;
	public static final String BUNDLE_FRAGMENT_INDEX = "BaseFragment.BUNDLE_FRAGMENT_INDEX";
	protected int mFragmentIndex;
	private LoadingBar progressBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			mFragmentIndex = bundle.getInt(BUNDLE_FRAGMENT_INDEX, 0);
		}

		if (mListener != null) {
			mListener.onFragmentAttached(this, mFragmentIndex);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (ScrollableFragmentListener) activity;
		} catch (ClassCastException e) {
			Log.e(TAG, activity.toString() + " must implement ScrollableFragmentListener");
		}
	}

	/**
	 * 加载进度条
	 */
	protected void loaderProgress() {

		if (null == progressBar) {
			progressBar = new LoadingBar(getContext());
		}
		progressBar.start();
	}

	/**
	 * 结束进度条
	 */
	protected void stopProgress() {

		if (null != progressBar) {
			progressBar.stop();
		}
	}

	@Override
	public void onDetach() {
		if (mListener != null) {
			mListener.onFragmentDetached(this, mFragmentIndex);
		}

		super.onDetach();
		mListener = null;
	}

	// 产品id
	public String id;

	public BaseViewPagerFragment setId(String id) {
		this.id = id;
		return this;
	}
	
	//产品状态值
	public int state;
	public BaseViewPagerFragment setState(int state){
		this.state = state;
		return this ;
	}
}
