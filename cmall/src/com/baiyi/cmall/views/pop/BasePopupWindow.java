/**
 * 
 */
package com.baiyi.cmall.views.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import com.baiyi.cmall.R;
import com.baiyi.cmall.views.LoadingBar;

/**
 * 
 * PopupWindow
 * 
 * @author tangkun
 * 
 */
public abstract class BasePopupWindow {
	protected PopupWindow popupWindow = null;

	private View animalView = null;
	public static final int yoff = -45;
	public static Context context;
	// 背景变暗的动画
	private Animation animIn, animOut;

	private PopShowOrDismissLister popShowOrDismissLister = null;

	public View contentView;
	private LoadingBar progressBar;

	/**
	 * 
	 * @param contentView
	 * @param context
	 * @param height
	 *            设置高度
	 */
	public BasePopupWindow(View contentView, Context context, int width, int height) {
		this.context = context;
		this.contentView = contentView;

		popupWindow = new PopupWindow(contentView);
		popupWindow.setHeight(height);
		popupWindow.setWidth(width);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setTouchable(true);
		popupWindow.setFocusable(true);
		ColorDrawable dw = new ColorDrawable(context.getResources().getColor(
				R.color.transparent));
		popupWindow.setBackgroundDrawable(dw);
		popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
		popupWindow.update();
		popupWindow.setOnDismissListener(new OnDismissListener() {

			public void onDismiss() {
				
				backGroundAlpha(1f);
				if(animalView != null)
				{
					animalView.setVisibility(View.GONE);
					animalView.startAnimation(animOut);
				}
				if (popShowOrDismissLister != null) {
					popShowOrDismissLister.setPopDismiss();
				}
			}
		});
		animIn = AnimationUtils.loadAnimation(context, R.anim.fade_in_anim);
		animOut = AnimationUtils.loadAnimation(context, R.anim.fade_out_anim);

	}

	/**
	 * 加载进度条
	 */
	protected void loaderProgress() {
		if(progressBar == null)
		{
			progressBar = new LoadingBar(context);
		}
		progressBar.start();
	}

	/**
	 * 结束进度条
	 */
	protected void stopProgress() {

		if (null != progressBar) {
			progressBar.stop();
		}
	}

	public void setPopShowOrDismissLister(
			PopShowOrDismissLister popShowOrDismissLister) {
		this.popShowOrDismissLister = popShowOrDismissLister;
	}

	public void showPopupWindow(ViewGroup view, ViewGroup parent, int yoff,
			View alphaView) {
		this.animalView = alphaView;
		if (!popupWindow.isShowing()) {
			alphaView.setVisibility(View.VISIBLE);
			alphaView.startAnimation(animIn);
			backGroundAlpha(0.4f);
			popupWindow.showAsDropDown(parent, 0, yoff);
			if (popShowOrDismissLister != null) {
				popShowOrDismissLister.setPopShow();
			}
		} else {
			popupWindow.dismiss();
		}
	}
	
	public void showPopAtLocation(View view, int gravity)
	{
		if (!popupWindow.isShowing()) {
			backGroundAlpha(0.4f);
			popupWindow.showAtLocation(view, gravity, 0, 0);
			if (popShowOrDismissLister != null) {
				popShowOrDismissLister.setPopShow();
			}
		} else {
			popupWindow.dismiss();
		}
	}

	/**
	 * 设置背景为半透明
	 * 
	 * @param alp
	 */
	public void backGroundAlpha(float alp) {
		WindowManager.LayoutParams lParams = ((Activity) context)
				.getWindow().getAttributes();
		lParams.alpha = alp;
		((Activity) context).getWindow().setAttributes(lParams);

	}

	// dismiss
	public void dismiss() {
		if (popupWindow != null) {
			popupWindow.dismiss();
		}
	}

	/**
	 * 设置动画
	 * 
	 * @param style
	 */
	public void setAnimationStyle(int style) {
		popupWindow.setAnimationStyle(style);
	}

}
