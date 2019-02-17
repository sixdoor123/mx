package com.baiyi.jj.app.manager.paypal;

//import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/10/8 0008.
 */
public class PayPayToken {
//    @SerializedName("scope")
    private String Scope;
//    @SerializedName("nonce")
    private String Nonce;
//    @SerializedName("access_token")
    private String AccessToken;
//    @SerializedName("token_type")
    private String TokenType;
//    @SerializedName("expires_in")
    private int ExpriresIn;
//    @SerializedName("refresh_token")
    private String RefreshToken;

    public String getScope() {
        return Scope;
    }

    public void setScope(String scope) {
        Scope = scope;
    }

    public String getNonce() {
        return Nonce;
    }

    public void setNonce(String nonce) {
        Nonce = nonce;
    }

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String accessToken) {
        AccessToken = accessToken;
    }

    public String getTokenType() {
        return TokenType;
    }

    public void setTokenType(String tokenType) {
        TokenType = tokenType;
    }

    public int getExpriresIn() {
        return ExpriresIn;
    }

    public void setExpriresIn(int expriresIn) {
        ExpriresIn = expriresIn;
    }

    public String getRefreshToken() {
        return RefreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        RefreshToken = refreshToken;
    }
}
