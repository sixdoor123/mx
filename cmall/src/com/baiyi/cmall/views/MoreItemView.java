package com.baiyi.cmall.views;

import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-1-12 下午1:15:44
 */
public class MoreItemView extends LinearLayout {

	// 上下文
	private Context context;

	public MoreItemView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public MoreItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

		initView();
	}

	// 显示更多的文字
	private TextView mTxtMore;

	/**
	 * 初始化内容
	 */
	private void initView() {
		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.item_more, null);
		addView(view);

		mTxtMore = (TextView) view.findViewById(R.id.txt_more);
	}

}
