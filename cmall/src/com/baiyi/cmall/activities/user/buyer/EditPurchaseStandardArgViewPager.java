package com.baiyi.cmall.activities.user.buyer;

import java.util.ArrayList;

import android.content.Context;
import android.widget.TextView;

import com.baiyi.cmall.entity.IntentionDetailStandardInfo;
import com.baiyi.cmall.listitem.EditPurStandardView;
import com.baiyi.cmall.utils.DataUtils;
import com.baiyi.cmall.views.pageview.BaseEditMyPurchaseDetailViewPager;

/**
 * 编辑采购详情-属性ViewPager
 * 
 * @author lizl
 */
public class EditPurchaseStandardArgViewPager extends
		BaseEditMyPurchaseDetailViewPager {

	// 編輯框是否可操作的标志位，true为可操作
	public boolean standIsEnable = false;
	// 属性数据源
	private ArrayList<IntentionDetailStandardInfo> standDatas;

	public EditPurchaseStandardArgViewPager(Context context, int id,
			boolean isShow) {
		super(context, id, isShow);

	}

	/**
	 * 内容
	 */
	@Override
	public void initContent() {
		standDatas = info.getStandardInfos();

		// 参数的分类名
		if (null == standDatas) {
			return;
		}
		// 拿到所有的规格分类名称
		ArrayList<String> strings = new ArrayList<String>();
		for (int j = 0; j < standDatas.size(); j++) {
			strings.add(standDatas.get(j).getCodevalue());
		}

		// 去重 （存放参数的分类名内容的集合）
		ArrayList<String> purStandTitle = DataUtils.removeDuplicateObj(strings);

		for (int i = 0; i < purStandTitle.size(); i++) {
			// 得到品牌分类名所对应的所有数据集合
			ArrayList<IntentionDetailStandardInfo> infos = DataUtils
					.getStandardRelevantSource(purStandTitle.get(i), standDatas);

			EditPurStandardView argmentView = new EditPurStandardView(context);
			argmentView.setStandIsEnable(standIsEnable);
			argmentView.setmTxtTypeName(purStandTitle.get(i), i);
			argmentView.setListView(infos);
			standardView(argmentView.getViews());
			// 添加对应的规格信息,固定不变
			standardId(infos);
			layout.addView(argmentView);
			layout.invalidate();
		}
	}

	ArrayList<IntentionDetailStandardInfo> newStandDatas = new ArrayList<IntentionDetailStandardInfo>();
	// 存放所有的输入框
	ArrayList<TextView> newViews = new ArrayList<TextView>();

	/**
	 * 属性的控件View 编辑时用于这些控件获取数据
	 * 
	 * @param arrayList
	 */
	private void standardView(ArrayList<TextView> arrayList) {

		for (TextView view : arrayList) {

			newViews.add(view);
		}

	}

	/**
	 * 获取所有的输入框
	 */
	public ArrayList<TextView> getAllstandardView() {

		return newViews;
	}

	/**
	 * 规格的ID 实体类
	 * 
	 * @param arrayList
	 */
	private void standardId(ArrayList<IntentionDetailStandardInfo> arrayList) {

		for (IntentionDetailStandardInfo info : arrayList) {

			newStandDatas.add(info);
		}

	}

	/**
	 * 获取所有的id
	 */
	public ArrayList<IntentionDetailStandardInfo> getAllstandardId() {

		return newStandDatas;
	}

	/**
	 * 用于标记属性是否可操作
	 * @param standIsEnable
	 */
	public void setStandIsEnable(boolean standIsEnable) {
		this.standIsEnable = standIsEnable;
	}
}
