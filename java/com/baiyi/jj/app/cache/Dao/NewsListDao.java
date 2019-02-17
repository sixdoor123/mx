package com.baiyi.jj.app.cache.Dao;

import android.content.Context;
import android.util.Log;

import com.baiyi.jj.app.activity.user.config.MemberConfig;
import com.baiyi.jj.app.cache.helper.DatabaseHelper;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.language.SwitchLanguageUtils;
import com.baiyi.jj.app.manager.CountryMannager;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.TagUtils;
import com.baiyi.jj.app.utils.Utils;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/14 0014.
 */
public class NewsListDao{

    private Dao<NewsListEntity,Integer> newsListDao;

    private DatabaseHelper dbHelper;

    public NewsListDao(Context context) {
        try {
            dbHelper = DatabaseHelper.getHelper(context);
            newsListDao = dbHelper.getDao(NewsListEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteCache(String tablename,long time){
        try {
            DeleteBuilder<NewsListEntity,Integer> deleteBuilder = newsListDao.deleteBuilder();
//                deleteBuilder.where().eq("tablename",tablename).and().lt("downLoadTime",time);
            deleteBuilder.where().lt("downLoadTime",time);
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getAllListSize() {
        try {
            List<NewsListEntity> listEntities = newsListDao.queryForAll();
            if (Utils.isStringEmpty(listEntities)){
                return 0;
            }
//            TLog.e("time",listEntities.get(1).getDownLoadTime()+"++++++++++++++++");
//            TLog.e("time",listEntities.get(listEntities.size()-2).getDownLoadTime()+"***********");
            return listEntities.size();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * add
     */
    public void add(NewsListEntity bean) {
        try {
            newsListDao.createIfNotExists(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void add(List<NewsListEntity> beans) {
        try {
            for (int i = beans.size()-1;i>=0;i--){
                if (!beans.get(i).getType().equals(TagUtils.StorType_eidtion_top)){
                    if (Utils.isStringEmpty(beans.get(i).getArticle_id()))
                        continue;
                    newsListDao.create(beans.get(i));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<NewsListEntity> getNewsOffline(long downloadtime,long downloadtime2){
        List<NewsListEntity> list = new ArrayList<>();
        try {

            QueryBuilder<NewsListEntity,Integer> queryBuilder = newsListDao.queryBuilder();
            queryBuilder.where().eq("reservezone3","true").and().lt("downLoadTime", downloadtime2).and().gt("downLoadTime",downloadtime);
            list =  queryBuilder.query();

            setImage(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  list;
    }

    public List<NewsListEntity> getNewsByTable(String tablename,long download){
        List<NewsListEntity> list = new ArrayList<>();
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("tablename",tablename);
            map.put("downLoadTime",download);
            list = newsListDao.queryForFieldValues(map);

            setImage(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  list;
    }

    public List<NewsListEntity> getNewsByPage(int page,int id,boolean isVideoList){
        List<NewsListEntity> list = new ArrayList<>();
        try {
            QueryBuilder<NewsListEntity,Integer> queryBuilder = newsListDao.queryBuilder();
            int coutry = CountryMannager.getInstance().getCurrentCountry();
//            if (id != 0){
//                queryBuilder.where().eq("reservezone1","true"+(isVideoList?"video":"")).and().and().lt("Id",id);
////                queryBuilder.where().eq("reservezone1","true").and().lt("Id",id);
//            }else {
//                queryBuilder.where().eq("reservezone1","true"+(isVideoList?"video":"")).and().and();
////                queryBuilder.where().eq("reservezone1","true");
//            }
            if (id != 0){
                queryBuilder.where().eq("reservezone1","true").and().eq("reservezone2", String.valueOf(coutry))
                        .and().eq("Language",SwitchLanguageUtils.getCurrentLanguage()).and().lt("Id",id);
//                queryBuilder.where().eq("reservezone1","true").and().lt("Id",id);
            }else {
                queryBuilder.where().eq("reservezone1","true").and().eq("reservezone2", String.valueOf(coutry))
                        .and().eq("Language",SwitchLanguageUtils.getCurrentLanguage());
//                queryBuilder.where().eq("reservezone1","true");
            }

            queryBuilder.orderBy("Id", false);
            queryBuilder.limit(10).offset(10*page);

            list = queryBuilder.query();
            setImage(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  list;
    }
    public List<NewsListEntity> getNewsByPageType(String type,int page,int id,boolean isVideoList){
        List<NewsListEntity> list = new ArrayList<>();
        try {
            QueryBuilder<NewsListEntity,Integer> queryBuilder = newsListDao.queryBuilder();
            int coutry = CountryMannager.getInstance().getCurrentCountry();
//            if (id != 0){
//                queryBuilder.where().eq("reservezone1","true"+(isVideoList?"video":"")).and().eq("type", type).and().lt("Id",id);
////                queryBuilder.where().eq("reservezone1","true").and().lt("Id",id);
//            }else {
//                queryBuilder.where().eq("reservezone1","true"+(isVideoList?"video":"")).and().eq("type", type);
////                queryBuilder.where().eq("reservezone1","true");
//            }
            if (id != 0){


                queryBuilder.where().eq("reservezone1","true"+(isVideoList?"video":"")).and().eq("type", type)
                        .and().eq("Language",SwitchLanguageUtils.getCurrentLanguage()).and().lt("Id",id);
//                queryBuilder.where().eq("reservezone1","true").and().lt("Id",id);
            }else {
                queryBuilder.where().eq("reservezone1","true"+(isVideoList?"video":"")).and().eq("type", type)
                        .and().eq("Language",SwitchLanguageUtils.getCurrentLanguage());
//                queryBuilder.where().eq("reservezone1","true");
            }
            queryBuilder.orderBy("Id", false);
            queryBuilder.limit(10).offset(10*page);

            list = queryBuilder.query();
            setImage(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  list;
    }

    public int getNewspagesize(boolean isVideoList){
        List<NewsListEntity> list = new ArrayList<>();
        try {
            int coutry = CountryMannager.getInstance().getCurrentCountry();
            Map<String,Object> map = new HashMap<>();

            map.put("reservezone1","true"+(isVideoList?"video":""));
            map.put("Language", SwitchLanguageUtils.getCurrentLanguage());

            list = newsListDao.queryForFieldValues(map);
            if (false){

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  list.size();
    }
    public int getPagesizebyChannel(String channleid){
        List<NewsListEntity> list = new ArrayList<>();
        try {
            int coutry = CountryMannager.getInstance().getCurrentCountry();
            Map<String,Object> map = new HashMap<>();
            map.put("channel_id",channleid);
            map.put("reservezone1","false");
            map.put("reservezone2",String.valueOf(coutry));
            map.put("Language", SwitchLanguageUtils.getCurrentLanguage());
            list = newsListDao.queryForFieldValues(map);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  list.size();
    }
    public List<NewsListEntity> getNewsByChannid(String channlid,long download){
        List<NewsListEntity> list = new ArrayList<>();
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("channel_seq",channlid);
            map.put("downLoadTime",download);
            list = newsListDao.queryForFieldValues(map);
            setImage(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<NewsListEntity> getNewsByChannidByPage(String channlid,int page,int id){

        List<NewsListEntity> list = new ArrayList<>();
        try {
            int coutry = CountryMannager.getInstance().getCurrentCountry();
            QueryBuilder<NewsListEntity,Integer> queryBuilder = newsListDao.queryBuilder();

            if (id != 0){
                queryBuilder.where().eq("channel_id",channlid).and().eq("reservezone1","false")
                        .and().eq("reservezone2", String.valueOf(coutry))
                        .and().eq("Language",SwitchLanguageUtils.getCurrentLanguage())
                        .and().lt("Id",id);
            }else {
                queryBuilder.where().eq("channel_id",channlid).and().eq("reservezone1","false")
                        .and().eq("reservezone2", String.valueOf(coutry))
                        .and().eq("Language",SwitchLanguageUtils.getCurrentLanguage());
            }
            queryBuilder.orderBy("Id", false);
            queryBuilder.limit(10).offset(10*page);
            list = queryBuilder.query();
            setImage(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  list;
    }

    public int getLocalNewsSize(String areaName){
        List<NewsListEntity> list = new ArrayList<>();
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("reservezone1",false);
            map.put("reservezone5",areaName);
            list = newsListDao.queryForFieldValues(map);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  list.size();
    }
    public List<NewsListEntity> getLocaNewsData(String areaName,int page,int id){
        List<NewsListEntity> list = new ArrayList<>();
        try {
            QueryBuilder<NewsListEntity,Integer> queryBuilder = newsListDao.queryBuilder();
            if (id != 0){
                queryBuilder.where().eq("reservezone5",areaName).and().eq("reservezone1","false")
                        .and().lt("Id",id);
            }else {
                queryBuilder.where().eq("reservezone5",areaName).and().eq("reservezone1","false");
            }
            queryBuilder.orderBy("Id", false);
            queryBuilder.limit(10).offset(10*page);
            list = queryBuilder.query();
            setImage(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  list;
    }

    private void setImage( List<NewsListEntity> dataList){
        for(NewsListEntity entity : dataList)
        {
            String imgData = entity.getImgData();
            if(!Utils.isStringEmpty(imgData))
            {
                entity.updateImageList(imgData);
            }
            if(!Utils.isStringEmpty(entity.getTagsData()))
            {
                entity.updateTags(entity.getTagsData());
            }
            if(!Utils.isStringEmpty(entity.getVideoStr()))
            {
                entity.updateVideoList(entity.getVideoStr());
            }
        }
    }

    public NewsListEntity getNewsEntity(String articleId){
        NewsListEntity entity = null;
        try {
            List<NewsListEntity> list = newsListDao.queryForEq("article_id",articleId);
            if (Utils.isStringEmpty(list)){
                return null;
            }
            entity = list.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    public void updateNewsComment(int article_comment_num, String articleId){
        NewsListEntity entity = getNewsEntity(articleId);
        if (entity == null){
            return;
        }
        try {
            entity.setArticle_comment_num(article_comment_num);
            newsListDao.update(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateNewsAgree(int agree, String articleId){
        NewsListEntity entity = getNewsEntity(articleId);
        if (entity == null){
            return;
        }
        try {
            entity.setInterest_agree_num(agree);
            newsListDao.update(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<NewsListEntity> queryForaa(String tablename) {
        List<NewsListEntity> list = new ArrayList<>();
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("tablename",tablename);
            list = newsListDao.queryForFieldValues(map);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<NewsListEntity> queryForAll() {
        List<NewsListEntity> themes = new ArrayList<>();
        try {
            themes = newsListDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return themes;
    }
    public void deleteOneNews(String articleid) {
        List<NewsListEntity> list = new ArrayList<>();
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("article_id",articleid);
            list = newsListDao.queryForFieldValues(map);
            if (Utils.isStringEmpty(list)){
                return;
            }
            NewsListEntity entity = list.get(0);
            newsListDao.delete(entity);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteAll() {
        try {
            newsListDao.queryRaw("delete from tb_newslist");
            newsListDao.clearObjectCache();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
