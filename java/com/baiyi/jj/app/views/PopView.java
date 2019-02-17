package com.baiyi.jj.app.views;

import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.Config;
import com.baiyi.jj.app.theme.ThemeUtil;
import com.baiyi.jj.app.views.PopNewsView.PopItemClick;
import com.baiyi.jj.app.views.PopNewsView.PopViewTheme;
import com.baiyi.jj.app.views.TitleView.PopThemeType;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;

public class PopView {
	
	public static final int State_Ticket = 1;
	public static final int State_PaySuccess = 2;

	private PopupWindow mPopMenu = null;
	private Context mContext;
	private PopStateListener popdismissListener;
	private PopItemOnclick popItemOnclick = null;
	private PopNewsView popNewsView = null;
	private PopViewTheme leftTheme = null;
	
	private PopThemeType type = null;

	private String[] names = null;
	private int[] resIds = null;
	private int[] nightResIds = null;
	
	public PopView(Context context, String[] names, int[] resIds, int[] nightResIds, PopViewTheme theme, PopThemeType type)
	{
		this.mContext = context;
		this.names = names;
		this.resIds = resIds;
		this.nightResIds = nightResIds;
		this.leftTheme = theme;
		this.type = type;
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
		PopViewTheme popTheme = leftTheme == null ? theme : leftTheme;
		popNewsView = new PopNewsView(mContext, names, resIds, nightResIds, popTheme);
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
		int width = type == PopThemeType.PopThemeType_More ? (int)(Config.getInstance().getScreenWidth(mContext)*0.9) : (int) (dm.widthPixels*0.5);
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

	public void showPop(View tabItemView, PopThemeType type) {
//		dismissPop();

		if (mPopMenu == null) {
			InitPopView();
		}
		if (mPopMenu.isShowing()) {
			mPopMenu.dismiss();
		}else {
			popNewsView.setThemeType(ThemeUtil.getAppTheme());
			if(type == PopThemeType.PopThemeType_More)
			{
				int left = (int)(Config.getInstance().getScreenWidth(mContext) * 0.05);
				mPopMenu.showAsDropDown(tabItemView, left, 0);
			}else
			{
				float density = mContext.getResources().getDisplayMetrics().density;
				int y = (int) (density*11);
				mPopMenu.showAsDropDown(tabItemView, 0, -y);
			}
		}
		
//		float fPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
//		43, mContext.getResources().getDisplayMetrics());
//		mPopMenu.showAtLocation(tabItemView, Gravity.TOP | Gravity.LEFT, pop_x, (int) fPx);
		
	}
	
	public void setDataNull()
	{
		if(popNewsView != null)
		{
			popNewsView.setDataNull();
		}
		if(names != null)
		{
			names = null;
		}
		if(resIds != null)
		{
			resIds = null;
		}
		if(nightResIds != null)
		{
			nightResIds = null;
		}
		if(mPopMenu != null)
		{
			mPopMenu = null;
		}
	}

//	public void dismissPop() {
//		if (mPopMenu == null) {
//			return;
//		}
//		if (mPopMenu.isShowing()) {
//			mPopMenu.dismiss();
//			mPopMenu = null;
//			return;
//		}
//	}
	
	private PopViewTheme theme = new PopViewTheme() {
		
		@Override
		public int getPopViewBg() {
			// TODO Auto-generated method stub
			return R.drawable.bg_pop;
		}

		@Override
		public int getPopItemView() {
			// TODO Auto-generated method stub
			return R.layout.pop_news_item;
		}
	};
	
	public interface PopItemOnclick
	{
		public void setOnclickLister(int popid);
	}
	
	public interface PopStateListener{
		public void popDismisslistener();
	}
}
