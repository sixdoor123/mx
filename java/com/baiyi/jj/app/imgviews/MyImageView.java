/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.baiyi.jj.app.imgviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.Scroller;

import com.baiyi.jj.app.entity.ImageParamsEntity;
import com.baiyi.jj.app.imgtools.RotateBitmap;
import com.baiyi.jj.app.utils.Vector2f;

/**
 * @author tangkun
 *
 */
public class MyImageView extends ImageView {
	private final static float FRICTION = 500.0f; //摩擦
	private static final String TAG = MyImageView.class.getName();
	protected Matrix mBaseMatrix = new Matrix();
	protected Matrix mSuppMatrix = new Matrix();
	private final Matrix mDisplayMatrix = new Matrix();
	private final float[] mMatrixValues = new float[9];
	protected final RotateBitmap mBitmapDisplayed = new RotateBitmap(null);
	private Scroller mScroller;
	int mThisWidth = -1, mThisHeight = -1;
	public float mSourceScale= Float.NaN;//实际比例 1.0为fit�?	
	private LayoutChangeListner layoutChangeListner;
	private Boolean isHorizatal = null;
	private ImageParamsEntity imageParams = null;

	public ImageParamsEntity getImageParams() {
		return imageParams;
	}
	public Vector2f getSourceImageSize(){
		Vector2f size = new Vector2f();
		size.x = mBitmapDisplayed.getWidth();
		size.y = mBitmapDisplayed.getHeight();
		return size;
	}
	public float getScaleCompareToSource(){
		return getScale()/mSourceScale;
	}
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		mThisWidth = right - left;
		mThisHeight = bottom - top;
		Runnable r = mOnLayoutRunnable;
		if (r != null) {
			mOnLayoutRunnable = null;
			r.run();
		}
		if (mBitmapDisplayed.getBitmap() != null) {
			if(Float.isNaN(mSourceScale)){
				getProperBaseMatrix(mBitmapDisplayed, mBaseMatrix);
				setImageMatrix(getImageViewMatrix());
			}
		}
	}
	public void onImageParamsChanged(ImageParamsEntity params){
		if(params==null){
			return;
		}
		if(imageParams==null){
			return;
		}
		if(!params.getImageScale().equals(imageParams.getImageScale())){
			zoomTo(params.getImageScale()*mSourceScale);
			imageParams.setImageScale(params.getImageScale());
		}
		Vector2f delta = getDeltaXY();
		int dx = (int)(params.getImageX()-delta.x);
		int dy = (int)(params.getImageY()-delta.y);
		if((dx!=0)||(dy!=0)){
			panBy(dx, dy);
			imageParams.setImageX(params.getImageX());
			imageParams.setImageY(params.getImageY());
		}
	}

	public ImageParamsEntity relayoutBitmap(int viewWidth,int viewHeight){
		if (mBitmapDisplayed.getBitmap() != null) {
			
			float w = mBitmapDisplayed.getWidth();
			float h = mBitmapDisplayed.getHeight();
			float fitScale = viewWidth/w;
			if(fitScale>1.5f){
				fitScale = 1.5f;
			}
			float totalScale = fitScale*mSourceScale;
			Vector2f delta = getDeltaXY();
			panBy(-delta.x, -delta.y);
			zoomTo(totalScale);
			float moveX =(viewWidth - w * fitScale) / 2F;
			delta = getDeltaXY();
			moveX -=  delta.x;
			float moveY =  -delta.y;
			if(viewHeight>h*fitScale){	
				panBy(moveX,(viewHeight - h* fitScale) / 2F+moveY);
			}else{
				panBy(moveX,moveY);
			}
			Vector2f delata = getDeltaXY();
			imageParams.setImageX((int)(delata.x));
			imageParams.setImageY((int)(delata.y));
			imageParams.setContainerHeight(viewHeight);
			imageParams.setContainerWidth(viewWidth);
			imageParams.setImageScale(fitScale);
			return imageParams;
		}
		return null;
	}
	private void getProperBaseMatrix(RotateBitmap bitmap, Matrix matrix) {
		float viewWidth = getWidth();
		float viewHeight = getHeight();
		float w = bitmap.getWidth();
		float h = bitmap.getHeight();
		matrix.reset();
		float widthScale = Math.min(viewWidth / w,3.0f);// 3.0f
		float scale = widthScale;//Math.min(widthScale, heightScale);
		matrix.postConcat(bitmap.getRotateMatrix());
		matrix.postScale(scale, scale);
		if(viewHeight>h*scale){
			matrix.postTranslate((viewWidth - w * scale) / 2F,(viewHeight - h* scale) / 2F);
		}else{
			matrix.postTranslate((viewWidth - w * scale) / 2F,0);
		}
		
	}
