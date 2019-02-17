package com.baiyi.jj.app.activity.user.center;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.user.ValidateEmailActivity;
import com.baiyi.jj.app.activity.user.config.MemberConfig;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.views.LetterSpacingTextView;
import com.turbo.turbo.mexico.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

//import com.zhy.http.okhttp.OkHttpUtils;
//import com.zhy.http.okhttp.callback.StringCallback;
//import com.zhy.http.okhttp.https.HttpsUtils;
//import com.paypal.android.sdk.payments.LoginActivity;
//import com.paypal.android.sdk.payments.PayPalAuthorization;
//import com.paypal.android.sdk.payments.PayPalConfiguration;
//import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
//import com.paypal.android.sdk.payments.PayPalService;

/**
 * Created by Administrator on 2016/9/29 0029.
 */

public class PayPalAct extends BaseActivity{

    @Bind(R.id.title_name)
    LetterSpacingTextView titleName;
    @Bind(R.id.text_right)
    TextView textRight;
    @Bind(R.id.btn_change_paypal)
    LinearLayout btnChangePayPal;
    @Bind(R.id.btn_change_pwd)
    LinearLayout btnChangePwd;
    @Bind(R.id.btn_forget_pwd)
    LinearLayout btnForgotPwd;
    @Bind(R.id.txt_paypay_accout)
    TextView txtPayPalAccout;

    @Bind(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);

        View titleView = ContextUtil.getLayoutInflater(this).inflate(com.turbo.turbo.mexico.R.layout.title_left, null);
        win_title.addView(titleView);

        View contentView = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_paypay, null);
        win_content.addView(contentView);
        ButterKnife.bind(this);

        initTitle();

    }


    private void initTitle() {
        titleName.setText(getResources().getString(R.string.txt_edit_hint36));
//        titleName.setTypeface(CmsApplication.getTitleFace(this));
        textRight.setVisibility(View.GONE);

        if (CmsApplication.getUserInfoEntity()!= null)
            txtPayPalAccout.setText(CmsApplication.getUserInfoEntity().getPaypal());

        btnLogin.setVisibility(View.GONE);
    }


    @OnClick({R.id.btn_change_paypal, R.id.btn_change_pwd,
            R.id.btn_forget_pwd,R.id.img_back,R.id.btn_login})
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_change_paypal){
            Intent intent = new Intent(PayPalAct.this,PayPalChangeAct.class);
            startActivityForResult(intent,MemberConfig.Member_RequestCode);
        }else if (id == R.id.btn_change_pwd){
            Intent intent = new Intent(PayPalAct.this,PwdSetAct.class);
            intent.putExtra(Define.PwdType,PwdSetAct.Type_PayPwd);
            startActivity(intent);
        }else if (id == R.id.btn_forget_pwd){
            Intent intent = new Intent(this,ValidateEmailActivity.class);
            intent.putExtra(MemberConfig.Key,MemberConfig.Forgot_PayPwd);
            startActivity(intent);
            setEnterSwichLayout();
        }else if (id == R.id.img_back){
            setExitSwichLayout();
        }else if (id == R.id.btn_login){
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MemberConfig.Paypal_Chanage_Success)
        {
            txtPayPalAccout.setText(CmsApplication.getUserInfoEntity().getPaypal());
        }
    }
}
