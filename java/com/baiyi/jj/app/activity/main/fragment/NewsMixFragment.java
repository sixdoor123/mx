package com.baiyi.jj.app.activity.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baiyi.core.file.Preference;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.activity.BaseAnalyticsActivity;
import com.baiyi.jj.app.activity.MorningNewsListAct;
import com.baiyi.jj.app.activity.main.task.NewsLoaderManager;
import com.baiyi.jj.app.activity.main.task.WebLoadManager;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.cache.Dao.ChannelDao;
import com.baiyi.jj.app.cache.Dao.NewsListDao;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.entity.MorningHeadEntity;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.entity.article.ArticleEntity;
import com.baiyi.jj.app.manager.ChannelDataManager;
import com.baiyi.jj.app.manager.MorningNewsManager;
import com.baiyi.jj.app.manager.NewsListDataManager;
import com.baiyi.jj.app.utils.Define;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.baiyi.jj.app.utils.XMLName;
import com.baiyi.jj.app.views.NetworkImageView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.turbo.turbo.mexico.R;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/19 0019.
 */
public class NewsMixFragment extends NewsBaseFragment{

    private String fidStr = "";
    boolean hasHeadView = false;
    boolean isListAdd = true;

    private List<ChannelItem> channelItems = new ArrayList<>();

    public static NewsMixFragment newInstance(List<ChannelItem> list) {
        NewsMixFragment fragment = new NewsMixFragment();
        fragment.setArguments(null);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Define.Channel_Group, (Serializable) list);
        fragment.setArguments(bundle);
        return fragment;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<ChannelItem> list = (ArrayList<ChannelItem>) getArguments().getSerializable(Define.Channel_Group);
        channelItems.clear();
        channelItems.addAll(list);
    }

    @Override
    public void saveFidStr(String fid) {
        if (preference != null) {
            preference.Set(XMLName.XML_NewsFID, fidStr);
            preference.saveConfig();
        }
    }

    @Override
    public boolean isLocal() {
        return false;
    }

    @Override
    public boolean hasHeadView() {
        return hasHeadView;
    }

    @Override
    public String getChannelId() {
        return "for you";
    }

    @Override
    public int getCacheSize() {
        return new NewsListDao(getContext()).getNewspagesize(false);
    }

    @Override
    public List<NewsListEntity> getCacheData(int page, int id) {
        return new NewsListDao(getContext()).getNewsByPage(page, id, false);
    }

    @Override
    public void initCreatData(boolean isShowNoData) {
        if (isShowNoData){
            showNoCacheLoading();
        }else {
            hideNoCacheLoading();
        }
        if (MorningNewsManager.isShowHead()) {
            loadMorningData();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mListView.init();
                setListRefreshing();
            }
        }, 1000);
    }

    @Override
    public String getUrl() {
        return NewsListDataManager.getUrl(true);
    }

    @Override
    public String getPostData(boolean iswifi) {
        List<ChannelItem> allList = new ArrayList<>();
        allList.clear();
        allList = new ChannelDao(getContext()).queryByTypeSub(ChannelDataManager.ChannelType_News);
        fidStr = preference.Get(XMLName.XML_NewsFID, "");
        if (!Utils.isStringEmpty(allList)){
            return NewsListDataManager.getPostData(true, allList, null, fidStr, iswifi, false, getContext());
        }else {
            return NewsListDataManager.getPostData(true, channelItems, null, fidStr, iswifi, false, getContext());
        }
    }

    @Override
    public ArticleEntity getAricleEntity(JSONArray array) {
        return NewsListDataManager.getParseData(array, true, false, null, false,null);
    }

    @Override
    public void addMorningHead() {
        if (!hasHeadView) {
            if (!Utils.isStringEmpty(morningHeadEntites)) {
                listAddHead();
            } else {
                isListAdd = false;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (convenientBanner != null) {
            convenientBanner.startTurning(2000);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (convenientBanner != null) {
            convenientBanner.stopTurning();
        }
    }

    private void loadMorningData() {
        TLog.e("ss", "showhead--------");
        MorningNewsManager.loadMornData(new MorningNewsManager.LoadComplete() {
            @Override
            public void loadcomplte(final List<MorningHeadEntity> entity, List<String> imgList) {
                if (Utils.isStringEmpty(entity)) {
                    return;
                }
                morningHeadEntites.clear();
                morningHeadEntites.addAll(entity);
                morningImgList.clear();
                morningImgList.addAll(imgList);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isListAdd) {
                            listAddHead();
                        }
                    }
                });


            }
        },getContext());
    }

    private void listAddHead() {

        if (!Utils.isStringEmpty(morningHeadEntites)) {
            addHead(morningImgList, false);
        }
    }

    List<MorningHeadEntity> morningHeadEntites = new ArrayList<>();
    List<String> morningImgList = new ArrayList<>();
    ConvenientBanner convenientBanner;

    private void addHead(final List<String> imgList, boolean isShowSmall) {

        if (Utils.isStringEmpty(imgList)) {
            return;
        }
        final View headView = LayoutInflater.from(getContext()).inflate(R.layout.head_morningnews, null);

        int imgW = (Config.getInstance().getScreenWidth(getContext()) - BaseActivity.getDensity_int(30));
        int imgH = (int) (imgW * 0.48);

        convenientBanner = (ConvenientBanner) headView.findViewById(R.id.morning_pager);
        convenientBanner.getLayoutParams().width = imgW ;
        convenientBanner.getLayoutParams().height = imgH;
        convenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public NetworkImageView createHolder() {
                return new NetworkImageView();
            }
        }, imgList).setPageIndicator(new int[]{R.drawable.current_point_ico, R.drawable.icon_noaccess_point});
        convenientBanner.startTurning(2000);
        RelativeLayout relativeLayout = (RelativeLayout)headView.findViewById(R.id.real_unclicked);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MorningNewsListAct.class);
                startActivity(intent);
                ((BaseActivity) getActivity()).addEnvent(BaseAnalyticsActivity.Envent_Click_MorningPager);
            }
        });
        TextView textViewTitleTime = (TextView) headView.findViewById(R.id.txt_title_time);
        textViewTitleTime.setText(MorningNewsManager.getDayTitleTime(getContext()));

        hasHeadView = true;
        mListView.addHeaderView(headView);
    }
}
