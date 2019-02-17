/**
 * 
 */
package com.baiyi.cmall.activities.base;

import com.baiyi.cmall.R;
import com.baiyi.cmall.dialog.DialogBase;
import com.baiyi.cmall.dialog.MsgBoxNotice;
import com.baiyi.cmall.dialog.MsgBoxNoticeE;
import com.baiyi.cmall.dialog.MsgBoxNoticeE.MsgBoxEOnClickListener;
import com.baiyi.cmall.views.LoadingBar;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * dialog taost 提示
 * 
 * @author tangkun
 * 
 */
public abstract class TipBaseActivity extends BaseGoActivity {

	public static int win_width;
	public static int win_height;
	
	private LoadingBar progressBar;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Display display = getWindowManager().getDefaultDisplay();
		win_width = display.getWidth();
		win_height = display.getHeight();
	}

	/**
	 * 加载进度条
	 */
	public void loaderProgress() {
		if(progressBar == null)
		{
			progressBar = new LoadingBar(this);
		}
		progressBar.start();
	}

	/**
	 * 结束进度条
	 */
	public void stopProgress() {

		if (null != progressBar) {
			progressBar.stop();
		}
	}

	public void displayToast(String text) {
		if (text == null)
			return;
		displayByselfToast(this, text, Toast.LENGTH_SHORT).show();
	}

	@SuppressLint("InflateParams")
	public static Toast displayByselfToast(Context content, CharSequence text,
			int duration) {

		Toast result = new Toast(content);
		// 获取LayoutInflater对象
		LayoutInflater inflater = (LayoutInflater) content
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// 由layout文件创建一个View对象
		View layout = inflater.inflate(R.layout.myself_toast, null);
		// LinearLayout linearLayout = (LinearLayout) layout
		// .findViewById(R.id.toast_linear);
		TextView tv = (TextView) layout.findViewById(R.id.message_toast);
		tv.setText(text);

		int get_height = win_height / 4;
		result.setView(layout);
		result.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0,
				get_height);

		result.setDuration(duration);

		return result;
	}

	public void displayMsgBox(String title, String content) {
		final MsgBoxNotice boxNotice = new MsgBoxNotice(this, title, content,
				DialogBase.Win_Center);
		boxNotice.show();
	}

	public void displayMsgBoxE(String title, String content,
			MsgBoxEOnClickListener lister) {
		MsgBoxNoticeE mbn = new MsgBoxNoticeE(this, title, content,
				DialogBase.Win_Center);
		mbn.show();
		mbn.setMsgOnClickListener(lister);
	}
}
