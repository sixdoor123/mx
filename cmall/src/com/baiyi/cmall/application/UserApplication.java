package com.baiyi.cmall.application;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.LoginInfo;
import com.baiyi.cmall.utils.XmlName;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.core.cache.Cache;
import com.baiyi.core.cache.MD5KeyMaker;
import com.baiyi.core.cache.NetFileCache;
import com.baiyi.core.file.NormalFileIO;
import com.baiyi.core.file.SimpleNormalFileIO;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.request.manager.LoginManager;

public class UserApplication extends ExceptionApplication {

	public static LoginInfo userInfo = null;

	private static Cache userCache = null;

	private static Cache baseDataCache = null;

	public static final String UserFileName = "user";

	public static final String BaseDataFileName = "base_data";

	private static Context context;

	public static String baseAddress = "";
	
	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
	}

	public static LoginInfo getUserInfo() {

		Cache cache = CmallApplication.getTestJsonCache(context);
		if (cache == null) {
			return getInfo();
		}
		if (!cache.isExist(CmallApplication.UserFileName)) {
			return getInfo();
		}
		byte[] data = (byte[]) cache.get(CmallApplication.UserFileName);
		try {
			LoginInfo info = LoginManager
					.getLoginResultInfo(new JSONArray(new String(data == null ? new byte[0] : data)));
			if (null == info) {
				return getInfo();
			}
			if (info.getUserName() == null) {
				setUserInfo(getInfo());
			} else {
				setUserInfo(info);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return userInfo;
	}

	private static LoginInfo getInfo() {

		LoginInfo info = new LoginInfo();
		XmlUtils utils = XmlUtils.getInstence(context);
		info.setUserID(utils.getXmlStringValue(XmlName.USER_ID));
		info.setCompanyID(utils.getXmlStringValue(XmlName.COMPANY_ID));
		info.setLoginName(utils.getXmlStringValue(XmlName.NAME_user_account));
		info.setToken(utils.getXmlStringValue(XmlName.TOKEN));
		info.setUserName(utils.getXmlStringValue(XmlName.USER_NAME));
		info.setPwd(utils.getXmlStringValue(XmlName.NAME_user_mm));
		info.setCompanyName(utils.getXmlStringValue(XmlName.Company_Name));
		info.setIscompany(utils.getXmlBooleanValue(XmlName.Is_Company, false));
		info.setHeadPortrait(utils.getXmlStringValue(XmlName.Head_Portrait));
		info.setBaseAddress(utils.getXmlStringValue(XmlName.Base_Address));
		info.setMobile(utils.getXmlStringValue(XmlName.Mobile));
		
		return info;
	}

	public static void setUserInfo(LoginInfo user) {
		userInfo = user;
	}

	public static Cache getTestJsonCache(Context context) {
		if (userCache == null) {
			String path = context.getCacheDir() + Config.User_File_Path;
			NormalFileIO fileIo = new SimpleNormalFileIO();
			try {
				userCache = new NetFileCache(path, Config.MAX_DOWN_COVER_NO,
						fileIo, true);
				((NetFileCache) userCache).setKeyMaker(new MD5KeyMaker());
			} catch (Exception e) {
				userCache = null;
			}
		}
		return userCache;
	}

	/**
	 * »ù´¡Êý¾Ý
	 */
	private static GoodsSourceInfo baseDataInfo;

	/**
	 * @return the info
	 */
	public static GoodsSourceInfo getBaseDataInfo() {
		return baseDataInfo;
	}

	/**
	 * @param info
	 *            the info to set
	 */
	public static void setBaseDataInfo(GoodsSourceInfo sourceInfo) {
		baseDataInfo = sourceInfo;
	}

	public static Cache getBaseDataCache(Context context) {
		if (baseDataCache == null) {
			String path = context.getCacheDir() + Config.Base_Data_File_Path;
			NormalFileIO fileIo = new SimpleNormalFileIO();
			try {
				baseDataCache = new NetFileCache(path,
						Config.MAX_DOWN_COVER_NO, fileIo, true);
				((NetFileCache) baseDataCache).setKeyMaker(new MD5KeyMaker());
			} catch (Exception e) {
				baseDataCache = null;
			}
		}
		return baseDataCache;
	}

}
