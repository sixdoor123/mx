package com.jcodecraeer.xrecyclerview;

import java.util.Date;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baiyi.cmall.R;
import com.jcodecraeer.xrecyclerview.progressindicator.AVLoadingIndicatorView;

public class ArrowRefreshHeader extends LinearLayout implements BaseRefreshHeader{
	private LinearLayout mContainer;
	private ImageView mArrowImageView;
	private SimpleViewSwithcer mProgressBar;
	private TextView mStatusTextView;
	private int mState = STATE_NORMAL;
    private Context mContext;
	
	private TextView mHeaderTimeView;

	private Animation mRotateUpAnim;
	private Animation mRotateDownAnim;
	
	private final int ROTATE_ANIM_DURATION = 180;

	public int mMeasuredHeight;

	public ArrowRefreshHeader(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public ArrowRefreshHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {

        mContext = context;
		// 閸掓繂顫愰幆鍛枌閿涘矁顔曠純顔荤瑓閹峰鍩涢弬鐨廼ew妤傛ê瀹虫稉锟�0
		mContainer = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.listview_header, null);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
		this.setLayoutParams(lp);
        this.setPadding(0, 0, 0, 0);

		addView(mContainer, new LayoutParams(LayoutParams.MATCH_PARENT, 0));
		setGravity(Gravity.BOTTOM);

		mArrowImageView = (ImageView)findViewById(R.id.listview_header_arrow);
		
//		setLoadingDrawable(context.getResources().getDrawable(R.anim.anim_loading));
		setLoadingDrawable(context.getResources().getDrawable(R.anim.anim_refresh));
		mStatusTextView = (TextView)findViewById(R.id.refresh_status_textview);

        //init the progress view
		mProgressBar = (SimpleViewSwithcer)findViewById(R.id.listview_header_progressbar);
        AVLoadingIndicatorView progressView = new  AVLoadingIndicatorView(context);
        progressView.setIndicatorColor(0xffB5B5B5);
        progressView.setIndicatorId(ProgressStyle.BallSpinFadeLoader);
        mProgressBar.setView(progressView);


		mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateUpAnim.setFillAfter(true);
		mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateDownAnim.setFillAfter(true);
		
		mHeaderTimeView = (TextView)findViewById(R.id.last_refresh_time);
		measure(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		mMeasuredHeight = getMeasuredHeight();
	}

    public void setProgressStyle(int style) {
        if(style == ProgressStyle.SysProgress){
            mProgressBar.setView(new ProgressBar(mContext, null, android.R.attr.progressBarStyle));
        }else{
            AVLoadingIndicatorView progressView = new  AVLoadingIndicatorView(this.getContext());
            progressView.setIndicatorColor(0xffB5B5B5);
            progressView.setIndicatorId(style);
            mProgressBar.setView(progressView);
        }
    }

	public final void setLoadingDrawable(Drawable imageDrawable) {
		// Set Drawable
		mArrowImageView.setImageDrawable(imageDrawable);

//		// Now call the callback
//		onLoadingDrawableSet(imageDrawable);
	}

    public void setArrowImageView(int resid){
        mArrowImageView.setImageResource(resid);
    }

	public void setState(int state) {
		if (state == mState) return ;

		if (state == STATE_REFRESHING) {	// 閺勫墽銇氭潻娑樺
//			mArrowImageView.clearAnimation();
//			mArrowImageView.setVisibility(View.INVISIBLE);
//			mProgressBar.setVisibility(View.VISIBLE);
		} else if(state == STATE_DONE) {
//            mArrowImageView.setVisibility(View.INVISIBLE);
//            mProgressBar.setVisibility(View.INVISIBLE);
        } else {	// 閺勫墽銇氱粻顓炪仈閸ュ墽澧�
//			mArrowImageView.setVisibility(View.VISIBLE);
//			mProgressBar.setVisibility(View.INVISIBLE);
		}
		
		switch(state){
            case STATE_NORMAL:
//                if (mState == STATE_RELEASE_TO_REFRESH) {
//                    mArrowImageView.startAnimation(mRotateDownAnim);
//                }
//                if (mState == STATE_REFRESHING) {
//                    mArrowImageView.clearAnimation();
//                }
                mStatusTextView.setText(R.string.listview_header_hint_normal);
                break;
            case STATE_RELEASE_TO_REFRESH:
                if (mState != STATE_RELEASE_TO_REFRESH) {
//                    mArrowImageView.clearAnimation();
//                    mArrowImageView.startAnimation(mRotateUpAnim);
                    mStatusTextView.setText(R.string.listview_header_hint_release);
                }
                break;
            case     STATE_REFRESHING:
                mStatusTextView.setText(R.string.refreshing);
    			((AnimationDrawable) mArrowImageView.getDrawable()).start();
                break;
            case    STATE_DONE:
    			((AnimationDrawable) mArrowImageView.getDrawable()).stop();
                mStatusTextView.setText(R.string.refresh_done);
                break;
            default:
		}
		
		mState = state;
	}

    public int getState() {
        return mState;
    }

    @Override
	public void refreshComplate(){
        mHeaderTimeView.setText(friendlyTime(new Date()));
        setState(STATE_DONE);
        new Handler().postDelayed(new Runnable(){
            public void run() {
                reset();
            }
        }, 500);
	}

	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		LayoutParams lp = (LayoutParams) mContainer
				.getLayoutParams();
		lp.height = height;
		mContainer.setLayoutParams(lp);
	}

