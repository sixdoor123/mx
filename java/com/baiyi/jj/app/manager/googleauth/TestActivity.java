//package com.baiyi.jj.app.manager.googleauth;
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.FragmentActivity;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.TextView;
//
//import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.SignInButton;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.turbo.turbo.mexico.R;
//
//public class TestActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
//    private GoogleApiClient mGoogleApiClient;
//    private SignInButton sign_in_button;
//    private static int RC_SIGN_IN = 10001;
//    private TextView tv_1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.test_gauth);
//        String clintId = BaiduPushManager.getMetaValue(TestActivity.this, "google_webclint_id");
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestId().requestIdToken(clintId).build();
//        mGoogleApiClient = new GoogleApiClient.Builder(this).
//                addConnectionCallbacks(this).
//                addOnConnectionFailedListener(this).
//                enableAutoManage(this, this)/* FragmentActivity *//* OnConnectionFailedListener */.
//                addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
//        tv_1 = (TextView) findViewById(R.id.tv_1);
//        sign_in_button = (SignInButton) findViewById(R.id.sign_in_button);
//        sign_in_button.setSize(SignInButton.SIZE_STANDARD);
//        sign_in_button.setScopes(gso.getScopeArray());
//        sign_in_button.setOnClickListener(this);
//    }
//
//    private void signIn() {
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//        startActivityForResult(signInIntent, RC_SIGN_IN);
//    }
//
//    private void handleSignInResult(GoogleSignInResult result) {
//        Log.i("robin", "handleSignInResult:" + result.isSuccess());
//        if (result.isSuccess()) {
//            Log.i("robin", "Success");
//            GoogleSignInAccount acct = result.getSignInAccount();
//            if (acct != null) {
//                Log.i("robin", "username:" + acct.getDisplayName());
//                Log.i("robin", "email:" + acct.getEmail());
//                Log.i("robin", "head:" + acct.getPhotoUrl());
//                Log.i("robin", "Id:" + acct.getId());
//                //之后就可以更新UI了
//                Log.i("robin", "IdToken:" + acct.getIdToken());
//                tv_1.setText("username:" + acct.getDisplayName() + "\nemail:" + acct.getEmail() + "\nhead:" + acct.getPhotoUrl() + "\nId:" + acct.getId() + "\nIdToken:" + acct.getIdToken());
//            }
//        } else {
//            tv_1.setText("login failure");
//            Log.i("robin", "shibai" + result.getStatus());
//        }
//    }
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        Log.i("robin", "googlelogin-->onConnected,bundle==" + bundle);
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//        Log.i("robin", "googlelogin-->onConnectionSuspended,i==" + i);
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Log.i("robin", "googlelogin-->onConnectionFailed,connectionResult==" + connectionResult);
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        mGoogleApiClient.connect();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (mGoogleApiClient.isConnected()) {
//            mGoogleApiClient.disconnect();
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.i("robin", "requestCode==" + requestCode + ",resultCode==" + resultCode + ",data==" + data);
//        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            handleSignInResult(result);
//        }
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.sign_in_button: {
//                Log.i("robin", "dianjilogin");
//                signIn();
//                break;
//            }
//        }
//    }
//}