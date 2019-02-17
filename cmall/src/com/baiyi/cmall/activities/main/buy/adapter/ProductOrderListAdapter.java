package com.baiyi.cmall.activities.main.buy.adapter;

import java.util.List;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.buy.entity.OwmEntity;
import com.baiyi.cmall.activities.main.mall.entity.PnvmlEntity;
import com.baiyi.cmall.activities.user.merchant.order.adapter.BaseRecycleViewAdapter;
import com.baiyi.cmall.application.CmallApplication;
import com.baiyi.core.loader.ImageLoader;
import com.baiyi.core.loader.Loader.LoaderListener;
import com.baiyi.core.loader.net.BaseNetLoder;
import com.baiyi.core.util.ContextUtil;
import com.tencent.a.a.a.a.h;
import com.tencent.connect.avatar.b;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import net.sf.json.JSONObject;

/**
 * ��Ʒ�����б�
 * 
 * @author sunxy
 * @param <T>
 */
public class ProductOrderListAdapter<T> extends BaseRecycleViewAdapter<T> {

	public ProductOrderListAdapter(Context context) {
		super(context);
	}

	@Override
	public void onInitViewHolder(ViewHolder viewHolder, int position) {
		OwmEntity entity = (OwmEntity) datas.get(position);

		MyViewHolder holder = (MyViewHolder) viewHolder;

		holder.mTvGoodName.setText(entity.getPn());
		holder.mTvNorms.setText(getNorms(entity.getEntities()));
		holder.mTvPrice.setText(entity.getPr()+"Ԫ/��");
		holder.mTvWeight.setText(entity.getPc()+"��");
		holder.mTvInventory.setText(getInventory(entity.getPc(),entity.getPr()));

		loadImg(entity.getIp(), holder.mImageView);
	}

	private CharSequence getInventory(String pc, String pr) {
		int num  = Integer.parseInt(pc);
		double  price = Double.parseDouble(pr);
		return (num*price)+"Ԫ";
	}

	@Override
	public ViewHolder reateViewHolder(ViewGroup parent, int viewType) {
		View view = ContextUtil.getLayoutInflater(context)//
				.inflate(R.layout.product_order_list_item, parent, false);
		MyViewHolder holder = new MyViewHolder(view);
		return holder;
	}

	static class MyViewHolder extends RecyclerView.ViewHolder {

		ImageView mImageView;
		// ��Ʒ����
		TextView mTvGoodName;
		// ���
		TextView mTvNorms;
		// ����
		TextView mTvWeight;
		// ����
		TextView mTvPrice;
		// ���
		TextView mTvInventory;

		public MyViewHolder(View viewItem) {
			super(viewItem);
			mImageView = (ImageView) viewItem.findViewById(R.id.pic);
			// ��Ʒ����
			mTvGoodName = (TextView) viewItem.findViewById(R.id.tv_good_name);
			// ���
			mTvNorms = (TextView) viewItem.findViewById(R.id.tv_guige);
			// ����
			mTvWeight = (TextView) viewItem.findViewById(R.id.tv_weight);
			// �۸�
			mTvPrice = (TextView) viewItem.findViewById(R.id.tv_price);
			// �ܼ۸�
			mTvInventory = (TextView) viewItem.findViewById(R.id.tv_inventory);
		}
	}

	/**
	 * ���
	 * 
	 * @param entities
	 * @return
	 */
	private String getNorms(List<PnvmlEntity> entities) {
		StringBuilder builder = new StringBuilder();

		for (PnvmlEntity pnvmlEntity : entities) {
			builder.append(pnvmlEntity.getNn()).append(":").append(pnvmlEntity.getNv()).append(" ");
		}

		return builder.toString();
	}

	/**
	 * ͼƬ����
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
