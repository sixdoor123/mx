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
 * �ɹ�����
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-4 ����1:18:31
 */
public class PurchaseDetailViewPager extends BasePurchaseDetailViewPager {

	// TableRow�Ϸ��ķָ���
	private View line;
	// �ɹ����������
	private TableLayout purLayout;

	/**
	 * ���ø���Ĺ��췽��
	 * 
	 * @param sourceInfo
	 */
	public PurchaseDetailViewPager(Context context, String purID,
			GoodsSourceInfo sourceInfo) {
		super(context, purID, sourceInfo);
	}

	/**
	 * ��ʼ������
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
	 * ��ʾ������Ϣ
	 */
	private void initShowIntent() {
		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_pur_intention_layout, null);
		purLayout = (TableLayout) view.findViewById(R.id.tab_pur_detail);
		// ����Ŀؼ�����������
		LinearLayout purControl = (LinearLayout) view
				.findViewById(R.id.lin_pur_control);
		View bigLine = view.findViewById(R.id.view_line_15);
		bigLine.setVisibility(View.GONE);
		purControl.setVisibility(View.GONE);
		layout.addView(view);

		updatePurchaseView();
	}

	/**
	 * ���½�����Ϣ
	 */

	private void updatePurchaseView() {

		// ���ݷ��������ݣ������ɹ���Ϣ���ݼ���
		ArrayList<IndutryArgumentInfo> infos = DataUtils
				.getSystemPurDetail(info);
		/*
		 * ѭ����������
		 */
		for (int i = 0; i < infos.size(); i++) {
			line = ContextUtil.getLayoutInflater(context).inflate(
					R.layout.view_line_1, null);
			TableRow row = (TableRow) ContextUtil.getLayoutInflater(context)
					.inflate(R.layout.tab_row_hang, null);
			TextView name = (TextView) row.findViewById(R.id.tv_jian);
			TextView value = (TextView) row.findViewById(R.id.tv_zhi);
			// ��
			name.setText(infos.get(i).getArgNmae());
			// ֵ
			value.setText(infos.get(i).getArgValue());
			purLayout.addView(row);
			purLayout.addView(line, ((BaseActivity) context).getScreenWidth(),
					1);
		}

	}

}
