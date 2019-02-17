package com.baiyi.cmall.activities.user.buyer;

import java.util.ArrayList;

import android.content.Context;
import android.widget.TextView;

import com.baiyi.cmall.entity.IntentionDetailStandardInfo;
import com.baiyi.cmall.listitem.EditPurStandardView;
import com.baiyi.cmall.utils.DataUtils;
import com.baiyi.cmall.views.pageview.BaseEditMyPurchaseDetailViewPager;

/**
 * �༭�ɹ�����-����ViewPager
 * 
 * @author lizl
 */
public class EditPurchaseStandardArgViewPager extends
		BaseEditMyPurchaseDetailViewPager {

	// ��݋���Ƿ�ɲ����ı�־λ��trueΪ�ɲ���
	public boolean standIsEnable = false;
	// ��������Դ
	private ArrayList<IntentionDetailStandardInfo> standDatas;

	public EditPurchaseStandardArgViewPager(Context context, int id,
			boolean isShow) {
		super(context, id, isShow);

	}

	/**
	 * ����
	 */
	@Override
	public void initContent() {
		standDatas = info.getStandardInfos();

		// �����ķ�����
		if (null == standDatas) {
			return;
		}
		// �õ����еĹ���������
		ArrayList<String> strings = new ArrayList<String>();
		for (int j = 0; j < standDatas.size(); j++) {
			strings.add(standDatas.get(j).getCodevalue());
		}

		// ȥ�� ����Ų����ķ��������ݵļ��ϣ�
		ArrayList<String> purStandTitle = DataUtils.removeDuplicateObj(strings);

		for (int i = 0; i < purStandTitle.size(); i++) {
			// �õ�Ʒ�Ʒ���������Ӧ���������ݼ���
			ArrayList<IntentionDetailStandardInfo> infos = DataUtils
					.getStandardRelevantSource(purStandTitle.get(i), standDatas);

			EditPurStandardView argmentView = new EditPurStandardView(context);
			argmentView.setStandIsEnable(standIsEnable);
			argmentView.setmTxtTypeName(purStandTitle.get(i), i);
			argmentView.setListView(infos);
			standardView(argmentView.getViews());
			// ��Ӷ�Ӧ�Ĺ����Ϣ,�̶�����
			standardId(infos);
			layout.addView(argmentView);
			layout.invalidate();
		}
	}

	ArrayList<IntentionDetailStandardInfo> newStandDatas = new ArrayList<IntentionDetailStandardInfo>();
	// ������е������
	ArrayList<TextView> newViews = new ArrayList<TextView>();

	/**
	 * ���ԵĿؼ�View �༭ʱ������Щ�ؼ���ȡ����
	 * 
	 * @param arrayList
	 */
	private void standardView(ArrayList<TextView> arrayList) {

		for (TextView view : arrayList) {

			newViews.add(view);
		}

	}

	/**
	 * ��ȡ���е������
	 */
	public ArrayList<TextView> getAllstandardView() {

		return newViews;
	}

	/**
	 * ����ID ʵ����
	 * 
	 * @param arrayList
	 */
	private void standardId(ArrayList<IntentionDetailStandardInfo> arrayList) {

		for (IntentionDetailStandardInfo info : arrayList) {

			newStandDatas.add(info);
		}

	}

	/**
	 * ��ȡ���е�id
	 */
	public ArrayList<IntentionDetailStandardInfo> getAllstandardId() {

		return newStandDatas;
	}

	/**
	 * ���ڱ�������Ƿ�ɲ���
	 * @param standIsEnable
	 */
	public void setStandIsEnable(boolean standIsEnable) {
		this.standIsEnable = standIsEnable;
	}
}
