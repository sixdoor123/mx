/**
 * 
 */
package com.baiyi.cmall.activities.main.mall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.mall.entity.MallContentProEntity;
import com.baiyi.cmall.activities.main.mall.holder.MallCategoryHolder;
import com.baiyi.cmall.utils.ImageTools;

/**
 * 商城主页-分类弹窗 listitem
 * 
 * @author tangkun
 * 
 */
public class MallCategoryAdapter extends
		BaseRecyclerAdapter<MallContentProEntity> {

	public MallCategoryAdapter(Context context) {
		super(context);
	}

	@Override
	public ViewHolder onCreateVH(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext())//
    		  .inflate(R.layout.item_mall_category,parent,false);
      MallCategoryHolder vh = new MallCategoryHolder(view);
      return vh;
	}

	@Override
	public void onBindVH(ViewHolder holder, int position) {
		MallContentProEntity entity = objects.get(position);
		((MallCategoryHolder) holder).txtName.setText(entity.getPn());
		ImageTools.getNormalImage(context,
				((MallCategoryHolder) holder).imgConver, entity.getUrl());

	}

}
