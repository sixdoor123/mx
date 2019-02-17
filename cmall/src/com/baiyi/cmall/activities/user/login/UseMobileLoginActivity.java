package com.baiyi.cmall.activities.user.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.user.login.web.PhoneRegisterActivity;
import com.baiyi.cmall.entity.LoginInfo;
import com.baiyi.cmall.utils.Code;
import com.baiyi.cmall.utils.ImageTools;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 使用手机登录
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-1-21 上午10:12:23
 */
public class UseMobileLoginActivity extends BaseActivity implements
		OnClickListener {

	@Override
	protected void initWin(boolean hasScrollView) {
		// TODO Auto-generated method stub
		super.initWin(true);
		initTitle();
		initContent();
	}

	// 返回按钮
	private LinearLayout mImgBack;
	// 注册按钮
	private TextView mTxtRegister;
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		mEdtCode.setText("");
		initCode();
	}

	/**
	 * 标题
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

	/**
	 * 内容
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_mobile_login, null);
		win_content.addView(view);

		// 找控件
		findViewById(view);
	}

	// 用户名
	private EditText mEdtAccoun;
	// 密码
	private EditText medtPassword;

	// 无法登陆
	// private TextView mTxtNoLogin;
	// 用户名登录
	private TextView mTxtMobileLogin;

	// 登录按钮
	private TextView mBtnLogin;
	// 返回按钮
	private TextView mBtnBack;
	// 获取验证码
	private TextView mBtnGetCode;

	// 验证码输入框
	private EditText mEdtCode;
	/**
	 * 生成的四位随机数
	 */

	private ImageView mImgCode;

	// 手机号的下划线
	private DownLineView phoneNumLine;
	// 验证码的下划线
	private DownLineView verCodeLine;

	/**
	 * 找控件
	 * 
	 * @param view
	 */
	@SuppressLint("ResourceAsColor")
	private void findViewById(View view) {
		mEdtAccoun = (EditText) view.findViewById(R.id.edt_account);

		// mTxtNoLogin = (TextView) view.findViewById(R.id.txt_no_login);
		mTxtMobileLogin = (TextView) view.findViewById(R.id.txt_mobile_login);
		// mTxtNoLogin.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);// 下划线
		mTxtMobileLogin.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);// 下划线
		mTxtMobileLogin.setOnClickListener(this);
		// mTxtNoLogin.setVisibility(View.GONE);

		mBtnGetCode = (TextView) view.findViewById(R.id.btn_get_code);
		mBtnGetCode.setOnClickListener(this);
		mEdtCode = (EditText) findViewById(R.id.edt_code);
		mImgCode = (ImageView) findViewById(R.id.img_feresh_code);
		mImgCode.setOnClickListener(this);

		phoneNumLine = (DownLineView) view.findViewById(R.id.account_line);
		verCodeLine = (DownLineView) view.findViewById(R.id.ver_line);
		verCodeLine.setLineColor(R.color.bg_hui3);

		initCode();

		mEdtAccoun.setOnTouchListener(new OnTouchListener() {

			@SuppressLint("ResourceAsColor")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				phoneNumLine.setLineColor(R.color.bg_buyer);
				verCodeLine.setLineColor(R.color.bg_hui3);
				return false;
			}
		});
		mEdtCode.setOnTouchListener(new OnTouchListener() {

			@SuppressLint("ResourceAsColor")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				phoneNumLine.setLineColor(R.color.bg_hui3);
				verCodeLine.setLineColor(R.color.bg_buyer);
				return false;
			}
		});

	}

	@SuppressLint("ResourceAsColor")
	private void initCode() {
		Bitmap bitmap = Code.getInstance(this).createBitmap();
		bitmap = ImageTools.toRoundCorner(bitmap, 10);
		// mImgCode.setImageBitmap(bitmap);
		ImageTools.setImageBitmap(mImgCode, bitmap, true);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_get_code:// 获取验证码
			countTime();
			break;
		case R.id.btn_cancel_modify:// 返回
			finish();
			break;
		case R.id.ll_back:// 返回
			finish();
			break;
		case R.id.txt_register:// 注册
			goActivity(PhoneRegisterActivity.class);
			break;
		case R.id.txt_mobile_login:// 用户名登录
			// goActivity(LoginActivity.class);
			finish();
			break;
		case R.id.img_feresh_code:// 刷新验证码
			initCode();
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (123 == resultCode) {
			finish();
		} else if (resultCode == 10) {
			Intent intent = new Intent();
			LoginInfo info = (LoginInfo) data.getSerializableExtra("key");
			intent.putExtra("key", info);
			setResult(10, intent);
			finish();
		}
	}

	private String phoneName;

	/**
	 * 倒计时
	 */
	private synchronized void countTime() {
		String code = mEdtCode.getText().toString().trim();
		phoneName = mEdtAccoun.getText().toString().trim();
		if (TextUtils.isEmpty(phoneName)) {
			displayToast("手机号不能为空");
			return;
		}
		if (!(Utils.isPhoneNum(phoneName) && Utils
				.isPhoneNumberValid(phoneName))) {
			displayToast("联系方式填写不正确");
			return;
		}
		if (TextUtils.isEmpty(code)) {
			displayToast("请输入验证码");
			return;
		}
		if (!code.equalsIgnoreCase(Code.getInstance(this).getCode())) {
			displayToast("验证码输入错误");
			return;
		} else {
			goActivity(phoneName, MobileDynamicLoginActivity.class);
		}

	}
}
