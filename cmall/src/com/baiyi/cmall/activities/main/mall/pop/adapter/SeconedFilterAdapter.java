package com.baiyi.cmall.activities.main.mall.pop.adapter;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.user.merchant.order.adapter.BaseRecycleViewAdapter;
import com.baiyi.cmall.model.Ftvm;
import com.baiyi.core.util.ContextUtil;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 筛选二级分类
 * 
 * @author sunxy
 * @param <T>
 */
public class SeconedFilterAdapter<T> extends BaseRecycleViewAdapter<T> {

	public SeconedFilterAdapter(Context context) {
		super(context);
	}

	@Override
	public void onInitViewHolder(ViewHolder viewHolder, int position) {

		MyViewHolder holder = (MyViewHolder) viewHolder;

		Ftvm ftvm = (Ftvm) datas.get(position);

		if (ftvm.isSelected()) {
			holder.mImageView.setVisibility(View.VISIBLE);
			holder.mTextView.setTextColor(context.getResources().getColor(R.color.bg_buyer));
		} else {
			holder.mImageView.setVisibility(View.GONE);
			holder.mTextView.setTextColor(context.getResources().getColor(R.color.bg_black));
		}

		holder.mTextView.setText(ftvm.getDs());

	}

	@Override
	public ViewHolder reateViewHolder(ViewGroup parent, int viewType) {
		View view = ContextUtil.getLayoutInflater(context)//
				.inflate(R.layout.seconed_category_item, parent, false);
		MyViewHolder holder = new MyViewHolder(view);
		return holder;
	}

	static class MyViewHolder extends ViewHolder {

		ImageView mImageView;
		TextView mTextView;

		public MyViewHolder(View itemView) {
			super(itemView);

			mImageView = (ImageView) itemView.findViewById(R.id.img_select);
			mTextView = (TextView) itemView.findViewById(R.id.txt_seconed_name);

		}
	}

}
