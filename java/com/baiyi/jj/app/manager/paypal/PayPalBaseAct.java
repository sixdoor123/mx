package com.baiyi.jj.app.manager.paypal;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.utils.TLog;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalOAuthScopes;
import com.paypal.android.sdk.payments.PayPalProfileSharingActivity;
import com.paypal.android.sdk.payments.PayPalService;
import com.turbo.turbo.mexico.R;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2016/11/26 0026.
 */
public class PayPalBaseAct extends BaseActivity{


    protected static final int REQUEST_CODE_PAYMENT = 1;
    protected static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    protected static final int REQUEST_CODE_PROFILE_SHARING = 3;

    @Override
    protected void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(hasScrollView, isAnimal);

        startService();

    }


    private void startService(){
        Intent intent = new Intent(this, PayPalService.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        startService(intent);

        onGetPayPalInfo();
    }

    public void onGetPayPalInfo() {
//        Intent intent = new Intent(PayPalAct.this, LoginActivity.class);
        Intent intent = new Intent(PayPalBaseAct.this, PayPalProfileSharingActivity.class);
        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PayPalProfileSharingActivity.EXTRA_REQUESTED_SCOPES, getOauthScopes());
//        intent.putExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION, config);
        startActivityForResult(intent, REQUEST_CODE_FUTURE_PAYMENT);

    }
    private PayPalOAuthScopes getOauthScopes() {
        Set<String> scopes = new HashSet<>(
                Arrays.asList(PayPalOAuthScopes.PAYPAL_SCOPE_EMAIL, PayPalOAuthScopes.PAYPAL_SCOPE_ADDRESS) );
        return new PayPalOAuthScopes(scopes);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (data == null){
                return;
            }
            PayPalAuthorization auth = data
                    .getParcelableExtra(PayPalProfileSharingActivity.EXTRA_RESULT_AUTHORIZATION);
            if (auth != null) {
                String authorization_code = auth.getAuthorizationCode();
//                Log.e("json","-sss-"+auth.toJSONObject());
                sendAuthorizationToServer(auth);
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            TLog.i("FuturePaymentExample", "The user canceled.");
            finish();
        } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
            TLog.i("FuturePaymentExample",
                    "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
        }
    }

    private void sendAuthorizationToServer(PayPalAuthorization authorization) {
        startPro();
        new PayPayNet(new PayPayNet.OnPayPalCallBack() {
            @Override
            public void PayPayCall(final PayPalUserInfo userInfo) {

//                    displayToast(getResources().getString(R.string.txt_paypal_getinfo));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        stopPro();
                        if (userInfo != null && !userInfo.getEmail().isEmpty()){
                            onGetToken(userInfo);
                        }else {
                            displayToast(getResources().getString(R.string.txt_paypal_getinfo));
                        }

                    }
                });

            }
        }).loadGetToken(PayConfig.CONFIG_CLIENT_ID,PayConfig.CONFIG_Secret,authorization.getAuthorizationCode(),PayPalBaseAct.this);

    }

    public void onGetToken(PayPalUserInfo userInfo){
        TLog.e("user","userinfo---------------");
    }
    public void startPro(){
        TLog.e("user","startPro---------------");
    }
    public void stopPro(){
        TLog.e("user","stopPro---------------");
    }


    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }


    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayConfig.CONFIG_ENVIRONMENT)
            .clientId(PayConfig.CONFIG_CLIENT_ID)
            // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Example Merchant")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));
}
