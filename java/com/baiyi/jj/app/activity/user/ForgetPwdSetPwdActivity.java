/**
 *
 */
package com.baiyi.jj.app.activity.user;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.user.config.MemberConfig;
import com.baiyi.jj.app.activity.user.net.register.entity.StateEnties;
import com.baiyi.jj.app.activity.user.net.setting.SettingManager;
import com.baiyi.jj.app.activity.user.net.setting.SettingUrl;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.MyLoadingBar;
import com.turbo.turbo.mexico.R;

import org.json.JSONArray;

/**
 * 找回登录密码
 * 找回支付密码
 */
public class ForgetPwdSetPwdActivity extends BaseActivity {

    private EditText editPwd1 = null;
    private EditText editPwd2 = null;
    private MyLoadingBar progressBar = null;

    private EditText mEdtCode;
    private String email;
    Button mBtnSave;

    private String PwdType;

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);

        View titleView = ContextUtil.getLayoutInflater(this).inflate(R.layout.title_left, null);
        win_title.addView(titleView);
        View contentView = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_forgetpwd_setpwd, null);
        win_content.addView(contentView);

        email = getIntent().getStringExtra(Define.Code);
        PwdType = getIntent().getStringExtra(MemberConfig.Key);

        initTitle();
        initContent();
    }

    /**
     * ��ʼ��������
     */
    private void initTitle() {
        TextView txt = (TextView) findViewById(R.id.title_name);
        if (PwdType.equals(MemberConfig.Forgot_LoginPwd)) {
            txt.setText(getResources().getString(R.string.name_title_find_pwd));
        } else if (PwdType.equals(MemberConfig.Forgot_PayPwd)) {
            txt.setText(getResources().getString(R.string.txt_paypal_forgetpwd));
        }
//		txt.setTypeface(CmsApplication.getTitleFace(this));
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                setExitSwichLayout();
            }
        });
        mBtnSave = (Button) findViewById(R.id.btn_save);
        mBtnSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEdit();
            }
        });
    }

    private void initContent() {
        progressBar = (MyLoadingBar) findViewById(R.id.loading);
        editPwd1 = (EditText) findViewById(R.id.edit_pwd1);
        editPwd2 = (EditText) findViewById(R.id.edit_pwd2);
        mEdtCode = (EditText) findViewById(R.id.edit_email_code);
        if (PwdType.equals(MemberConfig.Forgot_PayPwd)) {
            editPwd1.setHint(getResources().getString(R.string.txt_paypal_new_pin));
            editPwd2.setHint(getResources().getString(R.string.txt_paypal_reenter_pin));
        }
        editPwd1.addTextChangedListener(watcher);
        editPwd2.addTextChangedListener(watcher);
        mEdtCode.addTextChangedListener(watcher);
        mBtnSave.setEnabled(false);
    }

    @Override
    public void textChanged() {
        if (!Utils.isStringEmpty(editPwd1.getText().toString()) && !Utils.isStringEmpty(editPwd2.getText().toString())
                && !Utils.isStringEmpty(mEdtCode.getText().toString())) {
            mBtnSave.setEnabled(true);
        } else {
            mBtnSave.setEnabled(false);
        }
    }

    private String newPws1;
    private String newPws2;
    private String emailCode;

    /**
     * ������������
     */
    private void checkEdit() {
        emailCode = mEdtCode.getText().toString().trim();
        newPws1 = editPwd1.getText().toString();
        if (newPws1.length() < 6 || newPws1.length() > 20) {
            if (PwdType.equals(MemberConfig.Forgot_PayPwd)) {
                displayMsgBox(getResources().getString(R.string.txt_paypal_forgetpwd), getResources()
                        .getString(R.string.tip_paypal_pwd_input_error));
            } else {
                displayMsgBox(getResources().getString(R.string.name_title_find_pwd), getResources()
                        .getString(R.string.tip_pwd_input_error));
            }
            return;
        }

        newPws2 = editPwd2.getText().toString();
        if (!newPws1.equals(newPws2)) {
            if (PwdType.equals(MemberConfig.Forgot_PayPwd)) {
                displayMsgBox(getResources().getString(R.string.txt_paypal_forgetpwd), getResources()
                        .getString(R.string.tip_paypal_not_match));
            } else {
                displayMsgBox(getResources().getString(R.string.name_title_find_pwd), getResources()
                        .getString(R.string.register_pwd_no_equals));
            }
            return;
        }

        loadResetPwd();
    }

    /**
     * ������������ӿ�
     * ���Σ��ֻ��ţ���֤�룬������
     */
    private void loadResetPwd() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgressInfo(getResources().getString(R.string.txt_progress_loading));
        progressBar.start();

        JsonLoader loader = new JsonLoader(this);
        loader.setUrlName("Forget Password");
        if (PwdType.equals(MemberConfig.Forgot_LoginPwd)) {
            //　忘记登录密码
            loader.setUrl(SettingUrl.getUpdatePasswordForgetUrl());
            loader.setPostData(SettingManager.getForgetPwdPostDdata(email, emailCode, newPws1));
        } else if (PwdType.equals(MemberConfig.Forgot_PayPwd)) {
            // 忘记支付密码
            loader.setUrl(SettingUrl.getUpdatePayPwdForgetUrl());
            loader.setPostData(SettingManager.getForgetPayPwdPostDdata(emailCode, newPws1));
        }

        loader.setMethod(BaseNetLoder.Method_Post);
        loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
        loader.setType("application/json");
        loader.setLoaderListener(new LoaderListener() {

            public void onProgress(Object tag, long curByteNum,
                                   long totalByteNum) {
            }

            public void onError(Object tag, int responseCode,
                                String errorMessage) {
                progressBar.stop();
                progressBar.setVisibility(View.GONE);
                if (PwdType.equals(MemberConfig.Forgot_PayPwd)) {
                    displayToast(getResources().getString(R.string.txt_pin_reset_failed));
                } else {
                    displayToast(getResources().getString(R.string.tip_set_pwd_fal));
                }
            }

            public void onCompelete(Object tag, Object result) {
                progressBar.stop();
                progressBar.setVisibility(View.GONE);

                JSONArray array = (JSONArray) result;

                StateEnties stateEnties = SettingManager.getModifyLanguageResult(array);
                if (200 == stateEnties.getState()) {
                    if (PwdType.equals(MemberConfig.Forgot_PayPwd)) {
                        displayToast(getResources().getString(R.string.txt_pin_reset_succeed));
                    } else {
                        displayToast(getResources().getString(R.string.tip_set_pwd_suc));
                    }
                    setResult(MemberConfig.Reset_Password_Success);
                    setExitSwichLayout();
                    finish();
                } else {
                    if (PwdType.equals(MemberConfig.Forgot_PayPwd)) {
                        displayToast(getResources().getString(R.string.txt_pin_reset_failed));
                    } else {
                        displayToast(getResources().getString(R.string.tip_set_pwd_fal));
                    }
                }
            }
        });
        CmsApplication.getDataStratey().startLoader(loader);
    }

}
