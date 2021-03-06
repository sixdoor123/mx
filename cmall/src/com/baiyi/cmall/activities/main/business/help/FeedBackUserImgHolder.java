/**
 * 
 */
package com.baiyi.cmall.activities.main.business.help;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.main.business.adapter.FeedBackAdapter;
import com.baiyi.cmall.activities.main.business.entity.FeedBackEntity;
import com.baiyi.cmall.activities.main.mall.holder.BaseHolder;
import com.baiyi.cmall.utils.ImageTools;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.util.ContextUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * @author tangkun
 * 
 */
public class FeedBackUserImgHolder extends BaseHolder {

	public TextView txtContent = null;
	public TextView txtTime = null;
	public ImageView imgHead = null;
	public LinearLayout linAddImg = null;
	
	private Context context = null;

	/**
	 * @param view
	 */
	public FeedBackUserImgHolder(View convertView, Context context) {
		super(convertView);
		this.context = context;
		imgHead = (ImageView) convertView.findViewById(R.id.img_head);
		int imgW = BaseActivity.getDensity_int(35);
		int imgH = BaseActivity.getDensity_int(35);
		imgHead.getLayoutParams().width = imgW;
		imgHead.getLayoutParams().height = imgH;

		txtTime = (TextView) convertView.findViewById(R.id.txt_time);
		txtContent = (TextView) convertView.findViewById(R.id.txt_content);
		linAddImg = (LinearLayout) convertView.findViewById(R.id.lin_img_add);
	}


	public void setBitmap(boolean isLoading, String url, ImageView iv,
			boolean isHead) {
		if (imgHead.getVisibility() == View.VISIBLE) {
			// imgHead.setImg(imgHeadUrl, R.drawable.img_feed_system,
			// isLoading);
			getBitmap(url, iv, isHead);
		}

	}

	
	private void getBitmap(final String useUrl, final ImageView iv,
			final boolean isHead) {
		if (!isHead && useUrl.contains(ContextUtil.getSDPath())) {
			displaySDImage(useUrl, iv);
			return;
		}
		
		ImageLoader.getInstance().displayImage(useUrl, iv, new ImageLoadingListener() {

			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				// TODO Auto-generated method stub
				if(arg2 == null)
				{
					return;
				}
				if (isHead) {
					iv.setImageBitmap(ImageTools.setCircleImage(arg2));
				} else {
					iv.setImageBitmap(arg2);
				}
				
			}

			@Override
			public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
				// TODO Auto-generated method stub
				if (isHead) {
					imgHead.setImageDrawable(((BaseActivity) context)
							.getResources().getDrawable(
									R.drawable.img_feed_system));
				}
			}

			@Override
			public void onLoadingStarted(String arg0, View arg1) {
				// TODO Auto-generated method stub
				
			}});

	}

	private void displaySDImage(String url, final ImageView img) {

		com.nostra13.universalimageloader.core.ImageLoader.getInstance()
				.loadImage("file://" + url, new ImageLoadingListener() {

					@Override
					public void onLoadingStarted(String arg0, View arg1) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onLoadingFailed(String arg0, View arg1,
							FailReason arg2) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onLoadingComplete(String arg0, View arg1,
							Bitmap arg2) {
						int imgW = (int) (Config.getInstance().getScreenWidth(context) * 0.5);
						int imgH = (int) (imgW*arg2.getHeight()/arg2.getWidth());
						img.getLayoutParams().width = imgW;
						img.getLayoutParams().height = imgH;
						img.setImageBitmap(arg2);
						
					}

					@Override
					public void onLoadingCancelled(String arg0, View arg1) {
						// TODO Auto-generated method stub

					}
				});
	}
}
