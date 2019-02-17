package com.baiyi.cmall.activities.main.home_pager.adapter;

import com.baiyi.cmall.activities.user.buyer.adapter.BaseRCAdapter;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.R;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 限时抢购适配器
 * 
 * @author lizl
 * 
 */
public class FlashCaleAdapter extends BaseRCAdapter<GoodsSourceInfo> {

	public FlashCaleAdapter(Context context) {
		super(context);
	}

	@Override
	public ViewHolder onCreateVH(ViewGroup parent, int viewType) {

		View view = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.item_flash_cale, parent, false);
		return new FlashView(view);
	}

	@Override
	public void onBindVH(ViewHolder holder, int position) {
		final GoodsSourceInfo info = (GoodsSourceInfo) objects.get(position);
		((FlashView) holder).mTvTitle.setText(info.getTitle());
		((FlashView) holder).mTvContent.setText(info.getGoodSContent());
		((FlashView) holder).mTvCompany.setText(info.getCompanyName());
		((FlashView) holder).mTvCity.setText(info.getCity());
		((FlashView) holder).mTvPrice.setText(info.getPrice());

	}

	public static class FlashView extends RecyclerView.ViewHolder {

		TextView mTvTitle;// 题目
		TextView mTvContent;// 内容
		TextView mTvCompany;// 公司
		TextView mTvCity;// 城市
		TextView mTvPrice;// 价钱

		public FlashView(View itemView) {
			super(itemView);
			mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
			mTvContent = (TextView) itemView.findViewById(R.id.tv_content);
			mTvCompany = (TextView) itemView.findViewById(R.id.tv_company);
			mTvCity = (TextView) itemView.findViewById(R.id.tv_city);
			mTvPrice = (TextView) itemView.findViewById(R.id.tv_price);

		}

	}
}