//	private void onAutoAdjustLayout(){
//		float viewWidth = getWidth();
//		float viewHeight = getHeight();
//		if(!Config.isReadHorizatal){
//			if(viewHeight<viewWidth){
//				float temp = viewWidth;
//				viewWidth = viewHeight;
//				viewHeight = temp;
//			}
//		}else{
//			if(viewHeight>viewWidth){
//				float temp = viewWidth;
//				viewWidth = viewHeight;
//				viewHeight = temp;
//			}
//		}
//		float w = mBitmapDisplayed.getWidth();
//		float h = mBitmapDisplayed.getHeight();
//		float fitScale = viewWidth/w;
//		if(fitScale>1.5f){
//			fitScale = 1.5f;
//		}
//		float totalScale = fitScale*mSourceScale;
////		mFitScale = totalScale;
//		Vector2f delta = getDeltaXY();
//		panBy(-delta.x, -delta.y);
////		zoomToNoCenter(totalScale, 0, 0);
//		zoomTo(totalScale);
//		float moveX =(viewWidth - w * fitScale) / 2F;
//		delta = getDeltaXY();
//		moveX -=  delta.x;
//		float moveY =  -delta.y;
//		if(viewHeight>h*fitScale){	
//			panBy(moveX,(viewHeight - h* fitScale) / 2F+moveY);
//		}else{
//			panBy(moveX,moveY);
//		}
//		if(layoutChangeListner!=null){
//			layoutChangeListner.onLayoutChange();
//		}
//	}
	public int getImageArea(){
		return  mBitmapDisplayed.getWidth()*mBitmapDisplayed.getHeight();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			event.startTracking();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.isTracking()
				&& !event.isCanceled()) {
			if (getScale() > 1.0f) {
				zoomTo(1.0f);
				return true;
			}
		}
		return super.onKeyUp(keyCode, event);
	}
	public void setLayoutChangeListner(LayoutChangeListner layoutChangeListner) {
		this.layoutChangeListner = layoutChangeListner;
	}
	protected Handler mHandler = new Handler();

	@Override
	public void setImageBitmap(Bitmap bitmap) {
		setImageBitmap(bitmap, 0);
	}
	
	private Runnable mOnLayoutRunnable = null;
	private void setImageBitmap(Bitmap bitmap, int rotation) {
		super.setImageBitmap(bitmap);
		Drawable d = getDrawable();
		if (d != null) {
			d.setDither(true);
		}
		Bitmap old = mBitmapDisplayed.getBitmap();
		mBitmapDisplayed.setBitmap(bitmap);
		mBitmapDisplayed.setRotation(rotation);
	}
	public void setImageBitmapResetBase(final Bitmap bitmap,
			final boolean resetSupp) {
		RotateBitmap tempBitmap = new RotateBitmap(bitmap);
		setImageRotateBitmapResetBase(tempBitmap, resetSupp);
		if(bitmap!=null){
			//初始化imageParams
			imageParams = new ImageParamsEntity();
			imageParams.setImageHeight(bitmap.getHeight());
			imageParams.setImageWidth(bitmap.getWidth());
			Vector2f localtion = getDeltaXY();
			imageParams.setImageX((int)localtion.x);
			imageParams.setImageY((int)localtion.y);
			imageParams.setImageScale(1/mSourceScale);
			imageParams.setContainerHeight(getHeight());
			imageParams.setContainerWidth(getWidth());
			if(tempBitmap.getBitmap()==null){
				throw new IllegalArgumentException("bitmap:"+bitmap);
			}
		}
	}

	public void setImageRotateBitmapResetBase(final RotateBitmap bitmap,
			final boolean resetSupp) {
		final int viewWidth = getWidth();
		if (viewWidth <= 0) {
			mOnLayoutRunnable = new Runnable() {
				public void run() {
					setImageRotateBitmapResetBase(bitmap, resetSupp);
				}
			};
			return;
		}

		if (bitmap.getBitmap() != null) {
			getProperBaseMatrix(bitmap, mBaseMatrix);
			setImageBitmap(bitmap.getBitmap(), bitmap.getRotation());
		} else {
			mBaseMatrix.reset();
			setImageBitmap(null);
		}

		if (resetSupp) {
			mSuppMatrix.reset();
		}
		setImageMatrix(getImageViewMatrix());
		mSourceScale = realZoom();
		
	}

	// Center as much as possible in one or both axis. Centering is
	// defined as follows: if the image is scaled down below the
	// view's dimensions then center it (literally). If the image
	// is scaled larger than the view and is translated out of view
	// then translate it back into view (i.e. eliminate black bars).
	protected void center(boolean horizontal, boolean vertical) {
		if (mBitmapDisplayed.getBitmap() == null) {
			return;
		}

		Matrix m = getImageViewMatrix();

		RectF rect = new RectF(0, 0, mBitmapDisplayed.getBitmap().getWidth(),
				mBitmapDisplayed.getBitmap().getHeight());

		m.mapRect(rect);

		float height = rect.height();
		float width = rect.width();

		float deltaX = 0, deltaY = 0;

		if (vertical) {
			int viewHeight = getHeight();
			if (height < viewHeight) {
				deltaY = (viewHeight - height) / 2 - rect.top;
			} else if (rect.top > 0) {
				deltaY = -rect.top;
			} else if (rect.bottom < viewHeight) {
				deltaY = getHeight() - rect.bottom;
			}
		}

		if (horizontal) {
			int viewWidth = getWidth();
			if (width < viewWidth) {
				deltaX = (viewWidth - width) / 2 - rect.left;
			} else if (rect.left > 0) {
				deltaX = -rect.left;
			} else if (rect.right < viewWidth) {
				deltaX = viewWidth - rect.right;
			}
		}

		postTranslate(deltaX, deltaY);
		setImageMatrix(getImageViewMatrix());
	}

	public MyImageView(Context context) {
		super(context);
		init(context);
	}

	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		setScaleType(ImageView.ScaleType.MATRIX);
		mScroller = new Scroller(context);
