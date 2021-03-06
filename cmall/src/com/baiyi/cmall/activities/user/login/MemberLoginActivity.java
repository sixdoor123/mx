package com.baiyi.cmall.activities.user.login;

import org.json.JSONArray;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.activities.user.login.web.ForgetPasswordWebActivity;
import com.baiyi.cmall.activities.user.login.web.PhoneRegisterActivity;
import com.baiyi.cmall.application.UserApplication;
import com.baiyi.cmall.entity.LoginInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.request.manager.LoginManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.XmlName;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.cmall.views.LoadingBar;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.cache.SimpleCacheLoader;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.user.login.MemberLoginActivity;
import com.baiyi.cmall.main.cache.CatchUtils;

/**
 * 个人会员中心登录界面
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-30 下午3:37:45
 */
@SuppressLint("ResourceAsColor")
public class MemberLoginActivity extends BaseActivity implements OnClickListener {

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);
		ActivityStackControlUtil.add(this);
		initTitle();
		initContent();
	}

	// 返回按钮
	private LinearLayout mImgBack;
	// 注册按钮
	private TextView mTxtRegister;

	/**
	 * 标题
	 */
	private void initTitle() {
		View view = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			view = ContextUtil.getLayoutInflater(this).inflate(R.layout.member_login_title_h, null);
		} else {
			view = ContextUtil.getLayoutInflater(this).inflate(R.layout.member_login_title_l, null);
		}

		win_title.addView(view);
		win_title.addView(new DownLine_1dp(this), -1, 1);
		mImgBack = (LinearLayout) view.findViewById(R.id.ll_back);
		mTxtRegister = (TextView) view.findViewById(R.id.txt_register);

		mImgBack.setOnClickListener(this);
		mTxtRegister.setOnClickListener(this);
	}

	// TextInputLayout layout;
	/**
	 * 显示内容
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_member_login, null);
		win_content.addView(view);

		// 找控件
		findViewById(view);
	}

	// 用户名
	private EditText mEdtAccoun;
	// 密码
	private EditText medtPassword;

	// 登录按钮
	private TextView mBtnLogin;

	// 无法登陆
	private TextView mTxtNoLogin;
	// 用手机登陆
	private TextView mTxtMobileLogin;

	// 删除账户按钮
	private ImageView mImgAccountDelete;
	private ImageView mImgPasswordDelete;

	// 账户线面的下划线
	private DownLineView accountLine;
	// 密码下面的下划线
	private DownLineView passwordLine;

	/**
	 * 找控件
	 * 
	 * @param view
	 */
	private void findViewById(View view) {
		mEdtAccoun = (EditText) view.findViewById(R.id.edt_account);
		medtPassword = (EditText) view.findViewById(R.id.edt_password);

		mBtnLogin = (TextView) view.findViewById(R.id.btn_login);
		mBtnLogin.setOnClickListener(this);
		mBtnLogin.setText("登录");

		mTxtNoLogin = (TextView) view.findViewById(R.id.txt_no_login);
		mTxtMobileLogin = (TextView) view.findViewById(R.id.txt_mobile_login);
		mTxtNoLogin.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);// 下划线
		mTxtMobileLogin.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);// 下划线

		mTxtMobileLogin.setOnClickListener(this);
		mTxtNoLogin.setOnClickListener(this);

		mImgAccountDelete = (ImageView) findViewById(R.id.img_account_delete);
		mImgAccountDelete.setOnClickListener(this);
		mImgPasswordDelete = (ImageView) findViewById(R.id.img_password_delete);
		mImgPasswordDelete.setOnClickListener(this);

		accountLine = (DownLineView) view.findViewById(R.id.account_line);
		passwordLine = (DownLineView) view.findViewById(R.id.password_line);
		passwordLine.setLineColor(R.color.bg_hui3);

		mEdtAccoun.setOnTouchListener(new OnTouchListener() {

			@SuppressLint("ResourceAsColor")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mImgAccountDelete.setVisibility(View.VISIBLE);
				mImgPasswordDelete.setVisibility(View.GONE);

				accountLine.setLineColor(R.color.bg_buyer);
				passwordLine.setLineColor(R.color.bg_hui3);
				return false;
			}
		});
		medtPassword.setOnTouchListener(new OnTouchListener() {

			@SuppressLint("ResourceAsColor")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mImgAccountDelete.setVisibility(View.GONE);
				mImgPasswordDelete.setVisibility(View.VISIBLE);

				accountLine.setLineColor(R.color.bg_hui3);
				passwordLine.setLineColor(R.color.bg_buyer);
				return false;
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:// 登录
			login();
			break;
		case R.id.img_account_delete:// 删除账户
			mEdtAccoun.setText("");
			break;
		case R.id.img_password_delete:// 删除密码
			medtPassword.setText("");
			break;
		case R.id.ll_back:// 返回
			finish();
			break;
		case R.id.txt_register:// 注册
			goActivity(PhoneRegisterActivity.class);
			break;
		case R.id.txt_mobile_login:// 用手机登录
			goActivity(UseMobileLoginActivity.class);
			break;
		case R.id.txt_no_login:// 无法登录（忘记密码）
			goActivity(ForgetPasswordWebActivity.class);
			break;
		}
	}

	/**
	 * 登录
	 */
	private void login() {
		String name = mEdtAccoun.getText().toString();
		String password = medtPassword.getText().toString();
		if (TextUtils.isEmpty(name)) {
			displayToast("用户名不能为空");
			return;
		}
		if (TextUtils.isEmpty(password)) {
			displayToast("密码不能为空");
			return;
		}
		final LoadingBar loadingBar = new LoadingBar(this);
		loadingBar.start();
		loadingBar.setProgressInfo("登录中,请稍后...");
		// loadLogin(name, password);
		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getLoginURL());
		loader.setPostData(LoginManager.getLoginPostData(name, password));
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				displayToast(arg2.toString());
				loadingBar.stop();
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
						setResult(10, intent);

						info.setPwd(medtPassword.getText().toString().trim());
						UserApplication.setUserInfo(info);
						CatchUtils.cacheLogin(MemberLoginActivity.this, ((JSONArray) result).toString().getBytes());
						CatchUtils.saveXml(MemberLoginActivity.this, info);
						loadingBar.stop();
						finish();
					} else {
						loadingBar.stop();
						return;
					}
				} else {
					displayToast("登录失败");
					loadingBar.stop();
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	private void cacheLogin(byte[] data) {
		SimpleCacheLoader cache = new SimpleCacheLoader(CmallApplication.getTestJsonCache(this));
		cache.setUpdate(CmallApplication.UserFileName, data);
		CmallApplication.getDataStratey().startLoader(cache);
		UserApplication.getTestJsonCache(this);
	}

	/**
	 * 保存用户ＩＤ 和 商家ＩＤ
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
		utils.savaXmlValue(XmlName.NAME_user_mm, medtPassword.getText().toString().trim());
		utils.savaXmlValue(XmlName.NAME_user_mm, info.getPwd());
		utils.savaXmlValue(XmlName.Is_Company, info.isIscompany());
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LoginInfo info = CmallApplication.getUserInfo();
		if (info != null) {
			String name = TextUtils.isEmpty(info.getMobile()) ? "" : info.getMobile();
			mEdtAccoun.setText(name);
			String pwd = TextUtils.isEmpty(info.getPwd()) ? "" : info.getPwd();
			medtPassword.setText(pwd);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 10) {
			Intent intent = new Intent();
			LoginInfo info = (LoginInfo) data.getSerializableExtra("key");
			intent.putExtra("key", info);
			setResult(10, intent);
			finish();
		}
	}

}
