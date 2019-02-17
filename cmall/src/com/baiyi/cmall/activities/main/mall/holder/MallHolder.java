/**
 * 
 */
package com.baiyi.cmall.activities.main.mall.holder;

import com.baiyi.cmall.R;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ÉÌ³ÇÖ÷Ò³ listitem
 * @author tangkun
 *
 */
public class MallHolder extends RecyclerView.ViewHolder{
	
	public ImageView imgConver = null;
	public TextView txtName = null;
	public TextView txtPrice = null;
	public TextView txtUnit = null;
	public ImageView imgSigin = null;

	/**
	 * @param arg0
	 */
	public MallHolder(View view) {
		super(view);
		imgConver = (ImageView)view.findViewById(R.id.img_mall_item);
		txtName = (TextView)view.findViewById(R.id.txt_mall_name);
		txtPrice = (TextView)view.findViewById(R.id.txt_mall_price);
		txtUnit = (TextView)view.findViewById(R.id.txt_mall_unit);
		imgSigin = (ImageView)view.findViewById(R.id.img_mall_sigin);
		
	}

}
