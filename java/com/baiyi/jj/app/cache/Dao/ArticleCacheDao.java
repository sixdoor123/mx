package com.baiyi.jj.app.cache.Dao;

import android.content.Context;

import com.baiyi.jj.app.cache.helper.DatabaseHelper;
import com.baiyi.jj.app.entity.article.ArticleCacheEntity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/14 0014.
 */
public class ArticleCacheDao {
    private Dao<ArticleCacheEntity,Integer> articleCacheDao;

    private DatabaseHelper dbHelper;

    private int pageNum = 20;

    private Context context;

    public ArticleCacheDao(Context context) {
        try {
            dbHelper = DatabaseHelper.getHelper(context);
            articleCacheDao = dbHelper.getDao(ArticleCacheEntity.class);
            this.context = context;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCache(String tablename){
        List<ArticleCacheEntity> allCache =  queryByTablename(tablename);
        if (allCache.size()>pageNum){
            try {
                DeleteBuilder<ArticleCacheEntity,Integer> deleteBuilder = articleCacheDao.deleteBuilder();
                deleteBuilder.where().lt("time",allCache.get(pageNum-1).getTime());
                deleteBuilder.delete();
                deleteListCache(tablename,allCache.get(pageNum-1).getTime());

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteListCache(String tablename,long time){
        NewsListDao listDao = new NewsListDao(context);
        listDao.deleteCache(tablename,time);
    }


    /**
     */
    public void add(ArticleCacheEntity bean) {
        try {
            articleCacheDao.create(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     */
    public void delete(ArticleCacheEntity bean) {
        try {
            articleCacheDao.delete(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteAll() {
        try {
            articleCacheDao.queryRaw("delete from tb_articleindex");
            articleCacheDao.clearObjectCache();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * ����һ����¼
     */
    public void update(ArticleCacheEntity bean) {
        try {
            articleCacheDao.update(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ArticleCacheEntity> queryByTablename(String tablename) {
        List<ArticleCacheEntity> themes = new ArrayList<>();
        try {
//            themes = articleCacheDao.queryForEq("tablename",tablename);
            themes = articleCacheDao.queryBuilder().orderBy("Id", false).where().eq("tablename", tablename).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return themes;
    }

    public List<ArticleCacheEntity> queryForAll() {
        List<ArticleCacheEntity> themes = new ArrayList<>();
        try {
            themes = articleCacheDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return themes;
    }
}
