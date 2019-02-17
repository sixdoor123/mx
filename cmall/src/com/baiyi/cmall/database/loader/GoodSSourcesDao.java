package com.baiyi.cmall.database.loader;

import java.util.ArrayList;
import java.util.List;

import com.baiyi.cmall.database.Guanzhu;
import com.baiyi.cmall.database.dao.GoodSSourceLoader;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.core.database.dao.AbstractNormalDao;

/**
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-2-1 ����1:45:26
 */
public class GoodSSourcesDao extends AbstractNormalDao<GoodsSourceInfo> {public GoodSSourcesDao() {
	// TODO Auto-generated constructor stub
}
	/**
	 * PersonId����ĳһֵʱ���õ�List
	 * 
	 * @param PersonID
	 * @return
	 */
	public List<GoodsSourceInfo> selectById(String id) {
		String selection = "WHERE GOODSID = ?";
		return getList(selection, new String[] { id });
	}

	/**
	 * �Ա����sexʱ��ɾ����������
	 */
	public void deleteById(String id) {
		delete("WHERE ID = ?", new String[] { String.valueOf(id) });

	}


	public List<GoodsSourceInfo> getItem(int id) {
		String selection = "WHERE ID = ?";
		return getList(selection, new String[] { String.valueOf(id) });
	}

}
