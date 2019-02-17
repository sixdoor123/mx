package com.baiyi.cmall.activities.user.login;

import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-3-10 下午2:38:20
 */
public class ReSettingPwdActivity extends BaseActivity implements
		OnClickListener {

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(false);

		initTitle();
		initContent();
	}

	// 密码输入框
	private EditText mEdtInputPwd;
	// 确认密码
	private EditText mEdtSurePwd;
	// 完成按钮
	private TextView mBtnComplete;

	/**
	 * 内容
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_reset_pwd, null);
		win_content.addView(view);

		mEdtInputPwd = (EditText) view.findViewById(R.id.edt_input_pwd);
		mEdtSurePwd = (EditText) view.findViewById(R.id.edt_sure_pwd);

		mBtnComplete = (TextView) view.findViewById(R.id.btn_complete);
		mBtnComplete.setOnClickListener(this);
	}

	private EventTopTitleView topTitleView;

	/**
	 * 初始化标题栏
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
				MsgItemUtil.onclickPopItem(state, ReSettingPwdActivity.this);
			}
		});
		topTitleView.setEventName("找回密码");
		win_title.addView(topTitleView);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_complete:
			complete();
			break;

		default:
			break;
		}
	}

	/**
	 * 完成按钮
	 */
	private void complete() {
		String pwd = mEdtInputPwd.getText().toString().trim();
		String surePwd = mEdtSurePwd.getText().toString().trim();
		if (TextUtils.isEmpty(pwd)) {
			displayToast("密码不能为空");
			return;
		}
		if (TextUtils.isEmpty(surePwd)) {
			displayToast("确认密码不能为空");
			return;
		}

		if (!pwd.equals(surePwd)) {
			displayToast("两次密码输入不一致");
			return;
		}
	}
}
