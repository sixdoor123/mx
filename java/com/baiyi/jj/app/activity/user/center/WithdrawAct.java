package com.baiyi.jj.app.activity.user.center;

import android.content.Intent;
import android.renderscript.Double2;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.user.entity.IntegalEntity;
import com.baiyi.jj.app.activity.user.net.JsonParse_User;
import com.baiyi.jj.app.activity.user.net.NetUrl;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.LetterSpacingTextView;
import com.baiyi.jj.app.views.MyLoadingBar;
import com.turbo.turbo.mexico.R;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 提现界面
 * Created by tangkun on 16/9/8.
 */
public class WithdrawAct extends BaseActivity {

    @Bind(com.turbo.turbo.mexico.R.id.title_name)
    LetterSpacingTextView titleName;
    @Bind(com.turbo.turbo.mexico.R.id.tv_available)
    TextView tvAvailable;
    @Bind(com.turbo.turbo.mexico.R.id.edit_withdraw)
    EditText edtWithdraw;
    @Bind(com.turbo.turbo.mexico.R.id.btn_next)
    Button btnNext;
    @Bind(R.id.loading)
    MyLoadingBar progressBar;

    public static  double IntegralRate = 0.0002;

    private float availMoney = 0.00f;

    IntegalEntity integalEntity;

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);

        View titleView = ContextUtil.getLayoutInflater(this).inflate(com.turbo.turbo.mexico.R.layout.title_left, null);
        win_title.addView(titleView);

        View contentView = ContextUtil.getLayoutInflater(this).inflate(com.turbo.turbo.mexico.R.layout.activity_withdraw, null);
        win_content.addView(contentView);
        ButterKnife.bind(this);

        titleName.setText(getResources().getString(com.turbo.turbo.mexico.R.string.txt_withdraw));
//        titleName.setTypeface(CmsApplication.getTitleFace(this));
        btnNext.setPressed(true);
        btnNext.setClickable(false);


        loadIntegal();


    }


    private void initAvailableNum(IntegalEntity integalEntity){
        stopLoading(progressBar);
        UserInfoEntity entity = CmsApplication.getUserInfoEntity();

        if (integalEntity == null){
            integalEntity = new IntegalEntity();
            integalEntity.setCurrency("$");
            integalEntity.setExchange(0.0002);
        }

        if (entity != null){
            availMoney = (float) (entity.getIntegral()*integalEntity.getExchange());
        }
        tvAvailable.setText(integalEntity.getCurrency()+ " "+Utils.getPoint2Float(availMoney));
        edtWithdraw.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);

        edtWithdraw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (edtWithdraw.getText().toString().indexOf(".") == 0){
                    edtWithdraw.setText("0.");
                    edtWithdraw.setSelection(edtWithdraw.getText().toString().length());
                }
                if (edtWithdraw.getText().toString().indexOf(".") > 0 && edtWithdraw.getText().length()>2) {
                    // 1.22  1 4 3
                    // 222.33  3 6 3
                    if (edtWithdraw.getText().toString().length() - edtWithdraw.getText().toString().indexOf(".") >3) {
                        edtWithdraw.setText(edtWithdraw.getText().toString().substring(0, edtWithdraw.getText().toString().length() - 1));
                        edtWithdraw.setSelection(edtWithdraw.getText().toString().length());
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String InputStr = s.toString();
                if (Utils.isStringEmpty(InputStr)){
                    btnNext.setPressed(true);
                    btnNext.setClickable(false);
                    return;
                }
                if (InputStr.indexOf(".") == InputStr.length()-1){
                    InputStr = InputStr+"0";
                }
                float InputMoney = Float.parseFloat(InputStr);
                if (InputMoney<=0){
                    btnNext.setPressed(true);
                    btnNext.setClickable(false);
                    return;
                }
                if (InputMoney > availMoney){
                    displayToast(getResources().getString(
                            R.string.txt_edit_hint38));
                    btnNext.setPressed(true);
                    btnNext.setClickable(false);
//                    edtWithdraw.setFilters(new InputFilter[]{new InputFilter.LengthFilter(String.valueOf(availMoney).toString().length())});
                    return;
                }
                if (InputMoney>10000){
                    displayToast(getResources().getString(
                            R.string.tip_point_10000));
                    btnNext.setPressed(true);
                    btnNext.setClickable(false);
                    return;
                }
                btnNext.setPressed(false);
                btnNext.setClickable(true);
            }
        });
    }


    /**
     * 跳转到输入密码验证界面
     */
    private void goWithdrawPwd() {

        Intent intentWithdrawSet = new Intent(WithdrawAct.this, WithdrawPwdAct.class);
        intentWithdrawSet.putExtra(Define.CashPoints, Float.parseFloat(edtWithdraw.getText().toString()));
        startActivity(intentWithdrawSet);
        setExitSwichLayout();

    }

    @OnClick({com.turbo.turbo.mexico.R.id.img_back, com.turbo.turbo.mexico.R.id.btn_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case com.turbo.turbo.mexico.R.id.img_back:
                setExitSwichLayout();
                break;
            case com.turbo.turbo.mexico.R.id.btn_next:
                goWithdrawPwd();
                break;
        }
    }

    private void loadIntegal(){

        startLoading(progressBar,"");

        UserInfoEntity infoEntity = CmsApplication.getUserInfoEntity();
        OkHttpClient httpClient = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .url(NetUrl.getIntegal(infoEntity.getCountry()))
                .get()
                .header("Content-Type","application/json")
                .header("Authorization", CmsApplication.getUserToken())
                .build();
        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                stopLoading(progressBar);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String Json = response.body().string();
                TLog.e("abc","json-==="+Json);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String jsonstr = Json;
                        integalEntity = JsonParse_User.getIntegalEntity(jsonstr);
                        initAvailableNum(integalEntity);
                        if (integalEntity == null){
                            return;
                        }
                        IntegralRate = integalEntity.getExchange();
                    }
                });


            }
        });
    }


}
