package com.baiyi.jj.app.sevice;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 2017/4/1 0001.
 */
public class ReStartAPPTool {


    /**
     * ��������APP
     *
     * @param context
     * @param Delayed �ӳٶ��ٺ���
     */
    public static void restartAPP(Context context, long Delayed) {

        /**����һ���µķ�������������APP*/
        Intent intent1 = new Intent(context, killSelfService.class);
        intent1.putExtra("PackageName", context.getPackageName());
        intent1.putExtra("Delayed", Delayed);
        context.startService(intent1);

        /**ɱ����������**/
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /***
     * ��������APP
     */
    public static void restartAPP(Context context) {
        restartAPP(context, 1000);
    }
}

