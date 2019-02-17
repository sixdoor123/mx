package com.baiyi.cmall.activities.user.login;

import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.utils.MsgItemUtil;
import com.baiyi.cmall.views.itemview.EventTopTitleView;
import com.baiyi.cmall.views.itemview.EventTopTitleView.NewsPopItemOnclick;
import com.baiyi.cmall.views.itemview.EventTopTitleView.TitleNewsOnclick;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 验证手机号码的验证码
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-3-10 下午1:50:45
 */
public class VerifyPhoneCodeActivity extends BaseActivity implements OnClickListener {

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(false);
		ActivityStackControlUtil.add(this);
		initData();
		initTitle();
		initContent();
	}

	//发送验证码按钮
	private TextView mBtnSendCode;
	//下一步
	private TextView mBtnNex;
	/**
	 * 内容
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_verify_code, null);
		win_content.addView(view);
		
		mBtnNex = (TextView) view.findViewById(R.id.btn_next);
		mBtnSendCode = (TextView) view.findViewById(R.id.btn_send_code);
		
		mBtnNex.setOnClickListener(this);
		mBtnSendCode.setOnClickListener(this);
	}

	// 账户
	private String account;

	/**
	 * 接受数据
	 */
	private void initData() {
		account = getIntent().getStringExtra("temp");
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
				MsgItemUtil.onclickPopItem(state, VerifyPhoneCodeActivity.this);
			}
		});
		topTitleView.setEventName("找回密码");
		win_title.addView(topTitleView);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_next://下一步
			goActivity(ReSettingPwdActivity.class);
			break;
		case R.id.btn_send_code://发送验证码
			countTime();
			break;
		}
	}
	
	/**
	 * 刷新界面
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String s = time + "";
				if (s.length() < 2) {
					s = "0" + s;
				}
				mBtnSendCode.setText(s + "秒后重发");
				break;
			case 1:
				mBtnSendCode.setText("发送验证码");
				mBtnSendCode.setEnabled(true);
				break;

			default:
				break;
			}

		};
	};
	
	/**
	 * 标记位
	 */
	private boolean flag = true;
	// 倒计时为一分钟
	private int time;

	/**
	 * 倒计时
	 */
	private synchronized void countTime() {
		mBtnSendCode.setEnabled(false);
		new Thread() {
			@Override
			public void run() {
				flag = true;
				time = 60;
				while (flag) {
					try {
						handler.sendEmptyMessage(0);
						--time;
						if (0 == time) {
							flag = false;
							handler.sendEmptyMessage(1);
						}
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
		}.start();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		flag = false;
		handler.sendEmptyMessage(1);
	}
}
