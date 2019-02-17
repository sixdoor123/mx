package com.baiyi.jj.app.adapter;

import java.util.List;

import com.baiyi.core.loader.ImageLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.jj.app.activity.BaseActivity;
import com.baiyi.jj.app.application.CmsApplication;
import com.baiyi.jj.app.imgtools.GlideTool;
import com.baiyi.jj.app.theme.ThemeUtil;
import com.baiyi.jj.app.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PhotoPagerAdapter extends PagerAdapter {

	/**
	 *
	 * @author tangkun
	 *
	 */
	private final static String TAG = PageViewAdapter.class.getSimpleName();
	private Context context = null;
	private List<String> imgList = null;
	private boolean isGlide = true;

	public PhotoPagerAdapter(Context context,List<String> ImgList,boolean isGlide) {
		this.context = context;
		this.imgList = ImgList;
		this.isGlide = isGlide;
	}

	@Override
	public void finishUpdate(View arg0) {
	}

	@Override
	public int getCount() {
		return imgList.size();
	}

	@Override
	public Object instantiateItem(ViewGroup view, int index) {
//		View pageView = (View) mListViews.get(index);
//		if (pageView.getParent() == null) {
//			((ViewPager) view).addView((View) mListViews.get(index),
//					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//		}
//		return mListViews.get(index);

		PhotoView photoView = new PhotoView(context);

		photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
			@Override
			public void onViewTap(View view, float x, float y) {
				((BaseActivity)context).setExitSwichLayout();
			}
		});
		getImage(context,imgList.get(index), photoView);
		// Now just add PhotoView to ViewPager and return it
		view.addView(photoView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);

		return photoView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {

	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {

	}

	public void getImage(Context context, String imgUrl, final ImageView imageView) {
		if (Utils.isStringEmpty(imgUrl)) {
			imageView.setImageResource(ThemeUtil.getAppThemeBgImg());
			return;
		}
//		if (!isGlide){
//			GlideTool.loadProgressJpg2(context,imgUrl,imageView);
//		}else {
					Glide.with(context).load(imgUrl).asBitmap()
				.error(ThemeUtil.getAppThemeBgImg())
				.placeholder(ThemeUtil.getAppThemeBgImg())
				.centerCrop()
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				.into(new SimpleTarget<Bitmap>() {
					@Override
					public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//						int width = resource.getWidth();
//						int height = resource.getHeight();
//						double percentH = (double) height/(double) width;
//						imageView.getLayoutParams().height = (int)(imageView.getWidth()*percentH);
						imageView.setImageBitmap(resource);

					}
				});
//		}


	}
//	private void getBitmap(final String useUrl, final PhotoView iv) {
//		if (Utils.isStringEmpty(useUrl)) {
//			return;
//		}
////		GlideTool.getListImage(context,useUrl,iv);
//
////		ImageLoader loader = new ImageLoader(context, imgHead,
////				R.drawable.img_feed_system);
//		ImageLoader loader = new ImageLoader(context, false);
//		if (Utils.isStringEmpty(useUrl)) {
//			return;
//		}
//
//
//		loader.setCache(CmsApplication.getImageCoverCache());
//		loader.setUrl(useUrl);
//		loader.setMethod(BaseNetLoder.Method_Get);
//		loader.setLoaderListener(new LoaderListener() {
//
//			public void onProgress(Object tag, long curByteNum,
//					long totalByteNum) {
//			}
//
//			public void onError(Object tag, int responseCode,
//					String errorMessage) {
//
//			}
//
//			public void onCompelete(Object tag, Object result) {
//				// TODO Auto-generated method stub
//				if (result == null) {
//					return;
//				}
//				BitmapDrawable bm = (BitmapDrawable) result;
//				iv.setImageBitmap(bm.getBitmap());
//
//			}
//		});
//		CmsApplication.getImageStrategy().startLoader(loader);
//	}

}
