/**
 * 
 */
package com.baiyi.cmall.activities.main.mall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.mall.entity.PnvmlEntity;
import com.baiyi.core.util.ContextUtil;

/**
 * @author tangkun
 *
 */
public class OrderPurityPopGvAdapter extends BaseGridViewAdpater<PnvmlEntity>{
	
	private Context context = null;
	
	private ItemOnclick onclick = null;
	
	public OrderPurityPopGvAdapter(Context context)
	{
		this.context = context;
	}

	@Override
	public View getBindView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Button item = null;
		if(convertView == null)
		{
			convertView = ContextUtil.getLayoutInflater(context)//
					.inflate(R.layout.item_mall_detail_order_pop, null);
			item = (Button)convertView;
			convertView.setTag(item);
		}else
		{
			item = (Button)convertView.getTag();
		}
		final PnvmlEntity entity = (PnvmlEntity)getItem(position);
		item.setText(entity.getNv());
		if(getSelectId() == position)
		{
			item.setBackgroundResource(R.drawable.img_test_mall_detail_pop_item_select);
		}else
		{
			item.setBackgroundResource(R.drawable.img_test_mall_detail_pop_item);
		}
		convertView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(onclick != null)
				{
					onclick.setItemOnclick(position, entity);
				}
			}
		});
		return convertView;
	}
	
	public void setOnclick(ItemOnclick onclick) {
		this.onclick = onclick;
	}

	public interface ItemOnclick 
	{
		public void setItemOnclick(int position, PnvmlEntity entity);
	}

}
