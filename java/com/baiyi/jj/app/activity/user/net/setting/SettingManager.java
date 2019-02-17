package com.baiyi.jj.app.activity.user.net.setting;

import com.baiyi.jj.app.activity.user.config.MemberConfig;
import com.baiyi.jj.app.activity.user.entity.LanguageEntities;
import com.baiyi.jj.app.activity.user.net.register.entity.StateEnties;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.utils.JsonParseBase;
import com.baiyi.jj.app.utils.TLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/17.
 */

public class SettingManager extends JsonParseBase {

    //������Ϣ
    public static UserInfoEntity getMemberInfo(JSONArray array,int UserType) {
        try {
            UserInfoEntity entities;
            if (CmsApplication.getUserInfoEntity() == null){
                entities = new UserInfoEntity();
            }else {
                entities = CmsApplication.getUserInfoEntity();
            }

            for (int i = 0; i < array.length(); i++) {

                JSONObject jsonObject = array.getJSONObject(i);
                int state = jsonObject.getInt("state");
                entities.setState(state);

                if (200 == state) {
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int j = 0; j < data.length(); j++) {
                        JSONObject object = data.getJSONObject(j);

                        entities.setAvatar(getStringNodeValue(object, "avatar"));
                        entities.setDisplay(getStringNodeValue(object, "display"));
                        entities.setFirst_name(getStringNodeValue(object, "first_name"));
                        entities.setName(getStringNodeValue(object,"name"));
                        entities.setIntegral(getIntNodeValue(object, "integral"));
                        entities.setJob(getStringNodeValue(object, "job"));
                        entities.setEmail(getStringNodeValue(object,"email"));
                        entities.setUserType(UserType);
//                        entities.setLanguage(LanguageUtils.getLocaleLanguage(PreferenceUtil.getString(MemberConfig.Language, MemberConfig.EN)));
//                        entities.setCountry(CountryUtils.getCurrCountryName(PreferenceUtil.getString(MemberConfig.Language, "")));
                        entities.setCountry(getStringNodeValue(object,"country"));
                        entities.setLast_name(getStringNodeValue(object, "last_name"));
                        entities.setLocation(getStringNodeValue(object, "location"));
                        entities.setMobile(getStringNodeValue(object, "mobile"));
                        entities.setPaypal(getStringNodeValue(object, "paypal"));
                        entities.setMID(getStringNodeValue(object, "userid"));

                    }

                } else {
                    JSONObject object = jsonObject.getJSONObject("data");
                    entities.setMsg(object.getString("msg"));
                }
            }

            return entities;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * �޸�����
     *
     * @param oldPwd
     * @param newPwd
     * @return
     */
    public static String getModifyPwdPostData(String oldPwd, String newPwd) {

        try {
            JSONObject object = new JSONObject();
            object.put("oldpassword", oldPwd);
            object.put("newpassword", newPwd);

            return object.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getModifyPayPwdPostData(String oldPwd, String newPwd) {

        try {
            JSONObject object = new JSONObject();
            object.put("old_pya_pwd", oldPwd);
            object.put("new_pya_pwd", newPwd);

            return object.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  修改PayPal账号
     * @return
     */
    public static String getModifyPayPalPostData(String paypal, String paw) {

        try {
            JSONObject object = new JSONObject();
            object.put("new_paypal", paypal);
            object.put("pya_pwd", paw);

            return object.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * �����б�
     * {"data": [{"id": 0, "name": "English"},
     * {"id": 1, "name": "Chinese"},
     * {"id": 2, "name": "Spanish"},
     * {"id": 3, "name": "French"}]}
     *
     * @param jsonArray
     */
    public static List<LanguageEntities> getLanguageListData(JSONArray jsonArray) {

        List<LanguageEntities> languages = new ArrayList<LanguageEntities>();
        try {

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                JSONArray array = jsonObject.getJSONArray("data");


                for (int j = 0; j < array.length(); j++) {

                    JSONObject object = array.getJSONObject(j);

                    LanguageEntities entities = new LanguageEntities();
                    entities.setId(getStringNodeValue(object, "id"));
                    entities.setName(getStringNodeValue(object, "name"));

                    languages.add(entities);
                }
            }

            return languages;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getModifyLanguagePostData(LanguageEntities selLanguage) {
        try {
            return new JSONObject().put("language", selLanguage.getId()).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getModifyLanguagePostData2(int id) {
        try {
            return new JSONObject().put("language", id).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * �޸����ԵĽ��
     * {"state": 200, "data": {"msg": "Successful"}}
     *
     * @param array
     */
    public static StateEnties getModifyLanguageResult(JSONArray array) {

        try {
            StateEnties e = new StateEnties();
            for (int i = 0; i < array.length(); i++) {

                JSONObject js = array.getJSONObject(i);

                e.setState(getIntNodeValue(js,"state"));

                JSONObject data = js.getJSONObject("data");

                e.setMsg(getStringNodeValue(data,"msg"));
            }

            return e;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  null;
    }

    /**
     * �޸Ĺ���
     * @param selCountry
     * @return
     */
    public static String getModifyCountryPostData(LanguageEntities selCountry) {
        try {
            return new JSONObject().put("country", selCountry.getId()).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * forget pwd send email
     * @param email
     * @return
     */
    public static String getSendEmailPostData(String email) {
        try {
            return new JSONObject().put("email",email).toString();
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * modify username
     * @param name
     * @return
     */
    public static String getModifyUsernamePostData(String name) {
        try {
            return new JSONObject().put("username",name).toString();
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Forget pwd reset pwd
     * @param email
     * @param emailCode
     * @param newPws1
     * @return
     */
    public static String getForgetPwdPostDdata(String email, String emailCode, String newPws1) {

        try {
            JSONObject object = new JSONObject();
            object.put("email",email);
            object.put("verification_code",emailCode);
            object.put("newpassword",newPws1);

            System.out.println("Forget Pwd  :  "+object.toString());
            return  object.toString();

        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  忘记支付密码
     * @return
     */
    public static String getForgetPayPwdPostDdata(String emailCode, String payPwd) {

        try {
            JSONObject object = new JSONObject();
            object.put("verification_code",emailCode);
            object.put("pya_pwd",payPwd);

            System.out.println("Forget Pwd  :  "+object.toString());
            return  object.toString();

        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Modify Name
     * @param firstName
     * @return
     */
    public static String getModifyNamePostData(String firstName) {

        try {
            JSONObject object = new JSONObject();
            object.put("name",firstName);
            return object.toString();
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Other Login
     * @param loginType
     * @param token
     * @param mobileCode
     * @return
     */
    public static String getOtherLoginPostData(String loginType, String token, String mobileCode) {
        try {
            JSONObject object = new JSONObject();
            object.put("login_type",loginType);
            object.put("access_token",token);
            object.put("mobile_code",mobileCode);
            TLog.e("logstr",object.toString());
            return object.toString();
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Modify Email
     * @param email
     * @param emailCode
     * @return
     */
    public static String getModifyEmailPoastData(String email, String emailCode) {

        try {
            JSONObject object = new JSONObject();
            object.put("new_email",email);
            object.put("verification_code",emailCode);

            return object.toString();
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static StateEnties getOtherLoginResult(JSONArray array) {

            try {
                StateEnties stateEnties = new StateEnties();
                for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                    stateEnties.setState(getIntNodeValue(object,"state"));
                    if (200 != stateEnties.getState())
                    {
                        stateEnties.setMsg(getStringNodeValue(object,"msg"));
                    }
                }

                return stateEnties;
            } catch (JSONException e) {
                e.printStackTrace();
            }
       return null;
    }

}
