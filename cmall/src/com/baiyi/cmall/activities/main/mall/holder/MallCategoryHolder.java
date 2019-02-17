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
 * 商城主页-分类 
 * @author tangkun
 *
 */
public class MallCategoryHolder extends RecyclerView.ViewHolder{
	
	public ImageView imgConver = null;
	public TextView txtName = null;

	/**
	 * @param arg0
	 */
	public MallCategoryHolder(View view) {
		super(view);
		imgConver = (ImageView)view.findViewById(R.id.img_conver);
		txtName = (TextView)view.findViewById(R.id.mall_name);
	}

}
