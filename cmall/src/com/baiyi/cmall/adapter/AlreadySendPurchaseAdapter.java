package com.baiyi.cmall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.user.buyer.adapter.BaseRCAdapter;
import com.baiyi.cmall.adapter.PurchaseAdapter.PurView;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.utils.Utils;

/**
 * ���ǲɹ��̡��ɹ��������m����
 * 
 * @author lizl
 * 
 */
public class AlreadySendPurchaseAdapter extends BaseRCAdapter<GoodsSourceInfo> {

	public AlreadySendPurchaseAdapter(Context context) {
		super(context);
	}

	@Override
	public ViewHolder onCreateVH(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.item_5_view, parent, false);
		return new SendView(view);
	}

	@Override
	public void onBindVH(ViewHolder holder, int position) {
		final GoodsSourceInfo info = objects.get(position);
		((SendView) holder).goodsName.setText(info.getGoodSName());// ��Դ���ƣ����ࣩ
		// ֱ�Ӹ�ֵ״̬
		((SendView) holder).auditState.setText(info.getIntentionOrderState());// ����״̬
		((SendView) holder).merchantNmae.setText(info.getGoodSCompanyNmae());// �̼�����
		((SendView) holder).numberTextView.setText(Utils.dealWeight(info
				.getGoodSWeight()) + "��");// ����

		if (info.getGoodSpriceInterval().equals("null")) {

			((SendView) holder).priceInterval.setText("������");
		} else {

			((SendView) holder).priceInterval.setText(Utils.dealPrice(info
					.getGoodSpriceInterval()) + "Ԫ/��");// �۸�
		}

	}

	private static class SendView extends RecyclerView.ViewHolder {

		TextView goodsName;// ��Դ����
		TextView auditState;// ״̬
		TextView merchantNmae;// �̼�����
		TextView numberTextView;// ����
		TextView priceInterval;// �۸�Χ

		public SendView(View itemView) {
			super(itemView);

			goodsName = (TextView) itemView.findViewById(R.id.tv_content1);
			auditState = (TextView) itemView.findViewById(R.id.tv_content2);
			merchantNmae = (TextView) itemView.findViewById(R.id.tv_content3);
			numberTextView = (TextView) itemView.findViewById(R.id.tv_content4);
			priceInterval = (TextView) itemView.findViewById(R.id.tv_content5);

		}

	}

}
