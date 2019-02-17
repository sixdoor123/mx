/**
 * 
 */
package com.baiyi.cmall.activities.main.business.help;

import android.view.View;
import android.widget.TextView;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.mall.holder.BaseHolder;

/**
 * @author tangkun
 * 
 */
public class FeedBackSystemHolder extends BaseHolder {

	public TextView txtContent = null;
	public TextView txtTime = null;

	/**
	 * @param view
	 */
	public FeedBackSystemHolder(View view) {
		super(view);
		txtTime = (TextView) view.findViewById(R.id.txt_time);
		txtContent = (TextView) view.findViewById(R.id.txt_content);
	}
	
	
}
