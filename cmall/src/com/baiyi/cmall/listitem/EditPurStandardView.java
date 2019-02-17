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
 * ÿһ�����Ե�View
 * 
 * @author lizl
 */
public class EditPurStandardView extends LinearLayout {

	// ������
	private Context context;
	// ÿһ�����Եķ�������
	private TextView mTxtTypeName;
	// ����View
	private View view;
	// ���Ե����Բ���
	private TableLayout mLinLayout;
	// �ָ���
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
	 * ��������
	 */
	private void initView() {
		view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_standard_parmas, null);
		mTxtTypeName = (TextView) view.findViewById(R.id.txt_standard_title);
		mLinLayout = (TableLayout) view.findViewById(R.id.lin_layout);
		viewLine = view.findViewById(R.id.view_line);

	}

	/**
	 * ����ÿһ�ַ��������
	 * 
	 * @param typeNme
	 */
	public void setmTxtTypeName(String typeNme, int i) {
		this.mTxtTypeName.setText(typeNme);
		// ��Ϊ��һ��ʱ������
		if (0 == i) {
			viewLine.setVisibility(View.GONE);
		} else {
			viewLine.setVisibility(View.VISIBLE);
		}

	}

	View line;
	ArrayList<TextView> textViews = new ArrayList<TextView>();

	/**
	 * ÿһ�����Ͷ�Ӧ�Ĺ�������б�
	 * 
	 * @param datas
	 */
	public void setListView(ArrayList<IntentionDetailStandardInfo> datas) {

		for (int i = 0; i < datas.size(); i++) {

			// ���IDʵ��
			line = ContextUtil.getLayoutInflater(context).inflate(
					R.layout.view_line_1, null);
			TableRow row = (TableRow) ContextUtil.getLayoutInflater(context)
					.inflate(R.layout.tab_row_hang_edit, null);
			TextView mTvName = (TextView) row.findViewById(R.id.tv_jian);
			EditText mEtValue = (EditText) row.findViewById(R.id.et_zhi);
			TextView mTvUnit = (TextView) row.findViewById(R.id.tv_unit);

			// ������
			mTvName.setText(datas.get(i).getPropertyname() + ":");
			// ����ֵ
			mEtValue.setText(datas.get(i).getPropertyvalue());

			// ���Ե�λ
			String unit = datas.get(i).getPropertyunit();
			if (unit.equals("null")) {
				mTvUnit.setText("");
			} else {
				mTvUnit.setText(unit);
			}

			// ����״̬�ж��Ƿ�ɲ���
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
	 * ��ȡ���е����Կؼ�
	 * 
	 * @return
	 */
	public ArrayList<TextView> getViews() {
		return textViews;
	}

	/**
	 * ��־λ��Ĭ��false�ɲ�����true���ɲ���
	 */
	public boolean standIsEnable = false;

	public void setStandIsEnable(boolean standIsEnable) {
		this.standIsEnable = standIsEnable;
	}
}
