package com.baiyi.cmall.main.cache;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.LoginInfo;

/**
 * 数据缓存
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-3-21 上午10:22:43
 */
public class DataCache implements BaseCache {

	private Context context;
	/**
	 * 缓存基础数据
	 */
	private LruCache<Integer, GoodsSourceInfo> baseDataCache = null;

	/**
	 * 缓存登录数据
	 */
	private LruCache<Integer, LoginInfo> loginCache = null;
	/**
	 * 缓存图片数据
	 */
	private LruCache<Integer, Bitmap> bitmapCache = null;
	// 分配内存大小
	public static final int Momery_Size = 2 * 1024 * 1024 * 1024;

	/**
	 * 软引用缓存
	 */
	Map<Integer, SoftReference<GoodsSourceInfo>> baseDatas = new HashMap<Integer, SoftReference<GoodsSourceInfo>>();
	Map<Integer, SoftReference<LoginInfo>> loginInfos = new HashMap<Integer, SoftReference<LoginInfo>>();

	public DataCache(Context context) {
		this.context = context;
	}

	public void init(int status) {

		switch (status) {
		case StatusValue.Login_Status_Cache:
			loginCache = new LruCache<Integer, LoginInfo>(Momery_Size);
			break;
		case StatusValue.Base_Data_Cache:
			baseDataCache = new LruCache<Integer, GoodsSourceInfo>(Momery_Size);
			break;
		case StatusValue.Bitmap_Cache:
			bitmapCache = new LruCache<Integer, Bitmap>(Momery_Size);
			break;
		}
	}

	private static DataCache cache;

	public static DataCache getInstence(Context context) {
		if (null == cache) {
			cache = new DataCache(context);
		}
		return cache;
	}

	@Override
	public void put(int statue, Integer key, Object value) {
		switch (statue) {
		case StatusValue.Login_Status_Cache:// 登录数据缓存
			if (null != loginCache) {
				loginCache.put(key, (LoginInfo) value);
			}
			break;
		case StatusValue.Base_Data_Cache:// 基础数据缓存
			if (null != baseDataCache) {
				baseDataCache.put(key, (GoodsSourceInfo) value);
			}
			break;
		case StatusValue.Bitmap_Cache:// 图片数据缓存
			if (null != bitmapCache) {
				bitmapCache.put(key, (Bitmap) value);
			}
			break;
		}
	}

	@Override
	public Object get(int statue, Integer key) {
		Object object = null;
		switch (statue) {
		case StatusValue.Login_Status_Cache:
			if (null != loginCache) {
				object = loginCache.get(key);
			}
			return object;
		case StatusValue.Base_Data_Cache:
			if (null != baseDataCache) {
				object = baseDataCache.get(key);
			}
			return object;
		case StatusValue.Bitmap_Cache:
			if (null != bitmapCache) {
				object = bitmapCache.get(key);
			}
			return object;
		}
		return null;
	}

	@Override
	public void remove(int statue, Integer key) {
		switch (statue) {
		case StatusValue.Login_Status_Cache:
			if (null != loginCache) {
				loginCache.remove(key);
			}
			break;
		case StatusValue.Base_Data_Cache:
			if (null != baseDataCache) {
				baseDataCache.remove(key);
			}
			break;
		case StatusValue.Bitmap_Cache:
			if (null != bitmapCache) {
				bitmapCache.remove(key);
			}
			break;
		}
	}

	@Override
	public void update(int statue, Integer key, Object value) {
		switch (statue) {
		case StatusValue.Login_Status_Cache:// 登录数据缓存更新
			if (null != loginCache) {
				loginCache.put(key, (LoginInfo) value);
			}
			break;
		case StatusValue.Base_Data_Cache:// 基础数据缓存更新
			if (null != baseDataCache) {
				baseDataCache.put(key, (GoodsSourceInfo) value);
			}
			break;
		case StatusValue.Bitmap_Cache:// 图片数据缓存更新
			if (null != bitmapCache) {
				bitmapCache.put(key, (Bitmap) value);
			}
			break;
		}
	}

	@Override
	public boolean isExist(int status, Integer key) {
		Object object = null;
		switch (status) {
		case StatusValue.Login_Status_Cache:
			if (null != loginCache) {
				object = loginCache.get(key);
			}
			if (object != null) {
				return true;
			}
			return false;
		case StatusValue.Base_Data_Cache:
			if (baseDataCache != null) {
				object = baseDataCache.get(key);
			}
			if (object != null) {
				return true;
			}
			return false;
		case StatusValue.Bitmap_Cache:
			if (bitmapCache != null) {
				object = bitmapCache.get(key);
			}
			if (object != null) {
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public void clear() {

		
		context.deleteDatabase("webview.db");
		context.deleteDatabase("webview.db-shm");
		context.deleteDatabase("webview.db-wal");
		context.deleteDatabase("webviewCache.db");
		context.deleteDatabase("webviewCache.db-shm");
		context.deleteDatabase("webviewCache.db-wal");
		// 清除数据缓存
		clearFolder(context.getFilesDir(), System.currentTimeMillis());
		clearFolder(context.getCacheDir(), System.currentTimeMillis());
	}

	/**
	 * 采用若引用存储基础数据
	 * 
	 * @param key
	 * @param value
	 */
	public void putBaseData(int key, Object value) {
		GoodsSourceInfo info = (GoodsSourceInfo) value;
		SoftReference<GoodsSourceInfo> softReference = new SoftReference<GoodsSourceInfo>(
				info);
		baseDatas.put(key, softReference);
	}

	/**
	 * 在若引用的情况下读取基础数据
	 * 
	 * @param key
	 * @return
	 */
	public GoodsSourceInfo getBaseData(int key) {
		SoftReference<GoodsSourceInfo> softReference = baseDatas.get(key);
		GoodsSourceInfo info = softReference.get();
		return info;
	}

	/**
	 * 采用若引用存储登录数据
	 * 
	 * @param key
	 * @param value
	 */
	public void putLoginInfo(int key, Object value) {
		LoginInfo info = (LoginInfo) value;
		SoftReference<LoginInfo> softReference = new SoftReference<LoginInfo>(
				info);
		loginInfos.put(key, softReference);
	}

	/**
	 * 在若引用的情况下读取登录数据
	 * 
	 * @param key
	 * @return
	 */
	public LoginInfo getLoginInfo(int key) {
		SoftReference<LoginInfo> softReference = loginInfos.get(key);
		LoginInfo info = softReference.get();
		return info;
	}
	
	/**
	 * 清除文件、缓存
	 * 
	 * @param dir
	 *            目录
	 * @param numDays
	 *            当前系统时间
	 * @return
	 */
	public static int clearFolder(File dir, long curTime) {
		int cnt = 0;//清空缓存失败的最大次数
		int deletedFiles = 0;
		do {
			if (dir != null && dir.isDirectory()) {
				try {
					for (File child : dir.listFiles()) {
						if (child.isDirectory()) {
							deletedFiles += clearFolder(child, curTime);
						}
						if (child.lastModified() < curTime) {
							if (child.delete()) {
								deletedFiles++;
							}
						}
					}
				} catch (Exception e) {
					cnt++;
					Log.i("TAG", "清除缓存失败,正在尝试第" + cnt + "清除缓存");
				}
			}
			return deletedFiles;
		} while (cnt < 3);

	}
}
