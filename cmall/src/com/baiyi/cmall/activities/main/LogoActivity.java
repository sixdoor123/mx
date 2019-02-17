package com.baiyi.cmall.activities.main;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.application.UserApplication;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.LoginInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.request.manager.LoginManager;
import com.baiyi.cmall.request.manager.PublicActivityManager;
import com.baiyi.cmall.request.net.AppNetUrl;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.utils.XmlName;
import com.baiyi.cmall.utils.XmlUtils;
import com.baiyi.core.cache.Cache;
import com.baiyi.core.loader.JsonLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.LogoActivity;
import com.baiyi.cmall.main.cache.CatchUtils;
import com.baiyi.cmall.activities.user.login.ExitLogin;

/**
 * 引导界面
 * 
 * @author Administrator
 * 
 *         date 2016-1-15
 */
public class LogoActivity extends BaseActivity {
	// 延时两秒
	private static final int DELAY_TIME = 2000;
	private ImageView img;

	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		img = new ImageView(this);
		img.setScaleType(ScaleType.FIT_XY);
		// 背景设置为透明色
		img.setBackgroundResource(getResources().getColor(R.color.transparent));
		setContentView(img);

		goMainTabActivity();
		loadCacheLogin();
		loadCacheBaseData();
	}

	// 记录上次请求的时间
	private String lastTime;

	/**
	 * 基础数据
	 */
	private void loadCacheBaseData() {
		// 两个小时请求一次
		lastTime = XmlUtils.getInstence(this).getXmlStringValue("lastTime");
		// 计算上次请求距离现在的时间差
		boolean isDay = Utils.getTimeFromNow(lastTime);
		if (isDay) {
			initSelectData();
			return;
		}

		Cache cache = CmallApplication.getBaseDataCache(this);
		if (cache == null) {
			initSelectData();
			return;
		}
		if (!cache.isExist(CmallApplication.BaseDataFileName)) {
			initSelectData();
			return;
		}
		byte[] data = (byte[]) cache.get(CmallApplication.BaseDataFileName);
		try {
			GoodsSourceInfo info = PublicActivityManager.getSelectedData(this,
					new JSONArray(new String(data)));
			CmallApplication.setBaseDataInfo(info);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加载选择数据
	 */
	private void initSelectData() {

		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getPublicUrl());
		loader.setPostData(new JSONObject().toString());
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
				Log.d("TAG", "---------onProgress-----------");
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				// displayToast("选择数据：" + arg2);
			}

			@Override
			public void onCompelete(Object arg0, Object result) {
				GoodsSourceInfo sourceInfo = PublicActivityManager
						.getSelectedData(LogoActivity.this, result);
				UserApplication.setBaseDataInfo(sourceInfo);
				CatchUtils.cacheBaseDate(LogoActivity.this,
						((JSONArray) result).toString().getBytes());

				long time = System.currentTimeMillis();
				lastTime = Utils.getCurrentTime1(time);
				XmlUtils.getInstence(LogoActivity.this).savaXmlValue(
						"lastTime", lastTime);
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	private void loadCacheLogin() {
		Cache cache = CmallApplication.getTestJsonCache(this);
		if (cache == null) {
			login();
			return;
		}
		if (!cache.isExist(CmallApplication.UserFileName)) {
			login();
			return;
		}
		byte[] data = (byte[]) cache.get(CmallApplication.UserFileName);
		try {
			LoginInfo info = LoginManager.getLoginResultInfo(new JSONArray(
					new String(data == null ? new byte[0] : data)));
			CmallApplication.setUserInfo(info);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 登录
	 */
	private void login() {
		final String account = XmlUtils.getInstence(this).getXmlStringValue(
				XmlName.NAME_user_account);
		final String pwd = XmlUtils.getInstence(this).getXmlStringValue(
				XmlName.NAME_user_mm);
		if (TextUtils.isEmpty(account) || TextUtils.isEmpty(pwd)) {
			return;
		}
		// loadLogin(name, password);
		JsonLoader loader = new JsonLoader(this);
		loader.setUrl(AppNetUrl.getLoginURL());
		loader.setPostData(LoginManager.getLoginPostData(account, pwd));
		loader.setMethod(BaseNetLoder.Method_Post);
		loader.setType(BaseNetLoder.POST_DATA_Urlencoded);

		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object arg0, long arg1, long arg2) {
			}

			@Override
			public void onError(Object arg0, int arg1, String arg2) {
				// displayToast(arg2.toString());
			}

			@Override
			public void onCompelete(Object arg0, Object result) {
				LoginInfo info = LoginManager.getLoginResultInfo(result);
				if (null != info) {
					RequestNetResultInfo resultInfo = info.getResultInfo();
					displayToast(resultInfo.getMsg());
					if (1 == resultInfo.getStatus()) {
						Intent intent = new Intent();
						intent.putExtra("name", info.getUserName());
						setResult(0, intent);

						info.setPwd(pwd);
						UserApplication.setUserInfo(info);
						UserApplication.baseAddress = info.getBaseAddress();
						CatchUtils.cacheLogin(LogoActivity.this,
								((JSONArray) result).toString().getBytes());
						finish();

						CatchUtils.saveXml(LogoActivity.this, info);

					} else {
						ExitLogin.getInstence(LogoActivity.this).cleer();
						return;
					}
				} else {
					ExitLogin.getInstence(LogoActivity.this).cleer();
					displayToast("登录失败");
				}
			}
		});
		CmallApplication.getDataStratey().startLoader(loader);
	}

	/**
	 * 延时进入主界面
	 */
	private void goMainTabActivity() {
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				goActivity(MainTabActivity.class);
				finish();
			}
		}, DELAY_TIME);
	}

}
