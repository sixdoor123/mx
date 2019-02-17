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
 * ���ǲɹ��̡����ҵĲɹ�������Ӧ����Ŀ
 * 
 * @author lizl
 * 
 */
public class MyPurInfoItem extends LinearLayout {

	private Context context;
	private TextView merchantNmae;// �̼�����
	private TextView auditState;// ״̬
	private TextView companyName;// �����
	private TextView numberTextView;// ����
	private TextView price;// �۸�Χ

	// ������ʾ�����ݸ���
	private static final int num = 3;
	// ����Ŀ
	private View viewItem;
	// ��̬���ص���Ŀ����
	private ArrayList<GoodsSourceInfo> disDatas;
	// ������
	private LayoutInflater inflater;

	public MyPurInfoItem(Context context) {
		super(context);
		this.context = context;
		setOrientation(VERTICAL);
		// ���һ�����Բ���
		inflater = ContextUtil.getLayoutInflater(context);

	}

	/**
	 * ��������
	 * 
	 * @param oneClassifyEntity
	 * 
	 * @param disDatas
	 * @param notDisDatas
	 */
	public void setData(ArrayList<GoodsSourceInfo> infos) {

		disDatas = new ArrayList<GoodsSourceInfo>();

		/**
		 * ��ʾ��������
		 */

		for (int i = 0; i < infos.size(); i++) {

			if (i < num) {
				disDatas.add(infos.get(i));
			}

		}
		disAllData();

	}

	/**
	 * �ô˷���������ʾ����
	 */
	private void disAllData() {

		for (GoodsSourceInfo goodsSourceInfo : disDatas) {

			disData(goodsSourceInfo);
			addView(viewItem);
		}
	}

	/**
	 * ��ʾ���������
	 * 
	 * @param classifyInfoEntity
	 */
	public void disData(final GoodsSourceInfo info) {

		viewItem = inflater.inflate(R.layout.item_5_view, null);

		viewItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				/**
				 * ��ת���ɹ�����ҳ��
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
		numberTextView.setText(Utils.dealWeight(info.getKuCun()) + "��");
		price.setText(Utils.dealPrice(info.getGoodSPrice()) + "Ԫ/��");

	}
}
