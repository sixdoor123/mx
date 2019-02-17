package com.baiyi.jj.app.cache.Dao;

import android.content.Context;

import com.baiyi.jj.app.cache.bean.OfflineChannelBean;
import com.baiyi.jj.app.cache.helper.DatabaseHelper;
import com.baiyi.jj.app.utils.Utils;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/18 0018.
 */
public class OfflineChannelDao{

    private Dao<OfflineChannelBean,Integer> offlineDao;

    private DatabaseHelper dbHelper;


    public OfflineChannelDao(Context context) {
        try {
            dbHelper = DatabaseHelper.getHelper(context);
            offlineDao = dbHelper.getDao(OfflineChannelBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<OfflineChannelBean> queryByOffline(boolean isoffline,String language) {
        List<OfflineChannelBean> themes = new ArrayList<>();
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("isoffline", isoffline);
            map.put("language",language);
            themes = offlineDao.queryForFieldValues(map);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return themes;
    }
    public void deletebyLanguage(String language) {
        try {
            List<OfflineChannelBean> entitylist = offlineDao.queryForEq("language",language);
            for (OfflineChannelBean bean:entitylist){
                offlineDao.delete(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(List<OfflineChannelBean> beans) {
        try {
            for (OfflineChannelBean item : beans) {
                if (!isContain(item.getChannelid()))
                    offlineDao.create(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isContain(String cid) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("channelid", cid);
            List<OfflineChannelBean> items = offlineDao.queryForFieldValues(map);
            if (Utils.isStringEmpty(items)) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }
    
    public void deleteAll2() {
        try {
            offlineDao.queryRaw("delete from tb_offlinechannel");
            offlineDao.clearObjectCache();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<OfflineChannelBean> queryForAll() {
        List<OfflineChannelBean> themes = new ArrayList<>();
        try {
            themes = offlineDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return themes;
    }
}
