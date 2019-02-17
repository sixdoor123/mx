
package com.baiyi.jj.app.cache;

import java.util.HashMap;

/**
 * 告临时缓存view的管理类
 */
public class AdCacheManager2 {

    private static AdCacheManager2 instance = null;

    private HashMap<Long, Object> map = null;

    private AdCacheManager2() {
        map = new HashMap<Long, Object>();
    }

    public static AdCacheManager2 getInstance() {
        if (instance == null) {
            synchronized (AdCacheManager.class) {
                instance = new AdCacheManager2();
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
