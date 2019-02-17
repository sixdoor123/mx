package com.baiyi.jj.app.utils;

import android.content.Context;

import com.turbo.turbo.mexico.R;

import java.util.ArrayList;
import java.util.List;

public class ChannelIconManager {


    private Context context;

    private static ChannelIconManager instance;

    public ChannelIconManager(Context context) {
        this.context = context;
    }


    public static ChannelIconManager getInstance(Context context) {
        if (instance == null) {
            instance = new ChannelIconManager(context);
        }
        return instance;
    }

    public int getIconOn(int cid) {

        int iconId = 0;
        switch (cid) {
            case 26090:
                iconId = R.drawable.icon2_channel26090;
                break;
            case 26087:
                iconId = R.drawable.icon2_channel26087;
                break;
            case 26085:
                iconId = R.drawable.icon2_channel26085;
                break;
            case 26084:
                iconId = R.drawable.icon2_channel26084;
                break;
            case 26083:
                iconId = R.drawable.icon2_channel26083;
                break;
            case 26088:
                iconId = R.drawable.icon2_channel26088;
                break;
            case 26091:
                iconId = R.drawable.icon2_channel26091;
                break;
            case 26086:
                iconId = R.drawable.icon2_channel26086;
                break;
            case 26082:
                iconId = R.drawable.icon2_channel26082;
                break;
            case 26093:
                iconId = R.drawable.icon2_channel26093;
                break;
            case 26092:
                iconId = R.drawable.icon2_channel26092;
                break;
            case 26089:
                iconId = R.drawable.icon2_channel26089;
                break;

        }
        return iconId;

    }

    public int getIconUn(int cid) {

        int iconId = 0;
        switch (cid) {
            case 26090:
                iconId = R.drawable.icon_channel26090;
                break;
            case 26087:
                iconId = R.drawable.icon_channel26087;
                break;
            case 26085:
                iconId = R.drawable.icon_channel26085;
                break;
            case 26084:
                iconId = R.drawable.icon_channel26084;
                break;
            case 26083:
                iconId = R.drawable.icon_channel26083;
                break;
            case 26088:
                iconId = R.drawable.icon_channel26088;
                break;
            case 26091:
                iconId = R.drawable.icon_channel26091;
                break;
            case 26086:
                iconId = R.drawable.icon_channel26086;
                break;
            case 26082:
                iconId = R.drawable.icon_channel26082;
                break;
            case 26093:
                iconId = R.drawable.icon_channel26093;
                break;
            case 26092:
                iconId = R.drawable.icon_channel26092;
                break;
            case 26089:
                iconId = R.drawable.icon_channel26089;
                break;

        }
        return iconId;

    }
}
