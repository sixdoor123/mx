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
 * ��֤�ֻ��������֤��
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-3-10 ����1:50:45
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

	//������֤�밴ť
	private TextView mBtnSendCode;
	//��һ��
	private TextView mBtnNex;
	/**
	 * ����
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

	// �˻�
	private String account;

	/**
	 * ��������
	 */
	private void initData() {
		account = getIntent().getStringExtra("temp");
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
				MsgItemUtil.onclickPopItem(state, VerifyPhoneCodeActivity.this);
			}
		});
		topTitleView.setEventName("�һ�����");
		win_title.addView(topTitleView);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_next://��һ��
			goActivity(ReSettingPwdActivity.class);
			break;
		case R.id.btn_send_code://������֤��
			countTime();
			break;
		}
	}
	
	/**
	 * ˢ�½���
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
				mBtnSendCode.setText(s + "����ط�");
				break;
			case 1:
				mBtnSendCode.setText("������֤��");
				mBtnSendCode.setEnabled(true);
				break;

			default:
				break;
			}

		};
	};
	
	/**
	 * ���λ
	 */
	private boolean flag = true;
	// ����ʱΪһ����
	private int time;

	/**
	 * ����ʱ
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
