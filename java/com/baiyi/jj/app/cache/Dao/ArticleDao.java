package com.baiyi.jj.app.cache.Dao;

import android.content.Context;

import com.baiyi.jj.app.cache.bean.CollectBean;
import com.baiyi.jj.app.cache.helper.DatabaseHelper;
import com.baiyi.jj.app.entity.article.ArticleEntity;
import com.baiyi.jj.app.utils.Utils;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/19 0019.
 */
public class ArticleDao {
    private Dao<ArticleEntity,Integer> articleDao;

    private DatabaseHelper dbHelper;

    public ArticleDao(Context context) {
        try {
            dbHelper = DatabaseHelper.getHelper(context);
            articleDao = dbHelper.getDao(ArticleEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(ArticleEntity bean) {
        try {
            articleDao.create(bean);
        } catch (SQLException e) {
           e.printStackTrace();
        }
    }

    public void deleteAll() {
        try {
            articleDao.queryRaw("delete from tb_articlerec");
            articleDao.clearObjectCache();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isNull(boolean isRecomment, String channelId)
    {

        List<ArticleEntity> dataList = getArticleEnty(isRecomment, channelId);
        if(Utils.isStringEmpty(dataList))
        {
            return true;
        }
        return false;
    }

    public void insertOrUpdateArticle(ArticleEntity entity, boolean isRecomment, String channelId){

        if(isNull(isRecomment,channelId))
        {
            //insert
            add(entity);
        }else
        {
            //update
            update(entity);
        }
    }


    public ArticleEntity getArticleEntity(boolean isRecomment, String channelId)
    {
        List<ArticleEntity> dataList = getArticleEnty(isRecomment, channelId);
        if(Utils.isStringEmpty(dataList))
        {
            return null;
        }
        ArticleEntity entity = dataList.get(0);
        entity.updateArticleChannelList(entity.getArticleChannelListData());
        entity.updateArticleRecommend(entity.getArticleRecommendData());
        return entity;
    }

    private List<ArticleEntity> getArticleEnty(boolean isRecomment, String channelId){

        List<ArticleEntity> list = new ArrayList<>();
        try {
            if (isRecomment){
                list = articleDao.queryForEq("isrecomment",String.valueOf(isRecomment));
            }else {
                Map<String,Object> map = new HashMap<>();
                map.put("isrecomment",String.valueOf(isRecomment));
                map.put("channelid",channelId);
                list =  articleDao.queryForFieldValues(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  list;
    }

    public void update(ArticleEntity bean) {
        try {
            articleDao.update(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<ArticleEntity> queryForAll() {
        List<ArticleEntity> themes = new ArrayList<>();
        try {
            themes = articleDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return themes;
    }

}
