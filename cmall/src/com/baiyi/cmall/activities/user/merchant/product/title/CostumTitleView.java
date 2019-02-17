package com.baiyi.cmall.activities.user.merchant.product.title;

import java.util.ArrayList;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.core.util.ContextUtil;

import android.R.interpolator;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 商品详情标题栏
 * 
 * @author sunxy
 */
public class CostumTitleView extends LinearLayout implements OnClickListener {

	private Context context;

	public CostumTitleView(Context context) {
		this(context, null);
	}

	public CostumTitleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

		initView();

	}

	private View view = null;

	/**
	 * 界面
	 */
	private void initView() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			view = ContextUtil.getLayoutInflater(context).inflate(
					R.layout.activity_product_detail_title_h, null);
		} else {
			view = ContextUtil.getLayoutInflater(context).inflate(
					R.layout.activity_product_detail_title_l, null);
		}
		LayoutParams params = new LayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		addView(view, params);

		findViewById(view);
	}

	// 返回
	private ImageView mImgBack;
	// TextView
	ArrayList<TextView> textViews = null;
	// View
	ArrayList<View> views = null;

	// 商品
	private LinearLayout mLlProduct;
	// 详情
	private LinearLayout mLlDetails;
	// 评论
	private LinearLayout mLlComment;

	// 更多
	private ImageView mImgMore;

	/**
	 * 找控件
	 * 
	 * @param view
	 */
	private void findViewById(View view) {
		mImgBack = (ImageView) view.findViewById(R.id.img_back);
		mLlComment = (LinearLayout) view.findViewById(R.id.ll_third);
		mLlDetails = (LinearLayout) view.findViewById(R.id.ll_seconed);
		mLlProduct = (LinearLayout) view.findViewById(R.id.ll_first);
		mImgMore = (ImageView) view.findViewById(R.id.img_more);

		textViews = new ArrayList<TextView>();
		textViews.add((TextView) view.findViewById(R.id.txt_first));
		textViews.add((TextView) view.findViewById(R.id.txt_seconed));
		textViews.add((TextView) view.findViewById(R.id.txt_third));

		views = new ArrayList<View>();
		views.add(view.findViewById(R.id.v_first));
		views.add(view.findViewById(R.id.v_seconed));
		views.add(view.findViewById(R.id.v_third));

		mLlComment.setOnClickListener(this);
		mLlDetails.setOnClickListener(this);
		mLlProduct.setOnClickListener(this);
		mImgBack.setOnClickListener(this);
		mImgMore.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_back:// 返回
			((BaseActivity) context).finish();
			break;
		case R.id.ll_first:// 商品
			setTextColor(0);
			setViewColor(0);
			if (null != onTitleViewChangeListener) {
				onTitleViewChangeListener.onChange(0);
			}
			break;
		case R.id.ll_seconed:// 详情
			setTextColor(1);
			setViewColor(1);
			if (null != onTitleViewChangeListener) {
				onTitleViewChangeListener.onChange(1);
			}
			break;
		case R.id.ll_third:// 评论
			setTextColor(2);
			setViewColor(2);
			if (null != onTitleViewChangeListener) {
				onTitleViewChangeListener.onChange(2);
			}
			break;
		case R.id.img_more:// 更多

			break;
		}
	}

	private OnTitleViewChangeListener onTitleViewChangeListener;

	public void setOnTitleViewChangeListener(
			OnTitleViewChangeListener onTitleViewChangeListener) {
		this.onTitleViewChangeListener = onTitleViewChangeListener;
	}

	/**
	 * 当标题栏上的改变时，回调
	 * 
	 * @author sunxy
	 * 
	 */
	public interface OnTitleViewChangeListener {
		void onChange(int pos);
	}

	public void setTextColor(int pos) {
		for (int i = 0; i < textViews.size(); i++) {
			if (i == pos) {
				textViews.get(i).setTextColor(
						context.getResources().getColor(R.color.bg_buyer));
			} else {
				textViews.get(i).setTextColor(
						context.getResources().getColor(R.color.bg_white));
			}
		}
	}

	public void setViewColor(int pos) {
		for (int i = 0; i < textViews.size(); i++) {
			if (i == pos) {
				views.get(i).setBackgroundColor(
						context.getResources().getColor(R.color.bg_white));

				Animation animation = AnimationUtils.loadAnimation(context,
						R.anim.slide_in_from_bottom_select);
				views.get(i).setAnimation(animation);
			} else {
				views.get(i).setBackgroundColor(
						context.getResources().getColor(R.color.bg_green));
			}
		}
	}

}
