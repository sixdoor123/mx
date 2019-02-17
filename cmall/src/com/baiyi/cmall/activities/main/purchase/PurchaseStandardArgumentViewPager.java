package com.baiyi.cmall.activities.main.purchase;

import java.util.ArrayList;
import android.content.Context;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.IntentionDetailStandardInfo;
import com.baiyi.cmall.entity.RequestNetResultInfo;
import com.baiyi.cmall.listitem.PurStandardView;
import com.baiyi.cmall.utils.DataUtils;
import com.baiyi.cmall.views.pageview.BasePurchaseDetailViewPager;

/**
 * 采购详情属性的ViewPager
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-23 上午10:05:24
 */
public class PurchaseStandardArgumentViewPager extends
		BasePurchaseDetailViewPager {

	// 属性数据源
	private ArrayList<IntentionDetailStandardInfo> standDatas;

	/**
	 * 调用父类的构造器
	 * 
	 * @param context
	 * @param purID
	 * @param sourceInfo
	 */
	public PurchaseStandardArgumentViewPager(Context context, String purID,
			GoodsSourceInfo sourceInfo) {
		super(context, purID, sourceInfo);

	}

	/**
	 * 属性列表
	 */
	@Override
	public void initContent(RequestNetResultInfo resultInfo) {
		// TODO Auto-generated method stub
		if (-1 == resultInfo.getStatus()) {
			initNotContent();
		} else {
			initShow();
		}
	}

	private void initShow() {
		initData();
		// 参数的分类名
		ArrayList<String> strings = new ArrayList<String>();
		for (int j = 0; j < standDatas.size(); j++) {
			strings.add(standDatas.get(j).getCodevalue());
		}

		// 去重 存放 参数的分类名内容的集合
		ArrayList<String> purStandTitle = DataUtils.removeDuplicateObj(strings);

		for (int i = 0; i < purStandTitle.size(); i++) {
			// 得到品牌分类名所对应的所有聚合
			ArrayList<IntentionDetailStandardInfo> infos = DataUtils
					.getStandardRelevantSource(purStandTitle.get(i), standDatas);

			PurStandardView argmentView = new PurStandardView(context);
			argmentView.setmTxtTypeName(purStandTitle.get(i), i);
			argmentView.setListView(infos);
			layout.addView(argmentView);

		}
	}

	/**
	 * 数据
	 */
	private void initData() {
		standDatas = info.getStandardInfos();
	}

}
