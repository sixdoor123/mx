package com.baiyi.cmall.activities.user.buyer.form.product_order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.user.buyer.adapter.BaseRCAdapter;
import com.baiyi.cmall.entity.OrderEntity;
import com.baiyi.cmall.utils.Utils;

/**
 * 我的采购单
 * 
 * @author lizl
 * 
 */
public class ProductOrderAdapter extends BaseRCAdapter<OrderEntity> {

	public ProductOrderAdapter(Context context) {
		super(context);
	}

	@Override
	public ViewHolder onCreateVH(ViewGroup parent, int viewType) {

		View view = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.item_product_order_view, parent, false);
		return new FormHold(view);
	}

	@Override
	public void onBindVH(ViewHolder holder, int position) {
		final OrderEntity info = objects.get(position);
		((FormHold) holder).mCompanyName.setText(info.getCompanyname());
		((FormHold) holder).state.setText(info.getOrderStateName());
		((FormHold) holder).childItem.setListView(info.getOpilList());
		((FormHold) holder).mAllMoney.setText("￥ "
				+ Utils.dealPrice(info.getPrepayment()) + "元");

	}

	public static class FormHold extends RecyclerView.ViewHolder {

		TextView mCompanyName;// 公司名称
		TextView state;// 状态
		ImageView mImg_pic;// 商品图片
		ProductOrderChildItem childItem;
		TextView mAllMoney;// 总金额

		public FormHold(View itemView) {
			super(itemView);
			mCompanyName = (TextView) itemView.findViewById(R.id.tv_company);
			state = (TextView) itemView.findViewById(R.id.tv_state_name);
			mImg_pic = (ImageView) itemView.findViewById(R.id.img_pic);
			childItem = (ProductOrderChildItem) itemView
					.findViewById(R.id.lay_product_parent);
			mAllMoney = (TextView) itemView.findViewById(R.id.tv_all_money);
		}

	}

}
