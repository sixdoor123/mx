package com.baiyi.jj.app.activity.user.center;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.utils.Define;
import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.user.config.MemberConfig;
import com.baiyi.jj.app.activity.user.net.register.entity.StateEnties;
import com.baiyi.jj.app.activity.user.net.setting.SettingManager;
import com.baiyi.jj.app.activity.user.net.setting.SettingUrl;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.utils.EmailUtils;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.LetterSpacingTextView;
import com.baiyi.jj.app.views.MyLoadingBar;

import org.json.JSONArray;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tangkun on 16/9/8.
 */
public class EmailSetAct extends BaseActivity {
    @Bind(R.id.title_name)
    LetterSpacingTextView titleName;
    @Bind(R.id.eidt_email_name)
    EditText eidtEmailName;
    @Bind(R.id.eidt_email_code)
    EditText eidtEmailCode;
    @Bind(R.id.loading)
    MyLoadingBar loading;
    @Bind(R.id.btn_save_email)
    Button btnSave;

    private String emailCode;
    private String email;
    private boolean isBindEmail = false;

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);

        View titleView = ContextUtil.getLayoutInflater(this).inflate(R.layout.title_left, null);
        win_title.addView(titleView);
        View contentView = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_email_us, null);
        win_content.addView(contentView);

        ButterKnife.bind(this);

        titleName.setText(getResources().getString(R.string.title_email));
//        titleName.setTypeface(CmsApplication.getTitleFace(this));

        isBindEmail = getIntent().getBooleanExtra(MemberConfig.Is_Bind_Email, false);
        email = getIntent().getStringExtra(Define.Code);
        if (isBindEmail) {
            eidtEmailName.setText(email);
            eidtEmailName.setEnabled(false);
        }
        eidtEmailCode.addTextChangedListener(watcher);
        eidtEmailName.addTextChangedListener(watcher);
        btnSave.setEnabled(false);
    }

    @Override
    public void textChanged() {
        if (!Utils.isStringEmpty(eidtEmailCode.getText().toString()) && !Utils.isStringEmpty(eidtEmailName.getText().toString())) {
            btnSave.setEnabled(true);
        } else {
            btnSave.setEnabled(false);
        }
    }

    private void loadData() {

        emailCode = eidtEmailCode.getText().toString().trim();
        email = eidtEmailName.getText().toString().trim();
        if (!EmailUtils.isEmail(email)) {
            displayMsgBox(getResources().getString(R.string.tip_title_modify_email), getResources()
                    .getString(R.string.resgister_email_wrong));
            return;
        }

        submmitData();
    }

    private void submmitData() {
        startLoading();

        JsonLoader loader = new JsonLoader(this);
        loader.setUrl(SettingUrl.getModifyEmailUrl());
        loader.setUrlName(MemberConfig.Modify_Email);
        loader.setType(BaseNetLoder.APPLICATION_JSON);
        loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
        loader.setPostData(SettingManager.getModifyEmailPoastData(email, emailCode));
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
                displayToast(getResources().getString(R.string.tip_modify_email_fal));
            }

            @Override
            public void onCompelete(Object tag, Object result) {
                // TODO Auto-generated method stub
                JSONArray array = (JSONArray) result;
                stopLoading();

                StateEnties enties = SettingManager.getModifyLanguageResult(array);

                if (200 == enties.getState()) {
                    displayToast(getString(R.string.txt_paypal_change_success));
                } else if (521 == enties.getState()) {
                    //  尚未登陆，或COOKIES失效
                    displayToast(getResources().getString(R.string.tip_dialog_unlongin_token));
                    return;
                } else if (522 == enties.getState()) {
                    //522: 验证码不正确
                    displayToast(getResources().getString(R.string.txt_error_code));
                    return;
                } else if (528 == enties.getState()) {
                    // 528, 邮箱已存在
                    displayToast(getResources().getString(R.string.tip_email_exists));
                    return;
                }

                CmsApplication.getUserInfoEntity().setEmail(email);
                setResult(MemberConfig.Modify_Email_ResultCode);
                setExitSwichLayout();
            }
        });
        CmsApplication.getDataStratey().startLoader(loader);

    }

    @OnClick({R.id.img_back, R.id.btn_save_email})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                setResult(MemberConfig.Modify_Email_ResultCode);
                setExitSwichLayout();
                break;
            case R.id.btn_save_email:
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(MemberConfig.Modify_Email_ResultCode);
            CmsApplication.getUserInfoEntity().setEmail(email);
            setExitSwichLayout();
        }
        return super.onKeyDown(keyCode, event);
    }
}
