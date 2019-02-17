package com.baiyi.cmall.main.cache;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Hashtable;

import com.baiyi.cmall.entity.GoodsSourceInfo;


/**
 * 数据的缓存
 * @author sunxy
 */
public class BigDataCache{
	static private BigDataCache cache;// 一个Cache实例
	private Hashtable<String, GoodsSourceInfoRef> GoodsSourceInfoRefs;// 用于Chche内容的存储
	private ReferenceQueue<GoodsSourceInfo> q;// 垃圾Reference的队列

	// 继承SoftReference，使得每一个实例都具有可识别的标识。
	private class GoodsSourceInfoRef extends SoftReference<GoodsSourceInfo> {
		private String _key = "";

		public GoodsSourceInfoRef(GoodsSourceInfo info, ReferenceQueue<GoodsSourceInfo> q) {
			super(info, q);
			_key = info.getId();
		}
	}

	// 构建一个缓存器实例
	private BigDataCache() {
		GoodsSourceInfoRefs = new Hashtable<String, GoodsSourceInfoRef>();
		q = new ReferenceQueue<GoodsSourceInfo>();
	}

	// 取得缓存器实例
	public static BigDataCache getInstance() {
		if (cache == null) {
			cache = new BigDataCache();
		}
		return cache;
	}

	// 以软引用的方式对一个GoodsSourceInfo对象的实例进行引用并保存该引用
	private void cacheGoodsSourceInfo(GoodsSourceInfo info) {
		cleanCache();// 清除垃圾引用
		GoodsSourceInfoRef ref = new GoodsSourceInfoRef(info, q);
		GoodsSourceInfoRefs.put(info.getId(), ref);
	}

	// 依据所指定的ID号，重新获取相应GoodsSourceInfo对象的实例
	public GoodsSourceInfo getGoodsSourceInfo(String ID) {
		GoodsSourceInfo info = null;
		// 缓存中是否有该GoodsSourceInfo实例的软引用，如果有，从软引用中取得。
		if (GoodsSourceInfoRefs.containsKey(ID)) {
			GoodsSourceInfoRef ref = (GoodsSourceInfoRef) GoodsSourceInfoRefs.get(ID);
			info = (GoodsSourceInfo) ref.get();
		}
		// 如果没有软引用，或者从软引用中得到的实例是null，重新构建一个实例，
		// 并保存对这个新建实例的软引用
		if (info == null) {
			info = new GoodsSourceInfo();
			info.setId(ID);
			System.out.println("Retrieve From GoodsSourceInfoInfoCenter. ID=" + ID);
			this.cacheGoodsSourceInfo(info);
		}
		return info;
	}

	private void cleanCache() {
		GoodsSourceInfoRef ref = null;
		while ((ref = (GoodsSourceInfoRef) q.poll()) != null) {
			GoodsSourceInfoRefs.remove(ref._key);
		}
	}

	// 清除Cache内的全部内容
	public void clearCache() {
		cleanCache();
		GoodsSourceInfoRefs.clear();
		System.gc();
		System.runFinalization();
	}
}
