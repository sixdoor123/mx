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
 * @date 2016-2-1 下午1:45:26
 */
public class GoodSSourcesDao extends AbstractNormalDao<GoodsSourceInfo> {public GoodSSourcesDao() {
	// TODO Auto-generated constructor stub
}
	/**
	 * PersonId等于某一值时，得到List
	 * 
	 * @param PersonID
	 * @return
	 */
	public List<GoodsSourceInfo> selectById(String id) {
		String selection = "WHERE GOODSID = ?";
		return getList(selection, new String[] { id });
	}

	/**
	 * 性别等于sex时，删除这条数据
	 */
	public void deleteById(String id) {
		delete("WHERE ID = ?", new String[] { String.valueOf(id) });

	}


	public List<GoodsSourceInfo> getItem(int id) {
		String selection = "WHERE ID = ?";
		return getList(selection, new String[] { String.valueOf(id) });
	}

}
