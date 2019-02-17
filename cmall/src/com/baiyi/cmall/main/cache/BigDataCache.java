package com.baiyi.cmall.main.cache;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Hashtable;

import com.baiyi.cmall.entity.GoodsSourceInfo;


/**
 * ���ݵĻ���
 * @author sunxy
 */
public class BigDataCache{
	static private BigDataCache cache;// һ��Cacheʵ��
	private Hashtable<String, GoodsSourceInfoRef> GoodsSourceInfoRefs;// ����Chche���ݵĴ洢
	private ReferenceQueue<GoodsSourceInfo> q;// ����Reference�Ķ���

	// �̳�SoftReference��ʹ��ÿһ��ʵ�������п�ʶ��ı�ʶ��
	private class GoodsSourceInfoRef extends SoftReference<GoodsSourceInfo> {
		private String _key = "";

		public GoodsSourceInfoRef(GoodsSourceInfo info, ReferenceQueue<GoodsSourceInfo> q) {
			super(info, q);
			_key = info.getId();
		}
	}

	// ����һ��������ʵ��
	private BigDataCache() {
		GoodsSourceInfoRefs = new Hashtable<String, GoodsSourceInfoRef>();
		q = new ReferenceQueue<GoodsSourceInfo>();
	}

	// ȡ�û�����ʵ��
	public static BigDataCache getInstance() {
		if (cache == null) {
			cache = new BigDataCache();
		}
		return cache;
	}

	// �������õķ�ʽ��һ��GoodsSourceInfo�����ʵ���������ò����������
	private void cacheGoodsSourceInfo(GoodsSourceInfo info) {
		cleanCache();// �����������
		GoodsSourceInfoRef ref = new GoodsSourceInfoRef(info, q);
		GoodsSourceInfoRefs.put(info.getId(), ref);
	}

	// ������ָ����ID�ţ����»�ȡ��ӦGoodsSourceInfo�����ʵ��
	public GoodsSourceInfo getGoodsSourceInfo(String ID) {
		GoodsSourceInfo info = null;
		// �������Ƿ��и�GoodsSourceInfoʵ���������ã�����У�����������ȡ�á�
		if (GoodsSourceInfoRefs.containsKey(ID)) {
			GoodsSourceInfoRef ref = (GoodsSourceInfoRef) GoodsSourceInfoRefs.get(ID);
			info = (GoodsSourceInfo) ref.get();
		}
		// ���û�������ã����ߴ��������еõ���ʵ����null�����¹���һ��ʵ����
		// �����������½�ʵ����������
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

	// ���Cache�ڵ�ȫ������
	public void clearCache() {
		cleanCache();
		GoodsSourceInfoRefs.clear();
		System.gc();
		System.runFinalization();
	}
}
