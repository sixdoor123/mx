package com.baiyi.cmall.activities.main.mall.page.view;

import com.baiyi.cmall.R;
import com.baiyi.cmall.model.Rpv;
import com.baiyi.core.util.ContextUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 *
 * @author sunxy
 */
public class MallDetailItemView extends LinearLayout {

	private Context context = null;

	public MallDetailItemView(Context context) {
		this(context, null);
	}

	public MallDetailItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();
	}

	public TextView mTxtPropertyName;
	public TextView mTxtPropertyValue;

	private void initView() {
		View itemView = ContextUtil.getLayoutInflater(context)//
				.inflate(R.layout.mall_detail_property, null);

		mTxtPropertyName = (TextView) itemView.findViewById(R.id.txt_property_name);
		mTxtPropertyValue = (TextView) itemView.findViewById(R.id.txt_property_value);

	}

	public void display(Rpv rpv) {
		mTxtPropertyName.setText(rpv.getPn() + ": ");
		mTxtPropertyValue.setText(rpv.getPv() + rpv.getPu());
	}

}
