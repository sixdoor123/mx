package com.baiyi.cmall.adapter;

import java.util.ArrayList;

import com.baiyi.cmall.entity.IntentionTypeInfo;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 供应意向-类型与状态的选择
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-25 下午3:09:55
 */
public class IntentionSelectionAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<IntentionTypeInfo> datas;

	private LayoutInflater inflater;
	// 上一次点击的文字
	private String words;

	public IntentionSelectionAdapter(Context context,
			ArrayList<IntentionTypeInfo> datas, String words) {
		this.context = context;
		this.datas = datas;
		this.words = words;

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
			convertView = inflater.inflate(R.layout.pop_item_intentation, null);

			holder.mTextView = (TextView) convertView
					.findViewById(R.id.pop_intentation);
			holder.mImageView = (ImageView) convertView
					.findViewById(R.id.img_select);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		String content = datas.get(position).getContent();

		if (content.equals(words)) {
			holder.mTextView.setTextColor(context.getResources().getColor(
					R.color.bg_buyer));
			holder.mImageView.setVisibility(View.VISIBLE);
		}

		holder.mTextView.setText(content);

		return convertView;
	}

	static class ViewHolder {
		TextView mTextView;
		ImageView mImageView;
	}
}
