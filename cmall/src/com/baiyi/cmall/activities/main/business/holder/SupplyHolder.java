/**
 * 
 */
package com.baiyi.cmall.activities.main.business.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * @author tangkun
 *
 */
public class SupplyHolder extends RecyclerView.ViewHolder{
	
	public TextView txtContent;
	public TextView txtCompanyName;
	public TextView txtArea;
	public TextView txtMinPrice;

	/**
	 * @param arg0
	 */
	public SupplyHolder(View view) {
		super(view);
	}

}
