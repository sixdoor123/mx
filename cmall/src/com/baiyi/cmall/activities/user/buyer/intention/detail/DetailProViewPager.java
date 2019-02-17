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
 * �ɹ�����
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-4 ����1:18:31
 */
public class DetailProViewPager extends BaseDetailProViewPager {

	// TableRow�Ϸ��ķָ���
	private View line;

	/**
	 * ���ø���Ĺ��췽��
	 */
	public DetailProViewPager(Context context, String attentionID) {
		super(context, attentionID);
	}

	/**
	 * ��ʼ������
	 */
	@Override
	public void initContent() {
		// TODO Auto-generated method stub

		initProviderContent();
	}

	// ��Ӧ���������
	private TableLayout proLayout;
	// ��Ӧ����
	private LinearLayout proControl;

	/**
	 * ��Ӧ��Ϣ
	 */
	private void initProviderContent() {
		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_pro_intention_layout, null);
		proLayout = (TableLayout) view.findViewById(R.id.tab_pro_detail);
		proControl = (LinearLayout) view.findViewById(R.id.lin_pro_control);
		proControl.setVisibility(View.GONE);
		layout.addView(view);
		// ������Ϣ
		updateProviderView();
	}

	/**
	 * ���¹�Ӧ��Ϣ�Ľ���
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

			//ȥ��ĩ�еķָ���
			if (i != infos.size() - 1) {
				proLayout.addView(line,
						Config.getInstance().getScreenWidth(context), 1);
			}
		}

	}

}
