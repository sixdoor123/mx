package com.baiyi.jj.app.activity.user;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.user.center.EmailSetAct;
import com.baiyi.jj.app.activity.user.config.MemberConfig;
import com.baiyi.jj.app.activity.user.net.register.entity.StateEnties;
import com.baiyi.jj.app.activity.user.net.setting.SettingManager;
import com.baiyi.jj.app.activity.user.net.setting.SettingUrl;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.EmailUtils;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.MyLoadingBar;
import com.turbo.turbo.mexico.R;

import org.json.JSONArray;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/20.
 */

public class ValidateEmailActivity extends BaseActivity
{

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.loading)
    MyLoadingBar loading;
    @Bind(R.id.eidt_email)
    EditText mEdtEmail;
    @Bind(R.id.btn_send_email)
    Button btnSend;

    private String entrySetEmail;

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);

        View titleView = ContextUtil.getLayoutInflater(this).inflate(R.layout.title_left, null);
        win_title.addView(titleView);
        View contentView = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_validate_email, null);
        win_content.addView(contentView);
        ButterKnife.bind(this);

        entrySetEmail = getIntent().getStringExtra(MemberConfig.Key);
//        titleName.setTypeface(CmsApplication.getTitleFace(this));
        if (MemberConfig.Modify_Email.equals(entrySetEmail))
        {
            UserInfoEntity entity = CmsApplication.getUserInfoEntity();
            if (entity == null)
            {
                if (null != entity)
                {
                    titleName.setText(getResources().getString(R.string.tip_title_modify_email));
                    mEdtEmail.setText(entity.getEmail());
                    mEdtEmail.setEnabled(false);
                }
                else
                {
                    titleName.setText(getResources().getString(R.string.tip_bind_email_title));
                    mEdtEmail.setText("");
                    mEdtEmail.setEnabled(true);
                }
            }
            else
            {
                titleName.setText(getResources().getString(R.string.tip_title_modify_email));
                mEdtEmail.setText(entity.getEmail());
                mEdtEmail.setEnabled(Utils.isStringEmpty(entity.getEmail()));
            }
        }else if (entrySetEmail.equals(MemberConfig.Forgot_LoginPwd))
        {
            titleName.setText(getResources().getString(R.string.name_title_find_pwd));
        }else if (entrySetEmail.equals(MemberConfig.Forgot_PayPwd))
        {
            titleName.setText(getResources().getString(R.string.txt_paypal_forgetpwd));
            if (CmsApplication.getUserInfoEntity() != null){
                mEdtEmail.setText(CmsApplication.getUserInfoEntity().getEmail());
                mEdtEmail.setEnabled(false);
            }
        }
        mEdtEmail.addTextChangedListener(watcher);
        if (Utils.isStringEmpty(mEdtEmail.getText().toString())) {
            btnSend.setEnabled(false);
        }
    }

    @Override
    public void textChanged() {
        if (!Utils.isStringEmpty(mEdtEmail.getText().toString())){
            btnSend.setEnabled(true);
        }else {
            btnSend.setEnabled(false);
        }
    }

    @OnClick({R.id.img_back,R.id.btn_send_email})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.img_back:
                setExitSwichLayout();
                break;
            case R.id.btn_send_email:
                sendEmail();
                break;
        }
    }

    private String email;
    /**
     * �����ʼ�
     */
    private void sendEmail() {

        email = mEdtEmail.getText().toString().trim();

        if (!EmailUtils.isEmail(email))
        {
            displayMsgBox(getResources().getString(R.string.txt_my_email), getResources()
                    .getString(R.string.resgister_email_wrong));
            return;
        }

        startLoading();

        JsonLoader loader = new JsonLoader(this);
            //api/v1/member/send/email/forgot/password/
        if (MemberConfig.Modify_Email.equals(entrySetEmail))
        {
            if (!mEdtEmail.isEnabled())
            {
                loader.setUrl(SettingUrl.getSendEmailUrl());
            }
            else
            {
                loader.setUrl(SettingUrl.getBindEmailUrl());
            }
        }else {
            loader.setUrl(SettingUrl.getSendForgetPwdVerificationCodeUrl());
        }
        loader.setUrlName(MemberConfig.Send_Verification_Code);
        loader.setType(BaseNetLoder.APPLICATION_JSON);
        loader.addRequestHeader(Constant.HEAD_NAME,CmsApplication.getUserToken());
        loader.setPostData(SettingManager.getSendEmailPostData(email));
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
                displayToast(getResources().getString(R.string.tip_verification_code_send_fail));
            }

            @Override
            public void onCompelete(Object tag, Object result) {
                // TODO Auto-generated method stub
                JSONArray array = (JSONArray) result;
                stopLoading();
                System.out.println("Verification Code : "+array);
                StateEnties stateEnties = SettingManager.getModifyLanguageResult(array);
                if (null != stateEnties)
                {
                    int state = stateEnties.getState();
                    if (200 == state)
                    {
//                        displayToast(getResources().getString(R.string.tip_verification_code_send_success));
                        Intent intent = null;
                        if (MemberConfig.Modify_Email.equals(entrySetEmail))
                        {
                            intent = new Intent(ValidateEmailActivity.this, EmailSetAct.class);

                        }else {
                             intent = new Intent(ValidateEmailActivity.this, ForgetPwdSetPwdActivity.class);
                        }
                        intent.putExtra(Define.Code, email);
                        intent.putExtra(MemberConfig.Is_Bind_Email,mEdtEmail.isEnabled());
                        intent.putExtra(MemberConfig.Key,entrySetEmail);
                        startActivityForResult(intent, MemberConfig.Member_RequestCode);
                    }
                    else
                    {
                        displayToast(getResources().getString(R.string.tip_verification_code_send_fail));
                        return;
                    }
                }
            }
        });
        CmsApplication.getDataStratey().startLoader(loader);
    }

    private void startLoading()
    {
        loading.setVisibility(View.VISIBLE);
        loading.setProgressInfo(getResources().getString(R.string.txt_progress_loading));
        loading.start();
    }
    private void stopLoading()
    {
        if (loading != null)
        {
            loading.stop();
            loading.setVisibility(View.GONE);
        }
    }

    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional
     * data from it.  The <var>resultCode</var> will be
     * {@link #RESULT_CANCELED} if the activity explicitly returned that,
     * didn't return any result, or crashed during its operation.
     *
     * <p>You will receive this call immediately before onResume() when your
     * activity is re-starting.
     *
     * <p>This method is never invoked if your activity sets
     * <code>true</code>.
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode The integer result code returned by the child activity
     *                   through its setResult().
     * @param data An Intent, which can return result data to the caller
     *               (various data can be attached to Intent "extras").
     *
     * @see #startActivityForResult
     * @see #createPendingResult
     * @see #setResult(int)
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == MemberConfig.Reset_Password_Success)
        {
            setExitSwichLayout();
        }

        if (resultCode == MemberConfig.Modify_Email_ResultCode)
        {
            setResult(MemberConfig.Modify_Email_ResultCode);
            setExitSwichLayout();
        }
    }
}
