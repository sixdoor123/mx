package com.baiyi.cmall.activities.user.merchant.order;

import com.baiyi.cmall.R;
import com.baiyi.core.util.ContextUtil;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 *	显示实际付款的条目
 * @author sunxy
 */
public class PayMoneyAmountItemView extends LinearLayout implements OnClickListener{

	private Context context;

	public PayMoneyAmountItemView(Context context) {
		// TODO Auto-generated constructor stub
		this(context, null);
	}

	public PayMoneyAmountItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}

	//实际付款数量
	private TextView mTxtPayAmount;
	/**
	 * 初始化界面
	 */
	private void initView() {
		View view = ContextUtil.getLayoutInflater(context)
				.inflate(R.layout.money_amount_item_view, null);
		addView(view);
		
		mTxtPayAmount = (TextView) view.findViewById(R.id.txt_pay_amount);
		
		this.setOnClickListener(this);
	}
	
	/**
	 * 显示界面数据
	 * @param info
	 */
	public void displayView(String info){
		mTxtPayAmount.setText(info);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
