package com.baiyi.jj.app.manager;

import android.content.Context;

import com.facebook.ads.NativeAdView;
import com.google.android.gms.ads.AdRequest;
import com.turbo.turbo.mexico.R;

/**
 * Created by Administrator on 2017/7/4 0004.
 */
public class AdsManager {

    public static final float AD_HEIGHT_Small = 0.28f;
    public static final float AD_HEIGHT_Medium = 0.37f;
    public static final float AD_HEIGHT_Large = 0.89f;
    public static final int AD_TYPE_Small = 1;
    public static final int AD_TYPE_Medium = 2;
    public static final int AD_TYPE_Large = 3;
    public static final int AD_TYPE_XLarge = 4;

    public static AdsManager instance;
    Context context;

    public AdsManager(Context context) {
        this.context = context;
    }
    public static AdsManager getInstance(Context context) {
        if (instance == null) {
            instance = new AdsManager(context);
        }
        return instance;
    }

    public int getAdHeight(int templete, int adWidth) {

        if (templete == AD_TYPE_Small) {
            return (int) (adWidth * AD_HEIGHT_Small);
        } else if (templete == AD_TYPE_Medium) {
            return (int) (adWidth * AD_HEIGHT_Medium);
        } else if (templete == AD_TYPE_Large) {
            return (int) (adWidth * AD_HEIGHT_Large);
        } else {
            return (int) (adWidth * AD_HEIGHT_Small);
        }
    }

    public String getAdUnitID(int templete) {

        if (templete == AD_TYPE_Small) {
            return context.getResources().getString(R.string.googleads_unitid_small);
        } else if (templete == AD_TYPE_Medium) {
            return context.getResources().getString(R.string.googleads_unitid_medium);
        } else if (templete == AD_TYPE_Large) {
            return context.getResources().getString(R.string.googleads_unitid_large);
        } else {
            return context.getResources().getString(R.string.googleads_unitid_small);
        }
    }

    public NativeAdView.Type getFaceADsType(int templete){
        if (templete == AD_TYPE_Small) {
            return NativeAdView.Type.HEIGHT_100;
        } else if (templete == AD_TYPE_Medium) {
            return NativeAdView.Type.HEIGHT_120;
        } else if (templete == AD_TYPE_Large) {
            return NativeAdView.Type.HEIGHT_300;
        }  else if (templete == AD_TYPE_XLarge) {
            return NativeAdView.Type.HEIGHT_400;
        } else {
            return NativeAdView.Type.HEIGHT_100;
        }
    }
}
