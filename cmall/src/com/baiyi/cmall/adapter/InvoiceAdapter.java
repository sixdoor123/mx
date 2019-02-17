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
 * 我是采购商— 发票列表适配器
 * 
 * @author lizl
 * 
 */
public class InvoiceAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<OrderEntity> datas;
	// 解析器
	private LayoutInflater inflater;
	private int max;

	public InvoiceAdapter(Context context) {
		this.context = context;
		this.datas = new ArrayList<OrderEntity>();
		this.inflater = ContextUtil.getLayoutInflater(context);
	}

	/**
	 * 返回最新的数据，最多----max条
	 */
	@Override
	public int getCount() {

		return datas.size() > max ? max : datas.size();
	}

	@Override
	public Object getItem(int position) {

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
			convertView = inflater.inflate(R.layout.item_invoice_info, null);

			holder.isDefault = (ImageView) convertView
					.findViewById(R.id.img_is_select);
			holder.imgEdit = (ImageView) convertView
					.findViewById(R.id.img_invoice_edit);
			holder.invoiceTitle = (TextView) convertView
					.findViewById(R.id.tv_invoice_title);
			holder.invoiceContent = (TextView) convertView
					.findViewById(R.id.tv_invoice_content);
			holder.invoiceType = (TextView) convertView
					.findViewById(R.id.tv_invoice_type);
			holder.delete = (TextView) convertView.findViewById(R.id.delete);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final OrderEntity info = datas.get(position);

		boolean isDefault = info.isDefault();
		// 默认的添加标志
		if (isDefault) {
			holder.isDefault.setVisibility(View.VISIBLE);
		} else {
			holder.isDefault.setVisibility(View.GONE);
		}
		holder.invoiceTitle.setText(info.getTitle());// 发票抬头
		holder.invoiceContent.setText(info.getContext());// 内容
		holder.invoiceType.setText(info.getInvoicetypename());// 类型
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
		TextView invoiceTitle;// 抬头
		TextView invoiceContent;// 内容
		TextView invoiceType;// 类型
		ImageView isDefault;// 是否默认
		ImageView imgEdit;// 编辑
		TextView delete;// 删除

	}

	/**
	 * 设置数据，用于刷新頁面数据
	 * 
	 * @param datas
	 */
	public void setData(ArrayList<OrderEntity> datas, int max) {
		if (Utils.isStringEmpty(datas)) {
			this.datas = new ArrayList<OrderEntity>();
		}
		this.max = max;
		this.datas.clear();
		this.datas.addAll(datas);
		notifyDataSetChanged();
	}

	/**
	 * 设置数据，用于刷新頁面数据
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
	private OnInVoiceEditClickListener listener;

	public void setEditListener(OnInVoiceEditClickListener listener) {
		this.listener = listener;
	}

	public interface OnInVoiceEditClickListener {

		void onBtnEdit(OrderEntity entity);

		void onBtnDelete(OrderEntity entity);

	}

}
