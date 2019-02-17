package com.baiyi.cmall.adapter;

import com.baiyi.cmall.activities.user.buyer.SupplierDetailActivity;
import com.baiyi.cmall.activities.user.buyer.adapter.BaseRCAdapter;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.listitem.MyPurInfoItem;
import com.baiyi.cmall.R;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 我是采购商——我的采购——采购列表
 * 
 * @author lizl
 * 
 */
public class MyPurListAdapter extends BaseRCAdapter<GoodsSourceInfo> {

	public MyPurListAdapter(Context context) {
		super(context);
	}

	@Override
	public ViewHolder onCreateVH(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.activity_pur_order_list, parent, false);
		return new PurHold(view);
	}

	@Override
	public void onBindVH(ViewHolder holder, int position) {

		// 每一条数据
		final GoodsSourceInfo info = objects.get(position);

		// 名称（分类）
		((PurHold) holder).mTxtCompanyName.setText(info.getGoodSName() + "("
				+ info.getGoodSCategory() + "/" + info.getGoodSBrand() + ")");
		// 状态名称
		((PurHold) holder).mTxtState.setText(info.getIntentionstatename());

		/**
		 * 点击采购信息，看详情（以及对应的全部的供应商）
		 */
		((PurHold) holder).mLinControl
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						// 携带数据跳转
						Intent intent = new Intent(context,
								SupplierDetailActivity.class);
						intent.putExtra("data", info);
						context.startActivity(intent);

					}
				});
		// 关键处：每次执行getView方法必须删除此布局中所有的view，重新添加内容
		((PurHold) holder).mLstView.removeAllViews();

		/*
		 * 供应商数据的显示(二级分类条目)
		 */
		MyPurInfoItem myPurInfoItem = new MyPurInfoItem(context);
		myPurInfoItem.setId(position);
		myPurInfoItem.setData(info.getOfferInfos());
		((PurHold) holder).mLstView.addView(myPurInfoItem);

	}

	public static class PurHold extends RecyclerView.ViewHolder {

		TextView mTxtCompanyName;
		TextView mTxtState;
		LinearLayout mLstView;
		LinearLayout mLinControl;
		View viewLin;

		public PurHold(View itemView) {
			super(itemView);
			mTxtCompanyName = (TextView) itemView
					.findViewById(R.id.txt_company_name);
			mTxtState = (TextView) itemView.findViewById(R.id.txt_total_weight);
			mLstView = (LinearLayout) itemView.findViewById(R.id.group);
			mLinControl = (LinearLayout) itemView.findViewById(R.id.ll);
			viewLin = (View) itemView.findViewById(R.id.v_line);

		}

	}

}
