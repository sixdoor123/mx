package com.baiyi.cmall.activities.user.merchant.product;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.home.itemview.DividerItemDecoration;
import com.baiyi.cmall.activities.main.total.TotalUtils;
import com.baiyi.cmall.activities.user.merchant.product.detail.MyProductDetailActivity;
import com.baiyi.cmall.adapter.MyProductAdapter;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.LoginInfo;
import com.baiyi.cmall.model.Blm;
import com.baiyi.cmall.model.RequestNetResultInfo;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.LoadingBar;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView.LoadingListener;
import com.jcodecraeer.xrecyclerview.port.OnRecycleViewItemClickListener;
import com.tencent.connect.avatar.b;

/**
 * �ҵĲ�Ʒ
 * 
 * @author sunxy
 */
public class MyProductActivity extends BaseActivity implements OnRecycleViewItemClickListener {

	private ArrayList<Blm> datas = null;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(false);
		initData();
		initTitle();
		initContent();
		loadData();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
	}

	private int pageIndex = 1;
	private String userId = null;

	/**
	 * ��ʼ������
	 */
	private void initData() {
		LoginInfo loginInfo = CmallApplication.getUserInfo();
		if (loginInfo != null) {
			userId = loginInfo.getUserID();
		}
	}

	private LoadingBar loadingBar = null;
	private boolean barFlag = true;

	/**
	 * ��������
	 */
	private void loadData() {
		if (barFlag) {
			loadingBar = new LoadingBar(this);
			loadingBar.start();
		}
		final JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getProductUrl(userId, pageIndex, Config.LIST_ITEM_COUNT));
		loader.setPostData(new JSONObject().toString());
		loader.addRequestHeader("token", token);
		loader.addRequestHeader("iscompany", iscompany);
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum, long totalByteNum) {
			}

			public void onError(Object tag, int responseCode, String errorMessage) {
				if (barFlag) {
					loadingBar.stop();
				}
				stopRefresh();
				displayToast(errorMessage);

			}

			@SuppressWarnings("unchecked")
			@Override
			public void onCompelete(Object tag, Object result) {
				@SuppressWarnings("rawtypes")
				RequestNetResultInfo info = ProductManager.getProductList(result);
				loadingBar.stop();
				stopRefresh();

				// ��ʾ��������
				if (TotalUtils.getIntence().getTotal() <= 0 && pageIndex == 1) {
					mTxtNoData.setVisibility(View.VISIBLE);
				} else {
					mTxtNoData.setVisibility(View.GONE);
				}

				Log.d("TAG", "result--" + result);
				if (null != info) {
					if (1 != info.getStatus()) {
						displayToast(info.getMsg());
						return;
					}

					datas = info.getDatas();
					updataView();
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * ˢ�½���
	 */
	protected void updataView() {

		if (1 == pageIndex) {
			adapter.setDatas(datas);
		} else {
			adapter.addDatas(datas);
		}
		if (Utils.isStringEmpty(datas)) {
			return;
		}
	}

	/**
	 * ֹͣ����
	 */
	private void stopRefresh() {
		if (mRecyclerView != null) {
			if (1 == pageIndex) {
				mRecyclerView.refreshComplete();
			} else {
				mRecyclerView.loadMoreComplete();
			}
		}
	}

	/**
	 * ������
	 */
	private void initTitle() {
		EventTopTitleView topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("�ҵĲ�Ʒ");
		win_title.addView(topTitleView);
	}

	/**
	 * ��ʼ������
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(this)//
				.inflate(R.layout.activity_shop_bus, null);
		win_content.addView(view);

		findViewById(view);
	}

	private XRecyclerView mRecyclerView = null;
	private MyProductAdapter<Blm> adapter = null;

	private TextView mTxtNoData = null;

	/**
	 * �ҿؼ�
	 * 
	 * @param view
	 */
	private void findViewById(View view) {
		mRecyclerView = (XRecyclerView) view.findViewById(R.id.recycleview);
		mTxtNoData = (TextView) view.findViewById(R.id.no_data);

		// ���ò��ֹ�����
		LinearLayoutManager layout = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(layout);

		// ����Item���ӡ��Ƴ�����
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		// ��ӷָ���
		mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
		// ����adapter
		adapter = new MyProductAdapter(this);
		mRecyclerView.setAdapter(adapter);

		adapter.setItemClickListener(this);
		
		mRecyclerView.setLoadingListener(new LoadingListener() {

			@Override
			public void onRefresh() {

				Log.d("TAG", "----onRefresh----" + pageIndex);
				barFlag = false;
				pageIndex = 1;
				loadData();
			}

			@Override
			public void onLoadMore() {
				barFlag = false;
				pageIndex++;
				Log.d("TAG", "----onLoadMore----" + pageIndex);
				loadData();
			}
		});

	}

	/**
	 * ��Ŀ����¼�
	 * 
	 * @param view
	 * @param position
	 */
	@Override
	public <T> void onItemClick(View view, int position, T t) {
		Blm blm = (Blm) t;
		goActivity(MyProductDetailActivity.class, blm.getId(), blm.getC4());
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 1) {
			pageIndex = 1;
			loadData();
		}
	}
}
