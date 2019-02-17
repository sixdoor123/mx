package com.baiyi.cmall.activities.user.buyer.intention.detail;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.baiyi.cmall.Config;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.entity.IndutryArgumentInfo;
import com.baiyi.cmall.utils.DataUtils;
import com.baiyi.cmall.views.itemview.MyLinearLayout;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

/**
 * 采购详情
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-4 下午1:18:31
 */
public class DetailProViewPager extends BaseDetailProViewPager {

	// TableRow上方的分割线
	private View line;

	/**
	 * 调用父类的构造方法
	 */
	public DetailProViewPager(Context context, String attentionID) {
		super(context, attentionID);
	}

	/**
	 * 初始化内容
	 */
	@Override
	public void initContent() {
		// TODO Auto-generated method stub

		initProviderContent();
	}

	// 供应，存放详情
	private TableLayout proLayout;
	// 供应控制
	private LinearLayout proControl;

	/**
	 * 供应信息
	 */
	private void initProviderContent() {
		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_pro_intention_layout, null);
		proLayout = (TableLayout) view.findViewById(R.id.tab_pro_detail);
		proControl = (LinearLayout) view.findViewById(R.id.lin_pro_control);
		proControl.setVisibility(View.GONE);
		layout.addView(view);
		// 更新信息
		updateProviderView();
	}

	/**
	 * 更新供应信息的界面
	 */
	private void updateProviderView() {

		ArrayList<IndutryArgumentInfo> infos = DataUtils.getOfferDetail(info);
		for (int i = 0; i < infos.size(); i++) {
			line = ContextUtil.getLayoutInflater(context).inflate(
					R.layout.view_line_1, null);

			TableRow row = (TableRow) ContextUtil.getLayoutInflater(context)
					.inflate(R.layout.tab_row_hang, null);
			TextView name = (TextView) row.findViewById(R.id.tv_jian);
			TextView value = (TextView) row.findViewById(R.id.tv_zhi);
			name.setText(infos.get(i).getArgNmae());
			value.setText(infos.get(i).getArgValue());
			proLayout.addView(row);

			//去掉末行的分割线
			if (i != infos.size() - 1) {
				proLayout.addView(line,
						Config.getInstance().getScreenWidth(context), 1);
			}
		}

	}

}
