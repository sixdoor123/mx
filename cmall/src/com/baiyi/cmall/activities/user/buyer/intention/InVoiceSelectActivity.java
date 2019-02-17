package com.baiyi.cmall.activities.user.buyer.intention;

import java.util.ArrayList;
import org.json.JSONArray;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.baiyi.cmall.Config;
import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.activities.base.BaseMsgActivity;
import com.baiyi.cmall.adapter.InvoiceAdapter;
import com.baiyi.cmall.adapter.InvoiceAdapter.OnInVoiceEditClickListener;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.OrderEntity;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.listitem.QQListView;
import com.baiyi.cmall.request.manager.MyPurAttentionManager;
import com.baiyi.cmall.request.manager.NullJsonData;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.JsonParse_User;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * ��Ʊ��Ϣѡ�����
 * 
 * @author lizl
 * 
 */
public class InVoiceSelectActivity extends BaseMsgActivity implements
		OnItemClickListener {

	// ������Ʊ��Ϣ
	private TextView mBtnAddInvoice;
	// ��������İ�ť
	private TextView mBtnNo;
	// ��Ʊ��Ϣ�б�
	private ArrayList<OrderEntity> orderListInfo;
	ArrayList<OrderEntity> contextDatas;
	ArrayList<OrderEntity> typeDatas;
	private View view;
	private QQListView listView;
	private InvoiceAdapter invoiceAdapter;

	private OrderEntity allEntity;
	int max;
	// �Ñ�ID��ȡ
	private String userId;
	private String token;
	private int pageIndex = 1;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(false);

		initTitle();
		intUserId();
		setContent();
		initAdapter();
		initNetData();
		initFoot();
	}

	/**
	 * ��ʼ���û�ID
	 */
	private void intUserId() {

		userId = CmallApplication.getUserInfo().getUserID();
		token = CmallApplication.getUserInfo().getToken();
	}

	/**
	 * ��ʼ���ջ����б�
	 */
	protected void setContent() {

		view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.list_cehua_infos, null);

		listView = (QQListView) view.findViewById(R.id.lst_qq);
		listView.setOnItemClickListener(this);

		win_content.addView(view);
	}

	/**
	 * ��������
	 */
	private void initNetData() {
		loaderProgress();
		JsonLoader jsonLoader = new JsonLoader(this);
		jsonLoader.setUrl(AppNetUrl.getInVoiceUrl(userId, pageIndex,
				Config.LIST_ITEM_COUNT));
		jsonLoader.setPostData(NullJsonData.getPostData());
		jsonLoader.setMethod(BaseNetLoder.Method_Post);
		jsonLoader.addRequestHeader("token", token);
		jsonLoader.addRequestHeader("iscompany", iscompany);
		jsonLoader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object tag, int responseCode,
					String errorMessage) {
				// listView.onRefreshComplete();
				stopProgress();
				displayToast(errorMessage);
			}

			@Override
			public void onCompelete(Object arg0, Object result) {
				// listView.onRefreshComplete();
				stopProgress();
				JSONArray array = (JSONArray) result;
				allEntity = MyPurAttentionManager.getInvoiceList(array);

				orderListInfo = allEntity.getInvoiceList();
				contextDatas = allEntity.getContextList();
				typeDatas = allEntity.getTypeList();
				max = allEntity.getMaxNum();

				invoiceAdapter.setData(orderListInfo, max);

				// ���������������Ƶķ�Ʊ�������Ͳ���ʾ������ť���޷�����µķ�Ʊ��Ϣ
				if (orderListInfo.size() >= max) {

					win_menu.setVisibility(View.GONE);
				} else {
					win_menu.setVisibility(View.VISIBLE);
				}

			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);
	}

	/**
	 * ��������
	 */
	protected void initAdapter() {

		invoiceAdapter = new InvoiceAdapter(this);
		listView.setAdapter(invoiceAdapter);

		/**
		 * ���ñ༭��ť�ļ����¼� ����������
		 */
		invoiceAdapter.setEditListener(new OnInVoiceEditClickListener() {

			@Override
			public void onBtnEdit(OrderEntity entity) {

				Intent intent = new Intent(InVoiceSelectActivity.this,
						EditInvoiceActivity.class);
				intent.putExtra("infos", allEntity);
				intent.putExtra("info", entity);
				startActivityForResult(intent, 1);
			}

			@Override
			public void onBtnDelete(OrderEntity entity) {

				// TODO �ڴ���ɾ������
				deleteInvoiceData(entity.getId());
			}
		});
	}

	/**
	 * ɾ����Ʊ��Ϣ
	 * 
	 * @param consigneeId
	 */
	private void deleteInvoiceData(String invoiceId) {

		loaderProgress();

		JsonLoader jsonLoader = new JsonLoader(this);
		jsonLoader.setUrl(AppNetUrl.getDeleteInvoiceUrl(invoiceId));
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
				displayToast(errorMessage);
			}

			@Override
			public void onCompelete(Object arg0, Object result) {

				stopProgress();
				JSONArray array = (JSONArray) result;
				// ���������ؽ��
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);
				if (1 == info.getStatus()) {

					initNetData();
					listView.turnToNormal();
				}

			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);
	}

	/**
	 * ��ʼ��������Ϣ
	 */
	private void initTitle() {
		EventTopTitleView topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("���÷�Ʊ��Ϣ");
		win_title.addView(topTitleView);

		// �ڴ˲���ʾ���ఴť�����Խ�������
		FrameLayout frameLayout = (FrameLayout) findViewById(R.id.msg_layout);
		frameLayout.setVisibility(View.INVISIBLE);
	}

	/**
	 * ��������Ӱ�ť
	 */
	private void initFoot() {

		ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_form_foot, win_menu);

		mBtnNo = (TextView) findViewById(R.id.btn_commit);
		mBtnAddInvoice = (TextView) findViewById(R.id.btn_cancel_form);
		mBtnNo.setVisibility(View.GONE);
		mBtnAddInvoice.setText("������Ʊ��Ϣ");
		mBtnAddInvoice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (null == allEntity) {
					return;
				}
				Intent intent = new Intent(InVoiceSelectActivity.this,
						AddInvoiceActivity.class);
				intent.putExtra("info", allEntity);
				startActivityForResult(intent, 1);
			}
		});

	}

	/**
	 * �ص���ˢ�½���
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		initNetData();

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		if (!listView.isClick) {
			listView.isClick = true;
			return;
		}

		OrderEntity entity = (OrderEntity) parent.getItemAtPosition(position);
		Intent intent = new Intent();
		intent.putExtra("info", entity);
		setResult(NumEntity.RESULT_INVOICE, intent);
		finish();
	}
}
