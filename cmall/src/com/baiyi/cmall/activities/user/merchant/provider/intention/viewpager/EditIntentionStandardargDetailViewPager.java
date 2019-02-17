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
 * �༭�ɹ�����-����ViewPager
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-12-4 ����2:30:27
 */
public class EditIntentionStandardargDetailViewPager extends BeseEditIntentionProviderViewPager
		implements OnClickListener {

	// ��������Դ
	private ArrayList<IntentionDetailStandardInfo> standDatas;

	public EditIntentionStandardargDetailViewPager(Context context, String id,
			boolean isEditDetailEnable) {
		super(context, id);
		this.isEditDetailEnable = isEditDetailEnable;
	}

	/**
	 * ����
	 */
	@Override
	public void initContent() {
		if (null != info) {
			standDatas = info.getStandardInfos();
		}
		// �����ķ�����
		if (null == standDatas) {
			return;
		}

		// ProDataUtils.setIndex(0);
		// ProDataUtils.setCount(0);

		// �õ����еĹ���������
		ArrayList<String> strings = new ArrayList<String>();
		for (int j = 0; j < standDatas.size(); j++) {
			strings.add(standDatas.get(j).getCodevalue());
		}

		// ȥ�� ����Ų����ķ��������ݵļ��ϣ�
		ArrayList<String> purStandTitle = DataUtils.removeDuplicateObj(strings);
		EditStandardArgmentView argmentView = null;
ProDataUtils.clareMap();
		for (int i = 0; i < purStandTitle.size(); i++) {
			// �õ�Ʒ�Ʒ���������Ӧ���������ݼ���
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
		case R.id.btn_commit_modify:// ���
//			editComplete("", 0, new OnEditSuccessClickListener() {
//
//				@Override
//				public void onSuccess(RequestNetResultInfo info) {
//					// TODO Auto-generated method stub
//
//				}
//			});
			break;
		case R.id.btn_cancel_modify:// ȡ��
			((BaseActivity) context).finish();
			break;
		}
	}

	@Override
	public boolean getInputGoodSInfo() {
		return false;
	}

}
