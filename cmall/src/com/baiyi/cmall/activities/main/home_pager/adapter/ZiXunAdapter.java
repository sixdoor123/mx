package com.baiyi.cmall.activities.main.home_pager.adapter;

import com.baiyi.cmall.activities.main.home_pager.entity.ZiXunEntity;
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
 * 资讯适配器
 * 
 * @author lizl
 * 
 */
public class ZiXunAdapter extends BaseRCAdapter<ZiXunEntity> {

	public ZiXunAdapter(Context context) {
		super(context);
	}

	@Override
	public int getItemCount() {
		return objects.size();
	}

	@Override
	public ViewHolder onCreateVH(ViewGroup parent, int viewType) {

		View view = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.item_zixun, parent, false);
		return new ZiXunView(view);
	}

	@Override
	public void onBindVH(ViewHolder holder, int position) {
		final ZiXunEntity info = (ZiXunEntity) objects.get(position);
		((ZiXunView) holder).mTvTitle.setText(info.getTitle() + ":");
		// ((ZiXunView) holder).mTvType.setText(info.getType());
		// ((ZiXunView) holder).mTvName.setText(info.getName());
		// ((ZiXunView) holder).mTvTime.setText(info.getTime());

	}

	public static class ZiXunView extends RecyclerView.ViewHolder {

		TextView mTvTitle;// 题目
		TextView mTvType;// 类型
		TextView mTvName;// 名字
		TextView mTvTime;// 时间

		public ZiXunView(View itemView) {
			super(itemView);
			mTvTitle = (TextView) itemView.findViewById(R.id.tv_zixun_title);
			mTvType = (TextView) itemView.findViewById(R.id.tv_zixun_type);
			mTvName = (TextView) itemView.findViewById(R.id.tv_zixun_name);
			mTvTime = (TextView) itemView.findViewById(R.id.tv_zixun_time);

		}

	}
}
