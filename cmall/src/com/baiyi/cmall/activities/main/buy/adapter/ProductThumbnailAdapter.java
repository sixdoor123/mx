package com.baiyi.cmall.activities.main.buy.adapter;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.buy.ShoppingCarActivity;
import com.baiyi.cmall.activities.main.buy.entity.OwmEntity;
import com.baiyi.cmall.activities.user.merchant.order.adapter.BaseRecycleViewAdapter;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.utils.IntentClassChangeUtils;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.loader.ImageLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.tencent.a.a.a.a.h;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import net.sf.json.JSONObject;

/**
 *	提交订单产品列表
 * @author sunxy
 * @param <T>
 */
public class ProductThumbnailAdapter<T> extends BaseRecycleViewAdapter<T> {

	public ProductThumbnailAdapter(Context context) {
		super(context);
	}

	@Override
	public int getItemCount() {
		if (Utils.isStringEmpty(datas)) {
			return 0;
		}
//		if (datas.size() == 1) {
//			return 2;
//		}
//		if (datas.size() >= 2) {
//			return 3;
//		}
		return datas.size();
	}

	@Override
	public void onInitViewHolder(ViewHolder viewHolder, int position) {
		OwmEntity entity = null;
		MyViewHolder holder = (MyViewHolder) viewHolder;

				entity = (OwmEntity) datas.get(position);
				loadImg(entity.getIp(), holder.imageView);
	
	}

	@Override
	public ViewHolder reateViewHolder(ViewGroup parent, int viewType) {
		View convertView = ContextUtil.getLayoutInflater(context)//
				.inflate(R.layout.product_thumbnail_item, parent, false);
		MyViewHolder holder = new MyViewHolder(convertView);
		return holder;
	}

	static class MyViewHolder extends RecyclerView.ViewHolder {
		ImageView imageView;
		TextView textView;
		LinearLayout mLlMore;

		public MyViewHolder(View convertView) {
			super(convertView);
			imageView = (ImageView) convertView.findViewById(R.id.img_hot);
			textView = (TextView) convertView.findViewById(R.id.tv_company_name);
			mLlMore = (LinearLayout) convertView.findViewById(R.id.ll_more);
		}
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
				Log.d("TT", "onCompelete");

			}
		});

		CmallApplication.getDataStratey().startLoader(loader);
	}

}
