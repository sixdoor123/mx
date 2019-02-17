package com.baiyi.jj.app.activity.user.center;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.manager.paypal.PayPalBaseAct;
import com.baiyi.jj.app.manager.paypal.PayPalUserInfo;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.utils.JsonParseBase;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.views.LetterSpacingTextView;
import com.baiyi.jj.app.views.MyLoadingBar;
import com.turbo.turbo.mexico.R;
//import com.paypal.android.sdk.payments.PayPalAuthorization;
//import com.paypal.android.sdk.payments.PayPalConfiguration;
//import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
//import com.paypal.android.sdk.payments.PayPalOAuthScopes;
//import com.paypal.android.sdk.payments.PayPalProfileSharingActivity;
//import com.paypal.android.sdk.payments.PayPalService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tangkun on 16/9/8.
 */
public class PayPalSetAct extends PayPalBaseAct{
    @Bind(R.id.title_name)
    LetterSpacingTextView titleName;
    @Bind(R.id.btn_save_paypal)
    Button mBtnSavePayPal;
    @Bind(R.id.eidt_account_name)
    EditText eidtAccountName;
    @Bind(R.id.eidt_pwd)
    EditText eidtPwd;
    @Bind(R.id.eidt_pwd_ok)
    EditText eidtPwdOk;
    @Bind(R.id.loading)
    MyLoadingBar loading;



    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);

        View titleView = ContextUtil.getLayoutInflater(this).inflate(R.layout.title_left, null);
        win_title.addView(titleView);
        View contentView = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_paypal_us, null);
        win_content.addView(contentView);
        ButterKnife.bind(this);

        titleName.setVisibility(View.VISIBLE);
        titleName.setText(getResources().getString(R.string.title_paypal));
        eidtAccountName.setFocusable(false);
//        titleName.setTypeface(CmsApplication.getTitleFace(this));

    }

    @Override
    public void onGetToken(PayPalUserInfo userInfo) {
        super.onGetToken(userInfo);
        eidtAccountName.setText(userInfo.getEmail());
    }

    /**
     * �����п�
     */
    private void bindPayPal() {

        if (TextUtils.isEmpty(eidtAccountName.getText().toString())
                || TextUtils.isEmpty(eidtPwd.getText().toString())
                || TextUtils.isEmpty(eidtPwdOk.getText().toString())) {
            return;
        }

        if (!eidtPwd.getText().toString().equals(eidtPwdOk.getText().toString())) {
            displayToast(getString(R.string.tip_pwd_input_two_diff));
            return;
        }

        startLoading();
        JsonLoader loader = new JsonLoader(this);
        loader.setUrl(NetUtils.bindPayPal());
        loader.setPostData(getPostData());
        loader.addRequestHeader("Authorization", CmsApplication.getUserToken());
        loader.setType("application/json");
        loader.setMethod(BaseNetLoder.Method_Post);
        loader.setLoaderListener(new Loader.LoaderListener() {
            @Override
            public void onProgress(Object tag, long curByteNum, long totalByteNum) {
            }

            @Override
            public void onError(Object tag, int responseCode, String errorMessage) {
                displayToast(errorMessage);
                stopLoading();
            }

            @Override
            public void onCompelete(Object tag, Object result) {

                stopLoading();
                if (JsonParseBase.getState200((JSONArray) result)) {
                    displayToast(getString(R.string.tip_set_pwd_suc));
                    updateUserinfo(eidtAccountName.getText().toString());
                    setExitSwichLayout();
                } else {
                    displayToast(getString(R.string.tip_set_pwd_fal));
                }
            }
        });
        CmsApplication.getDataStratey().startLoader(loader);
    }
    private void updateUserinfo(String paypalStr){
        CmsApplication.getUserInfoEntity().setPaypal(paypalStr);
    }

    private String getPostData() {


        JSONObject object = new JSONObject();
        try {
            object.put("paypal", eidtAccountName.getText().toString());
            object.put("pya_pwd", eidtPwd.getText().toString());
//          object.put("verification_code", eidtCode.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    @OnClick({R.id.img_back, R.id.btn_save_paypal})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                setExitSwichLayout();
                break;
            case R.id.btn_save_paypal:
                bindPayPal();
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
    public void startPro() {
        super.startPro();
        startLoading();
    }

    @Override
    public void stopPro() {
        super.stopPro();
        stopLoading();
    }
}
