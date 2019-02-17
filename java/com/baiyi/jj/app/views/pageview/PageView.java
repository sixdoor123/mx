package com.baiyi.jj.app.views.pageview;

import android.content.Intent;
import android.os.Bundle;

/**
 * @author tangkun
 *
 */
public interface PageView {
	public void onCreate(Bundle savedInstanceState);
	public void onSaveInstanceState(Bundle outState);
	public boolean isCreated();
	public void visible();
	public void disVisible();
	public void onResume();
	public void onPause();
	public void onDestroy();
	public boolean isVisible();
	public void onRefresh(boolean isFontChange);
	public void onActivityResult(int requestCode, int resultCode, Intent data);
	public void onRefreshToNetWorkChange(boolean isGprs);
}
