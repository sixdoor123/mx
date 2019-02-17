package com.baiyi.jj.app.activity.user.net.setting;

import com.baiyi.jj.app.Config;

/**
 * Created by Administrator on 2016/9/17.
 */

public class SettingUrl {

    public static String getMemberInfoUrl()
    {
        return Config.getNewURL()+"api/v1/member/";
    }

    /**
     * 修改登录密码
     * @return
     */
    public static String getModifyPwdUrl() {
        return Config.getNewURL()+"api/v1/member/password/";
    }

    /**
     *  修改网银密码
     * @return
     */
    public static String getModifyPayPwdUrl() {
        return Config.getNewURL()+"api/v1/member/paypal/update/password/";
    }
    /**
     *  修改绑定网银
     * @return
     */
    public static String getModifyPayPalUrl() {
        return Config.getNewURL()+"api/v1/member/paypal/update/account/";
    }

    /**
     * �����б�
     * @return
     */
    public static String getLanguageListUrl() {
        return Config.getNewURL()+"api/v1/member/language/";
    }

    /**
     * �޸�����
     * @return
     */
    public static String getModifyLanguageUrl() {
        return Config.getNewURL() +"api/v1/member/language/update/";
    }

    /**
     * ��ȡ�����б�
     * @return
     */
    public static String getCountryListUrl() {
        return Config.getNewURL() +"api/v1/member/country/";
    }

    /**
     * �޸Ĺ���
     * @return
     */
    public static String getModifyCountryUrl() {
        return Config.getNewURL()+"api/v1/member/country/update/";
    }

    /**
     * 忘记密码
     * 发送忘记登录密码邮件
     * @return
     */
    public static String getSendEmailVerificationUrl() {
        return Config.getNewURL() +"api/v1/member/send/email/forgot/password/";
    }

    /**
     * forget pwd
     * @return
     */
    public static String getSendForgetPwdVerificationCodeUrl() {
        return Config.getNewURL()+"api/v1/member/send/email/forgot/password/";
    }

    /**
     * Modify Name
     * @return
     */
    public static String getModifyNameUrl() {
//        return Config.getNewURL()+"api/v1/member/name/set/";
        return Config.getNewURL()+"api/v1/member/name/update/";

    }

    /**
     * Modify UserName
     * @return
     */
    public static String getModifyUsernameUrl() {
        return Config.getNewURL()+"api/v1/member/username/update/";
    }

    /**
     * other login
     * @return
     */
    public static String getOtherLoginUrl() {
        return Config.getNewURL()+"api/v1/auth/third_party/";
    }

    /**
     * modify email
     * @return
     */
    public static String getSendEmailUrl()
    {
        return Config.getNewURL()+"api/v1/member/send/email/code/";
    }
    /**
     * Modify Email
     * @return
     */
    public static String getModifyEmailUrl() {
        return Config.getNewURL()+"api/v1/member/email/update/";
    }

    /**
     * 忘记登陆密码
     * @return
     */
    public static String getUpdatePasswordForgetUrl() {
        return Config.getNewURL()+"api/v1/member/forgot/password/";
    }
    /**
     * 忘记支付密码
     * @return
     */
    public static String getUpdatePayPwdForgetUrl() {
        return Config.getNewURL()+"api/v1/member/forgot/paypal/password/";
    }

    public static String getBindEmailUrl() {
        return Config.getNewURL()+"api/v1/member/send/email/bindemail/code/";
    }
}
