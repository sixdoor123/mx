package com.baiyi.cmall.adapter._public;

import java.util.ArrayList;

import com.baiyi.cmall.entity.SelectedInfo;
import com.baiyi.cmall.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 城市选择适配器
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-1-11 下午12:55:32
 */
public class CitySelectedAdapter extends BasepublicAdapter {

	public CitySelectedAdapter(ArrayList<?> datas, Context context) {
		super(datas, context);
	}

	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		SelectedInfo info = (SelectedInfo) datas.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			// holder.layout = new LinearLayout(context);
			// holder.layout.setOrientation(LinearLayout.VERTICAL);
			// holder.layout.setLayoutParams(new LayoutParams(
			// LayoutParams.MATCH_PARENT,
			// LayoutParams.MATCH_PARENT));

			convertView = inflater.inflate(R.layout.linear_layout, null);
			holder.layout = (LinearLayout) convertView.findViewById(R.id.ll_);
			holder.mTextView = (TextView) convertView
					.findViewById(R.id.txt_show);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// CitySelectedView selectedView = new CitySelectedView(context);
		// selectedView.setmTxtView(info);
		// holder.layout.addView(selectedView);
		holder.mTextView.setText(info.getCm_categoryname());
		return convertView;
	}

	static class ViewHolder {
		LinearLayout layout;
		TextView mTextView;
	}
}
