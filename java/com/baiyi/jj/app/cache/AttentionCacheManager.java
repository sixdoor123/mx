package com.baiyi.jj.app.cache;

import com.baiyi.jj.app.activity.attention.AttentionWordsEntity;
import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.utils.Utils;

import java.util.HashMap;
import java.util.List;

/**
 * 关注状态管理类
 */
public class AttentionCacheManager {

    private static AttentionCacheManager instance = null;

    private boolean tagRefresh = false;

    private HashMap<String, Object> map = null;

    private AttentionCacheManager() {
        map = new HashMap<String, Object>();
    }

    public static AttentionCacheManager getInstance() {
        if (instance == null) {
            synchronized (AttentionCacheManager.class) {
                instance = new AttentionCacheManager();
            }
        }
        return instance;
    }

    public boolean isTagRefresh() {
        return tagRefresh;
    }

    public void setTagRefresh(boolean tagRefresh) {
        this.tagRefresh = tagRefresh;
    }

    public void addCacheList(List<AttentionWordsEntity> dataList) {
        if (Utils.isStringEmpty(dataList)) {
            return;
        }
        for (AttentionWordsEntity entity : dataList) {
            addOneCaChe(entity.getChannel_id()+""+entity.getHotword_id(), entity);
        }
    }

    public boolean isHaveCache(String id){
        if (map.containsKey(id)) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * 添加缓存
     */
    public void addOneCaChe(String id, Object o) {
        if (map.containsKey(id)) {
            return;
        }
        map.put(id, o);
    }

    /**
     * 删除缓存
     */
    public void deleteOneCache(String id) {
        if (!map.containsKey(id)) {
            return;
        }
        map.remove(id);
    }

    /**
     * 清空全部
     */
    public void clear() {
        if (map != null) {
            map.clear();
        }
    }

}
