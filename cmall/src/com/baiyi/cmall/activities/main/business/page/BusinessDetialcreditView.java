/**
 * 
 */
package com.baiyi.cmall.activities.main.business.page;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.cmall.R;
import com.baiyi.core.util.ContextUtil;

/**
 * @author tangkun
 *
 */
public class BusinessDetialcreditView extends LinearLayout{
	
	public BusinessDetialcreditView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public BusinessDetialcreditView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.setOrientation(LinearLayout.VERTICAL);
	}
	
	public void init(String[] names, String[] infos)
	{
		for(int i = 0; i < names.length; i++)
		{
			View view = ContextUtil.getLayoutInflater(getContext()).inflate(R.layout.item_business_shop_credit, null);
			addView(view);
			TextView name = (TextView)view.findViewById(R.id.name);
			TextView info = (TextView)view.findViewById(R.id.info);
			name.setText(names[i]);
			info.setText(infos[i]);
		}
	}

}
