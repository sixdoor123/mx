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
import com.baiyi.cmall.activities.main.mall.adapter.BaseRecyclerAdapter;
import com.baiyi.cmall.activities.main.mall.entity.MallProEntity;
import com.baiyi.cmall.activities.main.mall.holder.MallHolder;

/**
 * ÉÌ³ÇÖ÷Ò³ item
 * @author tangkun
 *
 */
public class BusinessProAdapter extends BaseRecyclerAdapter<MallProEntity>{

	/**
	 * @param objects
	 */
	public BusinessProAdapter(Context context, ArrayList<MallProEntity> objects) {
		super(context, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ViewHolder onCreateVH(ViewGroup parent, int viewType) {
		// TODO Auto-generated method stub
	      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mall,parent,false);
	      MallHolder vh = new MallHolder(view);
	      return vh;
	}

	@Override
	public void onBindVH(ViewHolder holder, int position) {
//		((MallHolder)holder).txtName.setText(objects.get(position).getName());
	}

}
