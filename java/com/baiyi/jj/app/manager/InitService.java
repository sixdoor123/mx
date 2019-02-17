package com.baiyi.jj.app.manager;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.baiyi.core.file.Preference;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.activity.user.config.MemberConfig;
import com.baiyi.jj.app.language.PreferenceUtil;
import com.baiyi.jj.app.language.SwitchLanguageUtils;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.utils.XMLName;
import com.facebook.FacebookSdk;
import com.google.android.gms.ads.MobileAds;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OSPermissionSubscriptionState;
import com.onesignal.OneSignal;
import com.tencent.bugly.crashreport.CrashReport;
import com.turbo.turbo.mexico.R;

import org.json.JSONObject;

import java.io.File;

/**
 * Created by Administrator on 2017/6/23 0023.
 */
public class InitService extends IntentService{

    private static final String ACTION_INIT_WHEN_APP_CREATE = "com.anly.githubapp.service.action.INIT";
    private static final String ACTION_INIT_WHEN_HomeAct_CREATE = "com.anly.githubapp.service.action.HOMEINIT";

    public InitService() {
        super("InitializeService");
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, InitService.class);
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        context.startService(intent);
    }

    public static void startHomeinit(Context context) {
        Intent intent = new Intent(context, InitService.class);
        intent.setAction(ACTION_INIT_WHEN_HomeAct_CREATE);
        context.startService(intent);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT_WHEN_APP_CREATE.equals(action)) {
                performInit();
            }else if (ACTION_INIT_WHEN_HomeAct_CREATE.equals(action)){
                initHomeAct();
            }
        }
    }

    private void initHomeAct(){
        MobileAds.initialize(this, getResources().getString(R.string.googleads_app_id));
    }

    private void performInit() {
        TLog.d("performInit begin:" + System.currentTimeMillis());

        //�����ϴε��������ã�������������
        SwitchLanguageUtils.switchLanguage(getApplicationContext(),PreferenceUtil.getString(MemberConfig.Language, ""));
        FacebookSdk.sdkInitialize(getApplicationContext());

        /*
			����������ΪSDK����ģʽ���أ�����ģʽ����Ϊ�������£�
			�� �����ϸ��Bugly SDK��Log��
			�� ÿһ��Crash���ᱻ�����ϱ���
			�� �Զ�����־������Logcat�������
 			�����ڲ��Խ׶ν������ó�true������ʱ����Ϊfalse��
 			900059040��ӡ�ȣ�   29afcd90ce��������  ed3cc34e66��ī���磩
		 */
        CrashReport.initCrashReport(getApplicationContext(), "ed3cc34e66", false);


        TLog.d("performInit end:" + System.currentTimeMillis());
    }
}

