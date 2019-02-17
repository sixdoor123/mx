package com.baiyi.jj.app.manager;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.baiyi.core.file.Preference;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.attention.net.JsonAttention;
import com.baiyi.jj.app.activity.user.config.MemberConfig;
import com.baiyi.jj.app.activity.user.net.NetUrl;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.application.accont.PageSet;
import com.baiyi.jj.app.cache.Dao.ChannelDao;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.turbo.turbo.mexico.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/1/6 0006.
 */
public class CountryMannager {

    //国家 0.美国 1.印度
    private int Current_Country;

    public static final int Country_US = 0;
    public static final int Country_India = 1;
    public static final int Country_Test = 2;
    public static final int Country_Mexico = 3;

    public static final String Country_Str_US = "U.S.";
    public static final String Country_Str_India = "IN";
    public static final String Country_Str_Mexico = "MX";
    public static final String Country_Str_Test = "Test";

    private Preference pref = null;

    private static CountryMannager instance = null;

    private CountryMannager()
    {
        initPreference();
        Current_Country = pref.getInt(PageSet.Xml_Current_Country, Country_US);
    }
    public void initPreference()
    {
        if(pref == null)
        {
            pref = Preference.getInstance();
        }
    }

    public String getCurrentCountryStr(){
        if (Current_Country == Country_US){
            return Country_Str_US;
        }else if (Current_Country == Country_India){
            return Country_Str_India;
        }else if (Current_Country == Country_Mexico){
            return Country_Str_Mexico;
        }else if (Current_Country == Country_Test){
            return Country_Str_Test;
        }else {
            return Country_Str_US;
        }
    }

    public int getCurrentCountry() {
        return Current_Country;
    }

    public void setCurrent_Country(int current_Country) {
        Current_Country = current_Country;
        pref.Set(PageSet.Xml_Current_Country, String.valueOf(current_Country));
        pref.saveConfig();
    }

    public static CountryMannager getInstance()
    {
        if(instance == null)
        {
            instance = new CountryMannager();
        }
        return instance;
    }

    public interface CoutryCallBack{
        void callback(boolean isSuc);
    }

    public void initCurrentCountry(Activity activity,CoutryCallBack coutryCallBack){
//        TLog.e("ip","ip-=------------"+GetWifiIP(activity));
        loadIpCountry(activity,coutryCallBack);
        loadIp(activity);
    }

    public void loadIpCountry(final Context context, final CoutryCallBack coutryCallBack) {

        JsonLoader loader = new JsonLoader(context);

        TLog.e("time","time---"+System.currentTimeMillis());

        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .get()
                .url(NetUrl.getIpCountry())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (coutryCallBack!=null){
                    coutryCallBack.callback(false);
                }
                TLog.e("time","time3---"+System.currentTimeMillis());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                TLog.e("time","time2---"+System.currentTimeMillis());
                TLog.e("tag",json);
                try {
                    JSONObject object = new JSONObject(json);
                    String coutry = "";
                    if (JsonParse.getState200(object)){
                        JSONObject dataObj = JsonParse.getResultObj(object);
                        coutry = JsonParse.getStringNodeValue(dataObj,"country_id");
                        if (coutry.equals(Country_Str_India)){
                            setCurrent_Country(Country_India);
                        }else  if (coutry.equals(Country_Str_Mexico)){
                            setCurrent_Country(Country_Mexico);
                        }else {
                            setCurrent_Country(Country_US);
                        }
                        if (coutryCallBack!=null){
                            coutryCallBack.callback(true);
                        }
                    }else {
                        if (coutryCallBack!=null){
                            coutryCallBack.callback(false);
                        }
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        });

    }

    private void loadIp(Context context){
        TLog.e("time","jsontime1---"+System.currentTimeMillis());
        JsonLoader loader = new JsonLoader(context);
        loader.setUrl(NetUrl.getIpCountry());
        loader.setMethod(BaseNetLoder.Method_Get);
        loader.setLoaderListener(new Loader.LoaderListener() {

            @Override
            public void onProgress(Object arg0, long arg1, long arg2) {

            }

            @Override
            public void onError(Object arg0, int arg1, String arg2) {
                TLog.e("time","jsontimeerror---"+System.currentTimeMillis());
            }

            @Override
            public void onCompelete(Object arg0, Object arg1) {
                // TODO Auto-generated method  stub
                TLog.e("time","jsontime2---"+System.currentTimeMillis());
                JSONArray array = (JSONArray) arg1;
                TLog.e("array",array.toString());
            }
        });
        CmsApplication.getDataStratey().startLoader(loader);
    }


    private  String GetWifiIP(Activity activity){
        WifiManager wifiManager = (WifiManager) activity.getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
//            wifiManager.setWifiEnabled(true);
            return getLocalIpAddress();
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);
        return ip;
    }

    private String intToIp(int i) {

        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }

    private String getLocalIpAddress()
    {
        try
        {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
            {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
                {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress())
                    {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        }
        catch (SocketException ex)
        {
            TLog.e("WifiPreference IpAddress", ex.toString());
        }
        return null;
    }
}
