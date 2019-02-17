/**
 * 
 */
package com.baiyi.jj.app.activity.main;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.utils.TimerCountCallBack;
import com.baiyi.jj.app.utils.TimerCountCallBack.CallBack;
import com.baiyi.jj.app.views.pageview.BasePageView;

/**
 * @author tangkun
 *
 */
public abstract class LoadingBasePager extends BasePageView{

	private ImageView imgRefresh = null;
	private ImageView imgRefreshShow = null;
	
	protected RelativeLayout newCountGroup = null;
	protected TextView newCountTv = null;
	
	private TimerCountCallBack timer = null;
	
	public static final int Refresh_Type_Pull = 1;
	public static final int Refresh_Type_More = 2;
	public static final int Refresh_Type_New = 3;
	
	public LoadingBasePager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void initLoading(View view)
	{
		imgRefresh = (ImageView)view.findViewById(R.id.img_refresh);
		imgRefreshShow = (ImageView)view.findViewById(R.id.img_refresh_show);
		setLoadingTheme();
	}
	
	public void startLoading()
	{
		if(imgRefresh == null)
		{
			return;
		}
		imgRefresh.setVisibility(View.VISIBLE);
		imgRefreshShow.setVisibility(View.GONE);
	    AnimationDrawable anim = (AnimationDrawable) imgRefresh.getDrawable();
	    anim.start();
	}
	
	public void stopLoading()
	{
		if(imgRefresh == null)
		{
			return;
		}
		imgRefresh.setVisibility(View.GONE);
		imgRefreshShow.setVisibility(View.VISIBLE);
	    AnimationDrawable anim = (AnimationDrawable) imgRefresh.getDrawable();  
	    anim.stop();
	}
	
	public void initNewCont()
	{
		newCountGroup = (RelativeLayout)findViewById(R.id.news_new_count_group);
		newCountGroup.setVisibility(View.GONE);
		newCountTv = (TextView)findViewById(R.id.news_new_count);
		
	}
	
	public void displayRefreshCount(final int newCount, final boolean isError)
	{
		if(timer == null)
		{
			timer=new TimerCountCallBack(2000, 1000);
		}
		timer.cancel();
		timer.setCallBack(new CallBack() {
			@Override
			public void callBackOnTick(long millisUntilFinished) {
				newCountGroup.setVisibility(View.VISIBLE);
				if(isError)
				{
					newCountTv.setText(getContext().getResources().getString(R.string.tip_loaddata_timeout));
				}else
				{
					if(newCount == 0)
					{
						newCountTv.setText(getContext().getResources().getString(R.string.tip_loaddata_pushnull));
					}else
					{
						newCountTv.setText(getContext().getResources().getString(R.string.tip_recomment1) +" "+ newCount +" "+ getContext().getResources().getString(R.string.tip_recomment2));
					}
				}
			}
			@Override
			public void callBackOnFinish() {
				newCountGroup.setVisibility(View.GONE);
			}
		});
		timer.start();
	}
	
	/* (non-Javadoc)
	 * @see com.baiyi.jj.app.activity.BaseActivity#onResume()
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		setLoadingTheme();
	}
	
	private void setLoadingTheme()
	{
		if(imgRefresh == null)
		{
			return;
		}
		imgRefresh.setImageResource(R.drawable.anim_loading);
	}
	

	public void onRefreshListView()
	{
		
	}
	
	public abstract void onclickPopItem(int state, ViewGroup title);

}
