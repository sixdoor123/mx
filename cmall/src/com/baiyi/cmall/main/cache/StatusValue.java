package com.baiyi.cmall.main.cache;

/**
 *	各种状态值
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-3-21 
 *       上午11:13:16
 */
public class StatusValue {
	/**
	 * 在什么状态下缓存什么数据
	 */
	// 缓存登录数据
	public static final int Login_Status_Cache = 1;
	// 缓存基础数据
	public static final int Base_Data_Cache = 2;
	// 图片缓存
	public static final int Bitmap_Cache = 3;
	/**
	 * 数据缓存的Key
	 */
	// 缓存登录数据的Key
	public static final int Login_Cache_Key = 1;
	// 缓存基础数据的Key
	public static final int Base_Data_Cache_Key = 2;
	// 图片缓存的Key
	public static final int Bitmap_Cache_Key = 3;
}
