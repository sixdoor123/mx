package com.baiyi.cmall.activities.main.baseList;

import org.w3c.dom.Text;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * ≤‚ ‘  ≈‰∆˜
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-1-21 
 *       œ¬ŒÁ2:21:47
 */
public class MyAdapterText extends BaseListAdapter {

	public MyAdapterText(Context context) {
		super(context);
	}

	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		TextView textView = new TextView(context);
		textView.setText(((MyModelTest)datas.get(position)).getName()+"---"+
				((MyModelTest)datas.get(position)).getId());
		textView.setPadding(10, 15, 10, 15);
		textView.setSingleLine();
		return textView;
	}

	
}
