package com.baiyi.cmall.activities.user.buyer;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.total.TotalUtils;
import com.baiyi.cmall.activities.user.login.ExitLogin;
import com.baiyi.cmall.adapter.MyPurListAdapter;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.pageviews.BasePageView;
import com.baiyi.cmall.request.manager.MyPurchaseManager;
import com.baiyi.cmall.request.manager.NullJsonData;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.JsonParse_User;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.LoadingBar;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase;
import com.baiyi.cmall.views.pulldownview.PullToRefreshListView;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase.OnLastItemVisibleListener;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase.OnRefreshListener;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView.LoadingListener;

/**
 * ���ǲɹ��̡����ҵĲɹ������ĸ�ViewPager��ҳ����
 * 
 * @author lizl
 * 
 */
public class PurchaseView extends BasePageView {

	// ����Դ
	private ArrayList<GoodsSourceInfo> goodsSourceInfos;
	private Context context;
	// �ҵĲɹ�����������
	private XRecyclerView listView;
	// ������
	private MyPurListAdapter adapter;
	// ������
	private LoadingBar progressBar;
	// ���״��
	private int state;
	// ҳ��
	private int pagerIndex = 1;
	// ÿҳ����
	private int pagerSize = Config.LIST_ITEM_COUNT;
	// �Ñ�ID��ȡ
	private String userId;
	private String token;

	/**
	 * 
	 * @param context
	 *            ������
	 * @param state
	 *            ���״̬
	 * @param pagerIndex
	 *            ҳ��
	 * @param pagerSize
	 *            ÿҳ��ʾ����
	 */
	public PurchaseView(Context context, int state) {
		super(context);

		this.context = context;
		this.state = state;

		intUserId();
		initContent();
	}

	/**
	 * ��ʼ���û�ID
	 */
	private void intUserId() {

		userId = CmallApplication.getUserInfo().getUserID();
		token = CmallApplication.getUserInfo().getToken();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loaderProgress();
		initData();
	}

	private TextView mTxtNoData = null;
	/**
	 * ��ʼ������
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_shop_bus, null);
		listView = (XRecyclerView) view.findViewById(R.id.recycleview);
		mTxtNoData = (TextView) view.findViewById(R.id.no_data);

		LinearLayoutManager f = new LinearLayoutManager(context);
		f.setOrientation(LinearLayoutManager.VERTICAL);
		listView.setLayoutManager(f);

		listView.setLoadingListener(new LoadingListener() {
			// ���¼�������
			@Override
			public void onRefresh() {
				pagerIndex = 1;
				initData();
			}

			// �ϻ�������µ�����
			@Override
			public void onLoadMore() {

				pagerIndex++;
				initData();
			}
		});
		this.addView(view);

		adapter = new MyPurListAdapter(context);
		listView.setAdapter(adapter);
	}

	/**
	 * ���ؽ�����
	 */
	private void loaderProgress() {

		progressBar = new LoadingBar(context);
		progressBar.start();
	}

	/**
	 * ����������
	 */
	private void stopProgress() {

		if (null != progressBar) {
			progressBar.stop();
		}
	}

	/**
	 * ������������
	 */
	private void initData() {

		JsonLoader loader = new JsonLoader(context);

		// �ĸ�������-----�û�Id�����״̬��ҳ�롢ÿ��ҳ��
		loader.setUrl(AppNetUrl.getMyPurchaseDetailUrl(userId, state,
				pagerIndex, pagerSize));
		loader.setPostData(NullJsonData.getPostData());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.addRequestHeader("token", token);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum,
					long totalByteNum) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {
				listView.refreshComplete();
				listView.loadMoreComplete();
				stopProgress();
				((BaseActivity) context).displayToast(errorMessage);
			}

			@Override
			public void onCompelete(Object tag, Object result) {

				stopProgress();
				JSONArray array = (JSONArray) result;

				// ����������ʧ�ܽ��
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);
				// ��û������ʱ������toast��ʾ��
				if (1 != info.getStatus()) {
					((BaseActivity) context).displayToast(info.getMsg());
					if (NumEntity.PLEASE_LOG.equals(info.getMsg())) {

						ExitLogin.getInstence(context).cleer();
						((BaseActivity) context).finish();
					}
					return;
				}
				
				// ������������
				goodsSourceInfos = MyPurchaseManager.getMyPurchaseList(array);

				// ��ʾ��������
				if (TotalUtils.getIntence().getTotal() <= 0 && pagerIndex == 1) {
					mTxtNoData.setVisibility(View.VISIBLE);
				}else {
					mTxtNoData.setVisibility(View.GONE);
				}


				if (pagerIndex == 1) {
					adapter.setDatas(goodsSourceInfos);
					listView.refreshComplete();
				} else {
					adapter.addDatas(goodsSourceInfos);
					listView.loadMoreComplete();
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	}

	@Override
	public void onDestroy() {
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	}

}
