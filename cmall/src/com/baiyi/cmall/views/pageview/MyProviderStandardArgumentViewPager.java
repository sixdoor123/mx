package com.baiyi.cmall.views.pageview;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.baiyi.cmall.entity.GoodsSourceInfo;
import com.baiyi.cmall.entity.IntentionDetailStandardInfo;
import com.baiyi.cmall.listitem.PurStandardView;
import com.baiyi.cmall.pageviews.BaseScrollViewPageView;
import com.baiyi.cmall.utils.DataUtils;
import com.baiyi.cmall.utils.Utils;
import com.baiyi.cmall.views.itemview.MyLinearLayout;
import android.view.View.OnClickListener;

/**
 * ���Ե�ViewPager
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-23 ����10:05:24
 */
public class MyProviderStandardArgumentViewPager extends BaseScrollViewPageView
		implements OnClickListener {

	// ɽ����
	private Context context;
	// �Զ�������Բ���
	private MyLinearLayout layout;

	public MyProviderStandardArgumentViewPager(Context context,
			GoodsSourceInfo info) {
		super(context);
		this.context = context;
		if (null != info) {
			datas = info.getStandardInfos();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		layout = new MyLinearLayout(context);
		if (!Utils.isStringEmpty(datas)) {
			initStandard();
			addView(layout);
		}

	}

	// �������ݼ���
	private ArrayList<IntentionDetailStandardInfo> datas;

	/**
	 * ���Է����б�
	 */
	protected void initStandard() {
		// �����ķ�����
		ArrayList<String> strings = new ArrayList<String>();
		for (int j = 0; j < datas.size(); j++) {
			strings.add(datas.get(j).getCodevalue());
		}

		// ȥ�� ��� �����ķ��������ݵļ���
		ArrayList<String> providerContents = DataUtils
				.removeDuplicateObj(strings);

		for (int i = 0; i < providerContents.size(); i++) {
			// �õ�Ʒ�Ʒ���������Ӧ�����оۺ�
			ArrayList<IntentionDetailStandardInfo> infos = DataUtils
					.getStandardRelevantSource(providerContents.get(i), datas);

			PurStandardView argmentView = new PurStandardView(context);
			argmentView.setmTxtTypeName(providerContents.get(i), i);
			argmentView.setListView(infos);
			layout.addView(argmentView);
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {

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

		}
	}

}
