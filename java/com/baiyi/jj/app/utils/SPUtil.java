package com.baiyi.jj.app.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 15-5-22.
 */
public class SPUtil {

    private static SPUtil spUtil;
    private SharedPreferences sharedPreferences;
    //共享配置中的文件名，和数据库表名一样，有 用，没 创建
    private static final String SP_NAME = "config";
//    public static final String KEY_NAME = "isFirst";

    private SPUtil(Context context){
        //
        sharedPreferences = context.getSharedPreferences(SP_NAME,Context.MODE_PRIVATE);
    }
    public static SPUtil getInstance(Context context){
        if(null == spUtil){
            spUtil = new SPUtil(context);
        }
        return spUtil;
    }
    public boolean isFirst(String name){
        //若KEY_NAME不存在，默认true
        return sharedPreferences.getBoolean(name,true);
    }
    public void setNotFirst(String name){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(name,false);
        //提交，一定要写，否则没内容，加不进去
        editor.commit();
    }

    public void delete()
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.remove(Define.Is_First);
        editor.commit();
    }

    public void setIsFirst(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(name,true);
        //提交，一定要写，否则没内容，加不进去
        editor.commit();
    }
}
