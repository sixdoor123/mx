package com.baiyi.cmall.adapter._public;

import java.util.ArrayList;

import com.baiyi.cmall.entity._public.BrandEntities;
import com.baiyi.cmall.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 *
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-31 
 *       ÉÏÎç11:22:59
 */
public class BrandAdapter extends BasepublicAdapter {

	private ArrayList<BrandEntities> datas;
	
	@SuppressWarnings("unchecked")
	public BrandAdapter(ArrayList<?> datas, Context context) {
		super(datas, context);
		this.datas = (ArrayList<BrandEntities>) datas;
	}

	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		ViewHolde holde = null;
		if (null == convertView) {
			holde = new ViewHolde();
			convertView = inflater.inflate(R.layout.list_item_public,null);
			
			holde.mTextView = (TextView) convertView.findViewById(R.id.txt_public);
			
			convertView.setTag(holde);
		}else {
			holde = (ViewHolde) convertView.getTag();
		}
		
		holde.mTextView.setText(datas.get(position).getBrandName());
		
		return convertView;
	}

	static class ViewHolde{
		TextView mTextView;
	}
}
