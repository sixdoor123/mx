package com.baiyi.cmall.main.cache;

/**
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-3-21 ÉÏÎç10:32:54
 */
public interface BaseCache {

	void put(int statue, Integer key, Object value);

	Object get(int statue, Integer key);

	void remove(int statue, Integer key);

	void clear();

	void update(int statue, Integer key, Object value);

	boolean isExist(int status, Integer key);
}
