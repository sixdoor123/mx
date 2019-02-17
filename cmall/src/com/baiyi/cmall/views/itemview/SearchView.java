package com.baiyi.cmall.views.itemview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.baiyi.cmall.R;

/**
 * 自定义的搜索控件
 * 
 * @author lizl
 *
 */
public class SearchView extends EditText {

	private Drawable drawableLeft;
	private Drawable drawableRight;
	private boolean isShow;

	public SearchView(Context context) {
		this(context, null);
	}

	public SearchView(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.editTextStyle);
	}

	public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initDraw();
	}

	private void initDraw() {

		drawableLeft = getCompoundDrawables()[0];
		if (null == drawableLeft) {
			drawableLeft = getResources().getDrawable(R.drawable.icon_search);
		}
		drawableRight = getCompoundDrawables()[2];
		if (null == drawableRight) {
			drawableRight = getResources().getDrawable(R.drawable.icon_delete);
		}
		setRightVisible(false);
		// 字体改变事件
		addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

				if (TextUtils.isEmpty(s)) {
					setRightVisible(false);
				} else {
					setRightVisible(true);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	private void setRightVisible(boolean visible) {
		// 设置左、上、右、下各自是否显示图片
		setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, visible ? drawableRight : null, null);
		isShow = visible;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		float xPosition = event.getX();
		if (isShow && xPosition > getWidth() - getPaddingRight() - drawableRight.getIntrinsicWidth()
				&& xPosition < getWidth() - getPaddingRight()) {
			setText("");
			if (null != resetListener) {
				resetListener.onReset();
			}
		}
		return super.onTouchEvent(event);
	}

	private OnResetPressClickListener resetListener;

	public void setResetListener(OnResetPressClickListener resetListener) {
		this.resetListener = resetListener;
	}

	public interface OnResetPressClickListener {
		void onReset();
	}

}
