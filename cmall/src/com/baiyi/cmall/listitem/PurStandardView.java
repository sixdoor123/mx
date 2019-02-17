package com.baiyi.cmall.listitem;

import java.util.ArrayList;
import java.util.List;
import com.baiyi.cmall.entity.IntentionDetailStandardInfo;
import com.baiyi.cmall.model.Rpv;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 每一种属性的View
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-30 上午10:37:31
 */
public class PurStandardView extends LinearLayout {

	// 上下文
	private Context context;
	// 每一种属性的分类名称
	private TextView mTxtTypeName;
	// 布局View
	private View view;
	// 属性的线性布局
	private LinearLayout mLinLayout;
	// 分割线
	private View viewLine;

	public PurStandardView(Context context) {
		this(context, null);
	}

	public PurStandardView(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.context = context;
		this.setOrientation(LinearLayout.VERTICAL);
		initView();
	}

	/**
	 * 解析布局
	 */
	private void initView() {
		view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_standard_parmas, null);
		mTxtTypeName = (TextView) view.findViewById(R.id.txt_standard_title);
		mLinLayout = (LinearLayout) view.findViewById(R.id.lin_layout);
		viewLine = view.findViewById(R.id.view_line);

	}

	/**
	 * 设置每一种分类的类型
	 * 
	 * @param typeNme
	 */
	public void setmTxtTypeName(String typeNme, int i) {
		this.mTxtTypeName.setText(typeNme);
		// 当为第一个时，隐藏
		if (0 == i) {
			viewLine.setVisibility(View.GONE);
		} else {
			viewLine.setVisibility(View.VISIBLE);
		}

	}

	/**
	 * a每一种类型对应的规格类型列表
	 * 
	 * @param datas
	 */
	public void setListView(ArrayList<IntentionDetailStandardInfo> datas) {

		for (int i = 0; i < datas.size(); i++) {
			View mLinView = ContextUtil.getLayoutInflater(context).inflate(
					R.layout.list_item_standard, null);
			TextView mTvName = (TextView) mLinView
					.findViewById(R.id.txt_item_name);
			TextView mTvValue = (TextView) mLinView
					.findViewById(R.id.txt_item_value);

			mTvName.setText(datas.get(i).getPropertyname() + ":");
			// 属性单位
			String unit = datas.get(i).getPropertyunit();
			mTvValue.setText(datas.get(i).getPropertyvalue() + " "
					+ (unit.equals("null") ? "" : unit));

			mLinLayout.addView(mLinView);
		}
		addView(view);
	}
	
	/**
	 * 添加商城中商品详情属性
	 * @param datas
	 */
	public void setMallDetailListView(List<Rpv> datas) {
		
		for (int i = 0; i < datas.size(); i++) {
			View mLinView = ContextUtil.getLayoutInflater(context).inflate(
					R.layout.list_item_standard, null);
			TextView mTvName = (TextView) mLinView
					.findViewById(R.id.txt_item_name);
			TextView mTvValue = (TextView) mLinView
					.findViewById(R.id.txt_item_value);
			
			// (unit.equals("null") ? "" : unit)
			// 属性名称
			mTvName.setText(datas.get(i).getPn() + ":");
			// 属性值/单位
			mTvValue.setText(datas.get(i).getPv() + " " + datas.get(i).getPu());
			
			mLinLayout.addView(mLinView);
		}
		addView(view);
	}
}
