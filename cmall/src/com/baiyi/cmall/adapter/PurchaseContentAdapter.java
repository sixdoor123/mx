package com.baiyi.cmall.adapter;

import java.util.ArrayList;

import com.baiyi.cmall.entity.GoodSOriginInfo;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 
 * 我的供应单的适配器
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-15 上午10:35:01
 */
public class PurchaseContentAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<GoodsSourceInfo> datas;

	private LayoutInflater inflater;

	public PurchaseContentAdapter(Context context) {
		this.context = context;

		inflater = ContextUtil.getLayoutInflater(context);
	}

	@Override
	public int getCount() {
		return datas == null ? 0 : datas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = inflater.inflate(
					R.layout.item_5_view, null);

			holder.merchantName = (TextView) convertView
					.findViewById(R.id.tv_content1);
			holder.orderState = (TextView) convertView
					.findViewById(R.id.tv_content2);
			holder.areaTextView = (TextView) convertView
					.findViewById(R.id.tv_content3);
			holder.weighTextView = (TextView) convertView
					.findViewById(R.id.tv_content4);
			holder.price = (TextView) convertView
					.findViewById(R.id.tv_content5);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		GoodsSourceInfo info = datas.get(position);

		holder.merchantName.setText(info.getPurchasename() + "("
				+ info.getCompanyName() + ")");
		holder.areaTextView.setText(info.getDeliveryplacename());
		holder.weighTextView.setText(info.getInventory() + "吨");
		holder.orderState.setText(info.getIntentionstatename());
		holder.price.setText(Utils.twoDecimals(info.getPrice()) + "元/吨");

		return convertView;
	}

	static class ViewHolder {
		TextView merchantName;
		TextView areaTextView;
		TextView weighTextView;
		TextView orderState;
		TextView price;
	}

	public void setData(ArrayList<GoodsSourceInfo> datas) {
		if (null != datas) {
			this.datas = datas;
		} else {
			this.datas = new ArrayList<GoodsSourceInfo>();
		}
		notifyDataSetChanged();
	}
}
