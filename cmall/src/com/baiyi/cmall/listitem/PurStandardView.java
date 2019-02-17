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
 * ÿһ�����Ե�View
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-30 ����10:37:31
 */
public class PurStandardView extends LinearLayout {

	// ������
	private Context context;
	// ÿһ�����Եķ�������
	private TextView mTxtTypeName;
	// ����View
	private View view;
	// ���Ե����Բ���
	private LinearLayout mLinLayout;
	// �ָ���
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
	 * ��������
	 */
	private void initView() {
		view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_standard_parmas, null);
		mTxtTypeName = (TextView) view.findViewById(R.id.txt_standard_title);
		mLinLayout = (LinearLayout) view.findViewById(R.id.lin_layout);
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

	/**
	 * aÿһ�����Ͷ�Ӧ�Ĺ�������б�
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
			// ���Ե�λ
			String unit = datas.get(i).getPropertyunit();
			mTvValue.setText(datas.get(i).getPropertyvalue() + " "
					+ (unit.equals("null") ? "" : unit));

			mLinLayout.addView(mLinView);
		}
		addView(view);
	}
	
	/**
	 * ����̳�����Ʒ��������
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
			// ��������
			mTvName.setText(datas.get(i).getPn() + ":");
			// ����ֵ/��λ
			mTvValue.setText(datas.get(i).getPv() + " " + datas.get(i).getPu());
			
			mLinLayout.addView(mLinView);
		}
		addView(view);
	}
}
