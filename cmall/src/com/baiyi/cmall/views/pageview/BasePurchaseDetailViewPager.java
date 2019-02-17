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
 * �ɹ�����
 * 
 * @author lizl
 */
public abstract class BasePurchaseDetailViewPager extends
		BaseScrollViewPageView {

	// ������
	public Context context;
	// ����Դ
	public GoodsSourceInfo info;
	private GoodsSourceInfo sourceInfo;
	// �ɹ�ID
	public String purID;
	// ���ؽ���
	public MyLoadingBar loadingBar = null;
	// �Զ�������Բ���
	public MyLinearLayout layout;

	/*
	 * ��ʼ������
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
		loadingBar.setProgressInfo("���ڼ�����...");
		loadingBar.setPadding(0,
				Config.getInstance().getScreenHeight(context) / 3, 0, 0);
		loadingBar.start();
		layout.addView(loadingBar);

	}

	/**
	 * ֹͣ������
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

				// ������������Ϣ
				resultInfo = info.getResultInfo();

				// ���ɹ�ʱ��ʼ������
				if (1 == resultInfo.getStatus()) {

					initContent(resultInfo);
					if (null != dataFinishListener) {
						// ��ȡ��ע״̬,�û�ID
						dataFinishListener.getState(info.isIsfollow(),
								info.getUserId(), resultInfo);
					}
				} else if (-1 == resultInfo.getStatus()) {
					initContent(resultInfo);
					if (null != dataFinishListener) {
						// ��ȡ��ע״̬,�û�ID
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
	 * ȡ����ע�̼Һ�ȡ����ע�ɹ�
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
				// ���½���
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
	 * �ص��ӿ� �����õ����ݺ󣬷��ع�ע��״̬���û�ID
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
	 * ��ת����Ӧί�й�Ӧ����
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
	 * ��ʾ��Ӧ��Ϣ���¼�
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
