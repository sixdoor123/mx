package com.baiyi.cmall.views.pageview;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.purchase.DelegateProviderActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.pageviews.BaseScrollViewPageView;
import com.baiyi.cmall.request.manager.DelegationManager;
import com.baiyi.cmall.request.manager.NullJsonData;
import com.baiyi.cmall.request.manager.PurchaseSourceManager;
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
 * 采购详情
 * 
 * @author lizl
 */
public abstract class BasePurchaseDetailViewPager extends
		BaseScrollViewPageView {

	// 上下文
	public Context context;
	// 数据源
	public GoodsSourceInfo info;
	private GoodsSourceInfo sourceInfo;
	// 采购ID
	public String purID;
	// 加载进度
	public MyLoadingBar loadingBar = null;
	// 自定义的线性布局
	public MyLinearLayout layout;

	/*
	 * 初始化内容
	 */
	public abstract void initContent(RequestNetResultInfo resultInfo);

	public BasePurchaseDetailViewPager(Context context, String purID,
			GoodsSourceInfo sourceInfo) {
		super(context);
		this.context = context;
		this.purID = purID;
		this.sourceInfo = sourceInfo;
		layout = new MyLinearLayout(context);
		addView(layout);
		loaderProgress();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loaderData();
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

	public RequestNetResultInfo resultInfo;

	private void loaderData() {

		JsonLoader loader = new JsonLoader(context);
		loader.setUrl(AppNetUrl.getPurchaseDetailUrl(purID));
		loader.addRequestHeader("token", CmallApplication.getUserInfo().getToken());
		loader.setPostData(NullJsonData.getPostData());
		loader.setMethod(BaseNetLoder.Method_Post);

		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum,
					long totalByteNum) {
			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {

				stopProgress();
				((BaseActivity) context).finish();
				((BaseActivity) context).displayToast(errorMessage);
			}

			@Override
			public void onCompelete(Object tag, Object result) {

				JSONArray array = (JSONArray) result;
				info = PurchaseSourceManager.getPurchaseItemDetail(array);

				stopProgress();

				// 服务器返回信息
				resultInfo = info.getResultInfo();

				// 当成功时初始化界面
				if (1 == resultInfo.getStatus()) {

					initContent(resultInfo);
					if (null != dataFinishListener) {
						// 获取关注状态,用户ID
						dataFinishListener.getState(info.isIsfollow(),
								info.getUserId(), resultInfo);
					}
				} else if (-1 == resultInfo.getStatus()) {
					initContent(resultInfo);
					if (null != dataFinishListener) {
						// 获取关注状态,用户ID
						// dataFinishListener.initContent("0", resultInfo);
						dataFinishListener.getState(true, "0", resultInfo);
					}
				}else {
					((BaseActivity) context).finish();
					((BaseActivity) context).displayToast(resultInfo.getMsg());
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);

	}

	/**
	 * 取消关注商家和取消关注采购
	 */
	public void cancelAttention() {
		JsonLoader loader = new JsonLoader(context);
		loader.addRequestHeader("token", CmallApplication.getUserInfo()
				.getToken());
		loader.setUrl(AppNetUrl.getCancelAttentionPurchaseUrl(
				sourceInfo.getGoodSID(), 0));
		loader.setPostData(new JSONObject().toString());
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				((BaseActivity) context).displayToast(arg2);
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				RequestNetResultInfo info = DelegationManager
						.getCancelAttentionResultInfo(arg1, 0);
				// 更新界面
				((BaseActivity) context).displayToast(info.getMsg());
				if (1 == info.getStatus()) {
					((BaseActivity) context).finish();
				} else {
					return;
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * 回调接口 用于拿到数据后，返回关注的状态和用户ID
	 */
	private OnGetDataFinishListener dataFinishListener;

	public void setFinishListener(OnGetDataFinishListener dataFinishListener) {

		this.dataFinishListener = dataFinishListener;
	}

	public interface OnGetDataFinishListener {

		void getState(boolean isAttention, String userId,
				RequestNetResultInfo resultInfo);

		// void initContent(String userId, RequestNetResultInfo resultInfo);
	}

	/**
	 * 跳转至供应委托供应界面
	 * 
	 */
	public void toActivity() {
		((BaseActivity) context).goActivity(info,
				DelegateProviderActivity.class);

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

	}

	/**
	 * 显示供应信息已下架
	 */
	public void initNotContent() {
		TextView tView = new TextView(context);
		tView.setText(resultInfo.getMsg());
		tView.setGravity(Gravity.CENTER);
		tView.setPadding(0, 15, 0, 0);
		tView.setTextSize(16);
		android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(
				new LayoutParams(
						android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
						android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
		layout.addView(tView, params);
	}
}
