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
 * 编辑委托供应详情
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-14 上午11:31:34
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

	// 数据源
	private GoodsSourceInfo info;

	/**
	 * 加载数据
	 */
	private void initData() {
		info = (GoodsSourceInfo) getIntent().getSerializableExtra("key");
	}

	// 自定义标题
	private EventTopTitleView topTitleView;

	/**
	 * 标题栏
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
		topTitleView.setEventName("编辑委托供应");
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
	 * 加载内容
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_edit_delegation_details, null);

		win_content.addView(view);

		findViewById(view);
		updateViewData();
	}

	// 商品名称
	private EditText mEdtGoodSNam;
	// 商家名称
	private EditText mEdtMerchant;
	// 分类条目
	private TableRow mTbCategory;
	// 品牌条目mEdtMerchant
	private TableRow mTbBrandRow;
	// 数量
	private EditText mEdtWeight;

	// 完成按钮
	private TextView mBtnCompleted;

	// 进度
	private MyLoadingBar loadingBar;

	/**
	 * 找控件
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
		mBtnCompleted.setText("完成");
		mBtnCompleted.setOnClickListener(this);

		TextView mTextView = (TextView) view
				.findViewById(R.id.btn_cancel_modify);
		mTextView.setVisibility(View.GONE);

		loadingBar = (MyLoadingBar) view.findViewById(R.id.load);
	}

	/**
	 * 更新界面数据
	 */
	private void updateViewData() {
		if (null != info) {
			mEdtGoodSNam.setText(info.getGoodSName());
			mEdtMerchant.setText(info.getGoodSMerchant());
			mEdtWeight.setText(info.getGoodSWeight() + "吨");
		}
	}

	/**
	 * 点击事件
	 * 
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_commit_modify:// 完成
			editCompeleted();
			break;
		case R.id.tb_category:// 分类
goActivity(info, GoodSCategoryActivity.class);
			break;
		case R.id.tb_brand:// 品牌
			goActivity(info, GoodSBrandActivity.class);
			break;

		default:
			break;
		}
	}

	/**
	 * 完成
	 */
	private void editCompeleted() {
		String goodSName = mEdtGoodSNam.getText().toString().trim();
		if (TextUtils.isEmpty(goodSName)) {
			displayToast("商品名称不能为空");
			return;
		}
		String merchaseName = mEdtMerchant.getText().toString().trim();
		if (TextUtils.isEmpty(merchaseName)) {
			displayToast("商家名称不能为空");
			return;
		}
		String weight = mEdtWeight.getText().toString().trim();
		if (TextUtils.isEmpty(weight)) {
			displayToast("数量不能为空");
			return;
		}

		weight = Utils.getNumberOfString(weight);
//		displayToast("weight------" + weight);

		saveModefied(goodSName, merchaseName, weight);

	}

	/**
	 * 保存修改
	 * 
	 * @param goodSName
	 * @param merchaseName
	 * @param weight
	 */
	private void saveModefied(final String goodSName,
			final String merchaseName, final String weight) {
		loadingBar.setVisibility(View.VISIBLE);
		loadingBar.setProgressInfo("正在加载中...");
		loadingBar.start();

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getAttentionDetailUrl());
		loader.setPostData(new JSONObject().toString());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setLoaderListener(new LoaderListener() {
			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
				// loadingBar.setProgressInfo("正在解析...");
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
					displayToast(info.getMsg() + "成功");
				} else {
					displayToast(info.getMsg() + "失败");
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * 返回上一级
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
