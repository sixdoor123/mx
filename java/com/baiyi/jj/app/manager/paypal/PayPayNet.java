package com.baiyi.jj.app.manager.paypal;

import android.content.Context;

import com.baiyi.jj.app.Constant;
import com.baiyi.jj.app.activity.user.net.NetUrl;
import com.baiyi.jj.app.utils.Base64;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/10/8 0008.
 */
public class PayPayNet {

    public PayPayNet(OnPayPalCallBack payPalCallBack) {
        this.payPalCallBack = payPalCallBack;
    }

    public void loadGetToken(String clint_id, String secret, String authCode,final Context context) {

        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .build();
        RequestBody formBody = new FormBody.Builder()
                .add("grant_type", "authorization_code")
                .add("response_type", "token")
                .add("redirect_uri", "urn:ietf:wg:oauth:2.0:oob")
                .add("code", authCode)
                .build();
        String code = clint_id + ":" + secret;
        String authStr = new String(Base64.encode(code.getBytes()));
        //("Content-Type","application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .url(NetUrl.getPaypalToken())
                .post(formBody)
                .header("Authorization", "Basic " + authStr)
                .header(Constant.ContentType, Constant.ContentType_form)
                .build();


        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                if (payPalCallBack!=null){
//                    payPalCallBack.PayPayCall(null);
//                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
//                Log.i("wangshu", str);
//                Gson gson = new Gson();
                PayPayToken tokenEntity = JsonPay.getUserToken(str);
                if (tokenEntity != null) {
                    loadUserInfo("openid", tokenEntity);
                    String token = tokenEntity.getTokenType() + " " + tokenEntity.getAccessToken();
                }
            }

        });

    }
    public void loadUserInfo(String openid, PayPayToken token) {

        // 这段主要是针对android4.4以下版本不支持SSL协议，http接口不用这么麻烦
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .url(NetUrl.getPaypalInfo(openid))
                .get()
                .header(Constant.ContentType, Constant.ContentType_Json)
                .header("Authorization", token.getTokenType() + " " + token.getAccessToken())
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (payPalCallBack!=null){
                    payPalCallBack.PayPayCall(null);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
//                Log.e("str",str);
                PayPalUserInfo userEntity = JsonPay.getUserInfo(str);
                if (userEntity == null){
                    payPalCallBack.PayPayCall(null);
                    return;
                }
                if (payPalCallBack!=null){
                    payPalCallBack.PayPayCall(userEntity);
                }
            }

        });

    }


    private  OnPayPalCallBack payPalCallBack;

    public OnPayPalCallBack getPayPalCallBack() {
        return payPalCallBack;
    }

    public interface OnPayPalCallBack{
        public void PayPayCall(PayPalUserInfo userInfo);
    }

}
