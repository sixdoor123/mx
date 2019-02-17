package com.baiyi.jj.app.cache.Dao;

import android.content.Context;

import com.baiyi.jj.app.cache.ReadedCacheManager;
import com.baiyi.jj.app.cache.bean.CollectBean;
import com.baiyi.jj.app.cache.bean.ReadBean;
import com.baiyi.jj.app.cache.helper.DatabaseHelper;
import com.baiyi.jj.app.utils.Utils;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
public class ReadDao {

    private Dao<ReadBean,Integer> readDao;

    private DatabaseHelper dbHelper;

    public ReadDao(Context context) {
        try {
            dbHelper = DatabaseHelper.getHelper(context);
            readDao = dbHelper.getDao(ReadBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *  判断数据库中是否存在这条数据
     */
    public boolean isSupport(String newsid){
        try {
            if(Utils.isStringEmpty(newsid))
            {
                return false;
            }
            //先从缓存中取
            if(ReadedCacheManager.getInstance().isCache(newsid))
            {
                return true;
            }
            List<ReadBean> entity = readDao.queryForEq("newsId",newsid);
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
    public void add(ReadBean bean) {
        try {
            if (isSupport(bean.getNewsId()))
            {
                return;
            }
            readDao.create(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     *  清空
     */
    public void deleteAll() {
        try {
            readDao.queryRaw("delete from tb_read");
            readDao.clearObjectCache();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有记录
     * @return
     */
    public List<ReadBean> queryForAll() {
        List<ReadBean> themes = new ArrayList<>();
        try {
            themes = readDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return themes;
    }
}
