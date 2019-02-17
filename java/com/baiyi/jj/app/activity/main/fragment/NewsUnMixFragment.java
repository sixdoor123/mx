package com.baiyi.jj.app.activity.main.fragment;

import android.os.Bundle;
import android.os.Handler;

import com.baiyi.jj.app.cache.Dao.NewsListDao;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.entity.article.ArticleEntity;
import com.baiyi.jj.app.manager.MorningNewsManager;
import com.baiyi.jj.app.manager.NewsListDataManager;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.XMLName;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/19 0019.
 */
public class NewsUnMixFragment extends NewsBaseFragment{

    private String fidStr = "";
    private String ChannelId = "";

    public static NewsUnMixFragment newInstance(String channelId) {
        NewsUnMixFragment fragment = new NewsUnMixFragment();
        fragment.setArguments(null);
        Bundle bundle = new Bundle();
        bundle.putString(Define.ChannelId,channelId);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ChannelId = getArguments().getString(Define.ChannelId);
        TLog.e("channelid","---"+ChannelId);
    }

    @Override
    public void saveFidStr(String fid) {
        if (preference != null) {
            if (ChannelId != null) {
                preference.Set(XMLName.XML_NewsFID + ChannelId, fidStr);
            }
            preference.saveConfig();
        }
    }

    @Override
    public void initCreatData(boolean isShowNoData) {
        TLog.e("is---","ishow------------"+isShowNoData);
        if (isShowNoData){
            showNoCacheLoading();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mListView.init();
                    setListRefreshing();
                }
            }, 1000);
        }else {
            hideNoCacheLoading();
        }

    }

    @Override
    public int getCacheSize() {
        return new NewsListDao(getContext()).getPagesizebyChannel(ChannelId);
    }
    @Override
    public boolean isLocal() {
        return false;
    }
    @Override
    public List<NewsListEntity> getCacheData(int page, int id) {
        return  new NewsListDao(getContext()).getNewsByChannidByPage(ChannelId, page, id);
    }

    @Override
    public boolean hasHeadView() {
        return false;
    }

    @Override
    public String getChannelId() {
        return ChannelId;
    }

    @Override
    public String getUrl() {
        return NewsListDataManager.getUrl(false);
    }

    @Override
    public String getPostData(boolean isWifi) {
        fidStr = preference.Get(XMLName.XML_NewsFID + ChannelId, "");
        return NewsListDataManager.getPostData(false, null, ChannelId, fidStr, isWifi, false, getContext());
    }

    @Override
    public ArticleEntity getAricleEntity(JSONArray array) {
        return NewsListDataManager.getParseData(array, false, false, ChannelId, false,null);
    }

    @Override
    public void addMorningHead() {

    }
}
