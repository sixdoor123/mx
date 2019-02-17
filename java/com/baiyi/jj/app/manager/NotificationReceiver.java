package com.baiyi.jj.app.manager;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.baiyi.jj.app.activity.MorningNewsListAct;
import com.baiyi.jj.app.activity.NewsLocalAct;
import com.baiyi.jj.app.activity.NewsVideoAct;
import com.baiyi.jj.app.utils.ArticleHistoryUtils;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

/**
 * Created by Administrator on 2017/2/24 0024.
 */
public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        Intent launchIntent = context.getPackageManager().
                getLaunchIntentForPackage(NotifactionManager.packageStr);
        launchIntent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        Bundle args = intent.getBundleExtra(Define.EXTRA_BUNDLE);
        launchIntent.putExtra(Define.EXTRA_BUNDLE, args);

        String array = args.getString(Define.CallbackArray);
//        TLog.e("array22","---"+array.toString());
        if (!Utils.isStringEmpty(array)){
            try {
                JSONArray jsonArray = new JSONArray(array);
                OneSignal.handleNotificationOpen(context,jsonArray,false);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        context.startActivity(launchIntent);

        if (isAppAlive(context, NotifactionManager.packageStr)) {
            if (args != null) {
                String type = args.getString(Define.NoticaType);
                if (!Utils.isStringEmpty(type) && type.equals("1")){
                    Intent intent1 = new Intent(context, MorningNewsListAct.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent1.setAction(String.valueOf(System.currentTimeMillis()));
                    context.startActivity(intent1);
                    return;
                }
                //如果bundle存在，取出其中的参数，启动DetailActivity
                String url = args.getString(Define.ArticleUrl);
                String artiId = args.getString(Define.ArticleId);
                String message = args.getString(Define.ArticleTitle);
                String template = args.getString(Define.ArticleTemp);
                String source = args.getString(Define.ArticleSource);
                Intent newIntent = new Intent(context, NewsLocalAct.class);
                if (template.equals(String.valueOf(NotifactionManager.tem_video))){
                    if (!Utils.isStringEmpty(url) && url.contains("v=")){
                        newIntent = new Intent(context, NewsVideoAct.class);
                        String videoid = url.substring(url.indexOf("v=")+2);
                        newIntent.putExtra(Define.VideoId,videoid);
                    }
                }
                newIntent.putExtra(Define.ArticleUrl, url);
                newIntent.putExtra(Define.ArticleId, artiId);
                newIntent.putExtra(Define.ArticleTitle, message);
                newIntent.putExtra(Define.ArticleSource, source);
                newIntent.putExtra(Define.ArticleType, ArticleHistoryUtils.ArticleType_News);
                newIntent.putExtra(Define.IsPush, true);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                newIntent.setAction(String.valueOf(System.currentTimeMillis()));
                context.startActivity(newIntent);
            }
        }else {
//            TLog.e("absn","zhihsshhhhhhhhhhhhhhhhhhhhhh");
        }


    }

    public   static boolean isAppAlive(Context context, String packageName){
        ActivityManager activityManager =
                (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        if (Utils.isStringEmpty(processInfos)){
            return false;
        }
        for(int i = 0; i < processInfos.size(); i++){
            if(processInfos.get(i).processName.equals(packageName)){
//                Log.i("NotificationLaunch",
//                        String.format("the %s is running, isAppAlive return true", packageName));
                return true;
            }
        }
//        Log.i("NotificationLaunch",
//                String.format("the %s is not running, isAppAlive return false", packageName));
        return false;
    }
}
