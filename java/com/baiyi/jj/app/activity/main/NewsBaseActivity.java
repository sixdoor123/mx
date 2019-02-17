/**
 * 
 */
package com.baiyi.jj.app.activity.main;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.turbo.turbo.mexico.R;
import com.baiyi.jj.app.theme.ThemeUtil;

/**
 * ����ˢ��
 * @author tangkun
 *
 */
public class NewsBaseActivity extends MainNetChangeActivity{
	
	private ImageView imgRefresh = null;
	private ImageView imgRefreshShow = null;
	protected boolean isFirstRefresh = true;
	protected RelativeLayout newCountGroup = null;
	protected TextView newCountTv = null;
	
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
	
	public void inVisibleLoad(){
		if(imgRefresh == null)
		{
			return;
		}
		imgRefresh.setVisibility(View.GONE);
		imgRefreshShow.setVisibility(View.GONE);
	}
	public void VisibleLoad1(){
		if(imgRefresh == null)
		{
			return;
		}
		imgRefresh.setVisibility(View.GONE);
		imgRefreshShow.setVisibility(View.VISIBLE);
	}
	
	public void initNewCont()
	{
		newCountGroup = (RelativeLayout)findViewById(R.id.news_new_count_group);
		newCountTv = (TextView)findViewById(R.id.news_new_count);
	}
	
	public void displayRefreshCount(final int newCount)
	{
//		TimerCountCallBack timer=new TimerCountCallBack(3000, 1000);
//		timer.setCallBack(new CallBack() {
//			@Override
//			public void callBackOnTick(long millisUntilFinished) {
////				news_new_count_group.set
//				newCountGroup.setVisibility(View.VISIBLE);
//				newCountTv.setText("�Ƽ�������" + newCount + "������");
//			}
//			@Override
//			public void callBackOnFinish() {
//				newCountGroup.setVisibility(View.GONE);
//			}
//		});
//		timer.start();
	}
	
	/* (non-Javadoc)
	 * @see com.baiyi.jj.app.activity.BaseActivity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setLoadingTheme();
	}
	
	private void setLoadingTheme()
	{
		if(imgRefresh == null)
		{
			return;
		}
		if(ThemeUtil.getAppTheme() == ThemeUtil.Theme_day)
		{
			imgRefresh.setImageResource(R.drawable.anim_loading);
		}else
		{
//			imgRefresh.setImageResource(R.anim.anim_night_refresh);
		}
	}

}
