/**
 * 
 */
package com.baiyi.cmall.activities.main.business.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.business.entity.BusinessDynamicEntity;
import com.baiyi.cmall.activities.main.business.holder.DynamicHolder;
import com.baiyi.cmall.activities.main.mall.adapter.BaseRecyclerAdapter;

/**
 * @author tangkun
 *
 */
public class ShopDynamicAdapter extends BaseRecyclerAdapter<BusinessDynamicEntity>{

	/**
	 * @param context
	 * @param objects
	 */
	public ShopDynamicAdapter(Context context, ArrayList<BusinessDynamicEntity> objects) {
		super(context, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ViewHolder onCreateVH(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_business_dynamic,parent,false);
      DynamicHolder vh = new DynamicHolder(view);
      return vh;
	}

	@Override
	public void onBindVH(ViewHolder holder, int position) {
		BusinessDynamicEntity entity = objects.get(position);
		((DynamicHolder)holder).txtPrice.setText(entity.getPrice());
	}

}
