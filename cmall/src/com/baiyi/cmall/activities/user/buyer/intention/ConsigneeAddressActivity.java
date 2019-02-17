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
import com.baiyi.cmall.NumEntity;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.adapter.ConsigneeAdapter;
import com.baiyi.cmall.adapter.ConsigneeAdapter.OnEditClickListener;
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
import com.baiyi.cmall.activities.user.buyer.intention.AddNewConsigneeActivity;
import com.baiyi.cmall.activities.user.buyer.intention.ConsigneeAddressActivity;
import com.baiyi.cmall.activities.user.buyer.intention.EditConsigneeActivity;

/**
 * �ջ��˵�ַѡ�����
 * 
 * @author lizl
 * 
 */
public class ConsigneeAddressActivity extends BaseActivity implements
		OnItemClickListener {

	// �ջ�����Ϣ�б�
	private ArrayList<OrderEntity> orderListInfo;
	// ���ListView��View
	private View view;
	private QQListView listView;
	private ConsigneeAdapter consigneeAdapter;

	// �Ñ�ID��ȡ
	private String userId;
	private String token;

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

	// ������ַ
	private TextView mBtnAddAddress;
	// ��������İ�ť
	private TextView mBtnNo;

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
		jsonLoader.setUrl(AppNetUrl.getConsigneeUrl(userId, 1, 10));
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

				JSONArray array = (JSONArray) result;
				orderListInfo = MyPurAttentionManager.getConsigneeList(array);
				// ���������ؽ��
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);

				if (1 == info.getStatus()) {

					stopProgress();

					consigneeAdapter.setData(orderListInfo);

					// ���������������Ƶ��ջ���Ϣ�������Ͳ���ʾ������ť���޷�����µ��ջ���Ϣ
					if (orderListInfo.size() >= 8) {

						win_menu.setVisibility(View.GONE);
					} else {
						win_menu.setVisibility(View.VISIBLE);
					}
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
		topTitleView.setEventName("�ջ���ַ");
		win_title.addView(topTitleView);

		// �ڴ˲���ʾ���ఴť�����Խ�������
		FrameLayout frameLayout = (FrameLayout) findViewById(R.id.msg_layout);
		frameLayout.setVisibility(View.INVISIBLE);
	}

	/**
	 * ����������
	 */
	private void initAdapter() {

		consigneeAdapter = new ConsigneeAdapter(this);
		listView.setAdapter(consigneeAdapter);

		/**
		 * ���ñ༭��ť�ļ����¼� ����������
		 */
		consigneeAdapter.setListener(new OnEditClickListener() {

			@Override
			public void onBtnEdit(OrderEntity entity) {

				Intent intent = new Intent(ConsigneeAddressActivity.this,
						EditConsigneeActivity.class);
				intent.putExtra("info", entity);
				startActivityForResult(intent, 1);

			}

			@Override
			public void onBtnDelete(OrderEntity entity) {

				// TODO �ڴ���ɾ������
				deleteConsigneeData(entity.getId());
			}
		});

	}

	/**
	 * ɾ���ջ�����Ϣ
	 * 
	 * @param consigneeId
	 */
	private void deleteConsigneeData(String consigneeId) {

		loaderProgress();

		JsonLoader jsonLoader = new JsonLoader(this);
		jsonLoader.setUrl(AppNetUrl.getDeleteConsigneeUrl(consigneeId));
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
		setResult(NumEntity.RESULT_CONSIGNEE, intent);
		finish();
	}

	/**
	 * ��������Ӱ�ť
	 */
	private void initFoot() {

		ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_form_foot, win_menu);

		mBtnNo = (TextView) findViewById(R.id.btn_commit);
		mBtnAddAddress = (TextView) findViewById(R.id.btn_cancel_form);
		mBtnNo.setVisibility(View.GONE);
		mBtnAddAddress.setText("������ַ");
		mBtnAddAddress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				goActivity(AddNewConsigneeActivity.class);
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

}