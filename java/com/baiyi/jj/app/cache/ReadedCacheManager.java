/**
 *
 */
package com.baiyi.jj.app.cache;

import android.content.Context;

import com.baiyi.jj.app.cache.Dao.ReadDao;
import com.baiyi.jj.app.cache.bean.ReadBean;
import com.baiyi.jj.app.utils.Utils;

import java.util.HashMap;
import java.util.List;

/**
 * �������� ���� ֧�� ���� �ղ�
 * @author tangkun
 *
 */
public class ReadedCacheManager {

	private static ReadedCacheManager instance = null;

	private HashMap<String, Object> map = null;
	private ReadedCacheManager()
	{
		map = new HashMap<String, Object>();
	}

	public void init(Context context)
	{
		List<ReadBean> dataList = new ReadDao(context).queryForAll();
		if(Utils.isStringEmpty(dataList))
		{
			return;
		}
		for(ReadBean entity : dataList)
		{
			addCaChe(entity.getNewsId(), entity);
		}
	}

	public static ReadedCacheManager getInstance()
	{
		if(instance == null)
		{
			synchronized(ReadedCacheManager.class)
			{
				instance = new ReadedCacheManager();
			}
		}
		return instance;
	}

	/**
	 * ��ӻ���
	 * @param id
	 * @param o
	 */
	public void addCaChe(String id, Object o)
	{
		if(map.containsKey(id))
		{
			return;
		}
		map.put(id, o);
	}

	/**
	 */
	public void deleteCache(String key)
	{
		if(map.containsKey(key))
		{
			return;
		}
		map.remove(key);
	}

	/**
	 * �Ƿ��л���
	 * @param key
	 * @return
	 */
	public boolean isCache(String key)
	{
		if(map.containsKey(key))
		{
			return false;
		}
		if(map.get(key) != null)
		{
			return true;
		}
		return false;
	}

	public void clear()
	{
		if(map != null)
		{
			map.clear();
		}
	}

}
