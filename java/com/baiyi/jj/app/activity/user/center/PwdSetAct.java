package com.baiyi.jj.app.activity.user.center;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader;
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
import com.baiyi.jj.app.views.LetterSpacingTextView;
import com.baiyi.jj.app.views.MyLoadingBar;
import com.turbo.turbo.mexico.R;

import org.json.JSONArray;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tangkun on 16/9/8.
 */
public class PwdSetAct extends BaseActivity {
    @Bind(R.id.title_name)
    LetterSpacingTextView titleName;
    @Bind(R.id.eidt_pwd_old)
    EditText editCurrentPwd;
    @Bind(R.id.eidt_pwd_new)
    EditText eidtPwdNew;
    @Bind(R.id.eidt_pwd_retener)
    EditText eidtPwdRetener;
    @Bind(R.id.loading)
    MyLoadingBar loading;
    @Bind(R.id.btn_save)
    Button btnSave;

    private String PwdType;

    public static final String Type_LoginPwd = "TypeLogin";
    public static final String Type_PayPwd = "TypePay";

    private String currentPwd;
    private String newPwd;
    //ȷ������
    private String retenerPwd;

    /**
     * 修改密码和修改支付密码
     */

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);

        PwdType = getIntent().getStringExtra(Define.PwdType);

        View titleView = ContextUtil.getLayoutInflater(this).inflate(R.layout.title_left, null);
        win_title.addView(titleView);
        View contentView = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_setpwd_us, null);
        win_content.addView(contentView);

        ButterKnife.bind(this);

        if (PwdType.equals(Type_PayPwd)) {
            titleName.setText(getResources().getString(R.string.txt_paypal_changepwd));
            editCurrentPwd.setHint(getResources().getString(R.string.txt_paypal_current_pin));
            eidtPwdNew.setHint(getResources().getString(R.string.txt_paypal_new_pin));
            eidtPwdRetener.setHint(getResources().getString(R.string.txt_paypal_reenter_pin));
        } else {
            titleName.setText(getResources().getString(R.string.title_setpwd));
        }
