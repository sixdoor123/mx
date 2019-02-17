/**
 * 
 */
package com.baiyi.cmall.activities.main.business.holder;

import com.baiyi.cmall.R;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * µÍ∆Ã…Ã∆∑ item
 * @author tangkun
 *
 */
public class DynamicHolder extends RecyclerView.ViewHolder{
	
	public ImageView imgConver;
	public TextView txtPrice;

	/**
	 * @param arg0
	 */
	public DynamicHolder(View arg0) {
		super(arg0);
		imgConver = (ImageView)arg0.findViewById(R.id.img_conver);
		txtPrice = (TextView)arg0.findViewById(R.id.txt_price);
	}


}
