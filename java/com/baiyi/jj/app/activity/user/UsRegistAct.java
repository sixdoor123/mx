package com.baiyi.jj.app.activity.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiyi.core.file.Preference;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.BaseAnalyticsActivity;
import com.baiyi.jj.app.activity.user.config.MemberConfig;
import com.baiyi.jj.app.activity.user.login.BaseLoginActivity;
import com.baiyi.jj.app.activity.user.net.NetUrl;
import com.baiyi.jj.app.activity.user.net.UserNetCallBack;
import com.baiyi.jj.app.activity.user.net.register.RegisterManager;
import com.baiyi.jj.app.activity.user.net.register.entity.ResultEntities;
import com.baiyi.jj.app.activity.user.net.register.entity.StateEnties;
import com.baiyi.jj.app.activity.user.net.setting.SettingManager;
import com.baiyi.jj.app.activity.user.net.setting.SettingUrl;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.cache.Dao.ConfigDao;
import com.baiyi.jj.app.cache.bean.ConfigurationBean;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.language.SwitchLanguageUtils;
import com.baiyi.jj.app.manager.ChannelDataManager;
import com.baiyi.jj.app.utils.Base64;
import com.baiyi.jj.app.utils.EmailUtils;
import com.baiyi.jj.app.utils.Installation;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.utils.XMLName;
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
public class UsRegistAct extends BaseActivity {

    @Bind(R.id.title_name)
    LetterSpacingTextView titleName;
    @Bind(R.id.edit_email)
    EditText editEmail;
    @Bind(R.id.edit_pwd)
    EditText editPwd;
    @Bind(R.id.edit_pwd_ok)
    EditText editPwdOk;
    @Bind(R.id.loading)
    MyLoadingBar loading;
    @Bind(R.id.have_account)
    TextView forgetPassword;
    @Bind(R.id.txt_term)
    TextView txtTerm;
    @Bind(R.id.btn_sing_up)
    Button btnSignUp;

    private String type;

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(true, true);

        View titleView = ContextUtil.getLayoutInflater(this).inflate(R.layout.title_left, null);
        win_title.addView(titleView);
        View contentView = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_regist_us, null);
        win_content.addView(contentView);
        ButterKnife.bind(this);

        titleName.setText(getResources().getString(R.string.name_title_register));
