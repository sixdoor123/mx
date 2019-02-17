package com.baiyi.cmall.activities.main.provider;

import org.json.JSONObject;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main._public.GoodSBrandActivity;
import com.baiyi.cmall.activities.main._public.GoodSCategoryActivity;
import com.baiyi.cmall.activities.user.delegation.DelegationProviderDetailsActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.request.manager.UserLogisticsManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.MyLoadingBar;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * �༭ί�й�Ӧ����
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-14 ����11:31:34
 */
public class EditDelegationProviderDetailsActivity extends BaseActivity
		implements OnClickListener {

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(false);

		initTitle();
		initData();
		initContent();
	}

	// ����Դ
	private GoodsSourceInfo info;

	/**
	 * ��������
	 */
	private void initData() {
		info = (GoodsSourceInfo) getIntent().getSerializableExtra("key");
	}

	// �Զ������
	private EventTopTitleView topTitleView;

	/**
	 * ������
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("�༭ί�й�Ӧ");
		topTitleView.setTitleNewsOnclick(new TitleNewsOnclick() {

			@Override
			public void setTitleNewsOnclickLister(boolean isPop) {

				topTitleView.displayPoP(MsgItemUtil.Pop_Default_txt,
						MsgItemUtil.Pop_Default_img);

			}
		});
		topTitleView.setNewsPopItemOnclick(new NewsPopItemOnclick() {

			@Override
			public void setNewsPopItemOnclickLister(int state) {
				MsgItemUtil.onclickPopItem(state,
						EditDelegationProviderDetailsActivity.this);
			}
		});
		win_title.addView(topTitleView);
	}

	/**
	 * ��������
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_edit_delegation_details, null);

		win_content.addView(view);

		findViewById(view);
		updateViewData();
	}

	// ��Ʒ����
	private EditText mEdtGoodSNam;
	// �̼�����
	private EditText mEdtMerchant;
	// ������Ŀ
	private TableRow mTbCategory;
	// Ʒ����ĿmEdtMerchant
	private TableRow mTbBrandRow;
	// ����
	private EditText mEdtWeight;

	// ��ɰ�ť
	private TextView mBtnCompleted;

	// ����
	private MyLoadingBar loadingBar;

	/**
	 * �ҿؼ�
	 * 
	 * @param view
	 */
	private void findViewById(View view) {
		mEdtGoodSNam = (EditText) view.findViewById(R.id.edt_goods_name);
		mEdtMerchant = (EditText) view.findViewById(R.id.edt_merchant_name);
		mEdtWeight = (EditText) view.findViewById(R.id.edt_weight);

		mTbCategory = (TableRow) view.findViewById(R.id.tb_category);
		mTbBrandRow = (TableRow) view.findViewById(R.id.tb_brand);
		mTbCategory.setOnClickListener(this);
		mTbBrandRow.setOnClickListener(this);

		mBtnCompleted = (TextView) view.findViewById(R.id.btn_commit_modify);
		mBtnCompleted.setText("���");
		mBtnCompleted.setOnClickListener(this);

		TextView mTextView = (TextView) view
				.findViewById(R.id.btn_cancel_modify);
		mTextView.setVisibility(View.GONE);

		loadingBar = (MyLoadingBar) view.findViewById(R.id.load);
	}

	/**
	 * ���½�������
	 */
	private void updateViewData() {
		if (null != info) {
			mEdtGoodSNam.setText(info.getGoodSName());
			mEdtMerchant.setText(info.getGoodSMerchant());
			mEdtWeight.setText(info.getGoodSWeight() + "��");
		}
	}

	/**
	 * ����¼�
	 * 
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_commit_modify:// ���
			editCompeleted();
			break;
		case R.id.tb_category:// ����
goActivity(info, GoodSCategoryActivity.class);
			break;
		case R.id.tb_brand:// Ʒ��
			goActivity(info, GoodSBrandActivity.class);
			break;

		default:
			break;
		}
	}

	/**
	 * ���
	 */
	private void editCompeleted() {
		String goodSName = mEdtGoodSNam.getText().toString().trim();
		if (TextUtils.isEmpty(goodSName)) {
			displayToast("��Ʒ���Ʋ���Ϊ��");
			return;
		}
		String merchaseName = mEdtMerchant.getText().toString().trim();
		if (TextUtils.isEmpty(merchaseName)) {
			displayToast("�̼����Ʋ���Ϊ��");
			return;
		}
		String weight = mEdtWeight.getText().toString().trim();
		if (TextUtils.isEmpty(weight)) {
			displayToast("��������Ϊ��");
			return;
		}

		weight = Utils.getNumberOfString(weight);
//		displayToast("weight------" + weight);

		saveModefied(goodSName, merchaseName, weight);

	}

	/**
	 * �����޸�
	 * 
	 * @param goodSName
	 * @param merchaseName
	 * @param weight
	 */
	private void saveModefied(final String goodSName,
			final String merchaseName, final String weight) {
		loadingBar.setVisibility(View.VISIBLE);
		loadingBar.setProgressInfo("���ڼ�����...");
		loadingBar.start();

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getAttentionDetailUrl());
		loader.setPostData(new JSONObject().toString());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setLoaderListener(new LoaderListener() {
			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
				// loadingBar.setProgressInfo("���ڽ���...");
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				// loadingBar.setVisibility(View.GONE);
				// loadingBar.stop();
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				RequestNetResultInfo info = UserLogisticsManager
						.getEditDelegationProDetailsResultInfo(arg1);
				// loadingBar.setVisibility(View.GONE);
				// loadingBar.stop();
				if (1 == info.getStatus()) {
					backPreviousPage(goodSName, merchaseName, weight);
					displayToast(info.getMsg() + "�ɹ�");
				} else {
					displayToast(info.getMsg() + "ʧ��");
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * ������һ��
	 * 
	 * @param goodSName
	 * @param merchaseName
	 * @param weight
	 */
	private void backPreviousPage(String goodSName, String merchaseName,
			String weight) {
		Intent intent = new Intent(this,
				DelegationProviderDetailsActivity.class);
		intent.putExtra("goodSName", goodSName);
		intent.putExtra("merchaseName", merchaseName);
		intent.putExtra("weight", weight);
		startActivity(intent);
		finish();
	}
}
