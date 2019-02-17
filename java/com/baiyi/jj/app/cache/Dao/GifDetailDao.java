package com.baiyi.jj.app.cache.Dao;

import android.content.Context;

import com.baiyi.jj.app.cache.helper.DatabaseHelper;
import com.baiyi.jj.app.entity.article.GifEntity;
import com.baiyi.jj.app.utils.Utils;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/10/19 0019.
 */
public class GifDetailDao {

    private Dao<GifEntity, Integer> gifDao;

    private DatabaseHelper dbHelper;

    public GifDetailDao(Context context) {
        try {
            dbHelper = DatabaseHelper.getHelper(context);
            gifDao = dbHelper.getDao(GifEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(GifEntity bean) {
        try {
            gifDao.create(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll() {
        try {
            gifDao.queryRaw("delete from tb_gif");
            gifDao.clearObjectCache();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public GifEntity getGifEntityToId(String id) {
        try {
            List<GifEntity> items = gifDao.queryForEq("article_id", id);
            if (Utils.isStringEmpty(items)) {
                return null;
            } else {
                return items.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
