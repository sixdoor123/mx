package com.baiyi.cmall.activities.main;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.Toast;

import com.baiyi.cmall.activities.base.SystemStatusManager;
import com.baiyi.cmall.activities.main.BaseActivity.ActivityStackControlUtil;
import com.baiyi.cmall.activities.main.home_pager.MainActivity;
import com.baiyi.core.database.manager.SimpleSQLDataBaseManager;
import com.baiyi.cmall.R;

/**
 * 
 * @author weizd 底部菜单
 * 
 */
@SuppressWarnings("deprecation")
public class MainTabActivity extends TabActivity implements
		OnCheckedChangeListener {
	private TabHost mTabHost;
	private RadioGroup tab_rg;
	private RadioButton rButton;
	private long exitTime = 0;

	private String key;

	/**
	 * 设置状态栏背景状态
	 */
	@SuppressLint("InlinedApi")
	private void setTranslucentStatus() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window win = getWindow();
			WindowManager.LayoutParams winParams = win.getAttributes();
			final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
			winParams.flags |= bits;
			win.setAttributes(winParams);
		}
		SystemStatusManager tintManager = new SystemStatusManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(0);// 状态栏无背景
	}

	protected void onCreate(Bundle savedInstanceState) {
		// getWindow().setStatusBarColor(getResources().getColor(R.color.bg_green));
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTranslucentStatus();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tab);

		key = getIntent().getStringExtra("key");

		mTabHost = getTabHost();

		/**
		 * 添加选项卡、添加标签、设置按钮名称、设置内容
		 */
		mTabHost.addTab(mTabHost
				.newTabSpec("ONE")
				.setIndicator("ONE")
				.setContent(
						new Intent(MainTabActivity.this, MainActivity.class)));

		mTabHost.addTab(mTabHost
				.newTabSpec("TWO")
				.setIndicator("TWO")
				.setContent(
						new Intent(MainTabActivity.this,
								ShopingMallActivity.class)));
		mTabHost.addTab(mTabHost
				.newTabSpec("THREE")
				.setIndicator("THREE")
				.setContent(
						new Intent(MainTabActivity.this,
								GoodsMainActivity.class)));
		mTabHost.addTab(mTabHost
				.newTabSpec("FOUR")
				.setIndicator("FOUR")
				.setContent(
						new Intent(MainTabActivity.this, PurchaseActivity.class)));

		mTabHost.addTab(mTabHost
				.newTabSpec("FRIVE")
				.setIndicator("FRIVE")
				.setContent(
						new Intent(MainTabActivity.this, UserActivity.class)));

		tab_rg = (RadioGroup) findViewById(R.id.tab_rg);
		rButton = (RadioButton) findViewById(R.id.tab_main);
		/**
		 * 根据传过来的key，判断显示当前的Tab页
		 */
		if (!TextUtils.isEmpty(key)) {
			mTabHost.setCurrentTabByTag(key);
			@SuppressWarnings("unused")
			RadioButton button = null;
			if ("THREE".equals(key)) {
				rButton = (RadioButton) findViewById(R.id.tab_project);
			} else if ("FOUR".equals(key)) {
				rButton = (RadioButton) findViewById(R.id.tab_purchase);
			} else if ("FRIVE".equals(key)) {
				rButton = (RadioButton) findViewById(R.id.tab_user);
			}
			rButton.setChecked(true);
		}

		tab_rg.setOnCheckedChangeListener(this);

	}

	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		switch (arg1) {
		case R.id.tab_main:
			mTabHost.setCurrentTab(0);
			break;
		case R.id.tab_price:
			mTabHost.setCurrentTabByTag("TWO");
			break;
		case R.id.tab_project:// 发送广播，刷新供应界面
			mTabHost.setCurrentTabByTag("THREE");
			Intent intent = new Intent();
			intent.putExtra("key", "THREE");
			intent.setAction("com.baiyi.cmall.activities.main.MyCast");
			sendBroadcast(intent);
			break;
		case R.id.tab_purchase:// 发送广播，刷新采购界面
			mTabHost.setCurrentTabByTag("FOUR");
			Intent intent1 = new Intent();
			intent1.putExtra("key", "FOUR");
			intent1.setAction("com.baiyi.cmall.activities.main.MyCast");
			sendBroadcast(intent1);
			break;
		case R.id.tab_user:
			mTabHost.setCurrentTabByTag("FRIVE");
			break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 连续按两次退出系统
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (rButton.isChecked()) {
				if ((System.currentTimeMillis() - exitTime) > 2000) {
					Toast.makeText(getApplicationContext(), "再按一次退出程序",
							Toast.LENGTH_SHORT).show();

					exitTime = System.currentTimeMillis();

				} else {
					destory();
					// finish();
					// System.exit(0);
				}
			} else {
				rButton.setChecked(true);

			}
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if (rButton.isChecked()) {
				if ((System.currentTimeMillis() - exitTime) > 2000) {
					Toast.makeText(getApplicationContext(), "再按一次退出程序",
							Toast.LENGTH_SHORT).show();
					exitTime = System.currentTimeMillis();

				} else {
					destory();
				}
			} else {
				rButton.setChecked(true);
			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	private void destory() {
		ActivityStackControlUtil.finishProgram();
		SimpleSQLDataBaseManager.getInstance().closeAll();
		// DmallApplication.getDataStratey().clear();
		// DmallApplication.getImageStrategy().clear();
		System.exit(0);
	}


}
