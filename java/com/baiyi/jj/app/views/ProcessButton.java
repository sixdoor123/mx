/**
 * 
 */
package com.baiyi.jj.app.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.turbo.turbo.mexico.R;

/**
 * @author tangkun
 *
 */
@SuppressLint("NewApi")
public class ProcessButton extends FrameLayout{
	
	private ImageView button = null;
	private ImageView proImg = null;
	
	/**
	 * @param context
	 */
	public ProcessButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}
	/**
	 * @param context
	 * @param attrs
	 */
	public ProcessButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public ProcessButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init(context);
	}
	
	private void init(Context context)
	{
		button = new ImageView(context);
//		button.setBackgroundResource(R.drawable.btn_detail_bottom_collect);
		button.setVisibility(View.VISIBLE);
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(-2, -2);
		lp.gravity = Gravity.CENTER;
		addView(button, lp);
		
		proImg = new ImageView(context);
		proImg.setBackgroundDrawable(context.getDrawable(R.drawable.loading1));
		proImg.setVisibility(View.GONE);
		lp = new FrameLayout.LayoutParams(-2, -2);
		lp.gravity = Gravity.CENTER;
		addView(proImg, lp);
	}
	
	public void setButtonDrawableResId(int resId)
	{
		button.setBackgroundDrawable(getContext().getDrawable(resId));
	}
	
//	public void setProcessDrawableResId(int resId)
//	{
//		proImg.setBackgroundDrawable(getContext().getDrawable(resId));
//	}
	
	public void setButtonClickable(boolean isClickable)
	{
		button.setClickable(isClickable);
	}
	
	public void setButtonSelected(boolean isSelected)
	{
		button.setSelected(isSelected);
	}
	
	public void setProcessOnclick(OnClickListener processOnclick) {
		button.setOnClickListener(processOnclick);
	}

	public void startAnim()
	{
		button.setVisibility(View.GONE);
		proImg.setVisibility(View.VISIBLE);
		Animation operatingAnim = AnimationUtils.loadAnimation(getContext(), R.anim.anim_rote);
		LinearInterpolator lin = new LinearInterpolator();
		operatingAnim.setInterpolator(lin);
		proImg.startAnimation(operatingAnim);
	}
	
	public void clearAnim()
	{
		button.setVisibility(View.VISIBLE);
		proImg.setVisibility(View.GONE);
		proImg.clearAnimation();
	}
}
