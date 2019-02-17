/**
 * 
 */
package com.baiyi.cmall.activities.main.mall.adapter;

import java.util.ArrayList;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.mall.entity.MallProEntity;
import com.baiyi.cmall.activities.main.mall.entity.PbiEntity;
import com.baiyi.cmall.activities.main.mall.holder.MallHolder;
import com.baiyi.cmall.utils.ImageTools;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * ÉÌ³ÇÖ÷Ò³ item
 * 
 * @author tangkun
 * 
 */
public class MallAdapter extends BaseRecyclerAdapter<MallProEntity> {

	/**
	 * @param objects
	 */
	public MallAdapter(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ViewHolder onCreateVH(ViewGroup parent, int viewType) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.item_mall, parent, false);
		MallHolder vh = new MallHolder(view);
		return vh;
	}

	@Override
	public void onBindVH(ViewHolder holder, int position) {
		MallProEntity entity = objects.get(position);
		String name = objects.get(position).getPn() + "("
				+ entity.getSpecificList().get(0) + ")";
		((MallHolder) holder).txtName.setText(name);
		((MallHolder) holder).txtUnit.setText("/ton");
		((MallHolder) holder).txtPrice.setText("£¤"
				+ objects.get(position).getPp());

		int imgH = (Config.getInstance().getScreenWidth(context) - 30) / 2;
		((MallHolder) holder).imgConver.getLayoutParams().height = imgH;
		ImageTools.getNormalImage(context, ((MallHolder) holder).imgConver, objects.get(position).getPurl());
	}

}
