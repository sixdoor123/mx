package com.baiyi.cmall.activities.user.delegation;

import java.util.ArrayList;

import org.json.JSONObject;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.IndutryArgumentInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.request.manager.DelegationManager;
import com.baiyi.cmall.request.manager.UserLogisticsManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.DataUtils;
import com.baiyi.cmall.utils.MsgItemUtil;
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
 * �û�����-�ҵ�ί��-ί�й�Ӧ����
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-9 ����1:48:04
 */
public class DelegationProviderDetailsActivity extends BaseActivity implements
		OnClickListener {

	// ����
	private MyLoadingBar loadingBar;
	// id
	private String id;

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);

		initTitle();
		init();
	}

	private GoodsSourceInfo sourceInfo;

	private void init() {
		sourceInfo = (GoodsSourceInfo) getIntent().getSerializableExtra("key");
		id = sourceInfo.getGoodSID();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		win_content.removeAllViews();
		initProviderContent();
		initData();
	}

	// �Զ������
	private EventTopTitleView topTitleView;

	/**
	 * ������
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("ί�й�Ӧ����");
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
						DelegationProviderDetailsActivity.this);
			}
		});
		win_title.addView(topTitleView);
	}

	// ��Ӧ���������
	private TableLayout proLayout;
	// ��Ӧ����
	private LinearLayout proControl;

	/**
	 * ��Ӧ��Ϣ
	 */
	private void initProviderContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_pro_intention_layout, null);
		proLayout = (TableLayout) view.findViewById(R.id.tab_pro_detail);
		proControl = (LinearLayout) view.findViewById(R.id.lin_pro_control);
		proControl.setVisibility(View.GONE);
		win_content.addView(view);
	}

	// ����
	private GoodsSourceInfo info;

	/**
	 * ��ȡ��������
	 */
	private void initData() {
		loadingBar = new MyLoadingBar(this);
		win_content.addView(loadingBar);
		loadingBar.setPadding(0, getScreenHeight() / 3, 0, 0);
		loadingBar.setProgressInfo("���ڼ�����...");
		loadingBar.start();

		JsonLoader loader = new JsonLoader(this);
		loader.addRequestHeader("token", CmallApplication.getUserInfo()
				.getToken());
		loader.setUrl(AppNetUrl.getDelegationProDetailsDetailUrl(id));
		loader.setPostData(new JSONObject().toString());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
				loadingBar.setProgressInfo("���ڽ���...");
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				displayToast(arg2);
				loadingBar.setVisibility(View.GONE);
				loadingBar.stop();
				finish();
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				info = UserLogisticsManager
						.getDelegationProDetailsResultInfo(arg1);

				win_content.removeView(loadingBar);
				loadingBar.stop();
				// initContent();
				updataView();
				if (null != info) {
					if (-9 != info.getState()) {
						initContent();
					}
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	private View line;

	/**
	 * ���½���
	 */
	protected void updataView() {
		ArrayList<IndutryArgumentInfo> infos = DataUtils
				.getDelegationPurDetail(info);
		for (int i = 0; i < infos.size(); i++) {
			line = ContextUtil.getLayoutInflater(this).inflate(
					R.layout.view_line_1, null);

			TableRow row = (TableRow) ContextUtil.getLayoutInflater(this)
					.inflate(R.layout.tab_row_hang, null);
			TextView name = (TextView) row.findViewById(R.id.tv_jian);
			TextView value = (TextView) row.findViewById(R.id.tv_zhi);
			name.setText(infos.get(i).getArgNmae());
			value.setText(infos.get(i).getArgValue());
			proLayout.addView(line, Config.getInstance().getScreenWidth(this),
					1);
			proLayout.addView(row);
		}
	}

	/**
	 * ��ʼ������
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_button, null);
		win_content.addView(view);
		// �ҿؼ�
		findViewById(view);
	}

	// �༭��ť
	private TextView mBtnEditDetails;
	// ɾ��
	private TextView mBtnDelete;

	/**
	 * �ҿؼ�
	 * 
	 * @param view
	 */
	private void findViewById(View view) {
		mBtnEditDetails = (TextView) view.findViewById(R.id.btn_commit_modify);
		if (2 == info.getState() || 3 == info.getState()) {
			mBtnEditDetails.setVisibility(View.GONE);
		} else {
			mBtnEditDetails.setText("�༭");
			mBtnEditDetails.setOnClickListener(this);
		}
		mBtnDelete = (TextView) view.findViewById(R.id.btn_cancel_modify);
		mBtnDelete.setText("ɾ��");
		mBtnDelete.setOnClickListener(this);
	}

	/**
	 * ����¼� EditDelegationProviderDetailsActivity
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_commit_modify:
			goActivity(info, EditDelegationPurchaseDetailsActivity.class, 1);
			break;
		case R.id.btn_cancel_modify:// ɾ��
			delete();
			break;
		}
	}

	/**
	 * ����ɾ������
	 */
	private void delete() {
		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getDeleteDelegationProUril(id));
		loader.setPostData(new JSONObject().toString());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.addRequestHeader("token", CmallApplication.getUserInfo()
				.getToken());
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				displayToast(arg2);
			}

			@SuppressWarnings("unused")
			@Override
			public void onCompelete(Object arg0, Object arg1) {
				RequestNetResultInfo info = DelegationManager
						.getCancelAttentionResultInfo(arg1, -1);
				if (null != info) {
					displayToast(info.getMsg());
					if (1 == info.getStatus()) {
						finish();
					} else {
						return;
					}
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}
}
