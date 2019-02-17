package com.baiyi.jj.app.activity.main.news;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.cache.AdCacheManager;
import com.baiyi.jj.app.cache.AdCacheManager2;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.listitem.news.ListItemBaseNews;
import com.baiyi.jj.app.manager.AdsManager;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.NativeExpressAdView;
import com.turbo.turbo.mexico.R;

/**
 * Created by Administrator on 2017/6/20 0020.
 */
public class AdRender extends BaseHolder {


    private Context context;
    private LinearLayout cardView;
    private NativeExpressAdView adView;
    private View viewLineAd;

    private NativeAd nativeAd;
    private AdsManager adsManager;
    private View fbadView;

    public AdRender(View arg0, Context context) {
        super(arg0);
        this.context = context;
        adsManager = AdsManager.getInstance(context);
        initView(arg0);
    }

    private void initView(View contentView) {
        cardView = (LinearLayout) contentView.findViewById(R.id.ad_card_view);
        viewLineAd = contentView.findViewById(R.id.view_line_ad);
    }

    public void setDatas(int position, final NewsListEntity entity) {

        if (entity == null) {
            return;
        }
        viewLineAd.setVisibility(View.VISIBLE);

        if (Utils.isStringEmpty(entity.getArticle_source())){
           addGoogleADs(entity);
            return;
        }
        if (entity.getArticle_source().equals("google")){
            addGoogleADs(entity);
        }else if (entity.getArticle_source().equals("facebook")){
            addFacebookADs(entity);
        }


    }

    private void addFacebookADs(final NewsListEntity entity){

        fbadView = (View) AdCacheManager2.getInstance().getCaChe(entity.getDownLoadTime());

        if (fbadView == null){
            nativeAd = new NativeAd(context,context.getResources().getString(R.string.facebook_place_id));
            nativeAd.setAdListener(new com.facebook.ads.AdListener() {
                @Override
                public void onError(Ad ad, AdError adError) {
                    TLog.e("facebook","adderror-----"+adError.getErrorCode()+"----"+adError.getErrorMessage());
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    int template = Integer.parseInt(entity.getTemplate());
                    fbadView = NativeAdView.render(context, nativeAd, adsManager.getFaceADsType(template));
                    AdCacheManager2.getInstance().addCaChe(entity.getDownLoadTime(), fbadView);
                    TLog.e("facebook","addsuccess---------"+ad.getPlacementId());
                    if (cardView.getChildCount() > 0) {
                        cardView.removeAllViews();
                    }
                    if (fbadView.getParent() != null) {
                        ((ViewGroup) fbadView.getParent()).removeView(fbadView);
                    }
                    cardView.addView(fbadView);
                }
                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                }
            });
//        AdSettings.addTestDevice("f89ab1d7c2eef304b9f8738a52a5e425");
//        AdSettings.addTestDevice("fcc6b561f48c559b4a83801e4c3a23ec");
            nativeAd.loadAd();
        }else {
            if (cardView.getChildCount() > 0) {
                cardView.removeAllViews();
            }
            if (fbadView.getParent() != null) {
                ((ViewGroup) fbadView.getParent()).removeView(fbadView);
            }
            cardView.addView(fbadView);
        }

    }

    private void addGoogleADs(final NewsListEntity entity){
        adView = (NativeExpressAdView) AdCacheManager.getInstance().getCaChe(entity.getDownLoadTime());
        if (adView == null) {
            adView = new NativeExpressAdView(context);
            int imgW = Config.getInstance().getScreenWidth(context) - ContextUtil.dip2px(context, ListItemBaseNews.ThreeImg_Dip_Offset);
            final float scale = ((BaseActivity) context).density;
            int adW = (int) (imgW / scale);
            int template = Integer.parseInt(entity.getTemplate());
            AdSize adSize = new AdSize(adW, adsManager.getAdHeight(template, adW));
            adView.setAdSize(adSize);
            adView.setAdUnitId(adsManager.getAdUnitID(template));
            adView.loadAd(new AdRequest.Builder().build());
            adView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    TLog.e("ads", "addsuccess--------");
                    AdCacheManager.getInstance().addCaChe(entity.getDownLoadTime(), adView);
                    if (cardView.getChildCount() > 0) {
                        cardView.removeAllViews();
                    }
                    if (adView.getParent() != null) {
                        ((ViewGroup) adView.getParent()).removeView(adView);
                    }
                    cardView.addView(adView);
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    TLog.e("ads", "addfail--------" + errorCode);
                    cardView.removeAllViews();
                    viewLineAd.setVisibility(View.GONE);
                }
            });
        }else {
            if (cardView.getChildCount() > 0) {
                cardView.removeAllViews();
            }
            if (adView.getParent() != null) {
                ((ViewGroup) adView.getParent()).removeView(adView);
            }
            cardView.addView(adView);
        }
    }
}
