package com.baiyi.jj.app.imgviews;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.RelativeLayout;

import com.baiyi.jj.app.entity.ImageParamsEntity;
import com.baiyi.jj.app.entity.PicEntity;
import com.baiyi.jj.app.views.MyLoadingBar;

/**
 * @author tangkun
 *
 */
public class ImageContainerView extends RelativeLayout {
	private static int FILL_PARENT = LayoutParams.FILL_PARENT;
	private static int WRAP_CONTENT = LayoutParams.WRAP_CONTENT;	
	private MyImageView imageView;
	private MyLoadingBar progressBarContainer;
	private PicEntity entity = null;
	private Bitmap bitmap = null;
	private boolean isOnViewPort = false;
	private OnImageDisplayListener onImageDisplayListener = null;
	private OnClickRefreshListener onClickRefreshListener = null;
	private MyRefreshClickListener refreshClickListener = new MyRefreshClickListener();
	public ImageContainerView(Context context) {
		super(context);
		imageView = new MyImageView(context);
		RelativeLayout.LayoutParams layout = new LayoutParams(FILL_PARENT, FILL_PARENT);
		addView(imageView, layout);
		initProgress();
		initListner();
		
	}
	private void initListner(){
		imageView.setLayoutChangeListner(new MyImageView.LayoutChangeListner() {
			
			@Override
			public void onLayoutChange() {
				
			}
		});
	}
	private void initProgress(){
		
		progressBarContainer = new MyLoadingBar(getContext());
		RelativeLayout.LayoutParams layout = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
		layout.addRule(CENTER_IN_PARENT);
		this.addView(progressBarContainer,layout);
		progressBarContainer.setVisibility(View.GONE);
		progressBarContainer.setOnClickListener(refreshClickListener);
	}
	public void onImageParamsChanged(ImageParamsEntity params){
		imageView.onImageParamsChanged(params);
	}
	public Bitmap getBitmap(){
		return bitmap;
	}
	public void freshImage(){
		imageView.postInvalidate();
	}
	public void setOnClickRefreshListener(
			OnClickRefreshListener onClickRefreshListener) {
		this.onClickRefreshListener = onClickRefreshListener;
	}
	public void endLoad(boolean isRight,String msg){
		if(progressBarContainer!=null){
			if(isRight){
				progressBarContainer.setVisibility(View.GONE);
			}else{
				progressBarContainer.setProgressLoadError(msg, MyLoadingBar.type_sample);
			}
		}
	}
	public void loading(int percent){
		progressBarContainer.setPercent(percent);
	}
	
	public boolean canFling(int flingWidth){
		if(imageView.getDisplayImageWidth()>flingWidth){
			return false;
		}else{
			return true;
		}
	}
	
	public Integer getImageId() {
		return entity.getId();
	}
	
	public void setOnComicImageDisplayListener(OnImageDisplayListener onImageDisplayListener) {
		this.onImageDisplayListener = onImageDisplayListener;
	}
	private void checkImageDisplayListener(){
		if(isOnViewPort){
			if(this.bitmap!=null){
				if(onImageDisplayListener!=null){
					ImageParamsEntity imageParams = imageView.getImageParams();
					if(imageParams==null){
						throw new IllegalArgumentException("image params not init");
					}
					onImageDisplayListener.onImageDisplay(entity, imageParams);
				}
			}
		}
	}
	public ImageParamsEntity relayoutBitmap(int width,int height){
		if(this.bitmap!=null){
			return imageView.relayoutBitmap(width,height);
		}
		return null;
	}
	public void setImageData(Bitmap bitmap){
		imageView.setImageBitmapResetBase(bitmap,true);
		if(bitmap==null){
			if(this.bitmap!=null){
				this.bitmap.recycle();
				this.bitmap = null;
			}
		}else{
			this.bitmap = bitmap;
			checkImageDisplayListener();
		}	
	}
	public void onEnterViewPort(){
		if(isOnViewPort){
			return;
		}
		isOnViewPort = true;
		checkImageDisplayListener();
	}
	public void onExitViewPort(){
		if(!isOnViewPort){
			return;
		}
		isOnViewPort = false;
	}
	public boolean isComicImageExists(){
		if(bitmap!=null){
			return true;
		}
		return false;
	}
	public void setImage(PicEntity entity){
		if(entity!=null){
			this.entity  = entity;
			setImageData(null);
			progressBarContainer.reset();
			progressBarContainer.setVisibility(View.VISIBLE);
		}else{
			this.entity = null;
			this.setImageData(null);
		}
	}
	public String getImageUrl(){
		if(entity!=null){
			return entity.getProjectPath();
		}
		return null;
	}
	private class MyRefreshClickListener implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			if(onClickRefreshListener!=null){
				onClickRefreshListener.onClickRefresh(ImageContainerView.this, entity);
			}
		}
		
	}
	public interface OnImageDisplayListener{
		public void onImageDisplay(PicEntity entity,ImageParamsEntity imageParams);
	}
	public interface OnClickRefreshListener{
		public void onClickRefresh(ImageContainerView view,PicEntity entity);
	}
}
