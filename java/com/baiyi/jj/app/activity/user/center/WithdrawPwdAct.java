package com.baiyi.jj.app.activity.user.center;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.user.config.MemberConfig;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.JsonParseBase;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.views.LetterSpacingTextView;
import com.baiyi.jj.app.views.MyLoadingBar;
import com.turbo.turbo.mexico.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.baiyi.jj.app.utils.JsonParseBase.getIntNodeValue;

/**
 * 提现密码验证
 * Created by tangkun on 16/9/8.
 */
public class WithdrawPwdAct extends BaseActivity {
    @Bind(R.id.title_name)
    LetterSpacingTextView titleName;
    @Bind(R.id.text_right)
    TextView textRight;
    @Bind(R.id.loading)
    MyLoadingBar loading;
    @Bind(R.id.eidt_withdraw_pwd)
    EditText eidtWithdrawPwd;

    float num = 0;

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);

        initData();

        View titleView = ContextUtil.getLayoutInflater(this).inflate(R.layout.title_left, null);
        win_title.addView(titleView);
        View contentView = ContextUtil.getLayoutInflater(this).inflate(R.layout.activity_withdraw_us, null);
        win_content.addView(contentView);
        ButterKnife.bind(this);

        titleName.setText(getResources().getString(R.string.txt_withdraw));
//        titleName.setTypeface(CmsApplication.getTitleFace(this));
        textRight.setVisibility(View.GONE);
//        textRight.setTextColor(getResources().getColor(R.color.word_white_red));
//        textRight.setText(getResources().getString(R.string.txt_button26));

    }

    /**
     * 初始化提现金额
     */
    private void initData() {

        num = getIntent().getFloatExtra(Define.CashPoints,0);

    }

    /**
     * 提现
     */
    private void loadData() {

        startLoading();
        JsonLoader loader = new JsonLoader(this);
        loader.setUrl(NetUtils.getWithdrawUrl());
        loader.setPostData(getPostData());
        //Bearer O4ShnkgHW5F1a4F0bhd3ZlsUHNu58C
        Log.d("TAG", "Authorization:" + CmsApplication.getUserToken());
        loader.addRequestHeader(Constant.HEAD_NAME, CmsApplication.getUserToken());
        loader.setType(BaseNetLoder.APPLICATION_JSON);
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
                JSONArray array = (JSONArray) result;
                if (JsonParseBase.getState200(array)) {
                    //提现成功，修改本地的积分信息
                    CmsApplication.getUserInfoEntity().setIntegral( (int)( CmsApplication.getUserInfoEntity().getIntegral()-num/WithdrawAct.IntegralRate));
                    setExitSwichLayout();
                    displayToast(getString(R.string.txt_tixian1));
                } else {
                    try {
                        JSONObject object = array.getJSONObject(0);
                        int state = JsonParse.getIntState(object);
                        if (state == 500){
                            displayToast(getString(R.string.txt_tixian_tip1));
                        }else if (state == 501){
                            displayToast(getString(R.string.txt_tixian_tip2));
                        }else if (state == 502){
                            displayToast(getString(R.string.txt_tixian_tip3));
                        }else if (state == 503){
                            displayToast(getString(R.string.txt_tixian_tip4));
                        }else if (state == 504){
                            displayToast(getString(R.string.tip_point_large));
                        }else if (state == 505){
                            displayToast(getString(R.string.txt_tixian_tip5));
                        }else if (state == 506){
                            displayToast(getString(R.string.txt_tixian_tip5));
                        }else {
                            displayToast(getString(R.string.txt_tixian_tip1));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }
            }


        });
        CmsApplication.getDataStratey().startLoader(loader);
    }

    /**
     * 获取post数据
     *
     * @return
     */
    private String getPostData() {

        JSONObject object = new JSONObject();
        try {
            object.put("amount", num);
            object.put("pay_pwd", eidtWithdrawPwd.getText().toString());
//            object.put("client_id", "VpL2mTHlynQdHbHNFbjH2ajFuvSBGZ7pTXbfLaB0");
            object.put("client_id", Config.getDeviceId(this));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    @OnClick({R.id.img_back, R.id.btn_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                setExitSwichLayout();
                break;
            case R.id.btn_save:
                hideInput(eidtWithdrawPwd);
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
