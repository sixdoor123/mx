package com.baiyi.cmall.listitem;

import java.util.ArrayList;

import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.user.buyer.intention.PurchaseIntentionOrderActivity;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.core.util.ContextUtil;
import com.baiyi.cmall.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 我是采购商——我的采购——供应商条目
 * 
 * @author lizl
 * 
 */
public class MyPurInfoItem extends LinearLayout {

	private Context context;
	private TextView merchantNmae;// 商家名字
	private TextView auditState;// 状态
	private TextView companyName;// 交割地
	private TextView numberTextView;// 数量
	private TextView price;// 价格范围

	// 初次显示的数据个数
	private static final int num = 3;
	// 子条目
	private View viewItem;
	// 动态加载的项目数据
	private ArrayList<GoodsSourceInfo> disDatas;
	// 解析器
	private LayoutInflater inflater;

	public MyPurInfoItem(Context context) {
		super(context);
		this.context = context;
		setOrientation(VERTICAL);
		// 添加一个线性布局
		inflater = ContextUtil.getLayoutInflater(context);

	}

	/**
	 * 设置数据
	 * 
	 * @param oneClassifyEntity
	 * 
	 * @param disDatas
	 * @param notDisDatas
	 */
	public void setData(ArrayList<GoodsSourceInfo> infos) {

		disDatas = new ArrayList<GoodsSourceInfo>();

		/**
		 * 显示三条数据
		 */

		for (int i = 0; i < infos.size(); i++) {

			if (i < num) {
				disDatas.add(infos.get(i));
			}

		}
		disAllData();

	}

	/**
	 * 用此方法遍历显示数据
	 */
	private void disAllData() {

		for (GoodsSourceInfo goodsSourceInfo : disDatas) {

			disData(goodsSourceInfo);
			addView(viewItem);
		}
	}

	/**
	 * 显示具体的数据
	 * 
	 * @param classifyInfoEntity
	 */
	public void disData(final GoodsSourceInfo info) {

		viewItem = inflater.inflate(R.layout.item_5_view, null);

		viewItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/**
				 * 跳转至采购详情页面
				 */
				((BaseActivity) context).goActivity(
						PurchaseIntentionOrderActivity.class,
						info.getGoodSPurchaseOrderId());

			}
		});
		merchantNmae = (TextView) viewItem
				.findViewById(R.id.tv_content1);
		auditState = (TextView) viewItem.findViewById(R.id.tv_content2);

		companyName = (TextView) viewItem.findViewById(R.id.tv_content3);
		numberTextView = (TextView) viewItem
				.findViewById(R.id.tv_content4);
		price = (TextView) viewItem
				.findViewById(R.id.tv_content5);

		merchantNmae.setText(info.getGoodSName());
		auditState.setText(info.getIntentionOrderState());
		companyName.setText(info.getGoodSCompanyNmae());
		numberTextView.setText(Utils.dealWeight(info.getKuCun()) + "吨");
		price.setText(Utils.dealPrice(info.getGoodSPrice()) + "元/吨");

	}
}
