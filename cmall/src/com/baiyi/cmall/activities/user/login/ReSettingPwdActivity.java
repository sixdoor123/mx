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
 * @date 2016-3-10 ����2:38:20
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

	// ���������
	private EditText mEdtInputPwd;
	// ȷ������
	private EditText mEdtSurePwd;
	// ��ɰ�ť
	private TextView mBtnComplete;

	/**
	 * ����
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
	 * ��ʼ��������
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
		topTitleView.setEventName("�һ�����");
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
	 * ��ɰ�ť
	 */
	private void complete() {
		String pwd = mEdtInputPwd.getText().toString().trim();
		String surePwd = mEdtSurePwd.getText().toString().trim();
		if (TextUtils.isEmpty(pwd)) {
			displayToast("���벻��Ϊ��");
			return;
		}
		if (TextUtils.isEmpty(surePwd)) {
			displayToast("ȷ�����벻��Ϊ��");
			return;
		}

		if (!pwd.equals(surePwd)) {
			displayToast("�����������벻һ��");
			return;
		}
	}
}
