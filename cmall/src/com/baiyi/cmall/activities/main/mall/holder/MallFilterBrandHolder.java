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
 * 商城-筛选-品牌
 * @author tangkun
 *
 */
public class MallFilterBrandHolder extends RecyclerView.ViewHolder{
	
	public TextView txtName = null;
	public ImageView imgArrow = null;

	/**
	 * @param arg0
	 */
	public MallFilterBrandHolder(View view) {
		super(view);
		// TODO Auto-generated constructor stub
		txtName = (TextView)view.findViewById(R.id.txt_root_name);
		imgArrow = (ImageView)view.findViewById(R.id.img_mall_arrow);
	}

}
