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
 * ��ʱ����������
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

		TextView mTvTitle;// ��Ŀ
		TextView mTvContent;// ����
		TextView mTvCompany;// ��˾
		TextView mTvCity;// ����
		TextView mTvPrice;// ��Ǯ

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
