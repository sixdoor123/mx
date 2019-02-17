package com.baiyi.cmall.popupwindow;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.R;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow.OnDismissListener;

/**
 * 支付界面的pop
 * 
 * @author lizl
 * 
 */
public class PayPopupWindow extends PopupWindow implements OnDismissListener {

	// 上下文
	private Context context;

	public PayPopupWindow(Context context) {
		super(context);

		this.context = context;
	}

	/**
	 * 显示pop
	 * 
	 * @param view
	 * @param gravity
	 */
	public void showPop(View showLocation, View view, int gravity,int heightState) {

		setWidth(LayoutParams.MATCH_PARENT);
		if (heightState==0) {
			setHeight(((BaseActivity) context).getScreenHeight() / 2);
		}else {
			setHeight(LayoutParams.WRAP_CONTENT);
		}
		if (!isShowing()) {
			setFocusable(true);
			setOutsideTouchable(false);
			setContentView(view);
			backgroundAlpha(0.5f);
			setAnimationStyle(R.style.pay_popwin_anim_style);
			showAtLocation(showLocation, gravity, 0, 0);
			setOnDismissListener(this);
		} else {
			dismiss();
		}
	}

	@Override
	public void onDismiss() {
		backgroundAlpha(1);
	}

	/**
	 * 设置添加屏幕的背景透明度
	 * 
	 * @param bgAlpha
	 */
	public void backgroundAlpha(float bgAlpha) {
		WindowManager.LayoutParams lp = ((BaseActivity) context).getWindow()
				.getAttributes();
		lp.alpha = bgAlpha; // 0.0-1.0
		((BaseActivity) context).getWindow().setAttributes(lp);
	}

}
