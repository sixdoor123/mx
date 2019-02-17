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
import com.baiyi.cmall.request.manager.MerchantCenterManager;
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
 * 
 * 编辑物流信息
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-23 下午4:23:26
 */
public class EditLogisticsDetailsActivity extends BaseActivity implements
		OnClickListener {

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);

		initData();
		initTitle();

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		win_content.removeAllViews();
		initProviderContent();
		loadData();
	}

	// 供应，存放详情
	private TableLayout proLayout;
	// 供应控制
	private LinearLayout proControl;

	/**
	 * 供应信息
	 */
	private void initProviderContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_pro_intention_layout, null);
		proLayout = (TableLayout) view.findViewById(R.id.tab_pro_detail);
		proControl = (LinearLayout) view.findViewById(R.id.lin_pro_control);
		proControl.setVisibility(View.GONE);
		win_content.addView(view);
	}

	/**
	 * 加载数据
	 */
	private void loadData() {
		final MyLoadingBar loadingBar = new MyLoadingBar(this);
		loadingBar.setProgressInfo("正在加载中...");
		loadingBar.setPadding(0, getScreenHeight() / 3, 0, 0);
		loadingBar.start();
		win_content.addView(loadingBar);

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getLogisticsDetailsUrl(id));
		loader.addRequestHeader("token", CmallApplication.getUserInfo()
				.getToken());
		loader.setPostData(new JSONObject().toString());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
				loadingBar.setProgressInfo("正在解析...");
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				displayToast(arg2.toString());
				win_content.removeView(loadingBar);
				loadingBar.stop();
				finish();
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				info = UserLogisticsManager.getLogisticsDetailsInfo(arg1);
				win_content.removeView(loadingBar);
				loadingBar.stop();
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
	 * 更新界面
	 */
	protected void updataView() {
		ArrayList<IndutryArgumentInfo> infos = DataUtils
				.getDelegationLogisticsDetail(info);
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

	private GoodsSourceInfo sourceInfo;
	private GoodsSourceInfo info;
	private String id;

	/**
	 * 数据
	 */
	private void initData() {
		sourceInfo = (GoodsSourceInfo) getIntent().getSerializableExtra("key");
		id = sourceInfo.getGoodSID();
	}

	// 自定义标题栏
	protected EventTopTitleView topTitleView = null;

	/**
	 * 标题栏
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("委托物流");
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
						EditLogisticsDetailsActivity.this);
			}
		});
		win_title.addView(topTitleView);
	}

	// 编辑按钮
	private TextView mBtnEditLogistics;

	/**
	 * 初始化内容
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_button, null);
		win_content.addView(view);
		// 找控件
		findViewById(view);
	}

	// 编辑按钮
	private TextView mBtnEditDetails;
	// 删除
	private TextView mBtnDelete;

	/**
	 * 找控件
	 * 
	 * @param view
	 */
	private void findViewById(View view) {
		mBtnEditDetails = (TextView) view.findViewById(R.id.btn_commit_modify);
		if (2 == info.getState() || 3 == info.getState()) {
			mBtnEditDetails.setVisibility(View.GONE);
		} else {
			mBtnEditDetails.setText("编辑");
			mBtnEditDetails.setOnClickListener(this);
		}
		mBtnDelete = (TextView) view.findViewById(R.id.btn_cancel_modify);
		mBtnDelete.setText("删除");
		mBtnDelete.setOnClickListener(this);
	}

	/**
	 * 监听事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_commit_modify:
			goActivity(info, EditModifyLogisticsActivity.class);
			break;
		case R.id.btn_cancel_modify:// 删除
			delete();
			break;
		}
	}

	/**
	 * 请求删除操作
	 */
	private void delete() {
		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getDeleteDelegationLogUril(id));
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
