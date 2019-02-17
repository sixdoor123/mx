package com.baiyi.jj.app.manager.paypal;

import com.baiyi.jj.app.utils.JsonParseBase;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/10/8 0008.
 */
public class JsonPay extends JsonParseBase {


    public static PayPalUserInfo getUserInfo(String obj) {

        PayPalUserInfo info = new PayPalUserInfo();
        try {
            JSONObject o = new JSONObject(obj);
            if (!o.isNull("error")){
                return null;
            }
            info.setAccountType(getStringNodeValue(o, "account_type"));
            info.setBirthday(getStringNodeValue(o, "birthday"));
            info.setGivenName(getStringNodeValue(o, "given_name"));
            info.setLocale(getStringNodeValue(o, "locale"));
            info.setLanguage(getStringNodeValue(o, "language"));
            info.setEmail(getStringNodeValue(o, "email"));
            info.setName(getStringNodeValue(o, "name"));
            info.setFamilyName(getStringNodeValue(o, "family_name"));
            info.setPhoneNumber(getStringNodeValue(o, "phone_number"));
            info.setUserId(getStringNodeValue(o, "user_id"));
        } catch (JSONException e) {
        }
        return info;

    }

    public static PayPayToken getUserToken(String obj) {
        PayPayToken info = new PayPayToken();
        try {
            JSONObject o = new JSONObject(obj);
            info.setAccessToken(getStringNodeValue(o, "access_token"));
            info.setExpriresIn(getIntNodeValue(o, "expires_in"));
            info.setNonce(getStringNodeValue(o, "nonce"));
            info.setRefreshToken(getStringNodeValue(o, "refresh_token"));
            info.setScope(getStringNodeValue(o, "scope"));
            info.setTokenType(getStringNodeValue(o, "token_type"));
        } catch (JSONException e) {
        }
        return info;

    }
}
