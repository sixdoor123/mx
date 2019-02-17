package com.baiyi.jj.app.cache.Dao;

import android.content.Context;

import com.baiyi.jj.app.cache.bean.SupportBean;
import com.baiyi.jj.app.cache.helper.DatabaseHelper;
import com.baiyi.jj.app.utils.Utils;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
public class SupportDao {
    private Dao<SupportBean,Integer> supportDao;

    private DatabaseHelper dbHelper;

    public SupportDao(Context context) {
        try {
            dbHelper = DatabaseHelper.getHelper(context);
            supportDao = dbHelper.getDao(SupportBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *  判断数据库中是否存在这条数据
     */
    public boolean isSupport(String newsid,String mid){
        try {
//            List<SupportBean> entity = supportDao.queryForEq("newsId",newsid);
            Map<String,Object> map = new HashMap<>();
            map.put("newsId",newsid);
            map.put("mid",mid);
            List<SupportBean> entity = supportDao.queryForFieldValues(map);
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
    public void add(SupportBean bean) {
        try {
            if (isSupport(bean.getNewsId(),bean.getMid()))
            {
                return;
            }
            supportDao.create(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     *  清空
     */
    public void deleteAll() {
        try {
            supportDao.queryRaw("delete from tb_support");
            supportDao.clearObjectCache();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
