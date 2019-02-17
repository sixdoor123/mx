package com.baiyi.jj.app.cache.Dao;

import android.content.Context;
import android.util.Log;

import com.baiyi.jj.app.application.accont.AccountManager;
import com.baiyi.jj.app.cache.bean.ConfigurationBean;
import com.baiyi.jj.app.cache.helper.DatabaseHelper;
import com.baiyi.jj.app.theme.ThemeUtil;
import com.baiyi.jj.app.utils.Utils;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/10/20 0020.
 */
public class ConfigDao {

    private Dao<ConfigurationBean, Integer> configDao;

    private DatabaseHelper dbHelper;

    public ConfigDao(Context context) {
        try {
            dbHelper = DatabaseHelper.getHelper(context);
            configDao = dbHelper.getDao(ConfigurationBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     */
    public void add(ConfigurationBean bean) {
        try {
            if (queryCofig() == null) {
                configDao.createIfNotExists(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ConfigurationBean queryCofig() {
        ConfigurationBean configurationBean = null;
        try {
            List<ConfigurationBean> bean = configDao.queryForAll();
            if (!Utils.isStringEmpty(bean)) {
                configurationBean = bean.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return configurationBean;
    }

    public void updateTheme(){
        try {
            ConfigurationBean bean = queryCofig();
            if (bean == null)
                return;
            bean.setThemeid(ThemeUtil.getAppTheme());
            bean.setAbstractshow(AccountManager.getInstance().getSummary_Is_Display());
            bean.setFontsize(AccountManager.getInstance().getCurrentSize());
            configDao.update(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateGustMid(String gustId) {
        try {
            ConfigurationBean bean = queryCofig();
            if (bean == null)
                return;
            bean.setGustid(gustId);
            configDao.update(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAccoutAndPwd(String accout, String pwd) {
        try {
            ConfigurationBean bean = queryCofig();
            if (bean == null)
                return;
            bean.setUserAccount(accout);
            bean.setUserKey(pwd);
            configDao.update(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateOtherToken(String token,String isThird) {
        try {
            ConfigurationBean bean = queryCofig();
            if (bean == null)
                return;
            bean.setOtherToken(token);
            bean.setIsthirdlogin(isThird);
            configDao.update(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateUserType(int type) {
        try {
            ConfigurationBean bean = queryCofig();
            if (bean == null)
                return;
            bean.setUserType(type);
            configDao.update(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserToken(String token,String refreshToken) {
        try {
            ConfigurationBean bean = queryCofig();
            if (bean == null)
                return;
            bean.setUserToken(token);
            bean.setRefreshToken(refreshToken);
            configDao.update(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateGustToken(String token,String refreshToken) {
        try {
            ConfigurationBean bean = queryCofig();
            if (bean == null)
                return;
            bean.setGustToken(token);
            bean.setRefreshGustToken(refreshToken);
            configDao.update(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update() {

    }

}
