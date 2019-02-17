package com.baiyi.cmall.activities.user.merchant.order.product.adapter;

import org.json.JSONArray;
import org.json.JSONException;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.home_pager.adapter.HotBusinessAdapter.ViewHold;
import com.baiyi.cmall.activities.user.merchant.order.adapter.BaseRecycleViewAdapter;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.cmall.model.Pdm;
import com.baiyi.core.loader.ImageLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import net.sf.json.JSONObject;

/**
 * 产品订单性情
 * 
 * @author sunxy
 * @param <T>
 */
public class ProductOrderDetailAdapter<T> extends BaseRecycleViewAdapter<T> {

	public ProductOrderDetailAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onInitViewHolder(ViewHolder viewHolder, int position) {
		MyViewHolder holder = (MyViewHolder) viewHolder;
		Pdm pdm = (Pdm) datas.get(position);
		holder.mTvGoodsName.setText(pdm.getPn());
		StringBuilder builder = new StringBuilder();
		JSONArray array = pdm.getGgl();
		for (int i = 0; i < array.length(); i++) {
			try {
				builder.append(array.getString(i)).append("\n");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		holder.mTvGuiGe.setText(builder.toString());
		holder.mTvWeight.setText(pdm.getCo());
		holder.mTvPrice.setText(pdm.getPr());
		holder.mTvAllPrice.setText(pdm.getAm());

		// 图片
		loadImg(pdm.getPurl(), holder.icon);
	}

	@Override
	public ViewHolder reateViewHolder(ViewGroup parent, int viewType) {
		View view = ContextUtil.getLayoutInflater(context)//
				.inflate(com.baiyi.cmall.R.layout.product_order_detail_item,
				parent, false);
		MyViewHolder holder = new MyViewHolder(view);
		return holder;
	}

	static class MyViewHolder extends ViewHolder {
		ImageView icon;
		// 商品名称
		TextView mTvGoodsName;
		// 规格
		TextView mTvGuiGe;
		// 数量
		TextView mTvWeight;
		// 价格
		TextView mTvPrice;
		// 总价格
		TextView mTvAllPrice;

		public MyViewHolder(View viewItem) {
			super(viewItem);
			icon = (ImageView) viewItem.findViewById(R.id.pic);
			// 商品名称
			mTvGoodsName = (TextView) viewItem.findViewById(R.id.tv_good_name);
			// 规格
			mTvGuiGe = (TextView) viewItem.findViewById(R.id.tv_guige);
			// 数量
			mTvWeight = (TextView) viewItem.findViewById(R.id.tv_weight);
			// 价格
			mTvPrice = (TextView) viewItem.findViewById(R.id.tv_price);
			// 总价格
			mTvAllPrice = (TextView) viewItem.findViewById(R.id.tv_all_price);
			
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

		CmallApplication.getImageStrategy().startLoader(loader);
	}
}
