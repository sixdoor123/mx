/**
 * 
 */
package com.baiyi.cmall.activities.main.business.pop;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.cmall.R;
import com.baiyi.cmall.dialog.DialogBase;
import com.baiyi.core.util.ContextUtil;

/**
 * 商城-筛选Pop
 * @author tangkun
 *
 */
public class PopBusinessProCategory extends DialogBase{
	

	/**
	 * @param context
	 * @param gravity
	 */
	public PopBusinessProCategory(Context context, int gravity) {
		super(context, gravity);
		// TODO Auto-generated constructor stub
	}

	private LinearLayout itemGroup = null;
	
	private static final String[] TEXTS = {"有机原料与中间体","有机原料与中间体","有机原料与中间体","有机原料与中间体","有机原料与中间体","有机原料与中间体"};

	/**
	 * @param contentView
	 * @param context
	 * @param width
	 * @param height
	 */
//	public PopBusinessProCategory(View contentView, Context context, int width,
//			int height) {
//		super(contentView, context, width, height);
//		// TODO Auto-generated constructor stub
//		this.itemGroup = (LinearLayout)contentView.findViewById(R.id.item_group);
//		initViews(contentView);
//	}
	
	public interface BusinessProCategoryItemOnclick
	{
		public void setBusinessProCategoryItemOnclickLister(int position);
	}

	@Override
	public void setTitleContent() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setContainer() {
		// TODO Auto-generated method stub
		View view = ContextUtil.getLayoutInflater(getContext()).inflate(R.layout.pop_business_pro_category, null);
		addView(view);
		initViews(view);
	}
	
	private void initViews(View view)
	{
		this.itemGroup = (LinearLayout)view.findViewById(R.id.item_group);
		for(int i = 0; i < TEXTS.length; i++)
		{
			View item = ContextUtil.getLayoutInflater(getContext()).inflate(R.layout.item_business_category, null);
			itemGroup.addView(item);
			if(i != TEXTS.length - 1)
			{
				View line = ContextUtil.getLayoutInflater(getContext()).inflate(R.layout.view_line_1, null);
				itemGroup.addView(line, -1, 1);
			}
			item.setId(i);
			TextView tv = (TextView)item.findViewById(R.id.item);
			tv.setText(TEXTS[i]);
		}
	}

	@Override
	public void OnClickListenEvent(View v) {
		// TODO Auto-generated method stub
		
	}
}
