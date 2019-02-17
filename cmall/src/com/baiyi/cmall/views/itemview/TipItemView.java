/**
 * 
 */
package com.baiyi.cmall.views.itemview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.cmall.R;
import com.baiyi.core.util.ContextUtil;

/**
 * 提示说明item 例如 密度：11.22
 * @author tangkun
 *
 */
public class TipItemView extends LinearLayout{
	
	private TextView nameTv = null;
	private TextView valueTv = null;

	public TipItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	public TipItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	private void initView()
	{
		ContextUtil.getLayoutInflater(getContext()).inflate(R.layout.item_mall_tip, this);
		nameTv = (TextView)findViewById(R.id.txt_name);
		valueTv = (TextView)findViewById(R.id.txt_info);
	}
	
	public void setData(String name, String value)
	{
		nameTv.setText(name);
		valueTv.setText(value);
	}


}
