package com.baiyi.cmall.activities.user.merchant.provider.intention.viewpager;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.baiyi.cmall.activities.main.BaseActivity;
import com.baiyi.cmall.activities.user.merchant.provider.viewpager.EditStandardArgmentView;
import com.baiyi.cmall.activities.user.merchant.provider.viewpager.ProDataUtils;
import com.baiyi.cmall.entity.IntentionDetailStandardInfo;
import com.baiyi.cmall.utils.DataUtils;
import com.baiyi.cmall.R;
import android.view.View.OnClickListener;

/**
 * 编辑采购详情-属性ViewPager
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-4 下午2:30:27
 */
public class EditIntentionStandardargDetailViewPager extends BeseEditIntentionProviderViewPager
		implements OnClickListener {

	// 属性数据源
	private ArrayList<IntentionDetailStandardInfo> standDatas;

	public EditIntentionStandardargDetailViewPager(Context context, String id,
			boolean isEditDetailEnable) {
		super(context, id);
		this.isEditDetailEnable = isEditDetailEnable;
	}

	/**
	 * 内容
	 */
	@Override
	public void initContent() {
		if (null != info) {
			standDatas = info.getStandardInfos();
		}
		// 参数的分类名
		if (null == standDatas) {
			return;
		}

		// ProDataUtils.setIndex(0);
		// ProDataUtils.setCount(0);

		// 拿到所有的规格分类名称
		ArrayList<String> strings = new ArrayList<String>();
		for (int j = 0; j < standDatas.size(); j++) {
			strings.add(standDatas.get(j).getCodevalue());
		}

		// 去重 （存放参数的分类名内容的集合）
		ArrayList<String> purStandTitle = DataUtils.removeDuplicateObj(strings);
		EditStandardArgmentView argmentView = null;
ProDataUtils.clareMap();
		for (int i = 0; i < purStandTitle.size(); i++) {
			// 得到品牌分类名所对应的所有数据集合
			ArrayList<IntentionDetailStandardInfo> infos = DataUtils
					.getStandardRelevantSource(purStandTitle.get(i), standDatas);
			argmentView = new EditStandardArgmentView(context);
			argmentView.setmTxtTypeName(purStandTitle.get(i), i);
			argmentView.setListView(infos, isEditDetailEnable,i);
			layout.addView(argmentView);
			layout.invalidate();
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDestroy() {

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_commit_modify:// 完成
//			editComplete("", 0, new OnEditSuccessClickListener() {
//
//				@Override
//				public void onSuccess(RequestNetResultInfo info) {
//					// TODO Auto-generated method stub
//
//				}
//			});
			break;
		case R.id.btn_cancel_modify:// 取消
			((BaseActivity) context).finish();
			break;
		}
	}

	@Override
	public boolean getInputGoodSInfo() {
		return false;
	}

}
