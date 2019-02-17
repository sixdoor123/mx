package com.baiyi.jj.app.activity.attention.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baiyi.jj.app.utils.JsonParse;
import com.baiyi.jj.app.utils.TLog;
import com.turbo.turbo.mexico.R;

public class PullToRefreshListHeader extends LinearLayout {
    private LinearLayout mContainer;
    private ImageView mArrowImageView;
    private ProgressBar mProgressBar;
    private TextView mHintTextView;
    private int mState = STATE_NORMAL;

    AnimationDrawable animationDrawable;

    private final int ROTATE_ANIM_DURATION = 180;

    public final static int STATE_NORMAL = 0;
    public final static int STATE_READY = 1;
    public final static int STATE_REFRESHING = 2;

    public int mMeasuredHeight;

    public PullToRefreshListHeader(Context context) {
        super(context);
        initView(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public PullToRefreshListHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public int getmState() {
        return mState;
    }

    private void initView(Context context) {
        LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, 0);
        mContainer = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.xlistview_header, null);
        addView(mContainer, lp);
        setGravity(Gravity.BOTTOM);

        mArrowImageView = (ImageView) findViewById(R.id.pull_to_refresh_image);
        mHintTextView = (TextView) findViewById(R.id.refresh_status_textview);
        mProgressBar = (ProgressBar) findViewById(R.id.pull_to_refresh_progress);
        setLoadingDrawable(context);
        mMeasuredHeight = getMeasuredHeight();

    }

    public final void setLoadingDrawable(Context context) {

        animationDrawable = (AnimationDrawable) context.getResources().getDrawable(R.drawable.anim_loading);
        mArrowImageView.setImageDrawable(animationDrawable);

    }

    public void onMove(float delta) {
        if(getVisiableHeight() > 0 || delta > 0) {
            setVisiableHeight((int) delta + getVisiableHeight());
            if (mState <= STATE_READY) { // δ����ˢ��״̬�����¼�ͷ
                if (getVisiableHeight() > mMeasuredHeight) {
                    setState(STATE_READY);
                }else {
                    setState(STATE_NORMAL);
                }
            }
        }
    }
    public void reset() {
        new Handler().postDelayed(new Runnable(){
            public void run() {
                smoothScrollTo(0);
                setState(STATE_NORMAL);
            }
        }, 500);

    }

    private void smoothScrollTo(int destHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(getVisiableHeight(), destHeight);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                setVisiableHeight((Integer) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    public void setState(int state) {
        if (state == mState)
            return;

        if (state == STATE_REFRESHING) {
//            mArrowImageView.clearAnimation();
            animationDrawable.start();
            mArrowImageView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
        } else {
            animationDrawable.stop();
            mArrowImageView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
        }

        switch (state) {
        case STATE_NORMAL:
            if (mState == STATE_READY) {
//                mArrowImageView.startAnimation(mRotateDownAnim);
                animationDrawable.stop();
            }
            if (mState == STATE_REFRESHING) {
//                mArrowImageView.clearAnimation();
                animationDrawable.start();
            }
            mHintTextView.setText(R.string.pull_to_refresh_pull_label);
            break;
        case STATE_READY:
            if (mState != STATE_READY) {
//                mArrowImageView.clearAnimation();
//                mArrowImageView.startAnimation(mRotateUpAnim);
                animationDrawable.stop();
                mHintTextView.setText(R.string.pull_to_refresh_pull_label);
            }
            break;
        case STATE_REFRESHING:
            mHintTextView.setText(R.string.pull_to_refresh_refreshing_label);
            break;
        default:
        }

        mState = state;
    }

    public void setVisiableHeight(int height) {
        if (height < 0)
            height = 0;
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    public int getVisiableHeight() {
        return mContainer.getHeight();
    }

}
