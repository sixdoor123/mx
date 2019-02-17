package com.baiyi.cmall.activities.main.purchase;

import java.util.ArrayList;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;
import android.view.View.OnClickListener;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.IndutryArgumentInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.utils.DataUtils;
import com.baiyi.cmall.views.itemview.MyLinearLayout;
import com.baiyi.cmall.views.pageview.BasePurchaseDetailViewPager;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 采购详情
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-4 下午1:18:31
 */
public class PurchaseDetailViewPager extends BasePurchaseDetailViewPager {

	// TableRow上方的分割线
	private View line;
	// 采购，存放详情
	private TableLayout purLayout;

	/**
	 * 调用父类的构造方法
	 * 
	 * @param sourceInfo
	 */
	public PurchaseDetailViewPager(Context context, String purID,
			GoodsSourceInfo sourceInfo) {
		super(context, purID, sourceInfo);
	}

	/**
	 * 初始化内容
	 */
	@Override
	public void initContent(RequestNetResultInfo resultInfo) {

		if (-1 == resultInfo.getStatus()) {
			initNotContent();
		} else {
			initShowIntent();
		}

	}


	/**
	 * 显示详情信息
	 */
	private void initShowIntent() {
		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_pur_intention_layout, null);
		purLayout = (TableLayout) view.findViewById(R.id.tab_pur_detail);
		// 多余的控件，将其隐藏
		LinearLayout purControl = (LinearLayout) view
				.findViewById(R.id.lin_pur_control);
		View bigLine = view.findViewById(R.id.view_line_15);
		bigLine.setVisibility(View.GONE);
		purControl.setVisibility(View.GONE);
		layout.addView(view);

		updatePurchaseView();
	}

	/**
	 * 更新界面信息
	 */

	private void updatePurchaseView() {

		// 根据服务器数据，创建采购信息数据集合
		ArrayList<IndutryArgumentInfo> infos = DataUtils
				.getSystemPurDetail(info);
		/*
		 * 循环加载数据
		 */
		for (int i = 0; i < infos.size(); i++) {
			line = ContextUtil.getLayoutInflater(context).inflate(
					R.layout.view_line_1, null);
			TableRow row = (TableRow) ContextUtil.getLayoutInflater(context)
					.inflate(R.layout.tab_row_hang, null);
			TextView name = (TextView) row.findViewById(R.id.tv_jian);
			TextView value = (TextView) row.findViewById(R.id.tv_zhi);
			// 键
			name.setText(infos.get(i).getArgNmae());
			// 值
			value.setText(infos.get(i).getArgValue());
			purLayout.addView(row);
			purLayout.addView(line, ((BaseActivity) context).getScreenWidth(),
					1);
		}

	}

}
