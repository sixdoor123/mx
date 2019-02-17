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

public class NewsDetailAct extends BaseNewsDetailAct {

    @Bind(R.id.news_detail_local)
    WebView webViewLocal;

    public void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);
        webViewLocal.setVisibility(View.VISIBLE);
        loadImageList();
    }

    @JavascriptInterface
    public void initContent() {

//        String TestUrl = "http://www.toutiao.com/";

        final WebSettings seting2 = webViewLocal.getSettings();
        seting2.setJavaScriptEnabled(true);
        seting2.setAllowFileAccess(true);
        seting2.setSavePassword(false);
        seting2.setBuiltInZoomControls(false);
//        seting2.setBlockNetworkImage(true);

//        seting2.setDomStorageEnabled(true);
//        seting2.setDatabaseEnabled(true);
//        String cacheDirPath = getFilesDir().getAbsolutePath()
//                + APP_CACHE_DIRNAME;
//        seting2.setDatabasePath(cacheDirPath); // API 19 deprecated
//        seting2.setAppCachePath(cacheDirPath);
//        seting2.setAppCacheEnabled(true);

        if (ContextUtil.isNetWorking(NewsDetailAct.this)) {
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

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                stopProgress();

//                if (!failingUrl.isEmpty() && failingUrl.contains("image")){
//                    String bac = failingUrl.substring(failingUrl.lastIndexOf("e")+2,failingUrl.length());
//                    TLog.e("bac",bac+"===========");
//                    try {
//                        int posit = Integer.parseInt(bac);
//                        intentToImage(posit);
//                    }catch (NumberFormatException e){
//                        e.printStackTrace();
//                    }
//                }
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.isEmpty() && url.contains("image")) {
                    String bac = url.substring(url.lastIndexOf("e") + 2, url.length());
//                    TLog.e("bac",bac+"===========");
                    try {
                        int posit = Integer.parseInt(bac);
                        intentToImage(posit);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                    return true;
                } else {
                    return false;
                }
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
        webViewLocal.setWebChromeClient(new WebChromeClienter());
        boolean isWifi = !ContextUtil.isGprs(NewsDetailAct.this);
//        boolean isWifi = false;
        boolean isShow = AccountManager.getInstance().getWifi_Is_Display_Img() != 2;
        boolean isIndia = CountryMannager.getInstance().getCurrentCountry() == CountryMannager.Country_India;
        String DetaiUrl = NetUtils.getNewsDetailWeb(ArticleId, isWifi, isWifi ? false : isShow, isIndia);

        TLog.e("web-" + isShow, DetaiUrl);

        WebDetailDao webDetailDao = new WebDetailDao(this);
        WebDetailBean bean = webDetailDao.getDetaiText(ArticleId);
        if (!webDetailDao.isLoaded(ArticleId)) {
            startProgress();
            loadWebText(DetaiUrl);
            webViewLocal.loadUrl(DetaiUrl);
            return;
        }
        if (bean == null || Utils.isStringEmpty(bean.getWebcontent())) {
            startProgress();
            loadWebText(DetaiUrl);
            webViewLocal.loadUrl(DetaiUrl);
            return;
        }
        webViewLocal.loadUrl(DetaiUrl);
//        webViewLocal.loadDataWithBaseURL(DetaiUrl, bean.getWebcontent(), "text/html", "utf-8", null);

    }

    private void loadWebText(final String urlStr) {
        WebViewManager.loadWebUrlStr(null, NewsDetailAct.this, urlStr, ArticleId, new WebViewManager.LoadWebComplete() {
            @Override
            public void loadComplete(final WebDetailBean beans) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isLoadComplete) {
                            TLog.e("load", "++++++++++++++++++++++");
                            webViewLocal.stopLoading();
                            webViewLocal.loadDataWithBaseURL(urlStr, beans.getWebcontent(), "text/html", "utf-8", null);
                        }
                    }
                });
            }
        });
    }

//    private class InJavaScriptLocalObj{
//        @JavascriptInterface
//        public void showSource(String html){
//            imageList = WebViewManager.getAllImageUrlFromSrc(WebViewManager.getAllImageUrlFromHtml(html));
//        }
//    }

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
        Intent intent = new Intent(NewsDetailAct.this, ImageContainerActivity.class);
        intent.putExtra(IntentDefine.PicEntity, (Serializable) imageList);
        intent.putExtra(IntentDefine.Start_Img_Index, positon);
        startActivity(intent);
    }

    public void loadImageList() {
        new NewsDetailUtils().loadDetailImage(ArticleId, new NewsDetailUtils.ImageListCallback() {
            @Override
            public void callback(final List<String> list) {
                imageList.addAll(list);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        for (int i = 0;i<list.size();i++){
//                            GlideTool.getListImage(NewsDetailAct.this,list.get(i),new ImageView(NewsDetailAct.this));
//                        }
//                    }
//                });
            }
        });
    }


    @SuppressWarnings("deprecation")
    public void setWebTextSize(int fontType) {
        WebSettings seting = webViewLocal.getSettings();
        if (fontType == 0) {
            // seting.setTextSize(TextSize.SMALLER);
            webViewLocal.loadUrl("javascript:setFontSize('small')");
        } else if (fontType == 1) {
            // seting.setTextSize(TextSize.NORMAL);
            webViewLocal.loadUrl("javascript:setFontSize('middle')");
        } else if (fontType == 2) {
            // seting.setTextSize(TextSize.LARGER);
            webViewLocal.loadUrl("javascript:setFontSize('big')");
        } else if (fontType == 3) {
            // seting.setTextSize(TextSize.LARGEST);
            webViewLocal.loadUrl("javascript:setFontSize('ver_big')");
        }
    }


    class WebChromeClienter extends WebChromeClient {
        public void onProgressChanged(WebView view, int newProgress) {
//            if (newProgress == 100) {
//               stopProgress();
//            }
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webViewLocal != null) {
            webViewLocal.destroy();
        }
    }
}
