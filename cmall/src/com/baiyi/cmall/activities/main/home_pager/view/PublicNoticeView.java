package com.baiyi.cmall.activities.main.home_pager.view;

import java.util.ArrayList;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.home_pager.ZiXunActivity;
import com.baiyi.cmall.activities.main.home_pager.ZiXunDetailActivity;
import com.baiyi.cmall.activities.main.home_pager.entity.MainEntity;
import com.baiyi.cmall.activities.main.home_pager.entity.ZiXunEntity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

/**
 * 首页资讯
 * 
 * @author lizl
 * 
 */
public class PublicNoticeView extends LinearLayout {

	private Context mContext;
	// 资讯总布局
	private View mScrollTitleView;
	// 资讯容器
	private ViewFlipper mViewFlipper;

	private LinearLayout mLinZixun;
	
	private MarqueeTextView[] marqueeTextView;

	public PublicNoticeView(Context context) {
		super(context);
		mContext = context;
		bindLinearLayout();
	}

	public PublicNoticeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		bindLinearLayout();
	}

	/**
	 * 绑定布局
	 */
	private void bindLinearLayout() {
		mScrollTitleView = LayoutInflater.from(mContext).inflate(
				R.layout.activity_home_zixun, null);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		addView(mScrollTitleView, params);
		mLinZixun = (LinearLayout) findViewById(R.id.lin_zixun);
		mViewFlipper = (ViewFlipper) mScrollTitleView
				.findViewById(R.id.vp_zi_xun);
		/*
		 * 设置滚动的动画，从底部出来，从上部消失
		 */
		mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext,
				R.anim.slide_vp_in_bottom));
		mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext,
				R.anim.slide_vp_out_top));
		mViewFlipper.startFlipping();

	}

	/**
	 * 綁定资讯信息-----------------------------------------------------------
	 */
	@SuppressLint("InflateParams")
	public void bindNotices(final ArrayList<ZiXunEntity> zixunS) {

		mViewFlipper.removeAllViews();
		if (zixunS == null || zixunS.size() == 0) {
			return;
		}
		marqueeTextView = new MarqueeTextView[zixunS.size()];
		for (int i = 0; i < zixunS.size(); i++) {
			
			/*
			 * 添加能夠横屏滚动的TextView
			 */
			marqueeTextView[i] = (MarqueeTextView) LayoutInflater.from(mContext).inflate(
					R.layout.item_marquee_textview, null);
			marqueeTextView[i].setId(i);
			marqueeTextView[i].setText(zixunS.get(i).getTitle());
			final int s = i;
			marqueeTextView[i].setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					Intent intent = new Intent(getContext(),ZiXunDetailActivity.class);
					intent.putExtra("intent", zixunS.get(s));
					getContext().startActivity(intent);
				}
			});
			
			mViewFlipper.addView(marqueeTextView[i]);
		}

		mLinZixun.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				((BaseActivity) mContext).goActivitys(zixunS,ZiXunActivity.class);

			}
		});
	}

}
