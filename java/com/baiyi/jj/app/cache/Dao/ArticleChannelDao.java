package com.baiyi.jj.app.cache.Dao;

import android.content.Context;

import com.baiyi.jj.app.cache.helper.DatabaseHelper;
import com.baiyi.jj.app.entity.article.ArticleSeqEntity;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/14 0014.
 */
public class ArticleChannelDao {

    private Dao<ArticleSeqEntity,Integer> channelIndexDao;

    private DatabaseHelper dbHelper;

    public ArticleChannelDao(Context context) {
        try {
            dbHelper = DatabaseHelper.getHelper(context);
            channelIndexDao = dbHelper.getDao(ArticleSeqEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ArticleSeqEntity> queryByChannelId(String channid) {
        List<ArticleSeqEntity> themes = new ArrayList<>();
        try {
            themes = channelIndexDao.queryForEq("channelseq",channid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return themes;
    }

    /**
     * add
     */
    public void add(ArticleSeqEntity bean) {
        try {
            channelIndexDao.create(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteAll() {
        try {
            channelIndexDao.queryRaw("delete from tb_articleindexs");
            channelIndexDao.clearObjectCache();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * ��ѯ���м�¼
     * @return
     */
    public List<ArticleSeqEntity> queryForAll() {
        List<ArticleSeqEntity> themes = new ArrayList<>();
        try {
            themes = channelIndexDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return themes;
    }

}
