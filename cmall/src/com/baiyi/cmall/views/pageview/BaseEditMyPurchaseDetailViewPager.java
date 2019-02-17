package com.baiyi.cmall.views.pageview;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.pageviews.BaseScrollViewPageView;
import com.baiyi.cmall.request.manager.MyPurchaseManager;
import com.baiyi.cmall.request.manager.NullJsonData;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.JsonParse_User;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.MyLinearLayout;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 用户——我的采购—— 采购详情
 * 
 * @author lizl
 * 
 */
public abstract class BaseEditMyPurchaseDetailViewPager extends
		BaseScrollViewPageView {

	// 上下文
	public Context context;
	// 数据源
	public GoodsSourceInfo info;
	// 进度条
	public MyLoadingBar progressBar;
	// 采购ID
	public int id;
	// 是否显示 ---分类/产地 等信息(true为采购编辑信息，false为意向编辑信息)
	public boolean isShow;

	public abstract void initContent();

	// 自定义的线性布局
	public MyLinearLayout layout;

	public BaseEditMyPurchaseDetailViewPager(Context context, int id,
			boolean isShow) {
		super(context);
		this.context = context;
		this.id = id;
		this.isShow = isShow;
		layout = new MyLinearLayout(context);
		addView(layout);
		loaderProgress();

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
	}

	/**
	 * 加载进度条
	 */
	private void loaderProgress() {

		progressBar = new MyLoadingBar(context);
		progressBar.setProgressInfo("正在加载中...");
		progressBar.setPadding(0,
				Config.getInstance().getScreenHeight(context) / 3, 0, 0);
		progressBar.start();
		layout.addView(progressBar);

	}

	/**
	 * 停止进度条
	 */
	private void stopProgress() {
		progressBar.stop();
		layout.removeView(progressBar);
	}

	/**
	 * 根据采购ID初始化数据
	 */
	private void initData() {

		JsonLoader jsonLoader = new JsonLoader(context);
		jsonLoader.setUrl(isShow ? AppNetUrl.getEditIntentionDetailUrl(id)
				: AppNetUrl.getEditPurDetailUrl(id));
		jsonLoader.setPostData(NullJsonData.getPostData());
		jsonLoader.setMethod(BaseNetLoder.Method_Post);

		jsonLoader.addRequestHeader("token", CmallApplication.getUserInfo()
				.getToken());
		jsonLoader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum,
					long totalByteNum) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {
				// Log.d("TAG", "错误信息" + errorMessage);
				((BaseActivity) context).displayToast(errorMessage);
			}

			@Override
			public void onCompelete(Object tag, Object result) {

				JSONArray array = (JSONArray) result;
				// 获取数据成功时移除进度条
				stopProgress();

				RequestNetResultInfo infoResult = JsonParse_User
						.getResultInfo(array);
				if (1 == infoResult.getStatus()) {
					info = MyPurchaseManager.getMyPurchaseDetail(array);
					initContent();
				} else {
					// 当获取数据失败的时候退出此界面
					((BaseActivity) context).finish();
					((BaseActivity) context).displayToast(infoResult.getMsg());
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

	}
}
