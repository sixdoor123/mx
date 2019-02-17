package com.baiyi.cmall.activities.main._public;

import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.cache.SimpleCacheLoader;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 公共类的基类
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-31 上午10:29:40
 */
public abstract class BasePublicActivity extends BaseActivity implements
		OnClickListener {

	public GoodsSourceInfo sourceInfo;

	@Override
	protected void initWin(boolean hasScrollView) {
		super.initWin(false);
		initData();
		initTitle();
		loadData();
	}

	/**
	 * 初始化一些数据
	 */
	public abstract void initData();

	// 返回按钮
	private LinearLayout mImgBack;
	// 标题
	private TextView mTxtTitle;
	// 确定按钮
	public TextView mTxtSure;

	public LinearLayout mLlSure;

	/**
	 * 初始化标题
	 */
	@SuppressLint("InflateParams")
	private void initTitle() {
		View view = null;
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			view = ContextUtil.getLayoutInflater(this).inflate(
					R.layout.item_back_select_title_h, null);
		} else {
			view = ContextUtil.getLayoutInflater(this).inflate(
					R.layout.item_back_select_title_l, null);
		}
		win_title.addView(view);

		mImgBack = (LinearLayout) view.findViewById(R.id.ll_);
		mImgBack.setOnClickListener(this);
		mTxtSure = (TextView) view.findViewById(R.id.sure);
		mLlSure = (LinearLayout) view.findViewById(R.id.ll_sure);
		mLlSure.setOnClickListener(this);
		mLlSure.setVisibility(View.INVISIBLE);

		mTxtTitle = (TextView) view.findViewById(R.id.title);
		mTxtTitle.setText(setTitleName());
	}

	/**
	 * 设置标题
	 * 
	 * @return
	 */
	public abstract CharSequence setTitleName();

	/**
	 * 加载数据
	 */
	private void loadData() {

		GoodsSourceInfo info = BaseCacheUtil.getPublicDataInfo(this);
		if (null != info) {
			initSubData(info);
			initView();
			return;
		}


		final MyLoadingBar loadingBar = new MyLoadingBar(this);
		loadingBar.setProgressInfo("正在加载中...");
		loadingBar.setPadding(0, getScreenHeight() / 3, 0, 0);
		loadingBar.start();
		win_content.addView(loadingBar);

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getPublicUrl());
		loader.setPostData(new JSONObject().toString());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
				loadingBar.setProgressInfo("正在解析");
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				win_content.removeView(loadingBar);
				loadingBar.stop();
				displayToast(arg2);
			}

			@Override
			public void onCompelete(Object arg0, Object result) {
				win_content.removeView(loadingBar);
				loadingBar.stop();
//				sourceInfo = PublicActivityManager.getSelectedData(
//						BasePublicActivity.this, result);
//				UserApplication.setBaseDataInfo(sourceInfo);
//				cacheBaseData(BasePublicActivity.this, ((JSONArray) result)
//						.toString().getBytes());
				jsonParse(result);
				initView();
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * 缓存基础数据
	 * 
	 * @param context
	 * @param data
	 */
	@SuppressWarnings("unused")
	private static void cacheBaseData(Context context, byte[] data) {
		SimpleCacheLoader cache = new SimpleCacheLoader(
				CmallApplication.getBaseDataCache(context));
		cache.setUpdate(CmallApplication.BaseDataFileName, data);
		CmallApplication.getDataStratey().startLoader(cache);
	}

	/**
	 * 当可以从缓存中读取数据时，在此方法中进行数据的初始化
	 */
	public abstract void initSubData(GoodsSourceInfo info);

	/**
	 * 内容
	 */
	public abstract void initView();

	public abstract void jsonParse(Object result);

	/**
	 * 点击的监听事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		}
	}

	@Override
	protected void onDestroy() {
		sourceInfo = null;
		super.onDestroy();
	}
}
