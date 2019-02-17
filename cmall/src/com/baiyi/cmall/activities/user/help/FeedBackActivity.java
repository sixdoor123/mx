package com.baiyi.cmall.activities.user.help;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.base.BaseMsgActivity;
import com.baiyi.cmall.activities.user.help.manager.HelperManager;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.LoginInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.LoadingBar;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 意见反馈
 * 
 * @author sunxy
 * 
 */
public class FeedBackActivity extends BaseMsgActivity implements
		OnClickListener {

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(false);
		initData();
		initTitle();
		initContent();
	}

	private String userID;

	/**
	 * 初始化数据
	 */
	private void initData() {
		LoginInfo info = CmallApplication.getUserInfo();
		if (null != info) {
			userID = info.getUserID();
		}
	}
	/**
	 * 标题栏
	 */
	private void initTitle() {
		topTitleView = new EventTopTitleView(this, true);
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
				MsgItemUtil.onclickPopItem(state, FeedBackActivity.this);
			}
		});
		topTitleView.setEventName("意见反馈");
		win_title.addView(topTitleView);
	}

	// 提交意见按钮
	private TextView mBtnSupplySugges;
	// 意见内容
	private EditText mEdtSuggestContent;
	// 手机号码
	private EditText mEdtPhoneNumber;

	/**
	 * 内容
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_feed_back_content, null);
		win_content.addView(view, getScreenWidth(), getScreenHeight());

		mBtnSupplySugges = (TextView) view
				.findViewById(R.id.btn_supply_suggest);
		mBtnSupplySugges.setOnClickListener(this);
		mEdtSuggestContent = (EditText) view
				.findViewById(R.id.edt_suggest_content);
		mEdtPhoneNumber = (EditText) view.findViewById(R.id.edt_phone_number);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_supply_suggest:
			supplySuggest();
			break;

		default:
			break;
		}
	}

	/**
	 * 提交意见按钮
	 */
	private void supplySuggest() {
		String content = mEdtSuggestContent.getText().toString().trim();
		String number = mEdtPhoneNumber.getText().toString().trim();
		if (TextUtils.isEmpty(content)) {
			displayToast("内容不能为空");
			return;
		}
		if (!TextViewUtil.isStringEmpty(number)) {
			if (!(Utils.isPhoneNum(number) && Utils.isPhoneNumberValid(number))) {
				displayToast("联系方式填写不正确");
				return;
			}
		}
		commit(content, number);
		}

		private LoadingBar loadingBar = null;

	private void commit(String content, String number) {
		loadingBar = new LoadingBar(this);
		loadingBar.start();

		JsonLoader loader = new JsonLoader(this);
		loader.addRequestHeader("token", token);
		loader.addRequestHeader("iscompany", iscompany);
		loader.setUrl(AppNetUrl.getFeedBackUrl());
		loader.setPostData(HelperManager.getFeedBackContent(userID, content, number));
		loader.setMethod(BaseNetLoder.Method_Post);
//		loader.setType("application/json");
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);

		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				displayToast(arg2);
				loadingBar.stop();
			}

			@Override
			public void onCompelete(Object arg0, Object arg1) {
				RequestNetResultInfo info = HelperManager.getResultInfo(arg1);
				loadingBar.stop();
				if (info != null) {
					displayToast(info.getMsg());
					if (1 != info.getStatus()) {
						return;
					}
					finish();
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}
}
