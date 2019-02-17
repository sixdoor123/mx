package com.baiyi.cmall.activities.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import com.baiyi.cmall.Config;
import com.baiyi.cmall.activities.base.TipBaseActivity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.entity.LoginInfo;
import com.baiyi.cmall.entity.UserInfoEntity;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.pulldownview.PullToRefreshListView;
import com.baiyi.core.database.manager.SimpleSQLDataBaseManager;
import com.baiyi.core.file.Preference;
import com.baiyi.cmall.R;

/**
 * 所有activity 的基类,需要重载initWin fun来载入界面
 * 
 * @author tangkun
 */
public abstract class BaseActivity extends TipBaseActivity {

	private static final int PROGRESS_DIALOG_ID = 1;
	private String progressDialogMessage = null;
	private String progressDialogTitle = null;
	private boolean isDialogShow = false;
	public static float density;
	public Bundle savedInstanceState;

	public String token;
	public String iscompany;

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		this.savedInstanceState = outState;
	}

	/**
	 * 所有act的最上层layout
	 */
	protected RelativeLayout root;
	/**
	 * 将root分为3个部分 title content menu
	 */
	protected LinearLayout win_title;
	protected LinearLayout win_content;
	protected LinearLayout win_menu;

	public static int win_width;
	public static int win_height;

	private SharedPreferences pref;
	@SuppressWarnings("unused")
	private ScrollViewRefreshLister scrollViewRefreshLister = null;

	/**
	 * 考虑到content部分适配不同分辨率的问题，content要加scrollview 如果content包含有listview
	 * 需要initWin时设置hasScrollView 为false
	 */
	public ScrollView mScrollView;

	private void initORM() {
		SimpleSQLDataBaseManager manager = SimpleSQLDataBaseManager.getInstance();
		String[] beans = this.getResources().getStringArray(R.array.single_db_beans);
		int[] versions = this.getResources().getIntArray(R.array.single_db_version);
		manager.init(this, Utils.getDataBasePath(), beans, versions);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LoginInfo info = CmallApplication.getUserInfo();
		if (null != info) {
			iscompany = info.isIscompany() + "";
			token = info.getToken();
		}
		initORM();
		ActivityStackControlUtil.add(this);

		// requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		DisplayMetrics dm = this.getApplicationContext().getResources().getDisplayMetrics();
		density = dm.density;
		initWin(true);
	}

	protected boolean isFirstDayTimes(String key) {
		Preference pref = Preference.getInstance();
		int times = pref.getInt(key, -1);
		Calendar ca = Calendar.getInstance();
		int curDay = ca.get(Calendar.DAY_OF_MONTH);
		if (times == -1) {
			pref.Set(key, String.valueOf(curDay));
			pref.saveConfig();
			return true;
		}
		if (times == curDay) {
			return false;
		}
		pref.Set(key, String.valueOf(curDay));
		pref.saveConfig();
		return true;
	}

	public boolean isDisplayChannelManager(String key) {
		Preference pref = Preference.getInstance();
		if (pref.getBoolean(key, true)) {
			pref.Set(key, String.valueOf(false));
			pref.saveConfig();
			return true;
		}
		return false;
	}

	public void setListView(PullToRefreshListView listView, int dataSize) {
		if (dataSize < Config.LIST_ITEM_COUNT) {
			listView.SetFooterCanUse(false);
		} else {
			listView.SetFooterCanUse(true);
		}
	}

	/**
	 * 加载消息数量
	 * 
	 * @param newsCount
	 * @param count
	 */
	public void setNews(TextView newsCount, int count) {
		UserInfoEntity user = CmallApplication.getUserInfoEntity();
		if (user != null) {
			if (count > 0) {
				newsCount.setVisibility(View.VISIBLE);
				newsCount.setText("" + count);
			} else {
				newsCount.setVisibility(View.GONE);
			}
		}
	}

	// @Override
	// protected void onSaveInstanceState(Bundle outState) {
	// super.onSaveInstanceState(outState);
	// outState.putSerializable("user", BYApplication.getUserInfoEntity());
	// }

	protected String getXmlStringValue(String name) {
		if (name == null)
			return null;
		return pref.getString(name, null);
	}

	protected void savaXmlValue(String name, String value) {
		Editor e = pref.edit();
		e.putString(name, value);
		e.commit();
	}

	@SuppressWarnings("deprecation")
	public void showProgressDialog(String title, String message) {
		this.progressDialogMessage = message;
		this.progressDialogTitle = title;
		showDialog(PROGRESS_DIALOG_ID);
		isDialogShow = true;
	}

	@SuppressWarnings("deprecation")
	public void dismissProgressDialog() {
		if (isDialogShow) {
			try {
				dismissDialog(PROGRESS_DIALOG_ID);
				removeDialog(PROGRESS_DIALOG_ID);
			} catch (Exception e) {
			}
			isDialogShow = false;
		}
	}

	@Override
	public Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case PROGRESS_DIALOG_ID:
			ProgressDialog progressDialog = new ProgressDialog(this);
			progressDialog.setCancelable(false);
			progressDialog.setMessage(progressDialogMessage);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setTitle(progressDialogTitle);
			dialog = progressDialog;
			break;
		}
		return dialog;
	}

	protected void remove(String name) {

	}

	// @Override
	// public void onConfigurationChanged(Configuration newConfig) {
	// super.onConfigurationChanged(newConfig);
	// }

	@SuppressWarnings("deprecation")
	protected void initWin(boolean hasScrollView) {
		root = new RelativeLayout(this);
		root.setBackgroundColor(getResources().getColor(R.color.bg_white));
		// root.setBackgroundResource(R.drawable.main_bg);f0efed
		// root.setBackgroundResource(getResources().getColor(R.color.day_bg_color));
		setContentView(root);

		mScrollView = new ScrollView(this);
		mScrollView.setId(4);

		win_title = new LinearLayout(this);
		win_title.setOrientation(LinearLayout.VERTICAL);
		win_title.setId(1);
		win_menu = new LinearLayout(this);
		win_menu.setId(2);
		win_content = new LinearLayout(this);
		win_content.setOrientation(LinearLayout.VERTICAL);
		win_content.setId(3);

		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		root.addView(win_title, lp);

		lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		root.addView(win_menu, lp);

		lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		lp.addRule(RelativeLayout.BELOW, win_title.getId());
		lp.addRule(RelativeLayout.ABOVE, win_menu.getId());

		if (hasScrollView) {
			mScrollView.addView(win_content, lp);
			mScrollView.setVerticalFadingEdgeEnabled(false);
			// mScrollView.setOnRefreshListener(new
			// OnRefreshListener<ScrollView>() {
			//
			// @Override
			// public void onRefresh(PullToRefreshBase<ScrollView> refreshView)
			// {
			// if(scrollViewRefreshLister != null)
			// {
			// scrollViewRefreshLister.callBack();
			// return;
			// }
			// onRefreshComplete();
			// }
			// });
		}
		root.addView(hasScrollView ? mScrollView : win_content, lp);

		Display display = getWindowManager().getDefaultDisplay();
		win_width = display.getWidth();
		win_height = display.getHeight();

		pref = getSharedPreferences("REFRESH_TIME", MODE_PRIVATE);
	}

	// protected void setRootBackground(int resid){
	// root.setBackgroundResource(resid);
	// }

	// protected void setRootBackgroundColor(int color){
	// root.setBackgroundColor(color);
	// }

	protected void releaseWin() {
		// BYApplication.getDataStratey().clear();
		// BYApplication.getImageStrategy().clear();
		// win_title.removeAllViews();
		// win_content.removeAllViews();
	}

	public void onRefresh() {

	}

	public float getDensity() {
		return density;
	}

	public static int getDensity_int(int mun) {
		return (int) (mun * density);
	}

	public void setDensity(float density) {
		BaseActivity.density = density;
	}

	@SuppressWarnings("deprecation")
	public int getScreenHeight() {
		return getWindowManager().getDefaultDisplay().getHeight();
	}

	@SuppressWarnings("deprecation")
	public int getScreenWidth() {
		return getWindowManager().getDefaultDisplay().getWidth();
	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onResume() {
		onRefresh();
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	@Override
	protected void onDestroy() {
		releaseWin();
		super.onDestroy();
		// ActivityStackControlUtil.remove(this);
		// Editor editor = pref.edit();
		// editor.remove("userID");
		// editor.remove("companyID");
		// editor.commit();
	}

	private int dataListSize;

	public void setLisViewSize(int size) {
		this.dataListSize = size;
	}

	public boolean isListViewBottom() {
		if (dataListSize < Config.LIST_ITEM_COUNT) {
			displayToast("到底了");
			return true;
		}
		return false;
	}

	public boolean isListViewBottomSIX() {
		if (dataListSize < Config.LIST_SIX_COUNT) {
			displayToast("到底了");
			return true;
		}
		return false;
	}

	public void setScrollViewRefreshLister(ScrollViewRefreshLister scrollViewRefreshLister) {
		// mScrollView.setRefreshing(true);
		this.scrollViewRefreshLister = scrollViewRefreshLister;
	}

	public void OnClickListenEvent(View v) {

	}

	public OnClickListener viewOnClickListen = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			OnClickListenEvent(v);
		}
	};

	public interface ScrollViewRefreshLister {
		public void callBack();
	}

	public static final int EGetPic = 0x0999;
	public static final String EGetPicStr = "EGetPicStr";
	public static final int EGetModifyPinCheData = 0x1000;
	public static final String EGetModifyPinCheDataStr = "EGetModifyPinCheDataStr";
	public static final int EOtherPrifileInfo = 0x1001;
	public static final String EOtherPrifileInfoStr = "EOtherPrifileInfoStr";
	public static final int EAddNewPinCheData = 0x1003;
	public static final String EAddNewPinCheDataStr = "EAddNewPinCheDataStr";
	public static final int ECurrentChatDataTransType = 0x1004;
	public static final String ECurrentChatDataTransTypeStr = "ECurrentChatDataTransTypeStr";
	public static final int EUserPhoneVerify = 0x1005;
	public static final String EUserPhoneVerifyStr = "EUserPhoneVerifyStr";

	/**
	 * 控制所有的activity
	 */
	public static class ActivityStackControlUtil {
		private static List<Activity> activityList = new ArrayList<Activity>();

		public static int getCounter() {
			return activityList.size();
		}

		public static void remove(Activity activity) {
			activityList.remove(activity);
		}

		public static void add(Activity activity) {
			activityList.add(activity);
		}

		public static void add(int index, Activity activity) {
			activityList.add(activity);
		}

		public static void finishProgram() {
			for (Activity activity : activityList) {
				if (activity != null) {
					activity.finish();
				}
			}
			System.gc();
			// android.os.Process.killProcess(android.os.Process.myPid());
		}

		public static void finishAllActivityWithout(Activity withoutActivity) {

			for (int index = activityList.size() - 1; index >= 0; index--) {
				if (withoutActivity != activityList.get(index)) {
					activityList.get(index).finish();
					activityList.remove(index);
				}
			}
		}
	}
}