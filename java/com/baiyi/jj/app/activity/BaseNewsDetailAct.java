package com.baiyi.jj.app.activity;

import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baiyi.core.file.Preference;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.activity.attention.AttentionWordsEntity;
import com.baiyi.jj.app.activity.main.HomeTabAct;
import com.baiyi.jj.app.activity.main.news.AdRender;
import com.baiyi.jj.app.activity.user.MyCollectActivity;
import com.baiyi.jj.app.activity.user.config.MemberConfig;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.cache.Dao.CollectDao;
import com.baiyi.jj.app.cache.Dao.ReadDao;
import com.baiyi.jj.app.cache.Dao.SupportDao;
import com.baiyi.jj.app.cache.ReadedCacheManager;
import com.baiyi.jj.app.cache.bean.ReadBean;
import com.baiyi.jj.app.dialog.FontSetDialog;
import com.baiyi.jj.app.dialog.ShareDialog;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.listitem.news.ListItemBaseNews;
import com.baiyi.jj.app.manager.AdsManager;
import com.baiyi.jj.app.net.CollectUtils;
import com.baiyi.jj.app.net.NewsDetailUtils;
import com.baiyi.jj.app.theme.FontUtil;
import com.baiyi.jj.app.utils.ArticleHistoryUtils;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.views.LinNewsReadingView;
import com.baiyi.jj.app.views.MyLoadingBar;
import com.baiyi.jj.app.views.PleaseAttentionView;
import com.baiyi.jj.app.views.ShareView;
import com.baiyi.jj.core.basedialog.DialogBase;
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

import java.io.Closeable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 新闻详情的基本页面
 * Created by lizl on 2017/3/23 15:00
 */

