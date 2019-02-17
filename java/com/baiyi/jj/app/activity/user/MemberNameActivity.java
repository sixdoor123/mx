package com.baiyi.jj.app.activity.user;

import android.content.Intent;
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
import com.baiyi.jj.app.activity.user.center.EmailSetAct;
import com.baiyi.jj.app.activity.user.config.MemberConfig;
import com.baiyi.jj.app.activity.user.net.register.entity.StateEnties;
import com.baiyi.jj.app.activity.user.net.setting.SettingManager;
import com.baiyi.jj.app.activity.user.net.setting.SettingUrl;
import com.baiyi.jj.app.application.CmsApplication;
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

public class MemberNameActivity extends BaseActivity {

    @Bind(R.id.title_name)
    TextView titleName;
    @Bind(R.id.loading)
    MyLoadingBar loading;
    @Bind(R.id.edit_account_name)
    EditText mEdtName;
    @Bind(R.id.btn_save_member_name)
    Button btnSave;


    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);

        View titleView = ContextUtil.getLayoutInflater(this).inflate(R.layout.title_left, null);
        win_title.addView(titleView);
        View contentView = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_member_name, null);
        win_content.addView(contentView);
        ButterKnife.bind(this);

//        titleName.setTypeface(CmsApplication.getTitleFace(this));
        titleName.setText(R.string.txt_account);
        mEdtName.setText(CmsApplication.getUserInfoEntity().getDisplay());
        mEdtName.addTextChangedListener(watcher);
        if (Utils.isStringEmpty(mEdtName.getText().toString())) {
            btnSave.setEnabled(false);
        }
    }

    @Override
    public void textChanged() {
        if (!Utils.isStringEmpty(mEdtName.getText().toString())) {
            btnSave.setEnabled(true);
        } else {
            btnSave.setEnabled(false);
        }
    }

    @OnClick({R.id.img_back, R.id.btn_save_member_name})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                setExitSwichLayout();
                break;
            case R.id.btn_save_member_name:
                hideInput(mEdtName);
                changeName();
                break;
        }
    }

    private String name;

    private void changeName() {

        name = mEdtName.getText().toString().trim();
        if (name.length() < 6 || name.length() > 20) {
            displayMsgBox(getResources().getString(R.string.tip_dialog_title_user_name), getResources()
                    .getString(R.string.tip_user_name_required));
            return;
        }
        startLoading();

        JsonLoader loader = new JsonLoader(this);
        loader.setUrl(SettingUrl.getModifyUsernameUrl());
        loader.setType(BaseNetLoder.APPLICATION_JSON);
        loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
        loader.setPostData(SettingManager.getModifyUsernamePostData(name));
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
                displayToast(getString(R.string.txt_save_fail));
            }

            @Override
            public void onCompelete(Object tag, Object result) {
                // TODO Auto-generated method stub
                JSONArray array = (JSONArray) result;
                stopLoading();
                StateEnties stateEnties = SettingManager.getModifyLanguageResult(array);
                if (200 == stateEnties.getState()) {
                    CmsApplication.getUserInfoEntity().setDisplay(name);
//                    displayToast(getString(R.string.txt_save_success));
                    setExitSwichLayout();
                } else {
                    displayToast(getString(R.string.txt_save_fail));
                }
            }
        });
        CmsApplication.getDataStratey().startLoader(loader);
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

    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional
     * data from it.  The <var>resultCode</var> will be
     * {@link #RESULT_CANCELED} if the activity explicitly returned that,
     * didn't return any result, or crashed during its operation.
     * <p/>
     * <p>You will receive this call immediately before onResume() when your
     * activity is re-starting.
     * <p/>
     * <p>This method is never invoked if your activity sets
     * <code>true</code>.
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
     * @see #startActivityForResult
     * @see #createPendingResult
     * @see #setResult(int)
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == MemberConfig.Reset_Password_Success) {
            setExitSwichLayout();
        }

        if (resultCode == MemberConfig.Modify_Email_ResultCode) {
            setResult(MemberConfig.Modify_Email_ResultCode);
            setExitSwichLayout();
        }
    }
}
