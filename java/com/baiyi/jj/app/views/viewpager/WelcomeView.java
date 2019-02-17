package com.baiyi.jj.app.views.viewpager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.user.UsRegistAct;
import com.baiyi.jj.app.activity.user.WelcomeActivity;
import com.baiyi.jj.app.activity.user.login.BaseLoginActivity;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.views.MyLoadingBar;
import com.turbo.turbo.mexico.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ��ӭ�����еĵ��������棨��½���棩
 */
public class WelcomeView extends LinearLayout implements View.OnClickListener {

    @Bind(R.id.eidt_userName)
    EditText editUsername;
    @Bind(R.id.edit_pwd)
    EditText editPwd;
    @Bind(R.id.forget_password)
    TextView mTvForgetPassword;
    @Bind(R.id.tv_sign_up)
    TextView mTvSignUp;
    @Bind(R.id.loading)
    MyLoadingBar loadingBar;

    private Context context;
    public WelcomeView(Context context) {
        super(context);
        this.context = context;
        initVIew();
    }

    private void initVIew() {
        ContextUtil.getLayoutInflater(context).inflate(R.layout.activity_login_welcome, this);
        ButterKnife.bind(this);

        mTvForgetPassword.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        mTvSignUp.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
        ((BaseLoginActivity)context).initEdit((BaseActivity)context,editUsername,editPwd,loadingBar);

        ((BaseLoginActivity)context).setLoginComplete(new BaseLoginActivity.LoginComplete() {
            @Override
            public void callComplete() {
                IntentChannel();
            }
        });
    }

    @OnClick({ R.id.btn_skip_now,R.id.btn_fb,R.id.login,R.id.forget_password,R.id.tv_sign_up})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_skip_now:
                skipNow();
                break;
            case R.id.login:
                ((BaseLoginActivity)context).hideInput(editPwd);
                ((BaseLoginActivity)context). Login();
                break;
            case R.id.btn_fb:
                ((BaseLoginActivity)context).hideInput(editPwd);
                ((BaseLoginActivity)context).facebookLogin();
                break;
            case R.id.forget_password:
                ((BaseLoginActivity)context).entryForgetPassword();
                break;
            case R.id.tv_sign_up:
                Intent intent = new Intent(context, UsRegistAct.class);
                intent.putExtra("type", Constant.WELCOME_REGISTER_TYPE);
                ((WelcomeActivity)context).startActivityForResult(intent,0);
                break;
        }
    }


    private void skipNow(){
        startLoading();
        ((BaseLoginActivity)context).loadAnonymity(new BaseActivity.AnonymityLister() {
            @Override
            public void setAnonymityLister(UserInfoEntity infoEntity) {
                if (infoEntity == null){
                    stopLoading();
                    return;
                }
                stopLoading();
               IntentChannel();
            }
        });

    }

    private void IntentChannel(){
//        Intent intent = new Intent(context, ChannelInitActi.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
//        ((WelcomeActivity)context).setExitSwichLayout();
    }

    public void startLoading() {
        loadingBar.setVisibility(View.VISIBLE);
        loadingBar.start();
    }

    public void stopLoading() {
        if (loadingBar != null) {
            loadingBar.stop();
            loadingBar.setVisibility(View.GONE);
        }
    }
}
