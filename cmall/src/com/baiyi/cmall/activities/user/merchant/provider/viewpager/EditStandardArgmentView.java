package com.baiyi.cmall.activities.user.merchant.provider.viewpager;

import java.util.ArrayList;

import com.baiyi.cmall.entity.IntentionDetailStandardInfo;
import com.baiyi.cmall.views.pulldownview.PullToRefreshListView;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 每一种属性的View
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-30 上午10:37:31
 */
public class EditStandardArgmentView extends LinearLayout {

	// 上下文
	private Context context;

	public EditStandardArgmentView(Context context) {
		this(context, null);
	}

	// 用户ID
	private String userID;

	public EditStandardArgmentView(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.context = context;
		this.setOrientation(LinearLayout.VERTICAL);
		initView();
	}

	private PullToRefreshListView listView;
	// 每一种属性的分类名称
	private TextView mTxtTypeName;
	// 布局View
	private View view;
	// 分割线
	private View viewLine;

	private LinearLayout layout;

	/**
	 * 解析布局
	 */
	private void initView() {
		view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_standard_parmas, null);
		mTxtTypeName = (TextView) view.findViewById(R.id.txt_standard_title);
		layout = (LinearLayout) view.findViewById(R.id.lin_layout);
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
			this.mTxtTypeName.setPadding(0, 0, 0, 0);
			viewLine.setVisibility(View.VISIBLE);
		}

	}

	// 编辑输入框
	private EditText mEdtStandard;
	// 参数名称
	private TextView mTxtStandardName;
	// 显示单位
	private TextView mTxtUnit;

	/**
	 * a每一种类型对应的规格类型列表
	 * 
	 * @param datas
	 * @param isEditDetailEnable
	 * @param j
	 */
	public void setListView(ArrayList<IntentionDetailStandardInfo> datas,
			boolean isEditDetailEnable, int j) {

		for (int i = 0; i < datas.size(); i++) {
			IntentionDetailStandardInfo info = datas.get(i);
			View v = ContextUtil.getLayoutInflater(context).inflate(
					R.layout.activity_edit_sandard, null);
			mEdtStandard = (EditText) v.findViewById(R.id.edt_standard);
			mTxtStandardName = (TextView) v.findViewById(R.id.standard_name);
			mTxtUnit = (TextView) v.findViewById(R.id.txt_unit);

			mTxtStandardName.setText(info.getPropertyname());
			mEdtStandard.setText(info.getPropertyvalue());
			mTxtUnit.setText(info.getPropertyunit() == null ? "" : info
					.getPropertyunit());
			mEdtStandard.setTextColor(context.getResources().getColor(
					R.color.bg_hui1));

			
			ProDataUtils.setInfos(j+String.valueOf(i), info);
			ProDataUtils.setMap(j+String.valueOf(i), mEdtStandard);

			if (!isEditDetailEnable) {
				mEdtStandard.setEnabled(false);
			}
			layout.addView(v);
		}

		addView(view);
		view.invalidate();

	}

	/**
	 * 获取存放修改属性控件的集合
	 * 
	 * @return
	 */

	public void setStandards(ArrayList<IntentionDetailStandardInfo> infos) {

	}
}
