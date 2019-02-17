package com.baiyi.cmall.adapter;

import java.util.ArrayList;

import com.baiyi.cmall.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ∑÷œÌµƒ  ≈‰∆˜
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-2-5 œ¬ŒÁ2:15:50
 */
public class ShareAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<String> names;
	private ArrayList<Integer> imgs;

	private LayoutInflater inflater;

	public ShareAdapter(Context context, ArrayList<String> names,
			ArrayList<Integer> imgs) {
		this.context = context;
		this.names = names;
		this.imgs = imgs;

		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return names == null ? 0 : names.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return names.get(position);
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
			convertView = inflater.inflate(R.layout.list_item_share, null);
			holder = new ViewHolder();

			holder.mImageView = (ImageView) convertView
					.findViewById(R.id.img_pic);
			holder.mTextView = (TextView) convertView
					.findViewById(R.id.txt_name);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.mImageView.setImageResource(imgs.get(position));
		holder.mTextView.setText(names.get(position));

		return convertView;
	}

	static class ViewHolder {
		ImageView mImageView;
		TextView mTextView;
	}
}
