package com.baiyi.cmall.activities.user.buyer.intention.detail;

import org.json.JSONArray;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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

/**
 * 采购详情
 * 
 * @author lizl
 */
public abstract class BaseDetailProViewPager extends BaseScrollViewPageView {

	// 上下文
	public Context context;
	// 数据源
	public GoodsSourceInfo info;
	// 供应意向ID
	public String attentionID;
	// 加载进度
	public MyLoadingBar loadingBar = null;
	// 自定义的线性布局
	public MyLinearLayout layout;

	private String companyId;

	/*
	 * 初始化内容
	 */
	public abstract void initContent();

	public BaseDetailProViewPager(Context context, String attentionID) {
		super(context);
		this.context = context;
		this.attentionID = attentionID;
		layout = new MyLinearLayout(context);
		addView(layout);
		initCompanyId();
		loaderProgress();
		loaderData();

	}

	/**
	 * 初始化商家ID
	 */
	private void initCompanyId() {
		companyId = XmlUtils.getInstence(context)
				.getXmlStringValue("companyID");
	}

	private void loaderProgress() {

		loadingBar = new MyLoadingBar(context);
		loadingBar.setProgressInfo("正在加载中...");
		loadingBar.setPadding(0,
				Config.getInstance().getScreenHeight(context) / 3, 0, 0);
		loadingBar.start();
		layout.addView(loadingBar);

	}

	/**
	 * 停止进度条
	 */
	private void stopProgress() {
		loadingBar.stop();
		layout.removeView(loadingBar);
	}

	private void loaderData() {

		JsonLoader loader = new JsonLoader(context);
		loader.setUrl(AppNetUrl.getDetailProUrl(attentionID));

		loader.setPostData(NullJsonData.getPostData());
		loader.setMethod(BaseNetLoder.Method_Post);
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
				((BaseActivity) context).displayToast(errorMessage);
			}

			@Override
			public void onCompelete(Object tag, Object result) {

				JSONArray array = (JSONArray) result;
				Log.d("TAG", "gonghuo列表条目详情======" + result.toString());
				info = MyPurchaseManager.getProItemDetail(array);

				stopProgress();

				// 服务器返回信息
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);

				// 当成功时初始化界面
				if (1 == info.getStatus()) {

					initContent();
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);

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
