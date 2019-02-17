package com.baiyi.jj.app.views;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baiyi.core.util.ContextUtil;
import com.turbo.turbo.mexico.R;
public class MyLoadingBar extends RelativeLayout {
	private View loadingContainer;
	private TextView loadingPercentTv;
	private TextView loadingInfoTv;
	private TextView errorTv;
	private ImageView freshBtn;
	private ImageView pgBar_ImageView;
	private View.OnClickListener clickListner;
	private Context context;
	Animation operatingAnim;
	public static final int type_collect = 0;
	public static final int type_sample = 1;
	
	// private int color = 0xff000000;
	 private ImageView pgBar ;
	public MyLoadingBar(Context context) {
		super(context);
		init();
	}

	public void setOnClickListener(View.OnClickListener listener) {
		this.clickListner = listener;
	}

	public MyLoadingBar(Context context, AttributeSet attrs) {
		super(context, attrs);

		// TypedArray typeArray = getContext().obtainStyledAttributes(attrs,
		// R.styleable.MyProgressBar);
		// int num = typeArray.getIndexCount();
		// for(int i=0;i<num;i++){
		// int attr = typeArray.getIndex(i);
		// if(attr==R.styleable.MyProgressBar_text_color){
		// int colorIndex = typeArray.getResourceId(attr, -1);
		// if(colorIndex==-1){
		// color = typeArray.getColor(attr, 0xff000000);
		// }else{
		// color = getContext().getResources().getColor(colorIndex);
		// }
		//
		// }
		// }
		// typeArray.recycle();
		init();
	}

	private void init() {
		LayoutInflater inflater = ContextUtil.getLayoutInflater(getContext());
		View view = inflater.inflate(R.layout.loading_bar, null);
		loadingContainer = (View) view.findViewById(R.id.loading_bar);
		loadingPercentTv = (TextView) view.findViewById(R.id.text_loading);
		loadingInfoTv = (TextView) view.findViewById(R.id.progress_info);
		errorTv = (TextView) view.findViewById(R.id.progress_error_info);
		pgBar = (ImageView)view.findViewById(R.id.pgbar);
		pgBar.setImageResource(R.drawable.anim_loading);
	    //loadingInfoTv.setTextColor(color);
		 
		loadingPercentTv.setTextColor(0xffffffff);
		freshBtn = (ImageView) view.findViewById(R.id.fresh_button);
		
		/*
		freshBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				reset();
				if (clickListner != null) {
					clickListner.onClick(MyProgressBar.this);
				}
			}
		});
		*/
		addView(view);
	}

	public void setImageResecouce(int resecouce){
		pgBar.setImageResource(resecouce);
	}
	
	public void start()
	{
		this.setVisibility(View.VISIBLE);
	    AnimationDrawable anim = (AnimationDrawable) pgBar.getDrawable();
	    anim.start();
	}
	
	public void stop()
	{
		this.setVisibility(View.GONE);
	    AnimationDrawable anim = (AnimationDrawable) pgBar.getDrawable();  
	    anim.stop();
	}


	public void reset() {

		freshBtn.setVisibility(View.GONE);
		loadingInfoTv.setVisibility(View.GONE);
		loadingContainer.setVisibility(View.VISIBLE);
		errorTv.setVisibility(View.GONE);
	}
	

	public void setPercentTextColor(int color) {
		loadingPercentTv.setTextColor(color);
	}

	public void setPercent(int value) {
		loadingPercentTv.setVisibility(View.VISIBLE);
		loadingPercentTv.setText(value + "%");
	}

	public void setProgressInfo(String info) {
		freshBtn.setVisibility(View.GONE);
		loadingContainer.setVisibility(View.VISIBLE);
		loadingInfoTv.setVisibility(View.VISIBLE);
		loadingInfoTv.setText(info);
		loadingInfoTv.setPadding(10, 0, 0, 0);
		errorTv.setVisibility(View.GONE);
	}
	
	public void setNoTextProgressInfo() {
		freshBtn.setVisibility(View.GONE);
		loadingPercentTv.setVisibility(View.GONE);
		loadingContainer.setVisibility(View.VISIBLE);
		loadingInfoTv.setVisibility(View.GONE);
		errorTv.setVisibility(View.GONE);
	}

	public void setPercentInvisible() {
		loadingPercentTv.setVisibility(View.GONE);
		errorTv.setVisibility(View.GONE);
	}

	public void setProgressInfoInvisible() {
		loadingInfoTv.setVisibility(View.GONE);
		errorTv.setVisibility(View.GONE);
	}

	public void setProgressLoadError(String errorMessage, int resType) {
		loadingContainer.setVisibility(View.GONE);
		freshBtn.setVisibility(View.VISIBLE);
//		if(resType == type_collect)
//		{
//			freshBtn.setImageResource(R.drawable.bg_favorite_empty);
//		}else
//		{
//			freshBtn.setImageResource(R.drawable.icon_tip_no_coupon);
//		}
		loadingInfoTv.setVisibility(View.GONE);
		errorTv.setVisibility(View.VISIBLE);
		if (errorMessage != null) {
			errorTv.setText(errorMessage);
		}
	}

	
}
