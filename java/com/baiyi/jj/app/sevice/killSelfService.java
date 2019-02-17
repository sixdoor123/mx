package com.baiyi.jj.app.sevice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

/**
 * Created by Administrator on 2017/4/1 0001.
 */
public class killSelfService extends Service {
    /**�ر�Ӧ�ú�����������*/
    private static  long stopDelayed=1000;
    private Handler handler;
    private String PackageName;
    public killSelfService() {
        handler=new Handler();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        stopDelayed=intent.getLongExtra("Delayed",1000);
        PackageName=intent.getStringExtra("PackageName");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.turbo.turbo.mexico");
                startActivity(LaunchIntent);
                killSelfService.this.stopSelf();
            }
        },stopDelayed);
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
