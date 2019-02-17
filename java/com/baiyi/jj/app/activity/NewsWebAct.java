package com.baiyi.jj.app.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.cache.Dao.WebDetailDao;
import com.baiyi.jj.app.cache.bean.WebDetailBean;
import com.baiyi.jj.app.manager.CountryMannager;
import com.baiyi.jj.app.manager.WebViewManager;
import com.baiyi.jj.app.net.NewsDetailUtils;
import com.baiyi.jj.app.utils.IntentDefine;
import com.baiyi.jj.app.utils.NetUtils;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.turbo.turbo.mexico.R;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;


/**
 * Created by Administrator on 2016/9/19 0019.
 */

public class NewsWebAct extends BaseNewsDetailAct {

    @Bind(R.id.news_detail_local)
    WebView webViewLocal;

    public void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);
        webViewLocal.setVisibility(View.VISIBLE);
        loadImageList();
    }

    @JavascriptInterface
    public void initContent() {

        final WebSettings seting2 = webViewLocal.getSettings();
        seting2.setJavaScriptEnabled(true);
        seting2.setAllowFileAccess(true);
        seting2.setSavePassword(false);
        seting2.setBuiltInZoomControls(false);

        if (ContextUtil.isNetWorking(NewsWebAct.this)) {
            seting2.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            seting2.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        final long start = System.currentTimeMillis();
//        webViewLocal.addJavascriptInterface(new InJavaScriptLocalObj(),"local_obj");
        webViewLocal.addJavascriptInterface(new MyImageJavaScriptInterface(this), "imageListener");
        webViewLocal.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                stopProgress();
                super.onReceivedError(view, request, error);
            }


            @JavascriptInterface
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // setWebTheme(ThemeUtil.getAppTheme());
                isLoadComplete = true;
                seting2.setBlockNetworkImage(false);
                long value = System.currentTimeMillis() - start;
                setWebTextSize(AccountManager.getInstance().getCurrentSize());
//                WebViewManager.parseHTML(view);
                WebViewManager.getImageList(view);
                stopProgress();
                mLinBottom.setVisibility(View.VISIBLE);
                addRequestTime(this.getClass().getName(), BaseAnalyticsActivity.Category_web, BaseAnalyticsActivity.Web_Load_DetailUrl, value);
            }
        });
        boolean isWifi = !ContextUtil.isGprs(NewsWebAct.this);
//        boolean isWifi = false;
        boolean isShow = AccountManager.getInstance().getWifi_Is_Display_Img() != 2;
        boolean isIndia = CountryMannager.getInstance().getCurrentCountry() == CountryMannager.Country_India;
        String DetaiUrl = NetUtils.getNewsDetailWeb(ArticleId, isWifi, isWifi ? false : isShow, isIndia);

        TLog.e("web-" + isShow, DetaiUrl);

            startProgress();
            webViewLocal.loadUrl(DetaiUrl);
        webViewLocal.loadUrl(DetaiUrl);
//        webViewLocal.loadDataWithBaseURL(DetaiUrl, bean.getWebcontent(), "text/html", "utf-8", null);

    }


    private class MyImageJavaScriptInterface {
        private Context context;

        public MyImageJavaScriptInterface(Context context) {
            this.context = context;
        }

        @JavascriptInterface
        public void getImages(String url) {

        }
    }

    private void intentToImage(int positon) {
        if (Utils.isStringEmpty(imageList)) {
            return;
        }
        Intent intent = new Intent(NewsWebAct.this, ImageContainerActivity.class);
        intent.putExtra(IntentDefine.PicEntity, (Serializable) imageList);
        intent.putExtra(IntentDefine.Start_Img_Index, positon);
        startActivity(intent);
    }

    public void loadImageList() {
        new NewsDetailUtils().loadDetailImage(ArticleId, new NewsDetailUtils.ImageListCallback() {
            @Override
            public void callback(final List<String> list) {
                imageList.addAll(list);
            }
        });
    }


    @Override
    public void setWebTextSize(int a) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webViewLocal != null) {
            webViewLocal.destroy();
        }
    }
}
