/**
 * 
 */
package com.baiyi.jj.app.views.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.application.CmsApplication;

/**
 * @author tangkun
 * 
 */
public abstract class BasePopWindow {
	protected PopupWindow popupWindow = null;

	// private PopEvent popEventLister = null;

	private ViewGroup parent = null;
	public static final int yoff = -45;
	public Context context;

	public BasePopWindow(View contentView,Context context, int height, String popName) {
		this.context = context;
		popupWindow = new PopupWindow(contentView);
		popupWindow.setWidth(LayoutParams.FILL_PARENT);
		popupWindow.setHeight(height);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setTouchable(true);
		popupWindow.setFocusable(true);
		ColorDrawable dw = new ColorDrawable(0000000000);
		popupWindow.setBackgroundDrawable(dw);
		popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
		// popupWindow.setAnimationStyle(R.style.popwindow_anim);
		popupWindow.update();
		popupWindow.setOnDismissListener(new OnDismissListener() {

			public void onDismiss() {
				// if(popEventLister != null)
				// {
				// popEventLister.dismissPop(parent);
				// }
				backGroundAlpha(1f);
				dismissPop(parent);
			}
		});

		TextView txtName = (TextView) contentView
				.findViewById(R.id.title_name_channel_white);
//		txtName.setTypeface(CmsApplication.getTitleFace(context));
		ImageView imgBack = (ImageView) contentView
				.findViewById(R.id.btn_back_channel_white);
		if (txtName != null && imgBack != null) {
			txtName.setText(popName);
			imgBack.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					dismiss();
				}
			});
		}

	}

	public abstract void showPop(ViewGroup parent);

	public abstract void dismissPop(ViewGroup parent);

	public void setPopAnim(int anim) {
		popupWindow.setAnimationStyle(anim);
	}

	public void showPopupWindow(ViewGroup view, ViewGroup parent, int yoff) {
		this.parent = view;
		if (!popupWindow.isShowing()) {
			backGroundAlpha(0.4f);
			popupWindow.showAsDropDown(parent, 0, yoff);
			showPop(view);
		} else {
			popupWindow.dismiss();
		}
	}

	public void showPopupWindowLeftTop(ViewGroup view, ViewGroup parent) {
		this.parent = view;
		if (!popupWindow.isShowing()) {
			backGroundAlpha(0.4f);
			popupWindow
					.showAtLocation(parent, Gravity.LEFT | Gravity.TOP, 0, 0);
			showPop(view);
		} else {
			popupWindow.dismiss();
		}
	}
	
	/**
	 * ���ñ���Ϊ��͸��
	 * 
	 * @param alp
	 */
	protected void backGroundAlpha(float alp) {
		WindowManager.LayoutParams lParams = ((BaseActivity) context)
				.getWindow().getAttributes();
		lParams.alpha = alp;
		((BaseActivity) context).getWindow().setAttributes(lParams);

	}

	public int getInputMethodMode() {
		return popupWindow.getInputMethodMode();
	}

	public void showPopLocation(ViewGroup parent, int gravity, int x, int y) {
		if (!popupWindow.isShowing()) {
			backGroundAlpha(0.4f);
			popupWindow.showAtLocation(parent, gravity, x, y);
			showPop(parent);
		} else {
			popupWindow.dismiss();
		}
	}

	public void dismiss() {
		if (popupWindow != null) {
			popupWindow.dismiss();
		}
	}

	public void setAnimationStyle(int style) {
		popupWindow.setAnimationStyle(style);
	}

}
