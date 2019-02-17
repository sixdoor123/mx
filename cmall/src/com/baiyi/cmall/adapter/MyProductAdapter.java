package com.baiyi.cmall.adapter;

import org.json.JSONObject;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.user.merchant.order.adapter.BaseRecycleViewAdapter;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.model.Blm;
import com.baiyi.core.loader.ImageLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 我的产品的适配器
 * 
 * @author sunxy
 * @param <T>
 */
public class MyProductAdapter<T> extends BaseRecycleViewAdapter<T> {

	public MyProductAdapter(Context context) {
		super(context);
	}

	public static class MyViewHolder extends ViewHolder {

		ImageView mImgPic;
		TextView mTxtName;
		TextView mTxtCAS;
		TextView mTxtState;

		public MyViewHolder(View view) {
			super(view);
			mImgPic = (ImageView) view.findViewById(R.id.img_pic);
			mTxtName = (TextView) view.findViewById(R.id.txt_product_name);
			mTxtCAS = (TextView) view.findViewById(R.id.txt_cas);
			mTxtState = (TextView) view.findViewById(R.id.txt_product_state);
		}

	}

	@Override
	public void onInitViewHolder(ViewHolder viewHolder, int position) {
		MyViewHolder holder = (MyViewHolder) viewHolder;
		Blm blm = (Blm) datas.get(position);
		holder.mTxtCAS.setText(blm.getC3());
		holder.mTxtName.setText(blm.getC2());
		holder.mTxtState.setText(blm.getC4());

		loadImg(blm.getC1(), holder.mImgPic);
	}

	@Override
	public ViewHolder reateViewHolder(ViewGroup parent, int viewType) {
		View view = ContextUtil.getLayoutInflater(context)//
				.inflate(R.layout.item_my_product, parent,false);
		MyViewHolder holder = new MyViewHolder(view);
		return holder;
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

		CmallApplication.getImageStrategy().startLoader(loader);

	}
}
