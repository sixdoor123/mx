package com.baiyi.cmall.activities.user.buyer.intention;

import java.util.ArrayList;
import org.json.JSONArray;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.base.BaseMsgActivity;
import com.baiyi.cmall.adapter.AppGridAdapt;
import com.baiyi.cmall.adapter.AppGridAdapt.OnItemPressListener;
import com.baiyi.cmall.entity.OrderEntity;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.request.manager.MyPurAttentionManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.JsonParse_User;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.baiyi.cmall.application.CmallApplication;
public class EditInvoiceActivity extends BaseMsgActivity implements
		OnClickListener, OnItemClickListener {

	// ʵ����(�������ͣ�����)
	private OrderEntity allEntity;
	// ʵ�� (��ǰҪ�༭������ʵ����)
	private OrderEntity invoiceEntity;

	// ��Ʊ��������
	private ArrayList<OrderEntity> contextDatas;
	// ��Ʊ��������
	private ArrayList<OrderEntity> typeDatas;

	// ̧ͷ
	private EditText mTvTitle;
	// ����
	private RadioGroup mRGroup;
	// ����
	private GridView mGvView;
	// �Ƿ�Ĭ�ϣ�TODO Ŀǰû�����ã�ÿһ�༭һ�Σ�����������ΪĬ��ֵ
	private CheckBox mCbIsDefault;
	// ȷ��
	private TextView mTvOk;

	// �Ñ�ID��ȡ.TOKEN
	private String userId;
	private String token;

	// ����id
	private String typeId;
	// ̧ͷ
	private String title;
	// ����
	private String contextString;
	// �Ƿ�Ĭ��
	private boolean isDefault;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);
		initTitle();
		initData();
		initView();
	}

	/**
	 * ��ʼ������
	 */
	private void initData() {

		userId = CmallApplication.getUserInfo().getUserID();
		token = CmallApplication.getUserInfo().getToken();

		invoiceEntity = (OrderEntity) getIntent().getSerializableExtra("info");
		allEntity = (OrderEntity) getIntent().getSerializableExtra("infos");
		contextDatas = allEntity.getContextList();
		typeDatas = allEntity.getTypeList();
		Log.d("TT-----��Ʊ", contextDatas.size() + "-----" + typeDatas.size());
		// Ĭ�ϵķ�Ʊ����
		contextString = invoiceEntity.getContext();
	}

	/**
	 * ��ʼ��������Ϣ
	 */
	private void initTitle() {
		EventTopTitleView topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("�༭��Ʊ��Ϣ");
		win_title.addView(topTitleView);

		// �ڴ˲���ʾ���ఴť�����Խ�������
		FrameLayout frameLayout = (FrameLayout) findViewById(R.id.msg_layout);
		frameLayout.setVisibility(View.INVISIBLE);
	}

	/**
	 * �������
	 */
	private void initView() {

		ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_add_invoice, win_content);
		mTvTitle = (EditText) findViewById(R.id.et_title);
		mRGroup = (RadioGroup) findViewById(R.id.rg_context);
		mGvView = (GridView) findViewById(R.id.grd_type);
		mCbIsDefault = (CheckBox) findViewById(R.id.rb_is_default);
		mTvOk = (TextView) findViewById(R.id.btn_set_ok);

		// ����Ĭ�ϵķ�Ʊ̧ͷ
		mTvTitle.setText(invoiceEntity.getTitle());
		// �����Ƿ�ΪĬ�Ϸ�Ʊ
		mCbIsDefault.setChecked(invoiceEntity.isDefault());
		if (invoiceEntity.isDefault()) {
			mCbIsDefault.setVisibility(View.GONE);
		}
		mTvOk.setOnClickListener(this);
		mGvView.setOnItemClickListener(this);

		// ���÷�Ʊ������
		setType();
		// ���÷�Ʊ������
		setContext();
	}

	// ��Ʊ����������
	private AppGridAdapt adapt;

	/**
	 * ���÷�Ʊ����
	 */
	private void setType() {

		adapt = new AppGridAdapt(this, typeDatas, invoiceEntity);
		mGvView.setAdapter(adapt);
		typeId = typeDatas.get(adapt.getSelectPosition()).getId();
	}

	/**
	 * ���÷�Ʊ�����б�
	 */
	@SuppressLint("ResourceAsColor")
	private void setContext() {
		for (int i = 0; i < contextDatas.size(); i++) {

			// ÿһ��RadioButton������
			String rbText = contextDatas.get(i).getContext();
			RadioButton button = (RadioButton) LayoutInflater.from(this)
					.inflate(R.layout.button_radio_button, null);

			button.setText(rbText);
			button.setId(Integer.valueOf(contextDatas.get(i).getId()));
			// ����Ĭ�ϵ����ݱ�ѡ��
			if (contextString.equals(rbText)) {

				button.setChecked(true);
			}
			mRGroup.addView(button, -1, -2);
		}

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.btn_set_ok:

			getData();
			break;
		default:
			break;
		}
	}

	/**
	 * ��ȡ����
	 */
	private void getData() {

		title = mTvTitle.getText().toString().trim();

		/**
		 * ��ǰѡ�е�Radiobutton
		 */
		RadioButton button = (RadioButton) mRGroup.findViewById(mRGroup
				.getCheckedRadioButtonId());
		contextString = button.getText().toString();
		isDefault = mCbIsDefault.isChecked();

		/*
		 * �ж��������Ϣ�Ƿ�ϸ�
		 */
		if (TextUtils.isEmpty(typeId)) {
			displayToast("��ѡ��Ʊ����");
			return;
		}
		if (TextUtils.isEmpty(title)) {
			displayToast("��Ʊ̧ͷ����Ϊ��");
			return;
		} else if (title.length() > 50) {
			displayToast("��Ʊ̧ͷ�������");
			return;
		}
		if (TextUtils.isEmpty(contextString)) {
			displayToast("��ѡ��Ʊ����");
			return;
		}

		saveData();
	}

	/**
	 * ��������
	 */
	private void saveData() {
		JsonLoader jsonLoader = new JsonLoader(this);
		jsonLoader.setUrl(AppNetUrl.getEditInvoiceUrl());

		if (TextUtils.isEmpty(typeId)) {
			typeId = invoiceEntity.getId();
		}
		jsonLoader.setPostData(MyPurAttentionManager.getEditInvoicePostData(
				userId, typeId, title, contextString, invoiceEntity.getId(),
				isDefault));
		jsonLoader.setMethod(BaseNetLoder.Method_Post);
		jsonLoader.addRequestHeader("token", token);
		jsonLoader.setType(BaseNetLoder.POST_DATA_Urlencoded);
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

				// ���������ؽ��
				RequestNetResultInfo info = JsonParse_User.getResultInfo(array);

				if (1 == info.getStatus()) {

					finish();
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(jsonLoader);
	}

	/**
	 * ��ȡ��Ʊ����
	 */
	@Override
	public void onItemClick(final AdapterView<?> parent, View view,
			final int position, long id) {
		adapt.onRefreshed();
		OrderEntity entity = (OrderEntity) parent.getItemAtPosition(position);
		typeId = entity.getId();
		adapt.setListener(new OnItemPressListener() {

			@Override
			public void onPress(LinearLayout l, ImageView i, TextView t, int p) {
				if (position == p) {
					l.setActivated(true);
					i.setActivated(true);
					t.setActivated(true);
				}
			}
		});
	}
}
