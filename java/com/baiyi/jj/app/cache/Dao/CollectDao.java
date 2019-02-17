package com.baiyi.jj.app.cache.Dao;

import android.content.Context;
import android.util.Log;

import com.baiyi.jj.app.cache.bean.CollectBean;
import com.baiyi.jj.app.cache.bean.HistoryBean;
import com.baiyi.jj.app.cache.helper.DatabaseHelper;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ObjectCache;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
public class CollectDao {

    private Dao<CollectBean,Integer> collectDao;

    private DatabaseHelper dbHelper;

    public CollectDao(Context context) {
        try {
            dbHelper = DatabaseHelper.getHelper(context);
            collectDao = dbHelper.getDao(CollectBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *  判断数据库中是否存在这条数据
     */
    public boolean isCollect(String newsid,String mid){
        try {
//            List<CollectBean> entity = collectDao.queryForEq("newsId",newsid);
            Map<String,Object> map = new HashMap<>();
            map.put("newsId",newsid);
            map.put("mid",mid);
            List<CollectBean> entity = collectDao.queryForFieldValues(map);
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
    public void add(CollectBean bean) {
        try {
            if (isCollect(bean.getNewsId(),bean.getMid()))
            {
                return;
            }
            collectDao.create(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 删除一条记录
     */
    public void delete(String newsid,String mid) {
        try {
//            List<CollectBean> entity = collectDao.queryForEq("newsId",newsid);
            Map<String,Object> map = new HashMap<>();
            map.put("newsId",newsid);
            map.put("mid",mid);
            List<CollectBean> entity = collectDao.queryForFieldValues(map);
            if (Utils.isStringEmpty(entity)){
                return;
            }
           int i =  collectDao.delete(entity.get(0));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *  清空
     */
    public void deleteAll() {
        try {
            collectDao.queryRaw("delete from tb_collect");
            collectDao.clearObjectCache();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新一条记录
     */
    public void update(CollectBean bean) {
        try {
            collectDao.update(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CollectBean queryBean(String newsid,String mid) {
        CollectBean bean = null;
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("newsId",newsid);
            map.put("mid",mid);
            List<CollectBean> entity = collectDao.queryForFieldValues(map);
            if (!Utils.isStringEmpty(entity)){
                return entity.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 查询一条记录
     * @param id
     * @return
     */
    public CollectBean queryForId(int id) {
        CollectBean bean = null;
        try {
            bean = collectDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }
    /**
     * 查询所有记录
     * @return
     */
    public List<CollectBean> queryForAll() {
        List<CollectBean> themes = new ArrayList<>();
        try {
            themes = collectDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return themes;
    }

}
