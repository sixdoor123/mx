package com.baiyi.cmall.views.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.baiyi.cmall.views.pop.PopNewsView.PopItemClick;

public class PopView {
	
	public static final int State_Ticket = 1;
	public static final int State_PaySuccess = 2;

	private PopupWindow mPopMenu = null;
	private Context mContext;
	private PopStateListener popdismissListener;
	private PopItemOnclick popItemOnclick = null;
	private PopNewsView popNewsView = null;

	private String[] names = null;
	private int[] resIds = null;
	
	int pop_x = 0;
	int pagetag = 0;
	
	public PopView(Context context, String[] names, int[] resIds)
	{
		this.mContext = context;
		this.names = names;
		this.resIds = resIds;
		InitPopView();
	}
	
	public void setPopStateListener(PopStateListener listener)
	{
		this.popdismissListener = listener;
	}
	

	public PopItemOnclick getPopItemOnclick() {
		return popItemOnclick;
	}

	public void setPopItemOnclick(PopItemOnclick popItemOnclick) {
		this.popItemOnclick = popItemOnclick;
	}

	private void InitPopView() {
		popNewsView = new PopNewsView(mContext, names, resIds);
		popNewsView.setPopItemClickLister(new PopItemClick() {
			public void setPopItemClickLister(int id) {
			
				if(popItemOnclick != null)
				{
					popItemOnclick.setOnclickLister(id);
				}
				mPopMenu.dismiss();
			}
		});
		float fPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, LayoutParams.WRAP_CONTENT, mContext.getResources().getDisplayMetrics());
		mPopMenu = new PopupWindow(popNewsView, (int) fPx, LayoutParams.WRAP_CONTENT);
		DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
		int width = (int) (dm.widthPixels*0.5);
		mPopMenu.setWidth((int)(width));
		mPopMenu.setOutsideTouchable(true);
		mPopMenu.setTouchable(true);
		ColorDrawable dw = new ColorDrawable(0000000000);
		mPopMenu.setBackgroundDrawable(dw);
		mPopMenu.setFocusable(true);
		mPopMenu.update();
		mPopMenu.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				if(popdismissListener != null)
					popdismissListener.popDismisslistener();
				mPopMenu = null;
			}
		});
	}
	
	public boolean isShow() {
		if (mPopMenu == null) {
			return false;
		}
		return mPopMenu.isShowing();

	}

	public void showPop(ImageView tabItemView) {
		if (mPopMenu == null) {
			InitPopView();
		}
		if (mPopMenu.isShowing()) {
			mPopMenu.dismiss();
		}else {

			float density = mContext.getResources().getDisplayMetrics().density;
			int y = (int) (density*15);
			mPopMenu.showAsDropDown(tabItemView, 0,(int) -y);
		}
		
	}
	
	public interface PopItemOnclick
	{
		public void setOnclickLister(int popid);
	}
	
	public interface PopStateListener{
		public void popDismisslistener();
	}
}
