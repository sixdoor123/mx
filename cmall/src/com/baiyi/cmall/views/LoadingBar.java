package com.baiyi.cmall.views;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.Window;
import android.widget.LinearLayout.LayoutParams;

/**
 * ������
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-3-29 ����9:49:48
 */
public class LoadingBar {
	private Context context;

	public LoadingBar(Context context) {
		this.context = context;
		initWindow();
	}


	private MyLoadingBar loadingBar;
	private Window window;

	/**
	 * ������
	 */
	private void initWindow() {

		window = ((BaseActivity) context).getWindow();
		ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.bg_hui3));
		window.setBackgroundDrawable(dw);
		window.setLayout(android.view.WindowManager.LayoutParams.MATCH_PARENT,
				android.view.WindowManager.LayoutParams.MATCH_PARENT);

		loadingBar = new MyLoadingBar(context);
		loadingBar.setGravity(Gravity.CENTER);
		loadingBar.setProgressInfo("������,���Ժ�...");
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		window.addContentView(loadingBar, params);

	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				loadingBar.stop();
				if (null != window) {
					window.closeAllPanels();
					window = null;
				}
				break;
			case 1:
				loadingBar.start();
				break;
			}
		};
	};

	/**
	 * ����
	 */
	public void start() {
		handler.sendEmptyMessage(1);
	}

	/**
	 * ������Ϣ
	 * 
	 * @param info
	 */
	public void setProgressInfo(String info) {
		loadingBar.setProgressInfo(info);
	}

	/**
	 * ֹͣ
	 */
	public void stop() {
		handler.sendEmptyMessage(0);
	}

	public void setPercentTextColor(int color) {
		loadingBar.setPercentTextColor(color);
	}

	public void setPercent(int value) {
		loadingBar.setPercent(value);
	}

	public void setNoTextProgressInfo() {
		loadingBar.setNoTextProgressInfo();
	}

	public void setPercentInvisible() {
		loadingBar.setPercentInvisible();
	}

	public void setProgressInfoInvisible() {
		loadingBar.setProgressInfoInvisible();
	}

	public void setProgressLoadError(String errorMessage, int resType) {
		loadingBar.setProgressLoadError(errorMessage, resType);
	}

}
