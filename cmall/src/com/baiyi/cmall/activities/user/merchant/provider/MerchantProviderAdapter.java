package com.baiyi.cmall.activities.user.merchant.provider;

import java.util.ArrayList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.utils.DataUtils;
import com.baiyi.cmall.views.itemview.PurchaseContentView;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.user.merchant.order.adapter.BaseRecycleViewAdapter;

/**
 * 我是供应商-我的供应的ViewPager基类的适配器
 * 
 * @author sunxy
 * @param <T>
 * @label You can go as far as you want to go.
 * @date 2015-12-18 上午10:49:37
 */
public class MerchantProviderAdapter<T> extends BaseRecycleViewAdapter<T> {

	private Context context;
	// 布局解析器
	private LayoutInflater inflater;

	// 显示数据的条目
	private MerchantProviderItem item;

	// 上下文
	public MerchantProviderAdapter(Context context) {
		super(context);
		this.context = context;
		inflater = ContextUtil.getLayoutInflater(context);
	}

	//
	// @Override
	// public int getCount() {
	// return null != datas ? datas.size() : 0;
	// }
	//
	// @Override
	// public Object getItem(int position) {
	// return datas.get(position);
	// }
	//
	// @Override
	// public long getItemId(int position) {
	// return position;
	// }
	//
	// @Override
	// public View getView(int position, View convertView, ViewGroup parent) {
	// ViewHolder holder = null;
	// if (convertView == null) {
	// holder = new ViewHolder();
	// convertView = inflater.inflate(R.layout.order_list, null);
	//
	// holder.mGroup = (LinearLayout) convertView.findViewById(R.id.group);
	//
	// convertView.setTag(holder);
	// } else {
	// holder = (ViewHolder) convertView.getTag();
	// }
	//
	// holder.mGroup.removeAllViews();
	// GoodsSourceInfo info = datas.get(position);
	//
	// PurchaseContentView contentView = new PurchaseContentView(context);
	// contentView.setId(position);
	// contentView.setViewData(info, position);
	// holder.mGroup.addView(contentView);
	//
	// ArrayList<GoodsSourceInfo> purInfos = info.getPurInfos();
	// if (purInfos.size() >= 3) {
	// for (int i = 0; i < 3; i++) {
	// item = new MerchantProviderItem(context);
	// item.setId(i);
	// item.setDisplay(purInfos.get(i));
	// holder.mGroup.addView(item);
	// }
	// } else {
	// for (int i = 0; i < purInfos.size(); i++) {
	// item = new MerchantProviderItem(context);
	// item.setId(i);
	// item.setDisplay(purInfos.get(i));
	// holder.mGroup.addView(item);
	// }
	// }
	//
	// return convertView;
	// }
	//
	// static class ViewHolder {
	// TextView mName;
	// TextView mWeight;
	// LinearLayout mGroup;
	// LinearLayout mBtn;
	// }
	//
	// /**
	// * 设置数据 FIXME 数据分类
	// *
	// * @param datas
	// */
	// public void setData(ArrayList<GoodsSourceInfo> datas) {
	// if (datas != null && datas.size() > 0) {
	// this.datas.clear();
	// this.datas = datas;
	// notifyDataSetChanged();
	// }
	// }
	//
	// /**
	// * 添加数据 FIXME 数据分类
	// *
	// * @param datas
	// */
	// public void addData(ArrayList<GoodsSourceInfo> datas) {
	// this.datas.addAll(datas);
	// notifyDataSetChanged();
	// }

	@Override
	public void onInitViewHolder(android.support.v7.widget.RecyclerView.ViewHolder viewHolder, int position) {
		MyViewHolder holder = (MyViewHolder) viewHolder;
		holder.mGroup.removeAllViews();
		GoodsSourceInfo info = (GoodsSourceInfo) datas.get(position);

		PurchaseContentView contentView = new PurchaseContentView(context);
		contentView.setId(position);
		contentView.setViewData(info, position);
		holder.mGroup.addView(contentView);

		ArrayList<GoodsSourceInfo> purInfos = info.getPurInfos();
		if (purInfos.size() >= 3) {
			for (int i = 0; i < 3; i++) {
				item = new MerchantProviderItem(context);
				item.setId(i);
				item.setDisplay(purInfos.get(i));
				holder.mGroup.addView(item);
			}
		} else {
			for (int i = 0; i < purInfos.size(); i++) {
				item = new MerchantProviderItem(context);
				item.setId(i);
				item.setDisplay(purInfos.get(i));
				holder.mGroup.addView(item);
			}
		}
	}

	@Override
	public android.support.v7.widget.RecyclerView.ViewHolder reateViewHolder(ViewGroup parent, int viewType) {
		View view = inflater.inflate(R.layout.order_list, parent, false);
		MyViewHolder holder = new MyViewHolder(view);
		return holder;
	}

	public static class MyViewHolder extends RecyclerView.ViewHolder {
		LinearLayout mGroup;

		public MyViewHolder(View itemView) {
			super(itemView);
			mGroup = (LinearLayout) itemView.findViewById(R.id.group);
		}
	}
}
