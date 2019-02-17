package com.baiyi.cmall.activities.user.buyer.form.product_order;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.total.TotalUtils;
import com.baiyi.cmall.activities.user.buyer.adapter.BaseRCAdapter.OnItemClickListeners;
import com.baiyi.cmall.activities.user.buyer.form.intention_order.IntentionOrderAdapter;
import com.baiyi.cmall.activities.user.buyer.form.intention_order.ObligationIntentionOrderActivity;
import com.baiyi.cmall.activities.user.buyer.form.intention_order.OtherIntentionOrderActivity;
import com.baiyi.cmall.activities.user.login.ExitLogin;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.OrderEntity;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.pageviews.BasePageView;
import com.baiyi.cmall.request.manager.MyPurFormManager;
import com.baiyi.cmall.request.manager.NullJsonData;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.JsonParse_User;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.LoadingBar;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.jcodecraeer.xrecyclerview.XRecyclerView.LoadingListener;

/**
 * ��Ҷ���������Ʒ������ȫ��/������/���ջ�/����ɣ�
 * 
 * @author lizl
 * 
 */
public class ProductOrderView extends BasePageView implements
		OnItemClickListeners {

	// ����Դ
	private ArrayList<OrderEntity> datas;
	private Context context;

	// �ҵĶ�����������
	private ProductOrderAdapter orderAdapter;
	private XRecyclerView listView;

	// ����״̬
	private int dealState;
	// �Ñ�ID��ȡ
	private String token;

	private int pageIndex = 1;
	// ������
	private LoadingBar progressBar;

	public ProductOrderView(Context context, int dealState) {
		super(context);

		this.context = context;
		this.dealState = dealState;

		intUserId();
		initContent();

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loaderProgress();
		loadNetData();
	}

	/**
	 * ��ʼ���û�ID
	 */
	private void intUserId() {
		token = CmallApplication.getUserInfo().getToken();

	}

	private TextView mTxtNoData = null;
	/**
	 * ��ʼ�����������ݣ�
	 */
	private void initContent() {

		orderAdapter = new ProductOrderAdapter(context);

		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_shop_bus, null);
		
		listView = (XRecyclerView) view.findViewById(R.id.recycleview);
		mTxtNoData = (TextView) view.findViewById(R.id.no_data);
		
		LinearLayoutManager f = new LinearLayoutManager(context);
		f.setOrientation(LinearLayoutManager.VERTICAL);
		listView.setLayoutManager(f);
		listView.setAdapter(orderAdapter);
		orderAdapter.setOnItemClickListener(this);

		listView.setLoadingListener(new LoadingListener() {

			@Override
			public void onRefresh() {
				pageIndex = 1;
				loadNetData();
			}

			@Override
			public void onLoadMore() {

				pageIndex++;
				loadNetData();
			}
		});
		this.addView(view);
	}

	/**
	 * ���ط�������������
	 */
	public void loadNetData() {

		JsonLoader jsonLoader = new JsonLoader(context);

		// ������---�û�ID������״̬��ҳ�롢ÿҳ����
		// ����״̬��TODO �ȴ��ж�

		jsonLoader.setUrl(AppNetUrl.getPurProductFormListUrl(dealState,
				pageIndex, Config.LIST_ITEM_COUNT));
		jsonLoader.setPostData(NullJsonData.getPostData());
		jsonLoader.setMethod(BaseNetLoder.Method_Post);
		jsonLoader.addRequestHeader("token", token);
		jsonLoader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
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
			public void onCompelete(Object arg0, Object result) {

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
				// �ҷ�������������
				datas = MyPurFormManager.getMyPurchaseProductFormList(array);

				// ��ʾ��������
				if (TotalUtils.getIntence().getTotal() <= 0 && pageIndex == 1) {
					mTxtNoData.setVisibility(View.VISIBLE);
				}else {
					mTxtNoData.setVisibility(View.GONE);
				}

				
				
				// ���ɹ�ʱ������������ʾ����
				if (pageIndex == 1) {
					// ˢ�µ�һҳ����
					orderAdapter.setDatas(datas);
					listView.refreshComplete();
				} else {
					// �����һҳ����
					orderAdapter.addDatas(datas);
					listView.loadMoreComplete();
				}

			}

		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	}

	@Override
	public void onDestroy() {
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		loadNetData();
	}

	@Override
	public void onItemClick(int position, View itemView) {

		OrderEntity info = orderAdapter.getDataList().get(position);

		/*
		 * ���ݲ�ͬ�Ķ���״̬��ת����ͬ�Ľ��� ״̬��1,12-������
		 */
		switch (info.getOrderState()) {
		case 1:
		case 12:
			((BaseActivity) context).goToActivity(
					ObligationProductOrderActivity.class,
					info.getOrderStateName(), info.getId());
			break;

		default:
			((BaseActivity) context).goToActivity(
					OtherProductOrderActivity.class, info.getOrderStateName(),
					info.getId());
			break;
		}

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
}
