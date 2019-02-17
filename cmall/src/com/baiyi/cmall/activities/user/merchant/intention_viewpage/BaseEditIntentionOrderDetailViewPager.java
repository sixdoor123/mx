package com.baiyi.cmall.activities.user.merchant.intention_viewpage;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.baiyi.cmall.entity.IntentionDetailStandardInfo;
import com.baiyi.cmall.entity.IntentionProviderDetailInfo;
import com.baiyi.cmall.pageviews.BaseScrollViewPageView;
import com.baiyi.cmall.views.itemview.MyLinearLayout;

/**
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-1-4 ����4:48:32
 */
public class BaseEditIntentionOrderDetailViewPager extends
		BaseScrollViewPageView {
	// ������
	public Context context;
	// ����Դ
	public IntentionProviderDetailInfo info;
	// �ɹ�������
	public ArrayList<IntentionDetailStandardInfo> purDetailStandardInfos;
	// ��Ӧ������
	public ArrayList<IntentionDetailStandardInfo> proDetailStandardInfos;

	// �Զ�������Բ���
	public MyLinearLayout layout;

	public BaseEditIntentionOrderDetailViewPager(Context context,
			IntentionProviderDetailInfo info) {
		super(context);
		this.info = info;
		proDetailStandardInfos = info.getProDetailStandardInfos();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		layout = new MyLinearLayout(context);
		addView(layout);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

	}

}
