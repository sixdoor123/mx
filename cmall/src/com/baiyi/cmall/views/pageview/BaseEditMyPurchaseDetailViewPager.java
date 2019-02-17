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
 * �û������ҵĲɹ����� �ɹ�����
 * 
 * @author lizl
 * 
 */
public abstract class BaseEditMyPurchaseDetailViewPager extends
		BaseScrollViewPageView {

	// ������
	public Context context;
	// ����Դ
	public GoodsSourceInfo info;
	// ������
	public MyLoadingBar progressBar;
	// �ɹ�ID
	public int id;
	// �Ƿ���ʾ ---����/���� ����Ϣ(trueΪ�ɹ��༭��Ϣ��falseΪ����༭��Ϣ)
	public boolean isShow;

	public abstract void initContent();

	// �Զ�������Բ���
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
	 * ���ؽ�����
	 */
	private void loaderProgress() {

		progressBar = new MyLoadingBar(context);
		progressBar.setProgressInfo("���ڼ�����...");
		progressBar.setPadding(0,
				Config.getInstance().getScreenHeight(context) / 3, 0, 0);
		progressBar.start();
		layout.addView(progressBar);

	}

	/**
	 * ֹͣ������
	 */
	private void stopProgress() {
		progressBar.stop();
		layout.removeView(progressBar);
	}

	/**
	 * ���ݲɹ�ID��ʼ������
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
				// Log.d("TAG", "������Ϣ" + errorMessage);
				((BaseActivity) context).displayToast(errorMessage);
			}

			@Override
			public void onCompelete(Object tag, Object result) {

				JSONArray array = (JSONArray) result;
				// ��ȡ���ݳɹ�ʱ�Ƴ�������
				stopProgress();

				RequestNetResultInfo infoResult = JsonParse_User
						.getResultInfo(array);
				if (1 == infoResult.getStatus()) {
					info = MyPurchaseManager.getMyPurchaseDetail(array);
					initContent();
				} else {
					// ����ȡ����ʧ�ܵ�ʱ���˳��˽���
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