//        titleName.setTypeface(CmsApplication.getTitleFace(this));

        type = getIntent().getStringExtra("type");
        forgetPassword.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        String source = "<font color=#313131>" + getResources().getString(R.string.txt_term_tips1)
                + "</font><a href='https://api.turboapp.xyz/terms'>" + " " + getResources().getString(R.string.txt_term_tips2) + "</a>";
        txtTerm.setText(Html.fromHtml(source));
        txtTerm.setMovementMethod(LinkMovementMethod.getInstance());

        editEmail.addTextChangedListener(watcher);
        editPwd.addTextChangedListener(watcher);
        editPwdOk.addTextChangedListener(watcher);
        btnSignUp.setEnabled(false);

    }

    @Override
    public void textChanged() {
        if (!Utils.isStringEmpty(editEmail.getText().toString()) && !Utils.isStringEmpty(editPwdOk.getText().toString())
                && !Utils.isStringEmpty(editPwd.getText().toString()) ){
            btnSignUp.setEnabled(true);
        }else {
            btnSignUp.setEnabled(false);
        }
    }

    private void goLogin() {
        Intent intent = new Intent(this, UsLoginAct.class);
        startActivity(intent);
        setExitSwichLayout();
    }

    @OnClick({R.id.have_account, R.id.btn_sing_up, R.id.img_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.have_account:
                goLogin();
                break;
            case R.id.btn_sing_up:
                hideInput(editEmail);
                validateRegisterInfo();
                break;
            case R.id.img_back:
                setExitSwichLayout();
                break;
        }
    }

    private String email;
    private String password;
    private String rePassword;

    /**
     * Validate Register Info
     */
    private void validateRegisterInfo() {

        email = editEmail.getText().toString().trim();
        //邮箱格式验证
        if (!EmailUtils.isEmail(email)) {
            displayMsgBox(getResources().getString(R.string.name_title_register), getResources()
                    .getString(R.string.resgister_email_wrong));
            return;
        }

        password = editPwd.getText().toString().trim();
        if (password.length() < 6 || password.length() > 25) {
            //tip_pwd_input_error
            displayMsgBox(getResources().getString(R.string.tip_dialog_title5), getResources()
                    .getString(R.string.tip_pwd_input_error));
            return;
        }
        rePassword = editPwdOk.getText().toString().trim();
        if (!password.equals(rePassword)) {
            displayMsgBox(getResources().getString(R.string.name_title_register), getResources()
                    .getString(R.string.register_pwd_no_equals));
            return;
        }

        commitInfo();
    }

    private void startLoading() {
        loading.setVisibility(View.VISIBLE);
        loading.setProgressInfo(getResources().getString(R.string.txt_progress_loading));
        loading.start();
    }

    private void stopLoad() {
        if (loading != null) {
            loading.stop();
        }
    }

    private String getUUID() {
        ConfigurationBean configBean = new ConfigDao(this).queryCofig();
        String uuid = null;
        if (configBean != null) {
            uuid = configBean.getUuid();
            if (Utils.isStringEmpty(uuid)) {
                uuid = Installation.getClientId(this);
            }
        } else {
            uuid = Installation.getClientId(this);
        }

        return uuid;
    }

    private void commitInfo() {

        startLoading();
        final long start = System.currentTimeMillis();

        JsonLoader loader = new JsonLoader(this);
        loader.setUrl(NetUrl.getRegisterByNormal());
        loader.setUrlName(MemberConfig.Register);
        loader.setType(BaseNetLoder.APPLICATION_JSON);
        loader.setPostData(RegisterManager.getRegisterPostData(email, password, getUUID()));
        loader.setMethod(BaseNetLoder.Method_Post);
        loader.setLoaderListener(new Loader.LoaderListener() {

            @Override
            public void onProgress(Object tag, long curByteNum,
                                   long totalByteNum) {
            }

            @Override
            public void onError(Object tag, int responseCode,
                                String errorMessage) {
                stopLoad();
                displayToast(getResources().getString(R.string.register_failure));
            }

            @Override
            public void onCompelete(Object tag, Object result) {
                // TODO Auto-generated method stub
                JSONArray array = (JSONArray) result;

                long value = System.currentTimeMillis() - start;
                addRequestTime(this.getClass().getName(), BaseAnalyticsActivity.Category_net, BaseAnalyticsActivity.Net_Register,value);

                stopLoad();

                ResultEntities resultEntities = RegisterManager.getRegisterResult(array);
                if (null != resultEntities) {
                    StateEnties stateEnties = resultEntities.getStateEnties();
                    if (null != stateEnties) {
                        int state = stateEnties.getState();
                        if (state == 200) {
                            loadLogin();
                        } else if (state == 450) {
                            displayToast(getResources().getString(R.string.tip_email_exists));
                            return;
                        } else {
                            displayToast(getResources().getString(R.string.register_failure));
                            return;
                        }
                    }
                }
            }
        });
        CmsApplication.getDataStratey().startLoader(loader);
    }

    private void loadLogin() {
        startLoading();

        new UserNetCallBack(this, new UserNetCallBack.CallBack() {

            @Override
            public void callBackLister(int state, UserInfoEntity entity) {
                stopLoad();
                if (state == UserNetCallBack.SUCCESS) {
                    if (entity == null) {
                        return;
                    }

                    entity.setAccount(editEmail.getText().toString());
                    entity.setPwd(editPwd.getText().toString());
                    AccountManager.getInstance().setMid(entity.getMID());
                    AccountManager.getInstance().setAccount(editEmail.getText().toString());
                    AccountManager.getInstance().setPwd(editPwd.getText().toString());
                    AccountManager.getInstance().reset(true);
                    CmsApplication.setUserInfoEntity(entity, UsRegistAct.this);

                    if (Constant.WELCOME_REGISTER_TYPE.equals(type)) {
                        setResult(Constant.WELCOME_REGISTER_OK);
                    }
                    setExitSwichLayout();
                    loadSaveLanguage();
                    new ConfigDao(UsRegistAct.this).updateAccoutAndPwd(editEmail.getText().toString(),
                            Base64.encode(editPwd.getText().toString().getBytes()));
//                    new UserNetCallBack(UsRegistAct.this, null).loadLoginTrigger(CmsApplication.getUserToken());

                } else {
                    displayToast(getResources().getString(R.string.tip_login_failure));
                }
            }
        }).loadLogin(editEmail.getText().toString(), editPwd.getText().toString(), UserInfoEntity.UserType_Accout, null);
    }

    private void loadSaveLanguage() {

        JsonLoader loader = new JsonLoader(UsRegistAct.this);
        loader.setUrl(SettingUrl.getModifyLanguageUrl());
        loader.setUrlName(MemberConfig.Modify_Language);
        loader.setType(BaseNetLoder.APPLICATION_JSON);
        loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
        loader.setPostData(SettingManager.getModifyLanguagePostData2(SwitchLanguageUtils.languageid_ES));
        loader.setMethod(BaseNetLoder.Method_Post);
        loader.setLoaderListener(new Loader.LoaderListener() {
            @Override
            public void onProgress(Object tag, long curByteNum,
                                   long totalByteNum) {
            }
            @Override
            public void onError(Object tag, int responseCode,
                                String errorMessage) {
            }

            @Override
            public void onCompelete(Object tag, Object result) {
                // TODO Auto-generated method stub
            }
        });
        CmsApplication.getDataStratey().startLoader(loader);
    }

}
