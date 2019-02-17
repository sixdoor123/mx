package com.baiyi.jj.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.baiyi.jj.app.activity.main.news.AdRender;
import com.baiyi.jj.app.application.AnalyticsApplication;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.utils.TLog;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.turbo.turbo.mexico.R;


/**
 * Created by Administrator on 2016/9/23 0023.
 */

public class BaseAnalyticsActivity extends BaseSkinFragmentActivity{

    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CmsApplication application = (CmsApplication) getApplication();
        mTracker = application.getDefaultTracker();
    }

    public void sendDownLoadTest_Detail(){
        String articleTest = "https://play.google.com/store/apps/details?id=com.turbo.turbo.mexico&referrer=utm_source%3Dshare_web%26utm_medium%3Dreferral%26utm_campaign%3Dshare_story";
        mTracker.send(new HitBuilders.ScreenViewBuilder()
                .setCampaignParamsFromUrl(articleTest)
                .build());
    }
    public void sendDownLoadTest_Tofriend(){
        String articleTest = "https://play.google.com/store/apps/details?id=com.turbo.turbo.mexico&referrer=utm_source%3Dshare_web%26utm_medium%3Dreferral%26utm_campaign%3Dshare_app";
        mTracker.send(new HitBuilders.ScreenViewBuilder()
                .setCampaignParamsFromUrl(articleTest)
                .build());
    }

    /**
     *  ������Ļ��Ϣ
     * @param ScreenName
     */
    public void sendScreenImageName(String ScreenName) {

        // [START screen_view_hit]
        mTracker.setScreenName("" + ScreenName);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        // Enable Display  .
        mTracker.enableAdvertisingIdCollection(true);
        // [END screen_view_hit]
    }

    @Override
    protected void onStart() {
        super.onStart();
        //EasyTracker.getInstance(this).activityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //EasyTracker.getInstance(this).activityStop(this);
    }

    /**
     * �������ʱ��ͳ��
     * @param screenName
     */
    public void addRequestTime(String screenName, String categoryName, String varialeName,long value) {
        // Build and send timing.
        mTracker.send(new HitBuilders.TimingBuilder()
                .setCategory(categoryName)
                .setValue(value)
                .setVariable(varialeName)
                .setLabel(screenName)
                .build());
    }

    public void addEnvent(String action) {
        // Build and send timing.
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(Envent_Cage_1)
                .setValue(1)
                .setAction(action)
                .build());
    }
    public void addEnvent(String action,String lable) {
        // Build and send timing.
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(BaseAnalyticsActivity.Envent_Cage_1)
                .setValue(1)
                .setLabel(lable)
                .setAction(action)
                .build());
    }
    public void addEnventFirst(String action,String lable) {
        // Build and send timing.
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(Envent_Cage_3)
                .setValue(1)
                .setLabel(lable)
                .setAction(action)
                .build());
    }
    public void addEnventFirst(String action) {
        // Build and send timing.
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(Envent_Cage_3)
                .setValue(1)
                .setAction(action)
                .build());
    }





    public static final  String Category_net = "NetWork Request";
    public static final  String Category_init = "First Open Init";
    public static final  String Category_web = "Load Web";
    public static final  String Category_img = "Load Image";
    public static final  String Net_Tag = "Android_Tag_Pull";
    public static final  String Net_Creatgust = "Android_Create_Guest";
    public static final  String Net_Login = "Android_Login";
    public static final  String Net_Get_Member = "Android_Get_Member";
    public static final  String Net_Register = "Android_Register";
    public static final  String Net_Article_Pull = "Android_Article_Pull";
    public static final  String Net_Get_Channel = "Android_Get_Channel";
    public static final  String Web_Load_DetailUrl = "Android_Web_Load_DetailUrl";
    public static final  String Web_Load_DetailPageUrl = "Web_Load_SourcePageUrl";
    public static final  String Web_Load_DetailContent = "Web_Load_DetailContent";
    public static final  String Img_Load_SmallImage = "Web_Load_SmallImage";
    public static final  String Img_Load_LargeImage = "Web_Load_LargeImage";
    public static final  String Init_FirstOpen = "Android_Init_FirstOpen124";
    public static final  String Web_Load_Detail_Interface= "Web_Load_Detail_Interface";
    public static final  String Web_Load_Detail_Video= "Web_Load_Detail_Video";


    public static final  String Envent_Switch_Language= "Envent_Switch_Language";
    public static final  String Envent_Click_Offline = "Envent_Click_Offline";
    public static final  String Envent_Click_OfflineDone = "Envent_Click_OfflineDone";
    public static final  String Envent_Pull_Refresh = "Envent_Pull_Refresh";
    public static final  String Envent_Enter_Detail = "Envent_Enter_Detail";
    public static final  String Envent_First_Enter_NewsList = "Envent_First_Enter_NewsList";
    public static final  String Envent_Cage_1 = "lumenEnvent";
    public static final  String Envent_Cage_2 = "FirstIN_Envent";
    public static final  String Envent_Cage_3 = "FirstIN_Envent0627";
    public static final  String Envent_ReceiveNotif = "Envent_NotifReceive";
    public static final  String Envent_ReceiveNotiUser = "Envent_ReceiveNotiUser";
    public static final  String Envent_ClickNotif = "Envent_NotifClick";
    public static final  String Envent_List_VideoClick = "Envent_List_VideoClick";
    public static final  String Envent_detail_VideoClick = "Envent_Detail_VideoClick";

    public static final  String Envent_share_News = "Envent_share_News";
    public static final  String Envent_share_APP = "Envent_share_APP";
    public static final  String Envent_Frist_LogoIn = "Envent_Frist_LogoIn";
    public static final  String Envent_Frist_LogoOut = "Envent_Frist_LogoOut";
    public static final  String Envent_Close_Noti = "Envent_Close_Noti";
    public static final  String Envent_INITUser_SUC = "Envent_INITUser_SUC";
    public static final  String Envent_INITUser_FAI = "Envent_INITUser_FAI";
    public static final  String Envent_Click_MorningPager = "Envent_Click_MorningPager";
    public static final  String Envent_InitChannel_Skip = "Envent_InitChannel_Skip";
    public static final  String Envent_InitChannel_Subscr = "Envent_InitChannel_Subscr";
    public static final  String Envent_InitChannel_KeyDown = "Envent_InitChannel_KeyDown";

//    public static final  String Envent_Lable_PlanB= "Envent_Lable_PlanB";
//    public static final  String Envent_Lable_PlanA= "Envent_Lable_PlanA";
    public static final  String Envent_PlanA_Yes= "Envent_PlanA_Yes";
    public static final  String Envent_PlanB_No= "Envent_PlanB_No";
//    public static final  String Envent_EnterHome= "Envent_EnterHome";
    public static final  String Envent_PlanA_Yes_Enter= "Envent_PlanA_Yes_Enter";
    public static final  String Envent_PlanB_No_Enter= "Envent_PlanB_No_Enter";
}
