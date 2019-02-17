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
 * 我的供应-分类行
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-20 上午11:37:19
 */
public class PurchaseContentView extends LinearLayout implements
		OnClickListener {

	// 上下文
	private Context context;

	public PurchaseContentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView(context);
	}

	public PurchaseContentView(Context context) {
		this(context, null);
	}

	// 把每一类型的集合取出来(就是要带到先一个界面)
	private ArrayList<GoodsSourceInfo> infos;
	// 详细内容分类条目
	private LinearLayout mLayout;
	// 供应商名称
	private TextView mTxtCompanyName;
	// 分割线
	private View viewLine;
	// 重量、数量
	private TextView mTxtTotalWeight;
	// 数据源实体
	private GoodsSourceInfo info;

	/**
	 * 初始化View
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
				.setText(/* Utils.dealWeight(info.getGoodSWeight()) + "吨" */info
						.getStatename());
	}

	/**
	 * 详细内容条目的点击事件
	 * 
	 * 
	 */
	@Override
	public void onClick(View v) {
		((BaseActivity) context).goActivity(info,
				PurchaseContentDetailActivity.class);
	}

	/**
	 * 得到数据源
	 * 
	 * @param infos
	 */
	public void setInfos(ArrayList<GoodsSourceInfo> infos) {
		this.infos = infos;
	}
}
