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
 * 买家订单 - 产品订单 - 某个店铺的所有产品订单
 * 
 * @author lizl
 */
public class ProductOrderChildItem extends LinearLayout {

	// 上下文
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
	 * a每一种类型对应的规格类型列表
	 * 
	 * @param datas
	 */
	public void setListView(final ArrayList<OrderEntity> datas) {

		this.removeAllViews();
		
		for (int i = 0; i < datas.size(); i++) {
			View mLinView = ContextUtil.getLayoutInflater(context).inflate(
					R.layout.item_product_order_child_view, null);
			TextView mGoodsName = (TextView) mLinView
					.findViewById(R.id.tv_goods_name);// 商品名称
			TextView mPrice = (TextView) mLinView
					.findViewById(R.id.tv_form_price);// 价格
			TextView mNum = (TextView) mLinView.findViewById(R.id.tv_form_num);// 數量

			mGoodsName.setText(datas.get(i).getProductName());
			mPrice.setText(Utils.dealPrice(datas.get(i).getPrice()) + "元");
			mNum.setText("X " + Utils.dealWeight(datas.get(i).getInventory()));

			this.addView(mLinView);
		}
	}
}
