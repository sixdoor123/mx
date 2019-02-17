
package com.baiyi.jj.app.cache;

import java.util.HashMap;

/**
 * 告临时缓存view的管理类
 */
public class AdCacheManager {

    private static AdCacheManager instance = null;

    private HashMap<Long, Object> map = null;

    private AdCacheManager() {
        map = new HashMap<Long, Object>();
    }

    public static AdCacheManager getInstance() {
        if (instance == null) {
            synchronized (AdCacheManager.class) {
                instance = new AdCacheManager();
            }
        }
        return instance;
    }

    public void addCaChe(long id, Object o) {
        if (map.containsKey(id)) {
            return;
        }
        map.put(id, o);
    }

    public Object getCaChe(long id) {
        if (!map.containsKey(id)) {
            return null;
        }
        return map.get(id);
    }

    public void clear() {
        if (map != null) {
            map.clear();
        }
    }

}
