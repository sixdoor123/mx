package com.baiyi.jj.app.cache.Dao;

import android.content.Context;

import com.baiyi.jj.app.cache.bean.ReadBean;
import com.baiyi.jj.app.cache.helper.DatabaseHelper;
import com.baiyi.jj.app.entity.UserInfoEntity;
import com.baiyi.jj.app.utils.Utils;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/12/19 0019.
 */
public class UserDao {
    private Dao<UserInfoEntity,Integer> userDao;

    private DatabaseHelper dbHelper;

    public UserDao(Context context) {
        try {
            dbHelper = DatabaseHelper.getHelper(context);
            userDao = dbHelper.getDao(UserInfoEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserInfoEntity getUserInfo(int UserType){
        UserInfoEntity userInfoEntity = null;
        try {
            List<UserInfoEntity> list = userDao.queryForEq("userType",UserType);
            if (Utils.isStringEmpty(list)){
                return null;
            }
            userInfoEntity = list.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userInfoEntity;
    }

    public void UpdateUserInfo(UserInfoEntity entity,int Type){
        UserInfoEntity userInfoEntity = null;
        try {
            List<UserInfoEntity> list = userDao.queryForEq("userType",Type);
            if (Utils.isStringEmpty(list)){
                userDao.createIfNotExists(entity);
                return;
            }
            for (int i = 0; i<list.size();i++){
                userDao.delete(list.get(i));
            }
            userDao.createIfNotExists(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public UserInfoEntity getUserInfoByMid(String mid){
        UserInfoEntity userInfoEntity = null;
        try {
            List<UserInfoEntity> list = userDao.queryForEq("MID",mid);
            if (Utils.isStringEmpty(list)){
                return null;
            }
            userInfoEntity = list.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userInfoEntity;
    }
    public void deleteUserInfoByMid(String mid){
        try {
            List<UserInfoEntity> list = userDao.queryForEq("MID",mid);
            if (Utils.isStringEmpty(list)){
                return;
            }
            userDao.delete(list.get(0));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUserInfo(UserInfoEntity entity){
        try {
            userDao.createIfNotExists(entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
