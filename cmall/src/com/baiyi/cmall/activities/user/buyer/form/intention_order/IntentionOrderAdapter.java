package com.baiyi.cmall.activities.user.buyer.form.intention_order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.user.buyer.adapter.BaseRCAdapter;
import com.baiyi.cmall.entity.OrderEntity;
import com.baiyi.cmall.utils.TextViewUtil;
import com.baiyi.cmall.utils.Utils;

/**
 * �ҵĲɹ���
 * 
 * @author lizl
 * 
 */
public class IntentionOrderAdapter extends BaseRCAdapter<OrderEntity> {

	public IntentionOrderAdapter(Context context) {
		super(context);
	}

	@Override
	public ViewHolder onCreateVH(ViewGroup parent, int viewType) {

		View view = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.item_5_view, parent, false);
		return new FormHold(view);
	}

	@Override
	public void onBindVH(ViewHolder holder, int position) {
		final OrderEntity info = objects.get(position);
		((FormHold) holder).formName.setText(info.getPurName());
		((FormHold) holder).state.setText(TextViewUtil.getEditString(info.getOrderStateName()));
		((FormHold) holder).numberTextView.setText(Utils.getWeight(info
				.getInventory()) + "��");
		((FormHold) holder).merchantNmae.setText(info.getCompanyname());
		((FormHold) holder).prepaymentPrice.setText(Utils.dealPrice(info
				.getPrepayment()) + "Ԫ");

	}

	public static class FormHold extends RecyclerView.ViewHolder {

		TextView formName;// ��������
		TextView state;// ״̬
		TextView merchantNmae;// �̼�����
		TextView numberTextView;// ����
		TextView prepaymentPrice;// Ԥ�����

		public FormHold(View itemView) {
			super(itemView);
			formName = (TextView) itemView.findViewById(R.id.tv_content1);
			state = (TextView) itemView.findViewById(R.id.tv_content2);
			merchantNmae = (TextView) itemView.findViewById(R.id.tv_content3);
			numberTextView = (TextView) itemView.findViewById(R.id.tv_content4);
			prepaymentPrice = (TextView) itemView
					.findViewById(R.id.tv_content5);
		}

	}

}
