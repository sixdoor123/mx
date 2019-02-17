package com.baiyi.cmall.request.manager;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baiyi.cmall.activities.user.login.utils.BaseAddressUtils;
import com.baiyi.cmall.entity.LoginInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;

import android.util.Log;

/**
 * 登录管理
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-31 下午1:34:45
 */
public class LoginManager {

	/**
	 * 登录
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	public static String getLoginPostData(String name, String password) {
		StringBuilder builder = new StringBuilder();
		builder.append("loginName").append("=").append(name).append("&");
		builder.append("pwd").append("=").append(password);
		return builder.toString();
	}

	/**
	 * 登录成功后返回的数据
	 * 
	 * @param result
	 */
	public static LoginInfo getLoginResultInfo(Object result) {
		Log.d("TAG", "--getLoginResultInfo--" + result.toString());
		LoginInfo info = new LoginInfo();
		try {
			JSONArray array = (JSONArray) result;
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				RequestNetResultInfo resultInfo = new RequestNetResultInfo();
				resultInfo.setMsg(jsonObject.getString("msg"));
				resultInfo.setStatus(jsonObject.getInt("status"));
				info.setResultInfo(resultInfo);
				JSONObject object = jsonObject.getJSONObject("data");
				info.setLoginName(object.getString("loginName"));
				info.setCompanyID(object.getString("companyID"));
				info.setEmail(object.getString("email"));
				info.setMobile(object.getString("phone"));
				info.setUserID(object.getString("userID"));
				info.setUserName(object.getString("userName"));
				info.setId(object.getString("id"));
				info.setToken(object.getString("token"));
				info.setCompanyName(object.getString("companyName"));
				info.setIscompany(object.getBoolean("iscompany"));
				info.setBaseAddress(object.getString("baseAddress").trim());
				String baseAddress = object.getString("baseAddress");
				info.setBaseAddress(baseAddress);
				info.setHeadPortrait(object.getString("headPortrait"));
				BaseAddressUtils.saveBaseUrl(baseAddress);
			}

			return info;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 手机动态登录-
	 * @param phoneName
	 * @param phonepwd
	 * @return
	 */
	public static String getMobileLoginPostData(String phoneName,
			String phonepwd) {
		StringBuilder builder = new StringBuilder();
		builder.append("phoneName").append("=").append(phoneName).append("&");
		builder.append("phonepwd").append("=").append(phonepwd);
		return builder.toString();
	}

	
	/**
	 * 登录成功后返回的数据
	 * 
	 * @param result
	 */
	public static RequestNetResultInfo getResultInfo(Object result) {
		Log.d("TAG", "--getLoginResultInfo--" + result.toString());
		RequestNetResultInfo resultInfo = new RequestNetResultInfo();
		try {
			JSONArray array = (JSONArray) result;
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				
				resultInfo.setMsg(jsonObject.getString("msg"));
				resultInfo.setStatus(jsonObject.getInt("status"));
			}

			return resultInfo;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	/**
	 * 供应商登录
	 * 
	 * @param result
	 * @return
	 */
	public static LoginInfo getCompanyLoginResultInfo(Object result) {
		LoginInfo info = new LoginInfo();
		try {
			JSONArray array = (JSONArray) result;
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				RequestNetResultInfo resultInfo = new RequestNetResultInfo();
				resultInfo.setMsg(jsonObject.getString("msg"));
				resultInfo.setStatus(jsonObject.getInt("status"));
				info.setResultInfo(resultInfo);

				JSONObject object = jsonObject.getJSONObject("data");
				info.setToken(object.getString("token"));
			}

			return info;
		} catch (Exception e) {
		}
		return null;
	}

}
