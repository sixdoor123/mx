package com.baiyi.jj.app.cache.Dao;

import android.content.Context;

import com.baiyi.jj.app.cache.bean.EditionReadBean;
import com.baiyi.jj.app.cache.helper.DatabaseHelper;
import com.baiyi.jj.app.utils.Utils;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2017/1/7 0007.
 */
public class EditionReadDao {

    private Dao<EditionReadBean,Integer> editionDao;

    private DatabaseHelper dbHelper;

    public EditionReadDao(Context context) {
        try {
            dbHelper = DatabaseHelper.getHelper(context);
            editionDao = dbHelper.getDao(EditionReadBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     *  判断数据库中是否存在这条数据
     */
    public boolean isSupport(String newsid){
        try {
            if(Utils.isStringEmpty(newsid))
            {
                return false;
            }
            List<EditionReadBean> entity = editionDao.queryForEq("newsId",newsid);
            if (!Utils.isStringEmpty(entity))
            {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 添加一条记录
     */
    public void add(EditionReadBean bean) {
        try {
            if (isSupport(bean.getNewsId()))
            {
                return;
            }
            editionDao.create(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     *  清空
     */
    public void deleteAll() {
        try {
            editionDao.queryRaw("delete from tb_editionread");
            editionDao.clearObjectCache();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
