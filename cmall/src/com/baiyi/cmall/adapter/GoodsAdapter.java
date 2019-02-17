package com.baiyi.cmall.adapter;

import java.util.ArrayList;

import com.baiyi.cmall.entity.GoodSOriginInfo;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 货源的适配器
 * 
 * @author sunxy
 * 
 */
public class GoodsAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<GoodSOriginInfo> datas;

	private LayoutInflater inflater;

	public GoodsAdapter(Context context, ArrayList<GoodSOriginInfo> datas) {
		this.context = context;
		if (null == datas) {
			this.datas = new ArrayList<GoodSOriginInfo>();
		} else {
			this.datas = datas;
		}
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_4_view, null);

			holder.goodSName = (TextView) convertView
					.findViewById(R.id.txt_content1);
			holder.specification = (TextView) convertView
					.findViewById(R.id.txt_content2);
			holder.weightTextView = (TextView) convertView
					.findViewById(R.id.txt_content3);
			holder.priceTextView = (TextView) convertView
					.findViewById(R.id.txt_content4);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final GoodSOriginInfo info = datas.get(position);
		holder.goodSName.setText(info.getOfferName());
		String price = Utils.dealPrice(info.getPrice());
		if (price.startsWith(".")) {
			price = "0" + price;
		}
		holder.priceTextView.setText(price + "元/吨");
		// Html.fromHtml("<font color=\"red\">*</font>"+info.getCategoryName())
		holder.specification.setText(info.getCategoryName());
		holder.weightTextView.setText(Utils.getWeight(info.getInventory())
				+ "吨");

		return convertView;
	}

	static class ViewHolder {
		TextView goodSName;
		TextView specification;
		TextView priceTextView;
		TextView weightTextView;
	}

	public void setData(ArrayList<GoodSOriginInfo> datas) {
		if (Utils.isStringEmpty(datas)) {
			datas = new ArrayList<GoodSOriginInfo>();
		}
		this.datas.clear();
		this.datas.addAll(datas);
		notifyDataSetChanged();
	}

	/**
	 * 添加数据
	 */
	public void addData(ArrayList<GoodSOriginInfo> datas) {
		this.datas.addAll(datas);
		notifyDataSetChanged();
	}
}
