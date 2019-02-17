package com.baiyi.cmall.database.dao;

import java.util.List;

import com.baiyi.cmall.database.Guanzhu;
import com.baiyi.cmall.database.loader.GoodSSourcesDao;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.loader.LoaderResult;
import com.baiyi.core.loader.sql.BaseNormalDaoLoader;

/**
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-2-1 下午1:24:41
 */
public class GoodSSourceLoader extends BaseNormalDaoLoader<GoodSSourcesDao> {
	private static final String Type_SelectById = "Type_SelectById";

	private int id;

	public void setId(int Id) {
		this.id = Id;
		setOpType(Type_SelectById);
	}

	private LoaderResult getItemToId() {
		LoaderResult result = new LoaderResult();
		List<GoodsSourceInfo> dataList = dao.getItem(id);
		result.setCode(1);
		result.setResult(dataList);
		result.setTag(getTag());
		return result;
	}

	public void deleteAll(){
		dao.deleteAll();
	}
	/**
	 * 根据ID查找
	 * 
	 * @return
	 */
	public LoaderResult selectById() {
		LoaderResult result = new LoaderResult();
		List<GoodsSourceInfo> dataList = dao.selectById(String.valueOf(id));
		if (Utils.isStringEmpty(dataList)) {
			result.setCode(1);
			result.setResult(null);
			return result;
		}
		result.setCode(1);
		result.setResult(dataList);
		result.setTag(getTag());
		return result;
	}

	@Override
	protected LoaderResult onVisitor(String arg0) {
		// TODO Auto-generated method stub
		LoaderResult result = null;
		if (arg0.equals(Type_SelectById)) {
			result = getItemToId();
		}
		return result;
	}

}
