package com.baiyi.cmall.activities.user.merchant.order.adapter;

import java.util.ArrayList;

import com.baiyi.cmall.R;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.util.ContextUtil;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 商家订单-意向订单
 * 
 * @author sunxy
 */
@SuppressWarnings("rawtypes")
public class IntentionOrederAdapter extends BaseRecycleViewAdapter {

	public IntentionOrederAdapter(Context context) {
		super(context);
	}

	@Override
	public void onInitViewHolder(ViewHolder viewHolder, int position) {
		MyViewHolder holder = (MyViewHolder) viewHolder;
		GoodsSourceInfo info = (GoodsSourceInfo) datas.get(position);
		GoodsSourceInfo sourceInfo = null;
		ArrayList<GoodsSourceInfo> list = info.getPurInfos();
		if (!Utils.isStringEmpty(list)) {
			sourceInfo = list.get(0);
		}

		holder.merchantNmae.setText(info.getCompanyName());
		holder.state.setText(info.getStatename());

		if (null != sourceInfo) {

			holder.formName.setText(sourceInfo.getGoodSName());
			holder.priceInterval.setText(info.getInventory());
		}
	}

	@Override
	public ViewHolder reateViewHolder(ViewGroup parent, int viewType) {
		View view = ContextUtil.getLayoutInflater(context).inflate(R.layout.item_5_view, parent, false);
		MyViewHolder holder = new MyViewHolder(view);
		return holder;
	}

	public static class MyViewHolder extends ViewHolder {
		TextView formName;// 订单名称
		TextView numberTextView;// 數量
		TextView state;// 状态
		TextView merchantNmae;// 商家名称
		TextView priceInterval;// 价格范围

		public MyViewHolder(View convertView) {
			super(convertView);
			formName = (TextView) convertView.findViewById(R.id.tv_content1);
			state = (TextView) convertView.findViewById(R.id.tv_content2);
			merchantNmae = (TextView) convertView.findViewById(R.id.tv_content3);
			numberTextView = (TextView) convertView.findViewById(R.id.tv_content4);
			priceInterval = (TextView) convertView.findViewById(R.id.tv_content5);
		}

	}
}
