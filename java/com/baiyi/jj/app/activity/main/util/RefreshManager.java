package com.baiyi.jj.app.activity.main.util;

import android.util.Log;

import com.baiyi.core.file.Preference;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.application.accont.PageSet;

/**
 * Created by Administrator on 2016/10/27 0027.
 */
public class RefreshManager {

    private long lastTime;

    public int timeSpace = 3*60*60*1000;

    private Preference pref = null;

    public static final  String LASTTIME = "LASTTIME";

    private static RefreshManager instance = null;

    private RefreshManager(){
        initPreference();
        lastTime = pref.getLong(LASTTIME,0);
    }

    public void initPreference()
    {
        if(pref == null)
        {
            pref = Preference.getInstance();
        }
    }

    public static RefreshManager getInstance()
    {
        if(instance == null)
        {
            instance = new RefreshManager();
        }
        return instance;
    }

    public  boolean isRefresh(long currentTime){
        if (currentTime-lastTime > timeSpace){
            return true;
        }else {
            return false;
        }
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
        pref.Set(LASTTIME, String.valueOf(lastTime));
        pref.saveConfig();

    }
}
