package com.baiyi.cmall.activities.main.total;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * 暂无数据
 * 
 * @author sunxy
 */
public class TemporarilyNoData {

	private Context context = null;
	private Window window = null;
	private static TemporarilyNoData noData = null;
	private TextView textView = null;

	public TemporarilyNoData(Context context) {
		this.context = context;
		showWindow();
	}

	public static TemporarilyNoData getInstance(Context context) {
		if (noData == null) {
			synchronized (TemporarilyNoData.class) {
				if (null == noData) {
					noData = new TemporarilyNoData(context);
				}
			}
		}

		return noData;
	}

	public void showNoData(int total, int pageIndex) {
		if (total <= 0 && pageIndex == 1) {
			if (window == null) {
				showWindow();
			}
			show();
		} else {
			dismiss();
		}
	}

	public void showWindow() {

		if (null == window) {
			window = ((BaseActivity) context).getWindow();
			ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.bg_hui3));
			window.setBackgroundDrawable(dw);
			window.setLayout(android.view.WindowManager.LayoutParams.MATCH_PARENT,
					android.view.WindowManager.LayoutParams.MATCH_PARENT);
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

			textView = getTextView();

			window.addContentView(textView, params);
			dismiss();
		}
	}

	private TextView getTextView() {
		TextView textView = new TextView(context);
		textView.setText("暂无数据");
		textView.setTextColor(context.getResources().getColor(R.color.bg_hui1));
		textView.setTextSize(15);
		textView.setGravity(Gravity.CENTER);
		textView.setPadding(0, 200, 0, 0);

		return textView;
	}

	public void dismiss() {
		handler.sendEmptyMessage(hide);
	}

	public void show() {
		handler.sendEmptyMessage(show);		
	}

	// 显示
	private static final int show = 1;
	// 不显示
	private static final int hide = 2;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case show:
				textView.setVisibility(View.VISIBLE);
				break;
			case hide:
				textView.setVisibility(View.GONE);
				break;
			default:
				break;
			}
		};
	};

}
