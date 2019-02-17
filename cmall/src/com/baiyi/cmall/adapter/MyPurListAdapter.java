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
 * ���ǲɹ��̡����ҵĲɹ������ɹ��б�
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

		// ÿһ������
		final GoodsSourceInfo info = objects.get(position);

		// ���ƣ����ࣩ
		((PurHold) holder).mTxtCompanyName.setText(info.getGoodSName() + "("
				+ info.getGoodSCategory() + "/" + info.getGoodSBrand() + ")");
		// ״̬����
		((PurHold) holder).mTxtState.setText(info.getIntentionstatename());

		/**
		 * ����ɹ���Ϣ�������飨�Լ���Ӧ��ȫ���Ĺ�Ӧ�̣�
		 */
		((PurHold) holder).mLinControl
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						// Я��������ת
						Intent intent = new Intent(context,
								SupplierDetailActivity.class);
						intent.putExtra("data", info);
						context.startActivity(intent);

					}
				});
		// �ؼ�����ÿ��ִ��getView��������ɾ���˲��������е�view�������������
		((PurHold) holder).mLstView.removeAllViews();

		/*
		 * ��Ӧ�����ݵ���ʾ(����������Ŀ)
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
