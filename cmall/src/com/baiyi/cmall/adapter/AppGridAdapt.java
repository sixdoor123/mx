package com.baiyi.cmall.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

import com.baiyi.cmall.entity.OrderEntity;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.R;

/**
 * 发票类型 适配器
 * 
 * @author lizl
 *
 */
public class AppGridAdapt extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
	private ArrayList<OrderEntity> datas;
	private OrderEntity entity;
	public int selected = -1;

	public AppGridAdapt(Context context, ArrayList<OrderEntity> datas, OrderEntity entity) {
		this.context = context;
		this.datas = datas;
		this.entity = entity;
		inflater = LayoutInflater.from(context);
		selected = getSelectPosition();
	}

	@Override
	public int getCount() {
		return Utils.isStringEmpty(datas)?0:datas.size();
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
		// 每个条目的数据
		OrderEntity orderEntity = datas.get(position);

		ViewHolder holder = null;
		if (null == convertView) {
			convertView = inflater.inflate(R.layout.item_grid_app, null);
			holder = new ViewHolder();
			holder.imageView = (ImageView) convertView.findViewById(R.id.img_icon);
			holder.textView = (TextView) convertView.findViewById(R.id.tv_icon_text);
			holder.mLlItemLayout = (LinearLayout) convertView.findViewById(R.id.ll_item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textView.setText(orderEntity.getInvoicetypename());
		Log.d("TAG", position + "---TAG---" + selected + "------" + datas.size());
		if (position == selected) {
			holder.mLlItemLayout.setActivated(true);
			holder.imageView.setActivated(true);
			holder.textView.setActivated(true);
			if (0 != selected) {
				selected = Integer.MAX_VALUE;
			}
		} else {
			holder.mLlItemLayout.setActivated(false);
			holder.imageView.setActivated(false);
			holder.textView.setActivated(false);
			if (null != listener) {
				listener.onPress(holder.mLlItemLayout, holder.imageView, holder.textView, position);
			}
		}

		return convertView;
	}

	/**
	 * 判断当前默认哪个发票类型被选中
	 * 
	 * @return
	 */
	public int getSelectPosition() {
		if (!Utils.isStringEmpty(datas)) {
			for (int i = 0; i < datas.size(); i++) {
				if (null != entity) {
					if (datas.get(i).getInvoicetypename().equals(entity.getInvoicetypename())) {
						Log.d("TAG", datas.get(i).getInvoicetypename() + "-----" + entity.getInvoicetypename());
						return i;
					}
				}
			}
		}
		return -1;
	}

	public void setDates(ArrayList<OrderEntity> datas) {
		this.datas = datas;
		notifyDataSetChanged();
	}

	public void onRefreshed() {
		selected = Integer.MAX_VALUE;
		notifyDataSetChanged();
	}

	static class ViewHolder {
		ImageView imageView;
		TextView textView;
		LinearLayout mLlItemLayout;
	}

	private OnItemPressListener listener;

	public void setListener(OnItemPressListener listener) {
		this.listener = listener;
	}

	public interface OnItemPressListener {
		void onPress(LinearLayout l, ImageView i, TextView t, int p);
	}

}
