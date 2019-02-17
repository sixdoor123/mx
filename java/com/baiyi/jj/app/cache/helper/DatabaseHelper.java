package com.baiyi.jj.app.cache.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.baiyi.jj.app.activity.attention.AttentionWordsEntity;
import com.baiyi.jj.app.cache.Dao.ConfigDao;
import com.baiyi.jj.app.cache.bean.CollectBean;
import com.baiyi.jj.app.cache.bean.ConfigurationBean;
import com.baiyi.jj.app.cache.bean.EditionReadBean;
import com.baiyi.jj.app.cache.bean.HistoryBean;
import com.baiyi.jj.app.cache.bean.OfflineChannelBean;
import com.baiyi.jj.app.cache.bean.ReadBean;
import com.baiyi.jj.app.cache.bean.SupportBean;
import com.baiyi.jj.app.cache.bean.WebDetailBean;
import com.baiyi.jj.app.entity.ChannelItem;
import com.baiyi.jj.app.entity.NewsListEntity;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.entity.article.ArticleChannel;
import com.baiyi.jj.app.entity.article.ArticleEntity;
import com.baiyi.jj.app.entity.article.ArticleCacheEntity;
import com.baiyi.jj.app.entity.article.ArticleSeqEntity;
import com.baiyi.jj.app.entity.article.GifEntity;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/14 0014.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper{

    private  static final String TABLE_NAME = "sqlite_cms.db";
    /**
     * ���ݿ�汾
     */
    private static final int DB_VERSION = 43;

    private int updateVer = 28;
    private int deleteVer = 30;

    /**
     * �������Dao�ĵ�ͼ
     */
    private Map<String, Dao> daos = new HashMap<>();
    private static DatabaseHelper instance;
    private Context context;

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, DB_VERSION);
        this.context = context;
    }

    /**
     * ��ȡ����
     * @param context
     * @return
     */
    public static synchronized DatabaseHelper getHelper(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null) {
                    instance = new DatabaseHelper(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTableIfNotExists(connectionSource, HistoryBean.class);
            TableUtils.createTableIfNotExists(connectionSource, CollectBean.class);
            TableUtils.createTableIfNotExists(connectionSource, SupportBean.class);
            TableUtils.createTableIfNotExists(connectionSource, ReadBean.class);
            TableUtils.createTableIfNotExists(connectionSource, ArticleEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, ArticleCacheEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, ArticleSeqEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, NewsListEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, ChannelItem.class);
            TableUtils.createTableIfNotExists(connectionSource, ArticleChannel.class);
            TableUtils.createTableIfNotExists(connectionSource, AttentionWordsEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, UserInfoEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, ConfigurationBean.class);
            TableUtils.createTableIfNotExists(connectionSource, EditionReadBean.class);
            TableUtils.createTableIfNotExists(connectionSource, WebDetailBean.class);
            TableUtils.createTableIfNotExists(connectionSource, GifEntity.class);
            TableUtils.createTableIfNotExists(connectionSource, OfflineChannelBean.class);
            refreshConfig(context);
//            addChannelList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
//            TableUtils.dropTable(connectionSource,HistoryBean.class,true);
//            TableUtils.dropTable(connectionSource, CollectBean.class,true);
//            TableUtils.dropTable(connectionSource, SupportBean.class,true);
//            TableUtils.dropTable(connectionSource, ReadBean.class,true);
//            TableUtils.dropTable(connectionSource,ArticleEntity.class,true);
//            TableUtils.dropTable(connectionSource,ArticleCacheEntity.class,true);
//            TableUtils.dropTable(connectionSource,ArticleSeqEntity.class,true);
//            TableUtils.dropTable(connectionSource,NewsListEntity.class,true);
//            TableUtils.dropTable(connectionSource,ChannelItem.class,true);
//            TableUtils.dropTable(connectionSource,UserInfoEntity.class,true);
//            TableUtils.dropTable(connectionSource,ArticleChannel.class,true);
//            TableUtils.dropTable(connectionSource,UserInfoEntity.class,true);
//            TableUtils.dropTable(connectionSource,AttentionWordsEntity.class,true);
//            TableUtils.dropTable(connectionSource,WebDetailBean.class,true);
            getConfig(context);
            TableUtils.dropTable(connectionSource,ConfigurationBean.class,true);
            TableUtils.dropTable(connectionSource, OfflineChannelBean.class,true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ConfigurationBean bean;
    private void getConfig(Context context){
        if (DB_VERSION >= updateVer){
            bean = new ConfigDao(context).queryCofig();
        }
    }
    private void refreshConfig(Context context){
        if (DB_VERSION >= updateVer && bean != null){
            new ConfigDao(context).add(bean);
        }
    }

//    private List<ChannelItem> channelItems;
//    private void getChannelList(){
//        if (DB_VERSION != updateChannel){
//            return;
//        }
//        channelItems = new ChannelDao(context).queryForAll();
//        if (Utils.isStringEmpty(channelItems)){
//            TLog.e("abc","channelsss--------null");
//        }else {
//            TLog.e("abc","channelsss--------"+channelItems.size());
//        }
//    }
//    private void addChannelList(){
//        if (DB_VERSION != updateChannel){
//            return;
//        }
//
//        if (!Utils.isStringEmpty(channelItems)){
//            new ChannelDao(context).add(channelItems);
//        }
//    }

    /**
     * ͨ���������ָ����Dao
     */
    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();
        if (!daos.containsKey(className)) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }else {
            dao = daos.get(className);
        }
        return dao;
    }

    /**
     * �ͷ���Դ
     */
    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }
}
