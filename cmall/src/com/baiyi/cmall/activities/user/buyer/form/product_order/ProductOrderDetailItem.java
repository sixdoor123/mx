package com.baiyi.cmall.activities.user.buyer.form.product_order;

import com.baiyi.cmall.entity.OrderEntity;
import com.baiyi.cmall.utils.IntentClassChangeUtils;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * ���ǲɹ��̡����ҵĲ�Ʒ���������������� ����������Ŀ
 * 
 * @author lizl
 * 
 */
public class ProductOrderDetailItem {

	private Context context;
	// ��Ʒ����
	private TextView mTvGoodsName;
	// ���
	private TextView mTvGuiGe;
	// ����
	private TextView mTvWeight;
	// �۸�
	private TextView mTvPrice;
	// �ܼ۸�
	private TextView mTvAllPrice;
	// ����Ŀ
	private View viewItem;
	// ������
	private LayoutInflater inflater;

	public ProductOrderDetailItem(Context context) {
		this.context = context;
		// ���һ�����Բ���
		inflater = ContextUtil.getLayoutInflater(context);
	}

	/**
	 * ��ʾ���������
	 * 
	 * @param classifyInfoEntity
	 */
	public View disItem(final OrderEntity entity) {

		viewItem = inflater.inflate(R.layout.item_pur_product_order, null);

		viewItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/**
				 * ��ת���ɹ�����ҳ��
				 */
				IntentClassChangeUtils.startMallDetail(context, Utils.stringToInt(entity.getId())+"");

			}
		});
		// ��Ʒ����
		mTvGoodsName = (TextView) viewItem.findViewById(R.id.tv_good_name);
		// ���
		mTvGuiGe = (TextView) viewItem.findViewById(R.id.tv_guige);
		// ����
		mTvWeight = (TextView) viewItem.findViewById(R.id.tv_weight);
		// �۸�
		mTvPrice = (TextView) viewItem.findViewById(R.id.tv_price);
		// �ܼ۸�
		mTvAllPrice = (TextView) viewItem.findViewById(R.id.tv_all_price);

		// ��Ʒ����
		mTvGoodsName.setText("DMF(N,N-���׻�������)(CAS:68-12-22)");
		// ���
		mTvGuiGe.setText(entity.getGuige());
		// ����
		mTvWeight.setText(entity.getInventory() + "��");
		// �۸�
		mTvPrice.setText(Utils.twoDecimals(entity.getPrice()) + "Ԫ/��");
		/*
		 * �ܶ�= ����*����
		 */
		String allMoney = (Double.parseDouble(entity.getPrice()) * Long
				.parseLong(entity.getInventory())) + "";
		// �ܼ۸�
		mTvAllPrice.setText(Utils.twoDecimals(allMoney) + "Ԫ");

		return viewItem;
	}
}
