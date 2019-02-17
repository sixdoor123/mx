package com.baiyi.cmall.adapter;

import com.baiyi.cmall.activities.user.buyer.adapter.BaseRCAdapter;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 采购
 * 
 * @author lizl
 * 
 */
public class PurchaseAdapter extends BaseRCAdapter<GoodsSourceInfo> {

	public PurchaseAdapter(Context context) {
		super(context);
	}

	@Override
	public ViewHolder onCreateVH(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.item_4_view, parent, false);
		return new PurView(view);
	}

	@Override
	public void onBindVH(ViewHolder holder, int position) {

		final GoodsSourceInfo info = objects.get(position);
		((PurView) holder).purchaseContent.setText(info.getGoodSName());
		((PurView) holder).txtCategory.setText(info.getGoodSCategory());
		((PurView) holder).weightTextView.setText(Utils.dealWeight(info
				.getGoodSWeight()) + "吨");
		((PurView) holder).priceTextView.setText(Utils.dealPrice(info
				.getGoodSPrePrice() + "")
				+ "元/吨");
	}

	public static class PurView extends RecyclerView.ViewHolder {

		TextView purchaseContent;// 详细内容
		TextView txtCategory;// 分类
		TextView weightTextView;// 数量
		TextView priceTextView;// 价格区间

		public PurView(View itemView) {
			super(itemView);
			purchaseContent = (TextView) itemView
					.findViewById(R.id.txt_content1);
			txtCategory = (TextView) itemView.findViewById(R.id.txt_content2);
			weightTextView = (TextView) itemView
					.findViewById(R.id.txt_content3);
			priceTextView = (TextView) itemView.findViewById(R.id.txt_content4);

		}

	}

}
