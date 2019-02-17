package com.baiyi.jj.app.activity.user.center;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.user.config.MemberConfig;
import com.baiyi.jj.app.activity.user.net.register.entity.StateEnties;
import com.baiyi.jj.app.activity.user.net.setting.SettingManager;
import com.baiyi.jj.app.activity.user.net.setting.SettingUrl;
import com.baiyi.jj.app.manager.paypal.PayPalBaseAct;
import com.baiyi.jj.app.manager.paypal.PayPalUserInfo;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.LetterSpacingTextView;
import com.baiyi.jj.app.views.MyLoadingBar;
import com.turbo.turbo.mexico.R;

import org.json.JSONArray;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/29 0029.
 */

public class PayPalChangeAct extends PayPalBaseAct {

    @Bind(R.id.title_name)
    LetterSpacingTextView titleName;
    @Bind(R.id.text_right)
    TextView textRight;
    @Bind(R.id.eidt_newpaypal)
    EditText editNewPayPal;
    @Bind(R.id.eidt_pwd)
    EditText eidtPayNew;
    @Bind(R.id.loading)
    MyLoadingBar loading;

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);

        View titleView = ContextUtil.getLayoutInflater(this).inflate(com.turbo.turbo.mexico.R.layout.title_left, null);
        win_title.addView(titleView);

        View contentView = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_paypal_change, null);
        win_content.addView(contentView);
        ButterKnife.bind(this);

        initTitle();

    }

    private void initTitle() {
        titleName.setText(getResources().getString(R.string.txt_paypal_changepaypal));
//        titleName.setTypeface(CmsApplication.getTitleFace(this));
        textRight.setVisibility(View.GONE);
//        textRight.setTextColor(getResources().getColor(R.color.word_white_red));
//        textRight.setText(getResources().getString(R.string.txt_button25));

        editNewPayPal.setFocusable(false);
    }

    @Override
    public void onGetToken(PayPalUserInfo userInfo) {
        super.onGetToken(userInfo);
//        TLog.e("userinfo",userInfo.getEmail());
        editNewPayPal.setText(userInfo.getEmail());
    }

    private void loadData() {
        String paypalAccout = editNewPayPal.getText().toString().trim();
        String payPwd = eidtPayNew.getText().toString().trim();
        if (Utils.isStringEmpty(paypalAccout)) {
            displayToast(getResources().getString(R.string.tip_set_pwd_old_null));
            return;
        }

        if (Utils.isStringEmpty(payPwd)) {
            displayToast(getResources().getString(R.string.tip_set_pwd_old_null));
            return;
        }


        submmitData(paypalAccout,payPwd);
    }


    private void submmitData(final String paypal, String paypwd) {
        startLoading();

        JsonLoader loader = new JsonLoader(this);
        loader.setUrl(SettingUrl.getModifyPayPalUrl());
        loader.setPostData(SettingManager.getModifyPayPalPostData(paypal, paypwd));
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
                displayToast(getResources().getString(R.string.txt_paypal_change_failure));
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
//                        Intent intent = new Intent(PwdSetAct.this, UsLoginAct.class);
////                        intent.putExtra(MemberConfig.Key,MemberConfig.Modify_Password);
//                        startActivity(intent);
                        displayToast(getResources().getString(R.string.txt_paypal_change_success));
                        CmsApplication.getUserInfoEntity().setPaypal(paypal);
                        setResult(MemberConfig.Paypal_Chanage_Success);
                        setExitSwichLayout();

                    } else if (state == 520) {
                        displayToast(getResources().getString(R.string.txt_tixian_tip3));
                        return;
                    } else if (state == 521) {
                        displayToast(getResources().getString(R.string.txt_error_token));
                        return;
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

    @Override
    public void startPro() {
        super.startPro();
        startLoading();
    }

    @Override
    public void stopPro() {
        super.stopPro();
        stopLoading();
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
