package com.baiyi.cmall.views.itemview;

import java.util.ArrayList;
import java.util.Map;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.user.merchant.MyProviderDetailsActivity;
import com.baiyi.cmall.activities.user.merchant.provider.PurchaseContentDetailActivity;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

import android.R.integer;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

/**
 * �ҵĹ�Ӧ-������
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-20 ����11:37:19
 */
public class PurchaseContentView extends LinearLayout implements
		OnClickListener {

	// ������
	private Context context;

	public PurchaseContentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView(context);
	}

	public PurchaseContentView(Context context) {
		this(context, null);
	}

	// ��ÿһ���͵ļ���ȡ����(����Ҫ������һ������)
	private ArrayList<GoodsSourceInfo> infos;
	// ��ϸ���ݷ�����Ŀ
	private LinearLayout mLayout;
	// ��Ӧ������
	private TextView mTxtCompanyName;
	// �ָ���
	private View viewLine;
	// ����������
	private TextView mTxtTotalWeight;
	// ����Դʵ��
	private GoodsSourceInfo info;

	/**
	 * ��ʼ��View
	 */
	private void initView(Context context) {
		View view = ContextUtil.getLayoutInflater(context).inflate(
				R.layout.activity_order_list, null);
		addView(view);
		mTxtCompanyName = (TextView) view.findViewById(R.id.txt_company_name);
		mTxtTotalWeight = (TextView) view.findViewById(R.id.txt_total_weight);
		mLayout = (LinearLayout) view.findViewById(R.id.ll);
		mLayout.setOnClickListener(this);
		viewLine = view.findViewById(R.id.v_line);

	}

	public void setViewData(GoodsSourceInfo info, int state) {
		this.info = info;
		if (0 == state) {
			viewLine.setVisibility(View.GONE);
		} else {
			viewLine.setVisibility(View.VISIBLE);
		}
		String content = info.getGoodSName() + "(" + info.getGoodSCategory()
				+ "/" + info.getGoodSBrand() /* + "/" + info.getGoodSMerchant() */
				+ ")";
		mTxtCompanyName.setText(content);
		mTxtTotalWeight
				.setText(/* Utils.dealWeight(info.getGoodSWeight()) + "��" */info
						.getStatename());
	}

	/**
	 * ��ϸ������Ŀ�ĵ���¼�
	 * 
	 * 
	 */
	@Override
	public void onClick(View v) {
		((BaseActivity) context).goActivity(info,
				PurchaseContentDetailActivity.class);
	}

	/**
	 * �õ�����Դ
	 * 
	 * @param infos
	 */
	public void setInfos(ArrayList<GoodsSourceInfo> infos) {
		this.infos = infos;
	}
}
