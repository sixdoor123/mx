package com.baiyi.cmall.activities.user.buyer.form.product_order;

import java.util.ArrayList;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.entity.OrderEntity;
import com.baiyi.cmall.utils.IntentClassChangeUtils;
import com.baiyi.cmall.utils.Utils;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * ��Ҷ��� - ��Ʒ���� - ĳ�����̵����в�Ʒ����
 * 
 * @author lizl
 */
public class ProductOrderChildItem extends LinearLayout {

	// ������
	private Context context;

	public ProductOrderChildItem(Context context) {
		this(context, null);
	}

	public ProductOrderChildItem(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.context = context;
		this.setOrientation(LinearLayout.VERTICAL);
	}

	/**
	 * aÿһ�����Ͷ�Ӧ�Ĺ�������б�
	 * 
	 * @param datas
	 */
	public void setListView(final ArrayList<OrderEntity> datas) {

		this.removeAllViews();
		
		for (int i = 0; i < datas.size(); i++) {
			View mLinView = ContextUtil.getLayoutInflater(context).inflate(
					R.layout.item_product_order_child_view, null);
			TextView mGoodsName = (TextView) mLinView
					.findViewById(R.id.tv_goods_name);// ��Ʒ����
			TextView mPrice = (TextView) mLinView
					.findViewById(R.id.tv_form_price);// �۸�
			TextView mNum = (TextView) mLinView.findViewById(R.id.tv_form_num);// ����

			mGoodsName.setText(datas.get(i).getProductName());
			mPrice.setText(Utils.dealPrice(datas.get(i).getPrice()) + "Ԫ");
			mNum.setText("X " + Utils.dealWeight(datas.get(i).getInventory()));

			this.addView(mLinView);
		}
	}
}
