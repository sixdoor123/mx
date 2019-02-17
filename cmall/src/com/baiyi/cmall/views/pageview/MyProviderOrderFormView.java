package com.baiyi.cmall.views.pageview;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.user.merchant.MyProviderOrderDetailsActivity;
import com.baiyi.cmall.adapter.ProFormAdapter;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.pageviews.BasePageView;
import com.baiyi.cmall.pageviews.BaseScrollViewPageView;
import com.baiyi.cmall.request.manager.MerchantCenterManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.MobileStateUtils;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.MyLinearLayout;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase;
import com.baiyi.cmall.views.pulldownview.PullToRefreshListView;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase.Mode;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase.OnLastItemVisibleListener;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase.OnRefreshListener;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * �ҵĶ���������ҳ��
 * 
 * @author sunxy
 * 
 */
public class MyProviderOrderFormView extends BasePageView implements
		OnItemClickListener {

	// ����Դ
	private ArrayList<GoodsSourceInfo> datas;
	// �û��ɣ�
	private String companyID;
	// ״̬-����
	private String orderState;
	private Context context;

	// �б�����
	private int total;
	// �ҵĶ�����������
	private ProFormAdapter formAdapter;
	private PullToRefreshListView listView;

	// ���Բ��֣�����������ݣ�Ȼ�󽫴˲��ָ�����ҳ��
	private MyLinearLayout layout;
	private String token;

	// ��ǰ��ʾ��ҳ��
	public int pageIndex = 1;

	public MyProviderOrderFormView(Context context, String companyID,
			String orderState) {
		super(context);

		this.context = context;
		this.companyID = companyID;
		this.orderState = orderState;
		// layout = new MyLinearLayout(context);
		// addView(layout);

		setContentList();

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		pageIndex = 1;
		initData();
	}

	private MyLoadingBar loadingBar;

	// ����״̬
	// ֻ���ڵ�һ�ν���ʱ��ʾ������
	private boolean barFlag = true;

	/**
	 * ��ʼ������
	 */
	private void initData() {

		token = XmlUtils.getInstence(context).getXmlStringValue("token");
		if (TextUtils.isEmpty(token)) {
			listView.removeFooterView();
			((BaseActivity) context).displayToast("�û�δ��¼,���¼������");
			return;
		}
		if (barFlag) {
			loadingBar = new MyLoadingBar(context);
			loadingBar.setProgressInfo("������,���Ժ�...");
			loadingBar.setPadding(0,
					Config.getInstance().getScreenHeight(context) / 3, 0, 0);
			loadingBar.start();
			addView(loadingBar);
		}

		JsonLoader loader = new JsonLoader(context);
		loader.setUrl(AppNetUrl.getProviderOrderListUrl(companyID, orderState,
				pageIndex, Config.LIST_ITEM_COUNT));
		loader.addRequestHeader("token", token);
		loader.setPostData(MerchantCenterManager.getProviderPostData());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				((BaseActivity) context).displayToast(arg2);
				if (barFlag) {
					removeView(loadingBar);
					loadingBar.stop();
				}
				listView.removeFooterView();
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				datas = MerchantCenterManager.getgetMyProviderOrderListInfo(
						context, arg1);
				if (barFlag) {
					removeView(loadingBar);
					loadingBar.stop();
				}
				showList();
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * ��ʾ����
	 */
	protected void showList() {

		if (null != listView) {
			listView.onRefreshComplete();
		}
		listView.removeFooterView();
		if (Utils.isStringEmpty(datas)) {
			return;
		}
		((BaseActivity) context).setLisViewSize(datas.size());
		if (1 == pageIndex) {
			formAdapter.setData(datas);
		} else {
			formAdapter.addData(datas);
		}
	}

	private View view;

	/**
	 * ��������Դ������������
	 */
	private void setContentList() {

		/**
		 * �ж����򵥵�״̬�������ȫ��������ʾȫ����Ϣ �������ȫ��������Ϣ����������״̬������ʾ
		 */
		/*
		 * �����m����
		 */

		view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.list_have_divider1, null);
		listView = (PullToRefreshListView) view
				.findViewById(R.id.lst_industry_trends);
		formAdapter = new ProFormAdapter(context);
		listView.setAdapter(formAdapter);
		listView.setOnItemClickListener(this);

		listView.SetFooterCanUse(true);
		listView.addFootView();
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				barFlag = false;
				listView.SetFooterCanUse(true);
				// ֻ������ˢ��
				pageIndex = 1;
				initData();
			}
		});

		listView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				listView.addFootView();
				if (((BaseActivity) context).isListViewBottom()) {
					listView.SetFooterCanUse(false);
					listView.onRefreshComplete();
					listView.removeFooterView();
					return;
				}
				barFlag = true;
				pageIndex += 1;
				initData();
			}
		});
		addView(view, -1, -1);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		GoodsSourceInfo info = (GoodsSourceInfo) parent
				.getItemAtPosition(position);

		int state = 0;

		if (Integer.parseInt(orderState) == -1) {
			state = 0;
		} else if (Integer.parseInt(orderState) == 1) {
			state = 1;
		} else if (Integer.parseInt(orderState) == 2) {
			state = 2;
		} else if (Integer.parseInt(orderState) == 3) {
			state = 3;
		}

		// ������Ӧ�����������
		((BaseActivity) context).goActivity(info,
				MyProviderOrderDetailsActivity.class, state);

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	}

	@Override
	public void onDestroy() {
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
			initData();
	}

}
