package com.baiyi.cmall.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;
import com.baiyi.cmall.entity.OrderEntity;
import com.baiyi.cmall.utils.Utils;

/**
 * 我是采购商— 收货人列表适配器
 * 
 * @author lizl
 * 
 */
public class ConsigneeAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<OrderEntity> datas;
	// 解析器
	private LayoutInflater inflater;

	public ConsigneeAdapter(Context context) {
		this.context = context;
		this.datas = new ArrayList<OrderEntity>();
		inflater = ContextUtil.getLayoutInflater(context);
	}

	@Override
	public int getCount() {
		return datas.size() > 8 ? 8 : datas.size();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_consignee_address,
					null);

			holder.isDefault = (ImageView) convertView
					.findViewById(R.id.img_is_select);
			holder.imgEdit = (ImageView) convertView
					.findViewById(R.id.img_consignee_edit);
			holder.receivePerson = (TextView) convertView
					.findViewById(R.id.tv_receive_person);
			holder.phone = (TextView) convertView
					.findViewById(R.id.tv_tel_phone);

			holder.address = (TextView) convertView
					.findViewById(R.id.tv_goods_address);
			holder.city = (TextView) convertView.findViewById(R.id.tv_city);
			holder.delete = (TextView) convertView.findViewById(R.id.delete);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final OrderEntity info = datas.get(position);

		boolean isDefault = info.isDefault();
		// 默认的地址添加标志
		if (isDefault) {
			holder.isDefault.setVisibility(View.VISIBLE);
		} else {
			holder.isDefault.setVisibility(View.GONE);
		}
		holder.receivePerson.setText(info.getReceivername());// 收货人
		holder.phone.setText(info.getPhone());// 电话
		holder.address.setText(info.getAddress());// 收货地址
		holder.city.setText(info.getOrderCity());// 城市
		holder.imgEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				listener.onBtnEdit(info);
			}
		});
		holder.delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listener.onBtnDelete(info);
			}
		});
		return convertView;
	}

	static class ViewHolder {
		TextView receivePerson;// 收货人
		TextView phone;// 电话
		TextView address;// 收货地址
		TextView city;// 收货地址
		ImageView isDefault;// 是否默认
		ImageView imgEdit;// 编辑
		TextView delete;// 删除

	}

	/**
	 * 设置数据，用于刷新頁面数据
	 * 
	 * @param datas
	 */
	public void setData(ArrayList<OrderEntity> datas) {
		if (Utils.isStringEmpty(datas)) {
			this.datas = new ArrayList<OrderEntity>();
		}
		this.datas.clear();
		this.datas.addAll(datas);
		notifyDataSetChanged();
	}

	/**
	 * 设置数据，分页添加頁面数据
	 * 
	 * @param datas
	 */
	public void addData(ArrayList<OrderEntity> datas) {
		if (Utils.isStringEmpty(datas)) {
			this.datas = new ArrayList<OrderEntity>();
		}
		this.datas.addAll(datas);
		notifyDataSetChanged();
	}

	/**
	 * 删除按钮的监听器
	 */
	private OnEditClickListener listener;

	public void setListener(OnEditClickListener listener) {
		this.listener = listener;
	}

	public interface OnEditClickListener {

		void onBtnEdit(OrderEntity entity);

		void onBtnDelete(OrderEntity entity);
	}

}
