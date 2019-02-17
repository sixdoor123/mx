package com.baiyi.jj.app.activity.user.config;

/**
 * Created by Administrator on 2016/9/18.
 */

public class MemberConfig
{
    //请求吗
    public static final int Member_RequestCode = 0;
    //语言的结果码
    public static final int Language_ResultCode = 1;
    public static final String Language = "language";
    public static final String System_Language = "sysLanguage";

    //国家的结果码
    public static final int Country_ResultCode = 2;
    public static final String Country = "country";

    //登录成功
    public static final int Login_Success = 3;

    //重置密码成功
    public static final int Reset_Password_Success = 4;

    //key
    public static final String Key = "key";
    //由修改邮箱进入
    public static final String Modify_Email = "Modify_Email";
    //修改邮箱后返回
    public static final int Modify_Email_ResultCode = 6;

    //由找回登录密码进入
    public static final String Forgot_LoginPwd = "Forgot_LoginPwd";
    //由找回支付密码进入
    public static final String Forgot_PayPwd = "TypePay";

    public static final String App_Country = "appCountry";
    public static final String Name = "name";

    public static final String My_Collect_Is_Refresh = "isRefresh";
    public static final int Refresh_My_Collect = 9;
    public static final String My_Collect_Entry = "MyCollectEntry";
    public static final String My_Collect = "myCollect";

    public static final int Paypal_Chanage_Success = 10;

    public static final String Member_Info="Member Info";
    public static final String Login = "Login";
    public static final String Other_Login = "Other Login";
    public static final String Modify_Language = "Modify Language";
    public static final String Get_Country_List = "Get Country List";
    public static final String Modify_Country = "Modify Country";
    public static final String Get_Language_List = "Get Language List";
    public static final String Modify_Name = "Modify Name";
    public static final String Modify_Password = "Modify Password" ;
    public static final String Register = "Register" ;
    public static final String Send_Verification_Code = "Send Verification Code ";
    public static  final String Is_Bind_Email ="isBindEmail";
}
