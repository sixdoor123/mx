package com.baiyi.cmall.adapter;

import java.util.ArrayList;

import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.IndutryArgumentInfo;
import com.baiyi.cmall.entity.IntentionDetailStandardInfo;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 工业参数适配器
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-2 下午2:24:25
 */
public class StandardArgumentAdapter extends BaseAdapter {

	// 上下文
	private Context context;
	// 数据源
	private ArrayList<IntentionDetailStandardInfo> datas;

	// 布局解析器
	private LayoutInflater inflater;

	public StandardArgumentAdapter(Context context,
			ArrayList<IntentionDetailStandardInfo> datas) {
		this.context = context;
		if (null == datas) {
			this.datas = new ArrayList<IntentionDetailStandardInfo>();
		} else {
			this.datas = datas;
		}
		inflater = ContextUtil.getLayoutInflater(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
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
			convertView = inflater.inflate(R.layout.list_item_standard, null);

			holder.mTxtName = (TextView) convertView
					.findViewById(R.id.txt_item_name);
			holder.mTxtValue = (TextView) convertView
					.findViewById(R.id.txt_item_value);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		IntentionDetailStandardInfo info = datas.get(position);

		holder.mTxtName.setText(info.getPropertyname()+":");
		holder.mTxtValue.setText(info.getPropertyvalue());

		return convertView;
	}

	static class ViewHolder {
		TextView mTxtName;
		TextView mTxtValue;
	}

	/**
	 * 设置数据
	 * @param datas
	 */
	public void setData(ArrayList<IntentionDetailStandardInfo> datas) {
		if (null != datas) {
			this.datas = datas;
		} else {
			this.datas.clear();
			this.datas = new ArrayList<IntentionDetailStandardInfo>();
		}
		notifyDataSetChanged();
	}

	/**
	 * 添加数据
	 */
	public void addData(ArrayList<IntentionDetailStandardInfo> datas) {

		if (null == this.datas) {
			this.datas = new ArrayList<IntentionDetailStandardInfo>();
		}
		this.datas.addAll(datas);
		notifyDataSetChanged();
	}
}
