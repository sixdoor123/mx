package com.baiyi.jj.app.cache.Dao;

import android.content.Context;
import com.baiyi.jj.app.cache.bean.HistoryBean;
import com.baiyi.jj.app.cache.helper.DatabaseHelper;
import com.baiyi.jj.app.utils.TLog;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/14 0014.
 */
public class HistoryDao
{

    private Dao<HistoryBean,Integer> historyDao;
    private DatabaseHelper dbHelper;

    public HistoryDao(Context context) {
        try {
            dbHelper = DatabaseHelper.getHelper(context);
            historyDao = dbHelper.getDao(HistoryBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean isNUll(){
        return historyDao == null;
    }

    /**
     * ���һ�����?
     */
    public void add(HistoryBean bean) {
        try {
            int i = historyDao.create(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * ɾ��һ����¼
     */
    public void delete(HistoryBean bean) {
        try {
            historyDao.delete(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteAll() {
        try {
            historyDao.queryRaw("delete from tb_history");
            historyDao.clearObjectCache();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * ����һ����¼
     */
    public void update(HistoryBean bean) {
        try {
            historyDao.update(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * ��ѯһ����¼
     * @param id
     * @return
     */
    public HistoryBean queryForId(int id) {
        HistoryBean bean = null;
        try {
            bean = historyDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 根据字段名查�?
     */
    public void queryAndDeleteOther(String name) {
        List<HistoryBean> themes = new ArrayList<>();
        try {
            QueryBuilder builder = historyDao.queryBuilder();
            builder .where().eq("hisname",name);
            themes =  builder .query();
            for (int i = 0;i < themes.size()-1;i++) {
                delete(themes.get(i));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    /**
     * ��ѯ���м�¼
     * @return
     */
    public List<HistoryBean> queryForAll() {
        List<HistoryBean> themes = new ArrayList<>();
        try {
            themes = historyDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return themes;
    }
}
