package com.baiyi.cmall.activities.main.provider.viewpager;

import java.util.ArrayList;

import org.json.JSONObject;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.provider.DeleGateBuyMainActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.IntentionDetailStandardInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.pageviews.BaseScrollViewPageView;
import com.baiyi.cmall.request.manager.DelegationManager;
import com.baiyi.cmall.request.manager.ProviderSourceManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.XmlName;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.cmall.views.LoadingBar;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.MyLinearLayout;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

/**
 * 供应-详情
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-1-4 下午1:14:51
 */
public abstract class BaseProviderDetailsViewPager extends BaseScrollViewPageView implements OnClickListener {
	// 数据
	public GoodsSourceInfo info;
	public GoodsSourceInfo sourceInfo;
	// 山下文
	public Context context;
	// 这个状态标记是否是供应订单详情，如果是，就不显示编辑
	public int state;
	// 自定因的线性布局
	public MyLinearLayout layout;

	// 货源ID
	public String id;
	// 用户ｉＤ
	public String userID = null;
	//
	public RequestNetResultInfo resultInfo;

	/**
	 * 
	 * @param context
	 */
	public BaseProviderDetailsViewPager(Context context) {
		super(context);
		this.context = context;
//		userID = CmallApplication.getUserInfo().getUserID();
		userID = XmlUtils.getInstence(context).getXmlStringValue(XmlName.USER_ID);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		layout = new MyLinearLayout(context);
		addView(layout);

		initData();

	}

	// 工业分析数据源
	public ArrayList<IntentionDetailStandardInfo> datas;

	/**
	 * 数据
	 */
	private void initData() {

		// 显示进度
		startLoading();

		JsonLoader loader = new JsonLoader(context);
		loader.setUrl(AppNetUrl.getStandardAgrmentDetailUrl(id, userID));
		loader.setPostData(new JSONObject().toString());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
				
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				stopLoading();;
				((BaseActivity) context).displayToast(arg2);
				((BaseActivity) context).finish();
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				info = ProviderSourceManager.getStandardArgmentResultInfo(arg1);
				stopLoading();
				
				if (null != info) {
					boolean isowner = info.isIsowner();
					datas = info.getStandardInfos();
					resultInfo = info.getResultInfo();
					if (1 == resultInfo.getStatus()) {
						initContentView(layout);
						if (listener != null) {
							listener.onCurrentCompanyID(isowner, resultInfo);
							listener.ondataFinished(info.isIsfollow(), resultInfo);
						}
					} else if (-1 == resultInfo.getStatus()) {
						initContentView(layout);
						if (null != listener) {
							listener.onCurrentCompanyID(isowner, resultInfo);
							listener.ondataFinished(true,  resultInfo);
						}

					} /*
						 * else if (-1 == resultInfo.getStatus()) {
						 * ((BaseActivity) context).displayToast(resultInfo
						 * .getMsg()); return; }
						 */else {
						if (null != listener) {
							listener.onCurrentCompanyID(isowner, resultInfo);
							listener.onTokenFailure(resultInfo);
							userID = null;
						}
						return;
					}
				} else {
					((BaseActivity) context).displayToast("查询失败");
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	private OnDataInitFinishedListener listener;

	public void setListener(OnDataInitFinishedListener listener) {
		this.listener = listener;
	}

	/**
	 * 关注按钮按下时的回调 关注成功以后的回调
	 * 
	 * @author Administrator
	 * 
	 *         date 2016-1-28
	 */
	public interface OnDataInitFinishedListener {
		void ondataFinished(boolean isFollow, RequestNetResultInfo resultInfo);

		void onCurrentCompanyID(boolean isowner, RequestNetResultInfo resultInfo);

		void onTokenFailure(RequestNetResultInfo resultInfo);
	}

	/**
	 * 记载内容
	 * 
	 * @param layout
	 */
	public abstract void initContentView(MyLinearLayout layout);

	@Override
	public void onClick(View v) {

	}

	/**
	 * 进入委托采购界面
	 */
	public void goDelegation() {
		((BaseActivity) context).goActivity(info, DeleGateBuyMainActivity.class);
	}

	// 进度
	private LoadingBar loadingBar;

	/**
	 * 加入关注
	 */
	public void joinAttention() {

		// 显示进度
		startLoading();

		JsonLoader loader = new JsonLoader(context);
		loader.setUrl(AppNetUrl.getAttentionDetailUrl());
		loader.setPostData(ProviderSourceManager.getAttiontionPostData(userID, info));
		loader.addRequestHeader("token", CmallApplication.getUserInfo().getToken());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
				loadingBar.setProgressInfo("正在解析...");
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				stopLoading();
				((BaseActivity) context).displayToast(arg2);
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				RequestNetResultInfo info = ProviderSourceManager.getAttiontionResultInfo(arg1);
				stopLoading();

				if (1 == info.getStatus()) {
					((BaseActivity) context).displayToast(info.getMsg());
					if (null != listener) {
						listener.ondataFinished(true, info);
					}
				} else {
					((BaseActivity) context).displayToast(info.getMsg());
					if (NumEntity.PLEASE_LOG.equals(info.getMsg())) {
						if (null != listener) {
							listener.onTokenFailure(info);
						}
					}
					return;
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * 取消关注商家和取消关注采购
	 */
	public void cancelAttention() {
		
		startLoading();
		
		JsonLoader loader = new JsonLoader(context);
		loader.addRequestHeader("token", CmallApplication.getUserInfo().getToken());
		loader.setUrl(AppNetUrl.getCancelAttentionPurchaseUrl(sourceInfo.getGoodSID(), 1));
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
				stopLoading();
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				RequestNetResultInfo info = DelegationManager.getCancelAttentionResultInfo(arg1, state);
				stopLoading();
				// 更新界面
				((BaseActivity) context).displayToast(info.getMsg());
				if (1 == info.getStatus()) {
					 Intent intent= new Intent();
					 ((BaseActivity)context).setResult(1, intent);
					((BaseActivity) context).finish();
				} else {
					return;
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
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
				new LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
						android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
		layout.addView(tView, params);
	}

	private void startLoading() {
		if (loadingBar == null) {
			loadingBar = new LoadingBar(context);
			loadingBar.start();
		}
	}

	private void stopLoading() {
		if (loadingBar != null) {
			loadingBar.stop();
		}
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

	}

}
