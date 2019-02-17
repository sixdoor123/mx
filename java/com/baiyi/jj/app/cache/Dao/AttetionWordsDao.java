package com.baiyi.jj.app.cache.Dao;

import android.content.Context;

import com.baiyi.jj.app.activity.attention.AttentionWordsEntity;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.cache.helper.DatabaseHelper;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.utils.TLog;
import com.baiyi.jj.app.utils.Utils;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/30 0030.
 */
public class AttetionWordsDao {

    private Dao<AttentionWordsEntity, Integer> wordsDao;
    private DatabaseHelper dbHelper;

    public AttetionWordsDao(Context context) {
        try {
            dbHelper = DatabaseHelper.getHelper(context);
            wordsDao = dbHelper.getDao(AttentionWordsEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isContain(String channelID, String mid, int hotID) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("channel_id", channelID);
            map.put("mid", mid);
            map.put("hotword_id", hotID);
            List<AttentionWordsEntity> list = wordsDao.queryForFieldValues(map);
            if (Utils.isStringEmpty(list)) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //
    public void addCacheList(List<AttentionWordsEntity> beans, String channelID) {
        try {
            for (AttentionWordsEntity item : beans) {
                if (!isContain(channelID, item.getMid(), item.getHotword_id())) {
                    TLog.e("true",channelID+"==========="+item.getMid()+"======"+item.getHotword_id());
                    item.setChannel_id(channelID);
                    wordsDao.create(item);
                }else {
                    TLog.e("false",channelID+"==========="+item.getMid()+"======"+item.getHotword_id());
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteCountryTag() {
        if (CmsApplication.getUserInfoEntity()!=null){
            try {
                List<AttentionWordsEntity> list = queryAllAttetioned(CmsApplication.getUserInfoEntity().getMID());

               for (int i = 0;i<list.size();i++){
                   AttentionWordsEntity entity = list.get(i);
                   wordsDao.delete(entity);
               }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


    // update
    public void updateCacheList(List<AttentionWordsEntity> beans) {
        for (AttentionWordsEntity item : beans) {
            updateOrAdd(item);
        }
    }
    public void updateOrAdd(AttentionWordsEntity entity) {
        if (Utils.isStringEmpty(entity.getMid()) && CmsApplication.getUserInfoEntity()!=null){
            entity.setMid(CmsApplication.getUserInfoEntity().getMID());
        }
        try {
            AttentionWordsEntity queryEntity = queryEntitybyId(entity);
            if (queryEntity == null) {
//                wordsDao.create(entity);
            } else {
                queryEntity.setAttet(entity.isAttet());
                wordsDao.update(queryEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public void update(AttentionWordsEntity bean, boolean isAttention) {
//        try {
//            bean.setAttet(isAttention);
//            wordsDao.update(bean);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }



    private AttentionWordsEntity queryEntitybyId(AttentionWordsEntity entity) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("mid", entity.getMid());
            map.put("hotword_id", entity.getHotword_id());
            List<AttentionWordsEntity> list = wordsDao.queryForFieldValues(map);
            if (Utils.isStringEmpty(list)) {
                return null;
            }
            return list.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteAll() {
        try {
            wordsDao.queryRaw("delete from tb_attetion");
            wordsDao.clearObjectCache();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<AttentionWordsEntity> queryForChannel(String channelID, String mid) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("channel_id", channelID);
            map.put("mid", mid);
            List<AttentionWordsEntity> items = wordsDao.queryForFieldValues(map);
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<AttentionWordsEntity> queryAllAttetioned(String mid) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("mid", mid);
            map.put("isAttet", true);
            List<AttentionWordsEntity> items = wordsDao.queryForFieldValues(map);
            return items;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
