package com.baiyi.cmall.activities.main.mall.adapter;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.user.buyer.adapter.BaseRCAdapter;
import com.baiyi.cmall.model.Pvm;
import com.baiyi.cmall.utils.ImageTools;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 商城——详情——产品详情适配器
 * 
 * @author sunxy
 * 
 */
public class MallDetailAdapter extends BaseRCAdapter<Pvm> {

	public MallDetailAdapter(Context context) {
		super(context);
	}

	@Override
	public ViewHolder onCreateVH(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.item_mall_detail, parent, false);
		return new DetailView(view);
	}

	@Override
	public void onBindVH(ViewHolder holder, int position) {

		final Pvm info = objects.get(position);
		((DetailView) holder).mTvDteail.setText(info.getIl());
		ImageTools.getNormalImage(context, ((DetailView) holder).mImgPic,
				info.getIurl());
	}

	 class DetailView extends RecyclerView.ViewHolder {

		TextView mTvDteail;// 详细内容
		ImageView mImgPic;// 详情图片

		public DetailView(View itemView) {
			super(itemView);
			mTvDteail = (TextView) itemView.findViewById(R.id.tv_mall_detail);
			mImgPic = (ImageView) itemView.findViewById(R.id.img_mall_detail);
			
			int width = Config.getInstance().getScreenWidth(context);
			
			mImgPic.getLayoutParams().height = Math.round(width * (320f / 800f));
		}

	}

}
