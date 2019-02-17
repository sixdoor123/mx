package com.jcodecraeer.xrecyclerview;


import com.baiyi.cmall.R;
import com.baiyi.core.util.ContextUtil;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoadingMoreFooter extends LinearLayout {

	private SimpleViewSwithcer progressCon;
	private Context mContext;
	public final static int STATE_LAODING = 0;
	public final static int STATE_COMPLETE = 1;
	public final static int STATE_NOMORE = 2;
	private TextView mText;

	protected View mFooterView = null;
	private ImageView foot_loading = null;
	private ImageView foot_noLoad = null;
	private TextView moreTextEnd = null;
	private RelativeLayout moreLoading = null;

	public LoadingMoreFooter(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public LoadingMoreFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public void initView(Context context) {
		mContext = context;
		ContextUtil.getLayoutInflater(getContext())
		.inflate(R.layout.recly_footer_item, this);
		foot_loading = (ImageView) findViewById(R.id.foot_loading);
		foot_loading.setImageResource(R.anim.anim_refresh);
		foot_noLoad = (ImageView) findViewById(R.id.foot_noload);
		moreLoading = (RelativeLayout) findViewById(R.id.more_loading);
		moreTextEnd = (TextView) findViewById(R.id.more_text_end);
		// setGravity(Gravity.CENTER);
		// setLayoutParams(new ViewGroup.LayoutParams(
		// ViewGroup.LayoutParams.MATCH_PARENT,
		// ViewGroup.LayoutParams.WRAP_CONTENT));
		// progressCon = new SimpleViewSwithcer(context);
		// progressCon.setLayoutParams(new ViewGroup.LayoutParams(
		// ViewGroup.LayoutParams.WRAP_CONTENT,
		// ViewGroup.LayoutParams.WRAP_CONTENT));
		//
		// AVLoadingIndicatorView progressView = new
		// AVLoadingIndicatorView(this.getContext());
		// progressView.setIndicatorColor(0xffB5B5B5);
		// progressView.setIndicatorId(ProgressStyle.BallSpinFadeLoader);
		// progressCon.setView(progressView);
		//
		// addView(progressCon);
		// mText = new TextView(context);
		// mText.setText("正在加载中...");
		//
		// LinearLayout.LayoutParams layoutParams = new
		// LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
		// ViewGroup.LayoutParams.WRAP_CONTENT);
		// layoutParams.setMargins(
		// (int)getResources().getDimension(R.dimen.textandiconmargin),0,0,0 );
		//
		// mText.setLayoutParams(layoutParams);
		// addView(mText);
	}

	// public void setProgressStyle(int style) {
	// if(style == ProgressStyle.SysProgress){
	// progressCon.setView(new ProgressBar(mContext, null,
	// android.R.attr.progressBarStyle));
	// }else{
	// AVLoadingIndicatorView progressView = new
	// AVLoadingIndicatorView(this.getContext());
	// progressView.setIndicatorColor(0xffB5B5B5);
	// progressView.setIndicatorId(style);
	// progressCon.setView(progressView);
	// }
	// }

	// private void showFoot(boolean show) {
	// if (show) {
	//
	// this.setVisibility(View.VISIBLE);
	// AnimationDrawable anim = (AnimationDrawable) foot_loading.getDrawable();
	// anim.start();
	// foot_loading.setVisibility(View.VISIBLE);
	// foot_noLoad.setVisibility(View.GONE);
	// moreTextEnd.setText("正在加载中");
	//
	// } else {
	// setNoMore();
	// }
	// }

	public void setNoMore() {
		this.setVisibility(View.VISIBLE);
		AnimationDrawable anim = (AnimationDrawable) foot_loading.getDrawable();
		anim.stop();
		foot_loading.setVisibility(View.GONE);
		foot_noLoad.setVisibility(View.VISIBLE);
		// moreLoading.setVisibility(View.INVISIBLE);
		moreTextEnd.setText("已经到底了");
	}

	public void setState(int state) {
		switch (state) {
		case STATE_LAODING:
			this.setVisibility(View.VISIBLE);
			AnimationDrawable anim = (AnimationDrawable) foot_loading.getDrawable();
			anim.start();
			foot_loading.setVisibility(View.VISIBLE);
			foot_noLoad.setVisibility(View.GONE);
			moreTextEnd.setText("正在加载中");
			// progressCon.setVisibility(View.VISIBLE);
			// mText.setText(mContext.getText(R.string.listview_loading));
			// this.setVisibility(View.VISIBLE);
			break;
		case STATE_COMPLETE:
			moreTextEnd.setText("上拉加载更多");
			this.setVisibility(View.GONE);
			// mText.setText(mContext.getText(R.string.listview_loading));
			// this.setVisibility(View.GONE);
			break;
		case STATE_NOMORE:
			setNoMore();
			// mText.setText(mContext.getText(R.string.nomore_loading));
			// progressCon.setVisibility(View.GONE);
			// this.setVisibility(View.VISIBLE);
			break;
		}

	}
}
