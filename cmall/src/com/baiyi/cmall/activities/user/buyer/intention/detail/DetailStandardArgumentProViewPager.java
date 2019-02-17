package com.baiyi.cmall.activities.user.buyer.intention.detail;

import java.util.ArrayList;
import android.content.Context;
import com.baiyi.cmall.entity.IntentionDetailStandardInfo;
import com.baiyi.cmall.listitem.PurStandardView;
import com.baiyi.cmall.utils.DataUtils;
import com.baiyi.cmall.utils.Utils;

/**
 * �ɹ��������Ե�ViewPager
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2015-11-23 ����10:05:24
 */
public class DetailStandardArgumentProViewPager extends BaseDetailProViewPager {

	// ��������Դ
	private ArrayList<IntentionDetailStandardInfo> standDatas;

	/**
	 * ���ø���Ĺ�����
	 * 
	 * @param context
	 * @param purID
	 */
	public DetailStandardArgumentProViewPager(Context context, String purID) {
		super(context, purID);

	}

	/**
	 * �����б�
	 */
	@Override
	public void initContent() {
		// TODO Auto-generated method stub
		initData();
		if (Utils.isStringEmpty(standDatas)) {
			return;
		}
		// �����ķ�����
		ArrayList<String> strings = new ArrayList<String>();
		for (int j = 0; j < standDatas.size(); j++) {
			strings.add(standDatas.get(j).getCodevalue());
		}

		// ȥ�� ��� �����ķ��������ݵļ���
		ArrayList<String> purStandTitle = DataUtils.removeDuplicateObj(strings);

		for (int i = 0; i < purStandTitle.size(); i++) {
			// �õ�Ʒ�Ʒ���������Ӧ�����оۺ�
			ArrayList<IntentionDetailStandardInfo> infos = DataUtils
					.getStandardRelevantSource(purStandTitle.get(i), standDatas);

			PurStandardView argmentView = new PurStandardView(context);
			argmentView.setmTxtTypeName(purStandTitle.get(i), i);
			argmentView.setListView(infos);
			layout.addView(argmentView);

		}
	}

	/**
	 * ����
	 */
	private void initData() {
		standDatas = info.getStandardInfos();
	}

}
