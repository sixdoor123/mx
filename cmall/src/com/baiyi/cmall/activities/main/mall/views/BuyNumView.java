/**
 * 
 */
package com.baiyi.cmall.activities.main.mall.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiyi.cmall.R;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.util.ContextUtil;

/**
 * 购买产品 + -数量
 * 
 * @author tangkun
 *
 */
public class BuyNumView extends LinearLayout {
	// 购买价格
	private double price;
	// 增加数量
	private ImageView addView = null;
	// 减少数量
	private ImageView deleteView = null;
	// 订单个数
	private TextView numerView = null;
	// 最小数量
	private int minCount = 0;
	// 购买数量
	private int proNum = minCount;

	private Context context = null;

	private PriceCallBack priceCallBack = null;

	public BuyNumView(Context context) {
		this(context, null);
	}

	public BuyNumView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}

	private void initView() {
		ContextUtil.getLayoutInflater(getContext())//
				.inflate(R.layout.item_buy_num, this);
		addView = (ImageView) findViewById(R.id.order_add);
		deleteView = (ImageView) findViewById(R.id.order_delete);
		numerView = (TextView) findViewById(R.id.order_number);
		addView.setOnClickListener(new CountOnclick());
		deleteView.setOnClickListener(new CountOnclick());
	}

	public int getProNum() {
		return proNum;
	}

	public void setProNum(int proNum) {
		this.proNum = proNum;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCountPrice(double price) {
		double p = price * proNum;
		return Utils.getPoint2Float(p);
	}

	public void setMinNum(int minNum) {
		this.minCount = minNum;
		if (minNum == 0) {
			minCount = 1;
		}
	}

	private void add() {

		if (proNum >= ap) {
			((BaseActivity) context).displayToast("库存不足");
			return;
		}
		proNum++;
		numerView.setText("" + proNum);
		double p = getPrice() * proNum;
		if (priceCallBack != null) {
			priceCallBack.setPriceCallBack(Utils.getPoint2Float(p));
		}
		// if (num > 1) {
		// deleteView.setImageResource(R.drawable.order_delete_select);
		// }

		if (numberChangerListener != null) {
			numberChangerListener.onChange(proNum);
		}

	}

	private void delete() {
		if (proNum > minCount) {
			proNum--;
			numerView.setText("" + proNum);
			double p = getPrice() * proNum;
			if (priceCallBack != null) {
				priceCallBack.setPriceCallBack(Utils.getPoint2Float(p));
			}
			if (proNum <= minCount) {
				// deleteView.setImageResource(R.drawable.order_delete);
			}

			if (numberChangerListener != null) {
				numberChangerListener.onChange(proNum);
			}
		} else {
			// 数量不能少于1
			// displayToast(context.getResources().getString(R.string.tip_order_num));
			return;
		}

	}

	private class CountOnclick implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.order_add) {
				add();
			} else if (id == R.id.order_delete) {
				delete();
			}
		}

	}

	private OnNumberChangerListener numberChangerListener;

	public void setNumberChangerListener(OnNumberChangerListener numberChangerListener) {
		this.numberChangerListener = numberChangerListener;
	}

	public interface OnNumberChangerListener {
		void onChange(int count);
	}

	public void setPriceCallBack(PriceCallBack priceCallBack) {
		this.priceCallBack = priceCallBack;
	}

	public interface PriceCallBack {
		public void setPriceCallBack(String price);
	}

	public void setNumerView(String text) {
		this.numerView.setText(text);
	}

	public String getNumerView() {
		return numerView.getText().toString().trim();
	}

	private int ap = 100000;

	public void setAp(int ap) {
		this.ap = ap;
		if (ap>0) {
			minCount = 1;
			proNum = 1;
			numerView.setText("1");
		}
	}
}
