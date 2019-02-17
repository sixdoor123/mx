package com.baiyi.cmall.listitem;

import java.util.ArrayList;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.adapter.StandardArgumentAdapter;
import com.baiyi.cmall.entity.IntentionDetailStandardInfo;
import com.baiyi.cmall.utils.MobileStateUtils;
import com.baiyi.cmall.views.pulldownview.PullToRefreshListView;
import com.baiyi.cmall.views.pulldownview.PullToRefreshBase.Mode;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

import android.R.integer;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * 每一种属性的View
 * 
 * @author lizl
 */
public class EditPurStandardView extends LinearLayout {

	// 上下文
	private Context context;
	// 每一种属性的分类名称
	private TextView mTxtTypeName;
	// 布局View
	private View view;
	// 属性的线性布局
	private TableLayout mLinLayout;
	// 分割线
	private View viewLine;

	public EditPurStandardView(Context context) {
		this(context, null);
	}

	public EditPurStandardView(Context context, AttributeSet attrs) {
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
		mLinLayout = (TableLayout) view.findViewById(R.id.lin_layout);
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

	View line;
	ArrayList<TextView> textViews = new ArrayList<TextView>();

	/**
	 * 每一种类型对应的规格类型列表
	 * 
	 * @param datas
	 */
	public void setListView(ArrayList<IntentionDetailStandardInfo> datas) {

		for (int i = 0; i < datas.size(); i++) {

			// 规格ID实体
			line = ContextUtil.getLayoutInflater(context).inflate(
					R.layout.view_line_1, null);
			TableRow row = (TableRow) ContextUtil.getLayoutInflater(context)
					.inflate(R.layout.tab_row_hang_edit, null);
			TextView mTvName = (TextView) row.findViewById(R.id.tv_jian);
			EditText mEtValue = (EditText) row.findViewById(R.id.et_zhi);
			TextView mTvUnit = (TextView) row.findViewById(R.id.tv_unit);

			// 属性名
			mTvName.setText(datas.get(i).getPropertyname() + ":");
			// 属性值
			mEtValue.setText(datas.get(i).getPropertyvalue());

			// 属性单位
			String unit = datas.get(i).getPropertyunit();
			if (unit.equals("null")) {
				mTvUnit.setText("");
			} else {
				mTvUnit.setText(unit);
			}

			// 根据状态判断是否可操作
			if (standIsEnable) {
				mEtValue.setEnabled(false);
			}

			textViews.add(mEtValue);

			mLinLayout.addView(row);
			mLinLayout.addView(line,
					Config.getInstance().getScreenWidth(context), 1);
		}
		addView(view);
	}

	/**
	 * 获取所有的属性控件
	 * 
	 * @return
	 */
	public ArrayList<TextView> getViews() {
		return textViews;
	}

	/**
	 * 标志位，默认false可操作，true不可操作
	 */
	public boolean standIsEnable = false;

	public void setStandIsEnable(boolean standIsEnable) {
		this.standIsEnable = standIsEnable;
	}
}
