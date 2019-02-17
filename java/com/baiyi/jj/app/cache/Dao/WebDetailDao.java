package com.baiyi.jj.app.cache.Dao;

import android.content.Context;

import com.baiyi.jj.app.cache.bean.WebDetailBean;
import com.baiyi.jj.app.cache.helper.DatabaseHelper;
import com.baiyi.jj.app.utils.Utils;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2017/3/13 0013.
 */
public class WebDetailDao {

    private Dao<WebDetailBean,Integer> webDao;

    private DatabaseHelper dbHelper;

    private long the200stTime;

    public WebDetailDao(Context context) {
        try {
            dbHelper = DatabaseHelper.getHelper(context);
            webDao = dbHelper.getDao(WebDetailBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public WebDetailBean getDetaiText(String articleid){
        try {
        List<WebDetailBean> entity = webDao.queryForEq("articleid",articleid);
        if (!Utils.isStringEmpty(entity))
        {
            return entity.get(0);
        }else {
            return null;
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  判断数据库中是否存在这条数据
     */
    public boolean isLoaded(String newsid){
        try {
            if(Utils.isStringEmpty(newsid))
            {
                return false;
            }
            //先从缓存中取
//            if(ReadedCacheManager.getInstance().isCache(newsid))
//            {
//                return true;
//            }
            List<WebDetailBean> entity = webDao.queryForEq("articleid",newsid);
            if (!Utils.isStringEmpty(entity))
            {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 添加一条记录
     */
    public void add(WebDetailBean bean) {
        try {
            if (isLoaded(bean.getArticleid()))
            {
                return;
            }
            webDao.create(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCache(long time){
        try {
            DeleteBuilder<WebDetailBean,Integer> deleteBuilder = webDao.deleteBuilder();
            deleteBuilder.where().lt("downloadtime",time);
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void  delete(WebDetailBean bean){
        try {
            webDao.delete(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public long getThe200stTime() {
        return the200stTime;
    }

    public void delete200() {
        try {
            List<WebDetailBean> listEntities = webDao.queryForAll();
            if (Utils.isStringEmpty(listEntities)){
                return;
            }
            if (listEntities.size() > 200){
                // 289tiao 1---1000   189tiao
                WebDetailBean bean = listEntities.get(listEntities.size()-100);
                DeleteBuilder<WebDetailBean,Integer> deleteBuilder = webDao.deleteBuilder();
                deleteBuilder.where().lt("downloadtime",bean.getDownload());
                deleteBuilder.delete();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getAllCacheSize() {
        try {
            List<WebDetailBean> listEntities = webDao.queryForAll();
            if (Utils.isStringEmpty(listEntities)){
                return 0;
            }
            return listEntities.size();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void deleteAll() {
        try {
            webDao.queryRaw("delete from tb_webdetail");
            webDao.clearObjectCache();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
