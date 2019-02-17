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
 * ʹ���ֻ���¼
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-1-21 ����10:12:23
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

	// ���ذ�ť
	private LinearLayout mImgBack;
	// ע�ᰴť
	private TextView mTxtRegister;
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		mEdtCode.setText("");
		initCode();
	}

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

	/**
	 * ����
	 */
	private void initContent() {
		View view = ContextUtil.getLayoutInflater(this).inflate(
				R.layout.activity_mobile_login, null);
		win_content.addView(view);

		// �ҿؼ�
		findViewById(view);
	}

	// �û���
	private EditText mEdtAccoun;
	// ����
	private EditText medtPassword;

	// �޷���½
	// private TextView mTxtNoLogin;
	// �û�����¼
	private TextView mTxtMobileLogin;

	// ��¼��ť
	private TextView mBtnLogin;
	// ���ذ�ť
	private TextView mBtnBack;
	// ��ȡ��֤��
	private TextView mBtnGetCode;

	// ��֤�������
	private EditText mEdtCode;
	/**
	 * ���ɵ���λ�����
	 */

	private ImageView mImgCode;

	// �ֻ��ŵ��»���
	private DownLineView phoneNumLine;
	// ��֤����»���
	private DownLineView verCodeLine;

	/**
	 * �ҿؼ�
	 * 
	 * @param view
	 */
	@SuppressLint("ResourceAsColor")
	private void findViewById(View view) {
		mEdtAccoun = (EditText) view.findViewById(R.id.edt_account);

		// mTxtNoLogin = (TextView) view.findViewById(R.id.txt_no_login);
		mTxtMobileLogin = (TextView) view.findViewById(R.id.txt_mobile_login);
		// mTxtNoLogin.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);// �»���
		mTxtMobileLogin.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);// �»���
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
		case R.id.btn_get_code:// ��ȡ��֤��
			countTime();
			break;
		case R.id.btn_cancel_modify:// ����
			finish();
			break;
		case R.id.ll_back:// ����
			finish();
			break;
		case R.id.txt_register:// ע��
			goActivity(PhoneRegisterActivity.class);
			break;
		case R.id.txt_mobile_login:// �û�����¼
			// goActivity(LoginActivity.class);
			finish();
			break;
		case R.id.img_feresh_code:// ˢ����֤��
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
	 * ����ʱ
	 */
	private synchronized void countTime() {
		String code = mEdtCode.getText().toString().trim();
		phoneName = mEdtAccoun.getText().toString().trim();
		if (TextUtils.isEmpty(phoneName)) {
			displayToast("�ֻ��Ų���Ϊ��");
			return;
		}
		if (!(Utils.isPhoneNum(phoneName) && Utils
				.isPhoneNumberValid(phoneName))) {
			displayToast("��ϵ��ʽ��д����ȷ");
			return;
		}
		if (TextUtils.isEmpty(code)) {
			displayToast("��������֤��");
			return;
		}
		if (!code.equalsIgnoreCase(Code.getInstance(this).getCode())) {
			displayToast("��֤���������");
			return;
		} else {
			goActivity(phoneName, MobileDynamicLoginActivity.class);
		}

	}
}
