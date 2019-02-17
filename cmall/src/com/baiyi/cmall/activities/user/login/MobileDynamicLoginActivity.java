package com.baiyi.cmall.activities.user.login;

import org.json.JSONArray;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.application.UserApplication;
import com.baiyi.cmall.activities.user.login.web.PhoneRegisterActivity;
import com.baiyi.cmall.entity.LoginInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.request.manager.LoginManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.XmlName;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.cache.SimpleCacheLoader;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * �����ֻ���֤��
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-3-31 ����2:27:41
 */
public class MobileDynamicLoginActivity extends BaseActivity implements
		OnClickListener {

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(false);
		initData();
		sendCode();
		initTitle();
		initContent();
	}

	private String phoneNum;

	/**
	 * ��������
	 */
	private void initData() {
		phoneNum = getIntent().getStringExtra("temp");
	}

	/**
	 * ����
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_dynamic_login, null);
		win_content.addView(view);
		findViewById(view);
	}

	private TextView mBtnLogin;
	// �û�����¼(��ͨ��¼)
	private TextView mTxtMobileLogin;

	// ������֤�������
	private EditText mEdtCode;
	// ����ʱ
	private TextView mTxtCountTime;

	// ���·�����֤��
	private LinearLayout mReSendCode;

	/**
	 * �ҿؼ�
	 * 
	 * @param view
	 */
	private void findViewById(View view) {
		mBtnLogin = (TextView) view.findViewById(R.id.btn_login);
		mBtnLogin.setOnClickListener(this);

		mTxtMobileLogin = (TextView) view.findViewById(R.id.txt_account_login);
		mTxtMobileLogin.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);// �»���
		mTxtMobileLogin.setOnClickListener(this);

		mEdtCode = (EditText) view.findViewById(R.id.edt_code);
		mTxtCountTime = (TextView) view.findViewById(R.id.count_time);

		mReSendCode = (LinearLayout) view.findViewById(R.id.re_send_code);
		mReSendCode.setOnClickListener(this);

		conutTime();
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
				mTxtCountTime.setText("���·���(" + s + "s)");
				break;
			case 1:
				mTxtCountTime.setText("���·���");
				mTxtCountTime.setEnabled(true);
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
	 * ��ʼ����ʱ
	 */
	private void conutTime() {
		mTxtCountTime.setEnabled(false);
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

	// ���ذ�ť
	private LinearLayout mImgBack;
	// ע�ᰴť
	private TextView mTxtRegister;

	/**
	 * ����
	 */
	private void initTitle() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.member_login_title_h, null);
		win_title.addView(view);

		mImgBack = (LinearLayout) view.findViewById(R.id.ll_back);
		mTxtRegister = (TextView) view.findViewById(R.id.txt_register);

		mImgBack.setOnClickListener(this);
		mTxtRegister.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_back:// ����
			finish();
			break;
		case R.id.txt_register:// ע��
			goActivity(PhoneRegisterActivity.class);
			break;
		case R.id.txt_account_login:// �û�����¼����ͨ��¼
			// goActivity(LoginActivity.class);
			Intent intent = new Intent();
			setResult(123);
			finish();
			break;
		case R.id.btn_login:// ��¼��ť
			login();
			break;
		case R.id.re_send_code:// ���·�����֤��
			if (mTxtCountTime.isEnabled()) {
				conutTime();
				sendCode();
			}
			break;
		default:
			break;
		}
	}

	/**
	 * ��¼
	 */
	private void login() {

		String smsCode = mEdtCode.getText().toString().trim();
		if (TextUtils.isEmpty(smsCode)) {
			displayToast("������֤�벻��Ϊ��");
			return;
		}

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getMobileLoginURL());
		loader.setPostData(LoginManager.getMobileLoginPostData(phoneNum,
				smsCode));
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);

		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				displayToast(arg2.toString());
			}

			@Override
			public void onCompelete(Object arg0, Object result) {
				LoginInfo info = LoginManager.getLoginResultInfo(result);
				if (null != info) {
					RequestNetResultInfo resultInfo = info.getResultInfo();
					displayToast(resultInfo.getMsg());
					if (1 == resultInfo.getStatus()) {
						Intent intent = new Intent();
						intent.putExtra("key", info);
						// ��¼�ɹ���finish��LoginActivity
						setResult(10, intent);
						info.setPwd(mEdtCode.getText().toString().trim());
						UserApplication.setUserInfo(info);
						cacheLogin(((JSONArray) result).toString().getBytes());
						saveXml(info);
						finish();
					} else {
						flag = false;
						handler.sendEmptyMessage(1);
						return;
					}
				} else {
					flag = false;
					handler.sendEmptyMessage(1);
					displayToast("��¼ʧ��");
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		flag = false;
		handler.sendEmptyMessage(1);
	}

	/**
	 * http://10.20.22.5/webservice/IndexPlatLogin/LoginMobile phoneName
	 * phonepwd ������֤��
	 */
	private void sendCode() {

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getMobileCodeURL(phoneNum));
		loader.setPostData(new StringBuilder().append("phoneName").append("=")
				.append(phoneNum).toString());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);

		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				displayToast(arg2.toString());
			}

			@Override
			public void onCompelete(Object arg0, Object result) {
				RequestNetResultInfo info = LoginManager.getResultInfo(result);
				if (null != info) {
					if (1 == info.getStatus()) {
						displayToast("�ѷ���");
					} else {
						displayToast(info.getMsg());
						flag = false;
						handler.sendEmptyMessage(1);
						return;
					}
				} else {
					flag = false;
					handler.sendEmptyMessage(1);
					displayToast("��ȡʧ��");
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	private void cacheLogin(byte[] data) {
		SimpleCacheLoader cache = new SimpleCacheLoader(
				CmallApplication.getTestJsonCache(this));
		cache.setUpdate(CmallApplication.UserFileName, data);
		CmallApplication.getDataStratey().startLoader(cache);
				UserApplication.getTestJsonCache(this);
	}

	/**
	 * �����û��ɣġ��� �̼ңɣ�
	 * 
	 * 
	 * @param info
	 */
	protected void saveXml(LoginInfo info) {
		XmlUtils utils = XmlUtils.getInstence(this);
		utils.savaXmlValue(XmlName.USER_ID, info.getUserID());
		utils.savaXmlValue(XmlName.COMPANY_ID, info.getCompanyID());
		utils.savaXmlValue(XmlName.USER_NAME, info.getUserName());
		utils.savaXmlValue(XmlName.TOKEN, info.getToken());
		utils.savaXmlValue(XmlName.Company_Name, info.getCompanyName());
		utils.savaXmlValue(XmlName.Mobile, info.getMobile());
		utils.savaXmlValue(XmlName.NAME_user_account, info.getLoginName());
		utils.savaXmlValue(XmlName.NAME_user_mm, info.getPwd());
		utils.savaXmlValue(XmlName.Is_Company, info.isIscompany());
	}

}
