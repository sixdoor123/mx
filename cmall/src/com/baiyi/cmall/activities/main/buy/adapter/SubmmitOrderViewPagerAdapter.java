package com.baiyi.cmall.activities.main.buy.adapter;

import java.util.ArrayList;
import java.util.List;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.buy.adapter.ProductThumbnailAdapter.MyViewHolder;
import com.baiyi.cmall.activities.main.buy.entity.OwmEntity;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.core.loader.ImageLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.tencent.a.a.a.a.h;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import net.sf.json.JSONObject;

/**
 * 提交订单产品列表
 *
 * @author sunxy
 */
public class SubmmitOrderViewPagerAdapter extends BaseAdapter {

	private Context context = null;
	// 产品列表
	private List<OwmEntity> entities = null;

	public SubmmitOrderViewPagerAdapter(Context context) {
		this.context = context;

		if (null == entities) {
			entities = new ArrayList<OwmEntity>();
		}
	}

	@Override
	public int getCount() {
		return entities.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return entities.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView == null) {
			convertView = ContextUtil.getLayoutInflater(context)//
					.inflate(R.layout.product_thumbnail_item, null);

			holder = new ViewHolder();

			holder.imageView = (ImageView) convertView.findViewById(R.id.img_hot_);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		OwmEntity entity = entity = (OwmEntity) entities.get(position);
		loadImg(/*entity.getIp()*/"http://p3.so.qhimg.com/t0157b1888aea3551f8.jpg", holder.imageView);
		return convertView;
	}

	static class ViewHolder {
		ImageView imageView;
	}

	public void setEntities(List<OwmEntity> entities) {
		this.entities = entities;
		notifyDataSetChanged();
	}

	/**
	 * 图片下载
	 * 
	 * @param urlStr
	 * @param view
	 */

	private void loadImg(String urlStr, final ImageView view) {

		ImageLoader loader = new ImageLoader(context, true);
		loader.setUrl(urlStr);
		loader.setPostData(new JSONObject().toString());
		loader.setMethod(BaseNetLoder.Method_Get);
		loader.setLoaderListener(new LoaderListener() {

			@Override
			public void onProgress(Object tag, long curByteNum, long totalByteNum) {
			}

			@Override
			public void onError(Object tag, int responseCode, String errorMessage) {
				Log.d("TT", "errorMessage");
			}

			@Override
			public void onCompelete(Object tag, Object result) {

				BitmapDrawable drawable = (BitmapDrawable) result;
				view.setBackground(drawable);
				Log.d("TT", "---------------onCompelete");

			}
		});

		CmallApplication.getDataStratey().startLoader(loader);
	}
}
