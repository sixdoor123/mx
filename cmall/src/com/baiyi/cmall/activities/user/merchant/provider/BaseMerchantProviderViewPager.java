package com.baiyi.cmall.activities.user.merchant.provider;

import java.util.ArrayList;

import org.json.JSONObject;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.home.itemview.DividerItemDecoration;
import com.baiyi.cmall.activities.main.provider.ProvideListAdapter;
import com.baiyi.cmall.activities.main.total.TotalUtils;
import com.baiyi.cmall.activities.user.buyer.HintUtils;
import com.baiyi.cmall.activities.user.login.ExitLogin;
import com.baiyi.cmall.activities.user.login.MemberLoginActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.pageviews.BasePageView;
import com.baiyi.cmall.request.manager.MerchantCenterManager;
import com.baiyi.cmall.utils.MobileStateUtils;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase;
import com.baiyi.cmall.views.pulldownview.PullToRefreshListView;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase.Mode;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase.OnLastItemVisibleListener;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase.OnRefreshListener;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView.LoadingListener;
import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.R;
import com.baiyi.cmall.R.id;

/**
 * ���ǹ�Ӧ��-�ҵĹ�Ӧ��ViewPager����
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-19 ����11:14:39
 */
public abstract class BaseMerchantProviderViewPager extends BasePageView implements OnItemClickListener {
	//
	// ������
	public Context context;
	// ����Դ
	private ArrayList<GoodsSourceInfo> datas;
	// ��ǰ��ʾ��ҳ��
	public int pageIndex = 1;

	// ״̬
	public String orderState;
	// �̼�Id
	public String companyId;

	// token ��֤
	private String token;
	// ֻ���ڵ�һ�ν���ʱ��ʾ������
	private boolean barFlag = true;

	public BaseMerchantProviderViewPager(Context context, String orderState, String companyId) {
		super(context);
		this.context = context;
		this.orderState = orderState;
		this.companyId = companyId;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
		initData();
	}

	/**
	 * ��������
	 * 
	 * @parmer state 0 :����ˢ�� 1 : ����ˢ��
	 */
	public void initData() {
		token = CmallApplication.getUserInfo().getToken();
		if (TextUtils.isEmpty(XmlUtils.getInstence(context).getXmlStringValue("token"))) {
			// ((BaseActivity) context).displayToast("�û�δ��¼,���¼������");
			((BaseActivity) context).goActivity(MemberLoginActivity.class);
			return;
		}

		if (barFlag) {
			loadingBar.setVisibility(View.VISIBLE);
			loadingBar.setProgressInfo("���ڼ�����...");
			loadingBar.start();
		}

		JsonLoader loader = new JsonLoader(context);
		loader.addRequestHeader("token", token);
		loader.setUrl(getMerchantProUrl(loadingBar));
		loader.setPostData(new JSONObject().toString());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
				loadingBar.setProgressInfo("���ڽ���");
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				if (barFlag) {
					loadingBar.setVisibility(View.GONE);
					loadingBar.stop();
				}
				((BaseActivity) context).displayToast(arg2.toString());
			}

			@Override
			public void onCompelete(Object arg0, Object result) {
				GoodsSourceInfo info = MerchantCenterManager.getPreIntentationOrderList(context, result);
				RequestNetResultInfo resultInfo = info.getResultInfo();
				if (barFlag) {
					loadingBar.setVisibility(View.GONE);
					loadingBar.stop();
				}
				
				// ��ʾ��������
				if (TotalUtils.getIntence().getTotal() <= 0 && pageIndex == 1) {
					mTxtNoData.setVisibility(View.VISIBLE);
				}else {
					mTxtNoData.setVisibility(View.GONE);
				}
				
				if (null != resultInfo) {
					if (1 != resultInfo.getStatus()) {
						if (NumEntity.PLEASE_LOG.equals(resultInfo.getMsg())) {
							((BaseActivity) context).displayToast(resultInfo.getMsg());
							ExitLogin.getInstence(context).cleer();
							return;
						} else {
							((BaseActivity) context).displayToast(resultInfo.getMsg());
							return;
						}
					}
				}
				datas = info.getPurInfos();
				updataView();
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * �õ�����·��
	 * 
	 * @param loadingBar2
	 * 
	 * @return
	 */
	public abstract String getMerchantProUrl(MyLoadingBar loadingBar2);

	/**
	 * ���½�����ʾ
	 */
	protected void updataView() {
		if (null != mRecyclerView) {
			if (1 == pageIndex) {
				mRecyclerView.refreshComplete();
			} else {
				mRecyclerView.loadMoreComplete();
			}
		}
		// ���ݷ���
		if (1 == pageIndex) {
			adapter.setDatas(datas);
		} else {
			adapter.addDatas(datas);
		}
	}

	// ����
	private MyLoadingBar loadingBar;
	private XRecyclerView mRecyclerView = null;
	// ������
	private MerchantProviderAdapter adapter;
	private View view;
	
	private TextView mTxtNoData = null; 

	/**
	 * ���ؽ���
	 */
	private void initView() {
		view = ContextUtil.getLayoutInflater(context)//
				.inflate(R.layout.activity_shop_bus, null);
		addView(view);

		loadingBar = (MyLoadingBar) view.findViewById(R.id.loading);
		mRecyclerView = (XRecyclerView) view.findViewById(R.id.recycleview);
		
		mTxtNoData = (TextView) view.findViewById(R.id.no_data);
		
		// ���ò��ֹ�����
		LinearLayoutManager layout = new LinearLayoutManager(context);
		mRecyclerView.setLayoutManager(layout);

		// ����Item���ӡ��Ƴ�����
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		// ��ӷָ���
		mRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
		// ����adapter
		adapter = new MerchantProviderAdapter(context);
		mRecyclerView.setAdapter(adapter);
		// adapter.setDatas(datas);

		// ��Ŀ����¼�
		// adapter.setItemClickListener(this);

		mRecyclerView.setLoadingListener(new LoadingListener() {

			@Override
			public void onRefresh() {
				barFlag = false;
				// ֻ������ˢ��
				pageIndex = 1;
				initData();
			}

			@Override
			public void onLoadMore() {
				barFlag = false;
				pageIndex += 1;
				initData();
			}
		});

	}

	/**
	 * ��Ŀ����¼�
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		GoodsSourceInfo info = (GoodsSourceInfo) parent.getItemAtPosition(position);
		((BaseActivity) context).goActivity(info, PurchaseContentDetailActivity.class);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

	}

	@Override
	public void onDestroy() {

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

	}
}
