package com.baiyi.jj.app.activity.main.fragment;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baiyi.core.cache.Cache;
import com.baiyi.jj.app.activity.channelmanager.ChannelManager;
import com.baiyi.jj.app.entity.ChannelItem;

import java.util.List;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class NewsSlidePager extends ChannelManager{

    public NewsSlidePager(Context context, String tableName, int TabId, ImageView imgArrow) {
        super(context, tableName, TabId, imgArrow);
    }

    @Override
    public Cache getFeiYeCache() {
        return null;
    }

    @Override
    public void onItemClilck(ChannelItem entity) {

    }

    @Override
    public boolean isFY_FirstDayTimes() {
        return false;
    }

    @Override
    public void loadArticleData(String mid, List<ChannelItem> userChannelList) {

    }

    @Override
    public void onclickPopItem(int state, ViewGroup title) {

    }
}
