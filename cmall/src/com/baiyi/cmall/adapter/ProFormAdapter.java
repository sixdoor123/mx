package com.baiyi.cmall.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 我的采购单
 * 
 * @author lizl
 * 
 */
public class ProFormAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<GoodsSourceInfo> datas;

	private LayoutInflater inflater;

	public ProFormAdapter(Context context) {
		this.context = context;

		notifyDataSetChanged();
		inflater = ContextUtil.getLayoutInflater(context);
	}

	@Override
	public int getCount() {
		return datas == null ? 0 : datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_5_view, null);

			holder.formName = (TextView) convertView
					.findViewById(R.id.tv_content1);
			holder.state = (TextView) convertView
					.findViewById(R.id.tv_content2);
			holder.merchantNmae = (TextView) convertView
					.findViewById(R.id.tv_content3);
			holder.numberTextView = (TextView) convertView
					.findViewById(R.id.tv_content4);
			holder.priceInterval = (TextView) convertView
					.findViewById(R.id.tv_content5);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final GoodsSourceInfo info = datas.get(position);
		holder.formName.setText(info.getGoodSName());
		holder.numberTextView.setText(Utils.dealWeight(info.getGoodSWeight())
				+ "吨");
		holder.state.setText(info.getIntentionOrderState());
		holder.merchantNmae.setText(info.getGoodSContactPerson());
		String price = Utils.dealPrice(info.getPrepayment());
		if (price.startsWith(".")) {
			price = "0" + price;
		}
		holder.priceInterval.setText(price + "元");

		return convertView;
	}

	static class ViewHolder {

		TextView formName;// 订单名称
		TextView numberTextView;// 數量
		TextView state;// 状态
		TextView merchantNmae;// 商家名称
		TextView priceInterval;// 价格范围

	}

	public void setData(ArrayList<GoodsSourceInfo> datas) {
		this.datas = new ArrayList<GoodsSourceInfo>();
		this.datas = datas;
		notifyDataSetChanged();
	}

	/**
	 * 添加数据
	 */
	public void addData(ArrayList<GoodsSourceInfo> datas) {
		this.datas.addAll(datas);
		notifyDataSetChanged();
	}
}
