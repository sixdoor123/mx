package com.baiyi.jj.app.cache.Dao;

import android.content.Context;
import android.util.Log;

import com.baiyi.jj.app.cache.helper.DatabaseHelper;
import com.baiyi.jj.app.entity.article.ArticleChannel;
import com.baiyi.jj.app.entity.article.ArticleEntity;
import com.baiyi.jj.app.utils.Utils;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/19 0019.
 */
public class ChannelTendDao {

    private Dao<ArticleChannel,Integer> channelTendDao;

    private DatabaseHelper dbHelper;

    public ChannelTendDao(Context context) {
        try {
            dbHelper = DatabaseHelper.getHelper(context);
            channelTendDao = dbHelper.getDao(ArticleChannel.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ArticleChannel> getChannelList(String tablename){
        List<ArticleChannel> list = new ArrayList<>();
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("isSub","true");
            map.put("tablename",tablename);
            list = channelTendDao.queryForFieldValues(map);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }



    public void insertOrUpdateArticle(List<ArticleChannel> addArticleChannels){

        if(Utils.isStringEmpty(addArticleChannels))
        {
            return;
        }
        for(ArticleChannel ac : addArticleChannels)
        {
            if(isNull(ac.getChannel_id()))
            {
               add(ac);
            }else
            {
                update(ac);
            }
        }
    }
    public boolean isNull(int channelId)
    {
        try {
            List<ArticleChannel> dataList = channelTendDao.queryForEq("channel_id",channelId);
            if(Utils.isStringEmpty(dataList))
            {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }
    public void update(ArticleChannel bean) {
        try {
            List<ArticleChannel> entitys = channelTendDao.queryForEq("channel_id",bean.getChannel_id());
            if (Utils.isStringEmpty(entitys)){
                return;
            }
            channelTendDao.delete(entitys.get(0));
            channelTendDao.create(bean);
//            channelTendDao.update(bean);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     */
    public void add(ArticleChannel bean) {
        try {
            channelTendDao.create(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void add(List<ArticleChannel> beans) {
        try {
            for (int i =0; i<beans.size();i++){
                if (isNull(beans.get(i).getChannel_id())){
                    channelTendDao.create(beans.get(i));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ArticleChannel> queryForAll() {
        List<ArticleChannel> themes = new ArrayList<>();
        try {
            themes = channelTendDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return themes;
    }
    public void deleteAll() {
        try {
            channelTendDao.queryRaw("delete from tb_channeltrend");
            channelTendDao.clearObjectCache();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