public abstract class BaseNewsDetailAct extends BaseActivity {


    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.lin_webtitle)
    LinearLayout linWebTitle;
    @Bind(R.id.text_left_web)
    TextView txtWeb;
    @Bind(R.id.text_left_local)
    TextView txtLocal;
    @Bind(R.id.lin_title)
    LinearLayout linTitle;
    @Bind(R.id.img_right)
    ImageView imgSeeSource;

    @Bind(R.id.view_ad_detail)
    LinearLayout mLinAd;
    @Bind(R.id.tag_cloud_view)

    PleaseAttentionView mTagCloudView;
    @Bind(R.id.lin_list_reading)
    LinNewsReadingView mLinListReading;
    @Bind(R.id.lin_news_detail)
    LinearLayout mLinBottom;
    @Bind(R.id.lin_attention)
    LinearLayout mLinPleaseAttention;
    @Bind(R.id.lin_reading)
    LinearLayout mLinReading;

    @Bind(R.id.loading)
    MyLoadingBar progressBar;
    /**
     * 底部bottom菜单
     */
    @Bind(R.id.btn_comment)
    ImageView btnComment;
    @Bind(R.id.btn_collect)
    ImageView btnCollect;
    @Bind(R.id.btn_share)
    ImageView btnShare;
    @Bind(R.id.btn_like)
    ImageView btnLike;
    @Bind(R.id.btn_font)
    ImageView btnFont;

    // 前面传过来的
    public String ArticleId;
    public String ArticleTitle;
    private int CommentNum;
    private String imageUrl;
    public String PageUrl;
    public boolean isGif = false;
    public boolean is6 = false;
    public String SourceStr;
    public String TimeStr;
    public String videoId = "";
    // 回到列表时刷新列表评论数
    private boolean isResult = false;
    // 该文章的总评论数
    private int comAllNum;
    // 该文章在列表中的index
    private int itemPosition = -1;
    // 是否原创
    private boolean isOriginal;

    private int sep;
    // 是否推送
    private boolean isPush = false;
    // 是否有取消收藏的操作
    private boolean isCancelCellect = false;

    /**
     * 收藏相关
     */
    private CollectUtils collectUtils = null;

    public NewsDetailUtils newsDetailUtils = new NewsDetailUtils();

    public abstract void initContent();

    public abstract void setWebTextSize(int a);

    /**
     * 用于记录用户行为
     */
    private String ratStr;
    private final String RatRead = "read";
    private final String RatVote = "vote";
    private final String RatComm = "comment";

    private String entryFrom = "";

    public boolean isLoadComplete = false;

    public void initWin(boolean hasScrollView, boolean isAnimal) {
        super.initWin(false, true);

        initSwipe();
        setEnterSwichLayout();
        initIntentData();
        initTitle();
        initContent();
        initCollectInfo();
        if (!isGif) {
//            initAttention();
            loadRelatedReading();
        }
        loadAddRead();
        initNoNetRead();

//        String aa = "http://stories.cdn.in.turboapp.xyz/259055/?&wifi=0images0";
//        String bacss = aa.substring(aa.lastIndexOf("e")+2,aa.length());
//        TLog.e("nsss","ssss==========="+bacss);
    }

    private void initNoNetRead(){
        Preference preference = Preference.getInstance();
        String noNetId = preference.Get(Define.ArticleList,"");
        if (ContextUtil.isNetWorking(this)){
            if (!Utils.isStringEmpty(noNetId)){
                loadUpLoadReadList(noNetId);
            }
        }else {
            if (!Utils.isStringEmpty(ArticleId)){
                String noNetIdNew = Utils.isStringEmpty(noNetId) ? ArticleId : noNetId+","+ArticleId;
                TLog.e("netid","2----"+noNetIdNew);
                preference.Set(Define.ArticleList,noNetIdNew);
                preference.saveConfig();
            }
        }
    }

    private void loadUpLoadReadList(String noNetId){
        Preference preference = Preference.getInstance();
        List<String> idList = Utils.splitToList(noNetId,",");
        int loadsize = idList.size() > 5 ? 5 : idList.size();
        String loadIdlist = noNetId;
        String lastid = "";
        for (int i = 0; i < loadsize; i++) {
            String articleid = idList.get(i);
            newsDetailUtils.loadAddRead(BaseNewsDetailAct.this, articleid);
            lastid = articleid;
        }
        if (idList.size()>5){
            loadIdlist = noNetId.substring(noNetId.indexOf(lastid));
            TLog.e("netid","1----"+loadIdlist);
            preference.Set(Define.ArticleList,loadIdlist);
        }else {
            preference.Set(Define.ArticleList,"");
        }
        preference.saveConfig();

    }

    /**
     * 接收前面传过来的数据
     */
    private void initIntentData() {
        videoId = getIntent().getStringExtra(Define.VideoId);
        isGif = getIntent().getBooleanExtra(Define.IsGIf, false);
        ArticleId = getIntent().getStringExtra(Define.ArticleId);
        ArticleTitle = getIntent().getStringExtra(Define.ArticleTitle);
        CommentNum = getIntent().getIntExtra(Define.CommentNum, 0);
        imageUrl = getIntent().getStringExtra(Define.ArticleImage);
        PageUrl = getIntent().getStringExtra(Define.ArticleUrl);
        isResult = getIntent().getBooleanExtra(Define.Detail_Result, false);
        itemPosition = getIntent().getIntExtra(Define.ITEM_POSTION, -1);
        isOriginal = getIntent().getBooleanExtra(Define.IsOrigin, false);
        isPush = getIntent().getBooleanExtra(Define.IsPush, false);
        SourceStr = getIntent().getStringExtra(Define.ArticleSource);
        TimeStr = getIntent().getStringExtra(Define.ArticleTime);
//        TLog.e("abc","---sssss-------"+getIntent().getBooleanExtra(Define.IsPush,false));

        entryFrom = getIntent().getStringExtra(MemberConfig.My_Collect_Entry);

        if (isPush && !Utils.isStringEmpty(ArticleId)) {
            TLog.e("BaseNewsDetailAct", "articls-------------------------" + ArticleId);
            addEnvent(BaseAnalyticsActivity.Envent_ClickNotif, ArticleId);
        }

        UserInfoEntity infoEntity = CmsApplication.getUserInfoEntity();
        addEnvent(BaseAnalyticsActivity.Envent_Enter_Detail, infoEntity == null ? "null" : infoEntity.getMID());

//        if (isPush){
//            loadAnonymity(new AnonymityLister() {
//                @Override
//                private void setAnonymityLister(UserInfoEntity userinfo) {
//                    TLog.e("abc","-------"+CmsApplication.getUserToken());
//                }
//            });
//        }
    }

    /**
     * 初始化收藏相关,点赞相关
     */
    private void initCollectInfo() {

        collectUtils = new CollectUtils(BaseNewsDetailAct.this, ArticleId,
                ArticleHistoryUtils.ArticleType_News);
        ratStr = RatRead;


        // 点赞和收藏初始化
        loadAnonymity(new AnonymityLister() {
            @Override
            public void setAnonymityLister(UserInfoEntity infoEntity) {
                if (infoEntity == null) {
                    return;
                }
                btnLike.setSelected(new SupportDao(BaseNewsDetailAct.this).isSupport(ArticleId, infoEntity.getMID()));
                setCollectView(new CollectDao(BaseNewsDetailAct.this).isCollect(ArticleId, infoEntity.getMID()));
            }
        });

        // 添加本地阅读
        ReadBean bean = new ReadBean(ArticleId);
        new ReadDao(this).add(bean);
        ReadedCacheManager.getInstance().addCaChe(bean.getNewsId(), bean);


    }

    private void setCollectView(boolean isCollect) {
        btnCollect.setSelected(isCollect);
    }


    private void initTitle() {

        imgBack.setVisibility(View.VISIBLE);

        txtWeb.setSelected(true);
        linWebTitle.setSelected(true);
        txtLocal.setSelected(false);
        imgSeeSource.setImageResource(R.drawable.icon_bai_share);
        btnShare.setVisibility(View.GONE);

//        imgSeeSource.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse(PageUrl));
//                startActivity(intent);
//            }
//        });

        if (isOriginal) {
            imgSeeSource.setVisibility(View.GONE);
        }
    }

    @SuppressWarnings("deprecation")
    private void initSwipe() {

        ContextUtil.getLayoutInflater(this).inflate(
                R.layout.act_newsdetail, win_content);
        ButterKnife.bind(this);
    }

    public void stopProgress() {
        progressBar.stop();
        progressBar.setVisibility(View.GONE);
    }

    public void startProgress() {
        progressBar.start();
        progressBar.setVisibility(View.VISIBLE);
    }

    public List<String> imageList = new ArrayList<>();

    @OnClick(R.id.btn_comment)
    public void OnCommentClick() {
        Intent intent = new Intent(BaseNewsDetailAct.this,
                DetailCommnetListActivity.class);
        intent.putExtra(Define.ArticleId, ArticleId);
        intent.putExtra(Define.ArticleTitle, ArticleTitle);
        intent.putExtra(Define.CommentNum, CommentNum);
        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.img_back)
    public void onClick() {
        //返回到上一个页面 如果可以的话
//        if (webViewOrigin.canGoBack()||webViewLocal.canGoBack())
//        {
//            webViewOrigin.goBack();
//            return;
//        }
        exit(false);
    }

    private void setBackRefreshMyCollect() {
        Intent intent = new Intent(this, MyCollectActivity.class);
        setResult(MemberConfig.Refresh_My_Collect, intent);
    }


    ShareDialog shareDialog = null;

    @OnClick({R.id.btn_share,R.id.img_right})
    public void OnShareClick() {

//        shareDialog = new ShareDialog(NewsDetailAct.this,
//                DialogBase.Win_Top, ShareActivity.TitleId_Share,
//                getResources().getString(R.string.app_name), ArticleTitle, PageUrl, imageUrl);
//        shareDialog.showDialogAnimal(DialogBase.AnimalTop);
//        shareDialog.show();
//        ShareView.shareMsg(this, ArticleTitle, ArticleTitle + " " + PageUrl, imageUrl);
        addEnvent(BaseAnalyticsActivity.Envent_share_News);
        if (!Config.isRelease){
            sendDownLoadTest_Detail();
        }
        ShareView.shareMsg(this, ArticleTitle, ArticleTitle + "\r\n" + Config.getShareUrl(ArticleId) + " ", imageUrl);

    }

    @OnClick(R.id.btn_collect)
    public void OnCollectClick() {

        if (!ContextUtil.isNetWorking(this)) {
            displayMsgBox(getResources().getString(R.string.tip_dialog_title), getResources().getString(R.string.tip_net_fault));
            return;
        }
        btnCollect.setClickable(false);
        // 点赞和收藏初始化
        loadAnonymity(new AnonymityLister() {
            @Override
            public void setAnonymityLister(UserInfoEntity infoEntity) {
                if (infoEntity == null) {
                    return;
                }
                if (new CollectDao(BaseNewsDetailAct.this).isCollect(ArticleId, infoEntity.getMID())) {
                    loadDeleteCollect();
                } else {
                    loadAddCollect();
                }
            }
        });

    }

    @OnClick(R.id.btn_like)
    public void OnLikeClick() {
        // 1是支持，这儿只有支持
        if (!ContextUtil.isNetWorking(this)) {
            displayMsgBox(getResources().getString(R.string.tip_dialog_title), getResources().getString(R.string.tip_net_fault));
            return;
        }
        loadArticleVote();
    }

    @OnClick(R.id.btn_font)
    public void OnFontClick() {

        FontSetDialog dialogFontSet = new FontSetDialog(this, DialogBase.Win_Top, PageUrl);
        setStateColor();
        dialogFontSet.showDialogAnimal(DialogBase.AnimalTop);
        dialogFontSet.setFontItemClickLister(new FontSetDialog.FontOnItemClick() {

            @Override
            public void setFontOnItemClickLister(int FontType) {

                setWebTextSize(FontType);
                mTagCloudView.setTextSize();
                mLinListReading.setTextSize();
            }
        });
    }


    private void loadAddCollect() {

//        startProgress();
        checkLogin(new AnonymityLister() {

            @Override
            public void setAnonymityLister(UserInfoEntity infoEntity) {
                if (infoEntity == null) {
//                    stopProgress();
                    return;
                }

                collectUtils.loadAddCollect(infoEntity.getUserType() == UserInfoEntity.UserType_Gust, new CollectUtils.AddCollectCall() {

                    public void CallBack(int state) {

//                        stopProgress();

                        btnCollect.setClickable(true);
                        if (state == CollectUtils.State_Success) {
                            setCollectView(true);
                            isCancelCellect = false;
                            return;
                        }
                        if (state == CollectUtils.State_Failure) {
                            displayToast(getResources().getString(
                                    R.string.tip_collect_fal));
                            setCollectView(false);
                            return;
                        }
                    }
                });
            }
        });
    }

    private void loadDeleteCollect() {

//        startProgress();

        collectUtils.loadDeleteCollect(new CollectUtils.DeleteCollectCall() {

            @Override
            public void CallBack(int state) {
//                stopProgress();

                btnCollect.setClickable(true);
                if (state == CollectUtils.State_Success) {
                    setCollectView(false);
                    isCancelCellect = true;
                    return;
                }
                displayToast(getResources().getString(
                        R.string.tip_canel_collect_fal));
            }
        });
    }

    /**
     * 文章点赞
     */
    private void loadArticleVote() {

        if (btnLike.isSelected()) {
            //不允许多次投票
            displayToast(getResources().getString(R.string.tip_newsdetail_vote_repeat));
            return;
        }
//        startProgress();

        checkLogin(new AnonymityLister() {
            public void setAnonymityLister(final UserInfoEntity infoEntity) {
                if (infoEntity == null) {
//                    stopProgress();
                    return;
                }
                newsDetailUtils.loadArticleVote(infoEntity.getUserType() == UserInfoEntity.UserType_Gust,
                        BaseNewsDetailAct.this, ArticleId, ArticleHistoryUtils.ArticleType_News, new NewsDetailUtils.ArticleVoteCallBack() {

                            public void voteCallBack(int state) {

//                                stopProgress();

                                if (state == NewsDetailUtils.State_Success
                                        && !ratStr.contains("vote")) {
                                    ratStr = ratStr + "," + RatVote;
                                }
                                if (state == NewsDetailUtils.State_Success) {
                                    btnLike.setSelected(true);
                                } else if (state == NewsDetailUtils.State_Repeat) {
                                    boolean isHas = new SupportDao(BaseNewsDetailAct.this).isSupport(ArticleId, infoEntity.getMID());
                                    if (isHas) {
                                        return;
                                    }
                                    btnLike.setSelected(true);
                                }
                            }
                        });
            }
        }, progressBar);

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            exit(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 所有的退出都走这一步
     */
    private void exit(boolean isSwipe) {

//        TLog.e("ispush",isPush+"============");
        if (MemberConfig.My_Collect.equals(entryFrom) && isCancelCellect) {
            setBackRefreshMyCollect();
            setExitSwichLayout();
            return;
        }

        if (isResult) {

            Intent intent = new Intent(getApplicationContext(),
                    HomeTabAct.class);
            int num = comAllNum;
            intent.putExtra(Define.CommentNum, num);
            intent.putExtra(Define.ITEM_POSTION, itemPosition);
            setResult(ChannelUtils.Result_Detail_Comment, intent);
        }

//        if (isPush) {
//            Intent intent1 = new Intent(NewsDetailAct.this,
//                    LogoActivity.class);
//            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent1);
////            finish();
//        }
        if (isSwipe) {
            return;
        }
        setExitSwichLayout();
    }

    /**
     * 添加我的阅读
     */
    private void loadAddRead() {
        newsDetailUtils.loadAddRead(BaseNewsDetailAct.this, ArticleId);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (shareDialog != null) {
            shareDialog.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 相关阅读初始化
     */
    private void loadRelatedReading() {

        new NewsDetailUtils().loadRelatedReading(ArticleId, new NewsDetailUtils.RelatedReadingCallBack() {
            @Override
            public void relatedReadingBack(final List<NewsListEntity> list) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLinReading.setVisibility(View.VISIBLE);
                        mLinListReading.initView(list);
                    }
                });
            }
        });
    }

    /**
     * 请您关注初始化
     */
    private void initAttention() {

        new NewsDetailUtils().loadPleaseAttention(ArticleId, new NewsDetailUtils.PleaseAttentionCallBack() {
            @Override
            public void pleaseAttentionBack(final List<AttentionWordsEntity> list) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final List<String> listString = new ArrayList<String>();
                        for (AttentionWordsEntity entry : list) {
                            listString.add("#" + entry.getAlias());
                        }
                        mLinPleaseAttention.setVisibility(View.VISIBLE);
                        mTagCloudView.setTags(listString, FontUtil.getLabelTextSize());
                        mTagCloudView.setOnTagClickListener(new PleaseAttentionView.OnTagClickListener() {
                            @Override
                            public void onTagClick(int position) {
//                                AttentionWordsEntity entity = list.get(position);
//                                Intent intent = new Intent(BaseNewsDetailAct.this, MyAttentionListAct.class);
//                                boolean isHaveCache = AttentionCacheManager.getInstance().isHaveCache(entity.getChannel_id() + "" + entity.getHotword_id());
//                                intent.putExtra(Define.HotWord, entity);
//                                intent.putExtra(Define.IsAttention, isHaveCache);
//                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });
    }

    public int returnStateColor() {
        return R.color.day_text_color_red;
    }

    public void setGoogleAd(int unitType) {
        final NativeExpressAdView adView = new NativeExpressAdView(this);
        int imgW = Config.getInstance().getScreenWidth(this) - ContextUtil.dip2px(this, ListItemBaseNews.ThreeImg_Dip_Offset);
        int adW = (int) (imgW / density);
//        AdSize adSize = new AdSize(adW, (int) (adW * AdRender.AD_HEIGHT_Large));
//        adView.setAdUnitId(getResources().getString(R.string.googleads_unitid_large));
        AdSize adSize = new AdSize(adW, AdsManager.getInstance(this).getAdHeight(unitType, adW));
        adView.setAdSize(adSize);
        adView.setAdUnitId(AdsManager.getInstance(this).getAdUnitID(unitType));

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                TLog.e("google","loadsuccess----------------bigad");
                mLinAd.removeAllViews();
                mLinAd.addView(adView);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                TLog.e("google","loadfail----------------"+errorCode);
                mLinAd.setVisibility(View.GONE);
            }

        });
        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        adView.loadAd(adRequest);
    }

    NativeAd nativeAd;
    public void setFBAd(final int unitid) {
        nativeAd = new NativeAd(this,getResources().getString(R.string.facebook_place_id));
        nativeAd.setAdListener(new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                TLog.e("facebook","adderror-----"+adError.getErrorCode()+"----"+adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                View adView = NativeAdView.render(BaseNewsDetailAct.this, nativeAd, AdsManager.getInstance(BaseNewsDetailAct.this).getFaceADsType(unitid));
                // Add the Native Ad View to your ad container
                TLog.e("facebook","addsuccess---------"+ad.getPlacementId());
                mLinAd.removeAllViews();
                mLinAd.addView(adView);
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
    }
}
