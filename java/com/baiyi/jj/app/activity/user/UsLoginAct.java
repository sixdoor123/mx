package com.baiyi.jj.app.activity.user;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.activity.user.login.BaseLoginActivity;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.MyLoadingBar;
import com.turbo.turbo.mexico.R;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by tangkun on 16/9/8.
 */
public class UsLoginAct extends BaseLoginActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.text_right)
    TextView textRight;
    @Bind(R.id.eidt_userName)
    EditText eidtUserName;
    @Bind(R.id.edit_pwd)
    EditText editPwd;
    @Bind(R.id.regist_agreement_checkBox)
    CheckBox registAgreementCheckBox;
    @Bind(R.id.login)
    Button btnLogin;
    @Bind(R.id.forget_password)
    TextView forgetPassword;
    @Bind(R.id.loading)
    MyLoadingBar loading;

    private String type ;

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(true, true);

        View titleView = ContextUtil.getLayoutInflater(this).inflate(R.layout.title_left, null);
        win_title.addView(titleView);
        View bottomView = ContextUtil.getLayoutInflater(this).inflate(R.layout.item_bottom_sign_in, null);
        win_menu.addView(bottomView);
        View contentView = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_login_us, null);
        win_content.addView(contentView);
        ButterKnife.bind(this);
        type = getIntent().getStringExtra("type");
        initEdit(UsLoginAct.this,eidtUserName,editPwd,loading,type);

        titleName.setText(getResources().getString(R.string.name_title_login));
//        titleName.setTypeface(CmsApplication.getTitleFace(this));
        textRight.setVisibility(View.VISIBLE);
        textRight.setTextColor(getResources().getColor(R.color.word_white_red_press));
        textRight.setText(getResources().getString(R.string.name_title_register));

//        btnFb.setReadPermissions(Arrays.asList("user_status"));
        if (!Utils.isStringEmpty(AccountManager.getInstance().getAccount())){
            eidtUserName.setText(AccountManager.getInstance().getAccount());
        }

        eidtUserName.addTextChangedListener(watcher);
        editPwd.addTextChangedListener(watcher);
        btnLogin.setEnabled(false);
    }

    @OnClick(R.id.text_right)
    public void onClick() {
        Intent intent = new Intent(this, UsRegistAct.class);
        startActivity(intent);
        finish();
    }

    @OnClick({R.id.login, R.id.btn_fb, R.id.img_back,R.id.forget_password,R.id.img_google_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                hideInput(editPwd);
                Login();
                break;
            case R.id.btn_fb:
                hideInput(editPwd);
                facebookLogin();
                break;
            case R.id.img_back:
                setExitSwichLayout();
                break;
            case R.id.forget_password:
                entryForgetPassword();
                break;
            case R.id.img_google_login:
                googleSignIn(this);
                break;
        }
    }

    @Override
    public void textChanged() {
        if (!Utils.isStringEmpty(eidtUserName.getText().toString()) && !Utils.isStringEmpty(editPwd.getText().toString())){
            btnLogin.setEnabled(true);
        }else {
            btnLogin.setEnabled(false);
        }
    }
}
