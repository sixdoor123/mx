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
 * ÿһ�����Ե�View
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-30 ����10:37:31
 */
public class EditStandardArgmentView extends LinearLayout {

	// ������
	private Context context;

	public EditStandardArgmentView(Context context) {
		this(context, null);
	}

	// �û�ID
	private String userID;

	public EditStandardArgmentView(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.context = context;
		this.setOrientation(LinearLayout.VERTICAL);
		initView();
	}

	private PullToRefreshListView listView;
	// ÿһ�����Եķ�������
	private TextView mTxtTypeName;
	// ����View
	private View view;
	// �ָ���
	private View viewLine;

	private LinearLayout layout;

	/**
	 * ��������
	 */
	private void initView() {
		view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_standard_parmas, null);
		mTxtTypeName = (TextView) view.findViewById(R.id.txt_standard_title);
		layout = (LinearLayout) view.findViewById(R.id.lin_layout);
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
			this.mTxtTypeName.setPadding(0, 0, 0, 0);
			viewLine.setVisibility(View.VISIBLE);
		}

	}

	// �༭�����
	private EditText mEdtStandard;
	// ��������
	private TextView mTxtStandardName;
	// ��ʾ��λ
	private TextView mTxtUnit;

	/**
	 * aÿһ�����Ͷ�Ӧ�Ĺ�������б�
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
	 * ��ȡ����޸����Կؼ��ļ���
	 * 
	 * @return
	 */

	public void setStandards(ArrayList<IntentionDetailStandardInfo> infos) {

	}
}