//		this.getViewTreeObserver().addOnGlobalLayoutListener(new  ViewTreeObserver.OnGlobalLayoutListener() {
//
//			@Override
//			public void onGlobalLayout() {
//				if (mBitmapDisplayed.getBitmap() != null){
//					if(isHorizatal==null){
//						isHorizatal = Config.isReadHorizatal;
//						onAutoAdjustLayout();
//					}else if(!isHorizatal.equals(Config.isReadHorizatal)){
//						onAutoAdjustLayout();
//					}
//				}
//				isHorizatal = Config.isReadHorizatal;
//			}
//			
//		});
	}
	
	protected float getValue(Matrix matrix, int whichValue) {
		matrix.getValues(mMatrixValues);
		return mMatrixValues[whichValue];
	}

	// Get the scale factor out of the matrix.
	protected float getScale(Matrix matrix) {
		return getValue(matrix, Matrix.MSCALE_X);
	}

	public float getScale() {
		return getScale(mSuppMatrix);
	}

	// Setup the base matrix so that the image is centered and scaled properly.
	

	// Combine the base matrix and the supp matrix to make the final matrix.
	protected Matrix getImageViewMatrix() {
		// The final matrix is computed as the concatentation of the base matrix
		// and the supplementary matrix.
		mDisplayMatrix.set(mBaseMatrix);
		mDisplayMatrix.postConcat(mSuppMatrix);
		return mDisplayMatrix;
	}
	private float realZoom(){
		if (mBitmapDisplayed.getBitmap() == null) {
			return 1F;
		}
		float fw = (float) mBitmapDisplayed.getWidth() / (float) mThisWidth;
		return fw;
	}

	protected void zoomTo(float scale, float centerX, float centerY) {
		float oldScale = getScale();
		float deltaScale = scale / oldScale;

		mSuppMatrix.postScale(deltaScale, deltaScale, centerX, centerY);
		setImageMatrix(getImageViewMatrix());
		center(true, true);
	}
	protected Vector2f zoomTo(float scale) {
		float cx = getWidth() / 2F;
		float cy = getHeight() / 2F;

		zoomTo(scale, cx, cy);
		return new Vector2f(cx, cy);
	}

	protected void zoomToPoint(float scale, float pointX, float pointY) {
		float cx = getWidth() / 2F;
		float cy = getHeight() / 2F;

		panBy(cx - pointX, cy - pointY);
		zoomTo(scale, cx, cy);
	}
	private Vector2f getCenter(){
		float cx = getWidth() / 2F;
		float cy = getHeight() / 2F;
		return new Vector2f(cx,cy);
	}
	private float getScaleSpaceToEdge(float scale,float start,float point,float edge){
		float space = point - start;
		float scaleSpace = scale*space;
		float scaleStart = point -scaleSpace;
		return (scaleStart - edge);
	}


	protected void zoomIn(float rate) {
		if (mBitmapDisplayed.getBitmap() == null) {
			return;
		}

		float cx = getWidth() / 2F;
		float cy = getHeight() / 2F;

		mSuppMatrix.postScale(rate, rate, cx, cy);
		setImageMatrix(getImageViewMatrix());
	}

	protected void zoomOut(float rate) {
		if (mBitmapDisplayed.getBitmap() == null) {
			return;
		}

		float cx = getWidth() / 2F;
		float cy = getHeight() / 2F;

		// Zoom out to at most 1x.
		Matrix tmp = new Matrix(mSuppMatrix);
		tmp.postScale(1F / rate, 1F / rate, cx, cy);

		if (getScale(tmp) < 1F) {
			mSuppMatrix.setScale(1F, 1F, cx, cy);
		} else {
			mSuppMatrix.postScale(1F / rate, 1F / rate, cx, cy);
		}
		setImageMatrix(getImageViewMatrix());
		center(true, true);
	}

	protected void postTranslate(float dx, float dy) {
		mSuppMatrix.postTranslate(dx, dy);
	}

	public void panBy(float dx, float dy) {
		postTranslate(dx, dy);
		setImageMatrix(getImageViewMatrix());
	}
	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
		if(mScroller.computeScrollOffset()){
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			if(mScroller.isFinished()){
			}
		}
		
	}
	public void startScrollBy(int dx,int dy,int time){
		mScroller.startScroll(this.getScrollX(), this.getScrollY(), dx, dy,time);
		postInvalidate();
	}
	public void startScrollBy(int dx,int dy){
		int curX = this.getScrollX();
		int curY = this.getScrollY();
		if(!mScroller.isFinished()){
			dx = mScroller.getFinalX()-curX+dx;
			dy = mScroller.getFinalY()-curY+dy;
		}
		mScroller.startScroll(curX, curY, dx, dy);
		postInvalidate();
	}
	public int getDisplayImageHeight(){
		Drawable drawable = this.getDrawable();
		if(drawable==null){
			return 0;
		}
		return (int)(drawable.getBounds().height()*getCurrentScaleY());
	}
	public int getDisplayImageWidth(){
		Drawable drawable = this.getDrawable();
		if(drawable==null){
			return 0;
		}
		return (int)(drawable.getBounds().width()*getCurrentScaleY());
	}
	private float getCurrentScaleY(){
		this.getImageMatrix().getValues(mMatrixValues);
		return mMatrixValues[4];
	}
	public Vector2f getDeltaXY(){
		this.getImageMatrix().getValues(mMatrixValues);
		return new Vector2f(mMatrixValues[2], mMatrixValues[5]);
	}
	public interface LayoutChangeListner{
		public void onLayoutChange();
	}
}
