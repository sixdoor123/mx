/**
 * 
 */
package com.baiyi.cmall.activities.main.mall.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.mall.entity.MallFilterBrandEntity;
import com.baiyi.cmall.activities.main.mall.holder.MallFilterBrandHolder;

/**
 * 商城-筛选-品牌 item
 * @author tangkun
 *
 */
public class MallFilterBrandAdapter extends BaseRecyclerAdapter<MallFilterBrandEntity>{
	private int selectId;

	/**
	 * @param context
	 * @param objects
	 */
	public MallFilterBrandAdapter(Context context,
			ArrayList<MallFilterBrandEntity> objects) {
		super(context, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ViewHolder onCreateVH(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mall_filter_brand,parent,false);
      MallFilterBrandHolder vh = new MallFilterBrandHolder(view);
      return vh;
	}

	@Override
	public void onBindVH(ViewHolder holder, int position) {
		// TODO Auto-generated method stub
		MallFilterBrandEntity entity = objects.get(position);
		MallFilterBrandHolder h = (MallFilterBrandHolder)holder;
		h.txtName.setText(entity.getName());
		int color = selectId == position ? R.color.bg_buyer : R.color.bg_black;
		h.txtName.setTextColor(context.getResources().getColor(color));
		int visible = selectId == position ? View.VISIBLE : View.GONE;
		h.imgArrow.setVisibility(visible);
		
	}
	
	public void setSelectId(int selectId)
	{
		this.selectId = selectId;
		this.notifyDataSetChanged();
	}

}
