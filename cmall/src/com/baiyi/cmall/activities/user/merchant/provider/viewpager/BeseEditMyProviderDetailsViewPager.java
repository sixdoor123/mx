package com.baiyi.cmall.activities.user.merchant.provider.viewpager;

import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.database.dao.GoodSSourceLoader;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.pageviews.BaseScrollViewPageView;
import com.baiyi.cmall.request.manager.MerchantCenterManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.MyLinearLayout;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 
 * ���ǹ�Ӧ��-�༭����Ļ���
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-1-7 ����11:07:27
 */
public abstract class BeseEditMyProviderDetailsViewPager extends
		BaseScrollViewPageView implements OnClickListener {

	// �ж��Ƿ�ɲ���
	public boolean isEditDetailEnable;
	public int state;
	// ����Դʵ��
	public GoodsSourceInfo info;
	// ������
	public Context context;
	// ����
	public MyLinearLayout layout;
	// ����
	public MyLoadingBar loadingBar;

	// ������ѡ��ʵ��
	public SelectedInfo countryInfo;
	// ѡ������Ϣ
	public String content;

	// ���������޸ĵĿؼ�
	// public ArrayList<TextView> details;
	// ������Ե��޸Ŀؼ�
	// public ArrayList<EditText> standards;

	private String id;
	private String token;

	// ��¼�Զ���ʼ
	public String autoStart = "";
	// ��¼�Զ�����
	public String autoEnd = "";

	public BeseEditMyProviderDetailsViewPager(Context context, String id) {
		super(context);
		this.context = context;
		this.id = id;
		layout = new MyLinearLayout(context);
		addView(layout);

		// details = new ArrayList<TextView>();
		// standards = new ArrayList<EditText>();
		token = CmallApplication.getUserInfo().getToken();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		loadDatas();
	}

	/**
	 * ��������
	 */
	private void loadDatas() {
		loadingBar = new MyLoadingBar(context);
		loadingBar.setPadding(0,
				Config.getInstance().getScreenHeight(context) / 3, 0, 0);
		loadingBar.setProgressInfo("���ڼ�����...");
		loadingBar.start();
		layout.addView(loadingBar);

		JsonLoader loader = new JsonLoader(context);
		loader.setUrl(AppNetUrl.getMyProviderDetailEditUrl(id));
		loader.setPostData(new JSONObject().toString());
		loader.addRequestHeader("token", token);
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
				loadingBar.setProgressInfo("���ڽ���...");
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				layout.removeView(loadingBar);
				loadingBar.stop();
				((BaseActivity) context).displayToast(arg2.toString());
				((BaseActivity) context).finish();
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				info = MerchantCenterManager.getEditMyProviderDetails(arg1);

				layout.removeView(loadingBar);
				loadingBar.stop();
				initContent();
				// initButton();
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * ��ʼ������
	 */
	public abstract void initContent();

	/**
	 * �ҵĹ�Ӧ �༭����ύ�޸İ�ť
	 */
	public void editComplete(String intentionID, int idState) {
		// �õ������������Ϣ
		if (!getInputGoodSInfo()) {
			return;
		}

		loadingBar.setVisibility(View.VISIBLE);
		loadingBar.setProgressInfo("���ڼ�����...");
		loadingBar.start();

		JsonLoader loader = new JsonLoader(context);
		loader.setUrl(AppNetUrl.getMyProviderDetailEditCompleteUrl(state));
		loader.setPostData(MerchantCenterManager.getMyProviderDetailEditArg(
				info, ProDataUtils.getDetails(), ProDataUtils.getMap(),
				ProDataUtils.getInfos(), countryInfo, state, intentionID,
				idState, autoStart, autoEnd));
		loader.addRequestHeader("token", token);
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType("application/json");

		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
				loadingBar.setProgressInfo("���ڽ���...");
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				loadingBar.setVisibility(View.GONE);
				loadingBar.stop();
				((BaseActivity) context).displayToast(arg2.toString());
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				RequestNetResultInfo info = MerchantCenterManager
						.getgetMyProviderDetailsEditComplete(arg1);
				if (null != info) {
					((BaseActivity) context).displayToast(info.getMsg());
					loadingBar.setVisibility(View.GONE);
					loadingBar.stop();
					if (info.getStatus() == 1) {
						// �������ݿ�
						// saveSQlDataBase();
						if (listener != null) {
							listener.onSuccess(info);
						}
						// Intent intent = new Intent();
						// ((BaseActivity) context).setResult(10, intent);
						// ((BaseActivity) context).finish();
					} else {
						return;
					}
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * �ҵĹ�Ӧ ɾ����ť
	 */
	public void editDelete(String intentionID) {

		loadingBar.setVisibility(View.VISIBLE);
		loadingBar.setProgressInfo("���ڼ�����...");
		loadingBar.start();

		JsonLoader loader = new JsonLoader(context);
		loader.setUrl(AppNetUrl.getMyProviderDetailDeleteUrl(intentionID));
		loader.setPostData(new JSONObject().toString());
		loader.addRequestHeader("token", token);
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType("application/json");

		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
				loadingBar.setProgressInfo("���ڽ���...");
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				loadingBar.setVisibility(View.GONE);
				loadingBar.stop();
				((BaseActivity) context).displayToast(arg2.toString());
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				RequestNetResultInfo info = MerchantCenterManager
						.getgetMyProviderDetailsEditComplete(arg1);
				if (null != info) {
					((BaseActivity) context).displayToast(info.getMsg());
					loadingBar.setVisibility(View.GONE);
					loadingBar.stop();
					if (info.getStatus() == 1) {
						Intent intent = new Intent();
						((BaseActivity) context).setResult(12, intent);
						((BaseActivity) context).finish();
					} else {
						return;
					}
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	// �޸ĺ��ʵ��
	public GoodsSourceInfo sourceInfo;

	/**
	 * �����ݱ��������ݿ�
	 */
	// protected void saveSQlDataBase() {
	//
	// GoodSSourceLoader loader = new GoodSSourceLoader();
	// loader.deleteAll();
	// loader.setSelect(sourceInfo);
	// loader.setInsert(sourceInfo);
	// loader.setLoaderListener(new LoaderListener() {
	// @Override
	// public void onProgress(Object arg0, long arg1, long arg2) {
	// Log.d("TAG", arg1 + "--onProgress--" + arg2);
	// }
	//
	// @Override
	// public void onError(Object arg0, int arg1, String arg2) {
	// Log.d("TAG", arg1 + "--onError--" + arg2);
	// }
	//
	// @Override
	// public void onCompelete(Object arg0, Object arg1) {
	// Log.d("TAG", "--onCompelete--" + arg0);
	// ((BaseActivity) context).finish();
	// }
	// });
	// DmallApplication.getDataStratey().startLoader(loader);
	// }

	private OnEditSuccessClickListener listener;

	public void setListener(OnEditSuccessClickListener listener) {
		this.listener = listener;
	}

	public interface OnEditSuccessClickListener {
		void onSuccess(RequestNetResultInfo info);
	}

	/**
	 * ��Ҫ���ж�������Ƿ�������Ϣ ��ȡ�������Ϣ
	 */
	public abstract boolean getInputGoodSInfo();

	@Override
	public void onSaveInstanceState(Bundle outState) {
	}

	@Override
	public void onDestroy() {
	}

}
