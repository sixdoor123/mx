package com.baiyi.jj.app.manager;

import android.content.Context;
import android.content.Intent;

import com.baiyi.core.file.Preference;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.main.fragment.NewsFragment;
import com.baiyi.jj.app.activity.main.fragment.UserFragment;
import com.baiyi.jj.app.activity.main.task.WebLoadManager;
import com.baiyi.jj.app.activity.main.task.WebTask;
import com.baiyi.jj.app.cache.Dao.WebDetailDao;
import com.baiyi.jj.app.cache.bean.WebDetailBean;
import com.baiyi.jj.app.dialog.UpdateDialog;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.utils.XMLName;
import com.baiyi.jj.core.basedialog.DialogBase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/7/3 0003.
 */
public class UpdateAppManager {


    public static void loadGooglePlayVer(final Context context) {

        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .url(NetUtils.getUpdateVer())
                .get()
                .build();
        ConnectionPool pool = mOkHttpClient.connectionPool();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                if (Utils.isStringEmpty(str)){
                    return;
                }
                try {
                    JSONObject object = new JSONObject(str);
                    JSONObject dataJson = object.getJSONObject("data");
                    final String ver = dataJson.optString("ver","");
                    String currentVer = Config.getInstance().getVersionName(context);
                    if (Utils.isStringEmpty(ver)){
                        return;
                    }
                    if (currentVer.contains("-")){
                        currentVer = currentVer.substring(0, currentVer.indexOf("-"));
                    }
//                    TLog.e("version",ver+"========"+currentVer+"======="+compareVersion(ver,currentVer));
                    if(compareVersion(ver,currentVer) <=0){
                        return;
                    }

                    sendCast(context);
                    Preference preference = Preference.getInstance();
                    if (preference.getBoolean(XMLName.XML_SkipVerion+currentVer,false)){
                        return;
                    }
                    TLog.e("update",ver+"------"+currentVer);
                    ((BaseActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            UpdateDialog dialog = new UpdateDialog(context,ver, DialogBase.Win_Center);
                            dialog.showDialog(DialogBase.AnimalTop);
                        }
                    });
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });
    }

    private static void sendCast(Context context) {
        Intent intent = new Intent();
        intent.setAction(UserFragment.NewsVersionAction);
        context.sendBroadcast(intent);
    }

    //0代表相等，1代表version1大于version2，-1代表version1小于version2
    public static int compareVersion(String version1, String version2) {
        if (version1.equals(version2)) {
            return 0;
        }
        String[] version1Array = version1.split("\\.");
        String[] version2Array = version2.split("\\.");
//        TLog.d("HomePageActivity", "version1Array=="+version1Array.length);
//        TLog.d("HomePageActivity", "version2Array=="+version2Array.length);
        int index = 0;
        // 获取最小长度值
        int minLen = Math.min(version1Array.length, version2Array.length);
        int diff = 0;
        // 循环判断每位的大小
//        TLog.d("HomePageActivity", "verTag2=2222="+version1Array[index]);
        while (index < minLen
                && (diff = Integer.parseInt(version1Array[index])
                - Integer.parseInt(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            // 如果位数不一致，比较多余位数
            for (int i = index; i < version1Array.length; i++) {
                if (Integer.parseInt(version1Array[i]) > 0) {
                    return 1;
                }
            }
            for (int i = index; i < version2Array.length; i++) {
                if (Integer.parseInt(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }

}
