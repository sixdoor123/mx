package com.baiyi.jj.app.activity.user.center;

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
import com.baiyi.jj.app.activity.user.config.MemberConfig;
import com.baiyi.jj.app.activity.user.net.register.entity.StateEnties;
import com.baiyi.jj.app.activity.user.net.setting.SettingManager;
import com.baiyi.jj.app.activity.user.net.setting.SettingUrl;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.entity.UserInfoEntity;
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
public class NameSetAct extends BaseActivity {
    @Bind(R.id.title_name)
    LetterSpacingTextView titleName;
    @Bind(R.id.edit_name)
    EditText editName;
    @Bind(R.id.loading)
    MyLoadingBar loading;
    @Bind(R.id.btn_save_name)
    Button btnSave;

    private String name;

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);

        View titleView = ContextUtil.getLayoutInflater(this).inflate(R.layout.title_left, null);
        win_title.addView(titleView);
        View contentView = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_name_us, null);
        win_content.addView(contentView);
        ButterKnife.bind(this);

        titleName.setVisibility(View.VISIBLE);
        titleName.setText(getResources().getString(R.string.title_account));
//        titleName.setTypeface(CmsApplication.getTitleFace(this));

        UserInfoEntity entity = CmsApplication.getUserInfoEntity();
        if (null != entity) {
            editName.setText(entity.getName());
        }
        editName.addTextChangedListener(watcher);
        if (Utils.isStringEmpty(editName.getText().toString())) {
            btnSave.setEnabled(false);
        }
    }

    @Override
    public void textChanged() {
        if (!Utils.isStringEmpty(editName.getText().toString())) {
            btnSave.setEnabled(true);
        } else {
            btnSave.setEnabled(false);
        }
    }

    /**
     * �޸�
     * Name reset
     */
    private void loadData() {

        name = editName.getText().toString().trim();
        if (name.length()<4||name.length()>20) {
            displayMsgBox(getResources().getString(R.string.tip_dialog_title_name), getResources()
                    .getString(R.string.tip_first_name_required));
            return;
        }
        submitData();
    }

    private void submitData() {
        startLoading();

        JsonLoader loader = new JsonLoader(this);
        loader.setUrl(SettingUrl.getModifyNameUrl());
        loader.setUrlName(MemberConfig.Modify_Name);
        loader.setType(BaseNetLoder.APPLICATION_JSON);
        loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
        loader.setPostData(SettingManager.getModifyNamePostData(name));
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

                StateEnties enties = SettingManager.getModifyLanguageResult(array);
                if (200 != enties.getState()) {
                    displayToast(getString(R.string.txt_save_fail));
                    return;
                }
//                displayToast(getString(R.string.txt_save_success));

                CmsApplication.getUserInfoEntity().setName(name);

                finish();
            }
        });
        CmsApplication.getDataStratey().startLoader(loader);
    }

    @OnClick({R.id.img_back, R.id.btn_save_name})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                setExitSwichLayout();
                break;
            case R.id.btn_save_name:
                hideInput(editName);
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
