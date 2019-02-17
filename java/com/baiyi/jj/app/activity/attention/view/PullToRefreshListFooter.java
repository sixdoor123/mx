package com.baiyi.jj.app.activity.attention.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.jj.app.activity.BaseActivity;
import com.turbo.turbo.mexico.R;

public class PullToRefreshListFooter extends LinearLayout {
    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_LOADING = 2;
    public final static int STATE_NoMore = 3;

    private Context mContext;

    private View mContentView;
    private View mProgressBar;
    private TextView mHintView;
    private ImageView imgLoad;
    private AnimationDrawable animationDrawable;

    private int mState = STATE_NORMAL;

    public PullToRefreshListFooter(Context context) {
        super(context);
        initView(context);
    }

    public PullToRefreshListFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public int getmState() {
        return mState;
    }

    public void setState(int state) {
        mHintView.setVisibility(View.INVISIBLE);
        imgLoad.setVisibility(View.INVISIBLE);
        animationDrawable.stop();
        mState = state;
        if (state == STATE_READY) {
            mHintView.setVisibility(View.VISIBLE);
            mHintView.setText("");
        } else if (state == STATE_LOADING) {
            mProgressBar.setVisibility(View.GONE);
            imgLoad.setVisibility(View.VISIBLE);
            animationDrawable.start();
            mHintView.setVisibility(View.VISIBLE);
            mHintView.setText(R.string.txt_progress_loading);
        } else if (state == STATE_NoMore){
            mProgressBar.setVisibility(View.GONE);
            imgLoad.setVisibility(View.GONE);
            mHintView.setVisibility(View.VISIBLE);
            mHintView.setText(R.string.listview_foot);
        }else {
            mHintView.setVisibility(View.VISIBLE);
            mHintView.setText("");
        }
    }

    public void setBottomMargin(int height) {
        if (height < 0)
            return;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
        lp.bottomMargin = height;
        mContentView.setLayoutParams(lp);
    }

    public int getBottomMargin() {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
        return lp.bottomMargin;
    }

    /**
     * normal status
     */
    public void normal() {
        mHintView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        imgLoad.setVisibility(View.INVISIBLE);
        animationDrawable.stop();
    }

    /**
     * loading status
     */
    public void loading() {
        mHintView.setVisibility(View.VISIBLE);
        mHintView.setText(getContext().getResources().getString(R.string.txt_progress_loading));
        mProgressBar.setVisibility(View.GONE);
        imgLoad.setVisibility(View.VISIBLE);
        animationDrawable.start();
    }

    /**
     * hide footer when disable pull load more
     */
    public void hide() {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
        lp.height = 0;
        mContentView.setLayoutParams(lp);
    }

    /**
     * show footer
     */
    public void show() {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
//        lp.height = LayoutParams.WRAP_CONTENT;
        lp.height = BaseActivity.getDensity_int(36);
        mContentView.setLayoutParams(lp);
    }

    private void initView(Context context) {
        mContext = context;
        LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.xlistview_footer, null);
        addView(moreView);
        moreView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

        mContentView = moreView.findViewById(R.id.xlistview_footer_content);
        mProgressBar = moreView.findViewById(R.id.xlistview_footer_progressbar);
        mHintView = (TextView) moreView.findViewById(R.id.more_text_end);
        imgLoad = (ImageView) moreView.findViewById(R.id.foot_loading);
        mProgressBar.setVisibility(View.GONE);
        animationDrawable = (AnimationDrawable) context.getResources().getDrawable(R.drawable.anim_loading);
        imgLoad.setImageDrawable(animationDrawable);
    }

}