//        titleName.setTypeface(CmsApplication.getTitleFace(this));

        editCurrentPwd.addTextChangedListener(watcher);
        eidtPwdNew.addTextChangedListener(watcher);
        eidtPwdRetener.addTextChangedListener(watcher);
        btnSave.setEnabled(false);
    }

    @Override
    public void textChanged() {
        if (!Utils.isStringEmpty(editCurrentPwd.getText().toString()) && !Utils.isStringEmpty(eidtPwdNew.getText().toString())
                && !Utils.isStringEmpty(eidtPwdRetener.getText().toString()) ){
            btnSave.setEnabled(true);
        }else {
            btnSave.setEnabled(false);
        }
    }

    private void loadData() {

        currentPwd = editCurrentPwd.getText().toString().trim();
        if (currentPwd.length() < 6 || currentPwd.length() > 25) {
            if (PwdType.equals(Type_PayPwd)) {
                displayMsgBox(getResources().getString(R.string.txt_paypal_changepwd), getResources()
                        .getString(R.string.tip_paypal_pwd_input_error_old));
            } else {
                displayMsgBox(getResources().getString(R.string.tip_dialog_title5), getResources()
                        .getString(R.string.tip_pwd_input_error_old));
            }
            return;
        }

        newPwd = eidtPwdNew.getText().toString().trim();
        if (newPwd.length() < 6 || newPwd.length() > 25) {
            if (PwdType.equals(Type_PayPwd)) {
                displayMsgBox(getResources().getString(R.string.txt_paypal_changepwd), getResources()
                        .getString(R.string.tip_paypal_pwd_input_error));
            } else {
                displayMsgBox(getResources().getString(R.string.tip_dialog_title5), getResources()
                        .getString(R.string.tip_pwd_input_error));
            }
            return;
        }

        retenerPwd = eidtPwdRetener.getText().toString().trim();
        if (!newPwd.equals(retenerPwd)) {
            if (PwdType.equals(Type_PayPwd)) {
                displayMsgBox(getResources().getString(R.string.txt_paypal_changepwd), getResources()
                        .getString(R.string.tip_paypal_not_match));
            } else {
                displayMsgBox(getResources().getString(R.string.tip_dialog_title5), getResources()
                        .getString(R.string.register_pwd_no_equals));
            }
            return;
        }

        if (currentPwd.equals(newPwd)) {
            if (PwdType.equals(Type_PayPwd)) {
                displayMsgBox(getResources().getString(R.string.txt_paypal_changepwd), getResources()
                        .getString(R.string.tip_paypal_input_new_old));
            } else {
                displayMsgBox(getResources().getString(R.string.tip_dialog_title5), getResources()
                        .getString(R.string.tip_pwd_input_new_old));
            }
            return;
        }

        submmitData();
    }

    private void submmitData() {
        startLoading();

        JsonLoader loader = new JsonLoader(this);
        if (PwdType.equals(Type_PayPwd)) {
            // 支付密码
            loader.setUrl(SettingUrl.getModifyPayPwdUrl());
            loader.setPostData(SettingManager.getModifyPayPwdPostData(currentPwd, newPwd));
        } else {
            // 登录密码
            loader.setUrl(SettingUrl.getModifyPwdUrl());
            loader.setPostData(SettingManager.getModifyPwdPostData(currentPwd, newPwd));
        }

        loader.setUrlName(MemberConfig.Modify_Password);
        loader.setType(BaseNetLoder.APPLICATION_JSON);
        loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
        loader.setMethod(BaseNetLoder.Method_Post);
        loader.setLoaderListener(new Loader.LoaderListener() {

            @Override
            public void onProgress(Object tag, long curByteNum,
                                   long totalByteNum) {

            }

            @Override
            public void onError(Object tag, int responseCode,
                                String errorMessage) {
                stopLoading();
                if (PwdType.equals(Type_PayPwd)) {
                    displayToast(getResources().getString(R.string.txt_pin_reset_failed));
                } else {
                    displayToast(getResources().getString(R.string.tip_set_pwd_fal));
                }
            }

            @Override
            public void onCompelete(Object tag, Object result) {
                // TODO Auto-generated method stub
                JSONArray array = (JSONArray) result;
                stopLoading();

                StateEnties stateEnties = SettingManager.getModifyLanguageResult(array);
                if (null != stateEnties) {
                    int state = stateEnties.getState();
                    if (200 == state) {
                        if (PwdType.equals(Type_PayPwd)) {
                            displayToast(getResources().getString(R.string.txt_pin_reset_succeed));
                        } else {
                            displayToast(getResources().getString(R.string.tip_set_pwd_suc));
                        }
                        setExitSwichLayout();
                    } else if (520 == state) {
                        //520: 旧密码不正确
                        if (PwdType.equals(Type_PayPwd)) {
                            displayToast(getResources().getString(R.string.current_pin_not_right));
                        } else {
                            displayToast(getResources().getString(R.string.old_pwd_not_right));
                        }
                    } else if (521 == state) {
                        //521: 尚未登陆，或COOKIES失效，
                        displayToast(getString(R.string.tip_dialog_unlongin_token));
                    }
                }
            }
        });
        CmsApplication.getDataStratey().startLoader(loader);
    }

    @OnClick({R.id.img_back, R.id.btn_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                setExitSwichLayout();
                break;
            case R.id.btn_save:
                loadData();
                break;
        }
    }


    private void startLoading() {
        loading.setVisibility(View.VISIBLE);
        loading.setProgressInfo(getResources().getString(R.string.txt_progress_loading));
        loading.start();
    }

    private void stopLoading() {
        if (loading != null) {
            loading.stop();
            loading.setVisibility(View.GONE);
        }
    }
}
