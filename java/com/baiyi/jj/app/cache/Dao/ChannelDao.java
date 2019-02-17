package com.baiyi.jj.app.cache.Dao;

import android.content.Context;
import android.util.Log;

import com.baiyi.jj.app.activity.attention.AttentionWordsEntity;
import com.baiyi.jj.app.cache.helper.DatabaseHelper;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.manager.ChannelDataManager;
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
public class ChannelDao {

    private Dao<ChannelItem, Integer> channelDao;

    private DatabaseHelper dbHelper;

    public ChannelDao(Context context) {
        try {
            dbHelper = DatabaseHelper.getHelper(context);
            channelDao = dbHelper.getDao(ChannelItem.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     */
    public void add2(ChannelItem bean) {
        try {
            channelDao.create(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(List<ChannelItem> beans) {
        try {
            for (ChannelItem item : beans) {
                if (!isChannel(item.getCid()))
                channelDao.createIfNotExists(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ChannelItem> queryForAllTag() {
        try {
            List<ChannelItem> items = channelDao.queryForEq("mid", "alltag");
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateTags(List<ChannelItem> list) {
        List<ChannelItem> items = queryForAllTag();
        if (Utils.isStringEmpty(items)) {
            add(list);
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            delete(items.get(i));
        }
        add(list);
    }

    /**
     */
    public void delete(ChannelItem bean) {
        try {
            channelDao.delete(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll() {
        try {
            channelDao.queryRaw("delete from tb_channel");
            channelDao.clearObjectCache();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * ����һ����¼
     */
    public void update(ChannelItem bean) {
        try {
            channelDao.update(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ChannelItem> queryByTablename(String tablename, String mid) {
        List<ChannelItem> themes = new ArrayList<>();
        try {
            themes = channelDao.queryBuilder().where().eq("channel_type", tablename).and().eq("mid", mid).query();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return themes;
    }

    public boolean isChannelsToMid(String mid) {
        try {
            List<ChannelItem> items = channelDao.queryForEq("mid", mid);
            if (Utils.isStringEmpty(items)) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    // 判断是否已存在
    public boolean isChannel(String cid) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("cid", cid);
            List<ChannelItem> items = channelDao.queryForFieldValues(map);
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

    // 查找全部
    public List<ChannelItem> queryForAll() {
        List<ChannelItem> themes = new ArrayList<>();
        try {
            themes = channelDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return themes;
    }
    // 通过类型查找订阅的频道
    public List<ChannelItem> queryByTypeSub(String channelType) {
        List<ChannelItem> themes = new ArrayList<>();
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("channel_type", channelType);
            if (!channelType.equals(ChannelDataManager.ChannelType_Video)){
                map.put("is_default", "true");
            }
            themes = channelDao.queryForFieldValues(map);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return themes;
    }
    // 通过类型查找频道
    public List<ChannelItem> queryByType(String videoType) {
        List<ChannelItem> themes = new ArrayList<>();
        try {
           themes = channelDao.queryForEq("channel_type", videoType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return themes;
    }
    // 通过类型删除频道
    public void deleteByType(String channeType){
        List<ChannelItem> list = queryByType(channeType);
        for (ChannelItem item : list){
            delete(item);
        }
    }
}