	public int getVisiableHeight() {
        int height = 0;
        LayoutParams lp = (LayoutParams) mContainer
                .getLayoutParams();
        height = lp.height;
		return height;
	}

    @Override
    public void onMove(float delta) {
        if(getVisiableHeight() > 0 || delta > 0) {
            setVisiableHeight((int) delta + getVisiableHeight());
            if (mState <= STATE_RELEASE_TO_REFRESH) { // 閺堫亜顦╂禍搴″煕閺傛壆濮搁幀渚婄礉閺囧瓨鏌婄粻顓炪仈
                if (getVisiableHeight() > mMeasuredHeight) {
                    setState(STATE_RELEASE_TO_REFRESH);
                }else {
                    setState(STATE_NORMAL);
                }
            }
        }
    }

    @Override
    public boolean releaseAction() {
        boolean isOnRefresh = false;
        int height = getVisiableHeight();
        if (height == 0) // not visible.
            isOnRefresh = false;

        if(getVisiableHeight() > mMeasuredHeight &&  mState < STATE_REFRESHING){
            setState(STATE_REFRESHING);
            isOnRefresh = true;
        }
        // refreshing and header isn't shown fully. do nothing.
        if (mState == STATE_REFRESHING && height <=  mMeasuredHeight) {
            //return;
        }
        int destHeight = 0; // default: scroll back to dismiss header.
        // is refreshing, just scroll back to show all the header.
        if (mState == STATE_REFRESHING) {
            destHeight = mMeasuredHeight;
        }
        smoothScrollTo(destHeight);

        return isOnRefresh;
    }

    public void reset() {
        smoothScrollTo(0);
        setState(STATE_NORMAL);
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

    public static String friendlyTime(Date time) {
        //閼惧嘲褰噒ime鐠烘繄顬囪ぐ鎾冲閻ㄥ嫮顫楅弫锟�
        int ct = (int)((System.currentTimeMillis() - time.getTime())/1000);

        if(ct == 0) {
            return "閸掓艾鍨�";
        }

        if(ct > 0 && ct < 60) {
            return ct + "缁夋帒澧�";
        }

        if(ct >= 60 && ct < 3600) {
            return Math.max(ct / 60,1) + "閸掑棝鎸撻崜锟�";
        }
        if(ct >= 3600 && ct < 86400)
            return ct / 3600 + "鐏忓繑妞傞崜锟�";
        if(ct >= 86400 && ct < 2592000){ //86400 * 30
            int day = ct / 86400 ;
            return day + "婢垛晛澧�";
        }
        if(ct >= 2592000 && ct < 31104000) { //86400 * 30
            return ct / 2592000 + "閺堝牆澧�";
        }
        return ct / 31104000 + "楠炴潙澧�";
    }

}
