package com.baiyi.jj.app.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * ÊµÏÖÔ²½ÇImageView¿Ø¼þ
 * @author tangkun
 *
 */
public class ImageCorner extends RemoteImageView {
    
    private Path mClipPath = new Path();
    
	public ImageCorner(Context context) {
		super(context);
	}

	public ImageCorner(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ImageCorner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
	    int left = getPaddingLeft();
	    int top = getPaddingTop();
	    int right = getWidth() - getPaddingRight();
	    int bottom = getHeight() - getPaddingBottom();
		mClipPath.addRoundRect(new RectF(left, top, right, bottom), 5.0f, 5.0f, Path.Direction.CW);
		
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.FILTER_BITMAP_FLAG|Paint.ANTI_ALIAS_FLAG));
		canvas.clipPath(mClipPath);

		super.onDraw(canvas);
	}
}
